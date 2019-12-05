package com.xiaodou.server.mapi.request.notes;

import com.xiaodou.server.mapi.request.MapiBaseRequest;
import com.xiaodou.summer.validator.annotion.NotEmpty;

public class NotesDelRequest extends  MapiBaseRequest {
    @NotEmpty
	private String notesId;

	public String getNotesId() {
		return notesId;
	}

	public void setNotesId(String notesId) {
		this.notesId = notesId;
	}


	
}
