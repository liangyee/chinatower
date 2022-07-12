import Vue from 'vue';
import Vuex from 'vuex';
import mutations from './mutations';
import actions from './actions';
import state from './state';
import getters from './getters';
Vue.use(Vuex);
//初始化时用sessionStore.getItem('token'),这样子刷新页面就无需重新登录
export default new Vuex.Store({
  state,
  getters,
  mutations,
  actions
});
