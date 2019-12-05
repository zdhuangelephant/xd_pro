/**
 * @Title: BaseResponse.java
 * @Package com.elong.oms.vo
 * @Description: TODO
 * Copyright: Copyright (c) 2014 
 * Email: songbin0819@163.com
 * 
 * @author bin.song
 * @date 2014年12月25日 下午4:15:25
 * @version V1.0
 */
package com.xiaodou.oms.vo;

/**
 * BaseResponse
 * @Title: BaseResponse
 * @Description: TODO
 * @author bin.song
 * @date 2014年12月25日 下午4:15:25
 *
 */
public class UpdateBuyAccountIdByGorderIdResponse {

	private int retcode;
	private String retdesc;
	/**
	 * getter method for retcode
	 * @return the retcode
	 */
	public int getRetcode() {
		return retcode;
	}
	/**
	 * setter method for retcode
	 * @Description the retcode to set
	 * @param retcode 
	 */
	public void setRetcode(int retcode) {
		this.retcode = retcode;
	}
	/**
	 * getter method for retdesc
	 * @return the retdesc
	 */
	public String getRetdesc() {
		return retdesc;
	}
	/**
	 * setter method for retdesc
	 * @Description the retdesc to set
	 * @param retdesc 
	 */
	public void setRetdesc(String retdesc) {
		this.retdesc = retdesc;
	}
}
