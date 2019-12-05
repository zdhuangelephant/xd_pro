<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <!--<meta name="viewport" content="initial-scale=1.0, minimum-scale=1.0, 
    maximum-scale=1.0, user-scalable=no"/>-->
    <meta name="viewport" content="width=device-width,initial-scale=1.0, 
    minimum-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
    <meta name="apple-mobile-web-app-capable" content="yes"/>
    <meta name="full-screen" content="yes"/>
    <meta name="screen-orientation" content="portrait"/>
    <meta name="x5-fullscreen" content="true"/>
    <meta name="360-fullscreen" content="true"/>
    <link href="${baseCssOP}/share.css?${timeStamp}" rel="stylesheet" type="text/css">
    <link href="${baseCssOP}/webview.css?${timeStamp}" rel="stylesheet" type="text/css">
    <link href="${baseCssOP}/article-templet.css?${timeStamp}" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="${baseJsOP}/jquery-2.1.1.min.js?${timeStamp}"></script>
    <title>${forum.forumTitle}</title>
</head>
<script type="text/javascript">
if(!(navigator.userAgent.match(/MicroMessenger/i))){
     //window.location.href = "/mapi/not_wei_xin";
    $(".gift").hide();
}
</script>
 <body>
    <div class="top" >
      <div class="logo">
        <img src="${baseOP}/image/sharemo.png" alt="New logo">
      </div>
      <div class="slogan">
        <div class="slogan-title">深度知识分享学习平台</div>
      </div>
      <a class="app-download"  target="_blank" href="http://a.app.qq.com/o/simple.jsp?pkgname=com.xiaodou.android.app.moshare">免费下载</a>
    </div>
    <div class="article">
      <h1 class="title">${forum.forumTitle}</h1>
      <div class="info">
        <#if forum.authorPortrait?? && forum.authorPortrait?length gt 0>
          <div class="author-img">
            <img  height="36px" width="36px" src="${forum.authorPortrait}"/>
          </div>
           </#if>
         <#if forum.authorName?? && forum.authorName?length gt 0>
          <p class="author">${forum.authorName}</p>
            </#if>
          <#if forum.createTime?? && forum.createTime?length gt 0>
          <p class="release-date">${forum.createTime}</p>
          </#if>
          <div class="clearfix"></div>
      </div>
	   <#if forum.forumClassify==3>
	    <#if forum.forumMedia?? && forum.forumMedia?length gt 0>
		  <div class="media">
			  <audio src="${forum.forumMedia}" type="audio/mp3" controls="controls"></audio>
		  </div>
	    </#if>
	   </#if>
	   <#if  forum.forumClassify==2>
		  <div class="media">
			  <video width="640" height="480" src="${forum.forumMedia}" controls="controls" autobuffer=""></video>
			  <!--  poster="${forum.forumCover}" -->
		  </div>
	   </#if>
      <div id="resourceContent" class="content">
			${forum.forumContent}
      </div>
        <#if forum.forumTagName?? && forum.forumTagName?length gt 0>
      <div class="article-tab">
      	  标签：<span>${forum.forumTagName}</span>
      </div>
       </#if>
    </div>
    <div class="footer">
      <a class="app-download" target="_blank" href="http://a.app.qq.com/o/simple.jsp?pkgname=com.xiaodou.android.app.moshare">马上下载，开始学习</a>
    </div>

<input id="weixinFolder" type="hidden" value="forum" >
<input id="resourcesId" type="hidden" value="${resource.resourcesId}" >
<input id="shareImageUrl" type="hidden" value="${iconUrl}" >
<input id="timeStamp" type="hidden" value="${timeStamp}">
<input id="resourceTitle" type="hidden" value="${forum.forumTitle}" />
<input id="resourceContent" type="hidden" value="${withoutTagData}"/>

<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script type="text/javascript" src="${baseJsOP}/share.js?${timeStamp}"></script>
<script type="text/javascript" src="${baseJsOP}/loopPic.js?${timeStamp}"></script>
<script type="text/javascript" src="${baseJsOP}/layer/layer.js"></script>
</body>
</html>