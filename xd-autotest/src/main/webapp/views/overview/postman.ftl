<!DOCTYPE html>
<html lang="en">
<#include "/common/style.ftl">
<style>
</style>
</head>
<body style="scroll-snap-points-y: none">

	<!-- manage presets shadow begin -->
	<div class="manage-persets-shadow" id="manage-presets-id">
		<div class="manage-presets-height">
			<!-- top begin -->
			<div class="save-box-top">
				<p>模板管理</p>
				<a href="#" class="close pre-close">×</a>
			</div>
			<!-- top end -->
			<!--*************** add list begin ***************-->
			<div class="save-box-content" id="add-list-box">
				<!-- add button begin -->   
				<div class="save-content-name">
					<p></p>
					<a href="#" class="pre-add-button" id="preAddButton">添加</a>
				</div>
				<!-- add button end -->
				<!-- header list begin -->
				<div class="header-list-box scrollWidth scrollbar" id="headerListScroll">
					
				</div>
				<!-- header list end -->
			</div>
			<!--*************** add list end ***************-->
			
			<!----------------- key-value page add begin -->
			<div class="manage-perset-add-box" id="keyValue-box">
			<!-- key-value content begin -->
				<div class="key-value-content">
					<div class="key-value-tit">
						<p>创建模版</p>
						<input type="text" id="headerName" placeholder="模版名称">
					</div>
					<div class="key-value-box scrollWidth scrollbar" id="keyValueScroll">
						<!------------------ key-value-box begin ---------------->
						<!-- header key-value-box begin -->
						<div class="l-content show">
							<div class="l-con-con l-con-con2">
								<div class="add-relative">
									<a href="javascript:" class="AddMoreFileBox btn btn-info" onclick="templateHeaderAddInput(this)"></a>
									<form id="addHeaderForm" class="addForm form-top" method="post" action="">
										<table class="widthTable">
											<tr>
												<th style="width: 315px">参数名称</th>
												<th style="width: 315px">参数值</th>
												<th style="width: 30px"></th>
											</tr>
										</table>
									</form>
								</div>
							</div>
						</div>
						<!-- header key-value-box end -->
						
						<!-- 参数  key-value-box begin -->
						<div class="l-content">
							<div class="l-con-con l-con-con2">
								<div class="add-relative">
									<a href="javascript:" class="AddMoreFileBox btn btn-info" onclick="paramAddInput(this)"></a>
									<form id="Param" class="addForm form-top" method="post" action="">
										<table class="widthTable" id="paramTable" border="1">
											<tr>
												<th style="width: 20%">参数名称</th>
												<th style="width: 30%">参数值</th>
												<th>描述</th>
												<th style="width: 15%">数据类型</th>
												<th style="width: 30px"></th>
											</tr>
										</table>
									</form>
								</div>
							</div>
						</div>
						<!-- 参数 key-value-box begin -->
						
						<!-- 前置条件  key-value-box begin -->
						<div class="l-content">
							<div class="l-con-con l-con-con2">
								<div class="add-relative">
									<a href="javascript:" class="AddMoreFileBox btn btn-info" onclick="perAddInput(this)"></a>
									<form id="PreCondition" class="form-top" method="post" action="">
										<table class="widthTable" id="PreConditionTable" border="1">
											<tr>
												<th>目标键</th>
												<th style="width: 10%">条件类型</th>
												<th>目标值</th>
												<th style="width: 30px"></th>
											</tr>
										</table>
									</form>
								</div>
							</div>
						</div>
						<!-- 前置条件  key-value-box end -->
						
						<!-- 预置条件  key-value-box begin -->
						<div class="l-content">
							<div class="l-con-con l-con-con2">
								<div class="add-relative">
									<a href="javascript:" class="AddMoreFileBox btn btn-info" onclick="perSetAddInput(this)"></a>
									<form id="PreSet" class="form-top" method="post" action="">
										<table class="widthTable" id="PreSetTable" border="1">
											<tr>
												<th style="width: 15%">数据源类型</th>
												<th style="width: 15%">作用范围</th>
												<th style="width: 15%">存储键</th>
												<th style="width: 15%">数据类型</th>
												<th>赋值语句</th>
												<th style="width: 30px"></th>
											</tr>
										</table>
									</form>
								</div>
							</div>
						</div>
						<!-- 预置条件  key-value-box end -->
						
						<!-- 测试用例  key-value-box begin -->
						<div class="l-content">
							<div class="l-con-con l-con-con2">
								<div class="add-relative">
									<a href="javascript:" class="AddMoreFileBox btn btn-info" onclick="testCaseAddInput(this)"></a>
									<form id="TestCase" class="form-top" method="post" action="">
										<table class="widthTable" id="TestCaseTable" border="1">
											<tr>
												<th>测试名称</th>
												<th>待测试键</th>
												<th>数据类型</th>
												<th>目标值</th>
												<th style="width: 30px"></th>
											</tr>
										</table>
									</form>
								</div>
							</div>
						</div>
						<!-- 测试用例  key-value-box end -->
						
						<!-- 后置参数  key-value-box begin -->
						<div class="l-content">
							<div class="l-con-con l-con-con2">
								<div class="add-relative">
									<a href="javascript:" class="AddMoreFileBox btn btn-info" onclick="afterSetAddInput(this)"></a>
									<form id="AfterSet" class="form-top" method="post" action="">
										<table class="widthTable" id="AfterSetTable" border="1">
											<tr>
												<th>结果键</th>
												<th style="width: 20%">数据类型</th>
												<th>映射键</th>
												<th style="width: 30px"></th>
											</tr>
										</table>
									</form>
								</div>
							</div>
						</div>
						<!-- 后置参数  key-value-box end -->
						
						<!------------------ key-value-box end ---------------->
					</div>
				</div>
				<!-- key-value content end -->
				<!-- bottom button begin -->
 				<div class="save-content-bottom presets-buttom">
					<a href="javascript:void(0);" id="addHeaderOk">确定</a>
					<a href="#" class="cancel pre-cancel" id="pre-cancel">取消</a>
				</div>
				<!-- bottom button end -->
			</div>
			<!----------------- key-value page add begin -->
			
			<!----------------- key-value page edit begin -->
			<div class="manage-perset-add-box" id="eidtKeyValue-box">
			<!-- key-value content begin -->
				<div class="key-value-content">
					<div class="key-value-tit">
						<p>修改Header模版</p>
						<input type="text" id="eidtheaderName" placeholder="Header模版" readonly="readonly">
						<input type="hidden" value="" id="editGroupId">
					</div>
					<div class="key-value-box scrollWidth scrollbar" id="eidtkeyValueScroll">
						<!-- key-value-box begin -->
						<div class="l-content show">
							<div class="l-con-con l-con-con2">
								<div class="add-relative">
									<a href="javascript:" class="AddMoreFileBox btn btn-info" onclick="templateHeaderAddInput(this)"></a>
									<form id="eidtHeaderForm" class="addForm form-top" method="post" action="">
										<table class="widthTable">
											<tr>
												<th style="width: 315px">参数名称</th>
												<th style="width: 315px">参数值</th>
												<th style="width: 30px"></th>
											</tr>
											
										</table>
									</form>
								</div>
							</div>
						</div>
						<!-- key-value-box end -->
					</div>
				</div>
				<!-- key-value content end -->
				<!-- bottom button begin -->
 				<div class="save-content-bottom presets-buttom">
					<a href="javascript:void(0);" id="eidtHeaderOk">确定</a>
					<a href="#" class="cancel pre-cancel editPre-cancel" id="editPre-cancel">取消</a>
				</div>
				<!-- bottom button end -->
			</div>
			<!----------------- key-value page edit begin -->
			
		</div>
	</div>
	<!-- manage presets shadow end -->

	

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
					<a href="javascript:;" id="addReq">Save</a> <a href="#"
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
					<a href="javascript:;" id="v_generateNewVersion">Save</a> <a
						href="#" class="cancel">Cancel</a>
				</div>
				<input type="hidden" id="tempdocid">
			</div>
		</div>
	</div>
	<!-- generate new version end -->

	<!-- update doc_req begin -->
	<div class="save-shadow" id="update-docReq">
		<div class="save-box updateCase-height">
			<div class="save-box-top">
				<p>修改Doc_Req</p>
				<a href="#" class="close">×</a>
			</div>
			<div class="save-box-content">
				<form id="update_doc_req_form" action="">
					<div class="save-content-name">
						<p>命名</p>
						<input id="name" name="name" type="text">
					</div>
					<div class="save-content-collection">
						<p>选择Doc</p>
						<select id="docId" name="docId">
							<option value="">--请选择--</option>
						</select>
					</div>
					<div class="save-content-name">
						<p>描述</p>
						<textarea autoFocus  id="desc" name="desc" type="text" maxlength="500"></textarea>
						<p id="dese-box" style="color: red; height: 20px; padding: 0 0 5px 0;"></p>
					</div>
					<div class="save-content-name">
						<p>排序</p>
						<input id="sort" name="sort" type="text">
					</div>
					<input type="hidden" id="docReqId" name="docReqId">
				</form>

				<div class="save-content-bottom">
					<a href="javascript:;" id="u_doc_req">保存</a> <a href="#"
						class="cancel">取消</a>
				</div>
			</div>
		</div>
	</div>
	<!-- update doc_req version end -->

	<!-- update doc begin -->
	<div class="save-shadow" id="update-doc">
		<div class="save-box updateCase-height">
			<div class="save-box-top">
				<p>修改Doc</p>
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
					<a href="javascript:;" id="u_doc">保存</a> <a href="#" class="cancel">取消</a>
				</div>
			</div>
		</div>
	</div>
	<!-- update doc version end -->

	<!-- collection begin -->
	<div class="save-shadow collection-shadow" id="collection-shadow-box">
		<div class="save-box">
			<div class="save-box-top">
				<p>创建Document</p>
				<a href="#" class="close">×</a>
			</div>
			<div class="save-box-content">
				<div class="save-content-text">
					<p>添加Document</p>
					<p>Learn more about creating collections</p>
				</div>
				<div class="save-content-name">
					<p>Name</p>
					<input id="docName" type="text">
				</div>
				<div class="save-content-name">
					<p>Version</p>
					<input id="docVersion" type="text">
				</div>
				<div class="save-content-name">
					<p>BusinessLine</p>
					<input id="docBusiness" type="text">
				</div>
				<div class="save-content-description collection-description">
					<p>Document描述</p>
					<textarea id="description" name="description" cols="30" rows="10"
						placeholder=""></textarea>
				</div>
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
			<div class="common-top">
				<div class="index-logo">
					<!-- <a herf="#"><img alt="" src=""></a> -->
					<span><a href="/" title="自动化测试">自动化测试</a></span>
				</div>
				<div class="index-nav-wrap">
					<ul class="index-nav" id="indexNavWrap">
						<li><a href="/">首页</a></li>
						<li><a href="/doc/postman" class="index-active">服务</a></li>
						<li><a href="/case/testcase">用例</a></li>
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
							<input id="search" type="text" placeholder="Filter" onkeyup="resetBtnShow(this)">
						</div>
						<div id="l-top-tab" class="l-top-tabs">
							<!--	<a href="javascript:" class="h-active">History</a> -->
							<a href="javascript:" class="h-active doc-w100" id="clickDoc">Documents</a>
						</div>
					</div>
					<div id="l-content-wrap" class="tabs-cont-wrap">
						<div class="l-content show">
							<div class="content-top">
								<a href="#" class="add-collect"></a>
							</div>
							<div class="l-con-con l-con-con-collection scrollWidth scrollbar"
								id="collections-content">
								<div class="force-overflow"></div>
								<!-- 折叠begin -->
								<div class="collection-box" id="collectionBoxId"></div>
								<!-- 折叠end -->
								<input type="hidden" id="tempStoreId"> <input
									type="hidden" id="tempStoreDocId">
							</div>
						</div>
					</div>
				</div>

			</div>
			<!-- 文档end -->
			<#include "/overview/tab-doc.ftl"> <#include
			"/overview/template-doc.ftl">
		</div>

		<!--content end-->

		<!-- 临时存储五个table的值 -->
		<input type="hidden" id="paramsTemp"> <input type="hidden"
			id="preConditionsTemp"> <input type="hidden" id="preSetsTemp">
		<input type="hidden" id="testCasesTemp"> <input type="hidden"
			id="afterSetsTemp"> <input type="hidden" id="outParamsTemp">

	</div>
	<script src="${baseJsOP}/send/doc-send.js"></script>
	<script src="${baseJsOP}/addInput.js"></script>
	<script src="${baseJsOP}/util/string.util.js"></script>
	<!-- <script src="${baseJsOP}/plugin/autotest.docplugin.min.js"></script> -->
	<script src="${baseJsOP}/plugin/autotest.docplugin.js"></script>
	
	<!-- 模版管理 -->
	<script src="${baseJsOP}/template/template-manager.js"></script>
	
	<script>
		var nd;
		$(function() {
			$.ajax({
				type : "post",
				url : "/doc/getDocAndReq",
				dataType : "json",
				success : function(data) {
					var option = {
						docAMethod : {
							name : "generateNewVersion",
							showName : "生成新版本"
						},
						docSpanMethod : [ {
							name : "q_oper_doc",
							showName : "查看"
						}, {
							name : "u_oper_doc",
							showName : "修改"
						}, {
							name : "d_oper_doc",
							showName : "删除"
						}],
						reqSpanMethod : [ {
							name : "toDetailReq",
							showName : "查看"
						}, {
							name : "u_oper_docReq",
							showName : "修改"
						}, {
							name : "d_oper_docReq",
							showName : "删除"
						} ]
					};
					/* 折叠 */
// 					console.log(option);
					nd = new DocP('.collection-box', data, option);
				}
			});
			autosize(document.querySelectorAll('textarea'));
		})
		window.onresize = function() {
			autosize(document.querySelectorAll('textarea'));
		}
	</script>
	<script src="${baseJsOP}/doc-request.js"></script>
</body>
</html>