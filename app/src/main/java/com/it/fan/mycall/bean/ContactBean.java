package com.it.fan.mycall.bean;

import com.mcxtzhang.indexlib.IndexBar.bean.BaseIndexPinyinBean;

public class ContactBean extends BaseIndexPinyinBean {

    private String name;
    private int id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getTarget() {
        return name;
    }
}
