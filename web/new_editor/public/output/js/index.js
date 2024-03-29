<!--2015/7/8-->
/**
 * Created by Administrator on 2015/6/29.
 */
$(document).ready(function(){
    $(" .exam .move2").width("100%");
    $(" .exam .move2 .cartoon").css('display','block');
    $(" .exam .move2 .type").css('display','block');
    $(".topMain span").hide();
    $(".topMain span").css('opacity',0);
    $(".top .topMain").width(30);
    TweenMax.from(".topMain",0.2, {
        css: {top:-80,rotation:-270}, ease: Power2.easeOut
        ,delay:0.2, onComplete: function () {
            TweenMax.to(".topMain",0.2,{width:"80%",ease:Power2.easeOut});
            TweenMax.to(".topMain span",0.2,{display:"block",opacity:1,delay:0.20});
        }
    });
    TweenMax.from(".topLeft",1.1,{left:"-15%",ease:Elastic.easeOut,delay:0.4});
    TweenMax.from(".topRight",1.1,{right:"-15%",ease:Elastic.easeOut,delay:0.4});
    //考点动画
    TweenMax.from(".exam .examMove",0.5,{opacity:0,ease:Power2.easeOut,delay:0.7});
    //TweenMax.from(".exam .examMove",0.5,{top:30,opacity:0,ease:Power2.easeOut,delay:0.2});
    //图片动画
    TweenMax.from(".picture img",0.3,{opacity:0,delay:0.85,ease:Power2.easeOut});
    $(".topIco").click(function(){
        $(this).toggleClass("deploy");
        $(this).parent().next().slideToggle();
    });
});