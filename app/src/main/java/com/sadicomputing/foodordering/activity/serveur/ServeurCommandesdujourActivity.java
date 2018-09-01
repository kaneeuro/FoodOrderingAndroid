package com.sadicomputing.foodordering.activity.serveur;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.github.siyamed.shapeimageview.CircularImageView;
import com.jaychang.srv.SimpleCell;
import com.jaychang.srv.SimpleRecyclerView;
import com.jaychang.srv.decoration.SectionHeaderProvider;
import com.jaychang.srv.decoration.SimpleSectionHeaderProvider;
import com.sadicomputing.foodordering.R;
import com.sadicomputing.foodordering.activity.accueil.LoginActivity;
import com.sadicomputing.foodordering.adapter.ServeurCommandesdujourAdapter;
import com.sadicomputing.foodordering.entity.Categorie;
import com.sadicomputing.foodordering.entity.CommandeArticle;
import com.sadicomputing.foodordering.service.RetrofitService;
import com.sadicomputing.foodordering.service.RetrofitUtlis;
import com.sadicomputing.foodordering.utils.Constantes;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersTouchListener;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServeurCommandesdujourActivity extends AppCompatActivity{
    private Context contextView;
    private RetrofitService retrofitService;
    private SwipeRefreshLayout mSwipeLayout;
    private SimpleRecyclerView simpleRecyclerView;
    private List<Categorie> categories = new ArrayList<>();
    private ImageView imageView2, imageView3;
    ServeurCommandesdujourHeadersAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serveur_commandesdujour);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        retrofitService = RetrofitUtlis.getRetrofitService();
        mSwipeLayout = findViewById(R.id.swipeRefreshLayoutPlatsdujour);
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.reclycerViewPlatsdujour);
        //Button button = (Button) findViewById(R.id.button_update);
        //final ToggleButton isReverseButton = (ToggleButton) findViewById(R.id.button_is_reverse);

        // Set adapter populated with example dummy data
        adapter = new ServeurCommandesdujourHeadersAdapter();
        //adapter.add("Animals below!");
        getServeurCommandesDuJour();
        recyclerView.setAdapter(adapter);

        mSwipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getServeurCommandesDuJour();
            }
        });

        // Set layout manager
        //int orientation = getLayoutManagerOrientation(getResources().getConfiguration().orientation);
        //final LinearLayoutManager layoutManager = new LinearLayoutManager(this, orientation, isReverseButton.isChecked());
        //recyclerView.setLayoutManager(layoutManager);

        // Add the sticky headers decoration
        //final StickyRecyclerHeadersDecoration headersDecor = new StickyRecyclerHeadersDecoration(adapter);
        //recyclerView.addItemDecoration(headersDecor);

        // Add decoration for dividers between list items
        //recyclerView.addItemDecoration(new DividerDecoration(this));

        // Add touch listeners
        StickyRecyclerHeadersTouchListener touchListener = new StickyRecyclerHeadersTouchListener(recyclerView, null);
        touchListener.setOnHeaderClickListener(new StickyRecyclerHeadersTouchListener.OnHeaderClickListener() {
                    @Override
                    public void onHeaderClick(View header, int position, long headerId) {
                        Toast.makeText(ServeurCommandesdujourActivity.this, "Header position: " + position + ", id: " + headerId,
                                Toast.LENGTH_SHORT).show();
                    }
                });
        recyclerView.addOnItemTouchListener(touchListener);
        /*recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                adapter.remove(adapter.getItem(position));
            }
        }));*/
        /*adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                headersDecor.invalidateHeaders();
            }
        });*/


    }

    private class ServeurCommandesdujourHeadersAdapter
            extends ServeurCommandesdujourAdapter<RecyclerView.ViewHolder>
            implements StickyRecyclerHeadersAdapter<RecyclerView.ViewHolder> {
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_platsdujour, parent, false);
            return new RecyclerView.ViewHolder(view) {
            };
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            //TextView textVieww = (TextView) holder.itemView;
            //textVieww.setText(getItem(position));

            TextView textView = holder.itemView.findViewById(R.id.designationplatsdujour);
            TextView textView2 = holder.itemView.findViewById(R.id.prixplatsdujour);
            ImageView imageView = holder.itemView.findViewById(R.id.imageplatsdujour);
            ImageView imageView2 = holder.itemView.findViewById(R.id.addplatsdujour);

            textView.setText(getItem(position).getArticle().getDesignation());
            textView2.setText("Quantité: "+getItem(position).getQuantite().toString());
            Constantes.loadImage(getApplicationContext(), getItem(position).getArticle().getImageUrl(), imageView);
            //viewHolder.imageView2.setImageResource(R.drawable.ic_action_add);
            imageView2.setVisibility(View.GONE);
        }

        @Override
        public long getHeaderId(int position) {
            if (position == 0) {
                return -1;
            } else {
                return getItem(position).getIdCommandeArticle();
            }
        }

        @Override
        public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.header_serveurcommandes, parent, false);
            return new RecyclerView.ViewHolder(view) {
            };
        }

        @Override
        public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {
            //TextView textView = (TextView) holder.itemView;
            //textView.setText(String.valueOf(getItem(position).charAt(0)));
            //holder.itemView.setBackgroundColor(getRandomColor());

            TextView textView =  holder.itemView.findViewById(R.id.commande);
            TextView textView2 =  holder.itemView.findViewById(R.id.table);
            TextView textView3 =  holder.itemView.findViewById(R.id.date);
            CircularImageView imageView =  holder.itemView.findViewById(R.id.imagecommande);
            imageView2 =  holder.itemView.findViewById(R.id.imageaddcommande);
            imageView3 =  holder.itemView.findViewById(R.id.imagesendcomptable);
            Constantes.loadImage(contextView,"meal.png", imageView);
            textView.setText("Commande : "+getItem(position).getCommande().getNumero());
            textView2.setText("Table : "+getItem(position).getCommande().getTable().getNumero());
            textView3.setText("Heure : "+getItem(position).getCommande().getDate().substring(11,16));
        }



    }

    private void getServeurCommandesDuJour() {
        mSwipeLayout.setRefreshing(true);
        retrofitService.getServeurCommandesDuJour(LoginActivity.compte.getLogin()).enqueue(new Callback<List<CommandeArticle>>() {
            @Override
            public void onResponse(Call<List<CommandeArticle>> call, Response<List<CommandeArticle>> response) {
                if (response.isSuccessful()){
                    if (!response.body().isEmpty()){
                        adapter.addAll(response.body());
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
