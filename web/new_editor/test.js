//var eventProxy = require('eventproxy');
//var ep = eventProxy.create();
//var _ep = eventProxy.create();
//var fs = require('fs');
//_ep.all("a", "b", function (a, b) {
//    console.log(parseInt(a));
//    console.log(parseInt(b));
//});
//
//_ep.emit("a", 2);
//
//var _list = [1, 2, 3, 4, 5];
//ep.after("add", _list.length, function (list) {
//    console.log("end");
//    _ep.emit("b", 3);
//});
//for (var i in _list) {
//    fs.exists("a.txt", function (exists) {
//        console.log(_list[i]);
//        ep.emit("add", 1);
//    });
//}
require("./utils/string");
//var check = require('./utils/check');
//var id = 1, name = undefined, prefix=null;
//console.log(check.checkParam(id,name,prefix));
//console.log(check.checkParamDetail({id:id,name:name,prefix:prefix}));
var info = '<div class="yy-click picture" yy-type="img" style="border: none;"><img src="images/upload_fe89dda8a7c527ed0c579c922d881520XJ1-02-01-2.png" style="opacity: 1;"></div><img src="images/upload_ef3b64ae7ea7a4780c7d20cca5702745XJ1-02-01-4.png" style="opacity: 1;">';
info = info.replaceAll("images/","");
console.log(info);
var patt = /src=\"([^\"]*)\"/;
var r;
while(r = patt.exec(info))
{
    if(r[1] && r[1].length > 0){
        console.log(r[1]);
        info = info.replaceAll(r[1],"");
    }else{
        break;
    }
}