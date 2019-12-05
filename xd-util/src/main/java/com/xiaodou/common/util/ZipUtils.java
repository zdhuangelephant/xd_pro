package com.xiaodou.common.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import com.xiaodou.common.util.log.LoggerUtil;

/**
 * @name @see com.xiaodou.common.util.ZipUtils.java
 * @CopyRright (c) 2016 by <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年4月1日
 * @description 字符串压缩 gzip / zip 压缩解压缩算法
 * @version 1.0
 */
public class ZipUtils {

  /**
   * 
   * 使用gzip进行压缩
   */
  public static String gzip(String primStr) {
    if (primStr == null || primStr.length() == 0) {
      return primStr;
    }

    ByteArrayOutputStream out = new ByteArrayOutputStream();

    GZIPOutputStream gzip = null;
    try {
      gzip = new GZIPOutputStream(out);
      gzip.write(primStr.getBytes());
    } catch (IOException e) {
      LoggerUtil.error("压缩文件出错", e);
    } finally {
      if (gzip != null) {
        try {
          gzip.close();
        } catch (IOException e) {
          LoggerUtil.error("压缩文件出错", e);
        }
      }
    }


    return Base64Utils.encode(out.toByteArray());
  }

  /**
   * 
   * <p>
   * Description:使用gzip进行解压缩
   * </p>
   * 
   * @param compressedStr
   * @return
   */
  public static String gunzip(String compressedStr) {
    if (compressedStr == null) {
      return null;
    }

    ByteArrayOutputStream out = new ByteArrayOutputStream();
    ByteArrayInputStream in = null;
    GZIPInputStream ginzip = null;
    byte[] compressed = null;
    String decompressed = null;
    try {
      compressed = Base64Utils.decode(compressedStr);
      in = new ByteArrayInputStream(compressed);
      ginzip = new GZIPInputStream(in);

      byte[] buffer = new byte[1024];
      int offset = -1;
      while ((offset = ginzip.read(buffer)) != -1) {
        out.write(buffer, 0, offset);
      }
      decompressed = out.toString();
    } catch (IOException e) {
      LoggerUtil.error("解压缩文件出错", e);
    } finally {
      if (ginzip != null) {
        try {
          ginzip.close();
        } catch (IOException e) {
          LoggerUtil.error("解压缩文件出错", e);
        }
      }
      if (in != null) {
        try {
          in.close();
        } catch (IOException e) {
          LoggerUtil.error("解压缩文件出错", e);
        }
      }
      if (out != null) {
        try {
          out.close();
        } catch (IOException e) {
          LoggerUtil.error("解压缩文件出错", e);
        }
      }
    }

    return decompressed;
  }

  /**
   * 使用zip进行压缩
   * 
   * @param str 压缩前的文本
   * @return 返回压缩后的文本
   */
  public static final String zip(String str) {
    if (str == null) return null;
    byte[] compressed;
    ByteArrayOutputStream out = null;
    ZipOutputStream zout = null;
    String compressedStr = null;
    try {
      out = new ByteArrayOutputStream();
      zout = new ZipOutputStream(out);
      zout.putNextEntry(new ZipEntry("0"));
      zout.write(str.getBytes());
      zout.closeEntry();
      compressed = out.toByteArray();
      compressedStr = Base64Utils.encode(compressed);
    } catch (IOException e) {
      compressed = null;
    } finally {
      if (zout != null) {
        try {
          zout.close();
        } catch (IOException e) {}
      }
      if (out != null) {
        try {
          out.close();
        } catch (IOException e) {}
      }
    }
    return compressedStr;
  }

  /**
   * 使用zip进行解压缩
   * 
   * @param compressed 压缩后的文本
   * @return 解压后的字符串
   */
  public static final String unzip(String compressedStr) {
    if (compressedStr == null) {
      return null;
    }

    ByteArrayOutputStream out = null;
    ByteArrayInputStream in = null;
    ZipInputStream zin = null;
    String decompressed = null;
    try {
      byte[] compressed = Base64Utils.decode(compressedStr);
      out = new ByteArrayOutputStream();
      in = new ByteArrayInputStream(compressed);
      zin = new ZipInputStream(in);
      zin.getNextEntry();
      byte[] buffer = new byte[1024];
      int offset = -1;
      while ((offset = zin.read(buffer)) != -1) {
        out.write(buffer, 0, offset);
      }
      decompressed = out.toString();
    } catch (IOException e) {
      decompressed = null;
    } finally {
      if (zin != null) {
        try {
          zin.close();
        } catch (IOException e) {}
      }
      if (in != null) {
        try {
          in.close();
        } catch (IOException e) {}
      }
      if (out != null) {
        try {
          out.close();
        } catch (IOException e) {}
      }
    }
    return decompressed;
  }
}
