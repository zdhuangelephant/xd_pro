package com.xiaodou.server.mapi.domain;

import java.util.List;

import com.google.common.collect.Lists;

import lombok.Data;
@Data
public class ItemRecord {
	private String itemId;// 节ID
	private String itemName;// 节名称
	private String itemIndex;// 节序号 eg:"第一节"
	private String starLevel; // 星级 0 0颗 1 一星 2 两星 3 三星
	private String score; // 得分
	private List<String> topUserList = Lists.newArrayList();
	private Integer completeCount = 0;
	private String pictureUrl;
	private String itemType = "1";// 0 章 1节 2期末测试 3章总结精讲
	private String status = "0";
	private String deductPoint = "0";
    private String lock="0";//0无锁，1加锁
    /** itemTaskInfo 节下课时任务信息 modify by zdhuang at 2018-6-21 17:15:55*/
    private String progress;
    private String resourceType;    
}
