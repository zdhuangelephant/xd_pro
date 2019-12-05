
package com.xiaodou.oms.util.model;

/**
 * @ClassName: PruductConnEntity

 * @Description: 业务库message表的连接信息

 * @author Guanguo.Gao

 * @date 2014年8月28日 上午9:55:17

 * @version V1.0
 */
public class BizMessageInfo {
    
    /** db driver **/
    private String driverClassName;

    /** connection url **/
    private String url;

    /** db connection username **/
    private String username;
    
    /** db connection dbpassword **/
    private String password;
    
    /** 消息表表名 **/
    private String tableName;
    
    /** tag所在的列的名称 **/
    private String tagColumnName;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTagColumnName() {
        return tagColumnName;
    }

    public void setTagColumnName(String tagColumnName) {
        this.tagColumnName = tagColumnName;
    }

    public String getDriverClassName() {
        return driverClassName;
    }

    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }

/*    @Override
    public String toString(){
        StringBuffer buff = new StringBuffer("");
        for(Field field : getClass().getDeclaredFields()){
            try {
                buff.append(field.getName() + " = " + field.get(this) + "; ");
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        buff.append(System.getProperty("line.separator"));
        return buff.toString();
    }*/
    
}
