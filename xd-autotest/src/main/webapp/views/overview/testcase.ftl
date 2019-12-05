<!DOCTYPE html>
<html lang="en">
<#include "/common/style.ftl">
 <style>

</style>
</head>
<body style="scroll-snap-points-y: none">

	<!-- send shadow begin -->
    <div class="loader-inner ball-clip-rotate" id="loading">
      <div></div>
    </div>
    <!-- send shadow end -->

	<!-- save begin -->
	<div class="save-shadow" id="save-shadow-box">
		<div class="save-box">
			<div class="save-box-top">
				<p>保存请求</p>
				<a href="#" class="close">×</a>
			</div>
			<div class="save-box-content">
				<div class="save-content-text">
					<p>Requests in Postman are saved in collection(a group of
						requests).</p>
					<p>Learn more about creating collections</p>
				</div>
				<div class="save-content-name">
					<p>请求命名</p>
					<input id="req_name" type="text">
				</div>
				<div class="save-content-description">
					<p>Request description</p>
					<textarea name="description" id="descId" cols="30" rows="10"
						placeholder=""></textarea>
				</div>
				<div class="save-content-collection">
					<p>请选择一个所属Doc</p>
					<select id="colls" name="select-collection">
						<option value="">--请选择--</option>
					</select>
				</div>
				<div class="save-content-bottom">
					<a href="javascript:;"  id="addReq">Save</a> <a href="#"
						class="cancel">Cancel</a>
				</div>
			</div>
		</div>
	</div>
	
	<!-- save end -->
	
	
	<!-- generate new version begin -->
	<div class="save-shadow" id="generate-version">
		<div class="save-box generate-height">
			<div class="save-box-top">
				<p>生成新版本</p>
				<a href="#" class="close">×</a>
			</div>
			<div class="save-box-content">
				<div class="save-content-name">
					<p>命名新版本</p>
					<input id="newVersion" name="newVersion" type="text">
				</div>
				<div class="save-content-name">
					<p>请键入新版本号</p>
					<input id="newVersionCode" name="newVersionCode" type="text">
				</div>
				<div class="save-content-bottom">
					<a href="javascript:;"  id="v_addReq">Save</a> <a href="#"
						class="cancel">Cancel</a>
				</div>
				<input type="hidden" id="tempdocid">
			</div>
		</div>
	</div>
	<!-- generate new version end -->
	
	
	<!-- update case_req begin -->
	<div class="save-shadow" id="update-docReq">
		<div class="save-box updateCase-height">
			<div class="save-box-top">
				<p>修改Case_Req</p>
				<a href="#" class="close">×</a>
			</div>
			<div class="save-box-content">
				<form id="update_doc_req_form" action="">
					<div class="save-content-name">
						<p>命名</p>
						<input id="name" name="name" type="text">
					</div>
					<div class="save-content-collection">
						<p>选择Case</p>
						<select id="docId" name="docId">
							<option value="">--请选择--</option>
						</select>
					</div>
					<div class="save-content-name">
						<p>描述</p>
						<textarea id="desc" name="caseRequestDesc" type="text"></textarea>
					</div>
					<div class="save-content-name">
						<p>排序</p>
						<input id="sort" name="sort" type="text">
					</div>
					<input type="hidden" id="docReqId" name="docReqId">
				</form>
				
				<div class="save-content-bottom">
					<a href="javascript:;"  id="u_doc_req">保存</a> <a href="#"
						class="cancel">取消</a>
				</div>
			</div>
		</div>
	</div>
	<!-- update case_req version end -->
	
	<!-- update Case begin -->
	<div class="save-shadow" id="update-doc">
		<div class="save-box updateCase-height">
			<div class="save-box-top">
				<p>修改Case</p>
				<a href="#" class="close">×</a>
			</div>
			<div class="save-box-content">
				<form id="update_doc_form" action="">
					<div class="save-content-name">
						<p>命名</p>
						<input id="_docName" name="docName" type="text">
					</div>
					<div class="save-content-name">
						<p>描述</p>
						<input id="_desc" name="desc" type="text">
					</div>
					<div class="save-content-name">
						<p>版本</p>
						<input id="version" name="version" type="text">
					</div>
					<div class="save-content-name">
						<p>业务线</p>
						<input id="businessLine" name="businessLine" type="text">
					</div>
					<input type="hidden" id="_docId" name="docId">
				</form>
				
				<div class="save-content-bottom">
					<a href="javascript:;"  id="u_doc">保存</a> <a href="#"
						class="cancel">取消</a>
				</div>
			</div>
		</div>
	</div>
	<!-- update Case version end -->
	
	
	<!-- collection begin -->
	<div class="save-shadow collection-shadow" id="collection-shadow-box">
		<div class="save-box">
			<div class="save-box-top">
				<p>创建用例目录</p>
				<a href="#" class="close">×</a>
			</div>
			<div class="save-box-content">
				<div class="save-content-text">
					<p>添加用例目录</p>
					<p>Learn more about creating Cases</p>
				</div>
				<div class="save-content-name">
					<p>Name</p>
					<input id="caseName" type="text">
				</div>
				<div class="save-content-name">
					<p>Version</p>
					<input id="caseVersion" type="text">
				</div>
				<div class="save-content-name">
					<p>BusinessLine</p>
					<input id="caseBusiness" type="text">
				</div>
				<div class="save-content-description collection-description">
					<p>Case描述</p>
					<textarea id="description" name="description" cols="30" rows="10"
						placeholder=""></textarea>
				</div>
				<div class="save-content-bottom">
					<a href="javascript:;" id="addCase">保存</a> <a href="#"
						class="cancel">取消</a>
				</div>
			</div>
		</div>
	</div>
	<!-- collection end -->
	
	
	<!-- collection begin -->
	<div class="save-shadow collection-shadow" id="case-shadow-box">
		<div class="save-box collection-height">
			<div class="save-box-top">
				<p>创建Case-Req</p>
				<a href="#" class="close">×</a>
			</div>
			<div class="save-box-content">
				<div class="save-content-text">
					<p>添加Case-Req</p>
					<p>Learn more about creating collections</p>
				</div>
				<div class="case-name">
					<p>case名字：</p>
					<input type="text" id="caseName" placeholder="case name">
				</div>
				<div class="save-content-name">
					<p>选择Doc</p>
					<select name='city' id='first'>
						<option value='-1'>==请选择类型==</option>
					</select> 
				</div>
				<div class="save-content-name">
					<p>选择doc-req</p>
					<select name="msgTypeId" id="second">
						<option value='-1'>==请选择类型==</option>
					</select>
				</div>
				<input type="hidden" id="caseId">
				<input type="hidden" id="caseVersion">
				<div class="save-content-bottom">
					<a href="javascript:;" id="addDoc">保存</a> <a href="#"
						class="cancel">取消</a>
				</div>
			</div>
		</div>
	</div>
	<!-- collection end -->
	<div class="body-wrap">
		<!--top begin-->
		<!-- <div id="post-top">
			<div id="content-l-hide">
				<a href="javascript:">C</a>
			</div>
			<div id="top-tab">
				<a href="javascript:" id="builder" class="a-active">用例</a>
				<a href="javascript:" id="result">结果</a> 
			</div>
			<div id="top-r"></div>
		</div> -->
		<div class="common-top">
				<div class="index-logo">
					<!-- <a herf="#"><img alt="" src=""></a> -->
					<span><a href="/" title="自动化测试">自动化测试</a></span>
				</div>
				<div class="index-nav-wrap">
					<ul class="index-nav" id="indexNavWrap">
						<li><a href="/">首页</a></li>
						<li><a href="/doc/postman">服务</a></li>
						<li><a href="/case/testcase" class="index-active">用例</a></li>
						<li><a href="/run/runner">调度</a></li>
						<li><a href="/dataSource/list">配置</a></li>
					</ul>
				</div>
				<div class="index-login-out">
					<a href="/admin/loginOut" target="_parent" >退出 </a>
				</div>
			</div>
		
		<!--top end-->
		<!--content begin-->
		<div id="post-content">
			<!-- 文档being -->
			<div class="content show" id="builder-content">
				<div class="builder-content-l w30">
					<div id="l-top">
						<div class="search">
							<input id="search" type="text" placeholder="Filter">
						</div>
						<div id="l-top-tab" class="l-top-tabs share-class">
						   <a href="javascript:" id="clickDoc" class="h-active">CaseDoc</a>
						   <a href="javascript:" id="checkhistory">History</a>
						   <a href="javascript:" id="caseShare" onclick="getShare()">CaseShare</a>
						</div>
					</div>
					<div id="l-content-wrap" class="tabs-cont-wrap">
						<div class="l-content show" >
							<div class="content-top">
							    <a href="#" id="exportCase" class="add-import" onclick="case_oper_export(this)" ></a>
								<a href="#" id="firstDoor" class="add-collect"></a>
							</div>
							<div class="l-con-con l-con-con-collection" id="collections-content">
								<!-- 折叠begin -->
								<div class="collection-box" id="casedoc-box">
								</div>
								<!-- 折叠end -->
								<input type="hidden" id="tempStoreId">
								<input type="hidden" id="tempStoreDocId">
							</div>
						</div>
						<div class="l-content ">
							<div class="content-top"></div>
							<div class="l-con-con l-con-con-collection scrollbar scrollWidth" id="history-content">
								<div class="force-overflow"></div>
								<ul class="history-ul-style">
									<li id="unique" style="text-align: center;"><a href="#" class="loadmore-style" onclick="loadMore()" id="loadMore" pageno="">加载更多</a></li>
								</ul>
							</div> 
						</div>
						<div class="l-content" >
							<div class="l-con-con l-con-con-collection" id="share-collections-content">
								<!-- 折叠begin -->
								<div class="collection-box" id="caseshare-box">
								</div>
							</div>
						</div>
					</div>
				</div>
	
			</div>
			<!-- 文档end -->
				<#include "/overview/tab-case.ftl">
				<#include "/overview/template-case.ftl">
		</div>
		<!--content end-->
		
		<!-- import start -->
		<div class="save-shadow" id="import-shadow-box">
		<div class="save-box important-height">
		<form id="uploadFileForm" >
			<div class="save-box-top">
				<p>导入CaseReq</p>
				<a href="#" class="close">×</a>
			</div>
			<div class="save-box-content">
				<div class="save-content-name">
					<p>请选择文件</p>
					<input id="targetFile" type="file" name="targetFile">
				</div>
				
				<div class="save-content-bottom">
					<a href="javascript:;" id="uploadFile" onclick="submitFile()">确定</a> <a href="#"
						class="cancel">取消</a>
				</div>
			</div>
		 </form>
		</div>
	</div>
		<!-- import end -->
	</div>
		<script src="${baseJsOP}/send/case-send.js"></script>
		<script src="${baseJsOP}/addInput.js"></script>
		<script src="${baseJsOP}/util/string.util.js"></script>
		<script src="${baseJsOP}/plugin/autotest.caseplugin.js"></script>
		<script src="${baseJsOP}/plugin/autotest.historyplugin.js"></script>
		<script>
		var nd, nh;
		$(function(){
			$.ajax({
				   type: "post",
				   url: "/case/getCaseAndReq",
				   dataType: "json",
				   success: function(data){
					  var option = {
						docAMethod: {
							name: "generateNewVersion",
							showName: "生成新版本"
						},docSpanMethod: [{
							name: "addCaseReq",
							showName: "添加"
						},{
							name: "u_oper_doc",
							showName: "修改"
						},{
							name: "d_oper_share",
							showName: "分享"
						},{
							name: "case_oper_import",
							showName: "导出"
						},{
							name: "d_oper_doc",
							showName: "删除"
						}],reqSpanMethod: [{
							name: "toDetailReq",
							showName: "查看"
						},{
							name: "u_oper_docReq",
							showName: "修改"
						},{
							name: "d_oper_docReq",
							showName: "删除"
						}]
					  };
					  /* 折叠 */
// 					  console.log(option);
					  nd = new DocP('#casedoc-box',data,option);
// 					  $(".collection-box").Fold(); 
				   }
			});
			
			var pageNo = $("#loadMore").attr("pageno");
			if(pageNo == '' || typeof(pageNo) == 'undefined'){
				pageNo = 1;
			}
			$.ajax({   
				   type: "post",
				   url: "/case/acquireHistory",
				   data: {"pageNo":pageNo},
				   dataType: "json",
				   success: function(data){
					   $("#loadMore").attr("pageno", Number(pageNo) + 1)
						/* nh = new HistoryP('#history-content',"","");
						nh.insertHistoryReqList(data); */
						 if(data != null && data.length > 0){
							 $.each(data, function (index, obj){
								 if(obj != null && !jQuery.isEmptyObject(obj)){
									 $('#history-content ul li:last').before(
												"<li> "+obj.method+"&nbsp;<a href='javascript:;' onclick=\"toDisplay('"+obj.id+"',this)\">"+obj.url+"</a> </li>")
								 }
							 })
						 }
				   }
			});
		})
		</script>
		
		<script>
		function getCaseList(){
		
		}
		
		 function clearCaseShareCss() {
			$("#caseshare-box").find(".r-more-option").hide();
			 $("#caseshare-box").find(".blue").mouseover(function () {
				$(this).find(".more-option").hide();
			});
			 $("#caseshare-box .r-option-wrap > a").css({"text-align":"center", "line-height":"60px", "border-bottom":"none", "width":"60px", "height":"60px", "background":"none"});
			 $("#caseshare-box .r-option-wrap > a").html("拉取");
			 $("#caseshare-box .r-option-wrap").css("width", "60px");
		} 
		
		function getShare(){
		    var ne;
			$.ajax({
				   type: "post",
				   url: "/case/getShareCaseAndReq",
				   dataType: "json",
				   success: function(data){
					  var option = {
						docAMethod: {
							name: "pull",
							showName: "拉取"
						}
					  };
					  /* 折叠 */
 					  console.log(option);
					  ne = new DocP('#caseshare-box',data,option);
 					  $(".share-collection-box").Fold();
 					 clearCaseShareCss();
				   }
			});	
		}
		</script>
		<script src="${baseJsOP}/case-request.js"></script>
</body>
</html>