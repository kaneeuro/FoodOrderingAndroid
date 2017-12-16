package com.sadicomputing.foodordering.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.sadicomputing.foodordering.R;
import com.sadicomputing.foodordering.entity.Compte;
import com.sadicomputing.foodordering.service.RetrofitService;
import com.sadicomputing.foodordering.service.RetrofitUtlis;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends Activity {

    EditText editTextLogin, editTextPwd;
    Button btnConnexion;
    RetrofitService retrofitService;
    View contextView = null;
    String login="", password="";
    public static Compte compte;
    private static final String DEBUG_TAG = "NetworkStatusExample";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextLogin = findViewById(R.id.login);
        editTextPwd = findViewById(R.id.password);
        btnConnexion = findViewById(R.id.btnLogin);
        retrofitService = RetrofitUtlis.getRetrofitService();

        editTextLogin.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                login = editTextLogin.getText().toString().trim();
                if(login.equals(""))
                    editTextLogin.setError( "Champ obligatoire!" );
            }
        });
        editTextPwd.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                password = editTextPwd.getText().toString().trim();
                if(password.equals(""))
                    editTextPwd.setError( "Champ obligatoire!" );
            }
        });

        btnConnexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contextView = view;
                login = editTextLogin.getText().toString().trim();
                password = editTextPwd.getText().toString().trim();
                if (!isOnline()){
                    serverDialog(getApplicationContext());
                }
                else if(login.equals(""))
                    editTextLogin.setError( "Champ obligatoire!" );
                else if(password.equals(""))
                    editTextPwd.setError( "Champ obligatoire!" );
                else
                    seConnecter();
            }
        });
    }

    private void seConnecter() {
        retrofitService.authentification(login, password).enqueue(new Callback<Compte>() {
            @Override
            public void onResponse(Call<Compte> call, Response<Compte> response) {
                if (response.isSuccessful()){
                    if (response.body()!=null){
                        compte = response.body();
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        finish();
                        editTextLogin.setText("");
                        editTextPwd.setText("");
                    }
                }
            }

            @Override
            public void onFailure(Call<Compte> call, Throwable t) {
                Log.e("ERROR", t.getMessage());
                if (t.getMessage().contains("End of input at line 1 column 1 path $"))
                    alertDialog(contextView);
            }
        });
    }

    public void alertDialog(View view){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setIcon(R.drawable.ic_action_alert);
        alertDialogBuilder.setTitle("Authentification");
        alertDialogBuilder.setMessage("Votre login ou mot de passe est incorrect!");
        alertDialogBuilder.setNeutralButton("FERMER",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public void serverDialog(Context view){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setIcon(R.drawable.ic_action_alert);
        alertDialogBuilder.setTitle("Connexion impossible");
        alertDialogBuilder.setMessage("Votre appareil n'est pas connecté à internet.\nActiver votre connexion pour continuer.");
        alertDialogBuilder.setPositiveButton("FERMER",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public boolean isOnline() {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

}
