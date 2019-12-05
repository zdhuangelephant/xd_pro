package com.xiaodou.engine.scorepoint;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;
import com.xiaodou.domain.behavior.UserChapterRecord;
import com.xiaodou.domain.behavior.UserScorePointRecord;
import com.xiaodou.domain.product.CourseProductItem;
import com.xiaodou.manager.facade.QuesOperationFacade;

/**
 * @name @see com.xiaodou.engine.scorepoint.ItemScorePointCaculator.java
 * @CopyRright (c) 2018 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2018年4月16日
 * @description 节练习得分点计算器
 * @version 1.0
 */
public class ItemScorePointCaculator extends BaseScorePointCaculator {

  public ItemScorePointCaculator(UserScorePointRecord record) {
    super(record);
  }

  /** totalItemCount 节总数 */
  private Integer totalItemCount = 0;

  /** completeItemCount 节完成数 */
  private Integer completeItemCount = 0;

  /** totalItemScore 节总分 */
  private Double totalItemScore = 0d;

  /** caculateItemCount 节计算节点数 */
  private Integer caculateItemCount = 0;

  /** totalItemWeight 节总计算权重 */
  private Double totalItemWeight = 1.0d;

  @Override
  public void caculateScore(QuesOperationFacade facade) {
    Map<String, Object> cond = Maps.newHashMap();
    cond.put("userId", getRecord().getUserId());
    cond.put("courseId", getRecord().getProductId());
    List<UserChapterRecord> itemRecordList = facade.queryUserChapterRecord(cond);
    Map<Long, UserChapterRecord> itemRecordMap = buildUserChapterRecordMap(itemRecordList);
    List<CourseProductItem> itemList =
        facade.queryItemList(getRecord().getProductId().toString(), null);
    if (null != itemList && itemList.size() > 0) {
      this.totalItemCount = itemList.size();
      for (CourseProductItem item : itemList) {
        if (null == item || null == item.getParentId() || 0 == item.getParentId()) continue;
        UserChapterRecord itemScore = itemRecordMap.get(item.getId());
        this.caculateItemCount++;
        if (null == itemScore) continue;
        if (itemScore.getScore() >= 60) {
          this.completeItemCount++;
        }
        // 判断节是否有单独权重, 若有, 移除平均计算逻辑, 单独按其权重计算入总成绩, 同时移除其权重
        if (null != item.getWeight() && item.getWeight() > 0) {
          setScore(getScore() + itemScore.getScore() * item.getWeight());
          this.caculateItemCount--;
          this.totalItemWeight -= item.getWeight();
        } else {
          this.totalItemScore += itemScore.getScore();
        }
      }
    }
    setScore(getScore() + caculateItemAvgScore());
    setCompletePercent(caculateItemCompletePercent());
  }

  /**
   * <p>
   * 计算节平均成绩分
   * </p>
   * 节计算节点总分 / 节计算节点个数 * 节计算节点权重
   * 
   * @return itemCaculateScore
   */
  private Double caculateItemAvgScore() {
    if (caculateItemCount == 0) {
      return 0d;
    }
    return totalItemScore / caculateItemCount * totalItemWeight;
  }

  /**
   * 计算节完成度
   * 
   * @return
   */
  private Double caculateItemCompletePercent() {
    if (totalItemCount == 0) {
      return 0d;
    }
    return completeItemCount / totalItemCount * 1d;
  }

  private Map<Long, UserChapterRecord> buildUserChapterRecordMap(
      List<UserChapterRecord> userChapterRecordList) {
    if (null == userChapterRecordList || userChapterRecordList.size() == 0) {
      return null;
    }
    Map<Long, UserChapterRecord> userChapterRecordMap = Maps.newHashMap();
    for (UserChapterRecord record : userChapterRecordList) {
      userChapterRecordMap.put(record.getItemId(), record);
    }
    return userChapterRecordMap;
  }

}
