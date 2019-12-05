package com.xiaodou.course.service.product;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xiaodou.common.util.CommUtil;
import com.xiaodou.course.model.product.ProductItemModel;
import com.xiaodou.course.service.facade.ProductServiceFacade;
import com.xiaodou.summer.dao.pagination.Page;

/**
 * Created by zyp on 15/8/9.
 */
@Service("productItemService")
public class ProductItemService {

  @Resource
  ProductServiceFacade productServiceFacade;

  /**
   * 根据Id查找
   * 
   * @param itemId
   * @return
   */
  public ProductItemModel findItemById(Integer itemId) {
    ProductItemModel cond = new ProductItemModel();
    cond.setId(itemId);
    return productServiceFacade.queryProductItemById(cond);
  }

  /**
   * item列表
   * 
   * @param ids
   * @return
   */
  public List<ProductItemModel> queryItemListByIds(List<Integer> ids) {
    Map<String, Object> cond = new HashMap<>();
    cond.put("ids", ids);
    cond.put("showStatus", 1);
    Map<String, Object> output = new HashMap<>();
    CommUtil.getBasicField(output, ProductItemModel.class);
    Page<ProductItemModel> productItemModelPage =
        productServiceFacade.queryProductItemListByCondWithOutPage(cond, output);
    return productItemModelPage.getResult();
  }

  /**
   * 根据章ID获取item列表
   * 
   * @param chapterId 章ID
   * @return
   */
  public List<ProductItemModel> queryItemListByChapterId(Integer chapterId) {
    Map<String, Object> cond = new HashMap<>();
    cond.put("parentId", chapterId);
    cond.put("showStatus", 1);
    Map<String, Object> output = new HashMap<>();
    CommUtil.getBasicField(output, ProductItemModel.class);
    Page<ProductItemModel> productItemModelPage =
        productServiceFacade.queryProductItemListByCondWithOutPage(cond, output);
    return productItemModelPage.getResult();
  }

  /**
   * 根据productId获取item列表
   * 
   * @param productId
   * @return
   */
  public List<ProductItemModel> queryItemListByProductId(Integer productId) {
    Map<String, Object> cond = new HashMap<String, Object>();
    cond.put("productId", productId);
    cond.put("showStatus", 1);
    Map<String, Object> output = new HashMap<String, Object>();
    CommUtil.getBasicField(output, ProductItemModel.class);
    Page<ProductItemModel> categoryList =
        productServiceFacade.queryProductItemListByCondWithOutPage(cond, output);
    return categoryList.getResult();
  }

}
