package com.xiaodou.course.model.user;

import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.autobuild.tool.MybatisXmlTool;

import lombok.Data;

/**
 * @name UserSignStatistic CopyRright (c) 2017 by zhaodan
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年2月10日
 * @description 用户打卡统计记录表
 * @version 1.0
 */
@Data
public class UserSignStatistic {
	/** id 主键ID */
	@Column(isMajor = true, canUpdate = false)
	private Long id;
	/** userId 用户ID */
	@Column(canUpdate = false)
	private String userId;
	/** module 模块号 */
	@Column(canUpdate = false)
	private String module;
	/** totalSign 总打卡时长 */
	private Integer totalSign;
	/** continSign 连续打卡时长 */
	private Integer continSign;
	/** createTime 创建日期 */
	@Column(canUpdate = false)
	private String createTime;
	/** updateTime 更新日期 */
	private String updateTime;

	public static void main(String[] args) {
		MybatisXmlTool
				.getInstance(
						UserSignStatistic.class,
						"xd_user_sign_statistic",
						"/Users/zhaodan/xiaodou/server/WorkSpace/xd-course-2b-b1.1.0/src/main/resources/conf/mybatis/product")
				.buildXml();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public Integer getTotalSign() {
		return totalSign;
	}

	public void setTotalSign(Integer totalSign) {
		this.totalSign = totalSign;
	}

	public Integer getContinSign() {
		return continSign;
	}

	public void setContinSign(Integer continSign) {
		this.continSign = continSign;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

}
