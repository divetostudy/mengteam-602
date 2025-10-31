<!-- TeacherManagement.vue -->
<template>
  <div class="container">
    <el-button type="primary" @click="addTeacher">添加老师</el-button>
    <div class="scroll-target" style="flex: 1; overflow: auto;">
      <el-table  :data="filteredTeachers" style="width: 100%">
        <el-table-column prop="photo" label="照片" width="180">
          <template #default="scope">
            <div class="image-wrapper">
              <img :src="'data:image/png;base64,' + scope.row.photo" alt="">
            </div>
          </template>
        </el-table-column>
        <el-table-column prop="name" label="名字" width="180"></el-table-column>
        <el-table-column label="操作">
          <template #default="scope">
            <el-button @click="editTeacher(scope.row)" type="text" size="small">编辑</el-button>
            <el-button @click="deleteTeacher(scope.row.id)" type="text" size="small">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-backtop target=".scroll-target" :right="100" :bottom="10" />
    </div>
    
    <el-dialog :title="formTitle" v-model="showForm">
      <el-form @submit.prevent="submitForm">
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
            :file-list="fileList" 
            :before-upload="handleImageUpload"
            list-type="picture">
            <el-button type="primary">选择文件</el-button>
          </el-upload>
          <div v-if="form.imageUrl" class="image-preview">
            <img :src="form.imageUrl" alt="预览图片">
          </div>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitForm">提交</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>

  </div>
</template>

<script>
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
      fileList: [],
      selectedYear: '',
      years: [],
      filteredTeachers: []
    };
  },
  methods: {
    // 处理图片上传
    handleImageUpload(file) {
      console.log('handleImageUpload called with file:', file);
      this.form.image = file; // 将上传的文件存储到 form 对象的 image 属性中
      const reader = new FileReader(); // 创建一个 FileReader 对象，用于读取文件
      reader.onload = (e) => { // 当文件读取完成后会触发 onload 事件
        this.form.imageUrl = e.target.result; // 将读取到的文件内容（base64 编码的图片）存储到 form 对象的 imageUrl 属性中
        console.log('Image URL set to:', this.form.imageUrl); // 添加日志，确认 URL 设置成功
      };
      reader.onerror = (error) => { // 增加异常处理
        console.error("Error reading file:", error);
      };
      reader.readAsDataURL(file); // 读取文件内容，将其转换为 base64 编码的 URL
      console.log('Image selected:', file); // 添加日志，确认文件被选择
      return false; // 阻止自动上传，因为我们想在表单提交时再统一上传
    },
    // 提交表单
    submitForm() {
      console.log('submitForm called with image:', this.form.image);
      
      const formData = new FormData();
      formData.append('id', this.form.id);
      formData.append('name', this.form.name);
      formData.append('job', this.form.job);
      formData.append('introduction', this.form.introduction);
      formData.append('honor', this.form.honor);
      formData.append('direction', this.form.direction);
      if (this.form.image) {
        formData.append('file', this.form.image); // 确保这与后端的@RequestParam（“file”）匹配
      } else {
        formData.append('file', null); // 如果没有选择新图片，则将 file 设置为 null
      }

      console.log('FormData to be sent:', formData); // 确保发送的内容正确
      const request = this.form.id
        ? this.$axios.post(`${this.$baseURL}/admin/updateTeacher`, formData, {
            headers: {
              'Content-Type': 'multipart/form-data'
            }
          })
        : this.$axios.post(`${this.$baseURL}/admin/uploadTeacher`, formData, {
            headers: {
              'Content-Type': 'multipart/form-data'
            }
          });

      request.then(response => {
        console.log('Form submitted successfully', response);
        this.fetchTeachers();
        this.showForm = false;
      })
      .catch(error => {
        console.error('Error submitting form:', error);
      });
    },
    // 获取教师数据
    fetchTeachers() {
      this.$axios.get(`${this.$baseURL}/teacher`)
        .then(res => {
          this.teachers = res.data.data;
          this.extractYears();
          this.filterTeachers();
        })
        .catch(err => {
          console.error('Error fetching teachers:', err);
        });
    },
    // 提取年份
    extractYears() {
      const years = new Set(this.teachers.map(teacher => teacher.graduationYear));
      this.years = Array.from(years);
    },
    // 添加教师
    addTeacher() {
      this.resetForm();
      this.formTitle = '添加老师';
      this.showForm = true;
    },
    // 编辑教师
    editTeacher(teacher) {
      console.log('Editing teacher:', teacher);
      this.formTitle = '编辑老师';
      this.form = { ...teacher, image: null, imageUrl: 'data:image/png;base64,' + teacher.photo };
      this.showForm = true;
    },
    // 删除教师
    deleteTeacher(id) {
      this.$axios.delete(`${this.$baseURL}/admin/deleteTeacher/${id}`)
        .then(() => {
          this.fetchTeachers();
        })
        .catch(err => {
          console.error('Error deleting teacher:', err);
        });
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
    },
    // 筛选教师
    filterTeachers() {
      this.filteredTeachers = this.selectedYear ? this.teachers.filter(teacher => teacher.graduationYear === this.selectedYear) : this.teachers;
    }
  },
  mounted() {
    this.fetchTeachers();
  }
};
</script>

<style scoped>
.container{
display: flex;
flex-direction: column;
height:calc(89vh - 60px);
overflow: hidden;
}

.scroll-target {
flex: 1;
overflow: auto;
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
