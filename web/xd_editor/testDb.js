var db = require('./api/db');
// var courseware = require('./model/courseware');
//var sql = "select * from xd_editor_courseware where id = ?";
//var args = ["1"];
// var sql = "insert into xd_editor_courseware (`name`, `prefix`, `edit_url`, `show_url`) values (?, ?, ?, ?)";
// var args = ["2", "2", '/courseware/' + "3", '/courseware/show?id=' + "5"];
// var callback = function(results){
//     //for(var row in results){
//     //    console.log(courseware.createOne(results[row]));
//     //}
//     //console.log(courseware.createList(results));
//     console.log(results);
// }
// db.execQuery({sql:sql, args:args, callback:callback});


// function deleteAndInsertCourseware(chapterId, courseId, name, ret, callback) {
//     var resource = resourcefac.createOne(ret.domain_url, chapconfig.url.format(chapterId));
//     var sql = "update xd_course_resource_html5 set `status` = ? where `id` = ?";
var sql = "select id from xd_course_resource_html5 where `id` = ?";

var args = [125];
    db.execQuery({
        sql: sql, args: args, callback: function (results) {
            if (results) {
                console.log(results[0]["id"]);
            } else {
            }
        }
    });
// }