package com.xiaodou.ms.service.course;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xiaodou.common.util.StringUtils;
import com.xiaodou.ms.dao.course.CourseChapterDao;
import com.xiaodou.ms.model.course.CourseChapterModel;
import com.xiaodou.ms.util.tree.TreeNode;
import com.xiaodou.ms.util.tree.TreeUtils;
import com.xiaodou.ms.web.request.course.ChapterCreateRequest;
import com.xiaodou.ms.web.request.course.ChapterEditRequest;
import com.xiaodou.summer.dao.pagination.Page;

/**
 * Created by zyp on 15/4/19.
 */
@Service("courseChapterService")
public class CourseChapterService {

  @Resource
  CourseChapterDao courseChapterDao;


  /**
   * 多选树
   * 
   * @param ids
   * @param subjectId
   * @return
   */
  public String multiSelectChapterTree(List<String> ids, Long subjectId) {
    List<CourseChapterModel> courseChapters = this.queryAllChildChapter(subjectId, 0L);
    List<TreeNode> treeNodeList = new ArrayList<>();
    for (CourseChapterModel chapterModel : courseChapters) {
      TreeNode treeNode = new TreeNode();
      treeNode.setId(chapterModel.getId().toString());
      treeNode.setName(chapterModel.getName());
      treeNode.setParentId(chapterModel.getParentId().toString());
      treeNode.setData(chapterModel);
      treeNode.setLevel(chapterModel.getLevel());
      treeNodeList.add(treeNode);
    }
    TreeUtils treeUtils = new TreeUtils(treeNodeList);
    String template =
        "<tr id=\"node-${node.id}\" data-tt-id=\"${node.id}\" data-tt-parent-id=\"${node.parentId}\" #if($node.parentId!='0') class=\"child-of-node-${node.parentId}\" #end ><td>${spacer}<input type=\"checkbox\" name=\"menuid[]\" value=\"${node.id}\" level=\"${node.level}\" ${checked} onclick='javascript:checknode(this);' /> ${node.data.sindex}[${node.name}]</td></tr>";
    String treeMulti = treeUtils.getTreeMulti("0", template, ids, null, null);
    return treeMulti;
  }

  /**
   * 新增章节
   * 
   * @param entity
   * @return
   */
  public CourseChapterModel addChapter(CourseChapterModel entity) {
    CourseChapterModel courseChapterModel = courseChapterDao.addEntity(entity);
    this.cleanTree(entity.getSubjectId());
    return courseChapterModel;
  }

  /**
   * 新增章节
   * 
   * @param entity
   * @return
   */
  public CourseChapterModel addChapter0(CourseChapterModel entity) {
    CourseChapterModel courseChapterModel = courseChapterDao.addEntity(entity);
    return courseChapterModel;
  }

  /**
   * 新增章节
   * 
   * @param request
   * @return
   */
  public CourseChapterModel addChapter(ChapterCreateRequest request) {
    CourseChapterModel courseChapterModel = new CourseChapterModel();
    courseChapterModel.setName(request.getName());
    courseChapterModel.setSindex(request.getSIndex());
    courseChapterModel.setDetail(request.getDetail());
    courseChapterModel.setParentId(request.getParentId());
    courseChapterModel.setSubjectId(request.getSubjectId());
    courseChapterModel.setCreateTime(new Timestamp(System.currentTimeMillis()));
    return this.addChapter(courseChapterModel);
  }

  /**
   * 删除章节
   * 
   * @param chapterId
   * @return
   */
  public Boolean deleteChapter(Long chapterId) {
    CourseChapterModel chapter = this.findChapterById(chapterId);
    Map<String, Object> cond = new HashMap<>();
    cond.put("id", chapterId);
    courseChapterDao.deleteEntity(cond);
    return this.cleanTree(chapter.getSubjectId());
  }

  /**
   * 更新章节
   * 
   * @param entity
   * @return
   */
  public Boolean editChapter(CourseChapterModel entity) {
    Map<String, Object> cond = new HashMap<>();
    cond.put("id", entity.getId());
    courseChapterDao.updateEntity(cond, entity);
    CourseChapterModel chapter = this.findChapterById(entity.getId());
    if (entity.getParentId() != null) {
      this.cleanTree(chapter.getSubjectId());
    }
    return true;
  }

  /**
   * 编辑章节
   * 
   * @param request
   * @return
   */
  public Boolean editChapter(ChapterEditRequest request) {
    CourseChapterModel courseChapterModel = new CourseChapterModel();
    courseChapterModel.setId(request.getId());
    courseChapterModel.setParentId(request.getParentId());
    courseChapterModel.setName(request.getName());
    courseChapterModel.setSindex(request.getSIndex());
    courseChapterModel.setDetail(request.getDetail());
    return this.editChapter(courseChapterModel);
  }

  /**
   * 根据主键查询
   * 
   * @param chapterId
   * @return
   */
  public CourseChapterModel findChapterById(Long chapterId) {
    CourseChapterModel entity = new CourseChapterModel();
    entity.setId(chapterId);
    return courseChapterDao.findEntityById(entity);
  }

  public String ChapterTableTree(Long courseId, Long parentId, String template) {
    List<CourseChapterModel> ChapterList = this.queryAllChildChapter(courseId, parentId);
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
    String result = treeUtils.getTree("0", template, null, null, null);
    return result;
  }

  /**
   * 目录列表
   * 
   * @return
   */
  public String ChapterTableTree(Long courseId, Long parentId) {
    String template =
        "<tr>"
            + "<td>${node.id}</td>"
            + "<td><input style='padding:0px;' name='listOrder' preValue='${node.data.listOrder}' id='${node.id}' class='orderInput' type='text' value='${node.data.listOrder}' /></td>"
            + "<td>${spacer}${node.data.sindex}</td>"
            + "<td>${node.name}</td>"
            + "<td>${node.data.detail}</td>"
            + "<td>"
            + "<a onclick='editChapter(${node.id})' style='cursor: pointer;'>编辑</a>"
            + "<a onclick='delChapter(${node.id},\"${node.name}\")' style='cursor: pointer;padding-left:10px;'>删除</a>"
            + "<a onclick='addChildChapter(${node.data.subjectId},${node.id})' style='cursor: pointer;padding-left:10px'>添加子章节</a>"
            + "<a href='/courseKeyword/keywordsList?courseId=${node.data.subjectId}&chapterId=${node.id}' style='cursor: pointer;padding-left:10px'>编辑考点</a>"
            + "<div class=\"btn-group\" style=\"margin-left: 10px\">\n"
            + "                    <div class=\"dropdown\">\n"
            + "                        <button class=\"btn  btn-primary dropdown-toggle\" style=\"border: 0px; width: 90px; padding: 2px 4px; font-size: 12px;\" type=\"button\" id=\"dropdownMenu2\" data-toggle=\"dropdown\" aria-haspopup=\"true\" aria-expanded=\"true\">\n"
            + "                            资源管理\n"
            + "                            <span class=\"caret\"></span>\n"
            + "                        </button>\n"
            + "                        <ul class=\"dropdown-menu\" aria-labelledby=\"dropdownMenu2\">\n"
            + "                            <li><a href=\"/question/list?courseId=${node.data.subjectId}&chapterId=${node.id}\">试题</a></li>\n"
            + "                            <li><a href=\"/courseVideo/list?courseId=${node.data.subjectId}&chapterId=${node.id}\">视频</a></li>\n"
            + "                            <li><a href=\"/courseDoc/list?courseId=${node.data.subjectId}&chapterId=${node.id}\">文档</a></li>\n"
            + "                            <li><a href=\"/courseHtml5/list?courseId=${node.data.subjectId}&chapterId=${node.id}\">Html5</a></li>\n"
            + "                        </ul>\n" + "                    </div>\n"
            + "                </div>" + "</td>" + "</tr>";
    return this.ChapterTableTree(courseId, parentId, template);
  }

  /**
   * jqueryTree
   * 
   * @param courseId
   * @param parentId
   * @param parentTemplate
   * @param childTemplate
   * @return
   */
  public String ChapterJqueryTree(Long courseId, Long parentId, String parentTemplate,
      String childTemplate) {
    List<CourseChapterModel> ChapterList = this.queryAllChildChapter(courseId, parentId);
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

    String result =
        treeUtils.getJqueryTree(parentId.toString(), "chapterTree", parentTemplate, childTemplate,
            "filetree");
    return result;
  }

  /**
   * 获取jqueryTree
   * 
   * @param courseId
   * @param parentId
   * @return
   */
  public String ChapterJqueryTree(Long courseId, Long parentId) {
    if (courseId == null || courseId == 0) {
      return "";
    }
    List<CourseChapterModel> ChapterList = this.queryAllChildChapter(courseId, parentId);
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
    String parentTemplate =
        "<a target=\"listframe\" onclick=\"showChapterResource(${node.data.subjectId},${node.id})\" style=\"cursor: pointer;\" class=\"folder\">${node.data.sindex}[${node.name}]</a>";
    String childTemplate =
        "<a target=\"listframe\" onclick=\"showChapterResource(${node.data.subjectId},${node.id})\" style=\"cursor: pointer;\" class=\"file\">${node.data.sindex}[${node.name}]</a>";
    String result =
        treeUtils.getJqueryTree("0", "chapterTree", parentTemplate, childTemplate, "filetree");
    return result;
  }

  /**
   * 获取带checkBox的jqueryTree
   * 
   * @author zhouhaun
   * @param courseId
   * @param parentId
   * @return
   */
  public String ChapterJqueryTreeCheckBox(Long courseId, Long parentId) {
    if (courseId == null || courseId == 0) {
      return "";
    }
    List<CourseChapterModel> ChapterList = this.queryAllChildChapter(courseId, parentId);
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
    String parentTemplate =
        "<div><input style='float:left' onchange='change(this)' type='checkbox' id='${node.id}' class=\"file\">${node.data.sindex}[${node.name}]</input></div>";
    String childTemplate =
        "<div><input style='float:left' type='checkbox' name=\"${node.data.parentId}\" id='${node.data.subjectId},${node.id}' class=\"file\">${node.data.sindex}[${node.name}]</input> </div>";
    String result =
        treeUtils.getJqueryTree("0", "chapterTree", parentTemplate, childTemplate, "filetree");
    return result;
  }

  /**
   * 下拉框目录列表
   * 
   * @return
   */
  public String ChapterSelectTree(Long courseId, Long parentId, String selectedId) {
    List<CourseChapterModel> ChapterList = this.queryAllChildChapter(courseId, parentId);
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
    String result =
        treeUtils
            .getTree(
                parentId.toString(),
                "<option value=\"${node.id}\" ${selected}>${spacer}${node.data.sindex}[${node.name}]</option>",
                selectedId, null, null);
    return result;
  }

  /**
   * 获取所有子地区
   * 
   * @param chapterId 栏目id
   * @param courseId
   * @return
   */
  public List<CourseChapterModel> queryAllChildChapter(Long courseId, Long chapterId) {
    if (chapterId == 0) {
      return this.queryAllChapter(courseId);
    }
    CourseChapterModel courseChapterModel = this.findChapterById(chapterId);
    if (courseChapterModel == null) {
      return new ArrayList<>();
    }
    if (StringUtils.isNotBlank(courseChapterModel.getAllChildId())) {
      String[] ids = courseChapterModel.getAllChildId().split(",");
      List<Integer> idList = new ArrayList<>();
      for (String idString : ids) {
        idList.add(Integer.parseInt(idString));
      }
      return this.queryChapterByIds(idList);
    } else {
      return new ArrayList<>();
    }
  }

  /**
   * 根据Ids查询
   * 
   * @param ids
   * @return
   */
  public List<CourseChapterModel> queryChapterByIds(List<Integer> ids) {
    Map<String, Object> cond = new HashMap<String, Object>();
    cond.put("ids", ids);
    Map<String, Object> output = new HashMap<>();
    output.put("id", "");
    output.put("subjectId", "");
    output.put("parentId", "");
    output.put("name", "");
    output.put("sindex", "");
    output.put("detail", "");
    output.put("createTime", "");
    output.put("childId", "");
    output.put("allChildId", "");
    output.put("allParentId", "");
    output.put("level", "");
    output.put("isLeaf", "");
    output.put("sindex", "");
    output.put("listOrder", "");
    Page<CourseChapterModel> ChapterList = courseChapterDao.queryListByCond0(cond, output, null);
    return ChapterList.getResult();
  }


  /**
   * 根据父Id查询地区
   * 
   * @param parentId
   * @return
   */
  public List<CourseChapterModel> queryChapterByParentId(Integer courseId, Integer parentId) {
    Map<String, Object> cond = new HashMap<String, Object>();
    cond.put("parentId", parentId);
    cond.put("subjectId", courseId);
    Map<String, Object> output = new HashMap<>();
    output.put("id", "");
    output.put("subjectId", "");
    output.put("parentId", "");
    output.put("name", "");
    output.put("detail", "");
    output.put("createTime", "");
    output.put("childId", "");
    output.put("allChildId", "");
    output.put("allParentId", "");
    output.put("level", "");
    output.put("isLeaf", "");
    output.put("sindex", "");
    output.put("listOrder", "");
    Page<CourseChapterModel> ChapterList = courseChapterDao.queryListByCond0(cond, output, null);
    return ChapterList.getResult();
  }

  /**
   * 根据父Id查询地区
   * 
   * @param courseId
   * @return
   */
  public List<CourseChapterModel> queryAllChapter(Long courseId) {
    Map<String, Object> cond = new HashMap<String, Object>();
    cond.put("subjectId", courseId);
    Map<String, Object> output = new HashMap<>();
    output.put("id", "");
    output.put("subjectId", "");
    output.put("parentId", "");
    output.put("name", "");
    output.put("sindex", "");
    output.put("detail", "");
    output.put("createTime", "");
    output.put("childId", "");
    output.put("allChildId", "");
    output.put("allParentId", "");
    output.put("level", "");
    output.put("isLeaf", "");
    output.put("sindex", "");
    output.put("listOrder", "");
    Page<CourseChapterModel> ChapterList = courseChapterDao.queryListByCond0(cond, output, null);
    return ChapterList.getResult();
  }

  /**
   * 清洗tree
   * 
   * @return
   */
  public Boolean cleanTree(Long courseId) {
    // 1.查询栏目列表
    List<CourseChapterModel> courseChapterModelList = this.queryAllChapter(courseId);
    // 2.栏目ID
    Map<Long, CourseChapterModel> idMap = new HashMap<>();
    for (CourseChapterModel chapterModel : courseChapterModelList) {
      idMap.put(chapterModel.getId(), chapterModel);
    }
    // 3.父栏目ID
    Map<Long, List<CourseChapterModel>> parentIdMap = new HashMap<>();
    for (CourseChapterModel chapterModel : courseChapterModelList) {
      List<CourseChapterModel> chapterModelList = null;
      if (parentIdMap.containsKey(chapterModel.getParentId())) {
        chapterModelList = parentIdMap.get(chapterModel.getParentId());
      } else {
        chapterModelList = new ArrayList<>();
      }
      chapterModelList.add(chapterModel);
      parentIdMap.put(chapterModel.getParentId(), chapterModelList);
    }
    // 4.更新记录
    List<Long> needDeleteIds = new ArrayList<>();
    for (CourseChapterModel chapterModel : courseChapterModelList) {
      List<CourseChapterModel> parentList = this.parentList(idMap, chapterModel.getId());
      Boolean needDelete = true;
      for (CourseChapterModel courseChapterModel : parentList) {
        if (courseChapterModel.getParentId() == 0) {
          needDelete = false;
          break;
        }
      }
      if (needDelete == true) {
        needDeleteIds.add(chapterModel.getId());
        continue;
      }
      List<CourseChapterModel> allChildList = this.allChildId(parentIdMap, chapterModel.getId());
      List<CourseChapterModel> childList = parentIdMap.get(chapterModel.getId());

      CourseChapterModel updateEntity = new CourseChapterModel();
      updateEntity.setId(chapterModel.getId());
      updateEntity.setChildId(this.idString(childList));
      updateEntity.setAllChildId(this.idString(allChildList));
      updateEntity.setLevel(parentList.size() - 1);
      updateEntity.setAllParentId(this.idString(parentList));
      if (childList != null && childList.size() > 0) {
        updateEntity.setIsLeaf(0);
      } else {
        updateEntity.setIsLeaf(1);
      }
      this.editChapter(updateEntity);
    }
    // 5.删除记录
    if (needDeleteIds.size() > 0) {
      Map<String, Object> cond = new HashMap<>();
      cond.put("ids", needDeleteIds);
      courseChapterDao.deleteEntity(cond);
    }
    return true;
  }

  /**
   * ID序列化
   * 
   * @param chapterModelList
   * @return
   */
  private String idString(List<CourseChapterModel> chapterModelList) {
    StringBuilder stringBuilder = new StringBuilder("");
    if (chapterModelList == null) {
      return "";
    }
    for (CourseChapterModel chapterModel : chapterModelList) {
      stringBuilder.append(chapterModel.getId() + ",");
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
  private List<CourseChapterModel> parentList(Map<Long, CourseChapterModel> idMap, Long catId) {
    CourseChapterModel chapterModel = idMap.get(catId);
    List<CourseChapterModel> result = new ArrayList<>();
    if (chapterModel != null) {
      if (chapterModel.getParentId() != 0) {
        result.addAll(this.parentList(idMap, chapterModel.getParentId()));
      }
      result.add(chapterModel);
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
  private List<CourseChapterModel> allChildId(Map<Long, List<CourseChapterModel>> parentIdMap,
      Long catId) {
    List<CourseChapterModel> result = new ArrayList<>();
    List<CourseChapterModel> chapterList = parentIdMap.get(catId);
    if (chapterList != null) {
      result.addAll(chapterList);
      for (CourseChapterModel chapterModel : chapterList) {
        result.addAll(this.allChildId(parentIdMap, chapterModel.getId()));
      }
    }
    return result;
  }

  /**
   * 获取所有无章节子地区
   * 
   * @param chapterId 栏目id
   * @param courseId
   * @return
   */
  public List<CourseChapterModel> queryAllChildChapterNoChapter(Integer courseId, Integer chapterId) {
    if (chapterId == 0) {
      return this.queryAllItemNoChapter(courseId);
    } else
      return this.findChapterMissionById(chapterId);

  }

  /**
   * 根据父Id查询地区无章节
   * 
   * @param courseId
   * @return
   */
  public List<CourseChapterModel> queryAllItemNoChapter(Integer courseId) {
    Map<String, Object> cond = new HashMap<String, Object>();
    cond.put("subjectId", courseId);
    cond.put("parentIdNo", "0");
    Map<String, Object> output = new HashMap<>();
    output.put("id", "");
    output.put("subjectId", "");
    output.put("parentId", "");
    output.put("name", "");
    output.put("sindex", "");
    output.put("detail", "");
    output.put("createTime", "");
    output.put("childId", "");
    output.put("allChildId", "");
    output.put("allParentId", "");
    output.put("level", "");
    output.put("isLeaf", "");
    output.put("sindex", "");
    output.put("listOrder", "");
    output.put("missionId", "");
    Page<CourseChapterModel> ChapterList = courseChapterDao.cascadeQueryChapter(cond, output);
    return ChapterList.getResult();
  }

  /**
   * 根据主键查询外联任务表
   * 
   * @param chapterId
   * @return
   */
  public List<CourseChapterModel> findChapterMissionById(Integer chapterId) {
    Map<String, Object> cond = new HashMap<String, Object>();
    cond.put("chapterId", chapterId);
    cond.put("parentId", chapterId);
    Map<String, Object> output = new HashMap<>();
    output.put("id", "");
    output.put("subjectId", "");
    output.put("parentId", "");
    output.put("name", "");
    output.put("sindex", "");
    output.put("detail", "");
    output.put("createTime", "");
    output.put("childId", "");
    output.put("allChildId", "");
    output.put("allParentId", "");
    output.put("level", "");
    output.put("isLeaf", "");
    output.put("sindex", "");
    output.put("listOrder", "");
    output.put("missionId", "");
    Page<CourseChapterModel> ChapterList = courseChapterDao.cascadeQueryChapter(cond, output);
    return ChapterList.getResult();
  }

}
