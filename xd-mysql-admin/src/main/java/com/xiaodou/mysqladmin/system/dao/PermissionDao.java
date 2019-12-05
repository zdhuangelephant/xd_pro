package com.xiaodou.mysqladmin.system.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xiaodou.mysqladmin.common.persistence.Page;
import com.xiaodou.mysqladmin.system.entity.DataSource;
import com.xiaodou.mysqladmin.system.utils.Constants;
import com.xiaodou.mysqladmin.system.utils.DBUtil2;
import com.xiaodou.mysqladmin.system.utils.DataSourceWrapper;

@Repository
public class PermissionDao {

  @Autowired
  DataSourceDao dataSourceDao;

  public List<Map<String, Object>> getAllDataBase(String dataSourceId) throws Exception {
    String sql = " select * from  information_schema.schemata  ";
    DataSource ds = dataSourceDao.getDataSource(dataSourceId);
    DBUtil2 db2 =
        new DBUtil2(ds.getDriver(), ds.getIp(), ds.getPort(),
            Constants.DATA_SOURCE_INFORMATION_TABLE, ds.getUserName(), ds.getPasswrod());
    return db2.queryForList(sql);
  }

  public List<Map<String, Object>> getAllTables(String dbName) throws Exception {
    String sql =
        " select table_name from information_schema.TABLES where table_schema='" + dbName
            + "' and table_type='BASE TABLE' ";
    DataSource ds = dataSourceDao.getDataSource(DataSourceWrapper.getWrapper().getDataSourceId());
    DBUtil2 db2 =
        new DBUtil2(ds.getDriver(), ds.getIp(), ds.getPort(), dbName, ds.getUserName(),
            ds.getPasswrod());
    List<Map<String, Object>> list = db2.queryForList(sql);
    return list;
  }

  public List<Map<String, Object>> getAllViews(String dbName) throws Exception {
    String sql =
        " select table_name   from information_schema.TABLES where table_schema='" + dbName
            + "' and table_type='VIEW' ";
    DataSource ds = dataSourceDao.getDataSource(DataSourceWrapper.getWrapper().getDataSourceId());
    DBUtil2 db2 =
        new DBUtil2(ds.getDriver(), ds.getIp(), ds.getPort(), dbName, ds.getUserName(),
            ds.getPasswrod());
    List<Map<String, Object>> list = db2.queryForList(sql);
    return list;
  }

  public List<Map<String, Object>> getAllFuntion(String dbName) throws Exception {
    String sql =
        " select routine_name   from information_schema.ROUTINES where routine_schema='" + dbName
            + "' ";
    DataSource ds = dataSourceDao.getDataSource(DataSourceWrapper.getWrapper().getDataSourceId());
    DBUtil2 db2 =
        new DBUtil2(ds.getDriver(), ds.getIp(), ds.getPort(), dbName, ds.getUserName(),
            ds.getPasswrod());
    List<Map<String, Object>> list = db2.queryForList(sql);
    return list;
  }

  public List<Map<String, Object>> getTableColumnsForDesign(String dbName, String tableName)
      throws Exception {
    String sql = "select * from  " + tableName + " limit 1 ";
    DataSource ds = dataSourceDao.getDataSource(DataSourceWrapper.getWrapper().getDataSourceId());
    DBUtil2 db2 =
        new DBUtil2(ds.getDriver(), ds.getIp(), ds.getPort(), dbName, ds.getUserName(),
            ds.getPasswrod());
    List<Map<String, Object>> list = db2.queryForColumnOnly(sql);
    return list;
  }

  public Map<String, List<Map<String, Object>>> getAllTableColumns(String dbName) throws Exception {
    String sql =
        " select column_name as mainPrimaryKey, table_name,column_name,column_type , data_type ,character_maximum_length,is_nullable, column_key, column_comment  from information_schema.columns where table_schema='"
            + dbName + "'  ";
    DataSource ds = dataSourceDao.getDataSource(DataSourceWrapper.getWrapper().getDataSourceId());
    DBUtil2 db2 =
        new DBUtil2(ds.getDriver(), ds.getIp(), ds.getPort(), dbName, ds.getUserName(),
            ds.getPasswrod());
    List<Map<String, Object>> list = db2.queryForList(sql);
    Map<String, List<Map<String, Object>>> map = Maps.newHashMap();
    for (Map<String, Object> _map : list) {
      if (_map.containsKey("table_name")) {
        String tableName = _map.get("table_name").toString();
        if (!map.containsKey(tableName)) {
          List<Map<String, Object>> columnList = Lists.newArrayList();
          map.put(tableName, columnList);
        }
        map.get(tableName).add(_map);
      }
    }
    return map;
  }

  public Map<String, List<Map<String, Object>>> getAllTableColumns3(String dbName) throws Exception {
    String sql =
        " select column_name as mainPrimaryKey, table_name,column_name,column_type , data_type ,character_maximum_length,is_nullable, column_key, column_comment  from information_schema.columns where table_schema='"
            + dbName + "'  ";
    DataSource ds = dataSourceDao.getDataSource(DataSourceWrapper.getWrapper().getDataSourceId());
    DBUtil2 db2 =
        new DBUtil2(ds.getDriver(), ds.getIp(), ds.getPort(), dbName, ds.getUserName(),
            ds.getPasswrod());
    List<Map<String, Object>> list = db2.queryForList(sql);
    Map<String, List<Map<String, Object>>> map = Maps.newHashMap();
    for (Map<String, Object> _map : list) {
      if (_map.containsKey("table_name")) {
        String tableName = _map.get("table_name").toString();
        if (!map.containsKey(tableName)) {
          List<Map<String, Object>> columnList = Lists.newArrayList();
          map.put(tableName, columnList);
        }
        map.get(tableName).add(_map);
      }
    }
    return map;
  }

  public Page<Map<String, Object>> getData(Page<Map<String, Object>> page, String tableName,
      String dbName) throws Exception {
    int pageNo = page.getPageNo();
    int pageSize = page.getPageSize();
    int limitFrom = (pageNo - 1) * pageSize;
    String orderBy = page.getOrderBy();
    String order = page.getOrder();
    List<Map<String, Object>> list3 = getPrimaryKeyss(dbName, tableName);
    String tem = "";
    for (Map<String, Object> map : list3) {
      tem = tem + map.get("column_name") + ",";
    }
    String primaryKey = "";
    if (!tem.equals("")) {
      primaryKey = tem.substring(0, tem.length() - 1);
    }
    String sql = "select count(*) from  " + tableName;
    String sql2 = "";
    if ((orderBy == null) || (orderBy.equals("")))
      sql2 = "select  *  from  " + tableName + "  LIMIT " + limitFrom + "," + pageSize;
    else {
      sql2 =
          "select  *  from  " + tableName + " order by " + orderBy + " " + order + "  LIMIT "
              + limitFrom + "," + pageSize;
    }
    DataSource ds = dataSourceDao.getDataSource(DataSourceWrapper.getWrapper().getDataSourceId());
    DBUtil2 db2 =
        new DBUtil2(ds.getDriver(), ds.getIp(), ds.getPort(), dbName, ds.getUserName(),
            ds.getPasswrod());
    List<Map<String, Object>> list = db2.queryForList(sql2);
    int rowCount = db2.executeQueryForCount(sql);
    List<Map<String, Object>> columns = getTableColumnsForDesign(dbName, tableName);
    List<Map<String, Object>> tempList = Lists.newArrayList();
    Map<String, Object> map1 = Maps.newHashMap();
    map1.put("field", "mainPrimaryKey");
    map1.put("checkbox", Boolean.valueOf(true));
    tempList.add(map1);
    for (Map<String, Object> map : columns) {
      Map<String, Object> map2 = Maps.newHashMap();
      map2.put("field", map.get("column_name"));
      map2.put("title", map.get("column_name"));
      map2.put("sortable", Boolean.valueOf(true));
      map2.put("editor", "text");
      if (map.get("data_type").equals("DATETIME")) {
        map2.put("editor", "datebox");
      } else if ((map.get("data_type").equals("INT")) || (map.get("data_type").equals("SMALLINT"))
          || (map.get("data_type").equals("TINYINT"))) {
        map2.put("editor", "numberbox");
      } else if (map.get("data_type").equals("DOUBLE")) {
        map2.put("editor", "numberbox");
      } else {
        map2.put("editor", "text");
      }
      tempList.add(map2);
    }
    ObjectMapper mapper = new ObjectMapper();
    String jsonfromList = "[" + mapper.writeValueAsString(tempList) + "]";
    page.setTotalCount(rowCount);
    page.setResult(list);
    page.setColumns(jsonfromList);
    page.setPrimaryKey(primaryKey);
    return page;
  }

  public Page<Map<String, Object>> executeSql(Page<Map<String, Object>> page, String sql,
      String dbName) throws Exception {
    int pageNo = page.getPageNo();
    int pageSize = page.getPageSize();
    int limitFrom = (pageNo - 1) * pageSize;
    String sql2 = sql + "  LIMIT " + limitFrom + "," + pageSize;
    DataSource ds = dataSourceDao.getDataSource(DataSourceWrapper.getWrapper().getDataSourceId());
    DBUtil2 db2 =
        new DBUtil2(ds.getDriver(), ds.getIp(), ds.getPort(), dbName, ds.getUserName(),
            ds.getPasswrod());
    List<Map<String, Object>> list = db2.queryForList(sql2);
    int rowCount = db2.executeQueryForCount2(sql);
    List<Map<String, Object>> columns = executeSqlForColumns(sql, dbName);
    List<Map<String, Object>> tempList = Lists.newArrayList();
    for (Map<String, Object> map : columns) {
      Map<String, Object> map2 = Maps.newHashMap();
      map2.put("field", map.get("column_name"));
      map2.put("title", map.get("column_name"));
      map2.put("sortable", Boolean.valueOf(true));
      tempList.add(map2);
    }
    ObjectMapper mapper = new ObjectMapper();
    String jsonfromList = "[" + mapper.writeValueAsString(tempList) + "]";

    page.setTotalCount(rowCount);
    page.setResult(list);
    page.setColumns(jsonfromList);

    return page;
  }

  public List<Map<String, Object>> executeSqlForColumns(String sql, String dbName) throws Exception {
    String sql2 = sql + "  limit 1 ";
    DataSource ds = dataSourceDao.getDataSource(DataSourceWrapper.getWrapper().getDataSourceId());
    DBUtil2 db2 =
        new DBUtil2(ds.getDriver(), ds.getIp(), ds.getPort(), dbName, ds.getUserName(),
            ds.getPasswrod());
    List<Map<String, Object>> list = db2.executeSqlForColumns(sql2);
    return list;
  }

  public int executeSqlNotRes(String sql, String dbName) throws Exception {
    DataSource ds = dataSourceDao.getDataSource(DataSourceWrapper.getWrapper().getDataSourceId());
    DBUtil2 db2 =
        new DBUtil2(ds.getDriver(), ds.getIp(), ds.getPort(), dbName, ds.getUserName(),
            ds.getPasswrod());
    int i = db2.setupdateData(sql);
    return i;
  }

  public int deleteRows(String dbName, String tableName, String primary_key, String[] ids)
      throws Exception {
    DataSource ds = dataSourceDao.getDataSource(DataSourceWrapper.getWrapper().getDataSourceId());
    DBUtil2 db2 =
        new DBUtil2(ds.getDriver(), ds.getIp(), ds.getPort(), dbName, ds.getUserName(),
            ds.getPasswrod());
    int y = 0;
    for (int i = 0; i < ids.length; ++i) {
      String sql = " delete from  " + tableName + " where " + primary_key + " ='" + ids[i] + "'";
      y += db2.setupdateData(sql);
    }
    return y;
  }

  public int deleteRowsNew(String dbName, String tableName, String primary_key,
      List<String> condition) throws Exception {
    int y = 0;
    DataSource ds = dataSourceDao.getDataSource(DataSourceWrapper.getWrapper().getDataSourceId());
    DBUtil2 db2 =
        new DBUtil2(ds.getDriver(), ds.getIp(), ds.getPort(), dbName, ds.getUserName(),
            ds.getPasswrod());
    for (int i = 0; i < condition.size(); ++i) {
      String whereStr = (String) condition.get(i);
      String sql = " delete from  " + tableName + " where  1=1 " + whereStr;
      y += db2.setupdateData(sql);
    }
    return y;
  }

  public int saveRows(Map<String, String> map, String dbName, String tableName) throws Exception {
    String sql = " insert into " + tableName;
    int y = 0;
    String colums = " ";
    String values = " ";
    for (Map.Entry<String, String> entry : map.entrySet()) {
      colums = colums + (String) entry.getKey() + ",";

      String str = (String) entry.getValue();
      if (str.equals(""))
        values = values + " null ,";
      else {
        values = values + "'" + (String) entry.getValue() + "',";
      }
    }

    colums = colums.substring(0, colums.length() - 1);
    values = values.substring(0, values.length() - 1);
    sql = sql + " (" + colums + ") values (" + values + ")";
    DataSource ds = dataSourceDao.getDataSource(DataSourceWrapper.getWrapper().getDataSourceId());
    DBUtil2 db2 =
        new DBUtil2(ds.getDriver(), ds.getIp(), ds.getPort(), dbName, ds.getUserName(),
            ds.getPasswrod());
    y = db2.setupdateData(sql);
    return y;
  }

  public List<Map<String, Object>> getOneRowById(String dbName, String tableName, String id,
      String idValues) {
    String sql2 = " select * from   " + tableName + " where " + id + "='" + idValues + "' ";
    DataSource ds = dataSourceDao.getDataSource(DataSourceWrapper.getWrapper().getDataSourceId());
    DBUtil2 db2 =
        new DBUtil2(ds.getDriver(), ds.getIp(), ds.getPort(), dbName, ds.getUserName(),
            ds.getPasswrod());
    List<Map<String, Object>> list = db2.queryForListWithType(sql2);
    return list;
  }

  public int updateRows(Map<String, String> map, String dbName, String tableName, String id,
      String idValues) throws Exception {
    if ((id == null) || ("".equals(id))) {
      throw new Exception("数据不完整,保存失败!");
    }

    if ((idValues == null) || ("".equals(idValues))) {
      throw new Exception("数据不完整,保存失败!");
    }

    String sql = " update  " + tableName;

    int y = 0;
    String ss = " set  ";

    for (Map.Entry<String, String> entry : map.entrySet()) {
      String str = entry.getValue();
      if (str.equals("")) {
        ss = ss + (String) entry.getKey() + "= null ,";
      } else if (entry.getValue() instanceof String)
        ss = ss + (String) entry.getKey() + "= '" + entry.getValue() + "',";
      else {
        ss = ss + (String) entry.getKey() + "= " + entry.getValue() + ",";
      }

    }

    ss = ss.substring(0, ss.length() - 1);
    sql = sql + ss + " where " + id + "='" + idValues + "'";
    DataSource ds = dataSourceDao.getDataSource(DataSourceWrapper.getWrapper().getDataSourceId());
    DBUtil2 db2 =
        new DBUtil2(ds.getDriver(), ds.getIp(), ds.getPort(), dbName, ds.getUserName(),
            ds.getPasswrod());
    y = db2.setupdateData(sql);
    return y;
  }

  public String getViewSql(String dbName, String tableName) throws Exception {
    String sql =
        " select  view_definition  from  information_schema.VIEWS  where  table_name='" + tableName
            + "' and table_schema='" + dbName + "'  ";
    String str = "";
    DataSource ds = dataSourceDao.getDataSource(DataSourceWrapper.getWrapper().getDataSourceId());
    DBUtil2 db2 =
        new DBUtil2(ds.getDriver(), ds.getIp(), ds.getPort(), dbName, ds.getUserName(),
            ds.getPasswrod());
    List<Map<String, Object>> list = db2.queryForList(sql);
    if (list.size() == 1) {
      Map<String, Object> map = list.get(0);
      str = (String) map.get("view_definition");
    }
    return str;
  }

  public List<Map<String, Object>> getTableColumns2(String dbName, String tableName)
      throws Exception {
    String sql = "select * from  " + tableName + " limit 1";
    DataSource ds = dataSourceDao.getDataSource(DataSourceWrapper.getWrapper().getDataSourceId());
    DBUtil2 db2 =
        new DBUtil2(ds.getDriver(), ds.getIp(), ds.getPort(), dbName, ds.getUserName(),
            ds.getPasswrod());
    List<Map<String, Object>> list = db2.queryForColumnOnly(sql);
    return list;
  }

  public String getPrimaryKeys(String dbName, String tableName) {
    DataSource ds = dataSourceDao.getDataSource(DataSourceWrapper.getWrapper().getDataSourceId());
    DBUtil2 db2 =
        new DBUtil2(ds.getDriver(), ds.getIp(), ds.getPort(), dbName, ds.getUserName(),
            ds.getPasswrod());
    return db2.getPrimaryKeys(dbName, tableName);
  }

  public List<Map<String, Object>> getPrimaryKeyss(String dbName, String tableName)
      throws Exception {
    String sql =
        " select   column_name  from information_schema.columns where   table_name='" + tableName
            + "' and table_schema='" + dbName + "' and column_key='PRI' ";
    DataSource ds = dataSourceDao.getDataSource(DataSourceWrapper.getWrapper().getDataSourceId());
    DBUtil2 db2 =
        new DBUtil2(ds.getDriver(), ds.getIp(), ds.getPort(), dbName, ds.getUserName(),
            ds.getPasswrod());
    List<Map<String, Object>> list = db2.queryForList(sql);
    return list;
  }

  public boolean testConn(String driverUrl, String dbName, String ip, String port, String user,
      String pass) {
    DBUtil2 db2 = new DBUtil2(driverUrl, ip, port, dbName, user, pass);
    return db2.testConnection();
  }

  public int saveDesginColumn(Map<String, String> map, String dbName, String tableName)
      throws Exception {
    String sql = " alter table " + tableName + " add column ";

    sql = sql + (String) map.get("column_name") + "  ";
    sql = sql + (String) map.get("data_type");

    if (map.get("character_maximum_length") != null) {
      sql = sql + " (" + (String) map.get("character_maximum_length") + ") ";
    }

    if ((map.get("column_comment") != null) && (!((String) map.get("column_comment")).equals(""))) {
      sql = sql + " comment '" + (String) map.get("column_comment") + "'";
    }

    int y = 0;
    DataSource ds = dataSourceDao.getDataSource(DataSourceWrapper.getWrapper().getDataSourceId());
    DBUtil2 db2 =
        new DBUtil2(ds.getDriver(), ds.getIp(), ds.getPort(), dbName, ds.getUserName(),
            ds.getPasswrod());
    y = db2.setupdateData(sql);

    return y;
  }

  public int deleteTableColumn(String dbName, String tableName, String[] ids) throws Exception {
    int y = 0;
    DataSource ds = dataSourceDao.getDataSource(DataSourceWrapper.getWrapper().getDataSourceId());
    DBUtil2 db2 =
        new DBUtil2(ds.getDriver(), ds.getIp(), ds.getPort(), dbName, ds.getUserName(),
            ds.getPasswrod());
    for (int i = 0; i < ids.length; ++i) {
      String sql = " alter table   " + tableName + " drop column  " + ids[i];

      y += db2.setupdateData(sql);
    }
    return y;
  }

  public int updateTableColumn(Map<String, String> map, String dbName, String tableName,
      String columnName, String idValues) throws Exception {
    if ((columnName == null) || ("".equals(columnName))) {
      throw new Exception("数据不完整,保存失败!");
    }

    if ((idValues == null) || ("".equals(idValues))) {
      throw new Exception("数据不完整,保存失败!");
    }

    String old_field_name = (String) map.get("mainPrimaryKey");
    String column_name = (String) map.get("column_name");
    String data_type = (String) map.get("data_type");
    String character_maximum_length = map.get("character_maximum_length");
    String column_comment = (String) map.get("column_comment");
    DataSource ds = dataSourceDao.getDataSource(DataSourceWrapper.getWrapper().getDataSourceId());
    DBUtil2 db2 =
        new DBUtil2(ds.getDriver(), ds.getIp(), ds.getPort(), dbName, ds.getUserName(),
            ds.getPasswrod());
    if (!old_field_name.endsWith(column_name)) {
      String sql = " alter table  " + tableName + " change ";
      sql = sql + old_field_name + " " + column_name;

      db2.setupdateData(sql);
    }

    String sql2 = " alter table  " + tableName + " modify column " + column_name + " " + data_type;

    if ((character_maximum_length != null) && (!character_maximum_length.equals(""))) {
      sql2 = sql2 + " (" + character_maximum_length + ")";
    }

    if ((column_comment != null) && (!column_comment.equals(""))) {
      sql2 = sql2 + " comment '" + column_comment + "'";
    }

    int y = db2.setupdateData(sql2);

    return y;
  }

  public int dropPrimaryKey(String dbName, String tableName) throws Exception {
    String sql4 = " alter table  " + tableName + " drop primary key ";
    DataSource ds = dataSourceDao.getDataSource(DataSourceWrapper.getWrapper().getDataSourceId());
    DBUtil2 db2 =
        new DBUtil2(ds.getDriver(), ds.getIp(), ds.getPort(), dbName, ds.getUserName(),
            ds.getPasswrod());
    db2.setupdateData(sql4);

    return 0;
  }

  public int savePrimaryKey2(String dbName, String tableName, String primaryKeys) throws Exception {
    String sql4 = "";
    DataSource ds = dataSourceDao.getDataSource(DataSourceWrapper.getWrapper().getDataSourceId());
    DBUtil2 db2 =
        new DBUtil2(ds.getDriver(), ds.getIp(), ds.getPort(), dbName, ds.getUserName(),
            ds.getPasswrod());
    if ((primaryKeys != null) && (!primaryKeys.equals(""))) {
      sql4 = " alter table  " + tableName + " add primary key (" + primaryKeys + ")";

      db2.setupdateData(sql4);
    }
    return 0;
  }

  public int savePrimaryKey(String dbName, String tableName, String column_name, String isSetting)
      throws Exception {
    String sql4 = "";
    if ((column_name != null) && (!column_name.equals(""))) {
      List<String> list2 = selectTablePrimaryKey(dbName, tableName);
      if (isSetting.equals("true"))
        list2.add(column_name);
      else {
        list2.remove(column_name);
      }

      String tem = list2.toString();
      String primaryKey = tem.substring(1, tem.length() - 1);

      if (primaryKey.equals(""))
        sql4 = " alter table  " + tableName + " drop primary key ";
      else if ((list2.size() == 1) && (isSetting.equals("true")))
        sql4 = " alter table  " + tableName + " add primary key (" + primaryKey + ")";
      else {
        sql4 =
            " alter table  " + tableName + " drop primary key, add primary key (" + primaryKey
                + ")";
      }
      DataSource ds = dataSourceDao.getDataSource(DataSourceWrapper.getWrapper().getDataSourceId());
      DBUtil2 db2 =
          new DBUtil2(ds.getDriver(), ds.getIp(), ds.getPort(), dbName, ds.getUserName(),
              ds.getPasswrod());
      db2.setupdateData(sql4);
    }

    return 0;
  }

  public List<String> selectTablePrimaryKey(String dbName, String tableName) throws Exception {
    String sql =
        " select column_name   from information_schema.columns where   table_name='" + tableName
            + "' and table_schema='" + dbName + "'  and column_key='PRI' ";

    DataSource ds = dataSourceDao.getDataSource(DataSourceWrapper.getWrapper().getDataSourceId());
    DBUtil2 db2 =
        new DBUtil2(ds.getDriver(), ds.getIp(), ds.getPort(), dbName, ds.getUserName(),
            ds.getPasswrod());
    List<Map<String, Object>> list = db2.queryForList(sql);

    List<String> list2 = Lists.newArrayList();

    for (Map<String, Object> map : list) {
      list2.add(map.get("column_name").toString());
    }

    return list2;
  }

  public String selectOneColumnType(String dbName, String tableName, String column_name)
      throws Exception {
    String sql =
        " select   column_type  from information_schema.columns where   table_name='" + tableName
            + "' and table_schema='" + dbName + "' and column_name='" + column_name + "'";
    DataSource ds = dataSourceDao.getDataSource(DataSourceWrapper.getWrapper().getDataSourceId());
    DBUtil2 db2 =
        new DBUtil2(ds.getDriver(), ds.getIp(), ds.getPort(), dbName, ds.getUserName(),
            ds.getPasswrod());
    List<Map<String, Object>> list = db2.queryForList(sql);
    return list.get(0).get("column_type").toString();
  }

  public int updateTableNullAble(String dbName, String tableName, String column_name,
      String is_nullable) throws Exception {
    String sql4 = "";
    if ((column_name != null) && (!column_name.equals(""))) {
      String column_type = selectOneColumnType(dbName, tableName, column_name);

      if (is_nullable.equals("true"))
        sql4 =
            " alter table  " + tableName + " modify column " + column_name + " " + column_type
                + "  null ";
      else {
        sql4 =
            " alter table  " + tableName + " modify column " + column_name + " " + column_type
                + " not null ";
      }
      DataSource ds = dataSourceDao.getDataSource(DataSourceWrapper.getWrapper().getDataSourceId());
      DBUtil2 db2 =
          new DBUtil2(ds.getDriver(), ds.getIp(), ds.getPort(), dbName, ds.getUserName(),
              ds.getPasswrod());
      db2.setupdateData(sql4);
    }
    return 0;
  }

  public int upDownColumn(String dbName, String tableName, String column_name, String column_name2)
      throws Exception {
    String sql4 = "";
    if ((column_name != null) && (!column_name.equals(""))) {
      String column_type = selectOneColumnType(dbName, tableName, column_name);
      if ((column_name2 == null) || (column_name2.equals("")))
        sql4 =
            " alter table  " + tableName + " modify column " + column_name + " " + column_type
                + " first ";
      else {
        sql4 =
            " alter table  " + tableName + " modify column " + column_name + " " + column_type
                + " after " + column_name2;
      }
      DataSource ds = dataSourceDao.getDataSource(DataSourceWrapper.getWrapper().getDataSourceId());
      DBUtil2 db2 =
          new DBUtil2(ds.getDriver(), ds.getIp(), ds.getPort(), dbName, ds.getUserName(),
              ds.getPasswrod());
      db2.setupdateData(sql4);
    }
    return 0;
  }

  public Map<String, Object> queryDatabaseStatus(String dataSourceId) throws Exception {
    DataSource ds = dataSourceDao.getDataSource(dataSourceId);
    DBUtil2 db2 =
        new DBUtil2(ds.getDriver(), ds.getIp(), ds.getPort(),
            Constants.DATA_SOURCE_INFORMATION_TABLE, ds.getUserName(), ds.getPasswrod());
    String str1 = " show global status  ";
    List<Map<String, Object>> localList = db2.queryForList(str1);
    HashMap<String, Object> localHashMap = Maps.newHashMap();
    String str2 = "";
    String str3 = "";
    for (int i = 0; i < localList.size(); ++i) {
      Map<String, Object> localMap = localList.get(i);
      str2 = (String) localMap.get("Variable_name");
      str3 = (String) localMap.get("Value");
      localHashMap.put(str2, str3);
    }
    return localHashMap;
  }

  public List<Map<String, Object>> monitorItemValue(String dataSourceId) throws Exception {
    DataSource ds = dataSourceDao.getDataSource(dataSourceId);
    DBUtil2 db2 =
        new DBUtil2(ds.getDriver(), ds.getIp(), ds.getPort(),
            Constants.DATA_SOURCE_INFORMATION_TABLE, ds.getUserName(), ds.getPasswrod());
    String str1 = " show global status  ";
    List<Map<String, Object>> localArrayList = Lists.newArrayList();
    List<Map<String, Object>> localList = db2.queryForList(str1);
    String str2 = "";
    for (int i = 0; i < localList.size(); ++i) {
      Map<String, Object> localMap = localList.get(i);
      str2 = (String) localMap.get("Variable_name");
      if (str2.equals("Com_select"))
        localMap.put("descript", "select数量");
      else if (str2.equals("Com_update"))
        localMap.put("descript", "更新数量");
      else if (str2.equals("Open_tables"))
        localMap.put("descript", "当前打开的表的数量");
      else if (str2.equals("Open_files"))
        localMap.put("descript", "打开的文件的数目");
      else if (str2.equals("Opened_tables"))
        localMap.put("descript", "已经打开的表的数量");
      else if (str2.equals("Questions"))
        localMap.put("descript", "已经发送给服务器的查询的个数");
      else
        localMap.put("descript", "");
      localArrayList.add(localMap);
    }
    return localArrayList;
  }
}
