/**
 * @Title: UpdateBuyAccountIdByGorderIdRequest.java
 * @Package com.xiaodou.oms.agent.vo.request
 * @Description: TODO
 * Copyright: Copyright (c) 2014 
 * Email: songbin0819@163.com
 * 
 * @author bin.song
 * @date 2014年12月25日 下午3:13:12
 * @version V1.0
 */
package com.xiaodou.oms.agent.vo.request;

import com.xiaodou.oms.agent.model.Gorder;

/**
 * UpdateBuyAccountIdByGorderIdRequest
 * @Title: UpdateBuyAccountIdByGorderIdRequest
 * @Description: TODO
 * @author bin.song
 * @date 2014年12月25日 下午3:13:12
 *
 */
public class UpdateBuyAccountIdByGorderIdRequest extends BaseRequest {

	Gorder gorder;

	/**
	 * getter method for gorder
	 * @return the gorder
	 */
	public Gorder getGorder() {
		return gorder;
	}

	/**
	 * setter method for gorder
	 * @Description the gorder to set
	 * @param gorder 
	 */
	public void setGorder(Gorder gorder) {
		this.gorder = gorder;
	}	
	
}
