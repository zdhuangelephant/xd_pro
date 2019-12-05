<#include "/common/layout.ftl" />
<@htmlHead title="登陆页">

<link href="${JS_PATH}jqueryTableTreeView/jquery.treetable.css" rel="stylesheet" type="text/css" />
<link href="${JS_PATH}jqueryTableTreeView/jquery.treetable.theme.default.css" rel="stylesheet" type="text/css" />
<script src="${JS_PATH}jqueryTableTreeView/jquery.treetable.js"></script>

<script type="text/javascript">

    $(document).ready(function(){
        $("#checkAll").click(function(){
            if($(this).is(":checked")){
                $("input[type='checkbox'][name='questionIds']").prop("checked", true);
            } else {
                $("input[type='checkbox'][name='questionIds']").prop("checked", false);
            }
        });
    });

    $(document).ready(function () {
        $("#dnd-example").treetable({
            expandable: true
        });

        showCourseQuestion();


    });

    var productQuestionIds = new Array();
    <#list productQuestions as productQuestion >
    productQuestionIds.push(${productQuestion.questionId});
    </#list>

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

    function showCourseQuestion(){
        $("#questionBody").empty();
        $("#questionBody").append("<tr><td colspan='7' style='text-align: center;color: red;'>正在查询中..</td></tr>");

        var chapterIds = "";
        $(":checkbox[name='menuid[]']:checked").each(function(i){
            chapterIds = chapterIds + $(this).val()+",";
        })
        var courseId = "${courseId}";
        var productId = "${productId}";
        var chapterId = ${chapterId};

        $.post("/question/ajax_list_by_chapters", { courseId:courseId , chapterIds: chapterIds,productId:productId},
                function(data){
                    var questions = eval(data);
                    var questionList = "";
                    for(var i=0;i<questions.length;i++){
                        var checked = "";
                        if(jQuery.inArray(questions[i]['id'], productQuestionIds)>=0){
                            checked = "checked";
                        }
                        var hasInOtherChapter = "";
                        if(questions[i]['relationProductQuestions']&&questions[i]['relationProductQuestions'].length>=1) {
                            for (var k = 0; k < questions[i]['relationProductQuestions'].length; k++) {
                                if (questions[i]['relationProductQuestions'][k]['chapterId'] != chapterId) {
                                    hasInOtherChapter = "true";
                                }
                            }
                        }
                        questionList = questionList + "<tr>";
                        if(hasInOtherChapter=="true"){
                            questionList = questionList + "<td></td>";
                        } else {
                            questionList = questionList + "<td><input type='checkbox' "+checked+" name='questionIds' value='"+questions[i]['id']+"' /></td>";
                        }
                        questionList = questionList +  "<td>"+questions[i]['id']+"</td>" +
                                "<td>"+questionTypes[questions[i]['questionType']]+"</td>" +
                                "<td>"+diffcultLevel[questions[i]['diffcultLevel']]+"</td>" +
                                "<td>"+cognitionLevel[questions[i]['cognitionLevel']]+"</td>" +
                                "<td>"+questions[i]['mdesc']+"</td>";
                        if(hasInOtherChapter=="true"){
                            questionList = questionList +"<td>已关联其他章节</td>";
                        } else {
                            questionList = questionList +"<td></td>";
                        }
                        questionList = questionList +   "</tr>";
                    }
                    if(questionList==""){
                        $("#questionBody").empty();
                        $("#questionBody").append("<tr><td colspan='7' style='text-align: center;color: red;'>查询无结果..</td></tr>");
                    } else {
                        $("#questionBody").empty();
                        $("#questionBody").append(questionList);
                    }
                });
    }

    /**
     * 选择框
     * @param obj
     */
    function checknode(obj) {
        var chk = $("input[type='checkbox']");
        var count = chk.length;
        var num = chk.index(obj);
        var level_top = level_bottom = chk.eq(num).attr('level')
        for (var i = num; i >= 0; i--) {
            var le = chk.eq(i).attr('level');
            if (eval(le) < eval(level_top)) {
                chk.eq(i).prop("checked", true);
                var level_top = level_top - 1;
            }
        }
        for (var j = num + 1; j < count; j++) {
            var le = chk.eq(j).attr('level');
            if (chk.eq(num).prop("checked") == true) {
                if (eval(le) > eval(level_bottom)) chk.eq(j).prop("checked", true);
                else if (eval(le) == eval(level_bottom)) break;
            }
            else {
                if (eval(le) > eval(level_bottom)) chk.eq(j).prop("checked", false);
                else if (eval(le) == eval(level_bottom)) break;
            }
        }
        showCourseQuestion();
    }
</script>
</@htmlHead>

<@htmlBody bodyclass="page-content">
    <form id="addForm" method="post" action="/productItem/editResourceRelation">

        <input type="hidden" name="chapterId" value="${chapterId}">
        <input type="hidden" name="productId" value="${productId}">
        <div class="container-fluid" style="padding: 0px;">
            <div class="row">
                <div class="col-md-4" style="padding-left: 0px; padding-right: 10px;">

                    <table cellspacing="0" id="dnd-example" class="table table-striped table-bordered table-hover treetable" >
                        <thead>
                        <tr>
                            <th>
                                <a style="cursor: pointer;"
                                   onClick="javascript:$('input[name=\'menuid[]\']').prop('checked', true);">全选</a>/
                                <a style="cursor: pointer;"
                                   onClick="javascript:$('input[name=\'menuid[]\']').prop('checked', false);">取消</a>
                            </th>
                        </tr>
                        </thead>
                        <tbody>
                        ${tree}
                        </tbody>
                    </table>

                </div>
                <div class="col-md-8" style="padding-left: 0px; padding-left: 10px;">

                    <table class="table table-striped table-bordered table-hover">
                        <thead>
                            <tr>
                                <th><input type="checkbox" id="checkAll" /></th>
                                <th>ID</th>
                                <th>类型</th>
                                <th>认知层次</th>
                                <th>难易程度</th>
                                <th style="width: 320px;">题干</th>
                                <th></th>
                            </tr>
                        </thead>
                        <tbody id="questionBody">
                        <tr>
                            <td colspan="7" style="text-align: center;color: red;">请选择栏目...</td>
                        </tr>
                        </tbody>
                    </table>

                </div>
            </div>
        </div>



    </form>
</@htmlBody>

