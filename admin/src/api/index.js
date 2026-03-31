import axios from 'axios';

// 🔧 API 基础配置（统一管理配置的地方！）
export const baseURL = 'http://47.121.117.160:8081/api';

// 可选：直连后端（不通过Nginx代理）
// export const baseURL = 'http://47.121.117.160:8084';

const apiClient = axios.create({
  baseURL: baseURL,
  headers: {
    'Content-Type': 'application/json'
  },
  timeout: 10000
});

export default apiClient;
