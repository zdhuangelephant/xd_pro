package com.xiaodou.mission.vo.request;

import java.util.Map;

import com.alibaba.fastjson.TypeReference;
import com.google.common.collect.Maps;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.summer.validator.annotion.NotEmpty;

/**
 * @name @see com.xiaodou.mission.vo.request.UnReachedTaskCountRequest.java
 * @CopyRright (c) 2016 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年7月13日
 * @description 获取未完成任务数量
 * @version 1.0
 */
public class UnReachedTaskCountRequest extends BaseRequest {
  /** majorId 专业ID */
  @NotEmpty
  private String majorId;
  /** courseMap 课程字典 */
  @NotEmpty
  private String courseMap;
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
}
