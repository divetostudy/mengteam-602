import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      redirect: '/home',    //重定向
      component: () => import('@/views/HomeRouter/HomeRouter.vue'),
      children: [
        {
          path: '/home',
          component: () => import('@/views/Home/LabHome.vue'),
         },
         {
          path: '/about',
          component: () => import('@/views/AboutUs/AboutUs.vue')
         },
         {
          path: '/news',
          component: () => import('@/views/News/LabNews.vue')
         },
         {
          path: '/result',
          component: () => import('@/views/ResultShow/ResultShow.vue')
         },
         {
          path: '/join',
          component: () => import('@/views/JoinUs/JoinUs.vue')
         },
         {
          path: '/activities',
          component: () => import('@/views/Activities/LabActivities.vue')
         },
         {
          path: '/honor',
          component: () => import('@/views/Honor/LabHonor.vue')
         }     
      ]
    },
    {
      path: '/projectdetail',
      component: () => import('@/views/Home/projectShowDetail.vue')
    },
    {
      path: '/studentdetail',
      component: () => import('@/views/AboutUs/StudentIntroduce.vue')
    },
    {
      path: '/zhaoxin2023',
      component: () => import('@/views/JoinUs/joinUsDetail_23.vue')
    },
    {
      path: '/zhaoxin2024',
      component: () => import('@/views/JoinUs/joinUsDetail_24.vue')
    }
  ]
})

router.afterEach(() => {
  window.scrollTo({
    top: 0
  })
})

export default router
