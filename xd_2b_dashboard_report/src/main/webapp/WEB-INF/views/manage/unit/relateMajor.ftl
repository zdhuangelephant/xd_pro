<div class="modal fade" id="saveRelate" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel">关联专业</h4>
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
										<input type="hidden" id="unitId">
										<form id="saveRelateForm" class="form-horizontal" role="form">
											<div class="form-group">
												<div class="col-lg-8" id="relateCat">
													<#list listCategory as cat>
														<label><input name="catName" id="cat${cat.id}" type="radio" value="${cat.id}"/>${cat.name}</label><br> 
													</#list>
												</div>
											</div>
											<div class="modal-footer">
								                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
								                <button type="button" onclick="saveRelate();" class="btn btn-primary" >确定</button>
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