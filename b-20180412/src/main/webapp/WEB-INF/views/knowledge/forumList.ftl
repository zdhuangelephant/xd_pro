<#include "/common/layout.ftl" />
<@htmlHead title="首页">
</@htmlHead>
<@htmlBody bodyclass="page-content">
<div class="page-header">
    <h1>
        文章管理
        <small>
            <i class="ace-icon fa fa-angle-double-right"></i>
            文章列表
        </small>
    </h1>
</div>

<form class="form-inline" style="margin-bottom: 10px;">
    <div class="form-group">
    <ul class="nav nav-tabs" style="height: 45px;">
    	<li>
	    <div class="row" style="margin:2px">
		  <div style="margin-top: 5px;">
	    		<label style="color: #2679b5; display: auto;">标题</label>
		      <input style="height: 30px;" type="text" id="forumTitle" class="form-control" value="${forumTitle}" data-provide="typeahead" autocomplete="off" placeholder="请输入标题">
		  </div>
		</div>
	    </li>
	    <li>
	    <div class="row" style="margin:2px">
		  <div style="margin-top: 5px;">
	    	  <label style="color: #2679b5; display: auto;">作者</label>
		      <input style="height: 30px;" type="text" id="authorName" class="form-control" value="${authorName}" data-provide="typeahead" autocomplete="off" placeholder="请输入作者名">
		      <input type="hidden" id="authorId" class="form-control" value="${authorId}">
		  </div>
		</div>
	    </li>
	    <li>
	    	<a class="stext" style="background:none; border: none; font-weight: bold;color: #2679b5;">
	    		<label>分类</label>
	            <select id="forumType" name="forumType" style="width:160px;" >
	            	<option value=""></option>
	            	<#if forumType == 1>
	            	<option value="1" selected>公开课</option>
	            	<#else>
	            	<option value="1">公开课</option>
	            	</#if>
	            	<#if forumType == 2>
	            	<option value="2" selected>知识分享</option>
	            	<#else>
	            	<option value="2">知识分享</option>
	            	</#if>
	            </select>
        	</a>
	    </li>
	    <li>
	    	<a class="stext" style="background:none; border: none; font-weight: bold;color: #2679b5; " href="#settings" aria-controls="settings">
	    		<label>媒体类型</label>
	            <select id="forumClassify" name="forumClassify" style="width:160px;" >
	            	<option value=""></option>
	            	<#if forumClassify == 1>
	            	<option value="1" selected>文本</option>
	            	<#else>
	            	<option value="1">文本</option>
	            	</#if>
	            	<#if forumClassify == 2>
	            	<option value="2" selected>视频</option>
	            	<#else>
	            	<option value="2">视频</option>
	            	</#if>
	            	<#if forumClassify = 3>
	            	<option value="3" selected>音频</option>
	            	<#else>
	            	<option value="3">音频</option>
	            	</#if>
	            </select>
        	</a>
	    </li>
    </ul>
    </div>
    <a class="btn btn-primary" style="border: 0px; width: 90px; margin-left: 8px;" onclick="searchForum()" <a>搜索</a>
    <a class="btn btn-primary" style="border: 0px; width: 90px; margin-left: 8px;" onclick="create()" <a>添加</a></form>

  <#if forumList>
  	<table class="table table-striped table-bordered table-hover">
	    <thead>
	    <tr>
	        <th class="center" style="width: 50px;">
	            ID
	        </th>
	        <th style="width: 110px;">标题</th>
	        <th>地域</th>
	        <th>分类</th>
	        <th>媒体分类</th>
	        <th style="width: 200px;">作者姓名</th>
	        <th style="width: 180px;">资源封面</th>
	        <th>是否显示</th>
	        <th style="width: 110px;">更新时间</th>
	        <th style="width: 110px;">操作</th>
	    </tr>
	    </thead>
	    <tbody>
	        <#list forumList.result as forum>
	        <tr>
	            <td>${forum.forumId}</td>
	            <td>${forum.forumTitle}</td>
	            <td>${forum.regionName}</td>
	            <td>${forum.forumTypeDesc}</td>
	            <td>${forum.forumClassifyDesc}</td>
	            <td>${forum.author.name}</td>
	            <td><img src="${forum.forumCover}" style="width: 156px; height: 60px;" ></td>
	            <td>
	            <#if forum.status == 1>
	            	是
	            <#elseif forum.status == 0>
	            	否
	            </#if>
	            </td>
	            <td>${forum.updateTime}</td>
	            <td>
	
	                <div class="btn-group">
	                    <div class="dropdown">
	                        <button class="btn  btn-primary dropdown-toggle" style="border: 0px; width: 90px; padding: 2px 4px; font-size: 12px;" type="button" id="dropdownMenu2" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">
	                            操作
	                            <span class="caret"></span>
	                        </button>
	                        <ul class="dropdown-menu" aria-labelledby="dropdownMenu2">
								<#if (flag == 'isPush') && (forum.status == 1)>
	                        		<li><a style="cursor: pointer;" onclick="pushForum(${forum.forumId},'${forum.forumTitle}','${tag}')">全网推送</a></li>
	                        		<#if tag == 'xd_online'>
										<li><a style="cursor: pointer;"
											onclick="pushForum(${forum.forumId},'${forum.forumTitle}')，'xd_pre_release'">预发布版推送</a>
										</li>
									</#if>	
	                        	</#if>	                        	
	                            <li><a style="cursor: pointer;" onclick="edit('${forum.forumId}')">修改</a></li>
	                            <li><a style="cursor: pointer;" onclick="delForum(${forum.forumId},'${forum.forumTitle}')">删除</a></li>
	                        </ul>
	                    </div>
	                </div>
	            </td>
	        </tr>
	        </#list>
	    </tbody>
	</table>
	  <@page totalCount="${forumList.totalCount}" pageNo="${forumList.pageNo}" totalPage="${forumList.totalPage}" url="">
     </@page>
  <#else>
  	空
  </#if>
<script>

function pushForum(id,name,tag){
    if (confirm("确认要推送"+name+"?")) {
        $.post("/knowledge/forum/pushForum", { forumId:id,typeId:1,tag:tag},
                function(data){
                	data=eval('(' + data + ')');
                    if(data.retCode==0){
//                     	$(".dropdown").find("ul li:first-child").hidden();
                        alert("推送成功");
                        location.reload();
                    } else {
                        alert("推送失败");
                    }
                    location.reload();
                }
        );
    }
}


function searchForum() {
	var url = window.location.pathname;
	var forumTitle = $("#forumTitle").val();
	var authorId = $("#authorId").val();
	var authorName = $("#authorName").val();
	var forumType = $("#forumType").val();
	var forumClassify = $("#forumClassify").val();
	url+="?authorId="+authorId
		+ "&authorName="+base64encode(utf16to8(encodeURIComponent(authorName)))
		+ "&forumType="+forumType
		+ "&forumClassify="+forumClassify
		+ "&forumTitle="+base64encode(utf16to8(encodeURIComponent(forumTitle)));
    self.location=url;
}
function create() {
	window.open('/knowledge/forum/add',"_blank");
}
function edit(forumId) {
	window.open('/knowledge/forum/edit?forumId='+forumId,"_blank");
}
function delForum(id,name){
    if (confirm("确认要删除"+name+"?")) {
        $.post("/knowledge/school_audio/delete", { forumId:id},
                function(data){
                	data=eval('(' + data + ')');
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
		var forumTitle = $("#forumTitle").val();
		var authorId = $("#authorId").val();
		var authorName = $("#authorName").val();
		var forumType = $("#forumType").val();
		var forumClassify = $("#forumClassify").val();
        var url = window.location.pathname+"?authorId="+authorId
		+ "&authorName="+base64encode(utf16to8(encodeURIComponent(authorName)))
		+ "&forumType="+forumType
		+ "&forumClassify="+forumClassify
		+ "&forumTitle="+base64encode(utf16to8(encodeURIComponent(forumTitle)))
		+ "&page="+page;
        self.location=url;
    }
	/**
     * 编辑封面
     * @param catId
     */
    function modifyCover(forumId){
        art.dialog.open('/knowledge/forum/editCover?forumId='+forumId,{
            title: '编辑封面',
            width:600,
            height:400,
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
    $('#authorName').typeahead(
			{
				source : function(authorName, process) {
					// query是输入值
					$.post('/knowledge/author/search4Forum', {
						authorName : authorName
					}, function(data) {
						data = eval('(' + data + ')');
						if (data.retCode == 0) {
							process(data.authorList);
						}
					});
				},
				updater : function(item) {
					$('#authorId').val(
							item.replace(/<p value="(.+?)">(.+?)<\/p>/, "$1"));
					return item.replace(/<p value="(.+?)">(.+?)<\/p>/, "$2"); // 这里一定要return，否则选中不显示
				},
				items : 10, // 显示10条
				delay : 500
			// 延迟时间
			});
		$('#forumTitle').typeahead(
			{
				source : function(forumTitle, process) {
					// query是输入值
					$.post('/knowledge/forum/search4Forum', {
						forumTitle : forumTitle
					}, function(data) {
						data = eval('(' + data + ')');
						if (data.retCode == 0) {
							process(data.forumTitleList);
						}
					});
				},
				updater : function(item) {
					return item.replace(/<p value="(.+?)">(.+?)<\/p>/, "$2"); // 这里一定要return，否则选中不显示
				},
				items : 10, // 显示10条
				delay : 500
			// 延迟时间
			});
</script>
</@htmlBody>
