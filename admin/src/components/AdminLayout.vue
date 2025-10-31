<template>
  <el-container>
    <!-- 侧栏 -->
    <el-aside
      class="aside"
      :class="{ collapsed: isCollapsed }"
      :style="{ width: asideWidth }"
    >
      <el-menu
        ref="menuRef"
        :default-active="$route.path"
        class="el-menu-vertical-clip"
        :router="true"
        text-color="#fff"
        active-text-color="#ffd04b"
        :unique-opened="false"
        @select="onMenuSelect"
        @open="onOpen"
        @close="onClose"
      >
        <el-menu-item index="/admin/dashboard">
          <div class="icon-container"><HomeFilled class="menu-icon" /></div>
          <span class="menu-text">主页</span>
        </el-menu-item>

        <el-menu-item index="/admin/core-members" v-if="role === 'admin'">
          <div class="icon-container"><User class="menu-icon" /></div>
          <span class="menu-text">核心成员</span>
        </el-menu-item>

        <!-- 学生成员 -->
        <el-sub-menu v-if="role === 'admin'" index="student-members">
          <template #title>
            <div class="icon-container"><User class="menu-icon" /></div>
            <span class="menu-text">学生成员</span>
          </template>
          <el-menu-item-group>
            <el-menu-item class="centered-item" index="/admin/student-members">
              <el-icon><CircleCheck /></el-icon>
              <span class="menu-text">已审核通过</span>
            </el-menu-item>
            <el-menu-item class="centered-item" index="/admin/student-members-uncheck">
              <el-icon><Warning /></el-icon>
              <span class="menu-text">待审核</span>
            </el-menu-item>
          </el-menu-item-group>
        </el-sub-menu>

        <!-- 展示图片管理 -->
        <el-sub-menu v-if="role === 'admin'" index="image-management">
          <template #title>
            <div class="icon-container"><PictureFilled class="menu-icon" /></div>
            <span class="menu-text">展示图片管理</span>
          </template>
          <el-menu-item-group>
            <el-menu-item class="centered-item" index="/admin/daily-images">
              <el-icon><Picture /></el-icon>
              <span class="menu-text">团建与日常</span>
            </el-menu-item>
            <el-menu-item class="centered-item" index="/admin/lbt-management">
              <el-icon><Film /></el-icon>
              <span class="menu-text">首页与成果轮播图</span>
            </el-menu-item>
          </el-menu-item-group>
        </el-sub-menu>

        <el-menu-item index="/admin/student-members-uncheck" v-if="role === 'user'">
          <div class="icon-container"><Edit class="menu-icon" /></div>
          <span class="menu-text">学生信息填报</span>
        </el-menu-item>

        <el-menu-item index="/admin/news" v-if="role === 'admin'">
          <div class="icon-container"><Reading class="menu-icon" /></div>
          <span class="menu-text">时讯新闻</span>
        </el-menu-item>

        <el-menu-item index="/admin/achievement" v-if="role === 'admin'">
          <div class="icon-container"><DataAnalysis class="menu-icon" /></div>
          <span class="menu-text">成果展示</span>
        </el-menu-item>

        <el-menu-item index="/admin/prize" v-if="role === 'admin'">
          <div class="icon-container"><Medal class="menu-icon" /></div>
          <span class="menu-text">奖项成就</span>
        </el-menu-item>

        <el-menu-item index="/admin/project" v-if="role === 'admin'">
          <div class="icon-container"><Files class="menu-icon" /></div>
          <span class="menu-text">项目介绍</span>
        </el-menu-item>

        <el-menu-item index="/logout">
          <div class="icon-container"><CircleCloseFilled class="menu-icon" /></div>
          <span class="menu-text">退出登录</span>
        </el-menu-item>
      </el-menu>
    </el-aside>

    <el-container>
      <!-- 顶栏：左折叠、中标题、右主题开关 -->
      <el-header class="header">
        <div class="header-left">
          <el-button
            v-if="!isCollapsed"
            link
            class="collapse-btn"
            @click="collapseNow"
            title="收起菜单"
          >
            <el-icon><ArrowLeftBold /></el-icon>
          </el-button>
        </div>

        <div class="header-title">
          {{ role === 'admin' ? '实验室官网管理员后台' : '实验室官网学生信息填报后台' }}
        </div>

        <div class="header-right">
          <el-switch
            v-model="isDark"
            @change="toggleDark"
            :title="isDark ? '切到亮色' : '切到暗色'"
          >
            <template #active-action>
              <el-icon><Moon /></el-icon>
            </template>
            <template #inactive-action>
              <el-icon><Sunny /></el-icon>
            </template>
          </el-switch>
        </div>
      </el-header>

      <el-main>
        <router-view />
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
export default {
  name: 'AdminLayout',
  data() {
    return {
      role: localStorage.getItem('role') || 'user',
      isCollapsed: false,
      menuWidth: 250,
      railWidth: 67,
      openSet: new Set(),
      isDark:
        document.documentElement.classList.contains('dark') ||
        localStorage.getItem('theme') === 'dark',
    };
  },
  computed: {
    asideWidth() {
      return this.isCollapsed ? `${this.railWidth}px` : `${this.menuWidth}px`;
    },
  },
  mounted() {
    this.applyThemeClass(this.isDark ? 'dark' : 'light');
  },
  methods: {
    /* 侧栏渐变的淡出层：让主题切换时的渐变更顺滑 */
    fadeAsideGradientOnce() {
      const aside = this.$el?.querySelector?.('.aside');
      if (!aside) return;
      const styles = getComputedStyle(aside);
      const prevBg = styles.backgroundImage || styles.background;

      const fader = document.createElement('div');
      fader.className = 'aside-fader';
      fader.style.background = prevBg;
      aside.appendChild(fader);

      requestAnimationFrame(() => {
        fader.style.opacity = '0';
        fader.addEventListener('transitionend', () => fader.remove(), { once: true });
      });
    },

    applyThemeClass(mode) {
      const html = document.documentElement;
      html.classList.toggle('dark', mode === 'dark');
      html.classList.toggle('light', mode !== 'dark');
      localStorage.setItem('theme', mode);
    },

    toggleDark(val) {
      this.fadeAsideGradientOnce();
      this.applyThemeClass(val ? 'dark' : 'light');
    },

    onMenuSelect() {
      if (this.isCollapsed) this.isCollapsed = false;
    },
    onOpen(index) {
      if (this.isCollapsed) {
        this.isCollapsed = false;
        this.$nextTick(() => {
          const menu = this.$refs.menuRef;
          if (menu && typeof menu.open === 'function') menu.open(index);
          this.openSet.add(index);
        });
      } else {
        this.openSet.add(index);
      }
    },
    onClose(index) { this.openSet.delete(index); },
    collapseNow() {
      const menu = this.$refs.menuRef;
      if (menu && typeof menu.close === 'function')
        Array.from(this.openSet).forEach((idx) => menu.close(idx));
      this.openSet.clear();
      this.isCollapsed = true;
    },
  },
};
</script>

<style scoped>
/* 侧栏：宽度动画 + 渐变背景过渡（更慢一点更丝滑） */
.aside {
  position: relative;
  overflow: hidden;
  width: 250px;
  transition:
    width .25s ease,
    background-color .45s ease,
    background-image .45s ease,
    box-shadow .45s ease;
  background: var(--menu-bg, linear-gradient(-15deg, #0ba360, #3cba92 100%)) !important;
}

/* 折叠时隐藏文字（避免布局抖动） */
.aside.collapsed .menu-text { opacity: 0; }
.aside .menu-text { vertical-align: middle; display: inline-block; transition: opacity .2s ease; }

/* 菜单本体透明，让父容器背景透出来 */
.el-menu-vertical-clip {
  width: 250px;
  min-height: 100vh;
  padding: 20px 0;
  border-right: none;
  background: transparent !important;
}
:deep(.el-menu) { background: transparent !important; }

/* hover 色（保留原样式） */
.el-menu-item:hover { background-color: #00ac9ad0 !important; }
:deep(.el-sub-menu__title:hover) { background-color: #03b9a7 !important; }

/* 不换行避免跳动 */
:deep(.el-menu-item), :deep(.el-sub-menu__title) { white-space: nowrap; }

/* 图标容器固定尺寸 */
.icon-container {
  width: 28px; height: 28px;
  display: inline-flex; justify-content: center; align-items: center;
  margin-right: 12px;
}

/* 子项左对齐缩进 */
.centered-item { text-align: left; margin-left: 20px; }

/* 顶栏（颜色由 theme.css 的变量统一） */
.el-header.header {
  position: relative;
  background-color: var(--header-bg, #fff);
  color: var(--header-text, #333);
  padding: 16px 48px;
  font-size: 20px;
  font-weight: bold;
  box-shadow: var(--card-shadow, 0 2px 12px rgba(0, 0, 0, .1));
  display: flex; align-items: center; justify-content: center;
}

/* 顶栏：主题开关颜色只在这一处生效 */
.header-right :deep(.el-switch) {
  --el-switch-on-color: var(--switch-active);
  --el-switch-off-color: var(--switch-inactive);
}
.header-right :deep(.el-switch__action) {
  color: var(--text-primary);
}

/* 顶栏左右定位 */
.header-left {
  position: absolute; left: 16px; top: 50%; transform: translateY(-50%);
}
.header-right {
  position: absolute; right: 16px; top: 50%; transform: translateY(-50%);
  display: flex; align-items: center;
}
.collapse-btn { font-size: 22px; padding: 0 6px; line-height: 1; user-select: none; }
.header-title { text-align: center; }

/* 侧栏“淡出层”过渡（配合 JS 制造渐变跨场） */
.aside-fader {
  position: absolute; inset: 0; z-index: 1; pointer-events: none; opacity: 1;
  transition: opacity .25s ease;
}

/* 禁止菜单文字被选中 */
.aside :deep(.el-menu-vertical-clip),
.aside :deep(.el-menu-item),
.aside :deep(.el-sub-menu__title),
.aside :deep(.el-menu-item *),
.aside :deep(.el-sub-menu__title *) {
  -webkit-user-select: none; -moz-user-select: none; -ms-user-select: none; user-select: none;
  cursor: default;
}

/* 全局字号（保持风格） */
* { font-size: 18px; }
</style>
