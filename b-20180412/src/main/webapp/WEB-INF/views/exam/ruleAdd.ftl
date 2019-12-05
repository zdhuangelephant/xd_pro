<#include "/common/layout.ftl" />
<@htmlHead title="首页">
</@htmlHead>
<@htmlBody bodyclass="page-content">

<div class="page-header">
    <h1>
        添加命题蓝图
        <small>
            <i class="ace-icon fa fa-angle-double-right"></i>
        ${product.name}
        </small>
    </h1>
</div>

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

<form id="addForm" method="post" class="form-horizontal" role="form" action="/examRule/doAddRule">
    <input type="hidden" name="productId" value="${productId}" />

    <div>
		<span style="margin-bottom: 15px;">练习类型：<select name="ruleType" >
			<option value="-1">请选择练习类型</option>
			<#list examTypeList as examType>
				<option value="${examType.code}">${examType.showName}</option>
            </#list>
		</select></span>

		<span style="margin-bottom: 15px;">规则名称：<input type="text" name="ruleName" /></span>
	</div>

	<table class="table table-striped table-bordered table-hover">

        <!--题目类型-->
        <tr class="success">

            <!--章节操作-->
            <td style="width: 80px;vertical-align: middle; text-align: center;" rowspan="4">
                <!--<a id="showUrl" onclick="showChapter()" style="cursor: pointer;display: none;">
                    展开章节
                </a>
                <a id="hideUrl" onclick="closeChapter()" style="cursor: pointer;">
                    闭合章节
                </a>-->
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
            <td rowspan="4" style="width: 50px;">预期章节百分比</td>
            <td rowspan="4" style="width: 50px;">章节题数统计</td>
            <td rowspan="4" style="width: 50px;">章节分值统计</td>
            <td rowspan="4" style="width: 50px;">重点章节</td>
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
                <td colspan="3" class="tableHead" id="typeQuestionNum${type.id}">${type.questionNum}</td>
            </#list>
            <!--主管题题目数-->
            <#list zhuguan as type>
                <td colspan="3" class="tableHead" id="typeQuestionNum${type.id}">${type.questionNum}</td>
            </#list>
        </tr>

        <!--难易程度A，B，C-->
        <tr class="success">
            <td class="tableHeadLeft"></td>
            <#list setting.typeList as type>
                <td class="tableHead">A</td>
                <td class="tableHead">B</td>
                <td class="tableHead">C</td>
            </#list>
        </tr>

        <!--章节列表-->
        <#list chapters as chapter>

            <!--认知程度1-->
            <tr class="chapterTr chapterTr${chapter.id}" style="display: none;">
                <td rowspan="3" style="vertical-align: middle; text-align: center;">
                    <p>${chapter.chapterId}[${chapter.name}]</p>
                    <#if chapter.childList?size &gt; 0>
                        <p>
                            <input type="checkbox" class="setChildChapter" name="setChildChapter" value="${chapter.id}" />
                            <a>设置节</a>
                        </p>
                        <p>
                            <a>检查</a>
                        </p>
                    </#if>
                </td>
                <td class="tableHeadLeft">Ⅰ</td>

                <!--客观-->
                <#list keguan as type>
                    <td><input type="text" data-score="${type.score}" class="editorTd chapter${chapter.id} chapterEaseA chapterCognitionⅠ type${type.id}" name="chapter_${chapter.id}_${type.id}_A_Ⅰ" id="${chapter.id}_A_Ⅰ"  /></td>
                    <td><input type="text" data-score="${type.score}" class="editorTd chapter${chapter.id} chapterEaseB chapterCognitionⅠ type${type.id}" name="chapter_${chapter.id}_${type.id}_B_Ⅰ" id="${chapter.id}_B_Ⅰ"  /></td>
                    <td><input type="text" data-score="${type.score}" class="editorTd chapter${chapter.id} chapterEaseC chapterCognitionⅠ type${type.id}" name="chapter_${chapter.id}_${type.id}_C_Ⅰ" id="${chapter.id}_C_Ⅰ"  /></td>
                </#list>

                <!--主观-->
                <#list zhuguan as type>
                    <td><input type="text" data-score="${type.score}" class="editorTd chapter${chapter.id} chapterEaseA chapterCognitionⅠ type${type.id}" name="chapter_${chapter.id}_${type.id}_A_Ⅰ" id="${chapter.id}_A_Ⅰ"  /></td>
                    <td><input type="text" data-score="${type.score}" class="editorTd chapter${chapter.id} chapterEaseB chapterCognitionⅠ type${type.id}" name="chapter_${chapter.id}_${type.id}_B_Ⅰ" id="${chapter.id}_C_Ⅰ"  /></td>
                    <td><input type="text" data-score="${type.score}" class="editorTd chapter${chapter.id} chapterEaseC chapterCognitionⅠ type${type.id}" name="chapter_${chapter.id}_${type.id}_C_Ⅰ" id="${chapter.id}_C_Ⅰ"  /></td>
                </#list>

                <!--章节统计-->
                <td rowspan="3"><input type="text" class="editorTd"  /></td>
                <td rowspan="3" style="vertical-align: middle;" id="chapter${chapter.id}_question_sum"></td>
                <td rowspan="3" style="vertical-align: middle;" id="chapter${chapter.id}_score_sum"></td>
                <td rowspan="3" style="text-align: center;vertical-align: middle">
                    <p><input type="checkbox" style="" class="importChapter" name="importChapter" data-chapterId="${chapter.id}" value="${chapter.id}" /></p>
                </td>
            </tr>

            <!--认知程度2-->
            <tr class="chapterTr chapterTr${chapter.id}" style="display: none;">
                <td class="tableHeadLeft" >Ⅱ</td>

                <!--客观-->
                <#list keguan as type>
                    <td><input type="text" data-score="${type.score}" class="editorTd chapter${chapter.id} chapterEaseA chapterCognitionⅡ type${type.id}" name="chapter_${chapter.id}_${type.id}_A_Ⅱ" id="${chapter.id}_${type.id}_A_Ⅱ"  /></td>
                    <td><input type="text" data-score="${type.score}" class="editorTd chapter${chapter.id} chapterEaseB chapterCognitionⅡ type${type.id}" name="chapter_${chapter.id}_${type.id}_B_Ⅱ" id="${chapter.id}_${type.id}_B_Ⅱ"  /></td>
                    <td><input type="text" data-score="${type.score}" class="editorTd chapter${chapter.id} chapterEaseC chapterCognitionⅡ type${type.id}" name="chapter_${chapter.id}_${type.id}_C_Ⅱ" id="${chapter.id}_${type.id}_C_Ⅱ"  /></td>
                </#list>

                <!--主观-->
                <#list zhuguan as type>
                    <td><input type="text" data-score="${type.score}" class="editorTd chapter${chapter.id} chapterEaseA chapterCognitionⅡ type${type.id}" name="chapter_${chapter.id}_${type.id}_A_Ⅱ" id="${chapter.id}_${type.id}_A_Ⅱ"  /></td>
                    <td><input type="text" data-score="${type.score}" class="editorTd chapter${chapter.id} chapterEaseB chapterCognitionⅡ type${type.id}" name="chapter_${chapter.id}_${type.id}_B_Ⅱ" id="${chapter.id}_${type.id}_B_Ⅱ"  /></td>
                    <td><input type="text" data-score="${type.score}" class="editorTd chapter${chapter.id} chapterEaseC chapterCognitionⅡ type${type.id}" name="chapter_${chapter.id}_${type.id}_C_Ⅱ" id="${chapter.id}_${type.id}_C_Ⅱ"  /></td>
                </#list>
            </tr>

            <!--认知程度3-->
            <tr class="chapterTr chapterTr${chapter.id}" style="display: none;">
                <td class="tableHeadLeft">Ⅲ</td>

                <!--客观-->
                <#list keguan as type>
                    <td><input type="text" data-score="${type.score}" class="editorTd chapter${chapter.id} chapterEaseA chapterCognitionⅢ type${type.id}" name="chapter_${chapter.id}_${type.id}_A_Ⅲ" id="${chapter.id}_${type.id}_A_Ⅲ"  /></td>
                    <td><input type="text" data-score="${type.score}" class="editorTd chapter${chapter.id} chapterEaseB chapterCognitionⅢ type${type.id}" name="chapter_${chapter.id}_${type.id}_B_Ⅲ" id="${chapter.id}_${type.id}_B_Ⅲ"  /></td>
                    <td><input type="text" data-score="${type.score}" class="editorTd chapter${chapter.id} chapterEaseC chapterCognitionⅢ type${type.id}" name="chapter_${chapter.id}_${type.id}_C_Ⅲ" id="${chapter.id}_${type.id}_C_Ⅲ"  /></td>
                </#list>

                <!--主观-->
                <#list zhuguan as type>
                    <td><input type="text" data-score="${type.score}" class="editorTd chapter${chapter.id} chapterEaseA chapterCognitionⅢ type${type.id}" name="chapter_${chapter.id}_${type.id}_A_Ⅲ" id="${chapter.id}_${type.id}_A_Ⅲ"  /></td>
                    <td><input type="text" data-score="${type.score}" class="editorTd chapter${chapter.id} chapterEaseB chapterCognitionⅢ type${type.id}" name="chapter_${chapter.id}_${type.id}_B_Ⅲ" id="${chapter.id}_${type.id}_B_Ⅲ"  /></td>
                    <td><input type="text" data-score="${type.score}" class="editorTd chapter${chapter.id} chapterEaseC chapterCognitionⅢ type${type.id}" name="chapter_${chapter.id}_${type.id}_C_Ⅲ" id="${chapter.id}_${type.id}_C_Ⅲ"  /></td>
                </#list>
            </tr>

        <!--章节列表-->


            <#list chapter.childList as childchapter>

            <!--认知程度1-->
            <tr class="childChapterTr childChapterTr${chapter.id} warning chapterTr${chapter.id}" style="display: none;">
                <td rowspan="3" style="vertical-align: middle; text-align: center;">
                    <p>${childchapter.chapterId}[${childchapter.name}]</p>
                </td>
                <td class="tableHeadLeft">Ⅰ</td>

                <!--客观-->
                <#list keguan as type>
                    <td><input type="text" data-score="${type.score}" class="editorTd childChapter${childchapter.id} childChapterEaseA childChapterCognitionⅠ childtype${type.id}" name="childChapter_${childchapter.id}_${type.id}_A_Ⅰ" id="child_${childchapter.id}_${type.id}_A_Ⅰ"  /></td>
                    <td><input type="text" data-score="${type.score}" class="editorTd childChapter${childchapter.id} childChapterEaseB childChapterCognitionⅠ childtype${type.id}" name="childChapter_${childchapter.id}_${type.id}_B_Ⅰ" id="child_${childchapter.id}_${type.id}_B_Ⅰ"  /></td>
                    <td><input type="text" data-score="${type.score}" class="editorTd childChapter${childchapter.id} childChapterEaseC childChapterCognitionⅠ childtype${type.id}" name="childChapter_${childchapter.id}_${type.id}_C_Ⅰ" id="child_${childchapter.id}_${type.id}_C_Ⅰ"  /></td>
                </#list>

                <!--主观-->
                <#list zhuguan as type>
                    <td><input type="text" data-score="${type.score}" class="editorTd childChapter${childchapter.id} childChapterEaseA childChapterCognitionⅠ childtype${type.id}" name="childChapter_${childchapter.id}_${type.id}_A_Ⅰ" id="child_${childchapter.id}_${type.id}_A_Ⅰ"  /></td>
                    <td><input type="text" data-score="${type.score}" class="editorTd childChapter${childchapter.id} childChapterEaseB childChapterCognitionⅠ childtype${type.id}" name="childChapter_${childchapter.id}_${type.id}_B_Ⅰ" id="child_${childchapter.id}_${type.id}_C_Ⅰ"  /></td>
                    <td><input type="text" data-score="${type.score}" class="editorTd childChapter${childchapter.id} childChapterEaseC childChapterCognitionⅠ childtype${type.id}" name="childChapter_${childchapter.id}_${type.id}_C_Ⅰ" id="child_${childchapter.id}_${type.id}_C_Ⅰ"  /></td>
                </#list>

                <!--章节统计-->
                <td rowspan="3"><input type="text" class="editorTd"  /></td>
                <td rowspan="3" style="vertical-align: middle;" id="childChapter${childchapter.id}_question_sum"></td>
                <td rowspan="3" style="vertical-align: middle;" id="childChapter${childchapter.id}_score_sum"></td>
                <td rowspan="3"></td>
            </tr>

            <!--认知程度2-->
            <tr class="childChapterTr childChapterTr${chapter.id} warning chapterTr${chapter.id}" style="display: none;">
                <td class="tableHeadLeft" >Ⅱ</td>

                <!--客观-->
                <#list keguan as type>
                    <td><input type="text" data-score="${type.score}" class="editorTd childChapter${childchapter.id} childChapterEaseA childChapterCognitionⅡ childtype${type.id}" name="childChapter_${childchapter.id}_${type.id}_A_Ⅱ" id="child_${childchapter.id}_${type.id}_A_Ⅱ"  /></td>
                    <td><input type="text" data-score="${type.score}" class="editorTd childChapter${childchapter.id} childChapterEaseB childChapterCognitionⅡ childtype${type.id}" name="childChapter_${childchapter.id}_${type.id}_B_Ⅱ" id="child_${childchapter.id}_${type.id}_B_Ⅱ"  /></td>
                    <td><input type="text" data-score="${type.score}" class="editorTd childChapter${childchapter.id} childChapterEaseC childChapterCognitionⅡ childtype${type.id}" name="childChapter_${childchapter.id}_${type.id}_C_Ⅱ" id="child_${childchapter.id}_${type.id}_C_Ⅱ"  /></td>
                </#list>

                <!--主观-->
                <#list zhuguan as type>
                    <td><input type="text" data-score="${type.score}" class="editorTd childChapter${childchapter.id} childChapterEaseA childChapterCognitionⅡ childtype${type.id}" name="childChapter_${childchapter.id}_${type.id}_A_Ⅱ" id="child_${childchapter.id}_${type.id}_A_Ⅱ"  /></td>
                    <td><input type="text" data-score="${type.score}" class="editorTd childChapter${childchapter.id} childChapterEaseB childChapterCognitionⅡ childtype${type.id}" name="childChapter_${childchapter.id}_${type.id}_B_Ⅱ" id="child_${childchapter.id}_${type.id}_B_Ⅱ"  /></td>
                    <td><input type="text" data-score="${type.score}" class="editorTd childChapter${childchapter.id} childChapterEaseC childChapterCognitionⅡ childtype${type.id}" name="childChapter_${childchapter.id}_${type.id}_C_Ⅱ" id="child_${childchapter.id}_${type.id}_C_Ⅱ"  /></td>
                </#list>
            </tr>

            <!--认知程度3-->
            <tr class="childChapterTr childChapterTr${chapter.id} warning chapterTr${chapter.id}" style="display: none;">
                <td class="tableHeadLeft">Ⅲ</td>

                <!--客观-->
                <#list keguan as type>
                    <td><input type="text" data-score="${type.score}" class="editorTd childChapter${childchapter.id} childChapterEaseA childChapterCognitionⅢ childtype${type.id}" name="childChapter_${childchapter.id}_${type.id}_A_Ⅲ" id="child_${childchapter.id}_${type.id}_A_Ⅲ"  /></td>
                    <td><input type="text" data-score="${type.score}" class="editorTd childChapter${childchapter.id} childChapterEaseB childChapterCognitionⅢ childtype${type.id}" name="childChapter_${childchapter.id}_${type.id}_B_Ⅲ" id="child_${childchapter.id}_${type.id}_B_Ⅲ"  /></td>
                    <td><input type="text" data-score="${type.score}" class="editorTd childChapter${childchapter.id} childChapterEaseC childChapterCognitionⅢ childtype${type.id}" name="childChapter_${childchapter.id}_${type.id}_C_Ⅲ" id="child_${childchapter.id}_${type.id}_C_Ⅲ"  /></td>
                </#list>

                <!--主观-->
                <#list zhuguan as type>
                    <td><input type="text" data-score="${type.score}" class="editorTd childChapter${childchapter.id} childChapterEaseA childChapterCognitionⅢ childtype${type.id}" name="childChapter_${childchapter.id}_${type.id}_A_Ⅲ" id="child_${childchapter.id}_${type.id}_A_Ⅲ"  /></td>
                    <td><input type="text" data-score="${type.score}" class="editorTd childChapter${childchapter.id} childChapterEaseB childChapterCognitionⅢ childtype${type.id}" name="childChapter_${childchapter.id}_${type.id}_B_Ⅲ" id="child_${childchapter.id}_${type.id}_B_Ⅲ"  /></td>
                    <td><input type="text" data-score="${type.score}" class="editorTd childChapter${childchapter.id} childChapterEaseC childChapterCognitionⅢ childtype${type.id}" name="childChapter_${childchapter.id}_${type.id}_C_Ⅲ" id="child_${childchapter.id}_${type.id}_C_Ⅲ"  /></td>
                </#list>
            </tr>
            </#list>

        </#list>

        <!--总计一栏-->
        <tr class="success">
            <td rowspan="3" style="vertical-align: middle; text-align: center;">总计</td>
            <td class="tableHeadLeft">Ⅰ</td>
            <#list keguan as type>
                <td><input type="text" data-score="${type.score}" class="editorTd chaptersum chaptersumA chaptersumⅠ sumtype${type.id}" name="chaptersum_${product.id}_${type.id}_A_Ⅰ" id="sum_${product.id}_${type.id}_A_Ⅰ" /></td>
                <td><input type="text" data-score="${type.score}" class="editorTd chaptersum chaptersumB chaptersumⅠ sumtype${type.id}" name="chaptersum_${product.id}_${type.id}_B_Ⅰ" id="sum_${product.id}_${type.id}_B_Ⅰ"  /></td>
                <td><input type="text" data-score="${type.score}" class="editorTd chaptersum chaptersumC chaptersumⅠ sumtype${type.id}" name="chaptersum_${product.id}_${type.id}_C_Ⅰ" id="sum_${product.id}_${type.id}_C_Ⅰ" /></td>
            </#list>
            <#list zhuguan as type>
                <td><input type="text" data-score="${type.score}" class="editorTd chaptersum chaptersumA chaptersumⅠ sumtype${type.id}" name="chaptersum_${product.id}_${type.id}_A_Ⅰ" id="sum_${product.id}_${type.id}_A_Ⅰ" /></td>
                <td><input type="text" data-score="${type.score}" class="editorTd chaptersum chaptersumB chaptersumⅠ sumtype${type.id}" name="chaptersum_${product.id}_${type.id}_B_Ⅰ" id="sum_${product.id}_${type.id}_B_Ⅰ"  /></td>
                <td><input type="text" data-score="${type.score}" class="editorTd chaptersum chaptersumC chaptersumⅠ sumtype${type.id}" name="chaptersum_${product.id}_${type.id}_C_Ⅰ" id="sum_${product.id}_${type.id}_C_Ⅰ" /></td>
            </#list>
            <td rowspan="3">100%</td>
            <td rowspan="3"></td>
            <td rowspan="3">100</td>
            <td rowspan="3"></td>
        </tr>
        <tr class="success">
            <td class="tableHeadLeft" >Ⅱ</td>
            <#list keguan as type>
                <td><input type="text" data-score="${type.score}" class="editorTd chaptersum chaptersumA chaptersumⅡ sumtype${type.id}" name="chaptersum_${product.id}_${type.id}_A_Ⅱ" id="sum_${product.id}_${type.id}_A_Ⅱ" /></td>
                <td><input type="text" data-score="${type.score}" class="editorTd chaptersum chaptersumB chaptersumⅡ sumtype${type.id}" name="chaptersum_${product.id}_${type.id}_B_Ⅱ" id="sum_${product.id}_${type.id}_B_Ⅱ" /></td>
                <td><input type="text" data-score="${type.score}" class="editorTd chaptersum chaptersumC chaptersumⅡ sumtype${type.id}" name="chaptersum_${product.id}_${type.id}_C_Ⅱ" id="sum_${product.id}_${type.id}_C_Ⅱ" /></td>
            </#list>
            <#list zhuguan as type>
                <td><input type="text" data-score="${type.score}" class="editorTd chaptersum chaptersumA chaptersumⅡ sumtype${type.id}" name="chaptersum_${product.id}_${type.id}_A_Ⅱ" id="sum_${product.id}_${type.id}_A_Ⅱ" /></td>
                <td><input type="text" data-score="${type.score}" class="editorTd chaptersum chaptersumB chaptersumⅡ sumtype${type.id}" name="chaptersum_${product.id}_${type.id}_B_Ⅱ" id="sum_${product.id}_${type.id}_B_Ⅱ" /></td>
                <td><input type="text" data-score="${type.score}" class="editorTd chaptersum chaptersumC chaptersumⅡ sumtype${type.id}" name="chaptersum_${product.id}_${type.id}_C_Ⅱ" id="sum_${product.id}_${type.id}_C_Ⅱ" /></td>
            </#list>
        </tr>
        <tr class="success">
            <td class="tableHeadLeft">Ⅲ</td>
            <#list keguan as type>
                <td><input type="text" data-score="${type.score}" class="editorTd chaptersum chaptersumA chaptersumⅢ sumtype${type.id}" name="chaptersum_${product.id}_${type.id}_A_Ⅲ" id="sum_${product.id}_${type.id}_A_Ⅲ" /></td>
                <td><input type="text" data-score="${type.score}" class="editorTd chaptersum chaptersumB chaptersumⅢ sumtype${type.id}" name="chaptersum_${product.id}_${type.id}_B_Ⅲ" id="sum_${product.id}_${type.id}_B_Ⅲ" /></td>
                <td><input type="text" data-score="${type.score}" class="editorTd chaptersum chaptersumC chaptersumⅢ sumtype${type.id}" name="chaptersum_${product.id}_${type.id}_C_Ⅲ" id="sum_${product.id}_${type.id}_C_Ⅲ" /></td>
            </#list>
            <#list zhuguan as type>
                <td><input type="text" data-score="${type.score}" class="editorTd chaptersum chaptersumA chaptersumⅢ sumtype${type.id}" name="chaptersum_${product.id}_${type.id}_A_Ⅲ" id="sum_${product.id}_${type.id}_A_Ⅲ" /></td>
                <td><input type="text" data-score="${type.score}" class="editorTd chaptersum chaptersumB chaptersumⅢ sumtype${type.id}" name="chaptersum_${product.id}_${type.id}_B_Ⅲ" id="sum_${product.id}_${type.id}_B_Ⅲ" /></td>
                <td><input type="text" data-score="${type.score}" class="editorTd chaptersum chaptersumC chaptersumⅢ sumtype${type.id}" name="chaptersum_${product.id}_${type.id}_C_Ⅲ" id="sum_${product.id}_${type.id}_C_Ⅲ" /></td>
            </#list>
        </tr>

    </table>

    <div class="container-fluid" style="padding-left: 0px; padding-right: 0px;">
        <div class="row">
            <div class="col-xs-4">
                <table class="table table-striped table-bordered table-hover">
                    <tr>
                        <th style="width: 130px;">认知层次</th>
                        <th class="tableHead">Ⅰ</th>
                        <th class="tableHead">Ⅱ</th>
                        <th class="tableHead">Ⅲ</th>
                    </tr>
                    <tr>
                        <th>认知层次题量统计</th>
                        <td id="static1Count"></td>
                        <td id="static2Count"></td>
                        <td id="static3Count"></td>
                    </tr>
                    <tr>
                        <th>认知层次分值统计</th>
                        <td id="static1Sum"></td>
                        <td id="static2Sum"></td>
                        <td id="static3Sum"></td>
                    </tr>
                    <tr>
                        <th>认知层次分值比例</th>
                        <td id="static1SumR"></td>
                        <td id="static2SumR"></td>
                        <td id="static3SumR"></td>
                    </tr>
                    <tr>
                        <th>提示</th>
                        <td colspan="3"></td>
                    </tr>
                </table>
            </div>
            <div class="col-xs-4">
                <table class="table table-striped table-bordered table-hover">
                    <tr>
                        <th style="width: 130px;">难易程度</th>
                        <th class="tableHead">A</th>
                        <th class="tableHead">B</th>
                        <th class="tableHead">C</th>
                    </tr>
                    <tr>
                        <th>难易程度题量统计</th>
                        <td id="staticACount"></td>
                        <td id="staticBCount"></td>
                        <td id="staticCCount"></td>
                    </tr>
                    <tr>
                        <th>难易程度分值统计</th>
                        <td id="staticASum"></td>
                        <td id="staticBSum"></td>
                        <td id="staticCSum"></td>
                    </tr>
                    <tr>
                        <th>难易程度分值比例</th>
                        <td id="staticASumR"></td>
                        <td id="staticBSumR"></td>
                        <td id="staticCSumR"></td>
                    </tr>
                    <tr>
                        <th>平均分</th>
                        <td colspan="3" id="pingjunfen">

                        </td>
                    </tr>
                </table>
            </div>
            <div class="col-xs-4">
                <table class="table table-striped table-bordered table-hover">
                    <tr>
                        <th style="width: 130px;">章节分布</th>
                        <th class="tableHead">描述</th>
                    </tr>
                    <tr>
                        <th>重点章节</th>
                        <td id="importChapters"></td>
                    </tr>
                    <tr>
                        <th>一般章节</th>
                        <td id="noImportChapters"></td>
                    </tr>
                    <tr>
                        <th>重点章节比例</th>
                        <td id="importChapterR"></td>
                    </tr>
                    <tr>
                        <th>提示</th>
                        <td>
                            <p>（1）整套试题考查的知识点应覆盖教材80%以上的章节。</p>
                            <p>（2）重点考核内容的试题分值不少于60%。</p>
                            <p>（3）单章试题分值不超过20%。</p>
                        </td>
                    </tr>
                </table>
            </div>
        </div>
    </div>

</form>

<script>

    // 总分数
    totalScore = ${setting.totalScore};

    // 章列表
    chapterList = [<#list chapters as chapter>'${chapter.id}',</#list>];

    childChapterList = [<#list chapters as chapter><#if chapter.childList?size gt 0><#list chapter.childList as childChapter>'${childChapter.id}',</#list></#if></#list>];

    // 难易程度列表
    easeTypeList = ['A','B','C'];

    // 认知层次列表
    cognitionTypeList = ['Ⅰ','Ⅱ','Ⅲ'];

    /**
     * 展示章
     */
    function showChapter(){
        $(".chapterTr").show("normal","linear");
        /** $("#showUrl").hide();
        $("#hideUrl").show();*/
    }

    /**
     * 关闭章
     */
    function closeChapter(){
        $(".chapterTr").hide("normal","linear");
        /** $("#showUrl").show();
        $("#hideUrl").hide();*/
    }

    /**
     * 展示节
     */
    function showChildChapter(chapterId){
        $(".childChapterTr"+chapterId).show();
    }

    function hideChildChapter(chapterId){
        $(".childChapterTr"+chapterId).hide();
    }


    /**
     * 统计难易程度
     */
    function staticsEasy(){

        var levelACount=0;
        var levelASum=0;
        var levelBCount=0;
        var levelBSum=0;
        var levelCCount=0;
        var levelCSum=0;

        $(".chaptersumA").each(function(i){
            levelACount = levelACount + Number($(this).val());
            levelASum = levelASum + $(this).val()*$(this).attr('data-score');
        });

        $(".chaptersumB").each(function(i){
            levelBCount = levelBCount + Number($(this).val());
            levelBSum = levelBSum + $(this).val()*$(this).attr('data-score');
        });

        $(".chaptersumC").each(function(i){
            levelCCount = levelCCount + Number($(this).val());
            levelCSum = levelCSum + $(this).val()*$(this).attr('data-score');
        });

        var totalSum = levelASum + levelBSum + levelCSum;

        $("#staticACount").text(levelACount);
        $("#staticASum").text(levelASum);
        $("#staticASumR").text(parseFloat(levelASum/totalSum).toFixed(2));

        $("#staticBCount").text(levelBCount);
        $("#staticBSum").text(levelBSum);
        $("#staticBSumR").text(parseFloat(levelBSum/totalSum).toFixed(2));

        $("#staticCCount").text(levelCCount);
        $("#staticCSum").text(levelCSum);
        $("#staticCSumR").text(parseFloat(levelCSum/totalSum).toFixed(2));

        var pingjunfen = parseFloat(levelASum*0.85+levelBSum*0.55+levelCSum+0.25).toFixed(2);
        $("#pingjunfen").text("A*0.85+B*0.55+C*0.2="+pingjunfen);
    }

    /**
     * 重点章节统计
     */
    function importChapterStatics(){
        var totalScore = 0;
        var importChapterScore = 0;
        var importChapterName = "";
        var noImportChapterName = "";
        $(".importChapter").each(function(){
            var chapterId = $(this).val();
            var chapterScore = 0;
            $(".chapter"+chapterId).each(function(){
                var score = $(this).attr("data-score");
                var num = $(this).val();
                chapterScore = chapterScore + Number(score)*Number(num);
            });
            var chapterName = $(this).parents("tr").find("td:first > p:first").text();
            if($(this).is(":checked")){
                importChapterScore = importChapterScore + chapterScore;
                importChapterName = importChapterName + chapterName + ";";
            } else {
                noImportChapterName = noImportChapterName + chapterName + ";";
            }
            totalScore = totalScore + chapterScore;
        });
        $("#importChapters").text(importChapterName);
        $("#noImportChapters").text(noImportChapterName);
        $("#importChapterR").text(importChapterScore +"/" + totalScore + "=" +parseFloat(importChapterScore/totalScore).toFixed(2));

    }

    /**
     * 统计认知层次
     */
    function staticsCognition(){

        var level1Count=0;
        var level1Sum=0;
        var level2Count=0;
        var level2Sum=0;
        var level3Count=0;
        var level3Sum=0;

        $(".chaptersumⅠ").each(function(i){
            level1Count = level1Count + Number($(this).val());
            level1Sum = level1Sum + $(this).val()*$(this).attr('data-score');
        });

        $(".chaptersumⅡ").each(function(i){
            level2Count = level2Count + Number($(this).val());
            level2Sum = level2Sum + $(this).val()*$(this).attr('data-score');
        });

        $(".chaptersumⅢ").each(function(i){
            level3Count = level3Count + Number($(this).val());
            level3Sum = level3Sum + $(this).val()*$(this).attr('data-score');
        });

        var totalSum = level1Sum + level2Sum + level3Sum;

        $("#static1Count").text(level1Count);
        $("#static1Sum").text(level1Sum);
        $("#static1SumR").text(parseFloat(level1Sum/totalSum).toFixed(2));

        $("#static2Count").text(level2Count);
        $("#static2Sum").text(level2Sum);
        $("#static2SumR").text(parseFloat(level2Sum/totalSum).toFixed(2));

        $("#static3Count").text(level3Count);
        $("#static3Sum").text(level3Sum);
        $("#static3SumR").text(parseFloat(level3Sum/totalSum).toFixed(2));
    }

    /**
     * 统计章节
     */
    function staticsChapter(){
        for(var i=0;i<chapterList.length;i++){
            var chapterId = chapterList[i];
            var scoreTotal = 0;
            var questionTotal = 0;
            $(".chapter"+chapterId).each(function(){
                var score = $(this).attr("data-score");
                var num = $(this).val();
                questionTotal = questionTotal + Number(num);
                scoreTotal = scoreTotal + Number(score)*Number(num);
                $("#chapter"+chapterId+"_question_sum").text(questionTotal);
                $("#chapter"+chapterId+"_score_sum").text(scoreTotal);
            });
        }

        for(var i=0;i<childChapterList.length;i++){
            var chapterId = childChapterList[i];
            var scoreTotal = 0;
            var questionTotal = 0;
            $(".childChapter"+chapterId).each(function(){
                var score = $(this).attr("data-score");
                var num = $(this).val();
                questionTotal = questionTotal + Number(num);
                scoreTotal = scoreTotal + Number(score)*Number(num);
                $("#childChapter"+chapterId+"_question_sum").text(questionTotal);
                $("#childChapter"+chapterId+"_score_sum").text(scoreTotal);
            });
        }

    }

    $(document).ready(function(){

        staticsCognition();
        staticsEasy();
        staticsChapter();
        importChapterStatics();

        $(".importChapter").click(function(){
            staticsCognition();
            staticsEasy();
            staticsChapter();
            importChapterStatics();
        });

        $(".editorTd").change(function(){
            staticsCognition();
            staticsEasy();
            staticsChapter();
            importChapterStatics();
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
