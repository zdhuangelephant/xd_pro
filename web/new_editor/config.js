exports.dbhost = '211.157.137.248';
exports.port = '3306';
exports.user = 'xddev';
exports.password = 'xddev@xiaodou';
exports.db = 'editor';
exports.charset = 'utf8';
exports.maxConnLimit = '15';

exports.moudle="mo";
exports.course="co";
exports.chapter="ch";
exports.item="it";
exports.split="_";
exports.courselist = "courselist_";
exports.prefix = "courselist";
exports.suffix = "html";
var courselist_level = {};
courselist_level.moudle = 1;
courselist_level.course = 2;
courselist_level.chapter = 3;
courselist_level.item = 4;
courselist_level.file = 5;
courselist_level.l1 = "moudle";
courselist_level.l2 = "course";
courselist_level.l3 = "chapter";
courselist_level.l4 = "item";
courselist_level.l5 = "file";
exports.courselist_level = courselist_level;

var courselist_info = {};

var courselist_moudle = {};

courselist_moudle.courselist_1_1 = { id:1, name:"幼儿",open:false,children:[]};
courselist_info.courselist_moudle = courselist_moudle;
courselist_info.courselist_moudle.length = 2;

var courselist_course = {};
courselist_course.courselist_1_1_1 = { id:1, name:"保教知识与能力",open:false,children:[]};
courselist_course.courselist_1_1_2 = { id:2, name:"综合素质",open:false,children:[]};
courselist_info.courselist_course = courselist_course;
courselist_info.courselist_course.length = 3;

var courselist_chapter = {};
courselist_chapter.courselist_1_1_1_1 = { id:1, name:"第一章",open:false,children:[]};
courselist_chapter.courselist_1_1_1_2 = { id:2, name:"第二章",open:false,children:[]};
courselist_info.courselist_chapter = courselist_chapter;
courselist_info.courselist_chapter.length = 3;

var courselist_item = {};
courselist_item.courselist_1_1_1_1_1 = { id:1, name:"第一节",open:false,children:[]};
courselist_item.courselist_1_1_1_1_2 = { id:2, name:"第二节",open:false,children:[]};
courselist_info.courselist_item = courselist_item;
courselist_info.courselist_item.length = 3;

exports.courselist_info = courselist_info;

exports.basepath=__dirname;