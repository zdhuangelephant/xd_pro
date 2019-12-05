<div class="modal fade" id="update" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel">更新单位</h4>
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
										<input type="hidden" name="forumType" value="4" />
									    <input type="hidden" name="forumClassify" value="3" />
									    <input type="hidden" name="authorId" value="1" />
										
											<input type="hidden" id="update_id" name="id" >
											<div class="form-group">
												<label class="col-lg-3 control-label">单位名称:</label>
												<div class="col-lg-8">
													<input id="update_unitName" name="unitName" class="form-control" value="" type="text">
												</div>
											</div>
											
											
											<div class="form-group">
												<label class="col-lg-3 control-label">单位头像:</label>
												<div class="col-lg-8">
													<img id="update_unitPortrait" class="unitPortrait" src="" alt="单位头像">
										            <input type="hidden" datatype="s" sucmsg=" " id="callback_update_unitPortrait" name="unitPortrait" placeholder="" class="col-xs-10 col-sm-5" />
										            <a onclick="uploaD('update_unitPortrait','picture',3,'jpg,jpeg')">上传</a>
											        <br/>
												</div>
											    <div class="col-xs-9" style="color:red">*建议上传200*200像素的jpg、png、gif图片</div>
											</div>
											<div class="form-group">
												<label class="col-lg-3 control-label">单位角色:</label>
												<div class="col-lg-8">
													<select id="update_role" name="role" class="filter-status form-control">
														<#list listRole as role>
															<option value="${role.id}">${role.roleName}</option>
														</#list>
													</select>
												</div>
											</div>
											
											<div class="modal-footer">
								                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
								                <button type="button" onclick="updateUnit();" class="btn btn-primary" >确定</button>
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
