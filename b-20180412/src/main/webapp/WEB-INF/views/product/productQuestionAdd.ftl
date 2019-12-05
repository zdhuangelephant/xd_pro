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
        $(".filetree").treeview({
            persist: "location",
            unique: false
        });

        $("#chapterSelect").change(function(){
            var chapterId = $(this).val();
            showQuestion(${productId},chapterId);
        })

        $("#courseChapterSelect").change(function(){
            var chapterId = $(this).val();
            showCourseQuestion(${courseId},chapterId);
        })
    });


    var diffcultLevel=new Array();
    diffcultLevel[1]="A";
    diffcultLevel[2]="B";
    diffcultLevel[3]="C";

    var cognitionLevel = new Array();
    cognitionLevel[1] = "I";
    cognitionLevel[2] = "II";
    cognitionLevel[3] = "III";

    var questionTypes = new Array();
        <#list typeList as type>
        questionTypes[${type.id}] = "${type.typeName}";
        </#list>


    function showCourseQuestion(productId,chapterId){
        $("#courseQuestionBody").empty();
        $("#courseQuestionBody").append("<tr><td colspan='6' style='text-align: center;color: red;'>正在查询中..</td></tr>");
        $.post("/question/ajax_list", { courseId: productId, chapterId: chapterId},
                function(data){
                    var questions = eval(data);
                    var questionList = "";
                    for(var i=0;i<questions.length;i++){
                        questionList = questionList + "<tr><td>"+questions[i]['id']+"</td><td>"+questionTypes[questions[i]['questionType']]+"</td><td>"+diffcultLevel[questions[i]['diffcultLevel']]+"</td><td>"+cognitionLevel[questions[i]['cognitionLevel']]+"</td><td>"+questions[i]['mdesc']+"</td><td>添加</td></tr>";
                    }
                    if(questionList==""){
                        $("#courseQuestionBody").empty();
                        $("#courseQuestionBody").append("<tr><td colspan='6' style='text-align: center;color: red;'>查询无结果..</td></tr>");
                    } else {
                        $("#courseQuestionBody").empty();
                        $("#courseQuestionBody").append(questionList);
                    }
                });
    }

    function showQuestion(productId,chapterId){
        $("#questionBody").empty();
        $("#questionBody").append("<tr><td colspan='6' style='text-align: center;color: red;'>正在查询中..</td></tr>");
        $.post("/productQuestion/list", { productId: productId, chapterId: chapterId},
            function(data){
                var questions = eval(data);
                var questionList = "";
                for(var i=0;i<questions.length;i++){
                    questionList = questionList + "<tr><td>"+questions[i]['id']+"</td><td>"+questionTypes[questions[i]['questionType']]+"</td><td>"+diffcultLevel[questions[i]['diffcultLevel']]+"</td><td>"+cognitionLevel[questions[i]['cognitionLevel']]+"</td><td>"+questions[i]['questionDesc']+"</td><td>删除</td></tr>";
                }
                if(questionList==""){
                    $("#questionBody").empty();
                    $("#questionBody").append("<tr><td colspan='6' style='text-align: center;color: red;'>查询无结果..</td></tr>");
                } else {
                    $("#questionBody").empty();
                    $("#questionBody").append(questionList);
                }
        });
    }
</script>

<div class="container-fluid" style="padding-left: 0px; padding-right: 0px;">
    <div class="row">
        <div class="col-md-6" style="padding-left: 0px; padding-right: 25px;">
            <select style="width: 100%; margin-bottom: 10px;" id="courseChapterSelect">
                <option value="0">请选择栏目</option>
            ${courseChapterTree}
            </select>
            <table class="table table-striped table-bordered table-hover">
                <thead>
                <tr>
                    <th>ID</th>
                    <th>类型</th>
                    <th>难度</th>
                    <th>认知层次</th>
                    <th>题干</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody id="courseQuestionBody">

                </tbody>
            </table>
        </div>
        <div class="col-md-6" style="padding-left: 25px; padding-right: 0px;">
            <select style="width: 100%;margin-bottom: 10px;" id="chapterSelect">
                <option value="0">请选择栏目</option>
            ${chapterTree}
            </select>
            <table class="table table-striped table-bordered table-hover">
                <thead>
                <tr>
                    <th>ID</th>
                    <th>类型</th>
                    <th>难度</th>
                    <th>认知层次</th>
                    <th>题干</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody id="questionBody">

                </tbody>
            </table>

        </div>
    <div>
</div>

</@htmlBody>
