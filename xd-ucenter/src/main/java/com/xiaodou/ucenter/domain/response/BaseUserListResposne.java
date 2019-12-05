package com.xiaodou.ucenter.domain.response;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.xiaodou.summer.vo.out.ResultType;
import com.xiaodou.ucenter.domain.response.resultype.UcenterResType;

@Data
@EqualsAndHashCode(callSuper=false)
public class BaseUserListResposne extends BaseResultInfo {

  public BaseUserListResposne() {
    super();
  }

  public BaseUserListResposne(ResultType type) {
    super(type);
  }

  public BaseUserListResposne(UcenterResType resType) {
    super(resType);
  }

  private List<BaseUserInfo> userBaseInfoList;

}
