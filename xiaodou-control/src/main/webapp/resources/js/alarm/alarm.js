$(function() {
	$("#tab_20214", parent.document).addClass("active");
	
	var alarmLevel = $("#alarmLevel").val();
	$("#alarmLevel1 option[value='" + alarmLevel + "']").attr("selected", true);
	var alarmType = $("#alarmType").val();
	$("#alarmType1 option[value='" + alarmType + "']").attr("selected", true);

	// Date picker
	$('#datepicker').datepicker({
		language: "zh-CN",
        todayHighlight: true,
        format: 'yyyy-mm-dd',
        autoclose: true,
        startView: '0',
        maxViewMode:'years',
        minViewMode:'days'
	});
	
});

function rawDataList(alarmId){
	addNewTabs({
		id:'20215',title: '报告详情',close: true,url: '/alarm/raw_data_list?alarmId='+alarmId}
	);
}