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
import com.dataservicios.adminttaudit.model.Company;
import com.dataservicios.adminttaudit.model.Store;
import com.dataservicios.adminttaudit.util.AuditUtil;
import com.dataservicios.adminttaudit.util.SessionManager;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchCodClientFragment extends Fragment {


    private static final String LOG_TAG = SearchCodClientFragment.class.getSimpleName();

    private SessionManager session;
    private Activity myActivity = (Activity) getActivity();
    private ProgressDialog pDialog;
    private LinearLayout lySearch;
    private Button btSearch ;
    private int                         user_id;
    private String                      codClient;
    private EditText etCodClient;
    private ArrayAdapter<Company> spinnerAdapter;
    private StoresAdapterRecyclerView pictureAdapterRecyclerView;

    private RecyclerView storeRecycler;


    public SearchCodClientFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        session = new SessionManager(getActivity());
        HashMap<String, String> userSesion = session.getUserDetails();
        user_id = Integer.valueOf(userSesion.get(SessionManager.KEY_ID_USER)) ;



        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search_cod_client, container, false);
        storeRecycler  = (RecyclerView) view.findViewById(R.id.storeRecycler);


        lySearch  = (LinearLayout) view.findViewById(R.id.lySearch);
        etCodClient  = (EditText) view.findViewById(R.id.etCodClient);
        btSearch  = (Button) view.findViewById(R.id.btSearch);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        storeRecycler.setLayoutManager(linearLayoutManager);

//        RouteAdapterRecyclerView pictureAdapterRecyclerView =  new



        btSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (!etCodClient.getText().toString().trim().equals("") ) {
                    codClient    = String.valueOf(etCodClient.getText().toString().trim());

                    new loadStores().execute();
                    storeRecycler.requestFocus();
                } else {

                    Toast.makeText(getActivity() , R.string.text_requiere_store, Toast.LENGTH_LONG).show();
                    etCodClient.requestFocus();
                    return;
                }


            }
        });


        return view;
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
            return AuditUtil.getStoresForCode(codClient);

        }
        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(ArrayList<Store> stores) {

            if (stores.size() > 0 )  {

                pictureAdapterRecyclerView =  new StoresAdapterRecyclerView(stores, R.layout.cardview_store,getActivity(),false);
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
