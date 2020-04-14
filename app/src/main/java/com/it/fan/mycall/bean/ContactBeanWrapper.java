package com.it.fan.mycall.bean;

import java.util.List;

public class ContactBeanWrapper {
    private int total;
    private List<ContactBean> rows;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<ContactBean> getRows() {
        return rows;
    }

    public void setRows(List<ContactBean> rows) {
        this.rows = rows;
    }
}
