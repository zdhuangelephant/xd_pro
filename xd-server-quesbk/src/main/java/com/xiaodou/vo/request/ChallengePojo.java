package com.xiaodou.vo.request;

import com.xiaodou.summer.validator.annotion.LegalValue.LegalValueList;
import com.xiaodou.summer.validator.annotion.NotEmpty;

/**
 * @name @see com.xiaodou.vo.request.ChallengePojo.java
 * @CopyRright (c) 2016 by XiaoDou NetWork Technology
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年2月25日
 * @description 挑战请求pojo
 * @version 1.0
 */
public class ChallengePojo extends QuesBasePojo {

  /** type 挑战类型  1 好友挑战 2 随机挑战*/
  @NotEmpty
  @LegalValueList({"1", "2"})
  private Short type;
  /** userId 挑战用户ID */
  @NotEmpty(field = "type", value = "1")
  private String targetUserId;
  /** courseId 课程ID */
  @NotEmpty
  private String courseId;

  public Short getType() {
    return type;
  }

  public void setType(Short type) {
    this.type = type;
  }

  public String getTargetUserId() {
    return targetUserId;
  }

  public void setTargetUserId(String targetUserId) {
    this.targetUserId = targetUserId;
  }

  public String getCourseId() {
    return courseId;
  }

  public void setCourseId(String courseId) {
    this.courseId = courseId;
  }

}
