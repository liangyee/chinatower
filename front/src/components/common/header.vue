<template>
  <div class="header">
    <el-row :gutter="10">
      <!--Logo area-->
      <el-col :xs="12" :sm="12" :md="12">
        <div class="logo">
          <span class="logo_prefix">China  </span><span class="logo_suffix">Tower</span>
        </div>
      </el-col>

      <el-col :xs="12" :sm="12" :md="12">
        <div class="user-header">
          <el-dropdown trigger="click" menu-align="start">
            <img src="../../../static/images/themes_tower_images_echart.jpg" width="50px"/>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item>
                <div class="setting-div">
                  <span class="setting-icon"><i class="material-icons">settings</i></span>
                  <span class="setting-string"> Settings  设置</span>
                </div>
              </el-dropdown-item>
              <el-dropdown-item divided>
                <div class="setting-div" @click="logout">
                  <span class="setting-icon"><i class="material-icons">assignment_return</i></span>
                  <span class="setting-string"> Sign out  退出</span>
                </div>
              </el-dropdown-item>

            </el-dropdown-menu>
          </el-dropdown>


        </div>


      </el-col>
    </el-row>


  </div>


</template>

<script>
  import api from '../../axios.js';

  export default {
    methods: {
      logout() {
        this.$store.commit('UserLogout');
        this.$router.push('/login');
        api
          .userLogOut()
          .then(data => {
            console.log(data);
          })
          .catch(error => {
            console.log(error);
          });
      }
    }

  }
</script>
<style scoped>
  .header {
    position: fixed;
    box-sizing: border-box;
    width: 100%;
    height: 66px;

    font-size: 14px;
    line-height: 66px;
    background-color: #d3d9e2;
    box-shadow: 2px 0 3px rgba(0, 0, 0, .5);
    z-index: 1000;
    padding: 0 32px 0 40px;
    overflow: hidden;

  }

  .header .logo {
    float: left;
    cursor: pointer;
    font-size: 22px;
  }

  .logo_prefix {
    color: #ff4949;
  }

  .logo_suffix {
    color: #fff;
  }

  .website span {
    display: inline-block;
    color: #209e91;
  }

  .website span:first-child {
    color: #fff;
  }

  .header-right span {
    display: inline-block;
    margin-left: 14px;
    font-size: 18px;
    color: #fff;
    line-height: 1px;
    height: 18px;
    cursor: pointer;

  }

  .header-right span:hover {
    color: #209e91;

  }

  .user-header {
    float: right;
    line-height: 10px;
    padding-top: 7px;
    font-size: 12px;
  }

  .user-header img {
    border-radius: 50%;
    cursor: pointer;
  }

  .pop-image img {
    border-radius: 50%;
  }

  .setting-div {
    height: 30px;
    line-height: 30px;
    clear: both;
  }

  .setting-div span {
    display: block;
    float: left;
    font-size: 12px;
  }

  .setting-icon {
    padding-top: 3px;
    padding-right: 3px;
  }


</style>
