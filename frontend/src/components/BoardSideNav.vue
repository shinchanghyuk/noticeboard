<template>
  <div>
    <body class="sb-nav-fixed">
      <nav class="sb-topnav navbar navbar-expand navbar-dark bg-dark">
            <!-- Navbar Brand-->
            <a class="navbar-brand ps-3">NoticeBoard</a>

            <div class="d-flex align-items-center justify-content-end logout-button">
                <p class="h5 strong text-sm-center mt-3">{{userid}}님</p>
                <button class="btn btn-primary ms-3" @click="logout">로그아웃</button>   
            </div>
          <!-- Sidebar Toggle-->
          <button class="btn btn-link btn-sm order-1 order-lg-0 me-4 me-lg-0" id="sidebarToggle" href="#!"><i class="fas fa-bars"></i></button>
          <!-- Navbar-->
          <ul class="navbar-nav ms-auto ms-md-0 me-3 me-lg-4">
            <li class="nav-item dropdown">
              <a class="nav-link dropdown-toggle" id="navbarDropdown" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false"><i class="fas fa-user fa-fw"></i></a>
              <!-- <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="navbarDropdown">
                <li><a class="dropdown-item" href="#!">사용자 설정</a></li>
                <li><a class="dropdown-item" href="#!">Activity Log</a></li>
                <li><hr class="dropdown-divider" /></li>
                <li><a class="dropdown-item" href="#!">Logout</a></li> 
              </ul> -->
            </li>
          </ul>
        </nav>
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
                    <div class="sb-sidenav-footer">
                        <div class="small">NoticeBoard</div>
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
        };
    },
    methods: {
        mainPageMove() {
            console.log("BoardSideNav - mainPageMove START");
            this.$router.push('/boardMain');
        },
        boardWrite() {
            console.log("BoardSideNav - boardWrite START");
            this.$router.push('/boardWrite');
        },
        accountPageMove() {
            console.log("BoardSideNav - accountPageMove START");
            this.$router.push('/boardAccount');
        },
        logout() {
            console.log("BoardSideNav - logout START");
            if(confirm("로그아웃 하시겠습니까?")) {
                sessionStorage.removeItem("userid");
                sessionStorage.removeItem("usertype");
                
                this.$router.push('/');
            }
        },
        accountDelete() {
            console.log("BoardSideNav - accountDelete START");
            if(confirm("회원탈퇴 하시겠습니까?")) {
                sessionStorage.removeItem("userid");
                sessionStorage.removeItem("usertype");
                
                this.$router.push('/');

                const userid = this.userid;

                axios({
                    url: "http://127.0.0.1:8080/noticeboard/accountDelete/",
                    method: "POST",
                    data: {
                        userid
                    }
                }).then(res => {
                    if(res.status === 200) {
                        alert("회원탈퇴가 완료되었습니다.");
                        this.$router.push('/'); // 로그인 페이지로 이동
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
