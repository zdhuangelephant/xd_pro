$(document).ready(function() {
	var wechatInfo = navigator.userAgent.match(/MicroMessenger\/([\d\.]+)/i);
	if (!wechatInfo) {
		$(".main").hide();
		layer.msg('请在微信中打开', {
			time : 86400*1000, // 2s后自动关闭
			icon : 1
		});
	} else if (wechatInfo[1] < "5.0") {
		$(".main").hide();
		layer.msg('仅支持微信5.0以上版本', {
			time : 86400*1000, // 2s后自动关闭
			icon : 1
		});
	}
});
function onBridgeReady(data, resourceId) {
	var wxJson = JSON.parse(data);
	WeixinJSBridge
			.invoke(
					'getBrandWCPayRequest',
					{
						"appId" : wxJson.appId, // 公众号名称，由商户传入
						"timeStamp" : wxJson.timeStamp, // 时间戳，自1970年以来的秒数
						"nonceStr" : wxJson.nonceStr, // 随机串
						"package" : wxJson.package1,
						"signType" : wxJson.signType, // 微信签名方式：
						"paySign" : wxJson.paySign
					},
					function(res) {
						if (res.err_msg == "get_brand_wcpay_request:ok") {
							//alert("调取微信支付成功！");
							location.href = "http://mapi.5th.51xiaodou.com/mapi/share_page?resourceId="
									+ resourceId;
						}// 使用以上方式判断前端返回,微信团队郑重提示：res.err_msg将在用户支付成功后返回
						// ok，但并不保证它绝对可靠。
						else if (res.err_msg == "get_brand_wcpay_request:cancel") {
							//alert("取消调取微信支付！");
						} else {
							//alert("调取微信支付失败！");
							layer.msg('支付失败！', {
								time : 2000, // 2s后自动关闭
								icon : 1
							});
						}
					});
}


function whH5Pay() {
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
	var resourceId = $("#resourcesId").val();
	var giftMoney = $("#giftMoney").val();

	if (!isNaN(giftMoney)) {
		if (giftMoney < 0 || giftMoney == 0 || giftMoney > 200) {
			layer.msg("请填写0~200之间的数字");
			return;
		} else {

		}
	} else {
		layer.msg("请填写数字");
		return;
	}

	$.ajax({
		type : "post",
		url : "/mapi/gift_order_pay",
		timeout : 30000,
		data : {
			"targetTypeId" : resourceId,
			"targetUserId" : $("#targetUserId").val(),
			"giftType" : $("#giftType").val(),
			"resourceTitle" : $("#resourceTitle").val(),
			"giftMoney" : giftMoney,
			"h5Type" : "1",
			"weixinName" : $("#nickname").val(),
			"weixinPortrait" : $("#headimgurl").val(),
			"openId" : $("#openid").val()
		},
		dataType : "text",
		async : false,// 同步
		error : function() {
			layer.msg('支付异常！', {
				time : 2000, // 2s后自动关闭
				icon : 1
			});
		},
		success : function(data) {
			if ("fail" == data) {
				layer.msg('支付失败！', {
					time : 2000, // 2s后自动关闭
					icon : 1
				});
			} else {
				if (typeof WeixinJSBridge == "undefined") {
					if (document.addEventListener) {
						document.addEventListener('WeixinJSBridgeReady',
								onBridgeReady(data, resourceId), false);
					} else if (document.attachEvent) {
						document.attachEvent('WeixinJSBridgeReady',
								onBridgeReady(data, resourceId));
						document.attachEvent('onWeixinJSBridgeReady',
								onBridgeReady(data, resourceId));
					}
				} else {
					onBridgeReady(data, resourceId);
				}
			}
		}
	});
}
// 判断为空
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


function inputGift() {
	 var height = $('.zanshanBox').offset().top;
	 $('body').animate({
	 scrollTop : height
	 }, "30000");
//	var windowHeight = document.documentElement.clientHeight;
//	document.body.style.height = windowHeight + 'px';
}

function generateGiftMoney() {
	var n = 1;
	var giftMoney = generateMixed(n);
	$("#giftMoney").val(giftMoney);
}

function generateMixed(n) {
	var chars = [ '26.88', '36.88', '52.00', '59.99', '66.88', '88.88',
			'66.66', '99.99', '6.66', '8.88', '9.99', '16.66', '18.88',
			'19.99', '13.14', '11.11', '12.12' ];
	var res = "";
	for (var i = 0; i < n; i++) {
		var id = Math.ceil(Math.random() * 16);
		res += chars[id];
	}
	return res;
}
