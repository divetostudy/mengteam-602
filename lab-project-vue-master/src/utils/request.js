import axios from "axios"

const request = axios.create({
  //服务器接口
   baseURL: 'https://a602-backend.zeabur.app:8084',
  //测试接口
  //baseURL: 'http://192.168.203.245:8084',
  timeout: 10000
})

export default request
