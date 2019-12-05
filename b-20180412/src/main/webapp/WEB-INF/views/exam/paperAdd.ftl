<#include "/common/layout.ftl" />
<@htmlHead title="首页">
</@htmlHead>
<@htmlBody bodyclass="page-content">

<link rel="stylesheet" href="${rc.contextPath}/resources/js/jqueryTreeView/jquery.treeview.css"/>
<script src="${rc.contextPath}/resources/js/jqueryTreeView/jquery.cookie.js" type="text/javascript"></script>
<script src="${rc.contextPath}/resources/js/jqueryTreeView/jquery.treeview.js" type="text/javascript"></script>
<script language="javascript" type="text/javascript">

    $(document).ready(function(){
        $("#checkAll").click(function(){
            if($(this).is(":checked")){
                $(".question").each(function(){
                    $(this).prop("checked", true);
                    addQuestion($(this).val(),$(this).attr("data-type"));
                });
            } else {
                $(".question").each(function(){
                    $(this).prop("checked", false);
                    removeQuestion($(this).val(),$(this).attr("data-type"));
                });
            }
        });
    });

    $(document).ready(function(){
        $("#submitButton").click(function(){
            submitValid();
            $.post("/examPaper/doAdd", $("#addForm").serialize(),function(data){
                alert("添加成功");
                art.dialog.close();
            });
        });
        $("#resourceList").change(function(){
            showQuestion($(this).val());
        });
    });

    function submitValid(){
        var questionids = "";
        $(".paperQuestion").each(function(i){
            questionids = questionids +$(this).val()+",";
        });
        $("#quesIds").val(questionids);
        if(questionids == ""){
            alert("请先选择来源");
        }
    }

    function addQuestion(id,type){
        var td1 = $("#question"+id).find("td:eq(1)").text();
        var td2 = $("#question"+id).find("td:eq(2)").text();
        var td3 = $("#question"+id).find("td:eq(3)").text();
        var td4 = $("#question"+id).find("td:eq(4)").text();
        var td5 = $("#question"+id).find("td:eq(5)").text();
        var tr = "<tr id='paperQuestion"+id+"'>" +
                "<td><input class='paperQuestion' type='hidden' value='"+id+"' />"+td1+"</td>" +
                "<td>"+td2+"</td>" +
                "<td>"+td3+"</td>" +
                "<td>"+td4+"</td>" +
                "<td>"+td5+"</td>" +
                "<td><a style='cursor: pointer' data-id='"+id+"' onclick='removePaperQuestion(this)'>删除</a></td>" +
                "</tr>";
        $("#questionTypeBody"+type).append(tr);
    }

    function removePaperQuestion(obj){
        $(obj).parents("tr").remove();
        var question_id = $(obj).attr("data-id");
        $("#question"+question_id).find(":checkbox").removeAttr("checked");
    }

    function removeQuestion(id,type){
        $("#paperQuestion"+id).remove();
    }

    function showQuestion(resourceId){

        // 已经选择的试题
        var paperQuestionList = new Array();
        $(".paperQuestion").each(function(i){
            paperQuestionList.push($(this).val());
        });

        // 难易程度
        var diffcultLevel=new Array();
        diffcultLevel[1]="A";
        diffcultLevel[2]="B";
        diffcultLevel[3]="C";

        // 认知层次
        var cognitionLevel = new Array();
        cognitionLevel[1] = "I";
        cognitionLevel[2] = "II";
        cognitionLevel[3] = "III";

        // 题目类型
        var questionTypes = new Array();
        <#list typeList as type>
            questionTypes[${type.id}] = "${type.typeName}";
        </#list>

        var productId = ${productId};


        $("#questionBody").empty();
        $("#questionBody").append("<tr><td colspan='7' style='text-align: center;color: red;'>正在查询中..</td></tr>");

        // 数据请求
        $.post("/question/ajax_list_by_resource", { resourceId: resourceId,productId:productId},
            function(data){
                var questions = eval(data);
                var questionList = "";
                for(var i=0;i<questions.length;i++){
                    var ifchecked = "";
                    if($.inArray(''+questions[i]['id'], paperQuestionList)>=0){
                        ifchecked = "checked";
                    }
                    var disabled = "";
                    if(questions[i]['relationProductQuestions'].length<1){
                        disabled = "disabled";
                    }
                    questionList = questionList + "<tr id='question"+questions[i]['id']+"'>";

                    if(questions[i]['relationProductQuestions'].length<1){
                        questionList = questionList + "<td></td>";
                    } else {
                        questionList = questionList +
                                "<td><input  class='question' data-type='"+questions[i]['questionType']+"' type='checkbox' value='"+questions[i]['id']+"' "+ifchecked+"  /></td>";
                    }
                    questionList = questionList + "<td>"+questions[i]['id']+"</td><td>"+questionTypes[questions[i]['questionType']]+"</td>" +
                            "<td>"+diffcultLevel[questions[i]['diffcultLevel']]+"</td>" +
                            "<td>"+cognitionLevel[questions[i]['cognitionLevel']]+"</td>" +
                            "<td>"+questions[i]['mdesc']+"</td>";
                    if(questions[i]['relationProductQuestions'].length>=1){
                        questionList = questionList + "<td>"+"["+questions[i]['relationProductQuestions'][0]['chapterIdAlias']+"]"
                                + questions[i]['relationProductQuestions'][0]['chapterName']+ "</td>";
                    } else {
                        questionList = questionList + "<td>未绑定章节</td>";
                    }
                    questionList = questionList + "</tr>";
                }
                if(questionList==""){
                    $("#questionBody").empty();
                    $("#questionBody").append("<tr><td colspan='7' style='text-align: center;color: red;'>查询无结果..</td></tr>");
                } else {
                    $("#questionBody").empty();
                    $("#questionBody").append(questionList);
                }

                $(".question").click(function(){
                    var id = $(this).val();
                    var type = $(this).attr("data-type");
                    if($(this).is(":checked")){
                        addQuestion(id,type);
                    } else {
                        removeQuestion(id,type);
                    }
                });
        });
    }
</script>

<div class="container-fluid" style="padding: 0px;">
    <div class="well">
        <div style="margin-bottom: 10px;">
            <select id="resourceList">
                <option value="0">请选择来源</option>
                <#list resourceList as resource >
                    <option value="${resource.id}">${resource.name}</option>
                </#list>
            </select>
        </div>
        <table class="table table-striped table-bordered table-hover">
            <thead>
            <tr>
                <th>
                    <input type="checkbox" id="checkAll" />
                </th>
                <th>ID</th>
                <th>类型</th>
                <th>难度</th>
                <th>认知层次</th>
                <th style="width: 340px;">题干</th>
                <th>所属章节</th>
            </tr>
            </thead>
            <tbody id="questionBody">
            <tr>
                <td colspan="7" style="text-align: center; color: red;">
                    请选择来源
                </td>
            </tr>
            </tbody>
        </table>
    </div>
</div>

<form action="/examPaper/doAdd" method="post" id="addForm">
<div class="container-fluid">
    <div class="row">

        <div style="margin-bottom: 10px;">
            试卷名称：<input type="text" name="examName" />
        </div>
        <div style="margin-bottom: 10px;">
            试卷描述：<input type="text" name="mdesc" />
            <input type="hidden" value="${productId}" name="productId" />
            <input type="hidden" value="" name="quesIds" id="quesIds" />
        </div>


        <#list typeMap?keys as key>
            <h5>${typeMap[key]['typeName']}(总分：${(typeMap[key]['score'])*(typeMap[key]['questionNum'])}，每题：${typeMap[key]['score']}，题数：${typeMap[key]['questionNum']})</h5>

            <table class="table table-striped table-bordered table-hover">
                <thead>
                <tr>
                    <th style="width: 45px;">ID</th>
                    <th style="width: 100px;">类型</th>
                    <th style="width: 100px;">难度</th>
                    <th style="width: 100px;">认知层次</th>
                    <th style="width: 500px;">题干</th>
                    <th style="width: 100px;">操作</th>
                </tr>
                </thead>
                <tbody id="questionTypeBody${typeMap[key]['id']}">

                </tbody>
            </table>
        </#list>


    </div>
</div>

<div class="clearfix form-actions">
    <div class="col-md-offset-3 col-md-9">
        <button class="btn btn-info" type="button" id="submitButton">
            <i class="ace-icon fa fa-check bigger-110"></i>
            提交
        </button>
    </div>
</div>

</form>



</@htmlBody>
