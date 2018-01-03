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
import com.sadicomputing.foodordering.utils.Constantes;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by modykane on 29/12/2017.
 */

public class ComptableCommandeAdapter extends RecyclerView.Adapter<ComptableCommandeAdapter.ViewHolder> {

    private List<Commande> mItems;
    private Context mContext;
    public static Commande commande;

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView textView, textView2;
        private ImageView imageView, imageView2, imageView3;

        private ViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.designationplatsdujour);
            textView2 = itemView.findViewById(R.id.prixplatsdujour);
            imageView = itemView.findViewById(R.id.imageplatsdujour);
            imageView2 = itemView.findViewById(R.id.addplatsdujour);
            imageView3 = itemView.findViewById(R.id.detailsplatsdujour);
        }
    }

    public ComptableCommandeAdapter(Context context, List<Commande> articles) {
        mItems = articles;
        mContext = context;
    }

    @Override
    public ComptableCommandeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View postView = inflater.inflate(R.layout.row_platsdujour, parent, false);
        return new ComptableCommandeAdapter.ViewHolder(postView);
    }

    @Override
    public void onBindViewHolder(final ComptableCommandeAdapter.ViewHolder holder, final int position) {
        Commande item = mItems.get(position);
        holder.textView.setText(item.getNumero()+"");
        holder.textView2.setText(Constantes.formatDate(item.getDate()));
        Constantes.loadImage(mContext,"meal.png", holder.imageView);
        holder.imageView2.setVisibility(View.GONE);
        holder.imageView3.setImageResource(R.drawable.ic_action_details);
        holder.imageView3.setVisibility(View.VISIBLE);

        holder.imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                commande = getItem(holder.getAdapterPosition());
                mContext.startActivity(new Intent(mContext, ComptableFactureCommandeActivity.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void updateAnswers(List<Commande> items) {
        mItems = items;
        notifyDataSetChanged();
    }

    private Commande getItem(int adapterPosition) {
        return mItems.get(adapterPosition);
    }

    public void setFilter(List<Commande> items) {
        mItems = new ArrayList<>();
        mItems.addAll(items);
        notifyDataSetChanged();
    }
}
