import axios from "axios"

const request = axios.create({
  //服务器接口
  // baseURL: 'http://47.121.117.160:8083',
  //测试接口
  baseURL: 'http://47.121.117.160:8084',
  timeout: 10000
})

export default request