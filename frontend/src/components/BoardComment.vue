<template>  
  <div>
    <div class="card-header mt-3"><h6 class="text-start ms-sm-1 font-weight-light my-2">댓글 창</h6></div> 
    <div v-for="(comment, index) in comments" :key="index" class="mt-1">
      <div class="card-body shadow-lg mt-1">
        <h6 class="text-md-start">{{ comment.userid }}</h6>
        <h6 class="text-md-start mt-3" v-show="!modifyButtonClick || index != commentClickIndex">{{ comment.comment }}</h6>
        <input class="form-control" v-show="modifyButtonClick && index == commentClickIndex" v-model="modifyCommentInput" id="modifyCommentInput" type="text" />

        <div class="d-flex align-items-end justify-content-between mt-2">
          <h6 class="text-md-start"> {{ comment.createtimeFormat }}</h6> 
          <div class="d-flex">
          
          <!-- 수정 삭제는 관리자 및 게시글 작성자만 할 수 있도록 수정해야 함-->
            <button class="btn btn-primary mx-1" v-show="comments.boardUserid === userid || usertype == 1" @click="modifyComment(index)">수정</button>
            <button class="btn btn-primary mx-1" v-show="comments.boardUserid === userid || usertype == 1" @click="deleteComment(index)">삭제</button>
          </div>
        </div>
      </div>
    </div>

    <div class="form-group mb-3 mt-3" v-show="!modifyButtonClick">
      <h5 class="text-start mt-2" for="commentInput">댓글</h5>
      <input class="form-control" v-model="commentInput" id="commentInput" type="text" placeholder="댓글을 입력하세요." />
    </div>

    <div class="d-flex align-items-center justify-content-end mt-4 mb-0" v-if="!modifyButtonClick">
      <button class="btn btn-primary" @click="modifyComment(index)">댓글작성</button>
    </div>       
  </div>
</template>

<script>
import axios from 'axios';

export default {
  props:['boardJsonData'],
  components: {},
  data() {
    return {
      userid: '',
      usertype: '',
      commentInput: '',
      boardId: '',
      comments: [
         { userid: 'admin', comment: '테스트데이터 내용1', createtime: '2023-09-08 14:32:35' },
         { userid: 'admin', comment: '테스트데이터 내용2', createtime: '2023-09-08 14:32:35' }
      ],
      boardDataJsonData: this.boardJsonData,

      modifyButtonClick: false,
      commentClickIndex:0,
      modifyCommentInput:'',

      apiBaseUrl: process.env.VUE_APP_API_URL // 환경 변수에서 정의한 URL 사용
    };
  },
  created() {
    this.userid = sessionStorage.getItem("userid");
    this.usertype = sessionStorage.getItem("usertype");
    // this.boardDataJsonData = this.boardJsonData;

    console.log("BoardComment - created : ", this.boardDataJsonData);  
    this.searchComment();
  },
  computed: { },
  methods: {
    searchComment() {
      console.log("searchComment START");
      console.log("searchComment boardid : ", this.boardDataJsonData);

      this.sessionCheck();
    
      axios({
        url: this.apiBaseUrl + "noticeboard/commentSearch/",
        method: "POST",
        data: {
          boardid: this.boardDataJsonData.boardId,
        }
      }).then(res => {
        this.comments = res.data;

        this.dateFormatModify(this.comments);
      }).catch(err => {
        console.log("BoardComment - searchComment Exception : " + err);
      });
    },
    dateFormatModify(comments) {
      for(let i= 0; i < comments.length; i++) {
        var dateFormat = comments[i].createtime;

        var year = dateFormat.substring(0, 4);
        var month = dateFormat.substring(4, 6);
        var day = dateFormat.substring(6, 8);
        var hour = dateFormat.substring(8, 10);
        var minute = dateFormat.substring(10, 12);
        var second = dateFormat.substring(12, 14);

        //var date = '${year}-${month}-${day} ${hour}:${minute}:${second}';
        var date = year + "-" + month + "-" + day + " " + hour + ":" + minute + ":" + second;

        comments[i].createtimeFormat = date;
      }
    },
    modifyComment(index) {
      console.log("modifyComment START");
      console.log("modifyComment index : " + index);

      this.sessionCheck();

      var comment = '';
      var commentid = '';

      if(index !== undefined && index !== '') { // 수정버튼으로 호출되었을 경우
        if(!this.modifyButtonClick) {
          this.modifyButtonClick = true;
          console.log("modifyComment modifyButtonClick : " + this.modifyButtonClick);
          this.commentClickIndex = index;
          this.modifyCommentInput = this.comments[index].comment;
          return;
        }

        if(this.commentClickIndex != index) {
          alert("다른 댓글 수정중 상태입니다.");
          return;
        }

        if(!this.modifyCommentInput) {
          alert("수정할 댓글 내용을 입력해주세요. 비어있습니다.");
          return;
        }
        
        comment = this.modifyCommentInput;
        commentid = this.boardDataJsonData.boardId + "-" + this.comments[index].createtime;
      } else { // 댓글작성 버튼으로 호출되었을 경우
        if(!this.commentInput) {
          alert("댓글 내용을 입력해주세요. 비어있습니다.")
          return;
        }
        comment = this.commentInput;
      }

      axios({
        url: this.apiBaseUrl + "noticeboard/commentSave/",
        method: "POST",
        data: {
          userid: this.userid,
          comment: comment,
          commentid: commentid,
          boardid: this.boardDataJsonData.boardId,
        }
      }).then(res => {        
        if(res.status === 200) {
          if(index != null || index == "" || index == 0) {
            alert("댓글수정에 성공하였습니다.");
          } else {
            alert("댓글작성에 성공하였습니다.");
          }

          this.searchComment();
        } else {
          if(index != null || index == "" || index == 0) {   
            alert("댓글수정에 실패하였습니다.");
          } else {
            alert("댓글작성에 실패하였습니다.");
          }
        }
      }).catch(err => {
        console.log("BoardComment - modifyComment Exception : " + err);
      });

        // 통신 후 값들 초기화
        if(index !== undefined && index !== '') { // 댓글 수정일 떄
          this.modifyButtonClick= false;
          this.commentClickIndex = 0;
          this.modifyCommentInput = '';
        } else { // 댓글 작성일 때 
          this.commentInput='';
        }
       
    },
    deleteComment(index) {
    console.log("deleteComment START");
    console.log("deleteComment index : " + index);

    this.sessionCheck();

    var commentid = '';
    commentid = this.boardDataJsonData.boardId + "-" + this.comments[index].createtime;

    axios({
      url: this.apiBaseUrl + "noticeboard/commentDelete/",
      method: "POST",
      data: {
        // 몇번째의 댓글에 대한 삭제버튼인지 알 수 있는 ID를 담아야 함
        userid: this.userid,
        commentid: commentid,
        boardid: this.boardDataJsonData.boardId,
      }
      }).then(res => {
        if(res.status === 200) {
          alert("댓글삭제에 성공하였습니다.");

          this.searchComment();
        } else {
          alert("댓글삭제에 실패하였습니다.");
        }
      }).catch(err => {
        console.log("BoardComment - deleteComment Exception : " + err);
        alert(err);
      });
    },
    sessionCheck() {
      // 세션만료 시 로그인 화면으로 이동
      if((this.userid == null || this.userid == "") || (this.usertype == null || this.usertype == "")) {
        alert("세션이 만료되었습니다. 다시 로그인 해주세요.")
        this.$router.push('/');
      }
    }
  }
}
</script>

<style src="../assets/css/NoticeBoardStyles.css" scoped>

</style>
