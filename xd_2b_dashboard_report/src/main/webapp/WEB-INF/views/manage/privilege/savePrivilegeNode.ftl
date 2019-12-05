<div class="modal fade" id="saveNode" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel">新增子权限权限</h4>
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
										<form id="saveNodeForm" class="form-horizontal" role="form">
											<div class="form-group">
												<label class="col-lg-3 control-label">上级权限:</label>
												<div class="col-lg-8">
													<select id="save_node_parent_id" name="parentId" class="filter-status form-control">
														<option value="0">作为一级权限</option>
													</select>
												</div>
											</div>
											<div class="form-group">
												<label class="col-lg-3 control-label">权限名称:</label>
												<div class="col-lg-8">
													<input name="name" class="form-control" value="" type="text">
												</div>
											</div>
											<div class="form-group">
												<label class="col-lg-3 control-label">url:</label>
												<div class="col-lg-8">
													<input  name="url" class="form-control" value="" type="text">
												</div>
											</div>
											<div class="form-group">
												<label class="col-lg-3 control-label">icon:</label>
												<div class="col-lg-8">
													<input name="icon" class="form-control" value="" type="text">
												</div>
											</div>
											<div class="form-group">
									            <label class="col-sm-3 control-label no-padding-right" for="form-field-2"> 是否显示 </label>
									            <div class="col-sm-9">
									                <div class="control-group">
									                    <div class="radio">
									                        <label>
									                            <input name="display" type="radio" value="1" checked class="ace">
									                            <span class="lbl"> 是</span>
									                        </label>
									                    </div>
									                    <div class="radio">
									                        <label>
									                            <input name="display" type="radio" value="0" class="ace">
									                            <span class="lbl"> 否</span>
									                        </label>
									                    </div>
									                </div>
									
									
									            </div>
									        </div>
											<div class="modal-footer">
								                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
								                <button type="button" id="saveNodeFormButton" onclick="savePrivilege('saveNodeForm');" class="btn btn-primary" >确定</button>
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