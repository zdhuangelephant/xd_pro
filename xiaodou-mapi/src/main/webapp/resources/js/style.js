var cCont = document.getElementById('c-content');
var contList = cCont.getElementsByTagName('div');
var main = document.getElementById('main');
var aSpan = main.getElementsByTagName('span');
var aList = main.getElementsByTagName('a');

for (var i = 0; i < aList.length; i++) {
	aList[i].index = i;
	aList[i].onclick = function() {
		for (var i = 0; i < aList.length; i++) {
			aList[i].style.borderColor = '#fff';
			contList[i].style.display = 'none';
			aSpan[i].style.display = 'none';
		}
		contList[this.index].style.display = 'block';
		this.style.borderColor = '#fdb563';
		this.style.borderRadius = '7px';
		aSpan[this.index].style.display = 'block';
	}
}

// 首页time
var lLastTime = document.getElementById("lastTime");
/* 当前时间 */
//var now = new Date("December 3, 2017 23:00:00");
var now = new Date();
/* 年 */
var nowYear = now.getFullYear();
/* 月 */
var nowMonth = now.getMonth() + 1;
/* 1970年到现在时间的毫秒数 */
var nowTime = now.getTime();
/* 几号 */
var nowDayOfWeek = now.getDate();
/* 星期几 */
var nowDay = now.getDay();
if (nowDay == 0) {
    nowDay = 7;
}
/* 一天的毫秒数 */
var oneDayLong = 24 * 60 * 60 * 1000;

var MondayTime = nowTime - (nowDay + 6) * oneDayLong;
var monday = new Date(MondayTime);
var monFullYear = monday.getFullYear();
var monMonth = monday.getMonth() +1;
var monDate = monday.getDate();
if (monMonth < 10) {
    monMonth = '0' + monMonth;
}
if (monDate < 10) {
    monDate = '0' + monDate;
}

var SundayTime =  nowTime + (0 - nowDay) * oneDayLong ;
var sunday = new Date(SundayTime);
var sunFullYear = sunday.getFullYear();
var sunMonth = sunday.getMonth() + 1;
var sunDate = sunday.getDate();
if (sunMonth < 10) {
    sunMonth = '0' + sunMonth;
}
if (sunDate < 10) {
    sunDate = '0' + sunDate;
}
lLastTime.innerHTML = (monFullYear + "." + monMonth + '.' + monDate) + ' - ' + (sunFullYear + '.' + sunMonth + '.' + sunDate);
