package com.xiaodou.oms.dao.message;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.xiaodou.common.util.log.LoggerUtil;

public class DBUtil {

    /** 数据库驱动 **/
    private String dbdriver;

    /** 数据库地址 **/
    private String dburl;

    /** 用户名 **/
    private String dbusername;

    /** 密码 **/
    private String dbpasswd;

    /** 查询的字段 **/
    private String columnName;
    
    /** sql connection **/
    private Connection conn;
    
    /** preparedstatement **/
    private PreparedStatement pst;
    
    /** sql resultset **/
    private ResultSet rs;

    public DBUtil(String dbdriver, String dburl, String dbusername,
            String dbpasswd, String columnName) {
        super();
        this.dbdriver = dbdriver;
        this.dburl = dburl;
        this.dbusername = dbusername;
        this.dbpasswd = dbpasswd;
        this.columnName = columnName;
        getConnection();
    }

    /**
     * @Description: 获取数据库connection
     * @return
     * @throws
     */
    public Connection getConnection() {
        conn = null;
        try {
            Class.forName(dbdriver);
            conn = DriverManager.getConnection(dburl, dbusername, dbpasswd);
        } catch (Exception e) {
            LoggerUtil.error("[message check][cannot connect to" + dburl
                    + ",username=" + dbusername + ",password=" + dbpasswd, e);
        }
        return conn;
    }

    /**
     * @Description: 获得结果集
     * @param querySql
     * @return
     * @throws
     */
    public ResultSet executeQuery(String querySql) {
        rs = null;
        try {
            if(conn == null)
                return null;
            pst = conn.prepareStatement(querySql);
            rs = pst.executeQuery();
        } catch (Exception e) {
            LoggerUtil.error("[message check][execute sql:" + querySql
                    + " error", e);
        }
        return rs;
    }
    
    /**
     * @Description: 执行更新操作，如果是1，执行成功;如果是0，执行失败
     * @param updateSql
     * @return
     * @throws
     */
    public boolean excuteUpdate(String updateSql){
        try {
            if(conn == null)
                return false;
            pst = conn.prepareStatement(updateSql);
            int result = pst.executeUpdate();
            return result >= 1;
        } catch (Exception e) {
            LoggerUtil.error("[message check][execute sql:" + updateSql
                    + " error", e);
            return false;
        }
    }
    
    /**
     * @Description: 获取查询数据 针对message表
     * @return
     * @throws
     */
    public List<String> queryTagList(String sql){
        List<String> resultList = new ArrayList<>();
        try {
            rs = executeQuery(sql);
            while(rs.next()){
                String result = rs.getString(columnName);
                resultList.add(result);
            }
        } catch (SQLException e) {
            LoggerUtil.error("[insurance check][get result error]", e);
        }
        return resultList;
    }
    
    public void close(){
        try {
            if(rs != null){
                rs.close();
            }
            if(pst != null){
                pst.close();
            }
            if(conn != null){
                conn.close();
            }
        } catch (Exception e) {
            LoggerUtil.error("[message check][close connection error", e);
        }

    }

}
