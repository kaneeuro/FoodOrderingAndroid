package com.sadicomputing.foodordering.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
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
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.sadicomputing.foodordering.R;
import com.sadicomputing.foodordering.entity.Compte;
import com.sadicomputing.foodordering.firebase.SharedPrefManager;
import com.sadicomputing.foodordering.service.RetrofitService;
import com.sadicomputing.foodordering.service.RetrofitUtlis;
import com.sadicomputing.foodordering.utils.Constantes;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

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
    private ProgressDialog progressDialog;

    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

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
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Connexion en cours ...");
        progressDialog.show();

        retrofitService.authentification(login, password).enqueue(new Callback<Compte>() {
            @Override
            public void onResponse(Call<Compte> call, Response<Compte> response) {
                if (response.isSuccessful()){
                    if (response.body()!=null){
                        compte = response.body();
                        sendTokenToServer(compte.getRole());
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

        progressDialog.dismiss();
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

    //storing token to mysql server
    private void sendTokenToServer(String roleCompte) {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Registering Device...");
        progressDialog.show();

        final String token = SharedPrefManager.getInstance(this).getDeviceToken();
        final String role = roleCompte;

        if (token == null) {
            progressDialog.dismiss();
            Toast.makeText(this, "Token not generated", Toast.LENGTH_LONG).show();
            return;
        }

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constantes.URL_REGISTER_DEVICE,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject obj = new JSONObject(response);
                            Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_LONG).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("role", role);
                params.put("token", token);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

}
