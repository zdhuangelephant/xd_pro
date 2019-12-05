package com.xiaodou.resources.response.quesbk;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.annotation.JSONField;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xiaodou.resources.constant.quesbk.QuesBaseConstant;
import com.xiaodou.resources.model.product.ProductItemModel;
import com.xiaodou.resources.service.quesbk.facade.QuesOperationFacade;
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

  public ItemScore getItem(String itemId) {
    return _itemMap.get(itemId);
  }

  public int getItemCount() {
    return _itemMap.size();
  }

  /** courseId 课程ID */
  private String courseId;
  /** chapterScoreList 章分数列表 */
  private List<ChapterScore> chapterScoreList = Lists.newArrayList();

  public void init(String courseId) {
    this.courseId = courseId;
    QuesOperationFacade quesOperationFacade = SpringWebContextHolder.getBean("quesOperationFacade");
    List<ProductItemModel> productItemList = quesOperationFacade.queryChapterItemList(courseId);
    Map<Long, ChapterScore> _parentMap = Maps.newHashMap();
    for (ProductItemModel courseChapter : productItemList) {
      if (null != courseChapter.getResourceType()) {
        if (courseChapter.getParentId() > 0) {
          ChapterScore parentChapter = _parentMap.get(courseChapter.getParentId());
          if (parentChapter == null) {
            parentChapter = new ChapterScore();
            parentChapter.setChapterId(courseChapter.getParentId().toString());
            // 保存父章节至父章节Map中
            _parentMap.put(courseChapter.getParentId(), parentChapter);
            // 这里章列表只保存父章节即可
            this.chapterScoreList.add(parentChapter);
          }
          ItemScore item = new ItemScore(courseChapter);
          _itemMap.put(item.getItemId(), item);
          parentChapter.getItemScoreList().add(item);
        } else if (courseChapter.getParentId() == 0) {
          // 该章节本身就是父章节
          if (_parentMap.containsKey(courseChapter.getId())) {
            _parentMap.get(courseChapter.getId()).setChapterName(courseChapter.getName());
            _parentMap.get(courseChapter.getId()).setChapterId(courseChapter.getId().toString());
            if (null != courseChapter.getListOrder())
              _parentMap.get(courseChapter.getId()).setListOrder(courseChapter.getListOrder());
          } else {
            ChapterScore parentChapter = new ChapterScore(courseChapter);
            // 保存父章节至父章节Map中
            _parentMap.put(courseChapter.getId(), parentChapter);
            // 这里章列表只保存父章节即可
            this.chapterScoreList.add(parentChapter);
          }
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
  }

  public String getCourseId() {
    return courseId;
  }

  public void setCourseId(String courseId) {
    this.courseId = courseId;
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
    /** itemIndex 章序号 */
    private String chapterIndex;
    /** itemName 章名称 */
    private String chapterName;
    /** listOrder 排序 */
    @JSONField(serialize = false, deserialize = false)
    private Long listOrder = -1l;
    /** itemScoreList 节分数列表 */
    private List<ItemScore> itemScoreList = Lists.newArrayList();

    public ChapterScore(ProductItemModel courseChapter) {
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

    public ItemScore(ProductItemModel courseChapter) {
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
    private String bonusScore = QuesBaseConstant.D_FORMAT.format(0d);
    /** originalScore 原始分 */
    private String originalScore = QuesBaseConstant.D_FORMAT.format(0d);

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

    public void setBonusScore(Double bonusScore) {
      this.bonusScore = QuesBaseConstant.D_FORMAT.format(bonusScore);
    }

    public String getOriginalScore() {
      return originalScore;
    }

    public void setOriginalScore(Double originalScore) {
      this.originalScore = QuesBaseConstant.D_FORMAT.format(originalScore);
    }

    public void setBonusScore(String bonusScore) {
      this.bonusScore = bonusScore;
    }

    public void setOriginalScore(String originalScore) {
      this.originalScore = originalScore;
    }

  }

}
