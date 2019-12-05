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
var config = require('../config');
var chapconfig = require('../chapconfig');
var uuid = require('node-uuid');
var node = require('../model/node');
var db = require('../api/db');
var fs = require('fs');
var ejs = require('ejs');
var sCoureseware = require('./sCourseware');
var courseware = require('../model/courseware');
var chapterfac = require('../model/chapter');
var resourcefac = require('../model/resource');
var imagefac = require('../model/image');
var eventProxy = require('eventproxy');
var mpath = require('path');
var formidable = require("formidable");
var imgop = mpath.join(require('../config').basepath, 'public', 'images');
var baseop = mpath.join(__dirname, '../public/output');
var tempop = mpath.join(__dirname, '../public/template');
var cssloc = mpath.join(__dirname, '../public/item/css');
var jsloc = mpath.join(__dirname, '../public/item/js');
var imageloc = mpath.join(__dirname, '../public/images');
var dowwnloadop = mpath.join(__dirname, '../public/item/download');
var zip = require("node-native-zip");
var qnutil = require('../api/qn');

exports.list = function (id, courseId, callback) {
    var root = node.createParent(courseId, "自考君");
    root.open = true;
    root.isRoot = true;
    resolveFolder(root, root.id, id, function (root) {
        callback(root);
    });
}

function resolveFolder(root, productId, id, callbackfunc) {
    var sql = "select * from xd_course_chapter where subject_id = ? order by list_order";
    var args = [productId];
    db.execQuery({
        sql: sql, args: args, callback: function (results) {
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
            var _node = node.createParent(list[chapter].id, list[chapter].index + " " + list[chapter].name);
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
    incoming.on('file', function (field, file) {
        var sql = "select id from xd_editor_img where name like ? order by id desc limit 1";
        var args = [file.name + '%'];
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
                            callbackfunc(imagefac.createOne({name: args[0], src: args[1]}));
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
    zipFile(id, function (fileName, fileDst) {
        callback(fileName);
    });
};

function zipFile(id, callback) {
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
                    if (pages) {
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
                        var archive = new zip();
                        var images = new Object();
                        for (var i in pages) {
                            var data = pages[i].data;
                            var info = ejs.render(_tempinfo, {data: data});
                            info = info.replace(/images\//g, "");
                            var patt = /upload_([^\"]*)\"/g;
                            var match = info;
                            var row;
                            while (row = patt.exec(match)) {
                                if (row[1] && row[1].length > 0) {
                                    console.log(row[1]);
                                    if (!images[row[1]]) {
                                        images[row[1]] = "1";
                                        filelist.push({
                                            name: "upload_" + row[1].toString().substr(0, 39),
                                            path: mpath.join(imageloc, "upload_" + row[1].toString())
                                        });
                                        match = match.replaceAll(row[1], "");
                                        info = info.replaceAll(row[1], row[1].toString().substr(0, 39));
                                    }
                                } else {
                                    break;
                                }
                            }
                            archive.add("index" + (parseInt(i) + 1).toString() + ".html", new Buffer(info, "utf8"));
                        }
                        archive.addFiles(filelist, function (err) {
                            if (err) return console.log("err while adding files", err);
                            var buff = archive.toBuffer();
                            fs.writeFile(_fileDst, buff, function () {
                                console.log("Finished");
                                callback(_id + ".zip", _fileDst);
                            });
                        });
                    } else {
                        console.log("zipfile null page!");
                        callback(null, null);
                    }
                });
            });
        } else {
            console.log("zipfile write fail!");
            callback(null, null);
        }
    });
}

exports.upload = function (id, courseId, name, callback) {
    sCoureseware.beforeUpload(id, function (need) {
        if (need){
            zipFile(id, function (fileName, fileDst) {
                if (fileName && fileDst) {
                    qnutil.uploadFile(fileName, fileDst, function (ret) {
                        deleteAndInsertCourseware(id, courseId, name, ret, callback);
                    });
                } else {
                    callback(null);
                }
            });
        }else{
            callback("ok");
        }
    });
}

function deleteAndInsertCourseware(chapterId, courseId, name, ret, callback) {
    var resource = resourcefac.createOne(ret.domain_url, chapconfig.url.format(chapterId));
    var sql = "select id from xd_course_resource_html5 where `chapter_id` = ? and `course_id` = ?";
    var args = [chapterId, courseId];
    var id = null;
    db.execQuery({
        sql: sql, args: args, callback: function (results) {
            if (!results) {
                callback(null);
                return;
            } else if(results.length == 0){
                sql = "insert into xd_course_resource_html5(`chapter_id`, `name`, `url`, `detail`, `course_id`, `file_url`, `status`) value (?, ?, ?, ?, ?, ?, ?)";
                args = [chapterId, name, resource.url, name, courseId, resource.fileUrl, 99];
            } else {
                id = results[0]["id"];
                sql = "update xd_course_resource_html5 set `file_url` = ?, `url` = ? where `id` = ?";
                args = [resource.fileUrl, resource.url, id];
            }
            db.execQuery({
                sql: sql, args: args, callback: function (results) {
                    if (results) {
                        sCoureseware.afterUpload(chapterId, id, resource);
                        callback("ok");
                    } else {
                        callback(null);
                    }
                }
            });
        }
    });
}