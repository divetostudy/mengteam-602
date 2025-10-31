<script setup>
import { ref, onMounted } from 'vue'
import { useWindowScroll } from '@vueuse/core'
const showTopBtn = ref(false)
const { y } = useWindowScroll()
const scrollToTop = () => {
  console.log(window)
  window.scrollTo({
    top: 0,
    behavior: 'smooth'
  })
}

// 监听滚动事件，根据页面滚动的位置显示或隐藏按钮
const handleScroll = () => {
  if (y.value > 700) { // 页面滚动超过300px时显示按钮
    showTopBtn.value = true;
  } else {
    showTopBtn.value = false;
  }
}

// // 挂载时绑定滚动事件
onMounted(() => {
  window.addEventListener('scroll', handleScroll)
})


</script>

<template>
  <div class="btn" v-show="showTopBtn" @click="scrollToTop">
    <el-icon class="icon"><Upload /></el-icon>
  </div>
</template>

<style lang="scss" scoped>
.btn {
  width: 60px; 
  height: 60px; 
  position: fixed; 
  bottom: 100px; 
  right: 20px; 
  border: none; 
  cursor: pointer;
  border-radius: 15px;
  background: #DEDEDE;
  // border: 1px solid red;

  .icon {
    position: absolute;
    top: 15px;
    left: 15px;
    font-size: 30px;
    
  }

  
}
</style>