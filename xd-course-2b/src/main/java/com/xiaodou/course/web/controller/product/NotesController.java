package com.xiaodou.course.web.controller.product;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.course.service.notes.NotesService;
import com.xiaodou.course.web.controller.BaseController;
import com.xiaodou.course.web.request.notes.NotesAddRequest;
import com.xiaodou.course.web.request.notes.NotesDelRequest;
import com.xiaodou.course.web.request.notes.NotesListRequest;
import com.xiaodou.course.web.response.BaseResponse;
import com.xiaodou.course.web.response.notes.NotesListResponse;

@Controller
@RequestMapping("/notes")
public class NotesController extends BaseController {


  @Resource
  NotesService notesService;

  /**
   * 笔记增加
   * 
   * @param BaseRequest pojo
   * @return
   * @throws Exception
   */
  @RequestMapping("/notes_add")
  @ResponseBody
  public String notesAdd(NotesAddRequest request) throws Exception {
    BaseResponse reponse = notesService.addNotes(request);
    return FastJsonUtil.toJson(reponse);
  }

  /**
   * 笔记删除
   * 
   * @param BaseRequest pojo
   * @return
   * @throws Exception
   */
  @RequestMapping("/notes_del")
  @ResponseBody
  public String notesdel(NotesDelRequest request) throws Exception {
    BaseResponse reponse = notesService.delNotes(request);
    return FastJsonUtil.toJson(reponse);
  }

  /**
   * 笔记查询
   * 
   * @param BaseRequest pojo
   * @return
   * @throws Exception
   */
  @RequestMapping("/notes_list")
  @ResponseBody
  public String notesList(NotesListRequest request) throws Exception {
    NotesListResponse reponse = notesService.getNotesList(request);
    return FastJsonUtil.toJson(reponse);
  }



}
