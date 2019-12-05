//package com.xiaodou.course;
//
//import java.text.ParseException;
//import java.util.Date;
//
//import org.junit.Test;
//import org.unitils.spring.annotation.SpringBean;
//
//import com.xiaodou.course.dao.product.MajorCourseDao;
//import com.xiaodou.course.model.product.MajorCourseModel;
//import com.xiaodou.course.service.AbstractService;
//import com.xiaodou.course.service.product.ProductService;
//import com.xiaodou.course.web.request.product.ProductDetailRequest;
//import com.xiaodou.course.web.response.product.ProductDetailResponse;
//
//public class MajorCourseDaoTest extends BaseUnitils {
//
//
//  @SpringBean("majorCourseDao")
//  MajorCourseDao majorCourseDao;
//
//  @SpringBean("abstractService")
//  AbstractService abstractService;
//
//  @SpringBean("productService")
//  ProductService productService;
//
//  @Test
//  public void queryEntity() {
//    try {
//      MajorCourseModel entity = new MajorCourseModel();
//      entity.setId("00009");
//      MajorCourseModel entity1 = majorCourseDao.findEntityById(entity);
//      System.out.println(entity1.getId());
//    } catch (Exception e) {
//      throw e;
//    }
//  }
//
//  @Test
//  public void get1() throws ParseException {
//    Date date = abstractService.gainRecentExamDate("00009");
//    System.out.println(date.toString());
//  }
//
//  @Test
//  public void ProductDetail() {
//    ProductDetailRequest detailRequest = new ProductDetailRequest();
//    detailRequest.setCourseId("45");
//    detailRequest.setModule("2");
//    detailRequest.setTypeCode("00009");
//    detailRequest.setUid("100");
//    ProductDetailResponse response = productService.productDetail(detailRequest);
//    System.out.println(response.toString0());
//  }
//
//}
