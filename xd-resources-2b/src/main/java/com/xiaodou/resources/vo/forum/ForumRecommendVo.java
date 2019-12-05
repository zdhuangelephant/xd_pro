package com.xiaodou.resources.vo.forum;

import com.alibaba.fastjson.JSON;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.resources.model.forum.ForumUserModel;

public class ForumRecommendVo extends Forum {


  public ForumRecommendVo() {}

  public ForumRecommendVo(ForumUserModel model) {
    super(model);
    setTop(String.valueOf(model.getTop()));
    setClassficationShortName(model.getCategoryShortName());
  }
  /**
   * 话题属性 1-置顶 0-非置顶
   */
  private String top = StringUtils.EMPTY;
  /** classficationImageString 话题分类简称 */
  private String classficationShortName = StringUtils.EMPTY;

  public String getClassficationShortName() {
    return classficationShortName;
  }

  public void setClassficationShortName(String classficationShortName) {
    this.classficationShortName = classficationShortName;
  }

  public String getTop() {
    return top;
  }

  public void setTop(String top) {
    this.top = top;
  }

  @Override
  public String toString() {
    return JSON.toJSONString(this);
  }


}
