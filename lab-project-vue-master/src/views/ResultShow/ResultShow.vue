<script setup>
import { ref, onMounted, onUnmounted } from 'vue'
import { resultShow, showSlider } from '@/api/lab'
// import bannerImgOne from '@/assets/project2.jpg'
// import bannerImgTwo from '@/assets/project3.jpg'
// import bannerImgThree from '@/assets/project4.jpg'
// import bannerImgFour from '@/assets/project5.jpg'
// import bannerImgFive from '@/assets/project6.jpg'
// import bannerImgSix from '@/assets/project7.jpg'

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

const bannerPic = ref([])

const getBannerPic = async () => {
  const res = await showSlider()
  console.log(res.data)
  bannerPic.value = res.data.data
}

const nextImage = () => {
  // currentImageIndex.value = (currentImageIndex.value + 1) % bannerPic.length
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
  // currentImageIndex.value = (currentImageIndex.value - 1 + bannerPic.length) % bannerPic.length
  imagesRef.value[currentImageIndex.value].style.opacity = 0
  circlesRef.value[currentImageIndex.value].style.backgroundColor = 'rgb(209,195,172,0.4)'
  currentImageIndex.value--
  if(currentImageIndex.value == -1) {
    currentImageIndex.value = bannerPic.value.length - 1
  }
  imagesRef.value[currentImageIndex.value].style.opacity = 1
  circlesRef.value[currentImageIndex.value].style.backgroundColor = '#6dbeec'
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

const resultShowList = ref([])
const getResultShowList = async () => {
  const res = await resultShow()
  console.log(res)
  resultShowList.value = res.data.data
  // console.log(resultShowList);
}

onMounted(() => {
  startAutoPlay()
  getResultShowList()
  getBannerPic()
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

  <!-- 成果展示 -->
<div class="title3">
  <div class="text1">成果展示</div>
  <div class="text2">ACHIEVEMENT DISPLAY</div>
</div>
<div class="box-cgzs" >
  <div class="cgzs" v-for="item in resultShowList" :key="item.id">
    <div class="title-cgzs">
      <div class="text-cgzs">{{ item.title }}</div>
      <div class="circular">0{{ item.id }}</div>
      <div class="triangular"></div>
    </div>
    <div class="text-detail">
      <div class="text">
        {{ item.content }}
      </div>
      <!-- <img :src=item.photos alt=""> -->
      <img v-for="p in item.photos" :key="p.photoUrl" :src=p.photoUrl alt="">
    </div>
  </div> 
</div>

</template>

<style scoped lang="scss">
.lunbo {
  margin: 0;
  padding: 0;
  position: relative;
  width: 100%;
  height: 600px;

  .content {
    width: 100%;
    height: 600px;
    margin: 0 auto;
    justify-content: center;
    align-items: center;
    cursor: pointer;

    .item {
      width: 100%;
	    height: 600px;
      
      li {
        position: absolute;
        height: 600px;
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

.title3 {
  margin: 50px auto;
  width: 200px;
  height: 100px;

  .text1 {
    font-size: 36px;
    text-align: center;
  }

  .text2 {
    padding-top: 10px;
    width: 207px;
    font-size: 18px;
    text-align: center;
    color: #999;
  }
}

.box-cgzs {
  display: flex;
  flex-wrap: wrap;
  // border: 1px solid red;
  width: 1400px;
  margin: 0 auto;
  // justify-content: center;

  .cgzs {
    width: 100%;
    margin-bottom: 70px;
    // padding-left: 88px;
    padding-top: 20px;

    .title-cgzs {
      position: relative;
      margin-left: 87px;
      margin-bottom: 25px;
      width: 490px;
      height: 60px;
      background-color: pink;
      background-image: linear-gradient(
        to right,
        #FF8C00,
        #ffcc6c
      );

      .text-cgzs {
        font-size: 25px;
        font-weight: 600;
        line-height: 60px;
        text-align: center;
      }

      .circular {
        position: absolute;
        left: -53px;
        top: 50%;
        transform: translateY(-50%);
        width: 70px;
        height: 70px;
        border-radius: 50%;
        background-color: #E0FFFF;
        font-size: 33px;
        font-weight: 600;
        line-height: 70px;
        text-align: center;
      }

      .triangular {
        position: absolute;
        top: 50%;
        left: 100%;
        transform: translateY(-50%);
        width: 0;
        height: 0;
        border-top: 30px solid transparent;
        border-left: 30px solid #ffcc6c;
        border-bottom: 30px solid transparent;
      }
    }

    .text-detail {
      display: flex;
      margin-left: 58px;
      text-indent: 2rem;

      .text {
        width: 400px;
        color: black;
        font-size: 18px;
        line-height: 35px;
      }
      
      img {
        margin-left: 30px;
        width: 415px;
        height: 219px;
        margin-bottom: 50px;
        object-fit: contain;
      }
    }
  }
}

</style>
