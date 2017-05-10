package com.dataservicios.adminttaudit.model;

import com.j256.ormlite.field.DatabaseField;

/**
 * Created by jcdia on 9/05/2017.
 */

public class TableAffected {
    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField
    private String table;
    @DatabaseField
    private String action;
    @DatabaseField
    private int rowsAffected;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public int getRowsAffected() {
        return rowsAffected;
    }

    public void setRowsAffected(int rowsAffected) {
        this.rowsAffected = rowsAffected;
    }
}
