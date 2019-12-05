package com.xiaodou.course.service.product;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Resource;

import org.jsoup.helper.StringUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xiaodou.common.util.CommUtil;
import com.xiaodou.common.util.ConfigProp;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.course.common.enums.ResourceType;
import com.xiaodou.course.constant.CourseConstant;
import com.xiaodou.course.model.product.FinalExamModel;
import com.xiaodou.course.model.product.ProductItemModel;
import com.xiaodou.course.model.product.ProductModel;
import com.xiaodou.course.model.user.UserCourseHourProgress;
import com.xiaodou.course.service.AbstractService;
import com.xiaodou.course.service.cache.ProductCache;
import com.xiaodou.course.service.facade.ProductServiceFacade;
import com.xiaodou.course.service.queue.QueueService;
import com.xiaodou.course.vo.product.MicroVideo;
import com.xiaodou.course.vo.product.ResourceDescription;
import com.xiaodou.course.vo.productItem.AudioInfo;
import com.xiaodou.course.vo.user.ChapterRecord;
import com.xiaodou.course.vo.user.ItemRecord;
import com.xiaodou.course.vo.user.UserChapterRecordVo;
import com.xiaodou.course.web.request.product.ChapterCardListRequest;
import com.xiaodou.course.web.request.product.ChapterResourceRequest;
import com.xiaodou.course.web.request.product.ProductDetailRequest;
import com.xiaodou.course.web.request.user.UserLearnAchieveRequest;
import com.xiaodou.course.web.response.product.ChapterAudioResponse;
import com.xiaodou.course.web.response.product.ChapterCardListResponse;
import com.xiaodou.course.web.response.product.ChapterCardListResponse.LastOrNextChapter;
import com.xiaodou.course.web.response.product.ChapterResourceResponse;
import com.xiaodou.course.web.response.product.UserChapterRecordResponse;
import com.xiaodou.course.web.response.resultType.ProductResType;
import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.summer.dao.param.IQueryParam;
import com.xiaodou.summer.dao.param.QueryParam;
import com.xiaodou.summer.vo.out.ResultType;

/**
 * Created by zyp on 15/8/9.
 */
@Service("productItemService")
public class ProductItemService extends AbstractService {

  @Resource
  ProductCache productCache;

  @Resource
  ProductService productService;

  @Resource
  QueueService queueService;

  @Resource
  UserLearnAchieveService userLearnAchieveService;
  @Resource
  CourseKeywordService courseKeywordService;
  @Resource
  CourseKeywordResourceService courseKeywordResourceService;

  @Resource
  ProductServiceFacade productServiceFacade;

  @Resource
  ProductItemService productItemService;

  private static final String TEACH_VIDEO = "精讲视频";

  /**
   * 根据Id查找
   * 
   * @param itemId
   * @return
   */
  public ProductItemModel findItemById(Long itemId) {
    if (null == itemId) return null;
    ProductItemModel cond = productCache.getItemInfo(itemId.toString());
    if (null != cond) return cond;
    cond = new ProductItemModel();
    cond.setId(itemId);
    return productServiceFacade.queryProductItemById(cond);
  }

  public List<FinalExamModel> queryFinalExamList(String productId) {
    List<FinalExamModel> examList = productCache.getFinalExamList(productId);
    if (null != examList && examList.size() > 0) return examList;
    return productServiceFacade.queryFinalExamList(productId);
  }

  /**
   * item列表
   * 
   * @param ids
   * @return
   */
  public List<ProductItemModel> queryItemListByIds(List<Integer> ids) {
    if (null == ids || ids.size() == 0) return null;
    List<ProductItemModel> itemList = productCache.getItemList(ids);
    if (null != itemList && itemList.size() > 0) return itemList;
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
  public List<ProductItemModel> queryItemListByChapterId(Long chapterId) {
    if (null == chapterId) return null;
    List<ProductItemModel> itemList = productCache.getChapterItemList(chapterId.toString());
    if (null != itemList && itemList.size() > 0) return itemList;
    return queryItemList(null, chapterId, null);
  }

  /**
   * 根据productId获取item列表
   * 
   * @param productId
   * @return
   */
  public List<ProductItemModel> queryItemListByProductId(Long productId) {
    if (null == productId) return null;
    // List<ProductItemModel> itemList = productCache.getProductItemList(productId.toString());
    // if (null != itemList && itemList.size() > 0) return itemList;
    return queryItemList(productId, null, null);
  }

  /**
   * 根据productId获取chapter列表
   * 
   * @param productId
   * @return
   */
  public List<ProductItemModel> queryChapterListByProductId(String productId) {
    if (null == productId) return null;
    List<ProductItemModel> chapterList = productCache.getChapterList(productId.toString());
    if (null != chapterList && chapterList.size() > 0) return chapterList;
    return queryItemList(productId, 0, null);
  }

  /**
   * 根据产品id，父id，id 查找章节数据
   * 
   * @param productId
   * @param parentId
   * @param id
   * @return
   */
  public List<ProductItemModel> queryItemList(Object productId, Object parentId, Object id) {
    Map<String, Object> cond = new HashMap<>();
    if (null != productId && StringUtils.isNotBlank(productId.toString()))
      cond.put("productId", productId);
    if (null != parentId && StringUtils.isNotBlank(parentId.toString()))
      cond.put("parentId", parentId);
    if (null != id && StringUtils.isNotBlank(id.toString())) cond.put("id", id);
    cond.put("resourceType", CourseConstant.RESOURCE_TYPE_COURSE);
    cond.put("showStatus", 1);
    Map<String, Object> output = new HashMap<>();
    CommUtil.getBasicField(output, ProductItemModel.class);
    Page<ProductItemModel> productItemModelPage =
        productServiceFacade.queryProductItemListByCondWithOutPage(cond, output);
    return productItemModelPage.getResult();
  }

  public List<ProductItemModel> queryItemList(Object productId, Object parentId, Object id,
      Long resourceType) {
    Map<String, Object> cond = new HashMap<>();
    if (null != productId && StringUtils.isNotBlank(productId.toString()))
      cond.put("productId", productId);
    if (null != parentId && StringUtils.isNotBlank(parentId.toString()))
      cond.put("parentId", parentId);
    if (null != id && StringUtils.isNotBlank(id.toString())) cond.put("id", id);
    cond.put("resourceType", resourceType);
    cond.put("showStatus", 1);
    Map<String, Object> output = new HashMap<>();
    CommUtil.getBasicField(output, ProductItemModel.class);
    Page<ProductItemModel> productItemModelPage =
        productServiceFacade.queryProductItemListByCondWithOutPage(cond, output);
    return productItemModelPage.getResult();
  }

  /**
   * 闯关章节列表(自考菌)
   * 
   * @param detailRequest
   * @return
   */
  public UserChapterRecordResponse chapterList(ProductDetailRequest detailRequest, String version) {
    UserChapterRecordResponse response = new UserChapterRecordResponse(ResultType.SUCCESS);
    try {
      CheckResult checkRes =
          checkIsValidCourseId(detailRequest, detailRequest.getCourseId().toString());
      // 查询产品信息
      ProductModel product =
          productService.findProductById(Long.valueOf(detailRequest.getCourseId()));
      if (!checkRes.isRetOk()) {
        return new UserChapterRecordResponse(checkRes.getResType());
      }
      response.setCourseName(product.getName());
      List<UserChapterRecordVo> userChapterRecordList =
          productServiceFacade.queryChapterRecordList(detailRequest.getUid(), product.getId()
              .toString());
      response.setUserChapterRecordList(processChapterList(userChapterRecordList, version));
      // 期末考试逻辑
      List<FinalExamModel> list =
          productServiceFacade.findFinalExamListByCond(detailRequest.getCourseId(),
              detailRequest.getUid(), null);
      ChapterRecord finalRecord = new ChapterRecord();
      finalRecord.setChapterIndex("期末测试");
      finalRecord.setChapterName("期末测试");
      finalRecord.setTotalItemCount(list.size());
      finalRecord.setChapterId(CourseConstant.FINAL_EXAM_CHAPTER_ID);
      for (FinalExamModel f : list) {
        if (f.getScore() != null && Double.valueOf(f.getScore()) >= 60)
          finalRecord.addLearnedItemCount();
        int chapterCount = 0;
        int finishCount = 0;
        if (userChapterRecordList != null && userChapterRecordList.size() > 0) {
          for (UserChapterRecordVo item : userChapterRecordList) {
            if ((item.getParentId().toString().equals("0"))) {
              chapterCount++;
              if (item.getScore() != null && item.getScore() >= 60) {
                finishCount++;
              }
            }
          }
        }

        ItemRecord finalExam = new ItemRecord(f);
        if (ConfigProp.getParams("lock") != null && ConfigProp.getParams("lock").equals("1")) {
          if (finishCount < chapterCount) {
            finalExam.setLock("1");
          }
        }

        finalRecord.getItemList().add(finalExam);
      }

      if (list != null && list.size() > 0) {
        response.getUserChapterRecordList().add(finalRecord);
      }
      if (StringUtils.isNotBlank(product.getName())) response.setCourseName(product.getName());
    } catch (Exception e) {
      LoggerUtil.error("chapterList", e);
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
  public List<ChapterRecord> processChapterList(List<UserChapterRecordVo> userChapterRecordList,
      String version) {
    List<ChapterRecord> chapterList = Lists.newArrayList();

    Map<Long, ChapterRecord> _parentMap = Maps.newHashMap();
    Map<Long, Integer> finishCountMap = Maps.newHashMap();
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
          if (chapterRecordVo.getStarLevel() >= 1) parentChapter.addLearnedItemCount();
          // 局部容错
          if (parentChapter.getLearnedItemCount() > parentChapter.getTotalItemCount()) {
            LoggerUtil
                .error("章节数量异常." + FastJsonUtil.toJson(parentChapter), new RuntimeException());
            parentChapter.setTotalItemCount(parentChapter.getLearnedItemCount());
          }
          // 转化为节信息保存至父章节的子章节列表中
          ItemRecord record = new ItemRecord(chapterRecordVo);
          parentChapter.getItemList().add(record);

          if (!finishCountMap.containsKey(chapterRecordVo.getParentId())) {
            finishCountMap.put(chapterRecordVo.getParentId(), 0);
          }
          if (record.getScore() != null && Integer.valueOf(record.getScore()) >= 60) {
            Integer finishCount = finishCountMap.get(chapterRecordVo.getParentId());
            if (null == finishCount) {
              finishCount = 0;
            }
            finishCountMap.put(chapterRecordVo.getParentId(), finishCount + 1);
          }
        } else if (chapterRecordVo.getResourceType() == CourseConstant.RESOURCE_TYPE_CHAPTER) {
          // 该章节本身就是父章节
          if (_parentMap.containsKey(chapterRecordVo.getChapterId())) {
            _parentMap.get(chapterRecordVo.getChapterId()).setInfo(chapterRecordVo);
          } else {
            // 保存父章节至父章节Map中
            ChapterRecord chapterRecord = new ChapterRecord(chapterRecordVo);
            _parentMap.put(chapterRecordVo.getChapterId(), chapterRecord);
            // 这里章列表只保存父章节即可
            chapterList.add(chapterRecord);
          }
          if (!finishCountMap.containsKey(chapterRecordVo.getChapterId())) {
            finishCountMap.put(chapterRecordVo.getChapterId(), 0);
          }
        }
      }
    }

    for (ChapterRecord c : chapterList) {
      if (c.getItemList() != null && c.getItemList().size() > 0) {
        if (_parentMap.containsKey(Long.valueOf(c.getChapterId()))) {
          ChapterRecord chapter = _parentMap.get(Long.valueOf(c.getChapterId()));
          if(chapter == null) continue;
          Integer itemCount = chapter.getItemList().size();
          ItemRecord itemRecord = new ItemRecord(chapter, "0");
          // 不是 1.4.0以前的版本
          if (!version.equals("140")) {
            Integer relationItem = chapter.getRelationItem();
            if (null != relationItem && relationItem != 0) {
              itemRecord.setItemType("3");
              itemRecord.setItemName(TEACH_VIDEO);
            }
          }
          if (ConfigProp.getParams("lock") != null && ConfigProp.getParams("lock").equals("1")) {
            if (finishCountMap.get(Long.valueOf(c.getChapterId())) < itemCount) {
              itemRecord.setLock("1");
            }
          }

          c.getItemList().add(itemRecord);
          c.addTotalItemCount();
          if (!StringUtil.isBlank(c.getStarLevel())) {
            if (Integer.valueOf(c.getStarLevel()) >= 1) {
              c.addLearnedItemCount();
            }
          }
        }
      }
    }
    return chapterList;
  }


  public ChapterAudioResponse chapterAudio(ChapterResourceRequest req) {
    ChapterAudioResponse response = new ChapterAudioResponse(ResultType.SUCCESS);
    try {
      List<ProductItemModel> list =
          this.queryItemList(req.getCourseId(), req.getItemId(), null,
              CourseConstant.RESOURCE_TYPE_AUDIO);
      if (!CollectionUtils.isEmpty(list)) {
        ProductItemModel itemModel = list.get(0);
        // if (StringUtils.isJsonBlank(itemModel.getResource())) {
        // // TODO alarm...
        // return response;
        // }
        ResourceDescription resourceDes =
            FastJsonUtil.fromJson(itemModel.getResource(), ResourceDescription.class);
        // if (null == resourceDes || null == resourceDes.getSize()
        // || null == resourceDes.getTimeLengthMinute()
        // || null == resourceDes.getTimeLengthSecond() || null == resourceDes.getUrl()) {
        // // TODO alarm...
        // continue;
        // }
        AudioInfo audioInfo = new AudioInfo();
        audioInfo.setAudioId(itemModel.getId());
        audioInfo.setAudioName(itemModel.getName());
        audioInfo.setCourseId(itemModel.getProductId());
        audioInfo.setItemId(itemModel.getParentId());
        audioInfo.setSize(resourceDes.getSize() + "M");
        audioInfo.setTimeLength(resourceDes.getTimeLengthMinute()
            + "分"
            + (resourceDes.getTimeLengthSecond() > 9
                ? resourceDes.getTimeLengthSecond()
                : ("0" + resourceDes.getTimeLengthSecond())) + "秒");
        audioInfo.setUrl(resourceDes.getUrl());
        response.setAudioInfo(audioInfo);
      }
    } catch (Exception e) {
      LoggerUtil.error("chapterAudio", e);
      response = new ChapterAudioResponse(ResultType.SYSFAIL);
    }
    return response;
  }

  public ChapterResourceResponse chapterResource(ChapterResourceRequest req) {
    ChapterResourceResponse response = new ChapterResourceResponse(ResultType.SUCCESS);
    try {
      CheckResult checkRes = checkIsValidCourseId(req, req.getCourseId());
      if (!checkRes.isRetOk()) {
        return new ChapterResourceResponse(checkRes.getResType());
      }
      // 设置章节信息
      if (req.getChapterId().equals(req.getItemId())) {
        // 章总结
        req.setChapterId("0");
      }
      List<ProductItemModel> itemList =
          queryItemList(req.getCourseId(), req.getChapterId(), req.getItemId());
      if (null == itemList || itemList.size() == 0)
        return new ChapterResourceResponse(ProductResType.CantFindItem);
      ProductItemModel itemInfo = itemList.get(0);
      response.setCourseId(req.getCourseId());
      response.setChapterId(req.getChapterId());
      response.setItemId(req.getItemId());
      response.setItemName(itemInfo.getName());
      response.setNextItemInfo(nextItem(Long.valueOf(req.getCourseId()),
          Long.valueOf(req.getChapterId()), Long.valueOf(req.getItemId())));
      response.setPreviousItemInfo(previousItem(Long.valueOf(req.getCourseId()),
          Long.valueOf(req.getChapterId()), Long.valueOf(req.getItemId())));
      // 1 获取视频资源地址
      if (itemInfo.getResourceType() == ResourceType.CHAPTER.getTypeId()) {
        if (itemInfo.getRelationItem() != null && itemInfo.getRelationItem() != 0) {
          ProductItemModel relationItem = this.findItemById(itemInfo.getRelationItem());
          response.setResourceContent(relationItem.getDetail());
          response.setResourceType(relationItem.getResourceType());
          if (null != relationItem && StringUtils.isNotBlank(relationItem.getResource())) {
            ResourceDescription resourceInfo =
                JSON.parseObject(relationItem.getResource(), ResourceDescription.class);
            response.setResourceUrl(resourceInfo.getFileUrl());
            response.setWebUrl(resourceInfo.getUrl());
            // 记录学习进度
            recordLearnProcess(req);
          } else {
            response.setResourceUrl(null);
          }
        } else {
          response.setResourceUrl(null);
        }
      } else {
        if (StringUtils.isNotBlank(itemInfo.getResource())) {
          ResourceDescription resourceInfo =
              JSON.parseObject(itemInfo.getResource(), ResourceDescription.class);
          response.setResourceUrl(resourceInfo.getFileUrl());
          // 记录学习进度
          recordLearnProcess(req);
        } else {
          response.setResourceUrl(null);
        }
      }
      // 资源类型是微课的话
      // if (itemInfo.getResourceType() == ResourceType.MICROVIDEO.getTypeId()) {
      List<ProductItemModel> resOfItem =
          productItemService.queryItemList(req.getCourseId(), req.getItemId(), null,
              CourseConstant.RESOURCE_TYPE_MICRO);
      if (null != resOfItem && !resOfItem.isEmpty() && "1.6.0".compareTo(req.getVersion()) > 0) {
        response = new ChapterResourceResponse(ResultType.SYSFAIL);
        response.setRetdesc("当前版本不支持学习该课程。请升级至最新版学习。");
        return response;
      }

      List<MicroVideo> points = Lists.newArrayList();
      // 课件资源只有一种故此处先临时这样写
      for (ProductItemModel item : resOfItem) {
        MicroVideo vo = new MicroVideo();
        // CourseResourceVideoModel video =
        // productServiceFacade.queryResourceVideoById(item.getResourceId());
        ResourceDescription video = JSON.parseObject(item.getResource(), ResourceDescription.class);
        if (video == null) continue;
        vo.setContent(item.getDetail());
        vo.setPointId(item.getId() + "");
        vo.setPointName(item.getName());
        if (null != item && StringUtils.isNotBlank(item.getPictureUrl())) {
          vo.setVideoCover(item.getPictureUrl().trim());
        }
        if (null != video && StringUtils.isNotBlank(video.getFileUrl())) {
          vo.setVideoUrl(video.getFileUrl());
        }
        long videoLength = 0l;
        if (null != video.getTimeLengthMinute()) videoLength += video.getTimeLengthMinute() * 60;
        if (null != video.getTimeLengthSecond()) videoLength += video.getTimeLengthSecond();
        vo.setVideoDuration(videoLength + "");

        buildMicroVoLearnProgress(vo, vo.getPointId(), req);
        points.add(vo);
      }
      if (points.size() > 0) response.setPoints(points);
      // }

    } catch (Exception e) {
      LoggerUtil.error("卡片资源异常", e);
      response = new ChapterResourceResponse(ResultType.VALFAIL);
    }
    return response;
  }



  private void buildMicroVoLearnProgress(MicroVideo vo, String pointId, ChapterResourceRequest req) {
    IQueryParam param = new QueryParam();
    param.addInput("module", req.getModule());
    param.addInput("courseId", req.getCourseId());
    param.addInput("chapterId", req.getChapterId());
    param.addInput("itemId", req.getItemId());
    param.addInput("userId", req.getUid());
    param.addInput("keyPointId", pointId);
    param.addInput("resourceType", CourseConstant.RESOURCE_TYPE_MICRO);
    param.addOutputs(CommUtil.getGeneralField(UserCourseHourProgress.class));
    Page<UserCourseHourProgress> page =
        productServiceFacade.queryUserCourseHourProgress(param, null);
    if (null == page || page.getResult().size() == 0) return;
    UserCourseHourProgress pro = page.getResult().get(0);
    vo.setLastLearnTime(pro.getLearnTime() + "");
    if (pro.getDuration() != null && pro.getDuration() > 0) {
      String progress =
          CourseConstant.DD_FORMAT.format((double) pro.getLearnTime() / pro.getDuration());
      vo.setProgress(progress);
    }
  }


  /**
   * 记录学习进度
   */
  private void recordLearnProcess(ChapterResourceRequest req) {
    UserLearnAchieveRequest vo = new UserLearnAchieveRequest();
    vo.setUid(req.getUid());
    vo.setModule(req.getModule());
    vo.setCourseId(req.getCourseId());
    vo.setChapterId(req.getChapterId());
    vo.setItemId(req.getItemId());
    userLearnAchieveService.saveLearnAchieve(vo);
  }

  /**
   * @description 包装 每一章的第一节 数据
   * @author 李德洪
   * @Date 2018年2月9日
   * @param itemList
   * @return
   */
  private Map<Long, ProductItemModel> warpPerChapterOfFirstItem(List<ProductItemModel> itemList) {
    Map<Long, ProductItemModel> map = Maps.newHashMap();

    for (ProductItemModel item : itemList) {
      if (item.getResourceType() == 1 && item.getParentId() != 0
          && !map.containsKey(item.getParentId().longValue())) {
        map.put(item.getParentId().longValue(), item);
      }
    }

    return map;
  }

  /**
   * @description 获取下一节数据
   * @author 李德洪
   * @Date 2018年2月9日
   * @param courseId
   * @param chapterId
   * @param itemId
   * @return
   */
  public ProductItemModel nextItem(Long courseId, Long chapterId, Long itemId) {
    List<ProductItemModel> itemList = queryItemList(courseId, null, null);
    if (null == itemList || itemList.size() == 0) return null;
    for (int i = 0; i < itemList.size(); i++) {
      ProductItemModel item = itemList.get(i);
      if (null == item || item.getResourceType() != 1) continue;
      if (null != itemId && item.getId() != itemId.intValue()) continue;
      if (null == itemId && null != chapterId) {
        return warpPerChapterOfFirstItem(itemList).get(chapterId);
      }
      return loopNextItem(itemList, i);
    }
    return null;
  }

  /**
   * @description 递归 去掉 章 数据
   * @author 李德洪
   * @Date 2018年2月9日
   * @param itemList
   * @param point
   * @return
   */
  private ProductItemModel loopNextItem(List<ProductItemModel> itemList, int point) {
    if (itemList.size() <= point + 1) return null;
    if (null != itemList.get(point + 1) && itemList.get(point + 1).getResourceType() == 1
        && itemList.get(point + 1).getParentId() != 0) {
      return itemList.get(point + 1);
    }
    return loopNextItem(itemList, point + 1);
  }


  /**
   * @description 包装 每一章的最后一节 数据
   * @author 李德洪
   * @Date 2018年2月9日
   * @param itemList
   * @return
   */
  private TreeMap<Long, ProductItemModel> warpPerChapterOfLastItem(List<ProductItemModel> itemList) {
    TreeMap<Long, ProductItemModel> map = Maps.newTreeMap();
    for (ProductItemModel item : itemList) {
      if (item.getResourceType() == 1 && item.getParentId() != 0) {
        map.put(item.getParentId().longValue(), item);
      }
    }

    return map;
  }

  /**
   * @description 获取上一节数据
   * @author 李德洪
   * @Date 2018年2月9日
   * @param courseId
   * @param chapterId
   * @param itemId
   * @return
   */
  public ProductItemModel previousItem(Long courseId, Long chapterId, Long itemId) {
    List<ProductItemModel> itemList = queryItemList(courseId, null, null);
    if (null == itemList || itemList.size() == 0) return null;

    for (int i = 0; i < itemList.size(); i++) {
      ProductItemModel item = itemList.get(i);
      if (null == item || item.getResourceType() != 1) continue;
      if (null != itemId && item.getId() != itemId.intValue()) continue;
      if (null == itemId && null != chapterId) {
        // 上一章的最后一节
        return warpPerChapterOfLastItem(itemList).lowerEntry(chapterId).getValue();
      }
      return loopPreviousItem(itemList, i);
    }
    return null;
  }

  private ProductItemModel loopPreviousItem(List<ProductItemModel> itemList, int point) {
    int beforePoint = point - 1;
    if (point == 0) {
      return null;
      // return itemList.get(0);
    }
    if (null != itemList.get(beforePoint) && itemList.get(beforePoint).getResourceType() == 1
        && itemList.get(beforePoint).getParentId() != 0) {
      return itemList.get(beforePoint);
    }
    return loopPreviousItem(itemList, beforePoint);
  }

  /**
   * @param pojo
   * @param version
   * @return
   */
  public ChapterCardListResponse chapterCardList(ChapterCardListRequest pojo, String version) {
    ChapterCardListResponse response = new ChapterCardListResponse(ResultType.SUCCESS);
    try {
      CheckResult checkRes = checkIsValidCourseId(pojo, pojo.getCourseId());
      if (!checkRes.isRetOk()) {
        return new ChapterCardListResponse(checkRes.getResType());
      }
      // 查询产品信息
      ProductModel product = productService.findProductById(Long.parseLong(pojo.getCourseId()));
      if (product == null) {
        response = new ChapterCardListResponse(ResultType.SYSFAIL);
        response.setRetdesc("本课程不存在");
        return response;
      }

      // 期末测试逻辑
      List<FinalExamModel> list =
          productServiceFacade.findFinalExamListByCond(pojo.getCourseId(), pojo.getUid(), null);

      Page<ProductItemModel> itemPage =
          productServiceFacade.queryAllProductItem(pojo.getCourseId());
      // 判断有上一章、下一章
      rollIndexBuilder(response, list, pojo, itemPage);

      if (((StringUtils.isBlank(pojo.getChapterId()) || pojo.getChapterId().equals("-1")) && StringUtils
          .isNotBlank(pojo.getItemId()))
          || (StringUtils.isNotBlank(pojo.getChapterId()) && pojo.getChapterId().equals(
              CourseConstant.FINAL_EXAM_CHAPTER_ID))) {
        response.setShowCardIndex("0");
        response.setChapterIndex("期末测试");
        response.setChapterId(CourseConstant.FINAL_EXAM_CHAPTER_ID);
        if (list != null && list.size() > 0) {
          List<UserChapterRecordVo> itemRecordList =
              productServiceFacade.queryChapterRecordList(pojo.getUid(), pojo.getCourseId());
          Integer show = 0;
          for (FinalExamModel f : list) {
            int chapterCount = 0;
            int finishCount = 0;
            if (itemRecordList != null && itemRecordList.size() > 0) {
              for (UserChapterRecordVo itemRecord : itemRecordList) {
                if ((itemRecord.getParentId().toString().equals("0"))) {
                  chapterCount++;
                  if (itemRecord.getScore() != null && itemRecord.getScore() >= 60) {
                    finishCount++;
                  }
                }
              }
            }
            ItemRecord finalExam = new ItemRecord(f);
            if (ConfigProp.getParams("lock") != null && ConfigProp.getParams("lock").equals("1")) {
              if (finishCount < chapterCount) {
                finalExam.setLock("1");
              }
            }
            response.getItemList().add(finalExam);
            if (StringUtils.isNotBlank(pojo.getItemId())
                && pojo.getItemId().equals(f.getId().toString())) {
              response.setShowCardIndex(show.toString());
            }
            show++;
          }
          return response;
        } else {
          response = new ChapterCardListResponse(ResultType.SYSFAIL);
          response.setRetdesc("期末测试不存在");
          return response;
        }
      }

      // 获取音频列表
      INIT_AUDIO_LIST: {
        List<AudioInfo> audioInfoList = Lists.newArrayList();
        // Page<ProductItemModel> itemPage =
        // productServiceFacade.queryAllProductItem(pojo.getCourseId());
        if (null == itemPage || itemPage.getResult() == null || itemPage.getResult().size() == 0)
          break INIT_AUDIO_LIST;
        List<ProductItemModel> itemList = itemPage.getResult();
        Map<Long, ProductItemModel> itemMap = Maps.newHashMap();
        for (ProductItemModel itemModel : itemList) {
          if (null == itemModel || null == itemModel.getId()) continue;
          itemMap.put(itemModel.getId(), itemModel);
          if (itemModel.getResourceType() == null
              || CourseConstant.RESOURCE_TYPE_AUDIO.intValue() != itemModel.getResourceType())
            continue;
          AudioInfo audioInfo = new AudioInfo();
          audioInfo.setAudioId(itemModel.getId());
          audioInfo.setAudioName(itemModel.getName());
          audioInfo.setCourseId(itemModel.getProductId());
          audioInfo.setCourseName(product.getName());
          audioInfo.setItemId(itemModel.getParentId());
          if (StringUtils.isJsonBlank(itemModel.getResource())) {
            // TODO alarm...
            continue;
          }
          ResourceDescription resourceDes =
              FastJsonUtil.fromJson(itemModel.getResource(), ResourceDescription.class);
          if (null == resourceDes || null == resourceDes.getSize()
              || null == resourceDes.getTimeLengthMinute()
              || null == resourceDes.getTimeLengthSecond() || null == resourceDes.getUrl()) {
            // TODO alarm...
            continue;
          }
          audioInfo.setSize(resourceDes.getSize() + "M");
          audioInfo.setTimeLength(resourceDes.getTimeLengthMinute()
              + "分"
              + (resourceDes.getTimeLengthSecond() > 9
                  ? resourceDes.getTimeLengthSecond()
                  : ("0" + resourceDes.getTimeLengthSecond())) + "秒");
          audioInfo.setUrl(resourceDes.getUrl());
          audioInfoList.add(audioInfo);
        }
        if (null == audioInfoList || audioInfoList.size() == 0) break INIT_AUDIO_LIST;
        for (AudioInfo audio : audioInfoList) {
          ProductItemModel item = itemMap.get(audio.getItemId());
          if (null == item) continue;
          if (item.getParentId() == 0)
            audio.setChapterId(item.getId());
          else {
            ProductItemModel chapter = itemMap.get(item.getParentId());
            if (null == chapter) continue;
            audio.setChapterId(chapter.getId());
          }
        }
        response.setAudioList(audioInfoList);
      }



      // 去课时任务完成记录表内去查询该用户的所有节的完成情况
      List<UserCourseHourProgress> progressLis = queryUserCourseHourProgress0(pojo);

      // 正常章节
      UserChapterRecordVo chapter = null;
      if (StringUtils.isNotBlank(pojo.getChapterId())) {
        chapter =
            productServiceFacade.queryChapterRecord(pojo.getUid(), pojo.getCourseId(),
                pojo.getChapterId());
      } else {
        List<UserChapterRecordVo> chapterList =
            productServiceFacade.queryChapterRecordList(pojo.getUid(), pojo.getCourseId());
        if (null != chapterList && chapterList.size() > 0) chapter = chapterList.get(0);
      }
      if (null == chapter) return new ChapterCardListResponse(ProductResType.CantFindItem);
      response.setChapterInfo(chapter);
      List<UserChapterRecordVo> itemRecordList =
          productServiceFacade.queryItemRecordList(pojo.getUid(), pojo.getCourseId(), chapter
              .getChapterId().toString());

      // modify by zdhuang at 2018-7-12 17:11:19
      Map<String, ProductItemModel> sectionsResourceInfos =
          resourceOfSectionsBuilder(itemRecordList, itemPage);

      if (null != itemRecordList && itemRecordList.size() > 0) {
        Integer itemCount = 0;
        Integer finishCount = 0;
        // 需要展示的卡片序号(item数组下标)
        String showCardIndex = "0";
        for (UserChapterRecordVo itemRecord : itemRecordList) {
          if (StringUtils.isNotEmpty(pojo.getItemId())
              && itemRecord.getChapterId().toString().equals(pojo.getItemId())) {
            showCardIndex = itemCount.toString();
          }
          ItemRecord record = new ItemRecord(itemRecord);
          response.getItemList().add(record);
          if (itemRecord.getScore() != null && itemRecord.getScore() >= 60) {
            finishCount++;
          }
          itemCount++;
          // 该节不是章总结
          getLearnProgress(progressLis, itemPage, record, sectionsResourceInfos);
        }
        ItemRecord chapterRecord = new ItemRecord(chapter, "0");

        // 不是 1.4.0以前的版本
        if (!version.equals("140")) {
          Integer relationItem = chapter.getRelationItem();
          if (null != relationItem && relationItem != 0) {
            chapterRecord.setItemType("3");
            chapterRecord.setItemName(TEACH_VIDEO);
          }
        }

        if (ConfigProp.getParams("lock") != null && ConfigProp.getParams("lock").equals("1")) {
          if (finishCount < itemCount) {
            chapterRecord.setLock("1");
          }
        }
        response.getItemList().add(chapterRecord);

        // 章总结判断
        if (StringUtils.isNotEmpty(pojo.getChapterId())
            && (StringUtils.isBlank(pojo.getItemId()) || pojo.getChapterId().equals(
                pojo.getItemId()))) {
          showCardIndex = Integer.toString(response.getItemList().size() - 1);
        }
        response.setShowCardIndex(showCardIndex);
      }

    } catch (Exception e) {
      LoggerUtil.error("进入章节卡片页面出错", e);
      response = new ChapterCardListResponse(ResultType.SYSFAIL);
    }
    return response;
  }



  private Map<String, ProductItemModel> resourceOfSectionsBuilder(
      List<UserChapterRecordVo> itemRecordList, Page<ProductItemModel> itemPage) {
    Map<String, ProductItemModel> infos = Maps.newConcurrentMap();
    ArrayList<Long> sectionIds = Lists.newArrayList();
    for (UserChapterRecordVo vo : itemRecordList) {
      sectionIds.add(vo.getChapterId());
    }

    if (itemPage == null || itemPage.getResult().size() == 0) return null;
    for (ProductItemModel item : itemPage.getResult()) {
      if (sectionIds.contains(item.getParentId())) {
        infos.put(item.getParentId() + "", item);
      }
    }
    return infos;
  }

  /**
   * @param response
   * @param list 该章下期末测试列表
   * @param pojo
   * @param itemPage 该列表的查询条件仅为courseId
   */
  private void rollIndexBuilder(ChapterCardListResponse response, List<FinalExamModel> list,
      ChapterCardListRequest pojo, Page<ProductItemModel> itemPage) {
    LastOrNextChapter lnc = new LastOrNextChapter();
    if (null == itemPage || itemPage.getResult().size() == 0) return;
    List<ProductItemModel> bulks = Lists.newArrayList();
    for (ProductItemModel item : itemPage.getResult()) {
      if (item != null && pojo.getCourseId() != null
          && pojo.getCourseId().equals(item.getProductId() + "") && item.getLevel() == 0)
        bulks.add(item);
    }
    Long examId = null, prevChapter = null, nextChapter = null;
    if (null != list && list.size() > 0) {
      examId = list.get(0).getId();
    }
    Collections.sort(bulks, new Comparator<ProductItemModel>() {
      @Override
      public int compare(ProductItemModel o1, ProductItemModel o2) {
        if (null == o2.getListOrder()) return 1;
        if (null == o1.getListOrder()) return -1;
        return o1.getListOrder().compareTo(o2.getListOrder());
      }
    });
    for (int i = 0; i < bulks.size(); i++) {
      if (pojo.getChapterId() != null && pojo.getChapterId().equals(bulks.get(i).getId() + "")) {
        if ((i - 1) >= 0) prevChapter = bulks.get(i - 1).getId();
        if ((i + 1) < bulks.size()) nextChapter = bulks.get(i + 1).getId();
      } else if (StringUtils.isBlank(pojo.getChapterId()) && StringUtils.isBlank(pojo.getItemId())) {
        if (bulks.size() > 2) {
          nextChapter = bulks.get(1).getId();
          break;
        }
      } else if (StringUtils.isBlank(pojo.getChapterId())
          || pojo.getChapterId().equals(CourseConstant.FINAL_EXAM_CHAPTER_ID)) {
        if (bulks.size() > 0) {
          prevChapter = bulks.get(bulks.size() - 1).getId();
          break;
        }
      }
    }
    if (null != prevChapter && nextChapter != null) {
      lnc.setNextChapterId(nextChapter.toString());
      lnc.setPreviousChapterId(prevChapter.toString());
    } else if (null == prevChapter && nextChapter != null) {
      // 第一章
      lnc.setNextChapterId(nextChapter.toString());
    } else if (null != prevChapter && nextChapter == null) {
      // 最后一章
      lnc.setPreviousChapterId(prevChapter.toString());
      if (pojo.getChapterId() != null
          && !pojo.getChapterId().equals(CourseConstant.FINAL_EXAM_CHAPTER_ID) && examId != null) {
        lnc.setNextChapterId(CourseConstant.FINAL_EXAM_CHAPTER_ID);
      }
    }
    response.setLastOrNextChapter(lnc);
  }

  private List<UserCourseHourProgress> queryUserCourseHourProgress0(ChapterCardListRequest pojo) {
    // 先去学习进度表内查找记录
    IQueryParam param = new QueryParam();
    param.addInput("module", pojo.getModule());
    param.addInput("majorId", pojo.getMajorId());
    param.addInput("courseId", pojo.getCourseId());
    param.addInput("chapterId", pojo.getChapterId());
    param.addInput("userId", pojo.getUid());
    param.addOutputs(CommUtil.getGeneralField(UserCourseHourProgress.class));
    Page<UserCourseHourProgress> pageProgress =
        productServiceFacade.queryUserCourseHourProgress(param, null);
    return (null != pageProgress && pageProgress.getResult().size() > 0)
        ? pageProgress.getResult()
        : null;
  }

  private void getLearnProgress(List<UserCourseHourProgress> progressList,
      Page<ProductItemModel> itemPage, ItemRecord itemRecord, Map<String, ProductItemModel> infos) {

    // 该节下的资源数量
    Integer totalCount = 0;
    if (null != itemPage && itemPage.getResult().size() > 0) {
      for (ProductItemModel ele : itemPage.getResult()) {
        if (!ele.getParentId().toString().equals(itemRecord.getItemId())) {
          continue;
        }
        if (ele.getResourceType() != CourseConstant.RESOURCE_TYPE_AUDIO.intValue()
            && ele.getResourceType() != CourseConstant.RESOURCE_TYPE_MICRO.intValue()
            && ele.getResourceType() != CourseConstant.RESOURCE_TYPE_VIDEO.intValue()) {
          continue;
        }
        totalCount++;
      }
    }

    // 完成学习的资源数量
    Double finishCount = 0d;
    if (null != progressList && progressList.size() > 0) {
      for (UserCourseHourProgress pro : progressList) {
        if (null == pro || pro.getItemId() == null || pro.getItemId().equals("-1")) {
          continue;
        }
        if (itemRecord.getItemId().equals(pro.getItemId()) && null != pro.getLearnTime()
            && null != pro.getDuration()) {
          finishCount += new Double(pro.getLearnTime()) / pro.getDuration();
        }
      }
    }

    // 设置resourceType
    if (null != infos && infos.size() != 0) {
      if (infos.containsKey(itemRecord.getItemId())) {
        itemRecord.setResourceType(infos.get(itemRecord.getItemId()).getResourceType().toString());
      }
    }
    if (0 == totalCount) {
      itemRecord.setProgress("0");
      return;
    }
    String percent = CourseConstant.DD_FORMAT.format(finishCount / totalCount);
    itemRecord.setProgress(percent);
  }

}
