<#include "/common/layout.ftl" />
<@htmlHead title="登陆页">
<script type="text/javascript" src="${JS_PATH}validform/validform.js"></script>
</@htmlHead>
<@htmlBody bodyclass="page-content">

<div class="page-header">
    <h1>
        常用工具
        <small>
            <i class="ace-icon fa fa-angle-double-right"></i>
           校验页面
        </small>
    </h1>
</div>


<div class="form-horizontal" >
    
     <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-2"> 校验内容</label>
        <div class="col-sm-9">
            <textarea type="text" datatype="s" sucmsg="" id="decrypeData" name="decrypeData" placeholder="请填写内容" class="col-xs-10 col-sm-6"  style="resize:none; height:150px">${decrypeData}</textarea>
        </div>
    </div>

    <div class="form-group" id="result" style="display:none;text-aligen:center" >
        <label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 验证结果 </label>
        <div class="col-sm-3 control-label" style="color: red;"  id = "char_byte"></div>
    </div>

    

    <div class="clearfix form-actions">
        <div class="col-md-offset-3 col-md-9">
            <button id="sub" type="submit" class="btn btn-info">
                	提交
            </button>

            &nbsp; &nbsp; &nbsp;
            <button class="btn" id="resetBtn" type="reset">
                	重置
            </button>
        </div>
    </div>

</div>


    <script>
    
    $(function(){
    
    	$("#resetBtn").click(function(){
    		$("#result").css('display','none');
    		$("#decrypeData").val("");
    	});
    
    	$("#sub").click(function(){
    		var v = $("#decrypeData").val();
    		if(v == ''){
    			alert("校验内容不允许为空");
    			return ;
    		}
    		
    		$.ajax({
	             type: "POST",
	             url: "/util/getInputDataDetail",
	             data: {decrypeData:v},
	             dataType: "json",
	             success: function(data){
	                $('#decrypeData').empty();
	                $('#char_byte').html('')
	             	$("#result").css('display','block');
	             	$("#char_byte").append("<span>字符: " + data.charsCounts + "&nbsp;个,&nbsp; &nbsp; &nbsp;   字节: " + data.bytesCounts + "&nbsp;Byte,    &nbsp; &nbsp; &nbsp;字符编码utf-8</span>" ) 
	             	$("#decrypeData").val(data.content);
	             }
    		});
    });
 });      
    </script>
</@htmlBody>

