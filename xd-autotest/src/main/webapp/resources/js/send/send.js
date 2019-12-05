function send(){
	var allAction=new Array();
	var apiList=new Array();
	var afterSets=new Array();
	var afterSets=new Array();
	var params=new Array();
	var preConds=new Array();
	var preSets=new Array();
	var testCases=new Array();

	var actionInfo=new Object();
	var api=new Object();
	var afterSet=new Object();
	var param=new Object();
	var preCond=new Object();
	var preSet=new Object();
	var testCase=new Object();

	actionInfo.businessLine="0102";
	actionInfo.name="测试用例";
	actionInfo.version="1.3.0";

	api.format="normal";
	api.method="get";
	api.name="api_a_1";
	api.protocol="http"
	api.resultDataFormat="jSon";
	api.retryTime="2"
	api.special=false;
	api.status="UnExcute";
	api.timeOut=5000;
	api.uniqueId="107e0a60-07ec-490c-b3d6-8b4958db85ad";
	api.url="http://119.61.66.57:8102/quesbk/exam_result";


	//afterSets赋值
	afterSet.dataType="sTring";
	afterSet.mappingKey="api_a_1_pkExam";
	afterSet.resultKey="107e0a60-07ec-490c-b3d6-8b4958db85ad.pkExam";
	afterSets.push(afterSet);
	api.afterSets=afterSets;



	//param赋值
	param.dataType="sTring";
	param.desc="答题详情";
	param.name="examDetail";
	param.value="${examDetail}";
	param.paramType="QueryParam";
	params.push(param);
	api.params=params;


	//preConds赋值
	preCond.key="uid";
	preCond.condition="eq";
	preCond.targetValue="2";
	preConds.push(preCond);
	api.preConds=preConds;


	//preSets赋值
	preSet.assignment="[{\"answerIdList\":[\"26915_2\"],\"quesId\":26915},{\"answerIdList\":[\"26964_2\"],\"quesId\":26964},{\"answerIdList\":[\"26887_1\"],\"quesId\":26887},{\"answerIdList\":[\"27061_2\"],\"quesId\":27061},{\"answerIdList\":[\"26882_1\"],\"quesId\":26882}]";
	preSet.dataSource="Local";
	preSet.dataType="sTring";
	preSet.key="examDetail";
	preSet.scope="part";
	preSets.push(preSet);
	api.preSets=preSets;


	//testCase赋值
	testCase.dataType="sTring";
	testCase.name="tc1";
	testCase.testKey="107e0a60-07ec-490c-b3d6-8b4958db85ad.retcode";
	testCase.testValue="${targetRetcode}";
	testCases.push(testCase);
	api.testCases=testCases;

	apiList.push(api);
	actionInfo.apiList=apiList;
	allAction.push(actionInfo);
	var json_data = JSON.stringify(allAction);
	$.post("/request/send", {actionInfo:json_data},
				function(data){
					var u=JSON.parse(data);
					if(u.status="success"){	
						alert(u.data);
					}else{
						alert(u.data);
					}
				});	   
}
