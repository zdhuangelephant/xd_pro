package com.xiaodou.ms.web.controller.knowledge;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.xiaodou.common.util.Base64Utils;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.ms.model.knowledge.AuthorModel;
import com.xiaodou.ms.service.knowledge.AuthorService;
import com.xiaodou.ms.web.controller.BaseController;
import com.xiaodou.ms.web.request.knowledge.AuthorAddRequest;
import com.xiaodou.ms.web.request.knowledge.AuthorEditRequest;
import com.xiaodou.ms.web.response.BaseResponse;
import com.xiaodou.ms.web.response.ResultType;
import com.xiaodou.ms.web.response.selftaught.SearchAuthor4ForumResponse;

/**
 * @name AuthorController CopyRright (c) 2017 by zhaodan
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年2月6日
 * @description 知识分享 作者controller
 * @version 1.0
 */
@Controller("authorController")
@RequestMapping("/knowledge/author")
public class AuthorController extends BaseController {

  @Resource
  AuthorService authorService;

  /**
   * 作者列表
   * 
   * @return
   */
  @RequestMapping("/list")
  public ModelAndView authorList(String authorName) {
    if (StringUtils.isNotBlank(authorName)) {
      try {
        authorName = URLDecoder.decode(new String(Base64Utils.decode(authorName)), "utf8");
      } catch (UnsupportedEncodingException e) {
        authorName = StringUtils.EMPTY;
      }
    }
    ModelAndView modelAndView = new ModelAndView("knowledge/authorList");
    List<AuthorModel> authorList = authorService.search(authorName);
    modelAndView.addObject("authorName", authorName);
    modelAndView.addObject("authorList", authorList);
    return modelAndView;
  }

  /**
   * 作者列表
   * 
   * @return
   */
  @RequestMapping("/search4Forum")
  @ResponseBody
  public String search4Forum(String authorName) {
    SearchAuthor4ForumResponse res = new SearchAuthor4ForumResponse(ResultType.SUCCESS);
    List<AuthorModel> authorList = authorService.search(authorName);
    if (null == authorList || authorList.size() == 0) return FastJsonUtil.toJson(res);
    for (AuthorModel author : authorList)
      res.getAuthorList().add(
          String.format("<p value=\"%s\">%s</p>", author.getId(), author.getName()));
    return FastJsonUtil.toJson(res);
  }

  /**
   * 添加作者页面
   * 
   * @return
   */
  @RequestMapping("/add")
  public ModelAndView add() {
    ModelAndView modelAndView = new ModelAndView("knowledge/authorAdd");
    return modelAndView;
  }

  @RequestMapping("/doAdd")
  public ModelAndView doAdd(AuthorAddRequest request) {
    try {
      BaseResponse response = null;
      Errors errors = request.validate();
      if (errors.hasErrors()) {
        response = new BaseResponse(ResultType.VALID_FAIL);
        response.setRetDesc(this.getErrMsg(errors));
      } else {
        response = authorService.doAdd(request);
      }
      if (response.getRetCode() == 0) {
        return this.showMessage("添加成功", "", null, true);
      } else {
        return this.showMessage("添加失败", response.getRetDesc(), null, true);
      }
    } catch (Exception e) {
      throw e;
    }
  }

  /**
   * 修改作者页面
   * 
   * @return
   */
  @RequestMapping("/edit")
  public ModelAndView edit(Long id) {
    ModelAndView modelAndView = new ModelAndView("knowledge/authorEdit");
    modelAndView.addObject("author", authorService.show(id).getAuthor());
    return modelAndView;
  }

  @RequestMapping("/doEdit")
  public ModelAndView doEdit(AuthorEditRequest request) {
    try {
      BaseResponse response = null;
      Errors errors = request.validate();
      if (errors.hasErrors()) {
        response = new BaseResponse(ResultType.VALID_FAIL);
        response.setRetDesc(this.getErrMsg(errors));
      } else {
        response = authorService.doEdit(request);
      }
      if (response.getRetCode() == 0) {
        return this.showMessage("编辑成功", "", "/knowledge/author/edit?id=" + request.getId(), true);
      } else {
        return this.showMessage("编辑失败", response.getRetDesc(), "/knowledge/author/edit?id="
            + request.getId(), true);
      }
    } catch (Exception e) {
      throw e;
    }
  }

  @RequestMapping("/delete")
  @ResponseBody
  public String delete(Long id) {
    return FastJsonUtil.toJson(authorService.delete(id));
  }

}
