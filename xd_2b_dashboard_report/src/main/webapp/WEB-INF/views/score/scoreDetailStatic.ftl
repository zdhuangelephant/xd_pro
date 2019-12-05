
<#if chapterNodeList??>
<#assign chapterCount = chapterNodeList?size +1>
<#else>
<#assign chapterCount = 2>
</#if>

<#if finalExamNodeList??>
<#assign finalExamCount = finalExamNodeList?size>
<#else>
<#assign finalExamCount = 1>
</#if>
<#assign count = chapterCount + finalExamCount>
<tr>
	<td rowspan='${count}' class="text-center">阶段测评成绩（70分）</td>
	<td rowspan="${chapterCount}" class="text-center">章节测评</td>
	<td rowspan="${chapterCount}" class="text-center">70%</td>
	<td class="text-center"><#if chapterNodeList?? && chapterNodeList[0]??>${chapterNodeList[0].indexName}</#if></td>
	<td class="text-center"><#if chapterNodeList?? && chapterNodeList[0]??>${chapterNodeList[0].weight}</#if></td>
	<td class="text-center"><#if chapterNodeList?? && chapterNodeList[0]??>${chapterNodeList[0].score}</#if></td>
	<td rowspan="${chapterCount}" class="text-center">${scoreDO.avgChapterScore}</td>
	<td rowspan="${count}" class="text-center">${scoreDO.stageEvaluationScore *0.7}</td>
</tr>
<tr>
</tr>
<#if chapterNodeList??>
<#list chapterNodeList as chapter>
<#if chapter?? && chapter_index != 0>
<tr>
	<td class="text-center">${chapter.indexName}</td>
	<td class="text-center">${chapter.weight}</td>
	<td class="text-center">${chapter.score}</td>
</tr>
</#if>
</#list>
</#if>

<tr >
	<td rowspan="${finalExamCount}" class="text-center">期末测试</td>
	<td rowspan="${finalExamCount}" class="text-center">30%</td>
	<td class="text-center"><#if finalExamNodeList?? && finalExamNodeList[0]??>${finalExamNodeList[0].indexName}</#if></td>
	<td class="text-center"><#if finalExamNodeList?? && finalExamNodeList[0]??>${finalExamNodeList[0].weight}</#if></td>
	<td class="text-center"><#if finalExamNodeList?? && finalExamNodeList[0]??>${finalExamNodeList[0].score}</#if></td>
	<td rowspan="${finalExamCount}" class="text-center">${scoreDO.finalExamScore}</td>
</tr>

<#if finalExamNodeList??>
<#list finalExamNodeList as final>
<#if final??>
	<tr>
		<td class="text-center">${final.indexName}</td>
		<td class="text-center">${final.weight}</td>
		<td class="text-center">${final.score}</td>
	</tr>
</#if>
</#list>
</#if>


<tr >
	<td class="text-center">学习行为成绩（30分）</td>
	<td colspan="4" class="text-center">学习任务完成度</td>
	<td class="text-center">${scoreDO.missionFinishScore}</td>
	<td class="text-center">${scoreDO.missionFinishScore}</td>
	<td class="text-center">${scoreDO.missionFinishScore *0.3}</td>
</tr>
<tr >
	<td class="text-center">查漏补缺</td>
	<td colspan="6" class="text-center"></td>
	<td class="text-center">${scoreDO.supplementScore}</td>
</tr>

<tr >
	<td class="text-center">总成绩</td>
	<td colspan="6" class="text-center"></td>
	<td class="text-center">${scoreDO.score}</td>
</tr>