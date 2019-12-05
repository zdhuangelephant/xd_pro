/**
 * 数据库模块
 */
var dbconfig = require("../dbconfig");

var options = {
    'host': dbconfig.dbhost,
    'port': dbconfig.port,
    'user': dbconfig.user,
    'password': dbconfig.password,
    'database': dbconfig.db,
    'charset': dbconfig.charset,
    'connectionLimit': dbconfig.maxConnLimit,
    'supportBigNumbers': true,
    'bigNumberStrings': true
};

var mysql = require('mysql');
var pool = mysql.createPool(options);

/**
 * 释放数据库连接
 */
exports.release = function (connection) {
    connection.end(function (error) {
        console.log('Connection closed');
    });
};

/**
 * 执行查询
 */
exports.execQuery = function (options) {
    pool.getConnection(function (error, connection) {
        try {
            if (error) {
                console.log('DB-获取数据库连接异常！');
                throw error;
            }

            /*
             * connection.query('USE ' + dbconfig.db, function(error, results) { if(error) { console.log('DB-选择数据库异常！'); connection.end(); throw error; } });
             */

            // 查询参数
            var sql = options['sql'];
            var args = options['args'];
            var handler = options['callback'];

            // 执行查询
            if (sql) {
                if (!args) {
                    var query = connection.query(sql, function (error, results) {
                        if (error) {
                            console.log('DB-执行查询语句异常！');
                            if (handler) {
                                handler(null);
                            }
                            throw error;
                        }

                        // 处理结果
                        if (handler) {
                            handler(results);
                        }
                    });

                } else {
                    var query = connection.query(sql, args, function (error, results) {
                        if (error) {
                            console.log('DB-执行查询语句异常！');
                            if (handler) {
                                handler(null);
                            }
                            throw error;
                        }

                        // 处理结果
                        if (handler) {
                            handler(results);
                        }
                    });
                }
            }
        } catch (err) {
            // 处理结果
            console.log('DB-执行查询语句异常！');
            throw err;
        } finally {
            // 返回连接池
            if (connection)
                connection.release(function (error) {
                    if (error) {
                        console.log('DB-关闭数据库连接异常！');
                        throw error;
                    }
                });
        }
    });
};
