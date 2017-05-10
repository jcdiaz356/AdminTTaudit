package com.dataservicios.adminttaudit.util;

import android.content.Context;
import android.util.Log;


import com.dataservicios.adminttaudit.model.Company;
import com.dataservicios.adminttaudit.model.Store;
import com.dataservicios.adminttaudit.model.TableAffected;
import com.dataservicios.adminttaudit.model.User;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * Created by Jaime on 28/08/2016.
 */
public class AuditUtil {
    public static final String LOG_TAG = AuditUtil.class.getSimpleName();
    //private AlbumStorageDirFactory mAlbumStorageDirFactory = null;
    private Context context;

    public AuditUtil(Context context) {
        this.context = context ;
    }


    /**
     * Validate user and password for login
     * @param userName
     * @param password
     * @param imei
     * @return
     */
    public static User userLogin(String userName, String password , String imei){

        int success ;
        User user = new User();
        try {

            HashMap<String, String> params = new HashMap<>();

            params.put("username", String.valueOf(userName));
            params.put("password", String.valueOf(password));
            params.put("imei", String.valueOf(imei));

            JSONParserX jsonParser = new JSONParserX();
            // getting product details by making HTTP request
            JSONObject json = jsonParser.makeHttpRequest(GlobalConstant.dominio + "/loginMovil" ,"POST", params);
            // check your log for json response
            Log.d("Login attempt", json.toString());
            // json success, tag que retorna el json
            if (json == null) {
                Log.d("JSON result", "Está en nulo");
            } else{
                success = json.getInt("success");
                if (success == 1) {
                    user.setId(json.getInt("id"));
                    user.setEmail(userName);
                    user.setFullname(json.getString("fullname"));
                    user.setImage("use");
                    user.setPassword(password);
                }else{
                    Log.d(LOG_TAG, "No se pudo iniciar sesión");
                    //return false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            // return false;
        }
        return  user;
    }


    public static ArrayList<Company> getCompanies(int active, int visible ){

        int success ;

        ArrayList<Company> companies = new ArrayList<Company>();
        try {

            HashMap<String, String> params = new HashMap<>();

            params.put("active", String.valueOf(active));
            params.put("visible", String.valueOf(visible));

            JSONParserX jsonParser = new JSONParserX();
            // getting product details by making HTTP request
            JSONObject json = jsonParser.makeHttpRequest(GlobalConstant.dominio + "/admin_api/api_company.php" ,"POST", params);
            // check your log for json response
            Log.d("Login attempt", json.toString());
            // json success, tag que retorna el json
            if (json == null) {
                Log.d("JSON result", "Está en nullo");

            } else{
                success = json.getInt("success");
                if (success > 0) {
                    JSONArray ObjJson;
                    ObjJson = json.getJSONArray("companies");
                    // looping through All Products
                    if(ObjJson.length() > 0) {

                        for (int i = 0; i < ObjJson.length(); i++) {

                            JSONObject obj = ObjJson.getJSONObject(i);
                            Company company = new Company();
                            company.setId(Integer.valueOf(obj.getString("id")));
                            company.setFullname(obj.getString("fullname"));
                            companies.add(i,company);
                        }

                    }
                    Log.d(LOG_TAG, "Ingresado correctamente");
                }else{
                    Log.d(LOG_TAG, "No se ingreso el registro");
                    //return false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            // return false;
        }
        return  companies;
    }

    public static ArrayList<Store> getStores(int store_id, int company_id){

        int success ;

        ArrayList<Store> stores = new ArrayList<Store>();
        try {

            HashMap<String, String> params = new HashMap<>();

            params.put("company_id", String.valueOf(company_id));
            params.put("store_id", String.valueOf(store_id));

            JSONParserX jsonParser = new JSONParserX();
            // getting product details by making HTTP request
            JSONObject json = jsonParser.makeHttpRequest(GlobalConstant.dominio + "/admin_api/api_store_audit.php" ,"POST", params);
            // check your log for json response
            Log.d("Login attempt", json.toString());
            // json success, tag que retorna el json
            if (json == null) {
                Log.d("JSON result", "Está en nullo");

            } else{
                success = json.getInt("success");
                if (success > 0) {
                    JSONArray ObjJson;
                    ObjJson = json.getJSONArray("stores");
                    // looping through All Products
                    if(ObjJson.length() > 0) {

                        for (int i = 0; i < ObjJson.length(); i++) {

                            JSONObject obj = ObjJson.getJSONObject(i);
                            Store store = new Store();
                            store.setId(Integer.valueOf(obj.getString("id")));
                            store.setCadenaRuc(obj.getString("cadenaRuc"));
                            store.setFullname(obj.getString("fullname"));
                            store.setAddress(obj.getString("address"));
                            store.setDistrict(obj.getString("district"));
                            store.setRegion(obj.getString("region"));
                            store.setCompany_id(obj.getInt("company_id"));
                            stores.add(i,store);
                        }

                    }
                    Log.d(LOG_TAG, "Ingresado correctamente");
                }else{
                    Log.d(LOG_TAG, "No se ingreso el registro");
                    //return false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            // return false;
        }
        return  stores;
    }


    public static ArrayList<TableAffected> storeLiberate(int store_id, int company_id) {
        int success ;

        ArrayList<TableAffected> tablesAffecteds = new ArrayList<TableAffected>();
        try {

            HashMap<String, String> params = new HashMap<>();

            params.put("company_id", String.valueOf(company_id));
            params.put("store_id", String.valueOf(store_id));

            JSONParserX jsonParser = new JSONParserX();
            // getting product details by making HTTP request
            JSONObject json = jsonParser.makeHttpRequest(GlobalConstant.dominio + "/admin_api/api_store_liberation.php" ,"POST", params);
            // check your log for json response
            Log.d("Login attempt", json.toString());
            // json success, tag que retorna el json
            if (json == null) {
                Log.d("JSON result", "Está en nullo");

            } else{
                success = json.getInt("success");
                if (success > 0) {
                    JSONArray ObjJson;
                    ObjJson = json.getJSONArray("tables");
                    // looping through All Products
                    if(ObjJson.length() > 0) {

                        for (int i = 0; i < ObjJson.length(); i++) {

                            JSONObject obj = ObjJson.getJSONObject(i);
                            TableAffected tableAffected = new TableAffected();

                            tableAffected.setTable(String.valueOf(obj.getString("table")));
                            tableAffected.setAction(obj.getString("action"));
                            tableAffected.setRowsAffected(obj.getInt("rows"));

                            tablesAffecteds.add(i,tableAffected);
                        }

                    }
                    Log.d(LOG_TAG, "Ingresado correctamente");
                }else{
                    Log.d(LOG_TAG, "No se ingreso el registro");
                    //return false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            // return false;
        }
        return  tablesAffecteds;
    }

}
