/**
 * 封面
 */
var pageList = [];
var len = $(".back").length;
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
    window.stageHeight = parseInt(document.documentElement.clientHeight);
    var item = $(this).touchwipe({
        wipeRight: function () {
            $(document).scrollTop(0);
            if (index > 0 && index < len - 1) {
                var page = {};
                page.id = prevId;
                var n = page.id;
                page.onReady = function () {
                    director.readyLock = true;
                    currentPageId = page.id;
                };
                page.onEnter = function () {
                    if(prevPage.height() > window.stageHeight){
                        $('#mstage').height( prevPage.height());
                    }
                    setTimeout(function(){
                        director.lock = false;
                    },300)
                };
                page.onExit = function () {
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
                };
                page.onEnter = function () {
                    if(nextPage.height() > window.stageHeight){
                            $('#mstage').height( nextPage.height());
                    }
                    setTimeout(function(){
                        director.lock = false;
                    },300)
                };
                page.onExit = function () {};
                director.replaceScene(page, director.isNext);
            }
        }
    });
    pageTouchMap[thisId]=item;
});
