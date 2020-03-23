package com.it.fan.mycall.bean;

import java.util.List;

/**
 * Created by fan on 2019/9/4.
 */

public class PatientListBean {
    private int total;

    private List<PatientInfo> rows ;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<PatientInfo> getRows() {
        return rows;
    }

    public void setRows(List<PatientInfo> rows) {
        this.rows = rows;
    }
}
