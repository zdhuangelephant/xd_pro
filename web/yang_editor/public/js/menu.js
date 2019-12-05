$(function () {
  $("#uploadBtn").click(function () {
    var html = $(".screen").html();

    var req = {};
    req.data = html;
    $.post("/upload", req, function (resp) {
      if(resp.code == 0) {
        console.log(resp.url);
        window.open(resp.url);
      }
    })
  });
});