package com.xiaodou.ucenter.domain.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.xiaodou.summer.validator.annotion.NotEmpty;
import com.xiaodou.summer.vo.in.BaseValidatorPojo;

@Data
@EqualsAndHashCode(callSuper = false)
public class UserByTelAndModRequest extends BaseValidatorPojo {
  @NotEmpty
  private String telephone;
}
