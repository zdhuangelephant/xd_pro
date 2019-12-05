package com.xiaodou.vo.mq;

import java.util.List;

import lombok.Data;

import org.springframework.util.Assert;

import com.alibaba.fastjson.TypeReference;
import com.google.common.collect.Lists;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.dataclean.core.event.DCCoreEvent;
import com.xiaodou.domain.behavior.UserExamTotal;
import com.xiaodou.domain.behavior.UserExamTotal.ChapterScore;
import com.xiaodou.domain.behavior.UserScorePointRecord;
import com.xiaodou.domain.product.FinalExamModel;
import com.xiaodou.summer.validator.annotion.NotEmpty;
import com.xiaodou.vo.mq.UserExamTotalEvent.TransferExamTotal;

/**
 * @name UserExamTotalEvent
 * @CopyRright (c) 2017 by zhaodan
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年3月28日
 * @description 用户刷新得分事件
 * @version 1.0
 */
public class UserExamTotalEvent extends DCCoreEvent<TransferExamTotal> {
  private static final String EVENT_NAME = "userExamTotalEvent";

  public UserExamTotalEvent() {
    setEventName(EVENT_NAME);
  }

  /**
   * @name @see com.xiaodou.vo.mq.UserExamTotalEvent.java
   * @CopyRright (c) 2017 by Corp.XiaodouTech
   * 
   * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
   * @date 2017年3月31日
   * @description 传输参数类
   * @version 1.0
   */
  @Data
  public static class TransferExamTotal {
    public TransferExamTotal() {}

    public TransferExamTotal(String typeCode, UserExamTotal model,
        List<UserScorePointRecord> userScorePointRecordList, List<FinalExamModel> finalList) {
      Assert.isTrue(StringUtils.isNotBlank(typeCode), "typeCode不能为空");
      Assert.isTrue(null != model, "userExamTotalModel不能为空");
      this.userId = model.getUserId();
      this.module = model.getModule();
      this.typeCode = typeCode;
      this.courseId = model.getCourseId();
      this.totalQues = model.getTotalQues();
      this.totalRank = model.getTotalRank();
      this.rightQues = model.getRightQues();
      this.rightRank = model.getRightRank();
      this.rightPercent = model.getRightPercent();
      this.score = model.getScore();

      if (StringUtils.isJsonNotBlank(model.getChapterScore())) {
        List<ChapterScore> chapterScoreList =
            FastJsonUtil.fromJsons(model.getChapterScore(),
                new TypeReference<List<ChapterScore>>() {});
        if (null != chapterScoreList && chapterScoreList.size() > 0) {
          for (ChapterScore score : chapterScoreList) {
            ChapterNode node = new ChapterNode();
            node.indexName = score.getChapterIndex();
            node.score = score.getChapterSummaryScore();
            node.weight = score.getWeight();
            node.order = score.getListOrder();
            this.chapterNodeList.add(node);
          }
        }
      }

      if (null != finalList && finalList.size() > 0) {
        for (FinalExamModel finalExam : finalList) {
          FinalExamNode node = new FinalExamNode();
          node.indexName = finalExam.getExamName();
          node.score = finalExam.getScore();
          node.order = finalExam.getSort();
        }
      }
      this.userScorePointRecordList = userScorePointRecordList;
    }

    /** userId 用户ID */
    @NotEmpty
    private String userId;
    /** module 模块ID */
    @NotEmpty
    private String module;
    /** typeCode 专业码值 */
    @NotEmpty
    private String typeCode;
    /** courseId 产品ID */
    @NotEmpty
    private Long courseId;
    /** totalQues 总答题数 */
    @NotEmpty
    private Integer totalQues;
    /** totalRank */
    @NotEmpty
    private Integer totalRank;
    /** rightQues */
    @NotEmpty
    private Integer rightQues;
    /** rightRank */
    @NotEmpty
    private Integer rightRank;
    /** score */
    @NotEmpty
    private Double score;
    /** rightPercent */
    @NotEmpty
    private String rightPercent;

    /* 章 */
    private List<ChapterNode> chapterNodeList = Lists.newArrayList();
    /* 卷子 */
    private List<FinalExamNode> finalExamNodeList = Lists.newArrayList();
    /** userScorePointRecordList 用户计分点成绩记录 */
    private List<UserScorePointRecord> userScorePointRecordList = Lists.newArrayList();

    @Data
    public static class ChapterNode {
      // 名称内容
      private String indexName;
      // 權重
      private Double weight;
      // 排序
      private Long order;
      // 得分
      private Double score;
	public String getIndexName() {
		return indexName;
	}
	public void setIndexName(String indexName) {
		this.indexName = indexName;
	}
	public Double getWeight() {
		return weight;
	}
	public void setWeight(Double weight) {
		this.weight = weight;
	}
	public Long getOrder() {
		return order;
	}
	public void setOrder(Long order) {
		this.order = order;
	}
	public Double getScore() {
		return score;
	}
	public void setScore(Double score) {
		this.score = score;
	}
      
    }

    @Data
    public static class FinalExamNode {
      // 名称内容
      private String indexName;
      // 權重
      private Double weight;
      // 排序
      private Integer order;
      // 得分
      private String score;
	public String getIndexName() {
		return indexName;
	}
	public void setIndexName(String indexName) {
		this.indexName = indexName;
	}
	public Double getWeight() {
		return weight;
	}
	public void setWeight(Double weight) {
		this.weight = weight;
	}
	public Integer getOrder() {
		return order;
	}
	public void setOrder(Integer order) {
		this.order = order;
	}
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
      
    }
  }
}
