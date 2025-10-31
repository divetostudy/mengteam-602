<script setup>
import { teacherDetail, studentTeam, envPic } from '@/api/lab'
import { ref, onMounted } from 'vue'
// import router from '@/router'
// import ImgOne from '@/assets/environment04.jpg'
// import ImgTwo from '@/assets/environment07.jpg'
// import ImgThree from '@/assets/environment03.jpg'
// import ImgFour from '@/assets/environment02.jpg'
// import ImgFive from '@/assets/environment01.jpg'
// import ImgSix from '@/assets/environment06.jpg'

const teacherList = ref([])
const studentList = ref([])
const getTeacherList = async () => {
  const res = await teacherDetail()
  teacherList.value = res.data.data
  // console.log(teacherList.value)
}


const getStudentList = async () => {
  const res = await studentTeam()
  studentList.value = res.data.data
  // console.log(studentList.value)
}

const environmentList = ref([])

const getEnvPic = async () => {
  const res = await envPic()
  console.log(res.data.data)
  environmentList.value = res.data.data
}
// getStudentList()

// const environmentList = [
//   { id: 1, picture: ImgOne },
//   { id: 2, picture: ImgTwo },
//   { id: 3, picture: ImgThree },
//   { id: 4, picture: ImgFour },
//   { id: 5, picture: ImgFive },
//   { id: 6, picture: ImgSix }
// ]

const currentYear = new Date().getFullYear()
const years = []
for(let i = 0; i < 20; i++) {
  years.push(currentYear + 1 - i + '年')
}

const gradeRef = ref(null)

const clickPrev = () => {
  if(gradeRef.value) {
    gradeRef.value.scrollLeft -= 400
  }
}

const clickNext = () => {
  if(gradeRef.value) {
    gradeRef.value.scrollLeft += 400
  }
  // console.log(111)
}

const highlightedYear = ref(years[0])
const selectedYearStudents = ref([])
const highlightYear = (selectedYear) => {
  highlightedYear.value = selectedYear
  selectedYearStudents.value = studentList.value.filter(student => {
    // console.log(student.graduationYear)
    return student.graduationYear === selectedYear
  })
}

// const clickStudent = (item) => {
//   // localStorage.setItem('scrollPosition', window.scrollY)
//   router.push(`/studentdetail?name=${item.name}`)
// }


onMounted(async () => {
  getEnvPic()
  getTeacherList()
  await getStudentList()
  if (studentList.value.length > 0 && years.length > 0) {
    console.log(studentList.value);
    selectedYearStudents.value = studentList.value.filter(student => student.graduationYear === years[0]);
  } else {
    console.error('No student data available or years not generated.');
    // 处理没有数据的情况，可以设置一个默认状态或显示错误提示
  }
})

</script>

<template>

<top-btn></top-btn>
  <!-- 实验室概况 -->
  <div class="introduce">
    <div class="left">
      <div class="left-top">实验室概况
        <span class="text2">LAB OVERVIEW</span>
      </div>
      <div class="text">
      “大数据与信息处理实验室”所涉及的技术门类主要包括数据挖掘、软件工程、和物联网系统开发等，致力于为企事业单位提供相关领域的个性化产品定制开发，目前已完成各类产品和系统三十余项，完成横向经费400余万元。此外，实验室承担市级以上科研项目(含横向项目)二十余项，其中重大项目1项;出版科研专著1部，发表学术论文20余篇，其中被SCI/EI检索论文10篇;获中山市科技进步奖2项。
      </div>
    </div>
    <div class="right">
      <img src="../../assets/environment05.jpg" alt="">
    </div>
  </div>

  <!-- 核心团队成员 -->
  <div class="teacher">
    <div class="left-top">团队核心成员
      <span class="text2">LAB MAIN MEMBER</span>
    </div>
    <div class="tech-box">
      <div class="person" v-for="item in teacherList" :key="item.id" :style="{'--color': item.avatarColor, background: item.bgColor}">
        <div class="person-avatar">
          <div class="container-inner">
            <div class="circle"></div>
            <img :src=item.photoUrl alt="">
          </div>
        </div>
        <div class="divider"></div>
        <h1>{{item.name}}</h1>
        <h3>{{item.job}}</h3>
        <span>工作经历</span>
        <p>{{item.introduction}}</p>
        <span>荣誉</span>
        <p>{{item.honor}}</p>
        <span>专业方向</span>
        <p>{{item.direction}}</p>
      </div>
    </div>
  </div>

  <!-- 学生团队 -->
  <div class="student-team">
    <div class="left-top">团队学生成员
      <span class="text2">LAB MAIN MEMBER</span>
    </div>
  </div>

  <!-- 测试 -->
  <!-- 年份测试 -->
  <div class="container">
    <div class="grade" ref="gradeRef">
      <li 
      class="year-item" 
      v-for="year in years" 
      :key="year"
      :class="{ active: highlightedYear === year }"
      @click="highlightYear(year)"
      >
        {{ year }}
      </li>
    </div>
    <i class="iconfont btn-left icon-zuojiantou_huaban" @click="clickPrev"></i>
    <i class="iconfont btn-right icon-youjiantou_huaban" @click="clickNext"></i>   
  </div>

  <div class="student-item">
    <router-link 
      class="item-box" 
      v-for="item in selectedYearStudents" 
      :key="item.id" 
      :to="`/studentdetail?name=${item.name}`"
      target="_blank"
    >
      <img :src=item.photoUrl alt="">
      <p>{{ item.name }}</p>
    </router-link>    
  </div> 

<div>

</div>

  <!-- 实验室环境 -->
  <div class="lab-environment">
    <div class="left-top">实验室环境
      <span class="text2">LAB ENVIRONMENT</span>
    </div>
    <div class="picList">
      <div class="pic" v-for="item in environmentList" :key="item.id">
        <img :src=item.photoUrl alt="">
      </div>
    </div>
  </div>

<!-- 联系我们 -->
  <!-- <div class="lab-contact">
    <div class="left-top">联系我们
      <span class="text2">CONTACT US</span>
    </div>

    <div class="address">
      <div class="contact">
        <div class="name">大数据与信息处理实验室</div>
        <div class="text">
          <i class="iconfont icon-jisuanqi" style="margin-right: 10px; font-size: 22px;"></i>电话: 111111111111
        </div>
        <div class="text">
          <i class="iconfont icon-youxiang" style="margin-right: 10px; font-size: 26px;"></i>邮箱: 11111111@qq.com
        </div>
        <div class="text">
          <i class="iconfont icon-dizhi" style="margin-right: 10px; font-size: 22px;"></i>地址: 中山市石岐区电子科技大学中山学院厚德楼A602
        </div>
        <div class="text">
          <i class="iconfont icon-youbian" style="margin-right: 10px; font-size: 22px;"></i>邮编: 11111
        </div>
      </div>
      <div class="adrs-pic">
        <img src="../../assets/shiyanshidizhi.jpg" alt="">
      </div>
    </div>
  </div> -->

  <!-- <div class="btn">
    <div class="btn-box">
      <i class="iconfont icon-fanhuidingbu"></i>
      <p class="back">Top</p>
    </div>
  </div> -->

</template>

<style scoped lang="scss">
.introduce {
  display: flex;
  margin-top: 50px;
  width: 100%;
  height: 500px;

  .left {
    width: 50%;
    height: 100%;

    .left-top {
      line-height: 99px;
      font-size: 36px;
      color: #1a1a1a;
      background: url(../../assets/aboutbg.png) right center no-repeat;
      width: 400px;
      margin-bottom: 30px;
      margin-left: 88px;
      margin-top: 46px;

      .text2 {
        font-size: 14px;
        height: 40px;
        padding-left: 10px;
      }
    }

    .text {
      margin-left: 88px;
      line-height: 35px;
      text-indent: 2em;
      font-size: 19px;
    }
  }

  .right{
    width: 50%;
    height: 100%;

    img {
      margin-left: 50px;
      width: 80%;
      height: 100%;
    }
  }
}

.teacher {
  width: 100%;

  .left-top {
    line-height: 99px;
    font-size: 36px;
    color: #1a1a1a;
    background: url(../../assets/aboutbg.png) right center no-repeat;
    width: 400px;
    margin-bottom: 30px;
    margin-left: 88px;
    margin-top: 46px;
    
    .text2 {
      font-size: 14px;
      height: 40px;
      padding-left: 10px;
    }
  }

  .tech-box {
    display: flex;
    flex-wrap: wrap;
    align-items: center;
    justify-content: center;
    margin: 0;

    .person {
      border-radius: 20px;
      display: flex;
      flex-direction: column;
      align-items: center;
      width: 300px;
      margin-left: 50px;
      padding: 0 30px;
      padding-bottom: 30px;

      .person-avatar{
        height: 312px;
        width: 400px;
        cursor: pointer;
        transform: scale(0.48);
        transition: transform 250ms cubic-bezier(0.4, 0, 0.2, 1);

        .container-inner {
          position: relative;
          clip-path: path("M 390,400 C 390,504.9341 304.9341,590 200,590 95.065898,590 10,504.9341 10,400 V 10 H 200 390 Z");
          top: -200px;

          .circle {
            position: absolute;
            background: var(--color);
            height: 380px;
            width: 380px;
            top: 210px;
            left: 10px;
            border-radius: 50%;
          }

          img {
            position: relative;
            width: 290px;
            top: 157px;
            left: 62px;
            transform: translateY(20px) scale(1.15);
            transition: transform 250ms cubic-bezier(0.4, 0, 0.2, 1);
          }
        }
      }
    }

    .divider {
      height: 3px;
      width: 130px;
      border-radius: 5px;
      background: var(--color);
    }

    h1 {
      color: #464646;
      margin: 16px 0 0 0;
    }

    h3 {
      color: #6e6e6e;
      margin: 10px;
      font-size: 22px;
    }

    span {
      font-size: 20px;
      color: #575757;
      padding-top: 15px;
    }

    p {
      font-family: arial;
      color: #808080;
      margin-top: 5px;
      text-indent: 2em;
      line-height: 30px;
      font-size: 18px;
      padding-top: 5px;
    }
  }
}

.student-team {

  .left-top {
    line-height: 99px;
    font-size: 36px;
    color: #1a1a1a;
    background: url(../../assets/aboutbg.png) right center no-repeat;
    width: 400px;
    margin-bottom: 30px;
    margin-left: 88px;
    margin-top: 46px;
    
    .text2 {
      font-size: 14px;
      height: 40px;
      padding-left: 10px;
    }
  }
}

.lab-environment {

  .left-top {
    line-height: 99px;
    font-size: 36px;
    color: #1a1a1a;
    background: url(../../assets/aboutbg.png) right center no-repeat;
    width: 400px;
    margin-bottom: 30px;
    margin-left: 88px;
    margin-top: 46px;
    
    .text2 {
      font-size: 14px;
      height: 40px;
      padding-left: 10px;
    }
  }

  .picList {
    display: grid;
    grid-template-columns: repeat(3, 1fr);
    gap: 10px;
    margin: 0 auto;
    width: 1200px;
    height: 550px;

    .pic {

      img {
        margin-bottom: 20px;
        object-fit: contain;
        height: 240px;
        aspect-ratio: 4 / 3;
      }
    }
  }
}

// .lab-contact {

//   .left-top {
//     line-height: 99px;
//     font-size: 36px;
//     color: #1a1a1a;
//     background: url(../../assets/aboutbg.png) right center no-repeat;
//     width: 400px;
//     margin-bottom: 30px;
//     margin-left: 88px;
//     margin-top: 46px;
    
//     .text2 {
//       font-size: 14px;
//       height: 40px;
//       padding-left: 10px;
//     }
//   }

//   .address {
//     display: flex;
//     // border: 1px solid red;
//     width: 1300px;
//     height: 100%;
//     justify-content: space-between;
//     margin: 0 auto;
//     // width: 100%;
//     // margin-left: 2%;
//     // margin-right: 2%;
//     .contact {
//       padding-left: 39px;
//       padding-top: 102px;
//       // margin-left: 100px;
//       width: 520px;
//       height: 420px;
//       background-color: rgb(84, 162, 214);
//       color: #fff;

//       .name {
//         font-size: 32px;
//         margin-bottom: 35px;
//       }

//       .text {
//         margin-top: 38px;
//         font-size: 20px;

//         .iconfont {
//           font-size: 25px;
//         }
//       }
//     }

//     .adrs-pic {
//       // margin-left: 45px;
//       // flex: 1;

//       img {
//         width: 700px;
//         height: 700px;
//       }
//     }
//   }
// }

.student-item {

  height: 100%;
  display: flex;
  flex-wrap: wrap;
  // margin: 0 150px;
  margin-left: 10%;
  margin-right: 10%;
  
  .item-box {
    width: 200px; 
    height: 200px;
    padding: 20px;
    // border: 1px solid blue;
    color: #1a1a1a;

    img {
      width: 180px;
      height: 180px;
      border-radius: 50%;
      object-fit: contain;
    }
    
    p {
      text-align: center;
      font-size: 20px;
      margin-top: 10px;
    }
  }
}

.container {
  position: relative;
  height: 100px;
  margin: 0 auto;
  display: flex;

  ::-webkit-scrollbar {
    display: none;
  }

  .grade {
    display: flex;
    justify-content: space-between;
    overflow: scroll;
    scroll-behavior: smooth;
    white-space: nowrap;
    margin: 16px 120px;

    .year-item {
      font-size: 30px;
      padding: 0 20px;
      display: flex;
      align-items: center;
      cursor: pointer;
      width: 220px;

      &.active {
        background-color: #76B6EA;
        color:#F3F9F9;
        border-radius: 15px;
      }
    }
  }
  
  .iconfont {
    font-size: 50px;
  }

  .btn-left {
    position: absolute;
    left: 85px;
    top: 26%;
    justify-content: center;
    align-items: center;
    transform: translateX(-50%);
    display: flex;
    cursor: pointer;
    color: #457B9D;
  }

  .btn-right {
    position: absolute;
    right: 85px;
    top: 28%;
    justify-content: center;
    align-items: center;
    transform: translateX(50%);
    display: flex;
    block-size: 45px;
    aspect-ratio: 1;
    border-radius: 30px;
    cursor: pointer;
    color: #457B9D;
  }
}



</style>
