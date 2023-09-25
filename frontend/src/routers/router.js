import { createRouter, createWebHistory } from 'vue-router';

// 라우트 컴포넌트를 import합니다.
import BoardLogin from '../components/BoardLogin.vue';
import BoardRegister from '../components/BoardRegister.vue';
import BoardWrite from '../components/BoardWrite.vue';
import BoardMain from '../components/BoardMain.vue';
import BoardDetail from '../components/BoardDetail.vue';
import BoardAccount from '../components/BoardAccount.vue';

const routes = [
  {
    path: '/',
    name: 'BoardLogin',
    component: BoardLogin,
  },
  {
    path: '/boardMain',
    name: 'BoardMain',
    component: BoardMain,
  },
  {
    path: '/boardRegister',
    name: 'BoardRegister',
    component: BoardRegister,
  },
  {
    path: '/boardWrite',
    name: 'BoardWrite',
    component: BoardWrite,
  },
  {
    path: '/boardDetail',
    name: 'BoardDetail',
    component: BoardDetail,
  },
  {
    path: '/boardAccount',
    name: 'BoardAccount',
    component: BoardAccount,
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;