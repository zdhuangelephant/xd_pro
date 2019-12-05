package com.xiaodou.server.mapi.response.selftaught;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.google.common.collect.Lists;
import com.xiaodou.server.mapi.response.ucenter.UserModelResponse;
import com.xiaodou.server.mapi.response.ucenter.resultype.UcenterResType;
import com.xiaodou.server.mapi.vo.forum.Forum;
import com.xiaodou.summer.vo.out.ResultType;

public class HomeResponse extends BaseResponse {

  public HomeResponse() {}

  public HomeResponse(ResultType type) {
    super(type);
  }

  public HomeResponse(UcenterResType resType) {
    super(resType);
  }

  /** forum 校园之声 */
  private Forum forum;

  /** signCard 打卡记录 */
  private SignCard signCard;

  /** forumCard 知识分享 */
  private ForumCard forumCard;

  private UserModelResponse userInfo = new UserModelResponse();
  
  private List<StCourseInfo> recentExamCourseList = Lists.newArrayList();// 最近考期课程列表

  private List<StCourseInfo> otherExamCourseList = Lists.newArrayList();// 其它考期课程列表

  private String recentExamDate = StringUtils.EMPTY;// "2016年10月";// 最近考期

  private String countDownTime = Integer.toString(0);
  
  private String majorName = StringUtils.EMPTY;

  public String getMajorName() {
    return majorName;
  }

  public void setMajorName(String majorName) {
    this.majorName = majorName;
  }

  public UserModelResponse getUserInfo() {
    return userInfo;
  }

  public void setUserInfo(UserModelResponse userInfo) {
    this.userInfo = userInfo;
  }

  public Forum getForum() {
    return forum;
  }

  public void setForum(Forum forum) {
    this.forum = forum;
  }

  public SignCard getSignCard() {
    return signCard;
  }

  public void setSignCard(SignCard signCard) {
    this.signCard = signCard;
  }

  public ForumCard getForumCard() {
    return forumCard;
  }

  public void setForumCard(ForumCard forumCard) {
    this.forumCard = forumCard;
  }

  public List<StCourseInfo> getRecentExamCourseList() {
    return recentExamCourseList;
  }

  public void setRecentExamCourseList(List<StCourseInfo> recentExamCourseList) {
    this.recentExamCourseList = recentExamCourseList;
  }

  public String getRecentExamCourseCount() {
    return (null != getRecentExamCourseList())
        ? String.valueOf(getRecentExamCourseList().size())
        : Integer.toString(0);
  }

  public List<StCourseInfo> getOtherExamCourseList() {
    return otherExamCourseList;
  }

  public void setOtherExamCourseList(List<StCourseInfo> otherExamCourseList) {
    this.otherExamCourseList = otherExamCourseList;
  }

  public String getOtherExamCourseCount() {
    return (null != getOtherExamCourseList())
        ? String.valueOf(getOtherExamCourseList().size())
        : Integer.toString(0);
  }

  public String getRecentExamDate() {
    return recentExamDate;
  }

  public void setRecentExamDate(String recentExamDate) {
    this.recentExamDate = recentExamDate;
  }

  public String getCountDownTime() {
    return countDownTime;
  }

  public void setCountDownTime(String countDownTime) {
    this.countDownTime = countDownTime;
  }

  /**
   * @name SignCard CopyRright (c) 2017 by zhaodan
   * 
   * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
   * @date 2017年2月9日
   * @description 打卡记录
   * @version 1.0
   */
  public class SignCard {
    /** signId 打卡记录ID */
    private String signId;
    /** learnTime 学习时长 */
    private String learnTime;
    /** targetTime 目标时长 */
    private String targetTime;
    /** desc 描述文字 */
    private String desc;
    /** totalDay 总天数 */
    private String totalDay;
    /** continDay 连续天数 */
    private String continDay;
    /** state 状态 0 不能打卡 1 未打卡 2 已打卡 */
    private String state;

    public String getSignId() {
      return signId;
    }

    public void setSignId(String signId) {
      this.signId = signId;
    }

    public String getLearnTime() {
      return learnTime;
    }

    public void setLearnTime(String learnTime) {
      this.learnTime = learnTime;
    }

    public String getTargetTime() {
      return targetTime;
    }

    public void setTargetTime(String targetTime) {
      this.targetTime = targetTime;
    }

    public String getDesc() {
      return desc;
    }

    public void setDesc(String desc) {
      this.desc = desc;
    }

    public String getTotalDay() {
      return totalDay;
    }

    public void setTotalDay(String totalDay) {
      this.totalDay = totalDay;
    }

    public String getContinDay() {
      return continDay;
    }

    public void setContinDay(String continDay) {
      this.continDay = continDay;
    }

    public String getState() {
      return state;
    }

    public void setState(String state) {
      this.state = state;
    }
  }

  /**
   * @name ForumCard CopyRright (c) 2017 by zhaodan
   * 
   * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
   * @date 2017年2月9日
   * @description 知识分享
   * @version 1.0
   */
  public class ForumCard {
    /** cover 封面 */
    private String cover;
    /** titel 标题 */
    private String titel;
    /** desc 描述 */
    private String desc;

    public String getCover() {
      return cover;
    }

    public void setCover(String cover) {
      this.cover = cover;
    }

    public String getTitel() {
      return titel;
    }

    public void setTitel(String titel) {
      this.titel = titel;
    }

    public String getDesc() {
      return desc;
    }

    public void setDesc(String desc) {
      this.desc = desc;
    }
  }

  public static class StCourseInfo {
    /* 课程id */
    private String courseId = StringUtils.EMPTY;
    /* 课程码值 */
    private String courseCode = StringUtils.EMPTY;
    /* 课程名称 */
    private String courseName = StringUtils.EMPTY;
    /* 课程图片 */
    private String courseImage = StringUtils.EMPTY;
    /* 课程得分 */
    private String score = Integer.toString(0);
    /* 学习进度(上一次学习了第几章 */
    /* 章id */
    private String learnChapterId = StringUtils.EMPTY;
    /* 章索引 */
    private String learnChapterRate = StringUtils.EMPTY;
    /* 学习进度(上一次学习了第几节) */
    /* 节id */
    private String learnItemId = StringUtils.EMPTY;
    /* 节索引 */
    private String learnItemRate = StringUtils.EMPTY;
    /* 本课程已获取星级数量 */
    private String getStarCount = Integer.toString(0);
    /* 本课程星级总数 */
    private String totalStarCount = Integer.toString(0);
    /* 是否已经学习 0 未学习 1 已学习 */
    private String hasLearned = Integer.toString(0);

    /* 课程状态 1:有效2：无效 */
    private String courseStatus = Integer.toString(0);

    public StCourseInfo() {}

    public StCourseInfo(String courseId, String courseName, String courseImage, String score,
        String learnChapterId, String learnChapterRate, String learnItemId, String learnItemRate,
        String getStarCount, String totalStarCount) {
      super();
      this.courseId = courseId;
      this.courseName = courseName;
      this.courseImage = courseImage;
      this.score = score;
      this.learnChapterId = learnChapterId;
      this.learnChapterRate = learnChapterRate;
      this.learnItemId = learnItemId;
      this.learnItemRate = learnItemRate;
      this.getStarCount = getStarCount;
      this.totalStarCount = totalStarCount;
    }

    public String getCourseId() {
      return courseId;
    }

    public void setCourseId(String courseId) {
      this.courseId = courseId;
    }

    public String getCourseCode() {
      return courseCode;
    }

    public void setCourseCode(String courseCode) {
      this.courseCode = courseCode;
    }

    public String getHasLearned() {
      return hasLearned;
    }

    public void setHasLearned(String hasLearned) {
      this.hasLearned = hasLearned;
    }

    public String getCourseName() {
      return courseName;
    }

    public void setCourseName(String courseName) {
      this.courseName = courseName;
    }

    public String getCourseImage() {
      return courseImage;
    }

    public void setCourseImage(String courseImage) {
      this.courseImage = courseImage;
    }

    public String getScore() {
      return score;
    }

    public void setScore(String score) {
      this.score = score;
    }

    public String getLearnChapterId() {
      return learnChapterId;
    }

    public void setLearnChapterId(String learnChapterId) {
      this.learnChapterId = learnChapterId;
    }

    public String getLearnChapterRate() {
      return learnChapterRate;
    }

    public void setLearnChapterRate(String learnChapterRate) {
      this.learnChapterRate = learnChapterRate;
    }

    public String getLearnItemId() {
      return learnItemId;
    }

    public void setLearnItemId(String learnItemId) {
      this.learnItemId = learnItemId;
    }

    public String getLearnItemRate() {
      return learnItemRate;
    }

    public void setLearnItemRate(String learnItemRate) {
      this.learnItemRate = learnItemRate;
    }

    public String getGetStarCount() {
      return getStarCount;
    }

    public void setGetStarCount(String getStarCount) {
      this.getStarCount = getStarCount;
    }

    public String getTotalStarCount() {
      return totalStarCount;
    }

    public void setTotalStarCount(String totalStarCount) {
      this.totalStarCount = totalStarCount;
    }

    public String getCourseStatus() {
      return courseStatus;
    }

    public void setCourseStatus(String courseStatus) {
      this.courseStatus = courseStatus;
    }
  }
}
