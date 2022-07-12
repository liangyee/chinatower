<template>
  <div>
    <v-pageTitle vtitle="逾期未付款合同信息"></v-pageTitle>
    <el-row :gutter="20">
      <el-col :xs="24" :sm="24" :md="24" :lg="24">
        <el-card class="box-card">
          <overTimeContractTable>
          </overTimeContractTable>
        </el-card>
      </el-col>
    </el-row>
  </div>


</template>

<script>
  import vPageTitle from '../common/pageTitle.vue';
  import overTimeContractTable from '../tables/overTimeContractTable';

  export default {
    components: {
      vPageTitle, overTimeContractTable
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
