package com.xiaodou.vo.response;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.annotation.JSONField;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xiaodou.common.util.MathUtil;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.constant.ProductConstants;
import com.xiaodou.constant.QuesBaseConstant;
import com.xiaodou.domain.behavior.UserExamTotal;
import com.xiaodou.domain.behavior.UserScorePointRecord;
import com.xiaodou.domain.product.CourseProduct;
import com.xiaodou.domain.product.CourseProductItem;
import com.xiaodou.domain.product.FinalExamModel;
import com.xiaodou.domain.product.ProductScorePointRule;
import com.xiaodou.domain.product.ProductScorePointRule.RuleInfo;
import com.xiaodou.manager.facade.QuesOperationFacade;
import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.summer.dao.param.IQueryParam;
import com.xiaodou.summer.dao.param.QueryParam;
import com.xiaodou.summer.util.SpringWebContextHolder;

/**
 * @name @see com.xiaodou.domain.RedBonus.java
 * @CopyRright (c) 2016 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年6月18日
 * @description 课程红包
 * @version 1.0
 */
public class CourseScore {
  public CourseScore() {}

  // 此Map用于保存父章节信息，用于后续组织列表时查询使用
  @JSONField(serialize = false, deserialize = false)
  private Map<String, ItemScore> _itemMap = Maps.newHashMap();
  private Map<String, ItemScore> _chapterSummaryMap = Maps.newHashMap();

  public ItemScore getItem(String itemId) {
    return _itemMap.get(itemId);
  }

  public ItemScore getChapterSummaryItem(String itemId) {
    return _chapterSummaryMap.get(itemId);
  }

  public int getItemCount() {
    return _itemMap.size();
  }

  public int getChapterSummaryCount() {
    return _chapterSummaryMap.size();
  }

  /** courseId 课程ID */
  private String courseId;
  /** userId 用户ID */
  private String userId;
  /** chapterScoreList 章分数列表 */
  private List<ChapterScore> chapterScoreList = Lists.newArrayList();
  /** userScorePointRecordMap 计分点明细 */
  private Map<Short, UserScorePointRecord> userScorePointRecordMap = Maps.newHashMap();

  private void buildUserScorePointRecord(List<RuleInfo> ruleInfoList,
      List<UserScorePointRecord> recordList) {
    Map<Short, UserScorePointRecord> recordMap = Maps.newHashMap();
    for (UserScorePointRecord record : recordList) {
      recordMap.put(record.getRuleType(), record);
    }
    for (RuleInfo ruleInfo : ruleInfoList) {
      UserScorePointRecord record = recordMap.get(ruleInfo.getCode());
      if (null == record) {
        record = new UserScorePointRecord();
        record.setRuleType(ruleInfo.getCode());
        recordList.add(record);
      }
      record.setRuleInfo(ruleInfo);
    }
    Collections.sort(recordList, new Comparator<UserScorePointRecord>() {
      @Override
      public int compare(UserScorePointRecord o1, UserScorePointRecord o2) {
        if (null == o1 || null == o1.getRuleInfo() || null == o1.getRuleInfo().getOrder()
            || o1.getRuleInfo().getOrder() == -1) {
          return 1;
        } else if (null == o2 || null == o2.getRuleInfo() || null == o2.getRuleInfo().getOrder()
            || o2.getRuleInfo().getOrder() == -1) {
          return -1;
        } else {
          return o1.getRuleInfo().getOrder() - o2.getRuleInfo().getOrder();
        }
      }
    });
  }

  private Map<Short, UserScorePointRecord> buildUserScorePointRecordMap(
      List<UserScorePointRecord> userScorePointRecordList) {
    Map<Short, UserScorePointRecord> userScorePointRecordMap = Maps.newHashMap();
    if (null != userScorePointRecordList && userScorePointRecordList.size() > 0) {
      for (UserScorePointRecord record : userScorePointRecordList) {
        userScorePointRecordMap.put(record.getRuleType(), record);
      }
    }
    return userScorePointRecordMap;
  }

  public void init(String module, String courseId, String userId, UserExamTotal examTotal,
      String bonusScore) {
    this.courseId = courseId;
    this.userId = userId;
    QuesOperationFacade quesOperationFacade = SpringWebContextHolder.getBean("quesOperationFacade");

    CourseProduct productModel = quesOperationFacade.queryProduct(courseId.toString());
    if (null == productModel || StringUtils.isBlank(productModel.getRuleId())) {
      return;
    }
    ProductScorePointRule rule =
        quesOperationFacade.queryProductScorePointRuleById(productModel.getRuleId());
    if (null == rule || StringUtils.isJsonBlank(rule.getRuleDetail())) {
      return;
    }
    List<RuleInfo> ruleInfoList =
        FastJsonUtil.fromJsons(rule.getRuleDetail(), new TypeReference<List<RuleInfo>>() {});
    IQueryParam param = new QueryParam();
    param.addInput("userId", userId);
    param.addInput("module", module);
    param.addInput("productId", courseId);
    param.addOutputs(UserScorePointRecord.class);
    Page<UserScorePointRecord> userScorePointRecordPage =
        quesOperationFacade.queryUserScorePointRecord(param);
    buildUserScorePointRecord(ruleInfoList, userScorePointRecordPage.getResult());
    userScorePointRecordMap = buildUserScorePointRecordMap(userScorePointRecordPage.getResult());
    List<CourseProductItem> productItemList = quesOperationFacade.queryChapterItemList(courseId);
    Map<Long, ChapterScore> _parentMap = Maps.newHashMap();
    for (CourseProductItem courseChapter : productItemList) {
      if (null != courseChapter.getResourceType()) {
        if (courseChapter.getChapterType() == QuesBaseConstant.RESOURCE_TYPE_ITEM) {
          ChapterScore parentChapter = _parentMap.get(courseChapter.getParentId());
          if (parentChapter == null) {
            parentChapter = new ChapterScore();
          }
          ItemScore itemScore = new ItemScore();
          String weight = "";
          if (null != courseChapter && null != courseChapter.getWeight()
              && 0d != courseChapter.getWeight()) {
            weight = "(权重" + courseChapter.getWeight() + ")";
          }
          itemScore.setWeight(weight);
          itemScore.setItemIndex(courseChapter.getChapterId() + weight);
          itemScore.setItemName(courseChapter.getName());
          itemScore.setItemId(courseChapter.getId().toString());
          parentChapter.getItemScoreList().add(itemScore);
          _itemMap.put(itemScore.getItemId(), itemScore);
          // 保存父章节至父章节Map中
          _parentMap.put(courseChapter.getParentId(), parentChapter);
        } else if (courseChapter.getChapterType() == QuesBaseConstant.RESOURCE_TYPE_CHAPTER) {
          // 该章节本身就是父章节
          ChapterScore parentChapter;
          if (_parentMap.containsKey(courseChapter.getId())) {
            parentChapter = _parentMap.get(courseChapter.getId());
          } else {
            parentChapter = new ChapterScore(courseChapter);
          }
          parentChapter.setChapterName(courseChapter.getName());
          String weight = "";
          if (null != courseChapter && null != courseChapter.getWeight()
              && 0d != courseChapter.getWeight()) {
            weight = "(权重" + courseChapter.getWeight() + ")";
          }
          parentChapter.setWeight(weight);
          parentChapter.setChapterIndex(courseChapter.getChapterId() + weight);
          parentChapter.setChapterId(courseChapter.getId().toString());
          if (null != courseChapter.getListOrder())
            parentChapter.setListOrder(courseChapter.getListOrder());
          // 保存父章节至父章节Map中
          _parentMap.put(courseChapter.getId(), parentChapter);
          // 这里章列表只保存父章节即可
          this.chapterScoreList.add(parentChapter);
          // 新加章总结
          ItemScore chapterItem = new ItemScore();
          chapterItem.setItemId(courseChapter.getId().toString());
          chapterItem.setItemIndex("章总结");
          chapterItem.setItemName("习题解析");
          _chapterSummaryMap.put(chapterItem.getItemId(), chapterItem);
          _itemMap.put(chapterItem.getItemId(), chapterItem);
          parentChapter.getItemScoreList().add(chapterItem);
        }
      }
    }

    if (null == this.chapterScoreList || this.chapterScoreList.size() == 0) return;
    Collections.sort(this.chapterScoreList, new Comparator<ChapterScore>() {
      @Override
      public int compare(ChapterScore o1, ChapterScore o2) {
        if (null == o1 || null == o1.getListOrder() || o1.getListOrder() == -1)
          return 1;
        else if (null == o2 || null == o2.getListOrder() || o2.getListOrder() == -1)
          return -1;
        else
          return (int) (o1.getListOrder() - o2.getListOrder());
      }
    });
    addParentChapter(courseId, userId, examTotal, quesOperationFacade, bonusScore);
  }

  private void addParentChapter(String courseId, String userId, UserExamTotal examTotal,
      QuesOperationFacade quesOperationFacade, String bonusScore) {
    UserScorePointRecord finalExamRecord =
        userScorePointRecordMap.get(ProductConstants.RULE_TYPE_FINAL_EXAM);
    if (null != finalExamRecord && null != finalExamRecord.getRuleInfo()
        && finalExamRecord.getRuleInfo().getWeight() > 0) {
      // 新增期末测试
      ChapterScore finalExamScore = new ChapterScore();
      finalExamScore.setChapterIndex("期末测试");
      finalExamScore.setChapterName("期末测试");
      List<FinalExamModel> finalExamList =
          quesOperationFacade.selectFinalExamByCond(courseId, userId);
      if (null != finalExamList && !finalExamList.isEmpty()) {
        for (FinalExamModel finalExam : finalExamList) {
          ItemScore itemFinalExamScore = new ItemScore();
          itemFinalExamScore.setItemId(finalExam.getId().toString());
          itemFinalExamScore.setItemIndex(finalExam.getExamName());
          itemFinalExamScore.setItemName(finalExam.getExamName());
          itemFinalExamScore.setOriginalScore(MathUtil.getIntStringValue(finalExam.getScore()));
          _itemMap.put(itemFinalExamScore.getItemId(), itemFinalExamScore);
          finalExamScore.getItemScoreList().add(itemFinalExamScore);
        }
        this.chapterScoreList.add(finalExamScore);
      }
    }
    UserScorePointRecord missionRecord =
        userScorePointRecordMap.get(ProductConstants.RULE_TYPE_STANDARD_MISSION);
    if (null != missionRecord && null != missionRecord.getRuleInfo()
        && missionRecord.getRuleInfo().getWeight() > 0) {
      // 新增学习过程
      ChapterScore missionFinishScore = new ChapterScore();
      missionFinishScore.setChapterIndex("学习过程");
      missionFinishScore.setChapterName("学习过程");
      ItemScore itemMissionFinishScore = new ItemScore();
      itemMissionFinishScore.setItemIndex("学习任务完成度");
      itemMissionFinishScore.setItemName("学习任务完成度");
      if (null != missionRecord.getScore()) {
        itemMissionFinishScore
            .setOriginalScore(MathUtil.getIntStringValue(missionRecord.getScore()));
      }
      missionFinishScore.getItemScoreList().add(itemMissionFinishScore);
      this.chapterScoreList.add(missionFinishScore);
    }
    UserScorePointRecord leakFillingRecord =
        userScorePointRecordMap.get(ProductConstants.RULE_TYPE_LEAK_FILLING);
    if (null != leakFillingRecord && null != leakFillingRecord.getRuleInfo()
        && leakFillingRecord.getRuleInfo().getWeight() > 0) {
      // 新增查漏补缺
      ChapterScore supplementScore = new ChapterScore();
      supplementScore.setChapterIndex("查漏补缺");
      supplementScore.setChapterName("查漏补缺");
      ItemScore itemSupplementScore = new ItemScore();
      itemSupplementScore.setItemIndex("查漏补缺成绩");
      itemSupplementScore.setItemName("查漏补缺成绩");
      if (null != leakFillingRecord.getScore()) {
        itemSupplementScore.setOriginalScore(MathUtil.getIntStringValue(leakFillingRecord
            .getScore()));
      }
      if (bonusScore != null) {
        itemSupplementScore.setBonusScore(bonusScore);
      }
      supplementScore.getItemScoreList().add(itemSupplementScore);
      this.chapterScoreList.add(supplementScore);
    }
    UserScorePointRecord studyRecord =
        userScorePointRecordMap.get(ProductConstants.RULE_TYPE_STUDY_COURSE_WARE);
    if (null != studyRecord && null != studyRecord.getRuleInfo()
        && studyRecord.getRuleInfo().getWeight() > 0) {
      // 新增学习过程
      ChapterScore studyFinishScore = new ChapterScore();
      studyFinishScore.setChapterIndex("课件学习");
      studyFinishScore.setChapterName("课件学习");
      ItemScore itemStudyFinishScore = new ItemScore();
      itemStudyFinishScore.setItemIndex("课件学习成绩");
      itemStudyFinishScore.setItemName("课件学习成绩");
      if (null != studyRecord.getScore()) {
        itemStudyFinishScore.setOriginalScore(MathUtil.getIntStringValue(studyRecord.getScore()));
      }
      studyFinishScore.getItemScoreList().add(itemStudyFinishScore);
      this.chapterScoreList.add(studyFinishScore);
    }
  }

  public String getCourseId() {
    return courseId;
  }

  public void setCourseId(String courseId) {
    this.courseId = courseId;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public List<ChapterScore> getChapterScoreList() {
    return chapterScoreList;
  }

  public void setChapterScoreList(List<ChapterScore> chapterScoreList) {
    this.chapterScoreList = chapterScoreList;
  }

  /**
   * @name @see com.xiaodou.vo.response.CourseScore.java
   * @CopyRright (c) 2016 by Corp.XiaodouTech
   * 
   * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
   * @date 2016年6月18日
   * @description 章
   * @version 1.0
   */
  public static class ChapterScore {
    /** itemId 章ID */
    private String chapterId;
    /** weight 权重 */
    private String weight;
    /** itemIndex 章序号 */
    private String chapterIndex;
    /** itemName 章名称 */
    private String chapterName;
    /** listOrder 排序 */
    @JSONField(serialize = false, deserialize = false)
    private Long listOrder = -1l;
    /** itemScoreList 节分数列表 */
    private List<ItemScore> itemScoreList = Lists.newArrayList();

    private String needAddScore = MathUtil.getIntStringValue(0d);

    public ChapterScore(CourseProductItem courseChapter) {
      setChapterName(courseChapter.getName());
      setChapterIndex(courseChapter.getChapterId());
      setChapterId(courseChapter.getId().toString());
      if (null != courseChapter.getListOrder()) setListOrder(courseChapter.getListOrder());
    }

    public ChapterScore() {}

    public void setListOrder(Long listOrder) {
      this.listOrder = listOrder;
    }

    public Long getListOrder() {
      return listOrder;
    }

    public String getChapterId() {
      return chapterId;
    }

    public void setChapterId(String chapterId) {
      this.chapterId = chapterId;
    }

    public String getChapterIndex() {
      return chapterIndex;
    }

    public void setChapterIndex(String chapterIndex) {
      this.chapterIndex = chapterIndex;
    }

    public String getChapterName() {
      return chapterName;
    }

    public void setChapterName(String chapterName) {
      this.chapterName = chapterName;
    }

    public List<ItemScore> getItemScoreList() {
      return itemScoreList;
    }

    public void setItemScoreList(List<ItemScore> itemScoreList) {
      this.itemScoreList = itemScoreList;
    }

    public String getNeedAddScore() {
      return needAddScore;
    }

    public void setNeedAddScore(String needAddScore) {
      this.needAddScore = needAddScore;
    }

    public void setNeedAddScore(Double needAddScore) {
      this.needAddScore = MathUtil.getIntStringValue(needAddScore);
    }

    public String getWeight() {
      return weight;
    }

    public void setWeight(String weight) {
      this.weight = weight;
    }
  }

  /**
   * @name @see com.xiaodou.domain.RedBonus.java
   * @CopyRright (c) 2016 by Corp.XiaodouTech
   * 
   * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
   * @date 2016年6月18日
   * @description 节
   * @version 1.0
   */
  public static class ItemScore {
    public ItemScore() {}

    public ItemScore(CourseProductItem courseChapter) {
      setItemName(courseChapter.getName());
      setItemIndex(courseChapter.getChapterId());
      setItemId(courseChapter.getId().toString());
    }

    /** itemId 节ID */
    private String itemId;
    /** itemIndex 节序号 */
    private String itemIndex;
    /** itemName 节名称 */
    private String itemName;
    /** bonusScore 红包分 */
    private String bonusScore = MathUtil.getIntStringValue(0d);
    /** originalScore 原始分 */
    private String originalScore = MathUtil.getIntStringValue(0d);
    private String weight;

    public String getWeight() {
      return weight;
    }

    public void setWeight(String weight) {
      this.weight = weight;
    }

    public String getItemId() {
      return itemId;
    }

    public void setItemId(String itemId) {
      this.itemId = itemId;
    }

    public String getItemIndex() {
      return itemIndex;
    }

    public void setItemIndex(String itemIndex) {
      this.itemIndex = itemIndex;
    }

    public String getItemName() {
      return itemName;
    }

    public void setItemName(String itemName) {
      this.itemName = itemName;
    }

    public String getBonusScore() {
      return bonusScore;
    }

    public void setBonusScore(Long bonusScore) {
      this.bonusScore = bonusScore.toString();
    }

    public String getOriginalScore() {
      return originalScore;
    }

    public void setOriginalScore(Double originalScore) {
      this.originalScore = MathUtil.getIntStringValue(originalScore);
    }

    public void setBonusScore(String bonusScore) {
      this.bonusScore = bonusScore;
    }

    public void setOriginalScore(String originalScore) {
      this.originalScore = originalScore;
    }

  }
}
