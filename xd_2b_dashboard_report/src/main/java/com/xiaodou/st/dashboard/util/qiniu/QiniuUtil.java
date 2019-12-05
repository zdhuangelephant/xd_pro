package com.xiaodou.st.dashboard.util.qiniu;

import java.io.ByteArrayInputStream;
import java.io.File;

import org.json.JSONException;

import com.qiniu.api.auth.AuthException;
import com.qiniu.api.auth.digest.Mac;
import com.qiniu.api.config.Config;
import com.qiniu.api.io.IoApi;
import com.qiniu.api.io.PutExtra;
import com.qiniu.api.io.PutRet;
import com.qiniu.api.rs.GetPolicy;
import com.qiniu.api.rs.PutPolicy;
import com.qiniu.api.rs.RSClient;
import com.qiniu.api.rs.URLUtils;

/**
 * 
 * @name QiniuUtil CopyRright (c) 2017 by 李德洪
 * 
 * @author 李德洪
 * @date 2017年8月29日
 * @description 七牛云工具类
 * @version 1.0
 */
public class QiniuUtil {

  private String bucketName;
  private String domain;

  public void setDomain(String domain) {
    this.domain = domain;
  }

  public void setBucketName(String bucketName) {
    this.bucketName = bucketName;
  }

  // 设置AccessKey
  public void setAccessKey(String key) {
    Config.ACCESS_KEY = key;
  }

  // 设置SecretKey
  public void setSecretKey(String key) {
    Config.SECRET_KEY = key;
  }

  public static void main(String[] args) throws Exception {
    QiniuUtil qiniu = new QiniuUtil();
    // File file = new File("C:/shadow.jpg");
    qiniu.uploadFile("12334", "123qwe");
    String filePath = qiniu.getDownloadFileUrl("12334");
    System.out.println("file path == " + filePath);
  }

  public QiniuUtil() {
    super();
    this.setAccessKey("HkY48EzdbFQiP3iFdi0r-KxMya7mI6hsSNhFwqOi");
    this.setBucketName("picture");
    this.setDomain("resource.51xiaodou.com");
    this.setSecretKey("SEznQBN_EIax8C7ytFP_fdLOdR9kgAZ_O6LPXX1L");
  }

  // public QiniuUtil QiniuUtil() {
  // QiniuUtil qiniu = new QiniuUtil();
  // //设置AccessKey
  // qiniu.setAccessKey("HkY48EzdbFQiP3iFdi0r-KxMya7mI6hsSNhFwqOi");
  // //设置SecretKey
  // qiniu.setSecretKey("SEznQBN_EIax8C7ytFP_fdLOdR9kgAZ_O6LPXX1L");
  // //设置存储空间
  // qiniu.setBucketName("/ycp/sync");
  // //设置七牛域名
  // qiniu.setDomain("http://resource.51xiaodou.com/");
  // return qiniu;
  // }

  // 通过文件路径上传文件
  public boolean uploadFile(String localFile) throws AuthException, JSONException {
    File file = new File(localFile);
    return uploadFile(file);
  }

  // 通过File上传
  public boolean uploadFile(File file) throws AuthException, JSONException {
    String uptoken = getUpToken();

    // 可选的上传选项，具体说明请参见使用手册。
    PutExtra extra = new PutExtra();

    // 上传文件
    PutRet ret = IoApi.putFile(uptoken, file.getName(), file.getAbsolutePath(), extra);

    if (ret.ok()) {
      return true;
    } else {
      return false;
    }
  }

  /**
   * 从 inputstream 中写入七牛
   * 
   * @param key 文件名
   * @param content 要写入的内容
   * @return
   * @throws AuthException
   * @throws JSONException
   */
  public boolean uploadFile(String key, String content) throws AuthException, JSONException {
    // 读取的时候按的二进制，所以这里要同一
    ByteArrayInputStream inputStream = new ByteArrayInputStream(content.getBytes());

    String uptoken = getUpToken();

    // 可选的上传选项，具体说明请参见使用手册。
    PutExtra extra = new PutExtra();

    // 上传文件
    PutRet ret = IoApi.Put(uptoken, key, inputStream, extra);

    if (ret.ok()) {
      return true;
    } else {
      return false;
    }
  }

  // 获得下载地址
  public String getDownloadFileUrl(String filename) throws Exception {
    Mac mac = getMac();
    String baseUrl = URLUtils.makeBaseUrl(domain, filename);
    GetPolicy getPolicy = new GetPolicy();
    String downloadUrl = getPolicy.makeRequest(baseUrl, mac);
    return downloadUrl;
  }

  // 删除文件
  public void deleteFile(String filename) {
    Mac mac = getMac();
    RSClient client = new RSClient(mac);
    client.delete(domain, filename);
  }

  // 获取凭证
  private String getUpToken() throws AuthException, JSONException {
    Mac mac = getMac();
    PutPolicy putPolicy = new PutPolicy(bucketName);
    String uptoken = putPolicy.token(mac);
    return uptoken;
  }

  private Mac getMac() {
    Mac mac = new Mac(Config.ACCESS_KEY, Config.SECRET_KEY);
    return mac;
  }

}
