package com.xiaodou.server.mapi.response.selftaught;

import java.util.List;

import com.google.common.collect.Lists;
import com.xiaodou.summer.vo.out.ResultType;

public class MedalListResponse extends BaseResponse {
  public MedalListResponse() {}

  public MedalListResponse(ResultType type) {
    super(type);
  }

  private List<MedalDetail> medalList = Lists.newArrayList();

  public List<MedalDetail> getMedalList() {
    return medalList;
  }

  public void setMedalList(List<MedalDetail> medalList) {
    this.medalList = medalList;
  }


  public static class MedalDetail {
    private String medalId;// 章名称
    private String medalName; // 章ID
    private String medalImg; // 资源ID
    private String content; // 资源地址
    private String isUsed;// 唯一一个能使用。 0 不使用 1使用
    private String status; // 0表示未达成，1表示已达成
    private String progress; // 进度80%
    private String numerator;// 已经添加了6个好友
    private String denominator;// 添加15个好友达成此成就

    public MedalDetail() {}

    public MedalDetail(String medalId, String medalName, String medalImg, String content,
        String isUsed, String status, String progress, String numerator, String denominator) {
      this.medalId = medalId;
      this.medalName = medalName;
      this.medalImg = medalImg;
      this.content = content;
      this.isUsed = isUsed;
      this.status = status;
      this.progress = progress;
      this.numerator = numerator;
      this.denominator = denominator;
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

    public String getIsUsed() {
      return isUsed;
    }

    public void setIsUsed(String isUsed) {
      this.isUsed = isUsed;
    }

    public String getStatus() {
      return status;
    }

    public void setStatus(String status) {
      this.status = status;
    }

    public String getProgress() {
      return progress;
    }

    public void setProgress(String progress) {
      this.progress = progress;
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
