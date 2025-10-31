<script setup>
import { ref, reactive, watch } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()

// 导航栏
const activeIndex = ref(0)
const tabArr = reactive([
  { id: 1, text: '首页', url: '/home' },
  { id: 2, text: '关于我们', url: '/about' },
  { id: 3, text: '新闻动态', url: '/news' },
  { id: 4, text: '成果展示', url: '/result' },
  // { id: 5, text: '团建活动', url: '/activities' },
  // { id: 6, text: '荣誉成就', url: '/honor' },
  { id: 5, text: '加入我们', url: '/join' },
  { id: 6, text: '团建活动', url: '/activities' },
  { id: 7, text: '荣誉成就', url: '/honor' },
])

// 更新activeIndex以匹配当前路由
const updateActiveIndex = () => {
  const currentPath = router.currentRoute.value.path
  const foundIndex = tabArr.findIndex(item => item.url === currentPath)
  if (foundIndex !== -1) {
    activeIndex.value = foundIndex
  }
}

const changeColor = ({ url }, index) => {
  // console.log(index)
  activeIndex.value = index
  router.push(url)
}

watch(router.currentRoute, updateActiveIndex, { immediate: true })

</script>

<template>
  <header>
    <h1 style="color: #ddd; margin: auto;">大数据与信息技术处理实验室</h1>
    <div class="navTop">
      <div>
        <img src="../assets/logo.png" alt="">
      </div>
      <!-- 导航分类 -->
      <ul class="nav">
        <li 
          class="dropdown" 
          v-for="(item, index) in tabArr" 
          :key="item.id" 
          :class="{ active: activeIndex === index }" 
          @click="changeColor(item, index)"
        >
          {{ item.text }}
        </li>
      </ul>
    </div>
  </header>
</template>

<style scoped lang="scss">
header {
  color: #cfcfcf;
  text-align: center;
  padding: 1rem;
  height: 154px;
  background-image: url('../assets/bgtop09.jpg');
  background-repeat: no-repeat;
  background-size: cover;
  background-position: center;

  h1 {
    line-height: 100px;
    font-family:'Courier New', Courier, monospace;
    user-select: none;
    -webkit-user-select: none;
    -moz-user-select: none;
    -ms-user-select: none;
  }

  .navTop {
    position: relative;
    display: flex;
    justify-content: center;
    align-items: center;
    height: 70px;
    margin: 0 auto;

    img {
      margin-left: 45px;
      vertical-align: middle;
      height: 70px;
      width: 340px;
      object-fit: contain;
    }
    
    .nav {
      display: flex;

      .dropdown {
        display: block;
        width: 120px;
        height: 40px;
        line-height: 36px;
        font-size: 20px;
        border-right: 2px solid #0066CC;
        cursor: pointer;
        //去掉光标
        user-select: none;
        -webkit-user-select: none;
        -moz-user-select: none;
        -ms-user-select: none;

      }
      
    }
  }
}

.active {
  color: #8fc5f8;
}

</style>