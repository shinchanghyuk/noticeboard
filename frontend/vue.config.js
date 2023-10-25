module.exports = {

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