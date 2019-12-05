<#include "/common/layout.ftl" />
<@htmlHead title="首页">
</@htmlHead>
<@htmlBody bodyclass="page-content">

<form id="addForm" method="post" class="form-horizontal" role="form" action="/question/doEdit" >
    <input type="hidden" name="id" value="${courseQuestion.id}" />
    <input type="hidden" name="radioSelection" value="" id="radio" />
    <input type="hidden" name="checkBoxSelection" value="" id="checkBox" />
    <div class="form-group">
        <label class="col-sm-3 col-xs-3 control-label no-padding-right" for="form-field-1"> 上级目录 </label>
        <div class="col-sm-9 col-xs-9">
            <select datatype="n" sucmsg=" " id="form-field-1" class="col-xs-10 col-sm-5" name="chapterId">
            ${selectTree}
            </select>
        </div>
    </div>

    <div class="form-group">
        <label class="col-sm-3 col-xs-3 control-label no-padding-right" for="form-field-2"> 类型 </label>
        <div class="col-sm-9 col-xs-9">
            <select id="questionType" name="questionType"  >
            	<option>请选择类型</option>
                <#list typeList as type>
                    <option value="${type.id}" <#if courseQuestion.questionType==type.id>selected</#if> >${type.typeName}</option>
                </#list>
            </select>
        </div>
    </div>

    <div class="form-group">
        <label class="col-xs-3 col-xs-3 control-label no-padding-right" for="form-field-3"> 题目来源 </label>
        <div class="col-xs-9 col-xs-9">

            <select name="resourceId">
                <option>请选择来源</option>
                <#list questionResourceList as questionResource>
                    <option value="${questionResource.id}" <#if courseQuestion.resourceId==questionResource.id>selected</#if> >${questionResource.name}</option>
                </#list>
            </select>

        </div>
    </div>

    <div class="form-group">
        <label class="col-sm-3 col-xs-3 control-label no-padding-right" for="form-field-3"> 认知程度 </label>
        <div class="col-sm-9 col-xs-9">
            <select name="cognitionLevel">
            	<option>请选择认知程度</option>
                <option value="1" <#if courseQuestion.cognitionLevel=1>selected</#if>>I</option>
                <option value="2" <#if courseQuestion.cognitionLevel=2>selected</#if>>II</option>
                <option value="3" <#if courseQuestion.cognitionLevel=3>selected</#if>>III</option>
            </select>
        </div>
    </div>

    <div class="form-group">
        <label class="col-sm-3 col-xs-3 control-label no-padding-right" for="form-field-3"> 难易程度 </label>
        <div class="col-sm-9 col-xs-9">
            <select name="diffcultLevel">
            	<option>请选择难易程度</option>
                <option value="1" <#if courseQuestion.diffcultLevel=1>selected</#if>>A</option>
                <option value="2" <#if courseQuestion.diffcultLevel=2>selected</#if>>B</option>
                <option value="3" <#if courseQuestion.diffcultLevel=3>selected</#if>>C</option>
            </select>
        </div>
    </div>

    <div class="form-group">
        <label class="col-sm-3 col-xs-3 control-label no-padding-right" for="form-field-3"> 题干 </label>
        <div class="col-sm-9 col-xs-9">
            <textarea name="mdesc" class="form-control" style="width: 100%;overflow-y:visible;">${courseQuestion.mdesc}</textarea>
        </div>
    </div>


    <div class="form-group">
        <label class="col-xs-3 col-xs-3 control-label no-padding-right" for="form-field-3"> 是否真题 </label>
        <div class="col-xs-9 col-xs-9">
            <label class="radio-inline">
                <input type="radio" name="zhenti" value="1" <#if courseQuestion.zhenti == 1>checked</#if> > 真题
            </label>
            <label class="radio-inline">
                <input type="radio" name="zhenti" value="0" <#if courseQuestion.zhenti == 0>checked</#if>> 非真题
            </label>
        </div>
    </div>

    <div class="form-group">
        <label class="col-xs-3 col-xs-3 control-label no-padding-right" for="form-field-3"> 试题图片 </label>
        <div class="col-xs-9 col-xs-9">
            <input type="text" datatype="s" sucmsg="haha" id="quesImgUrl" name="quesImgUrl" value="${courseQuestion.quesImgUrl}" placeholder=""/>
            <a onclick="fileUpload('quesImgUrl','picture',20,'png,jpg,gif')">上传</a>
        </div>
    </div>

    <div class="form-group">
        <label class="col-sm-3 col-xs-3 control-label no-padding-right" for="form-field-3"> 解析 </label>
        <div class="col-sm-9 col-xs-9">
            <textarea name="manalyze" class="form-control" style="width: 100%;overflow-y:visible;">${courseQuestion.manalyze}</textarea>
        </div>
    </div>

    <!--<#if courseQuestion.questionType==1 || courseQuestion.questionType==2>-->
    <div class="form-group">
        <label class="col-xs-3 col-xs-3 control-label no-padding-right" for="form-field-3"> 选项类型 </label>
        <div class="col-xs-9 col-xs-9">
            <label class="radio-inline">
                <input type="radio" name="optionType" <#if questionAnswers.optionType==1>checked</#if> value="1" > 文字
            </label>
            <label class="radio-inline">
                <input type="radio" name="optionType" <#if questionAnswers.optionType==2>checked</#if> value="2"> 图片
            </label>
        </div>
    </div>
    <!--</#if>-->

	<input type="hidden" name="questionType" id="questionType" value="${courseQuestion.questionType}"/>
    <!--<#if courseQuestion.questionType==1>
    <div class="form-group xuanxiang danxuan_xuanxiang">
        <label class="col-sm-3 col-xs-3 control-label no-padding-right" for="form-field-3"> 选项 </label>
        <div class="col-sm-9 col-xs-9">
            <table class="table table-striped table-bordered table-hover" id="radioTable">
                <thead>
                <tr>
                    <th class="center" style="width: 50px;">
                        ID
                    </th>
                    <th>答案</th>
					<th style="width: 60px;"><a onclick="addRadioRow()" style="cursor: pointer;">添加</a></th>
                </tr>
                </thead>
                <tbody>

                <#list selections as selection>
                <tr>
                    <td style="text-align: center;vertical-align: middle;">
                        <input type="radio"  name="redioAnswerIds" <#if selection.isAnswer==1>checked</#if> value="${selection.id}" />
                    </td>
                    <td>
                        <input type="text" style="<#if selection.optionType==2>width: 85%</#if><#if selection.optionType==1>width: 100%</#if>" value="<#if selection.optionType==2>${selection.imgUrl}</#if><#if selection.optionType==1>${selection.selection}</#if>" id="${selection.id}"  name="redioAnswerSelection"/>
                        <#if selection.optionType==2>
                        <a class="uploadUrl" onclick="fileUpload('${selection.id}','picture',20,'png,jpg,gif')">上传</a>
                        </#if>
                    </td>
					<td><a onclick="deleteRow(this)" style="cursor: pointer;">删除</a></td>
                </tr>
                </#list>

                </tbody>
            </table>
        </div>
    </div>
    </#if>-->

    <#if courseQuestion.questionType==2>
    <div class="form-group xuanxiang duoxuan_xuanxiang">
        <label class="col-sm-3 col-xs-3 control-label no-padding-right" for="form-field-3"> 选项 </label>
        <div class="col-sm-9 col-xs-9">
            <table class="table table-striped table-bordered table-hover" id="checkboxTable">
                <thead>
                <tr>
                    <th class="center" style="width: 50px;">
                        ID
                    </th>
                    <th>答案</th>
                    <th style="width: 60px;"><a onclick="addCheckboxRow()" style="cursor: pointer;">添加</a></th>
                </tr>
                </thead>
                <tbody>
                    <#list selections as selection>
                    <tr>
                        <td style="text-align: center;vertical-align: middle;">
                            <input type="checkbox" disabled="disabled" name="checkboxAnswerIds" <#if selection.isAnswer==1>checked</#if> value="${selection.id}" />
                        </td>
                        <td>
                            <input type="text" style="<#if selection.optionType==2>width: 85%</#if><#if selection.optionType==1>width: 100%</#if>" value="<#if selection.optionType==2>${selection.imgUrl}</#if><#if selection.optionType==1>${selection.selection}</#if>" id="${selection.id}"   name="checkboxAnswerSelection"/>
                            <#if selection.optionType==2>
                                <a class="uploadUrl" onclick="fileUpload('${selection.id}','picture',20,'png,jpg,gif')">上传</a>
                            </#if>
                        </td>
						<td><a onclick="deleteRow(this)" style="cursor: pointer;">删除</a></td>
                    </tr>
                    </#list>
                </tbody>
            </table>
        </div>
    </div>
	<#else>
	<div class="form-group xuanxiang danxuan_xuanxiang">
        <label class="col-sm-3 col-xs-3 control-label no-padding-right" for="form-field-3"> 选项 </label>
        <div class="col-sm-9 col-xs-9">
            <table class="table table-striped table-bordered table-hover" id="radioTable">
                <thead>
                <tr>
                    <th class="center" style="width: 50px;">
                        ID
                    </th>
                    <th>答案</th>
					<th style="width: 60px;"><a onclick="addRadioRow()" style="cursor: pointer;">添加</a></th>
                </tr>
                </thead>
                <tbody>

                <#list selections as selection>
                <tr>
                    <td style="text-align: center;vertical-align: middle;">
                        <input type="radio"  name="redioAnswerIds" <#if selection.isAnswer==1>checked</#if> value="${selection.id}" />
                    </td>
                    <td>
                        <input type="text" style="<#if selection.optionType==2>width: 85%</#if><#if selection.optionType==1>width: 100%</#if>" value="<#if selection.optionType==2>${selection.imgUrl}</#if><#if selection.optionType==1>${selection.selection}</#if>" id="${selection.id}"  name="redioAnswerSelection"/>
                        <#if selection.optionType==2>
                        <a class="uploadUrl" onclick="fileUpload('${selection.id}','picture',20,'png,jpg,gif')">上传</a>
                        </#if>
                    </td>
					<td><a onclick="deleteRow(this)" style="cursor: pointer;">删除</a></td>
                </tr>
                </#list>

                </tbody>
            </table>
        </div>
    </div>
    </#if>

    <div class="clearfix form-actions">
        <div class="col-md-offset-3 col-md-9">
            <button class="btn btn-info" type="button" id="submitButton">
                <i class="ace-icon fa fa-check bigger-110"></i>
                修改
            </button>
        </div>
    </div>
</form>

<script>
    $("#addForm").Validform({
        tiptype: 2,
        postonce: true
    });
    function addRadioRow(){
        var rand = s20();
        var id = 'radio'+ rand;
        var row = '<tr>' +
                '<td style="text-align: center;vertical-align: middle;">' +
                '<input type="radio" name="redioAnswerIds" />' +
                '</td>' +
                '<td>' +
                '<input type="text" id="'+id+'" style="width: 100%" name="redioAnswerSelection" />' +
                '<a class="uploadUrl"  onclick="fileUpload(\''+id+'\',\'picture\',20,\'png,jpg,gif\')">上传</a>'+
                '</td>'+
                '<td>' +
                '<a onclick="deleteRow(this)" style="cursor: pointer;">删除</a>' +
                '</td>'+
                '</tr>';
        $("#radioTable").find("tbody").append(row);
    }
    function s20(){
        var data=["0","1","2","3","4","5","6","7","8","9","A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z","a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"];
        var result="";
        for(var i=0;i<20;i++){
            var r=Math.floor(Math.random()*62);     //取得0-62间的随机数，目的是以此当下标取数组data里的值！
            result+=data[r];        //输出20次随机数的同时，让rrr加20次，就是20位的随机字符串了。
        }
        return result;
    }
    function addCheckboxRow(){
        var rand = s20();
        var id = 'checkbox'+ rand;
        var row = '<tr>' +
                '<td style="text-align: center;vertical-align: middle;">' +
                '<input type="checkbox" name="checkboxAnswerIds" />' +
                '</td>' +
                '<td>' +
                '<input type="text" id="'+id+'" style="width: 100%" name="checkboxAnswerSelection" />' +
                '<a class="uploadUrl"  onclick="fileUpload(\''+id+'\',\'picture\',20,\'png,jpg,gif\')">上传</a>'+
                '</td>'+
                '<td>' +
                '<a onclick="deleteRow(this)" style="cursor: pointer;">删除</a>' +
                '</td>'+
                '</tr>';
        $("#checkboxTable").find("tbody").append(row);
    }
    function deleteRow(obj){
        $(obj).parent("td").parent("tr").remove();
    }
    function submitValid(){
    	var questionType = $("#questionType").val();
    	if(typeof(questionType) =="undefined" || "" == questionType)return false;
        var hasRightAnswer = false;
        var radioSelection = "";
        $("#radioTable").find("tbody").find("tr").each(function(i){
        	var text = $(this).find(":text").val();
        	if(typeof(text) =="undefined" || "" == text)return;
            if($(this).find(":radio").is(":checked")){
                radioSelection = radioSelection + text +"|1;";
                hasRightAnswer = true;
            } else {
                radioSelection = radioSelection + text +"|0;";
            }
        });
        if(questionType==1&&!hasRightAnswer)return false;
        $("#radio").val(radioSelection);

		hasRightAnswer = false;
        var checkBoxSelection = "";
        $("#checkboxTable").find("tbody").find("tr").each(function(i){
        	var text = $(this).find(":text").val();
        	if(typeof(text) =="undefined" || "" == text)return;
            if($(this).find(":checkbox").is(":checked")){
            	checkBoxSelection = checkBoxSelection + text +"|1;";
                hasRightAnswer = true;
            } else {
                checkBoxSelection = checkBoxSelection + text +"|0;";
            }
        });
        if(questionType==2&&!hasRightAnswer)return false;
        $("#checkBox").val(checkBoxSelection);
        return true;
    }

    $(function($) {
        $("#submitButton").click(function(){
            if(!submitValid()){
            	alert("添加失败，请检查类型和答案是否完整。");
            	return;
            }
            $.post("/question/doEdit", $("#addForm").serialize(),function(data){
                alert("添加成功");
                art.dialog.close();
                location.reload();
            });
        });
    });
</script>
    <@fileUpload></@fileUpload>
</@htmlBody>
