package com.xiaodou.course.web.response.product;

import java.util.List;

import com.xiaodou.course.vo.user.ChapterRecord;
import com.xiaodou.course.web.response.BaseResponse;
import com.xiaodou.course.web.response.resultType.ProductResType;
import com.xiaodou.summer.vo.out.ResultType;

public class UserChapterRecordResponse extends BaseResponse {

  // 闯关章节列表
  private List<ChapterRecord> userChapterRecordList;
  // 课程名称
  private String courseName;
  

  public UserChapterRecordResponse(ResultType resultType) {
    super(resultType);
  }

  public UserChapterRecordResponse(ProductResType resultType) {
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


}
