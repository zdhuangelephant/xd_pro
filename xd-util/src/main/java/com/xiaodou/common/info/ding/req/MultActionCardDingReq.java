package com.xiaodou.common.info.ding.req;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.google.common.collect.Lists;
import com.xiaodou.common.util.StringUtils;

/**
 * @name @see com.xiaodou.common.info.ding.req.MultActionCardDingReq.java
 * @CopyRright (c) 2018 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2018年1月23日
 * @description 钉钉通知独立跳转类型消息
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class MultActionCardDingReq extends AbstractDingReq {

  public MultActionCardDingReq(List<String> urls) {
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
    /** btns 跳转动作列表 */
    private List<Action> btns = Lists.newArrayList();
  }

  /**
   * @name @see com.xiaodou.common.info.ding.req.MultActionCardDingReq.java
   * @CopyRright (c) 2018 by Corp.XiaodouTech
   * 
   * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
   * @date 2018年1月23日
   * @description 独立跳转动作
   * @version 1.0
   */
  @Data
  public static class Action {
    private String title;
    private String actionURL;
  }

  @Override
  public boolean check() {
    return "actionCard".equals(this.msgtype) && null != this.actionCard
        && StringUtils.isAllNotBlank(this.actionCard.title, this.actionCard.text)
        && null != this.actionCard.btns && !this.actionCard.btns.isEmpty();
  }

}
