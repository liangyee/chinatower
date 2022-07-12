// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue';
import Vuex from 'vuex';
import VueRouter from 'vue-router';
import App from './App';
import router from './router';
import ElementUI from 'element-ui';
import iView from 'iview';
import axios from 'axios';
import store from './store';
//引入element-ui的默认CSS样式
import 'element-ui/lib/theme-default/index.css';

Vue.prototype.$ajax = axios;
Vue.use(ElementUI);
Vue.use(iView);
Vue.use(Vuex);
Vue.use(VueRouter);
Vue.config.productionTip = false


/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  store,
  template: '<App/>',
  components: {App},
  beforeCreate: function () {
    console.log('beforeCreated.....');
  }
})
