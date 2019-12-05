package com.xiaodou.course.vo.score;

import java.util.List;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.common.collect.Lists;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.course.enums.ExamStatus;

/**
 * @name @see com.xiaodou.course.vo.score.ScoreResult.java
 * @CopyRright (c) 2015 by XiaoDou NetWork Technology
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2015年9月25日
 * @description 查成绩结果
 * @version 1.0
 */
public class ScoreResult {

  /** province 省份 */
  private String province = StringUtils.EMPTY;
  /** nameInfo 姓名信息 */
  private NameInfo nameInfo;
  /** writtenScoreList 笔试成绩 */
  private List<WrittenScore> writtenScoreList = Lists.newArrayList();
  /** interviewScoreList 面试成绩 */
  private List<InterviewScore> interviewScoreList = Lists.newArrayList();
  /** proveScoreList 考试合格证明 */
  private List<ProveScore> proveScoreList = Lists.newArrayList();

  public String getProvince() {
    return province;
  }

  public void setProvince(String province) {
    if (StringUtils.isBlank(this.province)) this.province = province;
  }

  public NameInfo getNameInfo() {
    return nameInfo;
  }

  public void setNameInfo(NameInfo nameInfo) {
    this.nameInfo = nameInfo;
  }

  public List<WrittenScore> getWrittenScoreList() {
    return writtenScoreList;
  }

  public List<InterviewScore> getInterviewScoreList() {
    return interviewScoreList;
  }

  public List<ProveScore> getProveScoreList() {
    return proveScoreList;
  }

  /**
   * @name @see com.xiaodou.course.vo.score.NameInfo.java
   * @CopyRright (c) 2015 by XiaoDou NetWork Technology
   * 
   * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
   * @date 2015年9月25日
   * @description 用戶信息
   * @version 1.0
   */
  public static class NameInfo {
    /** name 考生姓名 */
    private String name;
    /** docNumber 证件号码 */
    private String docNumber;

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public String getDocNumber() {
      return docNumber;
    }

    public void setDocNumber(String docNumber) {
      this.docNumber = docNumber;
    }

  }

  /**
   * @name @see com.xiaodou.course.vo.score.BaseScore.java
   * @CopyRright (c) 2015 by XiaoDou NetWork Technology
   * 
   * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
   * @date 2015年9月25日
   * @description 考试成绩基类
   * @version 1.0
   */
  public static class BaseScore {

    /** istate 合格状态 */
    private Integer istate;
    /** coureseName 课程名称 */
    private String coureseName;
    /** sstate 合格状态 */
    private String sstate;
    /** docNumber 准考证号 */
    private String docNumber;
    /** examIndex 考试批次 */
    private String examIndex;

    public Integer getIstate() {
      return istate;
    }

    public void setState(ExamStatus status) {
      if (null == status) return;
      this.istate = status.getCode();
      this.sstate = status.getState();
    }

    public String getCoureseName() {
      return coureseName;
    }

    public void setCoureseName(String coureseName) {
      this.coureseName = coureseName;
    }

    public String getSstate() {
      return sstate;
    }

    public String getDocNumber() {
      return docNumber;
    }

    public void setDocNumber(String docNumber) {
      this.docNumber = docNumber;
    }

    public String getExamIndex() {
      return examIndex;
    }

    public void setExamIndex(String examIndex) {
      this.examIndex = examIndex;
    }
  }

  /**
   * @name @see com.xiaodou.course.vo.score.WrittenScore.java
   * @CopyRright (c) 2015 by XiaoDou NetWork Technology
   * 
   * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
   * @date 2015年9月25日
   * @description 笔试成绩
   * @version 1.0
   */
  public static class WrittenScore extends BaseScore {

    /** score 分数 */
    private String score;
    /** validDate 有效时间 */
    private String validDate;

    public String getScore() {
      return score;
    }

    public void setScore(String score) {
      this.score = score;
    }

    public String getValidDate() {
      return validDate;
    }

    public void setValidDate(String validDate) {
      this.validDate = validDate;
    }

  }

  /**
   * @name @see com.xiaodou.course.vo.score.InterviewScore.java
   * @CopyRright (c) 2015 by XiaoDou NetWork Technology
   * 
   * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
   * @date 2015年9月25日
   * @description 面试成绩
   * @version 1.0
   */
  public static class InterviewScore extends BaseScore {}

  /**
   * @name @see com.xiaodou.course.vo.score.ProveScore.java
   * @CopyRright (c) 2015 by XiaoDou NetWork Technology
   * 
   * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
   * @date 2015年9月25日
   * @description 合格证明
   * @version 1.0
   */
  public static class ProveScore {

    /** coureseName 课程名称 */
    private String coureseName;
    /** proveNumber 证明编号 */
    private String proveNumber;
    /** validDate 有效期限 */
    private String validDate;

    public String getCoureseName() {
      return coureseName;
    }

    public void setCoureseName(String coureseName) {
      this.coureseName = coureseName;
    }

    public String getProveNumber() {
      return proveNumber;
    }

    public void setProveNumber(String proveNumber) {
      this.proveNumber = proveNumber;
    }

    public String getValidDate() {
      return validDate;
    }

    public void setValidDate(String validDate) {
      this.validDate = validDate;
    }

  }

  public void setWrittenScoreList(Elements trs) {
    if (null == trs || trs.size() == 0) return;
    for (Element tr : trs) {
      Elements tds = tr.getElementsByTag("td");
      WrittenScore wr = new WrittenScore();
      wr.setCoureseName(tds.get(0).text());
      wr.setScore(tds.get(1).text());
      wr.setState(ExamStatus.getByState(tds.get(2).text()));
      wr.setDocNumber(tds.get(3).text());
      wr.setExamIndex(tds.get(4).text());
      wr.setValidDate(tds.get(5).text());
      setProvince(tds.get(6).text());
      writtenScoreList.add(wr);
    }
  }

  public void setInterviewScoreList(Elements trs) {
    if (null == trs || trs.size() == 0) return;
    for (Element tr : trs) {
      Elements tds = tr.getElementsByTag("td");
      InterviewScore in = new InterviewScore();
      in.setCoureseName(tds.get(0).text());
      in.setState(ExamStatus.getByState(tds.get(1).text()));
      in.setDocNumber(tds.get(2).text());
      in.setExamIndex(tds.get(3).text());
      setProvince(tds.get(4).text());
      interviewScoreList.add(in);
    }
  }


  public void setProveScoreList(Elements trs) {
    if (null == trs || trs.size() == 0) return;
    for (Element tr : trs) {
      Elements tds = tr.getElementsByTag("td");
      ProveScore pr = new ProveScore();
      pr.setCoureseName(tds.get(0).text());
      pr.setProveNumber(tds.get(1).text());
      pr.setValidDate(tds.get(2).text());
      proveScoreList.add(pr);
    }
  }

}
