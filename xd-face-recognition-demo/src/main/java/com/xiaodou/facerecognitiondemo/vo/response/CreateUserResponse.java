package com.xiaodou.facerecognitiondemo.vo.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.xiaodou.common.util.CommUtil;
import com.xiaodou.facerecognitiondemo.enums.UserFaceResType;
import com.xiaodou.summer.vo.out.ResultInfo;
import com.xiaodou.summer.vo.out.ResultType;

@Data
@EqualsAndHashCode(callSuper = true)
public class CreateUserResponse extends ResultInfo {
  public CreateUserResponse(UserFaceResType type) {
    setRetcode(type.getCode());
    setRetdesc(type.getDesc());
    setServerIp(CommUtil.getServerIp());
  }

  public CreateUserResponse(ResultType type) {
    super(type);
  }

  private String uid;
}
