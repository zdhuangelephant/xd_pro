<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>${forum.forumTitle}_慕享云测评</title>
    <meta name="Description" content="${forum.htmlContent}">
	<!-- css js begin  -->
	<#include "/common/style.ftl">
	<!-- css js end -->
    <script type="text/javascript" src="${baseJsOP}/thumbs.js"></script>
</head>
<body>

<!--top begin-->
<div class="top-wrap">
    <div class="top-box w1002">
        <div class="mo-logo" title="慕享云测评">
            <a href=""><img src="${baseOP}/images/mo-logo.png" alt="慕享云测评"></a>
        </div>
        <div class="top-nav">
            <ul>
                <li><a href="/">首页</a></li>
                <li><a href="/moshare">慕享APP</a></li>
                <li><a href="/moknowledge" class="active">慕享知识库</a></li>
                <li><a href="/mocooperate">合作单位</a></li>
            </ul>
        </div>
        <ul class="phoneNav">
            <li><a href="/"> 首页</a></li>
            <li><a href="/moshare">慕享APP</a></li>
            <li><a href="/moknowledge" class="present">慕享知识库</a></li>
            <li><a href="/mocooperate">合作单位</a></li>
        </ul>
        <!--导航 btn begin-->
        <div class="top-btn f_r">
            <a href="#" class="click"></a>
        </div>
        <!--导航 btn end-->
    </div>
</div>
<!--top end-->

<!--knowledge content begin-->
<div class="k-wrap">
    <div class="k-box w1002">
        <!--knowledge list-->
        <div class="know-list">
            <div class="bread">
                当前位置：<a href="/">首页</a>>><a href="/moknowledge">慕享知识库</a>>><span>${forum.forumTitle}</span>
            </div>
            <#if forum??>
            <div class="k-list-box">
                <h1 class="d-title">${forum.forumTitle} </h1>
                <div class="d-info">
                    <div class="d-logo">
                        <img src="${baseOP}/images/d-logo.png" alt="慕享" width="36px" height="36px">
                        	<#if forum.forumType ==1>公开课
							<#elseif forum.forumType ==2>知识分享
							<#elseif forum.forumType ==4>校园之声
							</#if>
                    </div>
                    <div class="d-time">${forum.createTime?string("yyyy-MM-dd") }</div>
                </div>
	                <div class="d-media">
	                	<#if forum.forumClassify ==2>
	                    <video width="620" height="460" src="${forum.forumMedia }" controls="controls" autobuffer></video>
	                	<#elseif forum.forumClassify ==3>
                  		<audio src="${forum.forumMedia }" controls="controls" type="audio/mp3"></audio>
						</#if>
	                </div>
                
                <div class="d-content">
                    <p>${forum.forumContent }</p>
                </div>
                <div class="zan">
                    <span>阅读(${forum.forumReaderNum }) </span><span><a
                        id="thumbsId" href="javascript:void(0)"
                        onclick="doThumbs('${forum.forumId}','${forum.forumPraiserNum}')">赞(${forum.forumPraiserNum })</a></span>
                </div>
            </div>
            <#else>
				${msg} 
			</#if>
        </div>
        <!--knowledge info-->
        <div class="know-info">
            <h3>欢迎投稿</h3>
            <p>慕享云测评致力于高等教育线上学习评价研究，促进优质教育资源共建共享，是一个运用人工智能技术，搭建教，学，管，考，评五合一的学习综合评价平台。</p>
            <p>现发布长期征稿启事，诚邀全国对“教育学”有独到见解的广大教师和专家学者前来投稿。文章内容题材不限，字数不限。</p>
            <p>投稿请将稿件以电子邮件形式发至征稿邮箱，邮件请注明作者姓名，联系方式和地址，或直接投递至慕享云测评微信公众号平台。</p>
            <p class="email">征稿邮箱：<span>tougao@corp.51xiaodou.com</span></p>
            <p class="chat">微信公众号：慕享云测评</p>
        </div>
    </div>
</div>
<!--knowledge content end-->
	
	<!-- 返回顶部 begin -->
	<div id="elevator_item">
		<a id="elevator" onclick="return false;" title="回到顶部"></a>
	</div>
	<!-- 返回顶部 end -->
	
<#include "/common/footer.ftl">
</body>
</html>