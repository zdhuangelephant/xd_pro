<#include "/common/layout.ftl" />

<@htmlHead title="首页">
</@htmlHead>
<@htmlBody bodyclass="page-content">

<form id="editForm" method="post" class="form-horizontal" role="form" action="/knowledge/forum/doEdit">

    <div class="form-group">
        <div class="col-sm-9">
        	<input type="hidden" id="forumId" name="forumId" class="form-control" value="${forum.forumId}">
        	<img class="forumCover" src="${forum.forumCover}" alt="封面图片" style="width:80%">
            <input type="hidden" datatype="s" sucmsg=" " value="${forum.forumCover}" id="forumCover" name="forumCover" placeholder="" class="col-xs-10 col-sm-5" />
            <a onclick="uploaD()">上传</a>
        </div>
    </div>

</form>
<script>
	function uploaD(){
		fileUploadCallBack(function(url) {
					$(".forumCover").attr("src",url);
					$("#forumCover").val(url);
				},'picture',3,'jpg,jpeg');
	}
    $("#editForm").Validform({
        tiptype: 2,
        postonce: true
    });
</script>

    <@fileUpload></@fileUpload>

</@htmlBody>
