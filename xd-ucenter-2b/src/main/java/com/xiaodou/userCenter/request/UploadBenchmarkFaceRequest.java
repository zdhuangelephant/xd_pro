package com.xiaodou.userCenter.request;

import com.xiaodou.summer.validator.annotion.AddValidField;
import com.xiaodou.summer.validator.annotion.NotEmpty;
import com.xiaodou.summer.validator.enums.AnnotationType;

/**
 * @name @see com.xiaodou.userCenter.request.UploadBenchmarkFaceRequest.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年5月11日
 * @description 更新基准面容请求类
 * @version 基准面容
 */
@AddValidField(annotiation = AnnotationType.NotEmpty, field = {"sessionToken"})
public class UploadBenchmarkFaceRequest extends BaseRequest {

  /** benchmarkFaceUrl 基准面容url */
  @NotEmpty
  private String benchmarkFaceUrl;

  public String getBenchmarkFaceUrl() {
    return benchmarkFaceUrl;
  }

  public void setBenchmarkFaceUrl(String benchmarkFaceUrl) {
    this.benchmarkFaceUrl = benchmarkFaceUrl;
  }

}
