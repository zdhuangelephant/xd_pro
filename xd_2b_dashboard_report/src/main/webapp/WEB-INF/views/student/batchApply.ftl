<div class="modal fade" id="batchApply" tabindex="-1" role="dialog"
			aria-labelledby="exampleModalLabel" aria-hidden="true">
			<div class="modal-dialog" role="document">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" id="batchApplyClose" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title" id="exampleModalLabel">批量报名课程</h4>
					</div>
					<div class="modal-body">
						<form id="batchApplyForm">
							<div class="form-group" >
								<label class="form-control-label">专业:</label> 
								<select id="batch_catId" type="text" class="form-control">
									<option value="">选择专业</option>
									<#list listCategory as cat>
										<option value="${cat.id}">${cat.name}</option>
									</#list>
								</select>
							</div>
							<div id="batch-form-group-course" class="form-group">
								<!-- 課程信息-->
							</div>
						</form>
							<div class="modal-footer">
								<span style="color:red;margin: 20px 100px 10px 10px">批量报课时系统会自动跳过已报名的学生</span>
				                <!--<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>-->
				                <button type="button" id="batchSaveApply" class="btn btn-primary" >确定</button>
            				</div>
					</div>
				</div>
			</div>
		</div>