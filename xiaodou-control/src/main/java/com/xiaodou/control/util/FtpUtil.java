package com.xiaodou.control.util;

import java.io.IOException;
import java.net.UnknownHostException;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import com.xiaodou.control.prop.FtpProp;

public class FtpUtil {
	public static final String ALLCHAR = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	public static final String LETTERCHAR = "abcdefghijkllmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	public static final String NUMBERCHAR = "0123456789";
	public static String userName=FtpProp.getParams("username");
	public static String passWord=FtpProp.getParams("password");
	public static String host=FtpProp.getParams("host");
	public static Integer port=Integer.parseInt(FtpProp.getParams("port"));
	public static FTPClient ftp;
	public static boolean isConnected=false;
	public FtpUtil(int defaultTimeoutSecond, int connectTimeoutSecond, int dataTimeoutSecond){
	      ftp = new FTPClient();
	      isConnected = false;   
/*	      ftp.setDefaultTimeout(defaultTimeoutSecond * 1000);
	      ftp.setConnectTimeout(connectTimeoutSecond * 1000);
	      ftp.setDataTimeout(dataTimeoutSecond * 1000);*/
    }
/*	public static FTPClient getFtpClient()throws Exception{      
         ftp = new FTPClient();    
         ftp.connect(host, port);//连接FTP服务器   
         //如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器   
         ftp.login(userName, passWord);//登录    
         return ftp;
	}*/
	
	
	 public static FTPClient  getFtpClient() throws IOException {
		    ftp = new FTPClient();    
	        // Connect to server.
	        ftp.connect(host, port);
	        // Check rsponse after connection attempt.
	        int reply = ftp.getReplyCode();
	        if (!FTPReply.isPositiveCompletion(reply)) {
	            disconnect();
	        }
	        // Login.
	        if (!ftp.login(userName, passWord)) {
	        	isConnected = false;
	            disconnect();
	        } else {
	        	isConnected = true;
	        }
	        return ftp;
	    }
	
	
	public static  void disconnect() throws IOException {
         if (ftp.isConnected()) {
             ftp.logout();
             ftp.disconnect();
             isConnected = false;
         }
     }
}

