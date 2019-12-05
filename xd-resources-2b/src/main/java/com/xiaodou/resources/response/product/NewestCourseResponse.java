package com.xiaodou.resources.response.product;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.google.common.collect.Lists;
import com.xiaodou.resources.response.BaseResponse;
import com.xiaodou.resources.response.resultype.ProductResType;
import com.xiaodou.resources.vo.product.CourseInfo;
import com.xiaodou.resources.vo.product.ResourceVo;
import com.xiaodou.summer.vo.out.ResultType;

/**
 * @name NewestCourseResponse CopyRright (c) 2016 by zhaodan
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年10月24日
 * @description 课程最新信息响应
 * @version 1.0
 */
public class NewestCourseResponse extends BaseResponse {
  public NewestCourseResponse() {}

  public NewestCourseResponse(ResultType resultType) {
    super(resultType);
  }

  public NewestCourseResponse(ProductResType resultType) {
    super(resultType);
  }

  private CourseInfo courseInfo;
  /** latestList 最新资源列表 */
  private List<NewestResource> latestList = Lists.newArrayList();

  public List<NewestResource> getLatestList() {
    return latestList;
  }

  public void setLatestList(List<NewestResource> latestList) {
    this.latestList = latestList;
  }

  public CourseInfo getCourseInfo() {
    return courseInfo;
  }

  public void setCourseInfo(CourseInfo courseInfo) {
    this.courseInfo = courseInfo;
  }

  /**
   * @name NewestResource CopyRright (c) 2016 by zhaodan
   * 
   * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
   * @date 2016年10月24日
   * @description 最新资源模型
   * @version 1.0
   */
  public static class NewestResource {
    /** time 时间点 */
    private String time = StringUtils.EMPTY;
    /** resourceList 资源列表 */
    private List<ResourceVo> resourceList = Lists.newArrayList();

    public String getTime() {
      return time;
    }

    public void setTime(String time) {
      this.time = time;
    }

    public List<ResourceVo> getResourceList() {
      return resourceList;
    }

    public void setResourceList(List<ResourceVo> resourceList) {
      this.resourceList = resourceList;
    }
  }
}
