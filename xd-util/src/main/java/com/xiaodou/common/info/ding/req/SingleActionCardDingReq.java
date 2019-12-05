package com.xiaodou.common.info.ding.req;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.xiaodou.common.util.StringUtils;

/**
 * @name @see com.xiaodou.common.info.ding.req.SingleActionCardDingReq.java
 * @CopyRright (c) 2018 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2018年1月23日
 * @description 钉钉通知整体跳转类型消息
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SingleActionCardDingReq extends AbstractDingReq {

  public SingleActionCardDingReq(List<String> urls) {
    super(urls);
  }

  /** msgtype 消息类型 */
  private String msgtype = "actionCard";
  /** actionCard 跳转卡片 */
  private ActionCard actionCard;

  /**
   * @name @see com.xiaodou.common.info.ding.req.ActionCardDingReq.java
   * @CopyRright (c) 2018 by Corp.XiaodouTech
   * 
   * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
   * @date 2018年1月23日
   * @description 跳转卡片实体
   * @version 1.0
   */
  @Data
  public static class ActionCard {
    /** title 标题 */
    private String title;
    /** text 内容 */
    private String text;
    /** hideAvatar 0-正常发消息者头像,1-隐藏发消息者头像 */
    private String hideAvatar;
    /** btnOrientation 0-按钮竖直排列，1-按钮横向排列 */
    private String btnOrientation;
    /** singleTitle 单个按钮的方案。(设置此项和singleURL后btns无效。) */
    private String singleTitle;
    /** singleURL 点击singleTitle按钮触发的URL */
    private String singleURL;
  }

  @Override
  public boolean check() {
    return "actionCard".equals(this.msgtype)
        && null != this.actionCard
        && StringUtils.isAllNotBlank(this.actionCard.title, this.actionCard.text,
            this.actionCard.singleTitle, this.actionCard.singleURL);
  }

}
