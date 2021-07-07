package com.zihuan.app.model;

import java.util.List;

import lombok.Builder;

public class DataListModel<T> extends BaseBeanModel {

    private List<T> data;

    @Override
    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }


}
