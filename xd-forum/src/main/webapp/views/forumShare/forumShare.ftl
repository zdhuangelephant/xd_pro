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
    <link href="${baseCssOP}/share.css??${timeStamp}" rel="stylesheet" type="text/css">
    <title></title>
</head>
<script type="text/javascript">
if(!(navigator.userAgent.match(/MicroMessenger/i))){
     window.location.href = "/forum/notWeixing";
}
</script>
<body>
<div class="main">
              <div class="download"><!--小逗考典，马上下载-->
                <div class="picture"><img src="${baseImgOP}/xiaodou_03.png?${timeStamp}"> </div>
                <div class="text">
                  <div class="p1">小逗考典
                   	<div class=" front" >马上下载</div>
                  </div>
                  <p class="p2">教师资格备考神器，助你一站通关</p>
                </div>
             </div>
    <div class="subject"><!--话题及评论部分-->
        <div class="subjectDetail"><!--话题部分-->
            <div class="subjectTitle">
            	<div class="tu"><img src="${baseImgOP}/jian_03.png?${timeStamp}"></div>
                <div class="title">${forumModel.title}</div><!--话题题目-->
            </div>
            <div class="text">${forumModel.content}</div>
            <div class="picture"><!--话题图片-->
              
                <#list forumModel.images as img>
	            <tr>
	              	<div class="pictureBox"><img src="${img}"></div>
	            </tr>
	            </#list>
            </div>
    
			<#if (hotCommentsCount > 0)>
				<div class="discuss"><!-- 评论部分 -->
				  <div class="hot">热门评论 （<span>${hotCommentsCount}</span>条 ）</div>
					<#list hotComments as hotComment>
						<div class="box">
							<div class="people">
								<#if hotComment.portrait??>
									<img src="${hotComment.portrait}" class="headImg">
								<#else>
									<img src="${baseImgOP}/xiaodou_03.png?${timeStamp}" class="headImg">
								</#if>
								<div class="name">${hotComment.people}</div>
								<div class="date">${hotComment.time}</div>
								<div class="clear"></div>
							</div>
							<div class="reply1">${hotComment.content}</div>
							<#if hotComment.targeContent??>
							<div class="reply2">${hotComment.targeContent}</div>
							</#if>
						</div>
					</#list>
				</div>
			</#if>
			
			<#if (normalCommentsCount > 0)>
				<div class="discuss"><!-- 评论部分 -->
				  <div class="hot">其它评论 （<span>${normalCommentsCount}</span>条 ）</div>
					<#list normalComments as normalComment>
						<div class="box">
							<div class="people">
								<#if normalComment.portrait??>
									<img src="${normalComment.portrait}" class="headImg">
								<#else>
									<img src="${baseImgOP}/xiaodou_03.png?${timeStamp}" class="headImg">
								</#if>
								<div class="name">${normalComment.people}</div>
								<div class="date">${normalComment.time}</div>
								<div class="clear"></div>
							</div>
							<div class="reply1">${normalComment.content}</div>
							<#if normalComment.targeContent??>
							<div class="reply2">${normalComment.targeContent}</div>
							</#if>
						</div>
					</#list>
				</div>
			</#if>
        </div>
        <div class="checkAll">查看全部</div>
	</div>
</div>
<div class="pictureBig">
    <div class="bigBox">
        <img src="${baseImgOP}/xiaodou_03.png?${timeStamp}">
    </div>
</div>
<div class="xd">
    <img src="${baseImgOP}/notice.png?${timeStamp}">
</div>
<input id="weixinFolder" type="hidden" value="forum" >
<input id="shareTitle" type="hidden" value="${shareResponse.title}" >
<input id="shareContent" type="hidden" value="${shareResponse.content}" >
<input id="shareImageUrl" type="hidden" value="${shareResponse.imageUrl}" >
<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script type="text/javascript" src="${baseJsOP}/jquery-2.1.1.min.js?${timeStamp}"></script>
<script type="text/javascript" src="${baseJsOP}/share.js?${timeStamp}"></script>
<script type="text/javascript" src="${baseJsOP}/loopPic.js?${timeStamp}"></script>
</body>
</html>