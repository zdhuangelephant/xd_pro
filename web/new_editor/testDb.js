var db = require('./api/db');
var courseware = require('./model/courseware');
//var sql = "select * from xd_editor_courseware where id = ?";
//var args = ["1"];
var sql = "insert into xd_editor_courseware (`name`, `prefix`, `edit_url`, `show_url`) values (?, ?, ?, ?)";
var args = ["2", "2", '/courseware/' + "3", '/courseware/show?id=' + "5"];
var callback = function(results){
    //for(var row in results){
    //    console.log(courseware.createOne(results[row]));
    //}
    //console.log(courseware.createList(results));
    console.log(results);
}
db.execQuery({sql:sql, args:args, callback:callback});