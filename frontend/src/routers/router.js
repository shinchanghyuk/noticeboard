import { createRouter, createWebHistory } from 'vue-router';

// 라우트 컴포넌트를 import합니다.
import BoardLogin from '../components/BoardLogin.vue';
import BoardRegister from '../components/BoardRegister.vue';
import BoardWrite from '../components/BoardWrite.vue';
import BoardMain from '../components/BoardMain.vue';
import BoardDetail from '../components/BoardDetail.vue';
import BoardAccount from '../components/BoardAccount.vue';
import BoardNotPage from '../components/BoardNotPage.vue';

const routes = [
  {
    path: '/login',
    name: 'BoardLogin',
    component: BoardLogin,
  },
  {
    path: '/',
    redirect: '/login',
  },
  {
    path: '/main',
    name: 'BoardMain',
    component: BoardMain,
  },
  {
    path: '/register',
    name: 'BoardRegister',
    component: BoardRegister,
  },
  {
    path: '/write',
    name: 'BoardWrite',
    component: BoardWrite,
    alias: '/write/:id',
  },
  {
    path: '/detail/:id',
    name: 'BoardDetail',
    component: BoardDetail,
  },
  {
    path: '/account',
    name: 'BoardAccount',
    component: BoardAccount,
  },
  {
    path: "/notPage",
    name: "BoardNotPage",
    component: BoardNotPage,
  },
  {
    path: "/:pathMatch(.*)*",
    redirect: "/notPage"
    }
];

//
//const routes = [
//  {
//    path: '/noticeboard',
//    name: 'BoardLogin',
//    component: BoardLogin,
//  },
//  {
//    path: '/',
//    redirect: '/noticeboard',
//  },
//  {
//    path: '/noticeboard/boardMain',
//    name: 'BoardMain',
//    component: BoardMain,
//  },
//  {
//    path: '/noticeboard/boardRegister',
//    name: 'BoardRegister',
//    component: BoardRegister,
//  },
//  {
//    path: '/noticeboard/boardWrite',
//    name: 'BoardWrite',
//    component: BoardWrite,
//  },
//  {
//    path: '/noticeboard/boardDetail',
//    name: 'BoardDetail',
//    component: BoardDetail,
//  },
//  {
//    path: '/noticeboard/boardAccount',
//    name: 'BoardAccount',
//    component: BoardAccount,
//  },
//  {
//    path: "/noticeboard/boardNotPage",
//    name: "BoardNotPage",
//    component: BoardNotPage,
//  },
//  {
//    path: "/:pathMatch(.*)*",
//    redirect: "/noticeboard/boardNotPage"
//    }
//];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;


// {
//   path: process.env.VUE_APP_TYPE === 'production'
//     ? '/noticeboard'
//   : '/noticeboard',
//   name: 'BoardLogin',
//   component: BoardLogin,
// },
// {
//   path: process.env.VUE_APP_TYPE === 'production'
//     ? '/noticeboard/boardMain'
//   : '/noticeboard/boardMain',
//   name: 'BoardMain',
//   component: BoardMain,
// },
// {
//   path: process.env.VUE_APP_TYPE === 'production'
//     ? '/noticeboard/boardRegister'
//   : '/noticeboard/boardRegister',
//   name: 'BoardRegister',
//   component: BoardRegister,
// },
// {
//   path: process.env.VUE_APP_TYPE === 'production'
//     ? '/noticeboard/boardWrite'
//   : '/noticeboard/boardWrite',
//   name: 'BoardWrite',
//   component: BoardWrite,
// },
// {
//   path: process.env.VUE_APP_TYPE === 'production'
//     ? '/noticeboard/boardDetail'
//   : '/noticeboard/boardDetail',
//   name: 'BoardDetail',
//   component: BoardDetail,
// },
// {
//   path: process.env.VUE_APP_TYPE === 'production'
//     ? '/noticeboard/boardAccount'
//   : '/noticeboard/boardAccount',
//   name: 'BoardAccount',
//   component: BoardAccount,
// },