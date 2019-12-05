package com.xiaodou.server.mapi.response.selftaught;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.summer.vo.out.ResultType;

public class PkListResponse extends BaseResponse {
  public PkListResponse() {}

  public PkListResponse(ResultType type) {
    super(type);
  }

  private List<PkRecord> list = Lists.newArrayList();

  public List<PkRecord> getList() {
    return list;
  }

  public void setList(List<PkRecord> list) {
    this.list = list;
  }

  public static class PkRecord {
    private String recordId = StringUtils.EMPTY;
    private String courseId = StringUtils.EMPTY;
    private String courseName = StringUtils.EMPTY;
    private String userId = StringUtils.EMPTY;
    private String result = StringUtils.EMPTY; // -1 负 0 平 1 胜 5 对方拒绝 2 我方取消 3 等待对方应答
    private String date = StringUtils.EMPTY;
    private String rightCount = StringUtils.EMPTY;
    private String wrongCount = StringUtils.EMPTY;
    private Map<String, Object> userInfo;

    public String getRecordId() {
      return recordId;
    }

    public void setRecordId(String recordId) {
      this.recordId = recordId;
    }

    public String getCourseName() {
      return courseName;
    }

    public void setCourseName(String courseName) {
      this.courseName = courseName;
    }

    public String getCourseId() {
      return courseId;
    }

    public void setCourseId(String courseId) {
      this.courseId = courseId;
    }

    public String getUserId() {
      return userId;
    }

    public void setUserId(String userId) {
      this.userId = userId;
    }

    public String getResult() {
      return result;
    }

    public void setResult(String result) {
      this.result = result;
    }

    public String getDate() {
      return date;
    }

    public void setDate(String date) {
      this.date = date;
    }

    public String getRightCount() {
      return rightCount;
    }

    public void setRightCount(String rightCount) {
      this.rightCount = rightCount;
    }

    public String getWrongCount() {
      return wrongCount;
    }

    public void setWrongCount(String wrongCount) {
      this.wrongCount = wrongCount;
    }

    public Map<String, Object> getUserInfo() {
      return userInfo;
    }

    public void setUserInfo(Map<String, Object> userInfo) {
      this.userInfo = userInfo;
    }

  }
}
