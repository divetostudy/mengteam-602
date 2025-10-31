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

const app = createApp(App);
Object.keys(Icons).forEach(key => app.component(key, Icons[key]));

// const baseURL = 'http://192.168.0.121:8083';
// const baseURL = 'http://192.168.0.107:8083';
// const baseURL = 'http://192.168.113.111:8083';
// const baseURL = 'http://192.168.203.107:8083';
const baseURL = 'http://192.168.203.245:8084';
// const baseURL = 'http://localhost:8084';

axios.defaults.baseURL = baseURL;

app.config.globalProperties.$axios = axios;
app.config.globalProperties.$baseURL = baseURL;

app.use(store).use(router).use(VueAxios, axios).use(ElementPlus);

// ✅ 启动时明确加上 'light' 或 'dark'
const saved = localStorage.getItem('theme') || 'light';
document.documentElement.classList.toggle('dark', saved === 'dark');
document.documentElement.classList.toggle('light', saved !== 'dark');

app.mount('#app');
