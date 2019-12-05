package com.xiaodou.webfetch.web.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xiaodou.common.util.StringUtils;
import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.summer.vo.out.ResultInfo;
import com.xiaodou.summer.vo.out.ResultType;
import com.xiaodou.summer.web.BaseController;
import com.xiaodou.webfetch.web.constants.Consts;
import com.xiaodou.webfetch.web.domain.ForumAccount;
import com.xiaodou.webfetch.web.service.ColumnService;
import com.xiaodou.webfetch.web.service.ForumAccountService;

/**
 * @name ZhiHuController CopyRright (c) 2017 by
 *       <a href="mailto:huangzedong@corp.51xiaodou.com">zdhuang</a>
 *
 * @date 2017年12月28日
 * @description TODO
 * @version 1.0
 */
@Controller("zhiHuController")
@RequestMapping("zhiHu")
public class ZhiHuController extends BaseController {

  @Resource
  ColumnService columnService;

  @Resource
  ForumAccountService forumAccountService;

  /**
   * 会去任务表内进行知乎的抓取
   * 
   * @param signature
   * @return updateTime: 2018年1月29日 下午4:18:02
   */
  @RequestMapping("executeBatch")
  @ResponseBody
  public String executeBatch() {

    Page<ForumAccount> pageList = forumAccountService.findEntityListByCond(null,null,(short)0);
    if (null != pageList) {
      for (ForumAccount account : pageList.getResult()) {
        if (StringUtils.isBlank(account.getSignature()))
          return new ResultInfo(ResultType.VALFAIL).toString0();
        try {
          columnService.save(account.getSignature(), Consts.DEFAULT_PAGENO, Consts.DEFAULT_PAGSIZE);
          ForumAccount fa = new ForumAccount();
          fa.setState((short)1);
          fa.setId(account.getId());
          forumAccountService.updateByEntityById(fa); // 更改状态
        } catch (Exception e) {
          return new ResultInfo(ResultType.SYSFAIL).toString0();
        }
      }
      return new ResultInfo(ResultType.SUCCESS).toString0();
    }
    return new ResultInfo(ResultType.VALFAIL).toString0();
  }

  /**
   * 访问该接口可以直接进行数据的抓取
   * 
   * @param signature
   * @return updateTime: 2018年1月29日 下午4:17:29
   */
//  @RequestMapping("execute")
//  @ResponseBody
//  public String execute(String signature) {
//    if (StringUtils.isBlank(signature)) return new ResultInfo(ResultType.VALFAIL).toString0();
//    try {
//      columnService.save(signature, Consts.DEFAULT_PAGENO, Consts.DEFAULT_PAGSIZE);
//      Page<ForumAccount> accounts = forumAccountService.findEntityListByCond(signature,
//          Platform.COLUMN_ZHIHU.getCode().toString(), (short)0);
//      if (accounts != null && accounts.getResult().size() != 0) {
//        return new ResultInfo(ResultType.SYSFAIL).toString0();
//      }
//      forumAccountService.save(signature);
//      return new ResultInfo(ResultType.SUCCESS).toString0();
//    } catch (Exception e) {
//      return new ResultInfo(ResultType.SYSFAIL).toString0();
//    }
//  }


  /**
   * 定时任务清洗知乎专栏的数据到xd_resource_list
   */
  @RequestMapping("/clean")
  @ResponseBody
  public String cleanColumnOfZHIHU() {
    columnService.initAuthor();
    columnService.clean();
    return new ResultInfo(ResultType.SUCCESS).toString0();
  }

}
