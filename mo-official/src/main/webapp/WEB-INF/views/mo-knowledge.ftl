<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>慕享知识库_慕享云测评</title>
<meta name="Keywords" content="慕享云测评,慕享知识库,慕享公开课,公开课,自考本科,学前教育,在线教育">
<meta name="Description" content="慕享知识库是慕享云测评在教学课程方面邀请国内各大高校知名学科专家及教授针对专业课程的知识详解与实践应用拓展平台，能够提供丰富的教育教学资源与便捷的互动交流方式,促进教育公平与资源共享，提高教学过程中知识管理水平和教学质量。">

<!-- css js begin  -->
<#include "/common/style.ftl">
<#include "/common/loadmore.ftl">
<!-- css js end -->
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
					当前位置：<a href="/">首页</a>>><h1>慕享知识库</h1>
				</div>
				<#if (forumList?size>0) > 
				<#list forumList as forum>
				<div class="k-list-append">
					<div class="k-list-box k-list-box2">
						<div class="k-list-top">
							<div class="k-list-top-img">
								<a href="javascript:;" onclick="toForumDeatil(${forum.forumId});" >
									<img src="${forum.forumCover}" alt="${forum.forumTitle}">
									<div class="k-lable">
									<#if forum.forumType ==1>
										<img src="${baseOP}/images/k-course.png">
									<#elseif forum.forumType ==2>
										<img src="${baseOP}/images/k-share.png">
									</#if>
									</div>
									
								</a>
							</div>
							<div class="k-list-top-text">
								<h3>
									<a href="javascript:;" onclick="toForumDeatil(${forum.forumId});" >${forum.forumTitle}</a>
								</h3>
								<p><a href="javascript:;" onclick="toForumDeatil(${forum.forumId});">${forum.htmlContent}</a></p>
							</div>
						</div>
						<div class="k-list-bottom">
							<p>作者：${forum.author.name}　　发布时间：${forum.createTime?string("yyyy-MM-dd") }</p>
							<span>赞（${forum.forumPraiserNum}）</span>
						</div>
					</div>
				</div>
				</#list>
				<div id="loading27">加载中</div>
				<div class="tutor-btn">
					<a href="#" id="getMore" pageno="${pageNo+1}">加载更多</a>
				</div>
				<#else>
				<div class="none-date">暂时没有数据</div>
				</#if>
			</div>
			<!--knowledge info-->
			<div class="know-info">
				<h3>欢迎投稿</h3>
				<p>慕享云测评致力于高等教育线上学习评价研究，促进优质教育资源共建共享，是一个运用人工智能技术，搭建教，学，管，考，评五合一的学习综合评价平台。</p>
				<p>现发布长期征稿启事，诚邀全国对“教育学”有独到见解的广大教师和专家学者前来投稿。文章内容题材不限，字数不限。</p>
				<p>投稿请将稿件以电子邮件形式发至征稿邮箱，邮件请注明作者姓名，联系方式和地址，或直接投递至慕享云测评微信公众号平台。</p>
				<p class="email">
					征稿邮箱：<span>tougao@corp.51xiaodou.com</span>
				</p>
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