<template>
  <div>
    <v-pageTitle vtitle="待续约合同信息"></v-pageTitle>
    <el-row :gutter="20">
      <el-col :xs="24" :sm="24" :md="24" :lg="24">
        <el-card class="box-card">
          <renewContractTable>
          </renewContractTable>
        </el-card>
      </el-col>
    </el-row>
  </div>


</template>

<script>
  import vPageTitle from '../common/pageTitle.vue';
  import renewContractTable from '../tables/renewContractTable';

  export default {
    components: {
      vPageTitle, renewContractTable
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
