package com.xiaodou.course.service.product;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.xiaodou.common.util.CommUtil;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.course.common.enums.ResourceType;
import com.xiaodou.course.model.product.ModuleSlideModel;
import com.xiaodou.course.model.product.ProductCategoryModel;
import com.xiaodou.course.model.product.ProductItemModel;
import com.xiaodou.course.model.product.ProductModel;
import com.xiaodou.course.model.user.UserLearnStaticsModel;
import com.xiaodou.course.service.facade.ProductServiceFacade;
import com.xiaodou.course.vo.product.Chapter;
import com.xiaodou.course.vo.product.ChapterResource;
import com.xiaodou.course.vo.product.ChildChapter;
import com.xiaodou.course.vo.product.CourseDetail;
import com.xiaodou.course.vo.product.CourseInfo;
import com.xiaodou.course.vo.product.ModuleSlide;
import com.xiaodou.course.vo.product.ProductListDetail;
import com.xiaodou.course.vo.product.ResourceDescription;
import com.xiaodou.course.web.request.product.ProductAuthRequest;
import com.xiaodou.course.web.request.product.ProductDetailRequest;
import com.xiaodou.course.web.request.product.ProductDownloadRequest;
import com.xiaodou.course.web.request.product.ProductListRequst;
import com.xiaodou.course.web.response.product.ProductDetailReponse;
import com.xiaodou.course.web.response.product.ProductDownloadResponse;
import com.xiaodou.course.web.response.product.ProductDownloadResponse.ResourceMap;
import com.xiaodou.course.web.response.product.ProductItemAuthResponse;
import com.xiaodou.course.web.response.product.ProductListResponse;
import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.summer.vo.out.ResultType;


/**
 * productService
 */
@Service("productService")
public class ProductService {

  @Resource
  ProductServiceFacade productServiceFacade;

  @Resource
  ModuleSlideService moduleSlideService;

  @Resource
  ProductCategoryService productCategoryService;

  @Resource
  ProductItemService productItemService;

  @Resource
  UserLearnStaticsService userLearnStaticsService;

  /**
   * 产品列表
   * 
   * @param listRequst
   */
  public ProductListResponse productList(ProductListRequst listRequst) {

    ProductListResponse response = new ProductListResponse(ResultType.SUCCESS);

    if (StringUtils.isNotBlank(listRequst.getModule())) {
      // 模块号
      Integer moduleId = Integer.parseInt(listRequst.getModule());

      // 幻灯片列表
      List<ModuleSlideModel> sildes = moduleSlideService.moduleSlideList(moduleId);
      List<ModuleSlide> slideList = new ArrayList<>();
      for (ModuleSlideModel moduleSlideModel : sildes) {
        ModuleSlide moduleSlide = new ModuleSlide();
        moduleSlide.setTitle(moduleSlideModel.getDescription());
        moduleSlide.setImageUrl(moduleSlideModel.getImageUrl());
        moduleSlide.setRedirectUrl(moduleSlideModel.getLinkUrl());
        slideList.add(moduleSlide);
      }
      response.setImageUrlList(slideList);
    }
    if (StringUtils.isNotBlank(listRequst.getTypeCode())
        && StringUtils.isNotBlank(listRequst.getUid())) {
      String typeCode = listRequst.getTypeCode();
      Integer userId = Integer.parseInt(listRequst.getUid());


      // 栏目
      ProductCategoryModel productCategory = productCategoryService.findCategoryByAppCode(typeCode);
      // 产品列表
      Page<ProductModel> productModelPage =
          this.cascadeQueryProductByCatId(productCategory.getId());
      List<ProductListDetail> productList = new ArrayList<>();
      for (ProductModel product : productModelPage.getResult()) {
        UserLearnStaticsModel statics =
            userLearnStaticsService.findStatics(product.getId(), userId);
        ProductListDetail productListDetail = new ProductListDetail();
        productListDetail.setCourseId(product.getId());
        productListDetail.setCourseName(product.getName());
        CourseInfo courseInfo = new CourseInfo();
        if (statics != null) {
          courseInfo.setChapterId(statics.getChapterId().toString());
          courseInfo.setChapterName(statics.getChapterName());
          courseInfo.setItemId(statics.getCurrentItem().toString());
          courseInfo.setItemName(statics.getCurrentItemName());
          courseInfo.setLastStudyDate(statics.getUpdateTime());
          courseInfo.setStudyProgress(statics.getRatio());
        } else {
          courseInfo.setChapterName("未开始学习");
          courseInfo.setItemName("未开始学习");
          courseInfo.setLastStudyDate(new Timestamp(System.currentTimeMillis()));
          courseInfo.setStudyProgress(0);
        }
        productListDetail.setInfo(courseInfo);
        productList.add(productListDetail);
      }
      response.setCourseList(productList);
    }
    return response;

  }

  /**
   * 查找条目
   * 
   * @param itemId
   * @return
   */
  public ProductItemModel findItemById(Integer itemId) {
    ProductItemModel productItemModel = new ProductItemModel();
    productItemModel.setId(itemId);
    return productServiceFacade.queryProductItemById(productItemModel);
  }

  /**
   * 条目授权
   * 
   * @param authRequest
   */
  public ProductItemAuthResponse itemAuth(ProductAuthRequest authRequest) {
    ProductItemAuthResponse response = new ProductItemAuthResponse(ResultType.SUCCESS);
    ProductItemModel item = this.findItemById(authRequest.getItemId());
    if (item == null) {
      response.setRetdesc("本课件不存在");
      response.setRetcode("-1");
      return response;
    }
    // 如果是章节
    if (item.getResourceType() == ResourceType.CHAPTER.getTypeId()) {
      if (item.getRelationItem() != null && item.getRelationItem() != 0) {
        ProductItemModel relationItem = this.findItemById(item.getRelationItem());
        if (StringUtils.isNotBlank(relationItem.getResource())) {
          response.setResourceUrl(JSON.parseObject(relationItem.getResource(),
              ResourceDescription.class));
        } else {
          response.setResourceUrl(new ResourceDescription());
        }
      } else {
        response.setResourceUrl(new ResourceDescription());
      }
    } else {
      if (StringUtils.isNotBlank(item.getResource())) {
        response.setResourceUrl(JSON.parseObject(item.getResource(), ResourceDescription.class));
      } else {
        response.setResourceUrl(new ResourceDescription());
      }
    }
    return response;
  }

  /**
   * 课程详情
   * 
   * @param detailRequest
   * @return
   */
  public ProductDetailReponse productDetail(ProductDetailRequest detailRequest) {
    ProductDetailReponse reponse = null;
    try {
      reponse = new ProductDetailReponse(ResultType.SUCCESS);

      // 查询产品信息
      ProductModel product = this.findProductById(detailRequest.getCourseId());
      if (product == null) {
        reponse = new ProductDetailReponse(ResultType.SYSFAIL);
        reponse.setRetdesc("本课程不存在");
        return reponse;
      }
      CourseDetail courseDetail = new CourseDetail();
      courseDetail.setShareUrl(product.getShareUrl());
      reponse.setCourseDetail(courseDetail);

      // 章节列表
      List<Chapter> chapterList = this.productDetail(detailRequest.getCourseId());
      reponse.setCourseItemList(chapterList);
    } catch (Exception e) {
      reponse = new ProductDetailReponse(ResultType.SYSFAIL);
    }
    return reponse;
  }

  /**
   * 课程详情
   * 
   * @param productId
   */
  public List<Chapter> productDetail(Integer productId) {

    // 查询产品条目信息
    List<ProductItemModel> productItems = productItemService.queryItemListByProductId(productId);
    Map<Integer, List<ProductItemModel>> itemParentMap = new HashMap<>();
    for (ProductItemModel productItem : productItems) {
      List<ProductItemModel> childList = new ArrayList<>();
      if (itemParentMap.containsKey(productItem.getParentId())) {
        childList = itemParentMap.get(productItem.getParentId());
      }
      childList.add(productItem);
      itemParentMap.put(productItem.getParentId(), childList);
    }

    // 从是0的开始
    if (null == itemParentMap || itemParentMap.size() == 0) return Lists.newArrayList();
    List<ProductItemModel> chapterItems = itemParentMap.get(0);
    List<Chapter> chapterList = new ArrayList<>();
    for (ProductItemModel chapterItem : chapterItems) {
      Chapter chapter = new Chapter();
      chapter.setChapterId(chapterItem.getId());
      chapter.setChapterName(chapterItem.getName());
      chapter.setFree(chapterItem.getIsFree());
      chapter.setImportance(chapterItem.getImportanceLevel());
      chapter.setChapterIdAlias(chapterItem.getChapterId());
      chapter.setQuestionNum(chapterItem.getQuesNum());
      List<ChildChapter> childChapterList = new ArrayList<>();
      List<ChapterResource> resources = new ArrayList<>();
      List<ProductItemModel> childChapterItems = itemParentMap.get(chapterItem.getId());
      if (childChapterItems != null && childChapterItems.size() > 0) {
        for (ProductItemModel childChapterItem : childChapterItems) {
          if (childChapterItem.getResourceType().equals(ResourceType.CHAPTER.getTypeId())) {
            ChildChapter childChapter = new ChildChapter();
            childChapter.setImportance(childChapterItem.getImportanceLevel());
            childChapter.setFree(childChapterItem.getIsFree());
            childChapter.setItemId(childChapterItem.getId());
            childChapter.setItemName(childChapterItem.getName());
            childChapter.setChapterIdAlias(childChapterItem.getChapterId());
            childChapter.setQuestionNum(childChapter.getQuestionNum());
            List<ProductItemModel> resourceList = itemParentMap.get(childChapterItem.getId());
            List<ChapterResource> chapterResourceList = new ArrayList<>();
            if (resourceList != null && resourceList.size() > 0) {
              for (ProductItemModel resource : resourceList) {
                ChapterResource chapterResource = new ChapterResource();
                chapterResource.setFree(resource.getIsFree());
                chapterResource.setResourceId(resource.getId());
                chapterResource.setResourceName(resource.getName());
                chapterResource.setResourceType(resource.getResourceType());
                chapterResourceList.add(chapterResource);
              }
            }
            childChapter.setResourceList(chapterResourceList);
            childChapterList.add(childChapter);
          } else {
            ChapterResource chapterResource = new ChapterResource();
            chapterResource.setFree(childChapterItem.getIsFree());
            chapterResource.setResourceId(childChapterItem.getId());
            chapterResource.setResourceName(childChapterItem.getName());
            chapterResource.setResourceType(childChapterItem.getResourceType());
            resources.add(chapterResource);
          }
        }
        chapter.setResourceList(resources);
        chapter.setChildList(childChapterList);
      }
      chapterList.add(chapter);
    }
    return chapterList;
  }

  /**
   * 查找产品信息
   * 
   * @param id
   * @return
   */
  public ProductModel findProductById(Integer id) {
    ProductModel productModel = new ProductModel();
    productModel.setId(id);
    return productServiceFacade.queryProductById(productModel);
  }

  /**
   * 
   * @param catId
   * @return
   */
  public Page<ProductModel> cascadeQueryProductByCatId(Integer catId) {
    List<ProductCategoryModel> productCategoryModels =
        productCategoryService.queryAllChildCategory(catId);
    List<Integer> ids = new ArrayList<>();
    for (ProductCategoryModel categoryModel : productCategoryModels) {
      ids.add(categoryModel.getId());
    }
    ids.add(catId);
    Map<String, Object> cond = new HashMap<>();
    cond.put("productCategoryIds", ids);
    cond.put("showStatus", 1);
    Map<String, Object> output = new HashMap<>();
    CommUtil.getGeneralField(output, ProductModel.class);
    return productServiceFacade.queryCascadeProductByCond(cond, output);
  }

  /**
   * @param downloadRequest
   * @return 获取下载地址列表
   */
  public ProductDownloadResponse downloadUrlList(ProductDownloadRequest downloadRequest) {
    List<ProductItemModel> itemList =
        productItemService.queryItemListByProductId(downloadRequest.getCourseId());
    if (null == itemList || itemList.size() == 0)
      return new ProductDownloadResponse(ResultType.SUCCESS);
    List<Integer> courseWareIds = Lists.newArrayList();
    for (ProductItemModel item : itemList) {
      if (item.getResourceType() == ResourceType.CHAPTER.getTypeId()
          && item.getRelationItem() != null && item.getRelationItem() != 0) {
        courseWareIds.add(item.getRelationItem());
      }
    }
    List<ProductItemModel> courseWareList = productItemService.queryItemListByIds(courseWareIds);
    if (null == itemList || itemList.size() == 0)
      return new ProductDownloadResponse(ResultType.SUCCESS);
    ProductDownloadResponse response = new ProductDownloadResponse(ResultType.SUCCESS);
    for (ProductItemModel courseWare : courseWareList) {
      ResourceMap map = JSON.parseObject(courseWare.getResource(), ResourceMap.class);
      map.setResourceId(courseWare.getId().toString());
      map.setItemId(courseWare.getParentId().toString());
      response.getResourceList().add(map);
    }
    return response;
  }
}
