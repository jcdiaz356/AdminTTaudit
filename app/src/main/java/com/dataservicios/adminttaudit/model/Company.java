package com.dataservicios.adminttaudit.model;

/**
 * Created by jcdia on 8/05/2017.
 */

public class Company {

    private int id;
    private String fullname;

    public Company(int id, String fullname ) {
        this.id=id;
        this.fullname=fullname;
    }

    public Company() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    @Override
    public String toString() {
        return fullname;
    }


}
