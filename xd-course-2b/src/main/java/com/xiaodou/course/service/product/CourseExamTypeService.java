package com.xiaodou.course.service.product;

import java.text.ParseException;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.TypeReference;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.course.constant.CourseConstant;
import com.xiaodou.course.model.jsonParse.ProductExamDetail;
import com.xiaodou.course.model.product.ProductModel;
import com.xiaodou.course.service.AbstractService;
import com.xiaodou.course.service.queue.QueueService;
import com.xiaodou.course.util.DateUtil;

@Service("courseExamTypeService")
public class CourseExamTypeService extends AbstractService{
  @Resource
  ProductService productService;

  @Resource
  QueueService queueService;



  /**
   * 获取最近考期时间值
   * 
   * @return
   * @throws ParseException
   */
  public String getRecentExamStr() throws ParseException {
    // 4月第2、第3个周六日<->4,3,0
    // 10月第2、第3个周六日<->10,3,0
    // 确定最近考期
    String recentExamStr = StringUtils.EMPTY;
    Date date = new Date();
    Integer nowYear = DateUtil.getYearOfDate(date);
    Integer nowMonth = DateUtil.getMonthOfDate(date);
    Integer nowWeekNum = DateUtil.getWeekOfMonth(date);
    String nowWeek = DateUtil.getWeekOfDate(date);
    Integer nowDate = Integer.valueOf(nowMonth.toString() + nowWeekNum.toString() + nowWeek);
    if (nowDate <= CourseConstant.UP_DATE_EXAM_DATE) {
      recentExamStr = String.format("%s年%s月考试", nowYear, CourseConstant.UP_MONTH_EXAM_DATE);
    } else if (nowDate > CourseConstant.UP_DATE_EXAM_DATE
        && nowDate <= CourseConstant.DOWN_DATE_EXAM_DATE) {
      recentExamStr = String.format("%s年%s月考试", nowYear, CourseConstant.DOWN_MONTH_EXAM_DATE);
    } else if (nowDate >= CourseConstant.DOWN_DATE_EXAM_DATE) {
      recentExamStr = String.format("%s年%s月考试", nowYear + 1, CourseConstant.UP_MONTH_EXAM_DATE);
    }
    return recentExamStr;
  }

  @SuppressWarnings("unused")
  private String getRecentExam() throws ParseException {
    // 4月第2、第3个周六日<->4,3,0
    // 10月第2、第3个周六日<->10,3,0
    // 确定最近考期
    String recentExam = StringUtils.EMPTY;
    Date date = new Date();
    Integer nowYear = DateUtil.getYearOfDate(date);
    Integer nowMonth = DateUtil.getMonthOfDate(date);
    Integer nowWeekNum = DateUtil.getWeekOfMonth(date);
    String nowWeek = DateUtil.getWeekOfDate(date);
    Integer nowDate = Integer.valueOf(nowMonth.toString() + nowWeekNum.toString() + nowWeek);
    if (nowDate <= CourseConstant.UP_DATE_EXAM_DATE) {
      recentExam = nowYear.toString().substring(2) + CourseConstant.UP_MONTH_EXAM_DATE;
    } else if (nowDate > CourseConstant.UP_DATE_EXAM_DATE
        && nowDate <= CourseConstant.DOWN_DATE_EXAM_DATE) {
      recentExam = nowYear.toString().substring(2) + CourseConstant.DOWN_MONTH_EXAM_DATE;
    } else if (nowDate >= CourseConstant.DOWN_DATE_EXAM_DATE) {
      recentExam =
          Integer.valueOf(nowYear + 1).toString().substring(2) + CourseConstant.UP_MONTH_EXAM_DATE;
    }
    return recentExam;
  }

  /**
   * 根据课程考期类型，计算出产品要求展示的考期字符串
   * 
   * @param courseExamDetail
   * @return
   */
  public ProductExamDetail getCourseExamDate(String courseExamDetail) {
    ProductExamDetail examDetail = new ProductExamDetail();
    if (StringUtils.isNotBlank(courseExamDetail)) {
      // ProductExamDetail examDetail =
      // FastJsonUtil.fromJson(courseExamDetail, ProductExamDetail.class);
      if (StringUtils.isJsonBlank(courseExamDetail)) return null;
      List<ProductExamDetail> examList =
          FastJsonUtil.fromJsons(courseExamDetail, new TypeReference<List<ProductExamDetail>>() {});
      // 先存近期考试（考试院也是这样给数据的）
      if (null != examList && examList.size() > 0) {
        examDetail = examList.get(0);
        Collections.sort(examList, new Comparator<ProductExamDetail>() {
          @Override
          public int compare(ProductExamDetail o1, ProductExamDetail o2) {
            if (o1 == null || o1.getdExamDate() == null)
              return 1;
            else if (o2 == null || o2.getdExamDate() == null)
              return -1;
            else
              return o1.getdExamDate().compareTo(o2.getdExamDate());
          }
        });
        Date now = new Date();
        for (int i = 0; i < examList.size(); i++) {
          examDetail = examList.get(i);
          if (examDetail == null) continue;
          if (null != examDetail.getdExamDate()) {
            if (examDetail.getdExamDate().after(now) || i == examList.size() - 1)
              return examDetail;
          }
        }
      }
    }
    return examDetail;
  }

  public static void main(String[] args) {
    String courseExamDetail =
        "[{\"examDate\":\"2016年04月23日星期六上午09:00-11:30\",\"examDateStr\":\"2016-04-23\",\"sExamDate\":\"2016-04-23\"}]";
    List<ProductExamDetail> examList =
        FastJsonUtil.fromJsons(courseExamDetail, new TypeReference<List<ProductExamDetail>>() {});
    for (ProductExamDetail exam : examList) {
      System.out.println(exam.getExamDate());
      System.out.println(exam.getExamDateStr());
      System.out.println(exam.getdExamDate());
      System.out.println(exam.getExamTime());
    }
  }

  /**
   * 
   * 
   * 根据课程考期，与近期时间，判断课程考期类型（1近期2其它考期）
   * 
   * @deprecated
   * @return
   * @throws ParseException
   */
  public String getExamDateStatus(Long courseId) throws ParseException {
    String examDateStatus = CourseConstant.RECENT_EXAM_DATE_STATUS;
    Integer nowMonth = DateUtil.getMonthOfDate(new Date());
    // String recentExam = this.getRecentExam();// eg:1610
    ProductModel product = productService.findProductById(courseId);
    if (null != product && StringUtils.isNotBlank(product.getExamDateType())) {
      if (CourseConstant.ALL_EXAM_DATE.equals(product.getExamDateType())) {
        return examDateStatus;
      } else {
        if (nowMonth > 3 && nowMonth < 10) {
          if (CourseConstant.DOWN_EXAM_DATE.equals(product.getExamDateType()))
            return examDateStatus;
          else
            return CourseConstant.OTHER_RECENT_EXAM_DATE_STATUS;
        } else {
          if (CourseConstant.UP_EXAM_DATE.equals(product.getExamDateType()))
            return examDateStatus;
          else
            return CourseConstant.OTHER_RECENT_EXAM_DATE_STATUS;
        }
      }
    }
    return CourseConstant.OTHER_RECENT_EXAM_DATE_STATUS;
  }
}
