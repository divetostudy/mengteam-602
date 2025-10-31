<script setup>
import { newsChannel } from '@/api/lab'
import { ref, onMounted } from 'vue'

const newsList = ref([])

const getNewsList = async () => {
  const res = await newsChannel()
  newsList.value = res.data.data
  console.log(newsList.value)
}

onMounted(() => {
  getNewsList()
})

</script>

<template>

<top-btn></top-btn>
  <h1 class="big-title">新闻动态</h1>
  <div class="news-box1" v-for="item in newsList" :key="item.id">
    <img :src=item.image.photoUrl alt="">
    <div class="text">
      <h3>{{ item.title }}</h3>
      <p>{{ item.time }}</p>
    </div>
  </div>
</template>

<style scoped lang="scss">
.big-title {
  width: 100%;
  margin: 20px auto;
  text-align: center;
}

.news-box1 {
  padding: 30px;
  display: flex;
  width: 800px;
  margin: 50px auto;
  border-radius: 15px;
  background-color: #fff;
  box-shadow: 0 0 20px #BDBDBD;

  img {
    width: 280px;
  height: 210px;
  object-fit: contain;
  padding-right: 30px;
  }

  .text {
    padding: 10px;
    height: 180px;
    display: flex;
    flex-direction: column;
    justify-content: space-between;

    h3 {
      font-size: 24px;
      width: 470px;
      color: black;
      text-indent: 2em;
    }
    p {
      display: flex;
      justify-content: flex-end;
      align-items: flex-end;
      padding: 20px 0;
      font-size: 20px;
      line-height: 1.6em;
    }
  }
}

</style>
