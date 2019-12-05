package com.xiaodou.ms.service.course;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.ms.common.ResourceType;
import com.xiaodou.ms.dao.course.CourseChapterDao;
import com.xiaodou.ms.dao.course.CourseResourceVideoDao;
import com.xiaodou.ms.model.course.CourseChapterModel;
import com.xiaodou.ms.model.course.CourseKeywordModel;
import com.xiaodou.ms.model.course.CourseResourceVideoModel;
import com.xiaodou.ms.service.product.ProductItemService;
import com.xiaodou.ms.util.tree.TreeNode;
import com.xiaodou.ms.util.tree.TreeUtils;
import com.xiaodou.ms.vo.ResourceDescription;
import com.xiaodou.ms.web.request.course.ResourceVideoCreateRequest;
import com.xiaodou.ms.web.request.course.ResourceVideoEditRequest;
import com.xiaodou.summer.dao.pagination.Page;

/**
 * Created by zyp on 15/4/19.
 */
@Service("courseResourceVideoService")
public class CourseResourceVideoService {

  @Resource
  CourseResourceVideoDao courseResourceVideoDao;

  @Resource
  CourseChapterService courseChapterService;

  @Resource
  CourseKeywordResourceService courseKeywordResourceService;

  @Resource
  ProductItemService productItemService;
  
  @Resource
  CourseChapterDao courseChapterDao;

  /**
   * 更新关键词列表
   * @param resourceId
   * @param keywordList
   * @return
   */
  public Boolean updateKeyPoint(Long resourceId,List<CourseKeywordModel> keywordList){
    Map<String,Object> cond = new HashMap<>();
    cond.put("id",resourceId);
    CourseResourceVideoModel videoModel = new CourseResourceVideoModel();
    videoModel.setKeyPoint(JSON.toJSONString(keywordList));
    return courseResourceVideoDao.updateEntity(cond,videoModel);
  }

  /**
   * 级联获取文档列表
   * @param courseId
   * @param chapterId
   * @return
   */
  public Page<CourseResourceVideoModel> cascadeQueryVideoByChapterId(Integer pageNo, Long courseId,Long chapterId){
    
    Page<CourseResourceVideoModel> page = new Page<CourseResourceVideoModel>();
    if(pageNo == null) { 
      page = null;
    }else {
      page.setPageNo(pageNo);
      page.setPageSize(20);
    }
    
    List<CourseChapterModel> courseChapterModels = courseChapterService.queryAllChildChapter(courseId, chapterId);
    List<Long> ids = new ArrayList<>();
    for (CourseChapterModel courseChapterModel:courseChapterModels){
      ids.add(courseChapterModel.getId());
    }
    ids.add(chapterId);
    Map<String,Object> cond = new HashMap<>();
    cond.put("chapterIds",ids);
    Page<CourseResourceVideoModel> courseResourceVideoModelPage =
      courseResourceVideoDao.cascadeQueryVideo(cond, page);
    return courseResourceVideoModelPage;
  }

  /**
   * 新增文档
   * @param entity
   * @return
   */
  public CourseResourceVideoModel addVideo(CourseResourceVideoModel entity){
    return courseResourceVideoDao.addEntity(entity);
  }

  /**
   * 新增文档
   * @param request
   * @return
   */
  public CourseResourceVideoModel addVideo(ResourceVideoCreateRequest request){
    CourseResourceVideoModel courseResourceVideoModel = new CourseResourceVideoModel();
    courseResourceVideoModel.setDetail(request.getDetail());
    courseResourceVideoModel.setName(request.getName());
    courseResourceVideoModel.setChapterId(request.getChapterId());
    courseResourceVideoModel.setUrl(request.getUrl());
    courseResourceVideoModel.setFileUrl(request.getFileUrl());
    courseResourceVideoModel.setCourseId(request.getCourseId());
    courseResourceVideoModel.setTimeLengthMinute(request.getTimeLengthMinute());
    courseResourceVideoModel.setTimeLengthSecond(request.getTimeLengthSecond());
    courseResourceVideoModel.setType(request.getType());
    courseResourceVideoModel.setImg(request.getImg());
    CourseResourceVideoModel result = this.addVideo(courseResourceVideoModel);
    return result;
  }

  /**
   * 删除文档
   * @param id
   * @return
   */
  public Boolean deleteResourceVideo(Integer id){
    Map<String,Object> cond = new HashMap<>();
    cond.put("id",id);
    return courseResourceVideoDao.deleteEntity(cond);
  }

  /**
   * 更新文档
   * @param entity
   * @return
   */
  public Boolean editVideo(CourseResourceVideoModel entity){
    Map<String,Object> cond = new HashMap<>();
    cond.put("id",entity.getId());
    ResourceDescription description = new ResourceDescription();
    description.setUrl(entity.getUrl());
    description.setFileUrl(entity.getFileUrl());
    productItemService.updateItemResource(entity.getId(),ResourceType.VIDEO.getTypeId(),JSON.toJSONString(description));
    return courseResourceVideoDao.updateEntity(cond,entity);
  }

  /**
   * 更新文档
   * @param request
   * @return
   */
  public Boolean editVideo(ResourceVideoEditRequest request){
    CourseResourceVideoModel courseResourceVideoModel = new CourseResourceVideoModel();
    courseResourceVideoModel.setId(request.getId());
    courseResourceVideoModel.setUrl(request.getUrl());
    courseResourceVideoModel.setName(request.getName());
    courseResourceVideoModel.setChapterId(request.getChapterId());
    courseResourceVideoModel.setDetail(request.getDetail());
    courseResourceVideoModel.setFileUrl(request.getFileUrl());
    courseResourceVideoModel.setTimeLengthMinute(request.getTimeLengthMinute());
    courseResourceVideoModel.setTimeLengthSecond(request.getTimeLengthSecond());
    courseResourceVideoModel.setType(request.getType());
    courseResourceVideoModel.setImg(request.getImg());
    return this.editVideo(courseResourceVideoModel);
  }

  /**
   * 根据主键查询
   * @param id
   * @return
   */
  public CourseResourceVideoModel findResourceVideoById(Long id){
    CourseResourceVideoModel entity = new CourseResourceVideoModel();
    entity.setId(id);
    return courseResourceVideoDao.findEntityById(entity);
  }
  
  /**
   * 根据chapterId查询
   * @return
   */
  public List<CourseResourceVideoModel> findAllResourceByChapterId(Long itemId){
    Map<String, Object> cond = new HashMap<>();
    cond.put("chapterId", itemId);
    Map<String, Object> output = new HashMap<>();
    output.put("id", "");
    output.put("courseId", "");
    output.put("chapterId", "");
    output.put("name", "");
    output.put("url", "");
    output.put("fileUrl", "");
    output.put("detail", "");
    output.put("keyPoint", "");
    output.put("chapterName", "");
    output.put("status", "");
    output.put("type", "");  
    output.put("timeLengthSecond", "");
    output.put("timeLengthMinute", "");
    output.put("img", "");
    List<CourseResourceVideoModel> list = courseResourceVideoDao
        .queryListByCond0(cond, output, null).getResult();
    
    return list;
  }

  public String builderTable(TreeUtils treeUtils, Long parentId, String chapterId) {
//    TreeUtils treeUtils = builderTreeUtils(chooseCourseId, parentId);
    // TODO 获取上传的章节ID
    String result =
        treeUtils
            .getTree(
                parentId.toString(),
                "<option value=\"${node.id}\" ${selected}>${spacer}${node.data.sindex}[${node.name}]</option>",
                chapterId, null, null);
    return result;
  }

  public TreeUtils builderTreeUtils(Long chooseCourseId, Long parentId) {
    List<CourseChapterModel> ChapterList = courseChapterService.queryAllChildChapter(chooseCourseId, parentId);
    List<TreeNode> treeNodeList = new ArrayList<>();
    for (CourseChapterModel chapterModel : ChapterList) {
      TreeNode treeNode = new TreeNode();
      treeNode.setId(chapterModel.getId().toString());
      treeNode.setName(chapterModel.getName());
      treeNode.setParentId(chapterModel.getParentId().toString());
      treeNode.setData(chapterModel);
      treeNode.setLevel(chapterModel.getLevel());
      treeNodeList.add(treeNode);
    }
    TreeUtils treeUtils = new TreeUtils(treeNodeList);
    return treeUtils;
  }


  
  public Long findChapterId(String courseId, String chapter, String item) {
    try {
      Map<String, Object> input = Maps.newHashMap();
      if (StringUtils.isNotBlank(chapter)) {
        input.put("s_index_zhang", chapter.trim());
      }
      if (StringUtils.isNotBlank(item)) {
        input.put("s_index_jie", item.trim());
      }
      input.put("_subjectId", courseId);
      Map<String, Object> output = Maps.newHashMap();
      output.put("id", "");
      Page<CourseChapterModel> res = courseChapterDao.findChapterId(input, output);
      if (res != null && res.getResult().size() > 0) {
        CourseChapterModel result = res.getResult().get(0);
        return result.getId() == null ? null : result.getId();
      }
    } catch (Exception e) {
      LoggerUtil.error("导入试题，课程id为控", e);
    }

    return null;
  }

  public String builderTable(String table, String vName, Long chapterId) {
    String s = "<tr><td><select datatype=\"n\" sucmsg=\" \" id=\"form-field-1\" class=\"col-xs-20 col-xs-15\" name=\"chapterId\">%s</select></td><td>%s</td><td><input type=\"checkbox\" name=\"isSelected\" value=\"%s\"></td></tr>";
    return String.format(s, table, vName, chapterId);
  }
  
  
}
