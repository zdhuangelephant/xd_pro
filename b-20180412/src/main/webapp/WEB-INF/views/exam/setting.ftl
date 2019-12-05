<#include "/common/layout.ftl" />
<@htmlHead title="首页">
</@htmlHead>
<@htmlBody bodyclass="page-content">
<form id="addForm" method="post"  action="/product/editQuestionSetting">
    <div style="height: 30px;">
        总分数:
        <input type="hidden" name="productId" value="${productId}" />
        <input name="totalScore" class="questionInput" id="totalScore" value="${setting.totalScore}" />
    </div>
    <div style="height: 10px;"></div>

    <table class="table table-striped table-bordered table-hover" id="scoreTable">
        <thead>
            <tr>
                <th style="width: 70px;">选择</th>
                <th>ID</th>
                <th>名称</th>
                <th style="width: 70px;">类型</th>
                <th style="width: 70px;">分值</th>
                <th style="width: 70px;">题数</th>
                <th style="">总分</th>
                <th style="width: 70px;">排序</th>
            </tr>
        </thead>
        <tbody>
        <#list typeList as type>
        <tr class="scoreTr">
            <td><input type="checkbox" name="typeSelect" value="${type.id}" <#list typeMap?keys as key><#if key==type.id>checked</#if></#list>   /></td>
            <td>
                ${type.id}
            </td>
            <td>
                <input type="hidden" name="typeName${type.id}" value="${type.typeName}">
                ${type.typeName}
            </td>
            <td>
                <input type="hidden" name="answerType${type.id}" value="${type.answerType}" />
                <#if type.answerType==0>
                    客观题
                    <#else>
                    主观题
                </#if>
            </td>
            <td>
                <input class="typeScore questionInput" style="width: 50px;" name="typeScore${type.id}" <#list typeMap?keys as key><#if key==type.id>value="${typeMap[key].score}"</#if></#list> />
            </td>
            <td>
                <input class="questionNum questionInput" style="width: 50px;" name="questionNum${type.id}" <#list typeMap?keys as key><#if key==type.id>value="${typeMap[key].questionNum}"</#if></#list> />
            </td>
            <td class="trScore">0分</td>
            <td>
                <input style="width: 50px;" name="typeOrder${type.id}" <#list typeMap?keys as key><#if key==type.id>value="${typeMap[key].listOrder}"</#if></#list> />
            </td>
        </tr>
        </#list>
        <tr>
            <td colspan="8" id="total">
                总分:0分

            </td>
        </tr>
        <tr id="tip">
            <td colspan="8">
                <span style="color: red;">提示:总分不正确!!!!</span>
            </td>
        </tr>
        </tbody>

    </table>

    <script>

        function validScore(){
            var totalScore = 0;
            //计算
            $("#scoreTable").find(".scoreTr").each(function(i){
                if($(this).find(":checkbox").is(":checked")){
                    var typeScore = $(this).find(".typeScore").val();
                    var questionNum =  $(this).find(".questionNum").val();
                    
                    var trScore = typeScore*questionNum;
                    $(this).find(".trScore").text(trScore+"分");
                    totalScore = trScore + totalScore;
                } else {
                    $(this).find(".trScore").text("0分");
                }
            });
            $("#total").text("总分:"+totalScore+"分");
            if(totalScore!=$("#totalScore").val()){
                $("#tip").show();
            } else {
                $("#tip").hide();
            }
        }

        $(document).ready(function(){
            validScore();
            $(".questionInput").change(function(){
                validScore();
            });
            $(":checkbox").click(function(){
                validScore();
            });
            
            
            
            
        });
    </script>

</form>
</@htmlBody>
