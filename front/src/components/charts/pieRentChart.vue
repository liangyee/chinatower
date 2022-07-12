<template>
  <div class="c-charts" v-cloak>
    <IEcharts :option="pie"></IEcharts>
  </div>


</template>

<script>
  import vPageTitle from '../common/pageTitle.vue';
  import IEcharts from 'vue-echarts-v3';

  export default {
    props:['statistic'],
    components: {
      vPageTitle, IEcharts
    },
    data: () => ({
      //statistic: {},
      pie: {
        title: {
          text: '租金统计图'
        },
        tooltip: {
          trigger: 'item',
          formatter: "{a} <br/>{b}: {c} ({d}%)"
        },
        legend: {
          orient: 'vertical',
          x: 'right',
          data: []
        },
        series: [
          {
            name: '租金统计',
            type: 'pie',
            selectedMode: 'single',
            radius: [0, '50%'],

            label: {
              normal: {
                position: 'inner'
              }
            },
            labelLine: {
              normal: {
                show: false
              }
            },
            data: []
          },
          {
            name: '租金统计',
            type: 'pie',
            radius: ['60%', '90%'],

            data: []
          }
        ]
      }
    }),
    created() {
      //this.statistic = JSON.parse(window.localStorage.getItem("statistic"));
      this.pie.legend.data = this.statistic.rentName;
      this.pie.series[0].data = this.statistic.innerRent;
      this.pie.series[1].data = this.statistic.outRent;
      //console.log(this.pie);
    },
    methods: {}
  }
</script>

<style scoped>
  .el-col {
    margin-bottom: 16px;
  }

  .material-icons {
    font-size: 80px;
    color: #ddd;
  }

  .cart-string {
    height: 100px;
    padding-top: 10px;
    font-size: 1.1rem;

  }

  .c-charts {
    height: 400px;
    width: 100%;
  }

</style>
