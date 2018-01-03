package com.sadicomputing.foodordering.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sadicomputing.foodordering.R;
import com.sadicomputing.foodordering.activity.comptable.ComptableFactureCommandeActivity;
import com.sadicomputing.foodordering.activity.serveur.ServeurPlatsdujourActivity;
import com.sadicomputing.foodordering.entity.Commande;
import com.sadicomputing.foodordering.entity.CommandeArticle;
import com.sadicomputing.foodordering.utils.Constantes;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by modykane on 29/12/2017.
 */

public class ComptableFactureCommandeAdapter extends RecyclerView.Adapter<ComptableFactureCommandeAdapter.ViewHolder> {

    private List<CommandeArticle> mItems;
    private Context mContext;

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView textView, textView2, textView3, textView4;

        private ViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.article);
            textView2 = itemView.findViewById(R.id.quantite);
            textView3 = itemView.findViewById(R.id.prixunitaire);
            textView4 = itemView.findViewById(R.id.prixtotal);
        }
    }

    public ComptableFactureCommandeAdapter(Context context, List<CommandeArticle> articles) {
        mItems = articles;
        mContext = context;
    }

    @Override
    public ComptableFactureCommandeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View postView = inflater.inflate(R.layout.row_facture, parent, false);
        return new ComptableFactureCommandeAdapter.ViewHolder(postView);
    }

    @Override
    public void onBindViewHolder(final ComptableFactureCommandeAdapter.ViewHolder holder, final int position) {
        CommandeArticle item = mItems.get(position);
        holder.textView.setText(item.getArticle().getDesignation());
        holder.textView2.setText(item.getQuantite().toString());
        holder.textView3.setText(item.getPrixUnitaire().toString());
        holder.textView4.setText(item.getPrixTotal().toString());

    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void updateAnswers(List<CommandeArticle> items) {
        mItems = items;
        notifyDataSetChanged();
    }

    private CommandeArticle getItem(int adapterPosition) {
        return mItems.get(adapterPosition);
    }

    public void setFilter(List<CommandeArticle> items) {
        mItems = new ArrayList<>();
        mItems.addAll(items);
        notifyDataSetChanged();
    }
}
