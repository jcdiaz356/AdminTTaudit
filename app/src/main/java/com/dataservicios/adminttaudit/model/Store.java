package com.dataservicios.adminttaudit.model;

/**
 * Created by jcdia on 9/05/2017.
 */

public class Store {
    private int id;
    private String cadenaRuc;
    private String fullname;
    private String address;
    private String district;
    private String region;
    private int company_id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCadenaRuc() {
        return cadenaRuc;
    }

    public void setCadenaRuc(String cadenaRuc) {
        this.cadenaRuc = cadenaRuc;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public int getCompany_id() {
        return company_id;
    }

    public void setCompany_id(int company_id) {
        this.company_id = company_id;
    }
}
