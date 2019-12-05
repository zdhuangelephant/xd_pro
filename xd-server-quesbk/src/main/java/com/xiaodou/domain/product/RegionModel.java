package com.xiaodou.domain.product;

import java.sql.Timestamp;

import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.autobuild.tool.MybatisXmlTool;

import lombok.Data;

/**
 * @name RegionModel CopyRright (c) 2018 by <a
 *       href=mailto:zhuweijin@corp.51xiaodou.com>zhuweijin</a>
 * 
 * @author <a href=mailto:zhuweijin@corp.51xiaodou.com>zhuweijin</a>
 * @date 2018年4月12日
 * @description 地域模块，之前的module指的是产品模块，已经废弃
 * @version 1.0
 */
@Data
public class RegionModel {

	@Column(isMajor = true)
	private Long id;

	private String ruleId;

	private String module;

	private String moduleName;

	private Integer listOrder;

	private String detail;

	private Integer isFirstChoice;

	private Timestamp createTime;

	public static void main(String[] args) {
		MybatisXmlTool
				.getInstance(
						RegionModel.class,
						"xd_module_info",
						"D:/MyWorkSpace/MyEclipse8.5/xd-server-quesbk/src/main/resources/conf/mybatis/product")
				.buildXml();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRuleId() {
		return ruleId;
	}

	public void setRuleId(String ruleId) {
		this.ruleId = ruleId;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public Integer getListOrder() {
		return listOrder;
	}

	public void setListOrder(Integer listOrder) {
		this.listOrder = listOrder;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public Integer getIsFirstChoice() {
		return isFirstChoice;
	}

	public void setIsFirstChoice(Integer isFirstChoice) {
		this.isFirstChoice = isFirstChoice;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

}
