<#include "/common/layout.ftl" />
<@htmlHead title="首页">
</@htmlHead>
<@htmlBody bodyclass="page-content">
<div class="page-header">
    <h1>
        话题
        <small>
            <i class="ace-icon fa fa-angle-double-right"></i>
            话题列表
        </small>
    </h1>
</div>
<form class="form-inline" style="margin-bottom: 10px;">
    <div class="form-group">
        <select name="catId" class="form-control">
            <option value="0">请选择话题分类</option>
            <#list forumCategoryList as category>
                <option value="${category.id}" <#if catId == category.id>selected</#if> >${category.title}</option>
            </#list>
        </select>
    </div>
    <button type="submit" class="btn btn-primary" style="border: 0px; width: 90px;">搜索</button>
</form>
<ul class="nav nav-tabs" style="height: 45px;">
    <li  class="active"><a href="#">正常状态</a></li>
    <li ><a href="/forumClose/list?catId=${catId}&status=0" >关闭状态</a></li>
    
</ul>
 

<table class="table table-striped table-bordered table-hover">
    <thead>
    <tr>
        <th class="center">
            ID
        </th>
        <th style="width: 150px;">话题标题</th>
        <th>描述</th>
        <th>内容</th>
        <th>图片展示</th>
        <th>点赞数</th>
        <th>是否精华</th>
        <th>是否置顶</th>
        <th>是否推荐</th>
        <th>标签</th>
        <th>状态</th>
        <th>创建时间</th>
        <th style="width:150px;">操作</th>
    </tr>
    </thead>
    <tbody>
    <#list subjectList as subject>
        <tr>
            <td>${subject.id}</td>
            <td>${subject.title}</td>
            <td>${subject.outline}</td>
            <td>${subject.content}</td>
            <td><img src="${subject.images}" style="width:30px;height:30px;" /></td>
            <td>${subject.praiseNumber}</td>
            <td><#if subject.digest==1><font color="red">精华</font><#else>普通</#if></td>
            <td><#if subject.top==1><font color="red">置顶</font><#else>普通</#if></td>
            <td><#if subject.recommend==1><font color="red">推荐</font><#else>普通</#if></td>
            <td>${subject.tag}</td>
            <td><#if subject.status==1><font color="red">√</font><#else><font
                        color="red">X</font></#if></td>
            <td>${subject.createTime}</td>
            <td><a style="cursor: pointer" onclick="subjectStatus(${subject.id})">状态</a>
                <a style="padding-left: 5px;cursor: pointer;" onclick="delCourse(${subject.id},'${subject.title}')">删除</a>
                <a style="padding-left: 5px;cursor: pointer;" href="/forumComment/list?catId=${subject.id}">回帖管理</a></td>
        </tr>
    </#list>
    </tbody>
</table>

<script>

    /** 
     *话题状态
     *@param id
     */
    function subjectStatus(id) {
        art.dialog.open('/forumTopic/edit?id='+id,{
            title: '话题状态设置',
            width: 400,
            ok: function () {
                var iframe = this.iframe.contentWindow;
                if (!iframe.document.body) {
                    alert('iframe还没加载完毕呢')
                    return false;
                };
                var form=iframe.document.getElementById('editForm');
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
     * 删除话题
     * @param catId
     */
    function delCourse(id,name){
        if (confirm("确认要删除"+name+"?")) {
            $.post("/forumTopic/delete", {id:id},
                    function(data){
                        if(data.retCode==0){
                            alert("删除失败！");
                        } else {
                            alert("删除成功！");
                        }
                        location.reload();
                    }
            );
        }
    }
</script>

</@htmlBody>
