<script setup>
import { ref, onMounted, onUnmounted } from 'vue'
import { projectShowChannel, homeSlider } from '@/api/lab'
// import { reactive } from 'vue'
// import { require } from '@/utils/api.js'
// import bannerImgOne from '@/assets/lunbo2.jpg'
// import bannerImgTwo from '@/assets/environment04.jpg'
// import bannerImgThree from '@/assets/图片1.png'
// import bannerImgFour from '@/assets/图片3.png'
// import bannerImgFive from '@/assets/图片2.png'
// import bannerImgSix from '@/assets/图片4.png'
// import router from '@/router'

// import { useRouter } from 'vue-router'
// const router = useRouter()

const bannerPic = ref([])

const getBannerPic = async () => {
  const res = await homeSlider()
  console.log(res.data)
  bannerPic.value = res.data.data
}




// const bannerPic = [
//   { id: 1, pic: bannerImgOne },
//   { id: 2, pic: bannerImgTwo },
//   { id: 3, pic: bannerImgThree },
//   { id: 4, pic: bannerImgFour },
//   { id: 5, pic: bannerImgFive },
//   { id: 6, pic: bannerImgSix }
//   ]

const showArrows = ref(false)
const currentImageIndex = ref(0)
let autoPlayTimer = null
const imagesRef = ref(null)
const circlesRef = ref(null)

const nextImage = () => {
  // currentImageIndex.value = (currentImageIndex.value + 1) % bannerPic.length
  // console.log(currentImageIndex.value)
  imagesRef.value[currentImageIndex.value].style.opacity = 0
  circlesRef.value[currentImageIndex.value].style.backgroundColor = 'rgb(209,195,172,0.4)'
  currentImageIndex.value++
  if(currentImageIndex.value == bannerPic.value.length) {
    currentImageIndex.value = 0
  }
  imagesRef.value[currentImageIndex.value].style.opacity = 1
  circlesRef.value[currentImageIndex.value].style.backgroundColor = '#6dbeec'

}

const prevImage = () => {
  imagesRef.value[currentImageIndex.value].style.opacity = 0
  circlesRef.value[currentImageIndex.value].style.backgroundColor = 'rgb(209,195,172,0.4)'
  currentImageIndex.value--
  if(currentImageIndex.value == -1) {
    currentImageIndex.value = bannerPic.value.length - 1
  }
  imagesRef.value[currentImageIndex.value].style.opacity = 1
  circlesRef.value[currentImageIndex.value].style.backgroundColor = '#6dbeec'
  // currentImageIndex.value = (currentImageIndex.value - 1 + bannerPic.length) % bannerPic.length
}

const startAutoPlay = () => {
  autoPlayTimer = setInterval(nextImage, 3000)
}

const pauseAutoPlay = () => {
  clearInterval(autoPlayTimer)
}


onUnmounted(() => {
  pauseAutoPlay()
})

const projectList = ref([])

const getProjectList = async () => {
  const res = await projectShowChannel()
  projectList.value = res.data.data
  console.log(res.data.data)
}

// const clickProject = (item) => { 
  //   // localStorage.setItem('scrollPosition', window.scrollY)
  //   router.push(`/projectdetail?id=${item.id}`)
  // }
  
onMounted(() => {
  getProjectList()
  getBannerPic()
  startAutoPlay()
  // const savedPosition = localStorage.getItem('scrollPosition')
  // if (savedPosition) {
  //   window.scrollTo(0, parseInt(savedPosition))
  //   // localStorage.removeItem('scrollPosition')
  // }
})
</script>

<template>

  <top-btn></top-btn>

  <div class="lunbo">
    <div class="content" @mouseover="showArrows = true" @mouseleave="showArrows = false">
      <ul class="item">
        <li 
        ref="imagesRef"
        v-for="(item, index) in bannerPic" 
        :key="item.id"
        :style="{ opacity: currentImageIndex === index ? 1 : 0 }"
        >
          <img :src=item.photo alt="" >
        </li>
      </ul>
      <div class="btn-left" v-show="showArrows" @click="prevImage">&#10094;</div>
      <div class="btn-right" v-show="showArrows" @click="nextImage">&#10095;</div>
      <ul class="circle">
        <li v-for="item in bannerPic" 
        :key="item.id"
        ref="circlesRef"
        >
        </li>
      </ul>
    </div>
  </div>

  <!-- 项目展示 -->
  <div class="project">
    <div class="title" style="font-size: 35px; color: dodgerblue; margin-left: 10px; font-weight: 600;">项目展示</div>
    <div class="big-box">
      <router-link v-for="item in projectList" :key="item.id" :to="`/projectdetail?id=${item.id}`" target="_blank" >
        <div class="box">
          <div class="img-box">
            <img :src=item.image.photoUrl alt="" />
          </div>
          <div class="text-box">
            <h2>{{ item.title }}</h2>
          </div>
        </div>
      </router-link>
    </div> 
  </div>


</template>

<style scoped lang="scss">

.lunbo {
  margin: 0;
  padding: 0;
  position: relative;
  width: 100%;
  height: 500px;

  .content {
    width: 100%;
    height: 500px;
    margin: 0 auto;
    justify-content: center;
    align-items: center;
    cursor: pointer;

    .item {
      width: 100%;
	    height: 500px;
      
      li {
        position: absolute;
        height: 500px;
        width: 100%;
        opacity: 0;
        transition: all 1.5s;
      }

      li:first-child {
        opacity: 1;
      }

      img {
        width: 100%;
        height: 100%;
      }
    }

    .btn-left {
      width: 50px;
      height: 90px;
      font-size: 30px;
      color: white;
      background-color:rgba(0,0,0,0.4);
      line-height: 90px;  /*箭头垂直居中*/
      text-align: center;   /*箭头水平居中*/
      z-index: 10;  /*始终显示在图片的上层*/
      position: absolute;
      left: 0;
      top: 50%;
      transform: translateY(-50%);  /*使按钮向上偏移居中对齐*/
      cursor: pointer;
    }

    .btn-right {
      width: 50px;
      height: 90px;
      font-size: 30px;
      color: white;
      background-color:rgba(0,0,0,0.4);
      line-height: 90px;  /*箭头垂直居中*/
      text-align: center; /*箭头水平居中*/
      z-index: 10;
      position: absolute;
      right: 0;
      top: 50%;
      cursor: pointer;
      transform: translateY(-50%);  /*使按钮向上偏移居中对齐*/
    }

    .circle {
      display: flex;
      position: absolute;
      bottom: 10px;
      left: 50%;
      transform: translateX(-50%);
      justify-content: center;
      align-items: center;
      text-align: center;

      li {
        width: 10px;
        height: 10px;
        border-radius: 10px;
        border: 2px solid rgba(255,255,255,0.8);
        background: rgb(209,195,172,0.4);
        cursor: pointer;
        margin: 5px;
      }

      li:first-child {
        background-color: #2196F3;
      }
    }
  }
}

.project {
  height: 100%;
  padding: 10px 100px;
  justify-content: center;
  align-items: center;
  margin: 0 auto;

  .big-box {
    position: relative;
    display: grid;
    grid-template-columns: repeat(3, 1fr);
    gap: 30px;
    justify-content: space-between;
    width: 100%;
    max-width: 100%; /* 根据需要调整最大宽度 */
    margin-bottom: 40px; /* 盒子行间距 */
    padding-top: 30px;

    .box {
      overflow: hidden;
      position: relative;
      aspect-ratio: 16 / 9;
      transition: 0.5s;
      cursor: pointer;

      .img-box {
        position: absolute;
        left: 0;
        top: 0;
        width: 100%;
        height: 100%;

        img {
          position: absolute;
          left: 0;
          top: 0;
          width: 100%;
          height: 100%;
        }
      }

      .img-box::before {
        content: "";
        position: absolute;
        left: 0;
        top: 0;
        width: 100%;
        height: 100%;
        background: linear-gradient(to top, #222, transparent);
        z-index: 1;
        opacity: 0;
        transition: 0.5s;        
      }

      .text-box {
        position: absolute;
        left: 0;
        top: 0;
        width: 100%;
        height: 100%;
        padding: 20px;
        /* 弹性布局 元素位于容器尾部 */
        display: flex;
        align-items: flex-end;
        color: #A3CBB2;
        z-index: 2;

        h2 {
          position: absolute;
          bottom: 0;
          left: 20;
          font-size: 20px;
          transition: 0.3s;
        }
      }
    }

    .box:hover {
      transform: scale(1.25);                         /*图片放大*/
      box-shadow: 0 25px 40px rgba(0, 0, 0, 0.5);   /*点击图片有阴影*/
      z-index: 1;

      .img-box::before {
        opacity: 1;
      }

      .text-box h2 {
        bottom: 60px;
        transition-delay: 0.3s;
      }
    }
  }
}

</style>
