<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <meta name="viewport" content="initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
    <meta name="apple-mobile-web-app-capable" content="yes"/>
    <meta name="full-screen" content="yes"/>
    <meta name="screen-orientation" content="portrait"/>
    <meta name="x5-fullscreen" content="true"/>
    <meta name="360-fullscreen" content="true"/>
    <link href="${baseCssOP}/resultList.css?${timeStamp}" rel="stylesheet" type="text/css">
    <title>查询结果列表</title>
</head>
<body>
    <div class="main">
        <div class="top">
            <ul>
                <li><span>姓名</span>${score.nameInfo.name}</li>
                <li>证件号码：${score.nameInfo.docNumber}</li>
            </ul>
        </div>
        <#if errorInfo??>
        <div class="notice">${errorInfo}</div><!--错误提示-->
        <#else>
        <ul class="resultList">
            <#if (score.writtenScoreList?size gt 0) >
            <li>
                <div class="box">
                    <div class="left">
                        <img src="${baseImgOP}/t1_03.png?${timeStamp}">
                        <span>笔试成绩</span>
                    </div>
                    <div class="right">
                        <span>详情</span>
                        <img src="${baseImgOP}/bottom_15.png?${timeStamp}">
                    </div>
                </div>
            </li>
            <div class="detail">
            	<#list score.writtenScoreList as writtenScore >
                <div class="score">
                    <#if writtenScore.istate==1>
                    <div class="head">
                    <#else>
                    <div class="head absent">
                    </#if>
                    ${writtenScore.coureseName}</div>
                    <p class="p1">报考分：<span>${writtenScore.score}</span></p>
                    <p class="p2">合格与否：<span>${writtenScore.sstate}</span></p>
                    <div class="clear"></div>
                    <p>准考证号：<span>${writtenScore.docNumber}</span></p>
                    <p>考试批次：<span>${writtenScore.examIndex}</span></p>
                    <p>有效时间：<span>${writtenScore.validDate}</span></p>
                    <#if writtenScore.istate!=1 >
                    <img src="${baseImgOP}/f_11.png?${timeStamp}" class="no">
                    </#if>
                </div>
                </#list>
                <div class="remark">备注：报考分满分120，70分合格</div>
                <div class="area">报考省份：<span>${score.province!}</span></div>
            </div>
            </#if>
            <#if (score.interviewScoreList?size gt 0) >
            <li>
                <div class="box">
                    <div class="left">
                        <img src="${baseImgOP}/t2_10.png?${timeStamp}">
                        <span>面试成绩</span>
                    </div>
                    <div class="right">
                        <span>详情</span>
                        <img src="${baseImgOP}/bottom_15.png?${timeStamp}">
                    </div>
                </div>
            </li>
            <div class="detail">
            	<#list score.interviewScoreList as interviewScore >
                <div class="score">
                    <#if interviewScore.istate==1>
                    <div class="head interview">
                    <#else>
                    <div class="head absent">
                    </#if>
                    ${interviewScore.coureseName}</div>
                    <p class="p1">准考证号：<span>${interviewScore.docNumber}</span></p>
                    <p class="p2">合格与否：<span>${interviewScore.sstate}</span></p>
                    <div class="clear"></div>
                    <p>考试批次：<span>${interviewScore.examIndex}</span></p>
                    <#if interviewScore.istate!=1 >
                    <img src="${baseImgOP}/f_11.png?${timeStamp}" class="no">
                    </#if>
                </div>
                </#list>
                <div class="text">备注：<span>面试合格与否由各省教育行政部门决定。</span></div>
            </div>
            </#if>
            <#if (score.proveScoreList?size gt 0)>
            <li>
                <div class="box last">
                    <div class="left">
                        <img src="${baseImgOP}/t3_12.png?${timeStamp}">
                        <span>考试合格证明</span>
                    </div>
                    <div class="right">
                        <span>详情</span>
                        <img src="${baseImgOP}/bottom_15.png?${timeStamp}">
                    </div>
                </div>

            </li>
            <div class="detail">
            	<#list score.proveScoreList as proveScore >
                <div class="score">
                    <div class="head qualified">${proveScore.coureseName}</div>
                    <p>证明编号：<span>${proveScore.proveNumber}</span></p>
                    <p>有效期限：<span>${proveScore.validDate}</span></p>
                </div>
                </#list>
                <div class="text">提示：<span>《中小学教师资格考试合格证明》是申请教师资格认定的必备条件之一，具体进行教师资格认定的日期和要求，请关注本省教师资格认定部门的有关通知。</span></div>
            </div>
            </#if>
        </ul>
		</#if>
    </div>
    <script type="text/javascript" src="${baseJsOP}/jquery-2.1.1.min.js?${timeStamp}"></script>
    <script type="text/javascript" src="${baseJsOP}/resultList.js?${timeStamp}"></script>
</body>
</html>