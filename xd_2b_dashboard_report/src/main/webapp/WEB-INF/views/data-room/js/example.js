(function () {
    require.config({
        paths: {
            echarts: './js'
        },
        packages: [
            {
                name: 'BMap',
                location: 'js/src',
                main: 'main'
            }
        ]
    });

    require(
        [
            'echarts',
            'BMap',
            'echarts/chart/map'
        ],
        function (echarts, BMapExtension) {
            $('#main').css({
                height: $('body').height(),
                width: $('body').width()
            });

            // 初始化地图
            var BMapExt = new BMapExtension($('#main')[0], BMap, echarts, {
                enableMapClick: false
            });
            var map = BMapExt.getMap();
            var container = BMapExt.getEchartsContainer();

            var startPoint = {
                x: 116.29561,
                y: 39.806602
            };

            var point = new BMap.Point(startPoint.x, startPoint.y);
            map.centerAndZoom(point, 11);
            map.enableScrollWheelZoom(false);
            // 地图自定义样式
            map.setMapStyle({
                styleJson: [
                    {
                        "featureType": "water",
                        "elementType": "all",
                        "stylers": {
                            "color": "#044161"
                        }
                    },
                    {
                        "featureType": "land",
                        "elementType": "all",
                        "stylers": {
                            "color": "#004981"
                        }
                    },
                    {
                        "featureType": "boundary",
                        "elementType": "geometry",
                        "stylers": {
                            "color": "#064f85"
                        }
                    },
                    {
                        "featureType": "railway",
                        "elementType": "all",
                        "stylers": {
                            "visibility": "off"
                        }
                    },
                    {
                        "featureType": "highway",
                        "elementType": "geometry",
                        "stylers": {
                            "color": "#004981"
                        }
                    },
                    {
                        "featureType": "highway",
                        "elementType": "geometry.fill",
                        "stylers": {
                            "color": "#005b96",
                            "lightness": 1
                        }
                    },
                    {
                        "featureType": "highway",
                        "elementType": "labels",
                        "stylers": {
                            "visibility": "off"
                        }
                    },
                    {
                        "featureType": "arterial",
                        "elementType": "geometry",
                        "stylers": {
                            "color": "#004981"
                        }
                    },
                    {
                        "featureType": "arterial",
                        "elementType": "geometry.fill",
                        "stylers": {
                            "color": "#00508b"
                        }
                    },
                    {
                        "featureType": "poi",
                        "elementType": "all",
                        "stylers": {
                            "visibility": "on"
                        }
                    },
                    {
                        "featureType": "green",
                        "elementType": "all",
                        "stylers": {
                            "color": "#056197",
                            "visibility": "on"
                        }
                    },
                    {
                        "featureType": "subway",
                        "elementType": "all",
                        "stylers": {
                            "visibility": "off"
                        }
                    },
                    {
                        "featureType": "manmade",
                        "elementType": "all",
                        "stylers": {
                            "visibility": "off"
                        }
                    },
                    {
                        "featureType": "local",
                        "elementType": "all",
                        "stylers": {
                            "visibility": "off"
                        }
                    },
                    {
                        "featureType": "arterial",
                        "elementType": "labels",
                        "stylers": {
                            "visibility": "off"
                        }
                    },
                    {
                        "featureType": "boundary",
                        "elementType": "geometry.fill",
                        "stylers": {
                            "color": "#029fd4"
                        }
                    },
                    {
                        "featureType": "building",
                        "elementType": "all",
                        "stylers": {
                            "color": "#1a5787"
                        }
                    },
                    {
                        "featureType": "label",
                        "elementType": "all",
                        "stylers": {
                            "visibility": "on"
                        }
                    }
                ]
                /*styleJson: [
                 // 陆地
                 {
                 "featureType": "land",
                 "elementType": "all",
                 "stylers": {
                 "color": "#073763"
                 }
                 },
                 // 水系
                 {
                 "featureType": "water",
                 "elementType": "all",
                 "stylers": {
                 "color": "#073763",
                 "lightness": -54
                 }
                 },
                 // 国道与高速
                 {
                 "featureType": "highway",
                 "elementType": "all",
                 "stylers": {
                 "color": "#45818e"
                 }
                 },
                 // 边界线
                 {
                 "featureType": "boundary",
                 "elementType": "all",
                 "stylers": {
                 "color": "#ffffff",
                 "lightness": -62,
                 "visibility": "on"
                 }
                 },
                 // 行政标注
                 {
                 "featureType": "label",
                 "elementType": "labels.text.fill",
                 "stylers": {
                 "color": "#ffffff",
                 "visibility": "on"
                 }
                 },
                 {
                 "featureType": "label",
                 "elementType": "labels.text.stroke",
                 "stylers": {
                 "color": "#444444",
                 "visibility": "on"
                 }
                 }
                 ]*/
            });

            option = {
/*                title: {
                    text: '全国地域分布总览',
                    x: 'center',
                    textStyle: {
                        color: '#fff'
                    }
                },*/
                color: ['gold', 'aqua', 'lime'],
                tooltip: {
                    trigger: 'item',
                    formatter: "{a} <br/>{b} : {d}%"
                },
                legend: {
                    orient: 'vertical',
                    x: 'left',
                    data: ['平均正确率','任务完成度','院校活跃度'],
                    textStyle: {
                        color: '#fff'
                    },
                    selectedMode: 'single',
                    selected:{
                        '任务完成度': false,
                        '院校活跃度': false
                    }
                },
                dataRange: {
                    min: 0,
                    max: 100,
                    x: 'right',
                    calculable: true,
                    color: ['#ff3333', 'orange', 'yellow', 'lime', 'aqua'],
                    textStyle: {
                        color: '#fff'
                    }
                },
                series: [
                    {
                        name: '院校活跃度',
                        type: 'map',
                        mapType: 'none',
                        hoverable: false,
                        roam: false,
                        data: [],
                        markPoint: {
                            symbol: 'emptyCircle',
                            /*symbolSize: 5,       // 标注大小，半宽（半径）参数，当图形为方向或菱形则总宽度为symbolSize * 2*/
                            symbolSize: function (v) {
                                return 10 + v / 500
                            },
                            effect: {
                                show: true,
                                shadowBlur: 0
                            },
                            itemStyle: {
                                normal: {
                                    borderWidth: 1,            // 标注边线线宽，单位px，默认为1
                                    label: {
                                        show: false
                                    }
                                },
                                emphasis: {
                                    borderWidth: 5,
                                    label: {
                                        show: false
                                    }
                                }
                            },
                            data: [
                                {name: '北京财贸职业学院',value: 98},
                                {name: '北京市朝阳区职业技术学校', value: 95},
                                {name: '北京广播电视大学供销合作总社分校', value: 98},
                                {name: '中央广播电视中等专业学校北京分校', value: 97},
                                {name: '密云县职业技术学校', value: 96},
                                {name: '北京金隅科技学校', value: 99},
                                {name: '北京市大兴区第一职业学校', value: 99},
                                {name: '北京市大兴区第二职业学校', value: 95},
                                {name: '北京八一艺术学校', value: 96},
                                {name: '北京市工业技师学校', value: 96},
                                {name: '北京市礼文中学', value: 95},
                                {name: '北京市供销学校', value: 97},
                                {name: '北京市经贸高级技术学校', value: 98},
                                {name: '北京市商业学校', value: 99},
                                {name: '北京中华会计函授学校昌平分校', value: 98},
                                {name: '北京市经济管理学校', value: 96},
                                {name: '北京市商务科技学校', value: 97},
                                {name: '北京市求实职业学校', value: 98},
                                {name: '北京市外事学校', value: 99},
                                {name: '北京市信息管理学校', value: 97}
                            ]
                        },
                        geoCoord: {
                            '北京财贸职业学院': [116.654329,39.926777],
                            '北京市朝阳区职业技术学校': [116.452938,39.89542],
                            '北京广播电视大学供销合作总社分校': [116.369936,39.943472],
                            '中央广播电视中等专业学校北京分校': [115.998676,39.703836],
                            '密云县职业技术学校': [116.833962,40.366673],
                            '北京金隅科技学校': [116.054224,39.5984],
                            '北京市大兴区第一职业学校': [116.288564,39.725817],
                            '北京市大兴区第二职业学校': [116.325891,39.6841],
                            '北京八一艺术学校': [116.310716,39.79754],
                            '北京市工业技师学校': [116.536734,39.85994],
                            '北京市礼文中学': [116.229837,39.916008],
                            '北京市供销学校': [116.367236,39.795553],
                            '北京市经贸高级技术学校': [116.04465,39.717663],
                            '北京市商业学校': [116.422983,39.89489],
                            '北京中华会计函授学校昌平分校': [116.447152,39.946519],
                            '北京市经济管理学校': [116.30735,39.93183],
                            '北京市商务科技学校': [116.65802,39.931952],
                            '北京市求实职业学校': [116.475634,39.937342],
                            '北京市外事学校': [116.363898,39.941066],
                            '北京市信息管理学校': [116.314837,39.983471]
                        }
                    },
                    {
                        name: '任务完成度',
                        type: 'map',
                        mapType: 'none',
                        data: [],
                        markPoint: {
                            symbol: 'emptyCircle',
                            symbolSize: function (v) {
                                return 10 + v / 500
                            },
                            effect: {
                                show: true,
                                shadowBlur: 0
                            },
                            itemStyle: {
                                normal: {
                                    label: {show: false}
                                }
                            },
                            data: [
                                {name: '北京财贸职业学院',value: 94},
                                {name: '北京市朝阳区职业技术学校', value: 95},
                                {name: '北京广播电视大学供销合作总社分校', value: 96},
                                {name: '中央广播电视中等专业学校北京分校', value: 97},
                                {name: '密云县职业技术学校', value: 95},
                                {name: '北京金隅科技学校', value: 94},
                                {name: '北京市大兴区第一职业学校', value: 93},
                                {name: '北京市大兴区第二职业学校', value: 95},
                                {name: '北京八一艺术学校', value: 94},
                                {name: '北京市工业技师学校', value: 93},
                                {name: '北京市礼文中学', value: 97},
                                {name: '北京市供销学校', value: 92},
                                {name: '北京市经贸高级技术学校', value: 93},
                                {name: '北京市商业学校', value: 95},
                                {name: '北京中华会计函授学校昌平分校', value: 91},
                                {name: '北京市经济管理学校', value: 92},
                                {name: '北京市商务科技学校', value: 93},
                                {name: '北京市求实职业学校', value: 95},
                                {name: '北京市外事学校', value: 97},
                                {name: '北京市信息管理学校', value: 96}
                            ]
                        }
                    },
                    {
                        name: '平均正确率',
                        type: 'map',
                        mapType: 'none',
                        data: [],
                        markPoint: {
                            symbol: 'emptyCircle',
                            symbolSize: function (v) {
                                return 10 + v / 500
                            },
                            effect: {
                                show: true,
                                shadowBlur: 0
                            },
                            itemStyle: {
                                normal: {
                                    label: {show: false}
                                }
                            },
                            data: [
                                {name: '北京财贸职业学院',value: 90},
                                {name: '北京市朝阳区职业技术学校', value: 88},
                                {name: '北京广播电视大学供销合作总社分校', value: 85},
                                {name: '中央广播电视中等专业学校北京分校', value: 92},
                                {name: '密云县职业技术学校', value: 93},
                                {name: '北京金隅科技学校', value: 85},
                                {name: '北京市大兴区第一职业学校', value: 95},
                                {name: '北京市大兴区第二职业学校', value: 81},
                                {name: '北京八一艺术学校', value: 82},
                                {name: '北京市工业技师学校', value: 97},
                                {name: '北京市礼文中学', value: 83},
                                {name: '北京市供销学校', value: 84},
                                {name: '北京市经贸高级技术学校', value: 88},
                                {name: '北京市商业学校', value: 100},
                                {name: '北京中华会计函授学校昌平分校', value: 96},
                                {name: '北京市经济管理学校', value: 80},
                                {name: '北京市商务科技学校', value: 83},
                                {name: '北京市求实职业学校', value: 89},
                                {name: '北京市外事学校', value: 90},
                                {name: '北京市信息管理学校', value: 92}
                            ]
                        }
                    }
                    /*{
                        name: '平均学习时长',
                        type: 'map',
                        mapType: 'none',
                        data: [],
                        markPoint: {
                            symbol: 'emptyCircle',
                            symbolSize: function (v) {
                                return 10 + v / 500
                            },
                            effect: {
                                show: true,
                                shadowBlur: 0
                            },
                            itemStyle: {
                                normal: {
                                    label: {show: false}
                                }
                            },
                            data: [
                                {name: '北京财贸职业学院',value: 900},
                                {name: '北京市朝阳区职业技术学校', value: 600},
                                {name: '北京广播电视大学供销合作总社分校', value: 100},
                                {name: '中央广播电视中等专业学校北京分校', value: 400},
                                {name: '密云县职业技术学校', value: 200},
                                {name: '北京金隅科技学校', value: 700},
                                {name: '北京市大兴区第一职业学校', value: 800},
                                {name: '北京市大兴区第二职业学校', value: 400},
                                {name: '北京八一艺术学校', value: 100},
                                {name: '北京市工业技师学校', value: 400},
                                {name: '北京市礼文中学', value: 100},
                                {name: '北京市供销学校', value: 100},
                                {name: '北京市经贸高级技术学校', value: 100},
                                {name: '北京市商业学校', value: 100},
                                {name: '北京中华会计函授学校昌平分校', value: 100},
                                {name: '北京市经济管理学校', value: 100},
                                {name: '北京市商务科技学校', value: 100},
                                {name: '北京市求实职业学校', value: 100},
                                {name: '北京市外事学校', value: 100},
                                {name: '北京市信息管理学校', value: 100}
                            ]
                        }
                    }*/
                ]
            };


            var myChart = BMapExt.initECharts(container);
            window.onresize = myChart.onresize;
            BMapExt.setOption(option);
        }
    );
})();
