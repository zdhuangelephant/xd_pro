package com.xiaodou.server.mapi.response.product;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.google.common.collect.Lists;
import com.xiaodou.server.mapi.domain.ChapterRecord;
import com.xiaodou.server.mapi.response.quesbk.CourseStatisticsInfo;
import com.xiaodou.server.mapi.response.selftaught.BaseResponse;
import com.xiaodou.summer.vo.out.ResultType;

public class UserChapterRecordResponse extends BaseResponse {

  /** score 课程得分 */
  private String score = StringUtils.EMPTY;
  /** myQues 做题数 */
  private String myQues = StringUtils.EMPTY;
  /** myRightPercent 正确率 */
  private String myRightPercent = StringUtils.EMPTY;
  /** myWrongQues 错题数 */
  private String myWrongQues = StringUtils.EMPTY;
  // 闯关章节列表
//  private List<UserChapterRecordVo> userChapterRecordList = Lists.newArrayList();
  private List<ChapterRecord> userChapterRecordList = Lists.newArrayList();
  // 课程名称
  private String courseName = StringUtils.EMPTY;

  public UserChapterRecordResponse() {}

  public UserChapterRecordResponse(ResultType resultType) {
    super(resultType);
  }

  public List<ChapterRecord> getUserChapterRecordList() {
    return userChapterRecordList;
  }

  public void setUserChapterRecordList(List<ChapterRecord> userChapterRecordList) {
    this.userChapterRecordList = userChapterRecordList;
  }

  public String getCourseName() {
    return courseName;
  }

  public void setCourseName(String courseName) {
    this.courseName = courseName;
  }

  public String getScore() {
    return score;
  }

  public void setScore(String score) {
    this.score = score;
  }

  public String getMyQues() {
    return myQues;
  }

  public void setMyQues(String myQues) {
    this.myQues = myQues;
  }

  public String getMyRightPercent() {
    return myRightPercent;
  }

  public void setMyRightPercent(String myRightPercent) {
    this.myRightPercent = myRightPercent;
  }

  public String getMyWrongQues() {
    return myWrongQues;
  }

  public void setMyWrongQues(String myWrongQues) {
    this.myWrongQues = myWrongQues;
  }



  public static class UserChapterRecordVo {
    private String chapterId = StringUtils.EMPTY;
    private String chapterName = StringUtils.EMPTY;
    private String chapterIndex = StringUtils.EMPTY;// eg:"第一章"
    private String learnedItemCount = StringUtils.EMPTY;
    private String totalItemCount = StringUtils.EMPTY;
    // private String starLevel = StringUtils.EMPTY; // 星级 0 0颗 1 一星 2 两心 3 三星
    private List<UserItemRecordVo> itemList = Lists.newArrayList();   
    private String starLevel; // 星级 0 0颗 1 一星 2 两星 3 三星
    private String score; // 得分
    private List<String> topUserList = Lists.newArrayList();
    private Integer completeCount = 0;
    private String pictureUrl;
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

    public String getLearnedItemCount() {
      return learnedItemCount;
    }

    public void setLearnedItemCount(String learnedItemCount) {
      this.learnedItemCount = learnedItemCount;
    }

    public String getTotalItemCount() {
      return totalItemCount;
    }

    public void setTotalItemCount(String totalItemCount) {
      this.totalItemCount = totalItemCount;
    }

    public List<UserItemRecordVo> getItemList() {
      return itemList;
    }

    public void setItemList(List<UserItemRecordVo> itemList) {
      this.itemList = itemList;
    }

	public String getStarLevel() {
		return starLevel;
	}

	public void setStarLevel(String starLevel) {
		this.starLevel = starLevel;
	}

	public List<String> getTopUserList() {
		return topUserList;
	}

	public void setTopUserList(List<String> topUserList) {
		this.topUserList = topUserList;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public Integer getCompleteCount() {
		return completeCount;
	}

	public void setCompleteCount(Integer completeCount) {
		this.completeCount = completeCount;
	}

	public String getPictureUrl() {
		return pictureUrl;
	}

	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}

  }

  public static class UserItemRecordVo {
    private String itemId = StringUtils.EMPTY;
    private String itemName = StringUtils.EMPTY;
    private String itemIndex = StringUtils.EMPTY;// eg:"第一节"
    private String starLevel = StringUtils.EMPTY; // 星级 0 0颗 1 一星 2 两心 3 三星

    public String getItemId() {
      return itemId;
    }

    public void setItemId(String itemId) {
      this.itemId = itemId;
    }

    public String getItemName() {
      return itemName;
    }

    public void setItemName(String itemName) {
      this.itemName = itemName;
    }

    public String getItemIndex() {
      return itemIndex;
    }

    public void setItemIndex(String itemIndex) {
      this.itemIndex = itemIndex;
    }

    public String getStarLevel() {
      return starLevel;
    }

    public void setStarLevel(String starLevel) {
      this.starLevel = starLevel;
    }

  }

  public void setStatistics(CourseStatisticsInfo statistics) {
    this.score = statistics.getScore();
    this.myQues = statistics.getMyQues();
    this.myWrongQues = statistics.getMyWrongQues();
    this.myRightPercent = statistics.getMyRightPercent();
  }
}
