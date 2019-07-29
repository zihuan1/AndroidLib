package com.zihuan.app.model;


public class DataObjModel<T> extends BaseBeanModel {

    private T data;

    @Override
    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }


}
