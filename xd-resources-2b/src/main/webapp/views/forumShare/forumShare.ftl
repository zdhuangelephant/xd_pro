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
<!--if(!(navigator.userAgent.match(/MicroMessenger/i))){
     window.location.href = "/asked/notWeixing";
}
-->
</script>
<body>
<div class="main">
              <div class="download"><!--小逗考典，马上下载-->
                <div class="picture"><img src="${baseImgOP}/xiaodou_03.png?${timeStamp}"> </div>
                <div class="text">
                  <div class="p1">
                   	<div class=" front" >马上下载</div>
                  </div>
                  <p class="p2">教师资格备考神器，助你一站通关</p>
                </div>
             </div>
    <div class="subject"><!--话题及评论部分-->
        <div class="subjectDetail"><!--话题部分-->
            <div class="subjectTitle">
            	<div class="tu"><img src="${baseImgOP}/jian_03.png?${timeStamp}"></div>
                <div class="title">${resource.title}</div><!--话题题目-->
            </div>
            <div class="subjectTime">
            	<div class="tu"><img src="${resource.portrait}?${timeStamp}"></div>
                <div class="title">${resource.people}</div>
                <div class="title">${resource.time}</div>
            </div>
            <div class="picture"><!--话题视频地址-->
            	<tr>
	              	<div class="pictureBox"><img src="${resource.videoUrl}"></div>
	            </tr>
            </div>
            <div class="text">${resource.content}</div>
            <div class="picture"><!--话题图片-->
                <#list resource.images as img>
	            <tr>
	              	<div class="pictureBox"><img src="${img}"></div>
	            </tr>
	            </#list>
            </div>
    
    <!--赞赏 -->
			<div class="gift">
				<div class="giftDesc">${resource.giftDesc}</div>
				<div>/n<span>共${resource.rewardCount}次赞赏</span></div>
				<#list resource.rewardRecordList as rewardRecord>
	              	<div ><img src="${rewardRecord.portrait}"></div>
				</#list>
			</div>
			
			<div class="p1">
                   	<div class=" front" >马上下载</div>
            </div>
			
			<#if (resource.repliesCount > 0)>
				<div class="discuss"><!-- 评论部分 -->
				  <div class="hot">全部评论 （<span>${resource.repliesCount}</span>条 ）</div>
					<#list commentList as comment>
						<div class="box">
							<div class="people">
								<#if comment.portrait??>
									<img src="${comment.portrait}" class="headImg">
								<#else>
									<img src="${baseImgOP}/xiaodou_03.png?${timeStamp}" class="headImg">
								</#if>
								<div class="name">${comment.people}</div>
								<div class="date">${comment.time}</div>
								<div class="clear"></div>
							</div>
							<div class="reply1">${comment.content}</div>
							<!--
							<#if comment.targeContent??>
							<div class="reply2">${comment.targeContent}</div>
							</#if>
							-->
						</div>
					</#list>
				</div>
			</#if>
        </div>
        <div class="checkAll">打开慕享，查看全部评论</div>
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