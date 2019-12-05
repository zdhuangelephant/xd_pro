$(function () {
  var the;
  var type = "content";

  function initRight() {
//        $("#widthInput").val(the.width());
//        $("#heightInput").val(the.height());
//        $("#topInput").val(the.css("top"));
//        $("#leftInput").val(the.css("left"));
    var yyType = the.attr("yy-type");
    type = yyType;
    if(yyType != null && yyType != "") {
      $(".choose li").removeClass('on');
      var str = "." + yyType + "Box";
      $(".rightMain").children().hide();

      console.log("show:"+str)
      $(str).show();
      $("." +yyType).addClass('on');
    }

    $("#contentInput").val(the.html());
    $(".simditor-body").html(the.html());
    $("#imageInput").val(the.find("img").attr("src"));
  }

  $("#test1").click(function () {
    the = $(this);
    initRight();
  });
  $("#enterBtn").click(function () {
//        the.width($("#widthInput").val());
//        the.height($("#heightInput").val());
//        the.css("top", ($("#topInput").val()));
//        the.css("left", $("#leftInput").val());
    console.log("type:"+type)
    if (type == "content") {
      the.html($("#contentInput").val());
    } else if (type == "img") {
      the.find("img").attr("src", $("#imageInput").val());
    } else if (type == "richText") {
      the.html($('#editor').val());
    }
    the.attr("yy-type",type);
  });

  $(".yy-click").click(function () {
    the = $(this);
    initRight();
  });
  $("[yy-click]").click(function () {
    the = $(this);
    initRight();
  });
    $("#title").click(function () {
        the = $(this);
        initRight();
    });
    $("#knowledgePoint").click(function () {
        the = $(this);
        initRight();
    });

    //******************************选项卡
  $(".choose li").click(function () {
    type = $(this).attr("data-type");
    console.log("click:" + type);
    $(".choose li").removeClass('on');
    var str = "." + $(this).attr('class') + "Box";
    $(".rightMain").children().hide();
    $(str).show();
    $(this).addClass('on');
  });

  var editor = new Simditor({
    textarea: $('#editor'),
    toolbar: ['title', 'bold', 'italic', 'underline', 'strikethrough',
      'color', 'blockquote', 'code', 'table',
      'link', 'image', 'hr', 'indent', 'outdent']
  });
});
