package com.xiaodou.summer.source.jdbc.helper;

/**
 * @name DynamicDataSourceHolder CopyRright (c) 2018 by 李德洪
 * 
 * @author 李德洪
 * @date 2018年1月25日
 * @description 本地线程
 * @version 1.0
 */
public final class RealSqlSessionKeyHolder {

    private static final ThreadLocal<RealSqlSessionKeyHolder> wrapper = new ThreadLocal<RealSqlSessionKeyHolder>();

    private RealSqlSessionKeyHolder() {}

    /**isUsekey 是否使用多数据源，以防可以写入null*/
    private Boolean isUsekey = false;
    /**realSqlSessionKey 多数据源key值*/
    private String realSqlSessionKey = null;

    public Boolean getIsUsekey() {
        return isUsekey;
    }

    public void setIsUsekey(Boolean isUsekey) {
        this.isUsekey = isUsekey;
    }

    public String getRealSqlSessionKey() {
        return realSqlSessionKey;
    }

    public void setRealSqlSessionKey(String realSqlSessionKey) {
        this.realSqlSessionKey = realSqlSessionKey;
    }

    static {
        initHolder();
    }

    /**
     * 构造方法
     * 
     */
    public static void initHolder() {
        RealSqlSessionKeyHolder ctx = getHolder();
        setHolder(ctx);
    }

    /**
     * 获取包装器
     * 
     * @return ErrorsWrapper
     */
    public static RealSqlSessionKeyHolder getHolder() {
        RealSqlSessionKeyHolder ctx = wrapper.get();
        if (ctx == null) {
            ctx = new RealSqlSessionKeyHolder();
            wrapper.set(ctx);
        }
        return ctx;
    }

    /**
     * 设置包装器
     * 
     * @param wrapper
     */
    public static void setHolder(RealSqlSessionKeyHolder holder) {
        wrapper.set(holder);
    }

    /**
     * 清理包装器
     */
    public void clear() {
        wrapper.remove();
    }

}
