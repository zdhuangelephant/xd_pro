package com.xiaodou.mission.vo.response;

import java.util.List;

import com.google.common.collect.Lists;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.mission.constants.MissionConstant;
import com.xiaodou.mission.engine.model.mission.AbstractMissionRecord;
import com.xiaodou.summer.vo.out.ResultInfo;
import com.xiaodou.summer.vo.out.ResultType;

/**
 * @name @see com.xiaodou.mission.vo.response.MedalListResponse.java
 * @CopyRright (c) 2016 by <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年4月4日
 * @description 勋章列表响应类
 * @version 1.0
 */
public class MedalListResponse extends ResultInfo {

  public MedalListResponse() {}

  public MedalListResponse(ResultType type) {
    super(type);
  }

  private List<Medal> medalList = Lists.newArrayList();

  public List<Medal> getMedalList() {
    return medalList;
  }

  public void setMedalList(List<Medal> medalList) {
    this.medalList = medalList;
  }

  public static class Medal {
    /** medalId 勋章ID */
    private String medalId;
    /** medalName 勋章名称 */
    private String medalName;
    /** medalImg 勋章图标 */
    private String medalImg;
    /** content 勋章描述 */
    private String content;
    /** status 达成状态 */
    private String status = MissionConstant.FALSE.toString();
    /** numerator 完成数量 */
    private String numerator;
    /** denominator 目标数量 */
    private String denominator;

    public Medal(AbstractMissionRecord record) {
      if (null == record) {
        return;
      }
      this.medalId = record.getMissionId();
      this.medalName = record.getMissionName();
      this.medalImg = record.getMissionPicurl();
      this.content = record.getMissionDesc();
      this.numerator =
          StringUtils.isBlank(record.getCurrentNum()) ? "0" : MissionConstant.TRUE == record
              .getIsReached() ? record.getThresholdNum() : record.getCurrentNum();
      this.denominator = record.getThresholdNum();
      if (null != record.getIsReached()) {
        this.status = record.getIsReached().toString();
      }
    }

    public String getMedalId() {
      return medalId;
    }

    public void setMedalId(String medalId) {
      this.medalId = medalId;
    }

    public String getMedalName() {
      return medalName;
    }

    public void setMedalName(String medalName) {
      this.medalName = medalName;
    }

    public String getMedalImg() {
      return medalImg;
    }

    public void setMedalImg(String medalImg) {
      this.medalImg = medalImg;
    }

    public String getContent() {
      return content;
    }

    public void setContent(String content) {
      this.content = content;
    }

    public String getStatus() {
      return status;
    }

    public void setStatus(String status) {
      this.status = status;
    }

    public String getProgress() {
      double number = Double.valueOf(numerator) / Double.valueOf(denominator);
      if (number > 1) {
        number = 1d;
      }
      return MissionConstant.D_FORMAT.format(number);
    }

    public String getNumerator() {
      return numerator;
    }

    public void setNumerator(String numerator) {
      this.numerator = numerator;
    }

    public String getDenominator() {
      return denominator;
    }

    public void setDenominator(String denominator) {
      this.denominator = denominator;
    }
  }
}
