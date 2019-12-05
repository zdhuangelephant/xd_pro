<#include "/common/layout.ftl" />

<@htmlHead title="首页">
</@htmlHead>
<@htmlBody bodyclass="page-content">

<form id="editForm" method="post" class="form-horizontal" role="form" action="/courseVideo/doEdit">


    <div class="form-group">
        <input type="hidden" name="id" value="${courseVideo.id}" />
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
            <input type="text" datatype="s" sucmsg=" " value="${courseVideo.name}" name="name" id="form-field-2" placeholder="" class="col-xs-10 col-sm-5" />
        </div>
    </div>

    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-2"> 描述 </label>
        <div class="col-sm-9">
            <textarea rows="5"  cols="40"    name="detail"  />
        	${courseVideo.detail} </textarea>
        </div>
    </div>

    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-2"> 下载URL </label>
        <div class="col-sm-9">
            <input type="text" datatype="s" sucmsg=" " value="${courseVideo.fileUrl}" id="videoFile" name="fileUrl" placeholder="" class="col-xs-10 col-sm-5" />
            <a onclick="fileUpload('videoFile','picture',300,'mp4,avi')">上传</a>
        </div>
    </div>

    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-2"> URL </label>
        <div class="col-sm-9">
            <input type="text" datatype="s" sucmsg=" " value="${courseVideo.url}" id="form-field-2" name="url" placeholder="" class="col-xs-10 col-sm-5" />
        </div>
    </div>
     <div class="form-group">
        <label class="col-xs-3 control-label no-padding-right" for="form-field-3"> 视频类型 </label>
        <div class="col-xs-9">
             <select datatype="n" value="${courseVideo.type}" sucmsg=" " id="form-field-1" class="col-xs-10 col-sm-5" name="type">
                <option value="2" <#if courseVideo.type == 2>selected</#if> > 普通视频</option>
                <option value="8" <#if courseVideo.type == 8>selected</#if> >微课视频</option>
            </select>
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-2"> 封面图片 </label>
        <div class="col-sm-9">
            <input type="text" datatype="s" sucmsg="haha" value="${courseVideo.img}" name="img" id="img" placeholder="" class="col-xs-10 col-sm-5" />
            <a onclick="fileUpload('img','picture',20,'png,jpg,gif')">上传</a>
        </div>
    </div>
        <div class="form-group">
        <label class="col-xs-3 control-label no-padding-right" for="form-field-3"> 时长分钟数 </label>
        <div class="col-xs-9">
            <input type="text" value="${courseVideo.timeLengthMinute}" datatype="s" sucmsg="haha" id="form-field-3" name="timeLengthMinute" placeholder="" class="col-xs-10 col-xs-5" />
        </div>
    </div>
    <div class="form-group">
        <label class="col-xs-3 control-label no-padding-right" for="form-field-3"> 时长秒数 </label>
        <div class="col-xs-9">
            <input type="text" value="${courseVideo.timeLengthSecond}" datatype="s" sucmsg="haha" id="form-field-3" name="timeLengthSecond" placeholder="" class="col-xs-10 col-xs-5" />
        </div>
    </div>
</form>
<script>
    $("#editForm").Validform({
        tiptype: 2,
        postonce: true
    });
</script>

    <@fileUpload></@fileUpload>

</@htmlBody>
