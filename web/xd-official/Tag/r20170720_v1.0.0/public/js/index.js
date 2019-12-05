$(document).ready(function () {

  //根据QueryString参数名称获取值
  function getQueryStringByName(name){
    var result = location.search.match(new RegExp("[\?\&]" + name+ "=([^\&]+)","i"));
    if(result == null || result.length < 1){
      return "";
    }
    return result[1];
  }

  function download(){
    window.location.href = "/download.html";
  }
  function androidDownload(){
	window.location.href = "http://7xlory.dl1.z0.glb.clouddn.com/xdkd.apk?attname=";
  }
  function iosDownload(){
	window.location.href = "https://appsto.re/cn/lIvq9.i";
  }
  $(".android").click(function(){
	androidDownload();
  });
  
  $(".iphone").click(function(){
	iosDownload();
  });

  $(".download").click(function(){
    if(navigator.userAgent.match(/MicroMessenger/i)){
      download();
    }else{
      if (navigator.userAgent.match(/Android/i)) {
        androidDownload();
      }else if (navigator.userAgent.match(/iPhone/i)) {
        iosDownload();
      }
    }
  });

  var dflag = getQueryStringByName("download");
  if(dflag && dflag.toString() == "true"){
    if(navigator.userAgent.match(/MicroMessenger/i)){
      window.stageHeight = parseInt(document.documentElement.clientHeight);
      var xd = '<div class="xd"><img src="http://7xlos1.com1.z0.glb.clouddn.com/notice.png"></div>';
      $("body").html(xd);
      $(".xd").show();
    }else{
      if (navigator.userAgent.match(/Android/i)) {
        androidDownload();
      }else if (navigator.userAgent.match(/iPhone/i)) {
        iosDownload();
      }
    }
  }

  if ($("body").width() > 999) {
    $(window).scroll(function () {
      $(".mainCenter").children().each(function () {
        var groupText = $(this).find(".groupText");
        var picture = $(this).find(".picture");
        var top = groupText.offset().top;
        var h = top - $(document).scrollTop();
        picture.css("top", h * 1.5 - 250);
      });
    });
  }
});