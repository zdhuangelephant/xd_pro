<div class="modal fade" id="previewLearnRecord" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel">打印预览</h4>
                
                <div class="pull-right">
						<#if listLearnRecord?? && (listLearnRecord?size>0) > 
							<button onclick="$('#print_learn_record_table').jqprint();" class="btn btn-primary">打印</a>
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
						
                       <table class="table-scrollable table table-hover" id="print_learn_record_table">
                                <tr>
                                	<th class="text-center" data-sortable="true">序号</th>
                                    <th class="text-center" data-sortable="false">学习行为</th>
                                    <th class="text-center" data-sortable="false">学习内容</th>
                                    <th class="text-center" data-sortable="false">学习日期</th>
                                    <th class="text-center" data-sortable="false">学习时长（分钟）</th>
                                </tr>
								<#if listLearnRecord??>
								<#list listLearnRecord as learnRecord>
								<#if learnRecord??>
									<tr id="st${learnRecord.id}" class="tr-parent">
										<td class="text-center">${learnRecord_index+1}</td>
										<td class="text-center">
											<#if learnRecord.learnType == 11>pk做题
											<#elseif learnRecord.learnType == 12>pk解析
											<#elseif learnRecord.learnType == 21>闯关做题
											<#elseif learnRecord.learnType == 22>闯关解析
											<#elseif learnRecord.learnType == 31>修炼
											<#elseif learnRecord.learnType == 41>错题
											<#elseif learnRecord.learnType == 51>每日一练
											<#elseif learnRecord.learnType == 52>每日一练解析
											<#elseif learnRecord.learnType == 51>每日一练
											<#elseif learnRecord.learnType == 52>每日一练解析
											<#elseif learnRecord.learnType == 61>期末测试
											<#elseif learnRecord.learnType == 62>期末测试解析
											<#elseif learnRecord.learnType == 71>查漏补缺
											<#elseif learnRecord.learnType == 72>查漏补缺解析
											</#if>
										</td>
										<td class="text-center">${learnRecord.learnContent}</td>
										<td class="text-center">${learnRecord.recordTime}</td>
										<td class="text-center">${learnRecord.learnTime}</td>
									</tr>
									
									</#if>
									</#list>
									</#if>
                    </div>
                </div>
                <!-- END SAMPLE TABLE PORTLET-->
            </div>
        </div>
    </section>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>