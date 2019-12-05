package com.xiaodou.course.service.product;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.course.model.product.ProductCategoryModel;
import com.xiaodou.course.model.product.ProductModel;
import com.xiaodou.course.service.AbstractService;
import com.xiaodou.course.service.queue.QueueService;
import com.xiaodou.course.util.DateUtil;
import com.xiaodou.course.vo.product.ExamDateDetail;
import com.xiaodou.course.web.request.product.ProcuctExamDetailRequest;
import com.xiaodou.course.web.response.product.ProductExamDetailResponse;
import com.xiaodou.course.web.response.resultType.ProductResType;
import com.xiaodou.summer.vo.out.ResultType;

@Service("productExamService")
public class ProductExamService extends AbstractService {
  @Resource
  ProductService productService;

  @Resource
  QueueService queueService;

  @Resource
  ProductCategoryService productCategoryService;

  /**
   * 
   * 根据课程id，专业id，查询距离考期时间值
   * 
   * @param
   * @return
   * @throws ParseException
   */
  public List<ExamDateDetail> findExamDateDetail(Integer catId, String userId, String module)
      throws ParseException {
    List<ExamDateDetail> examList = Lists.newArrayList();
    List<ProductModel> productList = super.cascadeQueryMyProductByCatId(catId, userId, module);
    if (null != productList && productList.size() > 0) {
      Timestamp beginApplyTime = productList.get(0).getBeginApplyTime();
      Timestamp endApplyTime = productList.get(0).getEndApplyTime();
      for (ProductModel productModel : productList) {
        if (!DateUtil.ifIsValid(productModel.getBeginApplyTime(), productModel.getEndApplyTime()))
          continue;
        beginApplyTime =
            beginApplyTime.before(productModel.getBeginApplyTime()) ? beginApplyTime : productModel
                .getBeginApplyTime();
        endApplyTime =
            endApplyTime.after(productModel.getEndApplyTime()) ? endApplyTime : productModel
                .getEndApplyTime();
        ExamDateDetail detail = new ExamDateDetail();
        detail.setCourseId(productModel.getId());
        detail.setCourseName(productModel.getName());
        examList.add(detail);
      }
    }
    return examList;
  }

  /**
   * 查看考试详情(距离考试天数)
   * 
   * @param pojo
   * @return
   */
  public ProductExamDetailResponse getExamDetail(ProcuctExamDetailRequest pojo) {
    ProductExamDetailResponse response = new ProductExamDetailResponse(ResultType.SUCCESS);
    try {
      // 栏目
      ProductCategoryModel productCategory =
          productCategoryService.findCategoryById(Integer.parseInt(pojo.getMajorId()));
      if (null == productCategory)
        return new ProductExamDetailResponse(ProductResType.NotFindCourse);
      List<ExamDateDetail> examList = Lists.newArrayList();
      List<ProductModel> productList =
          super.cascadeQueryMyProductByCatId(Integer.valueOf(productCategory.getId()),
              pojo.getUid(), pojo.getModule());
      // 新手課程
//      List<ProductModel> newbieProductModelList =
//          super.getNewbieProductList(pojo.getModule(), pojo.getUid());
      
      // productList.addAll(newbieProductModelList);
      if (null != productList && productList.size() > 0) {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        Timestamp beginApplyTime = null;
        Timestamp endApplyTime = null;
        for (ProductModel productModel : productList) {
          if (!DateUtil.ifIsValid(productModel.getBeginApplyTime(), productModel.getEndApplyTime()))
            continue;
          if (null != beginApplyTime) {
            beginApplyTime =
                beginApplyTime.before(productModel.getBeginApplyTime())
                    ? beginApplyTime
                    : productModel.getBeginApplyTime();
          } else {
            beginApplyTime = productModel.getBeginApplyTime();
          }
          if (null != endApplyTime) {
            endApplyTime =
                endApplyTime.after(productModel.getEndApplyTime()) ? endApplyTime : productModel
                    .getEndApplyTime();
          } else {
            endApplyTime = productModel.getEndApplyTime();
          }
          ExamDateDetail detail = new ExamDateDetail();
          detail.setCourseId(productModel.getId());
          detail.setCourseName(productModel.getName());
          examList.add(detail);
        }
//        if (null != newbieProductModelList && newbieProductModelList.size() > 0) {
//          ExamDateDetail detail = new ExamDateDetail();
//          for (ProductModel productModel : newbieProductModelList) {
//            detail.setCourseId(productModel.getId());
//            detail.setCourseName(productModel.getName());
//            examList.add(detail);
//          }
//        }
        if (null == beginApplyTime) beginApplyTime = now;
        if (null == endApplyTime) endApplyTime = now;
        response.setIntervalsBetweenExam(DateUtil.getDaysBetween(beginApplyTime, endApplyTime));
        response.setAwayNextExam(DateUtil.getDaysBetween(new Timestamp(System.currentTimeMillis()),
            endApplyTime));
        response.setAwayPreEaxm(DateUtil.getDaysBetween(beginApplyTime,
            new Timestamp(System.currentTimeMillis())));
      }
      response.setExamDateDetail(examList);
    } catch (Exception e) {
      LoggerUtil.error("获取用户购买课程情况与考期详情失败", e);
      response = new ProductExamDetailResponse(ResultType.VALFAIL);
    }
    return response;
  }

  public static void main(String[] args) {
    Timestamp currTime = new Timestamp(System.currentTimeMillis());
    Timestamp preExam = new Timestamp(DateUtil.getRecentExamStr(-1).getTime());
    Timestamp nextExam = new Timestamp(DateUtil.getRecentExamStr(0).getTime());
    /** intervalsBetweenExam 考期间隔天数 */
    Long intervalsBetweenExam = DateUtil.getDaysBetween(preExam, nextExam);
    /** awayPreEaxm 距上次考期已过天数 */
    Long awayPreEaxm = DateUtil.getDaysBetween(preExam, currTime);
    /** awayNextExam 距下次考期剩余天数 */
    Long awayNextExam = DateUtil.getDaysBetween(currTime, nextExam);
    System.out.println("当前时间：" + currTime);
    System.out.println("考期间隔天数:" + intervalsBetweenExam);
    System.out.println("距上次考期已过天数" + awayPreEaxm);
    System.out.println("距下次考期剩余天数" + awayNextExam);
  }
}
