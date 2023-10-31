<template>
  <div>
    <BoardSideNav/>
    <div id="layoutAuthentication" class="detail-container"> 
      <div id="layoutAuthentication_content">
        <main>
          <div>
            <div class="row justify-content-center">
              <div class="col-lg-10">
                <div class="card shadow-lg border-0 rounded-lg mt-6">     
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
      title:'',

      apiBaseUrl: process.env.VUE_APP_API_URL // 환경 변수에서 정의한 URL 사용
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
        url: this.apiBaseUrl + "noticeboard/save/",
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
          this.$router.push('/noticeboard/boardMain');
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
      this.$router.push('/noticeboard/boardMain');
    },
    sessionCheck() {
      // 세션만료 시 로그인 화면으로 이동
      if((this.userid == null || this.userid == "") || (this.usertype == null || this.usertype == "")) {
        alert("세션이 만료되었습니다. 다시 로그인 해주세요.")
        this.$router.push('/noticeboard');
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
