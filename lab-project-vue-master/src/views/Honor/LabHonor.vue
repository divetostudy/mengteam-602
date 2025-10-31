<script setup>
import { onMounted, ref } from 'vue'
import { honorChannel } from "@/api/lab"
const honorList = ref([])
const getHonorList = async () => {
  const res = await honorChannel()
  console.log(res.data.data)
  honorList.value = res.data.data

}

onMounted(() => {
  getHonorList()
})



</script>

<template>

<top-btn></top-btn>
  <div class="top">
    <div class="title">
      <h3>荣誉墙</h3>
      <p>HONOR WALL</p>
    </div>
  </div>

  <div class="group-carousel">
    <el-carousel 
      :interval="3000" 
      type="card" 
      height="400px" 
      trigger="click"
      cardScale="0.6"
    >
      <el-carousel-item v-for="item in honorList" :key="item.id">
        <img :src="item.url" alt="" style="width: 100%; height: 100%; object-fit: contain;">
        <!-- <h3 text="2xl" justify="center">{{ item }}</h3> -->
      </el-carousel-item>
    </el-carousel>
  </div>

  <!-- <div class="content">
    <div class="honor-teacher" v-for="item in honorList" :key="item.id">
      <div class="pic-box">
        <img :src="item.url" alt="">
      </div>
      <div class="text">
        {{item.content}}
      </div>
    </div>
  </div> -->

</template>

<style scoped lang="scss">

.top {
  .title {
    text-align: center;
    height: 100px;
    padding-bottom: 20px;

    h3 {
      font-size: 36px;
      line-height: 60px;
      font-weight: normal;
      padding-top: 20px;
    }

    p {
      color: rgb(151, 150, 150);
      font-weight: normal;
      padding-bottom: 20px;
    }
  }
}


// .content {
//   position: relative;
//   width: 1440px;
//   margin: 40px auto;
//   display: grid;
//   grid-template-columns: repeat(4, 1fr);
//   gap: 50px;
//   justify-content: center;
//   align-items: center;

//   .honor-teacher {
//     display: flex;
//     flex-direction: column;
//     width: 320px;
//     background-color: #fff;
//     box-shadow: 0 3px 0.3rem rgb(0 0 0 / 25%);
//     transition: all 0.3s;

//     &:hover {
//       cursor: pointer;
//       transform: scale(1.02);
//       box-shadow: 0 3px 0.5rem rgb(0 0 0 / 50%);
//     }

//     .pic-box {
//       width: 320px;

//       img {
//         width: 100%;
//         height: 100%;
//       }
//     }
//     .text {
//       padding: 20px;
//       font-size: 20px;
//       font-family: 'KaiTi';
//       text-align: center;
//       display: flex;
//       justify-content: center;
//       align-items: center;
//     }
//   }
// }
</style>
