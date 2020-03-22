package com.it.fan.mycall.bean;

import java.util.List;

/**
 * Created by fan on 2019/6/7.
 */

public class CallRecordListBean {
    private int total;

    private List<CallRecordBean> rows ;


    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<CallRecordBean> getRows() {
        return rows;
    }

    public void setRows(List<CallRecordBean> rows) {
        this.rows = rows;
    }
}
