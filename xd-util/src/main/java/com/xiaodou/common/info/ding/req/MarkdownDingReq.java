package com.xiaodou.common.info.ding.req;

import java.util.List;

import com.xiaodou.common.info.ding.DingAT;
import com.xiaodou.common.util.StringUtils;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @name @see com.xiaodou.common.info.ding.req.MarkdownDingReq.java
 * @CopyRright (c) 2018 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2018年1月23日
 * @description 钉钉通知markdown类型消息
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class MarkdownDingReq extends AbstractDingReq {

  public MarkdownDingReq(List<String> urls) {
    super(urls);
  }

  /** msgtype 消息类型 */
  private String msgtype = "markdown";
  /** markdown markdown消息 */
  private Markdown markdown;
  /** at 通知情况 */
  private DingAT at = new DingAT();

  /**
   * @name @see com.xiaodou.common.info.ding.req.MarkdownDingReq.java
   * @CopyRright (c) 2018 by Corp.XiaodouTech
   * 
   * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
   * @date 2018年1月23日
   * @description Markdown消息实体
   * @version 1.0
   */
  @Data
  public static class Markdown {
    /** title 标题 */
    private String title;
    /** text 内容 */
    private String text;
  }

  @Override
  public boolean check() {
    return "markdown".equals(this.msgtype) && null != this.markdown
        && StringUtils.isAllNotBlank(this.markdown.title, this.markdown.text);
  }

}
