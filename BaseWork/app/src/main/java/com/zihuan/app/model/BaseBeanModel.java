package com.zihuan.app.model;

/**
 */
public class BaseBeanModel {
    private String errorMsg;
    private int stateCode;

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public int getStateCode() {
        return stateCode;
    }

    public void setStateCode(int stateCode) {
        this.stateCode = stateCode;
    }

    @Override
    public String toString() {
        return "BaseBeanModel{" +
                "errorMsg='" + errorMsg + '\'' +
                ", stateCode=" + stateCode +
                '}';
    }
}
