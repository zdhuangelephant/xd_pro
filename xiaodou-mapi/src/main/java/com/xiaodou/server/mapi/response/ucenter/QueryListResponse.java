package com.xiaodou.server.mapi.response.ucenter;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xiaodou.server.mapi.response.selftaught.BaseResponse;
import com.xiaodou.server.mapi.response.ucenter.resultype.UcenterResType;
import com.xiaodou.summer.vo.out.ResultType;

public class QueryListResponse extends BaseResponse {


  public QueryListResponse() {
  }
  public QueryListResponse(ResultType type) {
    super(type);
  }

  public QueryListResponse(UcenterResType type) {
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

  public List<UserMessage> getUserList() {
    return userList;
  }

  public void setUserList(List<UserMessage> userList) {
    this.userList = userList;
  }


  public static class SystemMessage {
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

  public static class UserMessage {
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
