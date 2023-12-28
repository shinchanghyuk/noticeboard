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

                      <div class="form-group text-start" v-show="filenames !== null && filenames.length !== 0">
                        <h5 class="mt-2">첨부파일</h5>
                           <div v-for="(filename, index) in filenames" :key="index">
                              <a class="small" href="#" @click="fileDownload(filename)">첨부파일 다운로드 - {{ filename.originalFileName }}</a>
                           </div>
                        <!-- <input type="file" class="form-control" ref="fileInput" @change="fileUpload" readonly/> -->
                      </div>
                                  
                      <div class="d-flex align-items-center justify-content-end mt-4 mb-0">
                        <button class="btn btn-primary mx-1" @click="mainPageMove">목록으로 가기</button>
                        <button id="modifyButton" class="btn btn-primary mx-1" @click="boardModify" v-if="isModifyDelete">수정</button>
                        <button id="deleteButton" class="btn btn-primary mx-1" @click.prevent="boardDelete" v-if="isModifyDelete">삭제</button>
                      </div>           
                    </form>

                    <div v-if="boardJsonData.boardId !== null && boardJsonData.boardId !== ''">
                      <BoardComment :boardJsonData="boardJsonData" />
                    </div>
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

      filenames: [],
      apiBaseUrl: process.env.VUE_APP_API_URL // 환경 변수에서 정의한 URL 사용
    };
  },
  created() {
    this.userid = sessionStorage.getItem("userid");
    this.usertype = sessionStorage.getItem("usertype");

    this.sessionCheck();
    this.id = this.$route.params.id;

    this.detailSearch(this.id);
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
    detailSearch(id) {
        axios({
            url: this.apiBaseUrl + "board/detailSearch/" + id,
            method: "GET",
        }).then((res) => {
            console.log("detailSearch : ", res.data);

            if(res.data != undefined && res.data != '') {
                this.boardUserid = res.data.userid;
                this.boardJsonData.boardUserid = res.data.userid;
                this.boardJsonData.boardId = this.id;

                this.title = res.data.title;
                this.content = res.data.content;
                this.createtime = res.data.createtime;
                this.filenames = res.data.fileList;

                console.log("this.filenames :" + this.filenames);
            } else {
                alert("검색한 게시글이 없습니다.");
                this.$router.push('/main');
            }
        }).catch(err => {
            console.log("BoardDetail - detailSearch Exception : " + err);
            alert(err);
        });
    },
    mainPageMove() {
      console.log("BoardDetail - mainPageMove START");
      this.$router.push('/main');
    },
    boardModify() {
      console.log("BoardDetail - boardModify START");
      this.$router.push({ path: `/write/${this.id}`});
      // this.$router.push({ path: '/write/{id}', query: { title: this.title, content: this.content, id: this.id, filename : this.filename}});
    },
    fileDownload(downloadFile) {
      console.log("BoardDetail - fileDownload START");

      this.sessionCheck();
      
      axios({
        url: this.apiBaseUrl + "fileDownload/",
        method: "POST",
        data: {
          boardid:this.id,
          originalFileName:downloadFile.originalFileName
        },
        responseType: 'blob'
      }).then((res) => {

        // 파일다운로드
        const url = window.URL.createObjectURL(new Blob([res.data]));
        const link = document.createElement('a');
        link.href = url;
        link.setAttribute('download', downloadFile.originalFileName);
        document.body.appendChild(link);
        link.click();
        
        if(res.status === 200) {
          alert("파일 다운로드에 성공하였습니다.");
        } else {
          alert("파일 다운로드에 실패하였습니다.");
        }
      }).catch(err => {
        console.log("BoardDetail - fileDownload Exception : " + err);
        alert(err);
      });
    },
    boardDelete() {
      console.log("BoardDetail - boardDelete : " + this.id);
      
      if(confirm("게시글을 삭제하겠습니까?")) {
        axios({
          url: this.apiBaseUrl + "board/delete/",
          method: "POST",
          data: [
            this.id
          ]
        }).then(res => {
          if(res.status === 200) {
            alert("게시글 삭제를 성공하였습니다.");
            // this.$router.push({ path: '/boardMain', query: { userid: this.userid, usertype: this.usertype}});
            this.$router.push('/main');
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
        this.$router.push('/');
      }
    }
  }
};

</script>

<style src="../assets/css/NoticeBoardStyles.css" scoped>

</style>
