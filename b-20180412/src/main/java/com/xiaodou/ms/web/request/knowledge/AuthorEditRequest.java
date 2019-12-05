package com.xiaodou.ms.web.request.knowledge;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.xiaodou.ms.model.knowledge.AuthorModel;
import com.xiaodou.ms.web.request.BaseRequest;
import com.xiaodou.summer.validator.annotion.NotEmpty;

@Data
@EqualsAndHashCode(callSuper = true)
public class AuthorEditRequest extends BaseRequest {

  @NotEmpty
  private Long id;
  private String portrait;
  private String name;
  private String info;

  public AuthorModel initModel() {
    AuthorModel model = new AuthorModel();
    model.setId(id);
    model.setPortrait(portrait);
    model.setName(name);
    model.setInfo(info);
    return model;
  }

public Long getId() {
	return id;
}

public void setId(Long id) {
	this.id = id;
}

public String getPortrait() {
	return portrait;
}

public void setPortrait(String portrait) {
	this.portrait = portrait;
}

public String getName() {
	return name;
}

public void setName(String name) {
	this.name = name;
}

public String getInfo() {
	return info;
}

public void setInfo(String info) {
	this.info = info;
}

}
