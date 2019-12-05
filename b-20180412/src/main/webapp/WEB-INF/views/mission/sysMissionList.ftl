<#include "/common/layout.ftl" />
<@htmlHead title="首页">
</@htmlHead>
<@htmlBody bodyclass="page-content">
<div class="page-header">
    <h1>
       任务
        <small>
            <i class="ace-icon fa fa-angle-double-right"></i>
           系统任务列表
        </small>
    </h1>
</div>

<div style="margin-bottom: 10px;">
    <a class="btn btn-primary" style="border: 0px; width: 90px;" href="#">列表</a>
    <a class="btn btn-primary" style="border: 0px; width: 90px; margin-left: 10px;" onclick="addMission()" >添加</a>
</div>

<table class="table table-striped table-bordered table-hover">
    <thead>
    <tr>
        <th style="display:none;">Id</th>
        <th>任务名称</th>
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
function toPage(page) {
	var url = window.location.pathname + "?page=" + page;
	self.location = url;
}
    /**
     * 编辑系统任务
     * @param catId
     */
    function editMission(id){
        art.dialog.open('/mission/sysMissionEditPage?id='+id,{
            title: '编辑系统任务',
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
        art.dialog.open('/mission/sysMissionAdd',{
            title: '添加系统任务',
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
</script>

</@htmlBody>
