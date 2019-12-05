<div class="modal fade" id="update" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel">修改学生信息</h4>
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
										
											<input type="hidden" id="telephone" name="telephone">
                      <input type="hidden" id="admissionCardCode" name="admissionCardCode" >
                      
											<div class="form-group">
                        <label class="col-lg-3 control-label">姓名:</label>
                        <div class="col-lg-8" id="realName">                          
                        </div>
                      </div>
                      <div class="form-group">
                        <label class="col-lg-3 control-label">手机号:</label>
                        <div class="col-lg-8" id="telephone_name">                          
                        </div>
                      </div>
                      <div class="form-group">
                        <label class="col-lg-3 control-label">准考证号:</label>
                        <div class="col-lg-8" id="admissionCardCode_name">                          
                        </div>
                      </div>
                      <div class="form-group">
                        <label class="col-lg-3 control-label">${pilotUnitName}:</label>
                        <div class="col-lg-8" id="pilotUnitName">                          
                        </div>
                      </div>
											<div class="form-group">
												<label class="col-lg-3 control-label">新手机号:</label>
												<div class="col-lg-8">
													<input id="regTelephone" name="regTelephone" class="form-control" value="" type="text">
												</div>
											</div>	
                      <div class="form-group">
                        <label class="col-lg-3 control-label"></label>
                        <div class="col-lg-8" id="pilotUnitName">     
                            <font color="red">请认真核对学生信息，手机号修改后无法恢复！</font>                     
                        </div>
                      </div>																					
											<div class="modal-footer">
					                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
					                <button type="button" onclick="updateStudentInfo();" class="btn btn-primary">确定</button>
	            				</div>
										</form>
									</div>
								</div>
							</div>
						</div>
					</div>
					<!-- END OF BLANK PAGE -->
        </div>
        <!-- /.row -->
       </section>
       </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>
