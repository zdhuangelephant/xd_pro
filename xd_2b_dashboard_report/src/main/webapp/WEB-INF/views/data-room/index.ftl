<html>
<head>
    <meta charset="utf-8">
    <#include "/common/title.ftl"/>
    <link rel="stylesheet" href="./css/data-style.css" type="text/css">
    <script src="js/main-echarts.js"></script>
</head>
<body>

<div class="mo-top-wrap w1920">
    <div class="l-logo l">
        <img class="logo-m-left" src="images/data-center.png" alt="慕享云测评数据监控中心">
        <p class="update-time" id="time"></p>
    </div>
    <div class="top-logo-wrap l">
        <div class="learning-title"><img src="images/learning-time.png" alt="学习时长"></div>
        <div class="data" id="counter"></div>
    </div>
    <div class="r-logo r">
        <img class="logo-m-left" src="images/right-logo.png" alt="${taughtUnitName}">
    </div>
</div>

<div class="echart-wrap w1920">
    <div class="echarts-l l">
        <!---->
        <div class="echarts-bg m-bottom" id="line-chart"></div>
        <div class="echarts-bg" id="pie-chart"></div>
    </div>
    <div class="echarts-m l">
        <div class="m-top-bg">北京市地域分布总览</div>
        <div id="main" class="main-map"></div>
    </div>
    <div class="echarts-r l">
        <div class="echarts-bg m-bottom" id="bar-chart"></div>
        <div class="echarts-bg" id="relation-chart"></div>
    </div>
</div>
</body>
<script src="js/my-echarts.js"></script>
<script src="js/echarts.js"></script>
<script type="text/javascript" src="https://api.map.baidu.com/api?v=2.0&ak=LoWiglXZ3mHk1cfOtcIhRn03NiaALzNX"></script>
<script src="js/jquery.min.js"></script>
<script src="js/example.js"></script>
<script src="js/data.js"></script>
<link href="${baseOP}/content/min/css/supershopui.common.min.css" rel="stylesheet"/>
</html>
