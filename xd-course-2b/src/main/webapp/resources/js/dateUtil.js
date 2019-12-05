function getInfo(year, month, day) {//年，月，周
	var d = new Date();
	// what day is first day
	d.setFullYear(year, month - 1, 1);
	var w1 = d.getDay();
	if (w1 == 0)
		w1 = 7;
	// total day of month
	d.setFullYear(year, month, 0);
	var dd = d.getDate();
	// first Monday
	if (w1 != 1)
		d1 = 7 - w1 + 2;
	else
		d1 = 1;
	week_count = Math.ceil((dd - d1 + 1) / 7);
	//以下代码，可能再一些需求场景有用 (lidehong edit)
//	console.log(year + "年" + month + "月有" + week_count + "周<br/>");
//	for ( var i = 0; i < week_count; i++) {
//		var monday = d1 + i * 7;
//		var sunday = monday + 6;
//		var from = year + "/" + month + "/" + monday;
//		var to;
//		if (sunday <= dd) {
//			to = year + "/" + month + "/" + sunday;
//		} else {
//			d.setFullYear(year, month - 1, sunday);
//			to = d.getFullYear() + "/" + (d.getMonth() + 1) + "/" + d.getDate();
//		}
//		console.log("第" + (i + 1) + "周 从" + from + " 到 " + to + "<br/>");
//	};
	var monday = d1 + (day - 1) * 7;
	var sunday = monday + 6;
//     var from = year + "/" + month + "/" + monday;
	var to;
	if (sunday <= dd) {
		to = year + "/" + month + "/" + sunday;
	} else {
		d.setFullYear(year, month - 1, sunday);
		to = d.getFullYear() + "/" + (d.getMonth() + 1) + "/" + d.getDate();
	}

	var result;
	if (d1 == 1) {
		result = to;
	} else {
		result = year + "/" + month + "/" + (monday - 1);
	}
	return result;
}