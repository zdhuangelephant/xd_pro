package com.xiaodou.vo.mq;

import lombok.Data;

import com.xiaodou.jmsg.entity.AbstractMessagePojo;

/**
 * @name UserLearnAchieveMessage
 * @CopyRright (c) 2017 by zhaodan
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年3月24日
 * @description 记录用户学习进度异步消息参数类
 * @version 1.0
 */
public class UserLearnAchieveMessage extends AbstractMessagePojo {

  /** MESSAGE_NAME 记录学习进度参数名 */
  private static final String MESSAGE_NAME = "saveLearnAchieve";

  public UserLearnAchieveMessage() {
    super(null);
  }

  public UserLearnAchieveMessage(UserLearnAchieveVo message) {
    super(MESSAGE_NAME);
    setTransferObject(message);
  }

  /**
   * @name UserLearnAchieveVo
   * @CopyRright (c) 2017 by zhaodan
   * 
   * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
   * @date 2017年3月24日
   * @description 记录学习进度参数类
   * @version 1.0
   */
  @Data
  public static class UserLearnAchieveVo {
    /** userId 用户ID */
    private String userId;
    /** moduleId 模块号 */
    private String moduleId;
    /** courseId 课程ID */
    private String courseId;
    /** chapterId 章ID */
    private String chapterId;
    /** itemId 节ID */
    private String itemId;

    public UserLearnAchieveVo() {}

    public String getUserId() {
      return userId;
    }

    public void setUserId(String userId) {
      this.userId = userId;
    }

    public String getModuleId() {
      return moduleId;
    }

    public void setModuleId(String moduleId) {
      this.moduleId = moduleId;
    }

    public String getCourseId() {
      return courseId;
    }

    public void setCourseId(String courseId) {
      this.courseId = courseId;
    }

    public String getChapterId() {
      return chapterId;
    }

    public void setChapterId(String chapterId) {
      this.chapterId = chapterId;
    }

    public String getItemId() {
      return itemId;
    }

    public void setItemId(String itemId) {
      this.itemId = itemId;
    }

  }

}
