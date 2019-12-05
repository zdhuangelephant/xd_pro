package com.xiaodou.docplugin.entity;

/**
 * @author junpeng.chen
 */
public class DcLibEntity {
    private String url;
    private String packageName;
    private String simpleTypeName;

    public String getUrl() {
        if (url == null) {
            url = "/apidocs/refer/" + packageName.replace("com.elong.hotel.", "").replace(".", "/") + "/" + simpleTypeName + ".html";
        }
        return url;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getSimpleTypeName() {
        return simpleTypeName;
    }

    public void setSimpleTypeName(String simpleTypeName) {
        this.simpleTypeName = simpleTypeName;
    }
}
