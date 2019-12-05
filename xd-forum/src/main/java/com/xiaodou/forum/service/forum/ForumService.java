package com.xiaodou.forum.service.forum;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.forum.model.forum.ForumModel;
import com.xiaodou.forum.request.forum.ForumRequest;
import com.xiaodou.forum.response.forum.ForumResponse;
import com.xiaodou.forum.service.facade.ForumServiceFacade;
import com.xiaodou.forum.service.queue.QueueService;
import com.xiaodou.forum.util.FileUtils;
import com.xiaodou.summer.vo.out.ResultType;

/**
 * 话题service
 * 
 * @author bing.cheng
 * 
 */
@Service
public class ForumService {

  @Resource
  ForumServiceFacade forumServiceFacade;
  
  @Resource
  QueueService queueService;
  
  /**
   * 发表话题
   * 
   * @param forumRequest
   * @return
   * @throws Exception
   */
  public ForumResponse insertForum(ForumRequest forumRequest) throws Exception {
    ForumResponse response = new ForumResponse(ResultType.SUCCESS);

    // 新增话题
    ForumModel forumModel = createForumModel(forumRequest);
    forumServiceFacade.insertForumEntity(forumModel);

    try {
      // 更新话题分类下话题总数
      queueService.updateForumCategoryNumber(forumRequest);
      // 更新话题分类参与人数
      queueService.updatePartakeNumber(forumRequest.getCategoryId());
    } catch (Exception e) {
      LoggerUtil.error("更新话题分类下话题总数异常", e);
    }
    return response;
  }

  /**
   * 构造话题模型
   * 
   * @param forumRequest
   * @return
   */
  private ForumModel createForumModel(ForumRequest forumRequest) {
    ForumModel forumModel = new ForumModel();
    forumModel.setTitle(forumRequest.getTitle());
    forumModel.setContent(forumRequest.getContent());
    forumModel.setCategoryId(forumRequest.getCategoryId());
    // 话题概要字数 默认不超过100个
    int summaryNumber = FileUtils.FORUM_PROP.getPropertiesInt("forum.list.post.summary.number");
    if (forumRequest.getContent().length() > summaryNumber) {
      forumModel.setOutline(forumRequest.getContent().substring(0, summaryNumber));
    } else {
      forumModel.setOutline(forumRequest.getContent());
    }
    forumModel.setImages(getImagesString(forumRequest));

    forumModel.setPublisherId(Long.parseLong(forumRequest.getUserModel().getId()));
    Timestamp nowTime = new Timestamp(new Date().getTime());
    forumModel.setCreateTime(nowTime);
    forumModel.setUpdateTime(nowTime);
    forumModel.setOperator(forumRequest.getUserModel().getId().toString());
    forumModel.setOperatorip(forumRequest.getClientIp());
    return forumModel;
  }

  /**
   * 获取图片list json串
   * 
   * @param forumRequest
   * @return
   */
  private String getImagesString(ForumRequest forumRequest) {
    List<String> list = new ArrayList<String>();
    try {
      String imagesStr = forumRequest.getImages();
      if (StringUtils.isBlank(imagesStr)) {
        return JSON.toJSONString(new ArrayList<String>());
      } else {
        String imageArr[] = imagesStr.split(";");
        for (int i = 0; i < imageArr.length; i++) {
          list.add(imageArr[i]);
        }
      }
      return JSON.toJSONString(list);
    } catch (Exception e) {
      LoggerUtil.error("发布话题异常，图片地址解析异常", e);
      return JSON.toJSONString(new ArrayList<String>());
    }
  }

}
