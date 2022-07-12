<template>
  <div>
    <v-pageTitle vtitle="待付款合同信息"></v-pageTitle>
    <el-row :gutter="20">
      <el-col :xs="24" :sm="24" :md="24" :lg="24">
        <el-card class="box-card">
          <toPayContractTable>
          </toPayContractTable>
        </el-card>
      </el-col>
    </el-row>
  </div>


</template>

<script>
  import vPageTitle from '../common/pageTitle.vue';
  import toPayContractTable from '../tables/toPayContractTable';

  export default {
    components: {
      vPageTitle, toPayContractTable
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
