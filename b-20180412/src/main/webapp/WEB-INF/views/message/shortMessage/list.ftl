<#include "/common/layout.ftl" />
<@htmlHead title="首页">
</@htmlHead>
<@htmlBody bodyclass="page-content">
<div class="page-header">
    <h1>
        系统短信管理
        <small>
            <i class="ace-icon fa fa-angle-double-right"></i>
            系统短信管理列表
        </small>
    </h1>
</div>
<form class="form-inline" style="margin-bottom: 10px;">
    <div class="form-group">
        <select name="merchantId" class="form-control">
           <option value="0">请选择短信发送服务商</option>
           <#list merchantLists as merchant>
                <option value="${merchant.id}" <#if merchantId == merchant.id>selected</#if> >${merchant.name}</option>
            </#list>
        </select>
    </div>
    <button type="submit" class="btn btn-primary" style="border: 0px; width: 90px;">搜索</button>
    <a class="btn btn-primary pull-right" onclick="addShort()" style="border: 0px; width: 90px; margin-left: 10px;">添加短信</a>
</form>

<table class="table table-striped table-bordered table-hover">
    <thead>
    <tr>
        <th class="center" style="width: 50px;">
            ID
        </th>
        <th>后台短信操作状态</th>
        <th>短信模板</th>
        <th>发送手机号</th>
        <th>模板变量值</th>
        <th>创建时间</th>
       	<th>创建人</th>
       	<th>最后操作时间</th>
       	<th>操作人</th>
       	<th>操作</th>
    </tr>
    </thead>
    <tbody>
        <#list shortPage.result as sms>
        <tr>
            <td>${sms.id}</td>
            <td><#if sms.messageStatus==0>未发送<#elseif sms.messageStatus==1>已经发送</#if></td>
            <td>${sms.templateModel.messageContent}</td>
            <td>${sms.telephone}</td>
            <td>${sms.variables}</td>
            <td>${sms.createTime}</td>
            <td>${sms.createUser}</td>
            <td>${sms.updateTime}</td>
            <td>${sms.updateUser}</td>
            <td style="width:50px">
            	<a onclick="editShort(${sms.id})" style="cursor: pointer;">修改</a>
				<a onclick="delShort(${sms.id})" style="cursor: pointer;">删除</a>
				<#if sms.messageStatus==0>
					<a href="/shortMessage/sms?adminShortMessageId=${sms.id}" style="cursor: pointer;">发送</a>
				</#if>
            </td>
        </tr>
        </#list>
    </tbody>
</table>

	<@page totalCount="${shortPage.totalCount}" pageNo="${shortPage.pageNo}" totalPage="${shortPage.totalPage}" url="">
    </@page>
<script>

    /**
     * 添加短信
     */
    function addShort(){
        art.dialog.open('/shortMessage/addShort',{
            title: '添加短信',
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
     * 编辑短信内容
     * @param adminShortMessageId
     */
    function editShort(adminShortMessageId){
        art.dialog.open('/shortMessage/editShort?adminShortMessageId='+adminShortMessageId,{
            title: '编辑短信内容',
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
        var url = window.location.pathname+"?merchantId=${merchantId}&page="+page;
        self.location=url;
    }
    /**
     * 删除短信内容
     * @param adminShortMessageId
     */
    function delShort(adminShortMessageId){
        if (confirm("确认要删除?")) {
            $.post("/shortMessage/deleteShort", { adminShortMessageId:adminShortMessageId},
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
