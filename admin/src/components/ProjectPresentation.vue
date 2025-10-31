<!-- ProjectPresentation.vue -->
<template>
  <div class="container" v-loading.lock="fullscreenLoading">
    <el-button type="primary" @click="addProject">添加项目</el-button>

    <el-table
      :data="tableData"
      :header-cell-style="{ position: 'sticky', top: '0', zIndex: 1 }"
      style="width: 100%; flex: 1;"
      height="calc(85vh - 5px)"
    >
      <el-table-column prop="title" label="标题" />

      <el-table-column label="项目封面" width="160">
        <template #default="scope">
          <el-image
            alt="项目封面"
            :src="normalizeUrl(scope.row?.image?.photoUrl)"
            :preview-src-list="toPreviewList(scope.row?.image?.photoUrl)"
            style="width: 100px; height: 100px"
            fit="scale-down"
            preview-teleported
          />
        </template>
      </el-table-column>

      <el-table-column label="操作" width="220">
        <template #default="scope">
          <el-button type="primary" @click="editProject(scope.row.id)">详情/编辑</el-button>
          <el-button type="danger" @click="deleteProject(scope.row.id)">删除</el-button>
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

        <el-form-item label="内容">
          <el-input
            v-model="form.content"
            style="width: 100%"
            :rows="3"
            type="textarea"
            :autosize="{ minRows: 3, maxRows: 6 }"
          />
        </el-form-item>

        <el-form-item label="项目封面">
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

        <el-form-item label="项目配图">
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
import { ElMessage, genFileId } from 'element-plus';

const ALLOWED_MIME = ['image/jpeg', 'image/png', 'image/gif'];
const ALLOWED_EXTS = ['.jpg', '.jpeg', '.png', '.gif'];

export default {
  name: 'ProjectManagement',

  data() {
    return {
      tableData: [],
      showForm: false,
      formTitle: '编辑项目',
      form: {
        id: '',
        title: '',
        content: '',
      },

      fileList: [],            // 封面（可能是 {raw} 或 {url}）
      fileListMultiple: [],    // 配图（新图含 raw，旧图只有 url）
      deletedPhotoUrls: [],    // 记录被删的旧配图 URL（逐个 append 为 deleteUrl）

      dialogImageUrl: '',
      dialogVisible: false,

      loading: false,
      fullscreenLoading: false,
    };
  },

  computed: {
    hasCover() {
      const c = this.fileList[0];
      return !!(c && (c.raw || c.url));
    },
  },

  methods: {
    /* ------------ 工具：规范 URL ------------ */
    normalizeUrl(input) {
      let u = Array.isArray(input) ? input[0] : input;
      if (!u) return '';
      u = String(u).replace(/\\/g, '/');
      if (/^(https?:)?\/\//i.test(u)) return u;
      const base =
        (import.meta?.env?.VITE_OSS_BASE) ||
        this.$baseURL ||
        (typeof window !== 'undefined' ? window.location.origin : '');
      try {
        return new URL(u, base).href;
      } catch {
        return u;
      }
    },
    toPreviewList(input) {
      const u = this.normalizeUrl(input);
      return u ? [u] : [];
    },

    /* ------------ 类型校验（与新闻页一致） ------------ */
    isAllowedImage(file) {
      const type = (file?.type || '').toLowerCase();
      const name = (file?.name || '').toLowerCase();
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

    /* ------------ 拉取列表 ------------ */
    async fetchData() {
      try {
        this.fullscreenLoading = true;
        const { data } = await axios.get(`${this.$baseURL}/project`);
        this.tableData = data?.data || [];
      } catch (e) {
        console.error('Error fetching data:', e);
        ElMessage.error('加载失败');
      } finally {
        this.fullscreenLoading = false;
      }
    },

    /* ------------ 表单操作 ------------ */
    addProject() {
      this.form = { id: '', title: '', content: '' };
      this.fileList = [];
      this.fileListMultiple = [];
      this.deletedPhotoUrls = [];
      this.formTitle = '添加项目';
      this.showForm = true;
    },

    async editProject(projectId) {
      this.loading = true;
      this.showForm = true;
      this.deletedPhotoUrls = [];
      try {
        const { data } = await axios.get(`${this.$baseURL}/project/${projectId}`);
        const projectData = (data?.data || [])[0] || {};

        this.form = {
          id: projectData.id || '',
          title: projectData.title || '',
          content: projectData.content || '',
        };

        // 封面（旧图仅 url）
        const coverUrl = this.normalizeUrl(projectData?.image?.photoUrl);
        this.fileList = coverUrl ? [{ name: '封面图片', url: coverUrl }] : [];

        // 配图（旧图仅 url；编辑时不自动带上 imgProjects）
        if (Array.isArray(projectData.photos)) {
          this.fileListMultiple = projectData.photos
            .map(p => (typeof p === 'string' ? p : p?.photoUrl))
            .map(u => this.normalizeUrl(u))
            .filter(Boolean)
            .map(u => ({ name: u, url: u }));
        } else {
          this.fileListMultiple = [];
        }

        this.formTitle = '编辑项目';
      } catch (e) {
        console.error('Error fetching project details:', e);
        ElMessage.error('加载失败');
      } finally {
        this.loading = false;
      }
    },

    /* ------------ Upload 事件（含删除 URL 记录） ------------ */
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
      // 仅当是旧图（无 raw，有 url）时，把其 url 记入删除清单
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
      this.dialogImageUrl = this.normalizeUrl(file.url || '');
      this.dialogVisible = true;
    },
    handleChange(file, fileList) {
      if (file?.raw && !this.isAllowedImage(file.raw)) {
        const i = fileList.findIndex(f => f.uid === file.uid);
        if (i !== -1) fileList.splice(i, 1);
        ElMessage.error('仅支持上传 jpg / jpeg / png / gif 图片');
      }
      this.fileList = fileList;
    },
    handleChangeMultiple(file, fileList) {
      if (file?.raw && !this.isAllowedImage(file.raw)) {
        const i = fileList.findIndex(f => f.uid === file.uid);
        if (i !== -1) fileList.splice(i, 1);
        ElMessage.error('仅支持上传 jpg / jpeg / png / gif 图片');
      }
      this.fileListMultiple = fileList;
    },

    /* ------------ 提交（对齐后端 update 入参） ------------ */
    async submitForm() {
      // 新增必须上传本地封面；更新需保留旧封面或重新上传
      const isAdd = !this.form.id;
      const cover = this.fileList[0];

      if (isAdd) {
        if (!cover || !cover.raw) {
          ElMessage.error('请上传项目封面（必须选择本地文件）');
          return;
        }
      } else {
        if (!cover || (!cover.raw && !cover.url)) {
          ElMessage.error('封面不能为空，请保留现有封面或重新上传');
          return;
        }
      }

      this.loading = true;
      try {
        const fd = new FormData();

        // 通用字段
        if (this.form.id !== undefined && this.form.id !== null) {
          fd.append('id', this.form.id);
        }
        fd.append('title', this.form.title ?? '');
        fd.append('content', this.form.content ?? '');

        if (isAdd) {
          // ===== /project/add (假设与新闻页一致：封面字段名 image；多图 files) =====
          if (cover?.raw) fd.append('image', cover.raw);
          for (const f of this.fileListMultiple) {
            if (f?.raw) fd.append('files', f.raw);
          }
        } else {
          // ===== /project/update (按你提供的签名) =====
          // 封面：新传 coverImage；否则传 coverUrl
          if (cover?.raw) {
            fd.append('coverImage', cover.raw);
          } else if (cover?.url) {
            fd.append('coverUrl', this.normalizeUrl(cover.url));
          }

          // 只把“新增配图（有 raw）”作为 files 追加
          for (const f of this.fileListMultiple) {
            if (f?.raw) fd.append('files', f.raw);
          }

          // 把“删除的旧配图 URL”逐个 append 为 deleteUrl
          if (Array.isArray(this.deletedPhotoUrls)) {
            for (const u of this.deletedPhotoUrls) {
              if (u) fd.append('deleteUrl', this.normalizeUrl(u));
            }
          }

          // 与新闻页一致：imgProjects 暂不传（确保后端 required=false）
          // fd.append('imgProjects', ...) // 不传
        }

        const apiUrl = isAdd
          ? `${this.$baseURL}/project/add`
          : `${this.$baseURL}/project/update`;

        await axios.post(apiUrl, fd); // 让浏览器自动设置 multipart 边界
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

    /* ------------ 删除 ------------ */
    async deleteProject(projectId) {
      try {
        const confirmed = await this.$confirm('确定删除该项目信息吗?', '删除确认', {
          confirmButtonText: '确认',
          cancelButtonText: '取消',
          type: 'warning',
        });
        if (confirmed === 'confirm') {
          await axios.delete(`${this.$baseURL}/project/${projectId}`);
          this.fetchData();
        }
      } catch (error) {
        if (error === 'cancel') return;
        console.error('Error deleting project:', error?.response?.data || error);
        ElMessage.error('删除失败');
      }
    },
  },

  mounted() {
    this.fetchData();
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
