/**
 * Created by Administrator on 2015/9/1.
 */
$(document).ready(function () {
  function init(){
    $(".click").click(function () {
      window.stageHeight = parseInt(document.documentElement.clientHeight);
      $(".phoneNav").height($("body").height());
      $("body").toggleClass("aaa", "");
      $(".phoneNav").slideToggle("1");
    });
    $(".phoneNav li").click(function () {
      $(".phoneNav").slideUp();
    });
    $(".phoneNav").click(function(){
         $(this).slideUp();
      })
  }
  init();

  $("[yy-include]").each(function(){
    var $this = $(this);
    var temp = $this.attr("yy-include");
    $.ajax({
      url: temp + "?" + Math.round(Math.random()*100000000),
      type: 'get',
      async:false,
      success: function (data) {
        $this.html(data);
        init();
      }
    });
  });
});