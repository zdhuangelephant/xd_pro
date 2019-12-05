<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="utf-8">
    <meta name="viewport" content="initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
    <meta name="apple-mobile-web-app-capable" content="yes"/>
    <meta name="full-screen" content="yes"/>
    <meta name="screen-orientation" content="portrait"/>
    <meta name="x5-fullscreen" content="true"/>
    <meta name="360-fullscreen" content="true"/>
    <title>揪花瓣</title>
    <link href="${baseCssOP}/flower.css?${timeStamp}" rel="stylesheet">

</head>
<script type="text/javascript" src="${baseJsOP}/jquery-2.1.1.min.js?${timeStamp}"></script>
<script type="text/javascript">
var flag = ${flag!"null"};//是否下载，开关
localStorage.a = parseInt(${time});
if(!(navigator.userAgent.match(/MicroMessenger/i))){
 if(flag){
     window.location.href = "/product/notWeixing";
  }
}
</script>
<body>
<div class="loading">
     加载中。。。
</div>
<div class="main">
    <div class="download">
        <img src="${baseImgOP}/dou_03.png?${timeStamp}">
        <div class="downText">
            <p class="p1">小逗考典</p>
            <p class="p2">教师资格备考神器。有玩、有料、有重点。</p>
            <div class="downloadBtn">马上下载</div>
        </div>
    </div>
    <img src="${baseImgOP}/cloud.png?${timeStamp}" class="cloud1" id="cloud1">
    <img src="${baseImgOP}/cloud.png?${timeStamp}" class="cloud2" id="cloud2">

    <div class="bottom">
        <p>点击花瓣看看你有多幸运</p>
    </div>
    <div class="btns">
        <div class="again">
            <a href="pullPetals?flag=${flag}&time=${time}">
                <img src="${baseImgOP}/again_02.png?${timeStamp}">
                <p>再玩一次</p>
            </a>
        </div>
    </div>
    <div class="bottomBtns">
        <div class="bottomShare"><a href="jumpapp://www.51xiaodou.com/shareType=5">分享给TA</a></div>
        <div class="bottomAgain"><a href="pullPetals?flag=${flag}&time=${time}">再玩一次</a> </div>
    </div>
    <div class="topText"></div>
    <div class="flower">
        <div class="box">
            <img src="${baseImgOP}/flower_03.png?${timeStamp}" class="flowerBack">

            <div class="flowerHead">
                <div class="headBox">
                    <div class="faceBack">
                        <div class="face" id="face"></div>
                    </div>
                    <img src="${baseImgOP}/leaf1_03.png?${timeStamp}" class="leaf1 leafTween" id="leaf1" data-index="11">
                    <img src="${baseImgOP}/leaf2_03.png?${timeStamp}" class="leaf2 leafTween" id="leaf2" data-index="1">
                    <img src="${baseImgOP}/leaf3_03.png?${timeStamp}" class="leaf3 leafTween" id="leaf3" data-index="3">
                    <img src="${baseImgOP}/leaf4_03.png?${timeStamp}" class="leaf4 leafTween" id="leaf4" data-index="5">
                    <img src="${baseImgOP}/leaf5_03.png?${timeStamp}" class="leaf5 leafTween" id="leaf5" data-index="7">
                    <img src="${baseImgOP}/leaf6_03.png?${timeStamp}" class="leaf6 leafTween" id="leaf6" data-index="9">
                    <img src="${baseImgOP}/leafon6_03.png?${timeStamp}" class="leafOn6 leafTween" id="leafOn6" data-index="0">
                    <img src="${baseImgOP}/leafon1_03.png?${timeStamp}" class="leafOn1 leafTween" id="leafOn1" data-index="2">
                    <img src="${baseImgOP}/leafon2_05.png?${timeStamp}" class="leafOn2 leafTween" id="leafOn2" data-index="4">
                    <img src="${baseImgOP}/leafon3_03.png?${timeStamp}" class="leafOn3 leafTween" id="leafOn3" data-index="6">
                    <img src="${baseImgOP}/leafon4_03.png?${timeStamp}" class="leafOn4 leafTween" id="leafOn4" data-index="8">
                    <img src="${baseImgOP}/leafon5_03.png?${timeStamp}" class="leafOn5 leafTween" id="leafOn5" data-index="10">
                </div>
            </div>
            <img src="${baseImgOP}/left.png?${timeStamp}" class="left" id="leftLeaf">
            <img src="${baseImgOP}/right.png?${timeStamp}" class="right" id="rightLeaf">
        </div>
    </div>
    <div class="back"><img src="${baseImgOP}/bk1_02.png?${timeStamp}"></div>
    <div class="backOn"><img src="${baseImgOP}/bk2_03.png?${timeStamp}"></div>
</div>
<div class="xd">
    <img src="${baseImgOP}/notice.png?${timeStamp}">
</div>
<input id="weixinFolder" type="hidden" value="product" />
<input id="shareTitle" type="hidden" value="${shareResponse.title}" />
<input id="shareContent" type="hidden" value="${shareResponse.content}" />
<input id="shareImageUrl" type="hidden" value="${shareResponse.imageUrl}" />
<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script type="text/javascript" src="${baseJsOP}/jquery-2.1.1.min.js?${timeStamp}"></script>
<script type="text/javascript" src="${baseJsOP}/TweenMax.min.js?${timeStamp}"></script>
<script type="text/javascript" src="${baseJsOP}/zepto.js?${timeStamp}"></script>
<script type="text/javascript" src="${baseJsOP}/flower.js?${timeStamp}"></script>
<script type="text/javascript" src="${baseJsOP}/share.js?${timeStamp}"></script>

</body>
</html>