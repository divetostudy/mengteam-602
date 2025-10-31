<!-- StudentManagementUncheck.vue -->
<template>
  <div class="container" v-loading.lock="fullscreenLoading">
    <el-button type="primary" @click="addStudent" v-if="role === 'user'">添加学生信息</el-button>
    <el-table
    :data="filteredStudents"
    :header-cell-style="{ position: 'sticky', top: '0', zIndex: 1 }" 
    style="width: 100%; flex: 1;"  
    height="calc(85vh - 40px)" 
    >
      <el-table-column prop="photo" label="照片" width="180">
        <template #default="scope">
          <div class="image-wrapper">
            <el-image 
            alt="学生照片" 
            :src="[scope.row.photoUrl]" 
            style="width: 100px; height: 100px"
            :preview-src-list="[scope.row.photoUrl]"
            fit="scale-down"
            preview-teleported
          />
          </div>
        </template>
      </el-table-column>
      <el-table-column prop="name" label="名字"></el-table-column>
      <el-table-column 
        prop="graduationYear" 
        label="毕业年份" 
        :filters="years.map(year => ({ text: year, value: year }))"
        :filter-method="filterGraduationYear"
      ></el-table-column>
      <el-table-column label="操作">
        <template #header>
          <el-input v-model="search" placeholder="输入姓名搜索" />
        </template>
        <template #default="scope">
          <!-- For admin, show "编辑/审核", for user, show "编辑" -->
          <el-button @click="editStudent(scope.row)" type="primary">
            {{ role === 'admin' ? '审核' : '编辑' }}
          </el-button>

          <!-- "删除" button remains the same for both roles -->
          <el-button @click="deleteStudent(scope.row.id)" type="danger" v-if="role === 'admin'">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-backtop target=".el-scrollbar__wrap" :visibility-height="20" :bottom="75">
      <div
        style="
          display: flex;  
          align-items: center; /* 垂直居中 */  
          justify-content: center; /* 水平居中 */  
          height: 100%;  
          background-color: #1989fa;  
          border-radius: 50%;  
          box-shadow: rgb(16 0 0 / 41%) 0px 12px 6px 0px;  
          color: #fff;  
          width: 100%;
        "
      ><el-icon><Top /></el-icon>
      </div>
    </el-backtop>

    <el-dialog :title="formTitle" v-model="showForm">
      <el-form v-loading="loading" @submit.prevent="submitForm">
        <el-form-item label="名字">
          <el-input v-model="form.name" required></el-input>
        </el-form-item>
        <el-form-item label="性别">
          <el-input v-model="form.gender" required></el-input>
        </el-form-item>
        <el-form-item label="专业">
          <el-input v-model="form.major" required></el-input>
        </el-form-item>
        <el-form-item label="毕业年份">
          <el-input v-model="form.graduationYear" required></el-input>
        </el-form-item>
        <el-form-item label="联系方式">
          <el-input v-model="form.contact" required></el-input>
        </el-form-item>
        <el-form-item label="兴趣爱好">
          <el-input v-model="form.hobby" required></el-input>
        </el-form-item>
        <el-form-item label="格言">
          <el-input v-model="form.lifeMotto" required></el-input>
        </el-form-item>
        <el-form-item label="自我介绍">
          <el-input type="textarea" v-model="form.introduction" autosize></el-input>
        </el-form-item>
        <el-form-item label="在校获奖情况">
          <el-input type="textarea" v-model="form.awardAtschool" autosize></el-input>
        </el-form-item>
        <el-form-item label="工作经验">
          <el-input type="textarea" v-model="form.workExperience" autosize></el-input>
        </el-form-item>
        <el-form-item label="想对师弟师妹说的话">
          <el-input type="textarea" v-model="form.say" autosize></el-input>
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
            @change="handleChange">
            <el-icon><Plus /></el-icon>
            <div style="color:#AAAAAA;">
              点击上传/拖拽上传
            </div>
          </el-upload>
          <el-dialog v-model="dialogVisible">
            <img width="100%" :src="dialogImageUrl" alt="Preview Image" />
          </el-dialog>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitForm">{{ role === 'admin' ? '通过' : '保存' }}</el-button>
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
      activePath: this.$route.path,
      role: localStorage.getItem("role") || "user", // 获取当前用户的角色
      students: [],
      showForm: false,
      formTitle: '',
      form: {
        id: null,
        name: '',
        gender: '',
        major: '',
        hobby:'',
        introduction:'',
        graduationYear: '',
        contact: '',
        lifeMotto: '',
        awardAtschool: '',
        workExperience: '',
        say: '',
        photo: '',
        image: null,
        imageUrl: ''
      },
      dialogImageUrl: '',
      dialogVisible: false,
      fileList: [],
      selectedYear: '',
      years: [],
      search: '', // 添加搜索字段
      loading: false, // 添加 loading 状态
      fullscreenLoading: false, // 添加全屏 loading 状态
    };
  },
  computed: {
    // 姓名搜索过滤
    filteredStudents() {
      const searchLower = this.search.toLowerCase();
      // 确保 students 是一个数组
      if (Array.isArray(this.students)) {
        return this.students.filter(student => 
          student.name.toLowerCase().includes(searchLower) || 
          this.getFirstLetter(student.name).includes(searchLower)
        );
      }
      return []; // 如果 students 不是数组，返回空数组
    }
  },
  methods: {
    // 获取学生数据
    async fetchStudents() {
      try {
        this.fullscreenLoading = true; // 开始加载时设置 fullscreenLoading 为 true

        let response;
        if (this.role === 'admin') {
          // 管理员不需要 token
          response = await axios.get(`${this.$baseURL}/admin/getStudent`);
        } else {
          const token = localStorage.getItem('token'); // 从 localStorage 中获取 token
          const headers = {
            token: token, // 将 token 添加到请求头，命名为 token
          };
        
          // 用户端口请求
          response = await axios.get(`${this.$baseURL}/user`, { headers }); // 发送请求时附加 headers
        }
      
        // 更新学生数据
        this.students = response.data.data || []; // 确保 students 至少是一个空数组
        this.extractYears();
      } catch (error) {
        console.error('获取学生数据时出错:', error);
        ElMessage.error('加载失败');
      } finally {
        this.fullscreenLoading = false; // 加载完成时设置 fullscreenLoading 为 false
      }
    },
    // 学生毕业年份过滤
    filterGraduationYear(value, row) {
      return row.graduationYear === value;
    },
    // 处理图片上传
    handleRemove(file) {
      const index = this.fileList.indexOf(file);
      if (index !== -1) {
        this.fileList.splice(index, 1);
      }
    },
    // 提取年份
    extractYears() {
      // 确保 students 是一个数组
      if (Array.isArray(this.students)) {
      // 处理年份提取逻辑
        this.years = this.students.map(student => student.graduationYear); // 确保使用正确的属性
      } else {
        this.years = []; // 如果 students 不是数组，清空 years
      }
    },
    // 获取名字的拼音首字母
    getFirstLetter(name) {
      return pinyin(name, {
        style: pinyin.STYLE_FIRST_LETTER
      }).flat().join('');
    },
    // 添加学生
    addStudent() {
      this.resetForm();
      this.formTitle = '添加学生';
      this.showForm = true;
    },
    handleExceed(files) {
      this.$refs.upload.clearFiles();
      const file = files[0];
      file.uid = genFileId();
      this.$refs.upload.handleStart(file);
    },

    handlePictureCardPreview(file) {
      this.dialogImageUrl = file.url || this.form.imageUrl;
      this.dialogVisible = true;
    },

    handleChange(file, fileList) {
      this.fileList = fileList;
    },
    
    // 提交表单
    async submitForm() {
      // 检查必要字段是否填写
      if (this.fileList.length == 0) {
        ElMessage.error('请上传照片');
        return;
      }

      this.loading = true; // 开始加载时设置 loading 为 true

      try {
        const formData = new FormData();

        // 添加普通字段
        formData.append('id', this.form.id);
        formData.append('name', this.form.name);
        formData.append('gender', this.form.gender);
        formData.append('major', this.form.major);
        formData.append('graduationYear', this.form.graduationYear);
        formData.append('contact', this.form.contact);
        formData.append('lifeMotto', this.form.lifeMotto);
        formData.append('awardAtschool', this.form.awardAtschool);
        formData.append('workExperience', this.form.workExperience);
        formData.append('say', this.form.say);
        formData.append('hobby', this.form.hobby);
        formData.append('introduction', this.form.introduction);

        // 添加照片
        if (this.fileList.length > 0) {
          if (this.fileList[0].url.startsWith('data:')) {
            const base64Response = await fetch(this.fileList[0].url);
            const blob = await base64Response.blob();
            formData.append('file', blob, 'photo.png');
          } else {
            formData.append('file', this.fileList[0].raw || this.fileList[0]);
          }
        }
        
        // 确定 API URL
        let apiUrl;
        if (this.role === 'admin') {
          apiUrl = this.form.id ? `${this.$baseURL}/admin/addPreStudent` : `${this.$baseURL}/admin/addPreStudent`;
        } else {
          apiUrl = this.form.id ? `${this.$baseURL}/user/updateStudent` : `${this.$baseURL}/user/uploadStudent`;
        }
      
        // 获取 token
        const token = localStorage.getItem('token');
      
        // 发送请求
        const response = await axios.post(apiUrl, formData, {
          headers: {
            'Content-Type': 'multipart/form-data',
            'token': token // 添加 token 到请求头
          }
        });

        // 确保 admin 返回的 ID
        if (this.role === 'admin') {
          const { id } = response.data; // 假设返回的数据结构中包含 id
          this.form.id = id; // 更新当前表单的 id
        }

        // 成功后关闭表单并刷新数据
        this.showForm = false;
        this.fetchStudents();
      } catch (error) {
        console.error('Error submitting form:', error.response ? error.response.data : error);
        ElMessage.error(error.response ? error.response.data.message || '提交失败' : '提交失败');
      } finally {
        this.loading = false; // 加载完成时设置 loading 为 false
      }
    },
    // 编辑学生
    editStudent(student) {
      this.loading = true; // 开始加载时设置 loading 为 true
      this.showForm = true; // 立即显示对话框
      try {
        this.formTitle = '编辑学生';
        this.form = { 
          ...student, 
          image: null, 
          imageUrl: student.photoUrl
        };
        this.fileList = [{
          name: '照片',
          url: student.photoUrl,
        }];
      } catch (error) {
        console.error('Error setting student details:', error);
        ElMessage.error('加载失败');
      } finally {
        this.loading = false; // 加载完成时设置 loading 为 false
      }
    },
    // 删除学生
    async deleteStudent(id) {
      try {
        const studentId = Number(id); // 将 ID 转换为数字
        console.log(`Deleting student with ID: ${studentId}`);
        await axios.delete(`${this.$baseURL}/admin/deletePreStudent/${studentId}`);
        this.fetchStudents();
        } catch (error) {
          console.error('Error deleting student:', error.response ? error.response.data : error);
          ElMessage.error('删除失败');
        }
    },
    // 重置表单
    resetForm() {
      this.form = {
        id: null,
        name: '',
        gender: '',
        major: '',
        graduationYear: '',
        contact: '',
        lifeMotto: '',
        awardAtschool: '',
        workExperience: '',
        say: '',
        photo: '',
        image: null,
        imageUrl: ''
      };
      this.fileList = [];
    },
  },
  mounted() {
    this.fetchStudents();
  }
};
</script>

<style scoped>
.container {
  display: flex;
  flex-direction: column;
  height: 89vh;  /* 使容器占满视口高度 */
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
