package com.xiaodou.server.mapi.engine;

import com.xiaodou.server.mapi.engine.http.AbstractHttpProxy;
import com.xiaodou.server.mapi.engine.http.JsonHttpProxy;
import com.xiaodou.server.mapi.engine.http.NormalHttpProxy;
import com.xiaodou.server.mapi.engine.http.SoapHttpProxy;
import com.xiaodou.server.mapi.engine.proxy.AbstractApiProxy;
import com.xiaodou.server.mapi.engine.proxy.HttpApiProxy;

/**
 * @name @see com.xiaodou.server.mapi.engine.enums.ActionEnum.java
 * @CopyRright (c) 2016 by XiaoDou NetWork Technology
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年1月22日
 * @description 注册Action用枚举
 * @version 1.0
 */
public class ActionEnum {

  /**
   * *<ACTION package="" name="" version=""> * * <API protocol="" host="" port="" method=""
   * baseUrl="" encrept=""> * * <PARAM name=""></PARAM> * * </API> * *</ACTION> *
   * 
   * @description 节点关键字
   * @version 1.0
   */
  public enum Node {
    Actions, Action, Api, Param
  }

  /**
   * *<ACTION Package="" name="" version=""> * * <API protocol="" host="" port="" method=""
   * baseurl="" encrept=""> * * <PARAM name=""></PARAM> * * </API> * *</ACTION> *
   * 
   * @description 属性关键字
   * @version 1.0
   */
  public enum Attribute {
    PACKAGE, NAME, VERSION, PROTOCOL, HOST, PORT, METHOD, BASEURL, ENCREPT;

    @Override
    public String toString() {
      return this.toString().toLowerCase();
    }
  }

  /**
   * @description 方法关键字
   * @version 1.0
   */
  public enum Method {
    get, post, put, delete
  }

  /**
   * @description 传输数据格式
   * @version 1.0
   */
  public enum Format {
    normal(NormalHttpProxy.class), json(JsonHttpProxy.class), soap(SoapHttpProxy.class);
    Format(Class<? extends AbstractHttpProxy> proxyType) {
      this.proxyType = proxyType;
    }

    private Class<? extends AbstractHttpProxy> proxyType;

    public Class<? extends AbstractHttpProxy> getProxyType() {
      return proxyType;
    }

  }

  /**
   * @description API支持的协议类型
   * @version 1.0
   */
  public enum Protocol {
    http(HttpApiProxy.class), https(HttpApiProxy.class);
    Protocol(Class<? extends AbstractApiProxy> proxyType) {
      this.proxyType = proxyType;
    }

    private Class<? extends AbstractApiProxy> proxyType;

    public Class<? extends AbstractApiProxy> getProxyType() {
      return proxyType;
    }
  }

}
