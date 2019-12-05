/**
 * Created by Administrator on 2015/8/31.
 */
$(document).ready(function(){
    $("#job li").click(function(){
        var name = $(this).attr("class");
        $(this).addClass("on");
        //$("body").height('100%');
        //$("body").addClass("aaa");
        $("#"+name).show();
    });
    $(".shut").click(function(){
        $(this).parent().parent().hide();
        $("#job li").removeClass("on");
        $("body").removeClass("aaa");
    })

});
