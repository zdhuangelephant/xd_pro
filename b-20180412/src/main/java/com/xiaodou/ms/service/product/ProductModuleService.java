//package com.xiaodou.ms.service.product;
//
//import java.sql.Timestamp;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import javax.annotation.Resource;
//
//import org.springframework.stereotype.Service;
//
//import com.xiaodou.ms.dao.product.ProductModuleDao;
//import com.xiaodou.ms.model.product.ProductModuleModel;
//import com.xiaodou.ms.web.request.product.ProductModuleCreateRequest;
//import com.xiaodou.ms.web.request.product.ProductModuleEditRequest;
//import com.xiaodou.ms.web.response.BaseResponse;
//import com.xiaodou.ms.web.response.ResultType;
//import com.xiaodou.summer.dao.pagination.Page;
//import com.xiaodou.summer.dao.param.IQueryParam;
//
///**
// * Created by zyp on 15/4/19.
// */
//
//@Service("productModuleService")
//public class ProductModuleService {
//
//  /**
//   * 资源分类DAO
//   */
//  @Resource
//  ProductModuleDao productModuleDao;
//
//  /**
//   * 新增目录
//   * 
//   * @param entity
//   * @return
//   */
//  public ProductModuleModel addModule(ProductModuleModel entity) {
//    ProductModuleModel productModuleModel = productModuleDao.addEntity(entity);
//    return productModuleModel;
//  }
//
//  /**
//   * 新增目录
//   * 
//   * @param request
//   * @return
//   */
//  public BaseResponse addModule(ProductModuleCreateRequest request) {
//    ProductModuleModel categoryModel = new ProductModuleModel();
//    categoryModel.setCreateTime(new Timestamp(System.currentTimeMillis()));
//    categoryModel.setDetail(request.getDetail());
//    categoryModel.setName(request.getName());
//    categoryModel.setModule(request.getModule());
//    this.addModule(categoryModel);
//    return new BaseResponse(ResultType.SUCCESS);
//  }
//
//  /**
//   * 删除目录
//   * 
//   * @param id
//   * @return
//   */
//  public Boolean deleteModule(Integer id) {
//    Map<String, Object> cond = new HashMap<>();
//    cond.put("id", id);
//    return productModuleDao.deleteEntity(cond);
//  }
//
//  /**
//   * 更新目录
//   * 
//   * @param entity
//   * @return
//   */
//  public Boolean editModule(ProductModuleModel entity) {
//    Map<String, Object> cond = new HashMap<>();
//    cond.put("id", entity.getId());
//    productModuleDao.updateEntity(cond, entity);
//    return true;
//  }
//
//  /**
//   * 目录编辑
//   * 
//   * @param request
//   * @return
//   */
//  public BaseResponse editModule(ProductModuleEditRequest request) {
//    ProductModuleModel categoryModel = new ProductModuleModel();
//    categoryModel.setDetail(request.getDetail());
//    categoryModel.setName(request.getName());
//    categoryModel.setModule(request.getModule());
//    categoryModel.setId(request.getId());
//    Boolean aBoolean = this.editModule(categoryModel);
//    BaseResponse response = null;
//    if (aBoolean) {
//      response = new BaseResponse(ResultType.SUCCESS);
//    } else {
//      response = new BaseResponse(ResultType.SYS_FAIL);
//    }
//    return response;
//  }
//
//  /**
//   * 根据主键查询
//   * 
//   * @param catId
//   * @return
//   */
//  public ProductModuleModel findModuleById(Long catId) {
//    ProductModuleModel entity = new ProductModuleModel();
//    entity.setId(catId);
//    return productModuleDao.findEntityById(entity);
//  }
//
//  /**
//   * 获取所有栏目
//   * 
//   * @return
//   */
//  public List<ProductModuleModel> queryAllModule() {
//    Map<String, Object> cond = new HashMap<String, Object>();
//    Map<String, Object> output = new HashMap<>();
//    output.put("id", "");
//    output.put("module", "");
//    output.put("showStatus", "");
//    output.put("name", "");
//    output.put("detail", "");
//    output.put("misc", "");
//    output.put("createTime", "");
//    output.put("updateTime", "");
//    Page<ProductModuleModel> categoryList =
//        productModuleDao.queryListByCond0(cond, output, null);
//    return categoryList.getResult();
//  }
//
//public Page<ProductModuleModel> findModuleByCond(IQueryParam param,Page page) {
//	return productModuleDao.findEntityListByCond(param, page);
//}
//
//
//}
