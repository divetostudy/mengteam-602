<script setup>
import { studentTeamDetail } from '@/api/lab'
import { useRoute } from 'vue-router'
import { onMounted, ref } from 'vue'

const route = useRoute()
const name = String(route.query.name)
const studentList = ref([])

const getStudentList = async () => {
  const res = await studentTeamDetail(name)
  studentList.value = res.data.data
  console.log(studentList.value)
}
onMounted(() => {
  getStudentList()
})

</script>

<template>
  <div class="main" v-if="studentList.length">
    <div class="intro-all">
      <div class="intro-top">
        <img :src=studentList[0].photoUrl alt="">
        <ul class="intro-right">
          <li>个人信息</li>
          <li><i class="iconfont icon-xingming">&nbsp;&nbsp;</i>姓名：{{studentList[0].name}}</li>
          <li><i class="iconfont icon-xingbie"></i>&nbsp;&nbsp;性别：{{studentList[0].gender}}</li>
          <li><i class="iconfont icon-zhuanye"></i>&nbsp;&nbsp;专业：{{studentList[0].major}}</li>
          <li><i class="iconfont icon-ic_geyan"></i>&nbsp;&nbsp;毕业年份：{{studentList[0].graduationYear}}</li>
          <li><i class="iconfont icon-jisuanqi"></i>&nbsp;&nbsp;联系方式(电话)：{{studentList[0].contact}}</li>
          <li><i class="iconfont icon-boshimao-F"></i>&nbsp;&nbsp;人生格言：{{studentList[0].lifeMotto}}</li>
        </ul>
      </div>
      <div class="intro-detail">
        <li>个人简介</li>
        <div class="intro-text">
          <i style="color: #2B5A41; font-style: normal; font-weight: 600;">工作经历：</i>
          <p style="text-indent: 2rem; margin-top: 0;">
            {{studentList[0].workExperience}}
          </p>
        </div>
        <div class="intro-text">
          <i style="color: #2B5A41; font-style: normal; font-weight: 600;">在校获奖情况：</i>
          <p style="text-indent: 2rem; margin-top: 0;">
            {{studentList[0].awardAtschool}}
          </p>
        </div>
        <div class="intro-text">
          <i style="color: #2B5A41; font-style: normal; font-weight: 600;">想对师弟师妹说的话：</i>
          <p style="text-indent: 2rem; margin-top: 0;">
            {{studentList[0].say}}
          </p>
        </div>
      </div>
  </div>
</div>

</template>

<style scoped lang="scss">
.main {
  background: url('../../assets/bg16.jpg');
  background-size: cover;
  background-attachment: fixed;
  width: 100%;
  // height: 100%;
  position: absolute;

  .intro-all {
    width: 830px;
    margin: 50px auto;
    background-color: rgba(255, 255, 255, 0.5);
    border-radius: 20px;

    .intro-top {
      display: flex;
      align-items: center;
      padding-bottom: 50px;
      padding-top: 30px;

      img {
        width: 250px;
        height: 250px;
        object-fit: cover;
        margin-top: 50px;
        margin-left: 40px;
      }

      .intro-right{
        font-size: 22px;
        margin-left: 30px;

        .iconfont {
          color: #7B9F80;
          font-size: 22px;
        }

        li {
          height: 45px;
          width: 450px;
          border-top: none;
          line-height: 35px;
          padding-left: 20px;
        }

        li:first-child {
          background-color: #4A7A5E;
          text-align: center;
          color: #ddf0d7;
          border-radius: 10px;  
        }
      }
    }
    
    .intro-detail {
      margin-top: 15px;
      width: 767px;
      margin-left: 25px;
      padding-bottom: 15px;
      font-size: 25px;
      font-style: normal;

      li:first-child {
        background-color: #2B5A41;
        text-align: center;
        color: #e6fadf;
        height: 50px;
        line-height: 50px;
        border-radius: 10px;
      }

      .intro-text{
        padding-top: 10px;
        line-height: 40px;

        p {
          padding-bottom: 25px;
        }
      }      
    }
  }
}
</style>
