<#include "/common/layout.ftl" />
<@htmlHead title="首页">
</@htmlHead>
<@htmlBody bodyclass="page-content">
<div class="page-header">
    <h1>
        话题分类
        <small>
            <i class="ace-icon fa fa-angle-double-right"></i>
            资源分类列表
        </small>
    </h1>
</div>

<div style="margin-bottom: 10px;">
    <a class="btn btn-primary" style="border: 0px; width: 90px;" href="#">列表</a>
    <a class="btn btn-primary" style="border: 0px; width: 90px; margin-left: 10px;" onclick="addCategory()" >添加</a>
</div>

<table class="table table-striped table-bordered table-hover">
    <thead>
    <tr>
        <th class="center" style="width: 50px;">
            ID
        </th>
        <th style="width: 200px;">标题</th>
        <th>描述</th>
        <th>内容</th>
        <th>图片展示</th>
        <th>简称</th>
        
        <th style="width: 250px;">操作</th>
    </tr>
    </thead>
    <tbody>
        <#list categoryLists as categoryList>
        <tr>
            <td>${categoryList.id}</td>
            <td>${categoryList.title}</td>
            <td>${categoryList.outline}</td>
            <td>${categoryList.content}</td>
            <td><img src="${categoryList.images}" style="width:30px;height:30px;" /></td>
            <td>${categoryList.shortName}</td>
            <td><a
                    onclick="editCategory(${categoryList.id})">编辑</a><a
                    style="padding: 5px;">|</a><a
                    onclick="delCategory(${categoryList.id},'${categoryList.title}')"
                    >删除</a><a
                    style="padding: 5px;">|</a><a
                    href="/forumTopic/list?catId=${categoryList.id}">话题管理</a></td>
                    
                    
        </tr>
        </#list>
    </tbody>
</table>

<script>
    /**
     * 编辑栏目
     * @param catId
     */
    function editCategory(catId){
        art.dialog.open('/forumCategory/edit?categoryId='+catId,{
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
    function addCategory(){
        art.dialog.open('/forumCategory/add',{
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
     * 添加栏目
     * @param catId
     */
    function addChildCategory(catId){
        art.dialog.open('/forumCategory/add?moudle='+catId,{
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
    function delCategory(catId,catName){
        if (confirm("确认要删除"+catName+"?")) {
            $.post("/forumCategory/delete", { catId:catId},
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
