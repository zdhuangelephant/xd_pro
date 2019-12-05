package com.xiaodou.webfetchservice;

import java.util.UUID;

import com.google.common.collect.Lists;
import com.xiaodou.webfetch.engine.FlowExcutor;
import com.xiaodou.webfetch.enums.WebFetchEnums.ActionType;
import com.xiaodou.webfetch.enums.WebFetchEnums.HttpApiType;
import com.xiaodou.webfetch.model.FetchTacticModel;
import com.xiaodou.webfetch.model.FetchTaskModel;
import com.xiaodou.webfetch.model.FetchTaskModel.FetchActionModel;

public class FlowTest {
  public static void main(String[] args) {
    
    String searcherSeq = "#ProfileMain";
    
    FetchTacticModel taskGroup = new FetchTacticModel();
    taskGroup.setApiType(HttpApiType.Jsoup.name());
    taskGroup.setStartActionId(UUID.randomUUID().toString());

    FetchTaskModel list = new FetchTaskModel();
    list.setId(UUID.randomUUID().toString());
    list.setUrl("https://zhuanlan.zhihu.com/zuxian");
//    list.setUrl("https://www.zhihu.com/people/shi-tou-yuan-32/posts");
    list.setDependActionId(taskGroup.getStartActionId());
    list.setStartActionId(UUID.randomUUID().toString());
    list.setTargetFieldId(UUID.randomUUID().toString());

    FetchActionModel gotoA = new FetchActionModel();
    gotoA.setNeedLoop(true);
    gotoA.setInciseRule(searcherSeq);
    gotoA.setDependActionId(list.getStartActionId());
    gotoA.setId(UUID.randomUUID().toString());
    gotoA.setActionType(ActionType.Goto.name());
    gotoA.setSupporterFieldId(list.getTargetFieldId());
    
    FetchTaskModel detail = new FetchTaskModel();
    detail.setId(UUID.randomUUID().toString());
    detail.setDependActionId(gotoA.getId());
    detail.setStartActionId(UUID.randomUUID().toString());
    detail.setTargetFieldId(UUID.randomUUID().toString());
    
    FetchActionModel collect = new FetchActionModel();
    collect.setInciseRule("inciseRule");
    collect.setDependActionId(detail.getStartActionId());
    collect.setId(UUID.randomUUID().toString());
    collect.setActionType(ActionType.Collect.name());
    collect.setSupporterFieldId(detail.getTargetFieldId());
    
    list.getActionList().add(gotoA);
    detail.getActionList().add(collect);
    taskGroup.setTaskList(Lists.newArrayList(list, detail));
    FlowExcutor.start(taskGroup);
  }
}
