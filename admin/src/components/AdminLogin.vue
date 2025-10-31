<template>
  <div class="login-container">
    <el-card class="login-card">
      <h3 class="login-title">登录</h3>
      <el-form @submit.prevent="handleLogin">
        <el-form-item>
          <el-input 
            v-model="username" 
            placeholder="用户名" 
            prefix-icon="User" 
            @keyup.enter="focusPassword" 
            ref="usernameInput"
          />
        </el-form-item>
        <el-form-item>
          <el-input 
            v-model="password" 
            placeholder="密码" 
            prefix-icon="Lock" 
            show-password 
            @keyup.enter="handleLogin" 
            ref="passwordInput"
          />
        </el-form-item>
        <el-form-item>
          <el-button 
            type="primary" 
            @click="handleLogin"
          >
            登录
          </el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script>
import axios from 'axios'; // 使用axios进行HTTP请求
import { ElMessage } from 'element-plus';

export default {
  data() {
    return {
      username: '',
      password: '',
    };
  },
  methods: {
   focusPassword() {
      // 聚焦到密码输入框
      this.$refs.passwordInput.focus();
    },
    async handleLogin() {
      try {
       // 使用POST请求，将参数以URL查询参数的方式传递
        const response = await axios.post('/common/login', null, {
          params: {
            username: this.username,
            password: this.password,
          },
        });
      
        console.log('后端响应:', response.data); // 打印后端返回的数据
      
        if (response.data.code === 200 && response.data.data) {
          // 提取JWT token和角色
          const token = response.data.data.admin || response.data.data.user; // 根据实际返回字段提取
          const role = response.data.data.admin ? 'admin' : 'user'; // 根据token是否存在来判断角色
        
          // 将角色和token存储到localStorage
          localStorage.setItem('authenticated', 'true');
          localStorage.setItem('role', role);
          localStorage.setItem('token', token);
        
          // 根据角色跳转页面
          if (role === 'admin') {
            this.$router.push('/admin/dashboard');
            ElMessage.success('管理员登入成功');
          } else {
            this.$router.push('/admin/dashboard'); // 根据用户角色跳转到不同的页面
            ElMessage.success('用户登入成功');
          }
        } else {
          ElMessage.error('登录失败，请重试');
        }
      } catch (error) {
        console.error('请求错误:', error.response); // 打印错误日志，方便调试
        ElMessage.error('用户名或密码错误');
      }
    },
  },
};
</script>

<style scoped>
.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background-image:linear-gradient(135deg,#72EDF2 10%,#5151E5 100%);
}

.login-card {
  width: 400px;
  padding: 20px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
}

.login-title {
  text-align: center;
  margin-bottom: 20px;
  font-size: 24px;
  color: #333;
}

.centered-form-item {
  display: flex;
  justify-content: center;
}

.el-button {
  width: 100%;
}
</style>




<!-- <template>
  <div class="login-container">
    <el-card class="login-card">
      <h3 class="login-title">登录</h3>
      <el-form @submit.prevent="handleLogin">
        <el-form-item>
          <el-input 
            v-model="username" 
            placeholder="用户名" 
            prefix-icon="User" 
            @keyup.enter="focusPassword" 
            ref="usernameInput"
          />
        </el-form-item>
        <el-form-item>
          <el-input 
            v-model="password" 
            placeholder="密码" 
            prefix-icon="Lock" 
            show-password 
            @keyup.enter="handleLogin" 
            ref="passwordInput"
          />
        </el-form-item>
        <el-form-item>
          <el-button 
            type="primary" 
            @click="handleLogin"
          >
            登录
          </el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script>
import { ElMessage } from 'element-plus';

export default {
  data() {
    return {
      username: '',
      password: '',
    };
  },
  methods: {
    focusPassword() {
      // 聚焦到密码输入框
      this.$refs.passwordInput.focus();
    },
    handleLogin() {
      // 模拟一个离线登录响应
      if (this.username === 'admin' && this.password === '123456') {
        // 模拟管理员登录
        const token = 'fake-admin-token';
        const role = 'admin';
        
        // 存储到localStorage
        localStorage.setItem('authenticated', 'true');
        localStorage.setItem('role', role);
        localStorage.setItem('token', token);
        
        // 模拟跳转
        this.$router.push('/admin/dashboard');
        ElMessage.success('管理员登入成功');
      } else if (this.username === 'user' && this.password === '123456') {
        // 模拟普通用户登录
        const token = 'fake-user-token';
        const role = 'user';
        
        // 存储到localStorage
        localStorage.setItem('authenticated', 'true');
        localStorage.setItem('role', role);
        localStorage.setItem('token', token);
        
        // 模拟跳转
        this.$router.push('/user/dashboard');
        ElMessage.success('用户登入成功');
      } else {
        // 模拟失败
        ElMessage.error('用户名或密码错误');
      }
    },
  },
};
</script> -->

<style scoped>
.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background-image: linear-gradient(135deg, #72EDF2 10%, #5151E5 100%);
}

.login-card {
  width: 400px;
  padding: 20px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
}

.login-title {
  text-align: center;
  margin-bottom: 20px;
  font-size: 24px;
  color: #333;
}

.centered-form-item {
  display: flex;
  justify-content: center;
}

.el-button {
  width: 100%;
}
</style>
