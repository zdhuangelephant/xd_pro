<div class="modal fade" id="importVideo" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" id="closeId" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel">批量导入视频</h4>
            </div>

<div id="fuelux-wizard" data-target="#step-container">
    <ul class="wizard-steps">
        <!-- <li id="step1" data-target="#step1" class="active">
            <span class="step">1</span>
            <span class="title">下载模板</span>
        </li> -->

        <li id="step2" data-target="#step2" class="active">
            <span class="step">1</span>
            <span class="title">上传文件</span>
        </li>

        <li id="step3" data-target="#step3">
            <span class="step">2</span>
            <span class="title">视频检测</span>
        </li>
        
        <li id="step4" data-target="#step4">
            <span class="step">3</span>
            <span class="title">导入结果</span>
        </li>
    </ul>
</div>

<hr>

<div class="container-fluid" style="padding-left: 0px; padding-right: 0px;">
    <div style="margin-bottom: 10px;"></div>
</div>


<div id="download">
<!-- 	<input type="file" id='uploadInput' name="uploadInput"  multiple="multiple" onchange="uploadFile(this);" style="display:none;" /> -->
<!--     <button id='uploadbtn' class="btn btn-primary center" style="width:500px;margin-left:50px;" type="button" onclick="document.getElementById('uploadInput').click();" >选择上传文件</button><br /> -->
	<div class="modal-footer">
	    <button type="button" class="btn btn-primary" onclick="uploaD('update_unitPortrait','file',500,'mp4')">上传文件</button>  
	</div>
	<br/>
	<br/>
</div>


<div id="upload" style="display: none;">
	<button id="upload_file" onclick="uploaD('upload_file','file',500,'mp4')" type="button" class="btn btn-primary center" style="width:500px;margin-left:50px;">上传视频</button>
	<p id="upload_fileName" class="center"></p>
	<p class="center">重要说明：仅支持上传格式文件，上传成功后才能进行文件检测。</p>        
	<div class="modal-footer">
	    <button type="button" class="btn btn-primary" id="backStep" onclick="back_detection()">上一步</button> 
	    <button type="button" class="btn btn-primary" onclick="detection(this);">视频检测</button> 
	</div>
</div>

<div id="detection" style="display: none;" >
	<input type="hidden" id="detection_url"/>
	<p>检测结果：</p>   
	<br/>
	<p id="detection_msg"></p>
	<a href="javascript:;" id="createErrorExcelButton" onclick="createErrorExcel();">点此下载文件检测结果</a>
	<p id="verifyButton">“确认导入”将只导入有效试题信息</p>    
	<div class="modal-footer">
		<button type="button" class="btn btn-primary" id="backStep2" onclick="back_importStudent()">上一步</button> 
	    <button type="button" id="importButton" class="btn btn-primary" onclick="importStudent(this);">确认导入</button>  
	</div>    
</div>
      
<div id="success" style="display: none;" >
	<h3 id="import_msg" class="center">导入成功</h3> 
	<br/>
	<p class="center" id="success_msg">重要说明：已成功导入<span id="im_success_count">0</span>条试题信息，点击“完成”返回试题列表，可查看全部试题信息。 </p>
	<div class="modal-footer">
	    <button type="button" class="btn btn-primary" onclick="location.reload();">完成</button>  
	</div>    
</div>
        
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>