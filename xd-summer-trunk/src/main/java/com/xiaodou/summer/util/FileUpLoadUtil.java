package com.xiaodou.summer.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.xiaodou.common.util.log.LoggerUtil;

/**
 * SPRING MVC文件上传相关工具类
 * 
 * @author jianjun.wang 2014-4-16
 */
public class FileUpLoadUtil {

  /**
   * 删除因导入时上传的文件
   * 
   * @param file
   */
  public static void deleteFile(final File file) {
    if (file.isFile() && file.exists()) {
      file.delete();
    }
  }

  /**
   * SPRING MVC上传文件
   * 
   * @param request
   * @param file
   * @return
   */
  public static File getFile(HttpServletRequest request, MultipartFile file) {
    String dir = request.getSession().getServletContext().getRealPath("/") + "upload";
    String fileName = file.getOriginalFilename();
    File newFile = null;
    if (!file.isEmpty()) {
      fileName = fileName.substring(fileName.lastIndexOf("."));
      String realName = UUID.randomUUID().toString() + fileName;
      newFile = new File(dir, realName);
      if (!newFile.exists()) {
        if (!newFile.getParentFile().exists()) {
          newFile.getParentFile().mkdirs();
        }
        try {
          newFile.createNewFile();
          copyInputStreamToFile(file.getInputStream(), newFile);
        } catch (IOException e) {
          LoggerUtil.error(e.getMessage(), e);
        }
      }
    }
    return newFile;
  }

  private static void copyInputStreamToFile(InputStream ins, File file) {
    OutputStream os;
    try {
      os = new FileOutputStream(file);
      int bytesRead = 0;
      byte[] buffer = new byte[8192];
      while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
        os.write(buffer, 0, bytesRead);
      }
      os.close();
      ins.close();
    } catch (FileNotFoundException e) {
      LoggerUtil.error(e.getMessage(), e);
    } catch (IOException e) {
      LoggerUtil.error(e.getMessage(), e);
    }
  }
}
