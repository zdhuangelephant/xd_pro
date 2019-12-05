package com.xiaodou.common.info.ding.req;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.google.common.collect.Lists;
import com.xiaodou.common.info.ding.req.LinkDingReq.Link;

/**
 * @name @see com.xiaodou.common.info.ding.req.FeedCardDingReq.java
 * @CopyRright (c) 2018 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2018年1月23日
 * @description 钉钉通知FeedCard类型消息
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class FeedCardDingReq extends AbstractDingReq {

  public FeedCardDingReq(List<String> urls) {
    super(urls);
  }

  /** msgtype 消息类型 */
  private String msgtype = "feedCard";
  /** link 链接消息 */
  private List<Link> links = Lists.newArrayList();

  @Override
  public boolean check() {
    return "feedCard".equals(this.msgtype) && null != this.links && !this.links.isEmpty();
  }

}
