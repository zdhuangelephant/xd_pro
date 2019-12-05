<#include "/common/layout.ftl" />
<@htmlHead title="首页">
</@htmlHead>
<@htmlBody bodyclass="page-content">
<div class="page-header">
    <h1>
        产品
        <small>
            <i class="ace-icon fa fa-angle-double-right"></i>
            产品列表
        </small>
                <small>
                <#if category.name??>
            <i class="ace-icon fa fa-angle-double-right"></i>
   ${category.name}
        </small>
        </#if>
    </h1>
</div>

<form class="form-inline" style="margin-bottom: 10px;">
    <div class="form-group">
        <select name="catId" class="form-control">
            <option value="0">请选择栏目</option>
        ${selectTree}
        </select>
    </div>
    <button type="submit" class="btn btn-primary" style="border: 0px; width: 90px;">搜索</button>
    <a class="btn btn-primary pull-right" style="border: 0px; width: 90px; margin-left: 10px;" onclick="addProduct()" >添加产品</a>
</form>

<table class="table table-striped table-bordered table-hover">
    <thead>
    <tr>
        <th class="center">
            ID
        </th>
        <th style="width: 150px;">栏目</th>
        <th style="width: 150px;">名称</th>
        <th>类型</th>
        <th>简介</th>
        <th>报名人数</th>
        <th>点赞数</th>
        <th>是否显示</th>
        <th>原价</th>
        <th>优惠价</th>
        <th>创建时间</th>
        <th>封面</th>
        <th style="width:150px;">操作</th>
    </tr>
    </thead>
    <tbody>
    <#list subjectList as subject>
        <tr>
            <td>${subject.id}</td>
            <td>${subject.categoryName}</td>
            <td>${subject.name}</td>
            <td><#if subject.type==1>题库<#else>课程</#if></td>
            <td>${subject.briefInfo}</td>
            <td>${subject.currApplyCount}</td>
            <td>${subject.praiseCount}</td>
            <td>
                <#if subject.showStatus==1>显示<#else><span style="color: red;">不显示</span></#if>
            </td>
            <td>￥${subject.originalAmount}</td>
            <td>￥${subject.payAmount}</td>
            <td>${subject.createTime}</td>
            <td><img src="${subject.imageUrl}" style="width: 50px; height: 50px;" ></td>
            <td><a style="cursor: pointer" onclick="editCourse(${subject.id})">修改</a>
                <a style="padding-left: 5px;cursor: pointer;" onclick="delCourse(${subject.id},'${subject.name}')">删除</a>
                <a style="padding-left: 5px;cursor: pointer;" href="/productItem/list?productId=${subject.id}">内容管理</a></td>
        </tr>
    </#list>
    </tbody>
</table>

<script>
    /**
     * 编辑栏目
     * @param catId
     */
    function editCourse(id){
        art.dialog.open('/product/edit?id='+id,{
            title: '编辑课程',
            width:400,
            height:500,
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
    function addProduct(){
        art.dialog.open('/product/add?catId=${category.id}',{
            title: '添加产品',
            width:400,
            height:500,
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
    function delCourse(id,name){
        if (confirm("确认要删除"+name+"?")) {
            $.post("/product/delete", {id:id},
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
