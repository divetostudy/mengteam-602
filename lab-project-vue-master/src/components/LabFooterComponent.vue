<script setup>
import { useRouter } from 'vue-router'
import { onMounted, ref } from 'vue'
import { contactMsg } from '@/api/lab'

let contactMsgList = ref([])

const getContactMsg = async () => {
  const res = await contactMsg()
  // console.log(res.data.data)
  contactMsgList.value = res.data.data
  console.log(contactMsgList.value)
}
const router = useRouter()

onMounted(() => {
  getContactMsg()
})
</script>

<template>
<div class="footer">
  <div class="menu">
    <li @click="router.push('/home')">首页</li>
    <li @click="router.push('/about')">关于我们</li>
    <li @click="router.push('/news')">新闻动态</li>
    <li @click="router.push('/result')">成果展示</li>
    <li @click="router.push('/join')">加入我们</li>
    <li @click="router.push('/activities')">团建活动</li>
    <li @click="router.push('/honor')">荣誉成就</li>
  </div>
  <div class="contact" v-if="contactMsgList.length">
    <div class="contact-left">
      <p class="title">联系我们</p>
      <div class="text">
        <i class="iconfont icon-jisuanqi" style="margin-right: 10px; font-size: 18px;"></i>
        <p>电话：{{ contactMsgList[0].phone }}</p>
      </div>
      <div class="text">
        <i class="iconfont icon-youxiang" style="margin-right: 10px; font-size: 18px;"></i>
        <p>邮箱：{{ contactMsgList[0].email }}</p>
      </div>
      <div class="text">
        <i class="iconfont icon-dizhi" style="margin-right: 10px; font-size: 18px;"></i>
        <p>地址：{{ contactMsgList[0].address }}</p>
      </div>
      <div class="text">
        <i class="iconfont icon-youbian" style="margin-right: 10px; font-size: 18px;"></i>
        <p>邮编：{{ contactMsgList[0].zipCode }}</p>
      </div>
    </div>
    <div class="contact-right">
      <img src="../assets/2学校校徽（反白）.png" alt="">
      <!-- <p>扫一扫关注公众号</p> -->
    </div>
  </div>
  <footer>
    版权所有 &copy; 电子科技大学中山学院大数据与信息技术处理实验室
  </footer>
</div>
</template>

<style scoped lang="scss">
.footer {
  margin-top: 50px;
  width: 100%;
  // height: 300px;
  // border: 1px solid red;
  // background-color: #032174;
  background: url('../assets/footbg.png');
  .menu {
    color: #cfcfcf;
    display: flex;
    justify-content: space-around;
    // border: 1px solid red;
    width: 1400px;
    margin: 0 auto;
    padding-top: 50px;
    font-size: 20px;

    li {
      &:hover {
        color: #8fc5f8;
        cursor: pointer;
      }
    }
  }

  .contact {
    color: #cfcfcf;
    display: flex;
    font-size: 20px;
    // border: 1px solid blue;
    justify-content: space-around;
    width: 1300px;
    margin: 0 auto;
    padding-top: 20px;
    .contact-left {
      // border: 1px solid green;

      .title {
        font-weight: 800;
        font-size: 28px;
      }

      p {
        margin-bottom: 10px;
      }

      .text {
        display: flex;
      }
    }

    .contact-right {
      padding-top: 20px;
      img {
        width: 150px;
        height: 150px;
        opacity: 0.85;
      }
    }
  }

  footer {
    color: #cfcfcf;
    text-align: center;
    font-size: 20px;
    padding: 1rem;
  }
}
</style>