package com.xiaodou.webfetch.util;


import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

@Service("fileUtilService")
public class FileUtilService {
  @Resource
  QiniuMediaUtilService qiniuMediaUtilService;

  /**
   * 从网络Url中下载文件
   * 
   * @param urlStr
   * @param fileName
   * @param savePath
   * @throws IOException
   */
  public boolean downLoadByUrl(String urlStr, String fileName) {
    try {
      URL url = new URL(urlStr);
      HttpURLConnection conn = (HttpURLConnection) url.openConnection();
      // 设置超时间为3秒
      conn.setConnectTimeout(5 * 1000);
      // 防止屏蔽程序抓取而返回403错误
      conn.setRequestProperty("User-Agent",
          "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
      // 得到输入流
      InputStream inputStream = conn.getInputStream();
      boolean flag = qiniuMediaUtilService.uploadFile(fileName, inputStream);
      if (inputStream != null) {
        inputStream.close();
      }
      return flag;
    } catch (Exception e) {
      // TODO: handle exception
    }
    return false;
  }
}
