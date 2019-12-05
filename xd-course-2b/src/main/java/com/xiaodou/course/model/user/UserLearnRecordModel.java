package com.xiaodou.course.model.user;

import java.sql.Timestamp;

import lombok.Data;

/**
 * Created by zyp on 15/8/9.
 */
@Data
public class UserLearnRecordModel {

	// id
	private Long id;
	/** typeCode 专业码值 */
	private String typeCode;

	// 用户Id
	private Long userId;

	// 产品Id
	private Long productId;

	// 章节Id
	private Long chapterId;

	// 条目Id
	private Long itemId;

	// 记录时间
	private Timestamp recordTime;

	// 学习时长
	private Integer learnTime;

	// appId
	private Integer moduleId;

	private Short learnType;// 类型（pk(11做题，12解析),闯关（21做题，22解析），31修炼，41错题）

	/** learnContent 学习内容 */
	private String learnContent;// 学习内容（eg:第一章第一节）

	
}
