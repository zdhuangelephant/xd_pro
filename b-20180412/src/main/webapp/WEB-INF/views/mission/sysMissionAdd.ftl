<#include "/common/layout.ftl" />
<@htmlHead title="首页">
</@htmlHead>
<@htmlBody bodyclass="page-content">

<form id="addForm" method="post" class="form-horizontal" role="form" action="/mission/doAdd">

<input type="hidden" name="courseId" id="courseId" value="-1"/>
<div class="form-group">
    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-2"> 任务名称 </label>
        <div class="col-sm-9">
            <input type="text"  datatype="s" sucmsg="haha" name="missionName" id="form-field-2" placeholder="" class="col-xs-10 col-sm-5" />
        </div>
    </div>

    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-3"> 任务描述 </label>
        <div class="col-sm-9">
            <input type="text" datatype="s" sucmsg="haha" id="form-field-3" name="missionDesc" placeholder="" class="col-xs-10 col-sm-5" />
        </div>
    </div>
     <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-3"> 任务图片 </label>
        <div class="col-sm-9">
            <input type="text" datatype="s" sucmsg="haha" id="form-field-3" name="missionPicUrl" placeholder="" class="col-xs-10 col-sm-5" />
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-2"> 任务排序</label>
        <div class="col-sm-9">
            <input type="text" datatype="s" sucmsg="haha" name="missionOrder" id="form-field-2" placeholder="" class="col-xs-10 col-sm-5" />
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-1">任务类型</label>
        <div class="col-sm-9">
            <select name="missionType" class="form-control">
             <option value="task">任务</option>
             <option value="archieve">成就</option>
        </select>
        </div>
    </div>
      <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-1">条件类型</label>
        <div class="col-sm-9">
            <select name="condType" id="condType" class="form-control" onchange="changeCondition()">
                <option value="info">信息</option>
                <option value="addNote">笔记</option>
        </select>
        </div>
    </div>
      <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-1">前置条件列表</label>
        <div class="col-sm-9">    
            <input type="hidden" value="" id="preCondList" name="preCondList" disable="true"/>
            <select name="preCond" id="preCond" class="form-control" disabled ="true" >
             <option value="0">无</option>
             <option value="uncomplete_info">未完善信息</option>
        </select>
        </div>
    </div>
          <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-1">跳转类型</label>
        <div class="col-sm-9">
          <input type="hidden" value="" id="jumpType" name="jumpType"/>
            <select name="jump" id="jump" class="form-control" disabled ="true" >
            <option value="0">无</option>
              <option value="improveInfo">完善信息 </option>   
              <option value="addNote">笔记数量</option>  
        </select>
        </div>
    </div>
     <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-1">条件</label>
        <div class="col-sm-9">
            <select name="condition" class="form-control" id="condition">
        </select>
        </div>
    </div>
   
     <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-1">取值范围</label>
        <div class="col-sm-9">
        		 <select name="scope" id="scope" class="form-control">
              <option value="GE">>=</option>
             <option value="GT">></option>
             <option value="EQ">=</option>
             <option value="LT"><</option>
             <option value="LE"><=</option>
        </select>
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-1">取值范围阀值</label>
        <div class="col-sm-9">        
            <input type="text" datatype="s" sucmsg="haha" name="threshold" id="form-field-2" placeholder="" class="col-xs-10 col-sm-5" />
        </div>
    </div>

      <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-1">任务状态</label>
        <div class="col-sm-9">
            <select name="missionState" class="form-control">
             <option value="1">有效</option>
             <option value="0">无效</option>
        </select>
        </div>
    </div>
  <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-2"> 积分涨幅</label>
        <div class="col-sm-9">
            <input type="text" datatype="s" sucmsg="haha" name="creditUpper" id="form-field-2" placeholder="" class="col-xs-10 col-sm-5" />
        </div>
    </div>
</form>

<script>
 $(document).ready(function(){
      changeCondition();
    });
   function changeCondition(){
    var condTypeV=$("#condType").val();
		 if(condTypeV=="info"){
			$("#preCond").val("uncomplete_info");
		 	$("#jump").val("improveInfo");
		 	$("#preCondList").val("uncomplete_info");
		 	$("#jumpType").val("improveInfo");
		 	$("#condition").empty();
		 	$("#condition").append("<option value='info_improve'>完善信息</option>");
		 	$("#condition").append("<option value='info_buy_course'>购买指定课程</option>");  
		 }else if(condTypeV=="addNote"){
			$("#preCond").val("0");
		 	$("#jump").val("addNote");
		    $("#preCondList").val("0");
		 	$("#jumpType").val("addNote");
		 	$("#condition").empty();
		 	$("#condition").append("<option value='notes_count'>添加笔记数量</option>");
		 }
    }

 $("#addForm").Validform({
        tiptype: 2,
        postonce: true
    });
  
</script>
</@htmlBody>
