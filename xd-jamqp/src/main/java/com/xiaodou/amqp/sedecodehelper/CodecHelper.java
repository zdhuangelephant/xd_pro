package com.xiaodou.amqp.sedecodehelper;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.ArrayList;

import org.codehaus.jackson.map.ObjectMapper;

import com.xiaodou.common.util.log.LoggerUtil;

/**
 * 消息实体序列化和反序列化操作
 *
 * @author heshixiong
 */
public class CodecHelper {

  private static final ObjectMapper objectMapper = new ObjectMapper();

  /**
   * 序列化
   */
  public static byte[] serialize(Object obj) {
    ByteArrayOutputStream baos = null;
    ObjectOutputStream oos = null;
    try {
      baos = new ByteArrayOutputStream();
      oos = new ObjectOutputStream(baos);
      oos.writeObject(obj);
      oos.flush();
    } catch (Exception e) {
      LoggerUtil.error("serialize error: ", e);
    } finally {
      if (oos != null) {
        try {
          oos.close();
        } catch (IOException e) {
          LoggerUtil.error("stream close error: ", e);
        }
      }
    }
    return baos != null ? baos.toByteArray() : null;
  }

  /**
   * 反序列化,目前如果是java发送端发送数据,反序列化就使用该方法.
   *
   * @param in 输入的byte数据信息
   * @return 返回反序列化后的结果数据
   */
  public static Object deSerialize(byte[] in) {
    ByteArrayInputStream bais = null;
    ObjectInputStream ois = null;
    Object result = null;
    try {
      bais = new ByteArrayInputStream(in);
      ois = new ObjectInputStream(bais);
      result = ois.readObject();
    } catch (Exception e) {
      LoggerUtil.error("deserialize error: ", e);
    } finally {
      if (ois != null) {
        try {
          ois.close();
        } catch (IOException e) {
          LoggerUtil.error("stream close error: ", e);
        }
      }
    }
    return result;
  }

  /**
   * 反序列化为ArrayList<String>类型字符串
   *
   * @param bt 需要反序列化的数据
   * @return
   */
  @SuppressWarnings("unchecked")
  public static ArrayList<String> getArrayListString(byte[] bt) {
    ArrayList<String> list = new ArrayList<String>();
    ObjectInputStream objIps = null;
    try {
      objIps = new ObjectInputStream(new ByteArrayInputStream(bt));
      list = (ArrayList<String>) objIps.readObject();
    } catch (Exception e) {
      LoggerUtil.error("convert byte[] to ArrayList<string> error: ", e);
    } finally {
      if (objIps != null) {
        try {
          objIps.close();
        } catch (IOException e) {
          LoggerUtil.error("stream close error: ", e);
        }
      }
    }
    return list;
  }

  /**
   * 反序列化操作
   *
   * @param in 输入的byte流数据
   * @return 返回反序列化后所得的string数据
   */
  public static String deSerialDataInput(byte[] in) {
    DataInputStream dataInputStream = new DataInputStream(new ByteArrayInputStream(in));
    try {
      String resultData = dataInputStream.readUTF();
      return resultData;
    } catch (IOException e) {
      LoggerUtil.error("read string from byte[] error: ", e);
    } finally {
      try {
        if (dataInputStream != null) {
          dataInputStream.close();
        }
      } catch (Exception e) {
        LoggerUtil.error("stream close error: ", e);
      }
    }
    return null;
  }

  /**
   * 将byte类型数据转换为String类型
   *
   * @param buffer ByteBuffer对象
   * @return 返回转换后的String数据信息
   */
  public static String parseByteBufferToString(ByteBuffer buffer) {
    Charset charset = null;
    CharsetDecoder decoder = null;
    CharBuffer charBuffer = null;
    try {
      charset = Charset.forName("UTF-8");
      decoder = charset.newDecoder();
      charBuffer = decoder.decode(buffer.asReadOnlyBuffer());
      return charBuffer.toString();
    } catch (Exception e) {
      LoggerUtil.error("parseByteBufferToString error: ", e);
    }
    return "";
  }

  /**
   * 将字符串转换为byte类型
   *
   * @param string 原始字符串
   * @return 返回转换后的byte值
   */
  public static byte[] parseStringToByte(String string) {
    Charset charset = Charset.forName("UTF-8");
    return string.getBytes(charset);
  }

  public static String parseObjectToJson(Object obj) {
    try {
      return objectMapper.writeValueAsString(obj);
    } catch (Exception e) {
      LoggerUtil.error("parseObjectToJson error: ", e);
    }
    return "";
  }

  public static <T> T parseJsonToObject(String json, Class<T> clazz) throws IOException {
    return objectMapper.readValue(json, clazz);
  }
}
