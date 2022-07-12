import axios from 'axios';
import store from './store';
import router from './router';

//设置全局axios默认值

//axios.defaults.timeout = 150000; //15000的超时验证
axios.defaults.headers.post['Content-Type'] = 'application/json;charset=UTF-8';

//创建一个axios实例
const instance = axios.create();
//const _baseUrl = '/tower';
const _baseUrl = 'http://132.232.12.102/'
instance.defaults.headers.get['Content-Type'] = 'application/json;charset=UTF-8';
axios.interceptors.request.use = instance.interceptors.request.use;

//request拦截器
instance.interceptors.request.use(
  config => {
    //每次发送请求之前检测都vuex存有token,那么都要放在请求头发送给服务器
    if (store.state.token) {
      config.headers.Authorization = `${store.state.token}`;
    }
    return config;
  },
  err => {
    return Promise.reject(err);
  }
);
//response拦截器
instance.interceptors.response.use(
  response => {
    if (response.data.code === 403) {
      store.dispatch('UserLogout'); //可能是token过期，清除它
      router.replace({
        //跳转到登录页面
        path: 'login',
        query: {redirect: router.currentRoute.fullPath} // 将跳转的路由path作为参数，登录成功后跳转到该路由
      });
    }

    return response;
  },
  error => {
    //默认除了2XX之外的都是错误的，就会走这里
    if (error.response) {
      switch (error.response.status) {
        case 401:
          store.dispatch('UserLogout'); //可能是token过期，清除它
          router.replace({
            //跳转到登录页面
            path: 'login',
            query: {redirect: router.currentRoute.fullPath} // 将跳转的路由path作为参数，登录成功后跳转到该路由
          });
      }
    }
    return Promise.reject(error.response);
  }
);

export default {
  getBaseUrl() {
    return _baseUrl;
  },
  //用户登录
  userLogin(data) {
    return instance.post(_baseUrl + '/api/login', data);
  },
  //退出登录
  userLogOut() {
    return instance.get(_baseUrl + '/api/logout'); //线上没有问题  本地404
  },
  //获取台账信息
  getStandBook(param) {
    return instance.get(_baseUrl + '/api/standBook', param);
  },
  //获取待付款合同信息
  getToPayContract(param) {
    return instance.get(_baseUrl + '/api/toPayContract', param);
  },
  //获取台账信息
  getRenewContract(param) {
    return instance.get(_baseUrl + '/api/renewContract', param);
  },
  //获取台账信息
  getOverTimeContract(param) {
    return instance.get(_baseUrl + '/api/overTimeContract', param);
  },
  executeTaskStandBook() {
    return instance.put(_baseUrl + '/api/task');
  },
  deleteStandBook(id) {
    return instance.delete(_baseUrl + '/api/standBook/' + id);
  },
  updateStandBook(data) {
    return instance.put(_baseUrl + '/api/standBook', data);
  },
  getStatistic() {
    return instance.get(_baseUrl + '/api/statistic');
  },
  exportData() {
    return instance.get(_baseUrl + '/api/export', {responseType: 'blob'});
  }

};
