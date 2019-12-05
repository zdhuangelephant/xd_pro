package com.xiaodou.dao;

import java.util.Map;

import org.junit.Test;
import org.springframework.util.Assert;
import org.unitils.spring.annotation.SpringBean;

import com.google.common.collect.Maps;
import com.xiaodou.dao.product.CourseProductDao;
import com.xiaodou.dao.product.CourseProductItemDao;
import com.xiaodou.domain.product.CourseProduct;


public class ProductDaoTest extends BaseUnitils {

  private static String productId = "10289153";
  private static String module = "2";
  private static String typeCode = "01B0222";
  private static String uid = "1";

  private static String chapterId = "10289232";
  private static String itemId = "10289229";

  @SpringBean("courseProductDao")
  CourseProductDao courseProductDao;

  @SpringBean("courseProductItemDao")
  CourseProductItemDao courseProductItemDao;

  @Test
  public void courseProduct() {
    try {
      CourseProduct courseProduct = courseProductDao.selectById(productId);
      Assert.notNull(courseProduct, "courseProduct");
      System.out.println(courseProduct.toString());
      Assert.notNull(courseProductDao.selectByModuleAndTypeCode(module, typeCode));
      courseProductDao.selectTypeCodeByModuleAndCourseId(module, productId);
      Map<String, Object> params = Maps.newHashMap();
      courseProductDao.selectBuyProductByCond(params);

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Test
  public void courseProductItem() {

    try {
      // Object o =''
      courseProductItemDao.selectByProductId(productId);
      courseProductItemDao.selectByProductIdAndUserId(productId, uid);
      courseProductItemDao.selectChapterByProductId(productId);
      courseProductItemDao.selectItemByProductIdAndChapterId(productId, "10289232");
      courseProductItemDao.selectItemByProductIdAndChapterIdAndItemId(productId, chapterId, itemId);
      courseProductItemDao.selectChapterByProductIdAndChapterId(productId, chapterId);
      // System.out.println(o.toString());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }


}
