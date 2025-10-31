<script setup>
// import LabTopComponent from "./components/LabTopComponent.vue";
import { onMounted } from 'vue';
onMounted(() => {
  document.title = '电子科技大学中山学院大数据与信息技术处理实验室'
})
</script>

<template>

    <router-view></router-view>

 <footer>

 </footer>
</template>

<style scoped>

</style>
