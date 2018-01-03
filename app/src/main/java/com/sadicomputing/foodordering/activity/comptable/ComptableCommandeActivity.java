package com.sadicomputing.foodordering.activity.comptable;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.sadicomputing.foodordering.R;
import com.sadicomputing.foodordering.adapter.ComptableCommandeAdapter;
import com.sadicomputing.foodordering.adapter.PlatsdujourAdapter;
import com.sadicomputing.foodordering.entity.Article;
import com.sadicomputing.foodordering.entity.Commande;
import com.sadicomputing.foodordering.service.RetrofitService;
import com.sadicomputing.foodordering.service.RetrofitUtlis;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ComptableCommandeActivity extends AppCompatActivity {

    private Context contextView;
    private RecyclerView recyclerView;
    private RetrofitService retrofitService;
    private ComptableCommandeAdapter mAdapter;
    private List<Commande> commandes = new ArrayList<>();
    private SwipeRefreshLayout mSwipeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comptable_commande);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        contextView = this;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        retrofitService = RetrofitUtlis.getRetrofitService();
        recyclerView = findViewById(R.id.reclycerViewPlatsdujour);
        mSwipeLayout = findViewById(R.id.swipeRefreshLayoutPlatsdujour);

        mAdapter = new ComptableCommandeAdapter(this, commandes);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);

        getCommandesByStatut();

        mSwipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getCommandesByStatut();
            }
        });
    }

    private void getCommandesByStatut() {
        mSwipeLayout.setRefreshing(true);
        retrofitService.getCommandesByStatut(2).enqueue(new Callback<List<Commande>>() {
            @Override
            public void onResponse(Call<List<Commande>> call, Response<List<Commande>> response) {
                if (response.isSuccessful()){
                    mAdapter.updateAnswers(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Commande>> call, Throwable t) {
                Log.e("ERREUR", t.getMessage());
            }
        });

        mSwipeLayout.setRefreshing(false);
    }

}
