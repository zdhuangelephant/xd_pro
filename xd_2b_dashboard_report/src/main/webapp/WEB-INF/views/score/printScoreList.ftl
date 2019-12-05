<div class="modal fade" id="previewPrint" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel">打印预览</h4>
                
                <div class="pull-right">
						<#if listScore?? && (listScore?size>0) > 
							<button onclick="$('#print_score_table').jqprint();" class="btn btn-primary">打印</a>
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
						
                       <table border="1" class="pring-table table-scrollable table table-hover" id="print_score_table">
                                <tr>
                                	<th class="text-center" data-sortable="false">序号</th>
                                    <th class="text-center" data-sortable="false">
									<#if adminUser.role == 3>
										班级名称
									<#else>
										${pilotUnitName}名称
									</#if>
									</th>
                                    <th class="text-center" data-sortable="false">姓名</th>
                                    <th class="text-center" data-sortable="false">准考证号</th>
                                    <th class="text-center" data-sortable="false">专业</th>
                                    <th class="text-center" data-sortable="false">课程名称</th>
                                    <th class="text-center" data-sortable="false">结课时间</th>
                                    <th class="text-center" data-sortable="false">成绩</th>
                                </tr>
								<#if listScore??>
								<#list listScore as score>
								<#if score??>
									<tr id="st${score.id}" class="tr-parent">
										<td style="width: 50px;" class="text-center">${score_index+1}</td>
										<td style="width: 75px;" class="text-center">
										<#if adminUser.role == 3>
											${score.className}
										<#else>
											${score.pilotUnitName}
										</#if>
										</td>
										<td style="width: 60px;" class="text-center">${score.studentName}</td>
										<td class="text-center" style="mso-number-format:'\@';" >${score.admissionCardCode}</td>
										<td class="text-center">${score.catName}</td>
										<td class="text-center">${score.productName}</td>
										<td style="width: 100px;" class="text-center">${score.endTime}</td>
										<td class="text-center">${score.score}</td>
									</tr>
									</#if>
									</#list>
									</#if>
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