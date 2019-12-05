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
    <title></title>
    <link href="${baseCssOP}/index.css?${timeStamp}" rel="stylesheet"/>
</head>
<script type="text/javascript">
if(!(navigator.userAgent.match(/MicroMessenger/i))){
     window.location.href = "/product/notWeixing";
}
</script>
<body>
<div class="main">
    <div class="xdFail">
        <img src="${baseImgOP}/success_03.png?${timeStamp}">
        <div class="userName"></div>
    </div>
                  <div class="download"><!--小逗考典，马上下载-->
                <div class="picture"><img src="${baseImgOP}/dou_03.png?${timeStamp}"> </div>
                <div class="text">
                  <div class="p1">小逗考典
                   	<div class=" front" >马上下载</div>
                  </div>
                  <p class="p2">教师资格备考神器，助你一站通关</p>
                </div>
             </div>
</div>
<div class="xd">
    <img src="${baseImgOP}/notice.png?${timeStamp}">
</div>
<input id="weixinFolder" type="hidden" value="product" >
<input id="shareTitle" type="hidden" value="${shareResponse.title}" >
<input id="shareContent" type="hidden" value="${shareResponse.content}" >
<input id="shareImageUrl" type="hidden" value="${shareResponse.imageUrl}" >
<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script type="text/javascript" src="${baseJsOP}/jquery-2.1.1.min.js?${timeStamp}"></script>
<script type="text/javascript" src="${baseJsOP}/share.js?${timeStamp}"></script>
</body>
</html>