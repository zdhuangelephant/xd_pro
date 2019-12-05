$(document).ready(function () {

  if (navigator.userAgent.match(/Android/i)) {
    $(".download a").attr("href", "joinUs.html")
  }
  if ($("body").width() > 999) {
    $(window).scroll(function () {

      //var top1 = $(".group1Text").offset().top;
      //var h1 = $(document).scrollTop() - top1;
      //console.log(h1)
      //$(".picture1").css("top",-h1*1.2-200);

      $(".mainCenter").children().each(function () {
        var groupText = $(this).find(".groupText");
        var picture = $(this).find(".picture");
        var top = groupText.offset().top;
        var h = top - $(document).scrollTop();

        //console.log(top + "," + $(document).scrollTop() + ":" + h)
        picture.css("top", h * 1.5 - 100);

      });
    });
  }
});