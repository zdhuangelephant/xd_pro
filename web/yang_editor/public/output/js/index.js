<!--2015/7/8-->
/**
 * Created by Administrator on 2015/6/29.
 */
$(document).ready(function(){
    TweenMax.to("#page1 .exam",1,{marginTop:"140%",ease:Power1.Elastic}); TweenMax.to(".exam",1,{marginTop:5,globalTimeScale:3});
    TweenMax.to("#page1 .detail",1,{marginTop:"140%",ease:Power1.Elastic}); TweenMax.to(".detail",1,{marginTop:5,delay:0.2});

    $(".topIco").click(function(){
        $(this).toggleClass("deploy");
        $(this).parent().next().slideToggle();
    });

     //var h   = $(".top").clientHeight+$(".exam").clientHeight+$(".detail").clientHeight;
    //var h = $(".top").height()+$(".exam").height()+$(".detail").height();
    //
    // $("#page1").height(h);

});