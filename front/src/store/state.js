//初始化时用sessionStore.getItem('token'),这样子刷新页面就无需重新登录
export default {
  token: window.sessionStorage.getItem('token'),
  statistic: {}
}
;
