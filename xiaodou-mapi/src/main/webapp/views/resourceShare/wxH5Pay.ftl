<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <!--<meta name="viewport" content="initial-scale=1.0, minimum-scale=1.0, 
    maximum-scale=1.0, user-scalable=no"/>-->
    <meta name="viewport" content="width=device-width,initial-scale=1.0, 
    minimum-scale=1.0, maximum-scale=1.0, user-scalable=0"/>
    <meta name="apple-mobile-web-app-capable" content="yes"/>
    <meta name="full-screen" content="yes"/>
    <meta name="screen-orientation" content="portrait"/>
    <meta name="x5-fullscreen" content="true"/>
    <meta name="360-fullscreen" content="true"/>
    <link href="${baseCssOP}/redpacket.css?12${timeStamp}" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="${baseJsOP}/jquery-2.1.1.min.js?${timeStamp}"></script>
    <title>赞赏   ${resource.people}</title>
</head>
<body>
<div class="main">
        <div class="xiaoDou">
           <p class="authorPortrait">
           <img style="background:url('${baseImgOP}/head.png') no-repeat" src="${resource.portrait}"></p>
            <p class="authorName">${resource.people}</p>
            <p class="articleTitle">《${resource.title}》</p>
        </div>
        <div class="zanshanBox">
            <div class="rmb"><img src="${baseImgOP}/money.png"></div>
        	<input type="text" onfocus="inputGift();" class="rmbInput"  id="giftMoney" value="${defaultGiftMoney}" placeholder="单次赞赏不超过200元"/>
        </div>
        <div class="random" onclick="generateGiftMoney()">随机金额</div>
        <div class="confirm" onclick="whH5Pay()">确认赞赏</div>
        
</div>
<input id="wxSnsapiUserinfo" type="hidden" value="${wxSnsapiUserinfo}" />
<input id="openid" type="hidden" value="${wxSnsapiUserinfo.openid}" />
<input id="nickname" type="hidden" value="${wxSnsapiUserinfo.nickname}" />
<input id="headimgurl" type="hidden" value="${wxSnsapiUserinfo.headimgurl}" />
<input id="resourcesId" type="hidden" value="${resource.resourcesId}" />
<input id="targetUserId" type="hidden" value="${resource.userId}" />
<input id="resourceTitle" type="hidden" value="${resource.title}" />
<input id="giftType" type="hidden" value="${resource.digest}" />
<input id="portrait" type="hidden" value="${resource.portrait}" />
<input id="wxResult" type="hidden" value="${wxResult}"/>
<input id="defaultGiftMoney" type="hidden" value="${defaultGiftMoney}"/>
<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script type="text/javascript" src="${baseJsOP}/layer/layer.js"></script>
<script type="text/javascript" src="${baseJsOP}/wxPay.js?${timeStamp}"></script>
</body>
</html>