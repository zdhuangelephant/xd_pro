package com.xiaodou.resources.service.forum;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.xiaodou.async.toC.model.NoticeToFansMessage;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.jmsg.client.RabbitMQSender;
import com.xiaodou.resources.dao.search.ResourcesDao;
import com.xiaodou.resources.enums.forum.ForumResType;
import com.xiaodou.resources.model.forum.CategoryRelationModel;
import com.xiaodou.resources.model.forum.ForumModel;
import com.xiaodou.resources.model.forum.ForumUserModel;
import com.xiaodou.resources.model.forum.ResourcesColumnist;
import com.xiaodou.resources.request.forum.DelForumRequest;
import com.xiaodou.resources.request.forum.ForumRequest;
import com.xiaodou.resources.response.BaseResponse;
import com.xiaodou.resources.response.forum.ForumResponse;
import com.xiaodou.resources.service.QueueService;
import com.xiaodou.resources.service.forum.facade.ForumServiceFacade;
import com.xiaodou.resources.util.HtmlUtil;
import com.xiaodou.resources.util.IDGenerator;
import com.xiaodou.summer.vo.out.ResultType;

/**
 * 话题service
 * 
 * @author bing.cheng
 * 
 */
@Service("ForumService")
public class ForumService {
  @Resource
  ResourcesDao resourcesDao;
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
  public ForumResponse insertForum(ForumRequest pojo) throws Exception {
    ForumResponse response = new ForumResponse(ResultType.SUCCESS);
    // 新增话题
    ForumModel forumModel = createForumModel(pojo);
    if (null == forumModel) return new ForumResponse(ForumResType.NULLCOLUMN);
    String resourcesId = IDGenerator.getSeqID();
    forumModel.setId(resourcesId);
    forumModel.setModule(pojo.getModule());
    forumServiceFacade.insertForumEntity(forumModel);
    // 话题分类与话题关系表
    CategoryRelationModel model = new CategoryRelationModel();
    if (!StringUtils.isBlank(forumModel.getCategoryId())) {
      String categoryList[] = forumModel.getCategoryId().split(",");
      for (String s : categoryList) {
        model.setCategoryId(s);
        model.setResourcesId(forumModel.getId());
        forumServiceFacade.categoryRelationInsert(model);
      }
    }
    if (!pojo.getDigest().toString().equals("0") && forumModel.getColumnId() != null) {
      queueService.refreshOpusNum(forumModel);
      queueService.sendNoticeToColumnFollower(forumModel);
    }
    NoticeToFansMessage message = new NoticeToFansMessage();
    message.setModule(forumModel.getModule());
    message.setSrcUid(Long.toString(forumModel.getPublisherId()));
    RabbitMQSender.getInstance()
        .send(message, UUID.randomUUID().toString(), "3_noticeToFans");
    /*
     * //存储到ES ResourcesForEs re=new ResourcesForEs(); re.setContent(forumModel.getContent());
     * re.setOutline(forumModel.getOutline()); re.setPublisherId(forumModel.getPublisherId());
     * re.setTitle(forumModel.getTitle()); re.setId(forumModel.getId());
     * re.setCreateTime(forumModel.getCreateTime()); re.setDigest(pojo.getDigest());
     * resourcesDao.addORUpdata(forumModel.getId(),re);
     */
    // 用于发布完成的跳转
    response.setResourcesId(resourcesId);
    return response;
  }

  /**
   * 资源修改
   * 
   * @param forumRequest
   * @return
   */
  public ForumResponse editForum(ForumRequest pojo) {
    ForumUserModel forumUserModel = forumServiceFacade.queryForumUserById(pojo.getResourcesId());
    ForumResponse response = new ForumResponse(ResultType.SUCCESS);
    ForumModel forumModel = createForumEditModel(pojo);
    Map<String, Object> input = new HashMap<String, Object>();
    input.put("id", pojo.getResourcesId());
    forumServiceFacade.updateForumEntity(input, forumModel);
    // 话题分类与话题关系表
    CategoryRelationModel model = new CategoryRelationModel();
    model.setResourcesId(pojo.getResourcesId());
    Map<String, Object> cond = new HashMap<String, Object>();
    cond.put("resourcesId", pojo.getResourcesId());
    forumServiceFacade.categoryRelationDelete(cond);
    if (!StringUtils.isBlank(forumModel.getCategoryId())) {
      String categoryList[] = forumModel.getCategoryId().split(",");
      for (String s : categoryList) {
        model.setCategoryId(s);
        forumServiceFacade.categoryRelationInsert(model);
      }
    }
    /*
     * ResourcesForEs re=new ResourcesForEs(); re.setContent(forumModel.getContent());
     * re.setOutline(forumModel.getOutline()); re.setPublisherId(forumModel.getPublisherId());
     * re.setTitle(forumModel.getTitle()); re.setId(pojo.getResourcesId());
     * re.setCreateTime(forumUserModel.getCreateTime()); re.setDigest(forumUserModel.getDigest());
     * resourcesDao.addORUpdata(pojo.getResourcesId(),re);
     */
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
    forumModel.setCategoryId(forumRequest.getCategoryId());
    forumModel.setCategoryName(forumRequest.getCategoryName());
    if (forumRequest.getContent() == null) {
      forumModel.setContent("");
    } else {
      forumModel.setContent(forumRequest.getContent());
    }
    String outLine=HtmlUtil.delHTMLTag(forumRequest.getContent());
    if (outLine.length() > 100) {
      forumModel.setOutline(outLine.substring(0, 99));
    } else {
      forumModel.setOutline(outLine);
    }
    forumModel.setImages(getImagesString(forumRequest));
    forumModel.setPublisherId(Long.parseLong(forumRequest.getUid()));
    Timestamp nowTime = new Timestamp(new Date().getTime());
    forumModel.setCreateTime(nowTime);
    forumModel.setUpdateTime(nowTime);
    forumModel.setOperator(forumRequest.getUid());
    forumModel.setOperatorip(forumRequest.getClientIp());
    forumModel.setDigest(forumRequest.getDigest());
    forumModel.setVideoUrl(forumRequest.getVideoUrl());
    forumModel.setCover(forumRequest.getCover());
    if (StringUtils.isBlank(forumRequest.getColumnId())
        && !forumRequest.getDigest().toString().equals("0")) {
      ResourcesColumnist r =
          forumServiceFacade.queryDefaultResourcesColumnist(forumRequest.getUid());
      if (r != null) {
        forumModel.setColumnId(r.getId());
      } else {
        return null;
      }
    } else {
      forumModel.setColumnId(forumRequest.getColumnId());
    }
    return forumModel;
  }

  /**
   * 构造话题模型
   * 
   * @param forumRequest
   * @return
   */
  private ForumModel createForumEditModel(ForumRequest forumRequest) {
    ForumModel forumModel = new ForumModel();
    forumModel.setTitle(forumRequest.getTitle());
    forumModel.setCategoryId(forumRequest.getCategoryId());
    forumModel.setCategoryName(forumRequest.getCategoryName());
    if (forumRequest.getContent() == null) {
      forumModel.setContent("");
    } else {
      forumModel.setContent(forumRequest.getContent());
    }
    String outLine = HtmlUtil.getTextFromHtml(forumRequest.getContent());
    if (outLine.length() > 100) {
      forumModel.setOutline(outLine.substring(0, 99));
    } else {
      forumModel.setOutline(outLine);
    }
    forumModel.setImages(getImagesString(forumRequest));
    forumModel.setPublisherId(Long.parseLong(forumRequest.getUid()));
    Timestamp nowTime = new Timestamp(new Date().getTime());
    forumModel.setUpdateTime(nowTime);
    forumModel.setOperator(forumRequest.getUid());
    forumModel.setOperatorip(forumRequest.getClientIp());
    forumModel.setDigest(forumRequest.getDigest());
    forumModel.setVideoUrl(forumRequest.getVideoUrl());
    forumModel.setCover(forumRequest.getCover());
    if (StringUtils.isBlank(forumRequest.getColumnId())
        && !forumRequest.getDigest().toString().equals("0")) {
      ResourcesColumnist r =
          forumServiceFacade.queryDefaultResourcesColumnist(forumRequest.getUid());
      if (r != null) {
        forumModel.setColumnId(r.getId());
      } else {
        return null;
      }
    } else {
      forumModel.setColumnId(forumRequest.getColumnId());
    }
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

  public BaseResponse delResources(DelForumRequest pojo) {
    BaseResponse response = new BaseResponse(ResultType.SUCCESS);
    Map<String, Object> input = new HashMap<String, Object>();
    input.put("id", pojo.getResourcesId());
    forumServiceFacade.deleteForumList(input);
    // 删除话题分类与话题关系表中的数据
    Map<String, Object> cond = new HashMap<String, Object>();
    cond.put("resourcesId", pojo.getResourcesId());
    forumServiceFacade.categoryRelationDelete(cond);
    /* resourcesDao.del(pojo.getResourcesId()); */
    return response;
  }

}
