<#include "/common/layout.ftl" />
<@htmlHead title="首页">
<script src="${JS_PATH}jquery.cxselect-1.3.4/jquery.cxselect.js"></script>
  <style type="text/css"> 
      .test-textarea {
      width: 100%;
      min-height: 26px;
      line-height: 20px;
      /* max-height: 150px;*/
      margin-left: auto;
      margin-right: auto;
      padding: 3px;
      outline: 0;
      border: 1px solid #ccc;
      font-size: 12px;
      word-wrap: break-word;
      overflow-x: hidden;
      overflow-y: auto;
      -webkit-user-modify: read-write-plaintext-only;
      border-radius: 4px;
      }
   </style> 


</@htmlHead>
<@htmlBody bodyclass="page-content">

<form id="addForm" method="post" class="form-horizontal" role="form" action="/pushMessage/doAddPush">

<div class="form-group">
    
   <!-- <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-2"> 推送通道 </label>
        <div class="col-sm-9">
        	<select name="moduleId" class="col-xs-10 col-sm-5">
	           	<option value="1">Jpush</option>
        	</select>
        </div>
    </div>-->
    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-3">消息类型 </label>
        <div class="col-sm-9">
        	<select name="messageType" >
	           	<option value="0">通知+消息</option>
	           	<option value="2">通知</option>
	           	<option value="3">消息</option>
	       	</select>
        </div>
    </div>
    
    <div class="form-group">
    	<label class="col-sm-3 control-label no-padding-right" for="form-field-2"> 目标设备类型 </label>
        <div class="col-sm-9">
        	<select name="targetType">
	           	<option value="0">全部设备</option>
	           	<option value="1">android设备</option>
	           	<option value="2">IOS设备</option>
        	</select>
        </div>
    </div>
   <!-- <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-2"> 目标设备类型 </label>
        <div class="col-sm-9">
        	<select name="targetType">
	           	<option value="0">全部设备</option>
	           	<option value="1">android设备</option>
	           	<option value="2">IOS设备</option>
        	</select>
        </div>
    </div>-->
    
   <!-- <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-2"> 图片URL </label>
        <div class="col-sm-9">
            <input type="text" datatype="s" sucmsg="haha" name="images" id="images" placeholder="" class="col-xs-10 col-sm-5" />
            <a class="uploadUrl" onclick="fileUpload('images','picture',20,'png,jpg,gif')">上传</a>
        </div>
    </div>
    -->
    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-2"> 传播范围 </label>
        <div class="col-sm-9">
        	<select name="spreadRange" class="col-xs-10 col-sm-5">
	           	<option value="0">全部</option>
	           	<option value="1">通过ALIAS别名指定目标</option>
	           	<option value="2">通过TAG标签指定目标</option>
	           	<option value="4">通过别名ALIAS和标签TAG来指定目标</option>
	           	<option value="7">通过ALIAS,TAG和REGISTRATION_ID来指定目标</option>
        	</select>
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-2"> 消息内容 *(必填)</label>
        <div class="col-sm-9">
            <input type="text" datatype="s" sucmsg="haha" name="messageContent" id="form-field-2" placeholder="" class="col-xs-10 col-sm-5" />
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-2"> 通知内容 *(必填)</label>
        <div class="col-sm-9">
            <input type="text" datatype="s" sucmsg="haha" name="noticeContent" id="form-field-2" placeholder="" class="col-xs-10 col-sm-5" />
        </div>
    </div>
    
    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-2"> ALIAS别名指定的手机号<span style="color:red">eg:[12345678901,12345678901]</span> </label>
        <div class="col-sm-9">
            <input type="text" datatype="s" sucmsg="haha" name="alias" id="form-field-2" placeholder="" class="col-xs-10 col-sm-5" />
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-2"> TAG分类标签<span style="color:red">eg:[examDate2016年下半期,typeCode3,degreeCode2]</span> </label>
        <div class="col-sm-9">
            <input type="text" datatype="s" sucmsg="haha" name="tags" id="form-field-2" placeholder="" class="col-xs-10 col-sm-5" />
        </div>
    </div>
     <div id="element_id"> 
  			<select class="province"></select> 
  			<select class="city"></select> 
  			<select class="area"></select> 
	<a href="javascript:;" onclick="findCity();" >查询</a>
	城市code为：<span id="cityCode" style="color:blue"></span>
	</div> 
	<div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-2"> 消息参数 <span style="color:red">eg:{'retcode':'20409','retdesc':'token已失效，请重新登录'}</span></label>
        <div class="col-sm-9">
            <input type="text" datatype="s" sucmsg="haha" name="messageExtras" id="form-field-2" placeholder="" class="col-xs-10 col-sm-5" />
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-2"> 通知参数 <span style="color:red">eg:{'retcode':'20409','retdesc':'token已失效，请重新登录'}</span></label>
        <div class="col-sm-9">
            <input type="text" datatype="s" sucmsg="haha" name="noticeExtras" id="form-field-2" placeholder="" class="col-xs-10 col-sm-5" />
        </div>
    </div>
</form>

<script>
    $("#addForm").Validform({
        tiptype: 2,
        postonce: true
    });
    // selects 为数组形式，请注意顺序 
	$('#element_id').cxSelect({ 
  		url: '${JS_PATH}jquery.cxselect-1.3.4/cityData.min.json', // 提示：如果服务器不支持 .json 类型文件，请将文件改为 .js 文件 
  		selects: ['province', 'city', 'area'], 
  		nodata: 'none' 
	});
	function findCity(){
		var province = $(".province option:selected").val();
		var city = $(".city option:selected").val();
		var area = $(".area option:selected").val();
		alert(province);
		alert(city);
		alert(area);
		$.ajax({
		  type : "post",
		  url : "/pushMessage/findCity",
		  timeout : 30000,
		  data :{"province":province,"city":city,"area":area},
		  dataType: "json",
		  async: false,//同步
		  success: function (data){
		  	alert(data.id);
		  	$("#cityCode").text(data.id);
		  },
		  error: function(xhr, type){
			console.log("error");
		  }
		});
  	}
</script>
    <@fileUpload></@fileUpload>
</@htmlBody>
