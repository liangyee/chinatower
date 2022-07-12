<template>
  <div>
    <div>
      <el-row type="flex" align="middle">
        <el-col :xs="3" :sm="3" :md="3" :lg="3">
          <span class="customFont">所属区域 :</span>
        </el-col>
        <el-col :xs="5" :sm="5" :md="5" :lg="5">
          <el-select v-model="filter.region" placeholder="请选择区域">
            <el-option label="乐平市" value="乐平市"></el-option>
            <el-option label="昌江区" value="昌江区"></el-option>
            <el-option label="浮梁县" value="浮梁县"></el-option>
            <el-option label="珠山区" value="珠山区"></el-option>
          </el-select>
        </el-col>
        <el-col :xs="3" :sm="3" :md="3" :lg="3">
          <span class="customFont">站点编码 :</span>
        </el-col>
        <el-col :xs="5" :sm="5" :md="5" :lg="5">
          <Input v-model="filter.code" style="width: 200px">
          </Input>
        </el-col>
      </el-row>
      <el-row type="flex" align="middle">
        <el-col :xs="3" :sm="3" :md="3" :lg="3">
          <span class="customFont">站点名称 :</span>
        </el-col>
        <el-col :xs="5" :sm="5" :md="5" :lg="5">
          <Input v-model="filter.siteName" style="width: 200px">
          </Input>
        </el-col>
        <el-col :xs="3" :sm="3" :md="3" :lg="3">
          <span class="customFont">付款时间 :</span>
        </el-col>
        <el-col :xs="7" :sm="7" :md="7" :lg="7">
          <DatePicker type="daterange"
                      :value="time"
                      :split-panels=false
                      @on-change="handleChange"
                      placement="bottom-end"
                      placeholder="选择时间范围"
                      size="large"
                      style="width: 200px;height:40px"></DatePicker>
          </Input>
        </el-col>
        <el-col :xs="2" :sm="2" :md="2" :lg="2">
          <Button type="primary" @click="search">查询</Button>
        </el-col>
        <el-col :xs="2" :sm="2" :md="2" :lg="2">
          <Button type="primary" @click="reset">重置</Button>
        </el-col>
        <el-col :xs="2" :sm="2" :md="2" :lg="2">
          <Button type="primary" @click="exportData">导出</Button>
        </el-col>
      </el-row>

    </div>
    <div>
      <el-dialog title="台账信息" :visible.sync="dialogFormVisible" @close="closeDialog">
        <el-form :model="form" :inline="true" label-position="right" label-width="100px" class="demo-from-expand">
          <el-form-item label="所属区域">
            <el-select v-model="form.region" placeholder="请选择区域">
              <el-option label="乐平市" value="乐平市"></el-option>
              <el-option label="昌江区" value="昌江区"></el-option>
              <el-option label="浮梁县" value="浮梁县"></el-option>
              <el-option label="珠山区" value="珠山区"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="站点编码">
            <el-input v-model="form.code" auto-complete="off"></el-input>
          </el-form-item>
          <el-form-item label="站点名称">
            <el-input v-model="form.siteName" auto-complete="off"></el-input>
          </el-form-item>
          <el-form-item label="合同属性">
            <el-input v-model="form.attribute" auto-complete="off"></el-input>
          </el-form-item>
          <el-form-item label="合同名称">
            <el-input v-model="form.contractName" auto-complete="off"></el-input>
          </el-form-item>
          <el-form-item label="业主">
            <el-input v-model="form.ownerName" auto-complete="off"></el-input>
          </el-form-item>
          <el-form-item label="联系方式">
            <el-input v-model="form.phoneNumber" auto-complete="off"></el-input>
          </el-form-item>
          <el-form-item label="合同开始时间">
            <el-date-picker
              v-model="form.contractStart"
              type="date"
              placeholder="选择日期">
            </el-date-picker>
          </el-form-item>
          <el-form-item label="合同结束时间">
            <el-date-picker
              v-model="form.contractEnd"
              type="date"
              placeholder="选择日期">
            </el-date-picker>
          </el-form-item>
          <el-form-item label="付款时间">
            <el-date-picker
              v-model="form.payTime"
              type="date"
              placeholder="选择日期">
            </el-date-picker>
          </el-form-item>
          <el-form-item label="开始时间">
            <el-date-picker
              v-model="form.start"
              type="date"
              placeholder="选择日期">
            </el-date-picker>
          </el-form-item>
          <el-form-item label="结束时间">
            <el-date-picker
              v-model="form.end"
              type="date"
              placeholder="选择日期">
            </el-date-picker>
          </el-form-item>
          <el-form-item label="房租">
            <el-input v-model="form.rent" auto-complete="off"></el-input>
          </el-form-item>
          <el-form-item label="税金">
            <el-input v-model="form.tax" auto-complete="off"></el-input>
          </el-form-item>
          <el-form-item label="ID" style="display:none">
            <span>{{ form.id }}</span>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="closeDialog">取 消</el-button>
          <el-button type="primary" @click="editStandBook">确 定</el-button>
        </div>
      </el-dialog>
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
              <el-form-item label="合同属性">
                <span>{{ props.row.attribute }}</span>
              </el-form-item>
              <el-form-item label="合同名称">
                <span>{{ props.row.contractName }}</span>
              </el-form-item>
              <el-form-item label="业主">
                <span>{{ props.row.ownerName }}</span>
              </el-form-item>
              <el-form-item label="联系方式">
                <span>{{ props.row.phoneNumber }}</span>
              </el-form-item>
              <el-form-item label="合同开始时间">
                <span>{{ props.row.contractStart|formatDate}}</span>
              </el-form-item>
              <el-form-item label="合同结束时间">
                <span>{{ props.row.contractEnd|formatDate}}</span>
              </el-form-item>
              <el-form-item label="付款时间">
                <span>{{ props.row.payTime|formatDate }}</span>
              </el-form-item>
              <el-form-item label="开始时间">
                <span>{{ props.row.start|formatDate }}</span>
              </el-form-item>
              <el-form-item label="结束时间">
                <span>{{ props.row.end|formatDate }}</span>
              </el-form-item>
              <el-form-item label="房租">
                <span>{{ props.row.rent }} 元</span>
              </el-form-item>
              <el-form-item label="税金">
                <span>{{ props.row.tax }} 元</span>
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
        <el-table-column
          fixed="right"
          label="操作"
          width="100">
          <template slot-scope="scope">
            <el-button @click="handleClick(scope.row)" type="text" size="small">编辑</el-button>
            <el-button @click="deleteStandBook(scope.row.id)" type="text" size="small">删除</el-button>
          </template>
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

  .demo-from-expand {
    font-size: 0;
  }

  .demo-from-expand label {
    width: 120px;
    color: #99a9bf;
  }

  .demo-from-expand .el-form-item {
    margin-right: 0 !important;

    width: 50%;
  }

  .el-select .el-input {
    width: 200px !important;
  }

  .el-pagination {
    float: right;
    margin-top: 15px;
  }
</style>

<script>
  import api from '../../axios';
  import moment from 'moment';

  export default {
    methods: {
      handleClick(row) {
        this.dialogFormVisible = true;
        this.form = row;
      },
      editStandBook() {
        api.updateStandBook(this.form).then(res => {
          if (res.data.message === 'success') {
            this.$message.info("更新成功！");
            this.search();
          } else {
            this.$message.warning("更新失败！");
          }
        })
        this.search();
        this.dialogFormVisible = false;
      },
      closeDialog() {
        this.dialogFormVisible = false;
        this.search();
      },
      deleteStandBook(id) {
        this.$Modal.confirm({
          title: '删除台账',
          content: '<p>确认要删除该条台账信息吗?</p>',
          onOk: () => {
            api.deleteStandBook(id).then(res => {
              if (res.data.message === 'success') {
                this.$message.info("删除成功！");
                this.search();
              } else {
                this.$message.warning("删除失败！");
              }
            })
          },
          onCancel: () => {
          }
        });

      },
      getStandBookList(param) {
        var _getAll = this;
        api.getStandBook(param).then(res => {
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
      handleChange(date1) {
        this.filter.startTime = date1[0];
        this.filter.endTime = date1[1];
      },
      search() {
        if (this.filter.curPage === 1) {
          this.getStandBookList({params: this.filter});
        } else {
          this.filter.curPage = 1;
        }
      },
      exportData() {
        api.exportData(this.filter).then((res) => {
          let fileName = '台账.xlsx'
          let blob = new Blob([res.data], {type: 'application/x-xlsx'})
          if (window.navigator.msSaveOrOpenBlob) {
            navigator.msSaveBlob(blob, fileName);
          } else {
            var link = document.createElement('a');
            link.href = window.URL.createObjectURL(blob);
            link.download = fileName;
            link.click();
            window.URL.revokeObjectURL(link.href);
          }
        })
      },
      reset() {
        this.time = [];
        this.filter.region = '';
        this.filter.siteName = '';
        this.filter.code = '';
        this.filter.startTime = '';
        this.filter.endTime = '';

      }
    },
    data: function () {
      return {
        dialogFormVisible: false,
        standBookList: [],
        total: 0,
        time: [],
        filter: {
          curPage: 1,
          pageSize: 10,
          siteName: '',
          region: '',
          code: '',
          startTime: '',
          endTime: ''
        },
        form: {
          region: '',
          siteName: '',
          id: '',
          code: '',
          contractNmae: '',
          attribute: '',
          ownerName: '',
          phoneNumber: '',
          contractStart: '',
          contractEnd: '',
          payTime: "",
          start: '',
          end: '',
          rent: '',
          tax: ''
        },
      }
    },
    filters: {
      //时间格式化
      formatDate(allupdatetime) {
        if (allupdatetime === null || allupdatetime === '') {
          return ''
        }
        var date = new Date(allupdatetime);
        return moment(date).format('YYYY-MM-DD');
      }
    },
    mounted() {
      this.getStandBookList({params: this.filter});
    }

  }
</script>
