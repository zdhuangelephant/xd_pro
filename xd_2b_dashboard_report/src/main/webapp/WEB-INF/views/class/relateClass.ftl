<div class="modal fade" id="relate" tabindex="-1" role="dialog"
			aria-labelledby="exampleModalLabel" aria-hidden="true">
			<div class="modal-dialog" role="document">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title" id="exampleModalLabel">关联班级管理员</h4>
						<input type="hidden" value="" id="selectStudentId">
					</div>
					<div class="modal-body">
						<form id="relateForm">
							<input type="hidden" id="relate_id" name="id">
							<div class="form-group" >
								<#if listClassAdmin?? && (listClassAdmin?size>0)>
	                            	<#list listClassAdmin as classAdmin>
	                            		<#if classAdmin??>
	                                	<label>
	                                		<input name="adminId" id="${classAdmin.id}" type="radio" value="${classAdmin.id}" >
	                                		<span>${classAdmin.realName}</span>
	                                	</label>
										</#if>
									</#list>
								<#else>
									<span style="color:red;">请联系超级管理员，给班级添加“班级管理员”!</span>
								</#if>
							</div>
						</form>
							<div class="modal-footer">
				                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
				                <button type="button" onclick="relateClassAdmin();" class="btn btn-primary" >确定</button>
            				</div>
					</div>
				</div>
			</div>
		</div>