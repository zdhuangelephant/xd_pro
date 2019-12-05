package com.xiaodou.common.info.ding;

import java.util.List;

/**
 * @name @see com.xiaodou.common.info.ding.req.IDingReq.java
 * @CopyRright (c) 2018 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2018年1月23日
 * @description 钉钉通知抽象参数类
 * @version 1.0
 */
public interface IDingReq {

  /**
   * 获取文本消息体
   * 
   * @return 文本消息
   */
  public String info();

  /**
   * 获取机器人列表
   * 
   * @return 机器人列表
   */
  public List<String> getUrls();

}
