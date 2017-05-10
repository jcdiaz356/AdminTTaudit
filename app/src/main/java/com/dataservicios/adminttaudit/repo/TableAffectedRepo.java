package com.dataservicios.adminttaudit.repo;

import android.content.Context;

import com.dataservicios.adminttaudit.db.DatabaseHelper;
import com.dataservicios.adminttaudit.db.DatabaseManager;
import com.dataservicios.adminttaudit.model.TableAffected;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by jcdia on 9/05/2017.
 */

public class TableAffectedRepo implements Crud {
    private DatabaseHelper helper;

    public TableAffectedRepo(Context context) {

        DatabaseManager.init(context);
        helper = DatabaseManager.getInstance().getHelper();
    }

    @Override
    public int create(Object item) {
        int index = -1;
        TableAffected object = (TableAffected) item;
        try {
            index = helper.getTableAffectedDao().create(object);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return index;
    }


    @Override
    public int update(Object item) {

        int index = -1;

        TableAffected object = (TableAffected) item;

        try {
            helper.getTableAffectedDao().update(object);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return index;
    }


    @Override
    public int delete(Object item) {

        int index = -1;

        TableAffected object = (TableAffected) item;

        try {
            helper.getTableAffectedDao().delete(object);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return index;
    }

    @Override
    public int deleteAll() {

        List<TableAffected> items = null;
        int counter = 0;
        try {
            items = helper.getTableAffectedDao().queryForAll();

            for (TableAffected object : items) {
                // do something with object
                helper.getTableAffectedDao().deleteById(object.getId());
                counter++;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return counter;
    }


    @Override
    public Object findById(int id) {

        TableAffected wishList = null;
        try {
            wishList = helper.getTableAffectedDao().queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return wishList;
    }


    @Override
    public List<?> findAll() {

        List<TableAffected> items = null;

        try {
            items = helper.getTableAffectedDao().queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return items;

    }


}



