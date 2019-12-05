package com.xiaodou.course.web.response.product;
import com.xiaodou.course.vo.productItem.AudioInfo;
import com.xiaodou.course.web.response.BaseResponse;
import com.xiaodou.course.web.response.resultType.ProductResType;
import com.xiaodou.summer.vo.out.ResultType;

/**
 * @name @see com.xiaodou.course.web.response.product.ChapterAudioResponse.java
 * @CopyRright (c) 2018 by XiaoDou NetWork Technology
 *
 * @author <a href="mailto:lidehong@corp.51xiaodou.com">lidehong</a>
 * @date 2018年5月4日
 * @description TODO
 * @version 1.0
 */
public class ChapterAudioResponse extends BaseResponse{
  public ChapterAudioResponse() {}
  
  public ChapterAudioResponse(ResultType type) {
    super(type);
  }

  public ChapterAudioResponse(ProductResType productResType) {
    super(productResType);
  }
  private AudioInfo audioInfo;

  public AudioInfo getAudioInfo() {
    return audioInfo;
  }

  public void setAudioInfo(AudioInfo audioInfo) {
    this.audioInfo = audioInfo;
  }

}
