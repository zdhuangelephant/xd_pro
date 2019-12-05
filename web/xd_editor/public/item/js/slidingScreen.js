/**
 * jQuery Plugin to obtain touch gestures from iPhone, iPod Touch and iPad, should also work with Android mobile phones (not tested yet!)
 * Common usage: wipe images (left and right to show the previous or next image)
 *
 * @author Andreas Waltl, netCU Internetagentur (http://www.netcu.de)
 * @version 1.0 (15th July 2010)
 */
(function ($) {
    $.fn.touchwipe = function (settings) {
        if(navigator.userAgent.match(/Android/i)) {
            var config = {
                min_move_x: 10,
                min_move_y: 10,
                wipeLeft: function () {
                },
                wipeRight: function () {
                },
                wipeUp: function () {
                },
                wipeDown: function () {
                },
                preventDefaultEvents: false
            };
        } else {
            var config = {
                min_move_x: 50,
                min_move_y: 30,
                wipeLeft: function () {
                },
                wipeRight: function () {
                },
                wipeUp: function () {
                },
                wipeDown: function () {
                },
                preventDefaultEvents: false
            };
        }

        if (settings) $.extend(config, settings);
        this.each(function () {
            var startX;
            var startY;
            var startTime;
            var isMoving = false;
            function cancelTouch() {
                this.removeEventListener('touchmove', onTouchMove);
                startX = null;
                startY = null;
                isMoving = false;
            }
            function onTouchMove(e) {
                if (config.preventDefaultEvents) {
                    e.preventDefault();
                }
                if (isMoving) {
                    var x = e.touches[0].pageX;
                    var y = e.touches[0].pageY;
                    var dx = startX - x;
                    var dy = startY - y;
                    var time = new Date().getTime()-startTime.getTime();
                    var vx = Math.abs(dx)/time;
                    if (Math.abs(dy)<=Math.abs(dx) ) {
                        if (Math.abs(dx) >= config.min_move_x && vx>0.15) {
                            //alert("dx:"+dx+",dy:"+dy);
                            cancelTouch();
                            if (dx > 0) {
                                config.wipeLeft();
                            }
                            else {
                                config.wipeRight();
                            }
                        }
                    }
                }
            }
            function onTouchStart(e) {
                startTime = new Date();
                if (e.touches.length == 1) {
                    startX = e.touches[0].pageX;
                    startY = e.touches[0].pageY;
                    isMoving = true;
                    this.addEventListener('touchmove', onTouchMove, false);
                }
            }
            this.addEventListener('touchstart', onTouchStart, false);
        });
        return config;
    };
})(jQuery);