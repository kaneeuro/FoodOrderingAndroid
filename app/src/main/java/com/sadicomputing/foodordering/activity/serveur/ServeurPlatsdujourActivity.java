package com.sadicomputing.foodordering.activity.serveur;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.sadicomputing.foodordering.R;
import com.sadicomputing.foodordering.activity.LoginActivity;
import com.sadicomputing.foodordering.adapter.PlatsdujourAdapter;
import com.sadicomputing.foodordering.entity.Article;
import com.sadicomputing.foodordering.entity.CommandeArticleTemporaire;
import com.sadicomputing.foodordering.entity.Tables;
import com.sadicomputing.foodordering.service.RetrofitService;
import com.sadicomputing.foodordering.service.RetrofitUtlis;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServeurPlatsdujourActivity extends AppCompatActivity {

    private Context contextView;
    private RecyclerView recyclerView;
    private RetrofitService retrofitService;
    private PlatsdujourAdapter mAdapter;
    private List<Article> articles = new ArrayList<>();
    private SwipeRefreshLayout mSwipeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serveur_platsdujour);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        contextView = this;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        retrofitService = RetrofitUtlis.getRetrofitService();
        recyclerView = findViewById(R.id.reclycerViewPlatsdujour);
        mSwipeLayout = findViewById(R.id.swipeRefreshLayoutPlatsdujour);

        mAdapter = new PlatsdujourAdapter(this, articles);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);
        /*recyclerView.setHasFixedSize(true);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);*/

        getAllPlatsDuJour();

        mSwipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getAllPlatsDuJour();
            }
        });
    }

    private void getAllPlatsDuJour() {
        mSwipeLayout.setRefreshing(true);
        retrofitService.getAllArticlesByCategorie((long) 1).enqueue(new Callback<List<Article>>() {
            @Override
            public void onResponse(Call<List<Article>> call, Response<List<Article>> response) {
                if (response.isSuccessful()){
                    mAdapter.updateAnswers(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Article>> call, Throwable t) {
                serverDialog(contextView);
            }
        });

        mSwipeLayout.setRefreshing(false);
    }

    /*public void addArticleTemporaire(final CommandeArticleTemporaire temporaire){
        retrofitService.saveArticleTemporaire(temporaire).enqueue(new Callback<CommandeArticleTemporaire>() {
            @Override
            public void onResponse(Call<CommandeArticleTemporaire> call, Response<CommandeArticleTemporaire> response) {
                if (response.isSuccessful())
                    Toast.makeText(getApplicationContext(),temporaire.getArticle().getDesignation()+" est ajouté(e) à la commande", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<CommandeArticleTemporaire> call, Throwable t) {
                Log.e("ERROR",t.getMessage());
                Toast.makeText(getApplicationContext(),"Impossible d'ajouter cet article à la commande", Toast.LENGTH_SHORT).show();
            }
        });
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_commande, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_suivant) {
            ServeurResumeCommandeActivity.prixTotal=0;
            startActivity(new Intent(getApplicationContext(), ServeurResumeCommandeActivity.class));
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

}
