<#include "/common/layout.ftl" />
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <title>导入</title>
    <link rel="stylesheet" href="${rc.contextPath}/resources/assets/css/bootstrap.min.css">
    <script src="${rc.contextPath}/resources/assets/js/jquery.min.js"></script>
    <script src="${rc.contextPath}/resources/assets/js/bootstrap.min.js"></script>
    <script src="${rc.contextPath}/resources/js/jqueryTreeView/jquery.cookie.js"></script>
</head>
<body>
<div class="container-fluid">
    <h3>题目批量导入</h3>
    <table class="table table-striped table-bordered table-hover ">
        <tbody>

        <#list 1..20 as index>
        <tr class="success tr${index}">
            <th style="width: 50px;">编号</th>
            <th style="width: 150px;">栏目</th>
            <th style="width: 150px;">题目类型</th>
            <th style="width: 150px;">来源</th>
            <th style="width: 150px;">认知层次</th>
            <th style="width: 150px;">难易程度</th>
            <th style="width: 150px;">是否真题</th>
        </tr>
        <tr  class="tr${index}">
            <td rowspan="4" style="text-align: center;vertical-align: middle;">${index}</td>
            <td>
                <select class="form-control chapterSelect" id="chapter${index}" name="chapter${index}">
                    <option value="0">请选择栏目</option>
                    ${selectTree}
                </select>
            </td>
            <td>
                <select class="form-control questionTypeSelect" id="questionType${index}" name="questionType${index}">
                    <option value="0">请选择</option>
                    <#list typeList as type>
                        <option value="${type.id}">${type.typeName}</option>
                    </#list>
                </select>
            </td>
            <td>
                <select class="form-control questionResourceSelect" id="questionResource${index}" name="questionResource${index}">
                    <option value="0">请选择来源</option>
                    <#list questionResourceList as questionResource>
                        <option value="${questionResource.id}">${questionResource.name}</option>
                    </#list>
                </select>
            </td>
            <td>
                <label class="radio-inline">
                    <input type="radio" class="cognitionLevelSelect" name="cognitionLevel${index}" id="cognitionLevel${index}" value="1" checked> I
                </label>
                <label class="radio-inline">
                    <input type="radio" class="cognitionLevelSelect" name="cognitionLevel${index}" id="cognitionLevel${index}" value="2"> II
                </label>
                <label class="radio-inline">
                    <input type="radio" class="cognitionLevelSelect" name="cognitionLevel${index}" id="cognitionLevel${index}" value="3"> III
                </label>
            </td>
            <td>
                <label class="radio-inline">
                    <input type="radio" class="diffcultLevelSelect" name="diffcultLevel${index}" id="diffcultLevel${index}" value="1" checked> A
                </label>
                <label class="radio-inline">
                    <input type="radio" class="diffcultLevelSelect" name="diffcultLevel${index}" id="diffcultLevel${index}" value="2"> B
                </label>
                <label class="radio-inline">
                    <input type="radio" class="diffcultLevelSelect" name="diffcultLevel${index}" id="diffcultLevel${index}" value="3"> C
                </label>
            </td>

            <td>
                <label class="radio-inline">
                    <input type="radio" class="zhentiSelect" name="zhenti${index}" id="zhenti${index}" value="0" checked> 否
                </label>
                <label class="radio-inline">
                    <input type="radio" class="zhentiSelect" name="zhenti${index}" id="zhenti${index}" value="1" > 是
                </label>
            </td>

        </tr>
        <tr class="tr${index}">
            <td colspan="6" >
                <textarea class="form-control questionDescInput" id="questionDesc${index}" name="questionDesc${index}" placeholder="题干" rows="2"></textarea>
            </td>
        </tr>
        <tr class="tr${index}">
            <td colspan="6" >
                <textarea class="form-control questionAnalysisInput" id="questionAnalysis${index}" name="questionAnalysis${index}"  placeholder="解析" rows="2"></textarea>
            </td>
        </tr>
        <tr class="tr${index}">
            <td>
                <div class="form-inline">
                <div class="form-group">
                    <input type="radio" class="questionRadioSelect" name="questionRadio${index}" id="questionRadio${index}"  checked />
                </div>
                <div class="form-group">
                    <input type="text" name="questionOptionA${index}" id="questionOptionA${index}"  class="form-control questionOptionAInput" placeholder="答案1" >
                </div>
                </div>
            </td>
            <td >
                <div class="form-inline">
                    <div class="form-group">
                        <input type="radio" class="questionRadioSelect" name="questionRadio${index}" id="questionRadio${index}" />
                    </div>
                    <div class="form-group">
                        <input  type="text" name="questionOptionB${index}" id="questionOptionB${index}"  class="form-control questionOptionBInput" placeholder="答案2" >
                    </div>
                </div>
            </td>
            <td >
                <div class="form-inline">
                    <div class="form-group">
                        <input type="radio" class="questionRadioSelect" name="questionRadio${index}" id="questionRadio${index}" />
                    </div>
                    <div class="form-group">
                        <input  type="text" name="questionOptionC${index}" id="questionOptionC${index}"  class="form-control questionOptionCInput" placeholder="答案3" >
                    </div>
                </div>
            </td>
            <td >
                <div class="form-inline">
                    <div class="form-group">
                        <input type="radio" class="questionRadioSelect"  name="questionRadio${index}" id="questionRadio${index}"  />
                    </div>
                    <div class="form-group">
                        <input type="text" name="questionOptionD${index}" id="questionOptionD${index}"  class="form-control questionOptionDInput" placeholder="答案4" >
                    </div>
                </div>
            </td>
            <td >
                <div class="form-inline">
                    <div class="form-group">
                        <input type="radio" class="questionRadioSelect"  name="questionRadio${index}" id="questionRadio${index}" />
                    </div>
                    <div class="form-group">
                        <input  type="text" name="questionOptionE${index}" id="questionOptionE${index}"  class="form-control questionOptionEInput" placeholder="答案5" >
                    </div>
                </div>
            </td>
            <td >
                <div class="form-inline">
                    <div class="form-group">
                        <input type="radio" class="questionRadioSelect"  name="questionRadio${index}" id="questionRadio${index}" />
                    </div>
                    <div class="form-group">
                        <input type="text" name="questionOptionF${index}" id="questionOptionF${index}"  class="form-control questionOptionFInput" placeholder="答案6" >
                    </div>
                </div>
            </td>

        </tr>

        </#list>

        </tbody>

    </table>
</div>

<script>

//    $(document).ready(function(){
//
//        var chapterSelect = 'chapterSelect';
//        var questionTypeSelect = 'questionTypeSelect';
//        var questionResourceSelect = 'questionResourceSelect';
//        var cognitionLevelSelect = 'cognitionLevelSelect';
//        var diffcultLevelSelect = 'diffcultLevelSelect';
//        var questionDescInput = 'questionDescInput';
//        var questionAnalysisInput = 'questionAnalysisInput';
//        var questionRadioSelect = 'questionRadioSelect';
//        var questionOptionAInput = 'questionOptionAInput';
//        var questionOptionBInput = 'questionOptionBInput';
//        var questionOptionCInput = 'questionOptionCInput';
//        var questionOptionDInput = 'questionOptionDInput';
//        var questionOptionEInput = 'questionOptionEInput';
//
//
//        if($.cookie(chapterSelect)){
//            setValue($.cookie(chapterSelect));
//        }
//
//        if($.cookie(questionTypeSelect)){
//            setValue($.cookie(questionTypeSelect));
//        }
//
//        if($.cookie(questionResourceSelect)){
//            setValue($.cookie(questionResourceSelect));
//        }
//
//        if($.cookie(cognitionLevelSelect)){
//            setValue($.cookie(cognitionLevelSelect));
//        }
//
//        if($.cookie(diffcultLevelSelect)){
//            setValue($.cookie(diffcultLevelSelect));
//        }
//
//        if($.cookie(questionDescInput)){
//            setValue($.cookie(questionDescInput));
//        }
//
//        if($.cookie(questionAnalysisInput)){
//            setValue($.cookie(questionAnalysisInput));
//        }
//
//        if($.cookie(questionRadioSelect)){
//            setValue($.cookie(questionRadioSelect));
//        }
//
//        if($.cookie(questionOptionAInput)){
//            setValue($.cookie(questionOptionAInput));
//        }
//
//        if($.cookie(questionOptionBInput)){
//            setValue($.cookie(questionOptionBInput));
//        }
//
//        if($.cookie(questionOptionCInput)){
//            setValue($.cookie(questionOptionCInput));
//        }
//
//        if($.cookie(questionOptionDInput)){
//            setValue($.cookie(questionOptionDInput));
//        }
//
//        if($.cookie(questionOptionEInput)){
//            setValue($.cookie(questionOptionEInput));
//        }
//
//        $(":text,:checkbox,:radio,textarea").change(function(){
//            //cookie 时间
//            var date = new Date();
//            date.setTime(date.getTime() + (3 * 24 * 60 * 60 * 1000));
//
//            //cookie 路径
//            var path = "/question/batchAdd";
//
//            var chapterSelectValue = "";
//            $(".chapterSelect").each(function(i){
//                chapterSelectValue = chapterSelectValue + $(this).attr("id") + ":" + $(this).val() + ";";
//            });
//            $.cookie(chapterSelect, chapterSelectValue, { path: path, expires: date });
//
//            var questionTypeSelectValue = "";
//            $(".questionTypeSelect").each(function(i){
//                questionTypeSelectValue = questionTypeSelectValue + $(this).attr("id") + ":" + $(this).val() + ";";
//            });
//            $.cookie(questionTypeSelect, questionTypeSelectValue, { path: path, expires: date });
//
//            var questionResourceSelectValue = "";
//            $(".questionResourceSelect").each(function(i){
//                questionResourceSelectValue = questionResourceSelectValue + $(this).attr("id") + ":" + $(this).val() + ";";
//            });
//            $.cookie(questionResourceSelect, questionResourceSelectValue, { path: path, expires: date });
//
//            var cognitionLevelSelectValue = "";
//            $(".cognitionLevelSelect").each(function(i){
//                cognitionLevelSelectValue = cognitionLevelSelectValue + $(this).attr("id") + ":" + $(this).val() + ";";
//            });
//            $.cookie(cognitionLevelSelect, cognitionLevelSelectValue, { path: path, expires: date });
//
//            var diffcultLevelSelectValue = "";
//            $(".diffcultLevelSelect").each(function(i){
//                diffcultLevelSelectValue = diffcultLevelSelectValue + $(this).attr("id") + ":" + $(this).val() + ";";
//            });
//            $.cookie(diffcultLevelSelect, diffcultLevelSelectValue, { path: path, expires: date });
//
//            var questionDescInputValue = "";
//            $(".questionDescInput").each(function(i){
//                questionDescInputValue = questionDescInputValue + $(this).attr("id") + ":" + $(this).val() + ";";
//            });
//            $.cookie(questionDescInput, questionDescInputValue, { path: path, expires: date });
//
//            var questionAnalysisInputValue = "";
//            $(".questionAnalysisInput").each(function(i){
//                questionAnalysisInputValue = questionAnalysisInputValue + $(this).attr("id") + ":" + $(this).val() + ";";
//            });
//            $.cookie(questionAnalysisInput, questionAnalysisInputValue, { path: path, expires: date });
//
//            var questionRadioSelectValue = "";
//            $(".questionRadioSelect").each(function(i){
//                questionRadioSelectValue = questionRadioSelectValue + $(this).attr("id") + ":" + $(this).val() + ";";
//            });
//            $.cookie(questionRadioSelect, questionRadioSelectValue, { path: path, expires: date });
//
//            var questionOptionAInputValue = "";
//            $(".questionOptionAInput").each(function(i){
//                questionOptionAInputValue = questionOptionAInputValue + $(this).attr("id") + ":" + $(this).val() + ";";
//            });
//            $.cookie(questionOptionAInput, questionOptionAInputValue, { path: path, expires: date });
//
//            var questionOptionBInputValue = "";
//            $(".questionOptionBInput").each(function(i){
//                questionOptionBInputValue = questionOptionBInputValue + $(this).attr("id") + ":" + $(this).val() + ";";
//            });
//            $.cookie(questionOptionBInput, questionOptionBInputValue, { path: path, expires: date });
//
//            var questionOptionCInputValue = "";
//            $(".questionOptionCInput").each(function(i){
//                questionOptionCInputValue = questionOptionCInputValue + $(this).attr("id") + ":" + $(this).val() + ";";
//            });
//            $.cookie(questionOptionCInput, questionOptionCInputValue, { path: path, expires: date });
//
//            var questionOptionDInputValue = "";
//            $(".questionOptionDInput").each(function(i){
//                questionOptionDInputValue = questionOptionDInputValue + $(this).attr("id") + ":" + $(this).val() + ";";
//            });
//            $.cookie(questionOptionDInput, questionOptionDInputValue, { path: path, expires: date });
//
//            var questionOptionEInputValue = "";
//            $(".questionOptionEInput").each(function(i){
//                questionOptionEInputValue = questionOptionEInputValue + $(this).attr("id") + ":" + $(this).val() + ";";
//            });
//            $.cookie(questionOptionEInput, questionOptionEInputValue, { path: path, expires: date });
//        });
//    });
//
//    function setValue(cookie){
//        var items = cookie.split(";");
//        for(var i=0;i<items.length;i++){
//            var item = items[i].split(":");
//            $("#"+item[0]).val(item[1]);
//        }
//    }

</script>

</body>
</html>
