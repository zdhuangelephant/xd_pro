package com.xiaodou.common.http;

import java.io.UnsupportedEncodingException;

import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.HeadMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.PutMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;

import com.xiaodou.common.http.method.DeleteMethod;

/**
 * HttpMethod实例操作类
 * 
 * @author zhaodan
 * @version 1.0
 * @date 2014-1-20
 */
public class HttpMethodUtil {

  /**
   * 使用GET方式提交数据
   * 
   * @return GetMethod
   */
  public static GetMethod getGetMethod(String serUrl) {
    return new GetMethod(serUrl);
  }

  /**
   * 使用POST方式提交数据
   * 
   * @return PostMethod
   */
  public static PostMethod getPostMethod(String serUrl, NameValuePair[] params) {
    PostMethod post = new PostMethod(serUrl);
    post.setRequestBody(params);
    return post;
  }

  /**
   * 使用DELETE-POST方式提交数据
   * 
   * @return DeleteMethod
   */
  public static DeleteMethod getDeleteMethod(String serUrl, NameValuePair[] params) {
    DeleteMethod delete = new DeleteMethod(serUrl);
    delete.setRequestBody(params);
    return delete;
  }

  /**
   * 使用DELETE方式提交数据
   * 
   * @return DelMethod
   */
  public static org.apache.commons.httpclient.methods.DeleteMethod getDeleteMethod(String serUrl) {
    return new org.apache.commons.httpclient.methods.DeleteMethod(serUrl);
  }

  /**
   * 使用PUT方式提交数据
   * 
   * @return PutMethod
   */
  public static PutMethod getPutMethod(String serUrl) {
    return new PutMethod(serUrl);
  }

  /**
   * 使用HEAD方式提交数据
   * 
   * @return HeadMethod
   */
  public static HeadMethod getHeadMethod(String serUrl) {
    return new HeadMethod(serUrl);
  }

  /**
   * 使用POST方式提交数据
   * 
   * @param serUrl
   * @param contentType
   * @param charset
   * @param jsonStr
   * @return PostMethod
   * @throws UnsupportedEncodingException
   */
  public static PostMethod getPostMethod(String serUrl, String contentType, String charset,
      String jsonStr) throws UnsupportedEncodingException {
    PostMethod method = new PostMethod(serUrl);
    method.setRequestEntity(new StringRequestEntity(jsonStr, contentType, charset));
    return method;
  }

}
