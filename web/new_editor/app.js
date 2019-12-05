var express = require('express');
var path = require('path');
var favicon = require('serve-favicon');
var logger = require('morgan');
var cookieParser = require('cookie-parser');
var bodyParser = require('body-parser');
var http = require('http');
var routes = require('./routes/index');
var urlrouter = require('urlrouter');
var users = require('./routes/users');
var cEditor = require('./controller/cEditor')
var cCourseware = require('./controller/cCourseware')
var cChapter = require('./controller/cChapter');
var ejs = require('ejs');
var app = express();

// listen port
app.set('port', process.env.PORT || 8899);

// view engine setup
app.engine('.html', ejs.renderFile);
app.set('views', path.join(__dirname, 'public'));
app.set('view engine', 'ejs');

// uncomment after placing your favicon in /public
//app.use(favicon(__dirname + '/public/favicon.ico'));
app.use(logger('dev'));
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({extended: false}));
app.use(cookieParser());
app.use(express.static(path.join(__dirname, 'public'), {maxAge: 3600000 * 24 * 30}));
app.use('/editor', express.static(path.join(__dirname, 'public'), {maxAge: 3600000 * 24 * 30}));
app.use('/output', express.static(path.join(__dirname, 'public', 'output')));
app.use('/chapter', express.static(path.join(__dirname, 'public', 'item')));
app.use('/download', express.static(path.join(__dirname, 'public', 'item', 'download')));
app.use('/chapter/images', express.static(path.join(__dirname, 'public', 'images')));
app.use('/courseware', express.static(path.join(__dirname, 'public', 'output')));
app.use('/courseware/images', express.static(path.join(__dirname, 'public', 'images')));

/**
 * Routing
 */
var router = urlrouter(function (app) {
    /*editor routing*/
    app.get('/editor/main', cEditor.main);
    app.post('/editor/main', cEditor.main);
    app.post('/editor/save', cEditor.save);
    app.post('/editor/recover', cEditor.recover);
    /*courseware routing*/
    app.post('/courseware/list', cCourseware.list);
    app.post('/courseware/add', cCourseware.add);
    app.post('/courseware/edit', cCourseware.edit);
    app.post('/courseware/change', cCourseware.change);
    app.post('/courseware/delete', cCourseware.del);
    app.get('/courseware/show', cCourseware.show);
    app.post('/courseware/show', cCourseware.show);
    app.get('/courseware/getView', cCourseware.getView);
    app.post('/courseware/getView', cCourseware.getView);
    /*chapter routing*/
    app.post('/chapter/list', cChapter.list);
    app.post('/chapter/add', cChapter.add);
    app.post('/chapter/delete', cChapter.del);
    app.get('/chapter/show', cChapter.show);
    app.post('/chapter/show', cChapter.show);
    app.get('/chapter/download', cChapter.download);
    app.post('/chapter/download', cChapter.download);
    /*image routing*/
    app.post('/chapter/imglist', cChapter.imgList);
    app.post('/chapter/addImg', cChapter.addImg);
});
app.use(router);

/** default router*/
//app.use('/', routes);
app.use('/users', users);

// catch 404 and forward to error handler
app.use(function (req, res, next) {
    var err = new Error('Not Found');
    err.status = 404;
    next(err);
});

// error handlers

// development error handler
// will print stacktrace
if (app.get('env') === 'development') {
    app.use(function (err, req, res, next) {
        res.status(err.status || 500);
        res.render('error', {
            message: err.message,
            error: err
        });
    });
}

// production error handler
// no stacktraces leaked to user
app.use(function (err, req, res, next) {
    res.status(err.status || 500);
    res.ender('error', {
        message: err.message,
        error: {}
    });
});

module.exports = app;