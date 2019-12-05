<!DOCTYPE html>
<html lang="zh-CN">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>${forum.forumTitle}</title>
    <link rel="stylesheet" type="text/css" href="/resources/knowledge/forum/css/output.css">
  </head>
  <body>
    <div class="article">
      <h1 class="title">${forum.forumTitle}</h1>
      <div class="info">
          <div class="author-img">
            <img src="${forum.author.portrait}"/>
          </div>
          <p class="author">${forum.author.name}</p>
          <p class="release-date">${forum.createTime}</p>
          <div class="clearfix"></div>
      </div>
      <div class="media">
      	<#if forum>
			<#if forum.forumClassify == 2>
				<video src="${forum.forumMedia}" controls="" autobuffer="" width="640" height="480">您的浏览器不支持 video 标签。</video>
    		<#elseif forum.forumClassify == 3>
    			<audio src="${forum.forumMedia}" controls="controls">您的浏览器不支持 audio 标签。</audio>
    		</#if>
    	</#if>
      </div>
      <div class="content">
            ${forum.forumContent}
      </div>
      <div class="article-tab">
        标签：<span>${forum.forumTagName}</span>
      </div>
    </div>
  </body>
</html>