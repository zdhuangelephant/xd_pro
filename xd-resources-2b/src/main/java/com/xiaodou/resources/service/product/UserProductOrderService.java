package com.xiaodou.resources.service.product;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;
import com.xiaodou.common.util.CommUtil;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.resources.constant.product.CourseConstant;
import com.xiaodou.resources.model.product.ProductItemModel;
import com.xiaodou.resources.model.user.UserChapterRecordModel;
import com.xiaodou.resources.model.user.UserProductOrderModel;
import com.xiaodou.resources.request.product.UserProductOpenRequest;
import com.xiaodou.resources.request.product.UserProductOrderRequest;
import com.xiaodou.resources.response.BaseResponse;
import com.xiaodou.resources.response.product.UserProductOrderResponse;
import com.xiaodou.resources.response.resultype.ProductResType;
import com.xiaodou.resources.service.product.facade.ProductServiceFacade;
import com.xiaodou.summer.vo.out.ResultType;

@Service("userProductOrderService")
public class UserProductOrderService extends AbstractService {

  @Resource
  ProductServiceFacade productServiceFacade;
  @Resource
  ProductService productService;
  @Resource
  ProductItemService productItemService;
  // @Resource
  // CourseExamTypeService courseExamTypeService;
  @Resource
  ProductCategoryService productCategoryService;


  /**
   * 购买课程
   * 
   * @param UserProductOrderRequest pojo
   * @throws Exception
   */
  public UserProductOrderResponse purchaseProduct(UserProductOrderRequest pojo) {
    UserProductOrderResponse response = new UserProductOrderResponse(ResultType.SUCCESS);
    try {
      if (!super.checkCourseId(pojo.getCourseId()).isRetOk())
        return new UserProductOrderResponse(super.checkCourseId(pojo.getCourseId()).getResType());

      Map<String, Object> input = Maps.newHashMap();
      input.put("userId", pojo.getUid());
      input.put("courseId", pojo.getCourseId());
      input.put("isExp", CourseConstant.IS_EXP);
      // input.put("normalDate", "1");
      input.put("nowTime", new Timestamp(super.getAheadOfTime()));
      List<UserProductOrderModel> userProductOrderList =
          productServiceFacade.queryUserProductOrderByCond(input,
              CommUtil.getAllField(UserProductOrderModel.class));
      if (null != userProductOrderList && userProductOrderList.size() > 0)
        return new UserProductOrderResponse(ProductResType.HasOrder);
      // 添加逻辑
      UserProductOrderModel userProductOrderModel = new UserProductOrderModel();
      userProductOrderModel.setCourseId(Long.valueOf(pojo.getCourseId()));
      userProductOrderModel.setUserId(Long.valueOf(pojo.getUid()));
      userProductOrderModel.setOrderStatus(Short.valueOf(pojo.getOrderStatus()));
      userProductOrderModel.setOrderTime(new Timestamp(System.currentTimeMillis()));
      Calendar calendar = Calendar.getInstance();
      calendar.setTime(new Date());
      calendar.add(Calendar.YEAR, 1);
      userProductOrderModel.setExpDate(new Timestamp(calendar.getTime().getTime()));
      if (null == productServiceFacade.insertUserProductOrder(userProductOrderModel))
        return new UserProductOrderResponse(ProductResType.OrderFailed);
    } catch (Exception e) {
      LoggerUtil.error("购买课程出错", e);
      response = new UserProductOrderResponse(ResultType.SYSFAIL);
    }
    return response;
  }

  /**
   * 开通课程
   * 
   * @param UserProductOrderRequest pojo
   * @throws IllegalAccessException
   * @throws InstantiationException
   */
  public BaseResponse openProduct(UserProductOpenRequest pojo) throws InstantiationException,
      IllegalAccessException {
    BaseResponse response = new BaseResponse(ResultType.SUCCESS);
    UserProductOrderModel userProductOrderModel = new UserProductOrderModel();
    userProductOrderModel.setUserId(Long.valueOf(pojo.getUid()));
    userProductOrderModel.setOrderStatus(Short.valueOf(pojo.getOrderStatus()));
    userProductOrderModel.setOrderTime(new Timestamp(System.currentTimeMillis()));
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(new Date());
    calendar.add(Calendar.YEAR, 1);
    userProductOrderModel.setExpDate(new Timestamp(calendar.getTime().getTime()));
    userProductOrderModel.setExamDateStatus(Short.valueOf(CourseConstant.RECENT_EXAM_DATE_STATUS));
    Map<String, Object> input = Maps.newHashMap();
    input.put("userId", pojo.getUid());
    input.put("isExp", CourseConstant.IS_EXP);
    input.put("nowTime", new Timestamp(super.getAheadOfTime()));
    List<UserProductOrderModel> userProductOrderList =
        productServiceFacade.queryUserProductOrderByCond(input,
            CommUtil.getAllField(UserProductOrderModel.class));
    if (null != userProductOrderList && userProductOrderList.size() > 0)
      return new UserProductOrderResponse(ProductResType.HasOrder);
    userProductOrderModel = productServiceFacade.insertUserProductOrder(userProductOrderModel);
    return response;
  }

  /**
   * 解锁课程
   * 
   * @param courseId
   * @param userId
   * @return
   */
  @SuppressWarnings("unused")
  private boolean unlockProduct(String courseId, String userId) {
    boolean flag = true;
    // 1、用户章记录表里面的添加该课程下 的第一章为已解锁
    // 1.1 查找课程下的第一章 章id
    List<ProductItemModel> defaultChapterList =
        productItemService.queryCourseItemList(courseId, null, null);
    if (null == defaultChapterList || defaultChapterList.size() < 1) return false;
    ProductItemModel defaultChapter = defaultChapterList.get(0);// 第一章
    List<ProductItemModel> defaultItemList =
        productItemService.queryCourseItemList(courseId, defaultChapter.getId(), null);
    if (null == defaultItemList || defaultItemList.size() < 1) return false;
    ProductItemModel defaultItem = defaultItemList.get(0);// 第一节
    // 1.2 添加章节记录表数据，为已经解锁
    UserChapterRecordModel userChapterRecordModel = new UserChapterRecordModel();
    userChapterRecordModel.setCourseId(Long.valueOf(courseId));
    userChapterRecordModel.setChapterId(Long.valueOf(defaultChapter.getId()));//
    userChapterRecordModel.setItemId(Long.valueOf(defaultItem.getId()));
    userChapterRecordModel.setStarLevel("0");// 星级 0 0颗 1 一星 2 两心 3 三星
    userChapterRecordModel.setStatus("1");// 状态 0 未完成 1 已解锁 2 已完成
    userChapterRecordModel.setUserId(Long.valueOf(userId));
    Map<String, Object> _input = Maps.newHashMap();
    CommUtil.transferFromVO2Map(_input, userChapterRecordModel);
    List<UserChapterRecordModel> chapterRecordList =
        productServiceFacade.queryUserChapterRecordByCond(_input,
            CommUtil.getAllField(UserChapterRecordModel.class));
    if (null == chapterRecordList || chapterRecordList.size() < 1)
      userChapterRecordModel = productServiceFacade.insertUserChapterRecord(userChapterRecordModel);
    return flag;
  }


}
