<#include "/common/layout.ftl" />

<@htmlHead title="首页">
</@htmlHead>
<@htmlBody bodyclass="page-content">

<form id="editForm" method="post" class="form-horizontal" role="form" action="/courseAudio/doEdit">


    <div class="form-group">
        <input type="hidden" name="id" value="${courseAudio.id}" />
        <label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 上级目录 </label>
        <div class="col-sm-9">
            <select datatype="n" sucmsg=" " id="form-field-1" class="col-xs-10 col-sm-5" name="chapterId">
                <option value="0">作为一级目录</option>
            ${selectTree}
            </select>
        </div>
    </div>

    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-2"> 名称 </label>
        <div class="col-sm-9">
            <input type="text" datatype="s" sucmsg=" " value="${courseAudio.name}" name="name" id="form-field-2" placeholder="" class="col-xs-10 col-sm-5" />
        </div>
    </div>

    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-2"> 描述 </label>
        <div class="col-sm-9">
            <input type="text" datatype="s" sucmsg=" " value="${courseAudio.detail}" id="form-field-2" name="detail" placeholder="" class="col-xs-10 col-sm-5" />
        </div>
    </div>

    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-2"> 音频文件 </label>
        <div class="col-sm-9">
			<audio class="audioFile" src="${courseAudio.url}" controls="controls" style="width:100%">您的浏览器不支持 audio 标签。</audio>
        	<input type="hidden" datatype="s0-300" errormsg="音频文件名过长，请修改文件名后重新上传。" id="audioFile" value="${courseAudio.url}" name="url" placeholder="" class="col-xs-10 col-sm-5" />
            <a onclick="uploaD('audioFile','picture',50,'mp3,wav')">上传</a>
        </div>
    </div>

	<div class="form-group">
        <label class="col-xs-3 control-label no-padding-right" for="form-field-2"> 时长 </label>
        <div class="col-xs-3">
            <input type="text" datatype="s" sucmsg="haha" value="${courseAudio.timeLengthMinute}" name="timeLengthMinute" id="form-field-2" placeholder="" class="col-xs-9 col-xs-5" /> 分
        </div>
        <div class="col-xs-3">
            <input type="text" datatype="s" sucmsg="haha" value="${courseAudio.timeLengthSecond}" name="timeLengthSecond" id="form-field-2" placeholder="" class="col-xs-9 col-xs-5" /> 秒
        </div>
    </div>

	<div class="form-group">
        <label class="col-xs-3 control-label no-padding-right" for="form-field-2"> 大小 </label>
        <div class="col-xs-9">
            <input type="text" datatype="s" sucmsg="haha" value="${courseAudio.size}" name="size" id="form-field-2" placeholder="" class="col-xs-10 col-xs-5" /> M
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
    $("#editForm").Validform({
        tiptype: 2,
        postonce: true
    });
</script>

    <@fileUpload></@fileUpload>

</@htmlBody>
