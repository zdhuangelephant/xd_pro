package com.xiaodou.server.mapi.domain;

public class PkExam{
	  private Integer pkTotalCount=0;
	  private Integer pkSuccessCount=0;
	  private Integer pkFailCount=0;
	  private Integer equalCount=0;

	public Integer getPkSuccessCount() {
		return pkSuccessCount;
	}
	public void setPkSuccessCount(Integer pkSuccessCount) {
		this.pkSuccessCount = pkSuccessCount;
	}
	public Integer getPkFailCount() {
		return pkFailCount;
	}
	public void setPkFailCount(Integer pkFailCount) {
		this.pkFailCount = pkFailCount;
	}
	public Integer getEqualCount() {
		return equalCount;
	}
	public void setEqualCount(Integer equalCount) {
		this.equalCount = equalCount;
	}
	public Integer getPkTotalCount() {
		return pkTotalCount;
	}
	public void setPkTotalCount(Integer pkTotalCount) {
		this.pkTotalCount = pkTotalCount;
	}

 

}
