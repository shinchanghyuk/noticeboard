<template>
  <div>
    <p class="h3 strong text-sm-start">게시판 목록</p>

    <div class="d-flex align-items-center justify-content-end mt-4 mb-3">  
      <input class="form-control-search" type="text" v-model="rowSearchValue" placeholder="제목/작성자로 검색"/>
      <button class="btn btn-primary ms-2" type="button" @click.prevent="detailSearch">검색</button>
    </div>

    <div class="d-flex align-items-center justify-content-between mt-4 mb-1">  
      <p class="h5 strong text-sm-start">총 {{totcnt}}건</p>

      <!-- 관리자 권한 가졌거나 선택한 데이터 열이 있을 경우 -->
      <!-- <button class="btn btn-primary ms-2" type="button" v-show="itemsSelected.length > 0 && usertype == 1" @click.prevent="boardDelete">게시글 삭제</button> -->
    </div>

    <!-- checkbox가 안되는 이슈 찾아봐야함.. -->

    <EasyDataTable
      border-cell
      buttons-pagination
      :items="items" 
      :headers="headers" 
      @click-row="rowClick" 
      hide-footer
      :sort-by="sortBy"
      :sort-type="sortType" />



      <!-- @update-page-items="updateRowsPerPage" 
     -->

     <!--       v-if="usertype == 1"
        items-selected="itemsSelected" -->
      <!-- <template #pagination="{ prevPage, nextPage, isFirstPage }">
        <button :disabled="isFirstPage" @click="prevPage">prev page</button>
        <button :disabled="false" @click="nextPage">next page</button>
      </template> -->
    <!-- </EasyDataTable> -->

      <div class="d-flex align-items-center justify-content-end mt-1 mb-0">
        <label for="rowsPerPage">페이지 크기:</label>
        <select id="rowsPerPage" v-model="rowsPerPage">>
          <option value="10">10</option>
          <option value="25">25</option>
          <option value="50">50</option>
        </select>>

        <button :disabled="currentPage === 1" @click.prevent="prevPage">이전 페이지</button>
        <button :disabled="currentPage >= Math.ceil(totcnt / rowsPerPage)" @click.prevent="nextPage">다음 페이지</button>
      </div>   
  </div>
</template>

<script>
import axios from 'axios';

export default {
  // props: {
  //   dataTableData: {
  //     type: Object,
  //     required: true
  //   }
  // },
  props:['dataTableJsonData'],
  computed: {
  },
  data() {
    return {
      headers: [
        { text: '번호', value: 'id' },
        { text: '제목', value: 'title' },
        { text: '작성자', value: 'userid' },
        { text: '작성일자', value: 'createtime' }
      ],
      items: [
        // 테스트 데이터
        //  { id: 1, title: '테스트데이터1', content: '테스트데이터 내용1', userid: 'John Doe', createtime: '2023-09-08 14:32:35' },
        //  { id: 2, title: '테스트데이터2', content: '테스트데이터 내용2', userid: 'shin', createtime: '2023-09-08 14:32:35' }
      ],
      itemsSelected: [],
      rowsPerPage: 10, // 페이지당 표시할 행의 수 (기본값 25)
      currentPage: 1, // 현재 페이지 (기본값 1)
      tableData: this.dataTableJsonData,
      totcnt:0, // 게시글 건수 (기본값 0)

      // 정렬기준
      sortBy:'createtime',
      sortType:'desc',

      apiBaseUrl: process.env.VUE_APP_API_URL // 환경 변수에서 정의한 URL 사용
    };
  },
  mounted() { //mounted는 페이지가 켜질때 실행됨

  },
  created() {
    this.userid = this.tableData.userid;
    this.usertype = this.tableData.usertype;

    this.search();
  },
  methods: { // 서버와 통신하기 위한 API
    // 전체 게시글 검색
    search() {
      //this.items = '';
      
      axios({
        url: this.apiBaseUrl + "board/search/",
        method: "POST",
        data: {
          rowSearch: this.rowSearchValue,
          pageSize: this.rowsPerPage,
          currentPage: this.currentPage
        },
      }).then(res => {
        console.log("DataTable - search res.data : ", res.data);
        console.log("DataTable - search res.data.data : ", res.data.searchList);
        console.log("DataTable - search res.data.totcnt : ", res.data.totcnt);

        this.items = res.data.searchList;

        // 실제 총 데이터 건수를 가져와야 함
        if(res.data.totcnt == null || res.data.totcnt == "" || res.data.totcnt == 0) {
          this.totcnt = 0;
        } else {
          this.totcnt = res.data.totcnt;
        }
      }).catch(err => {
        console.log("DataTable - search Exception : " + err);
        alert("게시글 검색에 오류가 발생하였습니다.");
      });
    },
    detailSearch() {
      // 현재페이지 및 totcnt, 총 페이지 관련 변수를 초기화 해야함
      this.rowsPerPage = 10;
      this.currentPage = 1;
      this.totcnt = 0;

      this.items = '';

      this.search();
    },
    rowClick(item) {
      this.$router.push({ path: `/detail/${item.id}` });
    },
    prevPage() {
      console.log("DataTable - prevPage START")
      this.currentPage = this.currentPage - 1;

      this.search();
    },        
    nextPage() {
      console.log("DataTable - nextPage START");
      this.currentPage = this.currentPage + 1;

      this.search();
    },
    updateRowsPerPage() {
      console.log("DataTable - updateRowsPerPage START");
    },
  },
  watch: {
    rowsPerPage(newValue, oldValue) { // rowsPerPage 값이 변경될 때 호출
      console.log("DataTable - rowsPerPage START newValue : " + newValue + " , oldValue : " + oldValue);
      this.rowsPerPage = newValue;

      this.search();
    },
  },
};

</script>

<style src="../assets/css/NoticeBoardStyles.css" scoped></style>
