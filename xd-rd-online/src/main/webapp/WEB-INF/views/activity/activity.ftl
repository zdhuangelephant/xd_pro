<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>线下活动_瑞德在线</title>
<meta name="Keywords" content="瑞德活动,瑞德线下活动怎么样，成人教育,教育礼包,线下活动">
<link href="${baseImgOP}/rd-ico.png" rel="icon">
<link rel="stylesheet" type="text/css" href="${baseCssOP}/base.css">
<link rel="stylesheet" type="text/css" href="${baseCssOP}/style.css">
<script type="text/javascript" src="${baseJsOP}/jquery-1.7.min.js"></script>
<!--jq1.7Release-->
<script type="text/javascript" src="${baseJsOP}/service.js"></script>
<script type="text/javascript" src="${baseJsOP}/service-main.js"></script>
<script type="text/javascript" src="${baseJsOP}/loadMore.js"></script>
</head>
<body>
	<!--header-->
	<div class="top-wrap">
		<div class="top">
			<!--top logo nav-->
			<div class="top-left">
				<!--top logo-->
				<a class="logo" href="/" title="">
					<img alt="" src="${baseImgOP}/rd-logo.png" width="155" height="45">
				</a>
				<!--top nav-->
				<div class="nav">
					<ul>
						<li><a href="/">首页</a></li>
						<li><a href="/zyds">专业导师</a></li>
						<li><a href="/zyjs">专业介绍</a></li>
						<li><a href="/xxhd" class="active">线下活动</a></li>
					</ul>
				</div>
			</div>
			<!--top phone-->
			<div class="top-right">
				<div class="phone-logo">
					<img src="${baseImgOP}/phone.png" width="172" height="20">
				</div>
			</div>
		</div>
	</div>
	<!--content-->
	<div class="content-wrap">
		<div class="content">
			<div class="w1002">
				<div class="cont-left">
				<div class="bread">当前位置：<a href="/">首页</a>>><h1>线下活动</h1></div>
					<#if (activityList?size>0) >
					<div class="list-content">
						<#list activityList as activity> 
						<a class="dis-a"  href="javascript:;" onclick="toActivityDeatil(${activity.id});">
							<div class="cont-left-cont">
								<div class="cont-main">
									<div class="cont-main-img">
										<img src="${baseImgOP}/phone.png" alt="${activity.title }" width="210" height="116">
									</div>
									<div class="cont-main-sub">
										<h3>${activity.title }</h3>
										<p>${activity.subtitle }</p>
									</div>
								</div>
								<div class="cont-info">
									<div class="cont-info-left">
										<span>活动时间：${activity.activityTime?string("yyyy-MM-dd") }</span> <span>活动地点：${activity.activityPlace }</span>
									</div>
									<div class="cont-info-right">
										<div class="act-btn">查看详情</div>
									</div>
								</div>
							</div>
						</a> </#list>
					</div>
					<div id="loading27">加载中</div>
					<div class="tutor-btn">
						<a href="#" id="getMore" pageno="${pageNo+1}" typeid="2">加载更多</a>
					</div>
					<#else>
					<div class="none-date">暂时没有数据</div> 
					</#if>
				</div>
				<div class="cont-right">
					<div class="cont-article">
						<h3>最热文章</h3>
					</div>
					<div class="cont-article-list">
						<#if (paperList?size>0) >
						<ul>
							<#list paperList as paper>
							<li><a href="/paper/${paper.id}.html">${paper.title }</a></li>
							</#list>
						</ul>
						<#else>
							<div class="none-date">暂时没有数据</div> 
						</#if>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!--footer-->
	<div class="footer-wrap">
		<div class="footer-son">
			<div class="w1002">
				<div class="org-introduce-son">
					<h2 class="footer-h2 organization">机构介绍</h2>
					<p>瑞德机构—专注于职业教育，学历教育的互联网教育公司。</p>
					<p>瑞德机构的培训课程和服务范围广阔，提供全国精品直播课程、录播课程、面授课程等，业务涵盖上班族学历 MBA 注册会计师
						教师资格证等</p>
				</div>
				<div class="cnzz">
					<p>www.51readee.com 2015-2018 © All Rights Reserved.</p>
					<p>经营许可证编号：京ICP备17039644号　<script type="text/javascript">var cnzz_protocol = (("https:" == document.location.protocol) ? " https://" : " http://");document.write(unescape("%3Cspan id='cnzz_stat_icon_1262291076'%3E%3C/span%3E%3Cscript src='" + cnzz_protocol + "s22.cnzz.com/z_stat.php%3Fid%3D1262291076%26show%3Dpic' type='text/javascript'%3E%3C/script%3E"));</script></p>
				</div>
			</div>
		</div>
	</div>
	<!--在线客服-->
	<div class="main-im">
		<div id="open_im"></div>
	</div>
		<!-- 回到顶部 -->
	<div id="elevator_item">
		<a id="elevator" onclick="return false;" title="回到顶部"></a>
	</div>
</body>
<script type="text/javascript" src="${baseJsOP}/top.js"></script>
</html>