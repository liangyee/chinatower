<template>
  <div>
    <v-pageTitle vtitle="系统操作"></v-pageTitle>


    <el-card class="box-card">
      <el-row :gutter="20" style="margin-bottom: 30px" type="flex" align="middle">
        <el-col :xs="12" :sm="12" :md="12" :lg="12">
          <span class="customFont">台账模板下载:</span>
        </el-col>
        <el-col :xs="12" :sm="12" :md="12" :lg="12">
          <el-button type="primary" style="width: 100px" round @click="download">下载</el-button>
        </el-col>
      </el-row>
      <el-row :gutter="20" type="flex" align="middle">
        <el-col :xs="10" :sm="10" :md="10" :lg="10">
          <span class="customFont">台账信息上传:</span>
        </el-col>
        <el-col :xs="12" :sm="12" :md="12" :lg="12">

          <el-upload
            class="upload-demo"
            drag
            action="http://132.232.12.102/api/standbook/import"
            :limit="1"
            :file-list="fileList"
            :on-success="handleResponse">
            <i class="el-icon-upload"></i>
            <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
            <div class="el-upload__tip" slot="tip" style="margin-left: 50px">只能上传xls/xlsx文件，且不超过100Mb</div>
          </el-upload>
        </el-col>

      </el-row>
      <el-row :gutter="20" style="margin-top: 70px" type="flex" align="middle">
        <el-col :xs="12" :sm="12" :md="12" :lg="12">
          <span class="customFont">后台任务执行(生成待付款、待续约等合同信息):</span>
        </el-col>
        <el-col :xs="12" :sm="12" :md="12" :lg="12">
          <el-button type="primary" style="width: 100px" round @click="executeTask">执行</el-button>
        </el-col>
      </el-row>
    </el-card>


  </div>


</template>

<script>
  import vPageTitle from '../common/pageTitle.vue';
  import api from '../../axios';
  import axios from 'axios';

  export default {
    data() {
      return {
        fileList: []
      }
    },
    components: {
      vPageTitle, axios
    },
    methods: {
      download() {
        //let url = '/tower/api/download'
        let url = 'http://132.232.12.102/api/download'
        axios({
          method: 'get',
          url: url,
          responseType: 'blob',
        })
          .then((data) => {
            if (!data) {
              return
            }
            let url = window.URL.createObjectURL(data.data)
            let link = document.createElement('a')
            link.style.display = 'none'
            link.href = url
            link.setAttribute('download', '台账.xlsx')
            document.body.appendChild(link)
            link.click()
          })
      },
      handleResponse(response, file, fileList) {
        //console.log(response);
        this.fileList = [];
        if (response.code === 400) {
          this.$message.warning(response.message);
        }
        if (response.code === 200) {
          this.$message.info("上传成功！");
        }
      },
      executeTask() {
        api.executeTaskStandBook().then(res => {
          if (res.data.code === 200) {
            this.$message.info("执行任务成功！");
          } else {
            this.$message.warning("执行任务失败！");
          }
        })
      }
    }
  }
</script>

<style>
  .el-col {
    margin-bottom: 16px;
  }

  .customFont {
    font-family: "Helvetica Neue";
    font-size: large;
  }
</style>
