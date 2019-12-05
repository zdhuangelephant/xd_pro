//package com.xiaodou.course;
//
//import java.text.ParseException;
//
//import org.junit.Test;
//import org.unitils.spring.annotation.SpringBean;
//
//import com.xiaodou.course.dao.product.MajorDataDao;
//import com.xiaodou.course.dao.product.ProductDao;
//import com.xiaodou.course.service.AbstractService;
//import com.xiaodou.course.service.product.ProductService;
//import com.xiaodou.course.service.product.UserProductOrderService;
//import com.xiaodou.course.web.request.BaseRequest;
//import com.xiaodou.course.web.request.product.ModifyExamDateRequest;
//
//public class ProductDaoTest extends BaseUnitils {
//
//  @SpringBean("majorDataDao")
//  MajorDataDao majorDataDao;
//
//  @SpringBean("productdao")
//  ProductDao productDao;
//
//  @SpringBean("productService")
//  ProductService productService;
//
//  @SpringBean("userProductOrderService")
//  UserProductOrderService userProductOrderService;
//
//  @SpringBean("abstractService")
//  AbstractService abstractService;
//  @Test
//  public void queryProduct(){
////    productService.cascadeQueryMyProductByCatId(52, "1", "100", "2");
//    
//    BaseRequest pojo = new BaseRequest();
//    pojo.setModule("2");
//    pojo.setTypeCode("01A0103");
//    pojo.setUid("100");
////    System.out.println(productService.addCourseList(pojo).toString0());
////    System.out.println(productService.home(pojo).toString0());
//    
////    System.out.println(productService.cascadeQueryProductByTypeCodeAndCId("01A0103","2", "42"));
////    ModifyExamDateRequest _pojo = new ModifyExamDateRequest();
////    _pojo.setCourseId("42");
////    _pojo.setModule("2");
////    _pojo.setMoveType("2");
////    _pojo.setTypeCode("01A0103");
////    _pojo.setUid("100");
////    System.out.println(productService.modifyExamDate(_pojo).toString0());
//    try {
//      System.out.println(abstractService.checkCourseId(pojo, "42"));
//      
//    } catch (Exception e) {
//      e.printStackTrace();
//    }
////    UserProductOrderRequest pojo = new UserProductOrderRequest() ;
////    pojo.setCourseId("42");
////    pojo.setModule("2");
////    pojo.setOrderStatus("1");
////    pojo.setTypeCode("01A0103");
////    pojo.setUid("100");
////    System.out.println(userProductOrderService.purchaseProduct(pojo).toString0());
//  }
//}


