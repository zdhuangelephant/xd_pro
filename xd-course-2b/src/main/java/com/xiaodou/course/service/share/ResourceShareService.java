package com.xiaodou.course.service.share;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xiaodou.common.util.CommUtil;
import com.xiaodou.course.model.forum.ForumModel;
import com.xiaodou.course.service.facade.ForumServiceFacade;
import com.xiaodou.course.service.forum.ForumService;
import com.xiaodou.course.util.HtmlUtil;
import com.xiaodou.course.web.request.share.ResourceShareRequest;
import com.xiaodou.share.response.ShareResponse;
import com.xiaodou.share.service.AbstractShareService;

/**
 * @name ResourceShareService CopyRright (c) 2016 by zhaodan
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年11月25日
 * @description 资源分享Service
 * @version 1.0
 */
@Service("resourceShareService")
public  class ResourceShareService extends AbstractShareService<ResourceShareRequest> {

  @Resource
  ForumServiceFacade forumServiceFacade;
  @Resource
  ForumService  forumService;
  @Override
  public ShareResponse processResponse(ResourceShareRequest pojo, ShareResponse response) {
    String shareType = pojo.getShareType();
    if (("1").equals(shareType)) {
      // 根据id查询话题内容
      ForumModel forum = forumService.getForumModelById(pojo.getResourceId());
      if (null != forum) {
        String title = forum.getForumTitle();
        String content = forum.getForumContent();
        response.setTitle(HtmlUtil.delHTMLTag(title));
        response.setContent(HtmlUtil.delHTMLTag(content));
        response.setImageUrl(forum.getForumCover());
      } 
      String timeStamp = null;
      try {
        timeStamp = CommUtil.HEXAndMd5(forum.getCreateTime().toString());
      } catch (Exception e) {}
      // 做题详情展示接口 -> url
      String url = String.format(response.getUrl(), pojo.getResourceId(), timeStamp);
      response.setUrl(url);
    }
    return response;
  }

}
