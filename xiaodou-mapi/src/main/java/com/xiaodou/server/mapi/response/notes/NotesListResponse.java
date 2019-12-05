package com.xiaodou.server.mapi.response.notes;

import java.util.List;

import com.google.common.collect.Lists;
import com.xiaodou.server.mapi.domain.NotesModel;
import com.xiaodou.server.mapi.response.selftaught.BaseResponse;
import com.xiaodou.summer.vo.out.ResultType;

public class NotesListResponse extends BaseResponse {
  public NotesListResponse() {}

  public NotesListResponse(ResultType type) {
    super(type);
  }

  private List<NotesModel> notes = Lists.newArrayList();

  public List<NotesModel> getNotes() {
    return notes;
  }

  public void setNotes(List<NotesModel> notes) {
    this.notes = notes;
  }
}
