module.exports = {

  // 빌드 설정하여, 정적파일 경로세팅
  publicPath: process.env.VUE_APP_TYPE === 'production'
    ? '/noticeboard/'
    : '/',  
  // publicPath: 'noticeboard',

  productionSourceMap: false, // 소스 맵 비활성화
  outputDir: '../src/main/resources/static', // Spring Boot 프로젝트의 정적 리소스 폴더 경로
};