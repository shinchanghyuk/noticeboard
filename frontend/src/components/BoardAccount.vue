<template>
  <div>
    <BoardSideNav/>   
    <div id="layoutAuthentication"> 
      <div id="layoutAuthentication_content">
        <main>
          <div class="container">
            <div class="row justify-content-center">
              <div class="col-lg-7">
                <div class="card shadow-lg border-0 rounded-lg mt-5">
                  <div class="card-header"><h3 class="text-center font-weight-light my-4">회원 정보수정</h3></div>
                  <div class="card-body">
                    <form>
                      <div class="form-floating mb-3">
                          <input class="form-control" v-model="useridInput" id="useridInput" type="text" placeholder="사용자 아이디" readonly/>
                          <label for="useridInput">사용자 아이디</label>
                      </div>

                      <div class="row mb-3">
                        <div class="col-md-6">
                          <div class="form-floating mb-3 mb-md-0">
                            <input class="form-control" v-model="passwordInput" id="passwordInput" type="password" placeholder="패스워드" />
                            <label for="passwordInput">기존 패스워드</label>
                          </div>
                        </div>
                        <div class="col-md-6">
                          <div class="form-floating mb-3 mb-md-0">
                            <input class="form-control" v-model="modifyPasswordInput" id="modifyPasswordInput" type="password" placeholder="확인 패스워드" />
                            <label for="modifyPasswordInput">변경 패스워드</label>
                          </div>
                        </div>
                      </div>
                    </form>
                  </div>
                  <div class="card-footer d-flex align-items-center justify-content-between mb-0">
                    <div class="justify-content-sm-start small"><a href="#" @click="mainPageMove">뒤로가기</a></div>
                    <button class="btn btn-primary" @click.prevent="modify">정보수정</button>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </main>
      </div>
    </div>
  </div>
 </template>

<script>
import axios from 'axios';
import BoardSideNav from './BoardSideNav.vue'; 

export default {
  components: {
    BoardSideNav
  },
  data() {
    return {
      useridInput:'',
      passwordInput: '',
      passwordConfirmInput:'',

      responseData:'', 
      apiBaseUrl: process.env.VUE_APP_API_URL // 환경 변수에서 정의한 URL 사용
    };
  },
  created() {
    this.useridInput= sessionStorage.getItem("userid");
  },
  computed: {},
  methods: {
    async userCheck() {
      // 비동기가 아닌 동기로 작업해야지만 로그인 결과를 조건문에서 사용할 수 있음
      await axios({
        url: this.apiBaseUrl + "noticeboard/login/",
        method: "POST",
        data: {
          userid: this.useridInput,
          password: this.passwordInput,
        }
      }).then(res => {   
        this.responseData = res.data;
        console.log("BoardAccount - responseData : ", res.data.returnvalue);
        console.log("BoardAccount - responseData : ", this.responseData.returnvalue);

        if(this.responseData.returnvalue == "1001" || this.responseData.returnvalue == "1000") {
          console.log("BoardAccount - success");
          return true;
        } else {
          console.log("BoardAccount - failed");
          return false;
        }
      }).catch(err => {
        console.log("BoardAccount - userCheck Exception : " + err);
        return false;
      });
    },
    modify() {
      if((sessionStorage.getItem("userid") == null || sessionStorage.getItem("userid") == "") || (sessionStorage.getItem("usertype") == null || sessionStorage.getItem("usertype")) == "") {
        alert("세션이 만료되었습니다. 다시 로그인 해주세요.");
        return;
      }

      if(!this.userCheck()) { 
        console.log("BoardAccount - usercheck is false : " + this.userCheck());
        alert("아이디/비밀번호 입력을 다시 확인해주세요.");
        return;
      }

      axios({
        url: this.apiBaseUrl + "noticeboard/accountModify/",
        method: "POST",
        data: {
          userid: this.useridInput,
          password: this.modifyPasswordInput,
        }
        }).then(res => {
          // alert(res.data.message);
          if(res.status === 200) {
            alert("사용자 정보수정을 성공하였습니다. 재로그인 해주세요");
            this.$router.push('/'); // 로그인 페이지로 이동
            sessionStorage.removeItem("userid");
            sessionStorage.removeItem("usertype");
          } else {
            alert("사용자 정보수정을 실패하였습니다.");
          }
        }).catch(err => {
          console.log("BoardRegister - Exception : " + err);
          alert(err);
        });
    },
    mainPageMove() {
      console.log("mainPageMove START");
      this.$router.push('/boardMain'); // 메인 페이지로 이동
    },
  }
};
</script>

<style src="../assets/css/NoticeBoardStyles.css" scoped></style>