package com.xiaodou.server.mapi.engine;

import com.xiaodou.server.mapi.engine.ActionEnum.Format;
import com.xiaodou.server.mapi.engine.ActionEnum.Method;
import com.xiaodou.server.mapi.engine.ActionEnum.Protocol;

/**
 * @name @see com.xiaodou.server.mapi.engine.ActionConstant.java
 * @CopyRright (c) 2016 by XiaoDou NetWork Technology
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年1月22日
 * @description 注册Action用常量池
 * @version 1.0
 */
public class ActionConstant {

  /** DEFAULT_PROTOCOL 默认协议 */
  public static final Protocol DEFAULT_PROTOCOL = Protocol.http;

  /** DEFAULT_METHOD 默认方法 */
  public static final Method DEFAULT_METHOD = Method.get;

  /** DEFAULT_FORMAT 默认数据传输格式 */
  public static final Format DEFAULT_FORMAT = Format.normal;

  /** DEFAULT_TIMEOUT 默认超时时间 ms*/
  public static final Integer DEFAULT_TIMEOUT = 15000;

  /** DEFAULT_RETRY 默认重试次数 */
  public static final Integer DEFAULT_RETRY = 1;

}
