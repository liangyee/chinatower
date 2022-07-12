<template>
  <div v-cloak>
    <v-pageTitle vtitle="统计图表"></v-pageTitle>

    <div class="clear"></div>

    <el-row :gutter="20">

      <el-col :xs="8" :sm="8" :md="8" :lg="8">
        <el-card class="box-card">
          <el-row :gutter="6">
            <el-col :xs="24" :sm="11" :md="12" :lg="12">
              <div class="cart-string">
                <span>待付款合同</span>
                <span @click="toPayClick()" style="cursor:pointer"><h2 style="color: #7272d2;">{{topay}}</h2></span>
              </div>
            </el-col>
            <el-col :xs="24" :sm="5" :md="4" :lg="4">
              <div>
                <i class="material-icons">attach_money</i>
              </div>
            </el-col>
          </el-row>
        </el-card>
      </el-col>

      <el-col :xs="8" :sm="8" :md="8" :lg="8">
        <el-card class="box-card">
          <el-row :gutter="6">
            <el-col :xs="24" :sm="11" :md="12" :lg="12">
              <div class="cart-string">
                <span>待续签合同</span>
                <span @click="renewClick()" style="cursor:pointer"><h2 style="color: rgb(214, 182, 24);">{{renew}}</h2></span>
              </div>
            </el-col>
            <el-col :xs="24" :sm="5" :md="4" :lg="4">
              <div>
                <i class="material-icons">attach_money</i>
              </div>
            </el-col>
          </el-row>
        </el-card>
      </el-col>

      <el-col :xs="8" :sm="8" :md="8" :lg="8">
        <el-card class="box-card">
          <el-row :gutter="6">
            <el-col :xs="24" :sm="11" :md="12" :lg="12">
              <div class="cart-string">
                <span>逾期付款合同</span>
                <span @click="overtimeClick()" style="cursor:pointer"><h2
                  style="color: rgb(214,19,46);">{{overtime}}</h2></span>
              </div>
            </el-col>
            <el-col :xs="24" :sm="5" :md="4" :lg="4">
              <div>
                <i class="material-icons">attach_money</i>
              </div>
            </el-col>
          </el-row>
        </el-card>
      </el-col>
    </el-row>


    <el-row :gutter="20">
      <el-col :xs="24" :sm="24" :md="24" :lg="12">
        <el-card class="box-chart">
          <pieRentChart :statistic="statistic"></pieRentChart>
        </el-card>
      </el-col>
      <el-col :xs="24" :sm="24" :md="24" :lg="12">
        <el-card class="box-chart">
          <pieCountChart :statistic="statistic"></pieCountChart>
        </el-card>
      </el-col>

    </el-row>


    <el-row :gutter="20">
      <el-col :xs="24" :sm="24" :md="24" :lg="12">
        <el-card class="box-chart">
          <lineAvgChart :statistic="statistic"></lineAvgChart>
        </el-card>
      </el-col>
      <el-col :xs="24" :sm="24" :md="24" :lg="12">
        <el-card class="box-chart">
          <lineTaxChart :statistic="statistic"></lineTaxChart>
        </el-card>
      </el-col>

    </el-row>

  </div>


</template>

<script>
  import vPageTitle from '../common/pageTitle.vue';
  import pieRentChart from '../charts/pieRentChart.vue';
  import pieCountChart from '../charts/pieCountChart.vue';
  import lineTaxChart from '../charts/lineTaxChart.vue';
  import lineAvgChart from '../charts/lineAvgChart.vue';
  import api from '../../axios.js';

  export default {
    inject: ['reload'],
    data() {
      return {
        statistic: {},
        topay: 0,
        renew: 0,
        overtime: 0,
      }
    },
    components: {
      vPageTitle,
      pieRentChart,
      pieCountChart,
      lineAvgChart,
      lineTaxChart
    },
    mounted() {
    },
    created() {
      if (window.localStorage.getItem("statistic") == null) {
        this.getStatisticData();
      } else {
        this.statistic = JSON.parse(window.localStorage.getItem("statistic"));
        this.topay = this.statistic.toPay;
        this.renew = this.statistic.renew;
        this.overtime = this.statistic.overTime;
      }

    },
    methods: {
      getStatisticData() {
        api.getStatistic()
          .then(data => {
            let statistic = data.data.results[0];
            this.statistic = statistic;
            this.topay = this.statistic.toPay;
            this.renew = this.statistic.renew;
            this.overtime = this.statistic.overTime;
            this.$store.commit('Statistic', statistic);
            location.reload();
          }).catch(err => {
          console.log(err)
        })
      },
      toPayClick() {
        this.$router.push({
          path: '/ToPayContract'
        });
      },
      renewClick() {
        this.$router.push({
          path: '/RenewContract'
        });
      },
      overtimeClick() {
        this.$router.push({
          path: '/OverTimeContract'
        });
      }
    }

  }
</script>

<style scoped>
  .el-col {
    margin-bottom: 16px;
    text-align: center;
  }

  .material-icons {
    font-size: 80px;
    color: #ddd;
  }

  .box-card {
    height: 110px;
  }

  .cart-string {
    height: 100px;
    padding-top: 10px;
    font-size: 1.1rem;

  }

  .box-chart {
    height: 420px;
  }

  .box-list {
    height: auto;
    text-align: left;
  }

  .box-list hr {
    height: 1px;
    border: none;
    border-top: 1px dashed #ccc;
    margin-bottom: 5px;
    margin-top: 6px;

  }
</style>
