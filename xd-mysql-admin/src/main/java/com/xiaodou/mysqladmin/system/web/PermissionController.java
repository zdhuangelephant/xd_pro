package com.xiaodou.mysqladmin.system.web;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xiaodou.common.util.CommUtil;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.mysqladmin.common.persistence.Page;
import com.xiaodou.mysqladmin.common.web.BaseController;
import com.xiaodou.mysqladmin.system.entity.DataSource;
import com.xiaodou.mysqladmin.system.entity.Role;
import com.xiaodou.mysqladmin.system.entity.SearchHistory;
import com.xiaodou.mysqladmin.system.service.PermissionService;
import com.xiaodou.mysqladmin.system.utils.Constants;

@Controller
@RequestMapping({"system/permission"})
public class PermissionController extends BaseController {

  @Autowired
  private PermissionService permissionService;

  @RequestMapping({"i/allDataSourceList"})
  @ResponseBody
  public List<Map<String, Object>> allDataSourceList() throws Exception {
    List<Map<String, Object>> listDb = Lists.newArrayList();
    List<DataSource> dataSourceList = this.permissionService.getAllDataSource();
    if (null != dataSourceList) {
      for (DataSource ds : dataSourceList) {
        Map<String, Object> columnMap = Maps.newHashMap();
        CommUtil.transferFromVO2Map(columnMap, ds);
        listDb.add(columnMap);
      }
    }
    return listDb;
  }

  @RequestMapping({"i/allDatabaseList/{datasourceName}"})
  @ResponseBody
  public List<Map<String, Object>> allDatabaseList(
      @PathVariable("datasourceName") String datasourceId, HttpServletRequest request)
      throws Exception {
    HttpSession session = request.getSession(true);
    session.setAttribute("DATA_SOURCE_ID", datasourceId);
    List<Map<String, Object>> listDb = Lists.newArrayList();
    listDb = this.permissionService.getAllDataBase(datasourceId);
    return listDb;
  }

  @RequestMapping(value = {"i/databaseList/{databaseName}"}, method = {org.springframework.web.bind.annotation.RequestMethod.GET})
  @ResponseBody
  public List<Map<String, Object>> databaseList(@PathVariable("databaseName") String databaseName) {
    try {
      switch (0) {
        case 1:
          String date = null;
          String second = null;
          try {
            JSONObject jsoObj = new JSONObject();
            date = jsoObj.getString("date");
            second = jsoObj.getString("second");
          } catch (JSONException e) {
            e.printStackTrace();
          }

      }

      List listAll = Lists.newArrayList();

      List listTable = Lists.newArrayList();
      List listView = Lists.newArrayList();
      List listFunction = Lists.newArrayList();

      Map tempObject = Maps.newHashMap();
      Map<String, List<Map<String, Object>>> mapTableColumn = Maps.newHashMap();
      List listTableColumn = Lists.newArrayList();

      String dbName = databaseName;

      Map tempMap = Maps.newHashMap();
      String column_name = "";
      String data_type = "";
      Object precision = "";

      Map tempObjectTable2 = Maps.newHashMap();

      int id = 0;
      int pid = 0;
      int cpid = 0;

      tempObject.put("id", Integer.valueOf(++id));
      tempObject.put("name", dbName);
      tempObject.put("type", "db");
      tempObject.put("icon", "icon-hamburg-database");

      listAll.add(tempObject);

      pid = id;

      Map tempObject2 = Maps.newHashMap();
      tempObject2.put("id", Integer.valueOf(++id));
      tempObject2.put("pid", Integer.valueOf(pid));
      tempObject2.put("name", "表");
      tempObject2.put("icon", "icon-berlin-billing");
      tempObject2.put("type", "direct");

      listAll.add(tempObject2);

      Map tempObject3 = Maps.newHashMap();
      tempObject3.put("id", Integer.valueOf(++id));
      tempObject3.put("pid", Integer.valueOf(pid));
      tempObject3.put("name", "视图");
      tempObject3.put("icon", "icon-berlin-address");
      tempObject3.put("type", "direct");

      listAll.add(tempObject3);

      Map tempObject4 = Maps.newHashMap();
      tempObject4.put("id", Integer.valueOf(++id));
      tempObject4.put("pid", Integer.valueOf(pid));
      tempObject4.put("name", "函数");
      tempObject4.put("icon", "icon-berlin-address");
      tempObject4.put("type", "direct");

      listAll.add(tempObject4);

      listTable = this.permissionService.getAllTables(dbName);
      mapTableColumn = this.permissionService.getAllTableColumns(dbName);
      for (int y = 0; y < listTable.size(); ++y) {
        tempObject = (Map) listTable.get(y);

        String table_name = (String) tempObject.get("table_name");
        Map tempObjectTable = Maps.newHashMap();
        tempObjectTable.put("id", Integer.valueOf(++id));
        tempObjectTable.put("pid", Integer.valueOf(pid + 1));
        tempObjectTable.put("name", table_name);
        tempObjectTable.put("icon", "icon-berlin-calendar");
        tempObjectTable.put("type", "table");
        tempObjectTable.put("state", "closed");

        cpid = id;

        listAll.add(tempObjectTable);

        listTableColumn = mapTableColumn.get(table_name);
        for (int z = 0; z < listTableColumn.size(); ++z) {
          tempMap = (Map) listTableColumn.get(z);
          column_name = (String) tempMap.get("column_name");

          data_type = ((String) tempMap.get("column_type")).toLowerCase();

          tempObjectTable2 = Maps.newHashMap();
          tempObjectTable2.put("id", Integer.valueOf(++id));
          tempObjectTable2.put("pid", Integer.valueOf(cpid));

          tempObjectTable2.put("name", "<b>" + column_name + "</b>" + "  " + data_type);

          tempObjectTable2.put("icon", "icon-berlin-project");
          tempObjectTable2.put("type", "column");
          listAll.add(tempObjectTable2);
        }

      }

      listView = this.permissionService.getAllViews(dbName);
      for (int y = 0; y < listView.size(); ++y) {
        tempObject = (Map) listView.get(y);

        Map tempObjectView = Maps.newHashMap();
        tempObjectView.put("id", Integer.valueOf(++id));
        tempObjectView.put("pid", Integer.valueOf(pid + 2));
        tempObjectView.put("name", tempObject.get("table_name"));
        tempObjectView.put("icon", "icon-berlin-library");
        tempObjectView.put("type", "view");

        listAll.add(tempObjectView);
      }

      listFunction = this.permissionService.getAllFuntion(dbName);
      for (int y = 0; y < listFunction.size(); ++y) {
        tempObject = (Map) listFunction.get(y);
        Map tempObjectFunction = Maps.newHashMap();
        tempObjectFunction.put("id", Integer.valueOf(++id));
        tempObjectFunction.put("pid", Integer.valueOf(pid + 3));
        tempObjectFunction.put("name", tempObject.get("routine_name"));
        tempObjectFunction.put("icon", "icon-berlin-settings");
        tempObjectFunction.put("type", "function");
        listAll.add(tempObjectFunction);
      }
      return listAll;
    } catch (Exception e) {
      LoggerUtil.error("系统异常", e);
      return null;
    }
  }

  @RequestMapping(value = {"i/tableColumns/{tableName}/{dbName}"}, method = {org.springframework.web.bind.annotation.RequestMethod.GET})
  @ResponseBody
  public List<Map<String, Object>> tableColumns(@PathVariable("tableName") String tableName,
      @PathVariable("dbName") String dbName) throws Exception {
    List listAllColumn = this.permissionService.getTableColumnsForDesign(dbName, tableName);

    List list2 = Lists.newArrayList();

    for (int i = 0; i < listAllColumn.size(); ++i) {
      Map<String, Object> map1 = (Map) listAllColumn.get(i);
      Map<String, Object> map2 = Maps.newHashMap();

      map2.put("column_name", (String) map1.get("column_name"));
      map2.put("column_key", map1.get("column_key"));

      list2.add(map2);
    }
    return list2;
  }

  @RequestMapping(value = {"i/table/{tableName}/{dbName}"}, method = {org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public Map<String, Object> getData(@PathVariable("tableName") String tableName,
      @PathVariable("dbName") String dbName, HttpServletRequest request) throws Exception {
    try {
      Page<Map<String, Object>> page = getPage(request);

      page = this.permissionService.getData(page, tableName, dbName);

      return getEasyUIData(page);
    } catch (Exception e) {
      LoggerUtil.error("系统异常", e);
      return null;
    }
  }

  @RequestMapping(value = {"i/executeSqlForColumns"}, method = {org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public Map<String, Object> executeSqlForColumns(@RequestBody TempDto tem,
      HttpServletRequest request) {
    Map<String, Object> map = Maps.newHashMap();
    List columns = Lists.newArrayList();
    String mess = "";
    String status = "";

    String sql = tem.getSql();

    String dbName = tem.getDbName();
    try {
      columns = this.permissionService.executeSqlForColumns(sql, dbName);

      mess = "执行完成！";
      status = "success";

      if (columns.size() <= 0) mess = "执行完成,无数据！";
    } catch (Exception e) {
      System.out.println("提示：" + e.getMessage());

      mess = e.getMessage();
      status = "fail";
    }

    map.put("mess", mess);
    map.put("status", status);
    map.put("columns", columns);
    return map;
  }

  @RequestMapping(value = {"i/executeSql"}, method = {org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public Map<String, Object> executeSql(@RequestBody TempDto tem, HttpServletRequest request)
      throws Exception {
    Page<Map<String, Object>> page = getPage(request);

    String sql = tem.getSql();
    String dbName = tem.getDbName();

    page = this.permissionService.executeSql(page, sql, dbName);

    return getEasyUIData(page);
  }

  @RequestMapping(value = {"i/executeSqlTest"}, method = {org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public Map<String, Object> executeSqlTest(HttpServletRequest request) throws Exception {
    Map<String, Object> map = Maps.newHashMap();

    String sql = request.getParameter("sql");
    String dbName = request.getParameter("dbName");

    if ((sql.indexOf("select") == 0) || (sql.indexOf("SELECT") == 0))
      map = executeSqlHaveRes(sql, dbName, request);
    else {
      map = executeSqlNotRes(sql, dbName);
    }
    return map;
  }

  public Map<String, Object> executeSqlHaveRes(String sql, String dbName, HttpServletRequest request) {
    Map<String, Object> map = Maps.newHashMap();
    Page<Map<String, Object>> page = getPage(request);

    String mess = "";
    String status = "";
    Date b1 = new Date();
    try {
      page = this.permissionService.executeSql(page, sql, dbName);
      mess = "执行成功！";
      status = "success";
    } catch (Exception e) {
      System.out.println("444 " + e.getMessage());
      mess = e.getMessage();
      status = "fail";
    }

    Date b2 = new Date();

    long y = b2.getTime() - b1.getTime();
    map.put("rows", page.getResult());
    map.put("total", Long.valueOf(page.getTotalCount()));
    map.put("columns", page.getColumns());
    map.put("primaryKey", page.getPrimaryKey());

    map.put("totalCount", Long.valueOf(page.getTotalCount()));
    map.put("time", Long.valueOf(y));
    map.put("mess", mess);
    map.put("status", status);

    return map;
  }

  public Map<String, Object> executeSqlNotRes(String sql, String dbName) {
    String mess = "";
    String status = "";

    Date b1 = new Date();
    int i = 0;
    try {
      i = this.permissionService.executeSqlNotRes(sql, dbName);
      mess = "执行成功！";
      status = "success";
    } catch (Exception e) {
      mess = e.getMessage();
      status = "fail";
    }

    Date b2 = new Date();

    long y = b2.getTime() - b1.getTime();

    Map<String, Object> map = Maps.newHashMap();

    map.put("totalCount", Integer.valueOf(i));
    map.put("time", Long.valueOf(y));
    map.put("mess", mess);
    map.put("status", status);

    return map;
  }

  @RequestMapping(value = {"i/selectSearchHistory"}, method = {org.springframework.web.bind.annotation.RequestMethod.GET})
  @ResponseBody
  public List<Map<String, Object>> selectSearchHistory() {
    List<SearchHistory> list = this.permissionService.selectSearchHistory();
    List<Map<String, Object>> list2 = Lists.newArrayList();
    if (null == list || list.isEmpty()) {
      return list2;
    }
    for (SearchHistory entity : list) {
      Map<String, Object> map = Maps.newHashMap();
      CommUtil.transferFromVO2Map(map, entity);
      map.put("pid", "0");
      map.put("icon", "icon-hamburg-zoom");
      list2.add(map);
    }
    return list2;
  }

  @RequestMapping(value = {"i/config"}, method = {org.springframework.web.bind.annotation.RequestMethod.GET})
  public String config(Model model) {
    return "system/configList";
  }

  @RequestMapping(value = {"i/configList"}, method = {org.springframework.web.bind.annotation.RequestMethod.GET})
  @ResponseBody
  public Map<String, Object> configList(HttpServletRequest paramHttpServletRequest)
      throws Exception {
    Page<DataSource> localPage = getPage(paramHttpServletRequest);
    List<DataSource> dataSourceList = this.permissionService.getAllDataSource();
    if (null != dataSourceList && !dataSourceList.isEmpty()) {
      localPage.setResult(dataSourceList);
    }
    return getEasyUIData(localPage);
  }

  @RequestMapping(value = {"i/addConfigForm"}, method = {org.springframework.web.bind.annotation.RequestMethod.GET})
  public String addConfigForm(Model paramModel, HttpServletRequest request) {
    HttpSession session = request.getSession(true);
    String token = session.getAttribute("TOKEN").toString();
    if (!permissionService.checkPrivilege(token)) {
      return "system/noPrivilege";
    }
    return "system/configForm";
  }

  @RequestMapping(value = {"i/editConfigForm/{id}"}, method = {org.springframework.web.bind.annotation.RequestMethod.GET})
  public String editConfigForm(@PathVariable("id") String sid, Model paramModel,
      HttpServletRequest request) {
    HttpSession session = request.getSession(true);
    String token = session.getAttribute("TOKEN").toString();
    if (!permissionService.checkPrivilege(token)) {
      return "system/noPrivilege";
    }
    DataSource ds = this.permissionService.getDataSource(sid);
    if (null != ds) {
      paramModel.addAttribute("config", ds);
    }
    return "system/configForm";
  }

  @RequestMapping(value = {"i/configUpdate"}, method = {org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public Map<String, Object> configUpdate(@ModelAttribute @RequestBody DataSource config,
      Model model, HttpServletRequest request) {
    Map<String, Object> ret = Maps.newHashMap();
    HttpSession session = request.getSession(true);
    String token = session.getAttribute("TOKEN").toString();
    if (!permissionService.checkPrivilege(token)) {
      ret.put("status", "noPrivileges");
      ret.put("mess", "您不具备操作权限, 请联系管理员.");
      return ret;
    }
    String ip = config.getIp();
    String port = config.getPort();
    String url = "jdbc:mysql://" + ip + ":" + port + "/" + Constants.DATA_SOURCE_INFORMATION_TABLE;
    config.setUrl(url);
    if (StringUtils.isBlank(config.getSid())) {
      config.setSid(UUID.randomUUID().toString());
      this.permissionService.configInsert(config);
    } else {
      this.permissionService.configUpdate(config);
    }
    ret.put("status", "success");
    ret.put("mess", "操作成功.");
    return ret;
  }

  @RequestMapping(value = {"i/searchHistory"}, method = {org.springframework.web.bind.annotation.RequestMethod.GET})
  public String searchHistory(Model model) {
    return "system/searchHistory";
  }

  @RequestMapping(value = {"i/deleteRows"}, method = {org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public Map<String, Object> deleteRows(@RequestBody IdsDto tem) {
    String databaseName = tem.getDatabaseName();
    String tableName = tem.getTableName();
    String primary_key = tem.getPrimary_key();

    String checkedItems = tem.getCheckedItems();

    List condition = Lists.newArrayList();

    if (checkedItems != null) {
      JSONArray deleteArray = JSONArray.parseArray(checkedItems);

      for (int i = 0; i < deleteArray.size(); ++i) {
        Map<String, Object> map1 = (Map) deleteArray.get(i);
        String whereStr = "";

        if ((primary_key == null) || (primary_key.equals(""))) {
          for (String key : map1.keySet()) {
            if (map1.get(key) != null) {
              whereStr = whereStr + " and " + key + " = '" + map1.get(key) + "' ";
            }
          }
        } else {
          String[] primaryKeys = primary_key.split(",");
          for (String primaryKey : primaryKeys) {
            whereStr = whereStr + " and " + primaryKey + " = '" + map1.get(primaryKey) + "' ";
          }
        }
        condition.add(whereStr);
      }
    }
    int i = 0;
    String mess = "";
    String status = "";
    try {
      this.permissionService.deleteRowsNew(databaseName, tableName, primary_key, condition);
      mess = "删除成功";
      status = "success";
    } catch (Exception e) {
      mess = e.getMessage();
      status = "fail";
    }

    Map<String, Object> map = Maps.newHashMap();

    map.put("totalCount", Integer.valueOf(i));

    map.put("mess", mess);
    map.put("status", status);

    return map;
  }

  @RequestMapping(value = {"i/addRow/{tableName}/{databaseName}"}, method = {org.springframework.web.bind.annotation.RequestMethod.GET})
  public String addRows(@PathVariable("tableName") String tableName,
      @PathVariable("databaseName") String databaseName, HttpServletRequest request)
      throws Exception {
    List listAllColumn = this.permissionService.getTableColumnsForDesign(databaseName, tableName);

    List listAllColumn2 = Lists.newArrayList();

    for (int i = 0; i < listAllColumn.size(); ++i) {
      Map<String, Object> map2 = (Map) listAllColumn.get(i);

      boolean isAutoIncrement = ((Boolean) map2.get("isAutoIncrement")).booleanValue();

      if (!isAutoIncrement) {
        listAllColumn2.add(map2);
      }
    }

    request.setAttribute("databaseName", databaseName);
    request.setAttribute("tableName", tableName);
    request.setAttribute("listAllColumn", listAllColumn2);

    return "system/addRowOne";
  }

  @RequestMapping(value = {"i/saveRows"}, method = {org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public Map<String, Object> saveRows(HttpServletRequest request) {
    String databaseName = request.getParameter("databaseName");
    String tableName = request.getParameter("tableName");

    Map<String, Object> mapResult = Maps.newHashMap();
    Map<String, String> maps = Maps.newHashMap();

    Map<String, String[]> map = request.getParameterMap();
    Set set = map.entrySet();
    Iterator it = set.iterator();
    String column = "";
    String value = "";

    String mess = "";
    String status = "";

    while (it.hasNext()) {
      Map.Entry entry = (Map.Entry) it.next();

      column = (String) entry.getKey();
      for (String i : (String[]) entry.getValue()) {
        value = i;

        value = value.replaceAll("'", "''");
      }

      maps.put(column, value);
    }
    maps.remove("databaseName");
    maps.remove("tableName");
    try {
      this.permissionService.saveRows(maps, databaseName, tableName);
      mess = "新增成功！";
      status = "success";
    } catch (Exception e) {
      mess = e.getMessage();
      status = "fail";
    }

    mapResult.put("mess", mess);
    mapResult.put("status", status);

    return mapResult;
  }

  @RequestMapping(value = {"i/getPrimaryKeys"}, method = {org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public Map<String, Object> getPrimaryKeys(@RequestBody IdsDto tem) {
    Map<String, Object> mapResult = Maps.newHashMap();

    String tableName = tem.getTableName();
    String databaseName = tem.getDatabaseName();
    String mess = "";
    String status = "";

    String PrimaryKey = this.permissionService.getPrimaryKeys(databaseName, tableName);
    mess = "查询成功！";
    status = "success";
    mapResult.put("mess", mess);
    mapResult.put("status", status);
    mapResult.put("PrimaryKey", PrimaryKey);
    return mapResult;
  }

  @RequestMapping(value = {"i/editRows/{tableName}/{databaseName}/{id}/{idValues}"}, method = {org.springframework.web.bind.annotation.RequestMethod.GET})
  public String editRows(@PathVariable("tableName") String tableName,
      @PathVariable("databaseName") String databaseName, @PathVariable("id") String id,
      @PathVariable("idValues") String idValues, HttpServletRequest request) {
    List<Map<String, Object>> listAllColumn =
        this.permissionService.getOneRowById(databaseName, tableName, id, idValues);
    List<Map<String, Object>> newList = Lists.newArrayList();
    for (int i = 0; i < listAllColumn.size(); ++i) {
      Map<String, Object> map3 = listAllColumn.get(i);
      String data_type = (String) map3.get("data_type");
      if (data_type.equals("VARCHAR")) {
        String column_value = (String) map3.get("column_value");
        column_value = htmlEscape(column_value);
        map3.put("column_value", column_value);
      }
      newList.add(map3);
    }
    request.setAttribute("databaseName", databaseName);
    request.setAttribute("tableName", tableName);
    request.setAttribute("listAllColumn", newList);
    request.setAttribute("id", id);
    request.setAttribute("idValues", idValues);

    return "system/editRowOne";
  }

  @RequestMapping(value = {"i/updateRows"}, method = {org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public Map<String, Object> updateRows(HttpServletRequest request) {
    String mess = "";
    String status = "";
    Map<String, Object> mapResult = Maps.newHashMap();
    String databaseName = request.getParameter("databaseName");
    String tableName = request.getParameter("tableName");
    String id = request.getParameter("id");
    String idValues = request.getParameter("idValues");
    Map<String, String> maps = Maps.newHashMap();
    Map<String, String[]> map = request.getParameterMap();
    Set<Entry<String, String[]>> set = map.entrySet();
    Iterator<Entry<String, String[]>> it = set.iterator();
    String column = "";
    String value = "";
    while (it.hasNext()) {
      Map.Entry<String, String[]> entry = it.next();
      column = (String) entry.getKey();
      for (String i : (String[]) entry.getValue()) {
        value = i;
      }
      value = value.replaceAll("'", "''");
      maps.put(column, value);
    }
    maps.remove("databaseName");
    maps.remove("tableName");
    maps.remove("id");
    maps.remove("idValues");
    try {
      this.permissionService.updateRows(maps, databaseName, tableName, id, idValues);
      mess = " 更新成功！";
      status = "success";
    } catch (Exception e) {
      mess = e.getMessage();
      status = "fail";
    }
    mapResult.put("mess", mess);
    mapResult.put("status", status);
    return mapResult;
  }

  @RequestMapping(value = {"i/exportExcel"}, method = {org.springframework.web.bind.annotation.RequestMethod.POST})
  public void exportExcel(@RequestBody String sContent, HttpServletRequest request,
      HttpServletResponse response) {
    System.out.println(sContent);
    StringBuffer sb = new StringBuffer();
    sb.append("<html> <body> <table  border=\"1px\"> ");
    sb.append(" <tr> <td>8888888888888  </td> </tr>  ");
    sb.append(" </table> </body> </html>  ");
    String ss = sb.toString();
    try {
      OutputStream outputStream = response.getOutputStream();
      response.setContentType("application/msexcel; charset=UTF-8");
      response.setHeader("Content-disposition", "attachment; filename=data.xls");
      byte[] dataByteArr = ss.getBytes("UTF-8");
      outputStream.write(dataByteArr);
      outputStream.flush();
      outputStream.close();
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  @RequestMapping(value = {"i/download"}, method = {org.springframework.web.bind.annotation.RequestMethod.GET})
  public void download(HttpServletResponse response) {
    try {
      String path = "E://a.xls";

      File file = new File(path);

      String filename = file.getName();

      InputStream fis = new BufferedInputStream(new FileInputStream(path));
      byte[] buffer = new byte[fis.available()];
      fis.read(buffer);
      fis.close();

      response.reset();

      response.addHeader("Content-Disposition",
          "attachment;filename=" + new String(filename.getBytes()));
      response.addHeader("Content-Length", Long.toString(file.length()));
      OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
      response.setContentType("application/vnd.ms-excel;charset=gb2312");
      toClient.write(buffer);
      toClient.flush();
      toClient.close();
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }

  @RequestMapping(value = {"i/getViewSql"}, method = {org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public Map<String, Object> getViewSql(@RequestBody IdsDto tem) {
    Map<String, Object> mapResult = Maps.newHashMap();
    String tableName = tem.getTableName();
    String databaseName = tem.getDatabaseName();
    String viewSql = "";
    String mess = "";
    String status = "";
    try {
      viewSql = this.permissionService.getViewSql(databaseName, tableName);
      viewSql = viewSql.replaceAll("`", "");
      viewSql = viewSql.replaceAll(databaseName + ".", "");

      mess = "查询成功！";
      status = "success";
    } catch (Exception e) {
      mess = e.getMessage();
      status = "fail";
    }

    mapResult.put("mess", mess);
    mapResult.put("status", status);
    mapResult.put("viewSql", viewSql);

    return mapResult;
  }

  public static String htmlEscape(String strData) {
    if (strData == null) {
      return "";
    }
    strData = replaceString(strData, "&", "&amp;");
    strData = replaceString(strData, "<", "&lt;");
    strData = replaceString(strData, ">", "&gt;");
    strData = replaceString(strData, "'", "&apos;");
    strData = replaceString(strData, "\"", "&quot;");
    return strData;
  }

  public static String replaceString(String strData, String regex, String replacement) {
    if (strData == null) {
      return null;
    }

    int index = strData.indexOf(regex);
    String strNew = "";
    if (index >= 0) {
      while (index >= 0) {
        strNew = strNew + strData.substring(0, index) + replacement;
        strData = strData.substring(index + regex.length());
        index = strData.indexOf(regex);
      }
      strNew = strNew + strData;
      return strNew;
    }
    return strData;
  }

  @RequestMapping(value = {"i/help"}, method = {org.springframework.web.bind.annotation.RequestMethod.GET})
  public String help(HttpServletRequest request) {
    return "system/help";
  }

  @RequestMapping(value = {"i/testConn"}, method = {org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public Map<String, Object> testConn(@RequestBody DataSource config) {
    Map<String, Object> mapResult = Maps.newHashMap();

    String driverUrl = "com.mysql.jdbc.Driver";
    String ip = config.getIp();
    String port = config.getPort();
    String user = config.getUserName();
    String pass = config.getPasswrod();

    String mess = "";
    String status = "";

    boolean bl =
        this.permissionService.testConn(driverUrl, Constants.DATA_SOURCE_INFORMATION_TABLE, ip,
            port, user, pass);

    if (bl) {
      mess = "连接成功！";
      status = "success";
    } else {
      status = "fail";
    }

    mapResult.put("mess", mess);
    mapResult.put("status", status);

    return mapResult;
  }

  @RequestMapping(value = {"i/showTableData/{tableName}/{databaseName}"}, method = {org.springframework.web.bind.annotation.RequestMethod.GET})
  public String showTableData(@PathVariable("tableName") String tableName,
      @PathVariable("databaseName") String databaseName, HttpServletRequest request) {
    request.setAttribute("databaseName", databaseName);
    request.setAttribute("tableName", tableName);
    return "system/showTableData";
  }

  @RequestMapping(value = {"i/showResult/{sqlIndex}"}, method = {org.springframework.web.bind.annotation.RequestMethod.GET})
  public String showResult(@PathVariable("sqlIndex") String sqlIndex, HttpServletRequest request) {
    request.setAttribute("sqlIndex", sqlIndex);
    return "system/showResult";
  }

  @RequestMapping(value = {"i/updateRow/{tableName}/{databaseName}"}, method = {org.springframework.web.bind.annotation.RequestMethod.GET})
  public Map<String, Object> updateRow(@PathVariable("tableName") String tableName,
      @PathVariable("databaseName") String databaseName, HttpServletRequest request) {
    Map<String, Object> mapResult = Maps.newHashMap();

    String mess = "";
    String status = "";

    mess = "update成功！";
    status = "success";

    mapResult.put("mess", mess);
    mapResult.put("status", status);

    return mapResult;
  }

  @RequestMapping(value = {"i/designTable/{tableName}/{databaseName}"}, method = {org.springframework.web.bind.annotation.RequestMethod.GET})
  public String designTable(@PathVariable("tableName") String tableName,
      @PathVariable("databaseName") String databaseName, HttpServletRequest request)
      throws Exception {
    request.setAttribute("databaseName", databaseName);
    request.setAttribute("tableName", tableName);

    return "system/designTable";
  }

  @RequestMapping(value = {"i/designTableData/{tableName}/{databaseName}"}, method = {org.springframework.web.bind.annotation.RequestMethod.GET})
  @ResponseBody
  public Map<String, Object> designTableData(@PathVariable("tableName") String tableName,
      @PathVariable("databaseName") String databaseName, HttpServletRequest request)
      throws Exception {
    Map<String, Object> map = Maps.newHashMap();

    List<Map<String, Object>> listAllColumn =
        this.permissionService.getTableColumnsForDesign(databaseName, tableName);

    map.put("rows", listAllColumn);
    map.put("total", Integer.valueOf(listAllColumn.size()));

    return map;
  }

  @SuppressWarnings("unchecked")
  @RequestMapping(value = {"i/saveData"}, method = {org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public Map<String, Object> saveData(HttpServletRequest request) {
    Map<String, Object> mapResult = Maps.newHashMap();
    String databaseName = request.getParameter("databaseName");
    String tableName = request.getParameter("tableName");
    String inserted = request.getParameter("inserted");
    String updated = request.getParameter("updated");
    String primary_key = request.getParameter("primary_key");
    String mess = "";
    String status = "";
    try {
      if (inserted != null) {
        JSONArray insertArray = JSONArray.parseArray(inserted);
        for (int i = 0; i < insertArray.size(); ++i) {
          Map<String, String> map1 = (Map<String, String>) insertArray.get(i);
          Map<String, String> maps = Maps.newHashMap();
          for (String key : map1.keySet()) {
            maps.put(key, map1.get(key));
          }
          maps.remove("mainPrimaryKey");
          this.permissionService.saveRows(maps, databaseName, tableName);
        }
      }
      List<String> condition = Lists.newArrayList();
      if (updated != null) {
        JSONArray updateArray = JSONArray.parseArray(updated);
        for (int i = 0; i < updateArray.size(); ++i) {
          Map<String, Object> map1 = (Map<String, Object>) updateArray.get(i);
          Map<String, Object> map2 = (Map<String, Object>) map1.get("oldData");
          Map<String, Object> map3 = (Map<String, Object>) map1.get("changesData");
          String setStr = " set ";
          String whereStr = " where 1=1 ";
          if (map2.size() <= 0) {
            break;
          }
          if (map3.size() <= 0) {
            break;
          }
          if ((primary_key == null) || (primary_key.equals(""))) {
            for (String key : map3.keySet()) {
              if (map3.get(key) == null)
                setStr = setStr + key + " = null , ";
              else {
                setStr = setStr + key + " = '" + map3.get(key) + "',";
              }
            }
            for (String key : map2.keySet()) {
              if (map2.get(key) != null)
                whereStr = whereStr + " and " + key + " = '" + map2.get(key) + "' ";
            }
          } else {
            String[] primaryKeys = primary_key.split(",");
            for (String key : map3.keySet()) {
              if (map3.get(key) == null)
                setStr = setStr + key + " = null , ";
              else {
                setStr = setStr + key + " = '" + map3.get(key) + "',";
              }
            }
            for (String primaryKey : primaryKeys) {
              whereStr = whereStr + " and " + primaryKey + " = '" + map2.get(primaryKey) + "' ";
            }
          }
          setStr = setStr.substring(0, setStr.length() - 1);
          condition.add(setStr + whereStr);
        }
        this.permissionService.updateRowsNew(databaseName, tableName, condition);
      }
      mess = "保存成功！";
      status = "success";
    } catch (Exception e) {
      mess = "保存出错！" + e.getMessage();
      status = "fail";
    }

    mapResult.put("mess", mess);
    mapResult.put("status", status);
    return mapResult;
  }

  @SuppressWarnings("unchecked")
  @RequestMapping(value = {"i/designTableUpdate"}, method = {org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public Map<String, Object> designTableUpdate(HttpServletRequest request) {
    Map<String, Object> mapResult = Maps.newHashMap();
    String mess = "";
    String status = "";

    String databaseName = request.getParameter("databaseName");
    String tableName = request.getParameter("tableName");
    String inserted = request.getParameter("inserted");
    String updated = request.getParameter("updated");
    try {
      if (inserted != null) {
        JSONArray insertArray = JSONArray.parseArray(inserted);
        for (int i = 0; i < insertArray.size(); ++i) {
          Map<String, String> map1 = (Map<String, String>) insertArray.get(i);
          Map<String, String> maps = Maps.newHashMap();
          for (String key : map1.keySet()) {
            maps.put(key, map1.get(key));
          }

          maps.remove("mainPrimaryKey");
          this.permissionService.saveDesginColumn(maps, databaseName, tableName);
        }
      }

      if (updated != null) {
        this.permissionService.updateTableColumn(updated, databaseName, tableName);
      }

      mess = "保存成功！";
      status = "success";
    } catch (Exception e) {
      mess = "保存出错！" + e.getMessage();
      status = "fail";
    }

    mapResult.put("mess", mess);
    mapResult.put("status", status);
    return mapResult;
  }

  @RequestMapping(value = {"i/deleteTableColumn"}, method = {org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public Map<String, Object> deleteTableColumn(@RequestBody IdsDto tem) {
    String databaseName = tem.getDatabaseName();
    String tableName = tem.getTableName();
    String[] ids = tem.getIds();

    int i = 0;
    String mess = "";
    String status = "";
    try {
      this.permissionService.deleteTableColumn(databaseName, tableName, ids);
      mess = "删除成功";
      status = "success";
    } catch (Exception e) {
      mess = e.getMessage();
      status = "fail";
    }

    Map<String, Object> map = Maps.newHashMap();

    map.put("totalCount", Integer.valueOf(i));
    map.put("mess", mess);
    map.put("status", status);

    return map;
  }

  @RequestMapping(value = {"i/designTableSetNull"}, method = {org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public Map<String, Object> designTableSetNull(@RequestBody IdsDto tem) {
    String mess = "";
    String status = "";

    String databaseName = tem.getDatabaseName();
    String tableName = tem.getTableName();
    String column_name = tem.getColumn_name();
    String is_nullable = tem.getIs_nullable();
    try {
      this.permissionService.updateTableNullAble(databaseName, tableName, column_name, is_nullable);
      mess = "保存成功";
      status = "success";
    } catch (Exception e) {
      mess = e.getMessage();
      status = "fail";
    }

    Map<String, Object> map = Maps.newHashMap();

    map.put("mess", mess);
    map.put("status", status);

    return map;
  }

  @RequestMapping(value = {"i/designTableSetPimary"}, method = {org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public Map<String, Object> designTableSetPimary(@RequestBody IdsDto tem) {
    String mess = "";
    String status = "";

    String databaseName = tem.getDatabaseName();
    String tableName = tem.getTableName();
    String column_name = tem.getColumn_name();
    String column_key = tem.getColumn_key();
    try {
      this.permissionService.savePrimaryKey(databaseName, tableName, column_name, column_key);
      mess = "保存成功";
      status = "success";
    } catch (Exception e) {
      mess = e.getMessage();
      status = "fail";
    }

    Map<String, Object> map = Maps.newHashMap();

    map.put("mess", mess);
    map.put("status", status);

    return map;
  }

  @RequestMapping(value = {"i/upDownColumn"}, method = {org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public Map<String, Object> upDownColumn(@RequestBody IdsDto tem) {
    String mess = "";
    String status = "";

    String databaseName = tem.getDatabaseName();
    String tableName = tem.getTableName();
    String column_name = tem.getColumn_name();
    String column_name2 = tem.getColumn_name2();
    try {
      this.permissionService.upDownColumn(databaseName, tableName, column_name, column_name2);

      mess = "保存成功";
      status = "success";
    } catch (Exception e) {
      mess = e.getMessage();
      status = "fail";
    }

    Map<String, Object> map = Maps.newHashMap();

    map.put("mess", mess);
    map.put("status", status);

    return map;
  }


  @RequestMapping(value = {"i/monitor/{dataSourceId}"}, method = {org.springframework.web.bind.annotation.RequestMethod.GET})
  public String monitor(@PathVariable("dataSourceId") String dataSourceId,
      HttpServletRequest paramHttpServletRequest) throws Exception {
    DataSource dataSource = this.permissionService.getDataSource(dataSourceId);
    paramHttpServletRequest.setAttribute("ip", dataSource.getIp());
    paramHttpServletRequest.setAttribute("port", dataSource.getPort());
    paramHttpServletRequest.setAttribute("name", dataSource.getDataSourceName());
    paramHttpServletRequest.setAttribute("databaseType", "Mysql");
    paramHttpServletRequest.setAttribute("dataSourceId", dataSourceId);
    return "system/monitor";
  }

  @RequestMapping(value = {"i/monitorItem/{dataSourceId}"}, method = {org.springframework.web.bind.annotation.RequestMethod.GET})
  public String monitorItem(@PathVariable("dataSourceId") String dataSourceId,
      HttpServletRequest paramHttpServletRequest) throws Exception {
    paramHttpServletRequest.setAttribute("dataSourceId", dataSourceId);
    return "system/monitorItem";
  }

  @RequestMapping({"i/queryDatabaseStatus/{dataSourceId}"})
  @ResponseBody
  public Map<String, Object> queryDatabaseStatus(@PathVariable("dataSourceId") String dataSourceId) {
    Map<String, Object> localObject = Maps.newHashMap();
    String str1 = "";
    String str2 = "";
    try {
      localObject = this.permissionService.queryDatabaseStatus(dataSourceId);
      str1 = "查询成功";
      str2 = "success";
    } catch (Exception localException) {
      str1 = localException.getMessage();
      str2 = "fail";
    }
    localObject.put("mess", str1);
    localObject.put("status", str2);
    return localObject;
  }

  @RequestMapping({"i/monitorItemValue/{dataSourceId}"})
  @ResponseBody
  public Map<String, Object> monitorItemValue(@PathVariable("dataSourceId") String dataSourceId)
      throws Exception {
    List<Map<String, Object>> localObject = Lists.newArrayList();
    Map<String, Object> localHashMap = Maps.newHashMap();
    String str2, str3 = StringUtils.EMPTY;
    try {
      localObject = this.permissionService.monitorItemValue(dataSourceId);
      str2 = "查询成功";
      str3 = "success";
    } catch (Exception localException) {
      str2 = localException.getMessage();
      str3 = "fail";
      return null;
    }
    localHashMap.put("mess", str2);
    localHashMap.put("status", str3);
    localHashMap.put("rows", localObject);
    localHashMap.put("total", Integer.valueOf(localObject.size()));
    return localHashMap;
  }

  @RequestMapping(value = {"i/admin"}, method = {org.springframework.web.bind.annotation.RequestMethod.GET})
  public String admin(Model model) {
    return "system/adminList";
  }

  @RequestMapping(value = {"i/adminList"}, method = {org.springframework.web.bind.annotation.RequestMethod.GET})
  @ResponseBody
  public Map<String, Object> adminList(HttpServletRequest paramHttpServletRequest) throws Exception {
    Page<Role> localPage = getPage(paramHttpServletRequest);
    List<Role> roleList = this.permissionService.getAllAdmin();
    if (null != roleList && !roleList.isEmpty()) {
      localPage.setResult(roleList);
    }
    return getEasyUIData(localPage);
  }

  @RequestMapping(value = {"i/addRoleForm"}, method = {org.springframework.web.bind.annotation.RequestMethod.GET})
  public String addRoleForm(Model paramModel, HttpServletRequest request) {
    HttpSession session = request.getSession(true);
    String token = session.getAttribute("TOKEN").toString();
    if (!permissionService.checkPrivilege(token)) {
      return "system/noPrivilege";
    }
    return "system/adminForm";
  }

  @RequestMapping(value = {"i/editRoleForm/{id}"}, method = {org.springframework.web.bind.annotation.RequestMethod.GET})
  public String editRoleForm(@PathVariable("id") String rid, Model paramModel,
      HttpServletRequest request) {
    HttpSession session = request.getSession(true);
    String token = session.getAttribute("TOKEN").toString();
    if (!permissionService.checkPrivilege(token)) {
      return "system/noPrivilege";
    }
    Role role = this.permissionService.getRole(rid);
    if (null != role) {
      paramModel.addAttribute("role", role);
    }
    return "system/adminForm";
  }

  @RequestMapping(value = {"i/roleUpdate"}, method = {org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public Map<String, Object> roleUpdate(@ModelAttribute @RequestBody Role role, Model model,
      HttpServletRequest request) {
    Map<String, Object> ret = Maps.newHashMap();
    HttpSession session = request.getSession(true);
    String token = session.getAttribute("TOKEN").toString();
    if (!permissionService.checkPrivilege(token)) {
      ret.put("status", "noPrivileges");
      ret.put("mess", "您不具备操作权限, 请联系管理员.");
      return ret;
    }
    if (StringUtils.isBlank(role.getRid())) {
      role.setRid(UUID.randomUUID().toString());
      role.setNewPassword(role.getPassword());
      this.permissionService.roleInsert(role);
    } else {
      Role _role = this.permissionService.getRole(role.getRid());
      if (StringUtils.isBlank(_role.getUserId())) {
        _role.setNewPassword(role.getPassword());
      }
      _role.setType(role.getType());
      this.permissionService.roleUpdate(_role);
    }
    ret.put("status", "success");
    ret.put("mess", "操作成功.");
    return ret;
  }
}
