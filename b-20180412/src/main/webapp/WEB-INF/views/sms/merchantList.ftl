<#include "/common/layout.ftl" />
<@htmlHead title="首页">
</@htmlHead>
<@htmlBody bodyclass="page-content">
<div class="page-header">
    <h1>
        供应商
        <small>
            <i class="ace-icon fa fa-angle-double-right"></i>
            供应商列表
        </small>
    </h1>
</div>

<div style="margin-bottom: 10px;">
    <a class="btn btn-primary" style="border: 0px; width: 90px;" href="#">列表</a>
    <a class="btn btn-primary" style="border: 0px; width: 90px; margin-left: 10px;" onclick="addMerchant()" >添加</a>
</div>

<table class="table table-striped table-bordered table-hover">
    <thead>
    <tr>
        <th class="center" style="width: 50px;">
            ID
        </th>
        <th style="width: 200px;">名称</th>
        <th>简称</th>
        <th>地址</th>
        <th>联系电话</th>
        <th>联系人</th>
        
        <th style="width: 250px;">操作</th>
    </tr>
    </thead>
    <tbody>
        <#list merchantLists as merchantList>
        <tr>
            <td>${merchantList.id}</td>
            <td>${merchantList.name}</td>
            <td>${merchantList.shortName}</td>
            <td>${merchantList.address}</td>
            <td>${merchantList.telephone}</td>
            <td>${merchantList.contactPerson}</td>
            <td><a
                    onclick="editMerchant(${merchantList.id})">编辑</a><a
                    style="padding: 5px;">|</a><a
                    onclick="delMerchant(${merchantList.id},'${merchantList.name}')"
                    >删除</a><a
                    style="padding: 5px;">|</a><a
                    href="/smsChannel/list?catId=${merchantList.id}">通道管理</a></td>
                    
                    
        </tr>
        </#list>
    </tbody>
</table>

<script>
    /**
     * 编辑供应商
     * @param catId
     */
    function editMerchant(catId){
        art.dialog.open('/smsMerchant/edit?categoryId='+catId,{
            title: '编辑供应商',
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
     * 添加供应商
     */
    function addMerchant(){
        art.dialog.open('/smsMerchant/add',{
            title: '添加供应商',
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
     * 删除供应商
     * @param catId
     */
    function delMerchant(catId,catName){
        if (confirm("确认要删除"+catName+"?")) {
            $.post("/smsMerchant/delete", { catId:catId},
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
