<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <meta name="viewport" content="initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
    <meta name="apple-mobile-web-app-capable" content="yes"/>
    <meta name="full-screen" content="yes"/>
    <meta name="screen-orientation" content="portrait"/>
    <meta name="x5-fullscreen" content="true"/>
    <meta name="360-fullscreen" content="true"/>
    <link href="${baseCssOP}/login.css?${timeStamp}" rel="stylesheet" type="text/css">
    <title>成绩查询</title>
</head>
<body>
<div class="main">
    <div class="top">
        <div class="center">
            <img src="${baseImgOP}/ll_03.png?${timeStamp}">

            <div class="text">
                <p>成绩查询</p>

                <p>SCORES QUERY</p>
            </div>
        </div>
    </div>
    <div class="box">
        <p class="name"><span>姓</span> 名：</p>
        <div class="inputBox">
            <!--此处姓名输入框-->
            <input type="text" name="name" id="name"/>
        </div>
    </div>
    <div class="box">
        <p>证件号：</p>
        <div class="inputBox">
            <!--此处证件号输入框-->
            <input type="text" name="zjhm" id="zjhm"/>
        </div>
    </div>
    <div class="box">
        <p>验证码：</p>
        <div class="inputBox">
            <!--此处验证码输入框-->
            <input type="text" name="yzm" id="yzm"/>
        </div>
        <div class="code"><!--验证码 -->
            <img id="checkcode" src="data:image/jepg;base64,${image}"/>
        </div>
    </div>
    <div class="info"></div>
    <div class="renovate"><span>看不清</span>刷新</div>
    <div class="btns">
        <div class="btn login"><span>登</span>录</div>
        <div class="btn reset"><span>重</span>置</div>
    </div>
</div>
<div class="notice"><!--失败提示框-->
    <div class="noticeBox">
        <div class="text">
			${errorInfo}
            <img src="${baseImgOP}/close.png?${timeStamp}" class="close">
        </div>
    </div>
</div>
<script>
<#if errorInfo?? >
$(document).ready(function(){
	$(".notice").show();
});
</#if>
</script>
<script type="text/javascript" src="${baseJsOP}/jquery-2.1.1.min.js?${timeStamp}"></script>
<script type="text/javascript" src="${baseJsOP}/login.js?${timeStamp}"></script>
</body>
</html>