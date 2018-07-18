package com.dataservicios.adminttaudit.view;


import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;
import android.widget.TextView;

import com.dataservicios.adminttaudit.R;

import com.dataservicios.adminttaudit.db.DatabaseManager;
import com.dataservicios.adminttaudit.model.TableAffected;
import com.dataservicios.adminttaudit.repo.TableAffectedRepo;

import java.util.ArrayList;

public class DetailStoreLiberateActivity extends AppCompatActivity {
    private static final String LOG_TAG = DetailStoreLiberateActivity.class.getSimpleName();

    private Activity                    myActivity = this ;
    private TextView etDetail;
    private TableAffectedRepo           tableAffectedRepo;
    private ArrayList<TableAffected>    tablesAffecteds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_store_liberate);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Detalle");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        DatabaseManager.init(myActivity);

        etDetail = (TextView) findViewById(R.id.etDetail);
        tableAffectedRepo = new TableAffectedRepo(myActivity);

        tablesAffecteds = (ArrayList<TableAffected>) tableAffectedRepo.findAll();

        for (TableAffected t:tablesAffecteds){
            etDetail.append("Tabla:");
            etDetail.append(t.getTable().toString());
            etDetail.append("\n");
            etDetail.append("Acción: ");
            etDetail.append(t.getAction().toString());
            etDetail.append("\n");
            etDetail.append("Registros Afectados: ");
            etDetail.append(String.valueOf(t.getRowsAffected()));
            etDetail.append("\n");
            etDetail.append("\n");
        }


    }

//    @Override
//    public void onBackPressed() {
//        //super.onBackPressed();
////        this.finish();
//    }
}
