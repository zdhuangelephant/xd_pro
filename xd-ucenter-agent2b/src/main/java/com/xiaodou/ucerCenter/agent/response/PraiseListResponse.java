package com.xiaodou.ucerCenter.agent.response;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.google.common.collect.Lists;
import com.xiaodou.summer.vo.out.ResultType;
import com.xiaodou.ucerCenter.agent.response.resultype.UcenterResType;

public class PraiseListResponse extends BaseResultInfo {

  public PraiseListResponse() {}

  public PraiseListResponse(ResultType type) {
    super(type);
  }

  public PraiseListResponse(UcenterResType resType) {
    super(resType);
  }

  /**
   * 赞列表
   */
  private List<UserPraiseInfo> praiseList = Lists.newArrayList();

  public List<UserPraiseInfo> getPraiseList() {
    return praiseList;
  }

  public void setPraiseList(List<UserPraiseInfo> praiseList) {
    this.praiseList = praiseList;
  }

  public static class UserPraiseInfo {
    private String userId = StringUtils.EMPTY;
    private String nickName = StringUtils.EMPTY;
    private String portrait = StringUtils.EMPTY;
    private String gender = StringUtils.EMPTY;
    private String sign = StringUtils.EMPTY;
    private String timestamp = StringUtils.EMPTY;

    public String getUserId() {
      return userId;
    }

    public void setUserId(String userId) {
      this.userId = userId;
    }

    public String getNickName() {
      return nickName;
    }

    public void setNickName(String nickName) {
      this.nickName = nickName;
    }

    public String getPortrait() {
      return portrait;
    }

    public void setPortrait(String portrait) {
      this.portrait = portrait;
    }

    public String getGender() {
      return gender;
    }

    public void setGender(String gender) {
      this.gender = gender;
    }

    public String getSign() {
      return sign;
    }

    public void setSign(String sign) {
      this.sign = sign;
    }

    public String getTimestamp() {
      return timestamp;
    }

    public void setTimestamp(String timestamp) {
      this.timestamp = timestamp;
    }
  }
}
