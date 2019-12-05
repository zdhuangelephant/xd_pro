package com.xiaodou.resources.service.forum;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xiaodou.resources.model.forum.SharePagePvModel;
import com.xiaodou.resources.request.forum.StatisticsSharePvRequest;
import com.xiaodou.resources.response.forum.StatisticsSharePvResponse;
import com.xiaodou.resources.service.forum.facade.ForumServiceFacade;
import com.xiaodou.summer.vo.out.ResultType;

@Service("sharePagePvService")
public class SharePagePvService {
  @Resource
  ForumServiceFacade forumServiceFacade;

  public StatisticsSharePvResponse statisticsSharePv(StatisticsSharePvRequest pojo) {
    StatisticsSharePvResponse response = new StatisticsSharePvResponse(ResultType.SUCCESS);
    Long resourceId = Long.valueOf(pojo.getResourceId());
    SharePagePvModel sharePagePvModel = forumServiceFacade.querySharePagePvByResourceId(resourceId);
    if (null == sharePagePvModel) {
      sharePagePvModel = new SharePagePvModel();
      sharePagePvModel.setResourceId(resourceId);
      sharePagePvModel.setPv(1l);
      forumServiceFacade.insertSharePagePv(sharePagePvModel);
    } else {
      forumServiceFacade.updateSharePagePvByResourceId(resourceId, sharePagePvModel.getPv() + 1);
    }
    return response;
  }
}
