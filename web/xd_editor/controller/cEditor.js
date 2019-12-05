/*!
 *   editor - controllers/cEditor.js
 *   Copyright(c) 2015 zhaodan <zhaodan@corp.51xiaodou.com>
 *   Xiaodou Licensed
 */

"use strict"

/**
 * Module dependencies
 **/
var ejs = require('ejs');
var service = require('../service/sEditor');
var check = require('../utils/check');
var Redirect = require('../utils/redirect');

exports.main = function (req, res, next) {
    var id = req.query["id"];
    var courseId = req.query["courseId"];
    var checkInfo = check.checkParamDetail({id: id, courseId: courseId});
    if (!checkInfo.isOk) {
        var ret = {};
        ret.retcode = -1;
        ret.retdesc = checkInfo.info;
        res.send(ret);
    } else {
        service.main(id, function (courseware) {
            if (!courseware || typeof courseware == undefined) {
                //var ret = {};
                //ret.retcode = -1;
                //ret.retdesc = "Error!:数据错误,课件不存在.";
                //res.send(ret);
                Redirect.redirect(res)
            } else {
                res.render('show', {
                    id: courseware.id.toString(),
                    url: courseware.edit_url.toString(),
                    prefix: courseware.prefix.toString(),
                    name: courseware.name.toString(),
                    courseId: courseId.toString()
                });
            }
        })
    }
}

exports.save = function (req, res, next) {
    savaOrUpdate(req, res, next, service.saveOrUpdate);
}

exports.recover = function (req, res, next) {
    savaOrUpdate(req, res, next, service.recover);
}

function savaOrUpdate(req, res, next, action) {
    savaOrUpdateCourseware(req, res, next, action.courseware);
}

function savaOrUpdateCourseware(req, res, next, action) {
    var params = {};
    params.prefix = req.body.prefix ? req.body.prefix : null;
    params.id = req.body.id ? req.body.id : null;
    params.name = req.body.name ? req.body.name : null;
    params.templateId = req.body.templateId ? req.body.templateId : null;
    params.data = req.body.data ? req.body.data : null;

    var checkInfo = check.checkParamDetail({
        id: params.id,
        name: params.name,
        prefix: params.prefix,
        templateId: params.templateId,
        data: params.data
    });
    if (!checkInfo.isOk) {
        var ret = {};
        ret.retcode = -1;
        ret.retdesc = checkInfo.info;
        res.send(ret);
    } else {
        action(params, function (_filename) {
            var ret = {};
            if (_filename) {
                if (_filename == "Err:same") {
                    ret.retcode = -1;
                    ret.retdesc = "存在同名课件,请修改课件名.";
                } else if (_filename == "Err:deleted") {
                    ret.retcode = -3;
                    ret.retdesc = "该课件已被删除,是否需要恢复?";
                } else {
                    ret.retcode = 0;
                    ret.retdesc = "保存成功";
                }
            } else {
                ret.retcode = -1;
                ret.retdesc = "error!";
            }
            res.send(ret);
        });
    }
}