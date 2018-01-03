package com.sadicomputing.foodordering.activity.serveur;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.siyamed.shapeimageview.CircularImageView;
import com.jaychang.srv.SimpleCell;
import com.jaychang.srv.SimpleRecyclerView;
import com.jaychang.srv.decoration.SectionHeaderProvider;
import com.jaychang.srv.decoration.SimpleSectionHeaderProvider;
import com.sadicomputing.foodordering.R;
import com.sadicomputing.foodordering.activity.LoginActivity;
import com.sadicomputing.foodordering.adapter.MenuAdapter;
import com.sadicomputing.foodordering.entity.Article;
import com.sadicomputing.foodordering.entity.Categorie;
import com.sadicomputing.foodordering.entity.Commande;
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

public class ServeurMenuActivity extends AppCompatActivity {

    private int mNotificationsCount=0;
    private Context contextView;
    private RetrofitService retrofitService;
    private SwipeRefreshLayout mSwipeLayout;
    private SimpleRecyclerView simpleRecyclerView;
    private List<Categorie> categories = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serveur_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        contextView = this;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        retrofitService = RetrofitUtlis.getRetrofitService();
        simpleRecyclerView = findViewById(R.id.reclycerViewPlatsdujour);
        mSwipeLayout = findViewById(R.id.swipeRefreshLayoutPlatsdujour);

        addRecyclerHeaders();
        getAllCategories();
        getAllMenudujour();

        mSwipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getAllMenudujour();
            }
        });

    }

    /*
    - Create RecyclerViewHeaders
     */
    private void addRecyclerHeaders()
    {
        SectionHeaderProvider<Article> sh=new SimpleSectionHeaderProvider<Article>() {
            @NonNull
            @Override
            public View getSectionHeaderView(@NonNull final Article article, int i) {
                View view = LayoutInflater.from(ServeurMenuActivity.this).inflate(R.layout.header_menu, null, false);
                TextView textView =  view.findViewById(R.id.designationmenudujour);
                CircularImageView imageView =  view.findViewById(R.id.imagemenudujour);
                textView.setText(article.getCategorie().getNom());
                Constantes.loadImage(contextView,article.getCategorie().getImageUrl(),imageView);
                return view;
            }

            @Override
            public boolean isSameSection(@NonNull Article article, @NonNull Article nextArticle) {
                return article.getCategorie().getIdCategorie() == nextArticle.getCategorie().getIdCategorie();
            }
            // Optional, whether the header is sticky, default false
            @Override
            public boolean isSticky() {
                return true;
            }
        };
        simpleRecyclerView.setSectionHeader(sh);
    }

    private void getAllMenudujour() {
        mSwipeLayout.setRefreshing(true);
        retrofitService.getAllArticles().enqueue(new Callback<List<Article>>() {
            @Override
            public void onResponse(Call<List<Article>> call, Response<List<Article>> response) {
                if (response.isSuccessful()){
                    /*for (Article article : response.body()){
                        if (article.getCategorie().equals(categories.get(0)) || article.getCategorie().equals(categories.get(1))){
                            response.body().remove(article);
                        }
                    }*/
                    //CUSTOM SORT ACCORDING TO CATEGORIES
                    Collections.sort(response.body(), new Comparator<Article>(){
                        public int compare(Article article, Article nextArticle) {
                            return (int) (article.getCategorie().getIdCategorie() - nextArticle.getCategorie().getIdCategorie());
                        }
                    });
                    List<MenuAdapter> cells = new ArrayList<>();
                    simpleRecyclerView.removeAllCells();
                    //LOOP THROUGH ARTICLES INSTANTIATING THEIR CELLS AND ADDING TO CELLS COLLECTION
                    for (Article article : response.body()) {
                        MenuAdapter cell = new MenuAdapter(article, contextView, response.body());

                        // There are two default cell listeners: OnCellClickListener<CELL, VH, T> and OnCellLongClickListener<CELL, VH, T>
                        cell.setOnCellClickListener2(new SimpleCell.OnCellClickListener2<MenuAdapter, MenuAdapter.ViewHolder, Article>() {
                            @Override
                            public void onCellClicked(MenuAdapter adapter, MenuAdapter.ViewHolder viewHolder, Article item) {

                            }
                        });
                        cell.setOnCellLongClickListener2(new SimpleCell.OnCellLongClickListener2<MenuAdapter, MenuAdapter.ViewHolder, Article>() {
                            @Override
                            public void onCellLongClicked(MenuAdapter adapter, MenuAdapter.ViewHolder viewHolder, Article item) {

                            }
                        });
                        cells.add(cell);
                    }
                    simpleRecyclerView.addCells(cells);
                }
            }

            @Override
            public void onFailure(Call<List<Article>> call, Throwable t) {
                LoginActivity loginActivity = new LoginActivity();
                if (!loginActivity.isOnline())
                    serverDialog(contextView);
            }
        });

        mSwipeLayout.setRefreshing(false);
    }

    private void getAllCategories() {
        mSwipeLayout.setRefreshing(true);
        retrofitService.getAllCategories().enqueue(new Callback<List<Categorie>>() {
            @Override
            public void onResponse(Call<List<Categorie>> call, Response<List<Categorie>> response) {
                if (response.isSuccessful()){
                    categories = response.body();
                }
            }

            @Override
            public void onFailure(Call<List<Categorie>> call, Throwable t) {

            }
        });

        mSwipeLayout.setRefreshing(false);
    }

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

}
