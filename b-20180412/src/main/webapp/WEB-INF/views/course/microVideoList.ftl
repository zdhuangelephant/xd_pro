<#include "/common/layout.ftl" />
<#include "/course/chapterListFrame.ftl" />
<@htmlHead title="首页">
</@htmlHead>
<@htmlBody bodyclass="page-content">

 <@chapterframe>

<ul class="nav nav-tabs">

    <#if courseId?if_exists>
        <#if courseId!=0>
            <#if chapterId!=0>
    <li class="pull-right">
        <a style="background:none; border: none; font-weight: bold;color:#2679b5; " href="#settings" aria-controls="settings"
           onclick="addaudio(${courseId},${chapterId})">
            <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
            添加
        </a>
    </li>
</#if>
</#if>
</#if>

<table class="table table-striped table-bordered table-hover">
    <thead>
    <tr>
        <th>
            <input type="checkbox" id="checkAll" />
        </th>
        <th class="center" style="width: 50px;">
            ID
        </th>
        <th style="width: 150px;">章节</th>
        <th style="width: 150px;">名称</th>
        <th>URL</th>
		<th>时长</th>
		<th>大小</th>
		<th>封面</th>
		<th>排序</th>
		<th>描述</th>
        <th style="width: 50px;">状态</th>
        <th style="width: 110px;">操作</th>
    </tr>
    </thead>
    <tbody>
    
    <#if pageList.result?? && (pageList.result?size > 0) >
	    <#list pageList.result as audio>
	        <tr>
	            <td>
	                <input class="inputcheckbox " name="ids" value="${audio.id}" type="checkbox">
	            </td>
	            <td>${audio.id}</td>
	            <td>${audio.chapterName}</td>
	            <td>${audio.name}</td>
				<td style="width: 300px; word-break:break-all; word-wrap:break-word;">${audio.url}</td>
				
	            <td>${audio.timeLengthMinute}分${audio.timeLengthSecond}秒</td>
				<td>${audio.size}M</td>
				<td>${audio.videoCover}</td>
				<td>${audio.listOrder}</td>
				
				<td>${audio.detail}</td>
	            <td><#if audio.status==99>通过<#else><a style="color: red;">待审核</a></#if></td>
	            <td>
	            	<div class="btn-group">
	                    <div class="dropdown">
	                        <button class="btn  btn-primary dropdown-toggle" style="border: 0px; width: 90px; padding: 2px 4px; font-size: 12px;" type="button" id="dropdownMenu2" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">
	                            操作
	                            <span class="caret"></span>
	                        </button>
	                        <ul class="dropdown-menu" aria-labelledby="dropdownMenu2">
	                            <li><a style="cursor: pointer;" onclick="delaudio(${audio.id},'${audio.name}')">删除</a></li>
	                            <li><a style="cursor: pointer;" onclick="editaudio(${audio.id},${audio.courseId})">修改</a></li>
	                            <li><a style="cursor: pointer;" onclick="setKeywords(${audio.id},${audio.courseId},${audio.chapterId})">关联关键词</a></li>
	                        </ul>
	                    </div>
	                </div>
	            </td>
	        </tr>
        </#list>
    </#if>
    </tbody>
</table>
	<li class="pull-right">
		<a style="background:none; border: none; font-weight: bold;color: #2679b5; padding: 5px 12px 15px; " href="#settings" aria-controls="settings"
		onclick="verify()">
		<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
		审核通过
		</a>
	</li>
</ul>
<#if pageList.result?? && (pageList.result?size > 0) >
	<@page totalCount="${pageList.totalCount}" pageNo="${pageList.pageNo}" totalPage="${pageList.totalPage}" url="">
    </@page>
</#if>

<script>

    $(document).ready(function(){
        $("#checkAll").click(function(){
            if($(this).is(":checked")){
                $("input[type='checkbox'][name='ids']").prop("checked", true);
            } else {
                $("input[type='checkbox'][name='ids']").prop("checked", false);
            }
        });
    });
    /**
     * 编辑栏目
     * @param catId
     */
    function editaudio(id,courseId){
        art.dialog.open('/microVideo/edit?audioId='+id+'&courseId='+courseId,{
            title: '编辑',
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
    function addaudio(courseId,chapterId) {
        art.dialog.open('/microVideo/add?courseId='+courseId+'&chapterId='+chapterId, {
            title: '添加音频',
            width: 600,
            ok: function () {
                var iframe = this.iframe.contentWindow;
                if (!iframe.document.body) {
                    alert('iframe还没加载完毕呢')
                    return false;
                }
                ;
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
    function delaudio(id,name){
        if (confirm("确认要删除"+name+"?")) {
            $.post("/microVideo/delete", { id:id},
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

    function toPage(page){
        var url = window.location.pathname+"?courseId=${courseId}&chapterId=${chapterId}&page="+page;
        self.location=url;
    }

    function verify(){
        var ids = "";
        $("input[name='ids']:checked").each(function() {
            ids = ids + $(this).val() + ";";
        });
        if(ids!=""){
            $.post("/courseAudio/verify", { ids:ids },
                    function(data){
                        alert("成功");
                        location.reload();
                    });
        } else {
            alert("请选择试题");
        }
    }

    /**
     * 设置关联关键词
     * @param resourceId
     * @param courseId
     */
    function setKeywords(resourceId,courseId,chapterId){
        art.dialog.open('/courseKeyword/keywordsMap?courseId='+courseId+'&chapterId='+chapterId+'&resourceType=2&resourceId='+resourceId, {
            title: '关联关键词',
            width: 600,
            height:400,
            cancelVal: '关闭',
            cancel: true
        });
    }
</script>


    </@chapterframe>

</@htmlBody>
