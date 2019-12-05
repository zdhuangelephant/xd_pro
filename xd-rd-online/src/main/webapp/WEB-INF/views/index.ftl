<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>北京成人自考机构_北京继续教育机构|北京瑞德在线_学历教育中心</title>
<meta name="Keywords" content="瑞德在线,瑞德教育,瑞德在线官网,教育机构,瑞德在线">
<link href="${baseImgOP}/rd-ico.png" rel="icon">
<link rel="stylesheet" type="text/css" href="${baseCssOP}/base.css">
<link rel="stylesheet" type="text/css" href="${baseCssOP}/style.css">
<script src="${baseJsOP}/jquery-1.7.min.js"></script>
<script type="text/javascript" src="${baseJsOP}/service.js"></script>
<script type="text/javascript" src="${baseJsOP}/service-main.js"></script>
<script type="text/javascript" src="${baseJsOP}/banner.js"></script>
<script type="text/javascript" src="${baseJsOP}/top.js"></script>
</head>
<body>
	<div class="banner-wrap banner-wrap-position">
		<!--header-->
		<div class="top-wrap top-wrap-position">
			<div class="top">
				<!--top logo nav-->
				<div class="top-left">
					<!--top logo-->
					<h1>
						<a class="logo" href="/" title="瑞德在线">
							<img alt="瑞德在线" src="${baseImgOP}/rd-logo.png" width="155" height="45">
						</a>
					</h1>
					<!--top nav-->
					<div class="nav nav-color">
						<ul>
							<li><a href="/" class="active">首页</a></li>
							<li><a href="/zyds" target="_blank">专业导师</a></li>
							<li><a href="/zyjs" target="_blank">专业介绍</a></li>
							<li><a href="/xxhd" target="_blank">线下活动</a></li>
						</ul>
					</div>
				</div>
				<!--top phone-->
				<div class="top-right no-logo">
					<div class="phone-logo">
						<img src="${baseImgOP}/phone.png" alt="电话：101-512-86600" width="172" height="20">
					</div>
				</div>
			</div>
		</div>
		<div id="banner_tabs" class="flexslider indexbanner">
			<ul class="slides">
				<li><img width="1920" height="600" alt=""
					style="background: url(${baseImgOP}/banner1.png) no-repeat center;">
				</li>
				<li><img width="1920" height="600" alt=""
					style="background: url(${baseImgOP}/tea2.jpg) no-repeat center;">
				</li>
				<li><img width="1920" height="600" alt=""
					style="background: url(${baseImgOP}/sis.jpg) no-repeat center;">
				</li>
				<li><img width="1920" height="600" alt=""
					style="background: url(${baseImgOP}/bro.jpg) no-repeat center;">
				</li>
			</ul>
			<ul class="flex-direction-nav">
				<li><a class="flex-prev" href="javascript:;">Previous</a></li>
				<li><a class="flex-next" href="javascript:;">Next</a></li>
			</ul>
			<ol id="bannerCtrl" class="flex-control-nav flex-control-paging">
				<li class="active-ico"></li>
				<li></li>
				<li></li>
				<li style="margin-right: 0;"></li>
			</ol>
			<a class="consult" id="now">马上咨询</a>
		</div>
	</div>

	<!--tutor-->
	<div class="tutor-wrap">
		<div class="tutor-content w1002">
			<!-- <p class="tutor-p1">2017秋，我们只招募<strong>300人</strong>精学<br>额满为止</p> -->
			<p class="tutor-p1">北京首推导师制</p>
			<p class="tutor-p3">口传心授 · 亦师亦友</p>
			<p class="tutor-p2">
			每名导师大咖只带20人
			</p>
		</div>
		<div class="tutor-list w1002 t-img">
			<p class="more">
				<a href="/zyds">更多导师>></a>
			</p>
			<ul>
				<input type="hidden" id="number">
				<#list tutorList as tutor> 
				<li>
	                
	                <#if tutor_index = 0> <a class="tutor-click" href="/zyds/${tutor.id }.html">
	                <#elseif tutor_index= 1><a class="tutor-click" href="/zyds/${tutor.id }.html">
	                <#elseif tutor_index= 2><a class="tutor-click">
	                <#else><a class="tutor-click">
	                </#if>
	                    <div class="shadow-wrap">
	                    	<p>${tutor.title}</p>
			                <span>
			                    <#if tutor_index = 0> <img src="${baseImgOP}/1.png"
										width="20" height="20"> <img src="${baseImgOP}/2.png"
										width="20" height="20"> <img src="${baseImgOP}/3.png"
										width="20" height="20"> <#elseif tutor_index = 1> <img
										src="${baseImgOP}/4.png" width="20" height="20"> <img
										src="${baseImgOP}/5.png" width="20" height="20"> <img
										src="${baseImgOP}/6.png" width="20" height="20"> <#elseif
										tutor_index = 2> <img src="${baseImgOP}/7.png" width="20"
										height="20"> <img src="${baseImgOP}/9.png" width="20"
										height="20"> <img src="${baseImgOP}/8.png" width="20"
										height="20"> <#else> <img src="${baseImgOP}/10.png"
										width="20" height="20"> <img src="${baseImgOP}/11.png"
										width="20" height="20"> <img src="${baseImgOP}/12.png"
										width="20" height="20"> </#if>
			                	共<em>
									<#if tutor_index= 0> 14 <#elseif tutor_index = 1> 17 <#elseif tutor_index = 2>20 <#else> 20 </#if>
								</em>人报名
			                </span>
	                		<span>剩余名额<em<#if tutor_index= 2> style="color:red"<#elseif tutor_index = 3> style="color:red"</#if>>
								<#if tutor_index = 0> 6 <#elseif tutor_index = 1> 3 <#elseif tutor_index = 2> 0 <#else> 0 </#if>
							</em>人</span>
							<#if tutor_index= 0>
	                        <input type="button" value="立即报名" class="now1" id="input-btn">
	                        <#elseif tutor_index= 1>
	                        <input type="button" value="立即报名" class="now1" id="input-btn">
	                        <#elseif tutor_index= 2>
	                        <input id="input-btn" type="button" value="名额已满" style="background-color: rgb(212, 212, 212);cursor:auto" disabled="disabled">
	                        <#else>
	                        <input id="input-btn" type="button" value="名额已满" style="background-color: rgb(212, 212, 212);cursor:auto" disabled="disabled">
	                        </#if>
	                        
	                    </div>
	                    <div class="show-font">
	                    	<span>
	                    	<#if tutor_index= 0>正是那些<br>不可测量的事物<#elseif tutor_index = 1>认识你的时间<br>只要你肯<#elseif tutor_index = 2>正是那些<br>不可测量的事物<#else>认识你的时间<br>只要你肯</#if>
	                    	</span>
	                    	<p>
	                    	<#if tutor_index = 0>才使可测量<br>得以诞生<#elseif tutor_index = 1>就是一条卓<br>有成效之路<#elseif tutor_index = 2>才使可测量<br>得以诞生<#else>就是一条卓<br>有成效之路</#if>
	                    	</p>
	                    </div>
	                    <img src="${tutor.image}" alt="${tutor.title}" width="242" height="323">
	                </a>
            	</li>
            	</#list>
			</ul>
		</div>
	</div>
	<!--course-->
	<div class="course-wrap">
		<div class="course">
			<div class="w1002 course-tab">
				<h2 class="common-h2">专业课程</h2>
				<ul id="course-btn-item1" class="course-btn-item">
					<#list majorCategoryList as majorCategory>
					<li<#if (majorCategory_index=0)> class="active2 course-btn"
						<#else> class="course-btn" </#if>> <a> <span>${majorCategory.majorCategory }</span>
							<strong>（${majorCategory.remark }）</strong>
					</a>
					</li> </#list>
				</ul>
				<div>
					<ul id="course-list1">
						<#list majorCategoryList as majorCategory>
						<li class="course-list"<#if (majorCategory_index=0)>
							style="display: block;" </#if>> <#list
							majorCategory.listTutorMajor as tutorMajor>
							<div class="course-list-item">
								<div class="course-list-img1">
									<img src="${tutorMajor.image}" alt="${tutorMajor.majorName}">
								</div>
								<p>${tutorMajor.majorName}</p>
								<a
									href="/zyjs/${tutorMajor.id}.html ">查看专业详情</a>
							</div> </#list>
						</li> </#list>
					</ul>
				</div>
			</div>
		</div>
	</div>
	<!--school calendar-->
	<div class="s-calendar-wrap">
		<div class="s-calendar">
			<div class="w1002">
				<h2 class="common-h2">瑞德校历</h2>
				<ul id="course-btn-item" class="course-btn-item">
					<#list majorCategoryList as majorCategory>
					<li<#if (majorCategory_index=0)> class="active3 course-btn"
						<#else> class="course-btn" </#if>> <a> <span>${majorCategory.majorCategory }</span>
							<strong>（${majorCategory.remark }）</strong>
					</a>
					</li> </#list>
				</ul>
				<div>
					<ul id="calendar-list">
						<#list majorCategoryList as majorCategory>
						<li class="s-calendar-list"<#if (majorCategory_index=0)>
							style="display: block;" </#if>> <img src="${majorCategory.image}" alt="${majorCategory.majorCategory }"
							width="831" height="288">
						</li> </#list>
					</ul>
				</div>
			</div>
		</div>
	</div>
	<!--course character-->
	<div class="course-chara-wrap">
		<div class="course-chara">
			<div class="w1002">
				<div>
					<ul id="chara-list">
						<li class="course-chara-list" style="display: block;"><img
							src="${baseImgOP}/arkcjx.png" width="620" height="438" alt="AR课程教学"></li>
						<li class="course-chara-list"><img
							src="${baseImgOP}/dkdk.jpg" width="620" height="438" alt="大咖代课"></li>
						<li class="course-chara-list"><img
							src="${baseImgOP}/xxhd.png" width="620" height="438" alt="线下活动"></li>
					</ul>
				</div>
				<ul id="course-chara" class="course-chara-item">
					<li class="chara-btn chara-active">
						<div class="chara-logo">
							<div class="chara-logo1"></div>
						</div>
						<div class="chara-title">
							<p>AR课程教学</p>
							<p>利用APP随时随地学习，获得和现场名师课堂相同的体验</p>
						</div>
					</li>
					<li class="chara-btn">
						<div class="chara-logo">
							<div class="chara-logo2"></div>
						</div>
						<div class="chara-title">
							<p>大咖代课</p>
							<p>全国首家实施大咖带课的学校，让名师成为你的班主任</p>
						</div>
					</li>
					<li class="chara-btn">
						<div class="chara-logo">
							<div class="chara-logo3"></div>
						</div>
						<div class="chara-title">
							<p>线下活动</p>
							<p>和行业资深大咖一起参与活动，零距离交流</p>
						</div>
					</li>
				</ul>
			</div>
		</div>
	</div>
	<!--student-->
	<div class="stu-wrap">
		<div class="stu">
			<div class="w1002 stu-list">
				<h2 class="common-h2">学员成长日记</h2>
				<ul>
					<#list studentList as student>
					<li style="background: none;"><a class="tutor-click stu-click"
						href="/xygw/${student.id}.html"> <img
							src="${student.portrait}" width="69" height="69" alt="${student.author}"> <span>${student.author}
								<br>${student.tutorMajorModel.title}</span>
							<p>${student.thinkDesc}</p>
					</a></li>
					<li class="line"<#if (student_index=3&&student_index=7)>
						style="display: none;" </#if>></li>
					<!-- <#if (bigMajor_index=0)> style="display: block;" </#if> -->
					</#list>
				</ul>
			</div>
		</div>
	</div>
	<!--footer-->
	<div class="footer-wrap">
		<div class="footer">
			<div class="w1002">
				<div class="co-operative">
					<h2 class="footer-h2 company">合作企业</h2>
					<div class="footer-logo">
						<img src="${baseImgOP}/footer-logo1.png" alt="百度"> <img
							src="${baseImgOP}/footer-logo2.png" alt="支付宝"> <img
							src="${baseImgOP}/footer-logo3.png" alt="腾讯网"> <img
							src="${baseImgOP}/footer-logo4.png" alt="360搜索">
					</div>
				</div>
				<div class="org-introduce">
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
	<script src="${baseJsOP}/slider.js"></script>
	<script type="text/javascript">
    $(function() {
        var bannerSlider = new Slider($('#banner_tabs'), {
            time: 3000,
            delay: 400,
            event: 'hover',
            auto: true,
            mode: 'fade',
            controller: $('#bannerCtrl'),
            activeControllerCls: 'active-ico'
        });
        $('#banner_tabs .flex-prev').click(function() {
            bannerSlider.prev()
        });
        $('#banner_tabs .flex-next').click(function() {
            bannerSlider.next()
        });
    });
</script>
</body>
</html>
