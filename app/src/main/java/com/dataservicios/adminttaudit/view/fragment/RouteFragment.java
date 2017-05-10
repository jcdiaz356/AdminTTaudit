package com.dataservicios.adminttaudit.view.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.dataservicios.adminttaudit.R;
import com.dataservicios.adminttaudit.adapter.StoresAdapterRecyclerView;
import com.dataservicios.adminttaudit.db.DatabaseManager;
import com.dataservicios.adminttaudit.model.Company;
import com.dataservicios.adminttaudit.model.Store;
import com.dataservicios.adminttaudit.repo.TableAffectedRepo;
import com.dataservicios.adminttaudit.repo.UserRepo;
import com.dataservicios.adminttaudit.util.AuditUtil;
import com.dataservicios.adminttaudit.util.GlobalConstant;
import com.dataservicios.adminttaudit.util.SessionManager;

import java.util.ArrayList;
import java.util.HashMap;


public class RouteFragment extends Fragment {
    private static final String LOG_TAG = RouteFragment.class.getSimpleName();

    private SessionManager              session;
    private Activity                    myActivity = (Activity) getActivity();
    private ProgressDialog              pDialog;
    private Spinner                     spCompany;
    private LinearLayout                lySearch;
    private Button                      btSearch ;
    private int                         user_id, store_id, company_id;
    private EditText                    etStoreId;
    private ArrayAdapter<Company>       spinnerAdapter;
    private StoresAdapterRecyclerView   pictureAdapterRecyclerView;

    private RecyclerView                storeRecycler;


    public RouteFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        session = new SessionManager(getActivity());
        HashMap<String, String> userSesion = session.getUserDetails();
        user_id = Integer.valueOf(userSesion.get(SessionManager.KEY_ID_USER)) ;



        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_route, container, false);
        storeRecycler  = (RecyclerView) view.findViewById(R.id.storeRecycler);

        spCompany  = (Spinner) view.findViewById(R.id.spCompany);
        lySearch  = (LinearLayout) view.findViewById(R.id.lySearch);
        etStoreId  = (EditText) view.findViewById(R.id.etStoreId);
        btSearch  = (Button) view.findViewById(R.id.btSearch);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        storeRecycler.setLayoutManager(linearLayoutManager);

//        RouteAdapterRecyclerView pictureAdapterRecyclerView =  new

       new loadCompany().execute();

        btSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                store_id    = Integer.valueOf(etStoreId.getText().toString());
                company_id  = ((Company) spCompany.getSelectedItem () ).getId () ;
                new loadStores().execute();
            }
        });

        spCompany.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //String label = parent.getItemAtPosition(position).toString();
                int databaseId = ((Company) spCompany.getSelectedItem () ).getId () ;
                String label = ((Company) spCompany.getSelectedItem () ).getFullname () ;
                Toast.makeText(parent.getContext(),  " ID: " + String.valueOf(databaseId) ,Toast.LENGTH_LONG).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {



            }
        });


        return view;
    }

    private ArrayList<Company> companiesLista() {

        ArrayList<Company> companie = new ArrayList<Company>();
        companie.add(new Company(1,"sdf"));
        companie.add(new Company(2,"dfgdfgdfg"));
        companie.add(new Company(3,"sdf"));


        return companie;
    }

    class loadCompany extends AsyncTask<Void, Integer , ArrayList<Company>> {
        /**
         * Antes de comenzar en el hilo determinado, Mostrar progresión
         * */
        boolean failure = false;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage(getString(R.string.text_loading));
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }
        @Override
        protected ArrayList<Company> doInBackground(Void... params) {

            ArrayList<Company> companyes = new ArrayList<Company>();
            companyes = AuditUtil.getCompanies(1,1);

            return companyes;
        }
        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(ArrayList<Company> companies) {
            // dismiss the dialog once product deleted


            if (companies.size() > 0 )  {

                spinnerAdapter = new ArrayAdapter<Company>(getContext(), android.R.layout.simple_spinner_item, companies);
                spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spCompany.setAdapter(spinnerAdapter);
                lySearch.setVisibility(View.VISIBLE);
            } else  {

                Toast.makeText(myActivity , R.string.message_no_found, Toast.LENGTH_LONG).show();
                lySearch.setVisibility(View.INVISIBLE);

            }




            pDialog.dismiss();

        }
    }



//    private ArrayList<Route> routes() {
//
//        ArrayList<Route> route = new ArrayList<Route>();
//        route.add(new Route(1,"sdf",1,2));
//        route.add(new Route(2,"dfgdfgdfg",1,2));
//        route.add(new Route(3,"sdf",1,2));
//
//
//        return route;
//    }


    class loadStores extends AsyncTask<Void, String, ArrayList<Store>> {

        /**
         * Antes de comenzar en el hilo determinado, Mostrar progresión
         * */
        boolean failure = false;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage(getString(R.string.text_loading));
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected ArrayList<Store> doInBackground(Void... params) {
            // TODO Auto-generated method stub
            return AuditUtil.getStores(store_id,company_id);

        }
        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(ArrayList<Store> stores) {


            if (stores.size() > 0 )  {

                pictureAdapterRecyclerView =  new StoresAdapterRecyclerView(stores, R.layout.cardview_store,getActivity());
                storeRecycler.setAdapter(pictureAdapterRecyclerView);
                //lySearch.setVisibility(View.VISIBLE);
            } else  {

                Toast.makeText(getContext() , R.string.message_no_found, Toast.LENGTH_LONG).show();
                //lySearch.setVisibility(View.INVISIBLE);
            }
            pDialog.dismiss();
        }

    }

}
