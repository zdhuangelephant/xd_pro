package com.xiaodou.resources.response.product;

import java.util.List;

import com.google.common.collect.Lists;
import com.xiaodou.resources.response.BaseResponse;
import com.xiaodou.resources.response.resultype.ProductResType;
import com.xiaodou.resources.vo.user.ItemRecord;
import com.xiaodou.resources.vo.user.UserChapterRecordVo;
import com.xiaodou.summer.vo.out.ResultType;

public class ChapterCardListResponse extends BaseResponse {
  public ChapterCardListResponse(ResultType type) {
    super(type);
  }

  public ChapterCardListResponse(ProductResType productResType) {
    super(productResType);
  }

  /** chapterId 章ID */
  private String chapterId;
  /** chapterName 章名称 */
  private String chapterName;
  /** chapterIndex 章序号 */
  private String chapterIndex;
  // private String starLevel; // 星级 0 0颗 1 一星 2 两星 3 三星
  // private String score; // 得分
  // private List<String> topUserList = Lists.newArrayList();
  // private Integer completeCount = 0;
  // private String pictureUrl;
  /** showCardIndex 需要展示的卡片序号 */
  private String showCardIndex = "0";
  /** itemCardList 节卡片列表 */
  private List<ItemRecord> itemList = Lists.newArrayList();

  public String getChapterId() {
    return chapterId;
  }

  public void setChapterId(String chapterId) {
    this.chapterId = chapterId;
  }

  public String getChapterName() {
    return chapterName;
  }

  public void setChapterName(String chapterName) {
    this.chapterName = chapterName;
  }

  public String getChapterIndex() {
    return chapterIndex;
  }

  public void setChapterIndex(String chapterIndex) {
    this.chapterIndex = chapterIndex;
  }

  public String getShowCardIndex() {
    return showCardIndex;
  }

  public void setShowCardIndex(String showCardIndex) {
    this.showCardIndex = showCardIndex;
  }

  public List<ItemRecord> getItemList() {
    return itemList;
  }

  public void setItemList(List<ItemRecord> itemList) {
    this.itemList = itemList;
  }

  public void setChapterInfo(UserChapterRecordVo vo) {
    if (null != vo.getChapterId()) this.chapterId = vo.getChapterId().toString();
    this.chapterName = vo.getChapterName();
    this.chapterIndex = vo.getChapterIndex();
    // this.starLevel = vo.getStarLevel().toString();
    // this.score = vo.getScore().toString();
    // this.completeCount = vo.getCompleteCount();
    // if (StringUtils.isJsonNotBlank(vo.getTopUserList()))
    // this.topUserList =
    // FastJsonUtil.fromJsons(vo.getTopUserList(), new TypeReference<List<String>>() {});
    // this.pictureUrl = vo.getPictureUrl();
  }

}
