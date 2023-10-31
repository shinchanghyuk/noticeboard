<template>
  <div>
    <div>
        <div class="logout-button">
            <p class="h5 strong text-sm-center mt-3">{{userid}}님</p>
            <button class="btn btn-primary ms-3" @click="logout">로그아웃</button>   
        </div>
    </div>

    <body class="sb-nav-fixed">
        <div id="layoutSidenav">
            <div id="layoutSidenav_nav">
                <nav class="sb-sidenav accordion sb-sidenav-dark" id="sidenavAccordion">
                    <div class="sb-sidenav-menu">
                        <div class="nav">
                            <div class="sb-sidenav-menu-heading">게시판</div>
                            <a class="nav-link" href="#" @click="mainPageMove">
                                <div class="sb-nav-link-icon"><i class="fas fa-tachometer-alt"></i></div>
                                게시판 목록이동
                            </a>
                            <a class="nav-link" href="#" @click="boardWrite">
                            <div class="sb-nav-link-icon"><i class="fas fa-tachometer-alt"></i></div>
                                게시글 쓰기
                            </a>
                            <div class="sb-sidenav-menu-heading">사용자 설정</div>
                            <a class="nav-link collapsed" href="#" @click="accountPageMove" data-bs-toggle="collapse" data-bs-target="#collapseLayouts" aria-expanded="false" aria-controls="collapseLayouts">
                                <div class="sb-nav-link-icon"><i class="fas fa-columns"></i></div>
                                사용자 정보변경
                            </a>
                            <a class="nav-link collapsed" href="#" @click=" accountDelete" data-bs-toggle="collapse" data-bs-target="#collapseLayouts" aria-expanded="false" aria-controls="collapseLayouts">
                                <div class="sb-nav-link-icon"><i class="fas fa-columns"></i></div>
                                회원탈퇴
                            </a>
                        </div>
                    </div>
                </nav>
            </div>
        </div>
    </body>
  </div>
</template>

<script>
import axios from 'axios';

export default {
    components: {
    },  
    mounted() { //mounted는 페이지가 켜질때 실행됨

    },
    created() {
        this.userid = sessionStorage.getItem("userid");
    },
    data() {
        return {
            userid: '',

            apiBaseUrl: process.env.VUE_APP_API_URL // 환경 변수에서 정의한 URL 사용
        };
    },
    methods: {
        mainPageMove() {
            console.log("BoardSideNav - mainPageMove START");
            this.$router.push('/noticeboard/boardMain');
        },
        boardWrite() {
            console.log("BoardSideNav - boardWrite START");
            this.$router.push('/noticeboard/boardWrite');
        },
        accountPageMove() {
            console.log("BoardSideNav - accountPageMove START");
            this.$router.push('/noticeboard/boardAccount');
        },
        logout() {
            console.log("BoardSideNav - logout START");
            if(confirm("로그아웃 하시겠습니까?")) {
                sessionStorage.removeItem("userid");
                sessionStorage.removeItem("usertype");
                
                this.$router.push('/noticeboard');
            }
        },
        accountDelete() {
            console.log("BoardSideNav - accountDelete START");
            if(confirm("회원탈퇴 하시겠습니까?")) {
                sessionStorage.removeItem("userid");
                sessionStorage.removeItem("usertype");
                
                this.$router.push('/noticeboard');

                const userid = this.userid;

                axios({
                    url: this.apiBaseUrl + "noticeboard/accountDelete/",
                    method: "POST",
                    data: {
                        userid
                    }
                }).then(res => {
                    if(res.status === 200) {
                        alert("회원탈퇴가 완료되었습니다.");
                        this.$router.push('/noticeboard'); // 로그인 페이지로 이동
                    } else {
                        alert("회원탈퇴가 실패되었습니다.");
                    }
                }).catch(err => {
                    console.log("BoardSideNav accountDelete - Exception : " + err);
                });
            }
        },
    }
}
</script>

<style src="../assets/css/NoticeBoardStyles.css" scoped></style>
