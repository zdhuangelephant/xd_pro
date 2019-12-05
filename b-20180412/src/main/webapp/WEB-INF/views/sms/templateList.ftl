<#include "/common/layout.ftl" />
<@htmlHead title="首页">
</@htmlHead>
<@htmlBody bodyclass="page-content">
<div class="page-header">
    <h1>
        短信模板
        <small>
            <i class="ace-icon fa fa-angle-double-right"></i>
            短信模板列表
        </small>
    </h1>
</div>
<form class="form-inline" style="margin-bottom: 10px;">
    <div class="form-group">
        <select name="catId" class="form-control">
            <option value="0">请选择栏目</option>
           <#list selectTemplateType as templateType>
                <option value="${templateType.id}" <#if catId == templateType.id>selected</#if> >${templateType.name}</option>
            </#list>
        </select>
    </div>
    <button type="submit" class="btn btn-primary" style="border: 0px; width: 90px;">搜索</button>
    <a class="btn btn-primary pull-right" style="border: 0px; width: 90px; margin-left: 10px;" onclick="addTemplate()" >添加</a>
</form>

<table class="table table-striped table-bordered table-hover">
    <thead>
    <tr>
        <th class="center" style="width: 50px;">
            ID
        </th>
        <th style="width: 200px;">短信模板内容</th>
        <th>短信模板描述</th>
        <th>状态</th>
        <th>创建时间</th>
       
        <th style="width: 250px;">操作</th>
    </tr>
    </thead>
    <tbody>
        <#list templateList as template>
        <tr>
            <td>${template.id}</td>
            <td>${template.messageContent}</td>
            <td>${template.description}</td>
            <td><#if template.status==1><font color="red">√</font><#else><font color="red">X</font></#if></td>
           <td>${template.createTime}</td>
            <td><a
                    onclick="editTemplate(${template.id})">编辑</a><a
                    style="padding: 5px;">|</a><a
                    onclick="delTemplate(${template.id},'${template.name}')"
                    >删除</a>
                    
                    
        </tr>
        </#list>
    </tbody>
</table>

<script>
    /**
     * 编辑模版
     * @param catId
     */
    function editTemplate(catId){
        art.dialog.open('/smsTemplate/edit?id='+catId,{
            title: '编辑模版',
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
     * 添加模版
     */
    function addTemplate(){
        art.dialog.open('/smsTemplate/add',{
            title: '添加模版',
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
     * 删除模版
     * @param catId
     */
    function delTemplate(catId,catName){
        if (confirm("确认要删除"+catName+"?")) {
            $.post("/smsTemplate/delete", { catId:catId},
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
