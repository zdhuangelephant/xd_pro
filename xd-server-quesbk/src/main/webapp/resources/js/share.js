$(document).ready(function () {
  function androidDownload(){
	window.location.href = "http://7xlory.dl1.z0.glb.clouddn.com/xdkd.apk?attname=";
  }
  function iosDownload(){
	window.location.href = "https://appsto.re/cn/lIvq9.i";
  }
  if(navigator.userAgent.match(/MicroMessenger/i)){
      console.log("This is MicroMessenger'browser.");// 这是微信平台下浏览器
      window.stageHeight = parseInt(document.documentElement.clientHeight);
      $(".download").click(function(){
          $("body").height(window.stageHeight);
          $(".xd").show();
          return;
      });
  }
  if (navigator.userAgent.match(/Android/i)) {
	$(".download").click(function(){
		androidDownload();
	});
	$(".checkAll").click(function(){
		androidDownload();
	});
  }else if (navigator.userAgent.match(/iPhone/i)) {
	$(".download").click(function(){
		iosDownload();
	});
	$(".checkAll").click(function(){
		iosDownload();
	});
  }
  
});

var url;
$(function() {
// 动态获取微信jssdk自定义分享的config注入信息
/*
 * 确保你获取用来签名的url是动态获取的，动态页面可参见实例代码中php的实现方式。 如果是html的静态页面在前端通过ajax将url传到后台签名，
 * 前端需要用js获取当前页面除去'#'hash部分的链接（可用location.href.split('#')[0]获取,而且需要encodeURIComponent），
 * 因为页面一旦分享，微信客户端会在你的链接末尾加入其它参数，如果不是动态获取当前链接，将导致分享后的页面签名失败。
 */
// url = encodeURIComponent(location.href.split('#')[0]);//当前页面的完整链接
url = location.href;
var weixinFolder = $("#weixinFolder").val();
$.ajax({
		  type : "post",
		  url : "/"+weixinFolder+"/getJsApiMap",
		  timeout : 30000,
		  data :{"url":url},
		  dataType: "json",
		  async: false,// 同步
		  success: function (data){
			 // alert(data.signature);
			// 通过config接口注入权限验证配置
				wx.config({
				    debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
				    appId: 'wxb0ad9b23602b782e', // 必填，公众号的唯一标识
				    timestamp: data.timestamp, // 必填，生成签名的时间戳
				    nonceStr: data.nonceStr, // 必填，生成签名的随机串
				    signature: data.signature,// 必填，签名，见附录1
				    jsApiList: [
							'checkJsApi',
							'onMenuShareTimeline',
							'onMenuShareAppMessage',
				                ] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
				});
		  },
			 error: function(xhr, type){
				 console.log("error");
		     }
			});
});
		
wx.ready(function () {
	var shareTitle = $("#shareTitle").val();
	var shareContent = $("#shareContent").val();
	var shareImageUrl = $("#shareImageUrl").val();
	  // 1 判断当前版本是否支持指定 JS 接口，支持批量判断
	    wx.checkJsApi({
	      jsApiList: [
				"onMenuShareTimeline",
				"onMenuShareAppMessage"
	      ],
	      success: function (res) {
	    	  // 以键值对的形式返回，可用的api值true，不可用为false
	       // alert(JSON.stringify(res));
	      }
	    });
	  // 2. 分享接口
	  // 2.1 监听“分享给朋友”，按钮点击、自定义分享内容及分享结果接口
	    wx.onMenuShareAppMessage({
	      title: shareTitle,
	      desc: shareContent,
	      link: location.href,
	      imgUrl: shareImageUrl,
// trigger: function (res) {
// alert('用户点击发送给朋友');
// },
// success: function (res) {
// alert('已分享');
// },
// cancel: function (res) {
// alert('已取消');
// },
// fail: function (res) {
// alert(JSON.stringify(res));
// }
	    });
	   // alert('已注册获取“发送给朋友”状态事件');

	  // 2.2 监听“分享到朋友圈”按钮点击、自定义分享内容及分享结果接口
	    wx.onMenuShareTimeline({
	      title: shareTitle,
	      link: location.href,
	      imgUrl: shareImageUrl,
// trigger: function (res) {
// alert('用户点击分享到朋友圈');
// },
// success: function (res) {
// alert('已分享');
// },
// cancel: function (res) {
// alert('已取消');
// },
// fail: function (res) {
// alert(JSON.stringify(res));
// }
	    });
	  // alert('已注册获取“分享到朋友圈”状态事件');

// var shareData = {
// title: ' 微信JS-SDK DEMO',
// desc: '微信JS-SDK,帮助第三方为用户提供更优质的移动web服务',
// link: location.href,
// imgUrl:
// 'http://mmbiz.qpic.cn/mmbiz/icTdbqWNOwNRt8Qia4lv7k3M9J1SKqKCImxJCt7j9rHYicKDI45jRPBxdzdyREWnk0ia0N5TMnMfth7SdxtzMvVgXg/0'
// };
// wx.onMenuShareAppMessage(shareData);
// wx.onMenuShareTimeline(shareData);
});

wx.error(function (res) {
	  // alert(res.errMsg);
});
