<#include "/common/layout.ftl" />
<@htmlHead title="首页">
</@htmlHead>
<@htmlBody bodyclass="page-content">
<div class="page-header">
    <h1>
        产品应用端
        <small>
            <i class="ace-icon fa fa-angle-double-right"></i>
          产品应用端列表
        </small>
    </h1>
</div>

<div style="margin-bottom: 10px;">
    <a class="btn btn-primary" style="border: 0px; width: 90px; margin-left: 2px;" onclick="addModule()" >添加</a>
</div>

<table class="table table-striped table-bordered table-hover">
    <thead>
    <tr>
        <th class="center" style="width: 50px;">
            ID
        </th>
        <th style="width: 200px;">名称</th>
        <th>描述</th>
        <th style="width: 150px;">APP模块号</th>
        <th style="width: 300px;">操作</th>
    </tr>
    </thead>
     <tbody>
        <#list productModuleList as productModule>
        <tr>
            <td>${productModule.id}</td>
            <td>${productModule.name}</td>
            <td>${productModule.detail}</td>
            <td>${productModule.module}</td>
            <td><a
                    onclick="editModule(${productModule.id})">编辑</a><a
                    style="padding: 5px;">|</a><a
                    onclick="delModule(${productModule.id},'${productModule.name}')"
                    >删除</a><a
                    style="padding: 5px;">|</a><a
                    href="/productCategory/list?catId=${productModule.id}&classify=1">产品分类</a><a
                    style="padding: 5px;">|</a>
                            <#if productModule.hasBeginnerCourse==0||productModule.hasBeginnerCourse==''> <a onclick="addBeginner(${productModule.id},'${productModule.name}')">添加新手课程</a><#else>
                      		<a  href="/productItem/list?productId=${productModule.courseId}">新手课程查看</a></td>
                            </#if>
                   </td>
                    
                    
        </tr>
        </#list>
    </tbody>
</table>

<script>
    /**
     * 编辑栏目
     * @param catId
     */
    function editModule(id){
        art.dialog.open('/productModule/edit?id='+id,{
            title: '编辑栏目',
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
     * 添加栏目
     */
    function addModule(){
        art.dialog.open('/productModule/add',{
            title: '添加栏目',
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
     * 删除栏目
     * @param catId
     */
    function delModule(id,name){
        if (confirm("确认要删除"+name+"?")) {
            $.post("/productModule/delete", { id:id},
                    function(data){
                        if(data.retCode==0){
                            alert("删除成功");
                        } else {
                            alert("删除失败");
                        }
                        location.reload();
                    }
            );
        }
    }
        /**
     * 添加新手课程
     */
    function addBeginner(productModuleId,productModuleName){
        art.dialog.open('/product/addBeginner?productModuleId='+productModuleId,{
            title: '新增新手课程',
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
</script>

</@htmlBody>
