package com.xiaodou.server.mapi.request.ucenter;

import com.xiaodou.server.mapi.request.MapiBaseRequest;
import com.xiaodou.summer.validator.annotion.AddValidField;
import com.xiaodou.summer.validator.annotion.NotEmpty;
import com.xiaodou.summer.validator.enums.AnnotationType;

@AddValidField(annotiation = AnnotationType.NotEmpty, field = {"sessionToken"})
public class ModifyPwdRequest extends MapiBaseRequest {


  @NotEmpty
  private String OldPwd;

  @NotEmpty
  private String NewPwd;

  @NotEmpty
  private String confirmNewPwd;

  public String getOldPwd() {
    return OldPwd;
  }

  public void setOldPwd(String oldPwd) {
    OldPwd = oldPwd;
  }

  public String getNewPwd() {
    return NewPwd;
  }

  public void setNewPwd(String newPwd) {
    NewPwd = newPwd;
  }

  public String getConfirmNewPwd() {
    return confirmNewPwd;
  }

  public void setConfirmNewPwd(String confirmNewPwd) {
    this.confirmNewPwd = confirmNewPwd;
  }

}
