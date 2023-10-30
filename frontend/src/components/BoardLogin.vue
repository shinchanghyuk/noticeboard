<template>
<div id="layoutAuthentication">
  <div id="layoutAuthentication_content">
    <main>
      <div class="container">
        <div class="row justify-content-center">
          <div class="col-lg-5">
            <div class="card shadow-lg border-0 rounded-lg mt-5">
              <div class="card-header">
                <h3 class="text-center font-weight-light my-4">게시판 로그인</h3>
              </div>
              <div class="card-body">  
                <div class="form-floating mb-3">
                  <input class="form-control" v-model="useridInput" id="inputUserid" type="text" placeholder="ID" />
                  <label for="inputUserid">아이디</label>
                </div>
                <div class="form-floating mb-3">
                  <input class="form-control" v-model="passwordInput" id="inputPassword" type="password" placeholder="Password" />
                  <label for="inputPassword">비밀번호</label>
                </div>
                
                <div class="d-flex align-items-center justify-content-between mt-4 mb-0">
                  <a class="small" href="#" @click="idCreate">아이디 생성</a>
                  <button class="btn btn-primary" @click.prevent="login">로그인</button>
                </div>
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
      responseData:'',
      usertype:'',

      apiBaseUrl: process.env.VUE_APP_API_URL // 환경 변수에서 정의한 URL 사용

    };
  },
  computed: {},
  methods: {
    login() {
      console.log("login START");

      if(!this.useridInput || !this.passwordInput) {
        alert("아이디/비밀번호 입력을 다시 확인해주세요.")
        return;
      }

      axios({
        url: this.apiBaseUrl + "noticeboard/login/",
        method: "POST",
        data: {
          userid: this.useridInput,
          password: this.passwordInput,
        }
        }).then(res => {
          this.responseData = res.data;
          
          if(this.responseData.returnvalue == "1001" || this.responseData.returnvalue == "1000") {
            alert("로그인에 성공하였습니다.");
            if(this.responseData.returnvalue == "1001") {
              this.usertype = 1;
            } else {
              this.usertype = 0;
            }

            // 세션스토리지에 userid, usertype을 저장함
            sessionStorage.setItem('userid', this.useridInput);
            sessionStorage.setItem('usertype', this.usertype);
            
            // this.$router.push({ path: '/boardMain', query: { userid: this.useridInput, usertype: this.usertype}});
            this.$router.push('/noticeboard/boardMain');
          } else {
            alert("로그인에 실패하였습니다.");
          }
        }).catch(err => {
          console.log("BoardLogin - Exception : " + err);
          alert(err);
        });
      },
      idCreate() {
      console.log("idCreate START");
        this.$router.push('/noticeboard/boardRegister'); // 아이디 생성페이지로 이동
      },
  }
};
</script>

<style src="../assets/css/NoticeBoardStyles.css" scoped></style>
