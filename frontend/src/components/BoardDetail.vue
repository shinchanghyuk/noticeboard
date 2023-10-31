<template>
  <div>
    <BoardSideNav/>     
    <div id="layoutAuthentication" class="mt-2 detail-container"> 
      <div id="layoutAuthentication_content">
        <main>
          <div>
            <div class="row justify-content-center">
              <div class="col-lg-10">
                <div class="card shadow-lg border-0 rounded-lg mt-6">     
                  <div class="card-header"><h3 class="text-start ms-sm-1 font-weight-light my-4">게시글 상세보기</h3></div> 
                    <div class="card-body">
                    <form>
                      <div class="form-group">
                        <h5 class="text-start" for="titleInput">제목</h5>
                        <input type="text" class="form-control" id="titleInput" v-model="title" readonly/>
                      </div>

                      <div class="form-group">
                        <h5 class="text-start mt-2" for="contentInput">본문</h5>
                        <textarea rows="10" class="form-control" id="contentInput" v-model="content" readonly/>
                      </div>

                      <div class="form-group text-start" v-show="filename !== undefined && filename !== ''">
                        <h5 class="mt-2">첨부파일</h5>
                        <a class="small" href="#" @click="fileDownload">첨부파일 다운로드 - {{filename}}</a>
                        <!-- <input type="file" class="form-control" ref="fileInput" @change="fileUpload" readonly/> -->
                      </div>
                                  
                      <div class="d-flex align-items-center justify-content-end mt-4 mb-0">
                        <button class="btn btn-primary mx-1" @click="mainPageMove">목록으로 가기</button>
                        <button id="modifyButton" class="btn btn-primary mx-1" @click="boardModify" v-if="isModifyDelete">수정</button>
                        <button id="deleteButton" class="btn btn-primary mx-1" @click.prevent="boardDelete" v-if="isModifyDelete">삭제</button>
                      </div>           
                    </form>

  
                    <BoardComment :boardJsonData="boardJsonData"/>
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
import BoardComment from './BoardComment.vue'; 
import BoardSideNav from './BoardSideNav.vue'; 
import axios from 'axios';

export default {
  components: {
    BoardComment,
    BoardSideNav
  },
  data() {
    return {      
      id: '',
      boardUserid: '',
      title:'',
      content: '',
      userid: '',
      usertype: '',
      createtime: '',
      userTypeButton: '',

      boardJsonData: {
        boardUserid: '',
        boardId: '',
      },

      filename:'',
    
      apiBaseUrl: process.env.VUE_APP_API_URL // 환경 변수에서 정의한 URL 사용
    };
  },
  created() {
    this.id = this.$route.query.id;
    this.boardUserid = this.$route.query.boardUserid;
    // this.userid = this.$route.query.userid;
    // this.usertype = this.$route.query.usertype;
    
    this.userid = sessionStorage.getItem("userid");
    this.usertype = sessionStorage.getItem("usertype");

    this.boardJsonData.boardUserid = this.boardUserid;
    this.boardJsonData.boardId = this.id;

    this.sessionCheck();

    this.title = this.$route.query.title;
    this.content = this.$route.query.content;
    this.createtime = this.$route.query.createtime;
    this.filename = this.$route.query.filename;
  },
  computed: {
    isModifyDelete () {
      if(this.usertype == 1) {
        console.log("BoardDetail - user is admin");  
        
        return true;
      } else {
        if(this.boardUserid != '' && this.userid != '' && this.boardUserid == this.userid) {
        console.log("BoardDetail - user is Write user");

        return true;
        } else {
          return false;
        }
      }
    },
  },
  methods: {
    mainPageMove() {
      console.log("BoardDetail - mainPageMove START");
      //this.$router.push({ path: '/boardMain', query: { userid: this.userid, usertype: this.usertype}});
      this.$router.push('/noticeboard/boardMain');
    },
    boardModify() {
      console.log("BoardDetail - boardModify START");
      // this.$router.push({ path: '/boardWrite', query: { userid: this.userid, usertype: this.usertype, title: this.title, content: this.content, id: this.id }});
      this.$router.push({ path: '/noticeboard/boardWrite', query: { title: this.title, content: this.content, id: this.id, filename : this.filename}});
    },
    fileDownload(event) {
      console.log("BoardDetail - fileDownload START");
      console.log("BoardDetail - fileDownload event : " + event);

      this.sessionCheck();
      
      axios({
        url: this.apiBaseUrl + "noticeboard/fileDownload/",
        method: "POST",
        data: {
          id:this.id,
          originalFileName:this.filename
        },
        // url: "http://127.0.0.1:8080/noticeboard/fileDownload/" + this.id + "/" + this.filename,
        // method: "GET",
        responseType: 'blob'
      }).then((res) => {

        // 파일다운로드
        const url = window.URL.createObjectURL(new Blob([res.data]));
        const link = document.createElement('a');
        link.href = url;
        link.setAttribute('download', this.filename);
        document.body.appendChild(link);
        link.click();
        
        if(res.status === 200) {
          alert("파일 다운로드에 성공하였습니다.");
        } else {
          alert("파일 다운로드에 실패하였습니다.");
        }
      }).catch(err => {
        console.log("BoardDetail - fileDownload Excepion : " + err);
        alert(err);
      });
    },
    boardDelete() {
      console.log("BoardDetail - boardDelete : " + this.id);
      
      if(confirm("게시글을 삭제하겠습니까?")) {
        axios({
          url: this.apiBaseUrl + "noticeboard/delete/",
          method: "POST",
          data: [
            this.id
          ]
        }).then(res => {
          if(res.status === 200) {
            alert("게시글 삭제를 성공하였습니다.");
            // this.$router.push({ path: '/boardMain', query: { userid: this.userid, usertype: this.usertype}});
            this.$router.push('/noticeboard/boardMain');
          } else {
            alert("게시글 삭제를 실패하였습니다.");
          }
        }).catch(err => {
          console.log("BoardDetail - Exception : " + err);
          alert(err);
        });
      }
    },
    sessionCheck() {
      // 세션만료 시 로그인 화면으로 이동
      if((this.userid == null || this.userid == "") || (this.usertype == null || this.usertype == "")) {
        alert("세션이 만료되었습니다. 다시 로그인 해주세요.")
        this.$router.push('/noticeboard');
      }
    }
  }
};

</script>

<style src="../assets/css/NoticeBoardStyles.css" scoped>

</style>
