package com.xiaodou.ucenter.domain.request;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.xiaodou.summer.validator.annotion.NotEmpty;
import com.xiaodou.summer.vo.in.BaseValidatorPojo;

@Data
@EqualsAndHashCode(callSuper = false)
public class FindUserRequest extends BaseValidatorPojo {
  @NotEmpty
  private List<String> xdUniqueIdList;

}
