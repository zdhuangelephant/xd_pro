/*!
 *   editor - controllers/cEditor.js
 *   Copyright(c) 2015 zhaodan <zhaodan@corp.51xiaodou.com>
 *   Xiaodou Licensed
 */

"use strict"

/**
 * Module dependencies
 **/
require('../utils/string');
var db = require('../api/db');
var fs = require('fs');
var ejs = require('ejs');
var coursefac = require('../model/course');

exports.list = function (callbackfunc) {
    var sql = "select id, name from xd_course_subject";
    db.execQuery({
        sql: sql, callback: function (results) {
            if (results && results.length > 0) {
                var list = coursefac.createList(results);
                callbackfunc(list);
            } else {
                callbackfunc(null);
            }
        }
    });
}