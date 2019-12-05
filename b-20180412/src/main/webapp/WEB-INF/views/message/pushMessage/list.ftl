<#include "/common/layout.ftl" />
<@htmlHead title="首页">

</@htmlHead>
<@htmlBody bodyclass="page-content">
<meta name="viewport" content="width=device-width,initial-scale=1" />
<div class="page-header">
    <h1>
        系统推送管理
        <small>
            <i class="ace-icon fa fa-angle-double-right"></i>
            系统推送管理列表
        </small>
    </h1>
</div>
<form class="form-inline" style="margin-bottom: 10px;">
    <div class="form-group">
        <select name="moduleId" class="form-control">
            <option value="0">请选择推送通道</option>
           <#list selectModule as module>
                <option value="${module.module}" <#if moduleId == module.module>selected</#if> >${module.name}</option>
            </#list>
        </select>
    </div>
    <button type="submit" class="btn btn-primary" style="border: 0px; width: 90px;">搜索</button>
    <a class="btn btn-primary pull-right" onclick="addPush()" style="border: 0px; width: 90px; margin-left: 10px;">添加推送</a>
</form>

<table class="table table-striped table-bordered table-hover">
    <thead>
    <tr>
        <th >
            ID
        </th>
        <th>后台消息操作状态</th>
        <th>消息类型</th>
        <th>目标设备类型</th>
        <th>消息内容</th>
        <th>通知内容</th> 
        <th>传播范围</th>
        <th>别名指定的手机号</th>
        <th>分类标签</th>
        <th>消息参数</th>
        <th>通知参数 </th>
        <th>创建时间</th>
       	<th>创建人</th>
       	<th>最后操作时间</th>
       	<th>操作人</th>
       	<th>操作</th>
    </tr>
    </thead>
    <tbody>
        <#list pushPage.result as push>
        <tr>
            <td>${push.id}</td>
            <td><#if push.messageStatus=="0">未发送<#elseif push.messageStatus=="1">已经发送</#if></td>
            <td><#if push.messageType=="0">通知+消息<#elseif push.messageType=="2">通知<#elseif push.messageType=="3">消息</#if></td>
            <td><#if push.targetType=="0">全部设备<#elseif push.targetType=="1">android设备<#elseif push.targetType=="2">IOS设备</#if></td>
            <td>${push.messageContent}</td>
            <td>${push.noticeContent}</td>
            <td>
            	<#if push.spreadRange=="0">
            		全部 
            	<#elseif push.spreadRange=="1">
            		通过ALIAS别名指定目标
            	<#elseif push.spreadRange=="2">
            		通过TAG标签指定目标 
            	<#elseif push.spreadRange=="4">
            		通过别名ALIAS和标签TAG来指定目标 
            	<#elseif push.spreadRange=="5">
            		通过ALIAS和REGISTRATION_ID来指定目标 
            	<#elseif push.spreadRange=="6">
            		 通过TAG和REGISTRATION_ID来指定目标 
            	<#elseif push.spreadRange=="7">
            		  通过ALIAS,TAG和REGISTRATION_ID来指定目标 
            	</#if>
            </td>
            <td>${push.alias}</td>
            <td>${push.tags}</td>
            <td>${push.messageExtras}</td>
            <td>${push.noticeExtras}</td>
            <td>${push.createTime}</td>
            <td>${push.createUser}</td>
            <td>${push.updateTime}</td>
            <td>${push.updateUser}</td>
            <td style="width:50px">
            	<a onclick="editPush(${push.id})" style="cursor: pointer">修改</a>
				<a onclick="delPush(${push.id})" style="cursor: pointer;">删除</a>
				<#if push.messageStatus=="0">
				<a href="/pushMessage/push?adminPushMessageId=${push.id}" style="cursor: pointer;">推送</a>
				</#if>
            </td>
        </tr>
        </#list>
    </tbody>
</table>

	<@page totalCount="${pushPage.totalCount}" pageNo="${pushPage.pageNo}" totalPage="${pushPage.totalPage}" url="">
    </@page>
<script>

    /**
     * 添加推送
     */
    function addPush(){
        art.dialog.open('/pushMessage/addPush',{
            title: '添加推送',
            width:400,
            height:700,
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
     * 编辑推送内容
     * @param catId
     */
    function editPush(pushId){
        art.dialog.open('/pushMessage/editPush?adminPushMessageId='+pushId,{
            title: '编辑推送内容',
            width:400,
            height:700,
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
     * 删除推送内容
     * @param catId
     */
    function delPush(pushId){
        if (confirm("确认要删除?")) {
            $.post("/pushMessage/deletePush", { adminPushMessageId:pushId},
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
