/**
 * Created by Administrator on 2015/7/9.
 */


$(function () {


    $.ajax({
        url: "template/template.html",
        type: 'get',
        async:false,
        success: function (data) {
            $(".screen").html(data)
            console.log("load success")
        }
    });
    TweenMax.to("#page1 .exam",1,{marginTop:"140%",ease:Power1.Elastic}); TweenMax.to(".exam",1,{marginTop:5,globalTimeScale:3});
    TweenMax.to("#page1 .detail",1,{marginTop:"140%",ease:Power1.Elastic}); TweenMax.to(".detail",1,{marginTop:5,delay:0.2});
    $(".topIco").click(function(){
        $(this).toggleClass("deploy");
        $(this).parent().next().slideToggle();
    });
});