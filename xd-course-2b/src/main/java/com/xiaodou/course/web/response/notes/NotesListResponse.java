package com.xiaodou.course.web.response.notes;

import java.util.List;

import com.google.common.collect.Lists;
import com.xiaodou.course.common.enums.NotesResType;
import com.xiaodou.course.model.notes.NotesModel;
import com.xiaodou.course.web.response.BaseResponse;
import com.xiaodou.summer.vo.out.ResultType;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class NotesListResponse extends BaseResponse {
	public NotesListResponse(ResultType type) {
		super(type);
	}

	public NotesListResponse(NotesResType type) {
		super(type);
	}

	public List<NotesModel> getNotes() {
		return notes;
	}

	public void setNotes(List<NotesModel> notes) {
		this.notes = notes;
	}

	private List<NotesModel> notes = Lists.newArrayList();
}
