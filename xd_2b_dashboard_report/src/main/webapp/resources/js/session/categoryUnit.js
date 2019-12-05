$(function(){
	$("#tab_10003", parent.document).addClass('active');
});
function categoryUnitTendencyList(catId,pilotUnitId){
	addNewTabs({
		id:'10012',title: '趋势分析',close: true,url: '/session/category_unit_tendency_list?catId='+catId+'&pilotUnitId='+pilotUnitId}
	);
}