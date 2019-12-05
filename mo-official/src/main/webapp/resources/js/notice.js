/**
 * moshare 弹出公告
 */

/*window.onload = function() {
	var adv = document.getElementById('adv')
	var close = document.getElementById('close')
	var adShadow = document.getElementById('adv-shadow');
	广告弹出时的卷帘效果方法
	function ani(obj) {
		var allWidth = document.documentElement.clientWidth // 获取网页可视区域宽
		var allHeight = document.documentElement.clientHeight// 获取网页可视区域高
		adv.style.left = (allWidth - adv.offsetWidth) / 2 + 'px' // 使广告居中在页面
		adv.style.top = (allHeight - adv.offsetHeight) / 2 + 'px'
		var num = 0
		var objH = adv.offsetHeight
		var timer
		timer = setInterval(function() { // 定时器，没50毫秒增加10px的高度
			if (num < parseInt(objH)) {
				num += 10
				obj.style.height = num + 'px'
			} else {
				clearInterval(timer)
			}
		}, 10)
	}
	点击关闭广告
	close.onclick = function() {
		adv.style.display = 'none'
		adShadow.style.display = 'none';
	}
	// 设置cookie，cookie是以字符串形式存储的，可以有很多参数，但必要的一个是cookie 的名称name

	// * function setcookie () { var d = new Date() d.setTime(d.getTime() + 24 *
	// 60 *
	// * 60 * 1000) //设置过去时间为当前时间增加一天 document.cookie = 'name=world;expires=' +
	// * d.toGMTString() //expires是cookie的一个可选参数，设置cookie的过期时间 var res =
	// * document.cookie return res //返回cookie字符串 }

	// 判断网页是否是第一次浏览，如果第一次则弹出广告，然后设置cookie值，否则把广告隐藏
	if (document.cookie == '') {
		ani(adv)

		* setcookie()
	} else {
		adv.style.display = 'none'
	}

}*/


window.onload = function() {
	var close = document.getElementById('close');
	var adShadow = document.getElementById('adv-shadow');
	var indexId = document.getElementById('indexId');
	close.onclick = function() {
		adv.style.display = 'none'
		adShadow.style.display = 'none';
	}

	// index click事件
	indexId.onclick = function() {
		adShadow.style.width = 0;
	}
}