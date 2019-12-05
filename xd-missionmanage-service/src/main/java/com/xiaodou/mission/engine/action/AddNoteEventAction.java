package com.xiaodou.mission.engine.action;

import java.util.Map;

import com.xiaodou.mission.engine.event.AddNoteEvent;
import com.xiaodou.mission.engine.model.UserCollectDataInstance;

/**
 * @name AddNoteEventAction CopyRright (c) 2017 by zhaodan
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年2月5日
 * @description 添加笔记事件动作
 * @version 1.0
 */
public class AddNoteEventAction extends AbstractAction<AddNoteEvent> {

  @Override
  public void processCoreParam(AddNoteEvent event, UserCollectDataInstance coreParam) {
    Integer notesCount = coreParam.getNotesCount();
    coreParam.setNotesCount(++notesCount);
  }

  @Override
  public void processOtherParam(AddNoteEvent event, Map<String, Object> otherParam) {}

  @Override
  public void processShareParam(AddNoteEvent event, Map<String, Object> shareParam) {}

}
