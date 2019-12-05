package com.xiaodou.ms.service.course;

import com.xiaodou.common.util.StringUtils;
import com.xiaodou.ms.dao.course.CourseCategoryDao;
import com.xiaodou.ms.model.course.CourseCategoryModel;
import com.xiaodou.ms.util.tree.TreeNode;
import com.xiaodou.ms.util.tree.TreeUtils;
import com.xiaodou.ms.web.request.course.CategoryCreateRequest;
import com.xiaodou.ms.web.request.course.CategoryEditRequest;
import com.xiaodou.ms.web.response.ResultType;
import com.xiaodou.ms.web.response.course.CategoryCreateResponse;
import com.xiaodou.ms.web.response.course.CategoryEditResponse;
import com.xiaodou.summer.dao.pagination.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zyp on 15/4/19.
 */

@Service("courseCategoryService")
public class CourseCategoryService {

  /**
   * 资源分类DAO
   */
  @Resource
  CourseCategoryDao courseCategoryDao;

  /**
   * 新增目录
   * @param entity
   * @return
   */
  public CourseCategoryModel addCategory(CourseCategoryModel entity){
    CourseCategoryModel categoryModel = courseCategoryDao.addEntity(entity);
 /*   this.cleanTree();*/
    return categoryModel;
  }

  /**
   * 新增目录
   * @param request
   * @return
   */
  public CategoryCreateResponse addCategory(CategoryCreateRequest request){
    CourseCategoryModel categoryModel = new CourseCategoryModel();
    categoryModel.setCreateTime(new Timestamp(System.currentTimeMillis()));
    categoryModel.setDetail(request.getDetail());
    categoryModel.setName(request.getName());
    categoryModel.setParentId(request.getParentId());
    CourseCategoryModel resultModel = this.addCategory(categoryModel);
    CategoryCreateResponse response = new CategoryCreateResponse(ResultType.SUCCESS);
    response.setCatId(resultModel.getId());
    return response;
  }

  /**
   * 删除目录
   * @param catId
   * @return
   */
  public Boolean deleteCategory(Integer catId){
    Map<String,Object> cond = new HashMap<>();
    cond.put("id", catId);
    courseCategoryDao.deleteEntity(cond);
  /*  return this.cleanTree();*/
    return true;
  }

  /**
   * 更新目录
   * @param entity
   * @return
   */
  public Boolean editCategory(CourseCategoryModel entity){
    Map<String,Object> cond = new HashMap<>();
    cond.put("id",entity.getId());
    courseCategoryDao.updateEntity(cond, entity);
/*    if (entity.getParentId()!=null){
      this.cleanTree();
    }*/
    return true;
  }

  /**
   * 目录编辑
   * @param request
   * @return
   */
  public CategoryEditResponse editCategory(CategoryEditRequest request){
    CourseCategoryModel categoryModel = new CourseCategoryModel();
    categoryModel.setDetail(request.getDetail());
    categoryModel.setName(request.getName());
    categoryModel.setParentId(request.getParentId());
    categoryModel.setId(request.getId());
    Boolean aBoolean = this.editCategory(categoryModel);
    CategoryEditResponse response = null;
    if (aBoolean){
      response = new CategoryEditResponse(ResultType.SUCCESS);
    } else {
      response = new CategoryEditResponse(ResultType.SYS_FAIL);
    }
    return response;
  }

  /**
   * 根据主键查询
   * @param catId
   * @return
   */
  public CourseCategoryModel findCategoryById(Long catId){
    CourseCategoryModel entity = new CourseCategoryModel();
    entity.setId(catId);
    return courseCategoryDao.findEntityById(entity);
  }

  /**
   * 目录列表
   * @return
   */
  public String categoryTableTree(Long parentId){
    List<CourseCategoryModel> categoryList = this.queryAllChildCategory(parentId);
    Map<String,TreeNode> treeNodeMap = new HashMap();
    for (CourseCategoryModel courseCategory:categoryList){
      TreeNode treeNode = new TreeNode();
      treeNode.setId(courseCategory.getId().toString());
      treeNode.setName(courseCategory.getName());
      treeNode.setParentId(courseCategory.getParentId().toString());
      treeNode.setData(courseCategory);
      treeNodeMap.put(courseCategory.getId().toString(), treeNode);
    }
    TreeUtils treeUtils = new TreeUtils(treeNodeMap);
    String result =  treeUtils.getTree("0", "" +
        "<tr>" +
        "<td>${node.id}</td>" +
        "<td>${spacer}${node.name}</td>" +
        "<td>${node.data.detail}</td>" +
        "<td>" +
        "<a onclick='editCategory(${node.id})' style='cursor: pointer;'>编辑</a>" +
        "<a onclick='delCategory(${node.id},\"${node.name}\")' style='cursor: pointer;padding-left:10px;'>删除</a>" +
        "<a href='/course/list?catId=${node.id}' style='cursor: pointer;padding-left:10px;'>课程管理</a>" +
        "</td>" +
        "</tr>",
      null, null, null);
    return result;
  }

  /**
   * 下拉框目录列表
   * @return
   */
  public String categorySelectTree(Long parentId,String selectedId){
    List<CourseCategoryModel> categoryList = this.queryAllChildCategory(parentId);
    Map<String,TreeNode> treeNodeMap = new HashMap();
    for (CourseCategoryModel courseCategory:categoryList){
      TreeNode treeNode = new TreeNode();
      treeNode.setId(courseCategory.getId().toString());
      treeNode.setName(courseCategory.getName());
      treeNode.setParentId(courseCategory.getParentId().toString());
      treeNode.setData(courseCategory);
      treeNodeMap.put(courseCategory.getId().toString(), treeNode);
    }
    TreeUtils treeUtils = new TreeUtils(treeNodeMap);
    String result =  treeUtils.getTree(parentId.toString(), "<option value=\"${node.id}\" ${selected}>${spacer}${node.name}</option>", selectedId, null, null);
    return result;
  }

  /**
   * jquery树状结构
   * @param parentId
   * @return
   */
  public String categoryJqueryTree(Long parentId,String parentTemplate,String childTemplate){
    List<CourseCategoryModel> categoryList = this.queryAllChildCategory(parentId);
    Map<String,TreeNode> treeNodeMap = new HashMap();
    for (CourseCategoryModel courseCategory:categoryList){
      TreeNode treeNode = new TreeNode();
      treeNode.setId(courseCategory.getId().toString());
      treeNode.setName(courseCategory.getName());
      treeNode.setParentId(courseCategory.getParentId().toString());
      treeNode.setData(courseCategory);
      treeNodeMap.put(courseCategory.getId().toString(), treeNode);
    }
    TreeUtils treeUtils = new TreeUtils(treeNodeMap);
    return treeUtils.getJqueryTree("0","catTree",parentTemplate,childTemplate,"filetree");
  }


  /**
   * 根据父Id查询地区
   * @param parentId
   * @return
   */
  public List<CourseCategoryModel> queryCategoryByParentId(Integer parentId) {
    Map<String, Object> cond = new HashMap<String, Object>();
    cond.put("parentId", parentId);
    Map<String,Object> output = new HashMap<>();
    output.put("id","");
    output.put("parentId","");
    output.put("name","");
    output.put("detail","");
    output.put("createTime","");
    output.put("childId","");
    output.put("allChildId","");
    output.put("allParentId","");
    output.put("level","");
    output.put("isLeaf","");
    Page<CourseCategoryModel> categoryList = courseCategoryDao.queryListByCond0(cond, output, null);
    return categoryList.getResult();
  }

  /**
   * 获取所有栏目
   * @return
   */
  public List<CourseCategoryModel> queryAllCategory() {
    Map<String, Object> cond = new HashMap<String, Object>();
    Map<String,Object> output = new HashMap<>();
    output.put("id","");
    output.put("parentId","");
    output.put("name","");
    output.put("detail","");
    output.put("createTime","");
    output.put("childId","");
    output.put("allChildId","");
    output.put("allParentId","");
    output.put("level","");
    output.put("isLeaf","");
    Page<CourseCategoryModel> categoryList = courseCategoryDao.queryListByCond0(cond, output, null);
    return categoryList.getResult();
  }

  /**
   * 根据ID查找记录
   * @param ids
   * @return
   */
  public List<CourseCategoryModel> queryCategoryByIds(List<Integer> ids){
    Map<String, Object> cond = new HashMap<String, Object>();
    cond.put("ids", ids);
    Map<String,Object> output = new HashMap<>();
    output.put("id","");
    output.put("parentId","");
    output.put("name","");
    output.put("detail","");
    output.put("createTime","");
    output.put("childId","");
    output.put("allChildId","");
    output.put("allParentId","");
    output.put("level","");
    output.put("isLeaf","");
    Page<CourseCategoryModel> categoryList = courseCategoryDao.queryListByCond0(cond, output, null);
    return categoryList.getResult();
  }

  /**
   * 获取所有栏目Id,map
   * @return
   */
  public Map<Long,CourseCategoryModel> queryAllCategoryMap() {
    List<CourseCategoryModel> courseCategoryModelList = this.queryAllCategory();
    Map<Long,CourseCategoryModel> resultMap = new HashMap<>();
    for (CourseCategoryModel categoryModel:courseCategoryModelList){
      resultMap.put(categoryModel.getId(),categoryModel);
    }
    return resultMap;
  }

  /**
   * 查询所有子栏目
   * @param id
   * @return
   */
  public List<CourseCategoryModel> queryAllChildCategory(Long id){
    if (id==0){
      return this.queryAllCategory();
    }
    CourseCategoryModel courseCategory = this.findCategoryById(id);
    if (courseCategory==null){
      return new ArrayList<>();
    }
    if (StringUtils.isNotBlank(courseCategory.getAllChildId())){
      String[] ids = courseCategory.getAllChildId().split(",");
      List<Integer> idList = new ArrayList<>();
      for (String idString:ids){
        idList.add(Integer.parseInt(idString));
      }
      return this.queryCategoryByIds(idList);
    } else {
      return new ArrayList<>();
    }
  }

  /**
   * 清洗tree
   * @return
   */
  public Boolean cleanTree() {
    //1.查询栏目列表
    List<CourseCategoryModel> courseCategoryModelList = this.queryAllCategory();
    //2.栏目ID
    Map<Long, CourseCategoryModel> idMap = new HashMap<>();
    for (CourseCategoryModel categoryModel : courseCategoryModelList) {
      idMap.put(categoryModel.getId(), categoryModel);
    }
    //3.父栏目ID
    Map<Long, List<CourseCategoryModel>> parentIdMap = new HashMap<>();
    for (CourseCategoryModel categoryModel : courseCategoryModelList) {
      List<CourseCategoryModel> categoryModelList = null;
      if (parentIdMap.containsKey(categoryModel.getParentId())) {
        categoryModelList = parentIdMap.get(categoryModel.getParentId());
      } else {
        categoryModelList = new ArrayList<>();
      }
      categoryModelList.add(categoryModel);
      parentIdMap.put(categoryModel.getParentId(), categoryModelList);
    }
    //4.更新记录
    List<Long> needDeleteIds = new ArrayList<>();
    for (CourseCategoryModel categoryModel : courseCategoryModelList) {
      List<CourseCategoryModel> parentList = this.parentList(idMap, categoryModel.getId());
      Boolean needDelete = true;
      for (CourseCategoryModel courseCategoryModel : parentList) {
        if (courseCategoryModel.getParentId() == 0) {
          needDelete = false;
          break;
        }
      }
      if (needDelete == true) {
        needDeleteIds.add(categoryModel.getId());
        continue;
      }
      List<CourseCategoryModel> allChildList = this.allChildId(parentIdMap, categoryModel.getId());
      List<CourseCategoryModel> childList = parentIdMap.get(categoryModel.getId());

      CourseCategoryModel updateEntity = new CourseCategoryModel();
      updateEntity.setId(categoryModel.getId());
      updateEntity.setChildId(this.idString(childList));
      updateEntity.setAllChildId(this.idString(allChildList));
      updateEntity.setLevel(parentList.size() - 1);
      updateEntity.setAllParentId(this.idString(parentList));
      if (childList!=null&&childList.size() > 0) {
        updateEntity.setIsLeaf(0);
      } else {
        updateEntity.setIsLeaf(1);
      }
        this.editCategory(updateEntity);
    }
    //5.删除记录
    if (needDeleteIds.size() > 0) {
      Map<String, Object> cond = new HashMap<>();
      cond.put("ids", needDeleteIds);
      courseCategoryDao.deleteEntity(cond);
    }
    return true;
  }

  /**
   * ID序列化
   * @param categoryModelList
   * @return
   */
  private String idString(List<CourseCategoryModel> categoryModelList){
    StringBuilder stringBuilder = new StringBuilder("");
    if (categoryModelList==null){
      return "";
    }
    for (CourseCategoryModel categoryModel:categoryModelList){
      stringBuilder.append(categoryModel.getId()+",");
    }
    return stringBuilder.toString();
  }

  /**
   * 父节点
   * @param catId
   * @param idMap
   * @return
   */
  private List<CourseCategoryModel> parentList(Map<Long,CourseCategoryModel> idMap,Long catId){
    CourseCategoryModel categoryModel = idMap.get(catId);
    List<CourseCategoryModel> result = new ArrayList<>();
    if (categoryModel!=null){
      if(categoryModel.getParentId()!=0){
        result.addAll(this.parentList(idMap,categoryModel.getParentId()));
      }
      result.add(categoryModel);
      return result;
    } else {
      return new ArrayList<>();
    }
  }

  /**
   * 所有子节点
   * @param parentIdMap
   * @param catId
   * @return
   */
  private List<CourseCategoryModel> allChildId(Map<Long,List<CourseCategoryModel>> parentIdMap,Long catId){
    List<CourseCategoryModel> result = new ArrayList<>();
    List<CourseCategoryModel> categoryList = parentIdMap.get(catId);
    if (categoryList!=null){
      result.addAll(categoryList);
      for (CourseCategoryModel categoryModel:categoryList){
        result.addAll(this.allChildId(parentIdMap,categoryModel.getId()));
      }
    }
    return result;
  }
  /**
   * 新增目录不清洗树
   * @param entity
   * @author zhouhaun
   * @return
   */
  public CourseCategoryModel addCategoryNoTree(CourseCategoryModel entity){
    CourseCategoryModel categoryModel = courseCategoryDao.addEntity(entity);
    return categoryModel;
  }

}
