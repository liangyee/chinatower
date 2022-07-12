<template>
  <div>
    <div>
      <el-row type="flex" align="middle">
        <el-col :xs="3" :sm="3" :md="3" :lg="3">
          <span class="customFont" style="margin-left: 30px">站点名称 :</span>
        </el-col>
        <el-col :xs="10" :sm="10" :md="10" :lg="10">
          <Input v-model="filter.siteName" style="width: 200px">
          <Button slot="append" icon="ios-search" @click="search"></Button>
          </Input>
        </el-col>
      </el-row>
    </div>
    <div>
      <el-table
        :data="standBookList"
        height="490"
        stripe
        style="width: 100%">
        <el-table-column type="expand">

          <template slot-scope="props">
            <el-form label-position="left" inline class="demo-table-expand">
              <el-form-item label="所属区域">
                <span>{{ props.row.region }}</span>
              </el-form-item>
              <el-form-item label="站点编码">
                <span>{{ props.row.code }}</span>
              </el-form-item>
              <el-form-item label="站点名称">
                <span>{{ props.row.siteName }}</span>
              </el-form-item>
              <el-form-item label="合同名称">
                <span>{{ props.row.contractName }}</span>
              </el-form-item>
              <el-form-item label="合同开始时间">
                <span>{{ props.row.contractStart|formatDate}}</span>
              </el-form-item>
              <el-form-item label="合同结束时间">
                <span>{{ props.row.contractEnd|formatDate}}</span>
              </el-form-item>
              <el-form-item label="ID" style="display:none">
                <span>{{ props.row.id }}</span>
              </el-form-item>

            </el-form>
          </template>
        </el-table-column>
        <el-table-column
          label="站点编码"
          prop="code">
        </el-table-column>
        <el-table-column
          label="站点名称"
          prop="siteName">
        </el-table-column>
        <el-table-column
          label="合同名称"
          prop="contractName">
        </el-table-column>
      </el-table>
    </div>
    <div class="block">
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="filter.curPage"
        :page-sizes="[10, 20, 40, 80]"
        :page-size="filter.pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total">
      </el-pagination>
    </div>
  </div>
</template>

<style>
  .demo-table-expand {
    font-size: 0;
  }

  .demo-table-expand label {
    width: 120px;
    color: #99a9bf;
  }

  .demo-table-expand .el-form-item {
    margin-right: 0 !important;
    margin-bottom: 0;
    width: 50%;
  }

  .el-pagination {
    float: right;
    margin-top: 15px;
  }

  .el-select .el-input {
    width: 130px;
  }

  .input-with-select .el-input-group__prepend {
    background-color: #fff;
  }
</style>

<script>
  import api from '../../axios';
  import moment from 'moment';

  export default {
    methods: {
      handleClick(row) {
        console.log(row);
      },
      getStandBookList(param) {
        var _getAll = this;
        api.getRenewContract(param).then(res => {
          _getAll.total = res.data.total;
          _getAll.standBookList = res.data.results;
        })
      },

      handleSizeChange(val) {
        this.filter.pageSize = val;
        this.getStandBookList({params: this.filter});
      },
      handleCurrentChange(val) {
        this.filter.curPage = val;
        this.getStandBookList({params: this.filter});
      },
      search(){
        if(this.filter.curPage===1){
          this.getStandBookList({params: this.filter});
        }else{
          this.filter.curPage = 1;
        }

      }
    },
    data: function () {
      return {
        standBookList: [],
        total: 0,
        filter:{
          curPage: 1,
          pageSize: 10,
          siteName: '',
        }
      }
    },
    filters: {
      //时间格式化
      formatDate(allupdatetime) {
        var date = new Date(allupdatetime);
        return moment(date).format('YYYY-MM-DD');
      }
    },
    mounted() {
      this.getStandBookList({params: this.filter});
    }

  }
</script>
