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

var root = null;

function initRoot(){
    var root = node.createParent(1, "超级教师");
    root.open = true;
    root.isRoot = true;
    resolveFolder(root, root.id, 0, function (root, select) {
        callback(root, select);
    });
}

exports.list = function (id, callback) {
    var root = node.createParent(1, "超级教师");
    root.open = true;
    root.isRoot = true;
    //var courselist_level = 5;
    //var start_level = 1;
    //var baseprefix = config.prefix;
    //resolveFile(root, start_level, courselist_level, root.id, baseprefix, id, function (root) {
    //    callback(root);
    //});
    resolveFolder(root, root.id, id, function (root, select) {
        callback(root, select);
    });
}

function resolveFolder(root, root_prefix, id, callbackfunc) {
    var sql = "select * from xd_editor_chapter where status = 1";
    db.execQuery({
        sql: sql, callback: function (results) {
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
            var _node = node.createParent(list[chapter].id, list[chapter].name);
            _node.level = list[chapter].level;
            _node.prefix = list[chapter].prefix;
            if(parseInt(_node.level) == 4){
                _node.url = '/chapter/show?id=' + list[chapter].id;
            }
            root.addChild(_node);
            if (parseInt(_node.level) < 4) {
                recursionFolder(_node, hash, id, function (node) {
                    if (node && typeof(node) != 'undefined' && node.open == true) {
                        root.open = true;
                    }
                    ep.emit(root.id, node);
                });
            } else {
                resolveFile(_node, id, function (node) {
                    if (node && typeof(node) != 'undefined' && node.open == true) {
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
    var sql = "select * from xd_editor_courseware where prefix = ? and status = 1 order by orders";
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
                        root.addChild(_node);
                    }
                    _eq.emit(root.id, _node);
                }
            } else {
                ep.emit(root.id, _node);
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
//function resolveFile(root, cur_level, courselist_level, parent_id, prefix, select_id, callbackfunc) {
//    //1.判断是否继续解析
//    if (cur_level > courselist_level)return;
//    //2.解析当前节点
//    var _prefix = prefix + config.split + parent_id.toString();
//    var ep = eventProxy.create();
//    if (cur_level < 5) {
//        var curlevel = config.courselist_level["l" + cur_level.toString()];
//        var courseField = (config.courselist + curlevel);
//        var courselist = config.courselist_info[courseField];
//        if (courselist && typeof courselist != 'undefinded') {
//            ep.after(_prefix, courselist.length, function (list) {
//                callbackfunc(root);
//            });
//            for (var i in courselist) {
//                if (i.startWith(_prefix)) {
//                    var _node = node.createParent(courselist[i].id, courselist[i].name);
//                    _node.level = cur_level;
//                    _node.prefix = _prefix;
//                    root.open = true;
//                    root.addChild(_node);
//                    var next_level = cur_level + 1;
//                    resolveFile(_node, next_level, courselist_level, courselist[i].id, _prefix, select_id, function (root) {
//                        ep.emit(_prefix, root);
//                    });
//                } else {
//                    ep.emit(_prefix, "wrong_" + i + ":" + _prefix);
//                }
//            }
//        } else {
//            callbackfunc(root);
//        }
//    } else {
//        var sql = "select * from xd_editor_courseware where prefix = ?";
//        var args = [_prefix];
//        ep.all(_prefix, function (root) {
//            callbackfunc(root);
//        });
//        db.execQuery({
//            sql: sql, args: args, callback: function (results) {
//                if (results.length > 0) {
//                    var _eq = eventProxy.create();
//                    _eq.after(_prefix, results.length, function (list) {
//                        ep.emit(_prefix, root);
//                    });
//                    for (var row in results) {
//                        var model = courseware.createOne(results[row]);
//                        if (model) {
//                            var _node = node.createNode(model.id, model.name);
//                            if (model.id == select_id) {
//                                _node.checked = true;
//                            }
//                            _node.level = 5;
//                            _node.prefix = model.prefix;
//                            _node.srcUrl = model.edit_url;
//                            _node.url = model.show_url;
//                            root.open = true;
//                            root.addChild(_node);
//                            _eq.emit(_prefix, root);
//                        }
//                    }
//                } else {
//                    ep.emit(_prefix, root);
//                }
//            }
//        });
//    }
//}

exports.add = function (prefix, leval) {
    if (leval != 4) return;
    var _level = parseInt(leval) + 1;
    var _prefix = prefix;
    var _id = uuid.v1();
    var _file = encodeURI(_id) + '.html';
    var newNode = node.createNode(_id, _id);
    //newNode.checked = true;
    newNode.level = _level;
    newNode.prefix = _prefix;
    newNode.srcUrl = '/courseware/' + _file;
    newNode.url = '/courseware/show?id=' + _file;
    return newNode;
}

exports.edit = function (leval, id, callback) {
    var sql = "select * from xd_editor_courseware where id = ? and status = 1";
    action(leval, id, sql, function (results) {
        if (results.length == 1) {
            callback(true);
        } else {
            callback(false);
        }
    });
}

exports.change = function (id, targetId, callback) {
    var sql = "select * from xd_editor_courseware where id in (?, ?)";
    var args = [id, targetId];
    db.execQuery({
        sql: sql, args: args, callback: function (results) {
            if (results.length == 2) {
                var model1 = courseware.createOne(results[0]);
                var model2 = courseware.createOne(results[1]);
                var sql = "update xd_editor_courseware set orders = ? where id = ?";
                var args1 = [model2.orders, model1.id];
                var args2 = [model1.orders, model2.id];
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
    action(leval, id, sql, function (results) {
        if (results.length == 1) {
            var model = courseware.createOne(results[0]);
            sql = "update xd_editor_courseware set status = 0 where id = ?";
            action(leval, id, sql, function (result) {
                if (results.length == 1) {
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

function action(leval, id, sql, callback) {
    if (leval != 5) return false;
    var files = fs.readdirSync(baseop);
    if (!files.length) {
        return console.log('No files to show!');
    } else {
        var args = [id];
        db.execQuery({
            sql: sql, args: args, callback: callback
        });
    }
}

exports.show = function (id, callback) {
    var sql = "select * from xd_editor_courseware where id = ?";
    var args = [id];
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