// main.js
import { createApp } from 'vue';
import App from './App.vue';
import router from './router';
import store from './store';
import axios from 'axios';
import VueAxios from 'vue-axios';
import ElementPlus from 'element-plus';
import 'element-plus/dist/index.css';
import * as Icons from '@element-plus/icons-vue'; // 导入 Icons

// ✅ EP 暗黑变量、你的自定义主题变量
import 'element-plus/theme-chalk/dark/css-vars.css';
import './styles/theme.css';

// 📦 导入API配置（统一管理的地方！）
import { baseURL } from './api/index.js';

const app = createApp(App);
Object.keys(Icons).forEach(key => app.component(key, Icons[key]));

// 使用导入的 baseURL
axios.defaults.baseURL = baseURL;

app.config.globalProperties.$axios = axios;
app.config.globalProperties.$baseURL = baseURL;

app.use(store).use(router).use(VueAxios, axios).use(ElementPlus);

// ✅ 启动时明确加上 'light' 或 'dark'
const saved = localStorage.getItem('theme') || 'light';
document.documentElement.classList.toggle('dark', saved === 'dark');
document.documentElement.classList.toggle('light', saved !== 'dark');

app.mount('#app');
