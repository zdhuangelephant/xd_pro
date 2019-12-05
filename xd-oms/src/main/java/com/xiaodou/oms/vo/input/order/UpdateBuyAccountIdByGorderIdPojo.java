/**
 * @Title: UpdateBuyAccountIdByGorderIdPojo.java
 * @Package com.elong.oms.vo.input.order
 * @Description: TODO
 * Copyright: Copyright (c) 2014 
 * Email: songbin0819@163.com
 * 
 * @author bin.song
 * @date 2014年12月25日 下午3:37:35
 * @version V1.0
 */
package com.xiaodou.oms.vo.input.order;

import com.xiaodou.oms.entity.order.Gorder;
import com.xiaodou.oms.vo.input.sign.BasePojo;
import com.xiaodou.summer.validator.annotion.NotEmpty;

/**
 * UpdateBuyAccountIdByGorderIdPojo
 * @Title: UpdateBuyAccountIdByGorderIdPojo
 * @Description: TODO
 * @author bin.song
 * @date 2014年12月25日 下午3:37:35
 *
 */
public class UpdateBuyAccountIdByGorderIdPojo extends BasePojo {

    /**
     * gorder对象
     */
  @NotEmpty
  private Gorder gorder;

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
