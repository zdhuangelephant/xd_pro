package com.xiaodou.oms.statemachine.engine;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.oms.exception.ExceptionMessageProp;
import com.xiaodou.oms.statemachine.engine.document.DefaultDocumentLoader;
import com.xiaodou.oms.statemachine.engine.document.DocumentLoader;
import com.xiaodou.oms.statemachine.engine.instance.StateMachineConf;
import com.xiaodou.oms.statemachine.engine.instance.StateMachineProductLineConf;
import com.xiaodou.oms.statemachine.engine.instance.TransitionConf;
import com.xiaodou.oms.statemachine.engine.model.api.BaseAPI;
import com.xiaodou.oms.statemachine.engine.model.api.InputParam;
import com.xiaodou.oms.statemachine.engine.model.api.LocalAPI;
import com.xiaodou.oms.statemachine.engine.model.api.RemoteAPI;
import com.xiaodou.oms.statemachine.engine.model.api.proxy.IApiProxy;
import com.xiaodou.oms.statemachine.engine.model.api.proxy.LocalApiBase;
import com.xiaodou.oms.util.ApiProxyUtil;
import com.xiaodou.oms.util.EngineUtil;

public class StateMachineContext {

  private static String STATEMACHINEINSTANCE = "StateMachineInstance";

  private static String STATEINSTANCE = "StateInstance";

  private static String EVENTINSTANCE = "EventInstance";

  private static String TRANSITIONLIST = "TransitionList";

  private static String TRANSITION = "Transition";

  private static String PRECHECK = "PreCheck";

  private static String DO = "Do";

  private static String RECORDMESSAGE = "RecordMessage";

  private static String MESSAGE = "Message";

  private static String API = "Api";

  private static String INPUTPARAM = "InputParam";

  private static StateMachineConf conf;

  private static DocumentLoader loader = new DefaultDocumentLoader();

  private static String path;

  public static StateMachineConf getConf() {
    return conf;
  }

  /**
   * 构造方法
   * 
   * @param confPath 配置文件路径
   * @throws Exception 异常
   */
  public StateMachineContext(String confPath) throws Exception {
    try {
      path = this.getClass().getClassLoader().getResource(confPath).getPath();
      conf = new StateMachineConf();
      registContext(new File(path));
    } catch (Exception e) {
      LoggerUtil.error(
          ExceptionMessageProp.getErrMessage("error.doc.loaddoc.statemachineinstance.loadfail"), e);
      throw e;
    }
  }

  /**
   * 初始化方法
   * 
   * @param confPath 配置文件路径
   * @throws Exception 异常
   */
  public static void init(String confPath) throws Exception {
    try {
      path = StateMachineContext.class.getClassLoader().getResource(confPath).getPath();
      conf = new StateMachineConf();
      registContext(new File(path));
    } catch (Exception e) {
      LoggerUtil.error(
          ExceptionMessageProp.getErrMessage("error.doc.loaddoc.statemachineinstance.loadfail"), e);
      throw e;
    }
  }

  /**
   * 遍历路径下配置文件注册
   * 
   * @param confFile 配置文件目录
   * @throws Exception 异常
   */
  private static void registContext(File confFile) throws Exception {
    if (!confFile.isAbsolute()) throw new RuntimeException();
    if (confFile.isDirectory()) {
      for (File file : confFile.listFiles())
        registContext(file);
    }
    if (confFile.isFile()) {
      Document doc = loader.loadDocument(confFile);
      Element root = doc.getDocumentElement();
      registStateMachineConf(root);
    }
  }

  private static void registStateMachineConf(Element root) throws ClassNotFoundException {
    String errCode = "error.doc.loaddoc.statemachineinstance.misattr";
    String name = EngineUtil.validateNotBlank(root.getAttribute("name"), errCode, "name");
    String code = EngineUtil.validateNotBlank(root.getAttribute("code"), errCode, "code");

    if (conf.hasProductLineConf(code))
      throw new RuntimeException(ExceptionMessageProp.getErrMessage(
          "error.doc.loaddoc.statemachineinstance.same", name));

    String stateMachineInstance = getNodeValue(root, STATEMACHINEINSTANCE);
    String stateInstance = getNodeValue(root, STATEINSTANCE);
    String eventInstance = getNodeValue(root, EVENTINSTANCE);

    NodeList transitionList = root.getElementsByTagName(TRANSITIONLIST);
    if (transitionList.getLength() == 0 || transitionList.getLength() > 1)
      throw new RuntimeException(ExceptionMessageProp.getErrMessage(
          "error.doc.loaddoc.statemachineinstance.illegal", TRANSITIONLIST));

    // 注册TransitionList
    Map<String, TransitionConf> trnsationMap =
        registTransitionList(code, (Element) transitionList.item(0));

    // 实例化StateMachineProductLineConf
    StateMachineProductLineConf productLineConf = new StateMachineProductLineConf();
    productLineConf.setName(name);
    productLineConf.setCode(code);
    productLineConf.setStateMachineInstance(stateMachineInstance);
    productLineConf.setStateInstance(stateInstance);
    productLineConf.setEventInstance(eventInstance);
    productLineConf.setTransitionConfMap(trnsationMap);

    // 注册StateMachineProductLineConf至Conf
    conf.setStateMachineProductLineConf(code, productLineConf);


  }

  /**
   * 注册TransationsList
   * 
   * @param code 产品线
   * @param transitionsList transitionsList
   * @return transitionMap
   * @throws ClassNotFoundException
   */
  private static Map<String, TransitionConf> registTransitionList(String code,
      Element transitionsList) throws ClassNotFoundException {
    NodeList transitionList = transitionsList.getElementsByTagName(TRANSITION);
    if (transitionList.getLength() == 0)
      throw new RuntimeException(ExceptionMessageProp.getErrMessage(
          "error.doc.loaddoc.statemachineinstance.misattr", TRANSITION));
    Map<String, TransitionConf> transitionMap = Maps.newHashMap();
    for (int i = 0; i < transitionList.getLength(); i++) {
      registTransation(code, (Element) transitionList.item(i), transitionMap);
    }
    return transitionMap;

  }

  /**
   * 注册Transation
   * 
   * @param code 产品线
   * @param transitionNode transition节点
   * @param transitionMap transitonMap
   * @throws ClassNotFoundException
   */
  private static void registTransation(String code, Element transitionNode,
      Map<String, TransitionConf> transitionMap) throws ClassNotFoundException {

    String name =
        EngineUtil.validateNotBlank(transitionNode.getAttribute("name"),
            "error.doc.loaddoc.statemachineinstance.misattr", "name");
    // 忽略DoList
    Boolean ignoreDo = StringUtils.isNotBlank(transitionNode.getAttribute("ignoreDo"));

    if (transitionMap.containsKey(name))
      throw new RuntimeException(ExceptionMessageProp.getErrMessage(
          "error.doc.loaddoc.statemachineinstance.same", "name"));

    TransitionConf transition = new TransitionConf();
    transition.setName(name);

    // 注册PreCheck
    NodeList preCheck = transitionNode.getElementsByTagName(PRECHECK);
    if (preCheck.getLength() > 0) {
      transition.setPreCheckList(registApis(code, (Element) preCheck.item(0)));
    }

    // 注册Do
    if(!ignoreDo){
      NodeList dO = transitionNode.getElementsByTagName(DO);
      if (dO.getLength() == 0 || dO.getLength() > 1)
        throw new RuntimeException(ExceptionMessageProp.getErrMessage(
          "error.doc.loaddoc.statemachineinstance.illegal", DO));
      transition.setDoList(registApis(code, (Element) dO.item(0)));
    }

    // 注册RecordMessage
    NodeList recordMessage = transitionNode.getElementsByTagName(RECORDMESSAGE);
    if (recordMessage.getLength() > 0) {
      transition.setRecordMessage(registApis(code, (Element) recordMessage.item(0)));
    }

    // 注册Message
    NodeList message = transitionNode.getElementsByTagName(MESSAGE);
    if (message.getLength() > 0) {
      transition.setMessage(registApis(code, (Element) message.item(0)));
    }

    // 注册Transition
    transitionMap.put(name, transition);

  }

  /**
   * 注册API节点列表
   * 
   * @param code 产品线
   * @param apiNodes api节点列表
   * @return api节点List
   * @throws ClassNotFoundException
   */
  private static List<IApiProxy> registApis(String code, Element apiNodes)
      throws ClassNotFoundException {
//    NodeList api = apiNodes.getElementsByTagName(API);
    NodeList api = apiNodes.getChildNodes();
    if (api.getLength() == 0)
      throw new RuntimeException(ExceptionMessageProp.getErrMessage(
          "error.doc.loaddoc.statemachineinstance.illegal", API));
    List<IApiProxy> apiList = Lists.newArrayList();
    for (int i = 0; i < api.getLength(); i++) {
      if(api.item(i).getNodeName().equals(API)){
        apiList.add(registApi(code, (Element) api.item(i)));
      }
    }
    return apiList;
  }

  /**
   * 注册Api节点
   * 
   * @param code 产品线
   * @param apiNode api节点
   * @return api节点
   * @throws ClassNotFoundException
   */
  private static IApiProxy registApi(String code, Element apiNode) throws ClassNotFoundException {

    String errCode = "error.doc.loaddoc.statemachineinstance.misattr";
    String name = EngineUtil.validateNotBlank(apiNode.getAttribute("name"), errCode, "name");
    String type = EngineUtil.validateNotBlank(apiNode.getAttribute("type"), errCode, "type");

    IApiProxy proxy = null;
    switch (type.toLowerCase()) {
      case "local":
        proxy = registlocalApi(apiNode, name);
        break;
      case "remote":
        proxy = registremoteApi(code, name);
        break;
      default:
        throw new RuntimeException(ExceptionMessageProp.getErrMessage(
            "error.doc.loaddoc.statemachineinstance.illegal", type));
    }

    String onCondition = apiNode.getAttribute("onCondition");
    if (StringUtils.isNotBlank(onCondition)) proxy.setOnCondition(onCondition);
    String onTimeOut = apiNode.getAttribute("onTimeOut");
    if (StringUtils.isNotBlank(onCondition)) proxy.setOnTimeOut(onTimeOut);
    String onException = apiNode.getAttribute("onException");
    if (StringUtils.isNotBlank(onException)) proxy.setOnException(onException);

    return proxy;
  }

  /**
   * 注册本地API
   */
  private static IApiProxy registlocalApi(Element apiNode, String name) {
    BaseAPI api = APIContext.getApiLib().getLocalApi(name);
    if (null == api)
      throw new RuntimeException(ExceptionMessageProp.getErrMessage(
          "error.doc.loaddoc.localapi.mis", name));

    IApiProxy proxy = new LocalApiBase((LocalAPI) api);

    // 初始化入参List
    NodeList inputParamList = apiNode.getElementsByTagName(INPUTPARAM);
    if (null != inputParamList && inputParamList.getLength() > 0) {
      for (int i = 0; i < inputParamList.getLength(); i++) {
        registInputParams((Element) inputParamList.item(i), (LocalApiBase) proxy);
      }
    }
    return proxy;
  }

  /**
   * 注册远程API
   */
  private static IApiProxy registremoteApi(String code, String name) {
    BaseAPI api = APIContext.getApiLib().getRemoteApi(code, name);
    if (null == api)
      throw new RuntimeException(ExceptionMessageProp.getErrMessage(
          "error.doc.loaddoc.remoteapi.mis", name));
    try {
      return ApiProxyUtil.getHttpProxy(((RemoteAPI) api));
    } catch (InstantiationException | IllegalAccessException | IllegalArgumentException
        | InvocationTargetException | NoSuchMethodException | SecurityException e) {
      throw new RuntimeException(ExceptionMessageProp.getErrMessage(
          "error.doc.loaddoc.statemachineinstance.illegal", "remote"));
    }
  }

  /**
   * 注册LocalAPI入参列表
   * 
   * @param inpitParam 入参
   * @param proxy 待注册LocalAPI
   */
  private static void registInputParams(Element inpitParam, LocalApiBase proxy) {
    String errCode = "error.doc.loaddoc.localapi.inputparam.misattr";
    String name = EngineUtil.validateNotBlank(inpitParam.getAttribute("name"), errCode, "name");
    String type = EngineUtil.validateNotBlank(inpitParam.getAttribute("type"), errCode, "type");

    InputParam param = new InputParam();
    param.setName(name);
    param.setType(type);
    String value = inpitParam.getAttribute("value");
    if (StringUtils.isNotBlank(value)) param.setValue(value);
    proxy.setParam(param);
  }

  /**
   * 获取节点值 - 用于获取属性类型节点域的值方法
   * 
   * @param root 根节点
   * @param field 值域
   * @return 域值
   */
  private static String getNodeValue(Element root, String field) {
    NodeList fieldValueList = root.getElementsByTagName(field);
    if (fieldValueList.getLength() == 0 || fieldValueList.getLength() > 1)
      throw new RuntimeException(ExceptionMessageProp.getErrMessage(
          "error.doc.loaddoc.statemachineinstance.illegal", field));
    String fieldValue =
        EngineUtil.validateNotBlank(fieldValueList.item(0).getFirstChild().getNodeValue(),
            "error.doc.loaddoc.statemachineinstance.misattr", field);
    return fieldValue;
  }


}
