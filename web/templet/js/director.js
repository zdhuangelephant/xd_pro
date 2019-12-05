/**
 */
(function ($) {
    window.director = {
        TransitionMoveInB: 1,
        TransitionMoveInT: 2,
        TransitionMoveInLeft: 3,
        TransitionMoveInRight: 4,
        isNext : true,
        horizontal: true,
        lock:false,
        _curScene: null,
        _swipeupList: [],
        _swipedownList: [],
        _swipeleftList: [],
        _swiperightList: [],
        startup: function () {
            var self = this;
            touch.on('#mstage', 'swipeup', function (event) {
                for (var i = 0, len = self._swipeupList.length; i < len; ++i) {
                    self._swipeupList[i](event);
                }
            });
            touch.on('#mstage', 'swipedown', function (event) {
                for (var i = 0, len = self._swipedownList.length; i < len; ++i) {
                    self._swipedownList[i](event);
                }
            });
            touch.on('#mstage', 'swipeleft', function (event) {
                for (var i = 0, len = self._swipeleftList.length; i < len; ++i) {
                    self._swipeleftList[i](event);
                }
            });
            touch.on('#mstage', 'swiperight', function (event) {
                for (var i = 0, len = self._swiperightList.length; i < len; ++i) {
                    self._swiperightList[i](event);
                }
            });
        },
        replaceScene: function (scene, transition) {

            if(this.lock){
                return;
            }
            this.lock = true;
            var self = this;
            var el = null;
            if (null != this._curScene) {
                // 退出
                this._curScene.onExit();
                //var targetX = 0;
                //if (this.TransitionMoveInB == transition) {
                //    targetX = "-100%";
                //}
                //else {
                //    targetX = "100%";
                //}
                if (!this.horizontal) {
                    TweenMax.to('#' + this._curScene.id,0.6, {
                        css: {top: targetY}, ease: Power2.easeOut
                        , onComplete: function () {
                            el = $('#' + self._curScene.id);
                            el.css('display', 'none');
                        }
                    });
                } else {
                    var targetX = 0;
                    el = $('#' + self._curScene.id);
                    el.css("z-index","1");
                    var targetX = 0;
                    if (this.TransitionMoveInB == transition) {
                        targetX = "-100%";
                    }
                    else {
                        targetX = "100%";
                    }
                    TweenMax.to('#' + this._curScene.id, 0.6, {
                        css: {left:targetX}, ease: Power2.easeOut
                        , onComplete: function () {
                            el = $('#' + self._curScene.id);
                            el.css('display', 'none')
                            //el = $('#' + self._curScene.id);
                            //el.css('display', 'none');
                            //el = $('#' + self._curScene.id);
                            //el.css('display', 'none');
                            //el.css('width', '100%');
                            //el.css('height', 'auto');
                            //el.css('left', '0');
                            //el.css('top', '0');
                            //el.css('opacity', '1');
                            //el.css('z-index', '100');
                        }
                    });
                }
                var sceneEl = $('#' + scene.id);
                sceneEl.css('display', 'block');
                if (!this.horizontal) {
                } else {
                    if (this.isNext == transition) {
                        sceneEl.css('left', window.stageWidth);
                    }
                    else {
                        sceneEl.css('left', -window.stageWidth);
                    }
                }
                // 就绪
                scene.onReady();
                sceneEl.find('.arrow_up').height(sceneEl.find('.arrow_up img').height());
                if (!this.horizontal) {
                    TweenMax.to('#' + scene.id,0.6, {
                        css: {top: 0}, ease: Power2.easeOut
                        , onComplete: function () {
                            scene.onEnter();
                            self._curScene = scene;
                        }
                    });
                } else {
                    TweenMax.to('#' + scene.id,0.6, {
                        css: {left: 0}, ease: Power2.easeOut
                        , onComplete: function () {
                            scene.onEnter();
                            self._curScene = scene;
                        }
                    });
                }
                return;
            }
            this._curScene = scene;
            el = $('#' + scene.id);
            el.css('display', 'block');
            this._curScene.onReady();
            this._curScene.onEnter();
        },
        bindSwipeLeft: function (fun) {
            this._swipeleftList.push(fun);
        },
        unbindSwipeLeft: function (fun) {
            var idx = this._swipeleftList.indexOf(fun);
            if (idx >= 0) {
                this._swipeleftList.splice(idx, 1);
            }
        },
        bindSwipeRight: function (fun) {
            this._swiperightList.push(fun);
        },
        unbindSwipeRight: function (fun) {
            var idx = this._swiperightList.indexOf(fun);
            if (idx >= 0) {
                this._swiperightList.splice(idx, 1);
            }
        },
        bindSwipeUp: function (fun) {
            this._swipeupList.push(fun);
        },
        unbindSwipeUp: function (fun) {
            var idx = this._swipeupList.indexOf(fun);
            if (idx >= 0) {
                this._swipeupList.splice(idx, 1);
            }
        },
        bindSwipeDown: function (fun) {
            this._swipedownList.push(fun);
        },
        unbindSwipeDown: function (fun) {
            var idx = this._swipedownList.indexOf(fun);
            if (idx >= 0) {
                this._swipedownList.splice(idx, 1);
            }
        }
    };
})(jQuery);