package com.xiaodou.oms.statemachine.engine;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Hashtable;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.google.common.collect.Maps;
import com.xiaodou.common.util.FileUtil;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.oms.exception.ExceptionMessageProp;
import com.xiaodou.oms.statemachine.engine.document.DefaultDocumentLoader;
import com.xiaodou.oms.statemachine.engine.document.DocumentLoader;
import com.xiaodou.oms.statemachine.engine.model.APILib;
import com.xiaodou.oms.statemachine.engine.model.OmsOrder;
import com.xiaodou.oms.statemachine.engine.model.ParameterTemplate;
import com.xiaodou.oms.statemachine.engine.model.ProductLine;
import com.xiaodou.oms.statemachine.engine.model.Template;
import com.xiaodou.oms.statemachine.engine.model.api.LocalAPI;
import com.xiaodou.oms.statemachine.engine.model.api.RemoteAPI;
import com.xiaodou.oms.util.EngineUtil;
import com.xiaodou.summer.view.velocity.TemplateUtil;

/**
 * <p>
 * API容器
 * </p>
 * 所有注册的API在这做统一的管理
 * 
 * @author <a href="mailto:dan.zhao@elong.corp.com">zhaodan</a>
 * @version 1.0
 * @date 2014年6月18日
 */
public class APIContext {

  private static String regex = "\\$\\{(.+?)\\}";

  private static Pattern pattern = Pattern.compile(regex);

  private static Hashtable<Object, Object> params = new Hashtable<Object, Object>();

  private static String path = null;

  private static String IMPORT = "Import";

  private static String PRODUCTLINE = "ProductLine";

  private static String OMSORDER = "OmsOrder";

  private static String PARAMETERTEMPLATE = "ParameterTemplate";

  private static String APIS = "Apis";

  private static String TEMPLATE = "Template";

  private static APILib apiLib;

  private static DocumentLoader loader = new DefaultDocumentLoader();

  public static APILib getApiLib() {
    return apiLib;
  }

  /**
   * 構造方法
   * 
   * @param apiLibPath 配置文件路徑
   */
  public APIContext(String apiLibPath) throws Exception {
    try {
      path = APIContext.class.getClassLoader().getResource(apiLibPath).getPath();
      Document doc = loader.loadDocument(path);
      Element root = doc.getDocumentElement();
      apiLib = new APILib();
      registApiLib(root);
    } catch (Exception e) {
      LoggerUtil.error(
          ExceptionMessageProp.getErrMessage("error.doc.loaddoc.productline.loadfail"), e);
      throw e;
    }
  }

  public static void init(String apiLibPath) throws Exception {
    try {
      path = APIContext.class.getClassLoader().getResource(apiLibPath).getPath();
      Document doc = loader.loadDocument(path);
      Element root = doc.getDocumentElement();
      apiLib = new APILib();
      registApiLib(root);
    } catch (Exception e) {
      LoggerUtil.error(
          ExceptionMessageProp.getErrMessage("error.doc.loaddoc.productline.loadfail"), e);
      throw e;
    }
  }

  /**
   * 注册APILib
   * 
   * @param root 根节点
   * @throws InstantiationException
   * @throws IllegalAccessException
   * @throws SecurityException
   * @throws NoSuchMethodException
   * @throws InvocationTargetException
   * @throws IllegalArgumentException
   */
  private static void registApiLib(Element root) throws InstantiationException,
      IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException,
      InvocationTargetException {

    // 注册ParameterTemplate
    NodeList parameterTemplateList = root.getElementsByTagName(PARAMETERTEMPLATE);
    if (null == parameterTemplateList || parameterTemplateList.getLength() != 1)
      throw new RuntimeException(
          ExceptionMessageProp.getErrMessage("error.doc.loaddoc.parameterTemplate.illegal"));
    Element parameterTemplate = (Element) parameterTemplateList.item(0);
    registParameterTemplate(parameterTemplate);

    // 注册Import_Property
    NodeList importList = root.getElementsByTagName(IMPORT);
    if (null != importList && importList.getLength() > 0) {
      registImportList(importList);
    }

    // 注册ProductLine
    NodeList productLineList = root.getElementsByTagName(PRODUCTLINE);
    if (null != productLineList && productLineList.getLength() > 0) {
      registProductLineList(productLineList);
    }

    // 注册OmsOrder
    NodeList omsOrderList = root.getElementsByTagName(OMSORDER);
    if (null == omsOrderList || omsOrderList.getLength() != 1)
      throw new RuntimeException(
          ExceptionMessageProp.getErrMessage("error.doc.loaddoc.parameterTemplate.illegal"));
    Element omsOrder = (Element) omsOrderList.item(0);
    registOmsOrder(omsOrder);

  }

  /**
   * 注册Import Property
   * 
   * @param importList 引入文件
   */
  private static void registImportList(NodeList importList) {
    for (int i = 0; i < importList.getLength(); i++) {
      Element importNode = (Element) importList.item(i);
      registImport(importNode);
    }
  }

  /**
   * 注册Import Property
   * 
   * @param importNode 引入文件
   */
  private static void registImport(Element importNode) {
    String errCode = "error.doc.loaddoc.importproperty.misattr";
    String location =
        EngineUtil.validateNotBlank(importNode.getAttribute("location"), errCode, "location");
    params.putAll(FileUtil.getInstance(location).getPropertyFile());
  }

  /**
   * 注册OmsOrder
   * 
   * @param omsOrderNode OmsOrder节点
   * @throws InstantiationException
   * @throws IllegalAccessException
   * @throws SecurityException
   * @throws NoSuchMethodException
   * @throws InvocationTargetException
   * @throws IllegalArgumentException
   */
  @SuppressWarnings("unchecked")
  private static void registOmsOrder(Element omsOrderNode) throws InstantiationException,
      IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException,
      InvocationTargetException {
    OmsOrder omsOrder = new OmsOrder();

    NodeList apis = omsOrderNode.getElementsByTagName(APIS);
    if (null != apis && apis.getLength() > 0) {
      omsOrder.setApis(registApis(apis, LocalAPI.class));
    }

    // 注册OmsOrder
    apiLib.setOmsOrder(omsOrder);

  }

  /**
   * 注册参数模板
   * 
   * @param parameterTemplateNode 参数模板节点
   */
  private static void registParameterTemplate(Element parameterTemplateNode) {
    String errCode = "error.doc.loaddoc.parameterTemplate.misattr";
    String basePath =
        EngineUtil.validateNotBlank(parameterTemplateNode.getAttribute("basePath"), errCode,
            "basePath");

    ParameterTemplate parameterTemplate = new ParameterTemplate();
    parameterTemplate.setBasePath(basePath);

    // 注册Template
    NodeList templateList = parameterTemplateNode.getElementsByTagName(TEMPLATE);
    if (null == templateList || templateList.getLength() != 2)
      throw new RuntimeException(
          ExceptionMessageProp.getErrMessage("error.doc.loaddoc.template.illegal"));
    registTemplateList(templateList, parameterTemplate);

    // 注册参数模板
    apiLib.setParameterTemplate(parameterTemplate);
  }

  /**
   * 注册模板列表
   * 
   * @param templateList 模板列表
   * @param parameterTemplate 所属参数模板
   */
  private static void registTemplateList(NodeList templateList, ParameterTemplate parameterTemplate) {
    TemplateUtil templateUtil = new TemplateUtil(parameterTemplate.getBasePath());

    for (int i = 0; i < templateList.getLength(); i++) {
      Element template = (Element) templateList.item(i);
      registTemplate(template, templateUtil, parameterTemplate);
    }
  }

  /**
   * 注册模板
   * 
   * @param templateNode 模板节点
   * @param templateUtil 模板解析util
   * @param parameterTemplate 参数模板
   */
  private static void registTemplate(Element templateNode, TemplateUtil templateUtil,
      ParameterTemplate parameterTemplate) {
    // 属性验证
    String errCode = "error.doc.loaddoc.template.misattr";
    String name = EngineUtil.validateNotBlank(templateNode.getAttribute("name"), errCode, "name");
    String file = EngineUtil.validateNotBlank(templateNode.getAttribute("file"), errCode, "file");
    String pojo = EngineUtil.validateNotBlank(templateNode.getAttribute("pojo"), errCode, "pojo");

    if (parameterTemplate.hasTemplate(name))
      throw new RuntimeException(ExceptionMessageProp.getErrMessage(
          "error.doc.loaddoc.template.same", name));

    // 构建模板
    Template template = new Template();
    template.setName(name);
    template.setFile(file);
    template.setPojo(pojo);
    template.initTemplate(templateUtil);

    // 注册模板
    parameterTemplate.putTemplate(name, template);
  }

  /**
   * 注册产品线List
   * 
   * @param productLineList 产品线List
   * @throws InstantiationException
   * @throws IllegalAccessException
   * @throws SecurityException
   * @throws NoSuchMethodException
   * @throws InvocationTargetException
   * @throws IllegalArgumentException
   */
  private static void registProductLineList(NodeList productLineList)
      throws InstantiationException, IllegalAccessException, NoSuchMethodException,
      SecurityException, IllegalArgumentException, InvocationTargetException {
    for (int i = 0; i < productLineList.getLength(); i++) {
      Element productLineNode = (Element) productLineList.item(i);
      registProductLine(productLineNode);
    }

  }

  /**
   * 注册产品线
   * 
   * @param productLineNode 产品线节点
   * @throws InstantiationException
   * @throws IllegalAccessException
   * @throws SecurityException
   * @throws NoSuchMethodException
   * @throws InvocationTargetException
   * @throws IllegalArgumentException
   */
  @SuppressWarnings("unchecked")
  private static void registProductLine(Element productLineNode) throws InstantiationException,
      IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException,
      InvocationTargetException {
    String errCode = "error.doc.loaddoc.productline.misattr";
    String name =
        EngineUtil.validateNotBlank(productLineNode.getAttribute("name"), errCode, "name");
    String code =
        EngineUtil.validateNotBlank(productLineNode.getAttribute("code"), errCode, "code");

    if (apiLib.hasProductLine(code))
      throw new RuntimeException(ExceptionMessageProp.getErrMessage(
          "error.doc.loaddoc.productline.same", name));

    ProductLine productLine = new ProductLine();
    productLine.setName(name);
    productLine.setCode(code);

    NodeList apis = productLineNode.getElementsByTagName(APIS);
    if (null != apis && apis.getLength() > 0) {
      productLine.setApis(registApis(apis, RemoteAPI.class));
    }

    apiLib.registeProductLine(code, productLine);
  }

  /**
   * 注册API列表
   * 
   * @param apisList API列表
   * @param apiType API类型
   * @throws InstantiationException
   * @throws IllegalAccessException
   * @throws SecurityException
   * @throws NoSuchMethodException
   * @throws InvocationTargetException
   * @throws IllegalArgumentException
   */
  @SuppressWarnings("rawtypes")
  private static <T> Map registApis(NodeList apisList, Class<T> apiType)
      throws InstantiationException, IllegalAccessException, NoSuchMethodException,
      SecurityException, IllegalArgumentException, InvocationTargetException {
    String tagName = apiType.getSimpleName();
    Method methodTemp = APIContext.class.getDeclaredMethod("regist" + tagName, Element.class, Map.class);
    Map<String, T> apisLib = Maps.newHashMap();
    for (int i = 0; i < apisList.getLength(); i++) {
      Element apis = (Element) apisList.item(i);
      NodeList apiList = apis.getElementsByTagName(apiType.getSimpleName());
      for (int j = 0; j < apiList.getLength(); j++) {
        Element api = (Element) apiList.item(j);
        methodTemp.invoke(null, api, apisLib);
      }
    }
    return apisLib;
  }

  /**
   * @param api
   * @param apisLib
   */
  @SuppressWarnings({"rawtypes", "unchecked", "unused"})
  private static void registLocalAPI(Element api, Map apisLib) {
    String errCode = "error.doc.loaddoc.localapi.misattr";
    String name = EngineUtil.validateNotBlank(api.getAttribute("name"), errCode, "name");
    String className =
        EngineUtil.validateNotBlank(api.getAttribute("className"), errCode, "className");
    String beanId = EngineUtil.validateNotBlank(api.getAttribute("beanId"), errCode, "beanId");
    String method = EngineUtil.validateNotBlank(api.getAttribute("method"), errCode, "method");
    String returnValueName = api.getAttribute("returnValueName");
    if (StringUtils.isBlank(returnValueName)) returnValueName = name + "ReturnVlue";

    if (apisLib.containsKey(name))
      throw new RuntimeException(ExceptionMessageProp.getErrMessage(
          "error.doc.loaddoc.localapi.same", name));

    LocalAPI localApi = new LocalAPI();
    localApi.setName(name);
    localApi.setClassName(className);
    localApi.setBeanId(beanId);
    localApi.setMethod(method);
    localApi.setReturnValueName(returnValueName);

    // 添加到API库
    apisLib.put(name, localApi);

  }


  /**
   * 注册RemoteAPI
   * 
   * @param api API节点
   * @param apisLib API库
   * @throws InstantiationException
   * @throws IllegalAccessException
   */
  @SuppressWarnings({"rawtypes", "unchecked", "unused"})
  private static void registRemoteAPI(Element api, Map apisLib) throws InstantiationException,
      IllegalAccessException {
    String errCode = "error.doc.loaddoc.remoteapi.misattr";
    String name = EngineUtil.validateNotBlank(api.getAttribute("name"), errCode, "name");
    String protocol =
        EngineUtil.validateNotBlank(api.getAttribute("protocol"), errCode, "protocol");
    String parameterTemplate =
        EngineUtil.validateNotBlank(api.getAttribute("parameterTemplate"), errCode,
            "parameterTemplate");
    String url = EngineUtil.validateNotBlank(getAttribute(api, "url"), errCode, "url");
    String returnValueName = api.getAttribute("returnValueName");
    if (StringUtils.isBlank(returnValueName)) returnValueName = name + "ReturnVlue";

    if (apisLib.containsKey(name))
      throw new RuntimeException(ExceptionMessageProp.getErrMessage(
          "error.doc.loaddoc.remoteapi.same", name));

    RemoteAPI remoteApi = new RemoteAPI();
    remoteApi.setName(name);
    remoteApi.setProtocol(protocol);
    remoteApi.setReturnValueName(returnValueName);
    remoteApi.setUrl(url);
    remoteApi.setTemplate(apiLib.getParameterTemplate().getTemplate(parameterTemplate));
    // 添加到API库
    apisLib.put(name, remoteApi);

  }

  /**
   * 从Import Property文件中读取属性
   * 
   * @param node 所属节点
   * @param attrName 属性名
   * @return 属性值
   */
  private static String getAttribute(Element node, String attrName) {
    String value = node.getAttribute(attrName);
    if (StringUtils.isBlank(value)) return null;
    Matcher matcher = pattern.matcher(value);
    if (!matcher.matches()) return value;
    String group = matcher.group();
    return getProperty(group);
  }

  private static String getProperty(String regex) {
    return params.get(regex.subSequence(2, regex.length() - 1)).toString();
  }

}
