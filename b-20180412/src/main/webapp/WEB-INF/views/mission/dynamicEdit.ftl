<#include "/common/layout.ftl" />

<@htmlHead title="首页">
</@htmlHead>
<@htmlBody bodyclass="page-content">

<form id="editForm" method="post" class="form-horizontal" role="form" action="/mission/dynamicMissionEdit">
 <input type="hidden" value="${mission.missionType}" id="m1"/>
 <input type="hidden" value="${mission.condType}" id="m2"/>
  <input type="hidden" value="${mission.preCondList}" id="m3"/>
  <input type="hidden" value="${mission.jumpType}" id="m4"/>
  <input type="hidden" value="${mission.condition}" id="m5"/>
    <input type="hidden" value="${mission.missionState}" id="m6"/>
        <input type="hidden" value="${mission.missionScope}" id="m7"/>
      <input type="hidden" value="${mission.id}" name="id" id="form-field-2"/>
     <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-1">请选择课程</label>
        <div class="col-sm-9">
            <select name="courseId" class="form-control" id="courseId">
            <option value="0">请选择课程</option>
            <#list subjectList as subject>
                <option value="${subject.id}" <#if mission.courseId == subject.id>selected</#if> >${subject.name}</option>
            </#list>
        </select>
        </div>
    </div>
    
    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-2"> 任务名称 </label>
        <div class="col-sm-9">
            <input type="text"  datatype="s" sucmsg="haha" value="${mission.missionName}" name="missionName" id="form-field-2" placeholder="" class="col-xs-10 col-sm-5" />
        </div>
    </div>

    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-3"> 任务描述 </label>
        <div class="col-sm-9">
            <input type="text" datatype="s" sucmsg="haha" value="${mission.missionDesc}" id="form-field-3" name="missionDesc" placeholder="" class="col-xs-10 col-sm-5" />
        </div>
    </div>
     <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-3"> 任务图片 </label>
        <div class="col-sm-9">
            <input type="text" datatype="s" sucmsg="haha" value="${mission.missionPicUrl}" id="form-field-3" name="missionPicUrl" placeholder="" class="col-xs-10 col-sm-5" />
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-2"> 任务排序</label>
        <div class="col-sm-9">
            <input type="text" datatype="s" sucmsg="haha"  value="${mission.missionOrder}" name="missionOrder" id="form-field-2" placeholder="" class="col-xs-10 col-sm-5" />
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-1">任务类型</label>
        <div class="col-sm-9">
            <select name="missionType" id="missionType" class="form-control">
             <option value="task">任务</option>
             <option value="archieve">成就</option>
        </select>
        </div>
    </div>
      <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-1">条件类型</label>
        <div class="col-sm-9">
            <select name="condType" id="condType" class="form-control" onchange="changeCondType()">
             <option value="pk">挑战</option>
             <option value="randomPk">随机挑战</option>
             <option value="friendPk">好友挑战</option>
              <option value="tollgate">闯关</option>
              <option value="dailyPractice">每日一练</option>
			  <option value="leakFilling">查漏补缺</option>
              <option value="score">得分</option>
              <option value="credit">积分</option>
              <option value="friend">好友</option>
              <option value="answer">答题</option>
                <option value="info">信息</option>
              <option value="wrongques">错题</option>
              <option value="collection">收藏</option>
                <option value="follow">关注</option>
              <option value="followed">被关注</option>
              <option value="active">活跃</option>
        </select>
        </div>
    </div>
       <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-1">前置条件列表</label>
        <div class="col-sm-9">    
            <input type="hidden" value="" id="preCondList" name="preCondList"/>
            <select name="preCond" id="preCond" class="form-control" disabled ="true">
             <option value="0">无</option>
             <option value="have_wrongques">有错题</option>
             <option value="have_tollgate">有余关</option>
             <option value="uncomplete_info">未完善信息</option>
              <option value="hava_friend">有好友</option>
              <option value="score_greater_than">积分大于指定值</option>
              <option value="complete_pre_course">完成了一课的学习</option>
              <option value="complete_pre_chapter">完成了一章的学习</option>
              <option value="complete_pre_item">完成了一节的学习</option>
              <option value="every_daily_practice">每日触发</option>
        </select>
        </div>
    </div>
          <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-1">跳转类型</label>
        <div class="col-sm-9">
          <input type="hidden" value="" id="jumpType" name="jumpType" />
            <select name="jump" id="jump" class="form-control" disabled ="true" >
            <option value="0">无</option>
             <option value="tollgate">闯关</option>
             <option value="dailyPractice">每日练习</option>
			 <option value="leakFilling">查漏补缺</option>
             <option value="randomPk">随机PK</option>
             <option value="friendPk">好友PK</option>
              <option value="improveInfo">完善信息 </option>
              <option value="wrongQues">消灭错题</option>
              <option value="buyCourse">购买课程</option>
        </select>
        </div>
    </div>
      <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-1">条件</label>
        <div class="col-sm-9">
            <select name="condition" class="form-control" id="condition" onchange="changeCondition()">
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
            <input type="text" datatype="s" sucmsg="haha" value="${mission.threshold}" name="threshold" id="form-field-2" placeholder="" class="col-xs-10 col-sm-5" />
        </div>
    </div>

      <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-1">任务状态</label>
        <div class="col-sm-9">
            <select name="missionState" id="missionState" class="form-control">
             <option value="1">有效</option>
             <option value="0">无效</option>
        </select>
        </div>
    </div>
	<div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" style="color:red" for="form-field-2">红包类型</label>
        <div class="col-sm-9">
            <select name="redBonusType" class="form-control" id="redBonusType">
			</select>
        </div>
    </div>
  <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-2"> 积分涨幅</label>
        <div class="col-sm-9">
            <input type="text" datatype="s" sucmsg="haha" value="${mission.creditUpper}" name="creditUpper" id="form-field-2" placeholder="" class="col-xs-10 col-sm-5" />
        </div>
    </div>
</form>
<script>
 $(document).ready(function(){
      $("#missionType").val( $("#m1").val());
       $("#condType").val( $("#m2").val());
       $("#preCondList").val( $("#m3").val());
       $("#jumpType").val( $("#m4").val());
        $("#preCond").val( $("#m3").val());
       $("#jump").val( $("#m4").val());
       $("#missionState").val( $("#m6").val());
       $("#missionScope").val( $("#m7").val());
        changeCondType();
        $("#condition").val( $("#m5").val());
    });
  function changeCondType(){
    var condTypeV=$("#condType").val();
		 if(condTypeV=="pk"){
		 	$("#preCond").val("score_greater_than");
		 	$("#jump").val("0"); 
		 	$("#preCondList").val("score_greater_than");
		 	$("#jumpType").val("0"); 
		 	$("#condition").append("<option value='pk_total_times'>pk总次数</option>");
            $("#condition").append("<option value='pk_total_times_sameperson'>对同一个人pk次数</option>");
            $("#condition").append("<option value='pk_win_times'>pk胜利次数</option>");
            $("#condition").append("<option value='pk_win_times_sameperson'>对同一个人pk胜利次数</option>");
            $("#condition").append("<option value='pk_win_percent'>积pk胜率</option>");
            $("#condition").append("<option value='pk_win_percent_sameperson'>对同一个人pk胜率</option>");
            $("#condition").append("<option value='pk_fail_times'>pk失败次数</option>");
            $("#condition").append("<option value='pk_fail_times_sameperson'>对同一个人pk失败次数</option>");
            $("#condition").append("<option value='pk_fail_percent'>pk失败率</option>");
            $("#condition").append("<option value='pk_fail_percent_sameperson'>对同一个人pk失败率</option>");	  
		 }else if(condTypeV=="randomPk"){
			$("#preCond").val("complete_pre_course");
		 	$("#jump").val("randomPk");
		 	$("#preCondList").val("complete_pre_course");
		 	$("#jumpType").val("randomPk");
		    $("#condition").empty();
		 	$("#condition").append("<option value='complete_random_pk'>发起一次随机PK挑战并取得胜利</option>");	  
            $("#condition").append("<option value='complete_friend_pk'>发起一次好友PK挑战并取得胜利</option>"); 
		 }else if(condTypeV=="friendPk"){
			$("#preCond").val("score_greater_than");
		 	$("#jump").val("friendPk");
		 	$("#preCondList").val("score_greater_than");
		 	$("#jumpType").val("friendPk");
		 	$("#condition").empty();
		 }else if(condTypeV=="dailyPractice"){
			$("#preCond").val("every_daily_practice");
		 	$("#jump").val("dailyPractice");
		 	$("#preCondList").val("every_daily_practice");
		 	$("#jumpType").val("dailyPractice");
		 	$("#condition").empty();
		 	$("#condition").append("<option value='daily_practice_complete'>完成每日一练</option>");
            $("#condition").append("<option value='daily_practice_total'>完成每日一练总天数</option>");
            $("#condition").append("<option value='daily_practice_contin'>连续完成每日一练天数</option>");
		 }else if(condTypeV=="leakFilling"){
			$("#preCond").val("complete_pre_course");
		 	$("#jump").val("leakFilling");
		 	$("#preCondList").val("complete_pre_course");
		 	$("#jumpType").val("leakFilling");
		 	$("#condition").empty();
		 	$("#condition").append("<option value='leak_filling_complete'>完成查漏补缺</option>");
		 	$("#redBonusType").empty();
		 	$("#redBonusType").append("<option value='Leak_filling_complete'>查漏补缺红包</option>");
		 }else if(condTypeV=="tollgate"){
			$("#preCond").val("complete_pre_item");
		 	$("#jump").val("tollgate");
		 	$("#preCondList").val("complete_pre_item");
		 	$("#jumpType").val("tollgate");
		 	$("#condition").empty();
		 	$("#condition").append("<option value='tollgate_total_times'>闯关总数</option>");
            $("#condition").append("<option value='tollgate_total_times_samecourse'>同一课程闯关总数</option>");
            $("#condition").append("<option value='tollgate_designated'>指定关卡</option>");
            $("#condition").append("<option value='tollgate_onestar'>一星关卡总数</option>");
            $("#condition").append("<option value='tollgate_onestar_samecourse'>同一课程一星关卡总数</option>");
            $("#condition").append("<option value='tollgate_twostar'>二星关卡总数</option>");
            $("#condition").append("<option value='tollgate_twostar_samecourse'>同一课程二星关卡总数</option>");
            $("#condition").append("<option value='tollgate_threestar'>三星关卡总数</option>");
            $("#condition").append("<option value='tollgate_threestar_samecourse'>同一课程三星关卡总数</option>"); 
		 }else if(condTypeV=="info"){
			$("#preCond").val("uncomplete_info");
		 	$("#jump").val("improveInfo");
		 	$("#preCondList").val("uncomplete_info");
		 	$("#jumpType").val("improveInfo");
		 	$("#condition").empty();
		 	$("#condition").append("<option value='info_improve'>完善信息</option>"); 
		 }else if(condTypeV=="wrongques"){
			$("#preCond").val("have_wrongques");
		 	$("#jump").val("wrongQues");
		    $("#preCondList").val("have_wrongques");
		 	$("#jumpType").val("wrongQues");
		 	$("#condition").empty();
		 	$("#condition").append("<option value='wrongques_solve_total'>消灭错题总数</option>");
            $("#condition").append("<option value='wrongques_solve_oneday'>单天消灭错题数</option>");
            $("#condition").append("<option value='wrongques_solve_all'>消灭课程全部错题</option>");
		 }else if(condTypeV=="score"){
		 	$("#preCond").val("0");
		 	$("#jump").val("0");
		 	$("#preCondList").val("0");
		 	$("#jumpType").val("0");
		 	$("#condition").empty();
		 	$("#condition").append("<option value='score_total'>总得分</option>");
            $("#condition").append("<option value='score_oneday'>一天得分</option>");
		 }else if(condTypeV=="credit"){
		 	$("#preCond").val("0");
		 	$("#jump").val("0");
		 	$("#preCondList").val("0");
		 	$("#jumpType").val("0");
		 	$("#condition").empty();
		 	$("#condition").append("<option value='credit_total'>总积分</option>");
            $("#condition").append("<option value='credit_oneday'>一天获得积分</option>");
		 }else if(condTypeV=="friend"){
		 	$("#preCond").val("0");
		 	$("#jump").val("0");
		 	$("#preCondList").val("0");
		 	$("#jumpType").val("0");
		 	$("#condition").empty();
		 	$("#condition").append("<option value='friend_count_total'>好友总数</option>");
            $("#condition").append("<option value='friend_count_oneday'>单天加好友数</option>");
		 }else if(condTypeV=="answer"){
		 	$("#preCond").val("0");
		 	$("#jump").val("0");
		 	$("#preCondList").val("0");
		 	$("#jumpType").val("0");
		 	$("#condition").empty();
		 	$("#condition").append("<option value='answer_total'>总答题数</option>");
            $("#condition").append("<option value='answer_oneday'>一天答题数</option>");
		 }else if(condTypeV=="collection"){
		 	$("#preCond").val("0");
		 	$("#jump").val("0");
		 	$("#preCondList").val("0");
		 	$("#jumpType").val("0");
		 	$("#condition").empty();
		 	$("#condition").append("<option value='collection_total'>总收藏题数</option>");
            $("#condition").append("<option value='collection_oneday'>单天收藏题数</option>");
		 }else if(condTypeV=="follow"){
		 	$("#preCond").val("0");
		 	$("#jump").val("0");
		 	$("#preCondList").val("0");
		 	$("#jumpType").val("0");
		 	$("#condition").empty();
		 	$("#condition").append("<option value='follow_count_total'>关注总数</option>");
           $("#condition").append("<option value='follow_count_oneday'>一天内关注数</option>");
		 }else if(condTypeV=="followed"){
		 	$("#preCond").val("0");
		 	$("#jump").val("0");
		 	$("#preCondList").val("0");
		 	$("#jumpType").val("0");
		 	$("#condition").empty();
		 	$("#condition").append("<option value='followed_count_total'>被关注总数</option>");
            $("#condition").append("<option value='followed_count_oneday'>一天内被关注数</option>");
		 }else if(condTypeV=="active"){
		 	$("#preCond").val("0");
		 	$("#jump").val("0");
		    $("#preCondList").val("0");
		 	$("#jumpType").val("0");
		 	$("#condition").empty();	 
            $("#condition").append("<option value='active_total_days'>活跃总天数</option>");
            $("#condition").append("<option value='active_continuous_days'>连续活跃天数</option>");
		 }
    }
	function changeCondition() {
    	var condition=$("#condition").val();
		 if(condition=="daily_practice_complete" || condition=="leak_filling_complete" ){
		 	var courseId = $("#courseId").val();
		 	$("#threshold").val(courseId);
		 }
    }
    $("#editForm").Validform({
        tiptype: 2,
        postonce: true
    });
</script>

</@htmlBody>
