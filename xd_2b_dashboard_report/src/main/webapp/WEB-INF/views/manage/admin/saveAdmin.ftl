<div class="modal fade" id="save" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel">新增账号</h4>
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
												<label class="col-lg-3 control-label">用户名（登录名）:</label>
												<div class="col-lg-8">
													<input id="save_userName" name="userName" class="form-control" value="" type="text">
												</div>
											</div>
											<div class="form-group">
												<label class="col-lg-3 control-label">密码:</label>
												<div class="col-lg-8">
													<input id="save_password" name="password" class="form-control" value="" type="text">
												</div>
											</div>
											<div class="form-group">
												<label class="col-lg-3 control-label">所属单位:</label>
												<div class="col-lg-8">
													<select id="save_unitId" name="unitId" class="filter-status form-control">
														<#list listUnit as unit>
															<option value="${unit.id}" role="${unit.role}">${unit.unitName}</option>
														</#list>
													</select>
												</div>
											</div>
											<div class="form-group" id="save_childRole">
			                                    <label class="control-label col-md-3">是否为班级管理员:</label>
				                                    <div class="col-md-9">
														<label><input name="childRole" type="radio" checked value="0"/>否 </label> 
				                                    	<label><input name="childRole" type="radio" value="1"/>是 </label> 
				                                    </div>
			                                </div>
											<div class="form-group">
												<label class="col-lg-3 control-label">姓名:</label>
												<div class="col-lg-8">
													<input id="save_realName" name="realName" class="form-control" value="" type="text">
												</div>
											</div>
											<div class="form-group">
												<label class="col-lg-3 control-label">邮箱:</label>
												<div class="col-lg-8">
													<input id="save_email" name="email" class="form-control" value="" type="text">
												</div>
											</div>
											<div class="form-group">
												<label class="col-lg-3 control-label">手机号码:</label>
												<div class="col-lg-8">
													<input id="save_telephone" name="telephone" class="form-control" value="" type="text">
												</div>
											</div>
											<div class="modal-footer">
								                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
								                <button type="button" id="saveAdmin" class="btn btn-primary" >确定</button>
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