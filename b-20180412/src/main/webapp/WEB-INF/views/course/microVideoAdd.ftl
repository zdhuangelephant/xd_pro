<#include "/common/layout.ftl" />
<@htmlHead title="首页">
</@htmlHead>
<@htmlBody bodyclass="page-content">

<form id="addForm" method="post" class="form-horizontal" role="form" action="/microVideo/doAdd">
    <input type="hidden" name="courseId" value="${courseId}" />
    <div class="form-group">
        <label class="col-xs-3 control-label no-padding-right" for="form-field-1"> 上级目录 </label>
        <div class="col-xs-9">
            <select datatype="n" sucmsg=" " id="form-field-1" class="col-xs-10 col-xs-5" name="chapterId">
            ${selectTree}
            </select>
        </div>
    </div>

    <div class="form-group">
        <label class="col-xs-3 control-label no-padding-right" for="form-field-2"> 名称 </label>
        <div class="col-xs-9">
            <input type="text" datatype="s" sucmsg="haha"  name="name" id="form-field-2" placeholder="" class="col-xs-10 col-xs-5" />
        </div>
    </div>

    <div class="form-group">
        <label class="col-xs-3 control-label no-padding-right" for="form-field-3"> 描述 </label>
        <div class="col-xs-9">
            <input type="text" datatype="s" sucmsg="haha" id="form-field-3" name="detail" placeholder="" class="col-xs-10 col-xs-5" />
        </div>
    </div>

    <!-- <div class="form-group">
        <label class="col-xs-3 control-label no-padding-right" for="form-field-3"> 音频文件 </label>
        <div class="col-xs-9">
			<audio class="audioFile" src="" controls="controls" style="width:100%">您的浏览器不支持 audio 标签。</audio>
        	<input type="hidden" datatype="s" sucmsg=" " id="audioFile" name="url" placeholder="" class="col-xs-10 col-sm-5" />
            <a onclick="uploaD('audioFile','picture',50,'mp3,wav')">上传</a>
        </div>
    </div> -->
    <div class="form-group">
        <label class="col-xs-3 control-label no-padding-right" for="form-field-3"> 下载URL </label>
        <div class="col-xs-9">
            <input type="text" datatype="s" sucmsg="haha" id="videoFile" name="url" placeholder="" class="col-xs-10 col-xs-5" />
            <a onclick="fileUpload('videoFile','picture',1024,'mp4,avi')">上传</a>
        </div>
    </div>

    <!-- <div class="form-group">
        <label class="col-xs-3 control-label no-padding-right" for="form-field-3"> URL </label>
        <div class="col-xs-9">
            <input type="text" datatype="s" sucmsg="haha" id="form-field-3" name="url" placeholder="" class="col-xs-10 col-xs-5" />
        </div>
    </div> -->
    

	<div class="form-group">
        <label class="col-xs-3 control-label no-padding-right" for="form-field-2"> 时长 </label>
        <div class="col-xs-3">
            <input type="text" datatype="s" sucmsg="haha"  name="timeLengthMinute" id="form-field-2" placeholder="分" class="col-xs-9 col-xs-5" /> 分
		</div>
		<div class="col-xs-3">
			<input type="text" datatype="s" sucmsg="haha"  name="timeLengthSecond" id="form-field-2" placeholder="秒" class="col-xs-9 col-xs-5" /> 秒
        </div>
    </div>

	<div class="form-group">
        <label class="col-xs-3 control-label no-padding-right" for="form-field-2"> 大小 </label>
        <div class="col-xs-9">
            <input type="text" datatype="s" sucmsg="haha"  name="size" id="form-field-2" placeholder="" class="col-xs-10 col-xs-5" /> M
        </div>
    </div>
    
    <div class="form-group">
        <label class="col-xs-3 control-label no-padding-right" for="form-field-2"> 排序 </label>
        <div class="col-xs-9">
            <input type="text" datatype="s" sucmsg="haha"  name="listOrder" id="form-field-2" placeholder="排序" class="col-xs-10 col-xs-5" /> 
        </div>
    </div>




</form>

<script>
	function uploaD(id,scope,size,type){
		fileUploadCallBack(function(url) {
					$("."+id).attr("src",url);
					$("#"+id).val(url);
				},scope,size,type);
	}
    $("#addForm").Validform({
        tiptype: 2,
        postonce: true
    });
</script>
    <@fileUpload></@fileUpload>
</@htmlBody>
