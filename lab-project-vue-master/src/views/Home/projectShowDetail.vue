<script setup>
import { useRoute } from 'vue-router'
import { projectShowDetail } from '@/api/lab'
import { onMounted, ref } from 'vue'
const route = useRoute()
const id = Number(route.query.id)
console.log(id)
const projectDetailList = ref([])
const getProjectDetail = async () => {
  const res = await projectShowDetail(id)
  projectDetailList.value = res.data.data
  console.log(projectDetailList.value)
}

onMounted(() => {

  getProjectDetail()
})


</script>

<template>
<div class="item-box" v-if="projectDetailList.length">
  <h1 style="text-align: center;">{{ projectDetailList[0].title }}</h1>
  <p>
    {{ projectDetailList[0].content }}
  </p>
  <div class="img-box">
    <img :src=projectDetailList[0].image.photoUrl alt="">
    <img :src=projectDetailList[0].photos[0].photoUrl alt="">
    <img :src=projectDetailList[0].photos[1].photoUrl alt=""> 
  </div>
</div>

</template>

<style scoped lang="scss">
.item-box {
  padding: 30px 0;
  background: linear-gradient(to bottom, #B9EBD3, #FFE5D9);
  background-attachment: fixed;
  
  p {
    font-size: 20px;
    line-height: 35px;
    text-indent: 2em;
    margin: 30px 180px;
  }

  img {
    margin: 0 auto;
    display: flex;
    width: 1200px;
  }
}

</style>