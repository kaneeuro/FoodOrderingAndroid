package com.sadic.formationandroid.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.sadic.formationandroid.R;
import com.sadic.formationandroid.adapters.InvoiceAdapter;
import com.sadic.formationandroid.entities.Customer;
import com.sadic.formationandroid.entities.Invoice;
import com.sadic.formationandroid.services.RetrofitService;
import com.sadic.formationandroid.services.RetrofitUtlis;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class InvoicesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RetrofitService retrofitService;
    private InvoiceAdapter mAdapter;
    private TextView mTextView, soldeTextView;
    private List<Invoice> invoices = new ArrayList<>();
    private SwipeRefreshLayout mSwipeLayout;
    private Double nouveausolde = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoices);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog(view);
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        retrofitService = RetrofitUtlis.getRetrofitService();
        recyclerView = (RecyclerView) findViewById(R.id.rv);
        mSwipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        soldeTextView = (TextView) findViewById(R.id.solde);
        mTextView = (TextView) findViewById(R.id.totale);

        mAdapter = new InvoiceAdapter(this, invoices);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setHasFixedSize(true);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);
        mTextView.setText("Factures ("+mAdapter.getItemCount()+")");
        if(nouveausolde!=MainActivity.solde && nouveausolde!=0.0)
            soldeTextView.setText("Solde: "+nouveausolde+" FCFA");
        else
            soldeTextView.setText("Solde: "+MainActivity.solde+" FCFA");


        getInvoicesByCustomer();
        mSwipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getInvoicesByCustomer();
            }
        });

    }
    public void getInvoicesByCustomer() {
        mSwipeLayout.setRefreshing(true);
        retrofitService.getInvoicesByCustomer(MainActivity.loginstatic).enqueue(new Callback<List<Invoice>>() {
            @Override
            public void onResponse(Call<List<Invoice>> call, Response<List<Invoice>> response) {
                if(response.isSuccessful()) {
                    mAdapter.updateAnswers(response.body());
                    nouveausolde = response.body().get(0).getCustomer().getSolde();
                    soldeTextView.setText("Solde: "+nouveausolde+" FCFA");
                    mTextView.setText("Factures ("+mAdapter.getItemCount()+")");
                }
            }

            @Override
            public void onFailure(Call<List<Invoice>> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Impossible d'accéder au serveur!", Toast.LENGTH_LONG).show();
            }
        });

        mSwipeLayout.setRefreshing(false);
    }

    public void alertDialog(View view){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Confirmation");
        alertDialogBuilder.setMessage("Voulez-vous vraiment quitter l'application?");
        alertDialogBuilder.setPositiveButton("OUI", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                startActivity(new Intent(getApplicationContext(), DeconnexionActivity.class));
            }
        });
        alertDialogBuilder.setNegativeButton("ANNULER",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

}
