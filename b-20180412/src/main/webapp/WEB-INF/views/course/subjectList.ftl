<#include "/common/layout.ftl" />
<@htmlHead title="首页">
</@htmlHead>
<@htmlBody bodyclass="page-content">
<div class="page-header">
    <h1>
        课程
        <small>
            <i class="ace-icon fa fa-angle-double-right"></i>
            课程列表
        </small>
    </h1>
</div>

<form class="form-inline" style="margin-bottom: 10px;">
    <div class="form-group">
        <select name="catId" class="form-control">
            <option value="0">请选择分类</option>
        ${selectTree}
        </select>
    </div>
    <button type="submit" class="btn btn-primary" style="border: 0px; width: 90px;">搜索</button>
    <#if catId==0>
    <a class="btn btn-primary pull-right" style="border: 0px; width: 90px; margin-left: 10px;" onclick="addCourse()" >添加课程 </a>
    </#if>
    </form>

<table class="table table-striped table-bordered table-hover">
    <thead>
    <tr>
        <th class="center" style="width: 50px;">
            ID
        </th>
        <th style="width: 200px;">专业类别</th>
        <th style="width: 200px;">课程名称</th>
        <th>描述</th>
        <th style="width: 150px;">创建时间</th>
        <th style="width: 300px;">操作</th>
    </tr>
    </thead>
    <tbody>
    <#list subjectList as subject>
        <tr>
            <td>${subject.id}</td>
            <td>${subject.categoryName}</td>
            <td>${subject.name}</td>
            <td>${subject.detail}</td>
            <td>${subject.createTime}</td>
            <td><a style="cursor: pointer" onclick="editCourse(${subject.id})">修改</a>
                <a style="padding-left: 10px;cursor: pointer;" onclick="delCourse(${subject.id},'${subject.name}')">删除</a>
                <a style="padding-left: 10px;cursor: pointer;" href="/courseChapter/list?courseId=${subject.id}">章节管理</a>

                <div class="btn-group" style="margin-left: 10px">
                    <div class="dropdown">
                        <button class="btn  btn-primary dropdown-toggle" style="border: 0px; width: 90px; padding: 2px 4px; font-size: 12px;" type="button" id="dropdownMenu2" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">
                            资源管理
                            <span class="caret"></span>
                        </button>
                        <ul class="dropdown-menu" aria-labelledby="dropdownMenu2">
                            <li><a href="/question/list?courseId=${subject.id}">试题</a></li>
                            <li><a href="/courseVideo/list?courseId=${subject.id}">视频</a></li>
                            <li><a href="/courseAudio/list?courseId=${subject.id}">音频</a></li>
                            <li><a href="/courseDoc/list?courseId=${subject.id}">文档</a></li>
                            <li><a href="/courseHtml5/list?courseId=${subject.id}">Html5</a></li>
                        </ul>
                    </div>
                </div>
            </td>
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
        art.dialog.open('/course/edit?id='+id,{
            title: '编辑课程',
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
    function addCourse(){
        art.dialog.open('/course/add',{
            title: '添加课程',
            width:400,
            ok: function () {
                var iframe = this.iframe.contentWindow;
                if (!iframe.document.body) {
                    alert('iframe还没加载完毕呢')
                    return false;
                };
                var form = iframe.document.getElementById('addForm');
                chileloading();
                form.submit();
                return false;
            },
            cancelVal: '关闭',
            cancel: true,
            close: function () {
                childcloseloading();
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
            $.post("/course/delete", {id:id},
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
    
    
    /**
     * 遮罩层
     * 
     */
       function chileloading(){
         var sWidth,sHeight;  
         sWidth=$(window.parent.document)[0].body.offsetWidth;//浏览器工作区域内页面宽度
         sHeight=screen.height;//屏幕高度（垂直分辨率）
         var bgObj=$(window.parent.document)[0].createElement("div");//创建一个div对象（背景层）
         bgObj.setAttribute('id','bgDiv');
         bgObj.style.position="absolute";
         bgObj.style.top="0";
         bgObj.style.background="#000";
         bgObj.style.filter="Alpha(opacity=30)";
         bgObj.style.opacity="0.3";
         bgObj.style.left="0";
         bgObj.style.width=sWidth + "px";
         bgObj.style.height=sHeight + "px";
         bgObj.style.zIndex = "10000";
         var chidDiv=document.createElement("div");//创建一个div对象（背景层）
         chidDiv.setAttribute('id','chidDiv');
         chidDiv.style.position="absolute";
         chidDiv.style.top="40%";
         chidDiv.style.background="#000";
         chidDiv.style.left="50%";
         chidDiv.style.width=100 + "px";
         chidDiv.style.height=100 + "px";
         var bigImg = document.createElement("img");   
         bigImg.src="${rc.contextPath}/resources/images/loading.gif";  
         chidDiv.appendChild(bigImg);
         bgObj.appendChild(chidDiv);
         
         $(window.parent.document)[0].body.appendChild(bgObj);    
     }
    
      /**
     *关闭遮罩层
     */
    function childcloseloading(){
          $(window.parent.document).find("#bgDiv").remove();
     }
</script>

</@htmlBody>
