<#include "/common/layout.ftl" />
<@htmlHead title="首页">
</@htmlHead>
<@htmlBody bodyclass="page-content">
<div class="page-header">
    <h1>
       任务
        <small>
            <i class="ace-icon fa fa-angle-double-right"></i>
           动态任务列表
        </small>
    </h1>
</div>
<form class="form-inline" style="margin-bottom: 10px;">
    <div class="form-group">
        <input type="text" id="queryConds" list="courseId" name="courseId" value="${product.id}" placeholder="请输入课程名称" style="width: 300px;"/>
        <datalist id="courseId">
            <#list subjectList as subject>
                <option value="${subject.id}" <#if courseId == subject.id>selected</#if> >${subject.name}</option>
            </#list>
        </datalist>
    </div>
    <a class="btn btn-primary" style="border: 0px; width: 90px;margin-left: 0px;" onclick="return searchMisson()">搜索</a>
    <button id="refreshButton" class="btn btn-primary" style="border: 0px; width: 90px;margin-left: 0px;">清空</button>
    <a class="btn btn-primary pull-right" style="border: 0px; width: 90px; margin-left: 10px;" onclick="addMission()" >添加任务</a>
</form>



<table class="table table-striped table-bordered table-hover">
    <thead>
    <tr>
        <th style="display:none;">Id</th>
        <th>任务名称</th>
        <th>所属课程</th>
        <th>描述</th>
        <th>顺序</th>  
        <th style="width: 250px;">操作</th>
    </tr>
    </thead>
    <tbody>
        <#list missionlist.result as mission>
        <tr>
            <td style="display:none;">${mission.id}</td>
            <td>${mission.missionName}</td>
            <td>${mission.courseName}</td>
            <td>${mission.missionDesc}</td>
            <td>${mission.missionOrder}</td>
            <td><a
                    onclick="editMission('${mission.id}')">编辑</a><a
                    style="padding: 5px;">|</a><a
                    onclick="delMission('${mission.id}','${mission.missionName}')"
                    >删除</a></td>  
        </tr>
        </#list>
    </tbody>
</table>
<@page totalCount="${missionlist.totalCount}"
pageNo="${missionlist.pageNo}"
totalPage="${missionlist.totalPage}" url=""> </@page>
<script>

	 /**
	       * 编辑栏目
	       */
      function searchMisson() {
        toPage(1);
      }

	 function toPage(page) {
	   	var url = window.location.pathname;
        var courseId = $("#queryConds").val();
        url+="?courseId="+courseId
            + "&page=" + page;
        //alert(url);    
        self.location=url;
        return false;
	 }

    /**
     * 编辑动态任务
     * @param catId
     */
    function editMission(id){
        art.dialog.open('/mission/dynamicMissionEditPage?id='+id,{
            title: '编辑动态任务',
            width:500,
            height:600,
            ok: function () {
                var iframe = this.iframe.contentWindow;
                if (!iframe.document.body) {
                    alert('iframe还没加载完毕呢')
                    return false;
                };
                var form = iframe.document.getElementById('editForm');
                form.submit();
                return false;
            },
            cancelVal: '关闭',
            cancel: true,
            close: function () {
                location.reload();
            }
        });
    }

    /**
     * 添加任务
     */
    function addMission(){
        var courseName = document.getElementById("queryConds").value;
        art.dialog.open('/mission/dynamicAddPage?courseName='+courseName,{
            title: '添加动态任务',
            width:500,
            height:600,
            ok: function () {
                var iframe = this.iframe.contentWindow;
                if (!iframe.document.body) {
                    alert('iframe还没加载完毕呢')
                    return false;
                };
                var form = iframe.document.getElementById('addForm');
                form.submit();
                return false;
            },
            cancelVal: '关闭',
            cancel: true,
            close: function () {
                location.reload();
            }
        });
    }


    /**
     * 删除任务
     * @param Id
     */
    function delMission(id,catName){
        if (confirm("确认要删除"+catName+"?")) {
            $.post("/mission/delete", { id:id},
                    function(data){
                        if(data.retCode==0){
                            alert("删除失败");
                        } else {
                            alert("删除成功");
                        }
                        location.reload();
                    }
            );
        }
    }
    
//    $('#queryConds').typeahead(
//      {
//          source : function(courseName, process) {
//              // query是输入值
//              $.post('/mission/search4Name', {
//                  courseName : courseName
//              }, function(data) {
//                  data = eval('(' + data + ')');
//                  if (data.retCode == 0) {
//                      process(data.forumTitleList);
//                  }
//              });
//          },
//          updater : function(item) {
//              return item.replace(/<p value="(.+?)">(.+?)<\/p>/, "$2"); // 这里一定要return，否则选中不显示
//          },
//          items : 10, // 显示10条
//          delay : 500
//      // 延迟时间
//      });
   //单击下拉框
   $('#queryConds').focus();
   //清空按钮重新加载页面
   $('#refreshButton').click(function(){
     $("#queryConds").val('');
     toPage(1);
   });
   
</script>

</@htmlBody>
