<#include "/common/layout.ftl" />
<#include "/course/chapterListFrame.ftl" />
<@htmlHead title="首页">
</@htmlHead>
<@htmlBody bodyclass="page-content">


    <@chapterframe>
<style type="text/css">
.stext{padding:6px 12px;width:200px;border:1px solid #a4a2a2; }
label{
    position:absolute;z-index:2;top:7px;left:13px;display:block;color:#BCBCBC;cursor:text;
    
    -moz-user-select:none;
    -webkit-user-select:none;
    
    -moz-transition:all .16s ease-in-out;
    -webkit-transition:all .16s ease-in-out;
}
.fff{
    position:absolute;z-index:1;top:0;right:0;left:3px;bottom:0;background-color:#fff;*width:100%;*padding:6px 12px;*margin:0 0 0 -1px;_width:auto;_padding:0;_margin:0 ;
    
    border-radius:8px;
    -moz-border-radius:8px;
    -webkit-border-radius:8px;
}
input[type=text]:focus+label, textarea:focus+label{opacity:.5;filter:alpha(opacity="50");}
.FancyForm .val label{left:-9999px;opacity:0!important;filter:alpha(opacity="0")!important;}
/* Button base */
.Button{
    position:relative;
    display:inline-block;
    padding:.45em .825em .45em;
    text-align:center;
    line-height:1em; 
    border:1px solid transparent;
    cursor:pointer; 
     
    border-radius:.3em; 
    -moz-border-radius:.3em; 
    -webkit-border-radius:.3em; 
    
    -moz-transition-property:color, -moz-box-shadow, text-shadow; 
    -moz-transition-duration:.05s; 
    -moz-transition-timing-function:ease-in-out; 
    -webkit-transition-property:color, -webkit-box-shadow, text-shadow; 
    -webkit-transition-duration:.05s; 
    -webkit-transition-timing-function:ease-in-out; 
    
    box-shadow:0 1px rgba(255,255,255,0.8), inset 0 1px rgba(255,255,255,0.35); 
    -moz-box-shadow:0 1px rgba(255,255,255,0.8), inset 0 1px rgba(255,255,255,0.35); 
    -webkit-box-shadow:0 1px rgba(255,255,255,0.8), inset 0 1px rgba(255,255,255,0.35);
}
</style>
<ul class="nav nav-tabs" style="height: 95px">

   <li class="pull-right" style="padding-top: 5px;">
        <a style="background:none; border: none; font-weight: bold;color: #2679b5; " href="#frog" aria-controls="frog"
           onclick="searchK()">
            <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
        搜索
        </a>
    </li>
    
    <li class="pull-right">
    <div style="display: inline-block">题目状态：</div>
        <a class="stext" style="display: inline-block;background:none; border: none; font-weight: normal;color: #2679b5; " href="#frog" aria-controls="frog">
            <select id="status" name="status" style="width:182px;" >
                <option value="">---请选择---</option>
                <#if status = 99>
                <option value="99" selected>已审核</option>
                <#else>
                <option value="99">已审核</option>
                </#if>
                <#if status = -1>
                <option value="-1" selected>未审核</option>
                <#else>
                <option value="-1">未审核</option>
                </#if>
            </select>
        </a>
    </li>
    <!-- 类型 -->
    <li class="pull-right">
        <div style="display: inline-block">类型：</div>
        <a class="stext" style="display: inline-block;background:none; border: none; font-weight: normal;color: #2679b5; " href="#frog" aria-controls="frog">
            <select id="questionType" name="questionType" style="width:182px;" >
                <option value="">---请选择---</option>
                <#list typeMap?keys as key>
                    <option value="${key }" <#if key==questionType>selected </#if> >${typeMap[key].typeName}</option>
                </#list>
            </select>
        </a>
    </li>
    
    <li class="pull-right">
        <p style="background:none; border: none; font-weight: normal;color: #2679b5;padding-top:5px;padding-right:10px; ">
            <input type="text" id="id" name="id" placeHolder="题目ID" value="${id}"/>
        </p>
    </li>
    
    <li class="pull-left" style="margin-left: 23px;">
        <p style="background:none; border: none; font-weight: normal;color: #2679b5;padding-top:8px;padding-right:10px; ">
            <input type="text" id="keyword" name="keyword" placeHolder="题干关键词搜索" value="${keyword}"/>
        </p>
    </li>
    
    <!-- 来源 -->
    <li class="pull-left">
        <div style="display: inline-block">来源：</div>
        <a class="stext" style="display: inline-block;background:none; border: none; font-weight: normal;color: #2679b5; " href="#frog" aria-controls="frog">
            <select id="resourceId" name="resourceId" style="width:182px;" >
                <option value="">---请选择---</option>
                <#list resourceIdList as qs>
                    <option value="${qs.id }" <#if qs.id==resourceId>selected </#if> >${qs.name}</option>
                </#list>
            </select>
        </a>
    </li>
    
    <!-- 是否是真题 -->
    <li class="pull-left">
        <div style="display: inline-block">是否真题：</div>
        <a class="stext" style="display: inline-block;background:none; border: none; font-weight: normal;color: #2679b5; " href="#frog" aria-controls="frog">
            <select id="zhenti" name="zhenti" style="width:182px;" >
                <option value="">---请选择---</option>
                <#if zhenti = 1>
                    <option value="1" selected>真题</option>
                <#else>
                    <option value="1">真题</option>
                </#if>
                <#if zhenti = 0>
                <option value="0" selected>非真题</option>
                <#else>
                <option value="0">非真题</option>
                </#if>
            </select>
        </a>
    </li>
    
    <script src="${rc.contextPath}/resources/js/plugins/bootstrap-table/bootstrap-table.js"></script>
    <script src="${rc.contextPath}/resources/js/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.js"></script>
    <script src="${rc.contextPath}/resources/assets/js/bootstrap-typeahead.js"></script>
    <script src="${rc.contextPath}/resources/js/jqexcel/excel.js?1"></script>
    <script src="${rc.contextPath}/resources/dist/layui.js?${timeStamp}"></script>
    <script src="${rc.contextPath}/resources/exam/common.js?1"></script>
    <script src="${rc.contextPath}/resources/exam/exam.js?1${timeStamp}"></script>
    
    
    <#include "/exam/importQuestions.ftl"/>
    <#include "/common/form/file.ftl"> 
    
    <#if courseId?if_exists>
    <#if courseId!=0>
    <#if chapterId!=0>
    <li class="pull-right">
        <a style="background:none; border: none; font-weight: bold;color: #2679b5; " href="#frog" aria-controls="frog"
           onclick="addQuestion(${courseId},${chapterId})">
            <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
            添加
        </a>
    </li>
    </#if>
    </#if>
    </#if>
</ul>

<div style="margin-bottom: 10px;"></div>

<table class="table table-striped table-bordered table-hover" id="table">
    <thead>
    <tr>
        <#-- <th>
            <input type="checkbox" id="checkAll" />
        </th> -->
        <th class="center" style="width: 50px;">
            ID
        </th>
        <th style="width: 100px;">章节</th>
        <th style="width: 70px;">类型</th>
        <!--<th style="width: 70px;">难易程度</th>-->
        <!--<th style="width: 70px;">认知层次</th>-->
        <th style="width: 100px;">来源</th>
        <th style="width: 100px;">是否真题</th>
        <th style="width: 100px;">正确答案</th>
        <th>题干</th>
        <th>状态</th>
        <th style="width: 100px;">操作</th>
    </tr>
    </thead>
    <tbody>
        <#list pageList.result as question>
        <tr>
            <#-- <td>
                <input class="inputcheckbox " name="ids" value="${question.id}" type="checkbox">
            </td> -->
            <td> <a style="cursor: pointer;" onclick="detailQuestion(${question.id},${question.courseId})">${question.id}</a></td>
            <td>${question.chapterName}</td>
            <td>
              <#list typeMap?keys as key>
                  <#if key==question.questionType>${typeMap[key].typeName}</#if>
              </#list>
            </td>
            <!--<td>
                <#list easyLevel?keys as key>
                    <#if key==question.diffcultLevel>${easyLevel[key]}</#if>
                </#list>
            </td>
            <td>
                <#list diffcultLevel?keys as key>
                    <#if key==question.cognitionLevel>${diffcultLevel[key]}</#if>
                </#list>
            </td>-->
            <td>${question.questionSrc}</td>
            <td><#if question.zhenti==1>真题<#else>非真题</#if></td>
            <!-- 去掉'难易程度，认知层次',增加一列'正确答案'-->
            <td>
          	  ${question.answerIds}
                          
            </td>
            <td>${question.mdesc}</td>
            <td><#if question.status==99>通过<#else><a style="color: red;">待审核</a></#if></td>
            <td>
                <div class="btn-group">
                    <div class="dropdown">
                        <button class="btn  btn-primary dropdown-toggle" style="border: 0px; width: 90px; padding: 2px 4px; font-size: 12px;" type="button" id="dropdownMenu2" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">
                            操作
                            <span class="caret"></span>
                        </button>
                        <ul class="dropdown-menu" aria-labelledby="dropdownMenu2">
                            <li><a style="cursor: pointer;" onclick="delQuestion(${question.id},'${question.name}')">删除</a></li>
                            <li><a style="cursor: pointer;" onclick="editQuestion(${question.id},${question.courseId})">修改</a></li>
                            <li><a style="cursor: pointer;" onclick="setKeywords(${question.id},${question.courseId},${question.chapterId})">关联关键词</a></li>
                        </ul>
                    </div>
                </div>

            </td>
        </tr>
        </#list>
    </tbody>
</table>

    <@page totalCount="${pageList.totalCount}" pageNo="${pageList.pageNo}" totalPage="${pageList.totalPage}" url="">
    </@page>

<script>

    $(document).ready(function(){
        
        
        
        $("#checkAll").click(function(){
            if($(this).is(":checked")){
                $("input[type='checkbox'][name='ids']").prop("checked", true);
            } else {
                $("input[type='checkbox'][name='ids']").prop("checked", false);
            }
        });
        // 验证关键词
        $('input[name="keyword"]').blur(function(){
            if($(this).val().length <=5 && $(this).val()!=''){
                alert('关键词需要在5个字以上');
                $(this).val('').focus();
            }
        });
    });


    /**
     * 编辑栏目
     * @param catId
     */
    function editQuestion(id,courseId){
        art.dialog.open('/question/edit?questionId='+id+'&courseId='+courseId,{
            title: '编辑',
            width:600,
            height:500,
        });
    }
     /**
     * 查看栏目
     * @param catId
     */
    function detailQuestion(id,courseId){
        art.dialog.open('/question/detail?questionId='+id+'&courseId='+courseId,{
            title: '查看',
            width:600,
            height:500,
            cancelVal: '关闭',
            cancel: true
        });
    }

    /**
     * 添加栏目
     */
    function addQuestion(courseId,chapterId) {
        art.dialog.open('/question/add?courseId='+courseId+'&chapterId='+chapterId, {
            title: '添加试题',
            width: 600,
            height:500,
            close: function () {
                location.reload();
            }
        });
    }

    /**
     * 删除栏目
     * @param catId
     */
    function delQuestion(id,name){
        if (confirm("确认要删除"+name+"?")) {
            $.post("/question/delete", { id:id},
                    function(data){
                        data=eval('(' + data + ')');
                        if(data.retCode==0){
                            alert("删除成功");
                        } else {
                            alert("删除失败");
                        }
                        location.reload();
                    }
            );
        }
    }

    function toPage(page){
        var id = $("#id").val();
        var keyword = $("#keyword").val();
        var status = $("#status").val();
        
        var zhenti = $("#zhenti").val();
        var resourceId = $("#resourceId").val();
        var questionType = $("#questionType").val();
        
        var url = window.location.pathname+"?courseId=${courseId}&chapterId=${chapterId}&page="+page+"&keyword="+base64encode(utf16to8(encodeURIComponent(keyword)))+"&status="+status+"&id="+id+"&zhenti="+zhenti+"&questionType="+questionType + "&resourceId="+resourceId;
        self.location=url;
    }

    function searchK(){
        var id = $("#id").val();
        var keyword = $("#keyword").val();
        var status = $("#status").val();
        
        var zhenti = $("#zhenti").val();
        var resourceId = $("#resourceId").val();
        var questionType = $("#questionType").val();
        
        var url = window.location.pathname+"?courseId=${courseId}&chapterId=${chapterId}&keyword="+base64encode(utf16to8(encodeURIComponent(keyword)))+"&status="+status+"&id="+id+"&zhenti="+zhenti+"&questionType="+questionType + "&resourceId="+resourceId;
        self.location=url;
    }

    function selectall(name){
        if ($("#check_box").is(":checked")) {
            $("input[name='"+name+"']").each(function() {
                alert("1");
                $(this).attr("checked","checked");
            });
        } else {
            $("input[name='"+name+"']").each(function() {
                alert("0");
                $(this).removeAttr("checked");
            });
        }
    }
    // 点击批量导入按钮， 触发模态框，导入之前须选择课程
    function validatedIsChooseCourse(){
        var value = $("select[name='courseId']").val();
        if(value == '0'){
            layui.use('layer', function(){
              var layer = layui.layer;
              layer.msg('导入之前请先确定并选择左边的课程');
            });              
            return false;
        }else{
            $("#batchImport").attr("data-course",value);
            $('#importQuestions').modal();
        }
        
    }
    
    function verify(){
        var ids = "";
        $("input[name='ids']:checked").each(function() {
            ids = ids + $(this).val() + ";";
        });
        if(ids!=""){
            $.post("/question/verify", { ids:ids },
                    function(data){
                        alert("成功");
                        location.reload();
                    });
        } else {
            alert("请选择试题");
        }
    }

    /**
     * 设置关联关键词
     * @param resourceId
     * @param courseId
     */
    function setKeywords(resourceId,courseId,chapterId){
        art.dialog.open('/courseKeyword/keywordsMap?courseId='+courseId+'&chapterId='+chapterId+'&resourceType=5&resourceId='+resourceId, {
            title: '关联关键词',
            width: 600,
            height:400,
            cancelVal: '关闭',
            cancel: true
        });
    }
    
     //下面是64个基本的编码
     var base64EncodeChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";
     var base64DecodeChars = new Array(
         -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
         -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
         -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, -1, -1, 63,
         52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -1, -1, -1,
         -1,  0,  1,  2,  3,  4,  5,  6,  7,  8,  9, 10, 11, 12, 13, 14,
         15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, -1,
         -1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40,
         41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -1, -1, -1, -1, -1);
     //编码的方法
     function base64encode(str) {
         var out, i, len;
         var c1, c2, c3;
         len = str.length;
         i = 0;
         out = "";
         while(i < len) {
         c1 = str.charCodeAt(i++) & 0xff;
         if(i == len)
         {
             out += base64EncodeChars.charAt(c1 >> 2);
             out += base64EncodeChars.charAt((c1 & 0x3) << 4);
             out += "==";
             break;
         }
         c2 = str.charCodeAt(i++);
         if(i == len)
         {
             out += base64EncodeChars.charAt(c1 >> 2);
             out += base64EncodeChars.charAt(((c1 & 0x3)<< 4) | ((c2 & 0xF0) >> 4));
             out += base64EncodeChars.charAt((c2 & 0xF) << 2);
             out += "=";
             break;
         }
         c3 = str.charCodeAt(i++);
         out += base64EncodeChars.charAt(c1 >> 2);
         out += base64EncodeChars.charAt(((c1 & 0x3)<< 4) | ((c2 & 0xF0) >> 4));
         out += base64EncodeChars.charAt(((c2 & 0xF) << 2) | ((c3 & 0xC0) >>6));
         out += base64EncodeChars.charAt(c3 & 0x3F);
         }
         return out;
     }
    
     function utf16to8(str) {
         var out, i, len, c;
         out = "";
         len = str.length;
         for(i = 0; i < len; i++) {
             c = str.charCodeAt(i);
             if ((c >= 0x0001) && (c <= 0x007F)) {
                 out += str.charAt(i);
             } else if (c > 0x07FF) {
                 out += String.fromCharCode(0xE0 | ((c >> 12) & 0x0F));
                 out += String.fromCharCode(0x80 | ((c >>  6) & 0x3F));
                 out += String.fromCharCode(0x80 | ((c >>  0) & 0x3F));
             } else {
                 out += String.fromCharCode(0xC0 | ((c >>  6) & 0x1F));
                 out += String.fromCharCode(0x80 | ((c >>  0) & 0x3F));
             }
         }
         return out;
     }
</script>

        </@chapterframe>

</@htmlBody>
<@fileUpload></@fileUpload>