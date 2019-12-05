package com.xiaodou.async.response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xiaodou.async.domain.AsyncMessage;
import com.xiaodou.common.util.DateUtil;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.summer.vo.out.ResultInfo;
import com.xiaodou.summer.vo.out.ResultType;

public class QueryListResponse extends ResultInfo {

  public QueryListResponse(ResultType type) {
    super(type);
  }

  private List<SystemMessage> sysList = Lists.newArrayList();

  private List<UserMessage> userList = Lists.newArrayList();

  public List<SystemMessage> getSysList() {
    return sysList;
  }

  public void setSysList(List<SystemMessage> sysList) {
    this.sysList = sysList;
  }

  public void addSysList(List<AsyncMessage> mesList) {
    for (AsyncMessage mes : mesList) {
      SystemMessage smes = new SystemMessage();
      smes.setMid(mes.getId());
      smes.setContent(mes.getMessageBody());
      smes.setType(mes.getClassify().toString());
      smes.setTime(DateUtil.relativeDateFormat(mes.getCreateTime()));
      smes.setStatus(mes.getStatus().toString());
      sysList.add(smes);
    }
  }

  public List<UserMessage> getUserList() {
    return userList;
  }

  public void setUserList(List<UserMessage> userList) {
    this.userList = userList;
  }

  @SuppressWarnings("unchecked")
  public void addUserList(List<AsyncMessage> mesList) {
    for (AsyncMessage mes : mesList) {
      UserMessage umes = new UserMessage();
      umes.setMid(mes.getId());
      umes.setPublisherUserId(mes.getSrcUid());
      umes.setPublisherNickName(mes.getSrcNickName());
      umes.setPublisherPortrait(mes.getSrcPortrait());
      umes.setPublisherGender(mes.getSrcGender());
      umes.setPublisherUserName(mes.getSrcUserName());
      umes.setContent(mes.getMessageBody());
      umes.setType(mes.getClassify().toString());
      umes.setTime(DateUtil.relativeDateFormat(mes.getCreateTime()));
      umes.setStatus(mes.getStatus().toString());
      umes.setDealResult(mes.getDealResult());
      if (StringUtils.isJsonNotBlank(mes.getCallBackContent())) {
        umes.setCoreParam(FastJsonUtil.fromJson(mes.getCallBackContent(), HashMap.class));
      }
      userList.add(umes);
    }
  }

  public class SystemMessage {
    private String mid;
    private String content;
    private String type;
    private String time;
    private String status;

    public String getMid() {
      return mid;
    }

    public void setMid(String mid) {
      this.mid = mid;
    }

    public String getContent() {
      return content;
    }

    public void setContent(String content) {
      this.content = content;
    }

    public String getType() {
      return type;
    }

    public void setType(String type) {
      this.type = type;
    }

    public String getTime() {
      return time;
    }

    public void setTime(String time) {
      this.time = time;
    }

    public String getStatus() {
      return status;
    }

    public void setStatus(String status) {
      this.status = status;
    }

  }

  public class UserMessage {
    private String mid;
    private String content;
    private String publisherUserId;
    private String publisherNickName;
    private String publisherPortrait;
    private String publisherGender;
    private String publisherUserName;
    private String publisherMedalName;
    private String publisherMedalImageUrl;
    private String type;
    private String time;
    private String status;
    private String dealResult;
    private Map<String, Object> coreParam = Maps.newHashMap();

    public String getDealResult() {
      return dealResult;
    }

    public void setDealResult(String dealResult) {
      this.dealResult = dealResult;
    }

    public String getMid() {
      return mid;
    }

    public void setMid(String mid) {
      this.mid = mid;
    }

    public String getContent() {
      return content;
    }

    public void setContent(String content) {
      this.content = content;
    }

    public String getPublisherUserId() {
      return publisherUserId;
    }

    public void setPublisherUserId(String publisherUserId) {
      this.publisherUserId = publisherUserId;
    }

    public String getPublisherNickName() {
      return publisherNickName;
    }

    public void setPublisherNickName(String publisherNickName) {
      this.publisherNickName = publisherNickName;
    }

    public String getPublisherPortrait() {
      return publisherPortrait;
    }

    public void setPublisherPortrait(String publisherPortrait) {
      this.publisherPortrait = publisherPortrait;
    }

    public String getPublisherGender() {
      return publisherGender;
    }

    public void setPublisherGender(String publisherGender) {
      this.publisherGender = publisherGender;
    }

    public String getPublisherUserName() {
      return publisherUserName;
    }

    public void setPublisherUserName(String publisherUserName) {
      this.publisherUserName = publisherUserName;
    }

    public String getPublisherMedalName() {
      return publisherMedalName;
    }

    public void setPublisherMedalName(String publisherMedalName) {
      this.publisherMedalName = publisherMedalName;
    }

    public String getPublisherMedalImageUrl() {
      return publisherMedalImageUrl;
    }

    public void setPublisherMedalImageUrl(String publisherMedalImageUrl) {
      this.publisherMedalImageUrl = publisherMedalImageUrl;
    }

    public Map<String, Object> getCoreParam() {
      return coreParam;
    }

    public void setCoreParam(Map<String, Object> coreParam) {
      this.coreParam = coreParam;
    }

    public String getType() {
      return type;
    }

    public void setType(String type) {
      this.type = type;
    }

    public String getTime() {
      return time;
    }

    public void setTime(String time) {
      this.time = time;
    }

    public String getStatus() {
      return status;
    }

    public void setStatus(String status) {
      this.status = status;
    }

  }

}
