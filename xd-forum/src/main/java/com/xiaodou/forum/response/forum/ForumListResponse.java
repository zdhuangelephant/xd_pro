package com.xiaodou.forum.response.forum;

import java.util.List;

import com.xiaodou.forum.enums.ForumResType;
import com.xiaodou.forum.vo.forum.Forum;
import com.xiaodou.summer.vo.out.ResultType;

/**
 * 话题列表
 * 
 * @author weichao.zhai
 * @time 2015年4月6日 下午7:43:26
 */
public class ForumListResponse extends BaseResponse {

  public ForumListResponse(ResultType type) {
    super(type);
  }

  public ForumListResponse(ForumResType type) {
    super(type);
  }

  /**category 话题分类*/
  private ForumCategoryResponse category;
  /** 置顶话题 */
  private List<Forum> top;
  /** 话题列表 */
  private List<Forum> list;
  
  public ForumCategoryResponse getCategory() {
    return category;
  }

  public void setCategory(ForumCategoryResponse category) {
    this.category = category;
  }

  public List<Forum> getTop() {
    return top;
  }

  public void setTop(List<Forum> top) {
    this.top = top;
  }

  public List<Forum> getList() {
    return list;
  }

  public void setList(List<Forum> list) {
    this.list = list;
  }
}
