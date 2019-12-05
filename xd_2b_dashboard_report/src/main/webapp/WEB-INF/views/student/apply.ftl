<div class="modal fade" id="apply" tabindex="-1" role="dialog"
			aria-labelledby="exampleModalLabel" aria-hidden="true">
			<div class="modal-dialog" role="document">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title" id="exampleModalLabel">报名课程</h4>
						<input type="hidden" value="" id="selectStudentId">
					</div>
					<div class="modal-body">
						<form id="applyForm">
						<!--
							<div class="form-group">
								<label for="exam-datetime" class="form-control-label">考期:</label>
								<select name="majorId" type="text" class="form-control" id="exam-datetime">
									<option value="">请选择</option>
									<option value="1">2016年10月</option>
								</select>
							</div>
						-->
							<div class="form-group" >
								<label class="form-control-label">专业:</label> 
								<select id="catId" type="text" class="form-control">
									<option value="">选择专业</option>
									<#list listCategory as cat>
										<option value="${cat.id}">${cat.name}</option>
									</#list>
								</select>
							</div>
							<div id="form-group-course" class="form-group">
								<!-- 課程信息-->
							</div>
						</form>
							<div class="modal-footer">
				                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
				                <button type="button" id="saveApply" class="btn btn-primary" >确定</button>
            				</div>
					</div>
				</div>
			</div>
		</div>