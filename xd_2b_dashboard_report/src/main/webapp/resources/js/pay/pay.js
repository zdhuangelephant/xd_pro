$(function() {
	var goEasy = new GoEasy({
		appkey : 'BS-7470e51f2a5b4540850d50b91215c621'
	});
	var orderNumber = $("#orderNumber").val();
	goEasy.subscribe({
		channel : "wx_pay_channel_"+orderNumber,
		onMessage : function(message) {
//			alert("您有新消息：channel：" + message.channel + " 内容："
//					+ message.content);
			if(!isEmpty(message.content)){
				var json = JSON.parse(message.content);
				var tradeState  = json.trade_state;
				var tradeStateDesc = json.trade_state_desc;
				if (notEmpty(tradeState) && tradeState == "SUCCESS") {
					layer.msg(tradeStateDesc, {
						time : 2000, // 2s后自动关闭
						icon : 1
					});
					location.href="/order/order_detail?orderNumber="+orderNumber;
				}
			}
		}
	});
});
var idInt;
function pay(e) {
	$("img").removeClass('active');
	$(e).addClass("active");
}

function toPay() {
	if ($("img.active").attr("id") == "ali") {
		alert("coding...");
	}
	if ($("img.active").attr("id") == "wx") {
		$(".close").click();
		var orderNumber = $("#orderNumber").val();
		$.ajax({
			cache : true,
			type : "POST",
			url : "/pay/wx_pay_unifiedorder",
			data : {
				"orderNumber" : orderNumber
			},
			dataType : "html",
			error : function() {
				layer.msg('查找失败', {
					time : 2000, // 2s后自动关闭
					icon : 1
				});
				location.href="/order/order_detail?orderNumber="+orderNumber;
			},
			success : function(data) {
				var json = JSON.parse(data);
				var codeUrl = json.code_url;
				var errCodeDes = json.err_code_des;
				if (notEmpty(codeUrl)) {
					// 完整用法，有默认值的均可省略
					$("#wxqrcode").html("");
					$('#wxqrcode').qrcode(codeUrl);
					$("#wxPay").attr("style", "display: block;");
					$("#wxPay").addClass("in");
					idInt = setInterval("checkOrder("+orderNumber+")",2000);
				}else{
					layer.msg(errCodeDes, {
						time : 2000, // 2s后自动关闭
						icon : 1
					});
				}
			}
		});
	}
}

function toClosePay() {
	$("#wxPay").attr("style", "display: none;");
	$("#wxPay").removeClass("in");
	if(notEmpty(idInt))clearInterval(idInt);
}

// 定时器，每隔1s查询订单支付状态，订单状态改变，清除页面定时器，页面跳转
function checkOrder(orderNumber) {
	$.ajax({
		cache : true,
		type : "POST",
		url : "/pay/query_pay",
		data : {
			"orderNumber" : orderNumber
		},
		dataType : "html",
		error : function() {
			layer.msg('查找失败', {
				time : 2000, // 2s后自动关闭
				icon : 1
			});
			location.href="/order/order_detail?orderNumber="+orderNumber;
		},
		success : function(data) {
			if(!isEmpty(data)){
				var json = JSON.parse(data);
				var tradeState  = json.trade_state;
				var tradeStateDesc = json.trade_state_desc;
				if (notEmpty(tradeState) && tradeState == "SUCCESS") {
					layer.msg(tradeStateDesc, {
						time : 2000, // 2s后自动关闭
						icon : 1
					});
					location.href="/order/order_detail?orderNumber="+orderNumber;
				}
			}
		}
	});
}
