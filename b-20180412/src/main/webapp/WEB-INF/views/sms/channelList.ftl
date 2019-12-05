<#include "/common/layout.ftl" />
<@htmlHead title="首页">
</@htmlHead>
<@htmlBody bodyclass="page-content">
<div class="page-header">
    <h1>
        通道
        <small>
            <i class="ace-icon fa fa-angle-double-right"></i>
            通道列表
        </small>
    </h1>
</div>
<form class="form-inline" style="margin-bottom: 10px;">
    <div class="form-group">
        <select name="catId" class="form-control">
            <option value="0">请选择栏目</option>
           <#list selectMerchant as merchant>
                <option value="${merchant.id}" <#if catId == merchant.id>selected</#if> >${merchant.name}</option>
            </#list>
        </select>
    </div>
    <button type="submit" class="btn btn-primary" style="border: 0px; width: 90px;">搜索</button>
    <a class="btn btn-primary pull-right" style="border: 0px; width: 90px; margin-left: 10px;" onclick="addChannel()" >添加</a>
</form>



<table class="table table-striped table-bordered table-hover">
    <thead>
    <tr>
        <th class="center" style="width: 50px;">
            ID
        </th>
        <th style="width: 200px;">名称</th>
        <th>用户名</th>
        <th>密钥</th>
        <th>通道URL</th>
        <th>端口号</th>
        <th>响应超时时间</th>
        <th>单通道1秒内最大短信发送数量</th>
        <th>通道余额</th>
        <th>优先级</th>
        <th>状态</th>
        
        <th style="width: 250px;">操作</th>
    </tr>
    </thead>
    <tbody>
        <#list channelList as channel>
        <tr>
            <td>${channel.id}</td>
            <td>${channel.name}</td>
            <td>${channel.userName}</td>
            <td>${channel.secretKey}</td>
            <td>${channel.channelURL}</td>
            <td>${channel.port}</td>
            <td>${channel.timeOut}</td>
            <td>${channel.controlMaxCount}</td>
            <td>￥${channel.balance}</td>
            <td>${channel.priority}</td>
            <td><#if channel.status==1><font color="red">√</font><#else><font color="red">X</font></#if></td>
            <td><a
                    onclick="editChannel(${channel.id})">编辑</a><a
                    style="padding: 5px;">|</a><a
                    onclick="delChannel(${channel.id},'${channel.name}')"
                    >删除</a><a
                    style="padding: 5px;">|</a><a
                    href="/smsTemplateType/list?catId=${channel.id}">模版类型管理</a></td>
                    
                    
        </tr>
        </#list>
    </tbody>
</table>

<script>
    /**
     * 编辑通道
     * @param catId
     */
    function editChannel(catId){
        art.dialog.open('/smsChannel/edit?id='+catId,{
            title: '编辑通道',
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
     * 添加通道
     */
    function addChannel(){
        art.dialog.open('/smsChannel/add',{
            title: '添加通道',
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
     * 删除通道
     * @param catId
     */
    function delChannel(catId,catName){
        if (confirm("确认要删除"+catName+"?")) {
            $.post("/smsChannel/delete", { catId:catId},
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
