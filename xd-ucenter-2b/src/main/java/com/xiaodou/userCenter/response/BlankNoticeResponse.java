package com.xiaodou.userCenter.response;

import com.xiaodou.common.util.StringUtils;
import com.xiaodou.summer.vo.out.ResultType;
import com.xiaodou.userCenter.model.BlankNoticeModel;
import com.xiaodou.userCenter.response.resultype.UcenterResType;

import lombok.Data;
import lombok.EqualsAndHashCode;
@Data
@EqualsAndHashCode(callSuper=false)
public class BlankNoticeResponse extends BaseResultInfo {
  public BlankNoticeResponse(UcenterResType type) {
    super(type);
  }

  public BlankNoticeResponse(ResultType type) {
    super(type);
  }

  /** type  显示频次 0:每日首次， 1：每次启动 2：一次性*/
  private Short type = 1;
  /** jumpUrl 跳转地址 http:// app:// */
  private String jumpUrl = StringUtils.EMPTY;
  /** title 开屏通知标题 */
  private String title = StringUtils.EMPTY;
  /** image 展示地址 http:// */
  private String image = StringUtils.EMPTY;
  /** isVisible 是否显示 */
  private Short isVisible = 1;

  public BlankNoticeResponse setBlankNoticeResponse(BlankNoticeModel model) {
    BlankNoticeResponse response = new BlankNoticeResponse(ResultType.SUCCESS);
    response.setJumpUrl(model.getJumpUrl());
    response.setType(model.getType());
    response.setTitle(model.getTitle());
    response.setImage(model.getImage());
    response.setIsVisible(model.getIsVisible());
    return response;
  }
 
}
