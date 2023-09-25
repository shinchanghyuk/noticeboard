<template>
    <div>
        <BoardSideNav/>
        
        <div id="layoutAuthentication"> 
            <div id="layoutSidenav_content">
                <main>
                    <div class="container_sepa px-4">                                
                        <div class="card mb-4">
                            <div>
                                <DataTable 
                                :dataTableJsonData="dataTableJsonData"/>
                            </div>

                            <div class="d-flex align-items-center justify-content-end mt-4 mb-0">
                                <button class="btn btn-primary" @click="boardWrite">게시글 쓰기</button>   
                            </div>
                        </div>
                    </div>
                </main>
            </div>
        </div>
    </div>
</template>

<script>
import DataTable from './DataTable.vue'; 
import BoardSideNav from './BoardSideNav.vue'
// import axios from 'axios';

export default {
    components: { // 컴포넌트 등록
        BoardSideNav,
        DataTable
    },  
    mounted() { //mounted는 페이지가 켜질때 실행됨

    },
    created() {
        this.userid = sessionStorage.getItem("userid");
        this.usertype = sessionStorage.getItem("usertype");

        // 세션만료 시 로그인 화면으로 이동
        if((this.userid == null || this.userid == "") || (this.usertype == null || this.usertype == "")) {
            alert("세션이 만료되었습니다. 다시 로그인 해주세요.")
            this.$router.push('/');
        }
    },
    data() {
        return {
            userid: '',
            usertype: '',
          
            // 데이터테이블 컴포넌트로 보낼 데이터형태, 라우터로 넘겼을 때 사용
            // dataTableJsonData: { 
            //     userid: this.$route.query.userid,
            //     usertype: this.$route.query.usertype
            // },

            dataTableJsonData: { 
                // 세션에서 가지고 온 userid, usertype을 datatable 컴포넌트에 놓아줌 
                userid: sessionStorage.getItem("userid"),
                usertype: sessionStorage.getItem("usertype")
            },
        };
    },
    methods: {
        boardWrite() {
            console.log("BoardMain - boardWrite START");
            //this.$router.push({ path: '/boardWrite', query: { userid: this.userid, usertype: this.usertype}});
            this.$router.push('/boardWrite');
        },
    }
}
</script>

<style src="../assets/css/NoticeBoardStyles.css" scoped></style>
