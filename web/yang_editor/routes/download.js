var express = require('express');
var ejs = require('ejs');
var fs = require('fs');
var path = require('path');
var router = express.Router();

/**
 * 用不到下载功能，下载下来也没用
 *
 * */
router.get('/', function (req, res, next) {
  console.log("sdf")
  var fileName = req.param("fileName");
  if (fileName == null) {
    fileName = "template.html";
  }

  var show = fs.readFileSync(path.join("../views/", "show.ejs"), "utf-8");


  //fs.readFile(path.join("../public/template", fileName), "binary", function (error, file) {
  //
  //  res.writeHead(200, {
  //    "Content-Type": "application/octet-stream"
  //  });
  //  res.write(file, "binary");
  //  res.end();
  //
  //});
  var temp = fs.readFileSync(path.join("../public/template", fileName), "utf-8");

  var output = ejs.render(show,{content:temp});
  console.log(output)

  res.writeHead(200,{
    "Content-Type":"application/octet-stream"
  });
  res.write(output);
  res.end();
});

module.exports = router;
