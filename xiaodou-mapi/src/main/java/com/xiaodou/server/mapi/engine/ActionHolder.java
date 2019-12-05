package com.xiaodou.server.mapi.engine;

import java.util.Map;

import com.google.common.collect.Maps;
import com.xiaodou.server.mapi.engine.model.Action;

public class ActionHolder {

  private ActionHolder() {}

  private static final ActionHolder instance = new ActionHolder();

  public static ActionHolder getInstance() {
    return instance;
  }

  private Map<String, Action> actionMapper = Maps.newHashMap();

  public void registAction(Action action) {
    String key = action.getKey();
    if (actionMapper.containsKey(key)) {
      actionMapper.put(key, registAction0(actionMapper.get(key), action));
    } else {
      actionMapper.put(key, action);
    }
  }

  private Action registAction0(Action previous, Action current) {
    if (null == previous) return current;
    int result = current.compareTo(previous);
    if (result == 0) {
      throw new RuntimeException(ActionMessage.getErrMessage("error.doc.loaddoc.action.same"));
    } else if (result > 0) {
      current.setPrevious(previous);
      return current;
    } else {
      previous.setPrevious(registAction0(previous.getPrevious(), current));
      return previous;
    }
  }

  public Action getAction(String mpackage, String name, String version) {
    String key = ActionTool.getKey(mpackage, name);
    Action targetAction = actionMapper.get(key);
    if (null != targetAction) {
      Action action = targetAction.getSuitable(version);
//      int compareTo = action.getVersion().compareTo("1.5.0");
//      if(compareTo <0 ) {
//        ModuleHandle.getModuleHandle().setIsHasRegion(false);
//      }
      return action;
    }
    return null;
  }

}
