<template>
  <div id="layoutAuthentication" class="mt-5"> 
    <div id="layoutAuthentication_content">
      <main>
        <div class="container">
          <div class="row justify-content-center">
            <div class="col-lg-7">
              <div class="card shadow-lg border-0 rounded-lg mt-5">
                <div class="card-header"><h3 class="text-center font-weight-light my-4">아이디 생성</h3></div>
                <div class="card-body">
                  <form>
                    <div class="form-floating mb-3">
                        <input class="form-control" v-model="useridInput" id="useridInput" type="text" placeholder="사용자 아이디" />
                        <label for="useridInput">사용자 아이디</label>
                    </div>
                    <div class="row mb-3">
                      <div class="col-md-6">
                        <div class="form-floating mb-3 mb-md-0">
                          <input class="form-control" v-model="passwordInput" id="passwordInput" type="password" placeholder="패스워드" />
                          <label for="passwordInput">패스워드</label>
                        </div>
                      </div>
                      <div class="col-md-6">
                        <div class="form-floating mb-3 mb-md-0">
                          <input class="form-control" v-model="passwordConfirmInput" id="passwordConfirmInput" type="password" placeholder="확인 패스워드" />
                          <label for="passwordConfirmInput">확인 패스워드</label>
                        </div>
                      </div>
                    </div>
                    <div class="mt-4 mb-0">
                      <div class="d-grid"><a class="btn btn-primary btn-block" href="#" @click="create">아이디 생성</a></div>
                    </div>
                  </form>
                </div>
                <div class="card-footer text-center py-3">
                  <div class="small"><a href="#" @click="loginPageMove">아이디를 가지고 계신가요? 로그인 페이지로 이동</a></div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </main>
    </div>
  </div>
 </template>

<script>
import axios from 'axios';

export default {
  components: {

  },
  data() {
    return {
      useridInput:'',
      passwordInput: '',
      passwordConfirmInput:'',

      apiBaseUrl: process.env.VUE_APP_API_URL // 환경 변수에서 정의한 URL 사용
    };
  },
  computed: {},
  methods: {
    create() {
      console.log("create START");

      if(!this.useridInput || !this.passwordInput || !this.passwordConfirmInput) {
        alert("아이디/비밀번호 입력을 다시 확인해주세요.")
        return;
      }

      if(this.passwordInput !== this.passwordConfirmInput) {
        alert("입력한 비밀번호가 다릅니다. 다시 확인해주세요.")
        return;
      }

      axios({
        url: this.apiBaseUrl + "noticeboard/create/",
        method: "POST",
        data: {
          userid: this.useridInput,
          password: this.passwordInput, 
        }
        }).then(res => {
          // alert(res.data.message);
          if(res.status === 200) {
            alert("아이디 생성에 성공하였습니다.");
            this.$router.push('/noticeboard'); // 로그인 페이지로 이동
          } else {
            alert("아이디 생성에 실패하였습니다.");
          }
        }).catch(err => {
          console.log("BoardRegister - Exception : " + err);
          alert(err);
        });
      },
      loginPageMove() {
        console.log("loginPageMove START");
        this.$router.push('/noticeboard'); // 로그인 페이지로 이동
      },
  }
};
</script>

<style src="../assets/css/NoticeBoardStyles.css" scoped></style>
