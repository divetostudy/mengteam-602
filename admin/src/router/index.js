import { createRouter, createWebHistory } from 'vue-router';
import AdminLogin from '../components/AdminLogin.vue';
import AdminLayout from '../components/AdminLayout.vue';
import Dashboard from '../components/Dashboard.vue';
import TeacherManagement from '../components/TeacherManagement.vue';
import StudentManagement from '../components/StudentManagement.vue';
import StudentManagementUncheck from '../components/StudentManagementUncheck.vue';
import NewsManagement from '../components/NewsManagement.vue';
import AchievementDisplay from '../components/AchievementDisplay.vue';
import PrizeDisplay from '../components/PrizeDisplay.vue';
import ProjectPresentation from '../components/ProjectPresentation.vue';
import DailyImages from '../components/DailyImages.vue';
import LbtManagement from '../components/LbtManagement.vue';

const routes = [
  {
    path: '/',
    redirect: '/login',
  },

  { path: '/user',
    redirect: '/admin',
  },
  
  { path: '/user/dashboard', 
    redirect: '/admin/dashboard',
  },

  {
    path: '/admin',
    component: AdminLayout,
    meta: { requiresAuth: true },
    children: [
      {
        path: 'dashboard',
        name: 'Dashboard',
        component: Dashboard,
      },
      {
        path: 'core-members',
        component: TeacherManagement,
      },
      {
        path: 'student-members',
        component: StudentManagement,
      },
      {
        path: 'student-members-uncheck',
        component: StudentManagementUncheck,
      },
      {
        path: 'news',
        name: 'NewsManagement',
        component: NewsManagement,
      },
      {
        path: 'achievement',
        name: 'AchievementDisplay',
        component: AchievementDisplay,
      },
      {
        path: 'prize',
        name: 'PrizeDisplay',
        component: PrizeDisplay,
      },
      {
        path: 'project',
        name: 'ProjectPresentation',
        component: ProjectPresentation,
      },
      {
        path: 'daily-images',
        name: 'DailyImages',
        component: DailyImages,
      },
      {
        path: 'lbt-management',
        name: 'LbtManagement',
        component: LbtManagement,
      },
    ],
  },

  {
    path: '/login',
    component: AdminLogin,
  },
  
  {
    path: '/logout',
    beforeEnter: (to, from, next) => {
      // ✅ 退出时把鉴权相关都清掉
      localStorage.removeItem('authenticated');
      localStorage.removeItem('role');
      localStorage.removeItem('token');
      next('/login');
    },
  },

  // （可选）兜底：未知路径重定向到仪表盘
  // { path: '/:pathMatch(.*)*', redirect: '/admin/dashboard' },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

router.beforeEach((to, from, next) => {
  const isAuthenticated = localStorage.getItem('authenticated') === 'true';
  if (to.matched.some(record => record.meta.requiresAuth) && !isAuthenticated) {
    next('/login');
  } else {
    next();
  }
});

export default router;
