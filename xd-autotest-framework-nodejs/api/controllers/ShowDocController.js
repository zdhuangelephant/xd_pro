/**
 * ShowDocController
 *
 * @description :: Server-side logic for managing showdocs
 * @help        :: See http://sailsjs.org/#!/documentation/concepts/Controllers
 */

var formidable = require('formidable');
var http = require('http');
var util = require('util');
var ScheduleService=require("../services/ScheduleServices");

module.exports = {
  findRequestItemByID: function (req, res) {
    var dic = {name: req.param("name")};
    console.log(dic);
    mongoService.Find('RequestItem', dic, function (record) {
      console.log(record);
      res.view('showdoc', {data: record});
    })
  },

  showdoc: function (req, res) {
    DocService.testcallback('newLogin_API', res, function (records) {
      res.view('showdoc', {data: records});
    })
  },

  showreq: function (req, res) {
    console.log(req.body);
    var item = {headers: {}, queryParam: {}};
    for (var key in req.body) {
      var re_header = new RegExp(/^header/);
      var flag_header = key.match(re_header);
      var re_queryParam = new RegExp(/^queryParam/);
      var flag_queryParam = key.match(re_queryParam);
      var headers = "headers";
      var queryParam = "queryParam";
      if (flag_header) {
        var headerkey = key.substring(6);
        item[headers][headerkey] = req.body[key];
      }
      else if (flag_queryParam) {
        var queryParamkey = key.substring(10);
        item[queryParam][queryParamkey] = req.body[key];
      } else {
        item[key] = req.body[key];
      }
    }
    RequestItem.update({name: req.body.name}, item, function (err, records) {
      if (err) {
        console.log(err);
      } else {
        return res.send('OK');
      }
    })
  },

  operatfile: function (req, res) {
    var form = new formidable.IncomingForm();
    form.parse(req, function (error, fields, files) {
      console.log(files); //打印上传文件结构
      console.log(files.path); //文件路径
      files[fields] = files
    });
  },

  //增加用例
  addtestcase: function (req, res) {
    var item = {headers: {}, queryParam: {}};
    for (var key in req.body) {
      var re_header = new RegExp(/^header/);
      var flag_header = key.match(re_header);
      var re_queryParam = new RegExp(/^queryParam/);
      var flag_queryParam = key.match(re_queryParam);
      var headers = "headers";
      var queryParam = "queryParam";
      if (flag_header) {
        var headerkey = key.substring(6);
        item[headers][headerkey] = req.body[key];
      }
      else if (flag_queryParam) {
        var queryParamkey = key.substring(10);
        item[queryParam][queryParamkey] = req.body[key];
      } else if (key == "disabled") {
        if (req.body[key] == "YES") {
          item[key] = true;
        } else {
          item[key] = false;
        }
      } else {
        console.log(key);
        item[key] = req.body[key];
      }
    }

    mongoService.Insert('RequestItem', item, function (record) {
      console.log(record);
      return res.ok();
    })
  },

  //增加用例集合
  add_tc_coll_2db: function (req, res) {
    var reqFolder = req.body["reqFolder"];
    if (reqFolder) {
      mongoService.Insert('ReqFolder', reqFolder, function (inserted) {
        if (inserted) {
          mongoService.Find('ReqFolder', {}, function (found) {
            res.view('testcase/index', {data: found, curr_tc_coll: inserted});
          });
        }
        else {
          mongoService.Find('ReqFolder', {}, function (found) {
            res.view('testcase/index', {data: found, curr_tc_coll: null});
          });
        }
      });
    }
    else {
      res.send({retcode: -1, msg: "入参reqFoder为空"});
    }

  },

  //保存用例: 如果用例不存在,则添加;若存在,则更新。
  save_case: function (req, res) {
    var reqFolder_uniqid = req.body["tc_coll_uniqId"];
    var caseItem = req.body["caseItem"];
    mongoService.Find("ReqFolder",{uniqID:reqFolder_uniqid},function (records) {
      /** 不存在ReqFolder对象  */
      if (!records || records.length == 0) {
        res.send({retcode: -1, message: "不存在data对象", data: "reqFolder_uniqid=" + reqFolder_uniqid});
      }
      /** 存在ReqFolder对象**/
      else {//存在 ReqFolder
        mongoService.Find("RequestItem", {uniqID: caseItem.uniqID}, function (case_found) {
        /** 存在RequestItem记录,可以更新到db中 **/
        if (case_found && case_found.length > 0) {
          console.log(caseItem);
          mongoService.Update("RequestItem", caseItem, {uniqID: caseItem.uniqID}, function (case_updated) {
            console.log(" 用户更新了RequestItem:  "+case_updated[0].name)
            mongoService.Find("ReqFolder", {}, function (found) {
              mongoService.Find("ReqFolder", {uniqID: reqFolder_uniqid}, function (coll_found) {
                res.view('testcase/index', {data: found, curr_tc_coll: coll_found[0]});
              });
            });
          });
        } else {
          caseItem["ReqFolderID"] = records[0].id;
          // console.log(caseItem);
          mongoService.Insert("RequestItem", caseItem, function (case_inserted) {
            console.log(" 用户添加了RequestItem:  " + case_inserted.name);
            mongoService.Find("ReqFolder", {}, function (found) {
              mongoService.Find("ReqFolder", {uniqID: reqFolder_uniqid}, function (coll_found) {
                res.view('testcase/index', {data: found, curr_tc_coll: coll_found[0]});
              });
            });
          });
        }
        });
      }
    });
  },

  //运行case.
  executeCase:function(req,res){
    var uniqID=req.body["uniqid"];
    if(uniqID){
      mongoService.Find("RequestItem",{uniqID:uniqID},function (found) {
        ScheduleService.executeOne(found,function(results){
          console.log(results.results);
          res.send(results.results,200);
        });

      });
    }
  },


  //查询指定用例集合。
  query_tc_coll: function(req,res){
    var uniqId = req.body['uniqID'];
    mongoService.Find('ReqFolder', {}, function (docs_records) {
      if (uniqId) {
        mongoService.Find('ReqFolder', {uniqID: uniqId}, function (records) {
          //res.render('doc/APIdoc',{api_docs:docs_records, curr_doc:records[0]});
          res.view('testcase/index', {data: docs_records, curr_tc_coll: records[0]});
        });
      } else {
        res.view('testcase/index', {data: docs_records, curr_tc_coll: null});
      }
    });
  },

  save_tc_coll:function(req,res){
    var tc_coll = req.body['reqFolder'];
    if(tc_coll&&tc_coll.uniqID){
      mongoService.Update("ReqFolder",tc_coll,{uniqID:tc_coll.uniqID},function(updated){
        if(updated&&updated.length>0){
          res.ok();
        }else{
          res.fail("更新失败");
        }
      });
    }else{
        res.fail("不存在该对象的uniqID");
    }
  }
}


