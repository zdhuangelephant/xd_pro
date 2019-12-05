package com.xiaodou.resources.model.forum;

import java.util.List;

import com.xiaodou.ucenter.model.UserModel;

/**
 * weichao.zhai
 * @author bing.cheng
 *
 */
public class ForumUserModel extends ForumModel {
	/**
	 * 用户user
	 */
	private UserModel user;
	/**
	  * 图片(分享页展示时用到)
	*/
	private List<String> imgs;
	/**
     * 热门评论(分享页展示时用到)
   */
   private List<CommentUserModel> hotComments;
	

	public UserModel getUser() {
		return user;
	}

	public void setUser(UserModel user) {
		this.user = user;
	}

	public List<String> getImgs() {
    return imgs;
  }

  public void setImgs(List<String> imgs) {
    this.imgs = imgs;
  }

  public List<CommentUserModel> getHotComments() {
    return hotComments;
  }

  public void setHotComments(List<CommentUserModel> hotComments) {
    this.hotComments = hotComments;
  }

  @Override
	public String toString() {
		return super.toString();
	}
}
