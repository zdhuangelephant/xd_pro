package com.xiaodou.resources.response.forum;

import com.xiaodou.resources.vo.forum.ForumRecommendVo;
import com.xiaodou.summer.vo.out.ResultType;

/**
 * 推荐话题
 * 
 * @author bing.cheng
 * 
 */
public class ForumRecommendResponse extends ListResponse<ForumRecommendVo>{
  /** unReadInfoCount 我的未读消息数 */
  private String unReadInfoCount = Integer.toString(0);

  public String getUnReadInfoCount() {
    return unReadInfoCount;
  }

  public void setUnReadInfoCount(String unReadInfoCount) {
    this.unReadInfoCount = unReadInfoCount;
  }

  public ForumRecommendResponse(ResultType type) {
    super(type);
  }
  }
