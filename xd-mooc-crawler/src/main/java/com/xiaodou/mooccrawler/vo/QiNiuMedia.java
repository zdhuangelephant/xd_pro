package com.xiaodou.mooccrawler.vo;

public class QiNiuMedia {

    private static QiNiuMedia media = null;
    public static final String ACCESSKEY = "BoQZO87sq0mfPi1oONFaAVnUeLrKKmcuPUT6PO80";
    public static final String SECRETKEY = "fwluIvAlFyV8m-A-1hXXZP8rAbxe3DuVYSD6p38D";
    public static final String BUCKETNAME = "picture";
    public static final String DOMAIN = "http://7xigj3.com1.z0.glb.clouddn.com/";
    private String accessKey;// 设置accessKey
    private String secretKey;// 设置secretKey
    private String bucketName;// 设置存储空间
    private String domain;// 设置七牛域名

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    /**
     * 实例化一个QiNiuMedia实例
     * @uesr "xinzhifu@knet.cn"
     * @date 2016年11月19日下午2:58:27
     */
    public static synchronized QiNiuMedia getInstance() {
        if (media == null) {
            media = new QiNiuMedia();
            media.setAccessKey(ACCESSKEY);
            media.setSecretKey(SECRETKEY);
            media.setBucketName(BUCKETNAME);
            media.setDomain(DOMAIN);
        }
        return media;
    }
}