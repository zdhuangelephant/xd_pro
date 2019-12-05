/**
 * Created by zyc
 * on 2017/11/9.
 */

//折线图
var lineChart = echarts.init(document.getElementById('line-chart'));
//饼状图
var pieChart = echarts.init(document.getElementById('pie-chart'));
//柱状图begin
var barChart = echarts.init(document.getElementById('bar-chart'));
//柱状图end
var relationChart = echarts.init(document.getElementById('relation-chart'));

//---折线图begin---
lineChartOption = {
    textStyle: {
        color: '#fff'
    },
    title: {
        text: '启动次数（次）',
        textStyle: {
            color: '#68cbff'
        },
        left: 15,
        top: 5
    },
    tooltip: {
        trigger: 'axis'
    },
    grid: {
        left: '3%',
        right: '4%',
        bottom: '3%',
        containLabel: true
    },
    xAxis: {
        type: 'category',
        boundaryGap: false,
        data: ['0点', '4点', '8点', '12点', '16点', '20点', '24点']
    },
    yAxis: {
        type: 'value'
    },
    series: [
        {
            name: '前天',
            type: 'line',
            data: [86, 0, 3406, 2133, 2654, 3166, 45],
            itemStyle: {
                normal: {
                    color: '#ffbb5d',
                    lineStyle: {
                        color: '#ffa6a6'
                    }
                }
            }
        },
        {
            name: '昨天',
            type: 'line',
            data: [105, 0, 3555, 2366, 2833, 3011, 23],
            itemStyle: {
                normal: {
                    color: '#eb8146',
                    lineStyle: {
                        color: '#ffce55'
                    }
                }
            }
        },
        {
            name: '今天',
            type: 'line',
            data: [40, 0,3699, 2517, 2666, 2996, 12],
            itemStyle: {
                normal: {
                    color: '#ce6e69',
                    lineStyle: {
                        color: '#62c87f'
                    }
                }
            }
        }
    ]
};
lineChart.setOption(lineChartOption);
//---折线图end---

//饼状图begin
pieChartOption = {
    textStyle: {
        color: '#fff'
    },
    title: {
        text: '用户行为（小时）',
        left: 15,
        top: 5,
        textStyle: {
            color: '#68cbff'
        }
    },
    color: ['#5d9cec','#ffce55','#98e9e4','#ffa6a6', '#a6aaff', '#62c87f'],
    		//蓝			黄色		      亮蓝		 粉红			紫色		       绿色
    tooltip: {
        trigger: 'item',
        formatter: "{a} <br/>{b} : {c} ({d}%)"
    },
    series: [
        {
            name: '用户行为',
            type: 'pie',
            radius: '55%',
            center: ['50%', '53%'],
            data: [
                {value: 2636, name: '每日一练'},
                {value: 5836, name: '闯关做题'},
                {value: 521, name: '随机pk'},
                {value: 1279, name: '期末测试'},
                {value: 1536, name: '查漏补缺'},
                {value: 2064, name: '修炼'}
            ],
            itemStyle: {
                emphasis: {
                    shadowBlur: 10,
                    shadowOffsetX: 0,
                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                }
            }
        }
    ]
};
pieChart.setOption(pieChartOption);
//饼状图end

//---柱状图begin---
barChartOption = {
    textStyle: {
        color: '#fff'
    },
    title: {
        text: '学习热度（小时）',
        left: 15,
        top: 5,
        textStyle: {
            color: '#68cbff'
        }
    },
    tooltip: {
        trigger: 'axis',
        axisPointer: {            // 坐标轴指示器，坐标轴触发有效
            type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
        }
    },
    grid: {
        left: '3%',
        right: '4%',
        bottom: '-1%',
        containLabel: true
    },
    xAxis: [
        {
            type: 'category',
            data: [
                '毛概理论',
                '学前教育',
                '幼儿课程',
                '教育心理',
                '人力资源',
                '学前卫生',
                '近现代史'],
            axisTick: {
                alignWithLabel: true
            }
        }
    ],
    yAxis: [
        {
            type: 'value'
        }
    ],
    series: [
        {
            name: '学习热度(小时)',
            type: 'bar',
            barWidth: '50%',
            data: [3877, 3588, 3124, 3155, 2865, 2544, 1956],
            itemStyle: {
                normal: {
                    color: new echarts.graphic.LinearGradient(
                        0, 0, 0, 1,
                        [
                            {offset: 0, color: '#f9d98c'},//浅色
                            {offset: 1, color: '#ffce55'}//深色
                        ]
                    )
                }
            }
        }
    ],
    axisLabel: {
        interval: 0,
        formatter:function(value)
        {
            var ret = "";//拼接加\n返回的类目项
            var maxLength = 2;//每项显示文字个数
            var valLength = value.length;//X轴类目项的文字个数
            var rowN = Math.ceil(valLength / maxLength); //类目项需要换行的行数
            if (rowN > 1)//如果类目项的文字大于3,
            {
                for (var i = 0; i < rowN; i++) {
                    var temp = "";//每次截取的字符串
                    var start = i * maxLength;//开始截取的位置
                    var end = start + maxLength;//结束截取的位置
                    //这里也可以加一个是否是最后一行的判断，但是不加也没有影响，那就不加吧
                    temp = value.substring(start, end) + "\n";
                    ret += temp; //凭借最终的字符串
                }
                return ret;
            }
            else {
                return value;
            }
        }
    }
};
barChart.setOption(barChartOption);
//---柱状图end---

//关系图begin
/*relationChartOption = {
    title: {
        text: "院校分布",
        top: 5,
        left: 15,
        textStyle: {
            color: '#68cbff'
        }
    },
    tooltip: {},
    animationDurationUpdate: 1500,
    animationEasingUpdate: 'quinticInOut',
    label: {
        normal: {
            show: true,
            textStyle: {
                fontSize: 10
            }
        }
    },
    series: [
        {
            type: 'graph',
            layout: 'force',
            symbolSize: 25,
            focusNodeAdjacency: true,
            roam: true,
            categories: [{
                name: '',
                itemStyle: {
                    normal: {
                        color: "#009800"
                    }
                }
            }, {
                name: '',
                itemStyle: {
                    normal: {
                        color: "#4592FF"
                    }
                }
            }, {
                name: '',
                itemStyle: {
                    normal: {
                        color: "#3592F"
                    }
                }
            }],
            label: {
                normal: {
                    show: true,
                    textStyle: {
                        fontSize: 12
                    }
                }
            },
            force: {
                repulsion: 150
            },
            edgeSymbolSize: [4, 50],
            edgeLabel: {
                normal: {
                    textStyle: {
                        fontSize: 10
                    },
                    formatter: "{c}"
                }
            },
            data: [{
                name: '自考办',
                draggable: true,
                symbolSize: 38,
                itemStyle:{
                    normal:{color:'#ffce55'}
                }
            }, {
                name: '北大方正软件技术学院',
                category: 1,
                draggable: true,
                itemStyle:{
                    normal:{color:'#62ca7b'}
                }
            }, {
                name: '北京师范大学',
                category: 1,
                draggable: true,
                itemStyle:{
                    normal:{color:'#62ca7b'}
                }
            }, {
                name: '北京经济管理学校',
                category: 1,
                draggable: true,
                itemStyle:{
                    normal:{color:'#5d9cec'}
                }

            }, {
                name: '北京商务科技学校',
                category: 1,
                draggable: true,
                itemStyle:{
                    normal:{color:'#5d9cec'}
                }
            }, {
                name: '北京求实职业学校',
                category: 1,
                draggable: true,
                itemStyle:{
                    normal:{color:'#5d9cec'}
                }
            }, {
                name: '北京外事学校',
                category: 1,
                draggable: true,
                itemStyle:{
                    normal:{color:'#5d9cec'}
                }
            }, {
                name: '北京信息管理学校',
                category: 1,
                draggable: true,
                itemStyle:{
                    normal:{color:'#5d9cec'}
                }
            }, {
                name: '中央广播电视学校',
                category: 1,
                draggable: true,
                itemStyle:{
                    normal:{color:'#5d9cec'}
                }
            }, {
                name: '朝阳区职业学校',
                category: 1,
                draggable: true,
                itemStyle:{
                    normal:{color:'#5d9cec'}
                }
            }, {
                name: '密云县职业技术学校',
                category: 1,
                draggable: true,
                itemStyle:{
                    normal:{color:'#5d9cec'}
                }
            }, {//11
                name: '北京财贸职业学院',
                category: 1,
                draggable: true,
                itemStyle:{
                    normal:{color:'#5d9cec'}
                }
            }, {
                name: '北京金隅科技学校',
                category: 1,
                draggable: true,
                itemStyle:{
                    normal:{color:'#5d9cec'}
                }
            }, {
                name: '北京大兴区职业学校',
                category: 1,
                draggable: true,
                itemStyle:{
                    normal:{color:'#5d9cec'}
                }
            }, {
                name: '北京八一艺术学校',
                category: 1,
                draggable: true,
                itemStyle:{
                    normal:{color:'#5d9cec'}
                }
            }, {
                name: '北京工业技师学校',
                category: 1,
                draggable: true,
                itemStyle:{
                    normal:{color:'#5d9cec'}
                }
            }, {
                name: '北京礼文中学',
                category: 1,
                draggable: true,
                itemStyle:{
                    normal:{color:'#5d9cec'}
                }
            }, {
                name: '北京商业学校',
                category: 1,
                draggable: true,
                itemStyle:{
                    normal:{color:'#5d9cec'}
                }
            }],
            links: [{
                source: 0,
                target: 1,
                category: 0
            }, {
                source: 0,
                target: 2
            },{
                source: 1,
                target: 5
            },{
                source: 1,
                target: 6
            },{
                source: 1,
                target: 7
            },{
                source: 1,
                target: 8
            },{
                source: 1,
                target: 9
            },{
                source: 1,
                target: 10
            },{
                source: 2,
                target: 11
            }, {
                source: 2,
                target: 12
            }, {
                source: 2,
                target: 13
            }, {
                source: 2,
                target: 14
            }, {
                source: 2,
                target: 15
            }, {
                source: 2,
                target: 16
            }, {
                source: 2,
                target: 17
            }, {
                source: 1,
                target: 16
            }, {
                source: 1,
                target: 15
            }, {
                source: 2,
                target: 3
            }, {
                source: 2,
                target: 4
            }],
            lineStyle: {
                normal: {
                    opacity: 0.9,
                    width: 1,
                    curveness: 0
                }
            }
        }
    ]
};*/
var scale = 1;
var echartData = [{
    value: 1106,
    name: '北京商业学校'
}, {
    value: 987,
    name: '北京礼文中学'
}, {
    value: 1198,
    name: '密云职业学校'
}, {
    value: 1230,
    name: '北京金隅科技学校'
}, {
    value: 996,
    name: '北京财贸学院'
}, {
    value: 1066,
    name: '北京八一艺术学校'
}];
var rich = {
    yellow: {
        color: "#ffc72b",
        fontSize: 14,
        padding: [ 0, 0, 6, 0],
        align: 'center'
    },
    total: {
        color: "#ffc72b",
        fontSize: 20,
        align: 'center'
    },
    white: {
        color: "#fff",
        align: 'center',
        fontSize: 12,
        padding: [11, 0]
    },
    blue: {
        color: '#49dff0',
        fontSize: 12,
        align: 'center'
    },
    hr: {
        borderColor: '#0b5263',
        width: '100%',
        borderWidth: 1,
        height: 0
    }
};
relationChartOption = {
    backgroundColor: '#031f2d',
    title: {
        text: "院校分布（活跃人数）",
        top: 5,
        left: 15,
        textStyle: {
            color: '#68cbff'
        }
    },
    tooltip: {
        trigger: 'item',
        formatter: "{a} <br/>{b} : {c} ({d}%)"
    },
    legend: {
        selectedMode:false,
        formatter: function(name) {
            var total = 0; //各科正确率总和
            var averagePercent; //综合正确率
            echartData.forEach(function(value, index, array) {
                total += value.value;
            });
            return '{total|' + total + '}';
        },
        data: [echartData[0].name],
        left: 'center',
        top: '50%',
        icon: 'none',
        align:'center',
        textStyle: {
            color: "#fff",
            fontSize: 12,
            rich: rich
        }
    },
    series: [{
        name: '活跃人数',
        type: 'pie',
        radius: ['42%', '50%'],
        center: ['50%', '55%'],
        hoverAnimation: false,
        color: ['#c487ee', '#deb140', '#49dff0', '#034079', '#6f81da', '#00ffb4'],
        label: {
            normal: {
                formatter: function(params, ticket, callback) {
                    var total = 0; //考生总数量
                    var percent = 0; //考生占比
                    echartData.forEach(function(value, index, array) {
                        total += value.value;
                    });
                    percent = ((params.value / total) * 100).toFixed(1);
                    return '{white|' + params.name + '}\n{hr|}\n{yellow|' + params.value + '}\n{blue|' + percent + '%}';
                },
                rich: rich
            },
        },
        labelLine: {
            normal: {
                length: 15,
                length2: 0,
                lineStyle: {
                    color: '#0b5263'
                }
            }
        },
        data: echartData
    }]
};
relationChart.setOption(relationChartOption);
//关系图end
