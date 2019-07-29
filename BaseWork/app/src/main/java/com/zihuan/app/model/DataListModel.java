package com.zihuan.app.model;

import java.util.ArrayList;

public class DataListModel<T> extends BaseBeanModel {

    private ArrayList<T> data;

    @Override
    public ArrayList<T> getData() {
        return data;
    }

    public void setData(ArrayList<T> data) {
        this.data = data;
    }


}
