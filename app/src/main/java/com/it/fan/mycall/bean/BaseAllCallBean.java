package com.it.fan.mycall.bean;

import java.util.List;

/**
 * Created by fan on 2019/6/5.
 */

public class BaseAllCallBean {
    private int total;

    private List<AllCallBean> rows ;


    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<AllCallBean> getRows() {
        return rows;
    }

    public void setRows(List<AllCallBean> rows) {
        this.rows = rows;
    }
}
