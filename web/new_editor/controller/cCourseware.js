/*!
 *   editor - controllers/cEditor.js
 *   Copyright(c) 2015 zhaodan <zhaodan@corp.51xiaodou.com>
 *   Xiaodou Licensed
 */

"use strict"

/**
 * Module dependencies
 **/
var service = require('../service/sCourseware');
var check = require('../utils/check');


exports.list = function (req, res, next) {
    var id = req.body.id;
    service.list(id, function (node, select) {
        var ret = {};
        if (node) {
            ret.retcode = 0;
            ret.nodes = node;
            ret.select = select;
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

exports.edit = function (req, res, next) {
    var id = req.body.id;
    var level = req.body.level;
    var checkInfo = check.checkParamDetail({id: id, level: level});
    if (!checkInfo.isOk) {
        var ret = {};
        ret.retcode = -1;
        ret.retdesc = checkInfo.info;
        res.send(ret);
    } else {
        service.edit(level, id, function (canEdit) {
            var ret = {};
            if (canEdit) {
                ret.retcode = 0;
                ret.retdesc = 'Can Edit!';
            } else {
                ret.retcode = -1;
                ret.retdesc = 'Error!:该页面无法编辑';
            }
            res.send(ret);
        });
    }
}

exports.change = function (req, res, next) {
    var id = req.body.id;
    var targetId = req.body.targetId;
    var checkInfo = check.checkParamDetail({id: id, targetId: targetId});
    if (!checkInfo.isOk) {
        var ret = {};
        ret.retcode = -1;
        ret.retdesc = checkInfo.info;
        res.send(ret);
    } else {
        service.change(id, targetId, function (change) {
            var ret = {};
            if (change) {
                ret.retcode = 0;
                ret.retdesc = 'Success.';
            } else {
                ret.retcode = -1;
                ret.retdesc = 'Error!:交换顺序失败';
            }
            res.send(ret);
        });
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
                ret.retdesc = 'Error!:课件删除失败!';
            }
            res.send(ret);
        });
    }
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
        service.show(id, function (data) {
            if (data) {
                res.render("output/show", {
                    data: data
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

exports.getView = function (req, res, next) {
    var id = encodeURI(req.param("id"));
    var checkInfo = check.checkParamDetail({id: id});
    if (!checkInfo.isOk) {
        var ret = {};
        ret.retcode = -1;
        ret.retdesc = checkInfo.info;
        res.send(ret);
    } else {
        var data = service.show(id);
        var ret = {};
        if (data) {
            ret.retcode = 0;
            ret.data = data;
        } else {
            ret.retcode = -1;
            ret.retdesc = 'Error!:页面不存在';
        }
        res.send(ret);
    }

}
