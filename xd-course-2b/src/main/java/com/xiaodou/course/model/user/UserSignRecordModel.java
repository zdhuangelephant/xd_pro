package com.xiaodou.course.model.user;

import java.sql.Timestamp;

import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.autobuild.tool.MybatisXmlTool;

import lombok.Data;

/**
 * @name UserSignRecordModel CopyRright (c) 2017 by zhaodan
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年2月10日
 * @description 用户打卡记录模型
 * @version 1.0
 */
@Data
public class UserSignRecordModel {
	/** id 主键ID */
	@Column(isMajor = true, canUpdate = false)
	private Long id;
	/** userId 用户ID */
	@Column(canUpdate = false)
	private String userId;
	/** module 模块号 */
	@Column(canUpdate = false)
	private String module;
	/** signTime 打卡时间 */
	@Column(canUpdate = false, betweenScope = true)
	private Timestamp signTime;
	/** signDate 打卡日期 */
	@Column(canUpdate = false)
	private String signDate;
	/** learnTime 学习时长 */
	@Column(canUpdate = false)
	private Long learnTime;
	/** targetTime 目标时长 */
	@Column(canUpdate = false)
	private Long targetTime;

	public static void main(String[] args) {
		MybatisXmlTool
				.getInstance(
						UserSignRecordModel.class,
						"xd_user_sign_record",
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

	public Timestamp getSignTime() {
		return signTime;
	}

	public void setSignTime(Timestamp signTime) {
		this.signTime = signTime;
	}

	public String getSignDate() {
		return signDate;
	}

	public void setSignDate(String signDate) {
		this.signDate = signDate;
	}

	public Long getLearnTime() {
		return learnTime;
	}

	public void setLearnTime(Long learnTime) {
		this.learnTime = learnTime;
	}

	public Long getTargetTime() {
		return targetTime;
	}

	public void setTargetTime(Long targetTime) {
		this.targetTime = targetTime;
	}

}
