package com.xiaodou.ms.web.request.knowledge;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.xiaodou.ms.model.knowledge.AuthorModel;
import com.xiaodou.ms.web.request.BaseRequest;
import com.xiaodou.summer.validator.annotion.NotEmpty;

@Data
@EqualsAndHashCode(callSuper = true)
public class AuthorAddRequest extends BaseRequest {

  @NotEmpty
  private String portrait;
  @NotEmpty
  private String name;
  @NotEmpty
  private String info;

  public AuthorModel initModel() {
    AuthorModel model = new AuthorModel();
    model.setPortrait(portrait);
    model.setName(name);
    model.setInfo(info);
    return model;
  }

}
