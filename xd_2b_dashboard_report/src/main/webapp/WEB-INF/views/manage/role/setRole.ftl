<div class="modal fade" id="set" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel">权限设置</h4>
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
										<form id="setForm" class="form-horizontal" role="form">
											<input type="hidden" id="roleId" value="" name="roleId">
									        <input type="hidden" id="needDeleteId" value="" name="needDeleteId">
									        <input type="hidden" id="needAddId" value="" name="needAddId">
									        
											<table id="dnd-example" class="table table-striped table-bordered table-hover treetable">
									            <thead>
									            <tr>
									                <th>
									                    <a style="cursor: pointer;"
									                       onClick="javascript:$('input[name=\'menuid\']').prop('checked', true);">全选</a>/
									                    <a style="cursor: pointer;"
									                       onClick="javascript:$('input[name=\'menuid\']').prop('checked', false);">取消</a>
									                </th>
									            </tr>
									            </thead>
									            <tbody id="tbody">
									            </tbody>
									        </table>
									        
									        <div class="modal-footer">
								                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
								                <button type="button" onclick="setPrivilege();" class="btn btn-primary" >确定</button>
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