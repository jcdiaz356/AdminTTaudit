package com.dataservicios.adminttaudit.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.dataservicios.adminttaudit.db.DatabaseManager;
import com.dataservicios.adminttaudit.model.Store;
import com.dataservicios.adminttaudit.R;
import com.dataservicios.adminttaudit.model.TableAffected;
import com.dataservicios.adminttaudit.repo.TableAffectedRepo;
import com.dataservicios.adminttaudit.util.AuditUtil;
import com.dataservicios.adminttaudit.view.DetailStoreLiberateActivity;


import java.util.ArrayList;

/**
 * Created by jcdia on 30/04/2017.
 */

public class StoresAdapterRecyclerView extends RecyclerView.Adapter<StoresAdapterRecyclerView.StoreViewHolder> {
    private ArrayList<Store>       stores;
    private int                    resource;
    private Activity               activity;
    private ProgressDialog         pDialog;
    private TableAffectedRepo      tableAffectedRepo ;



    public StoresAdapterRecyclerView(ArrayList<Store> stores, int resource, Activity activity) {
        this.stores = stores;
        this.resource = resource;
        this.activity = activity;

        DatabaseManager.init(activity);

    }


    @Override
    public StoreViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resource, parent, false);
        view.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //action
                Toast.makeText(activity, "dfgdfg", Toast.LENGTH_SHORT).show();
            }
        });
        return new StoreViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StoreViewHolder holder, final int position) {
        final Store store = stores.get(position);

        holder.tvId.setText(String.valueOf(store.getId()));
        holder.tvCadenaRuc.setText(String.valueOf(store.getCadenaRuc()));
        holder.tvFullName.setText(store.getFullname());
        holder.tvAddress.setText(String.valueOf(store.getAddress()));
        holder.tvDistrict.setText(String.valueOf(store.getDistrict()));
        holder.tvRegion.setText(String.valueOf(store.getRegion()));
        holder.tvCompanyId.setText(String.valueOf(store.getCompany_id()));

        holder.btLiberar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(activity, "Prueba " +  store.getId() + "-" + store.getCompany_id() ,Toast.LENGTH_SHORT).show();

                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setTitle(R.string.save);
                builder.setMessage(R.string.message_store_liberation);
                builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener()

                {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Toast.makeText(activity, "Prueba " + store.getId() + "-" + store.getCompany_id(), Toast.LENGTH_SHORT).show();
                        new loadStoreLiberation().execute(store);

                        stores.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, stores.size());

                        dialog.dismiss();

                    }
                });

                builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                builder.show();
                builder.setCancelable(false);


            }
        });


    }

    @Override
    public int getItemCount() {
        return stores.size();
    }

    public class StoreViewHolder extends RecyclerView.ViewHolder {
        private TextView tvId;
        private TextView tvCadenaRuc;
        private TextView tvFullName;
        private TextView tvAddress;
        private TextView tvDistrict;
        private TextView tvRegion;
        private TextView tvCompanyId;
        private Button btLiberar;

        public StoreViewHolder(View itemView) {
            super(itemView);
            tvId = (TextView) itemView.findViewById(R.id.tvId);
            tvCadenaRuc = (TextView) itemView.findViewById(R.id.tvCadenaRuc);
            tvFullName = (TextView) itemView.findViewById(R.id.tvFullName);
            tvAddress = (TextView) itemView.findViewById(R.id.tvAddress);
            tvDistrict = (TextView) itemView.findViewById(R.id.tvDistrict);
            tvRegion = (TextView) itemView.findViewById(R.id.tvRegion);
            tvCompanyId = (TextView) itemView.findViewById(R.id.tvCompanyId);
            btLiberar = (Button) itemView.findViewById(R.id.btLiberar);
        }


    }



    class loadStoreLiberation extends AsyncTask<Store, String, ArrayList<TableAffected>> {

        /**
         * Antes de comenzar en el hilo determinado, Mostrar progresión
         */
        boolean failure = false;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(activity);
            pDialog.setMessage(activity.getString(R.string.text_loading));
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected ArrayList<TableAffected> doInBackground(Store... params) {
            // TODO Auto-generated method stub
            int store_id = params[0].getId();
            int company_id = params[0].getCompany_id();

            return AuditUtil.storeLiberate(store_id, company_id);

        }

        /**
         * After completing background task Dismiss the progress dialog
         **/
        protected void onPostExecute(ArrayList<TableAffected> tablesAffecteds) {


            if (tablesAffecteds.size() > 0) {

                tableAffectedRepo = new TableAffectedRepo(activity);
                tableAffectedRepo.deleteAll();

                for(TableAffected b : tablesAffecteds) {
                    tableAffectedRepo.create(b);
                }

                Intent intent = new Intent(activity,DetailStoreLiberateActivity.class);
                activity.startActivity(intent);
                Toast.makeText(activity, R.string.saveSuccess, Toast.LENGTH_LONG).show();

            } else {

                Toast.makeText(activity, R.string.saveError, Toast.LENGTH_LONG).show();


            }


            pDialog.dismiss();


        }

    }


}