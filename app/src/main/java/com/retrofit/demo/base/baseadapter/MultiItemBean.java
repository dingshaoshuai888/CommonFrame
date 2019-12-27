package com.retrofit.demo.base.baseadapter;

public class MultiItemBean {
    private int viewType;
    private String name;

    public MultiItemBean(int viewType, String name) {
        this.viewType = viewType;
        this.name = name;
    }

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
