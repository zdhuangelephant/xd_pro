package com.xiaodou.course.web.request.notes;

import com.xiaodou.course.web.request.BaseRequest;
import com.xiaodou.summer.validator.annotion.NotEmpty;

public class NotesDelRequest extends BaseRequest {
    @NotEmpty
	private String notesId;

	public String getNotesId() {
		return notesId;
	}

	public void setNotesId(String notesId) {
		this.notesId = notesId;
	}
	
}
