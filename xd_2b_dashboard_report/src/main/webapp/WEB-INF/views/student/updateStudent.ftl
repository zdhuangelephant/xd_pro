<div class="modal fade" id="update" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel">更新学生</h4>
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
										<form id="updateForm" class="form-horizontal" role="form">
											<input type="hidden" id="update_id" name="id" >
											<div class="form-group">
												<label class="col-lg-3 control-label">所属班级:</label>
												<div class="col-lg-8">
													<input id="update_classId" type="hidden" value="${studentDO.classId}"/>
													<select id="update_classId1" name="classId" class="filter-status form-control">
														<#list listClass as class>
															<option value="${class.id}">${class.className}</option>
														</#list>
													</select>
												</div>
											</div>
											<div class="form-group">
												<label class="col-lg-3 control-label">姓名:</label>
												<div class="col-lg-8">
													<input id="update_realName" class="form-control" name="realName" value="" type="text">
												</div>
											</div>
											<div class="form-group">
												<label class="col-lg-3 control-label">性别:</label>
												<div class="col-lg-8">
													<input id="update_gender" type="hidden" value="${studentDO.gender}"/>
													<select id="update_gender1" name="gender" class="filter-status form-control">
														<option value="1">男</option>
														<option value="2">女</option>
													</select>
												</div>
											</div>
											<div class="form-group">
												<label class="col-lg-3 control-label">手机号:</label>
												<div class="col-lg-8">
													<input id="update_telephone" name="telephone" class="form-control" value="" type="text">
												</div>
											</div>
											<!--
											<div class="form-group">
												<label class="col-lg-3 control-label">身份证号:</label>
												<div class="col-lg-8">
													<input id="identificationCardCode" name="identificationCardCode" class="form-control" value="" type="text">
												</div>
											</div>
											-->
											<div class="form-group">
												<label class="col-lg-3 control-label">准考证号:</label>
												<div class="col-lg-8">
													<input id="update_admissionCardCode" name="admissionCardCode" class="form-control" value="" type="text">
												</div>
											</div>
											
											<div class="modal-footer">
								                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
								                <button type="button" id="updateStudent" class="btn btn-primary" >确定</button>
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