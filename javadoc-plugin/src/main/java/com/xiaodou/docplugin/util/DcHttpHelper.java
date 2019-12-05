package com.xiaodou.docplugin.util;

import com.alibaba.fastjson.JSON;
import com.xiaodou.docplugin.entity.ApiClassJavadocEntity;
import com.xiaodou.docplugin.entity.ApiMethodJavadocEntity;
import com.xiaodou.docplugin.entity.DcLibEntity;
import com.xiaodou.docplugin.entity.SubFieldEntity;
import com.xiaodou.docplugin.service.impl.JavadocService;
import com.xiaodou.docplugin.service.impl.dc.DcJavadocService;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * @author junpeng.chen
 */
public class DcHttpHelper {

  static String tableStyle =
      "align=\"center\" style=\"width:1200px;\" border=\"1\" cellspacing=\"0\" bordercolor=\"#000000\" cellpadding=\"2\"";
  static String trStyle = "style=\"background-color:#bbff66\"";
  static String tdStyle = "align=\"center\" style=\"font-size:10pt;width:150px;\"";
  static String subtrStyle = "style=\"background-color:#E4DE60\"";
  static String realTdStyle = "align=\"center\" style=\"font-size:10pt;width:150px;\"";
  public static String apiDocPath;
  static String _10Space = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
  static String _8Space = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";

  /**
   * 生成一级索引页
   * 
   * @param classJavadocs 类javadoc实体
   */
  public static void getIndex(List<ApiClassJavadocEntity> classJavadocs) {
    StringBuilder sb = new StringBuilder();

    if (classJavadocs == null) {
      sb.append("class javadoc exception");
      generatePage(apiDocPath, apiDocPath + "/index.html", sb);
    }

    int id = 1;
    for (ApiClassJavadocEntity classJavadoc : classJavadocs) {
      String title =
          "<h3 align='left'> " + String.valueOf(id++) + "." + "<br/>&nbsp;&nbsp;名称："
              + classJavadoc.getApiName() + "<br/>&nbsp;&nbsp;描述：" + classJavadoc.getDescription()
              + "<br/>&nbsp;&nbsp;作者：" + classJavadoc.getAuthor() + "<h3>";
      int tableId = 1;
      String header =
          "<table " + tableStyle + ">" + "<tbody><tr " + trStyle + ">" + "<td " + tdStyle
              + ">序号</td>" + "<td " + tdStyle + ">名称</td>" + "<td " + tdStyle + ">链接</td>" + "<td "
              + tdStyle + ">描述</td>" + "</tr>";

      sb.append(title);
      sb.append(header);

      List<ApiMethodJavadocEntity> methodList = classJavadoc.getMethodList();
      if (methodList != null) {
        for (ApiMethodJavadocEntity method : methodList) {
          String dirPath = apiDocPath + "/" + classJavadoc.getApiName();
          String pageRefer = classJavadoc.getApiName() + "/" + method.getMethodName() + ".html";
          String pageName = dirPath + "/" + method.getMethodName() + ".html";
          String realReqMapping =
              classJavadoc.getRequestMapping() + "/" + method.getRequestMapping();
          // 方法名称须加入链接至方法页面
          String dynamicContent =
              "<tr " + ">" + "<td " + realTdStyle + ">" + String.valueOf(tableId++) + "</td>"
                  + "<td " + realTdStyle + ">" + "<a href=" + pageRefer + ">"
                  + method.getMethodName() + "</a>" + "</td>" + "<td " + realTdStyle + ">"
                  + realReqMapping.replace("//", "/") + "</td>" + "<td " + realTdStyle + ">"
                  + method.getDescription() + "</td></tr>";
          sb.append(dynamicContent);

          // 生成方法页面
          getContent(method, classJavadoc, dirPath, pageName);
        }
      }

      sb.append("</tbody></table></br>");

    }

    generatePage(apiDocPath, apiDocPath + "/index.html", sb);

    return;
  }

  /**
   * 生成二级接口描述
   * 
   * @param methodJavadoc 方法javadoc实体
   * @param classJavadoc 所属类javadoc实体
   */
  private static void getContent(ApiMethodJavadocEntity methodJavadoc,
      ApiClassJavadocEntity classJavadoc, String dirPath, String pageName) {
    if (classJavadoc == null || methodJavadoc == null) {
      return;
    }
    int id = 1;
    String realReqMapping =
        classJavadoc.getRequestMapping() + "/" + methodJavadoc.getRequestMapping();
    StringBuilder sb = new StringBuilder();

    String title = getMethodItemDesc(id++, "名称", methodJavadoc.getMethodName());
    sb.append(title);
    String author = getMethodItemDesc(id++, "作者", classJavadoc.getAuthor());
    sb.append(author);
    String url = getMethodItemDescWithLink(id++, "链接", realReqMapping.replace("//", "/"));
    sb.append(url);
    String httpMethod = getMethodItemDesc(id++, "HTTP访问方法", methodJavadoc.getHttpMethod());
    sb.append(httpMethod);
    String request = getMethodItemDesc(id++, "请求参数", "");
    if (methodJavadoc.getRequestDic().keySet().size() <= 0) {
      sb.append(request.replace("</h3>", "None&nbsp;&nbsp;</h3>"));
    } else {
      sb.append(request);
      sb.append("<h4 align='left'>" + _8Space + "JSON : </h4>");
      sb.append(getReqJSONResult(methodJavadoc.getRequestDic()));
      sb.append("<h4 align='left'>" + _8Space + "类图 : </h4>");
    }
    sb.append(getFieldContent(methodJavadoc.getRequestDic(), "req"));

    String response = getMethodItemDesc(id++, "返回类型", "");
    if (methodJavadoc.getResponseDic().keySet().size() <= 0) {
      sb.append(response.replace("</h3>", "None&nbsp;&nbsp;</h3>"));
    } else {
      sb.append(response);
      String resultJson = getRepJSONResult(methodJavadoc.getResponseDic());
      if (resultJson.contains("{") && resultJson.contains("}")) {
        sb.append("<h4 align='left'>" + _8Space + "JSON : </h4>");
        sb.append(resultJson);
      }
      sb.append("<h4 align='left'>" + _8Space + "类图 : </h4>");
    }
    sb.append(getFieldContent(methodJavadoc.getResponseDic(), "rep"));

    generatePage(dirPath, pageName, sb);
    return;
  }

  /**
   * 获取字段描述
   * 
   * @param fieldDic 字段
   * @return 类描述表格
   */
  private static String getFieldContent(HashMap<SubFieldEntity, Object> fieldDic, String mark) {
    StringBuilder ret = new StringBuilder();

    if (fieldDic != null) {
      Set<SubFieldEntity> keySet = fieldDic.keySet();
      if (keySet != null && keySet.size() > 0) {

        String header =
            "<table " + tableStyle + ">" + "<tbody><tr " + trStyle + ">"
                + (mark.equals("rep") ? "" : "<td " + tdStyle + ">名称</td>") + "<td " + tdStyle
                + ">类型</td>" + "<td " + tdStyle + ">描述</td>" + "</tr>";
        ret.append(header);
        String frameworkEntity = getFrameworkDefineEntity(mark);
        ret.append(frameworkEntity);

        for (SubFieldEntity field : keySet) {
          String dynamicContent =
              "<tr "
                  + ">"
                  + (mark.equals("rep") ? "" : "<td " + realTdStyle + ">" + field.getName()
                      + "</td>") + "<td " + realTdStyle + ">"
                  + field.getType().replace("<", "(").replace(">", ")") + "</td>" + "<td "
                  + realTdStyle + ">" + field.getDescription() + "</td></tr>";
          Object fieldType = fieldDic.get(field);
          if (fieldType instanceof DcLibEntity) {
            DcLibEntity dcLibEntity = (DcLibEntity) fieldType;
            String link =
                "<a href='" + dcLibEntity.getUrl() + "'>" + dcLibEntity.getSimpleTypeName()
                    + "</a>";
            dynamicContent =
                "<tr "
                    + ">"
                    + (mark.equals("rep") ? "" : "<td " + realTdStyle + ">" + "realRequest"
                        + "</td>") + "<td " + realTdStyle + ">" + link + "</td>" + "<td "
                    + realTdStyle + ">" + field.getDescription() + "</td></tr>";
          }
          ret.append(dynamicContent);
        }
        ret.append("</tbody></table></br>");

        for (SubFieldEntity field : keySet) {
          Object fieldType = fieldDic.get(field);
          if (fieldType instanceof ApiClassJavadocEntity) {
            ApiClassJavadocEntity realFieldType = (ApiClassJavadocEntity) fieldType;
            String subFieldContent = getSubFieldContent(realFieldType);
            ret.append(subFieldContent);
          }
        }
      }
      if (keySet.size() <= 0) {
        // ret.append("None");
      }
    }

    return ret.toString();
  }

  private static String getFrameworkDefineEntity(String mark) {
    String frameworkEntity = "";
    if (!mark.equals("rep")) {
      frameworkEntity +=
          "<tr><td " + realTdStyle + ">from</td>" + "<td " + realTdStyle + ">String</td>" + "<td "
              + realTdStyle + ">调用方所在的机器名称或者IP地址</td></tr>";
      frameworkEntity +=
          "<tr><td " + realTdStyle + ">logId</td>" + "<td " + realTdStyle + ">String</td>" + "<td "
              + realTdStyle + ">日志id，一般是GUID</td></tr>";
    } else {
      frameworkEntity +=
          "<tr><td " + realTdStyle + ">String</td>" + "<td " + realTdStyle
              + ">异常信息，若没有异常则为空字符串</td></tr>";
      frameworkEntity +=
          "<tr><td " + realTdStyle + ">String</td>" + "<td " + realTdStyle
              + ">日志id，一般是GUID</td></tr>";
      frameworkEntity +=
          "<tr><td " + realTdStyle + ">int</td>" + "<td " + realTdStyle
              + ">返回码，0(成功), 大于0(业务异常), 小于0(系统异常) </td></tr>";
      frameworkEntity +=
          "<tr><td " + realTdStyle + ">String</td>" + "<td " + realTdStyle
              + ">实际执行该服务的机器名</td></tr>";
    }
    return frameworkEntity;
  }

  /**
   * @param classField
   * @return
   */
  private static String getSubFieldContent(ApiClassJavadocEntity classField) {
    StringBuilder ret = new StringBuilder();
    HashMap<SubFieldEntity, Object> fieldDic = classField.getFieldList();
    Set<SubFieldEntity> keySet = fieldDic.keySet();
    if (keySet != null && keySet.size() > 0) {

      String classDesc =
          "<h4 align='center'> " + "名称：" + classField.getApiName() + "&nbsp;&nbsp;&nbsp;&nbsp;作者："
              + classField.getAuthor() + "&nbsp;&nbsp;&nbsp;&nbsp;描述："
              + classField.getDescription() + "</h4>";
      ret.append(classDesc);

      String header =
          "<table " + tableStyle + ">" + "<tbody><tr " + subtrStyle + ">" + "<td " + tdStyle
              + ">名称</td>" + "<td " + tdStyle + ">类型</td>" + "<td " + tdStyle + ">描述</td>"
              + "</tr>";
      ret.append(header);

      for (SubFieldEntity subfield : keySet) {
        String dynamicContent =
            "<tr " + ">" + "<td " + realTdStyle + ">" + subfield.getName() + "</td>" + "<td "
                + realTdStyle + ">" + subfield.getType().replace("<", "(").replace(">", ")")
                + "</td>" + "<td " + realTdStyle + ">" + subfield.getDescription() + "</td></tr>";
        ret.append(dynamicContent);
      }
      ret.append("</tbody></table></br>");

      for (SubFieldEntity subfield : keySet) {
        Object fieldType = fieldDic.get(subfield);
        if (fieldType instanceof ApiClassJavadocEntity) {
          ApiClassJavadocEntity realFieldType = (ApiClassJavadocEntity) fieldType;
          String subFieldContent = getSubFieldContent(realFieldType);
          ret.append(subFieldContent);
        }
      }
    }

    if (keySet.size() <= 0) {
      System.out.println("|--------无任何field...");
    }

    return ret.toString();
  }

  /**
   * @param id
   * @param key
   * @param value
   * @return
   */
  private static String getMethodItemDesc(int id, String key, String value) {
    String title =
        "<h3 align='left'> " + String.valueOf(id++) + "." + "&nbsp;&nbsp;" + key + "：" + value
            + "</h3>";
    return title;
  }

  /**
   * @param id
   * @param key
   * @param value
   * @return
   */
  private static String getMethodItemDescWithLink(int id, String key, String value) {
    String title =
        "<h3 align='left'> " + String.valueOf(id++) + "." + "&nbsp;&nbsp;" + key + "：<a href='"
            + value + "'>" + value + "</a></h3>";
    return title;
  }

  private static String getClassItemDesc(LinkedList<String> key, LinkedList<String> value) {

    String title = "<h4 align='center'> " + "&nbsp;&nbsp;" + key + "：" + value + "</h4>";
    return title;
  }

  /**
   * @param pageName
   */
  private static void generatePage(String dirPath, String pageName, StringBuilder content) {
    FileHelper.createFile(dirPath, pageName, content.toString());
  }

  /**
   * 生成请求对象Json
   * 
   * @param fieldDic 字段字典
   * @return Json字符串
   */
  private static String getReqJSONResult(HashMap<SubFieldEntity, Object> fieldDic) {
    String retJson = "{";

    if (fieldDic != null) {
      Set<SubFieldEntity> keys = fieldDic.keySet();
      for (SubFieldEntity key : keys) {
        String subJson = key.getName() + ":";
        Object value = fieldDic.get(key);
        if (key.getType().startsWith("List") || key.getType().startsWith("Set")
            || key.getType().contains("Map")) {
          subJson += "[";
          if (value instanceof ApiClassJavadocEntity) {
            ApiClassJavadocEntity realValue = (ApiClassJavadocEntity) value;
            String initJsonStr = getObjectJson(realValue.getFullName());
            subJson += initJsonStr;
          } else if (value instanceof DcLibEntity) {
            DcLibEntity dcLibEntity = (DcLibEntity) value;
            String initJsonStr =
                getObjectJson(dcLibEntity.getPackageName() + "." + dcLibEntity.getSimpleTypeName());
            subJson += initJsonStr;
          }
          subJson += "]";
        } else {
          if (value instanceof ApiClassJavadocEntity) {
            ApiClassJavadocEntity realValue = (ApiClassJavadocEntity) value;
            String initJsonStr = getObjectJson(realValue.getFullName());
            subJson += initJsonStr;
          } else if (value instanceof DcLibEntity) {
            subJson = "\"from\":\"\",";
            subJson += "\"logId\":\"GUID\",";
            subJson += "realRequest:";
            DcLibEntity dcLibEntity = (DcLibEntity) value;
            String initJsonStr =
                getObjectJson(dcLibEntity.getPackageName() + "." + dcLibEntity.getSimpleTypeName());
            subJson += initJsonStr;
          } else {
            if (key.getType().equals("String")) {
              subJson += "\"\"";
            } else if (key.getType().equals("Date") || key.getType().equals("Timestamp")) {
              subJson += "\"1970-01-01 00:00:00\"";
            } else {
              subJson += "0";
            }
          }
        }
        subJson += ",";
        retJson += subJson;
      }
    }
    retJson = retJson.substring(0, retJson.length() - 1);
    retJson += "}";
    retJson = retJson.startsWith("{{") ? retJson.replace("}}", "}").replace("{{", "{") : retJson;
    retJson = retJson.replace(",,", ",").replace("::", ":");
    retJson = JSONHelper.jsonFormatter(retJson);
    String realRetJson = retJson.replace(" ", "&nbsp;").replace("\n", "</br>");
    // realRetJson = "<div align='left'>" + _10Space + realRetJson + "</div>";
    realRetJson =
        "<table align=\"center\" style=\"width:100%;\"><tbody><tr><td align=\"center\" style=\"width:10%;\">"
            + _10Space
            + "</td><td align=\"left\"  style=\"width:90%;\">"
            + realRetJson
            + "</td></tr></tbody></table>";
    return realRetJson;
  }

  private static String getObjectJson(String className) {
    String jarPath = DcJavadocService.basePath + "/target/" + DcJavadocService.projectName + ".jar";
    String libPath =
        DcJavadocService.basePath + "/target/" + DcJavadocService.projectName + "/WEB-INF/lib";
    Object initJson = ClassLoaderHelper.getInitJSONObject(jarPath, className, libPath);
    return JSON.toJSONString(initJson);
  }

  /**
   * @param fieldDic
   * @return
   */
  private static String getRepJSONResult(HashMap<SubFieldEntity, Object> fieldDic) {
    String retJson = "{";

    if (fieldDic != null) {
      Set<SubFieldEntity> keys = fieldDic.keySet();
      for (SubFieldEntity key : keys) {
        String subJson = "";
        Object value = fieldDic.get(key);
        if (key.getType().startsWith("List") || key.getType().startsWith("Set")
            || key.getType().contains("Map")) {
          if (key.getType().startsWith("List")) {
            subJson += "List:[";
          } else if (key.getType().startsWith("Set")) {
            subJson += "Set:[";
          } else {
            int index = key.getType().indexOf("<");
            subJson += key.getType().substring(0, index) + ":[";
          }
          if (value instanceof ApiClassJavadocEntity) {
            ApiClassJavadocEntity realValue = (ApiClassJavadocEntity) value;
            String initJsonStr = getObjectJson(realValue.getFullName());
            subJson += initJsonStr;
          }
          subJson += "]";
        } else if (value instanceof DcLibEntity) {
          subJson = "\"exceptionMsg\":\"\",";
          subJson += "\"logId\":\"GUID\",";
          subJson += "\"responseCode\":0,";
          subJson += "\"serverName\":\"dc-core-server\",";
          subJson += "realResponse:";
          DcLibEntity dcLibEntity = (DcLibEntity) value;
          String initJsonStr =
              getObjectJson(dcLibEntity.getPackageName() + "." + dcLibEntity.getSimpleTypeName());
          subJson += initJsonStr;
        } else {
          if (value instanceof ApiClassJavadocEntity) {
            ApiClassJavadocEntity realValue = (ApiClassJavadocEntity) value;
            String initJsonStr = getObjectJson(realValue.getFullName());
            subJson += initJsonStr;
          }
        }
        subJson += ",";
        retJson += subJson;
      }
    }
    retJson = retJson.substring(0, retJson.length() - 1);
    retJson += "}";
    retJson = retJson.startsWith("{{") ? retJson.replace("}}", "}").replace("{{", "{") : retJson;
    retJson = retJson.replace(",,", ",").replace("::", ":");
    retJson = JSONHelper.jsonFormatter(retJson);
    String realRetJson = retJson.replace(" ", "&nbsp;").replace("\n", "</br>");
    if (realRetJson.equals("{}")) {
      realRetJson = "";
    }
    // realRetJson = "<div align='left'>" + _10Space + realRetJson + "</div>";
    realRetJson =
        "<table align=\"center\" style=\"width:100%;\"><tbody><tr><td align=\"center\" style=\"width:10%;\">"
            + _10Space
            + "</td><td align=\"left\" style=\"width:90%;\">"
            + realRetJson
            + "</td></tr></tbody></table>";
    return realRetJson;
  }

  public static void setPath(String projectName) {
    apiDocPath = DcJavadocService.basePath + String.format("/target/%s/apidocs", projectName);
  }
}
