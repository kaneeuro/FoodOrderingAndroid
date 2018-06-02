package com.sadic.formationandroid.activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sadic.formationandroid.R;
import com.sadic.formationandroid.adapters.InvoiceAdapter;
import com.sadic.formationandroid.entities.Invoice;
import com.sadic.formationandroid.services.RetrofitService;
import com.sadic.formationandroid.services.RetrofitUtlis;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailsActivity extends AppCompatActivity{

    TextView textView,textView1,textView2,textView3,textView4,textView5,
             textView6,textView7,textView8,textView9,textView10;
    Button button;
    RetrofitService retrofitService;
    Invoice invoice;
    Long id = InvoiceAdapter.idInvoiceSelected;
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    DateFormat mediumDateFormat = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM);
    private Long longDatePaiement;
    private Date datePaiement;
    private ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
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

        textView = (TextView) findViewById(R.id.message);
        textView1 = (TextView) findViewById(R.id.numero);
        textView2 = (TextView) findViewById(R.id.debut);
        textView3 = (TextView) findViewById(R.id.fin);
        textView4 = (TextView) findViewById(R.id.description);
        textView5 = (TextView) findViewById(R.id.montant);
        textView6 = (TextView) findViewById(R.id.validite);
        textView7 = (TextView) findViewById(R.id.statut);
        textView8 = (TextView) findViewById(R.id.datevalidite);
        textView9 = (TextView) findViewById(R.id.paiement);
        textView10 = (TextView) findViewById(R.id.datepaiement);
        button = (Button) findViewById(R.id.payer);

        image = (ImageView) findViewById(R.id.logocirculaire);

        retrofitService = RetrofitUtlis.getRetrofitService();

        getDetailsFacture();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                paymentInvoice();
            }
        });
    }

    public void alertDialog(View view){
        android.support.v7.app.AlertDialog.Builder alertDialogBuilder = new android.support.v7.app.AlertDialog.Builder(this);
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

        android.support.v7.app.AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void paymentInvoice() {
        retrofitService.paymentInvoice(id).enqueue(new Callback<Invoice>() {
            @Override
            public void onResponse(Call<Invoice> call, Response<Invoice> response) {
                if (response.isSuccessful()){
                    if (response.body().getStatus().equals("paye")){
                        image.setImageDrawable(getResources().getDrawable(R.drawable.invoice));
                        textView.setVisibility(View.VISIBLE);
                        textView.setText("Paiement effectué avec succès!");
                        textView7.setText("Facture payée");
                        textView7.setTextColor(getResources().getColor(R.color.colorGreen));
                        textView10.setVisibility(View.VISIBLE);
                        longDatePaiement=Long.valueOf(response.body().getDatePayment());
                        datePaiement = new Date(longDatePaiement);
                        textView9.setVisibility(View.VISIBLE);
                        textView9.setText(mediumDateFormat.format(new Date(""+datePaiement)));
                        textView6.setTextColor(getResources().getColor(R.color.colorBlack));
                        button.setVisibility(View.GONE);
                    }
                    else {
                        textView.setVisibility(View.VISIBLE);
                        textView.setText("Un problème est survenu. Merci de réessayer!");
                        textView.setTextColor(getResources().getColor(R.color.colorRed));
                    }

                }
            }

            @Override
            public void onFailure(Call<Invoice> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Impossible d'accéder au serveur!", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getDetailsFacture() {
        retrofitService.getInvoiceById(InvoiceAdapter.idInvoiceSelected).enqueue(new Callback<Invoice>() {
            @Override
            public void onResponse(Call<Invoice> call, Response<Invoice> response) {
                if (response.isSuccessful()){
                    invoice = response.body();
                    textView1.setText(invoice.getNumber());
                    textView2.setText(mediumDateFormat.format(new Date(invoice.getPeriodFrom().replace('-','/'))).substring(0,12));
                    textView3.setText(mediumDateFormat.format(new Date(invoice.getPeriodTo().replace('-','/'))).substring(0,12));
                    textView4.setText(invoice.getDescription());
                    textView5.setText(""+invoice.getPrice()+" FCFA");

                    try {
                        if (invoice.getStatus().equals("paye")){
                            image.setImageDrawable(getResources().getDrawable(R.drawable.invoice));
                            textView7.setText("Facture déjà payée");
                            textView7.setTextColor(getResources().getColor(R.color.colorGreen));
                            textView10.setVisibility(View.VISIBLE);
                            textView9.setVisibility(View.VISIBLE);
                            longDatePaiement=Long.valueOf(invoice.getDatePayment());
                            datePaiement = new Date(longDatePaiement);
                            textView9.setText(mediumDateFormat.format(new Date(""+datePaiement)));
                            button.setVisibility(View.GONE);
                        }
                        else if (invoice.getStatus().equals("impaye") && dateFormat.parse(invoice.getDateTill()).before(new Date())) {
                            textView.setVisibility(View.VISIBLE);
                            textView.setText("Date paiement expirée!");
                            textView.setTextColor(getResources().getColor(R.color.colorRed));
                            textView7.setText("Facture impayée");
                            textView7.setTextColor(getResources().getColor(R.color.colorRed));
                            textView8.setVisibility(View.VISIBLE);
                            textView6.setVisibility(View.VISIBLE);
                            textView6.setText(mediumDateFormat.format(new Date(invoice.getDateTill().replace('-','/'))).substring(0,12));
                            textView6.setTextColor(getResources().getColor(R.color.colorRed));
                            button.setVisibility(View.GONE);
                        }
                        else if (invoice.getStatus().equals("impaye") && invoice.getCustomer().getSolde()<invoice.getPrice()){
                            textView.setVisibility(View.VISIBLE);
                            textView.setText("Votre solde est insuffisant!");
                            textView.setTextColor(getResources().getColor(R.color.colorRed));
                            textView7.setText("Facture impayée");
                            textView7.setTextColor(getResources().getColor(R.color.colorRed));
                            textView8.setVisibility(View.VISIBLE);
                            textView6.setVisibility(View.VISIBLE);
                            textView6.setText(mediumDateFormat.format(new Date(invoice.getDateTill().replace('-','/'))).substring(0,12));
                            textView6.setTextColor(getResources().getColor(R.color.colorRed));
                            button.setVisibility(View.GONE);
                        }
                        else {
                            textView8.setVisibility(View.VISIBLE);
                            textView6.setVisibility(View.VISIBLE);
                            textView6.setText(mediumDateFormat.format(new Date(invoice.getDateTill().replace('-','/'))).substring(0,12));
                            textView6.setTextColor(getResources().getColor(R.color.colorRed));
                            textView7.setText("Facture impayée");
                            textView7.setTextColor(getResources().getColor(R.color.colorRed));
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<Invoice> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Impossible d'accéder au serveur!", Toast.LENGTH_LONG).show();
            }
        });
    }
}
