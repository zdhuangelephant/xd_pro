<#include "/common/layout.ftl" /> <@htmlHead title="首页"> </@htmlHead>
<@htmlBody bodyclass="page-content">

<ul class="nav nav-tabs" style="height: 45px;">

	<li class="pull-right"><a
		style="background: none; border: none; font-weight: bold; color: #2679b5;"
		href="#settings" aria-controls="settings" onclick="addAudio()"> <span
			class="glyphicon glyphicon-plus" aria-hidden="true"></span> 添加
	</a></li>
</ul>

<div style="margin-bottom: 10px;"></div>

<table class="table table-striped table-bordered table-hover">
	<thead>
		<tr>
			<th class="center" style="width: 50px;">ID</th>
			<th>标题</th>
			<th width="350">音频播放</th>
			<th width="110">资源封面</th>
			<th>描述</th>
			<th width="200">上传时间</th>
			<th style="width: 110px;">操作</th>
		</tr>
	</thead>
	<tbody>
		<#list audioList.result as audio>
		<tr>
			<td>${audio.forumId}</td>
			<td>${audio.forumTitle}</td>
			<td><audio src="${audio.forumMedia}" controls="controls"
					style="width: 100%">您的浏览器不支持 audio 标签。
				</audio></td>
			<td><img src="${audio.forumCover}"
				style="width: 60px; height: 60px;"></td>
			<td>${audio.forumContent}</td>
			<td>${audio.createTime}</td>
			<td>

				<div class="btn-group">
					<div class="dropdown">
						<button class="btn  btn-primary dropdown-toggle"
							style="border: 0px; width: 90px; padding: 2px 4px; font-size: 12px;"
							type="button" id="dropdownMenu2" data-toggle="dropdown"
							aria-haspopup="true" aria-expanded="true">
							操作 <span class="caret"></span>
						</button>
						<ul class="dropdown-menu" aria-labelledby="dropdownMenu2">
							<#if audio_index == 0 >
								<#if flag == 'isPush'>
									<li><a style="cursor: pointer;"
										onclick="pushAudio(${audio.forumId},'${audio.forumTitle}','${tag}')">全网推送</a>
									</li>
									<#if tag == 'xd_online'>
										<li><a style="cursor: pointer;"
											onclick="pushAudio(${audio.forumId},'${audio.forumTitle}','xd_pre_release')">预发布版推送</a>
										</li>
									</#if>	
								<#else>
								</#if>
							</#if>
							<li><a style="cursor: pointer;"
								onclick="downloadAudio('${audio.forumMedia}')">下载音频</a></li>
							<li><a style="cursor: pointer;"
								onclick="editAudio(${audio.forumId})">修改</a></li>
							<li><a style="cursor: pointer;"
								onclick="delAudio(${audio.forumId},'${audio.forumTitle}')">删除</a></li>
						</ul>
					</div>
				</div>
			</td>
		</tr>
		</#list>
	</tbody>
</table>
<@page totalCount="${audioList.totalCount}" pageNo="${audioList.pageNo}"
totalPage="${audioList.totalPage}" url=""> </@page>

<script>
	
	function downloadAudio(url) {
		window.open(url, '_blank');
	}
	
    /**
     * 编辑栏目
     * @param catId
     */
    function editAudio(id){
        art.dialog.open('/knowledge/school_audio/edit?forumId='+id,{
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
    function addAudio() {
        art.dialog.open('/knowledge/school_audio/add', {
            title: '添加',
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
     * 全网推送
     * @param catId
     */
    function delAudio(id,name){
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
        var url = window.location.pathname+"?page="+page;
        self.location=url;
    }
    
    //  pushAudio
		function pushAudio(id,name,tag){
	        if (confirm("确认要推送"+name+"?")) {
	            $.post("/knowledge/school_audio/pushForum", { forumId:id,typeId:2,tag:tag},
                    function(data){
                    	data=eval('(' + data + ')');
                        if(data.retCode==0){
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
</script>


</@htmlBody>
