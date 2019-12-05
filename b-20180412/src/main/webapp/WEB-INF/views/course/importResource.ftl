<div class="modal fade" id="importResource" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" id="closeId" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel">导入</h4>
            </div>

<div id="fuelux-wizard" data-target="#step-container">
    <ul class="wizard-steps">
        <li id="step1" data-target="#step1" class="active">
            <span class="step">1</span>
            <span class="title">上传文件</span>
        </li>
        
        <li id="step2" data-target="#step2">
            <span class="step">2</span>
            <span class="title">导入结果</span>
        </li>
    </ul>
</div>

<hr>

<div class="container-fluid" style="padding-left: 0px; padding-right: 0px;">
    <div style="margin-bottom: 10px;"></div>
</div>



<div id="upload">
	<p id="upload_fileName" class="center"></p>
	<input type="hidden" value="" id="url">
	<button onclick="uploaD('file',12,'xls,xlsx')" type="button" class="btn btn-primary center" style="width:500px;margin-left:50px;">上传文件</button>
	<p class="center">重要说明：仅支持上传.xls,xlsx格式文件，上传成功后才能进行文件检测。</p>        
	<div class="modal-footer">
	</div>
</div>

      
<div id="success" style="display: none;" >
	<h3 id="import_msg" class="center">上传成功</h3> 
	<br/>
	<!--
	<p class="center" id="success_msg">重要说明：已成功导入<span id="im_success_count">0</span>条试题信息，点击“完成”返回试题列表，可查看全部试题信息。 </p>
	-->
	<input type="hidden" id="importType">
	<button id="importButton" onclick="importExcel();" type="button" class="btn btn-primary center" style="width:500px;margin-left:50px;">确认导入</button>
	<div class="modal-footer">
	    <button type="button" id="finish" class="btn btn-primary" onclick="location.reload();" style="display: none;">完成</button>  
	</div>    
</div>
        
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>