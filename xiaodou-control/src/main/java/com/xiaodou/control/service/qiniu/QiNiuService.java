package com.xiaodou.control.service.qiniu;

import com.google.gson.Gson;
import com.qiniu.util.Auth;

import java.io.InputStream;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;

public class QiNiuService {
	
	/**基本配置-从七牛管理后台拿到*/
  //设置好账号的ACCESS_KEY和SECRET_KEY
  public static String  ACCESS_KEY = "BoQZO87sq0mfPi1oONFaAVnUeLrKKmcuPUT6PO80";
  public static String SECRET_KEY = "fwluIvAlFyV8m-A-1hXXZP8rAbxe3DuVYSD6p38D";
  //要上传的空间--
  public static String bucketName = "";
    
  //默认不指定key的情况下，以文件内容的hash值作为文件名
  public void upload(InputStream stream,String bucketName,String key){  
	//构造一个带指定Zone对象的配置类
	Configuration cfg = new Configuration(Zone.zone1());	
	//...其他参数参考类注释	
	UploadManager uploadManager = new UploadManager(cfg);	
	Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
	String upToken = auth.uploadToken(bucketName);
	try {
	    Response response = uploadManager.put(stream,key,upToken,null, null);
	    //解析上传成功的结果	
	    DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);	
	    System.out.println(putRet.key);
	    System.out.println(putRet.hash);	
	} catch (QiniuException ex) {	
	    Response r = ex.response;	
	    System.err.println(r.toString());	
	    try {
	        System.err.println(r.bodyString());	
	    } catch (QiniuException ex2) {	
	        //ignore	
	    }
	}
  }

}