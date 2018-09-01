package com.sadicomputing.foodordering.activity.cuisinier;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.siyamed.shapeimageview.CircularImageView;
import com.jaychang.srv.SimpleCell;
import com.jaychang.srv.SimpleRecyclerView;
import com.jaychang.srv.decoration.SectionHeaderProvider;
import com.jaychang.srv.decoration.SimpleSectionHeaderProvider;
import com.sadicomputing.foodordering.R;
import com.sadicomputing.foodordering.activity.accueil.LoginActivity;
import com.sadicomputing.foodordering.activity.serveur.ServeurCommandeActivity;
import com.sadicomputing.foodordering.activity.serveur.ServeurCommandesdujourActivity;
import com.sadicomputing.foodordering.adapter.CuisineCommandesdujourAdapter;
import com.sadicomputing.foodordering.adapter.CuisineCommandesdujourAdapter;
import com.sadicomputing.foodordering.entity.Categorie;
import com.sadicomputing.foodordering.entity.CommandeArticle;
import com.sadicomputing.foodordering.service.RetrofitService;
import com.sadicomputing.foodordering.service.RetrofitUtlis;
import com.sadicomputing.foodordering.utils.Constantes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CuisineCommandesdujourActivity extends AppCompatActivity {

    private Context contextView;
    private RetrofitService retrofitService;
    private SwipeRefreshLayout mSwipeLayout;
    private SimpleRecyclerView simpleRecyclerView;
    private List<Categorie> categories = new ArrayList<>();
    private ImageView imageView2, imageView3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuisine_commandesdujour);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        contextView = this;

        retrofitService = RetrofitUtlis.getRetrofitService();
        simpleRecyclerView = findViewById(R.id.reclycerViewPlatsdujour);
        mSwipeLayout = findViewById(R.id.swipeRefreshLayoutPlatsdujour);

        addRecyclerHeaders();
        getCuisinierCommandesDuJour();

        mSwipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getCuisinierCommandesDuJour();
            }
        });
    }

    /*
        - Create RecyclerViewHeaders
         */
    private void addRecyclerHeaders()
    {
        final SectionHeaderProvider<CommandeArticle> sh=new SimpleSectionHeaderProvider<CommandeArticle>() {
            @NonNull
            @Override
            public View getSectionHeaderView(@NonNull final CommandeArticle article, int i) {
                View view = LayoutInflater.from(CuisineCommandesdujourActivity.this).inflate(R.layout.header_serveurcommandes, null, false);
                TextView textView =  view.findViewById(R.id.commande);
                TextView textView2 =  view.findViewById(R.id.table);
                TextView textView3 =  view.findViewById(R.id.date);
                CircularImageView imageView =  view.findViewById(R.id.imagecommande);
                imageView2 =  view.findViewById(R.id.imageaddcommande);
                imageView2.setVisibility(View.GONE);
                Constantes.loadImage(contextView,"meal.png", imageView);
                textView.setText("Commande : "+article.getCommande().getNumero());
                textView2.setText("Heure : "+article.getCommande().getDate().substring(11,16));
                textView3.setVisibility(View.GONE);

                return view;
            }

            @Override
            public boolean isSameSection(@NonNull CommandeArticle article, @NonNull CommandeArticle nextArticle) {
                return article.getCommande().getIdCommande() == nextArticle.getCommande().getIdCommande();
            }
            // Optional, whether the header is sticky, default false
            @Override
            public boolean isSticky() {
                return true;
            }
        };
        simpleRecyclerView.setSectionHeader(sh);
    }

    private void getCuisinierCommandesDuJour() {
        mSwipeLayout.setRefreshing(true);
        retrofitService.getCuisinierCommandesDuJour(LoginActivity.compte.getLogin()).enqueue(new Callback<List<CommandeArticle>>() {
            @Override
            public void onResponse(Call<List<CommandeArticle>> call, Response<List<CommandeArticle>> response) {
                if (response.isSuccessful()){
                    if (!response.body().isEmpty()){
                        //CUSTOM SORT ACCORDING TO CATEGORIES
                        Collections.sort(response.body(), new Comparator<CommandeArticle>(){
                            public int compare(CommandeArticle article, CommandeArticle nextArticle) {
                                return (int) (article.getCommande().getIdCommande() - nextArticle.getCommande().getIdCommande());
                            }
                        });
                        List<CuisineCommandesdujourAdapter> cells = new ArrayList<>();
                        simpleRecyclerView.removeAllCells();
                        //LOOP THROUGH ARTICLES INSTANTIATING THEIR CELLS AND ADDING TO CELLS COLLECTION
                        for (CommandeArticle article : response.body()) {
                            CuisineCommandesdujourAdapter cell = new CuisineCommandesdujourAdapter(article, contextView, response.body(), simpleRecyclerView, mSwipeLayout);

                            // There are two default cell listeners: OnCellClickListener<CELL, VH, T> and OnCellLongClickListener<CELL, VH, T>
                            cell.setOnCellClickListener2(new SimpleCell.OnCellClickListener2<CuisineCommandesdujourAdapter, CuisineCommandesdujourAdapter.ViewHolder, CommandeArticle>() {
                                @Override
                                public void onCellClicked(CuisineCommandesdujourAdapter adapter, CuisineCommandesdujourAdapter.ViewHolder viewHolder, CommandeArticle item) {
                                    //Toast.makeText(contextView, item.getArticle().getDesignation()+"", Toast.LENGTH_LONG).show();
                                }
                            });
                            cell.setOnCellLongClickListener2(new SimpleCell.OnCellLongClickListener2<CuisineCommandesdujourAdapter, CuisineCommandesdujourAdapter.ViewHolder, CommandeArticle>() {
                                @Override
                                public void onCellLongClicked(CuisineCommandesdujourAdapter adapter, CuisineCommandesdujourAdapter.ViewHolder viewHolder, CommandeArticle item) {
                                    //Toast.makeText(contextView, item.getCommande().getDate()+"", Toast.LENGTH_LONG).show();
                                }
                            });
                            cells.add(cell);
                        }
                        simpleRecyclerView.addCells(cells);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<CommandeArticle>> call, Throwable t) {
                Log.e("MESSAGE",t.getMessage());
            }
        });
        mSwipeLayout.setRefreshing(false);
    }

}
