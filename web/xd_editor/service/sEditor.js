/*!
 *   editor - controllers/cEditor.js
 *   Copyright(c) 2015 zhaodan <zhaodan@corp.51xiaodou.com>
 *   Xiaodou Licensed
 */

"use strict"

/**
 * Module dependencies
 **/
var fs = require('fs');
var mpath = require('path');
var config = require('../config');
var baseop = mpath.join(__dirname, '../public/output');
var tempop = mpath.join(baseop, 'template');
var db = require('../api/db');
var courseware = require('../model/courseware');
var eventProxy = require('eventproxy');
var ejs = require('ejs');
var saveOrUpdate = {};


var save = {};
save.saveCourseware = function (cinfo, callback) {
    if (!cinfo) return;
    cinfo.currpath = baseop;
    cinfo.file = encodeURI(cinfo.id) + ".html";
    var _editfile = cinfo.file.toString(), _downfile = "d" + _editfile;
    cinfo._edit_url = mpath.join(cinfo.currpath, _editfile);
    cinfo._download_url = mpath.join(cinfo.currpath, _downfile);
    saveFile(cinfo, function (saveedit, savedownload) {
        var sql = "insert into xd_editor_courseware (`id`, `name`, `filename`, `dfilename`, `prefix`, `edit_url`, `show_url`, `download_url`) values (?, ?, ?, ?, ?, ?, ?, ?)";
        var args = [cinfo.id, cinfo.name, cinfo.file, _downfile, cinfo.prefix, 'output/' + _editfile, '/courseware/show?id=' + cinfo.id, 'output/' + _downfile];
        db.execQuery({
            sql: sql, args: args, callback: function (results) {
                if (results) {
                    callback(_editfile);
                }
            }
        });
    });
}
exports.save = save;

var update = {}
update.updateCourseware = function (cinfo, callback) {
    if (!cinfo) return;
    cinfo.currpath = baseop;
    cinfo.file = encodeURI(cinfo.id) + ".html";
    var _editfile = cinfo.file.toString(), _downfile = "d" + _editfile;
    cinfo._edit_url = mpath.join(cinfo.currpath, _editfile);
    cinfo._download_url = mpath.join(cinfo.currpath, _downfile);
    saveFile(cinfo, function (saveedit, savedownload) {
        var sql = "update xd_editor_courseware set `name` = ?, `filename` = ?, `dfilename` = ?, `edit_url` = ?, `show_url` = ?, `download_url` = ?, `resource_state` = ? where `id` = ?";
        var toResourceState = (cinfo.resourceState == config.resource_state.NOVERSION) ? config.resource_state.NOVERSION : config.resource_state.UNSYNC;
        var args = [cinfo.name, cinfo.file, _downfile, 'output/' + _editfile, '/courseware/show?id=' + cinfo.id, 'output/' + _downfile, toResourceState, cinfo.id];
        db.execQuery({
            sql: sql, args: args, callback: function (results) {
                if (results) {
                    callback(_editfile);
                }
            }
        });
    });
}
update.recoverCourseware = function (cinfo, callback) {
    if (!cinfo) return;
    var sql = "update xd_editor_courseware set `status` = 1 where `prefix` = ? and `name` = ?";
    var args = [cinfo.prefix, cinfo.name];
    db.execQuery({
        sql: sql, args: args, callback: function (results) {
            if (results) {
                callback("ok");
            }
        }
    });
}
exports.update = update;
saveOrUpdate.courseware = function (cinfo, callback) {
    if (!cinfo) return;
    var sql = "select * from xd_editor_courseware where `id` = ?";
    var args = [cinfo.id];
    db.execQuery({
        sql: sql, args: args, callback: function (results) {
            if (results && results.length > 0) {
                if (results[0].id == cinfo.id) {
                    cinfo.resourceState = results[0].resource_state;
                    update.updateCourseware(cinfo, callback);
                } else {
                    if (results[0].status == '1') {
                        callback("Err:same")
                    } else {
                        callback("Err:deleted")
                    }
                }
            } else {
                save.saveCourseware(cinfo, callback);
            }
        }
    });
}
exports.saveOrUpdate = saveOrUpdate;

var recover = {};
recover.courseware = function (cinfo, callback) {
    if (!cinfo) return;
    var sql = "select * from xd_editor_courseware where `name` = ? and `prefix` = ? and status = 0";
    var args = [cinfo.name, cinfo.prefix];
    db.execQuery({
        sql: sql, args: args, callback: function (results) {
            if (results && results.length > 0) {
                update.recoverCourseware(cinfo, callback);
            } else {
                callback("Error:�μ�������.")
            }
        }
    });
}
exports.recover = recover;

function saveFile(cinfo, callback) {
    var _templateId = cinfo.templateId.toString();
    var _tempop = mpath.join(tempop, _templateId);
    fs.exists(_tempop, function (exists) {
        if (exists) {
            var _temp = mpath.join(_tempop, "template.ejs");
            fs.exists(_temp, function (exists) {
                if (exists) {
                    var ep = eventProxy.create();
                    ep.all("saveedit", "savedownload", callback);
                    fs.exists(cinfo._edit_url, function (exists) {
                        if (exists)
                            fs.unlinkSync(cinfo._edit_url);
                        fs.writeFileSync(cinfo._edit_url, cinfo.data, "utf-8");
                        ep.emit("saveedit", cinfo._edit_url);
                    });
                    fs.exists(cinfo._download_url, function (exists) {
                        if (exists)
                            fs.unlinkSync(cinfo._download_url);
                        var _tempinfo = fs.readFileSync(_temp, 'utf-8');
                        fs.writeFileSync(cinfo._download_url, ejs.render(_tempinfo, {data: cinfo.data}), "utf-8");
                        ep.emit("savedownload", cinfo._download_url);
                    });
                }
            });
        }
    });
}

exports.main = function (id, callback) {
    var sql = "select * from xd_editor_courseware where id = ? and status = 1";
    var args = [id];
    db.execQuery({
        sql: sql, args: args, callback: function (results) {
            if (results.length == 1) {
                var model = courseware.createOne(results[0]);
                if (model && model.id) {
                    callback(model);
                }
            } else {
                callback(null);
            }
        }
    });
}