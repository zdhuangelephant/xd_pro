//package com.xiaodou.course;
//
//import java.util.List;
//import java.util.Map;
//
//import org.junit.Test;
//import org.unitils.spring.annotation.SpringBean;
//
//import com.google.common.collect.Maps;
//import com.xiaodou.common.util.CommUtil;
//import com.xiaodou.course.model.user.UserProductOrderModel;
//import com.xiaodou.course.service.facade.ProductServiceFacade;
//import com.xiaodou.course.service.product.UserProductOrderService;
//
//public class UserProductOrderDao extends BaseUnitils {
//
//
//  @SpringBean("userProductOrderService")
//  UserProductOrderService userProductOrderService;
//
//  @SpringBean("productServiceFacade")
//  ProductServiceFacade productServiceFacade;
//
//  @Test
//  public void get1() {
//    // 刚购买的课程，默认为近期
//    Map<String, Object> _input = Maps.newHashMap();
//    _input.put("userId", 94);
//    _input.put("isExp", "1");
//    _input.put("examDateStatus", "1");
//    _input.put("normalDate", "1");
//    _input.put("typeCode", "01A0820");
//    List<UserProductOrderModel> _userProductOrderList =
//        productServiceFacade.queryUserProductOrderByCond(_input,
//            CommUtil.getAllField(UserProductOrderModel.class));
//    // userProductOrderDao.queryList(cond, output);
//    System.out.println(_userProductOrderList.size());
//  }
//}
