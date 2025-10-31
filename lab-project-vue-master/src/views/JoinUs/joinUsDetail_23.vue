<script setup>

</script>

<template>

  <div class="big-box">
    <div class="box">
      <div class="header">
        <img src="../../assets/join_us/title_logo.png" alt="">
        <div class="code">
            <img src="../../assets/join_us/QR_code.jpg" alt="">
            <p>
              招新咨询群
            </p>
          </div>
      </div>
      <div class="content-top">
        <div class="title">
          招新岗位
        </div>
        <p>前端工程师</p>
        <p>后端工程师</p>
        <p>服务器管理员</p>
        <div class="title">
          招新时间
        </div>
        <p>2023.09.22</p>
        <p>周五中午12：30</p>
        <div class="title">
          会议地点
        </div>
        <p>厚德楼A602</p>
      </div>
      <div class="content-center">
        <div class="pic">
          <img src="../../assets/join_us/title_bg.png" alt="">
          <div class="title">
            前端工程师
          </div>
          <img class="people-img" src="../../assets/join_us/people02.png" alt="">
        </div>
        <div class="text">
          <p>
            在浩瀚的代码世界中舞动，前端工程师是数字艺术家<br>
            让抽象变成多彩，用数字创造绚丽<br>
            创造视觉盛宴，赋予代码生命力
          </p>
          <p>
            在孟之队，学习开发网站、手机app、<br>
            微信小程序，成为一名优秀的前端工程师
          </p>
        </div>

        <div class="pic">
          <img src="../../assets/join_us/title_bg.png" alt="">
          <div class="title">
            后端工程师
          </div>
          <img class="people-img" src="../../assets/join_us/people01.png" alt="">
        </div>
        <div class="text-box">
          <div class="text">
            <p>
              数据库技艺， 数据存储稳如山<br>
              我们探索底层，解决难题<br>
              精准算法，驱动业务创新<br>
              代码优化，提升性能领先
            </p>
            <p>
              在孟之队，化身数据构造者<br>
              成为一名优秀的后端工程师
            </p>
          </div>
          
        </div>        
        <p class="speak">
          拒绝躺平，不断提升自己！快来吧，融入孟之队这个大家庭，大家一起学习，一起做项目，一起团建玩耍，我们收获的永不止技术。
        </p>
        <p class="speak">
          只要你有一颗坚定的理想信念，那么从现在开始加入我们，开启未来的高薪之旅吧！
        </p>       
      </div>

    </div>
  </div>

</template>

<style scoped lang="scss">
* {
  padding: 0;
  margin: 0;
  list-style: none;
  text-decoration: none;
}

.big-box {
  border: 1px solid rgb(255, 255, 255, 0);
  background: url('../../assets/joinUs/bg13.jpg');
  background-repeat: no-repeat;
  background-attachment: fixed;
  background-size: cover;

  .box {
    border: 1px solid #E8D0A9;
    border-radius: 15px;
    width: 600px;
    margin: 40px auto;
    padding: 30px 0;
    background-color: #FFF2CC;
    box-shadow: 10px 10px 10px #F7DC6F;

    .header {
      height: 180px;
      width: 600px;
      display: flex;
      img {
        width: 100%;
        height: 100%;
        object-fit: contain;
      }

      .code {
          display: flex;
          flex-direction: column;
          justify-content: center;
          align-items: center;
          padding-right: 30px;
          // padding: 0 30px;

          img {
            width: 100px;
            height: 100px;
          }

          p {
            font-size: 20px;
            padding-top: 10px;
            font-family: 'KaiTi';
            color: #FF9F1C;
          }
        }

    }
    .content-top {
      margin-top: 15px;
      display: flex;
      flex-direction: column;
      align-items: center;

      .title {
        color: #fff;
        border-radius: 10px;
        background-color: #FFAB00;
        margin-bottom: 15px;
        font-size: 22px;
        width: 120px;
        height: 40px;
        border: 1px solid #F5CB5C;
        line-height: 40px;
        text-align: center;
        
      }

      p {
        font-size: 20px;
        margin-bottom: 10px;
        color: #FF9F1C;
      }
    }
    .content-center {
      .pic {
        margin-top: 30px;
        margin-left: 50px;
        position: relative;
        width: 155px;
        height: 55px;

        img {
          width: 100%;
          height: 100%;
          object-fit: cover;
        }

        .title {
          position: absolute;
          top: 0;
          left: 0;
          text-align: center;
          line-height: 50px;
          width: 150px;
          height: 50px;
          font-size: 22px;
          color: #fff;
          font-style: italic;
        }

        .people-img {
          position: absolute;
          top: -28px;
          width: 100px;
          height: 100px;
          margin-left: 10px;
        }
      }
      .text {
        padding: 0 50px;
        p {
          font-size: 18px;
          color: #FF9F1C;
          padding: 20px 0;
          line-height: 1.6em;
        }
      }
      .text-box {
        display: flex;
        .text {
          padding: 0 50px;
          p {

          }
        }
        
      }
      .speak {
        padding: 10px 50px;
        text-indent: 2em;
        font-size: 20px;
        font-family: 'Courier New', Courier, monospace;
        color: #FF9F1C;
        font-weight: bold;
      }
    }
  }
}

</style>