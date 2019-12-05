<#include "/common/layout.ftl" />
<@htmlHead title="首页">
</@htmlHead>
<@htmlBody bodyclass="page-content">
<div class="page-header">
    <h1>
       任务
        <small>
            <i class="ace-icon fa fa-angle-double-right"></i>
           标准任务列表
        </small>
    </h1>
</div>
<style>
    .orderInput{
        width: 40px;
        text-align: center;
    }
</style>
<form class="form-inline" style="margin-bottom: 10px;">
 
    <div class="form-group">
        <input type="text" id="queryConds" list="courseId" name="courseId" value="${product.id}" placeholder="请输入课程名称" style="width: 300px;"/>
        <datalist id="courseId">
            <#list subjectList as subject>
                <option  label="${subject.name}" value="${subject.id}" />
            </#list>
        </datalist>
    </div>
    <button type="submit" class="btn btn-primary" style="border: 0px; width: 90px;">搜索</button>
    <button id="refreshButton" class="btn btn-primary" style="border: 0px; width: 90px;margin-left: 0px;">清空</button>

    <a class="btn btn-primary pull-right" style="border: 0px; width: 90px; margin-left: 10px;" onclick="order()" >排序</a>
      <a class="btn btn-primary pull-right" style="border: 0px; width: 90px; margin-left: 10px;" onclick="editMissionState(0)" >关闭任务</a>
     <a class="btn btn-primary pull-right" style="border: 0px; width: 90px; margin-left: 10px;" onclick="editMissionState(1)" >启用任务</a>
       <a class="btn btn-primary pull-right" style="border: 0px; width: 90px; margin-left: 10px;" onclick="addMission()" >添加任务</a>
          
</form>



<table class="table table-striped table-bordered table-hover">
    <thead>
    <tr>
        <th>
            <input type="checkbox" id="checkAll" />
        </th>
        <th class="center" style="width: 50px;">
            ID
        </th>
        <th style="width: 100px;">章</th>
        <th style="width: 200px;">章名称</th>
        <th style="width: 100px;">节</th>
        <th>节名称</th>
        <th>排序</th>
        <th>描述</th>
        <th>是否有标准任务</th>
        <th>任务是否启用</th>
    </tr>
    </thead>
    <tbody>
        <#list missionList as mission>
        <tr>
            <td>
                <input class="inputcheckbox " name="ids" value="${mission.missionId}" type="checkbox" id="${mission.courseId};${mission.chapterId};${mission.desc};${mission.threshold};${mission.listOrder}">
            </td>
            <td>${mission.threshold}</td>
            <td>${mission.chapterIndex}</td>
            <td>${mission.chapterName}</td>
            <td>${mission.itemIndex}</td>
            <td>${mission.itemName}</td>
            <td>
            <#if mission.missionId==''>-<#else>
            <input style="padding:0px;width:100px" name="missionOrder" prevalue="${mission.missionOrder}" id="${mission.missionId}" class="orderInput" type="text" value="${mission.missionOrder}">
            </#if>
            </td>
            <td>${mission.desc}</td>
            <td><#if mission.missionId==''>否<#else><span style="color: red">是</span></#if></td>
            <td><#if mission.missionState==1>是<#else><span style="color: red">否</span></#if></td>
        </tr>
        </#list>
    </tbody>
</table>


<script>
 $(document).ready(function(){   
        $("#checkAll").click(function(){
            if($(this).is(":checked")){
                $("input[type='checkbox'][name='ids']").prop("checked", true);
            } else {
                $("input[type='checkbox'][name='ids']").prop("checked", false);
            }
        });
    });

      
	 
   /**
     * 添加栏目
     */
    function addMission(courseId,chapterId) {
          var ids = "";
        
           var missionAdd=new Array();
          var flag=0;
        $("input[name='ids']:checked").each(function() {
            flag++;
             if($(this).val()==""){
                var mv=new Object();
                var obj=$(this)[0].id.split(';');
                mv.courseId=obj[0];
                mv.chapterId=obj[1];
                mv.desc=obj[2];
                mv.threshold=obj[3];
                mv.listOrder=obj[4];
                missionAdd.push(mv);
            }
            
        });
          
        if(flag>0){
            if(missionAdd.length>0){
             var msJson=JSON.stringify(missionAdd);
                $.post("/mission/standardAdd", { msJson:msJson },
                        function(data){
                            alert("成功");
                            location.reload();
                        });
            } else {
                  alert("选择的章节已经添加了任务");
            }
        }else{
             alert("请选择要添加任务的章节");
         }
    }

    /**
     * 排序
     */
    function order(){
        var request = '';
        $(".orderInput").each(function(i){
            var value = $(this).val();
            var id = $(this).attr("id");
            var preValue = $(this).attr("preValue");
            if(typeof(id) != 'undefined' && id != '' && value!=preValue){
                request = request + id + ":" + value + ";";
            }
        });
        if(request==''){
            alert("排序成功");
        } else {
            $.post("/mission/order", { orders: request},
                    function(data){
                        alert("排序成功");
                        location.reload();
                    });
        }
    }

    /**
     * 启用关闭任务
     * 
     */
    function editMissionState(state){
         var ids = "";      
           var missionAdd=new Array();
        $("input[name='ids']:checked").each(function() {        
                 var mv=new Object();
                var id=$(this)[0].value;
                var obj=$(this)[0].id.split(';');
                mv.id=id;
                mv.threshold=obj[3];
                missionAdd.push(mv);   
        });       
            if(missionAdd.length>0){
             var msJson=JSON.stringify(missionAdd);
                $.post("/mission/editState", { msJson:msJson,state:state},
                        function(data){
                            alert(data);
                            location.reload();
                        });           
            } else {
                  alert("请选择章节");
            }
    }

    $('#refreshButton').click(function(){
      $("#queryConds").val('');
      var url = window.location.pathname;
      console.log("page");
      self.location = url;
    });
    
</script>

</@htmlBody>
