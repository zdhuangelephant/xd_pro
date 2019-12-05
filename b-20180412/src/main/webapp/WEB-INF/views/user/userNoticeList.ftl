<#include "/common/layout.ftl" />
<@htmlHead title="首页">
</@htmlHead>
<@htmlBody bodyclass="page-content">
<div class="page-header">
    <h1>
        公告
        <small>
            <i class="ace-icon fa fa-angle-double-right"></i>
            公告列表
        </small>
    </h1>
</div>

<div style="margin-bottom: 10px;">
    <a class="btn btn-primary" style="border: 0px; width: 90px;" href="#">列表</a>
    <a class="btn btn-primary" style="border: 0px; width: 90px; margin-left: 10px;" onclick="addUserNotice()" >添加</a>
</div>




<table class="table table-striped table-bordered table-hover">
    <thead>
    <tr>
        <th class="center" style="width: 50px;">
            ID
        </th>
        <th style="width: 200px;">标题</th>
        <th>外链设置</th>
        <th style="width: 200px;">创建时间</th>
        <th>创建人/修改人</th>
        <th style="width: 250px;">操作</th>
    </tr>
    </thead>
    <tbody>
        <#list userNoticeLists as userNotice>
        <tr>
            <td>${userNotice.id}</td>
            <#if userNotice.type==1>
            <td><a onclick="showUserNotice(${userNotice.id})">${userNotice.title}</a> 
            	| <a onclick="setOutLink(${userNotice.id})">设置外链</a></td>
            </#if>
            <#if userNotice.type==2>
            <td><a href="${userNotice.content}">${userNotice.title}</a>
            	<a onclick="cancelOutLink(${userNotice.id}, '${userNotice.title}')">取消外链</a></td>
            </#if>
            <td>外链设置</td>
            <td>${userNotice.createTime}</td>
            <td>${userNotice.updateUser}</td>
            
            <td>
                <div class="btn-group">
                    <div class="dropdown">
                        <button class="btn  btn-primary dropdown-toggle" style=" margin: auto 70px; width: 90px; padding: 0px 4px; font-size: 12px;" type="button" id="dropdownMenu2" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">
                                                操作
                            <span class="caret"></span>
                        </button>
                        <ul class="dropdown-menu" aria-labelledby="dropdownMenu2" style=" margin: auto 70px;width: 90px;text-align: center;">
	                        	<#if flag == 'isPush'>
	                        		<li><a style="cursor: pointer" onclick="pushForum(${userNotice.id},'该条信息','${tag}')">全网推送</a></li>
	                        		<#if tag == 'xd_online'>
	                        			<li><a style="cursor: pointer" onclick="pushForum(${userNotice.id},'该条信息','xd_pre_release')">预发布版推送</a></li>
	                        		</#if>
								</#if>
								<li><#if userNotice.type==1>
					            	<a onclick="editUserNotice(${userNotice.id})"  style="cursor: pointer">编辑</a>
					            </#if>
					            <#if userNotice.type==2>        
					            	<a onclick="setOutLink(${userNotice.id})"  style="cursor: pointer">编辑</a>
					            </#if>
					            </li>
                            <li><a style="cursor: pointer;" onclick="delUserNotice(${userNotice.id},'${userNotice.title}')">删除</a></li>
                        </ul>
                    </div>
                </div>
	         </td>
        </tr>
        </#list>
    </tbody>
</table>

<script>

	/**
     * 设置外部链接
     * @param catId
     */
    function setOutLink(catId){
        art.dialog.open('/userNotice/editOutLink?categoryId='+catId,{
            title: '设置外部链接',
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
     * 取消外部链接
     * @param catId
     */
    function cancelOutLink(catId,catName){
         if (confirm("确认要取消"+catName+"的外部链接吗?")) {
            $.post("/userNotice/cancelOutLink", { catId:catId},
                    function(data){
                        if(data.retCode==0){
                            alert("取消失败");
                        } else {
                            alert("取消成功");
                        }
                        location.reload();
                    }
            );
        }
    }
	
	/**
     * 预览用户帮助
     * @param catId
     */
    function showUserNotice(catId){
    	window.open('/userNotice/show?categoryId='+catId,"_blank");
    }
	

    /**
     * 编辑用户帮助
     * @param catId
     */
    function editUserNotice(catId){
    	window.open('/userNotice/edit?categoryId='+catId,"_blank");
    }

    /**
     * 添加公告
     */
    function addUserNotice(){
        window.open('/userNotice/add',"_blank");
    }


    /**
     * 删除公告
     * @param catId
     */
    function delUserNotice(catId,catName){
        if (confirm("确认要删除"+catName+"?")) {
            $.post("/userNotice/delete", { catId:catId},
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
    
    
    function pushForum(id,name,tag){
        if (confirm("确认要推送"+name+"?")) {
            $.post("/userNotice/pushForum", { id:id,typeId:4,tag:tag},
                    function(data){
                    	data=eval('(' + data + ')');
                        if(data.retCode==0){
                            alert("推送成功");
                            location.reload();
                        } else {
                            alert("推送失败");
                        }
                        location.reload();
                    }
            );
        }
    }
</script>

</@htmlBody>
