package com.xiaodou.server.mapi.response.product;
import com.xiaodou.server.mapi.response.product.ChapterCardListResponse.AudioInfo;
import com.xiaodou.server.mapi.response.selftaught.BaseResponse;
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

  private AudioInfo audioInfo;

  public AudioInfo getAudioInfo() {
    return audioInfo;
  }

  public void setAudioInfo(AudioInfo audioInfo) {
    this.audioInfo = audioInfo;
  }

}
