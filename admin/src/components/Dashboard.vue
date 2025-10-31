<template>
  <div class="dashboard" v-if="role === 'admin'">
    <h2>欢迎来到管理员后台</h2>
    <p>这是实验室官网管理后台，您可以在这里管理核心成员和学生成员。</p>
  </div>
  <div class="dashboard" v-if="role === 'user'">
    <h2>欢迎来到学生信息填报后台台</h2>
    <p>这是实验室官网后台，您可以在这里填写和查看填报的个人信息。</p>
  </div>
</template>

<script>
export default {
  name: 'AdminDashboard',
  data() {
    return {
      role: localStorage.getItem("role") || "user", // 获取当前用户的角色
    };
  }
};
</script>

<style scoped>
.dashboard {
  text-align: center;
  margin-top: 20%;
}
</style>
