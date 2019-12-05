package com.xiaodou.course.service.notes;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.course.constant.CourseConstant;
import com.xiaodou.course.dao.notes.NotesDao;
import com.xiaodou.course.model.notes.NotesModel;
import com.xiaodou.course.model.product.ProductItemModel;
import com.xiaodou.course.model.product.ProductModel;
import com.xiaodou.course.service.AbstractService;
import com.xiaodou.course.service.facade.ProductServiceFacade;
import com.xiaodou.course.service.product.ProductCategoryService;
import com.xiaodou.course.service.product.ProductItemService;
import com.xiaodou.course.service.product.ProductService;
import com.xiaodou.course.web.request.notes.NotesAddRequest;
import com.xiaodou.course.web.request.notes.NotesDelRequest;
import com.xiaodou.course.web.request.notes.NotesListRequest;
import com.xiaodou.course.web.response.BaseResponse;
import com.xiaodou.course.web.response.notes.NotesListResponse;
import com.xiaodou.course.web.response.product.ProductDetailResponse;
import com.xiaodou.mission.engine.event.AddNoteEvent;
import com.xiaodou.mission.engine.event.EventBuilder;
import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.summer.dao.param.DeleteParam;
import com.xiaodou.summer.dao.param.IDeleteParam;
import com.xiaodou.summer.vo.out.ResultType;

@Service("notesService")
public class NotesService extends AbstractService {

  @Resource
  NotesDao notesDao;
  @Resource
  ProductServiceFacade productServiceFacade;
  @Resource
  ProductItemService productItemService;
  @Resource
  ProductService productService;
  @Resource
  ProductCategoryService productCategoryService;

  public BaseResponse addNotes(NotesAddRequest request) {
    // TODO Auto-generated method stub
    BaseResponse response;
    try {
      CheckResult check = checkIsValidCourseId(request, request.getCourseId());
      if (!check.isRetOk()) return new ProductDetailResponse(check.getResType());
      NotesModel notes = new NotesModel();
      // notes.setId(RandomUtil.randomNumber(10));
      notes.setTypeCode(request.getTypeCode());
      notes.setChapterId(request.getChapterId());
      notes.setContent(request.getContent());
      notes.setCourseId(request.getCourseId());
      notes.setItemId(request.getItemId());
      notes.setCreateTime(new Timestamp(System.currentTimeMillis()));
      notes.setUserId(request.getUid());
      if (notes.getCourseId() != null) {
        ProductModel product = productService.findProductById(Long.valueOf(notes.getCourseId()));
        if (product != null) {
          String resourcesName = "来自《" + product.getName() + "》";
          notes.setResourcesName(resourcesName);
        }
      }
      if (notes.getItemId() != null && notes.getChapterId() != null) {
        ProductItemModel item = productItemService.findItemById(Long.valueOf(notes.getItemId()));
        ProductItemModel chapter =
            productItemService.findItemById(Long.valueOf(notes.getChapterId()));
        if (item != null && chapter != null) {
          String source = chapter.getChapterId() + item.getChapterId() + item.getName();
          notes.setSource(source);
        }
      }
      notesDao.addEntity(notes);
      AddNoteEvent event = EventBuilder.buildAddNoteEvent();
      event.setUserId(request.getUid());
      event.setModule(CourseConstant.COMMON_MODULE);
      event.setMajorId(request.getMajorId());
      event.setCourseId(request.getCourseId());
      event.send();
      response = new BaseResponse(ResultType.SUCCESS);
    } catch (Exception e) {
      response = new BaseResponse(ResultType.SYSFAIL);
    }
    return response;
  }

  public BaseResponse delNotes(NotesDelRequest request) {
    BaseResponse response;
    try {
//      Map<String, Object> cond = new HashMap<String, Object>();
//      cond.put("id", request.getNotesId());
//      cond.put("userId", request.getUid());
      
      IDeleteParam param = new DeleteParam();
      param.addInput("id", request.getNotesId());
      param.addInput("userId", request.getUid());
      notesDao.deleteEntityByCond(param);
//      notesDao.deleteEntityByCond(cond);
      response = new BaseResponse(ResultType.SUCCESS);
    } catch (Exception e) {
      response = new BaseResponse(ResultType.SYSFAIL);
    }
    return response;
  }

  public NotesModel getNotesById(String id) {
    NotesModel nodes = new NotesModel();
    nodes.setId(id);
    return notesDao.findEntityById(nodes);
  }

  public NotesListResponse getNotesList(NotesListRequest request) {
    NotesListResponse response = new NotesListResponse(ResultType.SUCCESS);
    try {
      Map<String, Object> cond = Maps.newHashMap();
      if (StringUtils.isNotBlank(request.getUid())) cond.put("userId", request.getUid());
      if (StringUtils.isNotBlank(request.getNotesId())) cond.put("idUpper", request.getNotesId());
      if (CourseConstant.IS_VALID.equals(request.getCourseStatus()))// 有效课程
        cond.put("applyTime", "1");
      else if (CourseConstant.IS_NOT_VALID.equals(request.getCourseStatus())) // 无效课程
        cond.put("noApplyTime", "1");
      Map<String, Object> output = Maps.newHashMap();
      output.put("id", "id");
      output.put("courseId", "courseId");
      output.put("chapterId", "chapterId");
      output.put("itemId", "itemId");
      output.put("content", "content");
      output.put("source", "source");
      output.put("resourcesName", "resourcesName");
      output.put("createTime", "createTime");
      output.put("noteId", 1);
      output.put("typeCode", 1);
      output.put("productCategoryName", 1);
      Page<NotesModel> page = productServiceFacade.queryUserNotesByCond(cond, output);
      response.setNotes(page.getResult());
    } catch (Exception e) {
      LoggerUtil.error("getNotesList", e);
      response = new NotesListResponse(ResultType.SYSFAIL);
    }
    return response;
  }

}
