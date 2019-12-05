<#include "/common/layout.ftl" />
<@htmlHead title="首页">
</@htmlHead>
<@htmlBody bodyclass="page-content">
<div class="page-header">
    <h1>
        幻灯片
        <small>
            <i class="ace-icon fa fa-angle-double-right"></i>
            列表
        </small>
    </h1>
</div>

<form class="form-inline" style="margin-bottom: 10px;">
    <div class="form-group">
        <select name="moduleId" class="form-control">
            <option value="0">请选择模块</option>
            <#list keywords as module>
                    <option value="${module.moduleId}" <#if moduleId==module.moduleId>selected</#if> >${module.moduleId}&${module.moduleName}</option>
            </#list>
        </select>
    </div>
    <button type="submit" class="btn btn-primary" style="border: 0px; width: 90px;">搜索</button>
    <a class="btn btn-primary" style="border: 0px; width: 110px; margin-left: 10px;" onclick="order()" >排序</a>
    <a class="btn btn-primary pull-right" style="border: 0px; width: 110px; margin-left: 10px;" onclick="addSlide()" >添加幻灯片</a>
</form>

<table class="table table-striped table-bordered table-hover">
    <thead>
    <tr>
        <th>排序</th>
        <th>
            ID
        </th>
        <th>描述</th>
        <th>链接地址</th>
        <th>图片</th>
        <th>模块名称</th>
        <th>操作</th>
    </tr>
    </thead>
     <tbody>
        <#list slideList as slide>
        <tr>
            <td><input class="listOrder" data-id="${slide.id}" value="${slide.listOrder}"  style="width: 50px; text-align: center;"/></td>
            <td>${slide.id}</td>
            <td>${slide.description}</td>
            <td>${slide.linkUrl}</td>
            <td><img src="${slide.imageUrl}" style="height: 50px;"></td>
            <td>${slide.moduleName}</td>
            <td>
                <a onclick="editSlide(${slide.id})" style="cursor: pointer;">编辑</a>
                <a style="padding: 5px;">|</a>
                <a onclick="delSlide(${slide.id})" style="cursor: pointer;">删除</a>
            </td>
        </tr>
        </#list>
    </tbody>
</table>

<script>

    function order(){
        var orders = "";
        $(".listOrder").each(function(){
            orders = orders + $(this).attr("data-id") + ":" + $(this).val() + ";";
        })
        $.post("/slide/order", { orders:orders},
                function(data){
                    alert("排序成功");
                    location.reload();
                }
        );
    }

    /**
     * 编辑栏目
     * @param catId
     */
    function editSlide(id){
        art.dialog.open('/slide/edit?id='+id,{
            title: '编辑幻灯片',
            width:400,
            height:420,
            ok: function () {
                var iframe = this.iframe.contentWindow;
                if (!iframe.document.body) {
                    alert('iframe还没加载完毕呢')
                    return false;
                };
                var form = iframe.document.getElementById('editForm');
                
                var v = iframe.document.getElementById('coalition-customer').value;
                if(!/^[0-9]+&\S+$/.test(v)){
                    alert("格式形如: 模块ID&模块名称");
                    return false;
                }
                
                form.submit();
                form.reset();
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
     * 添加栏目
     */
    function addSlide(){
        art.dialog.open('/slide/add',{
            title: '添加幻灯片',
            width:400,
            height:450,
            ok: function () {
                var iframe = this.iframe.contentWindow;
                if (!iframe.document.body) {
                    alert('iframe还没加载完毕呢')
                    return false;
                };
                var form = iframe.document.getElementById('addForm');
                
                var v = iframe.document.getElementById('coalition-customer').value;
                
                if(!(/^[0-9]+&\S+$/).test(v)){
                    alert("格式形如: 模块ID&模块名称");
                    return false;
                }
                
                form.submit();
                form.reset();
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
     * 删除栏目
     * @param catId
     */
    function delSlide(id){
        if (confirm("确认要删除?")) {
            $.post("/slide/delete", { id:id},
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
