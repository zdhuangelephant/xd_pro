<#include "/common/layout.ftl" />
<@htmlHead title="首页">
<link href="${rc.contextPath}/resources/js/starRating/star-rating.min.css" rel="stylesheet" type="text/css" />
<script src="${rc.contextPath}/resources/js/starRating/star-rating.min.js"></script>
</@htmlHead>
<@htmlBody bodyclass="page-content">
<style>
    .star-rating .caption{
        display: none!important;
    }
    .rating-xs {
        font-size: 1em!important;
    }
</style>

<div>
    <div style="width: 220px;float: left;position: static;padding-left: 0;padding-right: 0;">
        <div class="well well-sm">
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
                        cookieId: "treeview-black"
                    });
                });
            </script>
        ${chapterTree}
        </div>
    </div>

    <script>
        function showChapterKeywords(chapterId){
            var url = window.location.pathname+"?courseId="+courseId+"&chapterId="+chapterId;
            self.location=url;
        }
    </script>
    <div style="margin-left: 230px;">
        <p><span style="color: red;">所在章节</span>：${courseChapter.name}</p>
        <table class="table table-striped table-bordered table-hover">
            <thead>
            <tr>
                <th>选择</th>
                <th>关键词</th>
                <th>重要度</th>
            </tr>
            </thead>
            <tbody id="keywordsContainer">
            <#list keywordsList as keyword>
                <tr>
                    <td><input type="checkbox" value="${keyword.id}" class="keywordId" <#if keyword.isSelected==1>checked</#if> /></td>
                    <td>${keyword.name}</td>
                    <td><input  value="${keyword.importanceLevel}" type="number" class="rating" min=0 max=10 step=1 data-size="xs"></td>
                </tr>
            </#list>
            <#if keywordsList?size == 0 >
                <tr><td colspan="3" style="text-align: center;">没有相关关键词....</td></tr>
            </#if>
            </tbody>
        </table>
    </div>
</div>

<script>

    function showChapterKeywords(courseId,chapterId,obj){
        $("#chapterTree").find("a").css("color","");
        $(obj).css("color","red");

        $("#keywordsContainer").empty();
        $("#keywordsContainer").append("<tr><td colspan='3' style='text-align: center;'>正在加载中....</td></tr>");
        $.post("/courseKeyword/ajax_keywordsList", {courseId:courseId, chapterId:chapterId,resourceId:${resourceId},resourceType:${resourceType}},
            function(data){
                $("#keywordsContainer").empty();
                var tableString = "";
                var keywords = eval(data);
                for (var i=0;i<keywords.length;i++)
                {
                    var isChecked="";
                    if(keywords[i]['isSelected']==1){
                        isChecked="checked"
                    }
                    tableString = tableString + '<tr>'+
                            '<td><input type="checkbox" value="'+keywords[i]['id']+'" class="keywordId" '+isChecked+' /></td>'+
                '<td>'+keywords[i]['name']+'</td>'+
                '<td><input  value="'+keywords[i]['importanceLevel']+'" type="number" class="rating" min=0 max=10 step=1 data-size="xs"></td>'+
                '</tr>';
                }
                if(tableString==""){
                    tableString = "<tr><td colspan='3' style='text-align: center;'>没有相关关键词....</td></tr>";
                }
                $("#keywordsContainer").append(tableString);
                $('.rating').rating({
                    showClear:false,
                    disabled:true
                });

                $(".keywordId").click(function(){
                    var keywordId = $(this).val();
                    if($(this).is(":checked")){
                        $.post("/courseKeyword/addRelation", { keywordId:keywordId,resourceId:${resourceId},resourceType:${resourceType} },
                                function(data){
                                });
                    } else {
                        $.post("/courseKeyword/deleteRelation", { keywordId:keywordId,resourceId:${resourceId},resourceType:${resourceType} },
                                function(data){
                                });
                    }
                });
        });
    }


</script>

<script id="ratingScript" >

    $(".keywordId").click(function(){
        var keywordId = $(this).val();
        if($(this).is(":checked")){
            $.post("/courseKeyword/addRelation", { keywordId:keywordId,resourceId:${resourceId},resourceType:${resourceType} },
                function(data){
            });
        } else {
            $.post("/courseKeyword/deleteRelation", { keywordId:keywordId,resourceId:${resourceId},resourceType:${resourceType} },
                    function(data){
            });
        }
    });

    $('.rating').rating({
        showClear:false,
        disabled:true
    });
</script>

</@htmlBody>
