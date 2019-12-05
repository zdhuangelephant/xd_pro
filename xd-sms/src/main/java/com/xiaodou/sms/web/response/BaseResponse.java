package com.xiaodou.sms.web.response;

import com.xiaodou.sms.common.enums.ResultType;
/**
 * 
 * @author weichao.zhai
 * @time 2015年4月6日 下午4:52:13
 */
public class BaseResponse {
	public static final String IS_ERROR = "true";
	public static final String NO_ERROR = "false";

	/**
	 * 是否错误()
	 */
	private String isError;

	/**
	 * 如果错误，返回错误结果描述
	 */
	private String message;

	/**
	 * 如果错误，返回错误代码（返回错误码）
	 */
	private Integer code;

	//时间戳
	private Long timestamp = System.currentTimeMillis();

	public Long getTimestamp() {
		return timestamp;
	}

	public String getIsError() {
		return isError;
	}

	public void setIsError(String isError) {
		this.isError = isError;
	}

	public String getErrorMessage() {
		return message;
	}

	public void setErrorMessage(String errorMessage) {
		this.message = errorMessage;
	}

	public Integer getErrorCode() {
		return code;
	}

	public void setErrorCode(Integer errorCode) {
		this.code = errorCode;
		if (ResultType.SYSFAIL.getCode().equals(this.code)) {
			this.isError = "true";
		} else {
			this.isError = "false";
		}
	}

	public BaseResponse(ResultType type) {
		this.code = type.getCode();
		this.message = type.getMsg();
		if (ResultType.SYSFAIL.getCode().equals(this.code)) {
			this.isError = "true";
		} else {
			this.isError = "false";
		}
	}

	public BaseResponse() {
	}

	public void appendErrorMessage(String errorMsg) {
		this.message += errorMsg;
	}

}
