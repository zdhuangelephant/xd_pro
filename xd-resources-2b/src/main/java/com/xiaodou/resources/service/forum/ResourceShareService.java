package com.xiaodou.resources.service.forum;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.xiaodou.common.util.CommUtil;
import com.xiaodou.resources.enums.forum.DigestType;
import com.xiaodou.resources.model.forum.ForumUserModel;
import com.xiaodou.resources.request.forum.ResourceShareRequest;
import com.xiaodou.resources.service.forum.facade.ForumServiceFacade;
import com.xiaodou.resources.util.HtmlUtil;
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
public class ResourceShareService extends AbstractShareService<ResourceShareRequest> {

  @Resource
  ForumServiceFacade forumServiceFacade;

  @Override
  public ShareResponse processResponse(ResourceShareRequest pojo, ShareResponse response) {
    String shareType = pojo.getShareType();
    if (("1").equals(shareType)) {
      // 根据id查询话题内容
      ForumUserModel forumUserModel = forumServiceFacade.queryForumUserById(pojo.getResourceId());
      if (null != forumUserModel) {
        String title = forumUserModel.getTitle();
        String content = forumUserModel.getContent();
        if(DigestType.TALK.getType().equals(forumUserModel.getDigest())){
          response.setTitle(content);
          response.setContent(content);
        }else{
          response.setTitle(HtmlUtil.delHTMLTag(title));
          response.setContent(HtmlUtil.delHTMLTag(content));
        }
      }
      String timeStamp = null;
      try {
        timeStamp = CommUtil.HEXAndMd5(forumUserModel.getCreateTime().toString());
      } catch (Exception e) {}
      // 做题详情展示接口 -> url
      String url = String.format(response.getUrl(), pojo.getResourceId(), timeStamp);
      response.setUrl(url);
    }
    return response;
  }

}
