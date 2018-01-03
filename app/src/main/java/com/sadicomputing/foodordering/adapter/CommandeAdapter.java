package com.sadicomputing.foodordering.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jaychang.srv.SimpleCell;
import com.jaychang.srv.SimpleRecyclerView;
import com.jaychang.srv.SimpleViewHolder;
import com.sadicomputing.foodordering.R;
import com.sadicomputing.foodordering.activity.cuisinier.CuisineCommandeActivity;
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

/**
 * Created by modykane on 21/12/2017.
 */

public class CommandeAdapter extends SimpleCell<CommandeArticle, CommandeAdapter.ViewHolder> {
    public static CommandeArticle article;
    private RetrofitService retrofitService;
    private Context mContext;
    private List<CommandeArticle> mItems;
    private SimpleRecyclerView simpleRecyclerView;
    private SwipeRefreshLayout mSwipeLayout;

    public CommandeAdapter(@NonNull CommandeArticle item) {
        super(item);
    }

    public CommandeAdapter(@NonNull CommandeArticle item, Context mContext, List<CommandeArticle> mItems, SimpleRecyclerView simpleRecyclerView, SwipeRefreshLayout mSwipeLayout) {
        super(item);
        this.mContext = mContext;
        this.mItems = mItems;
        retrofitService = RetrofitUtlis.getRetrofitService();
        this.simpleRecyclerView = simpleRecyclerView;
        this.mSwipeLayout = mSwipeLayout;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.row_platsdujour;
    }

    /*
    - Return a ViewHolder instance
     */
    @NonNull
    @Override
    protected CommandeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, View cellView) {
        return new CommandeAdapter.ViewHolder(cellView);
    }

    /*
    - Bind data to widgets in our viewholder.
     */
    @Override
    protected void onBindViewHolder(@NonNull final CommandeAdapter.ViewHolder viewHolder, int i, @NonNull Context context, Object o) {
        viewHolder.textView.setText(getItem().getArticle().getDesignation());
        viewHolder.textView2.setText("Quantité: "+getItem().getQuantite().toString());
        Constantes.loadImage(mContext,getItem().getArticle().getImageUrl(),viewHolder.imageView);
        viewHolder.imageView2.setImageResource(R.drawable.ic_action_add);

        viewHolder.imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                article = getItem(viewHolder.getAdapterPosition());
                article.getCommande().setStatut(2);
                updateCommandeArticle(article.getIdCommandeArticle(), article);
            }
        });

    }

    public static class ViewHolder extends SimpleViewHolder {
        TextView textView, textView2;
        ImageView imageView, imageView2;

        ViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.designationplatsdujour);
            textView2 = itemView.findViewById(R.id.prixplatsdujour);
            imageView = itemView.findViewById(R.id.imageplatsdujour);
            imageView2 = itemView.findViewById(R.id.addplatsdujour);
        }
    }

    private CommandeArticle getItem(int adapterPosition) {
        return mItems.get(adapterPosition);
    }

    private void updateCommandeArticle(Long id, CommandeArticle commandeArticle){
        retrofitService.updateCommandeArticle(id, commandeArticle).enqueue(new Callback<CommandeArticle>() {
            @Override
            public void onResponse(Call<CommandeArticle> call, Response<CommandeArticle> response) {
                if (response.isSuccessful()){
                    Toast.makeText(mContext, response.body().getArticle().getDesignation()+" est envoyé(e) au serveur", Toast.LENGTH_LONG).show();
                    getAllCommandeArticlesByStatutCommande();
                }
            }

            @Override
            public void onFailure(Call<CommandeArticle> call, Throwable t) {
                Log.e("ERROR",t.getMessage());
                Toast.makeText(mContext,"Erreur!", Toast.LENGTH_SHORT).show();
            }
        });
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
                            CommandeAdapter cell = new CommandeAdapter(article, mContext, response.body(), simpleRecyclerView, mSwipeLayout);

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
}