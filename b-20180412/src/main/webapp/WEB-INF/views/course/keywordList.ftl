<#include "/common/layout.ftl" />
<@htmlHead title="首页">
<link href="${rc.contextPath}/resources/js/starRating/star-rating.min.css" rel="stylesheet" type="text/css" />
<script src="${rc.contextPath}/resources/js/starRating/star-rating.min.js"></script>
</@htmlHead>
<@htmlBody bodyclass="page-content">

<style>
    .star-rating .caption{
        display: none!important;
    }
    .rating-xs {
        font-size: 1em!important;
    }
</style>


<div class="page-header">
    <h1>
        考点管理
        <small>
            <i class="ace-icon fa fa-angle-double-right"></i>
            列表
        </small>
    </h1>
</div>

<form class="form-inline" style="margin-bottom: 10px;">
    <input type="hidden" name="courseId" value="${courseId}" />
    <div class="form-group">
        <select name="chapterId" class="form-control">
            <option value="0">请选择章节</option>
            ${chapterTree}
        </select>
    </div>
    <button type="submit" class="btn btn-primary" style="border: 0px; width: 90px;">搜索</button>
    <#if chapterId!=0>
    <a class="btn btn-primary pull-right" style="border: 0px; width: 90px; margin-left: 10px;" onclick="addKeyword(${chapterId},${courseId})" >添加考点</a>
    <a class="btn btn-primary pull-right" style="border: 0px; width: 120px; margin-left: 10px;" onclick="batchAddKeyword(${chapterId},${courseId})" >批量添加考点</a>
    </#if>
</form>

<table class="table table-striped table-bordered table-hover">
    <thead>
    <tr>
        <th class="center" style="width: 50px;">
            ID
        </th>
        <th >章节</th>
        <th >考点</th>
        <th>重要程度</th>
        <th>描述</th>
        <th>创建时间</th>
        <th style="width: 100px;">操作</th>
    </tr>
    </thead>
    <tbody id="tableBody">
    <#list keywordList as keyword>
        <tr>
            <td>${keyword.id}<input type="hidden" value="${keyword.id}" name="id${keyword.id}" /></td>
            <td>${keyword.chapterName}</td>
            <td>
                ${keyword.name}
            </td>
            <td>
                <input  value="${keyword.importanceLevel}" type="number" class="rating" min=0 max=10 step=1 data-size="xs">
            </td>
            <td>
                ${keyword.detail}
            </td>
            <td>
                ${keyword.createTime}
            </td>
            <td>
                <a  style="cursor: pointer" onclick="editKeyword(${keyword.id},${courseId})">修改</a>
                <a  style="cursor: pointer; padding-left: 10px;" onclick="delKeyword(${keyword.id},'${keyword.name}')">删除</a>
            </td>
        </tr>
    </#list>
    </tbody>
</table>

<script>

    /**
     * 批量添加
     */
    function batchAddKeyword(chapterId,courseId){
        art.dialog.open('/courseKeyword/batchAdd?chapterId='+chapterId+"&courseId="+courseId,{
            title: '添加关键词',
            width:1000,
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
     * 添加栏目
     */
    function addKeyword(chapterId,courseId){
        art.dialog.open('/courseKeyword/add?chapterId='+chapterId+"&courseId="+courseId,{
            title: '添加关键词',
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
     * 编辑栏目
     * @param catId
     */
    function editKeyword(keywordId,courseId){
        art.dialog.open('/courseKeyword/edit?keywordId='+keywordId+"&courseId="+courseId,{
            title: '编辑关键词',
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
     * 删除栏目
     * @param catId
     */
    function delKeyword(id,name){
        if (confirm("确认要删除"+name+"?")) {
            $.post("/courseKeyword/delete", {id:id},
                    function(data){
                        data=JSON.parse(data);
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

    $('.rating').rating({
        showClear:false,
        disabled:true
    });
</script>

</@htmlBody>
