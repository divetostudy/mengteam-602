<!-- PrizeDisplay.vue -->
<template>
  <div class="container" v-loading.lock="fullscreenLoading">
    <el-button type="primary" @click="addPrize">添加奖项</el-button>
    <el-table
      :data="filteredPrizes"
      :header-cell-style="{ position: 'sticky', top: '0', zIndex: 1 }"
      style="width: 100%; flex: 1;"
      height="calc(80vh - 5px)"
    >
      <el-table-column prop="photo" label="照片" width="180">
        <template #default="scope">
          <div class="image-wrapper">
            <el-image
              alt="奖项照片"
              :src="normalizeUrl(scope.row.url)"
              :preview-src-list="toPreviewList(scope.row.url)"
              style="width: 100px; height: 100px"
              fit="scale-down"
              preview-teleported
            />
          </div>
        </template>
      </el-table-column>

      <el-table-column prop="content" label="奖项" />

      <el-table-column label="操作">
        <template #header>
          <el-input v-model="search" placeholder="输入搜索" />
        </template>
        <template #default="scope">
          <el-button @click="editPrize(scope.row)" type="primary">编辑</el-button>
          <el-button @click="deletePrize(scope.row.id)" type="danger">删除</el-button>
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
      ><el-icon><Top /></el-icon></div>
    </el-backtop>

    <el-dialog :title="formTitle" v-model="showForm">
      <el-form v-loading="loading" @submit.prevent="submitForm">
        <el-form-item label="荣誉">
          <el-input type="textarea" v-model="form.content" autosize />
        </el-form-item>

        <el-form-item label="照片">
          <el-upload
            drag
            ref="upload"
            action="#"
            list-type="picture-card"
            :auto-upload="false"
            :file-list="fileList"
            :limit="1"
            :on-exceed="handleExceed"
            @remove="handleRemove"
            @preview="handlePictureCardPreview"
            @change="handleChange"
          >
            <el-icon><Plus /></el-icon>
            <div style="color:#AAAAAA;">点击上传/拖拽上传</div>
          </el-upload>

          <el-dialog v-model="dialogVisible">
            <img width="100%" :src="dialogImageUrl" alt="Preview Image" />
          </el-dialog>
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="submitForm">提交</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>

<script>
import axios from 'axios';
import { ElMessage } from 'element-plus';
import { genFileId } from 'element-plus';
import pinyin from 'pinyin';

export default {
  data() {
    return {
      prizes: [],
      showForm: false,
      formTitle: '',
      form: {
        id: null,
        content: '',
        imageUrl: '', // 仅前端回显/回传旧图
        img: { id: null, url: '', base64: '' } // 可留作回显，不参与提交
      },
      dialogImageUrl: '',
      dialogVisible: false,
      fileList: [],
      search: '',
      loading: false,
      fullscreenLoading: false
    };
  },
  computed: {
    filteredPrizes() {
      const searchLower = (this.search || '').toLowerCase();
      return this.prizes.filter((prize) =>
        (prize.content || '').toLowerCase().includes(searchLower) ||
        this.getFirstLetter(prize.content || '').includes(searchLower)
      );
    }
  },
  methods: {
    /* ---------- 工具 ---------- */
    normalizeUrl(input) {
      const u = Array.isArray(input) ? input[0] : input;
      return u ? String(u).replace(/\\/g, '/') : '';
    },
    toPreviewList(input) {
      const u = this.normalizeUrl(input);
      return u ? [u] : [];
    },
    debugFormData(fd) {
      console.groupCollapsed('[Prize] FormData entries');
      for (const [k, v] of fd.entries()) {
        if (v instanceof Blob) {
          console.log(k, `(Blob type=${v.type || 'unknown'}, size=${v.size} bytes)`);
        } else {
          console.log(k, v);
        }
      }
      console.groupEnd();
    },

    /* ---------- 数据 ---------- */
    async fetchPrizes() {
      try {
        this.fullscreenLoading = true;
        const res = await axios.get(`${this.$baseURL}/prize`);
        this.prizes = res.data.data || [];
      } catch (e) {
        console.error('Error fetching prizes:', e);
        ElMessage.error('加载失败');
      } finally {
        this.fullscreenLoading = false;
      }
    },

    /* ---------- 上传 ---------- */
    handleRemove(file) {
      const i = this.fileList.indexOf(file);
      if (i !== -1) this.fileList.splice(i, 1);
    },
    handleExceed(files) {
      this.$refs.upload.clearFiles();
      const file = files[0];
      file.uid = genFileId();
      this.$refs.upload.handleStart(file);
    },
    handlePictureCardPreview(file) {
      this.dialogImageUrl = this.normalizeUrl(file.url || this.form.imageUrl);
      this.dialogVisible = true;
    },
    handleChange(file, fileList) {
      this.fileList = fileList;
    },

    /* ---------- 提交（仅传 id/content + 二选一：file 或 url） ---------- */
    async submitForm() {
      // 允许不改图：这时必须有旧图 url 可回传
      if (this.fileList.length === 0 && !this.form.imageUrl && !(this.form.img && this.form.img.url)) {
        ElMessage.error('请上传照片或保留原照片');
        return;
      }

      this.loading = true;
      try {
        const fd = new FormData();
        fd.append('id', this.form.id ?? '');
        fd.append('content', this.form.content ?? '');

        const first = this.fileList[0];
        let used = 'none';

        if (first) {
          const u = first.url || '';
          if (first.raw) {
            // 新上传的二进制文件
            fd.append('file', first.raw, first.raw.name || 'photo.png');
            used = 'file(raw)';
          } else if (u.startsWith('data:')) {
            // dataURL（来自裁剪器/粘贴等）——转 Blob 当作 file 传
            const b = await fetch(u).then(r => r.blob());
            fd.append('file', b, 'photo.png');
            used = 'file(dataURL->blob)';
          } else if (/^https?:\/\//i.test(u)) {
            // 选择了已有 URL 的占位项：用 url 方式
            fd.append('url', this.normalizeUrl(u));
            used = 'url(from-fileList.url)';
          }
        }

        if (used === 'none') {
          // 没有 fileList，走旧图 url（表单回显）
          const keepUrl = this.normalizeUrl(this.form.imageUrl || this.form?.img?.url || '');
          if (keepUrl) {
            fd.append('url', keepUrl);
            used = 'url(from-form.imageUrl/img.url)';
          }
        }

        if (used === 'none') {
          ElMessage.error('图片无效，请重新选择');
          this.loading = false;
          return;
        }

        console.groupCollapsed('[Prize] 即将提交 /prize ' + (this.form.id ? 'update' : 'add'));
        console.log('id:', this.form.id);
        console.log('content:', this.form.content);
        console.log('fileList:', this.fileList);
        console.log('image source used:', used);
        console.log('form.imageUrl:', this.form.imageUrl);
        console.log('form.img (仅前端回显，不提交):', this.form.img);
        this.debugFormData(fd);
        console.groupEnd();

        const apiUrl = this.form.id
          ? `${this.$baseURL}/prize/update`
          : `${this.$baseURL}/prize/add`;

        const resp = await axios.post(apiUrl, fd, {
          headers: { 'Content-Type': 'multipart/form-data' }
        });

        console.groupCollapsed('[Prize] 提交成功响应');
        console.log('status:', resp?.status);
        console.log('data:', resp?.data);
        console.groupEnd();

        this.showForm = false;
        this.fetchPrizes();
      } catch (error) {
        console.groupCollapsed('[Prize] 提交失败');
        console.log('error:', error);
        console.log('error.response?.status:', error?.response?.status);
        console.log('error.response?.data:', error?.response?.data);
        console.groupEnd();
        ElMessage.error('提交失败');
      } finally {
        this.loading = false;
      }
    },

    /* ---------- 其他操作 ---------- */
    editPrize(prize) {
      this.loading = true;
      this.showForm = true;
      try {
        const fixedUrl = this.normalizeUrl(prize.url);
        this.formTitle = '编辑奖项成就';
        this.form = {
          id: prize.id ?? null,
          content: prize.content ?? '',
          imageUrl: fixedUrl,
          img: {
            id: prize?.img?.id ?? null,
            url: fixedUrl,
            base64: ''
          }
        };
        this.fileList = [{
          uid: String(prize.id || fixedUrl),
          name: '照片',
          url: fixedUrl,
          status: 'success',
          existing: true
        }];
      } finally {
        this.loading = false;
      }
    },

    async deletePrize(id) {
      try {
        const confirmed = await this.$confirm('确定删除该奖项成就信息吗?', '删除确认', {
          confirmButtonText: '确认',
          cancelButtonText: '取消',
          type: 'warning'
        });
        if (confirmed === 'confirm') {
          await axios.delete(`${this.$baseURL}/prize/${id}`);
          this.fetchPrizes();
        }
      } catch (error) {
        if (error === 'cancel') {
          console.log('删除操作已取消');
        } else {
          console.error('Error deleting prize:', error.response ? error.response.data : error);
        }
      }
    },

    resetForm() {
      this.form = {
        id: null,
        content: '',
        imageUrl: '',
        img: { id: null, url: '', base64: '' }
      };
      this.fileList = [];
    },

    getFirstLetter(content) {
      return pinyin(content, { style: pinyin.STYLE_FIRST_LETTER }).flat().join('');
    },

    addPrize() {
      this.resetForm();
      this.formTitle = '添加奖项成就';
      this.showForm = true;
    }
  },
  mounted() {
    this.fetchPrizes();
  }
};
</script>

<style scoped>
.container {
  display: flex;
  flex-direction: column;
  height: 89vh;
  overflow: hidden;
}
.image-wrapper {
  width: 100px;
  height: 100px;
  display: flex;
  justify-content: center;
  align-items: center;
  overflow: hidden;
  border: 1px solid #ddd;
  border-radius: 5px;
}
.image-wrapper img {
  max-width: 100%;
  max-height: 100%;
}
.image-preview {
  margin-top: 10px;
  width: 100px;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  overflow: hidden;
  border: 1px solid #ddd;
  border-radius: 5px;
}
.image-preview img {
  max-width: 100%;
  max-height: 100%;
}
.el-input[type="textarea"] {
  min-height: 100px;
  max-height: 300px;
  overflow: auto;
}
</style>
