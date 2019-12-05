$(document).ready(function() {
	var wechatInfo = navigator.userAgent.match(/MicroMessenger\/([\d\.]+)/i);
	if (!wechatInfo) {
		$(".gift").hide();
	} else if (wechatInfo[1] < "5.0") {
		$(".gift").hide();
	}
});


function wxPay() {
	var wechatInfo = navigator.userAgent.match(/MicroMessenger\/([\d\.]+)/i);
	if (!wechatInfo) {
		layer.msg('请在微信中打开', {
			time : 2000, // 2s后自动关闭
			icon : 1
		});
	} else if (wechatInfo[1] < "5.0") {
		layer.msg('仅支持微信5.0以上版本', {
			time : 2000, // 2s后自动关闭
			icon : 1
		});
	}
	location.href = getOpenId();
}

//判断为空
function isEmpty(src) {
	if (("undefined" == typeof src) || (src == null) || ($.trim(src) == "")) {
		return true;
	}
	return false;
}

// 判断不为空
function notEmpty(src) {
	return !isEmpty(src);
}

// 微信页面授权 snsapi_base方式
function wecharauto2burl(url) {
	return "https://open.weixin.qq.com/connect/oauth2/authorize?appid="
			+ appid
			+ "&redirect_uri="
			+ encodeURIComponent(url)
			+ "&response_type=code&scope=snsapi_base&state=xybank#wechat_redirect";
}

// 页面授权针对snsapi_base方式授权的url
function wecharauto2baseurl(url) {
	return wecharauto2burl(urlpre + url);
}

// 页面授权针对snsapi_userinfo方式授权的url
function wecharauto2userinfourl(url) {
	return "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxb0ad9b23602b782e&redirect_uri="
			+ encodeURI(url)
			// +url
			+ "&response_type=code&scope=snsapi_userinfo&state=xybank#wechat_redirect";
}
// 第一步：用户同意授权，获取code
function getOpenId() {
	// var urlpre = "http://pay.51xiaodou.com/wx_call_back";
	var urlpre = "http://mapi.5th.51xiaodou.com/resources";
	var url = "/wx_h5_pay?resourcesId=" + $("#resourcesId").val();
	// + "&targetUserId="
	// + $("#targetUserId").val() + "&giftType=" + $("#giftType").val()
	// + "&resourceTitle=aaa";//+ $("#resourceTitle").val();
	return wecharauto2userinfourl(urlpre + url);
}