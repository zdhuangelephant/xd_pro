<#include "/common/layout.ftl" />
<@htmlHead title="首页">
</@htmlHead>
<@htmlBody bodyclass="page-content">

<link rel="stylesheet" href="${rc.contextPath}/resources/js/jqueryTreeView/jquery.treeview.css"/>
<script src="${rc.contextPath}/resources/js/jqueryTreeView/jquery.cookie.js" type="text/javascript"></script>
<script src="${rc.contextPath}/resources/js/jqueryTreeView/jquery.treeview.js" type="text/javascript"></script>
<script language="javascript" type="text/javascript">
    $(document).ready(function(){
        //给ID为browser的UL标签添加树状交互
        $("#catTree").treeview({
            persist: "location",
            unique: false
        });
        $("#chapterTree").treeview({
            persist: "location",
            unique: false
        });
    });
</script>

<div id="fuelux-wizard" data-target="#step-container">
    <ul class="wizard-steps">
        <li data-target="#step1" class="active">
            <span class="step">1</span>
            <span class="title">选择<#if resourceType==2>视频</#if><#if resourceType==3>文档</#if><#if resourceType==4>HTML5</#if><#if resourceType==7>音频</#if>资源</span>
        </li>

        <li data-target="#step2">
            <span class="step">2</span>
            <span class="title">编辑<#if resourceType==2>视频</#if><#if resourceType==3>文档</#if><#if resourceType==4>HTML5</#if><#if resourceType==7>音频</#if></span>
        </li>

        <li data-target="#step3">
            <span class="step">3</span>
            <span class="title">成功</span>
        </li>
    </ul>
</div>

<hr>

<div class="container-fluid" style="padding-left: 0px; padding-right: 0px;">
    <div>
    </div>
    <div style="margin-bottom: 10px;"></div>
    <div class="row">
        <div class="col-md-4 col-xs-4" style="padding-left: 0px; padding-right: 0px;">
            <div class="lanmu">
                    <table class="table table-striped table-bordered table-hover">
                        <thead>
                        <tr>
                            <th>章节</th>
                        </tr>
                        </thead>
                        <tr>
                            <td id="chapterContainer">${chapterTree}</td>
                        </tr>
                        </table>
                </div>
            </div>
        <div class="col-md-5 col-xs-5" style="padding-left: 0px; padding-right: 0px;">
            <div class="lanmu" style="background: none;padding-right: 10px;">
                <table class="table table-striped table-bordered table-hover" >
                    <thead>
                    <tr>
                        <th>
                            <#if resourceType==2||resourceType==8>视频</#if>
                            <#if resourceType==3>文档</#if>
                            <#if resourceType==4>HTML5</#if>
							<#if resourceType==7>音频</#if>
                        </th>
                        <th style="width: 50px;">操作</th>
                    </tr>
                    </thead>
                    <tbody id="resourceContainer">
                    ${resourceList}
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>

<style>
    .lanmu{
        padding-right:5px;
        padding-left:5px;
    }
</style>

<script>

    function showCourse(obj){
        var catId = $(obj).val();
        if(catId==-1){
            catId = 0;
        }
        $("#courseContainer").empty();
        $("#courseContainer").append("<tr><td colspan='2' style='text-align: center; color: red;'>正在加载中..</td></tr>");
        $("#chapterContainer").empty();
        $("#resourceContainer").empty();
        $.post("/courseResource/courseList", { catId:catId },
            function(data){
                if(data!=""){
                    $("#courseContainer").empty();
                    $("#courseContainer").html(data);
                } else {
                    $("#courseContainer").empty();
                    $("#chapterContainer").empty();
                    $("#resourceContainer").empty();
                    $("#courseContainer").append("<tr><td colspan='2' style='text-align: center; color: red;'>无相关课程..</td></tr>");
                    $("#chapterContainer").append("<span style='color: red;'>没有相关章节...<span>");
                    $("#resourceContainer").append("<tr><td colspan='2' style='text-align: center; color: red;'>没有相关资源..</td></tr>");
                }
            }
        );
    }

    function showChapter(courseId,obj){
        $("#chapterContainer").empty();
        $("#chapterContainer").append("<span style='color: red;'>正在加载中..</span>");
        $("#resourceContainer").empty();
        $.post("/courseResource/chapterList", { courseId:courseId },
                function(data){
                    if(data!="<ul id='chapterTree' class='filetree' ></ul>"){
                        $("#chapterContainer").empty();
                        $("#chapterContainer").html(data);
                    } else {
                        $("#chapterContainer").empty();
                        $("#chapterContainer").append("<span style='color: red;'>没有相关章节...<span>");
                    }
                    chapterTree();
                }
        );

        showResource(courseId,0);

        $("#courseContainer").find("a").css("color","");
        $("#chapterContainer").find("a").css("color","");
        $(obj).css("color","red");

    }

    function showResource(courseId,chapterId,obj){
        $("#resourceContainer").empty();
        $("#resourceContainer").append("<tr><td colspan='2' style='text-align: center;color: red;'>正在加载中..</td></tr>");
        $.post("/courseResource/resourceList", { courseId:courseId,chapterId:chapterId,resourceType:${resourceType}},
                function(data){
                    if(data!=""){
                        $("#resourceContainer").empty();
                        $("#resourceContainer").html(data);
                    } else {
                        $("#resourceContainer").empty();
                        $("#resourceContainer").append("<tr><td colspan='2' style='text-align: center; color: red;'>没有相关资源..</td></tr>");
                    }
                }
        );
        $("#chapterContainer").find("a").css("color","");
        $(obj).css("color","red");

    }

    function chapterTree(){
        $("#chapterTree").treeview({
            persist: "location",
            unique: false
        });
    }

    function apply(id,chapterId){
        var selectId = ${selectId!'0'};
        var productId = ${productId};
        var resourceType = ${resourceType!'2'};
        var resourceId = id;
        if(resourceType==2) {
            window.location.href = "/productItem/addVideoResource?productId=" + productId + "&resourceId=" + resourceId + "&chapterId=" + chapterId + "&selectId=" + chapterId;
        } else if(resourceType==3){
            window.location.href = "/productItem/addDocResource?productId=" + productId + "&resourceId=" + resourceId + "&chapterId=" + chapterId + "&selectId=" + chapterId;
        } else if(resourceType==4){
            window.location.href = "/productItem/addHtml5Resource?productId=" + productId + "&resourceId=" + resourceId + "&chapterId=" + chapterId + "&selectId=" + chapterId;
        }else if(resourceType==7){
            window.location.href = "/productItem/addAudioResource?productId=" + productId + "&resourceId=" + resourceId + "&chapterId=" + chapterId + "&selectId=" + chapterId;
        }
    }
</script>

</@htmlBody>
