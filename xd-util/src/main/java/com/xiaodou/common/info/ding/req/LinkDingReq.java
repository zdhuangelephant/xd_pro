package com.xiaodou.common.info.ding.req;

import java.util.List;

import com.xiaodou.common.util.StringUtils;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @name @see com.xiaodou.common.info.ding.req.LinkDingReq.java
 * @CopyRright (c) 2018 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2018年1月23日
 * @description 钉钉通知链接类型消息
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class LinkDingReq extends AbstractDingReq {

  public LinkDingReq(List<String> urls) {
    super(urls);
  }

  /** msgtype 消息类型 */
  private String msgtype = "link";
  /** link 链接消息 */
  private Link link;

  /**
   * @name @see com.xiaodou.common.info.ding.req.LinkDingReq.java
   * @CopyRright (c) 2018 by Corp.XiaodouTech
   * 
   * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
   * @date 2018年1月23日
   * @description 链接消息实体
   * @version 1.0
   */
  @Data
  public static class Link {
    /** title 标题 */
    private String title;
    /** text 内容 */
    private String text;
    /** picUrl 封面图片 */
    private String picUrl;
    /** messageUrl 链接地址 */
    private String messageUrl;
  }

  @Override
  public boolean check() {
    return "link".equals(this.msgtype) && null != this.link
        && StringUtils.isAllNotBlank(this.link.title, this.link.text, this.link.messageUrl);
  }

}
