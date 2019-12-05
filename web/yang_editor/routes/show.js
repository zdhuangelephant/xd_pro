var express = require('express');
var fs = require('fs');
var path = require('path');
var ejs = require('ejs');

var router = express.Router();

/* GET users listing. */
router.get('/', function (req, res, next) {
  var fileName = req.param("fileName");

  var temp = fs.readFileSync(path.join("../public/template", fileName), "utf-8");

  console.log(fileName)

  console.log(temp)
  res.render("show", {
    content: temp
  });
});

module.exports = router;
