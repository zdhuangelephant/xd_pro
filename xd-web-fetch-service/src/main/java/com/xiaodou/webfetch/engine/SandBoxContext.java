package com.xiaodou.webfetch.engine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.Header;
import org.jsoup.nodes.Element;

import com.alibaba.fastjson.TypeReference;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.webfetch.action.IncisePoint;
import com.xiaodou.webfetch.data.Data;
import com.xiaodou.webfetch.enums.WebFetchEnums;
import com.xiaodou.webfetch.enums.WebFetchEnums.ActionType;
import com.xiaodou.webfetch.enums.WebFetchEnums.HttpApiType;
import com.xiaodou.webfetch.model.FetchTacticModel;
import com.xiaodou.webfetch.model.FetchTaskModel;
import com.xiaodou.webfetch.model.FetchTaskModel.FetchActionModel;
import com.xiaodou.webfetch.param.DependActionGroup;
import com.xiaodou.webfetch.param.DependTaskGroup;
import com.xiaodou.webfetch.param.FetchTask;
import com.xiaodou.webfetch.unique.IUnique;
import com.xiaodou.webfetch.unique.Target;
import com.xiaodou.webfetch.unique.TargetHolder;

/**
 * @name @see com.xiaodou.webfetch.engine.SandBoxContext.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年12月7日
 * @description 沙盒环境变量
 * @version 1.0
 */
public class SandBoxContext implements IUnique {
  /** fetchPlatform 目标website */
  private String fetchPlatform;
  /** 不允许私自构造沙盒 */
  private SandBoxContext() {}

  public static SandBoxContext newInstance() {
    SandBoxContext context = null;
    do {
      context = renew();
    } while (context == null);
    return context;
  }

  public static SandBoxContext getSandBox(String sandBoxId) {
    return sandBoxMap.get(sandBoxId);
  }

  private static SandBoxContext renew() {
    SandBoxContext context = new SandBoxContext();
    if (sandBoxMap.containsKey(context.unique())) return null;
    sandBoxMap.put(context.unique(), context);
    return context;
  }

  /** sandBoxMap 沙盒环境集合 */
  private static Map<String, SandBoxContext> sandBoxMap = Maps.newConcurrentMap();

  private HttpApiType apiType;
  private Map<String, String> baseInfo = Maps.newHashMap();
  private List<Header> headerList = Lists.newArrayList();
  private List<Cookie> cookieList = Lists.newArrayList();
  private Target startAction;
  private TargetHolder<Data> dataHolder = new TargetHolder<>();
  private TargetHolder<DependTaskGroup> taskGroupHolder = new TargetHolder<>();
  private TargetHolder<DependActionGroup> actionGroupHolder = new TargetHolder<>();
  private Map<String, String> saveField = Maps.newHashMap();

  public HttpApiType getApiType() {
    return apiType;
  }

  public String getBaseInfo(String key) {
    return baseInfo.get(key);
  }

  public List<Header> getHeaderList() {
    return headerList;
  }

  public List<Cookie> getCookieList() {
    return cookieList;
  }

  public void saveField(String key, String value) {
    saveField.put(key, value);
  }

  public Target getStartAction() {
    return startAction;
  }

  public void registTargetElements(Target target, Element element) {
    dataHolder.push(new Data(target, element));
  }

  public Data getTargetData(Target target) {
    return dataHolder.get(target);
  }

  private void registTargetTaskGroup(DependTaskGroup taskGroup) {
    taskGroupHolder.push(taskGroup);
  }

  private synchronized void registTargetTask(FetchTask task) {
    if (null == task) {
      return;
    }
    DependTaskGroup taskGroup = getTargetTaskGroup(task.getDependAction());
    if (null == taskGroup) {
      taskGroup = new DependTaskGroup(task.getDependAction());
      registTargetTaskGroup(taskGroup);
    }
    taskGroup.pushTask(task);
  }

  private void registTargetActionGroup(DependActionGroup actionGroup) {
    actionGroupHolder.push(actionGroup);
  }

  private synchronized void registTargetAction(IncisePoint action) {
    if (null == action) {
      return;
    }
    DependActionGroup actionGroup = getTargetActionGroup(action.getDependAction());
    if (null == actionGroup) {
      actionGroup = new DependActionGroup(action.getDependAction());
      registTargetActionGroup(actionGroup);
    }
    actionGroup.pushAction(action);
  }

  public DependTaskGroup getTargetTaskGroup(Target target) {
    return taskGroupHolder.get(target);
  }

  public DependActionGroup getTargetActionGroup(Target target) {
    return actionGroupHolder.get(target);
  }

  /** 清理沙盒环境 */
  public void clear() {
    dataHolder.clear();
    taskGroupHolder.clear();
  }

  /** 销毁沙盒环境 */
  public void destroy() {
    dataHolder.destroy();
    taskGroupHolder.destroy();
    sandBoxMap.remove(unique());
  }

  /** target 唯一可达目标 */
  private Target target = new Target(UUID.randomUUID().toString());

  @Override
  public String unique() {
    return target.unique();
  }

  public void init(FetchTacticModel model) {
    if (null == model) {
      init();
    }
    initApiType(model.getApiType());
    initDefaultParam(model);
    initTaskGroup(model);
  }

  private void init() {
    initApiType(null);
  }

  private void initApiType(String apiType) {
    if (StringUtils.isBlank(apiType)) {
      this.apiType = HttpApiType.Jsoup;
    } else {
      this.apiType = WebFetchEnums.getEnumValue(HttpApiType.class, apiType);
    }
  }

  @SuppressWarnings("unchecked")
  private void initDefaultParam(FetchTacticModel model) {
    if (StringUtils.isJsonNotBlank(model.getBaseInfo())) {
      HashMap<String, String> _baseInfo = FastJsonUtil.fromJson(model.getBaseInfo(), HashMap.class);
      this.baseInfo.putAll(_baseInfo);
    }
    if (StringUtils.isJsonNotBlank(model.getHeaderList())) {
      this.headerList =
          FastJsonUtil.fromJsons(model.getHeaderList(), new TypeReference<List<Header>>() {});
    }
    if (StringUtils.isJsonNotBlank(model.getCookieList())) {
      this.cookieList =
          FastJsonUtil.fromJsons(model.getCookieList(), new TypeReference<List<Cookie>>() {});
    }
  }

  private void initTaskGroup(FetchTacticModel model) {
    // modify by zdhuang
    if(null != model.getStartActionId()) {
      this.startAction = new Target(model.getStartActionId());
    }
    
    if (StringUtils.isJsonNotBlank(model.getTaskList())) {
      List<FetchTaskModel> taskModelList =
          FastJsonUtil.fromJsons(model.getTaskList(), new TypeReference<List<FetchTaskModel>>() {});
      for (FetchTaskModel taskModel : taskModelList) {
        for (FetchActionModel actionModel : taskModel.getActionList()) {
          registTargetAction(ActionType.buildAction(actionModel));
        }
        registTargetTask(new FetchTask(taskModel));
      }
    }
  }

  public String getFetchPlatform() {
    return fetchPlatform;
  }
  public void setFetchPlatform(String fetchPlatform) {
    this.fetchPlatform = fetchPlatform;
  }
  
}
