package com.xiaodou.webfetch.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.Header;
import org.jsoup.Connection;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;

import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.summer.vo.out.ResultType;
import com.xiaodou.webfetch.engine.SandBoxContext;
import com.xiaodou.webfetch.param.FetchTask;
import com.xiaodou.webfetch.plugin.jsoup.JsoupPojo;
import com.xiaodou.webfetch.plugin.jsoup.JsoupResponse;
import com.xiaodou.webfetch.plugin.phantom.PhantomPojo;
import com.xiaodou.webfetch.plugin.phantom.PhantomProp;
import com.xiaodou.webfetch.plugin.phantom.PhantomResponse;
import com.xiaodou.webfetch.util.PathUtil.Path;

/**
 * @name @see com.xiaodou.webfetch.util.HttpApiExcutor.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年12月8日
 * @description HttpApi执行器
 * @version 1.0
 */
public class HttpApiExcutor {

  /** apiJsPath 脚本路径 */
  private static Path apiJsPath = PathUtil.path().in(PhantomProp.getApiJsPath());
  /** pluginPath 插件路径 */
  private static Path pluginPath = PathUtil.path().in(PhantomProp.getPluginPath());
  /** CMD_COMMAND 命令行指令模式 */
  private static String CMD_COMMAND = "%s %s %s";

  public static PhantomPojo buildPhantomPojo(FetchTask task, SandBoxContext sandBox) {
    PhantomPojo phantomPojo = new PhantomPojo();
    if (null != sandBox.getHeaderList() && !sandBox.getHeaderList().isEmpty()) {
      phantomPojo.getHeaderList().addAll(sandBox.getHeaderList());
    }
    if (null != sandBox.getCookieList() && !sandBox.getCookieList().isEmpty()) {
      phantomPojo.getCookieList().addAll(sandBox.getCookieList());
    }
    phantomPojo.setUrl(task.getUrl());
    return phantomPojo;
  }

  public static JsoupPojo buildJsoupPojo(FetchTask task, SandBoxContext sandBox) {
    JsoupPojo jsoupPojo = new JsoupPojo();
    if (null != sandBox.getHeaderList() && !sandBox.getHeaderList().isEmpty()) {
      jsoupPojo.getHeaderList().addAll(sandBox.getHeaderList());
    }
    if (null != sandBox.getCookieList() && !sandBox.getCookieList().isEmpty()) {
      jsoupPojo.getCookieList().addAll(sandBox.getCookieList());
    }
    jsoupPojo.setUrl(task.getUrl());
    return jsoupPojo;
  }

  /**
   * @description 调用phantomjs程序，并传入js文件，并通过流拿回需要的数据。
   * @param pojo phantom入参
   * @return phantom出参
   * @throws IOException IO读写异常
   */
  public static PhantomResponse executePhantomApi(PhantomPojo pojo) throws IOException {
    Runtime rt = Runtime.getRuntime();
    String json = pojo.toJson();
    Process p = rt.exec(String.format(CMD_COMMAND, pluginPath.path(), apiJsPath.path(), json));
    InputStream is = p.getInputStream();
    BufferedReader br = new BufferedReader(new InputStreamReader(is));
    StringBuffer sbf = new StringBuffer();
    String tmp = org.apache.commons.lang.StringUtils.EMPTY;
    while ((tmp = br.readLine()) != null) {
      sbf.append(tmp);
    }
    String responseJson = sbf.toString();
    if (StringUtils.isJsonBlank(responseJson)) {
      return new PhantomResponse(ResultType.SYSFAIL);
    }
    return FastJsonUtil.fromJson(responseJson, PhantomResponse.class);
  }

  /**
   * @description 调用JsoupApi获取调用结果
   * @param pojo
   * @return
   * @throws IOException
   */
  public static JsoupResponse excuteJsoupApi(JsoupPojo pojo) throws IOException {
    Connection connect = Jsoup.connect(pojo.getUrl());
    if (null != pojo.getHeaderList() && !pojo.getHeaderList().isEmpty()) {
      for (Header header : pojo.getHeaderList()) {
        connect.header(header.getName(), header.getValue());
      }
    }
    if (null != pojo.getCookieList() && !pojo.getCookieList().isEmpty()) {
      for (Cookie cookie : pojo.getCookieList()) {
        connect.cookie(cookie.getName(), cookie.getValue());
      }
    }
    Response response = connect.execute();
    JsoupResponse jsoupResponse = new JsoupResponse(ResultType.SUCCESS);
    jsoupResponse.setResponse(response);
    return jsoupResponse;
  }

}
