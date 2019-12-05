package com.xiaodou.course.service.product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xiaodou.common.util.CommUtil;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.course.constant.CourseConstant;
import com.xiaodou.course.enums.MajorLevel;
import com.xiaodou.course.model.product.MajorCourseModel;
import com.xiaodou.course.model.product.MajorDataModel;
import com.xiaodou.course.model.product.MajorDataModel.MajorInfo;
import com.xiaodou.course.model.product.ProductCategoryModel;
import com.xiaodou.course.model.product.ProductModel;
import com.xiaodou.course.service.facade.ProductServiceFacade;
import com.xiaodou.course.vo.product.CourseDetail;
import com.xiaodou.course.vo.product.MajorDetail;
import com.xiaodou.course.vo.product.ProductCategory;
import com.xiaodou.course.web.request.BaseRequest;
import com.xiaodou.course.web.request.forum.MajorRequest;
import com.xiaodou.course.web.request.product.MajorDetailByMajorIdRequest;
import com.xiaodou.course.web.request.product.MajorDetailRequest;
import com.xiaodou.course.web.response.product.MajorDetailResponse;
import com.xiaodou.course.web.response.product.ProductCategoryListResponse;
import com.xiaodou.course.web.response.product.ProductCategoryListResponse.RegionVO;
import com.xiaodou.course.web.response.product.ProductCategoryListResponse.RegionVO.Major;
import com.xiaodou.course.web.response.resultType.ProductResType;
import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.summer.vo.out.ResultType;

/**
 * Created by zyp on 15/8/9.
 */
@Service("productCategoryService")
public class ProductCategoryService {

  @Resource
  ProductServiceFacade productServiceFacade;

  /**
   * 产品service
   */
  @Resource
  ProductService productService;

  /**
   * 根据产品查找栏目
   * 
   * @param code
   * @return
   */
  public ProductCategoryModel findCategoryByAppCode(String typeCode, String module) {
    Map<String, Object> cond = new HashMap<>();
    cond.put("typeCode", typeCode);
    cond.put("module", module);
    Page<ProductCategoryModel> productCategoryModelPage =
        productServiceFacade.queryProductCategoryListByCondWithOutPage(cond,
            CommUtil.getAllField(ProductCategoryModel.class));
    if (null != productCategoryModelPage) {
      List<ProductCategoryModel> result = productCategoryModelPage.getResult();
      if (null != result && result.size() > 0) return result.get(0);
    }
    return null;
  }

  public List<ProductCategoryModel> queryAllChildCategory(Integer id) {
    if (id == 0) {
      return this.queryAllCategory();
    }
    ProductCategoryModel productCategory = this.findCategoryById(id);
    if (productCategory == null) {
      return new ArrayList<>();
    }
    if (StringUtils.isNotBlank(productCategory.getAllChildId())) {
      String[] ids = productCategory.getAllChildId().split(",");
      List<Integer> idList = new ArrayList<>();
      for (String idString : ids) {
        idList.add(Integer.parseInt(idString));
      }
      return this.queryCategoryByIds(idList);
    } else {
      return new ArrayList<>();
    }
  }

  /**
   * 获取所有栏目
   * 
   * @return
   */
  public List<ProductCategoryModel> queryAllCategory() {
    Map<String, Object> cond = new HashMap<String, Object>();
    Map<String, Object> output = new HashMap<>();
    output.put("id", "1");
    output.put("parentId", "1");
    output.put("name", "1");
    output.put("detail", "1");
    output.put("createTime", "1");
    output.put("childId", "1");
    output.put("allChildId", "1");
    output.put("allParentId", "1");
    output.put("level", "1");
    output.put("isLeaf", "1");
    output.put("module", "1");
    output.put("typeCode", "1");
    Page<ProductCategoryModel> categoryList =
        productServiceFacade.queryProductCategoryListByCondWithOutPage(cond, output);
    return categoryList.getResult();
  }

  /**
   * 根据专业id查找专业
   * 
   * @param catId
   * @return
   */
  public ProductCategoryModel findCategoryById(Integer catId) {
    ProductCategoryModel entity = new ProductCategoryModel();
    entity.setId(catId);
    return productServiceFacade.queryProductCategoryById(entity);
  }

  /**
   * 根据父Id查询地区
   * 
   * @param ids
   * @return
   */
  public List<ProductCategoryModel> queryCategoryByIds(List<Integer> ids) {
    Map<String, Object> cond = new HashMap<String, Object>();
    cond.put("ids", ids);
    Map<String, Object> output = new HashMap<>();
    output.put("id", "1");
    output.put("parentId", "1");
    output.put("name", "1");
    output.put("detail", "1");
    output.put("createTime", "1");
    output.put("childId", "1");
    output.put("allChildId", "1");
    output.put("allParentId", "1");
    output.put("level", "1");
    output.put("isLeaf", "1");
    output.put("module", "1");
    output.put("typeCode", "1");
    Page<ProductCategoryModel> categoryList =
        productServiceFacade.queryProductCategoryListByCondWithOutPage(cond, output);
    return categoryList.getResult();
  }

  /**
   * 查询专业详情
   * 
   * @param detailRequest
   * @return
   */
  public MajorDetailResponse majorDetail(MajorDetailRequest detailRequest) {
    MajorDetailResponse response = new MajorDetailResponse(ResultType.SUCCESS);
    // 栏目（专业）
    ProductCategoryModel majorModel =
        this.findCategoryByAppCode(detailRequest.getTypeCode(), detailRequest.getModule());
    if (null == majorModel) return new MajorDetailResponse(ProductResType.NotFindMajor);
    String majorDataId = majorModel.getTypeCode();
    if (StringUtils.isBlank(majorDataId))
      return new MajorDetailResponse(ProductResType.NotFindMajor);
    MajorDataModel majorDataModel =
        productServiceFacade.queryMajorDataByIdAndRegion(majorDataId, majorModel.getModule());
    MajorDetail majorDetail = this.getMajorDetail(majorModel.getId(), majorDataModel);
    if (null == majorDetail) return new MajorDetailResponse(ProductResType.NotFindMajor);
    response.setMajorDetail(majorDetail);
    return response;
  }

  /**
   * 查询专业详情
   * 
   * @param detailRequest
   * @return
   */
  public MajorDetailResponse majorDetailById(MajorDetailByMajorIdRequest detailRequest,
      String headVersion) {
    MajorDetailResponse response = new MajorDetailResponse(ResultType.SUCCESS);
    // 栏目（专业）
    ProductCategoryModel majorModel = null;
    if (!headVersion.equals("149")) {
      majorModel = this.findCategoryById(Integer.valueOf(detailRequest.getMajorId()));
    } else {
      // 之前传入的majorId的值是typeCode的值
      majorModel =
          this.findCategoryByAppCode(detailRequest.getMajorId(), detailRequest.getModule());
    }
    if (null == majorModel) {
      return new MajorDetailResponse(ProductResType.NotFindMajor);
    }
    String majorDataId = majorModel.getTypeCode();
    if (StringUtils.isBlank(majorDataId))
      return new MajorDetailResponse(ProductResType.NotFindMajor);
    MajorDataModel majorDataModel =
        productServiceFacade.queryMajorDataByIdAndRegion(majorDataId, majorModel.getModule());
    MajorDetail majorDetail = getMajorDetail(majorModel.getId(), majorDataModel);
    if (null == majorDetail) return new MajorDetailResponse(ProductResType.NotFindMajor);
    response.setMajorDetail(majorDetail);
    return response;
  }

  @SuppressWarnings("unused")
  private MajorDetail getMajorDetail(String modelId, ProductCategoryModel majorModel) {
    MajorDetail majorDetail = new MajorDetail();
    majorDetail.setMajorName(majorModel.getName());
    majorDetail.setMajorCode(majorModel.getTypeCode());
    majorDetail.setChiefAcademy(majorModel.getChiefAcademy());
    majorDetail.setMajorDesc(majorModel.getDetail());
    majorDetail.setDegree(majorModel.getDegree());
    if (StringUtils.isNotBlank(majorModel.getMajorLevel()))
      majorDetail.setMajorLevel(majorModel.getMajorLevel().equals(MajorLevel.benke.getCode())
          ? MajorLevel.benke.getValue()
          : MajorLevel.zhuanke.getValue());

    // 课程列表
    List<CourseDetail> courseList = Lists.newArrayList();
    // 产品（课程）列表
    Page<ProductModel> productModelPage =
        productService.cascadeQueryProductByCatId(majorModel.getId(), modelId);
    if (null != productModelPage.getResult() && productModelPage.getResult().size() > 0) {
      for (ProductModel productModel : productModelPage.getResult()) {
        if (null != productModel) courseList.add(productService.getCourseDetail(productModel));
      }
    }
    majorDetail.setCourseList(courseList);
    return majorDetail;
  }

  public MajorDetail getMajorDetail(Integer majorId, MajorDataModel majorDataModel) {
    if (null == majorDataModel) return null;
    MajorDetail majorDetail = new MajorDetail();
    majorDetail.setMajorName(majorDataModel.getName());
    majorDetail.setMajorCode(majorDataModel.getId());
    if (StringUtils.isNotBlank(majorDataModel.getMajorInfo())) {
      MajorInfo majorInfo = FastJsonUtil.fromJson(majorDataModel.getMajorInfo(), MajorInfo.class);
      majorDetail.setDegree(majorInfo.getDegree());
      majorDetail.setMajorDesc(majorInfo.getDetail());
      majorDetail.setChiefAcademy(majorInfo.getExamSchool());
      majorDetail.setMajorLevel(majorInfo.getMajorLevel());
    }

    // 课程列表
    List<CourseDetail> courseList = Lists.newArrayList();
    // 产品（课程）列表
    Page<ProductModel> productModelPage = productService.cascadeQueryProductByCatId(majorId);
    if (null != productModelPage.getResult() && productModelPage.getResult().size() > 0) {
      for (ProductModel productModel : productModelPage.getResult()) {
        if (null != productModel) {
          String moduleCourse = productModel.getModuleCourse();
          if (StringUtils.isNotBlank(moduleCourse)) {
            // if (module.equals(moduleCourse)) continue;
            MajorCourseModel majorCourseModel =
                productServiceFacade.queryMajorCourseByIdAndRegion(productModel.getCourseCode(),
                    majorDataModel.getRegion());
            if (null != majorCourseModel) {
              CourseDetail courseDetail =
                  productService.getCourseDetail(productModel, majorCourseModel);
              courseList.add(courseDetail);
            }
          }
        }
      }
    }
    majorDetail.setCourseList(courseList);
    return majorDetail;
  }

  /**
   * 通过专业id与app模块号查询课程详情
   * 
   * @param majorId
   * @param module
   * @return
   */
  public List<CourseDetail> getCourseDetailBymId(String majorId, String module) {
    // 栏目（专业）
    ProductCategoryModel majorModel = this.findCategoryById(Integer.valueOf(majorId));
    if (null == majorModel) return null;
    // 课程列表
    List<CourseDetail> courseList = Lists.newArrayList();
    // 产品（课程）列表
    Page<ProductModel> productModelPage =
        productService.cascadeQueryProductByCatId(majorModel.getId(), module);
    if (null != productModelPage.getResult() && productModelPage.getResult().size() > 0) {
      for (ProductModel productModel : productModelPage.getResult()) {
        if (null != productModel) courseList.add(productService.getCourseDetail(productModel));
      }
    }
    return courseList;
  }

  /**
   * 获取所有特色栏目
   * 
   * @return
   */
  public List<ProductCategoryModel> queryAllTSCategory(BaseRequest request) {
    Map<String, Object> cond = new HashMap<String, Object>();
    cond.put("showStatus", "1");// 显示
    Page<ProductCategoryModel> categoryList =
        productServiceFacade.queryProductCategoryListByCondWithOutPage(cond,
            CommUtil.getAllField(ProductCategoryModel.class));
    return categoryList.getResult();
  }

  public ProductCategoryListResponse queryOldAllCategory(MajorRequest request) {
    ProductCategoryListResponse response = new ProductCategoryListResponse(ResultType.SUCCESS);
    List<ProductCategoryModel> list = this.queryAllTSCategory(request);
    ProductCategory productCategory;
    List<ProductCategory> resultList = Lists.newArrayList();
    if (!CollectionUtils.isEmpty(list)) {
      for (ProductCategoryModel p : list) {
        if (null != p && CourseConstant.MODULE.equals(p.getModule())) {
          productCategory = new ProductCategory(p);
          resultList.add(productCategory);
        }
      }
    }
    response.setList(resultList);
    return response;
  }

  public ProductCategoryListResponse queryAllCategory(BaseRequest request) {
    ProductCategoryListResponse response = new ProductCategoryListResponse(ResultType.SUCCESS);
    List<RegionVO> list = Lists.newArrayList();
    List<ProductCategoryModel> ls = this.queryAllTSCategory(request);
    Map<String, List<Major>> tempMap = Maps.newHashMap();
    List<Major> majorList = null;
    Major major = null;
    RegionVO regionVO = null;
    for (ProductCategoryModel cat : ls) {
      major =
          new Major(cat.getId().toString(), cat.getTypeCode(), cat.getName(), cat.getShowCover(),
              cat.getChiefAcademy());
      if (tempMap.containsKey(cat.getModule())) {
        tempMap.get(cat.getModule()).add(major);
      } else {
        majorList = Lists.newArrayList(major);
        tempMap.put(cat.getModule(), majorList);
        regionVO = new RegionVO(cat.getModule(), cat.getModuleName(), majorList);
        list.add(regionVO);
      }
    }
    response.setRegionList(list);
    return response;
  }

}
