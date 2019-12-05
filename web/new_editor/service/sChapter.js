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
var uuid = require('node-uuid');
var node = require('../model/node');
var db = require('../api/db');
var fs = require('fs');
var ejs = require('ejs');
var courseware = require('../model/courseware');
var chapterfac = require('../model/chapter');
var imagefac = require('../model/image');
var eventProxy = require('eventproxy');
var mpath = require('path');
var formidable = require("formidable");
var imgop = mpath.join(require('../config').basepath, 'public', 'images');
var baseop = mpath.join(__dirname, '../public/output');
var tempop = mpath.join(__dirname, '../public/template');
var cssloc = mpath.join(__dirname, '../public/item/css');
var jsloc = mpath.join(__dirname, '../public/item/js');
var imageloc = mpath.join(__dirname, '../public/item/images2');
var dowwnloadop = mpath.join(__dirname, '../public/item/download');
var zip = require("node-native-zip");

exports.list = function (id, callback) {
    var root = node.createParent(1, "超级教师");
    root.open = true;
    root.isRoot = true;
    resolveFolder(root, root.id, id, function (root) {
        callback(root);
    });
}

function resolveFolder(root, root_prefix, id, callbackfunc) {
    var sql = "select * from xd_editor_chapter where status = 1";
    db.execQuery({
        sql: sql, callback: function (results) {
            if (results && results.length > 0) {
                var hash = chapterfac.createHash(results);
                recursionFolder(root, hash, id, function (root) {
                    callbackfunc(root);
                });
            } else {
                callbackfunc(root);
            }
        }
    });
}

function recursionFolder(root, hash, id, callbackfunc) {
    var list = hash[root.id];
    if (list && list.length > 0) {
        var ep = eventProxy.create();
        ep.after(root.id, list.length, function (list) {
            callbackfunc(root);
        });
        for (var chapter in list) {
            var _node = node.createParent(list[chapter].id, list[chapter].name);
            _node.level = list[chapter].level;
            _node.prefix = list[chapter].prefix;
            root.open = true;
            root.addChild(_node);
            recursionFolder(_node, hash, id, function (node) {
                ep.emit(root.id, node);
            });
        }
    } else {
        callbackfunc(root);
    }
}

exports.add = function (prefix, leval) {
    if (leval >= 4) return;
    var _level = parseInt(leval) + 1;
    var _prefix = prefix;
    var _id = uuid.v1();
    var _file = encodeURI(_id) + '.html';
    var newNode = node.createNode(_id, _id);
    newNode.checked = true;
    newNode.level = _level;
    newNode.prefix = _prefix;
    newNode.srcUrl = '/courseware/' + _file;
    newNode.url = '/courseware/show?id=' + _file;
    return newNode;
}

exports.del = function (leval, id, callback) {
    var sql = "select * from xd_editor_chapter where id = ?";
    action(leval, id, sql, function (results) {
        if (results.length == 1) {
            var model = courseware.createOne(results[0]);
            sql = "update xd_editor_chapter set status = 0 where id = ?";
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
    if (leval > 4) return false;
    var args = [id];
    db.execQuery({
        sql: sql, args: args, callback: callback
    });
}

exports.imgList = function (callback) {
    var sql = "select * from xd_editor_img where status = 1";
    db.execQuery({
        sql: sql, callback: function (results) {
            if (results && results.length > 0) {
                callback(imagefac.createList(results));
            } else {
                callback(null);
            }
        }
    });
}

exports.saveImg = function (req, callbackfunc) {
    var incoming = new formidable.IncomingForm();
    //listen to file event
    incoming.uploadDir = imgop;
    incoming.on('fileBegin', function (field, file) {
        //appended the origin filename at the end after random filename
        if (file.name) {
            file.path += file.name
        }
    }).on('file', function (field, file) {
        if (!file.size) {
            return;
        }
        var sql = "select * from xd_editor_img where name = ? and status = 1 order by id desc";
        var args = [file.name];
        db.execQuery({
            sql: sql, args: args, callback: function (results) {
                if (results && results.length > 0) {
                    var args = [file.name + "_" + (parseInt(results[0].id) + 1), file.path.toString().substr(imgop.length - 6)];
                } else {
                    var args = [file.name, file.path.toString().substr(imgop.length - 6)];
                }
                var sql = "insert into xd_editor_img (name, src) values (?, ?)";
                db.execQuery({
                    sql: sql, args: args, callback: function (results) {
                        if (results) {
                            callbackfunc("ok");
                        } else {
                            callbackfunc(null);
                        }
                    }
                });
            }
        });
    });
    incoming.parse(req);
}

exports.show = show;
function show(id, callback) {
    var sql = "select * from xd_editor_courseware where prefix = ? and status = 1 order by orders";
    var args = [id];
    db.execQuery({
        sql: sql, args: args, callback: function (results) {
            if (results.length > 0) {
                var pages = [];
                for (var i = 0, k = 1; i < results.length; i++) {
                    var model = courseware.createOne(results[i]);
                    if (model && model.filename) {
                        var filepath = mpath.join(baseop, model.filename);
                        var exists = fs.existsSync(filepath)
                        if (exists) {
                            pages.push({id: "spage" + k, data: fs.readFileSync(filepath, "utf-8")});
                            k++;
                        }
                    }
                }
                callback(pages);
                return;
            }
            callback(null);
        }
    });
}

exports.download = function (id, callback) {
    var _chapterId = id.toString();
    var _id = uuid.v1();
    var _tempop = mpath.join(tempop, "download.ejs");
    fs.exists(_tempop, function (exists) {
        if (exists) {
            var _fileDst = mpath.join(dowwnloadop, _id + ".zip");
            fs.exists(_fileDst, function (exists) {
                if (exists) {
                    fs.unlinkSync(_fileDst);
                }
                var _tempinfo = fs.readFileSync(_tempop, 'utf-8');
                show(id, function (pages) {
                    var info = ejs.render(_tempinfo, {pages: pages});
                    //info = info.replaceAll("iamges/", "");
                    info = info.replace(/images\//g, "");
                    var filelist = [
                        {name: "index.css", path: mpath.join(cssloc, "index_d.css")},
                        {name: "Cover.js", path: mpath.join(jsloc, "Cover.js")},
                        {name: "director.js", path: mpath.join(jsloc, "director.js")},
                        {name: "fat.js", path: mpath.join(jsloc, "fat.js")},
                        {name: "jquery-2.1.1.min.js", path: mpath.join(jsloc, "jquery-2.1.1.min.js")},
                        {name: "slidingScreen.js", path: mpath.join(jsloc, "slidingScreen.js")},
                        {name: "TweenMax.min.js", path: mpath.join(jsloc, "TweenMax.min.js")},
                        {name: "at.png", path: mpath.join(imageloc, "at.png")},
                        {name: "b.png", path: mpath.join(imageloc, "b.png")},
                        {name: "bknn_03.png", path: mpath.join(imageloc, "bknn_03.png")},
                        {name: "bo.png", path: mpath.join(imageloc, "bo.png")},
                        {name: "left.png", path: mpath.join(imageloc, "left.png")},
                        {name: "phone.png", path: mpath.join(imageloc, "phone.png")},
                        {name: "right_03.png", path: mpath.join(imageloc, "right_03.png")},
                        {name: "ro.png", path: mpath.join(imageloc, "ro.png")},
                        {name: "shut.png", path: mpath.join(imageloc, "shut.png")},
                        {name: "star_03.png", path: mpath.join(imageloc, "star_03.png")},
                        {name: "t.png", path: mpath.join(imageloc, "t.png")},
                        {name: "top.png", path: mpath.join(imageloc, "top.png")},
                        {name: "tu.png", path: mpath.join(imageloc, "tu.png")},
                        {name: "tu_03.png", path: mpath.join(imageloc, "tu_03.png")}
                    ];
                    var patt = /upload_([^\"]*)\"/g;
                    var match = info;
                    var row;
                    while (row = patt.exec(match)) {
                        if (row[1] && row[1].length > 0) {
                            console.log(row[1]);
                            filelist.push({
                                name: "upload_" + row[1].toString(),
                                path: mpath.join(imageloc, "upload_" + row[1].toString())
                            });
                            match = match.replaceAll(row[1], "");
                        } else {
                            break;
                        }
                    }
                    var archive = new zip();
                    archive.add("index.html", new Buffer(info, "utf8"));
                    archive.addFiles(filelist, function (err) {
                        if (err) return console.log("err while adding files", err);
                        var buff = archive.toBuffer();
                        fs.writeFile(_fileDst, buff, function () {
                            console.log("Finished");
                        });
                        callback(_id + ".zip");
                    });
                });
            });
        } else {
            callback(null);
        }
    });
}