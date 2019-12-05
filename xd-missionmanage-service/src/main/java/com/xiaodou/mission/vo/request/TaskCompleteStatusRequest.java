package com.xiaodou.mission.vo.request;

import java.util.Map;

import com.alibaba.fastjson.TypeReference;
import com.google.common.collect.Maps;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.summer.validator.annotion.NotEmpty;


/**
 * @name @see com.xiaodou.mission.vo.request.TaskStatusRequest.java
 * @CopyRright (c) 2016 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年6月29日
 * @description 任务状态请求pojo
 * @version 1.0
 */
public class TaskCompleteStatusRequest extends BaseRequest {
  /** courseId 课程ID */
  // @NotEmpty
  // private String courseId;
  @NotEmpty
  private Long beginTime;
  @NotEmpty
  private Long endTime;
  @NotEmpty
  private String majorId;
  /** courseMap 课程字典 */
  @NotEmpty
  private String courseMap;
  private Map<String, String> mCourseMap;

  public Map<String, String> getMCourseMap() {
    if (null == mCourseMap) {
      if (StringUtils.isJsonBlank(courseMap)) {
        mCourseMap = Maps.newHashMap();
      } else {
        mCourseMap = FastJsonUtil.fromJsons(courseMap, new TypeReference<Map<String, String>>() {});
      }
    }
    return mCourseMap;
  }



  public String getCourseMap() {
    return courseMap;
  }

  public void setCourseMap(String courseMap) {
    this.courseMap = courseMap;
  }



  public Long getBeginTime() {
    return beginTime;
  }



  public void setBeginTime(Long beginTime) {
    this.beginTime = beginTime;
  }



  public Long getEndTime() {
    return endTime;
  }



  public void setEndTime(Long endTime) {
    this.endTime = endTime;
  }



  public Map<String, String> getmCourseMap() {
    return mCourseMap;
  }



  public void setmCourseMap(Map<String, String> mCourseMap) {
    this.mCourseMap = mCourseMap;
  }



  public String getMajorId() {
    return majorId;
  }

  public void setMajorId(String majorId) {
    this.majorId = majorId;
  }

}
