package com.xiaodou.st.dataclean.model.domain.raw;

import java.sql.Timestamp;

import lombok.Data;

import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.autobuild.tool.MybatisXmlTool;

/**
 * @name @see com.xiaodou.ms.model.product.ProductCategoryRelation.java
 * @CopyRright (c) 2016 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年5月20日
 * @description 原始数据:产品分类与产品关系模型
 * @version 1.0
 */
@Data
public class RawDataProductRelationModel {

	/** id 主键ID */
	@Column(isMajor = true)
	private Long id;
	/** productCategoryId 产品分类ID */
	private Long productCategoryId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getProductCategoryId() {
		return productCategoryId;
	}

	public void setProductCategoryId(Long productCategoryId) {
		this.productCategoryId = productCategoryId;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Integer getRelationState() {
		return relationState;
	}

	public void setRelationState(Integer relationState) {
		this.relationState = relationState;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	/** productId 产品ID */
	private Long productId;
	/** relationState 关系状态 */
	private Integer relationState;
	/** createTime 创建时间 */
	@Column(betweenScope = true, canUpdate = false)
	private Timestamp createTime;

	public static void main(String[] args) {
		MybatisXmlTool.getInstance(RawDataProductRelationModel.class,
				"xd_raw_data_product_relation",
				"src/main/resources/conf/mybatis/raw/").buildXml();
	}
}
