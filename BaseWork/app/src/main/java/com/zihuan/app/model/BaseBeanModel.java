package com.zihuan.app.model;

/**
 */
public class BaseBeanModel {
    private String errorMsg = "";
    private int status;

    public Object getData() {
        return "";
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public int getStateCode() {
        return status;
    }

    public void setStateCode(int stateCode) {
        this.status = stateCode;
    }

    @Override
    public String toString() {
        return "BaseBeanModel{" +
                "errorMsg='" + errorMsg + '\'' +
                ", stateCode=" + status + '\'' +
                ",getData='" + getData().toString() + '\'' +
                '}';
    }
}
