<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <meta name="viewport" content="initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
    <meta name="apple-mobile-web-app-capable" content="yes"/>
    <meta name="full-screen" content="yes"/>
    <meta name="screen-orientation" content="portrait"/>
    <meta name="x5-fullscreen" content="true"/>
    <meta name="360-fullscreen" content="true"/>
    <title>编辑器</title>
    <link rel="stylesheet" href="/resources/editor/css/simditor.css"/>
    <link rel="stylesheet" href="/resources/editor/css/simditor-mention.css"/>
    <link rel="stylesheet" href="/resources/editor/css/index.css">
    <link rel="stylesheet" href="/resources/editor/css/output.css">
    <script type="text/javascript">
        var id = '${id}', name = '${title}', action='${action}', uploadToken = '${uploadToken}';
    </script>
    <script type="text/javascript" src="/resources/knowledge/forum/js/jquery-2.1.1.min.js"></script>
    <script type="text/javascript" src="${rc.contextPath}/resources/js/util.js"></script>
    <script type="text/javascript" src="/resources/knowledge/forum/simditor/module.js"></script>
    <script type="text/javascript" src="/resources/knowledge/forum/simditor/hotkeys.js"></script>
    <script type="text/javascript" src="/resources/knowledge/forum/simditor/uploader.js"></script>
    <script type="text/javascript" src="/resources/knowledge/forum/simditor/simditor.js"></script>
    <script type="text/javascript" src="/resources/knowledge/forum/simditor/simditor-mention.js"></script>
    <script type="text/javascript" src="/resources/knowledge/forum/simditor/upload.js"></script>
    <script type="text/javascript" src="/resources/knowledge/forum/simditor/myatbutton.js"></script>
    <script type="text/javascript" src="/resources/editor/js/index.js"></script>
</head>
<body>
<div class="mainBody">
    <div class="mid">
        <!--<p>显示区</p>-->
        <div class="iphone">
            <div class="screen" id="screen">
                <div class="main" id="stage">
                    <div id="page">
                         <div class="content">
                             <div class="title"><span class="yy-click" yy-type="content">关于教育改革</span></div>
                             <div class="time"><span class="yy-click" yy-type="content">2012年10月15日23:20</span></div>
                             <div class="character"><span class="yy-click" yy-type="richText">
                                 教化培育，以现有的经验、学识推敲于人，为其解释各种现象、问题或行为，其根本是以人的一种相对成熟或更改的思维来认知对待，让事物得以接近其最根本的存在
                             </span></div>
                         </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="right">
        <div id="coursewarename">
            公告名称 <input type="text" id="courseName" onkeyup="changeId(this.value)">
        </div>
        <div >
            	应用类型 <select name="moduleId" id="moduleId">
            	<option value="-1">--请选择--</option>
            	<option value="1">慕享</option>
            	<option value="2">自考君</option>
            	<option value="3">小逗考典</option>
            	</select>
        </div>
        <p>操作区</p>

        <div>
            <ul class="choose">
                <li class="content on" data-type="content">外链地址</li>
                <li class="richText" data-type="richText">富文本编辑</li>
            </ul>
            <div class="rightMain">
                <div class="contentBox">
                    <div>外链地址:<input id="contentInput"></div>
                </div>
                <div class="richTextBox">
                    <textarea id="editor" placeholder="这里输入内容"></textarea>
                </div>
            </div>

            <!--<div>内容or图片(0内容，1图片)：<input id="typeInput"></div>-->
            <!--<div>内容:<input id="contentInput"></div>-->
            <!--<div>图片：<input id="imageInput"></div>-->

            <!--<div> width:<input id="widthInput"></div>-->
            <!--<div> height:<input id="heightInput"></div>-->
            <!--<div> top:<input id="topInput"></div>-->
            <!--<div>left:<input id="leftInput"></div>-->
        </div>
        <div id="enterBtn">确定</div>
        <div id="saveBtn">保存</div>
    </div>
</div>
</body>
</html>
