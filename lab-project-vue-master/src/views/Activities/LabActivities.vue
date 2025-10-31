<script setup>
import { actPic, foodPic } from "@/api/lab"
import { onMounted, ref } from "vue";
const actList = ref([])
const getActList = async () => {
  const res = await actPic()
  // console.log(res.data.data)
  actList.value = res.data.data
}
const foodList = ref([])
const getFoodList = async () => {
  const res = await foodPic()
  foodList.value = res.data.data
}

onMounted(() => {
  getActList()
  getFoodList()
})
// import actOne from "@/assets/active/group_photo1.jpg"
// import actTwo from "@/assets/active/group_photo2.jpg"
// import actThree from "@/assets/active/group_photo3.jpg"
// import actFour from "@/assets/active/img01.jpg"
// import actFive from "@/assets/active/img02.jpg"
// import actSix from "@/assets/active/img03.jpg"
// import actSeven from "@/assets/active/img04.jpg"
// import actEight from "@/assets/active/img05.jpg"
// import actNine from "@/assets/active/img06.jpg"

// import foodOne from "@/assets/food/food1.jpg"
// import foodTwo from "@/assets/food/food2.jpg"
// import foodThree from "@/assets/food/food3.jpg"
// import foodFour from "@/assets/food/food4.jpg"
// import foodFive from "@/assets/food/food5.jpg"
// import foodSix from "@/assets/food/food6.jpg"

// const actList = [
//   { id: 1, pic: actOne },
//   { id: 2, pic: actTwo },
//   { id: 3, pic: actThree },
//   { id: 4, pic: actFour },
//   { id: 5, pic: actFive },
//   { id: 6, pic: actSix },
//   { id: 7, pic: actSeven },
//   { id: 8, pic: actEight },
//   { id: 9, pic: actNine }
// ]

// const foodList = [
//   { id: 1, pic: foodOne },
//   { id: 2, pic: foodTwo },
//   { id: 3, pic: foodThree },
//   { id: 4, pic: foodFour },
//   { id: 5, pic: foodFive },
//   { id: 6, pic: foodSix }
// ]



// const img = actOne
</script>

<template>

<top-btn></top-btn>
  <div class="act-page">
    <div class="group-photo">
      <div class="title">
        <h3>团建合照</h3>
        <p>GROUP PHOTO</p>
      </div>
      <div class="group-carousel">
        <el-carousel 
          :interval="3000" 
          type="card" 
          height="400px"
          trigger="click"
          cardScale="0.6"
        >
          <el-carousel-item v-for="item in actList" :key="item.id">
            <img :src="item.photoUrl" alt="" style="width: 100%; height: 100%; object-fit: contain;">
            <!-- <h3 text="2xl" justify="center">{{ item }}</h3> -->
          </el-carousel-item>
        </el-carousel>
      </div>
    </div>

    <div class="group-photo">
      <div class="title">
        <h3>美食分享</h3>
        <p>FOOD SHARE</p>
      </div>
      <div class="group-carousel">
        <el-carousel 
          :interval="3000" 
          type="card" 
          height="400px"
          trigger="click"
          cardScale="0.6"
        >
          <el-carousel-item v-for="item in foodList" :key="item.id">
            <img :src="item.photoUrl" alt="" style="width: 100%; height: 100%; object-fit: contain;">
            <!-- <h3 text="2xl" justify="center">{{ item }}</h3> -->
          </el-carousel-item>
        </el-carousel>
      </div>
    </div>
  </div>

<!-- <div style="width: 500px; height: 500px;" v-for="item in actList" :key="item.id">
   <img style="width: 100%; height: 100%;" :src="item.pic" alt="">
</div>

<div style="width: 500px; height: 500px;" v-for="item in foodList" :key="item.id">
   <img style="width: 100%; height: 100%;" :src="item.pic" alt="">
</div> -->


</template>

<style scoped lang="scss">
.act-page {
  .group-photo {
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

    .group-carousel {
      // border: 1px solid red;
      // height: 300px;
      padding: 50px 0;
    }
  }
}
</style>
