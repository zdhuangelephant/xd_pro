//package com.xiaodou.course;
//
//import org.junit.Test;
//import org.unitils.spring.annotation.SpringBean;
//
//import com.xiaodou.course.dao.product.MajorDataDao;
//import com.xiaodou.course.model.product.MajorDataModel;
//import com.xiaodou.course.service.product.ProductCategoryService;
//import com.xiaodou.course.web.request.product.MajorDetailRequest;
//import com.xiaodou.course.web.response.product.MajorDetailResponse;
//
//public class MajorDataDaoTest extends BaseUnitils {
//
//  @SpringBean("majorDataDao")
//  MajorDataDao majorDataDao;
//  
//  @SpringBean("productCategoryService")
//  ProductCategoryService productCategoryService;
//  
//  //@Test
//  public void queryEntity() {
//    try {
//      MajorDataModel entity = new MajorDataModel();
//      entity.setId("01A0103");
//      MajorDataModel entity1 = majorDataDao.findEntityById(entity);
//      System.out.println(entity1.getId());
//      System.out.println(entity1.getName());
//    } catch (Exception e) {
//      throw e;
//    }
//  }
//  
//  @Test
//  public void getMajorDetail(){
//    MajorDetailRequest detailRequest = new MajorDetailRequest();
//    detailRequest.setMajorId("52");
//    detailRequest.setModule("2");
//    detailRequest.setTypeCode("01A0103");
//    detailRequest.setUid("100");
//    MajorDetailResponse response = productCategoryService.majorDetail(detailRequest);
//    System.out.println(response.toString0());
//  }
//}
