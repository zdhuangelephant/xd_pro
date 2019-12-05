/**
 * Created by Administrator on 2015/10/21.
 */
//var seek = require('./service/Sseek')
var express = require('express');
var router = express.Router();

/* GET home page. */
router.get('/', function(req, res, next) {
    res.render('index', { title: 'Express' });
});
router.post('/', function(req, res, next) {






    res.render('index', { title: 'Express' });
});

module.exports = router;