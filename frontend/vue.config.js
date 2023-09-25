module.exports = {
  // 개발 서버 설정
  devServer: {
    port: 8080, // 개발 서버 포트
    proxy: {
      '/noticeboard': {
        target: 'http://127.0.0.1:8080', // Spring Boot 서버 주소
        changeOrigin: true
      }
    }
  },

  // 빌드 설정
  productionSourceMap: false, // 소스 맵 비활성화
  outputDir: '../src/main/resources/static', // Spring Boot 프로젝트의 정적 리소스 폴더 경로

  // CSS 설정
  css: {
    loaderOptions: {
      // CSS에 추가적인 로더 옵션을 설정할 수 있습니다.
    },
  },
};