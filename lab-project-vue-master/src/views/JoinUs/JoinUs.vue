<script setup>

</script>

<template>

<top-btn></top-btn>
<div class="join-us">
  <div class="center">
    <div class="zhaoxin-box">
      <ul class="box">
        <li class="title">
          招新
        </li>
        <li class="note">
          <div class="content" >
            <img src="../../assets/icon/laba.png" alt="">
            <router-link to="/zhaoxin2024" 
            target="_blank"
            class="join"
            >
              孟之队24年夏季招新
            </router-link>
          </div>
        </li>
        <li class="note">
          <div class="content">
            <img src="../../assets/icon/laba.png" alt="">
            <router-link 
              to="/zhaoxin2023" 
              target="_blank"
              class="join"
            >
              孟之队23年秋季招新
            </router-link>
          </div>
        </li>
      </ul>
    </div>
  </div>
</div>



</template>

<style scoped lang="scss">
.join-us {
  .center {
    .zhaoxin-box{
      border: 1px solid;
      border-radius: 15px;
      height: 300px;
      width: 460px;
      margin-left: 60px;
      margin-top: 30px;

      .box {
        .title {
          font-size: 26px;
          border-radius: 15px 15px 0 0;
          padding-left: 2em;
          height: 50px;
          line-height: 50px;
          background-color: #006064;
          color: #fff;
        }

        .note {
          .content {
            padding-left: 2em;
            display: flex;
            height: 60px;
            border-bottom: 1px dashed #9FA8A3;

            img {
              width: 40px;
              height: 40px;
              padding-top: 10px;
            }

            .join {
              font-size: 22px;
              padding-left: 5px;
              line-height: 60px;
              color: #29506D;

              &:hover {
                color: #87CEFA;
              }
            }
          }
        }
      }
      
    }
  }
}
</style>
