package com.xiaodou.vo.response;

import java.sql.Timestamp;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
import com.google.common.collect.Lists;
import com.xiaodou.constant.ResultType;

/**
 * @name @see com.xiaodou.vo.response.PkListResponse.java
 * @CopyRright (c) 2016 by XiaoDou NetWork Technology
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年2月26日
 * @description PK记录列表响应
 * @version 1.0
 */
public class PkListResponse extends BaseResponse {

  public PkListResponse(ResultType type) {
    super(type);
  }

  private List<PkAbstractResult> list = Lists.newArrayList();

  public List<PkAbstractResult> getList() {
    return list;
  }

  public void setList(List<PkAbstractResult> list) {
    this.list = list;
  }

  /**
   * @name @see com.xiaodou.vo.response.PkAbstractResult.java
   * @CopyRright (c) 2016 by XiaoDou NetWork Technology
   * 
   * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
   * @date 2016年2月26日
   * @description PK记录主要信息
   * @version 1.0
   */
  public static class PkAbstractResult {
    private String recordId;
    private String userId;
    private String courseId;
    private String courseName;
    private String result;
    private String date;
    @JSONField(serialize = false)
    private String createDate;
    private Timestamp _timeStamp;
    private Integer rightCount;
    private Integer wrongCount;

    public String getRecordId() {
      return recordId;
    }

    public void setRecordId(String recordId) {
      this.recordId = recordId;
    }

    public String getUserId() {
      return userId;
    }

    public void setUserId(String userId) {
      this.userId = userId;
    }

    public String getCourseId() {
      return courseId;
    }

    public void setCourseId(String courseId) {
      this.courseId = courseId;
    }

    public String getCourseName() {
      return courseName;
    }

    public void setCourseName(String courseName) {
      this.courseName = courseName;
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

    public String getCreateDate() {
      return createDate;
    }

    public void setCreateDate(String createDate) {
      this.createDate = createDate;
    }

    public Timestamp get_timeStamp() {
      return _timeStamp;
    }

    public void set_timeStamp(Timestamp _timeStamp) {
      this._timeStamp = _timeStamp;
    }

    public Integer getRightCount() {
      return rightCount;
    }

    public void setRightCount(Integer rightCount) {
      this.rightCount = rightCount;
    }

    public Integer getWrongCount() {
      return wrongCount;
    }

    public void setWrongCount(Integer wrongCount) {
      this.wrongCount = wrongCount;
    }

  }
}
