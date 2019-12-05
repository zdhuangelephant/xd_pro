package com.xiaodou.resources.web.batch.quesbk;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xiaodou.resources.request.quesbk.QuesBatchProPojo;
import com.xiaodou.resources.service.quesbk.QuesBatchProService;
import com.xiaodou.summer.vo.out.ResultInfo;
import com.xiaodou.summer.web.BaseController;
import com.xiaodou.summer.web.ServiceHandler;

@Controller("quesBatchController")
@RequestMapping("/ques_batch")
public class QuesBatchController extends BaseController {

  @Resource
  QuesBatchProService quesBatchProService;

  @RequestMapping("/statistic_user_examinfo")
  @ResponseBody
  public String statisticUserExamInfo(@Qualifier QuesBatchProPojo pojo) throws Exception {
    return doMain(pojo, new ServiceHandler<QuesBatchProPojo>() {
      @SuppressWarnings("unchecked")
      @Override
      public ResultInfo doService(QuesBatchProPojo pojo) throws Exception {
        return quesBatchProService.batchPro(pojo);
      }
    });
  }

}
