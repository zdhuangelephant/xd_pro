package com.xiaodou.forum.service.forum;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.forum.model.forum.ForumUserModel;
import com.xiaodou.forum.request.forum.ForumShareRequest;
import com.xiaodou.forum.service.facade.ForumServiceFacade;
import com.xiaodou.share.response.ShareResponse;
import com.xiaodou.share.service.AbstractShareService;

@Service("forumShareService")
public class ForumShareService extends AbstractShareService<ForumShareRequest> {
  @Resource
  ForumServiceFacade forumServiceFacade;
  @Resource
  ForumDetailService forumDetailService;

  @Override
  public ShareResponse processResponse(ForumShareRequest pojo, ShareResponse response) {
    String shareType = pojo.getShareType();
    if (("4").equals(shareType)) {
      // 1 查询话题详情
      String forumId = pojo.getForumId();
      String categoryId = pojo.getCategoryId();
      ForumUserModel forumUserModel =
          forumServiceFacade.queryForumUserByIdAndCategoryId(forumId, categoryId);
      if (null != forumUserModel) {
        // 2 填充 话题标题 -> title 话题概括 -> content
        response.setTitle(forumUserModel.getTitle());
        response.setContent(forumUserModel.getOutline());
        // 3 如果话题中有图片 话题图片第一张 -> imgUrl 否则 不做修改
        // String imageUrl = ForumShareProp.getParams("shareinfo.1.imageUrl.4");
        String images = forumUserModel.getImages();
        if (StringUtils.isJsonNotBlank(images)) {
          response.setImageUrl(JSON.parseArray(images, String.class).get(0));
        }
        // 4 话题详情展示接口 -> url
        String url = String.format(response.getUrl(), forumId, categoryId);
        response.setUrl(url);
      }
    }
    return response;
  }

  // public ForumUserModel queryForumUserByIdAndCategoryId(String forumId, String categoryId) {
  // ForumUserModel forumUserModel =
  // forumServiceFacade.queryForumUserByIdAndCategoryId(forumId, categoryId);
  // String images = forumUserModel.getImages();
  // if (StringUtils.isJsonNotBlank(images)) {
  // List<String> imgList = FastJsonUtil.fromJsons(images, new TypeReference<List<String>>() {});
  // forumUserModel.setImgs(imgList);
  // }
  // // 评论
  // List<CommentUserModel> hotComments =
  // forumServiceFacade.queryForumCommentByForumIdForHot(forumId,
  // FileUtils.FORUM_PROP.getPropertiesInt("forum.detail.comment.hot.size"),
  // ForumUtil.getCommentListOutput());
  // forumUserModel.setHotComments(hotComments);
  // return forumUserModel;
  // }
}
