package com.zihuan.app.model;

import android.text.TextUtils;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class TestEntity {
    private String id;
    private String name;

    public String getName() {
        if (TextUtils.isEmpty(name)) return "";
        return name;
    }

}
