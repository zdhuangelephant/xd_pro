<#include "/common/layout.ftl" />
<@htmlHead title="首页">
</@htmlHead>
<@htmlBody bodyclass="page-content">

<form id="addForm" method="post" class="form-horizontal" role="form" action="/question/doAdd">
    <input type="hidden" name="courseId" value="${courseId}" />
    <input type="hidden" name="radioSelection" value="" id="radio" />
    <input type="hidden" name="checkBoxSelection" value="" id="checkBox" />
    <div class="form-group">
        <label class="col-xs-3 col-xs-3 control-label no-padding-right" for="form-field-1"> 上级目录 </label>
        <div class="col-xs-9 col-xs-9">
            <select datatype="n" sucmsg=" " id="form-field-1"  name="chapterId">
                ${selectTree}
            </select>
        </div>
    </div>

    <div class="form-group">
        <label class="col-xs-3 col-xs-3 control-label no-padding-right" for="form-field-2"> 类型 </label>
        <div class="col-xs-9 col-xs-9">
            <select id="questionType" name="questionType" onchange="switchQuestionType(this)">
            	<option>请选择类型</option>
                <#list typeList as type>
                    <option value="${type.id}">${type.typeName}</option>
                </#list>
            </select>
        </div>
    </div>

    <div class="form-group">
        <label class="col-xs-3 col-xs-3 control-label no-padding-right" for="form-field-3"> 试题来源 </label>
        <div class="col-xs-9 col-xs-9">

            <select name="resourceId">
                <option>请选择来源</option>
                <#list questionResourceList as questionResource>
                    <option value="${questionResource.id}">${questionResource.name}</option>
                </#list>
            </select>

        </div>
    </div>

    <div class="form-group">
        <label class="col-xs-3 col-xs-3 control-label no-padding-right" for="form-field-3"> 认知程度 </label>
        <div class="col-xs-9 col-xs-9">
            <select name="cognitionLevel">
                <option value="1">I</option>
                <option value="2">II</option>
                <option value="3">III</option>
            </select>
        </div>
    </div>

    <div class="form-group">
        <label class="col-xs-3 col-xs-3 control-label no-padding-right" for="form-field-3"> 难易程度 </label>
        <div class="col-xs-9 col-xs-9">
            <select name="diffcultLevel">
                <option value="1">A</option>
                <option value="2">B</option>
                <option value="3">C</option>
            </select>
        </div>
    </div>

    <div class="form-group">
        <label class="col-xs-3 col-xs-3 control-label no-padding-right" for="form-field-3"> 题干 </label>
        <div class="col-xs-9 col-xs-9">
            <textarea name="mdesc" class="form-control" style="width: 100%;overflow-y:visible;"></textarea>
        </div>
    </div>

    <div class="form-group">
        <label class="col-xs-3 col-xs-3 control-label no-padding-right" for="form-field-3"> 是否真题 </label>
        <div class="col-xs-9 col-xs-9">
            <label class="radio-inline">
                <input type="radio" name="zhenti" value="1" > 真题
            </label>
            <label class="radio-inline">
                <input type="radio" name="zhenti" value="0" checked> 非真题
            </label>
        </div>
    </div>


    <div class="form-group">
        <label class="col-xs-3 col-xs-3 control-label no-padding-right" for="form-field-3"> 试题图片 </label>
        <div class="col-xs-9 col-xs-9">
            <input type="text" datatype="s" sucmsg="haha" id="quesImgUrl" name="quesImgUrl" placeholder=""/>
            <a onclick="fileUpload('quesImgUrl','picture',20,'png,jpg,gif')">上传</a>
        </div>
    </div>

    <div class="form-group">
        <label class="col-xs-3 col-xs-3 control-label no-padding-right" for="form-field-3"> 解析 </label>
        <div class="col-xs-9 col-xs-9">
            <textarea name="manalyze" class="form-control" style="width: 100%;overflow-y:visible;"></textarea>
        </div>
    </div>

    <div class="form-group optionType" >
        <label class="col-xs-3 col-xs-3 control-label no-padding-right" for="form-field-3"> 选项类型 </label>
        <div class="col-xs-9 col-xs-9">
            <label class="radio-inline">
                <input type="radio" name="optionType" value="1" checked> 文字
            </label>
            <label class="radio-inline">
                <input type="radio" name="optionType" value="2"> 图片
            </label>
        </div>
    </div>

    <div class="form-group xuanxiang danxuan_xuanxiang">
        <label class="col-xs-3 col-xs-3 control-label no-padding-right" for="form-field-3"> 选项 </label>
        <div class="col-xs-9 col-xs-9">
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
                <tr>
                    <td style="text-align: center;vertical-align: middle;"><input type="radio" name="redioAnswerIds" /></td>
                    <td>
                        <input type="text" id="radio1" style="width: 100%"  name="redioAnswerSelection"/>
                        <a class="uploadUrl" onclick="fileUpload('radio1','picture',20,'png,jpg,gif')">上传</a>
                    </td>
                    <td><a onclick="deleteRow(this)" style="cursor: pointer;">删除</a></td>
                </tr>
                <tr>
                    <td style="text-align: center;vertical-align: middle;"><input type="radio" name="redioAnswerIds" /></td>
                    <td>
                        <input type="text" id="radio2" style="width: 100%" name="redioAnswerSelection" />
                        <a class="uploadUrl" onclick="fileUpload('radio2','picture',20,'png,jpg,gif')">上传</a>
                    </td>
                    <td><a onclick="deleteRow(this)" style="cursor: pointer;">删除</a></td>
                </tr>
                <tr>
                    <td style="text-align: center;vertical-align: middle;"><input type="radio" name="redioAnswerIds" /></td>
                    <td>
                        <input type="text" id="radio3" style="width: 100%" name="redioAnswerSelection" />
                        <a class="uploadUrl" onclick="fileUpload('radio3','picture',20,'png,jpg,gif')">上传</a>
                    </td>
                    <td><a onclick="deleteRow(this)" style="cursor: pointer;">删除</a></td>
                </tr>
                <tr>
                    <td style="text-align: center;vertical-align: middle;"><input type="radio" name="redioAnswerIds" /></td>
                    <td>
                        <input type="text" id="radio4" style="width: 100%" name="redioAnswerSelection" />
                        <a class="uploadUrl" onclick="fileUpload('radio4','picture',20,'png,jpg,gif')">上传</a>
                    </td>
                    <td><a onclick="deleteRow(this)" style="cursor: pointer;">删除</a></td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>



    <div class="form-group xuanxiang duoxuan_xuanxiang" style="display: none;">
        <label class="col-xs-3 col-xs-3 control-label no-padding-right" for="form-field-3"> 选项 </label>
        <div class="col-xs-9 col-xs-9">
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
                <tr>
                    <td style="text-align: center;vertical-align: middle;"><input type="checkbox" name="checkboxAnswerIds" /></td>
                    <td>
                        <input type="text" id="checkbox1" style="width: 100%" name="checkboxAnswerSelection" />
                        <a class="uploadUrl" onclick="fileUpload('checkbox1','picture',20,'png,jpg,gif')">上传</a>
                    </td>
                    <td><a onclick="deleteRow(this)" style="cursor: pointer;">删除</a></td>
                </tr>
                <tr>
                    <td style="text-align: center;vertical-align: middle;"><input type="checkbox" name="checkboxAnswerIds" /></td>
                    <td>
                        <input type="text" id="checkbox2" style="width: 100%" name="checkboxAnswerSelection" />
                        <a class="uploadUrl" onclick="fileUpload('checkbox2','picture',20,'png,jpg,gif')">上传</a>
                    </td>
                    <td><a onclick="deleteRow(this)" style="cursor: pointer;">删除</a></td>
                </tr>
                <tr>
                    <td style="text-align: center;vertical-align: middle;"><input type="checkbox" name="checkboxAnswerIds" /></td>
                    <td>
                        <input type="text" id="checkbox3" style="width: 100%" name="checkboxAnswerSelection" />
                        <a class="uploadUrl" onclick="fileUpload('checkbox3','picture',20,'png,jpg,gif')">上传</a>
                    </td>
                    <td><a onclick="deleteRow(this)" style="cursor: pointer;">删除</a></td>
                </tr>
                <tr>
                    <td style="text-align: center;vertical-align: middle;"><input type="checkbox" name="checkboxAnswerIds" /></td>
                    <td>
                        <input type="text" id="checkbox4" style="width: 100%" name="checkboxAnswerSelection" />
                        <a class="uploadUrl" onclick="fileUpload('checkbox4','picture',20,'png,jpg,gif')">上传</a>
                    </td>
                    <td><a onclick="deleteRow(this)" style="cursor: pointer;">删除</a></td>
                </tr>
                <tr>
                    <td style="text-align: center;vertical-align: middle;"><input type="checkbox" name="checkboxAnswerIds" /></td>
                    <td>
                        <input type="text" id="checkbox5" style="width: 100%" name="checkboxAnswerSelection" />
                        <a class="uploadUrl" onclick="fileUpload('checkbox5','picture',20,'png,jpg,gif')">上传</a>
                    </td>
                    <td><a onclick="deleteRow(this)" style="cursor: pointer;">删除</a></td>
                </tr>
                </tbody>
            </table>
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

<style>
    .uploadUrl{
        display: none;
    }
</style>


<script>
	var questionType = "";
    $("#addForm").Validform({
        tiptype: 2,
        postonce: true
    });

    function s20(){
        var data=["0","1","2","3","4","5","6","7","8","9","A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z","a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"];
        var result="";
        for(var i=0;i<20;i++){
            var r=Math.floor(Math.random()*62);     //取得0-62间的随机数，目的是以此当下标取数组data里的值！
            result+=data[r];        //输出20次随机数的同时，让rrr加20次，就是20位的随机字符串了。
        }
        return result;
    }

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

    function switchQuestionType(obj){
        questionType = $(obj).val();
        $(".xuanxiang").hide();
        $(".optionType").hide();
        /*if(questionType==1){
            $(".danxuan_xuanxiang").show();
            $(".optionType").show();
        } else if(questionType==2){
            $(".duoxuan_xuanxiang").show();
            $(".optionType").show();
        } else {
            $(".optionType").hide();
        }*/
		if(questionType==2){
            $(".duoxuan_xuanxiang").show();
            $(".optionType").show();
        } else {
            $(".danxuan_xuanxiang").show();
            $(".optionType").show();
        }
    }

    function submitValid(){
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
            $.post("/question/doAdd", $("#addForm").serialize(),function(data){
                alert("添加成功");
                art.dialog.close();
            });
        });

        $(":radio[name='optionType']").click(function(){
            if($(":radio[name='optionType']:checked").val()==1){
                $(".xuanxiang").find(":text").css("width","100%");
                $(".xuanxiang").find(":text").val("");
                $(".uploadUrl").hide();
            } else {
                $(".xuanxiang").find(":text").css("width","85%");
                $(".xuanxiang").find(":text").val("");
                $(".uploadUrl").show();
            }
        });

    });


</script>
    <@fileUpload></@fileUpload>
</@htmlBody>
