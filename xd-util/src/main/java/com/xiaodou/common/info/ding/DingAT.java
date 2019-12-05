package com.xiaodou.common.info.ding;

import java.util.List;

import lombok.Data;

/**
 * @name @see com.xiaodou.common.info.ding.req.DingAT.java
 * @CopyRright (c) 2018 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2018年1月23日
 * @description 钉钉通知手机描述类
 * @version 1.0
 */
@Data
public class DingAT {
  /** atMobiles 通知手机号列表 */
  private List<String> atMobiles;
  /** isAtAll 是否全部通知 */
  private boolean isAtAll = false;
}
