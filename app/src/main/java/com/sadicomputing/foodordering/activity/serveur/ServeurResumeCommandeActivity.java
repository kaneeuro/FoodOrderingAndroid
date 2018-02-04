package com.sadicomputing.foodordering.activity.serveur;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.sadicomputing.foodordering.R;
import com.sadicomputing.foodordering.activity.LoginActivity;
import com.sadicomputing.foodordering.activity.MainActivity;
import com.sadicomputing.foodordering.adapter.ArticleCommandeAdapter;
import com.sadicomputing.foodordering.entity.Commande;
import com.sadicomputing.foodordering.entity.CommandeArticle;
import com.sadicomputing.foodordering.entity.CommandeArticleTemporaire;
import com.sadicomputing.foodordering.entity.Tables;
import com.sadicomputing.foodordering.firebase.MyVolley;
import com.sadicomputing.foodordering.service.RetrofitService;
import com.sadicomputing.foodordering.service.RetrofitUtlis;
import com.sadicomputing.foodordering.utils.Constantes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServeurResumeCommandeActivity extends AppCompatActivity {

    private Context contextView;
    private RecyclerView recyclerView;
    private RetrofitService retrofitService;
    private ArticleCommandeAdapter mAdapter;
    private List<CommandeArticleTemporaire> articles = new ArrayList<>();
    private List<CommandeArticle> commandeArticles = new ArrayList<>();
    private SwipeRefreshLayout mSwipeLayout;
    public static TextView textView;
    public TextView textView2, textView3;
    private Button button;
    public static double prixTotal = 0;
    private ProgressDialog progressDialog;
    private Commande commandeServeur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serveur_resume_commande);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        contextView = this;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        retrofitService = RetrofitUtlis.getRetrofitService();
        recyclerView = findViewById(R.id.reclycerViewPlatsdujour);
        mSwipeLayout = findViewById(R.id.swipeRefreshLayoutPlatsdujour);

        textView = findViewById(R.id.prixtotal);
        textView2 = findViewById(R.id.total);
        textView3 = findViewById(R.id.pasdecommande);
        button = findViewById(R.id.passerlacommande);

        mAdapter = new ArticleCommandeAdapter(this, articles, mSwipeLayout);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);
        /*recyclerView.setHasFixedSize(true);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);*/

        getArticlesTemporairesByEmploye();
        mSwipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                prixTotal=0;
                getArticlesTemporairesByEmploye();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveCommandeArticles();
            }
        });

    }

    private void getArticlesTemporairesByEmploye() {
        mSwipeLayout.setRefreshing(true);
        retrofitService.getArticlesTemporairesByEmploye(LoginActivity.compte.getEmploye()).enqueue(new Callback<List<CommandeArticleTemporaire>>() {
            @Override
            public void onResponse(Call<List<CommandeArticleTemporaire>> call, Response<List<CommandeArticleTemporaire>> response) {
                if (response.isSuccessful()){
                    articles=response.body();
                    for (CommandeArticleTemporaire commandeArticleTemporaire:response.body()) {
                        prixTotal += commandeArticleTemporaire.getArticle().getPrix();
                    }
                    textView.setText(prixTotal+" FCFA");
                    mAdapter.updateAnswers(articles);
                    if (articles.isEmpty()){
                        textView.setVisibility(View.GONE);
                        textView2.setVisibility(View.GONE);
                        button.setVisibility(View.GONE);
                        textView3.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<CommandeArticleTemporaire>> call, Throwable t) {
                serverDialog(contextView);
            }
        });
        mSwipeLayout.setRefreshing(false);
    }

    public void saveCommandeArticles() {
        final Commande commande = new Commande(null, null, Long.valueOf(Constantes.randomString()), 1, null, LoginActivity.compte.getEmploye(), new Tables(MainActivity.numeroTable,2));
        for (CommandeArticleTemporaire cat:articles) {
            long quantiteCommande = (long) ArticleCommandeAdapter.quantiteArticle.get(cat.getIdCommandeArticleTemporaire().intValue(),1);
            commandeArticles.add(new CommandeArticle(cat.getArticle().getPrix(), cat.getArticle().getPrix()*quantiteCommande, quantiteCommande, cat.getArticle(),commande));
        }
        retrofitService.saveCommandeArticles(commandeArticles).enqueue(new Callback<List<CommandeArticle>>() {
            @Override
            public void onResponse(Call<List<CommandeArticle>> call, Response<List<CommandeArticle>> response) {
                if (response.isSuccessful()){
                    commandeServeur = response.body().get(0).getCommande();
                    deleteAllArticlesTemporaires();
                    prixTotal=0;
                    MainActivity.mNotificationsCountCommande=0;
                    getArticlesTemporairesByEmploye();
                    sendSinglePush();
                    commandeDialog();
                }
            }

            @Override
            public void onFailure(Call<List<CommandeArticle>> call, Throwable t) {
                serverDialog(contextView);
            }
        });
    }

    private void deleteAllArticlesTemporaires(){
        retrofitService.deleteAllArticlesTemporaires(articles).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()){
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e("ERROR",t.getMessage());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_addcommande, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_add) {
            startActivity(new Intent(getApplicationContext(), ServeurCommandeActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void serverDialog(Context view){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setIcon(R.drawable.ic_action_alert);
        alertDialogBuilder.setTitle("Connexion Impossible");
        alertDialogBuilder.setMessage("Impossible d'accéder au serveur.\nVeuillez vérifier votre connexion internet!");
        alertDialogBuilder.setPositiveButton("OUI",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public void commandeDialog(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setIcon(R.drawable.icons8_restaurant_menu);
        alertDialogBuilder.setTitle("Traitement commande");
        alertDialogBuilder.setMessage("La commande a été bien effectuée!");
        alertDialogBuilder.setPositiveButton("FERMER",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    //this method will send the push notification to the CUISINIER
    private void sendSinglePush(){
        final String title = "FOOD ORDERING";
        final String message = "Vous avez une nouvelle commande à préparer.\nNuméro Commande: "+commandeServeur.getNumero();
        final String image = null;
        final String role = "CUISINIER";

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Sending Notification ...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constantes.URL_SEND_SINGLE_PUSH,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();

                        Toast.makeText(ServeurResumeCommandeActivity.this, response, Toast.LENGTH_LONG).show();
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("title", title);
                params.put("message", message);

                if (!TextUtils.isEmpty(image))
                    params.put("image", image);

                params.put("role", role);
                return params;
            }
        };

        MyVolley.getInstance(this).addToRequestQueue(stringRequest);
    }

}