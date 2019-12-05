
  <#include "/common/common.ftl">
  
<html>
<head>
  <meta charset="UTF-8">
<title>社交网络</title>

 <style>
</style> 
   </head>
  <body>
    <!-- 为ECharts准备一个具备大小（宽高）的Dom -->
   <div style="padding:20px;width:100%;height:100%;"> 
               <div id="main" style="width: 1104px;height:464px;">
               </div>
 </div>
    <script type="text/javascript">
	//定义echarts容器
    var myChart = echarts.init(document.getElementById('main'), 'macarons');
    option = {
    title : {
        text: '手机品牌',
        subtext: '线、节点样式'
    },
    series : [
        {
            name:'树图',
            type:'tree',
            orient: 'horizontal',  // vertical horizontal
            rootLocation: {x: 100, y: '60%'}, // 根节点位置  {x: 'center',y: 10}
            nodePadding: 20,
            itemStyle: {
                normal: {
                    label: {
                        show: true,
                        position: 'inside',
                        textStyle: {
                            color: '#cc9999',
                            fontSize: 15,
                            fontWeight:  'bolder'
                        }
                    },
                    lineStyle: {
                        color: '#000',
                        width: 1,
                        type: 'broken' // 'curve'|'broken'|'solid'|'dotted'|'dashed'
                    }
                },
                emphasis: {
                    label: {
                        show: true
                    }
                }
            },
            data: [
                {
                    name: '手机',
                    value: 6,
                    itemStyle: {
                        normal: {
                            label: {
                                show: false
                            }
                        }
                    },
                    children: [
                        {
                            name: '小米',
                            value: 4,            
                            itemStyle: {
                                normal: {
                                    label: {
                                        show: false
                                    }
                                }
                            },
                            children: [
                                {
                                    name: '小米1',
                                    value: 4,
                                    itemStyle: {
                                        normal: {
                                            color: '#fa6900',
                                            label: {
                                                show: true,
                                                position: 'right'
                                            },
                                            
                                        },
                                        emphasis: {
                                            label: {
                                                show: false
                                            },
                                            borderWidth: 0
                                        }
                                    }
                                },
                                {
                                    name: '小米2',
                                    value: 4,
                                    itemStyle: {
                                        normal: {
                                            label: {
                                                show: true,
                                                position: 'right',
                                                formatter: "{b}"
                                            },
                                            color: '#fa6900',
                                            borderWidth: 2,
                                            borderColor: '#cc66ff'

                                        },
                                        emphasis: {
                                            borderWidth: 0
                                        }
                                    }
                                },
                                {
                                    name: '小米3',
                                    value: 2,
                                    itemStyle: {
                                        normal: {
                                            label: {
                                                position: 'right'
                                            },
                                            color: '#fa6900',
                                            brushType: 'stroke',
                                            borderWidth: 1,
                                            borderColor: '#999966',
                                        },
                                        emphasis: {
                                            borderWidth: 0
                                        }
                                    }
                                }
                            ]
                        },
                        {
                            name: '苹果',
                            itemStyle: {
                                normal: {
                                    label: {
                                        show: false
                                    }
                                    
                                }
                            },
                            value: 4
                        },
                        {
                            name: '华为',
                            itemStyle: {
                                normal: {
                                    label: {
                                        show: false
                                    }
                                    
                                }
                            },
                            value: 2
                        },
                        {
                            name: '联想',      
                            itemStyle: {
                                normal: {
                                    label: {
                                        show: false
                                    }
                                    
                                }
                            },
                            value: 2
                        }
                    ]
                }
            ]
        }
    ]
};             
 myChart.setOption(option);
  </script>      
  </body>
</html>