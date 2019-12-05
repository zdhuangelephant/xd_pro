package com.xiaodou.resources.response.forum;

import java.util.List;

import com.google.common.collect.Lists;
import com.xiaodou.resources.enums.forum.ForumResType;
import com.xiaodou.resources.response.BaseResponse;
import com.xiaodou.resources.response.model.ColumnistMajorModel;
import com.xiaodou.resources.vo.forum.Forum;
import com.xiaodou.summer.vo.out.ResultType;

/**
 * @name ColumnistDetailResponse CopyRright (c) 2016 by zhaodan
 * 
 * @author zhaodan
 * @date 2016年9月1日
 * @description 专栏详情响应
 * @version 1.0
 */
public class ColumnistDetailResponse extends BaseResponse {
  public ColumnistDetailResponse(ResultType type) {
    super(type);
  }

  public ColumnistDetailResponse(ForumResType type) {
    super(type);
  }

  /** columnist 专栏信息 */
  private ColumnistMajorModel columnist;
  /** newForumList 最新文章列表 */
  private List<Forum> newForumList = Lists.newArrayList();
  /** hotForumList 最热文章列表 */
  private List<Forum> hotForumList = Lists.newArrayList();
  /** followerList 关注者列表 */
  private List<String> followerList = Lists.newArrayList();

  public ColumnistMajorModel getColumnist() {
    return columnist;
  }

  public void setColumnist(ColumnistMajorModel columnist) {
    this.columnist = columnist;
  }

  public List<Forum> getNewForumList() {
    return newForumList;
  }

  public void setNewForumList(List<Forum> newForumList) {
    this.newForumList = newForumList;
  }

  public List<Forum> getHotForumList() {
    return hotForumList;
  }

  public void setHotForumList(List<Forum> hotForumList) {
    this.hotForumList = hotForumList;
  }

  public List<String> getFollowerList() {
    return followerList;
  }

  public void setFollowerList(List<String> followerList) {
    this.followerList = followerList;
  }

}
