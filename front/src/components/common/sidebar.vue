<template>
  <div class="sidebar">
    <el-menu :default-active="onRoutes" class="el-menu-vertical-demo" style="background-color:#d3d9e2" unique-opened
             router>
      <el-menu-item index="StatisticCharts">

        <span class="sb-icon"><i class="material-icons">home</i></span>
        <span class="sb-cn">首页</span>
      </el-menu-item>

      <el-submenu index="2">
        <template slot="title">

          <span class="sb-icon"><i class="material-icons">perm_data_setting</i></span>
          <span class="sb-cn">合同详情</span>

        </template>
        <el-menu-item index="StandBook">台账信息</el-menu-item>
        <el-menu-item index="ToPayContract">待付款合同信息</el-menu-item>
        <el-menu-item index="RenewContract">待续约合同信息</el-menu-item>
        <el-menu-item index="OverTimeContract">逾期未付款合同信息</el-menu-item>


      </el-submenu>
      <el-menu-item index="SystemControl">

        <span class="sb-icon"><i class="material-icons">settings</i></span>
        <span class="sb-cn">系统操作</span>
      </el-menu-item>

    </el-menu>
  </div>
</template>
<script>
  export default {
    computed: {
      onRoutes() {
        return this.$route.path.replace('/', '');
      }
    }
  }
</script>
<style scoped>
  .sidebar {
    display: block;
    position: absolute;
    width: 200px;
    left: 0;
    top: 66px;
    bottom: 0;
    background: #324157;

  }

  .sidebar > ul {
    height: 100%;
  }

  .el-menu-item, .el-submenu__title {
    font-size: 14px;

  }

  .material-icons {
    font-size: 12px;
  }
</style>
