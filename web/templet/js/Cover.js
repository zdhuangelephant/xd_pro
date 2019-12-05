/**
 * 封面
 */
var pageList = [];
var len = $(".back").length;
//var currentPageId;
var pageTouchMap = {};
function nextPage() {
    pageTouchMap[currentPageId].wipeLeft();
}
$("div").find(".back").each(function (index) {
    var prevId = $(this).prev().attr("id");
    var nextId = $(this).next().attr("id");
    var thisId = $(this).attr("id");
    var thisPage = $(this);
    var prevPage = $(this).prev();
    var nextPage = $(this).next();
    //var flag = true;
    var item = $(this).touchwipe({
        wipeRight: function () {
            $(document).scrollTop(0);
            if (index > 0) {
                var page = {};
                page.id = prevId;
                var n = page.id;
                page.onReady = function () {
                    director.readyLock = true;
                    currentPageId = page.id;
                    var order = n.substr(n.length-1,1);
                    if(order%3 == 1){
                        $(".exam .move2").width("100%");
                        $(".exam .move2 .cartoon").css('display','block');
                        $(".exam .move2 .type").css('display','block');
                        $(" .topMain").width(30);
                        $(".topMain span").hide();
                        $(".topMain span").css('opacity',0);
                        $(".top .topMain").width(30);
                        TweenMax.from(".topMain",0.2, {
                            css: {top:-80,rotation:-270}, ease: Power2.easeOut
                            ,delay:0.2, onComplete: function () {
                                TweenMax.to(".topMain",0.2,{width:"80%",ease:Power2.easeOut});
                                TweenMax.to(".topMain span",0.2,{display:"block",opacity:1,delay:0.1});
                            }
                        });
                        TweenMax.from(".topLeft",1.1,{left:"-15%",ease:Elastic.easeOut,delay:0.4});
                        TweenMax.from(".topRight",1.1,{right:"-15%",ease:Elastic.easeOut,delay:0.4});
                        //考点动画
                        TweenMax.from(".exam .examMove",0.5,{opacity:0,ease:Power2.easeOut,delay:0.7});
                        //图片动画
                        TweenMax.from(".picture img",0.3,{opacity:0,delay:0.95,ease:Power2.easeOut});
                    }
                    if(order%3 == 2){//第二页
                        $(".topMain").width("80%");
                        $(".exam .move2").width(30);
                        $(".exam .move2 .cartoon").css('display','none');
                        $(".exam .move2 .type").css('display','none');
                        TweenMax.from(".exam .move2",0.6, {
                            css: {rotation:-360}, ease: Power2.easeOut
                            ,delay:0.3, onComplete: function () {
                                TweenMax.to(".exam .move2",0.3,{width:"100%",ease:Power2.easeOut});
                                TweenMax.to(".exam .move2 .cartoon",0.3,{display:"block",opacity:1,delay:0.05});
                                TweenMax.to(".exam .move2 .type",0.3,{display:"block",opacity:1,delay:0.05});
                            }
                        });
                        //图片动画
                        TweenMax.from("#"+page.id+" .picture img",0.3,{opacity:0,delay:1.1,ease:Power2.easeOut});
                    }
                    if(order%3 == 0){
                        //第三页
                        $("#"+page.id+" .topLeft").hide();
                        $("#"+page.id+" .topLeft").css("opacity","0");
                        $("#"+page.id+" .topRight").hide();
                        $("#"+page.id+" .topRight").css("opacity","0");
                        $("#"+page.id+" .topMain span").hide();
                        $("#"+page.id+" .topMain span").css("opacity","0");//以上为toP初始化
                        $("#"+page.id+" .exam .move2").width(30);
                        $("#"+page.id+" .exam .move2").height(5);
                        $("#"+page.id+" .exam .move2 .cartoon").css('display','none');
                        $("#"+page.id+" .exam .move2 .type").css('display','none');//以上为考点初始化
                        TweenMax.from("#"+page.id+" .top",0.6,{width:"5%",opacity:0,delay:0.4});
                        TweenMax.to("#"+page.id+" .topMain span",0.4,{display:"block",opacity:1,delay:0.77});
                        TweenMax.to("#"+page.id+" .topLeft",0.8,{display:"block",opacity:1,delay:0.8});
                        TweenMax.to("#"+page.id+" .topRight",1,{display:"block",opacity:1,delay:0.8});//以上为toP动画
                        //考点动画
                        TweenMax.from("#"+page.id+" .exam .move2",0.2, {
                            css: {width:0,rotation:-360}, ease: Power2.easeOut
                            ,delay:0.7, onComplete: function () {
                                TweenMax.to("#"+page.id+" .exam .move2",0.1,{height:30,ease:Power2.easeOut});
                                TweenMax.to("#"+page.id+" .exam .move2",0.3,{width:"100%",ease:Power2.easeOut,delay:0.1});
                                TweenMax.to("#"+page.id+" .exam .move2 .cartoon",0.3,{display:"block",opacity:1,delay:0.3});
                                TweenMax.to("#"+page.id+" .exam .move2 .type",0.3,{display:"block",opacity:1,delay:0.3});
                            }
                        });
                        //图片动画
                        TweenMax.from("#"+page.id+" .picture img",0.3,{opacity:0,delay:1.2,ease:Power2.easeOut});
                    }
                };
                page.onEnter = function () {
                    $("#mstage").height(prevPage.height());
                    if(navigator.userAgent.match(/Android/i)) {
                        try {
                            var str = $("#"+ currentPageId +" .detail").text().replaceAll("[\\s：\:、]+","。");
                            var data = { 'content':str };
                            var dataJson = JSON.stringify(data);
                            window.js2Android.send(dataJson);
                        } catch (e) {
                        }
                    } else {
                        try {
                            var str = $("#"+ currentPageId +" .detail").text().replaceAll("[\\s：\:、]+","。");
                            var data = { 'content':str };
                            jsBridge.send(data, function(responseData) {
                            });
                        } catch (e) {
                        }
                    }
                    setTimeout(function(){
                        director.lock = false;
                    },800)
                };
                page.onExit = function () {
                    TweenMax.killTweensOf("#" + page.id + " .exam");
                    TweenMax.killTweensOf("#" + page.id + " .detail");
                };
                director.replaceScene(page, !director.isNext);
            }
        },
        wipeLeft: function () {
            $(document).scrollTop(0);
            if (index < len - 1) {
                var page = {};
                page.id = nextId;
                var n = page.id;
                page.onReady = function () {
                    currentPageId=page.id;
                    var order = n.substr(n.length-1,1);
                    if(order%3 == 1){
                        $(".exam .move2").width("100%");
                        $(".exam .move2 .cartoon").css('display','block');
                        $(".exam .move2 .type").css('display','block');
                        $("#page1 .topMain").width(30);
                        $(".topMain span").hide();
                        $(".topMain span").css('opacity',0);
                        $(".top .topMain").width(30);
                        TweenMax.from(".topMain",0.2, {
                            css: {top:-80,rotation:-270}, ease: Power2.easeOut
                            ,delay:0.2, onComplete: function () {
                                TweenMax.to(".topMain",0.2,{width:"80%",ease:Power2.easeOut});
                                TweenMax.to(".topMain span",0.2,{display:"block",opacity:1,delay:0.1});
                            }
                        });
                        TweenMax.from(".topLeft",1.1,{left:"-15%",ease:Elastic.easeOut,delay:0.4});
                        TweenMax.from(".topRight",1.1,{right:"-15%",ease:Elastic.easeOut,delay:0.4});
                        //考点动画
                        TweenMax.from(".exam .examMove",0.5,{opacity:0,ease:Power2.easeOut,delay:0.7});
                        //图片动画
                        TweenMax.from(".picture img",0.3,{opacity:0,delay:0.95,ease:Power2.easeOut});
                    }
                    if(order%3 == 2){//第二页
                        $(".topMain").width("80%");
                        $(".exam .move2").width(30);
                        $(".exam .move2 .cartoon").css('display','none');
                        $(".exam .move2 .type").css('display','none');
                        TweenMax.from(".exam .move2",0.6, {
                            css: {rotation:-360}, ease: Power2.easeOut
                            ,delay:0.3, onComplete: function () {
                                TweenMax.to(".exam .move2",0.3,{width:"100%",ease:Power2.easeOut});
                                TweenMax.to(".exam .move2 .cartoon",0.3,{display:"block",opacity:1,delay:0.05});
                                TweenMax.to(".exam .move2 .type",0.3,{display:"block",opacity:1,delay:0.05});
                            }
                        });
                        //图片动画
                        TweenMax.from("#"+page.id+" .picture img",0.3,{opacity:0,delay:1.1,ease:Power2.easeOut});
                    }
                    if(order%3 == 0){
                        //第三页
                        $("#"+page.id+" .topLeft").hide();
                        $("#"+page.id+" .topLeft").css("opacity","0");
                        $("#"+page.id+" .topRight").hide();
                        $("#"+page.id+" .topRight").css("opacity","0");
                        $("#"+page.id+" .topMain span").hide();
                        $("#"+page.id+" .topMain span").css("opacity","0");//以上为toP初始化
                        $("#"+page.id+" .exam .move2").width(30);
                        $("#"+page.id+" .exam .move2").height(5);
                        $("#"+page.id+" .exam .move2 .cartoon").css('display','none');
                        $("#"+page.id+" .exam .move2 .type").css('display','none');//以上为考点初始化
                        TweenMax.from("#"+page.id+" .top",0.6,{width:"5%",opacity:0,delay:0.4});
                        TweenMax.to("#"+page.id+" .topMain span",0.4,{display:"block",opacity:1,delay:0.77});
                        TweenMax.to("#"+page.id+" .topLeft",0.8,{display:"block",opacity:1,delay:0.8});
                        TweenMax.to("#"+page.id+" .topRight",1,{display:"block",opacity:1,delay:0.8});//以上为toP动画
                        //考点动画
                        TweenMax.from("#"+page.id+" .exam .move2",0.2, {
                            css: {width:0,rotation:-360}, ease: Power2.easeOut
                            ,delay:0.7, onComplete: function () {
                                //$("#page3 .exam .move2").height(30);
                                TweenMax.to("#"+page.id+" .exam .move2",0.1,{height:30,ease:Power2.easeOut});
                                TweenMax.to("#"+page.id+" .exam .move2",0.3,{width:"100%",ease:Power2.easeOut,delay:0.1});
                                TweenMax.to("#"+page.id+" .exam .move2 .cartoon",0.3,{display:"block",opacity:1,delay:0.3});
                                TweenMax.to("#"+page.id+" .exam .move2 .type",0.3,{display:"block",opacity:1,delay:0.3});
                            }
                        });
                        //图片动画
                        TweenMax.from("#"+page.id+" .picture img",0.3,{opacity:0,delay:1.2,ease:Power2.easeOut});
                    }
                };
                page.onEnter = function () {
                    $("#mstage").height(nextPage.height());
                    if(navigator.userAgent.match(/Android/i)) {
                        try {
                            var str = $("#"+ currentPageId +" .detail").text().replaceAll("[\\s：\:、]+","。");
                            var data = { 'content':str };
                            var dataJson = JSON.stringify(data);
                            window.js2Android.send(dataJson);
                        } catch (e) {
                        }
                    } else {
                        try {
                            var str = $("#"+ currentPageId +" .detail").text().replaceAll("[\\s：\:、]+","。");
                            var data = { 'content':str };
                            jsBridge.send(data, function(responseData) {
                            });
                        } catch (e) {
                        }
                    }
                    setTimeout(function(){
                        director.lock = false;
                    },800)
                };
                page.onExit = function () {};
                director.replaceScene(page, director.isNext);
            }
            if(index == len-1){
                //flag = false;
                window.mstageHeight = parseInt(document.documentElement.clientHeight);
                $("#rock").height(window.mstageHeight);
                $('#mstage').height(window.mstageHeight);
                try {
                    var obj={};
                    obj.showTitle=false;
                    window.js2Android.onShowOrHideTitle(JSON.stringify(obj));
                } catch (e) {
                }
                try {
                    var obj = {};
                    obj.open = true;
                    window.js2Android.openOrCloseShark(JSON.stringify(obj));
                } catch (e) {
                }
                try {
                    var data = { 'content':"true" };
                    jsBridge.send(data, function(responseData) {
                    });
                } catch (e) {
                }

                TweenMax.to("#rock",0.5,{display:"block",left:0});
            }
        }
    });
    pageTouchMap[thisId]=item;
    $(".shut").click(function(){
        TweenMax.to("#rock",0.5,{display:"none",left:'100%'});
        $(".rockBottom").show();
        $('#mstage').height($("#"+currentPageId).height());
        if(navigator.userAgent.match(/Android/i)) {
            try {
                var obj = {};
                obj.open = false;
                window.js2Android.openOrCloseShark(JSON.stringify(obj));
            } catch (e) {
            }
            try {
                var obj={};
                obj.showTitle=true;
                window.js2Android.onShowOrHideTitle(JSON.stringify(obj));
            } catch (e) {
            }
        } else {
            try {
                var data = { 'content':"false" };
                jsBridge.send(data, function(responseData) {
                });
            } catch (e) {
            }
        }
    })
});
