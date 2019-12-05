package com.xiaodou.server.mapi.engine;

import java.io.File;
import java.util.Hashtable;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.xiaodou.common.util.FileUtil;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.server.mapi.engine.loader.DefaultDocumentLoader;
import com.xiaodou.server.mapi.engine.loader.DocumentLoader;
import com.xiaodou.server.mapi.engine.model.Action;
import com.xiaodou.server.mapi.engine.model.Api;
import com.xiaodou.server.mapi.engine.proxy.AbstractApiProxy;

/**
 * @name @see com.xiaodou.server.mapi.engine.ActionParser.java
 * @CopyRright (c) 2016 by XiaoDou NetWork Technology
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年1月22日
 * @description Action分析器
 * @version 1.0
 */
public class ActionParser {

  private static String regex = "(^\\$\\{)*\\$\\{(.+?)\\}(^\\})*";

  private static Pattern pattern = Pattern.compile(regex);

  private static Hashtable<Object, Object> params = new Hashtable<Object, Object>();

  private static String path = null;

  private static String IMPORT = "Import";

  private static DocumentLoader loader = new DefaultDocumentLoader();

  private static final AtomicBoolean isInited = new AtomicBoolean(false);

  /**
   * 構造方法
   * 
   * @param apiLibPath 配置文件路徑
   */
  public ActionParser(String actionPath) throws Exception {
    try {
      if (isInited.compareAndSet(false, true)) {
        path = ActionParser.class.getClassLoader().getResource(actionPath).getPath();
        registContext(new File(path));
      }
    } catch (Exception e) {
      LoggerUtil.error(ActionMessage.getErrMessage("error.doc.loaddoc.actionlib.loadfail"), e);
      throw e;
    }
  }

  public static void init(String actionPath) throws Exception {
    try {
      if (isInited.compareAndSet(false, true)) {
        path = ActionParser.class.getClassLoader().getResource(actionPath).getPath();
        registContext(new File(path));
      }
    } catch (Exception e) {
      LoggerUtil.error(ActionMessage.getErrMessage("error.doc.loaddoc.actionlib.loadfail"), e);
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
      registActionHolder(root);
    }
  }

  /**
   * 注册APILib
   * 
   * @param root 根节点
   * @throws Exception
   */
  private static void registActionHolder(Element root) throws Exception {

    // 注册Import_Property
    NodeList importList = root.getElementsByTagName(IMPORT);
    if (null != importList && importList.getLength() > 0) {
      registImportList(importList);
    }

    // 注册Action
    NodeList actionList = root.getElementsByTagName(ActionEnum.Node.Action.toString());
    if (null != actionList && actionList.getLength() > 0) {
      for (int i = 0; i < actionList.getLength(); i++) {
        Element action = (Element) actionList.item(i);
        registAction(action);
      }
    }

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
        ActionTool.validateNotBlank(importNode.getAttribute("location"), errCode, "location");
    params.putAll(FileUtil.getInstance(location).getPropertyFile());
  }

  /**
   * 注册Action
   */
  private static void registAction(Element action) throws Exception {
    // 属性验证
    String errCode = "error.doc.loaddoc.action.misattr";
    // 必填屬性
    String mpackage =
        ActionTool.validateNotBlank(getAttribute(action, "package"), errCode, "package");
    String name = ActionTool.validateNotBlank(getAttribute(action, "name"), errCode, "name");
    String version =
        ActionTool.validateNotBlank(getAttribute(action, "version"), errCode, "version");
    // 可選屬性
    String special = getAttribute(action, "special");

    // 构建Action
    Action _action = new Action();
    _action.setMpackage(mpackage);
    _action.setName(name);
    _action.setVersion(version);
    if (StringUtils.isNotBlank(special)) _action.setSpecial(Boolean.valueOf(special));
    // 注册Action
    ActionHolder.getInstance().registAction(_action);

    // 注册API
    NodeList apiList = action.getElementsByTagName(ActionEnum.Node.Api.toString());
    if (null == apiList || apiList.getLength() > 1)
      throw new RuntimeException(ActionMessage.getErrMessage("error.doc.loaddoc.api.illegalcount"));
    registApi(_action, (Element) apiList.item(0));

  }

  /**
   * 注册Api
   */
  private static void registApi(Action action, Element api) throws Exception {
    if (null == action) return;
    // 属性验证
    String errCode = "error.doc.loaddoc.api.misattr";
    // 必填属性
    String url = ActionTool.validateNotBlank(getAttribute(api, "url"), errCode, "url");
    // 可选属性
    String protocol = getAttribute(api, "protocol");
    String method = getAttribute(api, "method");

    // 构建Api
    Api _api = new Api();
    _api.setUrl(url);
    if (StringUtils.isNotBlank(protocol)) _api.setProtocol(protocol);
    if (StringUtils.isNotBlank(method)) _api.setMethod(method);
    AbstractApiProxy apiProxy = _api.getProtocol().getProxyType().newInstance();
    // 注册Api
    apiProxy.setApi(_api);
    action.setApi(apiProxy);

    // 注册Param
    NodeList paramList = api.getElementsByTagName(ActionEnum.Node.Param.toString());
    if (null != paramList && paramList.getLength() > 0) {
      registParamList(_api, paramList);
    }
  }

  /**
   * 注册参数列表
   */
  private static void registParamList(Api api, NodeList paramList) {
    for (int i = 0; i < paramList.getLength(); i++) {
      Element param = (Element) paramList.item(i);
      registParam(api, param);
    }
  }

  /**
   * 注册参数
   */
  private static void registParam(Api api, Element param) {
    if (null == api) return;
    // 属性验证
    String errCode = "error.doc.loaddoc.param.misattr";
    String name = ActionTool.validateNotBlank(getAttribute(param, "name"), errCode, "name");

    // 注册Param
    api.registParam(name);
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
    StringBuffer sbr = new StringBuffer();
    while (matcher.find()) {
      String group = matcher.group();
      matcher.appendReplacement(sbr, getProperty(group));
    }
    matcher.appendTail(sbr);
    return sbr.toString();
  }

  private static String getProperty(String regex) {
    return params.get(regex.subSequence(2, regex.length() - 1)).toString();
  }

}
