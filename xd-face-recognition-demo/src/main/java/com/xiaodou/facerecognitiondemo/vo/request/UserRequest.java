package com.xiaodou.facerecognitiondemo.vo.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.xiaodou.summer.vo.in.BaseValidatorPojo;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserRequest extends BaseValidatorPojo {

  private String uid;
  private String faceUrl;

}
