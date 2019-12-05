package com.xiaodou.resources.service.product;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xiaodou.common.util.CommUtil;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.resources.constant.product.CourseConstant;
import com.xiaodou.resources.enums.product.ResourceType;
import com.xiaodou.resources.model.product.ProductItemModel;
import com.xiaodou.resources.model.product.ProductModel;
import com.xiaodou.resources.request.product.ProductDetailRequest;
import com.xiaodou.resources.response.product.UserChapterRecordResponse;
import com.xiaodou.resources.response.resultype.ProductResType;
import com.xiaodou.resources.service.QueueService;
import com.xiaodou.resources.vo.user.ChapterRecord;
import com.xiaodou.resources.vo.user.ItemRecord;
import com.xiaodou.resources.vo.user.UserChapterRecordVo;
import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.summer.dao.param.QueryEnums.Sort;
import com.xiaodou.summer.vo.out.ResultType;

/**
 * Created by zyp on 15/8/9.
 */
@Service("productItemService")
public class ProductItemService extends AbstractService {

  @Resource
  ProductService productService;

  @Resource
  QueueService queueService;

  /**
   * 根据Id查找
   * 
   * @param itemId
   * @return
   */
  public ProductItemModel findItemById(Long itemId) {
    ProductItemModel cond = new ProductItemModel();
    cond.setId(itemId);
    return productServiceFacade.queryProductItemById(cond);
  }

  /**
   * 检查itemId合法性
   * 
   * @param courseId
   * @return
   */
  public CheckResult checkItemId(String itemId) {
    CheckResult result = new CheckResult();
    if (null != this.findItemById(Long.valueOf(itemId))) {
      return result;
    }
    result.setResType(ProductResType.FindItemIdFailed);
    return result;
  }

  /**
   * 根据章ID获取item列表
   * 
   * @param chapterId 章ID
   * @return
   */
  public List<ProductItemModel> queryItemListByChapterId(Long chapterId) {
    return queryCourseItemList(null, chapterId, null);
  }

  /**
   * 根据productId获取item列表
   * 
   * @param productId
   * @return
   */
  public List<ProductItemModel> queryItemListByProductId(Long productId) {
    return queryCourseItemList(productId, null, null);
  }

  /**
   * 根据产品id，父id，id 查找章节数据
   * 
   * @param productId
   * @param parentId
   * @param id
   * @return
   */
  public List<ProductItemModel> queryCourseItemList(Object productId, Object parentId, Object id) {
    List<Integer> resourceTypeList = Lists.newArrayList();
    resourceTypeList.add(ResourceType.CHAPTER.getTypeId());
    return this.queryItemList(null, productId, parentId, id, resourceTypeList, null);
  }

  /**
   * 根据产品id，父id，id 查找资源数据
   * 
   * QUIZ("随堂测验","quiz",7), TALK("讨论","talk",8), TASK("单元任务/单元作业","task",9), EXAM("单元测验","exam",10),
   * FINALEXAM("期末考试","finalExam",11);
   * 
   * @param productId
   * @param parentId
   * @param id
   * @return
   */
  public ProductItemModel queryQUIZ(Object userId, Object productId, Object parentId, Object id) {
    List<Integer> resourceTypes = Lists.newArrayList();
    resourceTypes.add(ResourceType.QUIZ.getTypeId());
    List<ProductItemModel> list =
        this.queryItemList(userId, productId, parentId, id, resourceTypes, null);
    if (null != list && list.size() > 0) return list.get(0);
    return null;
  }

  public ProductItemModel queryTALK(Object userId, Object productId, Object parentId, Object id) {
    List<Integer> resourceTypes = Lists.newArrayList();
    resourceTypes.add(ResourceType.TALK.getTypeId());
    List<ProductItemModel> list =
        this.queryItemList(userId, productId, parentId, id, resourceTypes, null);
    if (null != list && list.size() > 0) return list.get(0);
    return null;
  }

  public ProductItemModel queryTASK(Object userId, Object productId, Object parentId, Object id) {
    List<Integer> resourceTypes = Lists.newArrayList();
    resourceTypes.add(ResourceType.TASK.getTypeId());
    List<ProductItemModel> list =
        this.queryItemList(userId, productId, parentId, id, resourceTypes, null);
    if (null != list && list.size() > 0) return list.get(0);
    return null;
  }

  public ProductItemModel queryEXAM(Object userId, Object productId, Object parentId, Object id) {
    List<Integer> resourceTypes = Lists.newArrayList();
    resourceTypes.add(ResourceType.EXAM.getTypeId());
    List<ProductItemModel> list =
        this.queryItemList(userId, productId, parentId, id, resourceTypes, null);
    if (null != list && list.size() > 0) return list.get(0);
    return null;
  }

  public ProductItemModel queryFINALEXAM(Object userId, Object productId, Object parentId, Object id) {
    List<Integer> resourceTypes = Lists.newArrayList();
    resourceTypes.add(ResourceType.FINAL.getTypeId());
    List<ProductItemModel> list =
        this.queryItemList(userId, productId, parentId, id, resourceTypes, null);
    if (null != list && list.size() > 0) return list.get(0);
    return null;
  }

  public List<Integer> getResource() {
    List<Integer> resourceTypes = Lists.newArrayList();
    resourceTypes.add(ResourceType.QUIZ.getTypeId());
    resourceTypes.add(ResourceType.TALK.getTypeId());
    resourceTypes.add(ResourceType.TASK.getTypeId());
    resourceTypes.add(ResourceType.EXAM.getTypeId());
    resourceTypes.add(ResourceType.FINAL.getTypeId());
    resourceTypes.add(ResourceType.VIDEO.getTypeId());
    resourceTypes.add(ResourceType.DOC.getTypeId());
    return resourceTypes;
  }

  /**
   * 根据产品id，父id，id 查找资源数据
   * 
   * @param productId
   * @param parentId
   * @param id
   * @return
   */
  public List<ProductItemModel> queryResourceItemList(Object productId, Object parentId,
      Object idUpper) {
    Map<String, Object> cond = new HashMap<>();
    if (null != productId && StringUtils.isNotBlank(productId.toString()))
      cond.put("productId", productId);
    if (null != parentId && StringUtils.isNotBlank(parentId.toString()))
      cond.put("parentId", parentId);
    cond.put("resourceTypeList", this.getResource());
    cond.put("showStatus", 1);
    Map<String, Sort> sorts = Maps.newHashMap();
    sorts.put("createTime", Sort.DESC);
    Page<ProductItemModel> productItemModelPage =
        productServiceFacade.queryListByPaging(cond, CommUtil.getAllField(ProductItemModel.class),
            null, sorts);
    return productItemModelPage.getResult();
  }

  /**
   * 根据产品id，父id，id,所属资源类型，非所属资源类型 查找章节数据
   * 
   * @param userId
   * @param productId
   * @param parentId
   * @param id
   * @param resourceTypes
   * @param noResourceTypes
   * @return
   */
  public List<ProductItemModel> queryItemList(Object userId, Object productId, Object parentId,
      Object id, List<Integer> resourceTypeList, Page<ProductItemModel> page) {
    Map<String, Object> cond = new HashMap<>();
    if (null != userId && StringUtils.isNotBlank(userId.toString())) cond.put("userId", userId);
    if (null != productId && StringUtils.isNotBlank(productId.toString()))
      cond.put("productId", productId);
    if (null != parentId && StringUtils.isNotBlank(parentId.toString()))
      cond.put("parentId", parentId);
    if (null != id && StringUtils.isNotBlank(id.toString())) cond.put("id", id);
    if (null != resourceTypeList && resourceTypeList.size() > 0)
      cond.put("resourceTypeList", resourceTypeList);
    cond.put("showStatus", 1);
    Map<String, Sort> sorts = Maps.newHashMap();
    sorts.put("listOrder", Sort.ASC);
    Page<ProductItemModel> productItemModelPage =
        productServiceFacade.queryListByPaging(cond, CommUtil.getAllField(ProductItemModel.class),
            page, sorts);
    return productItemModelPage.getResult();
  }

  /**
   * 闯关章节列表(自考菌)
   * 
   * @param detailRequest
   * @return
   */
  public UserChapterRecordResponse chapterList(ProductDetailRequest detailRequest) {
    UserChapterRecordResponse response = new UserChapterRecordResponse(ResultType.SUCCESS);
    try {
      // 查询产品信息
      ProductModel product =
      // productService.cascadeQueryProductByCourseCode(detailRequest.getTypeCode(),
      // detailRequest.getModule(), detailRequest.getCourseCode());
          productService.findProductById(Long.valueOf(detailRequest.getCourseId()));
      if (product == null) {
        response = new UserChapterRecordResponse(ResultType.SYSFAIL);
        response.setRetdesc("本课程不存在");
        return response;
      }
      CheckResult checkRes = checkCourseId(detailRequest, product.getId().toString());
      if (!checkRes.isRetOk()) {
        return new UserChapterRecordResponse(checkRes.getResType());
      }
      response.setCourseName(product.getName());
      List<UserChapterRecordVo> userChapterRecordList =
          productServiceFacade.queryChapterRecordList(detailRequest.getUid(), product.getId()
              .toString());
      response.setUserChapterRecordList(processChapterList(userChapterRecordList));
      if (StringUtils.isNotBlank(product.getName())) response.setCourseName(product.getName());
    } catch (Exception e) {
      response = new UserChapterRecordResponse(ResultType.SYSFAIL);
    }
    return response;
  }

  /**
   * 处理章节列表
   * 
   * @param userChapterRecordList
   * @return
   */
  public List<ChapterRecord> processChapterList(List<UserChapterRecordVo> userChapterRecordList) {
    List<ChapterRecord> chapterList = Lists.newArrayList();
    Map<Long, ChapterRecord> _parentMap = Maps.newHashMap();
    for (UserChapterRecordVo chapterRecordVo : userChapterRecordList) {
      ChapterRecord parentChapter = null;
      if (null != chapterRecordVo.getResourceType()) {
        if (chapterRecordVo.getResourceType() == CourseConstant.RESOURCE_TYPE_ITEM) {
          parentChapter = _parentMap.get(chapterRecordVo.getParentId());
          if (parentChapter == null) {
            parentChapter = new ChapterRecord();
            parentChapter.setChapterId(chapterRecordVo.getParentId().toString());
            // 保存父章节至父章节Map中
            _parentMap.put(chapterRecordVo.getParentId(), parentChapter);
            // 这里章列表只保存父章节即可
            chapterList.add(parentChapter);
          }
          parentChapter.addTotalItemCount();
          if (chapterRecordVo.getStarLevel() >= 2) parentChapter.addLearnedItemCount();
          // 局部容错
          if (parentChapter.getLearnedItemCount() > parentChapter.getTotalItemCount()) {
            LoggerUtil
                .error("章节数量异常." + FastJsonUtil.toJson(parentChapter), new RuntimeException());
            parentChapter.setTotalItemCount(parentChapter.getLearnedItemCount());
          }
          // 转化为节信息保存至父章节的子章节列表中
          ItemRecord record = new ItemRecord(chapterRecordVo);
          parentChapter.getItemList().add(record);
        } else if (chapterRecordVo.getResourceType() == CourseConstant.RESOURCE_TYPE_CHAPTER) {
          // 该章节本身就是父章节
          if (_parentMap.containsKey(chapterRecordVo.getChapterId())) {
            _parentMap.get(chapterRecordVo.getChapterId()).setInfo(chapterRecordVo);
            // if (null != courseChapter.getListOrder())
            // _parentMap.get(courseChapter.getId()).setListOrder(courseChapter.getListOrder());
          } else {
            // 保存父章节至父章节Map中
            ChapterRecord chapterRecord = new ChapterRecord(chapterRecordVo);
            _parentMap.put(chapterRecordVo.getChapterId(), chapterRecord);
            // 这里章列表只保存父章节即可
            chapterList.add(chapterRecord);
          }
        }
      }
    }
    return chapterList;
  }

}
