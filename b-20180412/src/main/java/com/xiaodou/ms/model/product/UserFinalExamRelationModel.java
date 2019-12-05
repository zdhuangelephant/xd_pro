package com.xiaodou.ms.model.product;

import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.autobuild.tool.MybatisXmlTool;

import lombok.Data;

@Data
public class UserFinalExamRelationModel {
	/** id 主键ID */
	@Column(isMajor = true)
	private String id;
	/** finalExamId 试卷id */
	private String finalExamId;
	/** paperNo  */
	private String paperNo;
	/** userId 关联用户的id */
	private Integer userId;
	
	public static void main(String[] args) {
		MybatisXmlTool.getInstance(UserFinalExamRelationModel.class, "xd_user_final_exam",
				"D:/snippets/eclipseWorks/xiaodou-ms-2b/src/main/resources/conf/mybatis/").buildXml();
	}
}
