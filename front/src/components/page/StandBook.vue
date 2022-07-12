<template>
  <div>
    <v-pageTitle vtitle="台账信息"></v-pageTitle>
    <el-row :gutter="20">
      <el-col :xs="24" :sm="24" :md="24" :lg="24">
        <el-card class="box-card">
          <standBookTable>
          </standBookTable>
        </el-card>
      </el-col>
    </el-row>
  </div>


</template>

<script>
  import vPageTitle from '../common/pageTitle.vue';
  import standBookTable from '../tables/standBookTable';

  export default {
    components: {
      vPageTitle, standBookTable
    }
  }
</script>

<style scoped>

  .box-card {
    line-height: 40px;
  }

  .el-col {
    margin-bottom: 16px;
  }

</style>
