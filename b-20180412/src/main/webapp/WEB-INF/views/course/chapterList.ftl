<#include "/common/layout.ftl" />
<@htmlHead title="首页">
</@htmlHead>
<@htmlBody bodyclass="page-content">
<div class="page-header">
    <h1>
        章节
        <small>
            <i class="ace-icon fa fa-angle-double-right"></i>
            章节列表
        </small>
    </h1>
</div>

<form class="form-inline" style="margin-bottom: 10px;">
    <div class="form-group">
        <select name="courseId" class="form-control">
            <option value="0">请选择课程</option>
            <#list subjectList as subject>
                <option value="${subject.id}" <#if courseId == subject.id>selected</#if> >${subject.name}</option>
            </#list>
        </select>
    </div>
    <button type="submit" class="btn btn-primary" style="border: 0px; width: 90px;">搜索</button>
    <a class="btn btn-primary" style="border: 0px; width: 90px;margin-left: 10px;" onclick="order()" >排序</a>
    <#if courseId!=0>
        <a class="btn btn-primary pull-right" style="border: 0px; width: 90px; margin-left: 10px;" onclick="addChapter(${courseId})" >添加章节</a>
    </#if>
</form>

<style>
    .orderInput{
        width: 40px;
        text-align: center;
    }
</style>

<table class="table table-striped table-bordered table-hover">
    <thead>
    <tr>
        <th class="center" style="width: 50px;">
            ID
        </th>
        <th>排序</th>
        <th style="width: 150px;">中文序号</th>
        <th style="width: 300px;">名称</th>
        <th>描述</th>
        <th style="width: 320px;">操作</th>
    </tr>
    </thead>
    <tbody>
    ${tableTree}
    </tbody>
</table>

<script>
    /**
     * 编辑栏目
     * @param catId
     */
    function editChapter(catId){
        art.dialog.open('/courseChapter/edit?chapterId='+catId,{
            title: '编辑栏目',
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
    function addChapter(courseId){
        art.dialog.open('/courseChapter/add?courseId='+courseId,{
            title: '添加章节',
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
     * 添加栏目
     * @param catId
     */
    function addChildChapter(courseId,parentId){
        art.dialog.open('/courseChapter/add?courseId='+courseId+'&parentId='+parentId,{
            title: '添加章节',
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
     * 排序
     */
    function order(){
        var request = '';
        $(".orderInput").each(function(i){
            var value = $(this).val();
            var id = $(this).attr("id");
            var preValue = $(this).attr("preValue");
            if(value!=preValue){
                request = request + id + ":" + value + ";";
            }
        });
        if(request==''){
            alert("排序成功");
        } else {
            $.post("/courseChapter/order", { orders: request},
                    function(data){
                        alert("排序成功");
                        location.reload();
                    });
        }
    }

    /**
     * 删除栏目
     * @param catId
     */
    function delChapter(id,name){
        if (confirm("确认要删除"+name+"?")) {
            $.post("/courseChapter/delete", { chapterId:id},
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
