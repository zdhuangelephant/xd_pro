<#include "/common/layout.ftl" />
<@htmlHead title="首页">
</@htmlHead>
<@htmlBody bodyclass="page-content">
<div class="page-header">
    <h1>
        用户
        <small>
            <i class="ace-icon fa fa-angle-double-right"></i>
            用户列表
        </small>
    </h1>
</div>
<form class="form-inline" style="margin-bottom: 10px;">
    <div class="form-group">
        <select name="moduleId" class="form-control">
            <option value="0">请选择模块</option>
           <#list selectModule as module>
                <option value="${module.module}" <#if moduleId == module.module>selected</#if> >${module.name}</option>
            </#list>
        </select>
    </div>
    <button type="submit" class="btn btn-primary" style="border: 0px; width: 90px;">搜索</button>
</form>

<table class="table table-striped table-bordered table-hover">
    <thead>
    <tr>
        <th class="center" style="width: 50px;">
            ID
        </th>
        <th style="width: 200px;">用户名</th>
        <th>昵称</th>
        <th>头像URL</th>
        <th>性别</th>
        <th>年龄</th> 
        <th>地址</th>
        <th>所属产品模块</th>
        <th>最新设备ip</th>
        <th>创建时间</th>
    </tr>
    </thead>
    <tbody>
        <#list pageList.result as user>
        <tr>
            <td>${user.id}</td>
            <td>${user.userName}</td>
            <td>${user.nickName}</td>
            <td><img src="${user.portrait}" style="width:30px;height:30px;" /></td>
            <td><#if user.gender==1><font color="red">女</font><#else><font color="red">男</font></#if></td>
            <td>${user.age}</td>
            <td>${user.address}</td>
            <td>${user.name}</td>
            <td>${user.latestDeviceIp}</td>
            <td style="width:150px;">${user.createTime}</td>
        </tr>
        </#list>
    </tbody>
</table>

	<@page totalCount="${pageList.totalCount}" pageNo="${pageList.pageNo}" totalPage="${pageList.totalPage}" url="">
    </@page>
<script>
    /**
     * 编辑用户
     * @param catId
     */
    function editUser(catId){
        art.dialog.open('/user/edit?categoryId='+catId,{
            title: '编辑用户',
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
    
    function toPage(page){
        var url = window.location.pathname+"?moduleId=${moduleId}&page="+page;
        self.location=url;
    }

    /**
     * 添加用户
     */
    function addUser(){
        art.dialog.open('/user/add',{
            title: '添加用户',
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
     * 删除用户
     * @param catId
     */
    function delUser(catId,catName){
        if (confirm("确认要删除"+catName+"?")) {
            $.post("/user/delete", { catId:catId},
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
