const { defineConfig } = require('@vue/cli-service')

module.exports = {
  transpileDependencies: true,
  publicPath: '/admin/',  // 使用绝对路径，很重要！
  devServer: {
    client: {
      overlay: false // 编译错误时，取消全屏覆盖（建议关掉）
    },
    host: 'localhost',
    port: 8080,  // 前端端口
    allowedHosts: ['all'],   // 允许所有主机访问
    historyApiFallback: true  // 支持Vue Router的history模式
  }
}