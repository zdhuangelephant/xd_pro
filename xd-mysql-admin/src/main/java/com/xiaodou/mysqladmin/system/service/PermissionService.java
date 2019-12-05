package com.xiaodou.mysqladmin.system.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.google.common.collect.Maps;
import com.xiaodou.common.util.CommUtil;
import com.xiaodou.mysqladmin.common.persistence.Page;
import com.xiaodou.mysqladmin.system.dao.DataSourceDao;
import com.xiaodou.mysqladmin.system.dao.PermissionDao;
import com.xiaodou.mysqladmin.system.dao.RoleDao;
import com.xiaodou.mysqladmin.system.dao.SearchHistoryMongoDao;
import com.xiaodou.mysqladmin.system.entity.DataSource;
import com.xiaodou.mysqladmin.system.entity.Role;
import com.xiaodou.mysqladmin.system.entity.SearchHistory;
import com.xiaodou.mysqladmin.system.utils.Constants;
import com.xiaodou.summer.dao.param.IQueryParam;
import com.xiaodou.summer.dao.param.QueryParam;

@Service
@Transactional(readOnly = true)
public class PermissionService {

  @Autowired
  private RoleDao roleDao;
  @Autowired
  private DataSourceDao dataSourceDao;
  @Autowired
  private PermissionDao permissionDao;
  @Autowired
  private SearchHistoryMongoDao serachHistoryMongoDao;

  public DataSource getDataSource(String sid) {
    DataSource entity = new DataSource();
    entity.setSid(sid);
    return this.dataSourceDao.findEntityById(entity);
  }

  public List<DataSource> getAllDataSource() {
    IQueryParam param = new QueryParam();
    param.addOutputs(CommUtil.getAllField(DataSource.class));
    com.xiaodou.summer.dao.pagination.Page<DataSource> dataSourcePage =
        this.dataSourceDao.findEntityListByCond(param, null);
    if (null != dataSourcePage && dataSourcePage.getResult() != null
        && dataSourcePage.getResult().size() > 0) {
      return dataSourcePage.getResult();
    }
    return null;
  }

  public List<Map<String, Object>> getAllDataBase(String dbName) throws Exception {
    return this.permissionDao.getAllDataBase(dbName);
  }

  public List<Map<String, Object>> getAllTables(String dbName) throws Exception {
    return this.permissionDao.getAllTables(dbName);
  }

  public List<Map<String, Object>> getAllViews(String dbName) throws Exception {
    return this.permissionDao.getAllViews(dbName);
  }

  public List<Map<String, Object>> getAllFuntion(String dbName) throws Exception {
    return this.permissionDao.getAllFuntion(dbName);
  }

  public List<Map<String, Object>> getTableColumnsForDesign(String dbName, String tableName)
      throws Exception {
    return this.permissionDao.getTableColumnsForDesign(dbName, tableName);
  }

  public Page<Map<String, Object>> getData(Page<Map<String, Object>> page, String tableName,
      String dbName) throws Exception {
    return this.permissionDao.getData(page, tableName, dbName);
  }

  public Page<Map<String, Object>> executeSql(Page<Map<String, Object>> page, String sql,
      String dbName) throws Exception {
    return this.permissionDao.executeSql(page, sql, dbName);
  }

  public List<Map<String, Object>> executeSqlForColumns(String sql, String dbName) throws Exception {
    return this.permissionDao.executeSqlForColumns(sql, dbName);
  }

  public boolean saveSearchHistory(String name, String sql, String dbName, String userId) {
    this.serachHistoryMongoDao.saveSearchHistory(name, sql, dbName, userId);
    return true;
  }

  public List<SearchHistory> selectSearchHistory() {
    return this.serachHistoryMongoDao.selectSearchHistory();
  }

  public DataSource configInsert(DataSource config) {
    return this.dataSourceDao.addEntity(config);
  }

  public boolean configUpdate(DataSource config) {
    return this.dataSourceDao.updateEntityById(config);
  }

  public int executeSqlNotRes(String sql, String dbName) throws Exception {
    return this.permissionDao.executeSqlNotRes(sql, dbName);
  }

  public int deleteRows(String databaseName, String tableName, String primary_key, String[] ids)
      throws Exception {
    return this.permissionDao.deleteRows(databaseName, tableName, primary_key, ids);
  }

  public int deleteRowsNew(String databaseName, String tableName, String primary_key,
      List<String> condition) throws Exception {
    return this.permissionDao.deleteRowsNew(databaseName, tableName, primary_key, condition);
  }

  public int saveRows(Map<String, String> map, String databaseName, String tableName)
      throws Exception {
    return this.permissionDao.saveRows(map, databaseName, tableName);
  }

  public List<Map<String, Object>> getOneRowById(String databaseName, String tableName, String id,
      String idValues) {
    return this.permissionDao.getOneRowById(databaseName, tableName, id, idValues);
  }

  public int updateRows(Map<String, String> map, String databaseName, String tableName, String id,
      String idValues) throws Exception {
    return this.permissionDao.updateRows(map, databaseName, tableName, id, idValues);
  }

  public int updateRowsNew(String databaseName, String tableName, List<String> strList)
      throws Exception {
    String sql = "";
    for (String str1 : strList) {
      if ((str1 == null) || ("".equals(str1))) {
        throw new Exception("数据不完整,保存失败!");
      }
      sql = " update  " + databaseName + "." + tableName + str1;

      this.permissionDao.executeSqlNotRes(sql, databaseName);
    }
    return 0;
  }

  public String getViewSql(String databaseName, String tableName) throws Exception {
    return this.permissionDao.getViewSql(databaseName, tableName);
  }

  public List<Map<String, Object>> getTableColumns2(String databaseName, String tableName)
      throws Exception {
    return this.permissionDao.getTableColumns2(databaseName, tableName);
  }

  public Map<String, List<Map<String, Object>>> getAllTableColumns(String databaseName)
      throws Exception {
    return this.permissionDao.getAllTableColumns(databaseName);
  }

  public String getPrimaryKeys(String databaseName, String tableName) {
    return this.permissionDao.getPrimaryKeys(databaseName, tableName);
  }

  public boolean testConn(String driverUrl, String databaseName, String ip, String port,
      String user, String pass) {
    return this.permissionDao.testConn(driverUrl, databaseName, ip, port, user, pass);
  }

  public int saveDesginColumn(Map<String, String> map, String databaseName, String tableName)
      throws Exception {
    return this.permissionDao.saveDesginColumn(map, databaseName, tableName);
  }

  public int deleteTableColumn(String databaseName, String tableName, String[] ids)
      throws Exception {
    return this.permissionDao.deleteTableColumn(databaseName, tableName, ids);
  }

  @Transactional
  public int updateTableColumn(String updated, String databaseName, String tableName)
      throws Exception {
    if (updated != null) {
      JSONArray updateArray = JSONArray.parseArray(updated);
      for (int i = 0; i < updateArray.size(); ++i) {
        @SuppressWarnings("unchecked")
        Map<String, String> map1 = (Map<String, String>) updateArray.get(i);
        Map<String, String> maps = Maps.newHashMap();
        for (String key : map1.keySet()) {
          maps.put(key, map1.get(key));
        }

        String idValues = maps.get("mainPrimaryKey");

        this.permissionDao
            .updateTableColumn(maps, databaseName, tableName, "column_name", idValues);
      }
    }
    return 0;
  }

  public int savePrimaryKey(String databaseName, String tableName, String column_name,
      String column_key) throws Exception {
    return this.permissionDao.savePrimaryKey(databaseName, tableName, column_name, column_key);
  }

  public int updateTableNullAble(String databaseName, String tableName, String column_name,
      String is_nullable) throws Exception {
    return this.permissionDao
        .updateTableNullAble(databaseName, tableName, column_name, is_nullable);
  }

  public int upDownColumn(String databaseName, String tableName, String column_name,
      String column_name2) throws Exception {
    return this.permissionDao.upDownColumn(databaseName, tableName, column_name, column_name2);
  }

  public boolean checkPrivilege(String token) {
    IQueryParam param = new QueryParam();
    param.addInput("token", token);
    param.addOutput("type", Constants.YES);
    com.xiaodou.summer.dao.pagination.Page<Role> rolePage =
        roleDao.findEntityListByCond(param, null);
    if (null == rolePage || null == rolePage.getResult() || rolePage.getResult().isEmpty()) {
      return false;
    }
    return Constants.ROLE_TYPE_ADMIN.equals(rolePage.getResult().get(0).getType());
  }

  public Map<String, Object> queryDatabaseStatus(String dataSourceId) throws Exception {
    return this.permissionDao.queryDatabaseStatus(dataSourceId);
  }

  public List<Map<String, Object>> monitorItemValue(String dataSourceId) throws Exception {
    return this.permissionDao.monitorItemValue(dataSourceId);
  }

  public List<Role> getAllAdmin() {
    IQueryParam param = new QueryParam();
    param.addOutputs(CommUtil.getAllField(Role.class));
    com.xiaodou.summer.dao.pagination.Page<Role> adminPage =
        this.roleDao.findEntityListByCond(param, null);
    if (null != adminPage && adminPage.getResult() != null && adminPage.getResult().size() > 0) {
      return adminPage.getResult();
    }
    return null;
  }

  public Role getRole(String rid) {
    Role entity = new Role();
    entity.setRid(rid);
    return this.roleDao.findEntityById(entity);
  }

  public Role roleInsert(Role role) {
    return this.roleDao.addEntity(role);
  }

  public boolean roleUpdate(Role role) {
    return this.roleDao.updateEntityById(role);
  }

}
