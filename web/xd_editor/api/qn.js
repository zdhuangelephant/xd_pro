/**
 * 七牛操作模块
 */
require('../utils/string');
var qiniu = require("qiniu");
var qnconfig = require("../qnconfig");

/**
 *上传文件到七牛云存储
 *
 * @param bucket 要上传的空间
 * @param key 上传到七牛后保存的文件名
 * @param filePath 要上传文件的本地路径
 */
exports.uploadFile = function (key, filePath, callbackfunc) {
    //需要填写你的 Access Key 和 Secret Key
    qiniu.conf.ACCESS_KEY = qnconfig.ACCESS_KEY;
    qiniu.conf.SECRET_KEY = qnconfig.SECRET_KEY;
    //生成上传 Token
    var token = uptoken(qnconfig.BUCKET, key);
    //调用uploadFile上传
    uploadFile0(token, key, filePath, callbackfunc);
}


//构建上传策略函数
function uptoken(bucket, key) {
    var putPolicy = new qiniu.rs.PutPolicy(bucket + ":" + key);
    return putPolicy.token();
}

//构造上传函数
function uploadFile0(uptoken, key, localFile, callbackfunc) {
    var extra = new qiniu.io.PutExtra();
    qiniu.io.putFile(uptoken, key, localFile, extra, function (err, ret) {
        if (!err) {
            // 上传成功， 处理返回值
            if (callbackfunc != null && typeof(callbackfunc) != undefined) {
                ret.domain_url = qnconfig.DOMAIN_URL.format(ret.key);
                callbackfunc(ret);
            }
        } else {
            // 上传失败， 处理返回代码
            console.log(err);
            if (callbackfunc != null && typeof(callbackfunc) != undefined)
                callbackfunc(null);
        }
    });
}