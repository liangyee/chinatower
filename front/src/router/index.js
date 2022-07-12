import Vue from 'vue';
import Router from 'vue-router';
import store from '../store/index.js'
import Login from '@/components/common/Login'
import Home from '@/components/common/Home';
import StatisticCharts from '@/components/page/StatisticCharts';
import SystemControl from '@/components/page/SystemControl';
import StandBook from '@/components/page/StandBook';
import ToPayContract from '@/components/page/ToPayContract';
import RenewContract from '@/components/page/RenewContract';
import OverTimeContract from '@/components/page/OverTimeContract';

Vue.use(Router)

const router = new Router({
  mode: 'history',
  base: __dirname,
  routes: [
    {
      path: '/login',
      component: Login,
      name: 'login'
    },
    {
      path: '/',
      component: Home,
      meta: {
        requiresAuth: true
      },
      redirect: '/StatisticCharts',
      children: [
        {
          path: '/StatisticCharts',
          component: StatisticCharts,
          name: '统计图表信息',
          meta: {
            requiresAuth: true
          }
        }, {
          path: '/StandBook',
          component: StandBook,
          meta: {
            requiresAuth: true
          }
        }, {
          path: '/ToPayContract',
          component: ToPayContract,
          meta: {
            requiresAuth: true
          }
        }, {
          path: '/RenewContract',
          component: RenewContract,
          meta: {
            requiresAuth: true
          }
        }, {
          path: '/OverTimeContract',
          component: OverTimeContract,
          meta: {
            requiresAuth: true
          }
        },{
          path: '/SystemControl',
          component: SystemControl,
          meta: {
            requiresAuth: true
          }
        }
      ]
    }
  ]
})
router.beforeEach((to, from, next) => {
  //获取store里面的token
  let token = store.state.token;
  //判断要去的路由有没有requiresAuth
  if (to.meta.requiresAuth) {
    if (token) {
      next();
    } else {
      next({
        path: '/login',
        query: {
          redirect: to.fullPath
        } // 将刚刚要去的路由path（却无权限）作为参数，方便登录成功后直接跳转到该路由
      });
    }

  } else {
    next(); //如果无需token,那么随它去吧
  }
});
router.afterEach(route => {
  //iView.LoadingBar.finish();
});
export default router;
