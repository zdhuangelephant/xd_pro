/*!
 *   editor - controllers/cEditor.js
 *   Copyright(c) 2015 zhaodan <zhaodan@corp.51xiaodou.com>
 *   Xiaodou Licensed
 */

"use strict"

/**
 * Module dependencies
 **/
var service = require('../service/sChapter');
var redirect = require('../utils/redirect');
var check = require('../utils/check');

exports.list = function (req, res, next) {
    var id = req.body.id;
    service.list(id, function (node) {
        var ret = {};
        if (node) {
            ret.retcode = 0;
            ret.nodes = node;
        } else {
            ret.retcode = -1;
            ret.retdesc = 'Error!';
        }
        res.send(ret);
    });
}

exports.add = function (req, res, next) {
    var id = req.body.id;
    var level = req.body.level;
    var checkInfo = check.checkParamDetail({id: id, level: level});
    if (!checkInfo.isOk) {
        var ret = {};
        ret.retcode = -1;
        ret.retdesc = checkInfo.info;
        res.send(ret);
    } else {
        var node = service.add(id, level);
        var ret = {};
        if (node) {
            ret.retcode = 0;
            ret.nodes = node;
        } else {
            ret.retcode = -1;
            ret.retdesc = 'Error!';
        }
        res.send(ret);
    }
}

exports.del = function (req, res, next) {
    var id = req.body.id;
    var level = req.body.level;
    var checkInfo = check.checkParamDetail({id: id, level: level});
    if (!checkInfo.isOk) {
        var ret = {};
        ret.retcode = -1;
        ret.retdesc = checkInfo.info;
        res.send(ret);
    } else {
        service.del(level, id, function (deleted) {
            var ret = {};
            if (deleted) {
                ret.retcode = 0;
                ret.retdesc = 'Deleted!';
            } else {
                ret.retcode = -1;
                ret.retdesc = 'Error!:删除章节失败!';
            }
            res.send(ret);
        });
    }
}

exports.imgList = function (req, res, next) {
    service.imgList(function (list) {
        var ret = {};
        if (list) {
            ret.retcode = 0;
            ret.list = list;
        } else {
            ret.retcode = -1;
            ret.retdesc = 'Error!:获取图片列表失败!';
        }
        res.send(ret);
    });
}

exports.addImg = function (req, res, next) {
    service.saveImg(req, function (result) {
        redirect.redirectLoc(res, "/image.html");
    });
}

exports.show = function (req, res, next) {
    var id = encodeURI(req.param("id"));
    var checkInfo = check.checkParamDetail({id: id});
    if (!checkInfo.isOk) {
        var ret = {};
        ret.retcode = -1;
        ret.retdesc = checkInfo.info;
        res.send(ret);
    } else {
        service.show(id, function (pages) {
            if (pages) {
                res.render("item/index", {
                    pages: pages
                });
            } else {
                var ret = {};
                ret.retcode = -1;
                ret.retdesc = 'Error!:页面不存在';
                res.send(ret);
            }
        });
    }
}

exports.download = function (req, res, next) {
    var id = encodeURI(req.param("id"));
    var checkInfo = check.checkParamDetail({id: id});
    if (!checkInfo.isOk) {
        var ret = {};
        ret.retcode = -1;
        ret.retdesc = checkInfo.info;
        res.send(ret);
    } else {
        service.download(id, function (pages) {
            if (pages) {
                redirect.redirectLoc(res, "/download/" + pages);
            } else {
                var ret = {};
                ret.retcode = -1;
                ret.retdesc = 'Error!:页面不存在';
                res.send(ret);
            }
        });
    }
}