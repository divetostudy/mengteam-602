<!-- NewsManagement.vue -->
<template>
  <div class="container" v-loading.lock="fullscreenLoading">
    <el-button type="primary" @click="addNews">添加新闻</el-button>

    <el-table
      :data="tableData"
      :header-cell-style="{ position: 'sticky', top: '0', zIndex: 1 }"
      style="width: 100%; flex: 1;"
      height="calc(85vh - 5px)"
    >
      <el-table-column prop="title" label="标题" />

      <el-table-column
        prop="category"
        label="分类"
        :filters="categoryFilters"
        :filter-method="filterCategory"
        filter-placement="bottom-end"
      >
        <template #default="scope">
          {{ displayCategory(scope.row.category) }}
        </template>
      </el-table-column>

      <el-table-column label="新闻封面" width="160">
        <template #default="scope">
          <el-image
            alt="新闻封面"
            :src="normalizeUrl(scope.row.image?.photoUrl)"
            :preview-src-list="toPreviewList(scope.row.image?.photoUrl)"
            style="width: 100px; height: 100px"
            fit="scale-down"
            preview-teleported
          />
        </template>
      </el-table-column>

      <el-table-column label="操作" width="220">
        <template #default="scope">
          <el-button type="primary" @click="editNews(scope.row.id)">详情/编辑</el-button>
          <el-button type="danger" @click="deleteNews(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-backtop target=".el-scrollbar__wrap" :visibility-height="20" :bottom="75">
      <div
        style="
          display: flex;
          align-items: center;
          justify-content: center;
          height: 100%;
          background-color: #1989fa;
          border-radius: 50%;
          box-shadow: rgb(16 0 0 / 41%) 0px 12px 6px 0px;
          color: #fff;
          width: 100%;
        "
      >
        <el-icon><Top /></el-icon>
      </div>
    </el-backtop>

    <el-dialog :title="formTitle" v-model="showForm" width="720px">
      <el-form v-loading="loading" @submit.prevent="submitForm" label-width="100px">
        <el-form-item label="标题">
          <el-input v-model="form.title" required />
        </el-form-item>

        <el-form-item label="简介">
          <el-input
            v-model="form.descr"
            style="width: 100%"
            :rows="2"
            type="textarea"
            :autosize="{ minRows: 1, maxRows: 2 }"
          />
        </el-form-item>

        <el-form-item label="分类">
          <el-select v-model="form.category" placeholder="选择分类" required style="width: 260px">
            <el-option
              v-for="item in categories"
              :key="item.id"
              :label="item.name"
              :value="item.id"
            />
          </el-select>
        </el-form-item>

        <el-form-item label="内容">
          <el-input
            v-model="form.contect"
            style="width: 100%"
            :rows="3"
            type="textarea"
            :autosize="{ minRows: 2, maxRows: 6 }"
          />
        </el-form-item>

        <el-form-item label="发布时间">
          <el-date-picker
            v-model="form.time"
            type="datetime"
            placeholder="选择发布时间"
            style="width: 260px"
          />
        </el-form-item>

        <el-form-item label="新闻封面">
          <el-upload
            ref="upload"
            drag
            action="#"
            list-type="picture-card"
            :auto-upload="false"
            :file-list="fileList"
            :limit="1"
            :on-exceed="handleExceed"
            :before-remove="beforeRemoveCover"
            :before-upload="beforeUploadCover"
            accept=".jpg,.jpeg,.png,.gif"
            @remove="handleRemove"
            @preview="handlePictureCardPreview"
            @change="handleChange"
          >
            <el-icon><Plus /></el-icon>
            <div style="color: #aaaaaa;">点击上传/拖拽上传</div>
            <template #tip>
              <div v-if="!hasCover" class="el-upload__tip">封面必填，请上传</div>
            </template>
          </el-upload>

          <el-dialog v-model="dialogVisible" :modal="true">
            <img width="100%" :src="dialogImageUrl" alt="Preview Image" />
          </el-dialog>
        </el-form-item>

        <el-form-item label="新闻配图">
          <el-upload
            ref="uploadMultiple"
            drag
            multiple
            action="#"
            list-type="picture-card"
            :auto-upload="false"
            :file-list="fileListMultiple"
            :before-upload="beforeUploadMultiple"
            accept=".jpg,.jpeg,.png,.gif"
            @remove="handleRemoveMultiple"
            @preview="handlePictureCardPreview"
            @change="handleChangeMultiple"
          >
            <el-icon><Plus /></el-icon>
            <div style="color: #aaaaaa;">点击上传/拖拽上传</div>
          </el-upload>
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="submitForm">提交</el-button>
          <el-button @click="showForm = false">取消</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>

<script>
import axios from 'axios';
import dayjs from 'dayjs';
import { ElMessage, genFileId } from 'element-plus';

const ALLOWED_MIME = ['image/jpeg', 'image/png', 'image/gif'];
const ALLOWED_EXTS = ['.jpg', '.jpeg', '.png', '.gif'];

export default {
  name: 'NewsManagement',

  data() {
    return {
      tableData: [],
      categories: [],
      categoryFilters: [
        { text: '测试', value: 1 },
        { text: '获奖新闻', value: 2 },
        { text: '实验室新闻', value: 3 },
      ],

      showForm: false,
      formTitle: '编辑新闻',
      form: {
        id: '',
        title: '',
        descr: '',
        category: '',
        contect: '',
        time: '',
      },

      fileList: [], // 封面：单文件（含 url 回显）
      fileListMultiple: [], // 配图：多文件（含 url 回显）
      dialogImageUrl: '',
      dialogVisible: false,

      loading: false,
      fullscreenLoading: false,
      deletedPhotoUrls: [], // 记录被删的旧配图 URL
    };
  },

  computed: {
    hasCover() {
      const c = this.fileList[0];
      return !!(c && (c.raw || c.url));
    },
  },

  methods: {
    /* ---------- 文件类型校验 ---------- */
    isAllowedImage(file) {
      const type = (file.type || '').toLowerCase();
      const name = (file.name || '').toLowerCase();
      const dot = name.lastIndexOf('.');
      const ext = dot >= 0 ? name.slice(dot) : '';
      if (ALLOWED_MIME.includes(type)) return true;
      if (ALLOWED_EXTS.includes(ext)) return true;
      return false;
    },
    beforeUploadCover(file) {
      if (!this.isAllowedImage(file)) {
        ElMessage.error('仅支持上传 jpg / jpeg / png / gif 图片');
        return false;
      }
      return true;
    },
    beforeUploadMultiple(file) {
      if (!this.isAllowedImage(file)) {
        ElMessage.error('仅支持上传 jpg / jpeg / png / gif 图片');
        return false;
      }
      return true;
    },

    /* ---------- 工具 ---------- */
    normalizeUrl(u) {
      if (!u) return '';
      return String(u).replace(/\\/g, '/');
    },
    toPreviewList(input) {
      const u = this.normalizeUrl(input);
      return u ? [u] : [];
    },
    displayCategory(val) {
      const map = { 1: '测试', 2: '获奖新闻', 3: '实验室新闻' };
      if (map[val]) return map[val];
      const asNum = Number(val);
      if (map[asNum]) return map[asNum];
      return val ?? '';
    },
    // 分类名字 -> id（如果 form.category 已经是数字/数字字符串就原样返回）
    categoryToId(val) {
      if (val === null || val === undefined || val === '') return '';
      if (/^\d+$/.test(String(val))) return Number(val);
      const hit = (this.categories || []).find((c) => c.name === val);
      // 修正：找不到就“原样返回”（有场景后端接收 String）
      return hit ? Number(hit.id) : val;
    },

    /* ---------- 拉数据 ---------- */
    async fetchData() {
      try {
        this.fullscreenLoading = true;
        const { data } = await axios.get(`${this.$baseURL}/news`);
        this.tableData = data?.data ?? [];
      } catch (e) {
        console.error('Error fetching data:', e);
        ElMessage.error('加载失败');
      } finally {
        this.fullscreenLoading = false;
      }
    },
    async fetchCategories() {
      try {
        const { data } = await axios.get(`${this.$baseURL}/news/category/list`);
        this.categories = (data?.data ?? []).map((c) => ({ ...c, id: Number(c.id) }));
      } catch (e) {
        console.error('Error fetching categories:', e);
      }
    },

    /* ---------- 列表筛选 ---------- */
    filterCategory(value, row) {
      const currentId = this.categoryToId(row.category);
      return String(currentId) === String(value);
    },

    /* ---------- 表单 ---------- */
    addNews() {
      this.form = {
        id: '',
        title: '',
        descr: '',
        category: '',
        contect: '',
        time: '',
      };
      this.fileList = [];
      this.fileListMultiple = [];
      this.deletedPhotoUrls = [];
      this.formTitle = '添加新闻';
      this.showForm = true;
    },

    async editNews(newsId) {
      this.loading = true;
      this.showForm = true;
      this.deletedPhotoUrls = [];
      try {
        if (!this.categories.length) {
          await this.fetchCategories();
        }
        const { data } = await axios.get(`${this.$baseURL}/news/${newsId}`);
        const newsData = (data?.data ?? [])[0] ?? {};

        this.form = {
          id: newsData.id ?? '',
          title: newsData.title ?? '',
          descr: newsData.descr ?? '',
          category: this.categoryToId(newsData.category),
          contect: newsData.contect ?? '',
          time: newsData.time ?? '',
        };

        const coverUrl = this.normalizeUrl(newsData?.image?.photoUrl);
        this.fileList = coverUrl ? [{ name: '封面图片', url: coverUrl }] : [];

        if (Array.isArray(newsData.photos)) {
          this.fileListMultiple = newsData.photos
            .map((p) => (typeof p === 'string' ? p : p?.photoUrl))
            .map((u) => this.normalizeUrl(u))
            .filter(Boolean)
            .map((u) => ({ name: u, url: u }));
        } else {
          this.fileListMultiple = [];
        }

        this.formTitle = '编辑新闻';
      } catch (e) {
        console.error('Error fetching news details:', e);
        ElMessage.error('加载失败');
      } finally {
        this.loading = false;
      }
    },

    /* ---------- Upload 事件 ---------- */
    beforeRemoveCover() {
      this.$nextTick(() => {
        if (!this.hasCover) {
          ElMessage.warning('封面不能为空，请重新上传后再提交');
        }
      });
      return true;
    },
    handleRemove(file) {
      const i = this.fileList.indexOf(file);
      if (i !== -1) this.fileList.splice(i, 1);
      if (!this.hasCover) {
        ElMessage.warning('封面不能为空，请重新上传后再提交');
      }
    },
    handleRemoveMultiple(file) {
      if (!file?.raw && file?.url) {
        const u = this.normalizeUrl(file.url);
        if (u && !this.deletedPhotoUrls.includes(u)) {
          this.deletedPhotoUrls.push(u);
        }
      }
      const i = this.fileListMultiple.indexOf(file);
      if (i !== -1) this.fileListMultiple.splice(i, 1);
    },
    handleExceed(files) {
      this.$refs.upload.clearFiles();
      const file = files[0];
      file.uid = genFileId();
      this.$refs.upload.handleStart(file);
    },
    handlePictureCardPreview(file) {
      this.dialogImageUrl = file.url;
      this.dialogVisible = true;
    },
    handleChange(file, fileList) {
      if (file?.raw && !this.isAllowedImage(file.raw)) {
        const i = fileList.findIndex((f) => f.uid === file.uid);
        if (i !== -1) fileList.splice(i, 1);
        ElMessage.error('仅支持上传 jpg / jpeg / png / gif 图片');
      }
      this.fileList = fileList;
    },
    handleChangeMultiple(file, fileList) {
      if (file?.raw && !this.isAllowedImage(file.raw)) {
        const i = fileList.findIndex((f) => f.uid === file.uid);
        if (i !== -1) fileList.splice(i, 1);
        ElMessage.error('仅支持上传 jpg / jpeg / png / gif 图片');
      }
      this.fileListMultiple = fileList;
    },

    /* ---------- 提交 ---------- */
    async submitForm() {
      // 检查日期字段是否填写
      if (!this.form.time) {
        ElMessage.error('请填写发布时间');
        return;
      }
      const isoTime = dayjs(this.form.time).format('YYYY-MM-DDTHH:mm:ss');

      const isAdd = !this.form.id;

      const cover = this.fileList[0];
      if (isAdd) {
        if (!cover || !cover.raw) {
          ElMessage.error('请上传新闻封面（必须选择本地文件）');
          return;
        }
      } else if (!cover || (!cover.raw && !cover.url)) {
        ElMessage.error('封面不能为空，请保留现有封面或重新上传');
        return;
      }

      this.loading = true;
      try {
        const fd = new FormData();

        if (this.form.id !== undefined && this.form.id !== null) {
          fd.append('id', this.form.id);
        }
        fd.append('title', this.form.title ?? '');
        fd.append('descr', this.form.descr ?? '');
        fd.append('category', this.categoryToId(this.form.category));
        fd.append('contect', this.form.contect ?? '');
        fd.append('time', isoTime);

        if (isAdd) {
          if (cover?.raw) fd.append('image', cover.raw);
          for (const f of this.fileListMultiple) {
            if (f?.raw) fd.append('files', f.raw);
          }
        } else {
          if (cover?.raw) {
            fd.append('coverImage', cover.raw);
          } else if (cover?.url) {
            fd.append('coverUrl', this.normalizeUrl(cover.url));
          }
          for (const f of this.fileListMultiple) {
            if (f?.raw) fd.append('files', f.raw);
          }
          if (Array.isArray(this.deletedPhotoUrls)) {
            for (const u of this.deletedPhotoUrls) {
              if (u) fd.append('deleteUrl', this.normalizeUrl(u));
            }
          }
          // imgNews 当前不传；确保后端 imgNews 参数 required=false
        }

        const apiUrl = isAdd ? `${this.$baseURL}/news/add` : `${this.$baseURL}/news/update`;
        await axios.post(apiUrl, fd);
        ElMessage.success(isAdd ? '添加成功' : '更新成功');
        this.showForm = false;
        this.fetchData();
      } catch (err) {
        console.error('Error submitting form:', err?.response?.data || err);
        ElMessage.error('提交失败');
      } finally {
        this.loading = false;
      }
    },

    /* ---------- 删除 ---------- */
    async deleteNews(newsId) {
      try {
        // 弹出确认删除的弹窗
        const confirmed = await this.$confirm('确定删除该新闻信息吗?', '删除确认', {
          confirmButtonText: '确认',
          cancelButtonText: '取消',
          type: 'warning',
        });

        // 如果用户点击了确认按钮，则执行删除操作
        if (confirmed === 'confirm') {
          await axios.delete(`${this.$baseURL}/news/${newsId}`);
          this.fetchData(); // 重新获取新闻数据
        }
      } catch (error) {
        if (error === 'cancel') return;
        console.error('Error deleting news:', error?.response?.data || error);
        ElMessage.error('删除失败');
      }
    },
  },

  mounted() {
    this.fetchData();
    this.fetchCategories();
  },
};
</script>

<style scoped>
.container {
  display: flex;
  flex-direction: column;
  height: 89vh;
  overflow: hidden;
}

.el-table {
  flex: 1;
  width: 100%;
}

.el-upload__tip {
  color: red;
}
</style>
