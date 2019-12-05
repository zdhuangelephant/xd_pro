<#include "/common/layout.ftl" />
<@htmlHead title="首页">
</@htmlHead>
<@htmlBody bodyclass="page-content">
<div class="page-header">
    <h1>
        用户帮助
        <small>
            <i class="ace-icon fa fa-angle-double-right"></i>
            用户帮助列表
        </small>
    </h1>
</div>

<div style="margin-bottom: 10px;">
    <a class="btn btn-primary" style="border: 0px; width: 90px;" href="#">列表</a>
    <a class="btn btn-primary" style="border: 0px; width: 90px; margin-left: 10px;" onclick="addUserHelp()" >添加</a>
</div>




<table class="table table-striped table-bordered table-hover">
    <thead>
    <tr>
        <th class="center" style="width: 50px;">
            ID
        </th>
        <th style="width: 200px;">标题</th>
        <th>内容</th>      
        <th>创建时间</th>
        
        <th style="width: 250px;">操作</th>
    </tr>
    </thead>
    <tbody>
        <#list userHelpLists as userHelp>
        <tr>
            <td>${userHelp.id}</td>
            <td><a onclick="showUserHelp(${userHelp.id})">${userHelp.title}</a></td>
            <td>${userHelp.createTime}</td>
            <td><a
                    onclick="editUserHelp(${userHelp.id})">编辑</a><a
                    style="padding: 5px;">|</a><a
                    onclick="delUserHelp(${userHelp.id},'${userHelp.title}')"
                    >删除</a>
                    
                    
        </tr>
        </#list>
    </tbody>
</table>

<script>
	/**
     * 预览用户帮助
     * @param catId
     */
    function showUserHelp(catId){
        art.dialog.open('/userHelp/show?categoryId='+catId,{
            title: '编辑用户帮助',
            width:400,
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
     * 编辑用户帮助
     * @param catId
     */
    function editUserHelp(catId){
        art.dialog.open('/userHelp/edit?categoryId='+catId,{
            title: '编辑用户帮助',
            width:400,
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
     * 添加用户帮助
     */
    function addUserHelp(){
        art.dialog.open('/userHelp/add',{
            title: '添加用户帮助',
            width:400,
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
     * 删除用户帮助
     * @param catId
     */
    function delUserHelp(catId,catName){
        if (confirm("确认要删除"+catName+"?")) {
            $.post("/userHelp/delete", { catId:catId},
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
