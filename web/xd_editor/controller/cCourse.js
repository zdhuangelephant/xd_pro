/*!
 *   editor - controllers/cEditor.js
 *   Copyright(c) 2015 zhaodan <zhaodan@corp.51xiaodou.com>
 *   Xiaodou Licensed
 */

"use strict"

/**
 * Module dependencies
 **/
var service = require('../service/sCourse');
var check = require('../utils/check');

exports.list = function (req, res, next) {
    service.list(function (courseList) {
        var ret = {};
        if (courseList) {
            ret.retcode = 0;
            ret.courseList = courseList;
        } else {
            ret.retcode = -1;
            ret.retdesc = 'Error!';
        }
        res.send(ret);
    });
}