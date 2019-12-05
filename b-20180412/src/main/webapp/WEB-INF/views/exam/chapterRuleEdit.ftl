<#include "/common/layout.ftl" />
<@htmlHead title="首页">
</@htmlHead>
<@htmlBody bodyclass="page-content">

<style>
    .tableHead{
        font-weight: bold;
        text-align: center;
    }
    .tableHeadLeft{
        font-weight: bold;
        width: 80px;
        text-align: center;
    }
    .editorTd{
        color: red!important;
        text-align: center;
        border: none!important;
        background: none!important;
        width: 100%;
        padding: 0px!important;
        height: 100%;
    }
</style>

<form id="editForm" method="post" class="form-horizontal" role="form" action="/examRule/doEditChapterRule">
    <input type="hidden" name="id" value="${chapterRule.id}" />

    <div style="margin-bottom: 15px;">规则名称：<input type="text" name="ruleName" value="${chapterRule.name}" /></div>

    <table class="table table-striped table-bordered table-hover">

        <!--题目类型-->
        <tr class="success">

            <!--章节操作-->
            <td style="width: 80px;vertical-align: middle; text-align: center;" rowspan="4">
                <#if childChapters?size &gt; 0>
                    <p>
                        <input type="checkbox" class="setChildChapter" name="setChildChapter" <#assign key = "chapter"+chapter.id /> <#if hasChildChaptersMap[key]=="1">checked</#if> value="${chapter.id}" />
                        <a>设置节</a>
                    </p>
                </#if>
            </td>
            <td class="tableHeadLeft"></td>
            <!--客观题类型*3-->
            <#if 3*keguan?size &gt; 0>
                <td colspan="${3*keguan?size}" class="tableHead">客观题</td>
            </#if>
            <!--主观题类型*3-->
            <#if 3*zhuguan?size &gt; 0>
                <td colspan="${3*zhuguan?size}" class="tableHead">主观题</td>
            </#if>
        </tr>

        <!--题型[分值]-->
        <tr class="success">
            <td class="tableHeadLeft">题型</td>
            <!--客观题分数-->
            <#list keguan as type>
                <td colspan="3" class="tableHead">${type.typeName}[${type.score}分]</td>
            </#list>
            <!--主观题分数-->
            <#list zhuguan as type>
                <td colspan="3" class="tableHead">${type.typeName}[${type.score}分]</td>
            </#list>
        </tr>

        <!--出题数目-->
        <tr class="success">
            <td class="tableHeadLeft">题数</td>
            <!--客观题题目数-->
            <#list keguan as type>
                <td colspan="3" class="tableHead" id="typeQuestionNum${type.id}">
                    <input type="text" class="editorTd" />
                </td>
            </#list>
            <!--主管题题目数-->
            <#list zhuguan as type>
                <td colspan="3" class="tableHead" id="typeQuestionNum${type.id}">
                    <input type="text" class="editorTd" />
                </td>
            </#list>
        </tr>

        <!--难易程度A，B，C-->
        <tr class="success">
            <td class="tableHeadLeft">认知层次\难易度</td>
            <#list setting.typeList as type>
                <td class="tableHead">A</td>
                <td class="tableHead">B</td>
                <td class="tableHead">C</td>
            </#list>
        </tr>

        <!--章节列表-->

        <!--认知程度1-->
        <tr class="chapterTr chapterTr${chapter.id}">
            <td rowspan="3" style="vertical-align: middle; text-align: center;">
                <p>${chapter.chapterId}[${chapter.name}]</p>
            </td>
            <td class="tableHeadLeft">Ⅰ</td>
            <!--客观-->
            <#list keguan as type>
                <td><input type="text" data-score="${type.score}" class="editorTd chapter${chapter.id} chapterEaseA chapterCognitionⅠ type${type.id}" name="chapter_${chapter.id}_${type.id}_A_Ⅰ" id="${chapter.id}_A_Ⅰ" value="<#assign key='chapter_'+chapter.id+'_'+type.id+'_A_Ⅰ'>${numMap[key]}"  /></td>
                <td><input type="text" data-score="${type.score}" class="editorTd chapter${chapter.id} chapterEaseB chapterCognitionⅠ type${type.id}" name="chapter_${chapter.id}_${type.id}_B_Ⅰ" id="${chapter.id}_B_Ⅰ" value="<#assign key='chapter_'+chapter.id+'_'+type.id+'_B_Ⅰ'>${numMap[key]}"   /></td>
                <td><input type="text" data-score="${type.score}" class="editorTd chapter${chapter.id} chapterEaseC chapterCognitionⅠ type${type.id}" name="chapter_${chapter.id}_${type.id}_C_Ⅰ" id="${chapter.id}_C_Ⅰ" value="<#assign key='chapter_'+chapter.id+'_'+type.id+'_C_Ⅰ'>${numMap[key]}"   /></td>
            </#list>

            <!--主观-->
            <#list zhuguan as type>
                <td><input type="text" data-score="${type.score}" class="editorTd chapter${chapter.id} chapterEaseA chapterCognitionⅠ type${type.id}" name="chapter_${chapter.id}_${type.id}_A_Ⅰ" id="${chapter.id}_A_Ⅰ" value="<#assign key='chapter_'+chapter.id+'_'+type.id+'_A_Ⅰ'>${numMap[key]}" /></td>
                <td><input type="text" data-score="${type.score}" class="editorTd chapter${chapter.id} chapterEaseB chapterCognitionⅠ type${type.id}" name="chapter_${chapter.id}_${type.id}_B_Ⅰ" id="${chapter.id}_B_Ⅰ" value="<#assign key='chapter_'+chapter.id+'_'+type.id+'_B_Ⅰ'>${numMap[key]}" /></td>
                <td><input type="text" data-score="${type.score}" class="editorTd chapter${chapter.id} chapterEaseC chapterCognitionⅠ type${type.id}" name="chapter_${chapter.id}_${type.id}_C_Ⅰ" id="${chapter.id}_C_Ⅰ" value="<#assign key='chapter_'+chapter.id+'_'+type.id+'_C_Ⅰ'>${numMap[key]}" /></td>
            </#list>
        </tr>
        <!--认知程度2-->
        <tr class="chapterTr chapterTr${chapter.id}">
            <td class="tableHeadLeft" >Ⅱ</td>

            <!--客观-->
            <#list keguan as type>
                <td><input type="text" data-score="${type.score}" class="editorTd chapter${chapter.id} chapterEaseA chapterCognitionⅡ type${type.id}" name="chapter_${chapter.id}_${type.id}_A_Ⅱ" id="${chapter.id}_${type.id}_A_Ⅱ" value="<#assign key='chapter_'+chapter.id+'_'+type.id+'_A_Ⅱ'>${numMap[key]}" /></td>
                <td><input type="text" data-score="${type.score}" class="editorTd chapter${chapter.id} chapterEaseB chapterCognitionⅡ type${type.id}" name="chapter_${chapter.id}_${type.id}_B_Ⅱ" id="${chapter.id}_${type.id}_B_Ⅱ" value="<#assign key='chapter_'+chapter.id+'_'+type.id+'_B_Ⅱ'>${numMap[key]}" /></td>
                <td><input type="text" data-score="${type.score}" class="editorTd chapter${chapter.id} chapterEaseC chapterCognitionⅡ type${type.id}" name="chapter_${chapter.id}_${type.id}_C_Ⅱ" id="${chapter.id}_${type.id}_C_Ⅱ" value="<#assign key='chapter_'+chapter.id+'_'+type.id+'_C_Ⅱ'>${numMap[key]}" /></td>
            </#list>

            <!--主观-->
            <#list zhuguan as type>
                <td><input type="text" data-score="${type.score}" class="editorTd chapter${chapter.id} chapterEaseA chapterCognitionⅡ type${type.id}" name="chapter_${chapter.id}_${type.id}_A_Ⅱ" id="${chapter.id}_${type.id}_A_Ⅱ" value="<#assign key='chapter_'+chapter.id+'_'+type.id+'_A_Ⅱ'>${numMap[key]}" /></td>
                <td><input type="text" data-score="${type.score}" class="editorTd chapter${chapter.id} chapterEaseB chapterCognitionⅡ type${type.id}" name="chapter_${chapter.id}_${type.id}_B_Ⅱ" id="${chapter.id}_${type.id}_B_Ⅱ" value="<#assign key='chapter_'+chapter.id+'_'+type.id+'_B_Ⅱ'>${numMap[key]}" /></td>
                <td><input type="text" data-score="${type.score}" class="editorTd chapter${chapter.id} chapterEaseC chapterCognitionⅡ type${type.id}" name="chapter_${chapter.id}_${type.id}_C_Ⅱ" id="${chapter.id}_${type.id}_C_Ⅱ" value="<#assign key='chapter_'+chapter.id+'_'+type.id+'_C_Ⅱ'>${numMap[key]}" /></td>
            </#list>
        </tr>
        <!--认知程度3-->
        <tr class="chapterTr chapterTr${chapter.id}">
            <td class="tableHeadLeft">Ⅲ</td>

            <!--客观-->
            <#list keguan as type>
                <td><input type="text" data-score="${type.score}" class="editorTd chapter${chapter.id} chapterEaseA chapterCognitionⅢ type${type.id}" name="chapter_${chapter.id}_${type.id}_A_Ⅲ" id="${chapter.id}_${type.id}_A_Ⅲ" value="<#assign key='chapter_'+chapter.id+'_'+type.id+'_A_Ⅲ'>${numMap[key]}" /></td>
                <td><input type="text" data-score="${type.score}" class="editorTd chapter${chapter.id} chapterEaseB chapterCognitionⅢ type${type.id}" name="chapter_${chapter.id}_${type.id}_B_Ⅲ" id="${chapter.id}_${type.id}_B_Ⅲ" value="<#assign key='chapter_'+chapter.id+'_'+type.id+'_B_Ⅲ'>${numMap[key]}" /></td>
                <td><input type="text" data-score="${type.score}" class="editorTd chapter${chapter.id} chapterEaseC chapterCognitionⅢ type${type.id}" name="chapter_${chapter.id}_${type.id}_C_Ⅲ" id="${chapter.id}_${type.id}_C_Ⅲ" value="<#assign key='chapter_'+chapter.id+'_'+type.id+'_C_Ⅲ'>${numMap[key]}" /></td>
            </#list>

            <!--主观-->
            <#list zhuguan as type>
                <td><input type="text" data-score="${type.score}" class="editorTd chapter${chapter.id} chapterEaseA chapterCognitionⅢ type${type.id}" name="chapter_${chapter.id}_${type.id}_A_Ⅲ" id="${chapter.id}_${type.id}_A_Ⅲ" value="<#assign key='chapter_'+chapter.id+'_'+type.id+'_A_Ⅲ'>${numMap[key]}" /></td>
                <td><input type="text" data-score="${type.score}" class="editorTd chapter${chapter.id} chapterEaseB chapterCognitionⅢ type${type.id}" name="chapter_${chapter.id}_${type.id}_B_Ⅲ" id="${chapter.id}_${type.id}_B_Ⅲ" value="<#assign key='chapter_'+chapter.id+'_'+type.id+'_B_Ⅲ'>${numMap[key]}" /></td>
                <td><input type="text" data-score="${type.score}" class="editorTd chapter${chapter.id} chapterEaseC chapterCognitionⅢ type${type.id}" name="chapter_${chapter.id}_${type.id}_C_Ⅲ" id="${chapter.id}_${type.id}_C_Ⅲ" value="<#assign key='chapter_'+chapter.id+'_'+type.id+'_C_Ⅲ'>${numMap[key]}" /></td>
            </#list>
        </tr>

        <!--章节下属列表-->
        <#list childChapters as childchapter>

            <!--认知程度1-->
            <tr class="childChapterTr childChapterTr${chapter.id} warning chapterTr${chapter.id}" style="display: none;" >
                <td rowspan="3" style="vertical-align: middle; text-align: center;">
                    <p>${childchapter.name}</p>
                </td>
                <td class="tableHeadLeft">Ⅰ</td>

                <!--客观-->
                <#list keguan as type>
                    <td><input type="text" data-score="${type.score}" class="editorTd childChapter${childchapter.id} childChapterEaseA childChapterCognitionⅠ childtype${type.id}" name="childChapter_${childchapter.id}_${type.id}_A_Ⅰ" id="child_${childchapter.id}_${type.id}_A_Ⅰ" value="<#assign key='chapter_'+childchapter.id+'_'+type.id+'_A_Ⅰ'>${numMap[key]}"   /></td>
                    <td><input type="text" data-score="${type.score}" class="editorTd childChapter${childchapter.id} childChapterEaseB childChapterCognitionⅠ childtype${type.id}" name="childChapter_${childchapter.id}_${type.id}_B_Ⅰ" id="child_${childchapter.id}_${type.id}_B_Ⅰ" value="<#assign key='chapter_'+childchapter.id+'_'+type.id+'_B_Ⅰ'>${numMap[key]}"   /></td>
                    <td><input type="text" data-score="${type.score}" class="editorTd childChapter${childchapter.id} childChapterEaseC childChapterCognitionⅠ childtype${type.id}" name="childChapter_${childchapter.id}_${type.id}_C_Ⅰ" id="child_${childchapter.id}_${type.id}_C_Ⅰ" value="<#assign key='chapter_'+childchapter.id+'_'+type.id+'_C_Ⅰ'>${numMap[key]}"   /></td>
                </#list>

                <!--主观-->
                <#list zhuguan as type>
                    <td><input type="text" data-score="${type.score}" class="editorTd childChapter${childchapter.id} childChapterEaseA childChapterCognitionⅠ childtype${type.id}" name="childChapter_${childchapter.id}_${type.id}_A_Ⅰ" id="child_${childchapter.id}_${type.id}_A_Ⅰ" value="<#assign key='chapter_'+childchapter.id+'_'+type.id+'_A_Ⅰ'>${numMap[key]}"  /></td>
                    <td><input type="text" data-score="${type.score}" class="editorTd childChapter${childchapter.id} childChapterEaseB childChapterCognitionⅠ childtype${type.id}" name="childChapter_${childchapter.id}_${type.id}_B_Ⅰ" id="child_${childchapter.id}_${type.id}_C_Ⅰ" value="<#assign key='chapter_'+childchapter.id+'_'+type.id+'_B_Ⅰ'>${numMap[key]}"   /></td>
                    <td><input type="text" data-score="${type.score}" class="editorTd childChapter${childchapter.id} childChapterEaseC childChapterCognitionⅠ childtype${type.id}" name="childChapter_${childchapter.id}_${type.id}_C_Ⅰ" id="child_${childchapter.id}_${type.id}_C_Ⅰ" value="<#assign key='chapter_'+childchapter.id+'_'+type.id+'_C_Ⅰ'>${numMap[key]}"   /></td>
                </#list>
            </tr>

            <!--认知程度2-->
            <tr class="childChapterTr childChapterTr${chapter.id} warning chapterTr${chapter.id}" style="display: none;" >
                <td class="tableHeadLeft" >Ⅱ</td>

                <!--客观-->
                <#list keguan as type>
                    <td><input type="text" data-score="${type.score}" class="editorTd childChapter${childchapter.id} childChapterEaseA childChapterCognitionⅡ childtype${type.id}" name="childChapter_${childchapter.id}_${type.id}_A_Ⅱ" id="child_${childchapter.id}_${type.id}_A_Ⅱ" value="<#assign key='chapter_'+childchapter.id+'_'+type.id+'_A_Ⅱ'>${numMap[key]}"   /></td>
                    <td><input type="text" data-score="${type.score}" class="editorTd childChapter${childchapter.id} childChapterEaseB childChapterCognitionⅡ childtype${type.id}" name="childChapter_${childchapter.id}_${type.id}_B_Ⅱ" id="child_${childchapter.id}_${type.id}_B_Ⅱ" value="<#assign key='chapter_'+childchapter.id+'_'+type.id+'_B_Ⅱ'>${numMap[key]}"   /></td>
                    <td><input type="text" data-score="${type.score}" class="editorTd childChapter${childchapter.id} childChapterEaseC childChapterCognitionⅡ childtype${type.id}" name="childChapter_${childchapter.id}_${type.id}_C_Ⅱ" id="child_${childchapter.id}_${type.id}_C_Ⅱ" value="<#assign key='chapter_'+childchapter.id+'_'+type.id+'_C_Ⅱ'>${numMap[key]}"   /></td>
                </#list>

                <!--主观-->
                <#list zhuguan as type>
                    <td><input type="text" data-score="${type.score}" class="editorTd childChapter${childchapter.id} childChapterEaseA childChapterCognitionⅡ childtype${type.id}" name="childChapter_${childchapter.id}_${type.id}_A_Ⅱ" id="child_${childchapter.id}_${type.id}_A_Ⅱ" value="<#assign key='chapter_'+childchapter.id+'_'+type.id+'_A_Ⅱ'>${numMap[key]}"   /></td>
                    <td><input type="text" data-score="${type.score}" class="editorTd childChapter${childchapter.id} childChapterEaseB childChapterCognitionⅡ childtype${type.id}" name="childChapter_${childchapter.id}_${type.id}_B_Ⅱ" id="child_${childchapter.id}_${type.id}_B_Ⅱ" value="<#assign key='chapter_'+childchapter.id+'_'+type.id+'_B_Ⅱ'>${numMap[key]}"   /></td>
                    <td><input type="text" data-score="${type.score}" class="editorTd childChapter${childchapter.id} childChapterEaseC childChapterCognitionⅡ childtype${type.id}" name="childChapter_${childchapter.id}_${type.id}_C_Ⅱ" id="child_${childchapter.id}_${type.id}_C_Ⅱ" value="<#assign key='chapter_'+childchapter.id+'_'+type.id+'_C_Ⅱ'>${numMap[key]}"  /></td>
                </#list>
            </tr>

            <!--认知程度3-->
            <tr class="childChapterTr childChapterTr${chapter.id} warning chapterTr${chapter.id}" style="display: none;" >
                <td class="tableHeadLeft">Ⅲ</td>

                <!--客观-->
                <#list keguan as type>
                    <td><input type="text" data-score="${type.score}" class="editorTd childChapter${childchapter.id} childChapterEaseA childChapterCognitionⅢ childtype${type.id}" name="childChapter_${childchapter.id}_${type.id}_A_Ⅲ" id="child_${childchapter.id}_${type.id}_A_Ⅲ" value="<#assign key='chapter_'+childchapter.id+'_'+type.id+'_A_Ⅲ'>${numMap[key]}"   /></td>
                    <td><input type="text" data-score="${type.score}" class="editorTd childChapter${childchapter.id} childChapterEaseB childChapterCognitionⅢ childtype${type.id}" name="childChapter_${childchapter.id}_${type.id}_B_Ⅲ" id="child_${childchapter.id}_${type.id}_B_Ⅲ" value="<#assign key='chapter_'+childchapter.id+'_'+type.id+'_B_Ⅲ'>${numMap[key]}"   /></td>
                    <td><input type="text" data-score="${type.score}" class="editorTd childChapter${childchapter.id} childChapterEaseC childChapterCognitionⅢ childtype${type.id}" name="childChapter_${childchapter.id}_${type.id}_C_Ⅲ" id="child_${childchapter.id}_${type.id}_C_Ⅲ" value="<#assign key='chapter_'+childchapter.id+'_'+type.id+'_C_Ⅲ'>${numMap[key]}"  /></td>
                </#list>

                <!--主观-->
                <#list zhuguan as type>
                    <td><input type="text" data-score="${type.score}" class="editorTd childChapter${childchapter.id} childChapterEaseA childChapterCognitionⅢ childtype${type.id}" name="childChapter_${childchapter.id}_${type.id}_A_Ⅲ" id="child_${childchapter.id}_${type.id}_A_Ⅲ" value="<#assign key='chapter_'+childchapter.id+'_'+type.id+'_A_Ⅲ'>${numMap[key]}"   /></td>
                    <td><input type="text" data-score="${type.score}" class="editorTd childChapter${childchapter.id} childChapterEaseB childChapterCognitionⅢ childtype${type.id}" name="childChapter_${childchapter.id}_${type.id}_B_Ⅲ" id="child_${childchapter.id}_${type.id}_B_Ⅲ" value="<#assign key='chapter_'+childchapter.id+'_'+type.id+'_B_Ⅲ'>${numMap[key]}"   /></td>
                    <td><input type="text" data-score="${type.score}" class="editorTd childChapter${childchapter.id} childChapterEaseC childChapterCognitionⅢ childtype${type.id}" name="childChapter_${childchapter.id}_${type.id}_C_Ⅲ" id="child_${childchapter.id}_${type.id}_C_Ⅲ" value="<#assign key='chapter_'+childchapter.id+'_'+type.id+'_C_Ⅲ'>${numMap[key]}"  /></td>
                </#list>
            </tr>
        </#list>

    </table>

</form>

<script>

    /**
     * 展示节
     */
    function showChildChapter(chapterId){
        $(".childChapterTr"+chapterId).show();
    }

    function hideChildChapter(chapterId){
        $(".childChapterTr"+chapterId).hide();
    }

    $(document).ready(function(){

        $(".setChildChapter").each(function(){
            if($(this).is(":checked")){
                showChildChapter($(this).val());
            } else {
                hideChildChapter($(this).val());
            }
        });

        $(".setChildChapter").click(function () {
            var chapterId = $(this).val();
            if($(this).is(":checked")){
                showChildChapter(chapterId);
            } else {
                hideChildChapter(chapterId);
            }
        });
    });
</script>


</@htmlBody>
