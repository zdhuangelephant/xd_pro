package com.xiaodou.domain.product;

import java.sql.Timestamp;
import java.util.UUID;

import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.autobuild.tool.MybatisXmlTool;
import com.xiaodou.domain.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @name @see com.xiaodou.domain.ChallengeRecord.java
 * @CopyRright (c) 2016 by XiaoDou NetWork Technology
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年2月19日
 * @description 挑战记录
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ChallengeRecord extends BaseEntity {

	/** id 主键ID */
	@Column(isMajor = true, autoIncrement = true, persistent = true, sortBy = true, listValue = true)
	private String id = UUID.randomUUID().toString();
	/** module 所属模块 */
	private String module;
	/** courseId 课程ID */
	private String courseId;
	/** courseName 课程名称 */
	private String courseName;
	/** challengerUid 挑战者ID */
	private String challengerUid;
	/** challengerScore 挑战者成绩 */
	private Double challengerScore;
	/** challengerDetail 挑战者详情 */
	private String challengerDetail;
	/** beChallengerUid 被挑战者ID */
	private String beChallengerUid;
	/** beChallengerScore 被挑战者成绩 */
	private Double beChallengerScore;
	/** beChallengerDetail 被挑战者详情 */
	private String beChallengerDetail;
	/** type 挑战类型 */
	private Short type;
	/** status 状态 */
	private Short status;
	/** winner 胜利者 */
	private String winner;
	/** result 挑战结果 */
	private Short result;
	/** paperId 试卷ID */
	private String paperId;
	/** createTime 记录生成时间 */
	private Timestamp createTime = new Timestamp(System.currentTimeMillis());
	/** updateTime 记录更新时间 */
	private Timestamp updateTime = new Timestamp(System.currentTimeMillis());

	public void setId(String id) {
		refreshUpdateTime();
		this.id = id;
	}

	public void setModule(String module) {
		refreshUpdateTime();
		this.module = module;
	}

	public void setCourseId(String courseId) {
		refreshUpdateTime();
		this.courseId = courseId;
	}

	public void setCourseName(String courseName) {
		refreshUpdateTime();
		this.courseName = courseName;
	}

	public void setChallengerUid(String challengerUid) {
		refreshUpdateTime();
		this.challengerUid = challengerUid;
	}

	public void setChallengerScore(Double challengerScore) {
		refreshUpdateTime();
		this.challengerScore = challengerScore;
	}

	public void setChallengerDetail(String challengerDetail) {
		refreshUpdateTime();
		this.challengerDetail = challengerDetail;
	}

	public void setBeChallengerUid(String beChallengerUid) {
		refreshUpdateTime();
		this.beChallengerUid = beChallengerUid;
	}

	public void setBeChallengerScore(Double beChallengerScore) {
		refreshUpdateTime();
		this.beChallengerScore = beChallengerScore;
	}

	public void setBeChallengerDetail(String beChallengerDetail) {
		refreshUpdateTime();
		this.beChallengerDetail = beChallengerDetail;
	}

	public void setType(Short type) {
		refreshUpdateTime();
		this.type = type;
	}

	public void setStatus(Short status) {
		refreshUpdateTime();
		this.status = status;
	}

	public void setWinner(String winner) {
		refreshUpdateTime();
		this.winner = winner;
	}

	public void setResult(Short result) {
		refreshUpdateTime();
		this.result = result;
	}

	public void setPaperId(String paperId) {
		refreshUpdateTime();
		this.paperId = paperId;
	}

	public void setCreateTime(Timestamp createTime) {
		refreshUpdateTime();
		this.createTime = createTime;
	}

	public void refreshUpdateTime() {
		this.updateTime = new Timestamp(System.currentTimeMillis());
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	public String getId() {
		return id;
	}

	public String getModule() {
		return module;
	}

	public String getCourseId() {
		return courseId;
	}

	public String getCourseName() {
		return courseName;
	}

	public String getChallengerUid() {
		return challengerUid;
	}

	public Double getChallengerScore() {
		return challengerScore;
	}

	public String getChallengerDetail() {
		return challengerDetail;
	}

	public String getBeChallengerUid() {
		return beChallengerUid;
	}

	public Double getBeChallengerScore() {
		return beChallengerScore;
	}

	public String getBeChallengerDetail() {
		return beChallengerDetail;
	}

	public Short getType() {
		return type;
	}

	public Short getStatus() {
		return status;
	}

	public String getWinner() {
		return winner;
	}

	public Short getResult() {
		return result;
	}

	public String getPaperId() {
		return paperId;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public static void main(String[] args) {
		MybatisXmlTool
				.getInstance(
						ChallengeRecord.class,
						"xd_quesbk_challenge_record",
						"F:/xdworks/xd-server-quesbk-b20180102/src/main/resources/conf/mybatis/product/")
				.buildXml();
	}
}
