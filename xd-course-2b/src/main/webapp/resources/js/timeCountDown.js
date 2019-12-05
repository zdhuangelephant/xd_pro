/* $(".ft_counter").EightycloudsFliptimer({
				enddate :"24 December 11019 12:00:00 GMT",//"24 December 2015 12:00:00 GMT"
				callback : function() {											//(new Date).toString().replace("+0800 (中国标准时间)","")
					alert("Countdown is Complete!");
				}
			}); */
var number = 1;
var interval;
var interv;
var day1;
var day2;
var day3;
var round;
var count1;
var count2;
var count3;
$(document)
		.ready(
				function() {
					/*
					 * var date = new Date();
					 * console.log(date.toString().replace("+0800
					 * (中国标准时间)",""));
					 * console.log(date.toGMTString().replace(",",""));
					 */
					// number = $(".EightycloudsFlipTimer .block.days
					// .block_right
					// .block_right_top_count").text();
					// number = 1;
					// 交替执行
					/*
					 * window.setTimeout(to_end1,interv1*10);
					 * window.setTimeout(to_end2,interv2*11);
					 * window.setTimeout(to_end3,interv3*12);
					 */
					var yearTime = $("#yearTime").val();
					var monthTime = $("#monthTime").val();
					var end_time;
					var year;
					if (yearTime == "" || monthTime == ""
							|| typeof (yearTime) == "undefined"
							|| typeof (monthTime) == "undefined") {
						year = new Date().getFullYear();
					} else {
						year = yearTime;
					}
					var mar = getInfo(year, "3", "3");
					var ele = getInfo(year, "11", "1");
					var nowTime = new Date().getTime();
					var marTime = new Date(mar + " 00:00:00").getTime();
					var eleTime = new Date(ele + " 00:00:00").getTime();
					if (yearTime == "" || monthTime == ""
							|| typeof (yearTime) == "undefined"
							|| typeof (monthTime) == "undefined") {
						if (nowTime < marTime ) {
							end_time = marTime;
						} else if(marTime <= nowTime && nowTime < eleTime){
							end_time = eleTime;
						}else if(nowTime >= eleTime){
							end_time = new Date(getInfo(year+1, "3", "3") + " 00:00:00").getTime();
						}
					} else {
						if (monthTime == 3)
							end_time = marTime;
						else if (monthTime == 11)
							end_time = eleTime;
						if(nowTime>=end_time){
							//告知后台需要更新
							$("#text").text("距离下一次考试还有");
							year = new Date().getFullYear();//确保正常时间
							mar = getInfo(year, "3", "3");
							ele = getInfo(year, "11", "1");
							marTime = new Date(mar + " 00:00:00").getTime();
							eleTime = new Date(ele + " 00:00:00").getTime();
							if (nowTime < marTime ) {
								end_time = marTime;
							} else if(marTime <= nowTime && nowTime < eleTime){
								end_time = eleTime;
							}else if(nowTime >= eleTime){
								end_time = new Date(getInfo(year+1, "3", "3") + " 00:00:00").getTime();
							}
						}
					}
					countDown(end_time);
					var sys_second = (end_time - new Date().getTime()) / 1000;
					var day = Math.floor((sys_second / 3600) / 24);
					day1 = Math.floor(day / 100);
					day2 = Math.floor((day / 10) % 10);
					day3 = Math.floor(day % 10);

					/* window.setTimeout(to_go,1000); */
					to_go();
					count1 = day1;
					count2 = (day2 + 5) % 10;
					count3 = (day3) % 10;

					orient();
				});

function orient() {
	if (window.orientation == 0 || window.orientation == 180) {
		$("body").attr("class", "portrait");
		orientation = 'portrait';
		return false;
	} else if (window.orientation == 90 || window.orientation == -90) {
		alert("横屏浏览效果不佳，建议竖屏浏览~");
		$("body").attr("class", "landscape");
		orientation = 'landscape';
		return false;
	}
}
$(window).bind('orientationchange', function(e) {
	orient();
});

function countDown(end_time) {
	// if(typeof end_time == "string")
	// var end_time = new Date(time).getTime(),//月份是实际月份-1
	// current_time = new Date().getTime(),
	sys_second = (end_time - new Date().getTime()) / 1000;
	var timer = setInterval(function() {
		if (sys_second > 0) {
			sys_second -= 1;
			var day = Math.floor((sys_second / 3600) / 24);
			var hour = Math.floor((sys_second / 3600) % 24);
			var minute = Math.floor((sys_second / 60) % 60);
			var second = Math.floor(sys_second % 60);
			// day_elem && $(day_elem).text(day);//计算天
			$(".hour").text(hour < 10 ? "0" + hour : hour);// 计算小时
			$(".minute").text(minute < 10 ? "0" + minute : minute);// 计算分
			$(".second").text(second < 10 ? "0" + second : second);// 计算秒
			$(".block_left_top_count").text(day1);
			$(".block_left_bottom_count").text(day1);
			$(".block_mid_top_count").text(day2);
			$(".block_mid_bottom_count").text(day2);
			$(".block_right_top_count").text(day3);
			$(".block_right_bottom_count").text(day3);
		} else {
			clearInterval(timer);
		}
	}, 1000);
}

var to_go = function() {
	interval1 = 200;
	interv1 = 211;// interv1后两位乘以倍数的值应该小于interval1
	interval2 = 200;
	interv2 = 211;
	interval3 = 200;
	interv3 = 211;
	round1 = 10;
	round2 = 15;
	round3 = 20;
	var show1 = window.setInterval(to_show1, interval1);// 每隔interval调用一次函数to_show
	var hide1 = window.setInterval(to_hide1, interv1);// 每隔interval调用一次函数to_hide
	window.setTimeout("window.clearInterval(" + show1 + ")", interval1
			* (round1 + 1));
	window.setTimeout("window.clearInterval(" + hide1 + ")", interv1
			* (round1 + 1));
	var show2 = window.setInterval(to_show2, interval2);// 每隔interval调用一次函数to_show
	var hide2 = window.setInterval(to_hide2, interv2);// 每隔interval调用一次函数to_hide
	window.setTimeout("window.clearInterval(" + show2 + ")", interval2
			* (round2 + 1));
	window.setTimeout("window.clearInterval(" + hide2 + ")", interv2
			* (round2 + 1));
	var show3 = window.setInterval(to_show3, interval3);// 每隔interval调用一次函数to_show
	var hide3 = window.setInterval(to_hide3, interv3);// 每隔interval调用一次函数to_hide
	window.setTimeout("window.clearInterval(" + show3 + ")", interval3
			* (round3 + 1));
	window.setTimeout("window.clearInterval(" + hide3 + ")", interv3
			* (round3 + 1));
};

var to_end1 = function() {
	$(".EightycloudsFlipTimer .block.days .block_left .block_left_top_count")
			.text(day1);
	$(".EightycloudsFlipTimer .block.days .block_left .block_left_bottom_count")
			.text(day1);
};
var to_end2 = function() {
	$(".EightycloudsFlipTimer .block.days .block_mid .block_mid_top_count")
			.text(day2);
	$(".EightycloudsFlipTimer .block.days .block_mid .block_mid_bottom_count")
			.text(day2);
};
var to_end3 = function() {
	$(".EightycloudsFlipTimer .block.days .block_right .block_right_top_count")
			.text(day3);
	$(
			".EightycloudsFlipTimer .block.days .block_right .block_right_bottom_count")
			.text(day3);
};
/*
 * var count1 = 3; var count2 =(2+3)%10; var count3 =(9+5)%10;
 */

var to_show1 = function() {
	if (count1 == 0)
		count1 = 10;
	count1--;
	$(".EightycloudsFlipTimer .block.days .block_left .block_effect5").css({
		"display" : "block"
	});
	$(".EightycloudsFlipTimer .block.days .block_left .block_effect6").css({
		"display" : "block"
	});
	$(".EightycloudsFlipTimer .block.days .block_left .block_left_top_count")
			.text(count1);

	/*
	 * $(".EightycloudsFlipTimer .block.days .block_right .block_effect5")
	 * .css({"display": "block", "top": "3.47656px", "height":" 59.5313px","
	 * left":" 3.05469px", "overflow":"hidden"}); $(".EightycloudsFlipTimer
	 * .block.days .block_right .block_effect6") .css({"display": "none",
	 * "height": "0px", "left": "10px", "overflow": "hidden"});
	 * $(".EightycloudsFlipTimer .block.days .block_right
	 * .block_right_top_count") .text(count);
	 */
};
var to_show2 = function() {
	if (count2 == 0)
		count2 = 10;
	count2--;
	$(".EightycloudsFlipTimer .block.days .block_mid .block_effect5").css({
		"display" : "block"
	});
	$(".EightycloudsFlipTimer .block.days .block_mid .block_effect6").css({
		"display" : "block"
	});
	$(".EightycloudsFlipTimer .block.days .block_mid .block_mid_top_count")
			.text(count2);

};
var to_show3 = function() {
	if (count3 == 0)
		count3 = 10;
	count3--;
	$(".EightycloudsFlipTimer .block.days .block_right .block_effect5").css({
		"display" : "block"
	});
	$(".EightycloudsFlipTimer .block.days .block_right .block_effect6").css({
		"display" : "block"
	});
	$(".EightycloudsFlipTimer .block.days .block_right .block_right_top_count")
			.text(count3);
};
var to_hide1 = function() {
	$(".EightycloudsFlipTimer .block.days .block_left .block_effect5").css({
		"display" : "none"
	});
	$(".EightycloudsFlipTimer .block.days .block_left .block_effect6").css({
		"display" : "none"
	});
	$(".EightycloudsFlipTimer .block.days .block_left .block_left_bottom_count")
			.text(count1);
	if (count1 == 0)
		count1 = 10;

	/*
	 * $(".EightycloudsFlipTimer .block.days .block_right .block_effect5")
	 * .css({"display": "none", "top": "3px", "height":" 60px"," left":" 3px"});
	 * $(".EightycloudsFlipTimer .block.days .block_right .block_effect6")
	 * .css({"display": "none", "height": "0px", "left": "10px"});
	 * $(".EightycloudsFlipTimer .block.days .block_right
	 * .block_right_bottom_count") .text(count);
	 */
};
var to_hide2 = function() {
	$(".EightycloudsFlipTimer .block.days .block_mid .block_effect5").css({
		"display" : "none"
	});
	$(".EightycloudsFlipTimer .block.days .block_mid .block_effect6").css({
		"display" : "none"
	});
	$(".EightycloudsFlipTimer .block.days .block_mid .block_mid_bottom_count")
			.text(count2);
	if (count2 == 0)
		count2 = 10;
};
var to_hide3 = function() {
	$(".EightycloudsFlipTimer .block.days .block_right .block_effect5").css({
		"display" : "none"
	});
	$(".EightycloudsFlipTimer .block.days .block_right .block_effect6").css({
		"display" : "none"
	});
	$(
			".EightycloudsFlipTimer .block.days .block_right .block_right_bottom_count")
			.text(count3);
	if (count3 == 0)
		count3 = 10;
};