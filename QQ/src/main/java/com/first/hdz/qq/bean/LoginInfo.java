package com.first.hdz.qq.bean;

import java.io.Serializable;

/**
 * created by hdz
 * on 2018/9/27
 */
public class LoginInfo implements Serializable{

    private String appId;
    private String appName;
    private int appType;
    private String versionName;
    private int versionCode;
    private String apkUrl;
    private boolean forceStatus;
    private String tips;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public int getAppType() {
        return appType;
    }

    public void setAppType(int appType) {
        this.appType = appType;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public String getApkUrl() {
        return apkUrl;
    }

    public void setApkUrl(String apkUrl) {
        this.apkUrl = apkUrl;
    }

    public boolean isForceStatus() {
        return forceStatus;
    }

    public void setForceStatus(boolean forceStatus) {
        this.forceStatus = forceStatus;
    }

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }
}
