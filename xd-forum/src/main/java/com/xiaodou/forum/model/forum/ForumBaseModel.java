package com.xiaodou.forum.model.forum;
/**
 * 话题基础model
 * @author bing.cheng
 *
 */
public class ForumBaseModel {
	
    /**操作人*/
    public String operator;

    /**操作人ip*/
    public String operatorip;

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getOperatorip() {
		return operatorip;
	}

	public void setOperatorip(String operatorip) {
		this.operatorip = operatorip;
	}
    
}
