import request from '@/utils/request'

//获取首页项目展示的接口
export const projectShowChannel = () => request.get('/project')

//获取首页项目展示详情的接口
export const projectShowDetail = (id) => {
  return request.get(`/project/${id}`)
}

//获取关于我们页面老师团队的接口
export const teacherDetail = () => request.get('/teacher')

//获取关于我们页面学生团队的接口
export const studentTeam = () => request.get('/student')

//获取关于我们页面学生团队详情的接口
export const studentTeamDetail = (name) => {
  return request.get(`/student/${name}`)
} 

//获取新闻动态页面的接口
export const newsChannel = () => request.get('/news')

//获取成果展示页面的接口
export const resultShow = () => request.get('/achievement')

//获取荣誉成就页面的接口
export const honorChannel = () => request.get('/prize')

//获取首页轮播图
export const homeSlider = () => request.get('/lbt/getHomeLbt')

// 获取成果轮播图
export const showSlider = () => request.get('/lbt/getCGLbt')

// 获取成果轮播图
export const contactMsg = () => request.get('/contactInformation')

// 获取实验室环境图片
export const envPic = () => request.get('/environment/get')

// 获取团建活动图片
export const actPic = () => request.get('/activities/get')

// 获取美食分享图片
export const foodPic = () => request.get('/food/get')

