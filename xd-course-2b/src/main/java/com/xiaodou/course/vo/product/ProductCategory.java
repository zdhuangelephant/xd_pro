package com.xiaodou.course.vo.product;

import com.xiaodou.course.model.product.ProductCategoryModel;

import lombok.Data;

@Data
public class ProductCategory {

	// 主键
	private String productCategoryId;

	// 名称
	private String name;

	// 描述
	private String detail;

	private String misc;
	// 分类（专业代码）
	private String typeCode;

	// 模块名
	private String moduleName;

	/* 专业层次(eg:专科) */
	private String majorLevel;
	/* 主考院校 */
	private String chiefAcademy;
	/* 学位（eg:文学学士） */
	private String degree;

	private String showCover;

	private String classify;

	public ProductCategory(ProductCategoryModel p) {
		this.productCategoryId = p.getId().toString();
		this.name = p.getName();
		this.detail = p.getDetail();
		this.misc = p.getMisc();
		this.typeCode = p.getTypeCode();
		this.moduleName = p.getModuleName();
		this.majorLevel = p.getMajorLevel();
		this.chiefAcademy = p.getChiefAcademy();
		this.degree = p.getDegree();
		this.showCover = p.getShowCover();
		this.classify = p.getClassify();
	}

	public String getProductCategoryId() {
		return productCategoryId;
	}

	public void setProductCategoryId(String productCategoryId) {
		this.productCategoryId = productCategoryId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getMisc() {
		return misc;
	}

	public void setMisc(String misc) {
		this.misc = misc;
	}

	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getMajorLevel() {
		return majorLevel;
	}

	public void setMajorLevel(String majorLevel) {
		this.majorLevel = majorLevel;
	}

	public String getChiefAcademy() {
		return chiefAcademy;
	}

	public void setChiefAcademy(String chiefAcademy) {
		this.chiefAcademy = chiefAcademy;
	}

	public String getDegree() {
		return degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

	public String getShowCover() {
		return showCover;
	}

	public void setShowCover(String showCover) {
		this.showCover = showCover;
	}

	public String getClassify() {
		return classify;
	}

	public void setClassify(String classify) {
		this.classify = classify;
	}

}
