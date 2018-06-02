package com.sadic.formationandroid.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.sadic.formationandroid.R;
import com.sadic.formationandroid.entities.Customer;
import com.sadic.formationandroid.services.RetrofitService;
import com.sadic.formationandroid.services.RetrofitUtlis;

import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    EditText editText;
    Button button;
    RetrofitService retrofitService;
    View context = null;
    String login;
    static String loginstatic;
    static Double solde;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = (EditText) findViewById(R.id.login);
        button = (Button) findViewById(R.id.btnLogin);

        retrofitService = RetrofitUtlis.getRetrofitService();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context = view;
                login = editText.getText().toString().trim();
                if (!login.equals("")){
                    loginstatic = login;
                    seConnecter();
                }
                else
                    Snackbar.make(view, "Veuillez entrer votre référence!", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });
    }

    private void seConnecter() {
        retrofitService.login(login).enqueue(new Callback<Customer>() {
            @Override
            public void onResponse(Call<Customer> call, Response<Customer> response) {
                if (response.isSuccessful()){
                   if (response.body()!=null){
                       solde = response.body().getSolde();
                       startActivity(new Intent(getApplicationContext(), LoadingActivity.class));
                   }
                }
            }

            @Override
            public void onFailure(Call<Customer> call, Throwable t) {
                //Log.e("ERROR",t.getMessage());
                if (t.getMessage().contains("End of input at line 1 column 1"))
                    Snackbar.make(context, "Votre référence est incorrecte!", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                else
                    Snackbar.make(context, "Impossible d'accéder au serveur!", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                //startActivity(new Intent(getApplicationContext(), LoadingActivity.class));
            }
        });
    }


}
