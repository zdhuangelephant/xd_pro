/**
 * Created by zhaodan on 2017/7/17.
 */
var myChart = echarts.init(document.getElementById("morris-area-chart"));

var colors = ['#5793f3', '#d14a61', '#675bba'];

var option = {
    color: colors,
    tooltip: {
        trigger: 'none',
        axisPointer: {
            type: 'cross'
        }
    },
    legend: {
        data: ['2016 Passed', '2016 Failed']
    },
    grid: {
        top: 70,
        bottom: 50
    },
    xAxis: [
        {
            type: 'category',
            axisTick: {
                alignWithLabel: true
            },
            axisLine: {
                onZero: false,
                lineStyle: {
                    color: colors[1]
                }
            },
            axisPointer: {
                label: {
                    formatter: function (params) {
                        return 'Failed  ' + params.value
                            + (params.seriesData.length ? '：' + params.seriesData[0].data : '');
                    }
                }
            },
            data: ["2016-1", "2016-2", "2016-3", "2016-4", "2016-5", "2016-6", "2016-7", "2016-8", "2016-9", "2016-10", "2016-11", "2016-12"]
        },
        {
            type: 'category',
            axisTick: {
                alignWithLabel: true
            },
            axisLine: {
                onZero: false,
                lineStyle: {
                    color: colors[0]
                }
            },
            axisPointer: {
                label: {
                    formatter: function (params) {
                        return 'Passed  ' + params.value
                            + (params.seriesData.length ? '：' + params.seriesData[0].data : '');
                    }
                }
            },
            data: ["2016-1", "2016-2", "2016-3", "2016-4", "2016-5", "2016-6", "2016-7", "2016-8", "2016-9", "2016-10", "2016-11", "2016-12"]
        }
    ],
    yAxis: [
        {
            type: 'value'
        }
    ],
    series: [
        {
            name: '2016 Passed',
            type: 'line',
            xAxisIndex: 1,
            smooth: true,
            data: [27, 26, 28, 26, 25, 30, 31, 28, 26, 24, 26, 23]
        },
        {
            name: '2016 Failed',
            type: 'line',
            smooth: true,
            data: [4, 2, 3, 4, 6, 0, 0, 3, 4, 7, 4, 7]
        }
    ]
};

// 使用刚指定的配置项和数据显示图表。
myChart.setOption(option);