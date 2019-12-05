package com.xiaodou.server.mapi.request.quesbk;

import com.xiaodou.server.mapi.request.MapiBaseRequest;
import com.xiaodou.summer.validator.annotion.NotEmpty;

public class PkResultRequest extends MapiBaseRequest{

	/**
	 *  PK记录ID
	 * 
	 */
	@NotEmpty
	private String recordId;

	public String getRecordId() {
		return recordId;
	}

	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}


}
