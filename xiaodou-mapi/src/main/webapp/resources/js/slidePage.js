(function () {
    var opt = {
        'after': function () {
        },
        'before': function () {
        },
        'useArrow': true,
        'useAnimation': true,
    };
    window.slidePage = {
        'init': function (option) {
            $.extend(opt, option);
            initDom(opt);
            initEvent(opt)
        }
    };
    var obj = {
        'nextSlide': function(item) {
            item.css('-webkit-transform', 'translate3d(0,-100%,0)');
            item.next().css('-webkit-transform', 'scale(1)')
        },
        'prevSlide': function(item) {
            item.css('-webkit-transform', 'translate3d(0,100%,0)')
            item.prev().css('-webkit-transform', 'scale(1)');
        },
        'showSlide': function(item) {
            item.css('-webkit-transform', 'scale(1)');
            item.next().css('-webkit-transform', 'translate3d(0,100%,0)')
        }
    };
    var after = true;
    var prevItem;

    function swipeUp(event) {
        var item = $(event.target).closest('.item');
        if (!item.length) {
            return
        }
        nextSlide(item)
    }

    function swipeDown(event) {
        var item = $(event.target).closest('.item');
        if (!item.length) {
            return
        }
        prevSlide(item)

    }

    function nextSlide(item) {
        if (item.next().length) {
            currentItem = item.next();
            orderStep(item.next());
            obj.nextSlide(item);
            opt.before(item.index() + 1);
        } else {
            obj.showSlide(item)
        }
    }

    function prevSlide(item) {
        if (item.prev().length) {
            currentItem = item.prev();
            orderStep(item.prev());
            obj.prevSlide(item);
            opt.before(item.index() + 1);
        } else {
            obj.showSlide(item)
        }
    }

    function initDom(opt) {
        currentItem = $('.item').eq(opt.index - 1);
        prevItem = currentItem.prev();
        currentItem.css('-webkit-transform', 'translate3d(0px, 0px, 0px)');
        prevItem.css('-webkit-transform', 'translate3d(0px, -100%, 0px)');
        if (opt.index>1){
            $('.item').eq(0).css('-webkit-transform','scale(0)');
        }
/*        if (opt.useAnimation) {
            var items = $('.item');
            items.find('.step').addClass('hide');
            orderStep(items.eq(opt.index - 1))
        }
        if (opt.useArrow) {
            $('.item').slice(0, $('.item').length - 1).append('<span class="arrow"></span>')
        }*/
    }
    function orderStep(dom) {
        after = true;
        var steps = $(dom).find('.step');
        steps.forEach(function (item) {
            var time = $(item).attr('data-delay') || 100;
            setTimeout(function () {
                $(item).removeClass('hide')
            }, time)
        })
    }

    function initEvent(opt) {
        var box = false;
        $('.box').on('touchstart', function (e) {
            box = true;
        });
        $('.box').on('touchend', function (e) {
            box = false;
        });
        $('.slidePage-container').on('touchmove', function (e) {
            if (box == true)return;
            e.preventDefault();
        });
        $('.item').on({
            'swipeUp': swipeUp,
            'swipeDown': swipeDown
        });
        $('.item').on('transitionend webkitTransitionEnd', function (event) {
            if (after) {
                opt.after($(event.target).index() + 1);
                after = false;
            }

        })
    }
})();