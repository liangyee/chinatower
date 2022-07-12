<template>
  <div class="login-wrapper">
    <el-row>
      <el-col :span="24">
        <div class="logo"></div>
      </el-col>
    </el-row>

    <div class="container">
      <i-form ref="formLogin" :model="formLogin" :rules="formLoginRules" class="card-box">
        <Form-item class="formLogin-title">
          <h3>系统登录</h3>
        </Form-item>
        <Form-item prop="username">
          <i-input size="large" type="text" v-model="formLogin.username" placeholder="用户名">
            <Icon type="ios-person-outline" slot="prepend"></Icon>
          </i-input>
        </Form-item>
        <Form-item prop="password">
          <i-input size="large" type="password" v-model="formLogin.password" placeholder="密码">
            <Icon type="ios-locked-outline" slot="prepend"></Icon>
          </i-input>
        </Form-item>
        <Form-item class="login-no-bottom">
          <Row class="cell-primary around-justify">
            <button class="button active" @click.prevent="handleSubmit('formLogin')">登录</button>
            <button class="button active" @click.prevent="formLoginReset('formLogin')">重置</button>
          </Row>
        </Form-item>
      </i-form>
    </div>
    <div class="footer"> Copyright 2018 中国铁塔股份有限公司景德镇市分公司 ALL Rights Reserved</div>
  </div>
</template>
<script>
  import api from '../../axios.js';

  export default {
    data() {
      return {
        modal10: false,
        formLogin: {
          username: '',
          password: ''
        },
        formLoginRules: {
          username: [
            {
              required: true,
              message: '请填写用户名',
              trigger: 'blur'
            }
          ],
          password: [
            {
              required: true,
              message: '请填写密码',
              trigger: 'blur'
            },
            {
              type: 'string',
              min: 6,
              message: '密码长度不能小于6位',
              trigger: 'blur'
            }
          ]
        }
      };
    },
    mounted() {
      this.$el.style.height = window.innerHeight + 'px';
    },
    methods: {
      handleSubmit(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) { //验证通过
            let opt = this.formLogin;
            api.userLogin(opt)
              .then(({
                       data
                     }) => {
                if (data.message !== "login success") {
                  this.$Message.error('账号或密码不正确，请重新输入');
                  //alert('账号或密码不正确，请重新输入')
                  return;
                }
                //账号存在
                if (data.message === "login success") {
                  let token = data.results[0].token;
                  //let username = data.username;
                  this.$store.commit('UserLogin', token);
                  //this.$store.commit('UserName', username);
                  let redirectUrl = decodeURIComponent(this.$route.query.redirect || '/')
                  //跳转到指定的路由
                  this.$router.push({
                    path: redirectUrl
                  });
                  //如果用户手动输入"/"那么会跳转到这里来，即this.$route.query.redirect有参数
                  //this.getPersonMes()
                } else {
                  //alert('登录失败')
                  this.$Message.error('登录失败');
                }
              });
          } else {
            //验证不通过
            return false;
          }
        });
      },
      formLoginReset(name) {
        this.$refs[name].resetFields();
      }
    }
  };
</script>
<style lang="less" src="../../assets/style/iview-custom.less"></style>
<style lang="less" src="../../assets/style/button.less"></style>
<style lang="less" src="../../assets/style/grid.less"></style>
<style lang="less" src="../../assets/style/login.less"></style>
<style type="text/css">
  .vertical-center-modal {
    display: flex;
    align-items: center;
    justify-content: center;

  .ivu-modal {
    top: 0;
  }

  }
</style>
