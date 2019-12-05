<#macro chapterframe>

<div class="page-header">
    <h1>

        <#if courseId?if_exists&&courseId!=0>
        <#list subjectList as subject>
            <#if courseId == subject.id>${subject.name}</#if>
        </#list>
        <small>
            <i class="ace-icon fa fa-angle-double-right"></i>
        ${chapter.name}
        </small>
        <#else>
            请选择课程
        </#if>
    </h1>
        <a id="batchImport"  style="float:right;background:none; border: none; font-weight: bold;color: #2679b5; " href="#frog" 
            aria-controls="frog"  data-course="" onclick="validatedIsChooseCourse()">
            <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
        批量导入  
        </a>
    
</div>

<div>
    <div style="width: 220px;float: left;position: static;padding-left: 0;padding-right: 0;">
        <div class="well well-sm">
            <form class="form-inline">
                <div class="form-group" style="margin-bottom: 10px;">
                    <select onchange="nowSubmitFun()" name="courseId" class="form-control" style="overflow:hidden;width:200px;text-overflow:ellipsis;white-space:nowrap">
                        <option value="0">请选择课程</option>
                        <#list subjectList as subject>
                            <option value="${subject.id}" <#if courseId == subject.id>selected</#if> >${subject.name}</option>
                        </#list>
                    </select>
                </div>
                <button id="nowSubmit" type="submit" class="btn btn-primary" style="border: 0px; width: 60px;">搜索</button>
            </form>
            <link rel="stylesheet" href="${rc.contextPath}/resources/js/jqueryTreeView/jquery.treeview.css"/>
            <script src="${rc.contextPath}/resources/js/jqueryTreeView/jquery.cookie.js" type="text/javascript"></script>
            <script src="${rc.contextPath}/resources/js/jqueryTreeView/jquery.treeview.js" type="text/javascript"></script>
            
           <script src="${rc.contextPath}/resources/dist/layui.js?${timeStamp}"></script>
    	   <script src="${rc.contextPath}/resources/course/common.js?1"></script>
           <script src="${rc.contextPath}/resources/course/exam.js?1${timeStamp}"></script>
    
            
            <script language="javascript" type="text/javascript">
                $(document).ready(function(){
                    //给ID为browser的UL标签添加树状交互
                    $("#chapterTree").treeview({
                        persist: "cookie",
                        unique: true,
                        collapsed: true,
                        cookieId: "treeview-black"
                    });
                });
            </script>
        ${tableTree}
        </div>
    </div>

    <script>
        function showChapterResource(courseId,chapterId){
            var url = window.location.pathname+"?courseId="+courseId+"&chapterId="+chapterId;
            self.location=url;
        }
        function nowSubmitFun(){
           $("#nowSubmit").click();
        }
    </script>
    <div style="margin-left: 230px;">
        <#nested>
    </div>
</div>

</#macro>


