<#include "/common/layout.ftl" />
<@htmlHead title="首页">
</@htmlHead>
<@htmlBody bodyclass="page-content">
<div class="page-header">
    <h1>
        模板类型
        <small>
            <i class="ace-icon fa fa-angle-double-right"></i>
            模版类型列表
        </small>
    </h1>
</div>
<form class="form-inline" style="margin-bottom: 10px;">
    <div class="form-group">
        <select name="catId" class="form-control">
            <option value="0">请选择栏目</option>
           <#list selectChannel as channel>
                <option value="${channel.id}" <#if catId == channel.id>selected</#if> >${channel.name}</option>
            </#list>
        </select>
    </div>
    <button type="submit" class="btn btn-primary" style="border: 0px; width: 90px;">搜索</button>
    <a class="btn btn-primary pull-right" style="border: 0px; width: 90px; margin-left: 10px;" onclick="addTemplateType()" >添加</a>
</form>

<table class="table table-striped table-bordered table-hover">
    <thead>
    <tr>
        <th class="center" style="width: 50px;">
            ID
        </th>
        <th style="width: 200px;">名称</th>
        <th>描述</th>
        <th>重试次数</th>
        <th>模板类型缓存失效时间</th>
        <th>创建时间</th>
       
        <th style="width: 250px;">操作</th>
    </tr>
    </thead>
    <tbody>
        <#list templateTypeList as templateType>
        <tr>
            <td>${templateType.id}</td>
            <td>${templateType.name}</td>
            <td>${templateType.description}</td>
            <td>${templateType.retryTime}</td>
            <td>${templateType.cacheTime}</td>
           <td>${templateType.createTime}</td>
            <td><a
                    onclick="editTemplateType(${templateType.id})">编辑</a><a
                    style="padding: 5px;">|</a><a
                    onclick="delTemplateType(${templateType.id},'${templateType.name}')"
                    >删除</a><a
                    style="padding: 5px;">|</a><a
                    href="/smsTemplate/list?catId=${templateType.id}">短信模版管理</a></td>
                    
                    
        </tr>
        </#list>
    </tbody>
</table>

<script>
    /**
     * 编辑模版类型
     * @param catId
     */
    function editTemplateType(catId){
        art.dialog.open('/smsTemplateType/edit?id='+catId,{
            title: '编辑模版类型',
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
     * 添加模版类型
     */
    function addTemplateType(){
        art.dialog.open('/smsTemplateType/add',{
            title: '添加模版类型',
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
     * 删除模版类型
     * @param catId
     */
    function delTemplateType(catId,catName){
        if (confirm("确认要删除"+catName+"?")) {
            $.post("/smsTemplateType/delete", { catId:catId},
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
