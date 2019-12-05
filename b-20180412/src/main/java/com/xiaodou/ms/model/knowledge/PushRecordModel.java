package com.xiaodou.ms.model.knowledge;

import java.sql.Timestamp;

import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.autobuild.tool.MybatisXmlTool;
import com.xiaodou.ms.model.ruide.ActivityModel;

import lombok.Data;

/**
 * @author zdh:
 * @date 2017年7月12日
 */
@Data
public class PushRecordModel {
	@Column(isMajor = true, canUpdate = false)
	private Long id;
	// 1: 2:  3:  4: 
	private Integer typeId;
	private String remark;
	private Integer isPush; // 0:可推送 1:不可推送
	/** createTime 创建时间 */
	private Timestamp createTime;

	public static void main(String[] args) {
		MybatisXmlTool.getInstance(PushRecordModel.class, "xd_push_record",
				"F:/snippet/eclipseWorks/xiaodou-ms-2b/src/main/resources/conf/mybatis/knowledge").buildXml();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getTypeId() {
		return typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getIsPush() {
		return isPush;
	}

	public void setIsPush(Integer isPush) {
		this.isPush = isPush;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	
}
