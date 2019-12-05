<div class="modal fade" id="importStudent" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" id="importStudentClose" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel">批量导入学生</h4>
            </div>

<div id="fuelux-wizard" data-target="#step-container">
    <ul class="wizard-steps">
        <li id="step1" data-target="#step1" class="active">
            <span class="step">1</span>
            <span class="title">下载模板</span>
        </li>

        <li id="step2" data-target="#step2">
            <span class="step">2</span>
            <span class="title">上传文件</span>
        </li>

        <li id="step3" data-target="#step3">
            <span class="step">3</span>
            <span class="title">文件检测</span>
        </li>
        
        <li id="step4" data-target="#step4">
            <span class="step">4</span>
            <span class="title">导入结果</span>
        </li>
    </ul>
</div>

<hr>

<div class="container-fluid" style="padding-left: 0px; padding-right: 0px;">
    <div style="margin-bottom: 10px;"></div>
</div>


<div id="download">
	<button onclick="method1('import_student_table')" type="button" class="btn btn-primary center" style="width:500px;margin-left:50px;">下载模板</button>
	<br/>
	<p class="center">重要说明：先下载模板文件，按文件内“示例”格式添加学生信息后保存并上传文件。</p>        
	<div class="modal-footer">
	    <button type="button" class="btn btn-primary" onclick="uploaD('update_unitPortrait','file',12,'xls,xlsx')">上传文件</button>  
	</div>
</div>

<div id="upload" style="display: none;">
	<button id="upload_file" onclick="uploaD('upload_file','file',12,'xls,xlsx')" type="button" class="btn btn-primary center" style="width:500px;margin-left:50px;">上传文件</button>
	<p id="upload_fileName" class="center"></p>
	<p class="center">重要说明：仅支持上传.xls格式文件，上传成功后才能进行文件检测。</p>        
	<div class="modal-footer">
	    <button type="button" class="btn btn-primary" onclick="back_detection()">上一步</button> 
	    <button type="button" class="btn btn-primary" onclick="detection();">文件检测</button> 
	</div>
</div>

<div id="detection" style="display: none;" >
	<input type="hidden" id="detection_url"/>
	<p>文件检测结果：</p>   
	<br/>
	<p id="detection_msg"></p>
	<a href="javascript:;" id="createErrorExcelButton" onclick="createErrorExcel();">点此下载文件检测结果</a>
	<p id="verifyButton">“确认导入”将只导入有效学生信息</p>    
	<div class="modal-footer">
		<button type="button" class="btn btn-primary" onclick="back_importStudent()">上一步</button> 
	    <button type="button" id="importButton" class="btn btn-primary" onclick="importStudent();">确认导入</button>  
	</div>    
</div>
      
<div id="success" style="display: none;" >
	<h3 id="import_msg" class="center">导入成功</h3> 
	<br/>
	<p class="center" id="success_msg">重要说明：已成功导入<span id="im_success_count">0</span>条学生信息，点击“完成”返回学生列表，可查看全部学生信息。 </p>
	<div class="modal-footer">
	    <button type="button" class="btn btn-primary" onclick="location.reload();">完成</button>  
	</div>    
</div>
        
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>