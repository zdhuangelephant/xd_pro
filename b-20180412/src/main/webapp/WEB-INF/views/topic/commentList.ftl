<#include "/common/layout.ftl" />
<@htmlHead title="首页">
</@htmlHead>
<@htmlBody bodyclass="page-content">
<div class="page-header">
    <h1>
        回帖
        <small>
            <i class="ace-icon fa fa-angle-double-right"></i>
            回帖列表
        </small>
    </h1>
</div>


<table class="table table-striped table-bordered table-hover">
    <thead>
    <tr>
        <th class="center">
            ID
        </th>
        <th style="width: 200px;">回帖人</th>
        <th>回帖内容</th>
        <th>图片URL</th>
        <th>回帖id</th>
        <th>回复id</th>
        <th>回复内容</th>
        <th>评论id</th>
        <th>点赞数</th>
        <th>回帖状态</th>
        <th>回帖时间</th>
        <th style="width: 250px;">操作</th>
    </tr>
    </thead>
   <tbody>
    <#list forumCommentList as forumComment>
        <tr>
            <td>${forumComment.id}</td>
            <td>${forumComment.nickName}</td>
            <td>${forumComment.content}</td>
            <td>${forumComment.images}</td>
            <td>${forumComment.replyId}</td>
            <td>${forumComment.targeId}</td>
            <td>${forumComment.targeContent}</td>
            <td>${forumComment.targeCommentId}</td>
            <td>${forumComment.praiseNumber}</td>
            <td><#if forumComment.status==1><font color="red">√</font><#else><font
                        color="red">X</font></#if></td>
            <td>${forumComment.createTime}</td>
            <td><a style="cursor: pointer" onclick="editStatus(${forumComment.id})">状态编辑</a>
                <a style="padding-left: 5px;cursor: pointer;" onclick="delComment(${forumComment.id},'${forumComment.content}')">删除</a>
        </tr>
    </#list>
    </tbody>
</table>


<script>
    /**
     * 编辑回帖状态
     * @param chapterId
     */
    function editStatus(id){
        art.dialog.open('/forumComment/edit?id='+id,{
            title: '编辑状态',
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
     * 删除回帖
     * @param catId
     */
    function delComment(id,name){
        if (confirm("确认要删除"+name+"?")) {
            $.post("/forumComment/delete", {id:id},
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
