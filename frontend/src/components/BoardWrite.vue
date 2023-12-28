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

                      <div class="form-group">
                        <h5 class="text-start mt-2" for="fileInput">첨부파일</h5>
                          <input type="file" class="form-control" multiple ref="fileInput" @change="fileUpload" />
                      </div>

                      <div class="form-group text-start mt-2" v-show="uploadedFiles !== null && uploadedFiles.length !== 0">
                        <div v-for="(uploadedFile, index) in uploadedFiles" :key="index">
                           <a class="small" href="#">{{uploadedFile.originalFileName}}
                            <img src="../assets/delete.jpg" alt="버튼 이미지" class="delete-button ms-1" @click="uploadedFileDelete(uploadedFile.originalFileName)"/>
                           </a>
                        </div>
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
      selectedFiles: '',
      uploadedFiles: [],
      title:'',

      apiBaseUrl: process.env.VUE_APP_API_URL // 환경 변수에서 정의한 URL 사용
    };
  },
  methods: {
    fileUpload(event) {
      console.log("fileUpload START");

      this.sessionCheck();
      this.selectedFiles = event.target.files;

      /// var filename = event.target.files
    },
    uploadedFileDelete(deleteFile) {
      console.log("uploadedFileDelete START");

      this.sessionCheck();

      this.uploadedFiles = this.uploadedFiles.filter(item => item.originalFileName !== deleteFile);

      console.log("uploaded file length : " + this.uploadedFiles.length);
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

      if (this.selectedFiles) {
        for (let i = 0; i < this.selectedFiles.length; i++) {
          formData.append("files", this.selectedFiles[i]);
        }
      }

      if(this.uploadedFiles) {
        for (let i = 0; i < this.uploadedFiles.length; i++) {
          formData.append("uploadedFileName", this.uploadedFiles[i].originalFileName);
        }
      }

      axios({
        url: this.apiBaseUrl + "board/save",
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
          this.$router.push('/main');
        } else {
          if(this.id) {
            alert("게시글 수정에 실패하였습니다.");
          } else {
            alert("게시글 저장을 실패하였습니다.");
          }
        }
      }).catch(err => {
        console.log("BoardWrite - boardSaveModify Exception : " + err);
      });
    },
    mainPageMove() {
      console.log("mainPageMove START");
      //this.$router.push({ path: '/boardMain', query: { userid: this.userid, usertype: this.usertype}});
      this.$router.push('/main');
    },
    sessionCheck() {
      // 세션만료 시 로그인 화면으로 이동
      if((this.userid == null || this.userid == "") || (this.usertype == null || this.usertype == "")) {
        alert("세션이 만료되었습니다. 다시 로그인 해주세요.")
        this.$router.push('/');
      }
    },
    boardDataSearch() {
        axios({
            url: this.apiBaseUrl + "board/detailSearch/" + this.id,
            method: "GET",
        }).then(res => {
            console.log("boardDataSearch : " + res.data);
        if(res.status === 200 && res.data != null) {
            if(res.data.userid == this.userid) {
                this.id = res.data.id;
                this.titleInput = res.data.title;
                this.contentInput = res.data.content;
                this.uploadedFiles = res.data.fileList;
            } else {
                alert("잘못된 접근입니다.");
                this.$router.push('/main');
            }
        }
      }).catch(err => {
        console.log("BoardWrite - boardDataSearch Exception : " + err);
      });
    },
  },
  created() {
    this.userid = sessionStorage.getItem("userid");
    this.usertype = sessionStorage.getItem("usertype");

    this.id = this.$route.params.id;

    this.sessionCheck();

    if(this.id) {
      this.title = '게시글 수정';
      this.buttonType = '수정';
      this.boardDataSearch(this.id);
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
