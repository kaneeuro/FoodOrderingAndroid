package com.sadicomputing.foodordering.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.jaychang.srv.SimpleCell;
import com.jaychang.srv.SimpleRecyclerView;
import com.jaychang.srv.SimpleViewHolder;
import com.sadicomputing.foodordering.R;
import com.sadicomputing.foodordering.activity.accueil.LoginActivity;
import com.sadicomputing.foodordering.entity.CommandeArticle;
import com.sadicomputing.foodordering.firebase.MyVolley;
import com.sadicomputing.foodordering.service.RetrofitService;
import com.sadicomputing.foodordering.service.RetrofitUtlis;
import com.sadicomputing.foodordering.utils.Constantes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by modykane on 21/12/2017.
 */

public class CuisineCommandesdujourAdapter extends SimpleCell<CommandeArticle, CuisineCommandesdujourAdapter.ViewHolder> {
    public static CommandeArticle article;
    private RetrofitService retrofitService;
    private Context mContext;
    private List<CommandeArticle> mItems;
    private SimpleRecyclerView simpleRecyclerView;
    private SwipeRefreshLayout mSwipeLayout;
    private ProgressDialog progressDialog;

    public CuisineCommandesdujourAdapter(@NonNull CommandeArticle item) {
        super(item);
    }

    public CuisineCommandesdujourAdapter(@NonNull CommandeArticle item, Context mContext, List<CommandeArticle> mItems, SimpleRecyclerView simpleRecyclerView, SwipeRefreshLayout mSwipeLayout) {
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
    protected CuisineCommandesdujourAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, View cellView) {
        return new CuisineCommandesdujourAdapter.ViewHolder(cellView);
    }

    /*
    - Bind data to widgets in our viewholder.
     */
    @Override
    protected void onBindViewHolder(@NonNull final CuisineCommandesdujourAdapter.ViewHolder viewHolder, int i, @NonNull Context context, Object o) {
        viewHolder.textView.setText(getItem().getArticle().getDesignation());
        viewHolder.textView2.setText("Quantité: "+getItem().getQuantite().toString());
        Constantes.loadImage(mContext,getItem().getArticle().getImageUrl(),viewHolder.imageView);
        //viewHolder.imageView2.setImageResource(R.drawable.ic_action_add);
        viewHolder.imageView2.setVisibility(View.GONE);

        /*viewHolder.imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                article = getItem(viewHolder.getAdapterPosition());
                //article.getCommande().setStatut(2);
                article.setStatut(1);
                article.setCuisinier(LoginActivity.compte.getEmploye());
                //article.getCommande().setCuisinier(LoginActivity.compte.getEmploye());
                sendSinglePush();
                updateCommandeArticle(article.getIdCommandeArticle(), article);
            }
        });*/

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

}