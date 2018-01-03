package com.sadicomputing.foodordering.activity.cuisinier;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.github.siyamed.shapeimageview.CircularImageView;
import com.jaychang.srv.SimpleCell;
import com.jaychang.srv.SimpleRecyclerView;
import com.jaychang.srv.decoration.SectionHeaderProvider;
import com.jaychang.srv.decoration.SimpleSectionHeaderProvider;
import com.sadicomputing.foodordering.R;
import com.sadicomputing.foodordering.adapter.CommandeAdapter;
import com.sadicomputing.foodordering.entity.Categorie;
import com.sadicomputing.foodordering.entity.CommandeArticle;
import com.sadicomputing.foodordering.service.RetrofitService;
import com.sadicomputing.foodordering.service.RetrofitUtlis;
import com.sadicomputing.foodordering.utils.Constantes;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CuisineCommandeActivity extends AppCompatActivity {

    private Context contextView;
    private RetrofitService retrofitService;
    private SwipeRefreshLayout mSwipeLayout;
    private SimpleRecyclerView simpleRecyclerView;
    private List<Categorie> categories = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuisine_commande);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        contextView = this;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        retrofitService = RetrofitUtlis.getRetrofitService();
        simpleRecyclerView = findViewById(R.id.reclycerViewPlatsdujour);
        mSwipeLayout = findViewById(R.id.swipeRefreshLayoutPlatsdujour);

        addRecyclerHeaders();
        getAllCommandeArticlesByStatutCommande();

        mSwipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getAllCommandeArticlesByStatutCommande();
            }
        });
    }
    /*
        - Create RecyclerViewHeaders
         */
    private void addRecyclerHeaders()
    {
        SectionHeaderProvider<CommandeArticle> sh=new SimpleSectionHeaderProvider<CommandeArticle>() {
            @NonNull
            @Override
            public View getSectionHeaderView(@NonNull final CommandeArticle article, int i) {
                View view = LayoutInflater.from(CuisineCommandeActivity.this).inflate(R.layout.header_menu, null, false);
                TextView textView =  view.findViewById(R.id.designationmenudujour);
                CircularImageView imageView =  view.findViewById(R.id.imagemenudujour);
                textView.setText("Commande: "+article.getCommande().getNumero().toString());
                Constantes.loadImage(contextView,"meal.png", imageView);

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

    private void getAllCommandeArticlesByStatutCommande() {
        mSwipeLayout.setRefreshing(true);
        retrofitService.getAllCommandeArticlesByStatutCommande(1).enqueue(new Callback<List<CommandeArticle>>() {
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
                        List<CommandeAdapter> cells = new ArrayList<>();
                        simpleRecyclerView.removeAllCells();
                        //LOOP THROUGH ARTICLES INSTANTIATING THEIR CELLS AND ADDING TO CELLS COLLECTION
                        for (CommandeArticle article : response.body()) {
                            CommandeAdapter cell = new CommandeAdapter(article, contextView, response.body(), simpleRecyclerView, mSwipeLayout);

                            // There are two default cell listeners: OnCellClickListener<CELL, VH, T> and OnCellLongClickListener<CELL, VH, T>
                        cell.setOnCellClickListener2(new SimpleCell.OnCellClickListener2<CommandeAdapter, CommandeAdapter.ViewHolder, CommandeArticle>() {
                            @Override
                            public void onCellClicked(CommandeAdapter adapter, CommandeAdapter.ViewHolder viewHolder, CommandeArticle item) {
                                //Toast.makeText(contextView, item.getArticle().getDesignation()+"", Toast.LENGTH_LONG).show();
                            }
                        });
                        cell.setOnCellLongClickListener2(new SimpleCell.OnCellLongClickListener2<CommandeAdapter, CommandeAdapter.ViewHolder, CommandeArticle>() {
                            @Override
                            public void onCellLongClicked(CommandeAdapter adapter, CommandeAdapter.ViewHolder viewHolder, CommandeArticle item) {
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
