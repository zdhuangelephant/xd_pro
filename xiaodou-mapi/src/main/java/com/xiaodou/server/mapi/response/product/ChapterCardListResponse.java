package com.xiaodou.server.mapi.response.product;

import java.util.List;

import com.google.common.collect.Lists;
import com.xiaodou.server.mapi.domain.ItemRecord;
import com.xiaodou.server.mapi.response.selftaught.BaseResponse;
import com.xiaodou.summer.vo.out.ResultType;

import lombok.Data;

public class ChapterCardListResponse extends BaseResponse {

  public ChapterCardListResponse() {}

  public ChapterCardListResponse(ResultType type) {
    super(type);
  }

  /** chapterId 章ID */
  private String chapterId;
  /** chapterName 章名称 */
  private String chapterName;
  /** chapterIndex 章序号 */
  private String chapterIndex;
  /** showCardIndex 需要展示的卡片序号 */
  private String showCardIndex;
  /** itemCardList 节卡片列表 */
  private List<ItemRecord> itemList = Lists.newArrayList();
  /** audioList 音频文件列表 */
  private List<AudioInfo> audioList = Lists.newArrayList();
  
  private LastOrNextChapter lastOrNextChapter;
  @Data
  public static class LastOrNextChapter {
    private String previousChapterId;
    private String nextChapterId;
  }

  public LastOrNextChapter getLastOrNextChapter() {
    return lastOrNextChapter;
  }

  public void setLastOrNextChapter(LastOrNextChapter lastOrNextChapter) {
    this.lastOrNextChapter = lastOrNextChapter;
  }

  private String credit = String.valueOf(0);

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

  public List<AudioInfo> getAudioList() {
    return audioList;
  }

  public void setAudioList(List<AudioInfo> audioList) {
    this.audioList = audioList;
  }

  public String getCredit() {
    return credit;
  }

  public void setCredit(String credit) {
    this.credit = credit;
  }



//  public static class ItemRecord {
//    private String itemId;// 节ID
//    private String itemName;// 节名称
//    private String itemIndex;// 节序号 eg:"第一节"
//    private String starLevel; // 星级 0 0颗 1 一星 2 两星 3 三星
//    private String score; // 闯关得分
//    private List<String> topUserList = Lists.newArrayList();
//    private String completeCount;
//    private String pictureUrl;
//    private String itemType = "1";// 0 章 1节 2期末
//    private String status = "0";
//    private String deductPoint = "0";
//    private String lock = "0";
//
//    public String getItemId() {
//      return itemId;
//    }
//
//    public void setItemId(String itemId) {
//      this.itemId = itemId;
//    }
//
//    public String getItemName() {
//      return itemName;
//    }
//
//    public void setItemName(String itemName) {
//      this.itemName = itemName;
//    }
//
//    public String getItemIndex() {
//      return itemIndex;
//    }
//
//    public void setItemIndex(String itemIndex) {
//      this.itemIndex = itemIndex;
//    }
//
//    public String getStarLevel() {
//      return starLevel;
//    }
//
//    public void setStarLevel(String starLevel) {
//      this.starLevel = starLevel;
//    }
//
//    public String getScore() {
//      return score;
//    }
//
//    public void setScore(String score) {
//      this.score = score;
//    }
//
//    public List<String> getTopUserList() {
//      return topUserList;
//    }
//
//    public void setTopUserList(List<String> topUserList) {
//      this.topUserList = topUserList;
//    }
//
//    public String getCompleteCount() {
//      return completeCount;
//    }
//
//    public void setCompleteCount(String completeCount) {
//      this.completeCount = completeCount;
//    }
//
//    public String getPictureUrl() {
//      return pictureUrl;
//    }
//
//    public void setPictureUrl(String pictureUrl) {
//      this.pictureUrl = pictureUrl;
//    }
//
//    public String getItemType() {
//      return itemType;
//    }
//
//    public void setItemType(String itemType) {
//      this.itemType = itemType;
//    }
//
//    public String getStatus() {
//      return status;
//    }
//
//    public void setStatus(String status) {
//      this.status = status;
//    }
//
//    public String getDeductPoint() {
//      return deductPoint;
//    }
//
//    public void setDeductPoint(String deductPoint) {
//      this.deductPoint = deductPoint;
//    }
//
//    public String getLock() {
//      return lock;
//    }
//
//    public void setLock(String lock) {
//      this.lock = lock;
//    }
//
//  }

  @Data
  public static class AudioInfo {
    /** courseName 课程名 */
    private String courseName;
    /** courseId 课程ID */
    private String courseId;
    /** chapterId 章ID */
    private String chapterId;
    /** itemId 节ID */
    private String itemId;
    /** audioName 音频名称 */
    private String audioName;
    /** audioId 音频ID */
    private String audioId;
    /** timeLength 音频时长 */
    private String timeLength;
    /** size 音频大小 */
    private String size;
    /** url 下载地址 */
    private String url;
  }

}
