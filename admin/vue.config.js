const { defineConfig } = require('@vue/cli-service')


module.exports = {
  transpileDependencies: true,
  publicPath: './',  // 改为相对路径
  devServer: {
    client: {
      overlay: false // 编译错误时，取消全屏覆盖（建议关掉）
    },
    host: '192.168.203.245', // 使用你的实际IP
    port: 8080,
    allowedHosts: ['all']   // 允许所有主机访问
  }
}