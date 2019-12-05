var express = require('express');
var uuidGen = require('node-uuid');
var fs = require('fs');
var path = require('path');
var router = express.Router();

/* GET users listing. */
router.get('/', function (req, res, next) {
  console.log(req.param("aaa"))
  res.send('success');
});

router.post('/', function (req, res, next) {
  console.log("dirname:"+__dirname )
  var data = req.param("data");
  var uuid = uuidGen.v1();
  var fileName = uuid + ".html";

  fs.writeFile(path.join("../public/template", fileName), data, function (err) {
    if (err) {
      console.log(err)
      throw err;
    }
  });

  var ret = {};
  ret.code = 0;
  ret.url = "/show?fileName=" + fileName;
  res.send(ret);
});

module.exports = router;
