package com.zihuan.app.model;

import java.util.ArrayList;

/**
 */
public class DataListModel extends ArrayList<Object> {
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

}
