package com.xiaodou.mooccrawler.service;
import java.io.File;
import java.io.InputStream;

import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.qiniu.api.auth.AuthException;
import com.qiniu.api.auth.digest.Mac;
import com.qiniu.api.config.Config;
import com.qiniu.api.io.IoApi;
import com.qiniu.api.io.PutExtra;
import com.qiniu.api.io.PutRet;
import com.qiniu.api.rs.GetPolicy;
import com.qiniu.api.rs.PutPolicy;
import com.qiniu.api.rs.URLUtils;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.processing.OperationManager;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.model.FileInfo;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import com.qiniu.util.UrlSafeBase64;
import com.xiaodou.mooccrawler.vo.QiNiuMedia;

/**
 * 七牛云存储的视频文件上传 和视频转码工具等
 * 
 */
@Service("qiniuMediaUtilService")
public class QiniuMediaUtilService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private String bucketName;
    private String domain;
    private String accessKey = "";
    private String secretKey = "";
    public static final String PDF = ".pdf";
    public static final String MP4 = ".mp4";

    /**
     * 初始化参数设置
     */
    public QiniuMediaUtilService() {

        QiNiuMedia niuMedia = QiNiuMedia.getInstance();
        this.domain = niuMedia.getDomain();
        this.bucketName = niuMedia.getBucketName();
        this.accessKey = niuMedia.getAccessKey();
        this.secretKey = niuMedia.getSecretKey();
        // 设置AccessKey
        Config.ACCESS_KEY = niuMedia.getAccessKey();
        // 设置SecretKey
        Config.SECRET_KEY = niuMedia.getSecretKey();
    }

    /**
     * 通过文件路径上传文件
     * 
     * @param localFile
     *            文件的所在路径
     * @throws AuthException
     * @throws JSONException
     */
    public boolean uploadFile(String localFile) throws AuthException,
            JSONException {
        File file = new File(localFile);
        return uploadFile(file);
    }

    /**
     * 通过文件流上传
     * 
     * @param file
     *            文件流
     * @throws AuthException
     * @throws JSONException
     */
    public boolean uploadFile(File file) throws AuthException, JSONException {
        long startTime = System.currentTimeMillis();// 获取当前时间
        String uptoken = getUpToken();
        // 可选的上传选项，具体说明请参见使用手册。
        PutExtra extra = new PutExtra();
        // 上传文件
        PutRet ret = IoApi.putFile(uptoken, file.getName(),
                file.getAbsolutePath(), extra);
        if (ret.ok()) {
            logger.info("视频文件上传成功!");
            long endTime = System.currentTimeMillis();
            logger.info("视频文件上传用时：" + (endTime - startTime) + "ms");
            return true;
        } else {
            logger.error("视频文件上传失败!");
            return false;
        }
    }

    /**
     * 从 inputstream 中写入七牛
     * @param fileName 文件名
     * @param content 要写入的内容
     * @return
     * @throws AuthException
     * @throws JSONException
     */
    public boolean uploadFile(String fileName,InputStream in)
            throws AuthException, JSONException {
        long startTime = System.currentTimeMillis();// 获取当前时间
        String uptoken = getUpToken();
        // 可选的上传选项，具体说明请参见使用手册。
        PutExtra extra = new PutExtra();
        // 上传文件
        PutRet ret = IoApi.Put(uptoken, fileName, in, extra);
        if (ret.ok()) {
            logger.info("文件上传成功!");
            long endTime = System.currentTimeMillis();
            logger.info("文件上传用时：" + (endTime - startTime) + "ms");
            return true;
        } else {
            logger.error("文件上传失败!");
            return false;
        }

    }

    /**
     * 获得下载地址
     * 这个获取的就是你上传文件资源的链接
     * @param filename
     * @return
     * @throws Exception
     */
    public String getFileResourceUrl(String filename) throws Exception {
        String downloadUrl = "";
        if (filename != null) {
            Mac mac = getMac();
            String baseUrl = URLUtils.makeBaseUrl(domain, filename);
            GetPolicy getPolicy = new GetPolicy();
            downloadUrl = getPolicy.makeRequest(baseUrl, mac);
        }
        return downloadUrl;
    }

    /**
     * 七牛上将文件删除
     * @param fileName
     */
    public void delete(String fileName) {

        Auth auth = Auth.create(accessKey, secretKey);
        Zone z = Zone.zone0();
        Configuration c = new Configuration(z);
        // 实例化一个BucketManager对象
        BucketManager bucketManager = new BucketManager(auth, c);
        // 要测试的空间和key，并且这个key在你空间中存在
        String bucket = bucketName;
        String key = fileName;
        try {
            // 调用delete方法移动文件
            bucketManager.delete(bucket, key);
            logger.info("文件删除成功!.[fileName={}]", fileName);
        } catch (QiniuException e) {
            logger.error("文件删除失败!.[fileName={}]", fileName);
            // 捕获异常信息
            Response r = e.response;
            logger.error(r.toString());
        }
    }

    /**
     * 获取凭证
     * 
     * @throws AuthException
     * @throws JSONException
     */
    private String getUpToken() throws AuthException, JSONException {
        Mac mac = getMac();
        PutPolicy putPolicy = new PutPolicy(bucketName);
        String uptoken = putPolicy.token(mac);
        return uptoken;
    }

    /**
     * 操作许可
     * 
     */
    private Mac getMac() {
        Mac mac = new Mac(Config.ACCESS_KEY, Config.SECRET_KEY);
        return mac;
    }



    /**
     * 七牛的视频转码
     * @param fileName  要转换的文件名称
     * @param format  要转成的视频格式
     */
    public String qiNiuTransCodeToAny(String fileName, String format) {
        long startTime = System.currentTimeMillis();// 获取当前时间
        // 身份验证
        Auth auth = Auth.create(accessKey, secretKey);
        Zone z = Zone.zone0();
        Configuration c = new Configuration(z);
        // 新建一个OperationManager对象
        OperationManager operater = new OperationManager(auth, c);
        // 设置要转码的空间和key，并且这个key在你空间中存在(key就是文件的名字)
        String bucket = bucketName;
        String key = fileName;
        // 设置转码操作参数
        String fops = "avthumb/" + format;
        // 设置转码的队列
        String pipeline = "pipeline";
        // 可以对转码后的文件进行使用saveas参数自定义命名，当然也可以不指定文件会默认命名并保存在当前空间。
        String str = fileName.substring(0, fileName.indexOf("."));
        String urlbase64 = UrlSafeBase64.encodeToString(bucketName + ":" + str
                + "." + format);
        String pfops = fops + "|saveas/" + urlbase64;
        // 设置pipeline参数
        StringMap params = new StringMap().putWhen("force", 1, true)
                .putNotEmpty("pipeline", pipeline);
        try {
            String persistid = operater.pfop(bucket, key, pfops, params);
            logger.info("视频转码成功.[persistid={}]", persistid);
            long endTime = System.currentTimeMillis();
            logger.info("视频转码用时：" + (endTime - startTime) + "ms");
            return str + "." + format;
        } catch (QiniuException e) {
            Response r = e.response;// 捕获异常信息
            logger.info(r.toString());// 请求失败时简单状态信息
            try {
                logger.info(r.bodyString());// 响应的文本信息
                 return "";
            } catch (QiniuException e1) {
                logger.error("视频转码失败={}", e1.getMessage());
                return "";
            }
        }
        
    }

    
    
    
    /**
     * 判断文件是否存在
     * @param fileName    文件名
     */
    public boolean isExist(String fileName) {
        boolean flag = false;
        // 身份验证
        Auth auth = Auth.create(accessKey, secretKey);
        Zone z = Zone.zone0();
        Configuration c = new Configuration(z);
        // 实例化一个BucketManager对象
        BucketManager bucketManager = new BucketManager(auth, c);
        // 要测试的空间和key，并且这个key在你空间中存在
        String bucket = bucketName;
        String key = fileName;
        try {
            // 调用stat()方法获取文件的信息
            FileInfo info = bucketManager.stat(bucket, key);
            if (info.hash != null) {
                flag = true;
            }
            logger.info("视频文件信息.[hash={},key={}]", info.hash, info.key);
        } catch (QiniuException e) {
            // 捕获异常信息
            Response r = e.response;
            logger.error("视频文件不存在={}", r.toString());
        }
        return flag;
    }
    
}