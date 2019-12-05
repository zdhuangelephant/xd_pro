package com.xiaodou.oms.entity.order;

import com.xiaodou.oms.vo.input.sign.BasePojo;

/**    
 * Gorder订单更新Pojo
 * @ClassName UpdateGorderPojo   
 * @author Xiaodong.Fan   
 * @date 2015年1月29日 下午4:26:03       
 */ 
public class UpdateGorderPojo extends BasePojo{
	
	private Integer gorderId;
	
	private String uid;
	
	private Gorder gorder;

	public Integer getGorderId() {
		return gorderId;
	}

	public void setGorderId(Integer gorderId) {
		this.gorderId = gorderId;
	}

	public Gorder getGorder() {
		return gorder;
	}

	public void setGorder(Gorder gorder) {
		this.gorder = gorder;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

}
