<#include "/common/common-header.ftl"/>

<div class="index-content-wrap" id="indexWrapId">
	<!-- index left begin -->
	<div class="index-content-left-wrap">
	<div class="wrap-border">
		<div class="index-cont-left-title">调度分析</div>
		<div class="left-list-wrap">
			<ul class="left-list-wrap-ul">
				<#if caseList?? && (caseList?size > 0) >
				<#list caseList as case>
					<li>
						<span class="left-li-name">${case.name}</span> 
						<#if case.lastTime =='1'>
							<span class="left-li-state-success"> 
								整体成功率：<span>${case.successRate}%</span>
								近5次成功：<span >${case.nearlyFiveSuccessRate}%</span> 上次结果：<span>成功</span>
							</span>
						<#elseif case.lastTime == '-1'>
							<span class="left-li-state-failed">
								整体成功率：<span>${case.successRate}%</span>
								近5次成功：<span >${case.nearlyFiveSuccessRate}%</span> 上次结果：<span>失败</span>
							</span>
						<#else>
							<span class="left-li-state-success"> 
								整体成功率：<span>${case.successRate}%</span>
								近5次成功：<span >${case.nearlyFiveSuccessRate}%</span> 上次结果：<span>无</span>
							</span>
						</#if>
					</li>
				</#list>
			</#if>
			</ul>
		</div>
	</div>
	</div>
	<input type="hidden" name="version" value="${version }">
	<input type="hidden" name="updateTime" value="${updateTime }">
	
	<!-- index left end -->
	<!-- index right begin -->
	<div class="index-content-right-wrap">
	<div class="wrap-border">
		<!-- search begin -->
		<div class="index-right-search">
			<!-- <span>文档</span> <input type="text" class="main-search" id="searchV" placeholder="搜索..." onkeyup="mySearch(this)">
			<input type="button" class="index-search-btn" value="搜索" > -->
			<div class="search search-reset index-input-wrap">
				<input type="text" placeholder="文档搜索..." id="searchV" onkeyup="mySearch(this)">
				<a href="javascript:" class="reset-inputvalue" id="indexResetinput" onclick="resetPrevInput(this)"></a>
			</div>
		</div>
		<!-- search end -->
		<div class="rigth-list-wrap">
			<ul class="rigth-list-wrap-ul" id="searchUl">
			<li class="index-sort">
				<a class="bg-down" href="/?version=${version }&updateTime=${updateTime}">版本号</a>
				<a class="bg-down" href="/?version=${version }&updateTime=${updateTime}">更新时间</a>
			</li>
			<#if docList?? && (docList?size > 0) >
				<#list docList as doc>
					<li>
						<span class="rigth-li-name"><a href="/run/doc?docId=${doc.id}" target="_blank">${doc.name}</a></span> 
						<strong class="rigth-li-state-success"> 版本号：<a>${doc.version}</a>　　更新时间：${doc.historyTime}</strong>
					</li>
					<ul class="index-fold-ul-wrap">
						<#if doc.docReqList?? && (doc.docReqList?size > 0) >
							<#list doc.docReqList as reqOfDoc>
						<li><a target="_blank" href="/run/doc?docId=${doc.id}#point${reqOfDoc_index}">名称: ${reqOfDoc.name}</span>　${reqOfDoc.protocol}://${reqOfDoc.url}</a></li>
							</#list>
						</#if>
					</ul>
				</#list>
			</#if>
				
			</ul>
		</div>
	</div>
	<!-- index right end -->
</div>
</div>
<script>
function mySearch (obj) {
	$("#indexResetinput").show();
	
	if ($(obj).val() == '') {
		$('.index-fold-ul-wrap').hide();
		$("#indexResetinput").hide();
	}		
		
 	var filter = $("#searchV").val().toUpperCase()
	var a = $("#searchUl > li > span > a");
 	var span = $(".index-fold-ul-wrap > li");
 	
	a.each(function () {
		if ($(this).html().toUpperCase().indexOf(filter) > -1) {
			$(this).parent().parent().show();
		} else {
			span.each(function () {
				if ($(this).html().toUpperCase().indexOf(filter) > -1) {
					$(this).show();
					$(this).parent().show();
					$(this).parent().prev().show().siblings("li:not('.index-sort')").hide();
				} else {
					$(this).hide();
				}
			});
			$(this).parent().parent().hide();
		}
	})
	span.each(function () {
		if ($(this).html().toUpperCase().indexOf(filter) > -1) {
			$(this).show();
			$(this).parent().prev().show();
		} else {
			$(this).hide();
		}
	});
}
	
$(".rigth-list-wrap-ul>li").click(function (e) {
	e.stopPropagation();
	$(this).next(".index-fold-ul-wrap").slideToggle('fast');
})

//清空search value
function resetPrevInput(obj) {
	$(obj).prev().val('').focus();
	$('.index-fold-ul-wrap').hide();
	mySearch ();
	$("#indexResetinput").hide();
}
</script>
