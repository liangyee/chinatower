<template>
  <div class="c-charts" v-cloak>
    <IEcharts :option="line"></IEcharts>
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
      line: {
        title: {
          text: '税金统计图'
        },
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'cross',
            label: {
              backgroundColor: '#6a7985'
            }
          }
        },
        legend: {
          data: []
        },

        grid: {
          left: '1%',
          right: '3%',
          bottom: '3%',
          containLabel: true,
          show: true
        },
        xAxis: [
          {
            type: 'category',
            boundaryGap: false,
            data: [],
            axisLine: {lineStyle: {color: '#999'}},
            splitLine: {show: true, lineStyle: {color: '#ddd'}}
          }
        ],
        yAxis: [
          {
            type: 'value',
            axisLine: {lineStyle: {color: '#999'}},
            splitLine: {lineStyle: {color: '#ddd'}}

          }
        ],
        series: []
      }
    }),
    created() {
      //this.statistic = JSON.parse(window.localStorage.getItem("statistic"));
      this.line.legend.data = this.statistic.region;
      this.line.xAxis[0].data = this.statistic.year;
      var a = [];
      var dictTax = this.statistic.tax;
      for (var key in dictTax) {
        var m = {};
        m.name = key;
        m.type = 'line';
        m.label = {
          normal: {
            show: true
          }
        };
        m.areaStyle = {normal: {}};
        m.data = dictTax[key];
        a.push(m);
      }
      this.line.series = a;
      //console.log(JSON.stringify(this.line));
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
