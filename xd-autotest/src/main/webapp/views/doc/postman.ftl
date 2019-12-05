<#include "/common/style.ftl">
<body style="scroll-snap-points-y: none">

	<!-- send shadow begin -->
    <div class="loader-inner ball-clip-rotate" id="loading">
      <div></div>
    </div>
    <!-- send shadow end -->

	<!-- save begin -->
	<div class="save-shadow" id="save-shadow-box">
		<div class="save-box save-new-height">
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
		<div class="save-box" style="height: 450px">
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
	
	
	<!-- update doc_req begin -->
	<div class="save-shadow" id="update-docReq">
		<div class="save-box" style="height: 450px">
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
						<textarea id="desc" name="desc" type="text"></textarea>
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
	<!-- update doc_req version end -->
	
	<!-- update doc begin -->
	<div class="save-shadow" id="update-doc">
		<div class="save-box" style="height: 450px">
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
					<a href="javascript:;"  id="u_doc">保存</a> <a href="#"
						class="cancel">取消</a>
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
		<#include "/common/common-header.ftl"/>
		<!-- <div id="post-top">
			<div id="content-l-hide">
				<a href="javascript:">C</a>
			</div>
			<div id="top-tab">
				<a href="javascript:" id="builder" class="a-active">文档</a>
				<a href="javascript:" id="result">结果</a> 
			</div>
			<div id="top-r"></div>
		</div> -->
		<!--top end-->
		<!--content begin-->
		<div id="post-content">
			<!-- 文档being -->
			<div class="content show" id="builder-content">
				<div class="builder-content-l w30">
					<div id="l-top">
						<div class="search">
							<input type="text" placeholder="Filter">
						</div>
						<div id="l-top-tab" class="l-top-tabs">
						<!--	<a href="javascript:" class="h-active">History</a> -->
						 <a href="javascript:" class="h-active" id="clickDoc">Documents</a>
						</div>
					</div>
					<div id="l-content-wrap" class="tabs-cont-wrap">
				<!--		<div class="l-content show">
							<div class="content-top"></div>
							<div class="l-con-con" id="history-content">
								Nothing in your history yet.Requests that you send through
								Postman are automatically saved here.<br>
							</div> 
						</div> -->
						<div class="l-content show" >
							<div class="content-top">
								<a href="#" class="add-collect"></a>
							</div>
							<div class="l-con-con" id="collections-content">
								<!-- 折叠begin -->
								<div class="collection-box" id="collectionBox">
									<#if docList?? && (docList?size > 0)> 
										<#list docList as doc>
											<p class="green">
												${doc.name }
												<a href="javascript:" onclick="generateNewVersion('${doc.id}')">生成新版本</a>
												  <span onclick="window.open('/run/doc?docId=${doc.id}')">查看文档</span>
												  <span onclick="u_oper_doc('${doc.id }')" id="u_oper_doc">修改</span>
												  <span onclick="d_oper_doc('${doc.id }','${doc.name }')" id="d_oper_doc">删除</span>
											</p>
											<ul>
												<#if doc.docRequestList?? && (doc.docRequestList?size > 0)> 
														<#list doc.docRequestList as docReq>
															<li>
															<a onclick="toDetailReq('${docReq.id }','${docReq.docId }')" href="javascript:">${docReq.name}
																<span onclick="u_oper_docReq('${docReq.id }','${docReq.docId }')" id="u_oper">修改</span>
																<span onclick="d_oper_docReq('${docReq.id }','${docReq.name }')" id="d_oper">删除</span>
															</a>
															</li>
														</#list> 
												<#else>
												<p>
													该Doc下暂无用例数据22
												</p>
												</#if>
											</ul>
										</#list> 
									
									<#else>
										Doc暂无数据
									</#if>
									
								</div>
								<!-- 折叠end -->
								<input type="hidden" id="tempStoreId">
								<input type="hidden" id="tempStoreDocId">
							</div>
						</div>
					</div>
				</div>
				<div class="builder-content-r w70" id="scroll-style">
					<div style="margin-bottom: 10px">
						<a href="javascript:" class="easyui-linkbutton"
							onclick="addTab('New Tab')">Add Tabs +</a>
					</div>
					<!-- ui wrap begin -->
					<div id="tt" class="easyui-tabs">
						<div title="New Tab">
							<iframe id="iframeFresh" src="/doc/addtab" scrolling="auto" frameborder="0" style="overflow: hidden;width: 100%;"></iframe>
						</div>
					</div>
					<!-- ui wrap begin -->
				</div>
			</div>
			<!-- 文档end -->
				
			<!--Team Library-->
			<div class="content" id="team-content">Team Library</div>
		</div>
		<!--content end-->
		
		<!-- 临时存储五个table的值 -->
		<input type="hidden" id="paramsTemp">
		<input type="hidden" id="preConditionsTemp">
		<input type="hidden" id="preSetsTemp">
		<input type="hidden" id="testCasesTemp">
		<input type="hidden" id="afterSetsTemp">
		<input type="hidden" id="outParamsTemp">
		
	</div>
    <script src="${baseJsOP}/send/doc-send.js"></script>
	<script src="${baseJsOP}/doc-request.js"></script>
</body>
</html>