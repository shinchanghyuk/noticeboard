<template>
  <div>
    <BoardSideNav/>
    <div id="layoutAuthentication"> 
      <div id="layoutAuthentication_content">
        <main>
          <div>
            <div class="row justify-content-center">
              <div class="col-lg-10">
                <div class="card shadow-lg border-0 rounded-lg mt-6 ms-6">     
                  <div class="card-header"><h3 class="text-center font-weight-light my-4">{{title}}</h3></div> 
                  <div class="card-body">
                    <form>
                      <div class="form-group">
                        <h5 class="text-start" for="titleInput">제목</h5>
                        <input type="text" class="form-control" id="titleInput" v-model="titleInput" placeholder="글 제목을 입력해주세요."/>
                      </div>

                      <div class="form-group">
                        <h5 class="text-start mt-2" for="contentInput">본문</h5>
                        <textarea rows="10" class="form-control" id="contentInput" v-model="contentInput" placeholder="본문내용을 입력해주세요." />
                      </div>

                      <div class="form-group" v-show="uploadedFile === undefined || uploadedFile === ''">
                        <h5 class="text-start mt-2" for="fileInput">첨부파일</h5>
                          <input type="file" class="form-control" ref="fileInput" @change="fileUpload" />
                      </div>

                      <div class="form-group text-start mt-2" v-show="uploadedFile !== undefined && uploadedFile !== ''">
                        <a class="small" href="#">{{uploadedFile}}
                          <img src="../assets/delete.jpg" alt="버튼 이미지" class="delete-button ms-1" @click="uploadedFileDelete"/>
                        </a>
                         <!-- <button class="btn btn-primary ms-1 delete-button" @click="uploadedFileDelete">삭제</button></a> -->
                      </div>

                      <div class="d-flex align-items-center justify-content-between mt-4 mb-0">
                        <button class="btn btn-primary" @click="mainPageMove">목록으로 가기</button>
                        <button class="btn btn-primary" @click.prevent="boardSaveModify" >{{ buttonType }}</button>
                      </div>           
                    </form>
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
    <!-- <body class="sb-nav-fixed">  
      <nav class="sb-topnav navbar navbar-expand navbar-dark bg-dark">
        <a class="navbar-brand ps-3">NoticeBoard</a>
        <button class="btn btn-link btn-sm order-1 order-lg-0 me-4 me-lg-0" id="sidebarToggle" href="#!"><i class="fas fa-bars"></i></button>
        <ul class="navbar-nav ms-auto ms-md-0 me-3 me-lg-4">
          <li class="nav-item dropdown">
            <a class="nav-link dropdown-toggle" id="navbarDropdown" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false"><i class="fas fa-user fa-fw"></i></a>
            <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="navbarDropdown">
              <li><a class="dropdown-item" href="#!">Settings</a></li>
              <li><a class="dropdown-item" href="#!">Activity Log</a></li>
              <li><hr class="dropdown-divider" /></li>
              <li><a class="dropdown-item" href="#!">Logout</a></li>
            </ul>
          </li>
        </ul>
      </nav>
      <div id="layoutSidenav">
        <div id="layoutSidenav_nav">
          <nav class="sb-sidenav accordion sb-sidenav-dark" id="sidenavAccordion">
            <div class="sb-sidenav-menu">
              <div class="nav">
                <div class="sb-sidenav-menu-heading">Core</div>
                <a class="nav-link" href="index.html">
                  <div class="sb-nav-link-icon"><i class="fas fa-tachometer-alt"></i></div>
                  NoticeBoard
                </a>
                <div class="sb-sidenav-menu-heading">Interface</div>
                <a class="nav-link collapsed" href="#" data-bs-toggle="collapse" data-bs-target="#collapseLayouts" aria-expanded="false" aria-controls="collapseLayouts">
                  <div class="sb-nav-link-icon"><i class="fas fa-columns"></i></div>
                  Layouts
                  <div class="sb-sidenav-collapse-arrow"><i class="fas fa-angle-down"></i></div>
                </a>
                <div class="collapse" id="collapseLayouts" aria-labelledby="headingOne" data-bs-parent="#sidenavAccordion">
                  <nav class="sb-sidenav-menu-nested nav">
                    <a class="nav-link" href="layout-static.html">Static Navigation</a>
                    <a class="nav-link" href="layout-sidenav-light.html">Light Sidenav</a>
                  </nav>
                </div>
                <a class="nav-link collapsed" href="#" data-bs-toggle="collapse" data-bs-target="#collapsePages" aria-expanded="false" aria-controls="collapsePages">
                  <div class="sb-nav-link-icon"><i class="fas fa-book-open"></i></div>
                  Pages
                  <div class="sb-sidenav-collapse-arrow"><i class="fas fa-angle-down"></i></div>
                </a>
                <div class="collapse" id="collapsePages" aria-labelledby="headingTwo" data-bs-parent="#sidenavAccordion">
                  <nav class="sb-sidenav-menu-nested nav accordion" id="sidenavAccordionPages">
                    <a class="nav-link collapsed" href="#" data-bs-toggle="collapse" data-bs-target="#pagesCollapseAuth" aria-expanded="false" aria-controls="pagesCollapseAuth">
                      Authentication
                      <div class="sb-sidenav-collapse-arrow"><i class="fas fa-angle-down"></i></div>
                    </a>
                    <div class="collapse" id="pagesCollapseAuth" aria-labelledby="headingOne" data-bs-parent="#sidenavAccordionPages">
                      <nav class="sb-sidenav-menu-nested nav">
                        <a class="nav-link" href="login.html">Login</a>
                        <a class="nav-link" href="register.html">Register</a>
                        <a class="nav-link" href="password.html">Forgot Password</a>
                      </nav>
                    </div>
                    <a class="nav-link collapsed" href="#" data-bs-toggle="collapse" data-bs-target="#pagesCollapseError" aria-expanded="false" aria-controls="pagesCollapseError">
                      Error
                      <div class="sb-sidenav-collapse-arrow"><i class="fas fa-angle-down"></i></div>
                    </a>
                    <div class="collapse" id="pagesCollapseError" aria-labelledby="headingOne" data-bs-parent="#sidenavAccordionPages">
                      <nav class="sb-sidenav-menu-nested nav">
                        <a class="nav-link" href="401.html">401 Page</a>
                        <a class="nav-link" href="404.html">404 Page</a>
                        <a class="nav-link" href="500.html">500 Page</a>
                      </nav>
                    </div>
                  </nav>
                </div>
                <div class="sb-sidenav-menu-heading">Addons</div>
                <a class="nav-link" href="charts.html">
                  <div class="sb-nav-link-icon"><i class="fas fa-chart-area"></i></div>
                  Charts
                </a>
                <a class="nav-link" href="tables.html">
                  <div class="sb-nav-link-icon"><i class="fas fa-table"></i></div>
                  Tables
                </a>
              </div>
            </div>
            <div class="sb-sidenav-footer">
              <div class="small">Logged in as:</div>
              Start Bootstrap
            </div>
          </nav>
        </div>
      </div> -->

<script>
import BoardSideNav from './BoardSideNav.vue';
import axios from 'axios';

export default {
  components: {
    BoardSideNav
  },
  data() {
    return {
      titleInput: '',
      contentInput: '',
      userid: '',
      usertype: '',
      id:'',
      buttonType: '',
      selectedFile: '',
      uploadedFile: '',
      title:''
    };
  },
  methods: {
    fileUpload(event) {
      console.log("fileUpload START");

      this.sessionCheck();

      if(this.uploadedFile) {
        alert("게시글에 1개의 첨부파일만 업로드 가능합니다.");
        return;
      }

      console.log("fileUpload event : ", event.target.files[0]);
      this.selectedFile = event.target.files[0];

    },
    uploadedFileDelete() {
      console.log("uploadedFileDelete START");

      this.sessionCheck();
      
      this.uploadedFile = '';
    },
    boardSaveModify() {
      console.log("boardSaveModify START");

      this.sessionCheck();

      if(!this.titleInput || !this.contentInput) {
        alert("게시글 제목/내용 입력을 다시 확인해주세요.")
        return;
      }

      const formData = new FormData();
      formData.append('userid', this.userid);
      formData.append('title', this.titleInput);
      formData.append('content', this.contentInput);
      
      if(this.id) {
        formData.append('id', this.id);
      }

      // 파일객체
      if(this.selectedFile) {
        formData.append('file', this.selectedFile);
      }
      
      axios({
        url: "http://127.0.0.1:8080/noticeboard/save/",
        method: "POST",
        data: formData,
        headers: {
          'Content-Type': 'multipart/form-data',
        }
      }).then(res => {
        // alert(res.data.message);
        if(res.status === 200) {
          if(this.id) {
            alert("게시글 수정을 성공하였습니다.");
          } else {
            alert("게시글 저장을 성공하였습니다.");
          }
          // this.$router.push({ path: '/boardMain', query: { userid: this.userid, usertype: this.usertype}});
          this.$router.push('/boardMain');
        } else {
          alert("게시글 저장을 실패하였습니다.");
        }
      }).catch(err => {
        console.log("BoardWrite - Exception : " + err);
      });
    },
    mainPageMove() {
      console.log("mainPageMove START");
      //this.$router.push({ path: '/boardMain', query: { userid: this.userid, usertype: this.usertype}});
      this.$router.push('/boardMain');
    },
    sessionCheck() {
      // 세션만료 시 로그인 화면으로 이동
      if((this.userid == null || this.userid == "") || (this.usertype == null || this.usertype == "")) {
        alert("세션이 만료되었습니다. 다시 로그인 해주세요.")
        this.$router.push('/');
      }
    }
  },
  created() {
    // this.userid = this.$route.query.userid;
    // this.usertype = this.$route.query.usertype;

    this.userid = sessionStorage.getItem("userid");
    this.usertype = sessionStorage.getItem("usertype");

    this.sessionCheck();

    //  게시글 상세화면에서 쓰기화면으로 넘어올 경우 title, content, id 값이 들어오게 됨
    this.id = this.$route.query.id;
    this.titleInput = this.$route.query.title;
    this.contentInput = this.$route.query.content;
    this.uploadedFile = this.$route.query.filename;

    if(this.id) {
      this.title = '게시글 수정';
      this.buttonType = '수정';
    } else {
      this.title = '게시글 작성';
      this.buttonType = '저장';
    }
  },
  computed: {
  },
};
</script>

<style src="../assets/css/NoticeBoardStyles.css" scoped>

</style>
