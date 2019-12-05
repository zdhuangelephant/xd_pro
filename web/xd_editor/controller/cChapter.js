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
    var courseId = req.body.courseId;
    var checkInfo = check.checkParamDetail({courseId: courseId});
    if (!checkInfo.isOk) {
        var ret = {};
        ret.retcode = -1;
        ret.retdesc = checkInfo.info;
        res.send(ret);
    } else {
        service.list(id, courseId, function (node) {
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
        var ret = {};
        if (result) {
            ret.retcode = 0;
            ret.image = result;
        } else {
            ret.retcode = -1;
            ret.retdesc = 'Error!:添加图片失败!';
        }
        res.send(ret);
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

exports.upload = function (req, res, next) {
    var id = req.body.id;
    var courseId = req.body.courseId;
    var name = req.body.name;
    var checkInfo = check.checkParamDetail({id: id, courseId: courseId, name: name});
    if (!checkInfo.isOk) {
        var ret = {};
        ret.retcode = -1;
        ret.retdesc = checkInfo.info;
        res.send(ret);
    } else {
        service.upload(id, courseId, name, function (result) {
            var ret = {};
            if (result != null && typeof(result) != 'undefined') {
                ret.retcode = 0;
                ret.nodes = 'Success!';
            } else {
                ret.retcode = -1;
                ret.retdesc = 'Error! Can\'t be null page.';
            }
            res.send(ret);
        });
    }
}