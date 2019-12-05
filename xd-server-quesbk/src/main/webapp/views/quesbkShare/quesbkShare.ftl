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
    <link href="${baseCssOP}/test.css?${timeStamp}" rel="stylesheet"/>
    <link href="${baseCssOP}/score.css?${timeStamp}" rel="stylesheet"/>
    <title></title>
</head>
<script type="text/javascript">
if(!(navigator.userAgent.match(/MicroMessenger/i))){
     window.location.href = "/quesbk/notWeixing";
}
</script>
<#if examDetailResponse?? && examDetailResponse.examDetail?? && examDetailResponse.examDetail.questionList??>
<body>
<div class="main" id="mstage">
	<#list examDetailResponse.examDetail.questionList as question>
    <div id="spage${question_index+1}" class="back" style="height:100%;">
        <div class="examination">
            <div class="title">
                <div class="count">${question_index+1}</div>
                <span class="type">[${question.quesTypeName}]</span>
                <span> ${examDetailResponse.examDetail.paperName}</span>
            </div>
            <div class="question">
                <span>${question.quesDesc}</span>
                <#list question.quesImgUrl as imgUrl>
                	<img src="${imgUrl}">
                </#list>
            </div>
            <ul class="chooseResult">
            <#list question.answerList as answer >
            	<li class="orderli">
                    <div class="order" value="${answer.answerId}" valueid="${question_index+1}">
                    	<#if answer_index==0 > 
                   	 		A
                    	<#elseif answer_index==1>
                    		B
                    	<#elseif answer_index==2>
                    		C
                    	<#elseif answer_index==3>
                    		D
                    	</#if>
                    </div>
                    <span>${answer.answerDesc}</span>
                    <input type="hidden" value="${answer.answerId}"><!--答案id-->
                </li>
            </#list>
             	<input id="rightAnswer${question_index+1}" type="hidden" value="${question.rightAnswerIds[0]}"><!--正确答案id-->
            	<input id="quesAnaly${question_index+1}" type="hidden" value="${question.quesAnaly}"><!--解析-->
            </ul>
            
        </div>
        
        	 <div class="download"><!--小逗考典，马上下载-->
                <div class="picture"><img src="${baseImgOP}/xiaodou_03.png?${timeStamp}"> </div>
                <div class="text">
                  <div class="p1">小逗考典
                   	<div class=" front" >马上下载</div>
                  </div>
                  <p class="p2">教师资格备考神器，助你一站通关</p>
                </div>
             </div>
    </div>
    
   </#list>
   
    <div id="spage6" class="back">
        <div class="score">
            <div class="scoreText">
                <div class="scoreTitle">成绩单</div>
                <div class="thisTime">本次答题<span id="scoreTitle">0</span>/5</div>
            </div>
            <div id="scoreDetail" class="scoreDetail d"><!--此处为分数，根据不同的分数，添加a,b,c,d类名-->

            </div>
        </div>
        <ul class="resultList"><!--各题成绩列表-->
            <#list examDetailResponse.examDetail.questionList as question>
            	<li id="right${question_index+1}" class="list wrong" name="wrong"><div class="order">${question_index+1}</div>
            		<div class="result" id="result${question_index+1}">答错</div>
             		<div class="resolve">
                    <div>解析</div>
                    <img id="img${question_index+1}" src="${baseImgOP}/go_03.png?${timeStamp}">
             		</div>
             	</li>
             	<div class="solveDetail">
             	
             	<div class="title">
    			<span class="type">[${question.quesTypeName}]</span>
                <span> ${examDetailResponse.examDetail.paperName}</span>
				</div>
				<div class="question">
    			<span>${question.quesDesc}</span>
                <#list question.quesImgUrl as imgUrl>
                	<img src="${imgUrl}">
                </#list>
				</div>
           <ul class="optionList">
            <#list question.answerList as answer >
            	<li>
                    <div class="option">
                    	<#if answer_index==0 > 
                   	 		A
                    	<#elseif answer_index==1>
                    		B
                    	<#elseif answer_index==2>
                    		C
                    	<#elseif answer_index==3>
                    		D
                    	</#if>
                    </div>
                    <span>${answer.answerDesc}</span>
                </li>
             </#list>
    	   </ul>
           
                <div class="result">
                	<img src="${baseImgOP}/answer.png?${timeStamp}">
                    <div class="text">答案</div>
                    <div class="clear"></div>
                </div>
                <span>正确答案是：<span class="true" id="true${question_index+1}"></span> 
                	，您的答案是：<span class="false" id="myAnswer${question_index+1}"></span></span>

                <div class="line"></div>
                
				 <div class="result">考点</div>
                <p class="point">
                <font style="color:#1fb1f0;">
                <#list question.keyPointList as keyPoint>
                	${keyPoint.name}&nbsp;&nbsp;
                </#list>
                </font>
                </p>
                <div class="line"></div>
                <div class="result">解析</div>
                <p>${question.quesAnaly}</p>        
            	</div>
            </#list>
        </ul>
              <div class="download"><!--小逗考典，马上下载-->
                <div class="picture"><img src="${baseImgOP}/xiaodou_03.png?${timeStamp}"> </div>
                <div class="text">
                  <div class="p1">小逗考典
                   	<div class=" front" >马上下载</div>
                  </div>
                  <p class="p2">教师资格备考神器，助你一站通关</p>
                </div>
             </div>
    </div>
</div>
<div class="xd">
    <img src="${baseImgOP}/notice.png?${timeStamp}">
</div>
<input id="weixinFolder" type="hidden" value="quesbk" >
<input id="shareTitle" type="hidden" value="${shareResponse.title}" >
<input id="shareContent" type="hidden" value="${shareResponse.content}" >
<input id="shareImageUrl" type="hidden" value="${shareResponse.imageUrl}" >
<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"> </script>
<script type="text/javascript" src="${baseJsOP}/jquery-2.1.1.min.js?${timeStamp}"></script>
<script type="text/javascript" src="${baseJsOP}/TweenMax.min.js?${timeStamp}"></script>
<script type="text/javascript" src="${baseJsOP}/director.js?${timeStamp}"></script>
<script type="text/javascript" src="${baseJsOP}/slidingScreen.js?${timeStamp}"></script>
<script type="text/javascript" src="${baseJsOP}/Cover.js?${timeStamp}"></script>
<script type="text/javascript" src="${baseJsOP}/share.js?${timeStamp}"></script>
<script type="text/javascript" src="${baseJsOP}/quesbkShare.js?${timeStamp}"></script>
</body>
<#else>
error
</#if> 
</html>