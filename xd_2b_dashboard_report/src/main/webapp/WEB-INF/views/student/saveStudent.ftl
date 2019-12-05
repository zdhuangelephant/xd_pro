<div class="modal fade" id="save" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel">新增学生</h4>
            </div>
        <section class="content">
				<div class="row">
					<div class="col-sm-12">
						<!-- BLANK PAGE-->
						<div style="margin: -20px 15px;" class="nest" id="Blank_PageClose">
							<div class="body-nest" id="Blank_Page_Content">
								<div class="row">
									<!-- edit form column -->
									<div class="col-md-12 personal-info">
										<form id="saveForm" class="form-horizontal" role="form">
											<div class="form-group">
												<label class="col-lg-3 control-label">所属班级:</label>
												<div class="col-lg-8">
													<select id="save_classId" name="classId" class="filter-status form-control">
														<#list listClass as class>
															<option value="${class.id}">${class.className}</option>
														</#list>
													</select>
												</div>
											</div>
											<div class="form-group">
												<label class="col-lg-3 control-label">姓名:</label>
												<div class="col-lg-8">
													<input id="save_realName" class="form-control" name="realName" value="" type="text">
												</div>
											</div>
											<div class="form-group">
												<label class="col-lg-3 control-label">性别:</label>
												<div class="col-lg-8">
													<select name="gender" class="filter-status form-control">
														<option value="1">男</option>
														<option value="2">女</option>
													</select>
												</div>
											</div>
											<div class="form-group">
												<label class="col-lg-3 control-label">手机号:</label>
												<div class="col-lg-8">
													<input id="save_telephone" name="telephone" class="form-control" value="" type="text">
												</div>
											</div>
											<!--
											<div class="form-group">
												<label class="col-lg-3 control-label">身份证号:</label>
												<div class="col-lg-8">
													<input id="card_no" name="identificationCardCode" class="form-control" value="" type="text">
												</div>
											</div>
											-->
											<div class="form-group">
												<label class="col-lg-3 control-label">准考证号:</label>
												<div class="col-lg-8">
													<input id="save_admissionCardCode" name="admissionCardCode" class="form-control" value="" type="text">
												</div>
											</div>
											<div class="modal-footer">
												<#if listClass?? && (listClass?size>0)>
									                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
									                <button type="button" id="saveStudent" class="btn btn-primary" >确定</button>
					            				<#else>
					            					请先新增班级!
					            				</#if>
				            				</div>
										</form>
									</div>
								</div>
							</div>
						</div>
					</div>
					<!-- END OF BLANK PAGE -->
        <!-- /.row -->
       </section>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>