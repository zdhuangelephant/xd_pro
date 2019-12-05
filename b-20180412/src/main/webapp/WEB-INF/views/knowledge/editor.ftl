<#include "/common/form.ftl">
<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="apple-mobile-web-app-capable" content="yes"/>
    <meta name="full-screen" content="yes"/>
    <meta name="screen-orientation" content="portrait"/>
    <meta name="x5-fullscreen" content="true"/>
    <meta name="360-fullscreen" content="true"/>
    <title>编辑器</title>
    <link rel="stylesheet" href="/resources/knowledge/forum/css/bootstrap.min.css">
    <link rel="stylesheet" href="/resources/knowledge/forum/css/fileinput.min.css" media="all"/>
    <link rel="stylesheet" href="/resources/knowledge/forum/css/simditor.css"/>
    <link rel="stylesheet" href="/resources/knowledge/forum/css/simditor-mention.css"/>
    <link rel="stylesheet" href="/resources/knowledge/forum/css/simditor-html.css"/>
    <link rel="stylesheet" href="/resources/knowledge/forum/css/metro.css"/>
    <link rel="stylesheet" href="/resources/knowledge/forum/css/process.css">
    <link rel="stylesheet" href="/resources/knowledge/forum/css/output.css">
    <link rel="stylesheet" href="/resources/knowledge/forum/css/uploadImage.css">
    <link rel="stylesheet" href="/resources/knowledge/forum/css/index-base.css">
    <link rel="stylesheet" href="/resources/knowledge/forum/css/index-left.css">
    <link rel="stylesheet" href="/resources/knowledge/forum/css/index-mid.css">
    <link rel="stylesheet" href="/resources/knowledge/forum/css/index-right.css">
    <link rel="stylesheet" href="${rc.contextPath}/resources/assets/css/mycss/ace.min.css"/>
    <link rel="stylesheet" href="${rc.contextPath}/resources/assets/css/mycss/font-awesome.min.css"/>
    <style>
    	.forumCover
		{
			width: 156px;
			height: 60px;
		}
    </style>
</head>

<body>
<div class="mainBody">
    <div class="mid">
        <!--<p>显示区</p>-->
        <div class="iphone">
            <div class="screen" id="screen">
                <div class="main" id="stage">
                    <div id="page1" class="article">
                    	<div class="media">
                    		<#if forum && action == 'doEdit'>
                    			<#if forum.forumClassify == 2>
                    				<video src="${forum.forumMedia}" controls="controls" style="width:100%;height:100%">您的浏览器不支持 video 标签。</video>
                        		<#elseif forum.forumClassify == 3>
                        			<audio src="${forum.forumMedia}" controls="controls" style="width:100%">您的浏览器不支持 audio 标签。</audio>
                        		</#if>
                        	</#if>
                    	</div>
                        <div id="forumContent" class="content">
                        	<#if forum && action == 'doEdit'>
                        		${forum.forumContent}
                        	<#else>
                        		<div class="point yy-click auto-read" yy-type="richText">
							        <blockquote><p><i>坚持，几乎是我们每天都挂在嘴边的词，但知易行难，如果真正做到了，很多事情都会做成。</i></p></blockquote>
							          <p>读者和我聊天，说他写作一段时间了但没有进展，很多时候都不知道如何下笔。我说很多事情都是慢慢积累的，尤其是写作，不可能一蹴而就，想写好，唯有坚持。</p>
							          <p><b>标题1</b></p>
							          <p>坚持，几乎是我们每天都挂在嘴边的词，但知易行难，如果真正做到了，很多事情都会做成。</p>
							          <ol>
							            <li>随时随地发现和创作内容</li>
							            <li>每天为你精选好文</li>
							            <li>第一时间收到作者文章更新</li>
							          </ol>
							          <p>很多时候，我们都羡慕别人，当看到别人取得很好的成果时，我们陶醉在幻想里，觉得自己和别人没差多少，也可以做到。热血沸腾，立马去做，但往往过两天就放弃了。</p>
							          <p><b>标题2</b></p>
							          <p>因为别人是别人，你是你，别人能抗住风雨然后日积月累，而你遇到一点挫折，就立刻丢盔卸甲。还没上战场，就做了逃兵，这样的行为，真的，没必要再评价。</p>
							          <ul>
							            <li>随时随地发现和创作内容</li>
							            <li>每天为你精选好文</li>
							            <li>第一时间收到作者文章更新</li>
							          </ul>
							          <p>你总是这样，每天在幻想速成的方法，你嘲笑那些日复一日做苦工的人，但最后成功的，往往就是这些人。反观你自己，遇到一点压力就把自己想成不堪重负的样子，碰到一点不确定性就把前途描摹成黯淡无光的样子，其实说白了，都是为你自己的懦弱而找的借口。</p>
							          <p>这是最low，最拙劣的借口，因为你只能骗自己玩，最后挫败感最大的，是你自己。</p>
							    </div>
                        	</#if>
						</div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="right">
    	<input type="hidden" id="forumId" class="form-control" value="${forum.forumId}">
    	<div class="row" style="margin:5px">
		  <div class="col-lg-6">
		    <div class="input-group">
		     <div class="input-group-btn">
		        <button type="button" class="btn btn-default" aria-haspopup="true" aria-expanded="false">标题</button>
		      </div>
		      <input type="text" id="forumTitle" class="form-control" value="${forum.forumTitle}" placeholder="标题-500字符以内"> 
		    </div>
		  </div>
		</div>
		<div class="row" style="margin:5px">
		  <div class="col-lg-6">
		    <div class="input-group">
		     <div class="input-group-btn">
		        <button type="button" class="btn btn-default" aria-haspopup="true" aria-expanded="false">标签</button>
		      </div>
		      <input type="text" id="forumTagName" class="form-control" value="${forum.forumTagName}" data-provide="typeahead" autocomplete="off" placeholder="请输入标签">
		      <input type="hidden" id="forumTag" class="form-control" value="${forum.forumTag}">
		    </div>
		  </div>
		</div>
		<div class="row" style="margin:5px">
		  <div class="col-lg-6">
		    <div class="input-group">
		      <div class="input-group-btn">
		        <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">地域 <span class="caret"></span></button>
		        <ul class="dropdown-menu dropdown-menu-right">
		          <#list regionList as region>
				    <li><a href="#" class="dropdown-point" showPanel="regionName" valuePanel="region" value="${region.module}">${region.moduleName}</a></li>
				  </#list>
		        </ul>
		      </div>
		      <input type="text" id="regionName" class="form-control" value="${forum.regionName}">
		      <input type="hidden" id="region" class="form-control" value="${forum.region}">
		    </div>
		  </div>
		</div>
    	<div class="row" style="margin:5px">
		  <div class="col-lg-6">
		    <div class="input-group">
		     <div class="input-group-btn">
		        <button type="button" class="btn btn-default" aria-haspopup="true" aria-expanded="false">作者</button>
		      </div>
		      <input type="text" id="authorName" class="form-control" value="${forum.author.name}" data-provide="typeahead" autocomplete="off" placeholder="请输入作者名">
		      <input type="hidden" id="authorId" class="form-control" value="${forum.authorId}">
		    </div>
		  </div>
		</div>
		<div class="row" style="margin:5px">
		  <div class="col-lg-6">
		    <div class="input-group">
		      <div class="input-group-btn">
		        <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">分类 <span class="caret"></span></button>
		        <ul class="dropdown-menu dropdown-menu-right">
		          <li><a href="#" class="dropdown-point" showPanel="forumTypeDesc" valuePanel="forumType" value="1">公开课</a></li>
		          <li><a href="#" class="dropdown-point" showPanel="forumTypeDesc" valuePanel="forumType" value="2">知识分享</a></li>
		        </ul>
		      </div>
		      <input type="text" id="forumTypeDesc" class="form-control" value="${forum.forumTypeDesc}">
		      <input type="hidden" id="forumType" class="form-control" value="${forum.forumType}">
		    </div>
		  </div>
		</div>
		<div class="row" style="margin:5px">
		  <div class="col-lg-6">
		    <div class="input-group">
		      <div class="input-group-btn">
		        <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">媒体类型 <span class="caret"></span></button>
		        <ul class="dropdown-menu dropdown-menu-right">
		          <li><a href="#" class="dropdown-point" showPanel="forumClassifyDesc" valuePanel="forumClassify" value="1">文本</a></li>
		          <li><a href="#" class="dropdown-point" showPanel="forumClassifyDesc" valuePanel="forumClassify" value="2")">视频</a></li>
		          <li><a href="#" class="dropdown-point" showPanel="forumClassifyDesc" valuePanel="forumClassify" value="3")">音频</a></li>
		        </ul>
		      </div>
		      <input type="text" id="forumClassifyDesc" class="form-control" value="${forum.forumClassifyDesc}">
		      <input type="hidden" id="forumClassify" class="form-control" value="${forum.forumClassify}">
		    </div>
		  </div>
		</div>
		<div class="row" style="margin:5px">
		  <div class="col-lg-6">
		    <div class="input-group">
		      <div class="input-group-btn">
		        <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" id="modifyMedia">媒体资源 </button>
		      </div>
		      <input type="text" id="forumMedia" class="form-control" disabled="disabled" value="${forum.forumMedia}">
		    </div>
		  </div>
		</div>
		<div class="row" style="margin:5px">
		  <div class="col-lg-6">
		    <div class="input-group">
		      <div class="input-group-btn">
		        <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" id="forumCoverButton">封面图片 </button>
		      </div>
		      <div class="input-group-btn">
		      <img class="forumCover" src="${forum.forumCover}" alt="封面图片" style="width:156px;height:60px">
		      </div>
		      <input type="hidden" id="forumCover" class="form-control" value="${forum.forumCover}">
		    </div>
		  </div>
		</div>
		<div class="row" style="margin:5px">
		  <div class="col-lg-6">
		    <div class="input-group">
		      <div class="input-group-btn">
		        <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">是否展示<span class="caret"></span></button>
		        <ul class="dropdown-menu dropdown-menu-right">
		          <li><a href="#" class="dropdown-point" showPanel="statusDesc" valuePanel="status" value="1">是</a></li>
		          <li><a href="#" class="dropdown-point" showPanel="statusDesc" valuePanel="status" value="0")">否</a></li>
		        </ul>
		      </div>
		      <#if forum && action == 'doEdit'>
                        		<input type="text" id="statusDesc" class="form-control" value="${forum.statusDesc}">
			      <input type="hidden" id="status" class="form-control" value="${forum.status}">
                        	<#else>
                        		<input type="text" id="statusDesc" class="form-control" value="否">
			      <input type="hidden" id="status" class="form-control" value="0">
                        	</#if>
		    </div>
		  </div>
		</div>
        <p class="editor-tit">操作区</p>
		<span class="img-size">建议上传图片大小：666*666</span>
        <div class="operationArea">
            <ul class="choose">
                <li class="richText on" data-type="richText">文本</li>
            </ul>
            <div class="rightMain">
                <div class="richText-box">
                    <textarea id="editor" placeholder="这里输入内容"></textarea>
                </div>
            </div>
        </div>
        <div class="btnArea">
            <div class="btnGroup">
                <div id="enterBtn" class="Btn_common">保存元素</div>
                <div id="saveBtn" class="Btn_common">确定</div>
                <div id="showBtn" class="Btn_common">预览</div>
            </div>
        </div>
    </div>
</div>
<script>
	var action = '${action}', uploadToken = '${uploadToken}';
</script>
<script type="text/javascript" src="/resources/knowledge/forum/js/jquery-2.1.1.min.js"></script>
<script type="text/javascript" src="${rc.contextPath}/resources/js/util.js"></script>
<script type="text/javascript" src="/resources/knowledge/forum/js/jquery.form.js"></script>
<script type="text/javascript" src="/resources/knowledge/forum/js/xd.common-1.0.0.js"></script>
<script type="text/javascript" src="/resources/knowledge/forum/js/bootstrap.min.js"></script>
<script type="text/javascript" src="/resources/knowledge/forum/js/bootstrap-typeahead.js"></script>
<script type="text/javascript" src="/resources/knowledge/forum/js/TweenMax.min.js"></script>
<script type="text/javascript" src="/resources/knowledge/forum/js/jquery.ztree.all-3.5.min.js"></script>
<script type="text/javascript" src="/resources/knowledge/forum/js/beautify-html.js"></script>
<script type="text/javascript" src="/resources/knowledge/forum/simditor/module.js"></script>
<script type="text/javascript" src="/resources/knowledge/forum/simditor/hotkeys.js"></script>
<script type="text/javascript" src="/resources/knowledge/forum/simditor/uploader.js"></script>
<script type="text/javascript" src="/resources/knowledge/forum/simditor/simditor.js"></script>
<script type="text/javascript" src="/resources/knowledge/forum/simditor/simditor-mention.js"></script>
<script type="text/javascript" src="/resources/knowledge/forum/simditor/simditor-html.js"></script>
<script type="text/javascript" src="/resources/knowledge/forum/simditor/upload.js"></script>
<script type="text/javascript" src="/resources/knowledge/forum/simditor/myatbutton.js"></script>
<script type="text/javascript" src="/resources/knowledge/forum/js/index.js?attr=1"></script>
<script src="${rc.contextPath}/resources/js/artdialog/artDialog.js?skin=blue"></script>
<script src="${rc.contextPath}/resources/js/artdialog/plugins/iframeTools.js"></script>
<@fileUpload></@fileUpload>
<script type="text/javascript">
	 $("#forumTitle").keyup(function(){
		    var title = $("#forumTitle").val();
		    if(title.length > 500){
		    	alert("title 不可以超过500个字符")
		    }
	 });
</script>
</body>
</html>
