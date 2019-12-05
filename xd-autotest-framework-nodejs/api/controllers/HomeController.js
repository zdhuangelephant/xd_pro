var mysqlService=require("../services/mysqlService");
/**
 * HomeController
 *
 * @description :: Server-side logic for managing Homes
 * @help        :: See http://sailsjs.org/#!/documentation/concepts/Controllers
 */
module.exports = {
  index: function(req, res) {
    res.view('index');
  },
  overview: function(req, res) {
    res.view('overview/index');
  },
  doc: function(req, res) {
    ApiDocService.findAllDoc(function(records){
      if(null != records && records.length > 0) {
        var lastRecord = records[records.length-1];
        ApiDocService.findDocWithItem(lastRecord["uniqID"], function (record) {
          if(null != record) {
            res.view('doc/APIdoc', {api_docs:records, curr_doc:record});
          }else{
            res.view('doc/APIdoc', {api_docs:records, curr_doc:null});
          }
        })
      }else{
        res.view('doc/APIdoc', {api_docs:null, curr_doc:null});
      }
    })
  },
  task: function(req, res) {
    mongoService.Find("TaskFolder", null, function (records) {
      res.view('task/index', {data:records});
    });
  },
  testcase: function(req, res) {
    mongoService.Find("ReqFolder",null,function (records) {
      res.view('testcase/index', {data:records,curr_tc_coll:records[records.length-1]});
    });
  },
  schedule: function(req, res) {
    mongoService.Find('ScheduleTask', null, function (records) {
      res.view('schedule/index', {data: records});
    });
  },
};

