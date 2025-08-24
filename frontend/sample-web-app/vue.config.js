const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  devServer: {
    proxy: {
      '^/api': {
        // Proxy API requests to the backend service running on port 80
        target: 'http://localhost:80',
        changeOrigin: true,
        logLevel: 'debug',
        pathRewrite: { '^/api': '/api' },
      },
    },
  },
})
