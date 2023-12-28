import { createApp } from 'vue'
import App from './App.vue'
import axios from 'axios';
import router from './routers/router'; // 생성한 router.js 파일을 import합니다.
//import { createVuetify }  from 'vuetify';

import Vue3EasyDataTable from 'vue3-easy-data-table';
import 'vue3-easy-data-table/dist/style.css';

const app = createApp(App);
app.component('EasyDataTable', Vue3EasyDataTable);

app.provide('$axios', axios);

app.config.productionTip = false
app.use(router);
app.mount('#app');