package com.xiaodou.common.info.ding.req;

import java.util.List;

import com.xiaodou.common.info.ding.DingAT;
import com.xiaodou.common.util.StringUtils;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @name @see com.xiaodou.common.info.ding.req.TextDingReq.java
 * @CopyRright (c) 2018 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2018年1月23日
 * @description 钉钉通知文本类型消息
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TextDingReq extends AbstractDingReq {

  public TextDingReq(List<String> urls) {
    super(urls);
  }

  /** msgtype 消息类型 */
  private String msgtype = "text";
  /** text 文本消息 */
  private Text text;
  /** at 通知情况 */
  private DingAT at;

  /**
   * @name @see com.xiaodou.common.info.ding.req.TextDingReq.java
   * @CopyRright (c) 2018 by Corp.XiaodouTech
   * 
   * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
   * @date 2018年1月23日
   * @description 文本消息实体
   * @version 1.0
   */
  @Data
  public static class Text {
    private String content;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
  }

  @Override
  public boolean check() {
    return "text".equals(this.msgtype) && null != this.text
        && StringUtils.isNotBlank(this.text.getContent());
  }

}
