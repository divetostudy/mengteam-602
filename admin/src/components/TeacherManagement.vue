<!-- TeacherManagement.vue -->
<template>
  <div class="container" v-loading.lock="fullscreenLoading">
    <el-button type="primary" @click="addTeacher">添加老师信息</el-button>

    <el-table
      :data="filteredTeachers"
      :header-cell-style="{ position: 'sticky', top: '0', zIndex: 1 }"
      style="width: 100%; flex: 1;"
      height="calc(80vh - 5px)"
    >
      <el-table-column prop="photo" label="照片" width="180">
        <template #default="scope">
          <div class="image-wrapper">
            <el-image
              alt="老师照片"
              :src="normalizeUrl(scope.row.photoUrl)"
              :preview-src-list="toPreviewList(scope.row.photoUrl)"
              style="width: 100px; height: 100px"
              fit="scale-down"
              preview-teleported
            />
          </div>
        </template>
      </el-table-column>

      <el-table-column prop="name" label="名字"></el-table-column>

      <el-table-column label="操作">
        <template #header>
          <el-input v-model="search" placeholder="输入姓名搜索" />
        </template>
        <template #default="scope">
          <el-button @click="editTeacher(scope.row)" type="primary">编辑</el-button>
          <el-button @click="deleteTeacher(scope.row.id)" type="danger">删除</el-button>
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

    <el-dialog :title="formTitle" v-model="showForm">
      <el-form v-loading="loading" @submit.prevent="submitForm">
        <el-form-item label="名字">
          <el-input v-model="form.name" required></el-input>
        </el-form-item>
        <el-form-item label="职位">
          <el-input v-model="form.job" required></el-input>
        </el-form-item>
        <el-form-item label="介绍">
          <el-input v-model="form.introduction" required></el-input>
        </el-form-item>
        <el-form-item label="荣誉">
          <el-input type="textarea" v-model="form.honor" autosize></el-input>
        </el-form-item>
        <el-form-item label="方向">
          <el-input type="textarea" v-model="form.direction" autosize></el-input>
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
      teachers: [],
      showForm: false,
      formTitle: '',
      form: {
        id: null,
        name: '',
        job: '',
        introduction: '',
        honor: '',
        direction: '',
        photo: '',
        image: null,
        imageUrl: ''
      },
      dialogImageUrl: '',
      dialogVisible: false,
      fileList: [],
      selectedYear: '',
      years: [],
      search: '',
      loading: false,
      fullscreenLoading: false,
    };
  },
  computed: {
    // 姓名搜索过滤（含拼音首字母）
    filteredTeachers() {
      const searchLower = this.search.toLowerCase();
      return this.teachers.filter((teacher) =>
        (teacher.name || '').toLowerCase().includes(searchLower) ||
        this.getFirstLetter(teacher.name || '').includes(searchLower)
      );
    }
  },
  methods: {
    /* ---------------- 工具方法：统一规范图片地址 ---------------- */
    // 传入任意类型（字符串 / 数组 / 空），产出「字符串」URL；并把反斜杠替换为正斜杠
    normalizeUrl(input) {
      let u = Array.isArray(input) ? input[0] : input;
      if (!u) return '';
      u = String(u).replace(/\\/g, '/'); // \ -> /
      return u;
    },
    // 传入任意类型（字符串 / 数组 / 空），产出「字符串数组」用于 preview-src-list
    toPreviewList(input) {
      const u = this.normalizeUrl(input);
      return u ? [u] : [];
    },

    // 获取老师数据
    async fetchTeachers() {
      try {
        this.fullscreenLoading = true;
        const response = await axios.get(`${this.$baseURL}/teacher`);
        // 原样接收，渲染阶段用 normalizeUrl 保证安全
        this.teachers = response.data.data || [];
      } catch (error) {
        console.error('Error fetching teachers:', error);
        ElMessage.error('加载失败');
      } finally {
        this.fullscreenLoading = false;
      }
    },

    // 处理图片上传
    handleRemove(file) {
      const index = this.fileList.indexOf(file);
      if (index !== -1) {
        this.fileList.splice(index, 1);
      }
    },

    // 获取名字的拼音首字母
    getFirstLetter(name) {
      return pinyin(name, {
        style: pinyin.STYLE_FIRST_LETTER
      }).flat().join('');
    },

    // 添加老师
    addTeacher() {
      this.resetForm();
      this.formTitle = '添加老师';
      this.showForm = true;
    },

    handleExceed(files) {
      this.$refs.upload.clearFiles();
      const file = files[0];
      file.uid = genFileId();
      this.$refs.upload.handleStart(file);
    },

    handlePictureCardPreview(file) {
      const rawUrl = file.url || this.form.imageUrl;
      this.dialogImageUrl = this.normalizeUrl(rawUrl);
      this.dialogVisible = true;
    },

    handleChange(file, fileList) {
      this.fileList = fileList;
    },

    // 提交表单
    async submitForm() {
      if (this.fileList.length === 0) {
        ElMessage.error('请上传照片');
        return;
      }

      this.loading = true;

      try {
        const formData = new FormData();

        // 添加普通字段
        formData.append('id', this.form.id);
        formData.append('name', this.form.name);
        formData.append('job', this.form.job);
        formData.append('introduction', this.form.introduction);
        formData.append('honor', this.form.honor);
        formData.append('direction', this.form.direction);

        // 添加照片（兼容 base64 与原始文件）
        if (this.fileList.length > 0) {
          const first = this.fileList[0];
          const url = first.url || '';
          if (url.startsWith('data:')) {
            const base64Response = await fetch(url);
            const blob = await base64Response.blob();
            formData.append('file', blob, 'photo.png');
          } else {
            formData.append('file', first.raw || first);
          }
        }

        const apiUrl = this.form.id
          ? `${this.$baseURL}/admin/updateTeacher`
          : `${this.$baseURL}/admin/uploadTeacher`;

        await axios.post(apiUrl, formData, {
          headers: { 'Content-Type': 'multipart/form-data' }
        });

        this.showForm = false;
        this.fetchTeachers();
      } catch (error) {
        console.error('Error submitting form:', error.response ? error.response.data : error);
        ElMessage.error('提交失败');
      } finally {
        this.loading = false;
      }
    },

    // 编辑老师
    editTeacher(teacher) {
      this.loading = true;
      this.showForm = true;
      try {
        const fixedUrl = this.normalizeUrl(teacher.photoUrl);
        this.formTitle = '编辑老师';
        this.form = {
          ...teacher,
          image: null,
          imageUrl: fixedUrl
        };
        this.fileList = [
          {
            name: '照片',
            url: fixedUrl
          }
        ];
      } catch (error) {
        console.error('Error setting teacher details:', error);
        ElMessage.error('加载失败');
      } finally {
        this.loading = false;
      }
    },

    // 删除老师
    async deleteTeacher(id) {
      try {
        // 弹出确认删除的弹窗
        const confirmed = await this.$confirm('确定删除该老师信息吗?', '删除确认', {
          cancelButtonText: '取消',
          confirmButtonText: '确认',
          type: 'warning'
        });
      
        // 如果用户点击了确认按钮，则执行删除操作
        if (confirmed === 'confirm') {
          console.log(`Deleting teacher with ID: ${id}`);
          await axios.delete(`${this.$baseURL}/admin/deleteTeacher/${id}`);
          this.fetchTeachers();
        }
      } catch (error) {
        // 如果用户点击了取消按钮或者出现错误，捕获异常
        if (error === 'cancel') {
          console.log('删除操作已取消');
        } else {
          console.error('Error deleting teacher:', error.response ? error.response.data : error);
        }
      }
    },

    // 重置表单
    resetForm() {
      this.form = {
        id: null,
        name: '',
        job: '',
        introduction: '',
        honor: '',
        direction: '',
        photo: '',
        image: null,
        imageUrl: ''
      };
      this.fileList = [];
    }
  },
  mounted() {
    this.fetchTeachers();
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
  height: 100px;
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
