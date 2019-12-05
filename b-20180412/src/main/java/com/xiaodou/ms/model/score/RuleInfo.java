package com.xiaodou.ms.model.score;

import lombok.Data;

@Data
public class RuleInfo {
	/** code 类型码 */
	private Short code;
	/** abtractInfo 概要描述 */
	private String abtractInfo;
	/** moreInfo 详细描述 */
	private String moreInfo;
	/** weight 权重 */
	private Double weight;
	/** order 排序 */
	private Integer order;
	/** INDEX 为了页面设置weight */
	private Integer index;

	@Override
	public String toString() {
		return "RuleInfo [code=" + code + ", abtractInfo=" + abtractInfo
				+ ", moreInfo=" + moreInfo + ", weight=" + weight + ", order="
				+ order + "]";
	}

	public Short getCode() {
		return code;
	}

	public void setCode(Short code) {
		this.code = code;
	}

	public String getAbtractInfo() {
		return abtractInfo;
	}

	public void setAbtractInfo(String abtractInfo) {
		this.abtractInfo = abtractInfo;
	}

	public String getMoreInfo() {
		return moreInfo;
	}

	public void setMoreInfo(String moreInfo) {
		this.moreInfo = moreInfo;
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

}
