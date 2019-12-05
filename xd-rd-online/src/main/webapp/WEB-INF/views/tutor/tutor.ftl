<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>专业导师_继续教育讲师_瑞德在线</title>
<meta name="Keywords" content="明星导师,瑞德在线导师怎么样,名师课堂,教育指南,瑞德在线">
<link href="${baseImgOP}/rd-ico.png" rel="icon">
<link rel="stylesheet" type="text/css" href="${baseCssOP}/base.css">
<link rel="stylesheet" type="text/css" href="${baseCssOP}/style.css">
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
						<li><a href="/zyds" class="active">专业导师</a></li>
						<li><a href="/zyjs">专业介绍</a></li>
						<li><a href="/xxhd">线下活动</a></li>
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
				<div class="bread">当前位置：<a href="/">首页</a>>><h1>专业导师</h1></div>
				<#if (tutorList?size>0) >
					<div class="list-content">
						<#list tutorList as tutor> 
						<a class="dis-a" href="javascript:;" onclick="toTutorDeatil(${tutor.id});">
							
							<div class="tour-cont-left">
								<!-- 左边导师图片 -->
								<div class="tour-left-img">
									<img src="${tutor.image }" alt="${tutor.author }"  width="165" height="220">
								</div>
								<!-- 右边信息 -->
								<div class="tour-info-right">
									<!-- 导师信息 -->
									<div class="cont-main-sub tour-main-sub">
										<h3>${tutor.author }：${tutor.title }</h3>
										<p>${tutor.subtitle }</p>
									</div>
									<!-- 时间信息 点赞数 -->
									<div class="cont-info tour-cont-info">
										<div class="cont-info-left tour-info-left">
											<span>作者：瑞德在线</span> <span>${tutor.publishTime?string("yyyy-MM-dd") }</span>
										</div>
										<div class="cont-info-right">
											<span>赞(${tutor.thumbNums })</span>
										</div>
									</div>
								</div>
							</div>
						</a>
						</#list>
					</div>
					<div id="loading27">加载中</div>
					<div class="tutor-btn">
						<a href="javascript:;" typeid="1_tutor" id="getMore" pageno="${pageNo+1}">加载更多</a>
					</div>
					<#else>
						<div class="none-date">暂时没有数据</div> 
					</#if>
				</div>
				<div class="cont-right stu-cl">
					<div class="stu-gnosol cont-article">
						<h3>学员感悟</h3>
					</div>
					<#if (studentList?size>0) >
						<#list studentList as student>
						<div class="cont-article-list stu-gnosol-list">
							<a href="/xygw/${student.id }.html">
								<p class="stu-info">
									<img src="${student.portrait}"> <span>${student.author}</span>
									<span>${student.tutorMajorModel.title}</span>
								</p>
								<p class="stu-mes">感悟:${student.thinkDesc}</p>
								<p class="stu-time">${student.createTime?string("yyyy-MM-dd")}</p>
							</a>
						</div>
						</#list>
					<#else>
						<div class="none-date">暂时没有数据</div> 
					</#if>
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
<script type="text/javascript" src="${baseJsOP}/jquery-1.7.min.js"></script>
<script type="text/javascript" src="${baseJsOP}/service.js"></script>
<script type="text/javascript" src="${baseJsOP}/service-main.js"></script>
<script type="text/javascript" src="${baseJsOP}/loadMore.js"></script>
<script type="text/javascript" src="${baseJsOP}/top.js"></script>
</html>