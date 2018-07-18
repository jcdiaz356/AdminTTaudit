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
import com.dataservicios.adminttaudit.adapter.StoresVisitAdapterRecyclerView;
import com.dataservicios.adminttaudit.model.Company;
import com.dataservicios.adminttaudit.model.Store;
import com.dataservicios.adminttaudit.model.Visit;
import com.dataservicios.adminttaudit.util.AuditUtil;
import com.dataservicios.adminttaudit.util.SessionManager;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchStoreVisitFragment extends Fragment {

    private static final String LOG_TAG = SearchStoreVisitFragment.class.getSimpleName();

    private SessionManager                  session;
    private Activity                        myActivity = (Activity) getActivity();
    private ProgressDialog                  pDialog;
    private Spinner                         spCompany,spVisit;
    private LinearLayout                    lySearch;
    private Button                          btSearch ;
    private int                             user_id, store_id, company_id,visit_id;
    private EditText                        etStoreId;
    private ArrayAdapter<Company>           spinnerAdapter;
    private ArrayAdapter<Visit>             visitArrayAdapter;
    private StoresVisitAdapterRecyclerView  storesVisitAdapterRecyclerView;

    private RecyclerView storeRecycler;

    public SearchStoreVisitFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        session = new SessionManager(getActivity());
        HashMap<String, String> userSesion = session.getUserDetails();
        user_id = Integer.valueOf(userSesion.get(SessionManager.KEY_ID_USER)) ;



        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search_store_visit, container, false);
        storeRecycler  = (RecyclerView) view.findViewById(R.id.storeRecycler);

        spCompany   = (Spinner) view.findViewById(R.id.spCompany);
        spVisit     = (Spinner) view.findViewById(R.id.spVisit);
        lySearch    = (LinearLayout) view.findViewById(R.id.lySearch);
        etStoreId   = (EditText) view.findViewById(R.id.etStoreId);
        btSearch    = (Button) view.findViewById(R.id.btSearch);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        storeRecycler.setLayoutManager(linearLayoutManager);

//        RouteAdapterRecyclerView pictureAdapterRecyclerView =  new

        new loadCompany().execute();

        btSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (!etStoreId.getText().toString().trim().equals("") ) {
                    store_id    = Integer.valueOf(etStoreId.getText().toString().trim());
                    company_id  = ((Company) spCompany.getSelectedItem () ).getId () ;
                    visit_id  = ((Visit) spVisit.getSelectedItem () ).getId () ;
                    new loadStores().execute();
                    storeRecycler.requestFocus();
                } else {

                    Toast.makeText(getActivity() , R.string.text_requiere_store, Toast.LENGTH_LONG).show();
                    etStoreId.requestFocus();
                    return;
                }


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
            companyes = AuditUtil.getCompanies(1,1,1);

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

                visitArrayAdapter = new ArrayAdapter<Visit>(getContext(),android.R.layout.simple_spinner_item, visits());
                visitArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spVisit.setAdapter(visitArrayAdapter);


                lySearch.setVisibility(View.VISIBLE);


            } else  {

                Toast.makeText(getActivity() , R.string.message_no_found, Toast.LENGTH_LONG).show();
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

    private ArrayList<Visit> visits(){
        ArrayList<Visit> visits = new ArrayList<Visit>();

        visits.add(new Visit(1,"Visita 1"));
        visits.add(new Visit(2,"Visita 2"));
        visits.add(new Visit(3,"Visita 3"));
        visits.add(new Visit(4,"Visita 4"));
        visits.add(new Visit(5,"Visita 5"));
        visits.add(new Visit(6,"Visita 6"));
        visits.add(new Visit(7,"Visita 7"));
        visits.add(new Visit(8,"Visita 8"));

        return  visits;
    }



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
            return AuditUtil.getStoresVisit(store_id,company_id,visit_id);

        }
        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(ArrayList<Store> stores) {


            if (stores.size() > 0 )  {

                storesVisitAdapterRecyclerView =  new StoresVisitAdapterRecyclerView(stores, R.layout.cardview_store_visit,getActivity(),true);
                storeRecycler.setAdapter(storesVisitAdapterRecyclerView);
                //lySearch.setVisibility(View.VISIBLE);
            } else  {

                Toast.makeText(getContext() , R.string.message_no_found, Toast.LENGTH_LONG).show();
                //lySearch.setVisibility(View.INVISIBLE);
            }
            pDialog.dismiss();
        }

    }

}
