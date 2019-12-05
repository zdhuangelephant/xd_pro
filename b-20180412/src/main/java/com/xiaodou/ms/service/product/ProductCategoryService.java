package com.xiaodou.ms.service.product;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.ms.constants.XdmsConstant;
import com.xiaodou.ms.dao.product.ProductCategoryDao;
import com.xiaodou.ms.model.major.MajorDataModel;
import com.xiaodou.ms.model.major.MajorInfo;
import com.xiaodou.ms.model.product.ProductCategoryModel;
import com.xiaodou.ms.model.robot.ChallengeRobotModel;
import com.xiaodou.ms.service.major.MajorDataService;
import com.xiaodou.ms.util.tree.TreeNode;
import com.xiaodou.ms.util.tree.TreeUtils;
import com.xiaodou.ms.vo.mq.AddProductCategoryEvent;
import com.xiaodou.ms.vo.mq.AddProductCategoryEvent.TransferProductCategoryData;
import com.xiaodou.ms.vo.mq.DeleteProductCategoryEvent;
import com.xiaodou.ms.vo.mq.ModifyProductCategoryEvent;
import com.xiaodou.ms.web.request.product.ProductCategoryCreateRequest;
import com.xiaodou.ms.web.request.product.ProductCategoryEditRequest;
import com.xiaodou.ms.web.request.product.ProductCategoryQueryConditionReq;
import com.xiaodou.ms.web.response.ResultType;
import com.xiaodou.ms.web.response.course.CategoryCreateResponse;
import com.xiaodou.ms.web.response.course.CategoryEditResponse;
import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.summer.dao.param.IQueryParam;
import com.xiaodou.summer.dao.param.QueryParam;

/**
 * Created by zyp on 15/4/19.
 */

@Service("productCategoryService")
public class ProductCategoryService {

  /**
   * 资源分类DAO
   */
  @Resource
  ProductCategoryDao productCategoryDao;
  /**
   * 资源分类DAO
   */
  @Resource
  MajorDataService majorDataService;


  /**
   * 目录列表
   * 
   * @author zhouhaun
   * @return
   */
  public Page<ProductCategoryModel> getProductCategorylist(ProductCategoryQueryConditionReq req,
      Integer pageNo, Long parentId, Long module, Integer classify) {
    Page<ChallengeRobotModel> page = new Page<ChallengeRobotModel>();
    page.setPageNo(pageNo);
    page.setPageSize(10); // 20

    Page<ProductCategoryModel> categoryList =
        this.queryAllChildCategory(req, page, parentId, module, classify);
    // Map<String, TreeNode> treeNodeMap = Maps.newHashMap();
    for (ProductCategoryModel productCategory : categoryList.getResult()) {
      TreeNode treeNode = new TreeNode();
      treeNode.setId(productCategory.getId().toString());
      treeNode.setName(productCategory.getName());
      treeNode.setParentId(productCategory.getParentId().toString());
      treeNode.setData(productCategory);
      // treeNodeMap.put(productCategory.getId().toString(), treeNode);
      MajorInfo majorInfo = majorDataService.getMajorInfo(productCategory.getMajorInfo());
      productCategory.setMajorInfoModel(majorInfo);
    }
    return categoryList;
  }


  /**
   * 新增目录
   * 
   * @param entity
   * @return
   */
  public ProductCategoryModel addCategory(ProductCategoryModel entity) {
    if (null != entity && entity.getUpdateTime() == null) {
      entity.setUpdateTime(new Timestamp(System.currentTimeMillis()));
    }
    ProductCategoryModel productCategoryModel = productCategoryDao.addEntity(entity);
    MajorDataModel majorData =
        majorDataService.findMajorByIdAndRegion(entity.getTypeCode(), entity.getModule());
    productCategoryModel.setName(majorData.getName());
    if (null != productCategoryModel.getIsSync()
        && XdmsConstant.YES.equals(productCategoryModel.getIsSync().toString())) {
      AddProductCategoryEvent addProductCategoryEvent = new AddProductCategoryEvent();
      addProductCategoryEvent.setModule(productCategoryModel.getModule());
      addProductCategoryEvent.setDataModel(new TransferProductCategoryData(productCategoryModel));
      addProductCategoryEvent.send();
    }
    this.cleanTree();
    return productCategoryModel;
  }

  /**
   * 新增目录
   * 
   * @param request
   * @return
   */
  public CategoryCreateResponse addCategory(ProductCategoryCreateRequest request) {
    Map<String, Object> input = Maps.newHashMap();
    input.put("module", request.getModule());
    input.put("typeCode", request.getTypeCode());
    Map<String, Object> output = Maps.newHashMap();
    output.put("id", "1");
    Page<ProductCategoryModel> categoryModelPage =
        productCategoryDao.queryListByCond0(input, output, null);
    if (categoryModelPage != null && categoryModelPage.getResult() != null
        && categoryModelPage.getResult().size() > 0)
      return new CategoryCreateResponse(ResultType.DUPLICATE_CATEGORY);
    ProductCategoryModel categoryModel = new ProductCategoryModel();
    categoryModel.setTypeCode(request.getTypeCode());
    categoryModel.setModule(request.getModule());
    categoryModel.setCreateTime(new Timestamp(System.currentTimeMillis()));
    categoryModel.setUpdateTime(new Timestamp(System.currentTimeMillis()));
    categoryModel.setDetail(request.getDetail());
    // categoryModel.setName("");
    categoryModel.setName(request.getMajorName());
    categoryModel.setPictureUrl(request.getPictureUrl());
    categoryModel.setShowCover(request.getShowCover());
    categoryModel.setClassify(1);// '专业分类 1 自考专业 2 特设专业',
    categoryModel.setParentId(request.getParentId());
    categoryModel.setShowStatus(request.getShowStatus());
    categoryModel.setIsCooperation(request.getIsCooperation());
    categoryModel.setIsSync(request.getIsSync());
    categoryModel.setIsBuy(request.getIsBuy());
    categoryModel.setChiefAcademy(request.getChiefAcademy());

    ProductCategoryModel resultModel = this.addCategory(categoryModel);
    CategoryCreateResponse response = new CategoryCreateResponse(ResultType.SUCCESS);
    response.setCatId(resultModel.getId());
    return response;
  }

  /**
   * 删除目录
   * 
   * @param catId
   * @return
   */
  public Boolean deleteCategory(Long catId) {
    ProductCategoryModel entity = new ProductCategoryModel();
    entity.setId(catId);
    entity = productCategoryDao.findEntityById(entity);
    if (null != entity) {
      Map<String, Object> cond = new HashMap<>();
      cond.put("id", catId);
      if (productCategoryDao.deleteEntity(cond)) {
        DeleteProductCategoryEvent deleteProductCategoryEvent = new DeleteProductCategoryEvent();
        deleteProductCategoryEvent.setModule(entity.getModule());
        deleteProductCategoryEvent.setDataModel(new TransferProductCategoryData(catId, entity
            .getMajorInfo()));
        deleteProductCategoryEvent.send();
      }
    }
    /* return this.cleanTree(); */
    return true;
  }

  /**
   * 更新目录
   * 
   * @param entity
   * @return
   */
  public Boolean editCategory(ProductCategoryModel entity) {
    Map<String, Object> cond = new HashMap<>();
    cond.put("id", entity.getId());
    if (productCategoryDao.updateEntity(cond, entity)) {
      ModifyProductCategoryEvent addProductCategoryEvent = new ModifyProductCategoryEvent();
      addProductCategoryEvent.setModule(addProductCategoryEvent.getModule());
      addProductCategoryEvent.setDataModel(new TransferProductCategoryData(entity));
      addProductCategoryEvent.send();
    }
    if (entity.getParentId() != null) {
      return this.cleanTree();
    } else {
      return true;
    }
  }

  /**
   * 目录编辑
   * 
   * @param request
   * @return
   */
  public CategoryEditResponse editCategory(ProductCategoryEditRequest request) {

    ProductCategoryModel find = new ProductCategoryModel();
    find.setId(request.getId());
    ProductCategoryModel findEntityById = productCategoryDao.findEntityById(find);

    Map<String, Object> input = Maps.newHashMap();
    input.put("module", request.getModule());
    input.put("typeCode", findEntityById.getTypeCode());
    Map<String, Object> output = Maps.newHashMap();
    output.put("id", "1");
    output.put("module", "1");
    Page<ProductCategoryModel> categoryModelPage =
        productCategoryDao.queryListByCond0(input, output, null);
    if (categoryModelPage != null && categoryModelPage.getResult() != null
        && categoryModelPage.getResult().size() > 0) {
      if (!request.getModule().equals(findEntityById.getModule())) {
        return new CategoryEditResponse(ResultType.DUPLICATE_CATEGORY);
      }
    }
    ProductCategoryModel categoryModel = new ProductCategoryModel();
    categoryModel.setDetail(request.getDetail());
    categoryModel.setName(request.getName());
    categoryModel.setShowStatus(request.getShowStatus());
    if (StringUtils.isNotBlank(request.getPictureUrl()))
      categoryModel.setPictureUrl(request.getPictureUrl());
    if (StringUtils.isNotBlank(request.getShowCover()))
      categoryModel.setShowCover(request.getShowCover());
    categoryModel.setModule(request.getModule());
    categoryModel.setTypeCode(request.getTypeCode());
    categoryModel.setParentId(request.getParentId());
    categoryModel.setId(request.getId());
    categoryModel.setUpdateTime(new Timestamp(System.currentTimeMillis()));
    categoryModel.setIsCooperation(request.getIsCooperation());
    categoryModel.setIsSync(request.getIsSync());
    categoryModel.setIsBuy(request.getIsBuy());
    categoryModel.setCourseCount(request.getCourseCount());

    Boolean aBoolean = this.editCategory(categoryModel);
    CategoryEditResponse response = null;
    if (aBoolean) {
      response = new CategoryEditResponse(ResultType.SUCCESS);
    } else {
      response = new CategoryEditResponse(ResultType.SYS_FAIL);
    }
    return response;
  }

  /**
   * 根据主键查询
   * 
   * @param catId
   * @return
   */
  public ProductCategoryModel findCategoryById(Long catId) {
    ProductCategoryModel entity = new ProductCategoryModel();
    entity.setId(catId);
    return productCategoryDao.findEntityById(entity);
  }

  /**
   * 根据地域查询产品分类 module
   * 
   * @return
   */
  public Page<ProductCategoryModel> cascadeQueryChannelByModule(Integer module) {
    Map<String, Object> cond = new HashMap<>();
    cond.put("module", module);
    Map<String, Object> output = new HashMap<>();
    output.put("id", "");
    output.put("parentId", "");
    output.put("childId", "");
    output.put("showStatus", "");
    output.put("allParentId", "");
    output.put("allChildId", "");
    output.put("level", "");
    output.put("name", "");
    output.put("pictureUrl", "");
    output.put("courseCategoryType", "");
    output.put("detail", "");
    output.put("misc", "");
    output.put("createTime", "");
    output.put("updateTime", "");
    output.put("isLeaf", "");
    output.put("module", "");
    output.put("moduleName", "");
    output.put("typeCode", "");
    output.put("isCooperation", "");
    output.put("isSync", "");
    output.put("isBuy", "");
    output.put("courseCount", "");
    return productCategoryDao.cascadeQueryProduct(cond, output);
  }

  /**
   * 下拉框目录列表
   * 
   * @return
   */

  /*
   * public String categorySelectTree(Long parentId, Integer classify, String selectedId) {
   * List<ProductCategoryModel> categoryList = this.queryAllChildCategory(parentId, classify);
   * Map<String, TreeNode> treeNodeMap = Maps.newHashMap(); for (ProductCategoryModel
   * productCategory : categoryList) { TreeNode treeNode = new TreeNode();
   * treeNode.setId(productCategory.getId().toString());
   * treeNode.setName(productCategory.getName());
   * treeNode.setParentId(productCategory.getParentId().toString());
   * treeNode.setData(productCategory); treeNodeMap.put(productCategory.getId().toString(),
   * treeNode); } TreeUtils treeUtils = new TreeUtils(treeNodeMap); String result =
   * treeUtils.getTree(parentId.toString(),
   * "<option value=\"${node.id}\" ${selected}>${spacer}${node.name}</option>", selectedId, null,
   * null); return result; }
   */
  public String categorySelectTree(Long parentId, Integer classify, String selectedId) {
    Page<ProductCategoryModel> categoryList =
        this.queryAllChildCategory(null, null, parentId, classify);
    Map<String, TreeNode> treeNodeMap = Maps.newHashMap();
    if (categoryList != null) {
      for (ProductCategoryModel productCategory : categoryList.getResult()) {
        TreeNode treeNode = new TreeNode();
        treeNode.setId(productCategory.getId().toString());
        treeNode.setName(productCategory.getName());
        treeNode.setParentId(productCategory.getParentId().toString());
        treeNode.setData(productCategory);
        treeNodeMap.put(productCategory.getId().toString(), treeNode);
      }
    }

    TreeUtils treeUtils = new TreeUtils(treeNodeMap);
    String result =
        treeUtils.getTree(parentId.toString(),
            "<option value=\"${node.id}\" ${selected}>${spacer}${node.name}</option>", selectedId,
            null, null);
    return result;
  }

  /**
   * 获取所有子地区
   * 
   * @param id 栏目id
   * @param module
   * @return
   */

  /*
   * public List<ProductCategoryModel> queryAllChildCategory(Long id, Long module, Integer classify)
   * { if (id == 0) { return this.queryAllCategory(module, classify); } ProductCategoryModel
   * productCategory = this.findCategoryById(id); if (productCategory == null) { return new
   * ArrayList<>(); } if (StringUtils.isNotBlank(productCategory.getAllChildId())) { String[] ids =
   * productCategory.getAllChildId().split(","); List<Integer> idList = new ArrayList<>(); for
   * (String idString : ids) { idList.add(Integer.parseInt(idString)); } return
   * this.queryCategoryByIds(idList, classify); } else { return new ArrayList<>(); } }
   */
  public Page<ProductCategoryModel> queryAllChildCategory(ProductCategoryQueryConditionReq req,
      Page page, Long id, Long module, Integer classify) {
    if (id == 0) {
      return this.queryAllCategory(req, page, module, classify);
    }
    ProductCategoryModel productCategory = this.findCategoryById(id);
    if (productCategory == null) {
      return new Page<ProductCategoryModel>();
    }
    if (StringUtils.isNotBlank(productCategory.getAllChildId())) {
      String[] ids = productCategory.getAllChildId().split(",");
      List<Integer> idList = new ArrayList<>();
      for (String idString : ids) {
        idList.add(Integer.parseInt(idString));
      }
      return this.queryCategoryByIds(req, page, idList, classify);
    } else {
      return new Page<ProductCategoryModel>();
    }
  }

  /**
   * 获取所有子地区
   * 
   * @param id 栏目id
   * @return
   */

  /*
   * public List<ProductCategoryModel> queryAllChildCategory(Long id, Integer classify) { if (id ==
   * 0) { return this.queryAllCategory(classify); } ProductCategoryModel productCategory =
   * this.findCategoryById(id); if (productCategory == null) { return new ArrayList<>(); } if
   * (StringUtils.isNotBlank(productCategory.getAllChildId())) { String[] ids =
   * productCategory.getAllChildId().split(","); List<Integer> idList = new ArrayList<>(); for
   * (String idString : ids) { idList.add(Integer.parseInt(idString)); } return
   * this.queryCategoryByIds(idList, classify); } else { return new ArrayList<>(); } }
   */

  public Page<ProductCategoryModel> queryAllChildCategory(ProductCategoryQueryConditionReq req,
      Page page, Long id, Integer classify) {
    if (id == 0) {
      return this.queryAllCategory(req, page, classify);
    }
    ProductCategoryModel productCategory = this.findCategoryById(id);
    if (productCategory == null) {
      return new Page<ProductCategoryModel>();
    }
    if (StringUtils.isNotBlank(productCategory.getAllChildId())) {
      String[] ids = productCategory.getAllChildId().split(",");
      List<Integer> idList = new ArrayList<>();
      for (String idString : ids) {
        idList.add(Integer.parseInt(idString));
      }
      return this.queryCategoryByIds(req, page, idList, classify);
    } else {
      return new Page<ProductCategoryModel>();
    }
  }

  /**
   * 根据父Id查询地区
   * 
   * @param ids
   * @return
   */
  /*
   * public List<ProductCategoryModel> queryCategoryByIds(List<Integer> ids, Integer classify) {
   * Map<String, Object> cond = new HashMap<String, Object>(); cond.put("ids", ids);
   * cond.put("classify", classify); Map<String, Object> output = new HashMap<>(); output.put("id",
   * ""); output.put("parentId", ""); output.put("name", ""); output.put("pictureUrl", "");
   * output.put("showCover", ""); output.put("classify", ""); output.put("showCover", "");
   * output.put("detail", ""); output.put("createTime", ""); output.put("childId", "");
   * output.put("allChildId", ""); output.put("allParentId", ""); output.put("level", "");
   * output.put("isLeaf", ""); output.put("module", ""); output.put("typeCode", "");
   * Page<ProductCategoryModel> categoryList = productCategoryDao.queryListByCond0(cond, output,
   * null); return categoryList.getResult(); }
   */
  public Page<ProductCategoryModel> queryCategoryByIds(ProductCategoryQueryConditionReq req,
      Page page, List<Integer> ids, Integer classify) {
    Map<String, Object> cond = new HashMap<String, Object>();
    cond.put("ids", ids);
    cond.put("classify", classify);

    //
    INPUT: {
      if (req == null) break INPUT;
      if (StringUtils.isNotBlank(req.getExamSchool())) {
        cond.put("examSchoolLike", req.getExamSchool());
      }
      if (StringUtils.isNotBlank(req.getMajorName())) {
        cond.put("majorNameLike", req.getMajorName());
      }
      if (StringUtils.isNotBlank(req.getModule()) && !req.getModule().equals("-1")) {
        cond.put("module", req.getModule());
      }
      if (StringUtils.isNotBlank(req.getTypeCode())) {
        cond.put("typeCodeLike", req.getTypeCode());
      }
      if (StringUtils.isNotBlank(req.getModule())) {
        cond.put("module", req.getModule());
      }
    }


    Map<String, Object> output = new HashMap<>();
    output.put("id", "");
    output.put("parentId", "");
    output.put("name", "");
    output.put("pictureUrl", "");
    output.put("showCover", "");
    output.put("classify", "");
    output.put("showCover", "");
    output.put("detail", "");
    output.put("createTime", "");
    output.put("childId", "");
    output.put("allChildId", "");
    output.put("allParentId", "");
    output.put("level", "");
    output.put("isLeaf", "");
    output.put("module", "");
    output.put("moduleName", "");
    output.put("typeCode", "");
    output.put("isCooperation", "");
    output.put("isSync", "");
    output.put("isBuy", "");
    output.put("courseCount", "");
    return productCategoryDao.queryListByCond0(cond, output, page);
  }

  /**
   * 根据父Id查询地区
   * 
   * @param parentId
   * @return
   */
  public List<ProductCategoryModel> queryCategoryByParentId(Integer parentId, Integer classify) {
    Map<String, Object> cond = new HashMap<String, Object>();
    cond.put("parentId", parentId);
    cond.put("classify", classify);
    Map<String, Object> output = new HashMap<>();
    output.put("id", "");
    output.put("parentId", "");
    output.put("name", "");
    output.put("pictureUrl", "");
    output.put("showCover", "");
    output.put("detail", "");
    output.put("createTime", "");
    output.put("childId", "");
    output.put("allChildId", "");
    output.put("allParentId", "");
    output.put("level", "");
    output.put("isLeaf", "");
    output.put("module", "");
    output.put("typeCode", "");
    output.put("isCooperation", "");
    output.put("isSync", "");
    output.put("isBuy", "");
    output.put("courseCount", "");
    Page<ProductCategoryModel> categoryList =
        productCategoryDao.queryListByCond0(cond, output, null);
    return categoryList.getResult();
  }

  /**
   * 获取所有栏目
   * 
   * @return
   */
  /*
   * public List<ProductCategoryModel> queryAllCategory(Integer classify) { Map<String, Object> cond
   * = new HashMap<String, Object>(); cond.put("classify", classify); Map<String, Object> output =
   * new HashMap<>(); output.put("id", ""); output.put("parentId", ""); output.put("name", "");
   * output.put("pictureUrl", ""); output.put("showCover", ""); output.put("classify", "");
   * output.put("detail", ""); output.put("createTime", ""); output.put("childId", "");
   * output.put("allChildId", ""); output.put("allParentId", ""); output.put("level", "");
   * output.put("isLeaf", ""); output.put("module", ""); output.put("typeCode", "");
   * Page<ProductCategoryModel> categoryList = productCategoryDao.queryListByCond0(cond, output,
   * null); return categoryList.getResult(); }
   */
  public Page<ProductCategoryModel> queryAllCategory(ProductCategoryQueryConditionReq req,
      Page page, Integer classify) {
    Map<String, Object> cond = new HashMap<String, Object>();
    cond.put("classify", classify);

    //
    INPUT: {
      if (req == null) break INPUT;
      if (StringUtils.isNotBlank(req.getExamSchool())) {
        cond.put("examSchoolLike", req.getExamSchool());
      }
      if (StringUtils.isNotBlank(req.getMajorName())) {
        cond.put("majorNameLike", req.getMajorName());
      }
      if (StringUtils.isNotBlank(req.getModule()) && !req.getModule().equals("-1")) {
        cond.put("module", req.getModule());
      }
      if (StringUtils.isNotBlank(req.getTypeCode())) {
        cond.put("typeCodeLike", req.getTypeCode());
      }
    }


    Map<String, Object> output = new HashMap<>();
    output.put("id", "");
    output.put("parentId", "");
    output.put("name", "");
    output.put("pictureUrl", "");
    output.put("showCover", "");
    output.put("classify", "");
    output.put("detail", "");
    output.put("createTime", "");
    output.put("childId", "");
    output.put("allChildId", "");
    output.put("allParentId", "");
    output.put("level", "");
    output.put("isLeaf", "");
    output.put("module", "");
    output.put("typeCode", "");
    output.put("isCooperation", "");
    output.put("isSync", "");
    output.put("isBuy", "");
    output.put("courseCount", "");
    return productCategoryDao.queryListByCond0(cond, output, page);
  }

  /**
   * 根据module获取所有栏目
   * 
   * @param module
   * @return
   */
  /*
   * public List<ProductCategoryModel> queryAllCategory(Long module, Integer classify) { Map<String,
   * Object> cond = new HashMap<String, Object>(); cond.put("module", module); cond.put("classify",
   * classify); Map<String, Object> output = new HashMap<>(); output.put("id", "");
   * output.put("parentId", ""); output.put("name", ""); output.put("pictureUrl", "");
   * output.put("showCover", ""); output.put("detail", ""); output.put("createTime", "");
   * output.put("childId", ""); output.put("allChildId", ""); output.put("allParentId", "");
   * output.put("level", ""); output.put("isLeaf", ""); output.put("module", "");
   * output.put("typeCode", ""); output.put("moduleName", ""); output.put("majorName", "");
   * output.put("majorInfo", ""); output.put("showStatus", ""); Page<ProductCategoryModel>
   * categoryList = productCategoryDao.queryListByCond0(cond, output, null); return
   * categoryList.getResult(); }
   */
  public Page<ProductCategoryModel> queryAllCategory(ProductCategoryQueryConditionReq req,
      Page page, Long module, Integer classify) {
    Map<String, Object> cond = new HashMap<String, Object>();
    // cond.put("module", module);
    cond.put("classify", classify);
    //
    INPUT: {
      if (req == null) break INPUT;
      if (StringUtils.isNotBlank(req.getExamSchool())) {
        cond.put("examSchoolLike", req.getExamSchool());
      }
      if (StringUtils.isNotBlank(req.getMajorName())) {
        cond.put("majorNameLike", req.getMajorName());
      }
      if (StringUtils.isNotBlank(req.getModule()) && !req.getModule().equals("-1")) {
        cond.put("module", req.getModule());
      }
      if (StringUtils.isNotBlank(req.getTypeCode())) {
        cond.put("typeCodeLike", req.getTypeCode());
      }
    }

    Map<String, Object> output = new HashMap<>();
    output.put("id", "");
    output.put("parentId", "");
    output.put("name", "");
    output.put("pictureUrl", "");
    output.put("showCover", "");
    output.put("detail", "");
    output.put("createTime", "");
    output.put("childId", "");
    output.put("allChildId", "");
    output.put("allParentId", "");
    output.put("level", "");
    output.put("isLeaf", "");
    output.put("module", "");
    output.put("moduleName", "");
    output.put("typeCode", "");
    output.put("majorName", "");
    output.put("majorInfo", "");
    output.put("showStatus", "");
    output.put("isCooperation", "");
    output.put("isSync", "");
    output.put("isBuy", "");
    output.put("courseCount", "");
    return productCategoryDao.queryListByCond0(cond, output, page);
  }



  /**
   * 清洗tree
   * 
   * @return
   */
  /*
   * public Boolean cleanTree() { // 1.查询栏目列表 List<ProductCategoryModel> productCategoryModelList =
   * this.queryAllCategory(null); // 2.栏目ID Map<Long, ProductCategoryModel> idMap = new HashMap<>();
   * for (ProductCategoryModel categoryModel : productCategoryModelList) {
   * idMap.put(categoryModel.getId(), categoryModel); } // 3.父栏目ID Map<Long,
   * List<ProductCategoryModel>> parentIdMap = new HashMap<>(); for (ProductCategoryModel
   * categoryModel : productCategoryModelList) { List<ProductCategoryModel> categoryModelList =
   * null; if (parentIdMap.containsKey(categoryModel.getParentId())) { categoryModelList =
   * parentIdMap.get(categoryModel.getParentId()); } else { categoryModelList = new ArrayList<>(); }
   * categoryModelList.add(categoryModel); parentIdMap.put(categoryModel.getParentId(),
   * categoryModelList); } // 4.更新记录 List<Long> needDeleteIds = new ArrayList<>(); for
   * (ProductCategoryModel categoryModel : productCategoryModelList) { List<ProductCategoryModel>
   * parentList = this.parentList(idMap, categoryModel.getId()); Boolean needDelete = true; for
   * (ProductCategoryModel productCategoryModel : parentList) { if
   * (productCategoryModel.getParentId() == 0) { needDelete = false; break; } } if (needDelete ==
   * true) { needDeleteIds.add(categoryModel.getId()); continue; } List<ProductCategoryModel>
   * allChildList = this.allChildId(parentIdMap, categoryModel.getId()); List<ProductCategoryModel>
   * childList = parentIdMap.get(categoryModel.getId());
   * 
   * ProductCategoryModel updateEntity = new ProductCategoryModel();
   * updateEntity.setId(categoryModel.getId()); updateEntity.setChildId(this.idString(childList));
   * updateEntity.setAllChildId(this.idString(allChildList));
   * updateEntity.setLevel(parentList.size() - 1);
   * updateEntity.setAllParentId(this.idString(parentList)); if (childList != null &&
   * childList.size() > 0) { updateEntity.setIsLeaf(0); } else { updateEntity.setIsLeaf(1); }
   * this.editCategory(updateEntity); } // 5.删除记录 if (needDeleteIds.size() > 0) { Map<String,
   * Object> cond = new HashMap<>(); cond.put("ids", needDeleteIds);
   * productCategoryDao.deleteEntity(cond); for (Long id : needDeleteIds) {
   * DeleteProductCategoryEvent deleteProductCategoryEvent = new DeleteProductCategoryEvent();
   * deleteProductCategoryEvent.setDataModel(new TransferProductCategoryData(id));
   * deleteProductCategoryEvent.send(); } } return true; }
   */

  public Boolean cleanTree() {
    // 1.查询栏目列表
    Page<ProductCategoryModel> productCategoryModelList = this.queryAllCategory(null, null, null);
    // 2.栏目ID
    Map<Long, ProductCategoryModel> idMap = new HashMap<>();
    if (productCategoryModelList != null) {
      for (ProductCategoryModel categoryModel : productCategoryModelList.getResult()) {
        idMap.put(categoryModel.getId(), categoryModel);
      }
    }
    // 3.父栏目ID
    Map<Long, List<ProductCategoryModel>> parentIdMap = new HashMap<>();
    if (productCategoryModelList != null) {
      for (ProductCategoryModel categoryModel : productCategoryModelList.getResult()) {
        List<ProductCategoryModel> categoryModelList = null;
        if (parentIdMap.containsKey(categoryModel.getParentId())) {
          categoryModelList = parentIdMap.get(categoryModel.getParentId());
        } else {
          categoryModelList = new ArrayList<>();
        }
        categoryModelList.add(categoryModel);
        parentIdMap.put(categoryModel.getParentId(), categoryModelList);
      }
    }
    // 4.更新记录
    List<Long> needDeleteIds = new ArrayList<>();
    if (productCategoryModelList != null) {
      for (ProductCategoryModel categoryModel : productCategoryModelList.getResult()) {
        List<ProductCategoryModel> parentList = this.parentList(idMap, categoryModel.getId());
        Boolean needDelete = true;
        for (ProductCategoryModel productCategoryModel : parentList) {
          if (productCategoryModel.getParentId() == 0) {
            needDelete = false;
            break;
          }
        }
        if (needDelete == true) {
          needDeleteIds.add(categoryModel.getId());
          continue;
        }
        List<ProductCategoryModel> allChildList =
            this.allChildId(parentIdMap, categoryModel.getId());
        List<ProductCategoryModel> childList = parentIdMap.get(categoryModel.getId());

        ProductCategoryModel updateEntity = new ProductCategoryModel();
        updateEntity.setId(categoryModel.getId());
        updateEntity.setChildId(this.idString(childList));
        updateEntity.setAllChildId(this.idString(allChildList));
        updateEntity.setLevel(parentList.size() - 1);
        updateEntity.setAllParentId(this.idString(parentList));
        if (childList != null && childList.size() > 0) {
          updateEntity.setIsLeaf(0);
        } else {
          updateEntity.setIsLeaf(1);
        }
        this.editCategory(updateEntity);
      }
    }

    // 5.删除记录
    if (needDeleteIds.size() > 0) {
      Map<String, Object> cond = new HashMap<>();
      cond.put("ids", needDeleteIds);
      for (Long id : needDeleteIds) {
        DeleteProductCategoryEvent deleteProductCategoryEvent = new DeleteProductCategoryEvent();
        ProductCategoryModel entity = new ProductCategoryModel();
        entity.setId(id);
        entity = productCategoryDao.findEntityById(entity);
        if (null != entity) {
          productCategoryDao.deleteEntity(cond);
          deleteProductCategoryEvent.setModule(entity.getModule());
          deleteProductCategoryEvent.setDataModel(new TransferProductCategoryData(id, entity
              .getModule()));
          deleteProductCategoryEvent.send();
        }
      }
    }
    return true;
  }

  /**
   * ID序列化
   * 
   * @param categoryModelList
   * @return
   */
  private String idString(List<ProductCategoryModel> categoryModelList) {
    StringBuilder stringBuilder = new StringBuilder("");
    if (categoryModelList == null) {
      return "";
    }
    for (ProductCategoryModel categoryModel : categoryModelList) {
      stringBuilder.append(categoryModel.getId() + ",");
    }
    return stringBuilder.toString();
  }

  /**
   * 父节点
   * 
   * @param catId
   * @param idMap
   * @return
   */
  private List<ProductCategoryModel> parentList(Map<Long, ProductCategoryModel> idMap, Long catId) {
    ProductCategoryModel categoryModel = idMap.get(catId);
    List<ProductCategoryModel> result = new ArrayList<>();
    if (categoryModel != null) {
      if (categoryModel.getParentId() != 0) {
        result.addAll(this.parentList(idMap, categoryModel.getParentId()));
      }
      result.add(categoryModel);
      return result;
    } else {
      return new ArrayList<>();
    }
  }

  /**
   * 所有子节点
   * 
   * @param parentIdMap
   * @param catId
   * @return
   */
  private List<ProductCategoryModel> allChildId(Map<Long, List<ProductCategoryModel>> parentIdMap,
      Long catId) {
    List<ProductCategoryModel> result = new ArrayList<>();
    List<ProductCategoryModel> categoryList = parentIdMap.get(catId);
    if (categoryList != null) {
      result.addAll(categoryList);
      for (ProductCategoryModel categoryModel : categoryList) {
        result.addAll(this.allChildId(parentIdMap, categoryModel.getId()));
      }
    }
    return result;
  }

  public ProductCategoryModel findCategoryByTypeCode(String typeCode) {
    if (StringUtils.isBlank(typeCode)) return null;
    IQueryParam param = new QueryParam();
    param.addInput("typeCode", typeCode);
    param.addInput("showStatus", 1);
    param.addOutput("id", "");
    Page<ProductCategoryModel> dbPage = productCategoryDao.findEntityListByCond(param, null);
    return (dbPage != null && dbPage.getResult().size() != 0) ? dbPage.getResult().get(0) : null;
  }

}
