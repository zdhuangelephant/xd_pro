package com.xiaodou.ms.web.request.user;

import com.xiaodou.ms.web.request.BaseRequest;

public class UserFeedbackEditRequest extends BaseRequest { 
    
    private Integer id;
    
    private Integer handleStatus;
    
    private String handleNote;

    public Integer getId() {
      return id;
    }

    public void setId(Integer id) {
      this.id = id;
    }

    public Integer getHandleStatus() {
      return handleStatus;
    }

    public void setHandleStatus(Integer handleStatus) {
      this.handleStatus = handleStatus;
    }

    public String getHandleNote() {
      return handleNote;
    }

    public void setHandleNote(String handleNote) {
      this.handleNote = handleNote;
    }

   

}
