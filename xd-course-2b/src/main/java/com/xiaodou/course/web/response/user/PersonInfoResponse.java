package com.xiaodou.course.web.response.user;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.apache.commons.lang.StringUtils;

import com.xiaodou.course.web.response.BaseResponse;
import com.xiaodou.course.web.response.resultType.ProductResType;
import com.xiaodou.summer.vo.out.ResultType;

/**
 * @name @see com.xiaodou.course.web.response.user.PersonInfoResponse.java
 * @CopyRright (c) 2018 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2018年7月24日
 * @description 获取用户信息响应类
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PersonInfoResponse extends BaseResponse {
  /**
   * 缓存反序列化构造方法
   */
  public PersonInfoResponse() {}

  public PersonInfoResponse(ResultType resultType) {
    super(resultType);
  }

  public PersonInfoResponse(ProductResType resultType) {
    super(resultType);
  }

  /** learnTime 总学习时长 */
  private String learnTime = StringUtils.EMPTY;
  /** learnDays 连续学习天数 */
  private String learnDays = StringUtils.EMPTY;
  /** otherExamCourseCount 其它考期课程数目 */
  private String otherExamCourseCount = String.valueOf(0);
}
