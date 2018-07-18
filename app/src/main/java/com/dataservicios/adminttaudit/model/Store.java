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
    private String nombCompany;
    private String codClient;
    private int visit_id;
    private int road_id;
    private String road_name;
    private int user_id;
    private String user_name;


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

    public String getNombCompany() {
        return nombCompany;
    }

    public void setNombCompany(String nombCompany) {
        this.nombCompany = nombCompany;
    }

    public String getCodClient() {
        return codClient;
    }

    public void setCodClient(String codClient) {
        this.codClient = codClient;
    }

    public int getVisit_id() {
        return visit_id;
    }

    public void setVisit_id(int visit_id) {
        this.visit_id = visit_id;
    }

    public int getRoad_id() {
        return road_id;
    }

    public void setRoad_id(int road_id) {
        this.road_id = road_id;
    }

    public String getRoad_name() {
        return road_name;
    }

    public void setRoad_name(String road_name) {
        this.road_name = road_name;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }
}
