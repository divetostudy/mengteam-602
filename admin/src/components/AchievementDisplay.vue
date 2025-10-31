<!-- AchievementDisplay.vue -->
<template>
  <div class="container" v-loading.lock="fullscreenLoading">
    <el-button type="primary" @click="addAchievement">添加成果</el-button>

    <el-table
      :data="tableData"
      :header-cell-style="{ position: 'sticky', top: '0', zIndex: 1 }"
      style="width: 100%; flex: 1;"
      height="calc(85vh - 5px)"
    >
      <el-table-column prop="title" label="标题" />

      <el-table-column label="封面" width="160">
        <template #default="scope">
          <el-image
            alt="封面"
            :src="normalizeUrl(scope.row?.photos?.[0]?.photoUrl)"
            :preview-src-list="toPreviewListArray(scope.row?.photos)"
            style="width: 100px; height: 100px"
            fit="scale-down"
            preview-teleported
          />
        </template>
      </el-table-column>

      <el-table-column label="操作" width="220">
        <template #default="scope">
          <el-button type="primary" @click="editAchievement(scope.row.id)">详情/编辑</el-button>
          <el-button type="danger" @click="deleteAchievement(scope.row.id)">删除</el-button>
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

        <el-form-item label="成果配图">
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
import { ElMessage } from 'element-plus';

const ALLOWED_MIME = ['image/jpeg', 'image/png', 'image/gif'];
const ALLOWED_EXTS = ['.jpg', '.jpeg', '.png', '.gif'];

export default {
  name: 'AchievementManagement',

  data() {
    return {
      tableData: [],
      showForm: false,
      formTitle: '编辑成果',
      form: {
        id: '',
        title: '',
        content: '',
      },
      fileListMultiple: [],   // 旧图：只有 url；新图：有 raw
      deletedPhotoUrls: [],   // 被删除的旧配图 URL（逐个 append 为 deleteUrl）

      dialogImageUrl: '',
      dialogVisible: false,
      loading: false,
      fullscreenLoading: false,
    };
  },

  methods: {
    /* ---------- 工具：URL 规范化与预览列表 ---------- */
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
    toPreviewListArray(photos) {
      if (!Array.isArray(photos)) return [];
      return photos
        .map(p => (typeof p === 'string' ? p : p?.photoUrl))
        .map(u => this.normalizeUrl(u))
        .filter(Boolean);
    },

    /* ---------- 图片类型校验（与项目/新闻一致） ---------- */
    isAllowedImage(file) {
      const type = (file?.type || '').toLowerCase();
      const name = (file?.name || '').toLowerCase();
      const dot = name.lastIndexOf('.');
      const ext = dot >= 0 ? name.slice(dot) : '';
      if (ALLOWED_MIME.includes(type)) return true;
      if (ALLOWED_EXTS.includes(ext)) return true;
      return false;
    },
    beforeUploadMultiple(file) {
      if (!this.isAllowedImage(file)) {
        ElMessage.error('仅支持上传 jpg / jpeg / png / gif 图片');
        return false;
      }
      return true;
    },

    /* ---------- 列表 ---------- */
    async fetchData() {
      try {
        this.fullscreenLoading = true;
        const { data } = await axios.get(`${this.$baseURL}/achievement`);
        this.tableData = data?.data || [];
      } catch (e) {
        console.error('Error fetching data:', e);
        ElMessage.error('加载失败');
      } finally {
        this.fullscreenLoading = false;
      }
    },

    /* ---------- 表单 ---------- */
    async addAchievement() {
      this.form = { id: '', title: '', content: '' };
      this.fileListMultiple = [];
      this.deletedPhotoUrls = [];
      this.formTitle = '添加成果';
      this.showForm = true;
    },

    async editAchievement(achievementId) {
      this.loading = true;
      this.showForm = true;
      this.deletedPhotoUrls = [];
      try {
        // 直接用 tableData 中已加载的数据
        const achievement = this.tableData.find(i => i.id === achievementId) || {};
        this.form = {
          id: achievement.id || '',
          title: achievement.title || '',
          content: achievement.content || '',
        };
        if (Array.isArray(achievement.photos)) {
          this.fileListMultiple = achievement.photos
            .map(p => (typeof p === 'string' ? p : p?.photoUrl))
            .map(u => this.normalizeUrl(u))
            .filter(Boolean)
            .map(u => ({ name: u, url: u }));
        } else {
          this.fileListMultiple = [];
        }
        this.formTitle = '编辑成果';
      } catch (e) {
        console.error('Error fetching achievement details:', e);
        ElMessage.error('加载失败');
      } finally {
        this.loading = false;
      }
    },

    /* ---------- Upload 事件（记录旧图删除） ---------- */
    handleRemoveMultiple(file) {
      // 旧图：只有 url、没有 raw；把 url 记入删除清单
      if (!file?.raw && file?.url) {
        const u = this.normalizeUrl(file.url);
        if (u && !this.deletedPhotoUrls.includes(u)) {
          this.deletedPhotoUrls.push(u);
        }
      }
      const index = this.fileListMultiple.indexOf(file);
      if (index !== -1) this.fileListMultiple.splice(index, 1);
    },
    handlePictureCardPreview(file) {
      this.dialogImageUrl = this.normalizeUrl(file.url);
      this.dialogVisible = true;
    },
    handleChangeMultiple(file, fileList) {
      if (file?.raw && !this.isAllowedImage(file.raw)) {
        const i = fileList.findIndex(f => f.uid === file.uid);
        if (i !== -1) fileList.splice(i, 1);
        ElMessage.error('仅支持上传 jpg / jpeg / png / gif 图片');
      }
      this.fileListMultiple = fileList;
    },

    /* ---------- 提交（对齐后端 update 入参） ---------- */
    async submitForm() {
      this.loading = true;
      try {
        const fd = new FormData();

        // 通用字段
        if (this.form.id !== undefined && this.form.id !== null && this.form.id !== '') {
          fd.append('id', this.form.id);
        }
        fd.append('title', this.form.title ?? '');
        fd.append('content', this.form.content ?? '');

        // 新增配图：只上传有 raw 的新文件（字段名 files）
        for (const f of this.fileListMultiple) {
          if (f?.raw) fd.append('files', f.raw);
        }

        // 删除旧配图：逐个追加 deleteUrl
        if (Array.isArray(this.deletedPhotoUrls)) {
          for (const u of this.deletedPhotoUrls) {
            if (u) fd.append('deleteUrl', this.normalizeUrl(u));
          }
        }

        // 不传 imgAchievement（后端 required=false）
        // fd.append('imgAchievement', ...);

        const apiUrl = this.form.id
          ? `${this.$baseURL}/achievement/update`
          : `${this.$baseURL}/achievement/add`;

        await axios.post(apiUrl, fd);
        ElMessage.success(this.form.id ? '更新成功' : '添加成功');

        this.showForm = false;
        this.fetchData();
      } catch (error) {
        console.error('Error submitting form:', error?.response?.data || error);
        ElMessage.error('提交失败');
      } finally {
        this.loading = false;
      }
    },

    /* ---------- 删除成果 ---------- */
    async deleteAchievement(achievementId) {
      try {
        const confirmed = await this.$confirm('确定删除该成果信息吗?', '删除确认', {
          confirmButtonText: '确认',
          cancelButtonText: '取消',
          type: 'warning',
        });
        if (confirmed === 'confirm') {
          await axios.delete(`${this.$baseURL}/achievement/${achievementId}`);
          this.fetchData();
        }
      } catch (error) {
        if (error === 'cancel') return;
        console.error('Error deleting achievement:', error?.response?.data || error);
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
