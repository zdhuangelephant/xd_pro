package com.xiaodou.st.dataclean.model.transport;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.alibaba.fastjson.JSON;
import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.common.util.StringUtils;

/**
 * @name RegionModel CopyRright (c) 2018 by <a
 *       href=mailto:zhuweijin@corp.51xiaodou.com>zhuweijin</a>
 *
 * @author <a href=mailto:zhuweijin@corp.51xiaodou.com>zhuweijin</a>
 * @date 2018年4月12日
 * @description 计分点规则
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TransferScoreRuleData extends BaseTransferModel {

	@Column(isMajor = true)
	private String id;

	private String ruleName;

	private String ruleDetail;

	private String ruleDesc;

	private Integer scope;

	@Column(persistent = false)
	private Map<String, TransferRuleInfoData> ruleInfoMap = new TreeMap<String, TransferRuleInfoData>();

	private Timestamp createTime;

	private Timestamp modifyTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRuleName() {
		return ruleName;
	}

	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}

	public String getRuleDetail() {
		return ruleDetail;
	}

	public void setRuleDetail(String ruleDetail) {
		this.ruleDetail = ruleDetail;
	}

	public String getRuleDesc() {
		return ruleDesc;
	}

	public void setRuleDesc(String ruleDesc) {
		this.ruleDesc = ruleDesc;
	}

	public Integer getScope() {
		return scope;
	}

	public void setScope(Integer scope) {
		this.scope = scope;
	}

	public Map<String, TransferRuleInfoData> getRuleInfoMap() {
		return ruleInfoMap;
	}

	public void setRuleInfoMap(Map<String, TransferRuleInfoData> ruleInfoMap) {
		this.ruleInfoMap = ruleInfoMap;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Timestamp getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Timestamp modifyTime) {
		this.modifyTime = modifyTime;
	}

	public void initModuleInfo(String ruleDetail) {
		if (StringUtils.isJsonNotBlank(ruleDetail)) {
			List<TransferRuleInfoData> mInfo = JSON.parseArray(ruleDetail,
					TransferRuleInfoData.class);
			for (int i = 0; i < mInfo.size(); i++) {
				ruleInfoMap.put(String.valueOf(i), mInfo.get(i));
			}
		}
	}

	@Data
	public static class TransferRuleInfoData {
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

}
