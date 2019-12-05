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
    });

    function deleteQuestion(id,obj){
        if (confirm("确认要删除?")) {
            $.post("/productQuestion/delete?id="+id, function(data){
                $(obj).parents("tr").remove();
            });
        }
    }

    function showQuestion(productId,chapterId,obj){

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

        $(obj).parents("ul").find("a").css("color","");
        $(obj).css("color","red");
        $("#questionBody").empty();
        $("#questionBody").append("<tr><td colspan='6' style='text-align: center;color: red;'>正在查询中..</td></tr>");
        $.post("/productQuestion/list", { productId: productId, chapterId: chapterId},
            function(data){
                var questions = eval(data);
                var questionList = "";
                for(var i=0;i<questions.length;i++){
                    questionList = questionList + "<tr><td>"+questions[i]['id']+"</td><td>"+questionTypes[questions[i]['questionType']]+"</td><td>"+diffcultLevel[questions[i]['diffcultLevel']]+"</td><td>"+cognitionLevel[questions[i]['cognitionLevel']]+"</td><td>"+questions[i]['questionDesc']+"</td><td><a  onclick='deleteQuestion("+questions[i]['id']+",this)' style='cursor: pointer;'>删除</a></td></tr>";
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
        <div class="col-md-4">
        ${chapterTree}
        </div>
        <div class="col-md-8">
            <table class="table table-striped table-bordered table-hover">
                <thead>
                <tr>
                    <th style="width: 20px;">ID</th>
                    <th style="width: 80px;">类型</th>
                    <th style="width: 50px;">难度</th>
                    <th style="width: 80px;">认知层次</th>
                    <th style="width: 350px;">题干</th>
                    <th style="width: 50px;">操作</th>
                </tr>
                </thead>
                <tbody id="questionBody">
                    <tr><td colspan="6" style="text-align: center;color: red;">请选择栏目查询。。</td></tr>
                </tbody>
            </table>
        </div>
    <div>
</div>

</@htmlBody>
