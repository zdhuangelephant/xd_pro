<div class="modal fade" id="printApply" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel">打印预览</h4>
                
                <div class="pull-right">
						<#if listApply?? && (listApply?size>0) > 
							<button onclick="$('#print_apply_table').jqprint();" class="btn btn-primary">打印</a>
						<#else>
							没有可打印的信息
						</#if>
				</div>
            </div>
    <section class="table-well">
        <div class="row">
            <div class="col-md-12">
                <!-- BEGIN SAMPLE TABLE PORTLET-->
                <div class="box">
                    <div class="box-body">
						
                        <table class="table-scrollable table table-hover" id="print_apply_table">
								<tr>
									<td class="text-center">报名人数：</td>
									<td class="text-center" id="studentCount">
									<#if studentCount?? && (studentCount>0)>
									${studentCount}
									<#else>
									${orderDO.studentCount}
									</#if>
									</td>
								</tr>
								<tr>
									<td class="text-center">报考科次：</td>
									<td class="text-center" id="applyCount">
									<#if applyCount?? && (applyCount>0)>
									${applyCount}
									<#else>
									${orderDO.applyCount}
									</#if>
									</td>
								</tr>
								<tr>
									<td class="text-center">助学${pilotUnitName}：</td>
									<td class="text-center">
									<#if adminUser.unitName??>
									${adminUser.unitName}
									<#else>
									${orderDO.pilotUnitName}
									</#if>
									</td>
								</tr>
                                 <tr>
                                 	<th class="text-center">序号</th>
									<th class="text-center">姓名</th>
									<th class="text-center">手机号</th>
									<th class="text-center">准考证号</th>
									<th class="text-center">课程代码</th>
									<th class="text-center">课程名称</th>
									</tr>
										<#list listApply as apply>
										<tr>
											<input type="hidden" value="${apply.id}" name="applyId">
											<td class="text-center">${apply_index+1}</td>
											<td class="text-center">${apply.studentName}</td>
											<td class="text-center" style="mso-number-format:'\@';" >${apply.telephone}</td>
											<td class="text-center" style="mso-number-format:'\@';" >${apply.admissionCardCode}</td>
											<td class="text-center" style="mso-number-format:'\@';" >${apply.productCode}</td>
											<td class="text-center">${apply.productName}</td>
										</tr>
										</#list>
						</table>
                    </div>
                </div>
                <!-- END SAMPLE TABLE PORTLET-->
            </div>
        </div>
    </section>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>