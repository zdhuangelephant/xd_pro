package com.xiaodou.forum.request.forum;

import com.xiaodou.summer.validator.annotion.LegalValue;
import com.xiaodou.summer.validator.annotion.NotEmpty;
import com.xiaodou.summer.validator.enums.ValueScope;

/**
 * 话题列表
 * 
 * @author weichao.zhai
 * @time 2015年4月6日 下午7:34:22
 */
public class ForumListRequest extends BaseRequest {

  /** 分类ID */
  @NotEmpty
  private String classificationId;

  /** 最后一条话题ID */
  private String forumId;

  /** 每页显示个数 */
  @LegalValue(value = "1", scope = ValueScope.GE)
  private String size = "20";

  public String getClassificationId() {
    return classificationId;
  }

  public void setClassificationId(String classificationId) {
    this.classificationId = classificationId;
  }

  public String getForumId() {
    return forumId;
  }

  public void setForumId(String forumId) {
    this.forumId = forumId;
  }

  public String getSize() {
    return size;
  }

  public void setSize(String size) {
    this.size = size;
  }

}
