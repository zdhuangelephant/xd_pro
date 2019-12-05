/**
 * Created by Administrator on 2015/9/15.
 */
Zepto(function($){
    //var flag =true;//是否下载，开关
    if(localStorage.a == null ){
        localStorage.a=0
    }
    localStorage.a = parseInt(localStorage.a) +1;
    var text1="PASS";
    var text2="NO";
    if(localStorage.a % 2 ==0){
        text1="有对象";
        text2="单身狗"
    }
    //云彩动画
    TweenMax.to('#cloud1',6, {left: 45,  repeat: -1, yoyo: true});
    TweenMax.to('#cloud2',6, {right: 50,  repeat: -1, yoyo: true});
    //以下树叶动画
    TweenMax.to('#leftLeaf', 1, {left: 16, rotation: 7, repeat: -1, yoyo: true});
    TweenMax.to('#rightLeaf', 1, {right: 18, rotation: -8, repeat: -1, yoyo: true});
    //以下花瓣动画
    var drop2Count = Math.random() > 0.5 ? 1 : 2;
    var dropTimeList = [];
    if (drop2Count == 1) {
        var count = parseInt(Math.random() * 1000) % 5 + 1;
        dropTimeList.push(count);
    } else {
        var count1 = parseInt(Math.random() * 1000) % 5 + 1;
        dropTimeList.push(count1);
        var count2 = parseInt(Math.random() * 1000) % 5 + 1;
        dropTimeList.push(count2);
    }
//定义数组，存放各花瓣top,left
    var leafs = [];
    for(var i = 0; i<12; i++){
        var leaf = new Object();
        var radian = i * 30;
        var offsetTop = $("[data-index='"+i+"']").offset().top - $(".headBox").offset().top;
        var top = -(Math.cos(radian / 360 * (2 * Math.PI)) * 85);
        top += offsetTop;
        var left = Math.sin(radian / 360 * (2 * Math.PI)) * 85;
        var offsetLeft = $("[data-index='"+i+"']").offset().left - $(".headBox").offset().left;
        left += offsetLeft;
        leaf.top = top;
        leaf.left = left;
        leafs.push(leaf);
    }
    var time = 0;
    $(".leafTween").each(function(){
        $(this).bind("click", function () {
            var thisId = $(this).attr('id');
            var $this = $(this);
            time++;
            if (time % 2 == 1) {
                $("#face").css('background-image', "url('images/hehe.png')");
                $(".topText").html(text1);
            } else {
                $("#face").css('background-image', "url('images/cry.png')");
                $(".topText").html(text2);
            }
            if (dropTimeList.indexOf(time) != -1) {
                //拔掉2个
                $("#face").css('background-image', "url('images/aaa_03.png')");
            }
            $(this).css('z-index', 500);
            top=leafs[$(this).attr("data-index")].top;
            left=leafs[$(this).attr("data-index")].left;
            TweenMax.to(this, 0.1, {
                css: {top: top, left: left}, ease: Power2.easeOut
                , onComplete: function () {
                    TweenMax.to('#' + thisId, 0.5, {rotation: 30, delay: 0.05});
                    TweenMax.to('#' + thisId, 1.3, {
                        css: {top: 400, delay: 1}
                        , onComplete: function () {
                            $this.remove();
                            check();
                        }
                    });
                }
            });
            $(this).unbind("click");
            if (dropTimeList.indexOf(time) != -1) {
                //要掉2个
                var next = $(this).next();
                next.css('z-index', 500);
                top=leafs[$(this).attr("data-index")].top;
                left=leafs[$(this).attr("data-index")].left;
                TweenMax.to(next, 0.1, {
                    css: {top: top,left:left}, ease: Power2.easeOut//右边云
                    , onComplete: function () {
                        TweenMax.to(next, 0.5, {rotation: 30, delay: 0.05});
                        TweenMax.to(next, 1.3, {
                            css: {top: 400, delay: 1},
                            onComplete: function () {
                                next.remove();
                                check();
                            }
                        });
                    }
                });
                next.unbind("click");
            }
            function check(){
                var count = $(".leafTween").length;//剩余花瓣数
                if(count==0 && flag){//花瓣揪完并且需要下载
                    $(".downLoad").show();//下载条
                    $(".bottom").hide();
                    $(".bottomBtns").hide();//APP底部按钮组
                    $(".btns").show();// 分享按钮
                }
                if (count == 0 && !flag){//花瓣揪完，不需要下载
                    $(".bottom").hide();
                    $(".bottomBtns").show();
                }
            }
        })
    })
});