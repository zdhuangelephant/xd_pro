package com.xiaodou.ms.service.product;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xiaodou.common.util.RandomUtil;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.ms.common.ResourceType;
import com.xiaodou.ms.dao.mission.MissionDao;
import com.xiaodou.ms.dao.product.FinalExamDao;
import com.xiaodou.ms.dao.product.ProductDao;
import com.xiaodou.ms.dao.product.ProductItemDao;
import com.xiaodou.ms.model.course.CourseChapterModel;
import com.xiaodou.ms.model.course.CourseResourceAudioModel;
import com.xiaodou.ms.model.course.CourseResourceDocModel;
import com.xiaodou.ms.model.course.CourseResourceHtml5Model;
import com.xiaodou.ms.model.course.CourseResourceMicroVideoModel;
import com.xiaodou.ms.model.course.CourseResourceVideoModel;
import com.xiaodou.ms.model.exam.QuestionBankQuestionModel;
import com.xiaodou.ms.model.mission.MissionModel;
import com.xiaodou.ms.model.product.FinalExamModel;
import com.xiaodou.ms.model.product.ProductItemModel;
import com.xiaodou.ms.model.product.ProductMiscInfo;
import com.xiaodou.ms.model.product.ProductModel;
import com.xiaodou.ms.service.course.CourseChapterService;
import com.xiaodou.ms.service.course.CourseMicroVideoService;
import com.xiaodou.ms.service.course.CourseResourceAudioService;
import com.xiaodou.ms.service.course.CourseResourceDocService;
import com.xiaodou.ms.service.course.CourseResourceHtml5Service;
import com.xiaodou.ms.service.course.CourseResourceVideoService;
import com.xiaodou.ms.service.exam.QuestionBankQuestionService;
import com.xiaodou.ms.service.mission.MissionService;
import com.xiaodou.ms.util.tree.TreeNode;
import com.xiaodou.ms.util.tree.TreeUtils;
import com.xiaodou.ms.vo.MicroVideo;
import com.xiaodou.ms.vo.MissionVo;
import com.xiaodou.ms.vo.ResourceDescription;
import com.xiaodou.ms.web.request.product.ProductItemCreateRequest;
import com.xiaodou.ms.web.request.product.ProductItemEditRequest;
import com.xiaodou.ms.web.request.product.ProductTreeRequest;
import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.summer.dao.param.IQueryParam;
import com.xiaodou.summer.dao.param.QueryParam;
import com.xiaodou.summer.dao.param.QueryEnums.Sort;

/**
 * Created by zyp on 15/4/19.
 */

@Service("productItemService")
public class ProductItemService {

  /**
   * 资源分类DAO
   */
  @Resource
  ProductItemDao productItemDao;

  @Resource
  FinalExamDao finalExamDao;

  @Resource
  ProductService productService;

  @Resource
  CourseResourceDocService courseResourceDocService;

  @Resource
  CourseResourceHtml5Service courseResourceHtml5Service;

  @Resource
  CourseResourceVideoService courseResourceVideoService;

  @Resource
  CourseResourceAudioService courseResourceAudioService;

  @Resource
  CourseChapterService courseChapterService;

  @Resource
  ProductItemService productItemService;
  @Resource
  MissionService missionService;
  @Resource
  ProductQuestionService productQuestionService;

  @Resource
  ProductDao productDao;

  @Resource
  MissionDao missionDao;
  // 题库
  @Resource
  QuestionBankQuestionService questionBankQuestionService;

  @Resource
  CourseMicroVideoService courseMicroVideoService;

  /**
   * 更新资源
   * 
   * @param resourceId
   * @param resourceType
   * @param resource
   * @return
   */
  public Boolean updateItemResource(Long resourceId, Integer resourceType, String resource) {
    Map<String, Object> cond = new HashMap<>();
    cond.put("resourceId", resourceId.toString());
    cond.put("resourceType", resourceType);
    ProductItemModel productItemModel = new ProductItemModel();
    productItemModel.setResource(resource);
    return productItemDao.updateEntity(cond, productItemModel);
  }

  /**
   * 新增
   * 
   * @param entity
   * @return
   */
  public ProductItemModel addItem(ProductItemModel entity) {
    ProductItemModel productItemModel = productItemDao.addEntity(entity);
    this.cleanTree(entity.getProductId());
    return productItemModel;
  }

  /**
   * 新增
   * 
   * @param entity
   * @return
   */
  public ProductItemModel addItemNoClean(ProductItemModel entity) {
    ProductItemModel productItemModel = productItemDao.addEntity(entity);
    return productItemModel;
  }

  /**
   * 根据主键查询
   * 
   * @param id
   * @return
   */
  public Page<ProductItemModel> findItems(Long id) {
	  IQueryParam param = new QueryParam();
	  param.addInput("parentId", id);
	  param.addOutput("id", "");
	  return  productItemDao.findEntityListByCond(param, null);
  }
  /**
   * 添加章节
   * 
   * @param request
   * @return
   */
  public ProductItemModel addChapter(ProductItemCreateRequest request) {
    ProductItemModel itemModel = new ProductItemModel();
    itemModel.setId(Long.parseLong(RandomUtil.randomNumber(9)));
    itemModel.setProductId(request.getProductId());
    if (StringUtils.isNotBlank(request.getDetail())) {
      itemModel.setDetail(request.getDetail());
    }
    if (StringUtils.isNotBlank(request.getName())) {
      itemModel.setName(request.getName());
    }
    if (StringUtils.isNotBlank(request.getPictureUrl())) {
      itemModel.setPictureUrl(request.getPictureUrl());
    }
    itemModel.setParentId(request.getParentId());
    itemModel.setResourceType(ResourceType.CHAPTER.getTypeId());
    itemModel.setCreateTime(new Timestamp(System.currentTimeMillis()));
    List<ProductItemModel> queryItemByParentId =
        productItemService.queryItemByParentId(request.getParentId().intValue(), request
            .getProductId().intValue());
    ProductItemModel findItemById = productItemService.findItemById(request.getParentId());
    itemModel.setListOrder(request.getParentId() == 0
        ? queryItemByParentId.size() * 100
        : findItemById.getListOrder() + queryItemByParentId.size() + 1);
    if (StringUtils.isNotBlank(request.getChapterId())) {
      itemModel.setChapterId(request.getChapterId());
    }
    itemModel.setImportanceLevel(request.getImportanceLevel());
    itemModel.setShowStatus(request.getShowStatus());
    itemModel.setIsFree(request.getIsFree());
    ProductItemModel resultModel = this.addItem(itemModel);

    MissionVo missionVo = null;
    ProductItemModel parentItem = productItemService.findItemById(request.getParentId());
    if (request.getParentId() != 0) {
      missionVo = packageItem2Mission(parentItem, itemModel);
      missionVo.setModule(request.getModule());
      missionService.addStandardMission(missionVo);
    } else {
      missionVo = packageChapter2Mission(itemModel);
      missionVo.setModule(request.getModule());
      missionService.addStandardMission(missionVo);
    }
    productService.updateSubjectMisc(request.getProductId(), buildMisc(request.getProductId()));

    return resultModel;
  }

  /**
   * 添加视频
   * 
   * @param request
   * @return
   */
  public ProductItemModel addVideoResource(ProductItemCreateRequest request) {
    CourseResourceVideoModel videoResource =
        courseResourceVideoService.findResourceVideoById(request.getResourceId());
    ProductItemModel itemModel = new ProductItemModel();
    itemModel.setProductId(request.getProductId());
    itemModel.setDetail(request.getDetail());
    itemModel.setName(request.getName());
    itemModel.setParentId(request.getParentId());
    itemModel.setResourceType(Integer.valueOf(videoResource.getType()));
    itemModel.setResourceId(request.getResourceId().toString());
    itemModel.setCreateTime(new Timestamp(System.currentTimeMillis()));
    itemModel.setListOrder(request.getListOrder());
    itemModel.setChapterId(request.getChapterId());
    itemModel.setImportanceLevel(request.getImportanceLevel());
    itemModel.setShowStatus(request.getShowStatus());
    itemModel.setIsFree(request.getIsFree());
    ResourceDescription resource = new ResourceDescription();
    resource.setFileUrl(videoResource.getFileUrl());
    resource.setUrl(videoResource.getUrl());
    resource.setTimeLengthMinute(videoResource.getTimeLengthMinute());
    resource.setTimeLengthSecond(videoResource.getTimeLengthSecond());
    itemModel.setResource(JSON.toJSONString(resource));
    ProductItemModel resultModel = this.addItem(itemModel);
    return resultModel;
  }

  /**
   * 添加音频
   * 
   * @param request
   * @return
   */
  public ProductItemModel addAudioResource(ProductItemCreateRequest request) {
    CourseResourceAudioModel audioResource =
        courseResourceAudioService.findResourceAudioById(request.getResourceId());
    ProductItemModel itemModel = new ProductItemModel();
    itemModel.setProductId(request.getProductId());
    itemModel.setDetail(request.getDetail());
    itemModel.setName(request.getName());
    itemModel.setParentId(request.getParentId());
    itemModel.setResourceType(ResourceType.AUDIO.getTypeId());
    itemModel.setResourceId(request.getResourceId().toString());
    itemModel.setCreateTime(new Timestamp(System.currentTimeMillis()));
    itemModel.setListOrder(request.getListOrder());
    itemModel.setChapterId(request.getChapterId());
    itemModel.setImportanceLevel(request.getImportanceLevel());
    itemModel.setShowStatus(request.getShowStatus());
    itemModel.setIsFree(request.getIsFree());
    ResourceDescription resource = new ResourceDescription();
    resource.setUrl(audioResource.getUrl());
    resource.setTimeLengthMinute(audioResource.getTimeLengthMinute());
    resource.setTimeLengthSecond(audioResource.getTimeLengthSecond());
    resource.setSize(audioResource.getSize());
    itemModel.setResource(JSON.toJSONString(resource));
    ProductItemModel resultModel = this.addItem(itemModel);
    return resultModel;
  }

  /**
   * 添加文档资源
   * 
   * @param request
   * @return
   */
  public ProductItemModel addDocResource(ProductItemCreateRequest request) {
    CourseResourceDocModel docResource =
        courseResourceDocService.findResourceDocById(request.getResourceId());
    ProductItemModel itemModel = new ProductItemModel();
    itemModel.setProductId(request.getProductId());
    itemModel.setDetail(request.getDetail());
    itemModel.setName(request.getName());
    itemModel.setParentId(request.getParentId());
    itemModel.setResourceType(ResourceType.DOC.getTypeId());
    itemModel.setResourceId(request.getResourceId().toString());
    itemModel.setCreateTime(new Timestamp(System.currentTimeMillis()));
    itemModel.setListOrder(request.getListOrder());
    itemModel.setChapterId(request.getChapterId());
    itemModel.setImportanceLevel(request.getImportanceLevel());
    itemModel.setShowStatus(request.getShowStatus());
    itemModel.setIsFree(request.getIsFree());
    ResourceDescription resource = new ResourceDescription();
    resource.setFileUrl(docResource.getFileUrl());
    resource.setUrl(docResource.getUrl());
    itemModel.setResource(JSON.toJSONString(resource));
    ProductItemModel resultModel = this.addItem(itemModel);
    return resultModel;
  }


  /**
   * 添加文档资源
   * 
   * @param request
   * @return
   */
  public ProductItemModel addMicroVideoResource(ProductItemCreateRequest request) {
    CourseResourceMicroVideoModel microResource =
        courseMicroVideoService.findResourceMicroVideoById(request.getResourceId());
    // CourseResourceDocModel docResource =
    // courseResourceDocService.findResourceDocById(request.getResourceId());
    ProductItemModel itemModel = new ProductItemModel();
    itemModel.setProductId(request.getProductId());
    itemModel.setDetail(request.getDetail());
    itemModel.setName(request.getName());
    itemModel.setParentId(request.getParentId());
    itemModel.setResourceType(ResourceType.DOC.getTypeId());
    itemModel.setResourceId(request.getResourceId().toString());
    itemModel.setCreateTime(new Timestamp(System.currentTimeMillis()));
    itemModel.setListOrder(request.getListOrder());
    itemModel.setChapterId(request.getChapterId());
    itemModel.setImportanceLevel(request.getImportanceLevel());
    itemModel.setShowStatus(request.getShowStatus());
    itemModel.setIsFree(request.getIsFree());
    MicroVideo mv = new MicroVideo();
    mv.setKeypointId(microResource.getKeyPoint());
    mv.setPointName(microResource.getName());
    mv.setPointVideoId(microResource.getId().toString());
    mv.setPointVideoCover(microResource.getVideoCover());
    mv.setPointVideoUrl(microResource.getUrl());
    mv.setPointDuration(String.valueOf(microResource.getTimeLengthMinute() == null
        ? 0
        : (microResource.getTimeLengthMinute() * 60)
            + (microResource.getTimeLengthSecond() == null ? 0 : microResource
                .getTimeLengthSecond())));
    // no set lastDuration/ no set progress
    ArrayList<MicroVideo> mvList = Lists.newArrayList();
    itemModel.setResource(JSON.toJSONString(mvList.add(mv)));
    ProductItemModel resultModel = this.addItem(itemModel);
    return resultModel;
  }

  /**
   * 添加Html5资源
   * 
   * @param request
   * @return
   */
  public ProductItemModel addHtml5Resource(ProductItemCreateRequest request) {
    CourseResourceHtml5Model html5Resource =
        courseResourceHtml5Service.findResourceHtml5ById(request.getResourceId());

    ProductItemModel itemModel = new ProductItemModel();
    itemModel.setProductId(request.getProductId());
    itemModel.setDetail(request.getDetail());
    itemModel.setName(request.getName());
    itemModel.setParentId(request.getParentId());
    itemModel.setResourceType(ResourceType.HTML5.getTypeId());
    itemModel.setResourceId(request.getResourceId().toString());
    itemModel.setCreateTime(new Timestamp(System.currentTimeMillis()));
    itemModel.setListOrder(request.getListOrder());
    itemModel.setChapterId(request.getChapterId());
    itemModel.setImportanceLevel(request.getImportanceLevel());
    itemModel.setShowStatus(request.getShowStatus());
    ResourceDescription resource = new ResourceDescription();
    resource.setFileUrl(html5Resource.getFileUrl());
    resource.setUrl(html5Resource.getUrl());
    itemModel.setResource(JSON.toJSONString(resource));
    itemModel.setIsFree(request.getIsFree());
    ProductItemModel resultModel = this.addItem(itemModel);

    return resultModel;
  }

  /**
   * 删除目录
   * 
   * @param id
   * @return
   */
  public Boolean deleteItem(Long id) {
    ProductItemModel productItemModel = this.findItemById(id);
    Map<String, Object> cond = new HashMap<>();
    cond.put("id", id);
    productItemDao.deleteEntity(cond);
    missionService.deleteStandardMission(id.toString());
    // 删除记录
    productQuestionService.deleteQuestionsByChapter(productItemModel.getProductId(), id);
    Boolean res = this.cleanTree(productItemModel.getProductId());
    productService.updateSubjectMisc(productItemModel.getProductId(),
        buildMisc(productItemModel.getProductId()));
    return res;
  }

  /**
   * 更新目录
   * 
   * @param entity
   * @return
   */
  public Boolean editItem(ProductItemModel entity) {
    ProductItemModel productItemModel = this.findItemById(entity.getId());
    Map<String, Object> cond = new HashMap<>();
    cond.put("id", entity.getId());
    productItemDao.updateEntity(cond, entity);
    // 于此同时需要更新course_product表中的misc字段所表示的当前product下的章、节总数统计
    productService.updateSubjectMisc(productItemModel.getProductId(),
        buildMisc(productItemModel.getProductId()));
    if (entity.getParentId() != null) {
      return this.cleanTree(productItemModel.getProductId());
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
  public Boolean editItem(ProductItemEditRequest request) {
    ProductItemModel itemModel = new ProductItemModel();
    itemModel.setIsFree(request.getIsFree());
    itemModel.setName(request.getName());
    if (StringUtils.isNotBlank(request.getPictureUrl()))
      itemModel.setPictureUrl(request.getPictureUrl());
    itemModel.setId(request.getId());
    itemModel.setShowStatus(request.getShowStatus());
    itemModel.setListOrder(request.getListOrder().intValue());
    itemModel.setChapterId(request.getChapterId());
    itemModel.setDetail(request.getDetail());
    itemModel.setParentId(request.getParentId());
    itemModel.setImportanceLevel(request.getImportanceLevel());
	List<MissionModel>  missionList=missionService.findStMissionById(itemModel.getId().toString());
	if(missionList!=null&&missionList.size()>0){
    	MissionModel mission = missionList.get(0);
        mission.setMissionOrder(itemModel.getListOrder().intValue());
        missionService.editMission(mission);
	}
    Boolean aBoolean = this.editItem(itemModel);
    return aBoolean;
  }

  /**
   * 根据主键查询
   * 
   * @param id
   * @return
   */
  public ProductItemModel findItemById(Long id) {
    ProductItemModel entity = new ProductItemModel();
    entity.setId(id);
    return productItemDao.findEntityById(entity);
  }

  /**
   * jquery 章节树
   * 
   * @param productId
   * @return
   */
  public String jqueryChapterTree(Long productId, String parentTemplate, String childTemplate) {
    List<ProductItemModel> chapterList = this.queryAllChapterItem(productId);
    List<TreeNode> treeNodeList = new ArrayList<>();
    for (ProductItemModel productItem : chapterList) {
      TreeNode treeNode = new TreeNode();
      treeNode.setId(productItem.getId().toString());
      treeNode.setName(productItem.getName());
      treeNode.setParentId(productItem.getParentId().toString());
      treeNode.setData(productItem);
      treeNode.setListOrder(productItem.getListOrder());
      treeNodeList.add(treeNode);
    }
    TreeUtils treeUtils = new TreeUtils(treeNodeList);
    String result =
        treeUtils.getJqueryTree("0", "chapterTree", parentTemplate, childTemplate, "filetree");
    return result;
  }

  /**
   * 目录列表
   * 
   * @return
   */
  public String jqueryTableTree(Long parentId, Long productId, String isExpired) {
    // 设置是过期课程
    String banInput = "", banBtn = "";
    String banLinkA =
        "#if ($node.data.relationItem && $node.data.relationItem!=0) ${node.data.relationItem}.${node.data.relationItemName}[<a onclick='relationItem(${node.id})' style='cursor: pointer;'>修改</a>] #else <a onclick='relationItem(${node.id})' style='cursor: pointer;'>关联</a> #end";
    String banLinkEdit = "editChapter(${node.id})";
    String banLinkDelete = "deleteItem(${node.id},'${node.name}')";
    if (StringUtils.isNotBlank(isExpired) && isExpired.equals("1")) {
      banInput = "readonly='true'";
      banBtn = "disabled='disabled'";
      banLinkA =
          "#if ($node.data.relationItem && $node.data.relationItem!=0) ${node.data.relationItem}.${node.data.relationItemName}[<a onclick='return false;' style='cursor: pointer;'>修改</a>] #else <a onclick='return false;' style='cursor: pointer;'>关联</a> #end";
      banLinkEdit = "return false;";
      banLinkDelete = "return false;";;

    }

    List<ProductItemModel> itemModelList = this.queryAllChildItem(parentId, productId);
    List<TreeNode> treeNodeList = new ArrayList<>();
    for (ProductItemModel productItem : itemModelList) {
      TreeNode treeNode = new TreeNode();
      treeNode.setId(productItem.getId().toString());
      treeNode.setName(productItem.getName());
      treeNode.setParentId(productItem.getParentId().toString());
      treeNode.setData(productItem);
      treeNode.setListOrder(productItem.getListOrder());
      treeNodeList.add(treeNode);
    }
    TreeUtils treeUtils = new TreeUtils(treeNodeList);
    String template =
        "<tr data-tt-id=\"${node.id}\" data-tt-parent-id=\"${node.parentId}\">"
            + "<td>"
            + "#if ($node.data.resourceType==1)"
            + "<span class=\"glyphicon glyphicon-folder-open\" aria-hidden=\"true\" style=\"padding-right: 7px;padding-left: 2px;color: black;\"></span>"
            + "#end"
            + "#if ($node.data.resourceType==2)"
            + "<span class=\"glyphicon glyphicon-play-circle\" aria-hidden=\"true\" style=\"padding-right: 7px;padding-left: 2px;color: black;\"></span>"
            + "#end"
            + "#if ($node.data.resourceType==8)"
            + "<span class=\"glyphicon glyphicon-play-circle\" aria-hidden=\"true\" style=\"padding-right: 7px;padding-left: 2px;color: black;\"></span>"
            + "#end"
            + "#if ($node.data.resourceType==7)"
            + "<span class=\"glyphicon glyphicon-play-circle\" aria-hidden=\"true\" style=\"padding-right: 7px;padding-left: 2px;color: black;\"></span>"
            + "#end"
            + "#if ($node.data.resourceType==3)"
            + "<span class=\"glyphicon glyphicon-file\" aria-hidden=\"true\" style=\"padding-right: 7px;padding-left: 2px;color: black;\"></span>"
            + "#end"
            + "#if ($node.data.resourceType==4)"
            + "<span class=\"glyphicon glyphicon-link\" aria-hidden=\"true\" style=\"padding-right: 7px;padding-left: 2px;color: black;\"></span>"
            + "#end"
            + "#if ($node.data.resourceType==6)"
            + "<span class=\"glyphicon glyphicon-book\" aria-hidden=\"true\" style=\"padding-right: 7px;padding-left: 2px;color: black;\"></span>"
            + "#end"
            + "${node.data.chapterId}"
            + "</td>"
            + "<td>"
            + "${node.id}"
            + "</td>"
            + "<td>"
            + "#if($node.data.pictureUrl=='') 暂无图片 #end"
            + " #if($node.data.pictureUrl!='')<img src='${node.data.pictureUrl}' height=\"37\" width=\"60\"/> #end"
            + "</td>" + "<td><input style='padding:0px;' "
            + banInput
            + " name='listOrder' preValue='${node.data.listOrder}' id='${node.id}' class='orderInput' type='text' value='${node.data.listOrder}' />"
            + "</td>"
            + "<td><p class='itemName' title='${node.data.detail}'>${node.name}</p></td>"
            + "<td>"
            + "<input  value=\"${node.data.importanceLevel}\" type=\"number\" class=\"rating\" min=0 max=10 step=1 data-size=\"xs\">"
            + "</td>"
            + "<td>"
            + "#if ($node.parentId==0)"
            + "<input style='padding:0px;' data-id='${node.id}' "
            + banInput
            + " name='weight' preValue='${node.data.weight}' value='${node.data.weight}' id='${node.id}' class='weight' type='text' />"
            + "#end"
            + "</td>"
            + "<td>"
            + "#if($node.data.resourceType==1)"
            + banLinkA
            // + "#if ($node.data.relationItem &&
            // $node.data.relationItem!=0)
            // ${node.data.relationItem}.${node.data.relationItemName}[<a
            // onclick='relationItem(${node.id})' style='cursor:
            // pointer;'>修改</a>] #else <a
            // onclick='relationItem(${node.id})' style='cursor:
            // pointer;'>关联</a> #end"
            + "#end"
            + "</td>"
            + "<td>"
            + "#if($node.data.resourceType==1) 章节 #end"
            + "#if($node.data.resourceType==2) 视频 #end"
            + "#if($node.data.resourceType==8) 微课 #end"
            + "#if($node.data.resourceType==7) 音频 #end"
            + "#if($node.data.resourceType==3) 文档 #end"
            + "#if($node.data.resourceType==4) Html5 #end"
            + "#if($node.data.resourceType==6) 练习 #end"
            + "</td>"
            + "<td><p class='itemName'>${node.data.quesNum}</p></td>"
            // + "<td>"
            // + "#if($node.data.showStatus==1) 是 #end"
            // + "#if($node.data.showStatus==0) <span
            // style='color:red;padding:0px;'>否</span> #end"
            // + "</td>"
            + "<td>"
            + "#if($node.data.isFree==1) <span style='color:red;padding:0px;'>是</span> #end"
            + "#if($node.data.isFree==0) 否 #end"
            + "</td>"
            + "<td>"
            + "#if ($node.data.resourceType==1)"
            + "<div class=\"btn-group\" style=\"\">\n"
            + "                    <div class=\"dropdown\">\n"
            + "                        <button class=\"btn  btn-primary dropdown-toggle\" "
            + banBtn
            + " style=\"border: 0px; width: 80px; padding: 2px 4px; font-size: 12px;\" type=\"button\" id=\"dropdownMenu2\" data-toggle=\"dropdown\" aria-haspopup=\"true\" aria-expanded=\"true\">\n"
            + "                            操作\n"
            + "                            <span class=\"caret\" style=\"padding:0px;\"></span>\n"
            + "                        </button>\n"
            + "                        <ul class=\"dropdown-menu\" aria-labelledby=\"dropdownMenu2\">\n"
            + "<li><a onclick=\"editChapter(${node.id})\" style=\"cursor: pointer;\">编辑</a></li>"
            + "<li><a onclick=\"deleteItem(${node.id},'${node.name}')\" style=\"padding-left:10px;cursor: pointer;\">删除</a></li>"
            + "<li role=\"separator\" class=\"divider\"></li>"
            + "                            <li><a onclick=\"resourceRelation(${node.id},${node.data.productId})\" style=\"padding-left:10px;cursor:pointer\">关联题库</a></li>\n"
            + "<li role=\"separator\" class=\"divider\"></li>"
            + "                            <li><a onclick=\"chapterRuleList(${node.data.productId},${node.id})\" style=\"padding-left:10px;cursor:pointer\">出题规则</a></li>\n"
            + "                            <li><a onclick=\"addChapterExamRule(${node.data.productId},${node.id})\" style=\"padding-left:10px;cursor:pointer\">新增规则</a></li>\n"
            + "<li role=\"separator\" class=\"divider\"></li>"
            + "                            <li><a onclick=\"addVideo(${node.data.productId},${node.id})\" style=\"padding-left:10px;cursor:pointer\">添加视频</a></li>\n"
            + "                            <li><a onclick=\"addAudio(${node.data.productId},${node.id})\" style=\"padding-left:10px;cursor:pointer\">添加音频</a></li>\n"
            + "                            <li><a onclick=\"addDoc(${node.data.productId},${node.id})\" style=\"padding-left:10px;cursor:pointer\">添加文档</a></li>\n"
            + "                            <li><a onclick=\"addHtml5(${node.data.productId},${node.id})\" style=\"padding-left:10px;cursor:pointer\">添加HTML5</a></li>\n"
            + "                        </ul>\n"
            + "                    </div>\n"
            + "                </div>"
            + "#end"
            + "#if ($node.data.resourceType==2)"
            + "<a onclick=\""
            + banLinkEdit
            + "\" style=\"cursor: pointer;\">编辑</a>"
            + "<a onclick=\""
            + banLinkDelete
            + "\" style=\"padding-left:10px;cursor: pointer;\">删除</a>"
            + "#end"
            + "#if ($node.data.resourceType==8)"
            + "<a onclick=\""
            + banLinkEdit
            + "\" style=\"cursor: pointer;\">编辑</a>"
            + "<a onclick=\""
            + banLinkDelete
            + "\" style=\"padding-left:10px;cursor: pointer;\">删除</a>"
            + "#end"
            + "#if ($node.data.resourceType==7)"
            + "<a onclick=\""
            + banLinkEdit
            + "\" style=\"cursor: pointer;\">编辑</a>"
            + "<a onclick=\""
            + banLinkDelete
            + "\" style=\"padding-left:10px;cursor: pointer;\">删除</a>"
            + "#end"
            + "#if ($node.data.resourceType==3)"
            + "<a onclick=\""
            + banLinkEdit
            + "\" style=\"cursor: pointer;\">编辑</a>"
            + "<a onclick=\""
            + banLinkDelete
            + "\" style=\"padding-left:10px;cursor: pointer;\">删除</a>"
            + "#end"
            + "#if ($node.data.resourceType==4)"
            + "<a onclick=\""
            + banLinkEdit
            + "\" style=\"cursor: pointer;\">编辑</a>"
            + "<a onclick=\""
            + banLinkDelete
            + "\" style=\"padding-left:10px;cursor: pointer;\">删除</a>"
            + "#end"
            + "#if ($node.data.resourceType==6)"
            + "<a onclick=\""
            + banLinkEdit
            + "\" style=\"cursor: pointer;\">编辑</a>"
            + "<a onclick=\""
            + banLinkDelete
            + "\" style=\"padding-left:10px;cursor: pointer;\">删除</a>" + "#end" + "</td>" + "</tr>";

    String result = treeUtils.getJqueryTableTree("0", template);

    return result;
  }

  /**
   * 获取所有子地区
   * 
   * @param id 栏目id
   * @return
   */
  public List<ProductItemModel> queryAllChildItem(Long id, Long productId) {

    if (id == 0) {
      return this.queryAllItem(productId);
    }
    ProductItemModel productItemModel = this.findItemById(id);
    if (productItemModel == null) {
      return new ArrayList<>();
    }
    if (StringUtils.isNotBlank(productItemModel.getAllChildId())) {
      String[] ids = productItemModel.getAllChildId().split(",");
      List<Integer> idList = new ArrayList<>();
      for (String idString : ids) {
        idList.add(Integer.parseInt(idString));
      }
      return this.queryItemsByIds(idList);
    } else {
      return new ArrayList<>();
    }
  }

  /**
   * 根据Id查询
   * 
   * @param ids
   * @return
   */
  public List<ProductItemModel> queryItemsByIds(List<Integer> ids) {
    Map<String, Object> cond = new HashMap<String, Object>();
    cond.put("ids", ids);
    Map<String, Object> output = new HashMap<>();
    output.put("id", "");
    output.put("parentId", "");
    output.put("name", "");
    output.put("detail", "");
    output.put("resourceType", "");
    output.put("showStatus", "");
    output.put("isFree", "");
    output.put("productId", "");
    output.put("childId", "");
    output.put("allChildId", "");
    output.put("allParentId", "");
    output.put("level", "");
    output.put("isLeaf", "");
    output.put("productId", "");
    output.put("chapterId", "");
    output.put("importanceLevel", "");
    output.put("listOrder", "");
    output.put("taskRatio", "");
    output.put("relationItem", "");
    output.put("relationItemName", "");
    Page<ProductItemModel> categoryList = productItemDao.queryListByCond0(cond, output, null);
    return categoryList.getResult();
  }

  /**
   * 根据父Id查询地区
   * 
   * @param parentId
   * @return
   */
  public List<ProductItemModel> queryItemByParentId(Integer parentId, Integer productId) {
    Map<String, Object> cond = new HashMap<String, Object>();
    cond.put("parentId", parentId);
    cond.put("productId", productId);
    Map<String, Object> output = new HashMap<>();
    output.put("id", "");
    output.put("parentId", "");
    output.put("name", "");
    output.put("detail", "");
    output.put("resourceType", "");
    output.put("showStatus", "");
    output.put("isFree", "");
    output.put("productId", "");
    output.put("childId", "");
    output.put("allChildId", "");
    output.put("allParentId", "");
    output.put("level", "");
    output.put("isLeaf", "");
    output.put("productId", "");
    output.put("chapterId", "");
    output.put("importanceLevel", "");
    output.put("listOrder", "");
    output.put("taskRatio", "");
    output.put("weight", "");
    output.put("relationItem", "");
    output.put("relationItemName", "");
    Page<ProductItemModel> categoryList = productItemDao.queryListByCond0(cond, output, null);
    return categoryList.getResult();
  }

  /**
   * 获取所有栏目
   * 
   * @return
   */
  public List<ProductItemModel> queryAllItem(Long productId) {
    Map<String, Object> cond = new HashMap<String, Object>();
    cond.put("productId", productId);
    Map<String, Object> output = new HashMap<>();
    output.put("id", "");
    output.put("productId", "");

    output.put("resourceId", "");
    output.put("resourceType", "");
    output.put("parentId", "");
    output.put("level", "");
    output.put("name", "");
    output.put("showStatus", "");
    output.put("detail", "");
    output.put("misc", "");
    output.put("allParentId", "");
    output.put("childId", "");
    output.put("allChildId", "");
    output.put("updateTime", "");
    output.put("isFree", "");
    output.put("isLeaf", "");
    output.put("chapterId", "");
    output.put("importanceLevel", "");
    output.put("points", "");
    output.put("freeType", "");
    output.put("quesNum", "");
    output.put("resource", "");
    output.put("taskRatio", "");
    output.put("relationItem", "");
    output.put("relationItemName", "");
    output.put("pictureUrl", "");
    output.put("resourceStatus", "");
    output.put("weight", "");
    output.put("listOrder", "");

    Page<ProductItemModel> categoryList = productItemDao.queryListByCond0(cond, output, null);
    return categoryList.getResult();
  }

  /**
   * 获取所有栏目
   * 
   * @return
   */
  public List<ProductItemModel> queryAllChapterItem(Long productId) {
    Map<String, Object> cond = new HashMap<String, Object>();
    cond.put("productId", productId);
    cond.put("resourceType", ResourceType.CHAPTER.getTypeId());
    Map<String, Object> output = new HashMap<>();
    output.put("id", "");
    output.put("parentId", "");
    output.put("name", "");
    output.put("detail", "");
    output.put("resourceType", "");
    output.put("showStatus", "");
    output.put("isFree", "");
    output.put("childId", "");
    output.put("allChildId", "");
    output.put("allParentId", "");
    output.put("level", "");
    output.put("isLeaf", "");
    output.put("productId", "");
    output.put("chapterId", "");
    output.put("importanceLevel", "");
    output.put("listOrder", "");
    output.put("resourceId", "");
    output.put("taskRatio", "");
    output.put("weight", "");
    output.put("relationItem", "");
    output.put("relationItemName", "");
    output.put("quesNum", "");
    Page<ProductItemModel> categoryList = productItemDao.queryListByCond0(cond, output, null);
    return categoryList.getResult();
  }

  /**
   * 章节添加selectTree
   * 
   * @param produtId
   * @param selectedId
   * @return
   */
  public String chapterAddSelectTree(Long produtId, String selectedId) {
    List<ProductItemModel> itemModelList = this.queryChapterItemByParentId(0L, produtId);
    Map<String, TreeNode> treeNodeMap = Maps.newHashMap();
    for (ProductItemModel itemModel : itemModelList) {
      TreeNode treeNode = new TreeNode();
      treeNode.setId(itemModel.getId().toString());
      treeNode.setName(itemModel.getName());
      treeNode.setParentId(itemModel.getParentId().toString());
      treeNode.setData(itemModel);
      treeNodeMap.put(itemModel.getId().toString(), treeNode);
    }
    TreeUtils treeUtils = new TreeUtils(treeNodeMap);
    String result =
        treeUtils
            .getTree(
                "0",
                "<option value=\"${node.id}\" ${selected}>${spacer}${node.data.chapterId}[${node.name}]</option>",
                selectedId, null, null);
    return result;
  }

  /**
   * 资源添加章节列表
   * 
   * @return
   */
  public String resourceAddSelectTree(Long productId, String selectedId) {
    List<ProductItemModel> itemModelList = this.queryAllChildChapterItem(0L, productId);
    List<TreeNode> treeNodeList = new ArrayList<>();
    for (ProductItemModel itemModel : itemModelList) {
      TreeNode treeNode = new TreeNode();
      treeNode.setId(itemModel.getId().toString());
      treeNode.setName(itemModel.getName());
      treeNode.setParentId(itemModel.getParentId().toString());
      treeNode.setData(itemModel);
      treeNodeList.add(treeNode);
    }
    TreeUtils treeUtils = new TreeUtils(treeNodeList);
    String result =
        treeUtils
            .getTree(
                "0",
                "<option value=\"${node.id}\" ${selected}>${spacer}${node.data.chapterId}[${node.name}]</option>",
                selectedId, null, null);
    return result;
  }

  /**
   * 获取全部章节节点
   * 
   * @param id
   * @param productId
   * @return
   */
  public List<ProductItemModel> queryAllChildChapterItem(Long id, Long productId) {
    List<ProductItemModel> result = new ArrayList<ProductItemModel>();
    List<ProductItemModel> list = this.queryChapterItemByParentId(id, productId);
    result.addAll(list);
    for (ProductItemModel model : list) {
      result.addAll(this.queryAllChildChapterItem(model.getId(), productId));
    }
    return result;
  }

  /**
   * 获取章节节点
   * 
   * @param parentId
   * @param productId
   * @return
   */
  public List<ProductItemModel> queryChapterItemByParentId(Long parentId, Long productId) {
    Map<String, Object> cond = new HashMap<String, Object>();
    cond.put("parentId", parentId);
    cond.put("productId", productId);
    cond.put("resourceType", ResourceType.CHAPTER.getTypeId());
    Map<String, Object> output = new HashMap<>();
    output.put("id", "");
    output.put("parentId", "");
    output.put("name", "");
    output.put("detail", "");
    output.put("resourceType", "");
    output.put("showStatus", "");
    output.put("isFree", "");
    output.put("childId", "");
    output.put("allChildId", "");
    output.put("allParentId", "");
    output.put("level", "");
    output.put("isLeaf", "");
    output.put("chapterId", "");
    output.put("importanceLevel", "");
    output.put("listOrder", "");
    output.put("taskRatio", "");
    output.put("weight", "");
    output.put("relationItem", "");
    output.put("relationItemName", "");
    Page<ProductItemModel> categoryList = productItemDao.queryListByCond0(cond, output, null);
    return categoryList.getResult();
  }

  /**
   * 清洗tree
   * 
   * @return
   */
  public Boolean cleanTree(Long productId) {
    // 1.查询栏目列表
    List<ProductItemModel> productItemModelList = this.queryAllItem(productId);
    // 2.栏目ID
    Map<Long, ProductItemModel> idMap = new HashMap<>();
    for (ProductItemModel itemModel : productItemModelList) {
      idMap.put(itemModel.getId(), itemModel);
    }
    // 3.父栏目ID
    Map<Long, List<ProductItemModel>> parentIdMap = new HashMap<>();
    for (ProductItemModel itemModel : productItemModelList) {
      List<ProductItemModel> itemModelList = null;
      if (parentIdMap.containsKey(itemModel.getParentId())) {
        itemModelList = parentIdMap.get(itemModel.getParentId());
      } else {
        itemModelList = new ArrayList<>();
      }
      itemModelList.add(itemModel);
      parentIdMap.put(itemModel.getParentId(), itemModelList);
    }
    // 4.更新记录
    List<Long> needDeleteIds = new ArrayList<>();
    for (ProductItemModel itemModel : productItemModelList) {
      List<ProductItemModel> parentList = Lists.newArrayList();
      this.parentList(parentList, idMap, itemModel.getId());
      Boolean needDelete = true;
      for (ProductItemModel productItemModel : parentList) {
        if (productItemModel.getParentId() == 0) {
          needDelete = false;
          break;
        }
      }
      if (needDelete == true) {
        needDeleteIds.add(itemModel.getId());
        // 删除题的关联，防止脏数据
        productQuestionService.deleteQuestionsByChapter(productId, itemModel.getId());
        continue;
      }
      List<ProductItemModel> childList = parentIdMap.get(itemModel.getId());
      List<ProductItemModel> allChildList = this.allChildId(parentIdMap, itemModel.getId());

      ProductItemModel updateEntity = new ProductItemModel();
      updateEntity.setId(itemModel.getId());
      updateEntity.setChildId(this.idString(childList));
      updateEntity.setAllChildId(this.idString(allChildList));
      updateEntity.setLevel(parentList.size() - 1);
      updateEntity.setAllParentId(this.idString(parentList));
      if (childList != null && childList.size() > 0) {
        updateEntity.setIsLeaf(0);
      } else {
        updateEntity.setIsLeaf(1);
      }
      this.editItem(updateEntity);
    }
    // 5.删除记录
    if (needDeleteIds.size() > 0) {
      Map<String, Object> cond = new HashMap<>();
      cond.put("ids", needDeleteIds);
      missionService.deleteStandardMission(needDeleteIds);
      productItemDao.deleteEntity(cond);
    }
    return true;
  }

  /**
   * ID序列化
   * 
   * @param itemModelList
   * @return
   */
  private String idString(List<ProductItemModel> itemModelList) {
    StringBuilder stringBuilder = new StringBuilder("");
    if (itemModelList == null) {
      return "";
    }
    for (ProductItemModel itemModel : itemModelList) {
      stringBuilder.append(itemModel.getId() + ",");
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
  private void parentList(List<ProductItemModel> result, Map<Long, ProductItemModel> idMap,
      Long catId) {
    ProductItemModel itemModel = idMap.get(catId);
    if (itemModel != null) result.add(itemModel);
    if (itemModel != null && itemModel.getParentId() != null && itemModel.getParentId() != 0
        && !itemModel.getParentId().equals(catId))
      this.parentList(result, idMap, itemModel.getParentId());
  }

  /**
   * 所有子节点
   * 
   * @param parentIdMap
   * @param catId
   * @return
   */
  private List<ProductItemModel> allChildId(Map<Long, List<ProductItemModel>> parentIdMap,
      Long catId) {
    List<ProductItemModel> result = new ArrayList<>();
    List<ProductItemModel> itemList = parentIdMap.get(catId);
    if (itemList != null) {
      result.addAll(itemList);
      for (ProductItemModel itemModel : itemList) {
        System.out.println(FastJsonUtil.toJson(itemModel));
        if (itemModel.getId().equals(catId)) continue;
        result.addAll(this.allChildId(parentIdMap, itemModel.getId()));
      }
    }
    return result;
  }

  public ProductMiscInfo buildMisc(Long productId) {
    ProductMiscInfo misc = new ProductMiscInfo();
    List<ProductItemModel> itemList = queryAllItem(productId);
    List<FinalExamModel> finalExamList = queryAllFinalExam(productId);
    if (null == itemList || itemList.size() == 0) return misc;
    Integer chapterCount = 0, itemCount = 0;
    for (ProductItemModel model : itemList) {
      if (model.getResourceType() != ResourceType.CHAPTER.getTypeId()) continue;
      if (model.getParentId() == 0)
        chapterCount++;
      else
        itemCount++;
    }
    misc.setChapterCount(chapterCount);
    misc.setItemCount(itemCount);
    if(null != finalExamList) {
      misc.setFinalExamCount(finalExamList.size());
    }
    return misc;
  }

  private List<FinalExamModel> queryAllFinalExam(Long productId) {
    IQueryParam param = new QueryParam();
    param.addInput("courseId", productId);
    param.addOutput("id", "");
    param.addOutput("examName", "");
    param.addOutput("questionNums", "");
    param.addOutput("sort", "");
    param.addOutput("courseId", "");
    param.addOutput("createTime", "");
    param.addSort("sort", Sort.ASC);
    Page<FinalExamModel> finalExamPage = finalExamDao.findEntityListByCond(param, null);
    if (null == finalExamPage || null == finalExamPage.getResult()
        || finalExamPage.getResult().isEmpty()) {
      return null;
    }
    return finalExamPage.getResult();
  }

  /**
   * 获取所有闯关任务栏目
   * 
   * @return
   */
  public List<MissionVo> queryAllItemMission(Long productId) {
    Map<String, Object> output = new HashMap<>();
    output.put("id", "");
    output.put("parentId", "");
    output.put("name", "");
    output.put("pictureUrl", "");
    output.put("detail", "");
    output.put("resourceType", "");
    output.put("showStatus", "");
    output.put("isFree", "");
    output.put("childId", "");
    output.put("allChildId", "");
    output.put("allParentId", "");
    output.put("level", "");
    output.put("isLeaf", "");
    output.put("productId", "");
    output.put("chapterId", "");
    output.put("importanceLevel", "");
    output.put("listOrder", "");
    output.put("taskRatio", "");
    output.put("weight", "");
    output.put("relationItem", "");
    output.put("relationItemName", "");

    HashMap<String, Object> inputs = Maps.newHashMap();
    if (productId != null && productId.intValue() != 0) {
      inputs.put("productId", productId);
    }
    inputs.put("level", "1"); // 默认是节
    inputs.put("resourceType", "1");

    Page<ProductItemModel> productItemList =
        productItemDao.cascadeQueryProductMission(inputs, output);

    // 设置missionID/missionOrder/missionState
    settingMissionIdAndMissionOrderAndMissionState(productItemList);

    inputs.put("level", "0"); // 修改为章
    Page<ProductItemModel> productChaptList =
        productItemDao.cascadeQueryProductMission(inputs, output);

    // 设置missionID/missionOrder/missionState
    settingMissionIdAndMissionOrderAndMissionState(productChaptList);

    List<MissionVo> list = combin(productItemList.getResult(), productChaptList.getResult());
    return list;
  }

  private void settingMissionIdAndMissionOrderAndMissionState(
      Page<ProductItemModel> productChaptList) {
    if (productChaptList != null && productChaptList.getResult().size() > 0) {
      for (ProductItemModel pIModel : productChaptList.getResult()) {
        if (pIModel == null) continue;
        Map<String, Object> inputArgument = Maps.newHashMap();
        inputArgument.put("taskType", "standard");
        inputArgument.put("threshold", pIModel.getId());
        HashMap<String, Object> outputField = Maps.newHashMap();
        outputField.put("id", "");
        outputField.put("missionOrder", "");
        outputField.put("missionState", "");
        Page<MissionModel> missionList =
            missionDao.queryListByCondWithOutPage(inputArgument, outputField);
        if (missionList == null || missionList.getResult().size() == 0) continue;
        pIModel.setMissionId(missionList.getResult().get(0).getId());
        pIModel.setMissionOrder(missionList.getResult().get(0).getMissionOrder());
        pIModel.setMissionState(missionList.getResult().get(0).getMissionState() + "");
      }
    }
  }

  /**
   * 合并章List与节List
   * 
   * @return
   */
  public List<MissionVo> combin(List<ProductItemModel> productItemList,
      List<ProductItemModel> productChaptList) {
    List<MissionVo> list = new ArrayList<MissionVo>();
    Map<Long, ProductItemModel> chapterMap = Maps.newHashMap();
    for (ProductItemModel c : productChaptList) {
      chapterMap.put(c.getId(), c);
      list.add(packageChapter2Mission(c));
    }
    for (ProductItemModel i : productItemList) {
      if (null == i.getParentId()) continue;
      ProductItemModel chapter = chapterMap.get(i.getParentId());
      if (null == chapter) continue;
      list.add(packageItem2Mission(chapter, i));
    }
    return list;
  }

  private MissionVo packageChapter2Mission(ProductItemModel chapter) {
    MissionVo mission = new MissionVo();
    mission.setDesc("完成" + chapter.getChapterId() + "章总结并闯关成功");
    mission.setCourseId(chapter.getProductId().toString());
    mission.setChapterId(chapter.getId().toString());
    mission.setChapterIndex(chapter.getChapterId());
    mission.setChapterName(chapter.getName());
    mission.setThreshold(chapter.getId().toString());
    mission.setListOrder(chapter.getListOrder().intValue());
    mission.setMissionId(chapter.getMissionId());
    mission.setMissionOrder(chapter.getMissionOrder());
    mission.setMissionState(chapter.getMissionState());
    return mission;
  }

  private MissionVo packageItem2Mission(ProductItemModel chapter, ProductItemModel item) {
    MissionVo mission = new MissionVo();
    mission.setDesc("完成" + chapter.getChapterId() + item.getChapterId() + "学习并闯关成功");
    mission.setCourseId(item.getProductId().toString());
    mission.setChapterId(chapter.getId().toString());
    mission.setChapterIndex(chapter.getChapterId());
    mission.setChapterName(chapter.getName());
    mission.setItemId(item.getId().toString());
    mission.setItemName(item.getName());
    mission.setThreshold(item.getId().toString());
    mission.setListOrder(item.getListOrder().intValue());
    mission.setMissionId(item.getMissionId());
    mission.setMissionOrder(item.getMissionOrder());
    mission.setMissionState(item.getMissionState());
    return mission;
  }

  /**
   * 通过资源增加目录
   * 
   * @return
   */
  public void addChapterByChoose(List<ProductTreeRequest> parentList,
      List<ProductTreeRequest> childList, Long productId) {

    for (int index = 0; index < parentList.size(); index++) {
      int s = 0;

      for (ProductTreeRequest child : childList) {
        if (parentList.get(index).getId().equals(child.getParentId())) {
          s++; // 验证有没有选中章下面的节
        }
      }
      if (s > 0) {
        ProductModel productModel = productService.findSubjectById(productId);

        // 章
        Long chapterId = Long.valueOf((parentList.get(index).getId()));
        CourseChapterModel productItem =
            courseChapterService.findChapterById(Long.valueOf(chapterId));
        ProductItemModel entity = new ProductItemModel();
        entity.setChapterId(productItem.getSindex());
        entity.setDetail(productItem.getDetail());
        entity.setCreateTime(new Timestamp(System.currentTimeMillis()));
        entity.setImportanceLevel(0);
        entity.setIsFree(0);
        entity.setName(productItem.getName());
        entity.setParentId(0L);
        entity.setProductId(productId);
        entity.setResourceType(1);
        entity.setShowStatus(1);
        entity.setListOrder((index - 1) * 100); // 章排序为整百
        entity.setCourseItem(productItem.getId().toString());
        ProductItemModel chapterModel = productItemService.addItemNoClean(entity);
        MissionVo missionVo = packageChapter2Mission(chapterModel);
        missionVo.setModule(productModel.getModule());
        missionService.addStandardMission(missionVo);// 给mission设置module

        // 复制章视频资源
        List<CourseResourceVideoModel> videoList =
            courseResourceVideoService.findAllResourceByChapterId(chapterModel.getId());
        List<ProductItemModel> copiedChapterVideoResource = Lists.newArrayList();
        if (videoList != null && videoList.size() > 0) {
          for (CourseResourceVideoModel videoModel : videoList) {

            ProductItemModel itemModel = new ProductItemModel();
            itemModel.setProductId(productId);
            itemModel.setDetail(videoModel.getDetail());
            itemModel.setName(videoModel.getName());
            itemModel.setParentId(chapterModel.getId());
            itemModel.setResourceType(Integer.valueOf(videoModel.getType()));
            itemModel.setResourceId(videoModel.getId().toString());
            itemModel.setCreateTime(new Timestamp(System.currentTimeMillis()));
            itemModel.setListOrder(chapterModel.getListOrder());
            itemModel.setChapterId(chapterModel.getChapterId());
            itemModel.setImportanceLevel(0);
            itemModel.setShowStatus(1);
            itemModel.setPictureUrl(videoModel.getImg());
            ResourceDescription resource = new ResourceDescription();
            resource.setFileUrl(videoModel.getFileUrl());
            resource.setUrl(videoModel.getUrl());
            resource.setTimeLengthMinute(videoModel.getTimeLengthMinute());
            resource.setTimeLengthSecond(videoModel.getTimeLengthSecond());
            itemModel.setResource(JSON.toJSONString(resource));
            ProductItemModel addItemNoClean = productItemService.addItemNoClean(itemModel);
            copiedChapterVideoResource.add(addItemNoClean);
          }
        }

        if (!videoList.isEmpty()) {
          // 关联章视频
          ProductItemModel relationItem = new ProductItemModel();
          relationItem.setId(chapterModel.getId());
          relationItem.setRelationItem(copiedChapterVideoResource.get(0).getId());// 关联第一个资源
          relationItem.setRelationItemName(copiedChapterVideoResource.get(0).getName());
          productItemService.editItem(relationItem);
        }

        // 章下面的节
        for (int childIndex = 0; childIndex < childList.size(); childIndex++) {

          if (parentList.get(index).getId().equals(childList.get(childIndex).getParentId())) {
            Long sectionId = Long.valueOf(childList.get(childIndex).getId());
            CourseChapterModel courseItemModel =
                courseChapterService.findChapterById(Long.valueOf(sectionId));
            ProductItemModel entityChild = new ProductItemModel();
            entityChild.setChapterId(courseItemModel.getSindex());
            entityChild.setDetail(courseItemModel.getDetail());
            entityChild.setCreateTime(new Timestamp(System.currentTimeMillis()));
            entityChild.setImportanceLevel(0);
            entityChild.setIsFree(0);
            entityChild.setListOrder((index - 1) * 100 + childIndex + 1);// 排序
            entityChild.setName(courseItemModel.getName());
            entityChild.setParentId(chapterModel.getId());
            entityChild.setProductId(productId);
            entityChild.setResourceType(1);
            entityChild.setShowStatus(1);
            entityChild.setCourseItem(courseItemModel.getId().toString());
            // 存储节内容
            ProductItemModel item = productItemService.addItemNoClean(entityChild);

            // 添加标准任务
            ProductItemModel chapter = new ProductItemModel();
            chapter.setId(entityChild.getParentId());
            chapter.setChapterId(chapterModel.getChapterId());
            chapter.setName(chapterModel.getName());
            MissionVo mission = packageItem2Mission(chapter, entityChild);
            mission.setModule(productModel.getModule());
            missionService.addStandardMission(mission);// 给mission设置module

            List<ProductItemModel> copiedResource = Lists.newArrayList();
            // 复制文档资源
            List<CourseResourceDocModel> resourceDocList =
                courseResourceDocService.findAllResourceDocById(courseItemModel.getId());
            if (resourceDocList != null && resourceDocList.size() > 0) {
              for (CourseResourceDocModel docModel : resourceDocList) {
                ProductItemModel itemModel = new ProductItemModel();
                itemModel.setProductId(productId);
                itemModel.setDetail(docModel.getDetail());
                itemModel.setName(docModel.getName());
                itemModel.setParentId(item.getId());
                itemModel.setResourceType(ResourceType.DOC.getTypeId());
                itemModel.setResourceId(docModel.getId().toString());
                itemModel.setCreateTime(new Timestamp(System.currentTimeMillis()));
                itemModel.setListOrder(courseItemModel.getListOrder());
                itemModel.setChapterId(courseItemModel.getSindex());
                itemModel.setImportanceLevel(0);
                itemModel.setShowStatus(1);
                ResourceDescription resource = new ResourceDescription();
                resource.setFileUrl(docModel.getFileUrl());
                resource.setUrl(docModel.getUrl());
                itemModel.setResource(JSON.toJSONString(resource));
                ProductItemModel saveDocItem = productItemService.addItemNoClean(itemModel);
                copiedResource.add(saveDocItem);
              }
            }

            // 复制H5资源
            List<CourseResourceHtml5Model> resourceH5List =
                courseResourceHtml5Service.findAllResourceHtml5ById(courseItemModel.getId());
            for (CourseResourceHtml5Model Html5Model : resourceH5List) {
              ProductItemModel itemModel = new ProductItemModel();
              itemModel.setProductId(productId);
              itemModel.setDetail(Html5Model.getDetail());
              itemModel.setName(Html5Model.getName());
              itemModel.setParentId(item.getId());
              itemModel.setResourceType(ResourceType.HTML5.getTypeId());
              itemModel.setResourceId(Html5Model.getId().toString());
              itemModel.setCreateTime(new Timestamp(System.currentTimeMillis()));
              itemModel.setListOrder(courseItemModel.getListOrder());
              itemModel.setChapterId(courseItemModel.getSindex());
              itemModel.setImportanceLevel(0);
              itemModel.setShowStatus(1);
              ResourceDescription resource = new ResourceDescription();
              resource.setFileUrl(Html5Model.getFileUrl());
              resource.setUrl(Html5Model.getUrl());
              itemModel.setResource(JSON.toJSONString(resource));
              ProductItemModel saveH5Item = productItemService.addItemNoClean(itemModel);
              copiedResource.add(saveH5Item);
            }

            // 复制视频资源
            List<CourseResourceVideoModel> childVideoList =
                courseResourceVideoService.findAllResourceByChapterId(courseItemModel.getId());
            if (childVideoList != null && childVideoList.size() > 0) {
              for (CourseResourceVideoModel videoModel : childVideoList) {

                ProductItemModel itemModel = new ProductItemModel();
                itemModel.setProductId(productId);
                itemModel.setDetail(videoModel.getDetail());
                itemModel.setName(videoModel.getName());
                itemModel.setParentId(item.getId());
                itemModel.setResourceType(Integer.valueOf(videoModel.getType()));
                itemModel.setResourceId(videoModel.getId().toString());
                itemModel.setCreateTime(new Timestamp(System.currentTimeMillis()));
                itemModel.setListOrder(courseItemModel.getListOrder());
                itemModel.setChapterId(courseItemModel.getSindex());
                itemModel.setImportanceLevel(0);
                itemModel.setShowStatus(1);
                itemModel.setPictureUrl(videoModel.getImg());
                ResourceDescription resource = new ResourceDescription();
                resource.setFileUrl(videoModel.getFileUrl());
                resource.setUrl(videoModel.getUrl());
                resource.setTimeLengthMinute(videoModel.getTimeLengthMinute());
                resource.setTimeLengthSecond(videoModel.getTimeLengthSecond());
                itemModel.setResource(JSON.toJSONString(resource));
                ProductItemModel saveVideoItem = productItemService.addItemNoClean(itemModel);
                copiedResource.add(saveVideoItem);
              }
            }

            // 复制除了音频的资源另外三种资源，关联第一个
            if (copiedResource.size() > 0) {
              ProductItemModel relItem = new ProductItemModel();
              relItem.setId(item.getId());
              relItem.setRelationItem(copiedResource.get(0).getId());// 关联第一个资源
              relItem.setRelationItemName(copiedResource.get(0).getName());
              productItemService.editItem(relItem);
            }

            // 复制音频资源，单独排序
            List<CourseResourceAudioModel> audioResourceList =
                courseResourceAudioService.findAllResourceByChapterId(courseItemModel.getId());
            for (CourseResourceAudioModel audioResource : audioResourceList) {
              if (audioResource != null) {
                ProductItemModel itemModel = new ProductItemModel();
                itemModel.setProductId(productId);
                itemModel.setDetail(audioResource.getDetail());
                itemModel.setName(audioResource.getName());
                itemModel.setParentId(item.getId());
                itemModel.setResourceType(ResourceType.AUDIO.getTypeId());
                itemModel.setResourceId(audioResource.getId().toString());
                itemModel.setCreateTime(new Timestamp(System.currentTimeMillis()));
                itemModel.setListOrder((index - 1) * 100 + childIndex + 1);
                itemModel.setChapterId(courseItemModel.getSindex());
                itemModel.setImportanceLevel(0);
                itemModel.setShowStatus(1);
                ResourceDescription resource = new ResourceDescription();
                resource.setUrl(audioResource.getUrl());
                resource.setTimeLengthMinute(audioResource.getTimeLengthMinute());
                resource.setTimeLengthSecond(audioResource.getTimeLengthSecond());
                resource.setSize(audioResource.getSize());
                itemModel.setResource(JSON.toJSONString(resource));
                ProductItemModel saveAudioItem = this.addItemNoClean(itemModel);
              }
            }

            ProductModel product = productService.findSubjectById(productId);
            // 复制题库
            List<Long> chapters = new ArrayList<>();
            chapters.add(sectionId);
            List<QuestionBankQuestionModel> questionModels =
                questionBankQuestionService.queryQuestionByChapterIds(product.getResourceSubject(),
                    chapters);

            StringBuilder idsStr = new StringBuilder("");
            for (QuestionBankQuestionModel questionModel : questionModels) {
              idsStr.append(questionModel.getId() + ",");
            }
            ProductItemModel productItemModel = new ProductItemModel();
            productItemModel.setResourceId(idsStr.toString());
            productItemModel.setId(item.getId());// 题库也挂在节下面
            productItemModel.setProductId(productId);
            productItemService.editItem(productItemModel);

            // 增加记录
            productQuestionService.addQuestions(item.getId(), productId, questionModels);
            // 统计题目
            productQuestionService.staticsProductChapterQuestionNum(productId);

          }
        }
      }
    }
    this.cleanTree(productId);
    productService.updateSubjectMisc(productId, buildMisc(productId));
  }

  /**
   * 根据Id查询
   * 
   * @param relationItem
   * @return
   */
  public List<ProductItemModel> queryItemsById(Long relationItem) {
    Map<String, Object> cond = new HashMap<String, Object>();
    cond.put("relationItem", relationItem);
    Map<String, Object> output = new HashMap<>();
    output.put("id", "");
    Page<ProductItemModel> categoryList = productItemDao.queryListByCond0(cond, output, null);
    return categoryList.getResult();
  }

  public List<ProductItemModel> queryProductItemByResourceId(Long id,String resourceType) {
    Map<String, Object> cond = new HashMap<String, Object>();
    if(resourceType!=null){
    	cond.put("resourceType", resourceType);
    }
    cond.put("resourceId", id);
    Map<String, Object> output = new HashMap<>();
    output.put("id", "");
    output.put("resourceId", "");
    output.put("name", "");
    output.put("detail", "");
    output.put("resource", "");
    output.put("resourceType", "");
    Page<ProductItemModel> categoryList = productItemDao.queryListByCond0(cond, output, null);
    return categoryList != null ? categoryList.getResult() : null;
  }

  public Boolean updateProductItem(ProductItemModel productItemModel) {
    Map<String, Object> cond = new HashMap<>();
    cond.put("resourceId", productItemModel.getResourceId());
    cond.put("resourceType", productItemModel.getResourceType());
    return productItemDao.updateEntity(cond, productItemModel);
  }
  
  /**
   * 根据课程ID查询章节信息，返回值map的key为节ID,value为该节下面的所有资源。
 * @param courseId
 * @return
 */
  public Map<Long,List<ProductItemModel>> queryProductItemByCourseId(Long courseId){
      IQueryParam param = new QueryParam();
      param.addInput("productId", courseId);
      param.addOutput("id", "");
      param.addOutput("productId", "");
      param.addOutput("parentId", "");
      param.addOutput("level", "");
      param.addOutput("chapterId", "");
      param.addOutput("resourceType", "");
      param.addOutput("pictureUrl", "");
      Page<ProductItemModel> productItemPage = productItemDao.findEntityListByCond(param, null);
      List<ProductItemModel> productItemList = productItemPage.getResult();
      Map<Long,List<ProductItemModel>> chapterAndResourceMap = Maps.newHashMap();
      for(ProductItemModel productItemModel : productItemList) {
          List<ProductItemModel> resourceList = Lists.newArrayList();
          if(productItemModel.getLevel() == 2) {
              if(!chapterAndResourceMap.containsKey(productItemModel.getParentId())) {
                  resourceList.add(productItemModel);
                  chapterAndResourceMap.put(productItemModel.getParentId(),resourceList);
              }else {
                  resourceList = chapterAndResourceMap.get(productItemModel.getParentId());
                  resourceList.add(productItemModel);
                  chapterAndResourceMap.put(productItemModel.getParentId(), resourceList);
              }
          }else if(productItemModel.getLevel() == 1) {
              if(!chapterAndResourceMap.containsKey(productItemModel.getId())) {
                  chapterAndResourceMap.put(productItemModel.getId(), resourceList);
              }
          }
      }
      return chapterAndResourceMap;
  }
  
  
  
}
