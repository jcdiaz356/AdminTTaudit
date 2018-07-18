package com.dataservicios.adminttaudit.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.dataservicios.adminttaudit.R;
import com.dataservicios.adminttaudit.db.DatabaseManager;
import com.dataservicios.adminttaudit.model.Store;
import com.dataservicios.adminttaudit.model.TableAffected;
import com.dataservicios.adminttaudit.repo.TableAffectedRepo;
import com.dataservicios.adminttaudit.util.AuditUtil;
import com.dataservicios.adminttaudit.view.DetailStoreLiberateActivity;

import java.util.ArrayList;

public class StoresVisitAdapterRecyclerView  extends RecyclerView.Adapter<StoresVisitAdapterRecyclerView.StoreVisitViewHolder> {
    private ArrayList<Store> stores;
    private int                    resource;
    private Activity activity;
    private boolean                activeLiberation;
    private ProgressDialog pDialog;
    private TableAffectedRepo tableAffectedRepo ;



    public StoresVisitAdapterRecyclerView(ArrayList<Store> stores, int resource, Activity activity, boolean activeLiberation) {
        this.stores = stores;
        this.resource = resource;
        this.activity = activity;
        this.activeLiberation = activeLiberation ;

        DatabaseManager.init(activity);

    }


    @Override
    public StoresVisitAdapterRecyclerView.StoreVisitViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resource, parent, false);
        view.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //action
                Toast.makeText(activity, "dfgdfg", Toast.LENGTH_SHORT).show();
            }
        });
        return new StoresVisitAdapterRecyclerView.StoreVisitViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StoresVisitAdapterRecyclerView.StoreVisitViewHolder holder, final int position) {
        final Store store = stores.get(position);

        holder.tvId.setText(String.valueOf(store.getId()));
        holder.tvCadenaRuc.setText(String.valueOf(store.getCadenaRuc()));
        holder.tvFullName.setText(store.getFullname());
        holder.tvAddress.setText(String.valueOf(store.getAddress()));
        holder.tvDistrict.setText(String.valueOf(store.getDistrict()));
        holder.tvRegion.setText(String.valueOf(store.getRegion()));
        holder.tvCompanyId.setText(String.valueOf(store.getCompany_id()));
        holder.tvCompanyName.setText(String.valueOf(store.getNombCompany()));
        holder.tvCodClient.setText(String.valueOf(store.getCodClient()));
        holder.tvRoadId.setText(String.valueOf(store.getRoad_id()));
        holder.tvRoadName.setText(String.valueOf(store.getRoad_name()));
        holder.tvUserId.setText(String.valueOf(store.getUser_id()));
        holder.tvUserName.setText(String.valueOf(store.getUser_name()));
        holder.tvVisitId.setText(String.valueOf(store.getVisit_id()));

        if (activeLiberation)  holder.btLiberar.setVisibility(View.VISIBLE); else holder.btLiberar.setVisibility(View.INVISIBLE);


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

                        Toast.makeText(activity,  store.getId() + "-" + store.getCompany_id(), Toast.LENGTH_SHORT).show();
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

        holder.btOpenForOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(activity, "Prueba " +  store.getId() + "-" + store.getCompany_id() ,Toast.LENGTH_SHORT).show();

                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setTitle(R.string.title_open_store);
                builder.setMessage(R.string.message_store_open_for_order);
                builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener()

                {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Toast.makeText(activity,  store.getId() + "-" + store.getCompany_id(), Toast.LENGTH_SHORT).show();
                        new loadOpenStoreForOreder().execute(store);

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

        holder.btShared.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(activity, "Prueba " +  store.getId() + "-" + store.getCompany_id() ,Toast.LENGTH_SHORT).show();

                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = "ID Store: " + store.getId() + " \n  Tienda: " + store.getFullname() + " \n Dirección: " + store.getAddress()  ;
                String shareSub = "Tienda";
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, shareSub);
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                sharingIntent.putExtra(Intent.EXTRA_TITLE, shareBody);
                activity.startActivity(Intent.createChooser(sharingIntent, "Share using"));


            }
        });


    }

    @Override
    public int getItemCount() {
        return stores.size();
    }

    public class StoreVisitViewHolder extends RecyclerView.ViewHolder {
        private TextView tvId;
        private TextView tvCadenaRuc;
        private TextView tvFullName;
        private TextView tvAddress;
        private TextView tvDistrict;
        private TextView tvRegion;
        private TextView tvCompanyId;
        private TextView tvCodClient;
        private TextView tvCompanyName;
        private TextView tvRoadId;
        private TextView tvUserId;
        private TextView tvUserName;
        private TextView tvRoadName;
        private TextView tvVisitId;
        private Button btLiberar;
        private Button btOpenForOrder;
        private Button btShared;

        public StoreVisitViewHolder(View itemView) {
            super(itemView);
            tvId = (TextView) itemView.findViewById(R.id.tvId);
            tvCadenaRuc = (TextView) itemView.findViewById(R.id.tvCadenaRuc);
            tvFullName = (TextView) itemView.findViewById(R.id.tvFullName);
            tvAddress = (TextView) itemView.findViewById(R.id.tvAddress);
            tvDistrict = (TextView) itemView.findViewById(R.id.tvDistrict);
            tvRegion = (TextView) itemView.findViewById(R.id.tvRegion);
            tvCompanyId = (TextView) itemView.findViewById(R.id.tvCompanyId);
            tvCodClient = (TextView) itemView.findViewById(R.id.tvCodClient);
            tvCompanyName = (TextView) itemView.findViewById(R.id.tvCompanyName);
            tvRoadId = (TextView) itemView.findViewById(R.id.tvRoadId);
            tvRoadName = (TextView) itemView.findViewById(R.id.tvRoadName);
            tvUserId = (TextView) itemView.findViewById(R.id.tvUserId);
            tvUserName = (TextView) itemView.findViewById(R.id.tvUserName);
            tvVisitId = (TextView) itemView.findViewById(R.id.tvVisitId);

            btShared = (Button) itemView.findViewById(R.id.btShared);
            btOpenForOrder = (Button) itemView.findViewById(R.id.btOpenForOrder);
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
            int road_id = params[0].getRoad_id();
            int visit_id = params[0].getVisit_id();

            return AuditUtil.storeLiberateStoreVisit(store_id, company_id,visit_id,road_id);

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


    class loadOpenStoreForOreder extends AsyncTask<Store, String, ArrayList<TableAffected>> {

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
            int road_id = params[0].getRoad_id();
            int visit_id = params[0].getVisit_id();

            return AuditUtil.storeOpenForOrders(store_id, company_id,visit_id,road_id);

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
                Toast.makeText(activity, R.string.message_opn_store_for_pedido, Toast.LENGTH_LONG).show();

            } else {

                Toast.makeText(activity, R.string.saveError, Toast.LENGTH_LONG).show();


            }


            pDialog.dismiss();


        }

    }


}