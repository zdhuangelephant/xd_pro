<div class="modal fade" id="previewLoginInfoPrint" tabindex="-1"
	role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">&times;</button>
				<h4 class="modal-title" id="myModalLabel">打印预览</h4>

				<div class="pull-right">
					<#if studentDO??>
					<button onclick="$('#print_score_table').jqprint();"
						class="btn btn-primary">
						打印</a> <#else> 没有可打印的信息 </#if>
				</div>
			</div>
			<section class="table-well" id="print_score_table">
				<div class="row">
					<#if alarmRecord?? >
					<div class="col-md-12">
						<div class="box">
							<div class="box-body">
								<div style="margin-bottom: 0px; text-align: center;">
									<div class="box-header">
										<h3 class="box-title">预警详情</h3>
									</div>
								</div>
								<hr>
								<div class="col-md-6 col-sm-6 col-xs-12">
									<ul class="nav nav-stacked">
										<li><a href="javascript:volid(0);">预警级别
												：${alarmRecord.alarmLevel.desc}</a></li>
										<li><a href="javascript:volid(0);">预警时间
												：${alarmRecord.alarmTime}</a></li>
									</ul>
								</div>
								<div class="col-md-6 col-sm-6 col-xs-12">
									<ul class="nav nav-stacked">
										<li><a href="javascript:volid(0);">预警类型
												：${alarmRecord.alarmType.desc}</a></li>
										<li><a href="javascript:volid(0);">预警处理
												：${alarmRecord.pretreatment.desc}</a></li>
									</ul>
								</div>
							</div>
						</div>
					</div>
					<#else></#if> <#if adminUser.role?? >
					<div class="col-md-12">
						<!-- BEGIN SAMPLE TABLE PORTLET-->
						<div class="box">
							<div class="box-body">
								<div style="margin-bottom: 0px; text-align: center;">
									<div class="box-header">
										<h3 class="box-title">预警账号列表</h3>
									</div>
								</div>
								<hr>
								<table class="table-scrollable table table-hover">
									<thead>
										<tr>
											<#if adminUser.role==3>
											<th class="text-center" data-sortable="false">班级名称</th>
											<#else>
											<th class="text-center" data-sortable="false">${pilotUnitName}</th>
											</#if>
											<th class="text-center" data-sortable="false">姓名</th>
											<th class="text-center" data-sortable="false">性别</th>
											<th class="text-center" data-sortable="false">准考证号</th>
											<th class="text-center" data-sortable="false">手机号</th>
										</tr>
									</thead>
									<tbody>
										<#if studentDO??>
										<tr>
											<#if adminUser.role==3>
											<td class="text-center">${studentDO.className}</td> <#else>
											<td class="text-center">${studentDO.pilotUnitName}</td>
											</#if>
											<td class="text-center">${studentDO.realName}</td>
											<td class="text-center"><#if studentDO.gender == 1> 男
												<#elseif studentDO.gender == 2> 女 <#else> 不明 </#if></td>
											<td class="text-center">${studentDO.admissionCardCode}</td>
											<td class="text-center">${studentDO.telephone}</td>
										</tr>
										</#if>
									</tbody>
								</table>
							</div>
						</div>
						<!-- END SAMPLE TABLE PORTLET-->
					</div>
					<#else></#if> <#if studentDO?? && listRawDataFaceRecognition?? &&
					(listRawDataFaceRecognition?size>0) >
					<div class="col-md-12">
						<!-- BEGIN SAMPLE TABLE PORTLET-->
						<div class="box">
							<div class="box-body">

								<div style="margin-bottom: 0px; text-align: center;">
									<div class="box-header">
										<h3 class="box-title">登录日志详情</h3>
									</div>
								</div>
								<hr>

								<table class="table-scrollable table table-hover" id="table_2"
									data-toggle="table" 
									data-advanced-search="true" data-id-table="advancedTable"
									data-row-style="rowStyle">
									<thead>
										<tr>
											<th class="text-center" data-sortable="true">序号</th>
											<th class="text-center" data-sortable="false">准考证号</th>
											<th class="text-center" data-sortable="false">姓名</th>
											<th class="text-center" data-sortable="false">登录时间</th>
											<th class="text-center" data-sortable="false">登录设备</th>
											<th class="text-center" data-sortable="false">设备标识</th>
											<th class="text-center" data-sortable="false">登录地区</th>
										</tr>
									</thead>
									<tbody>
										<#if listLoginInfo??> <#list listLoginInfo as loginInfo> <#if
										loginInfo??>
										<tr>
											<td class="text-center">${loginInfo_index+1}</td>
											<td class="text-center">${loginInfo.admissionCardCode}</td>
											<td class="text-center">${loginInfo.studentName}</td>
											<td class="text-center">${loginInfo.loginTime}</td>
											<td class="text-center">${loginInfo.deviceId}</td>
											<td class="text-center">${loginInfo.systemType}</td>
											<td class="text-center">${loginInfo.area}</td>
										</tr>
										</#if> </#list> </#if>
									</tbody>
								</table>
							</div>
						</div>
						<!-- END SAMPLE TABLE PORTLET-->
					</div>
					<#else></#if>

				</div>
			</section>
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal -->
</div>