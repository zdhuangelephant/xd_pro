<head>
<title>服务</title>
</head>
<div class="tab-pane active" name="parentDiv" id="template" style="scroll-snap-points-y:none;display:none">			
	<!--右侧内容区域-->
	<div id="tab-content" class="tab-content-class">
			<!--top begin:select input button -->
			<div class="tab-cont-top">
				<div class="tab-cont-top-l">
					<!--select begin-->
					<div class="select-wrap">
						<div id="dropdown" class="dropdown-style">
							<p onclick="dropdownOn(this)" value="get">
								POST
							</p>
							<ul>
								<li><a href="javascript:" rel="0">GET</a></li>
								<li><a href="javascript:">POST</a></li>
							</ul>
						</div>
					</div>
					<!--select end-->
					<!--search button input begin-->
					<div class="search content-input">
						<input type="text" value="${docReq.url }" id="req_url"
							placeholder="Enter Request URL">
					</div>
					<!--search button input end-->
				</div>
				
				<!--send save button begin-->
				<div class="tab-con-top-r">
					<div class="send">
						<a href="javascript:"  onclick="docSend(this)" id="sendBtn">发送</a>
					</div>
					<div class="save" data-id="" onclick="doSave(this)"  id="save-btn">
						<a href="javascript:">保存</a>
					</div>
				</div>
				<!--send save button end-->
			</div>
			
			<!--get post tab 区域begin-->
			<div id="content-wrap">
				<div class="tab-cont-tab show">
					<div class="l-top-tabs content-tabs zdh template-tab-head" id="top-heads">
						<a href="javascript:" class="h-active" onclick = "headerOn(this)">Header</a>
						<a href="javascript:" onclick = "paramOn(this)">参数</a>
						<a onclick = "preOn(this)" href="javascript:">前置条件</a>
						<a onclick = "preSetOn(this)" href="javascript:">预置参数</a>
						<a onclick = "testCaseOn(this)" href="javascript:">测试用例</a>
						<a onclick = "afterSetOn(this)" href="javascript:">后置参数</a>
						<a onclick = "outParamOn(this)" href="javascript:">出参</a>
						<div class="template-wrap" id="headertemplate">
							<a onclick = "presetBtnOn(this)" class="presetBtn show-header" label="header">Header
								<span class="preset-box">
									<div class="pre-title" onclick="managePresetOn(this)" label="header">Header管理</div>
									<ul id="headerTemplate0" class="headerTemplate"></ul>
								</span>
							</a>
							<a onclick = "presetBtnOn(this)" class="presetBtn" label="param">参数
								<span class="preset-box">
									<div class="pre-title" onclick="managePresetOn(this)" label="param">参数管理</div>
									<ul id="paramTemplate" class="paramTemplate"></ul>
								</span>
							</a>
							<a onclick = "presetBtnOn(this)" class="presetBtn" label="preCondition">前置条件
								<span class="preset-box">
									<div class="pre-title" onclick="managePresetOn(this)" label="preCondition">前置条件管理</div>
									<ul id="preConditionTemplate" class="preConditionTemplate"></ul>
								</span>
							</a>
							<a onclick = "presetBtnOn(this)" class="presetBtn" label="preSet">预置参数
								<span class="preset-box">
									<div class="pre-title" onclick="managePresetOn(this)" label="preSet">预置参数管理</div>
									<ul id="preSetTemplate" class="preSetTemplate"></ul>
								</span>
							</a>
							<a onclick = "presetBtnOn(this)" class="presetBtn" label="testCase">测试用例
								<span class="preset-box">
									<div class="pre-title" onclick="managePresetOn(this)" label="testCase">测试用例管理</div>
									<ul id="testCaseTemplate" class="testCaseTemplate"></ul>
								</span>
							</a>
							<a onclick = "presetBtnOn(this)" class="presetBtn" label="afterSet">后置参数
								<span class="preset-box">
									<div class="pre-title" onclick="managePresetOn(this)" label="afterSet">后置参数管理</div>
									<ul id="afterSetTemplate" class="afterSetTemplate"></ul>
								</span>
							</a>
						</div>
					</div>
				</div>
			</div>
			<!--get post tab 区域end-->

			<div id="tabs-wrap" class="tabs-cont-wrap tabs-cont-wrap2">
			
				<!-- Header begin -->
				<div class="l-content show">
					<div class="l-con-con l-con-con2">
						<div class="add-relative">
							<a href="javascript:" id="tdTourchId" class="AddMoreFileBox btn btn-info" onclick="headerAddInput(this)"></a>
							<form id="Header" class="addForm form-top" method="post" action="">
								<table class="widthTable" id="headerTable" border="1">
									<tr>
										<th colspan="2" style="width: 20%">参数名称</th>
										<th>参数值</th>
										<th>描述</th>
									</tr>
								</table>
							</form>
						</div>
					</div>
				</div>
				<!-- Header end -->
			
				<!--Param begin-->
				<div class="l-content get-show">
					<div class="l-con-con l-con-con2">
					<div  class="body-tab show">
						<input onclick="xInputOn(this)" class="radioChecked" type="radio" id="formData" value="x-www-form-urlencoded">x-www-form-urlencoded
						<input onclick="rInputOn(this)" type="radio" id="rawData"  value="raw">raw
					</div>
					<div class="body-content-wrap">
						<div class="shows body-content">
							<div class="add-relative">
								<a href="javascript:" class="AddMoreFileBox btn btn-info" onclick="paramAddInput(this)"></a>
								<form id="Param" class="addForm form-top" method="post" action="">
									<table class="widthTable" id="paramTable" border="1">
										<tr>
											<th style="width: 20%">参数名称</th>
											<th style="width: 30%">参数值</th>
											<th>描述</th>
											<th style="width: 15%">数据类型</th>
										</tr>
									</table>
								</form>
							</div>
						</div>
						<div class="body-content">
							<textarea onkeydown="editTab(event,this)" name="raw" id="" cols="30" rows="10"></textarea>
						</div>
					</div>
					</div>
				</div>
				<!--Param end-->

				<!--PreCondition begin-->
				<div class="l-content">
					<div class="l-con-con l-con-con2 body-contents">
						<div class="add-relative">
							<a href="javascript:" class="AddMoreFileBox btn btn-info" onclick="perAddInput(this)"></a>
							<form id="PreCondition" class="form-top" method="post" action="">
								<table class="widthTable" id="PreConditionTable" border="1">
									<tr>
										<th>目标键</th>
										<th style="width: 10%">条件类型</th>
										<th>目标值</th>
									</tr>
								</table>
							</form>
						</div>
					</div>
				</div>
				<!--PreCondition end-->

				<!--PreSet begin-->
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
									</tr>
								</table>
							</form>
						</div>
					</div>
				</div>
				<!--PreSet end-->

				<!--TestCase being-->
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
									</tr>
								</table>
							</form>
						</div>
					</div>
				</div>
				<!--TestCase end-->

				<!--AfterSet being-->
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
									</tr>
								</table>
							</form>
						</div>
					</div>
				</div>
				<!--AfterSet end-->
				
				<!--OutParam being-->
				<div class="l-content">
					<div class="l-con-con">
						<!-- JSON Editor begin -->
						<div id="jsoneditor" class="jsoneditor-box" style="display: block;">
							<div class="btn-wrap">
								<a href="javascript:" onclick="textBtnOn(this)" class="json-btn" id="out-text-btn">text</a>
								<a href="javascript:" onclick="jsonBtnOn(this)" class="json-btn" id="out-json-btn">json</a><br>
							</div>
							<textarea onkeydown="editTab(event,this)" name="jsontextarea" class="json-textarea" id="out-j-textarea"></textarea>
						</div>
						<!-- JSON Editor end -->
					</div>
				</div>
				<!--OutParams end-->
				
				<!-- 临时存储五个table的值 -->
				<input type="hidden" id="paramsTemp">
				<input type="hidden" id="preConditionsTemp">
				<input type="hidden" id="preSetsTemp">
				<input type="hidden" id="testCasesTemp">
				<input type="hidden" id="afterSetsTemp">
				<input type="hidden" id="outParamsTemp">
			</div>
		</div>
	<!--右侧内容区域-->
	<!--editor begin-->
	<div class="editor-wrap">
		<div class="editor-top">
			<a onclick="editorRequestBody(this)" href="javascript:">RequestBody</a>
			<a onclick="resultA(this)" href="javascript:" class="h-active">Result</a>
			<a onclick="editorTestCaseA(this)" href="javascript:">TestCase</a>
		</div>

		<div class="editor-content-wrap">
		
			<div class="jsoneditor-box jsoneditor-box2">
				<table id="requestBodyTablesHeader" class="table table-hover">
				</table>
				<table id="requestBodyTablesBody" class="table table-hover">
				</table>
				
				<div id="jsoneditor0" style="display: none;" class="jsoneditor-box show">
					<div class="btn-wrap">
						<a href="javascript:" class="json-btn" id="text-btn0">text</a>
						<a href="javascript:" class="json-btn" id="json-btn0">json</a><br>
					</div>
					<div onkeydown="editTab(event,this)" name="jsontextarea" class="json-textarea" id="j-textarea0"></div>
				</div>
			
			</div>
			
			<!-- JSON Editor begin -->
			<div id="jsoneditor" class="jsoneditor-box show">
				<div class="btn-wrap">
					<a href="javascript:" class="json-btn" id="text-btn">text</a>
					<a href="javascript:" class="json-btn" id="json-btn">json</a><br>
				</div>
				<div onkeydown="editTab(event,this)" name="jsontextarea" class="json-textarea" id="j-textarea"></div>
				<!-- <div id="jsoneditor"></div> -->
			</div>
			<!-- JSON Editor end -->

			<div class="jsoneditor-box jsoneditor-box2">
				<table id="testCaseTables" class="table table-hover">
				</table>
			</div>
			
			
		</div>
	</div>
	<!--editor end-->
</div>
