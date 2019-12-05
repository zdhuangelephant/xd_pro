<#include "/common/layout.ftl" />
<@htmlHead title="首页">
</@htmlHead>
<@htmlBody bodyclass="page-content">
<div class="page-header">
    <h1>
		
        <#if courseId?if_exists&&courseId!=0>
        <#list subjectList as subject>
            <#if courseId == subject.id>${subject.name}</#if>
        </#list>
        <#else>
            请选择课程
        </#if>
    </h1>
</div>

<div>
    <div style="width: 300px;float: left;position: static;padding-left: 0;padding-right: 0;">
        <div class="well well-sm">
            <form class="form-inline" style="margin-bottom: 10px;">
                <div class="form-group">
                 <input type="hidden" id="productId" name="productId" value="${productId}">
                    <input type="text" id="courseName" class="form-control" value="${courseName}" data-provide="typeahead" autocomplete="off" placeholder="待关联课程资源名称">
                    <input type="hidden" id="courseId" name="courseId" class="form-control" value="${courseId}">
                </div>
                <button type="submit" class="btn btn-primary" style="border: 0px; width: 60px;">搜索</button>
                <a class="btn btn-primary" style="border: 0px; width: 60px;" onclick="chooseAll(true)" >全选</a>
                 <button  class="btn btn-primary"  onclick="chooseAll(false)" style="border: 0px; width: 70px;">全不选</button>
            </form>
            <link rel="stylesheet" href="${rc.contextPath}/resources/js/jqueryTreeView/jquery.treeview.css"/>
            <script src="${rc.contextPath}/resources/js/jqueryTreeView/jquery.cookie.js" type="text/javascript"></script>
            <script src="${rc.contextPath}/resources/js/jqueryTreeView/jquery.treeview.js" type="text/javascript"></script>
            <script language="javascript" type="text/javascript">
                $(document).ready(function(){
                    //给ID为browser的UL标签添加树状交互
                    $("#chapterTree").treeview({
                        persist: "cookie",
                        unique: true,
                        collapsed: true,
                        cookieId: "treeview-black",
                    });
                });
            </script>
        ${tableTree}
        </div>
    </div>

    <script>
      
    </script>
</div>
<script>
  function showChapterResource(courseId,chapterId){
            var url = window.location.pathname+"?courseId="+courseId+"&chapterId="+chapterId+"&productId=${productId}";
            self.location=url;
        }
   function change(parentNode){
  		 var checkboxArr = document.getElementsByName(parentNode.id);
  		  for(var i=0;i<checkboxArr.length;i++)   
   			 {   
    			  checkboxArr[i].checked = parentNode.checked;   
    		}   
  		
	 }
  
	 function chooseAll(flag){
 		var checkboxArr=document.getElementsByTagName("input");
 		for(var i=0;i<checkboxArr.length;i++)   
   			 {   
   			     if(flag==true){
    			  	checkboxArr[i].checked = true;
    			  	 } 
				 else{
    			  	 checkboxArr[i].checked = false;
    			  	 }
    		} 
	 }
    
		
	$('#courseName').typeahead(
		{
			source : function(courseName, process) {
				// query是输入值
				$.post('/product/search_subject_name', {
					resourceSubjectName : courseName
				}, function(data) {
					data = eval('(' + data + ')');
					if (data.retCode == 0) {
						process(data.subjectNameList);
					}
				});
			},
			updater : function(item) {
				$('#courseId').val(
						item.replace(/<p value="(.+?)">(.+?)<\/p>/, "$1"));
				return item.replace(/<p value="(.+?)">(.+?)<\/p>/, "$2"); // 这里一定要return，否则选中不显示
			},
			items : 10, // 显示10条
			delay : 500
		// 延迟时间
		});
</script>


</@htmlBody>
