package com.xiaodou.userCenter.response;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.google.common.collect.Lists;
import com.xiaodou.summer.vo.out.ResultType;
import com.xiaodou.userCenter.response.resultype.UcenterResType;

public class PersonInfoResponse extends UserInfoResponse {

  public PersonInfoResponse(ResultType type) {
    super(type);
  }

  public PersonInfoResponse(UcenterResType resType) {
    super(resType);
  }

  /**
   * 课程信息
   */
  private List<StCourseInfo> myCourseList = Lists.newArrayList();

  public List<StCourseInfo> getMyCourseList() {
    return myCourseList;
  }

  public void setMyCourseList(List<StCourseInfo> myCourseList) {
    this.myCourseList = myCourseList;
  }

  public static class StCourseInfo {
    private String courseId = StringUtils.EMPTY;// 课程id
    private String courseName = StringUtils.EMPTY;// 课程名
    private String score = Integer.toString(0);// 得分
    private String flag = Integer.toString(0);// // 0 普通 1 热门
    private String status = Integer.toString(0);//  0 未购买 1 已购买
    private String coin = Integer.toString(0);// 购买价格，即金币数

    public String getCourseId() {
      return courseId;
    }

    public void setCourseId(String courseId) {
      this.courseId = courseId;
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

    public String getFlag() {
      return flag;
    }

    public void setFlag(String flag) {
      this.flag = flag;
    }

    public String getStatus() {
      return status;
    }

    public void setStatus(String status) {
      this.status = status;
    }

    public String getCoin() {
      return coin;
    }

    public void setCoin(String coin) {
      this.coin = coin;
    }

    public enum CourseStatus {
      NOTBUY("0", "未购买"), BUY("1", "已购买");
      private String code;
      private String name;

      private CourseStatus(String code, String name) {
        this.code = code;
        this.name = name;
      }

      public String getCode() {
        return code;
      }

      public void setCode(String code) {
        this.code = code;
      }

      public String getName() {
        return name;
      }

      public void setName(String name) {
        this.name = name;
      }
    }
  }
}
