<#include "/common/layout.ftl" />
<@htmlHead title="首页">
</@htmlHead>
<@htmlBody bodyclass="page-content">

<form id="addForm" method="post" class="form-horizontal" role="form" action="/userNotice/doAdd">

    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-2"> 标题</label>
        <div class="col-sm-9">
            <input type="text" datatype="s" sucmsg="haha" name="title" id="form-field-2" placeholder="" class="col-xs-10 col-sm-5" />
        </div>
    </div>

   <div class="form-group">
        <label class="col-sm-3 col-xs-3 control-label no-padding-right" for="form-field-3"> 处理记录 </label>
        <div class="col-sm-9 col-xs-9">
            <textarea name="handleNote" class="form-control" style="width: 100%;overflow-y:visible;height: 160px;">${userFeedBack.handleNote}</textarea>
        </div>
    </div>

    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-3"> 内容 </label>
        <div class="col-sm-9">
            <input id="content" type="text" datatype="s" sucmsg="haha" id="form-field-3" name="content" placeholder="" class="col-xs-10 col-sm-5" />
       		<button style="cursor: pointer" href="javascript:;" onclick="toH5();">跳往h5编辑页</button> 
        </div>
    </div>
    

</form>

<script>
    $("#addForm").Validform({
        tiptype: 2,
        postonce: true
    });
    
    var toH5 = function(){
    	 window.open($("#content").val());
    }
</script>
</@htmlBody>
