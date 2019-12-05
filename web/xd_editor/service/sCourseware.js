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
var fs = require('fs');
var uuid = require('node-uuid');
var mpath = require('path');
var node = require('../model/node');
var config = require('../config');
var db = require('../api/db');
var courseware = require('../model/courseware');
var chapterfac = require('../model/chapter');
var baseop = mpath.join(__dirname, '../public/output');
var eventProxy = require('eventproxy');

exports.list = function (id, courseId, courseName, callback) {
    var root = node.createParent(0, courseName);
    root.open = true;
    root.isRoot = true;
    root.resourceState = config.resource_state.EMPTY;
    resolveFolder(root, id, courseId, function (root, select) {
        callback(root, select);
    });
}

function resolveFolder(root, id, productId, callbackfunc) {
    var sql = "select * from xd_course_chapter where subject_id = ? order by list_order";
    var args = [productId];
    db.execQuery({
        sql: sql, args: args, callback: function (results) {
            if (results && results.length > 0) {
                var hash = chapterfac.createHash(results);
                recursionFolder(root, hash, id, function (select) {
                    callbackfunc(root, select);
                });
            } else {
                callbackfunc(root, null);
            }
        }
    });
}

function recursionFolder(root, hash, id, callbackfunc) {
    var list = hash[root.id];
    if (list && list.length > 0) {
        var ep = eventProxy.create();
        ep.after(root.id, list.length, function (list) {
            callbackfunc(getSelected(list));
        });
        for (var chapter in list) {
            var _node = node.createParent(list[chapter].id, list[chapter].index + " " + list[chapter].name);
            _node.level = list[chapter].level;
            _node.prefix = list[chapter].prefix;
            _node.resourceState = config.resource_state.EMPTY;
            if (parseInt(_node.level) == 1) {
                _node.url = '/chapter/show?id=' + list[chapter].id;
            }
            root.addChild(_node);
            if (parseInt(_node.level) < 1) {
                recursionFolder(_node, hash, id, function (node) {
                    if (node && typeof(node) != 'undefined' && node.checked) {
                        root.open = true;
                    }
                    ep.emit(root.id, node);
                });
            } else {
                resolveFile(_node, id, function (node) {
                    if (node && typeof(node) != 'undefined' && node.checked) {
                        root.open = true;
                    }
                    ep.emit(root.id, node);
                });
            }
        }
    } else {
        callbackfunc(null);
    }
}

function resolveFile(root, select_id, callbackfunc) {
    var sql = "select * from xd_editor_courseware where prefix = ? order by orders";
    var args = [root.id];
    var ep = eventProxy.create();
    ep.all(root.id, function (root) {
        callbackfunc(root);
    });
    db.execQuery({
        sql: sql, args: args, callback: function (results) {
            if (results.length > 0) {
                var _eq = eventProxy.create();
                _eq.after(root.id, results.length, function (list) {
                    ep.emit(root.id, getSelected(list));
                });
                for (var row in results) {
                    var model = courseware.createOne(results[row]);
                    if (model) {
                        var _node = node.createNode(model.id, model.name);
                        if (model.id == select_id) {
                            _node.checked = true;
                            root.open = true;
                        }
                        _node.level = 5;
                        _node.prefix = model.prefix;
                        _node.srcUrl = model.edit_url;
                        _node.url = model.show_url;
                        _node.resourceState = model.resource_state;
                        _node.status = model.status;
                        root.addChild(_node);
                    }
                    _eq.emit(root.id, _node);
                }
            } else {
                ep.emit(root.id, null);
            }
        }
    });
}

function getSelected(list) {
    var select = null;
    if (list && list.length > 0) {
        for (var i in list) {
            if (list[i] && typeof(list[i]) != 'undefined' && list[i].checked) {
                select = list[i];
                break;
            }
        }
    }
    return select;
}

exports.add = function (prefix, leval) {
    if (leval != 2) return;
    var _level = parseInt(leval) + 1;
    var _prefix = prefix;
    var _id = uuid.v1();
    var _file = encodeURI(_id) + '.html';
    var newNode = node.createNode(_id, config.initDocName);
    //newNode.checked = true;
    newNode.level = _level;
    newNode.prefix = _prefix;
    newNode.srcUrl = '/courseware/' + _file;
    newNode.url = '/courseware/show?id=' + _file;
    return newNode;
}

exports.edit = function (leval, id, callback) {
    var sql = "select * from xd_editor_courseware where id = ? and status = ?";
    action(leval, [id, config.show_status.INUSE], sql, function (results) {
        if (results.length == 1) {
            callback(true);
        } else {
            callback(false);
        }
    });
}

exports.change = function (id, targetId, callback) {
    var sql = "select * from xd_editor_courseware where id in (?, ?) and status = ?";
    var args = [id, targetId, config.show_status.INUSE];
    db.execQuery({
        sql: sql, args: args, callback: function (results) {
            if (results.length == 2) {
                var model1 = courseware.createOne(results[0]);
                var model2 = courseware.createOne(results[1]);
                var sql = "update xd_editor_courseware set id = ?, name = ?, prefix = ?, filename = ?, dfilename = ?, show_url = ?, edit_url = ?, download_url = ?, resource_state = ? where orders = ?";
                var args1 = [model2.id, model2.name, model2.prefix, model2.filename, model2.dfilename, model2.show_url, model2.edit_url, model2.download_url, model2.resource_state, model1.orders];
                var args2 = [model1.id, model1.name, model1.prefix, model1.filename, model1.dfilename, model1.show_url, model1.edit_url, model1.download_url, model1.resource_state, model2.orders];
                var _eq = eventProxy.create();
                _eq.after("change", 2, function (list) {
                    callback(true);
                });
                db.execQuery({
                    sql: sql, args: args1, callback: function (results) {
                        _eq.emit("change", results);
                    }
                });
                db.execQuery({
                    sql: sql, args: args2, callback: function (results) {
                        _eq.emit("change", results);
                    }
                });
            } else {
                callback(false);
            }
        }
    });
}

exports.del = function (leval, id, callback) {
    var sql = "select * from xd_editor_courseware where id = ?";
    action(leval, [id], sql, function (results) {
        if (results.length == 1) {
            sql = "update xd_editor_courseware set status = ?, resource_state = ? where id = ?";
            action(leval, [config.show_status.UNUSE, config.resource_state.UNSYNC, id], sql, function (results) {
                if (results.affectedRows == 1 && results.changedRows == 1) {
                    callback(true);
                } else {
                    callback(false);
                }
            });
        } else {
            callback(false);
        }
    });
}

function action(leval, args, sql, callback) {
    if (leval != 3) return false;
    var files = fs.readdirSync(baseop);
    if (!files.length) {
        return console.log('No files to show!');
    } else {
        db.execQuery({
            sql: sql, args: args, callback: callback
        });
    }
}

exports.show = function (id, callback) {
    var sql = "select * from xd_editor_courseware where id = ? and status = ?";
    var args = [id, config.show_status.INUSE];
    db.execQuery({
        sql: sql, args: args, callback: function (results) {
            if (results.length == 1) {
                var model = courseware.createOne(results[0]);
                if (model && model.filename) {
                    var filepath = mpath.join(baseop, model.filename);
                    var exists = fs.existsSync(filepath)
                    if (exists) {
                        callback(fs.readFileSync(filepath, "utf-8"));
                        return;
                    }
                }
            }
            callback(null);
        }
    });
}

exports.beforeUpload = function (prefix, callback) {
    var sql = "select count(id) count from xd_editor_courseware where `prefix` = ? and resource_state != ?";
    var args = [prefix, config.resource_state.SYNC];
    db.execQuery({
        sql: sql, args: args, callback: function (results) {
            if (results && results.length > 0) {
                if (parseInt(results[0]['count']) > 0) {
                    callback(true)
                } else {
                    callback(false);
                }
            } else {
                callback(false);
            }
        }
    });
}

exports.afterUpload = function (chapterId, id, resource) {
    var sql = "update xd_editor_courseware set resource_state = ? where `prefix` = ?";
    var args = [config.resource_state.SYNC, chapterId];
    db.execQuery({
        sql: sql, args: args, callback: function (results) {
        }
    });
    if (id && typeof(id) != 'undefined') {
        var sql = "update xd_course_resource_html5 set `file_url` = ?, `url` = ?  where `id` = ?";
        var args = [resource.fileUrl, resource.url, id];
        db.execQuery({
            sql: sql, args: args, callback: function (results) {
            }
        });

        var sql = "update xd_course_product_item set `resource` = ? where `resource_id` = ? and resource_type = 4";
        var args = [JSON.stringify(resource), id];
        db.execQuery({
            sql: sql, args: args, callback: function (results) {
            }
        });
    }
}