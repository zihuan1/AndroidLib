package com.zihuan.app.model;

/**
 */
public class SheBeiEntity {
    private int companyId;
    private String companyName;
    private String companyIcon;
    private String breakInfo;
    private String breakTime;
    private String deviceName;
    private String deviceId;
    private int factoryCount;//工厂数量
    private int companydeviceCount;  //公司设备总量

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getBreakInfo() {
        return breakInfo;
    }

    public void setBreakInfo(String breakInfo) {
        this.breakInfo = breakInfo;
    }

    public String getBreakTime() {
        return breakTime;
    }

    public void setBreakTime(String breakTime) {
        this.breakTime = breakTime;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyIcon() {
        return companyIcon;
    }

    public void setCompanyIcon(String companyIcon) {
        this.companyIcon = companyIcon;
    }

    public int getFactoryCount() {
        return factoryCount;
    }

    public void setFactoryCount(int factoryCount) {
        this.factoryCount = factoryCount;
    }

    public int getCompanydeviceCount() {
        return companydeviceCount;
    }

    public void setCompanydeviceCount(int companydeviceCount) {
        this.companydeviceCount = companydeviceCount;
    }
}
