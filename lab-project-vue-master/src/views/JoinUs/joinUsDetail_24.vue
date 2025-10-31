<script setup>

</script>

<template>
  <div class="big-box">
    <div class="box">
      <div class="logo">
        <img src="../../assets/join_us/team_name.png" alt="">
      </div>
      <div class="content">
        <div class="lab">孟之队大数据与信息处理实验室</div>
        <p class="zxdx">招新对象:&ensp;&ensp;本次招新面向电信学院与计算机学院大一学生</p>
        <div class="ask">
          <div class="ask-bottom">
            <div class="code">
              <img src="../../assets/join_us/QR_code.jpg" alt="">
            </div>
            <div class="text-box">
              <div class="contact">
                <div class="text-title">
                  联系方式
                </div>
                <span>
                  左侧扫码入群咨询
                </span>
              </div>
              <div class="contact">
                <div class="text-title">
                  招新方式
                </div>
                <span>
                  笔试与面试
                </span>
              </div>
            </div>
          </div>
        </div>
        <div class="title">
          <img src="../../assets/join_us/qianduan.png" alt="">
        </div>
        <span>学习网站、app、小程序等前端开发，配合团队完成项目，成为一位优秀的前端工程师</span>
        <div class="title">
          <img src="../../assets/join_us/houduan.png" alt="">
        </div>
        <span>系统学习数据库技术、框架搭建、架构设计等后端开发核心领域，配合团队完成项目，成为一名卓越的后端工程师</span>
        <div class="title">
          <img src="../../assets/join_us/xitong.png" alt="">
        </div>
        <span>熟练掌握服务器管理与维护，项目搭建部署</span>
        <div class="icon-demand">
          <i class="iconfont icon-caifang-yaoqiu"></i>
          <span class="text">要求</span>
        </div>
        <div class="demand">
          <li>
            <i class="iconfont icon-xingxing"></i>
            <span>一定编程基础</span>
          </li>
          <li>
            <i class="iconfont icon-xingxing"></i>
            <span>爱学习 有礼貌 负责任</span>
          </li>
          <li>
            <i class="iconfont icon-xingxing"></i>
            <span>对软件开发有兴趣</span>
          </li>
          <li>
            <i class="iconfont icon-xingxing"></i>
            <span>课余与假期进行学习与项目开发</span>
          </li>
        </div>
        <div class="talk">
          拒绝躺平，不断提升自己！快来吧，融入孟之队这个大家庭，大家一起学习，一起做项目，一起团建玩耍，我们收获的永不止技术。
        </div>
        <div class="talk">
          只要你有一颗坚定的理想信念，那么从现在开始加入我们，开启未来的高薪之旅吧！
        </div>
      </div>
    </div>
  </div>

</template>

<style scoped lang="scss">

* {
  list-style: none;
  padding: 0;
  margin: 0;
}

.big-box {
  border: 1px solid rgba(255, 255, 255, 0);
  background: url('../../assets/joinUs/bg06.jpg');
  background-repeat: no-repeat;
  background-attachment: fixed;
  background-size: cover;

  .box {
    width: 600px;
    margin: 20px auto;
    border: 1px solid #008080;
    border-radius: 10px;
    background-color: #eff4f8;
    box-shadow: 0 0 10px #008080;

    .logo {
      margin-top: 30px;
      height: 50px;
      display: flex;
      justify-content: center;
      align-items: center;
    
      img {
        height: 100%;
        object-fit: contain;
      }
    }
    .content {
      padding: 0 40px;

      .lab {
        width: 100%;
        text-align: center;
        font-size: 26px;
        font-style: italic; 
        font-weight: 600;
        margin: 10px 0;
        color: #1ABC9C;
      }

      .zxdx {
        color: #009B77;
        font-size: 20px;
        margin-bottom: 40px;
      }
      
      .ask {
        position: relative;
        border: 5px solid #A7C4B5;
        height: 180px;
        width: 450px;
        margin-bottom: 60px;
        border-radius: 15px;
        background-color: #eff4f8;
        margin-left: 35px;
        background-color: #D6EADF;

        .ask-bottom {
          position: absolute;
          display: flex;
          align-items: center;
          top: 15px;
          left: 15px;
          border: 5px solid #A7C4B5;
          height: 180px;
          width: 450px;
          border-radius: 15px;
          background-color: #eff4f8;

          .code {
            width: 100px;
            height: 100px;
            margin: 0 40px;

            img {
              width: 100%;
              height: 100%;
            }
          }

          .text-box {
            height: 100px;
            display: flex;
            flex-direction: column;
            justify-content: space-around;

            .contact {
              height: 30px;
              display: flex;

              .text-title {
                line-height: 30px;
                text-align: center;
                border: 1px solid;
                background-color: #27AE60;
                color: #efefef;
                border-radius: 15px;
                height: 30px;
                width: 100px;
              }

              span {
                line-height: 30px;
                padding-left: 10px;
                font-family: 'KaiTi';
                font-size: 18px;
              }
            }
          }
        }
      }

      .title {
        margin: 10px 0;
        height: 40px;
        width: 140px;
        border: 1px solid green;
        font-size: 20px;
        background-color: #009B77;
        border-radius: 10px;

        img {
          width: 120px;
          transform: translateY(50%);
          padding-left: 10px;
        }
      }

      span {
        font-size: 18px;
        line-height: 1.8em;
        color: #27AE60;
      }

      .icon-demand {
        margin-top: 10px;
        height: 50px;
        position: relative;

        .icon-caifang-yaoqiu {
          font-size: 50px;
          color: #1ABC9C;
        }

        .text {
          color: #1ABC9C;
          position: absolute;
          top: 50%;
          transform: translateY(-50%);
          font-size: 22px;
          font-weight: bold;
        }
      }

      .demand {
        padding-top: 10px;

        li {
          position: relative;
          height: 40px;
        }

        .icon-xingxing {
          font-size: 26px;
          color: #FFD700;
          line-height: 40px;
          padding-right: 10px;
        }

        span {
          font-size: 18px;
          position: absolute;
          top: 50%;
          transform: translateY(-50%);
        }
      }
      
      .talk {
        padding-top: 10px;
        font-size: 18px;
        text-indent: 2em;
        line-height: 1.6em;
        padding-bottom: 20px;
        color: #27AE60;
      }
    }
  }
}

</style>