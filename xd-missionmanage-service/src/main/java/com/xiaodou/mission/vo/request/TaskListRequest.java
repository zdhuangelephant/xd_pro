package com.xiaodou.mission.vo.request;

import java.util.Map;

import com.alibaba.fastjson.TypeReference;
import com.google.common.collect.Maps;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.summer.validator.annotion.NotEmpty;

/**
 * @name @see com.xiaodou.mission.vo.request.TaskListRequest.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年12月26日
 * @description 任务列表请求类
 * @version 1.0
 */
public class TaskListRequest extends BaseRequest {

  /** majorId 专业ID */
  @NotEmpty
  private String majorId;
  /** courseMap 课程字典 */
  @NotEmpty
  private String courseMap;
  /** surplusDays 剩余天数 */
  @NotEmpty
  private Integer surplusDays = 0;
  /** lastDate 分页任务日期 */
  private Long lastDate;
  private Map<String, String> mCourseMap;

  public String getMajorId() {
    return majorId;
  }

  public void setMajorId(String majorId) {
    this.majorId = majorId;
  }

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

  public Integer getSurplusDays() {
    return surplusDays - 2;
  }

  public void setSurplusDays(Integer surplusDays) {
    this.surplusDays = surplusDays;
  }

  public Long getLastDate() {
    return lastDate;
  }

  public void setLastDate(Long lastDate) {
    this.lastDate = lastDate;
  }

}
