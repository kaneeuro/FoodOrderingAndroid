package com.sadicomputing.foodordering.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sadicomputing.foodordering.R;
import com.sadicomputing.foodordering.activity.accueil.LoginActivity;
import com.sadicomputing.foodordering.activity.accueil.MainActivity;
import com.sadicomputing.foodordering.entity.CommandeArticleTemporaire;
import com.sadicomputing.foodordering.service.RetrofitService;
import com.sadicomputing.foodordering.service.RetrofitUtlis;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.sadicomputing.foodordering.activity.serveur.ServeurResumeCommandeActivity.prixTotal;
import static com.sadicomputing.foodordering.activity.serveur.ServeurResumeCommandeActivity.textView;

/**
 * Created by modykane on 12/12/2017.
 */

public class ArticleCommandeAdapter extends RecyclerView.Adapter<ArticleCommandeAdapter.ViewHolder> {

    private RetrofitService retrofitService;
    private List<CommandeArticleTemporaire> mItems=new ArrayList<>();
    private Context mContext;
    private CommandeArticleTemporaire article;
    private int quantite=1;
    public static SparseIntArray quantiteArticle = new SparseIntArray();
    private SwipeRefreshLayout mSwipeLayout;

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView textView, textView2;
        private ImageView imageView, imageView2, imageView3;
        private TextView editText;

        private ViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.articlecommande);
            textView2 = itemView.findViewById(R.id.prixarticlecommande);
            imageView = itemView.findViewById(R.id.moins);
            imageView2 = itemView.findViewById(R.id.plus);
            imageView3 = itemView.findViewById(R.id.deletearticlecommande);
            editText = itemView.findViewById(R.id.quantite);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            //CommandeArticleTemporaire item = getItem(getAdapterPosition());
            //idArticleSelected=item.getArticle().getIdArticle();
            //notifyDataSetChanged();
            //Toast.makeText(mContext,item.getDesignation()+" est ajouté à la commande", Toast.LENGTH_SHORT).show();
        }
    }

    public ArticleCommandeAdapter(Context context, List<CommandeArticleTemporaire> articles, SwipeRefreshLayout mSwipeLayout) {
        mItems = articles;
        mContext = context;
        retrofitService = RetrofitUtlis.getRetrofitService();
        this.mSwipeLayout = mSwipeLayout;
    }

    @Override
    public ArticleCommandeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View postView = inflater.inflate(R.layout.row_resume_commande, parent, false);
        return new ArticleCommandeAdapter.ViewHolder(postView);
    }

    @Override
    public void onBindViewHolder(final ArticleCommandeAdapter.ViewHolder holder, final int position) {
        CommandeArticleTemporaire item = mItems.get(position);
        holder.textView.setText(item.getArticle().getDesignation());
        holder.textView2.setText(""+item.getArticle().getPrix()*quantite+" FCFA");
        holder.imageView.setImageResource(R.drawable.ic_action_moins);
        holder.imageView2.setImageResource(R.drawable.ic_action_plus);
        holder.imageView3.setImageResource(R.drawable.ic_action_delete);
        holder.editText.setText(((int) R.string.quantite));

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                article = getItem(holder.getAdapterPosition());
                if (Integer.valueOf(holder.editText.getText().toString())>1){
                    quantite=Integer.valueOf(holder.editText.getText().toString())-1;
                    holder.editText.setText(""+quantite);
                    holder.textView2.setText(""+article.getArticle().getPrix()*quantite+" FCFA");
                    prixTotal-=article.getArticle().getPrix();
                    textView.setText(prixTotal+" FCFA");
                    for (int i = 0; i < getItemCount(); i++) {
                        quantiteArticle.put(article.getIdCommandeArticleTemporaire().intValue(),quantite);
                    }
                }
            }
        });

        holder.imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                article = getItem(holder.getAdapterPosition());
                quantite=Integer.valueOf(holder.editText.getText().toString())+1;
                holder.editText.setText(""+quantite);
                holder.textView2.setText(""+article.getArticle().getPrix()*quantite+" FCFA");
                prixTotal+=article.getArticle().getPrix();
                textView.setText(prixTotal+" FCFA");
                for (int i = 0; i < getItemCount(); i++) {
                    quantiteArticle.put(article.getIdCommandeArticleTemporaire().intValue(),quantite);
                }
            }
        });

        holder.imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                article = getItem(holder.getAdapterPosition());
                serverDialog();
            }
        });

    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void updateAnswers(List<CommandeArticleTemporaire> items) {
        mItems = items;
        notifyDataSetChanged();
    }

    private CommandeArticleTemporaire getItem(int adapterPosition) {
        return mItems.get(adapterPosition);
    }

    public void setFilter(List<CommandeArticleTemporaire> items) {
        mItems = new ArrayList<>();
        mItems.addAll(items);
        notifyDataSetChanged();
    }

    private void deleteArticleTemporaire(Long id){
        retrofitService.deleteArticleTemporaire(id).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()){
                    getArticlesTemporairesByEmploye();
                    MainActivity.mNotificationsCountCommande-=1;
                    Toast.makeText(mContext, "L'article a été supprimé de la commande", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e("ERROR",t.getMessage());
                Toast.makeText(mContext,"Impossible de supprimer l'article de la commande", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getArticlesTemporairesByEmploye() {
        mSwipeLayout.setRefreshing(true);
        retrofitService.getArticlesTemporairesByEmploye(LoginActivity.compte.getEmploye().getIdEmploye()).enqueue(new Callback<List<CommandeArticleTemporaire>>() {
            @Override
            public void onResponse(Call<List<CommandeArticleTemporaire>> call, Response<List<CommandeArticleTemporaire>> response) {
                if (response.isSuccessful()){
                    prixTotal=0;
                    for (CommandeArticleTemporaire commandeArticleTemporaire:response.body()) {
                        prixTotal += commandeArticleTemporaire.getArticle().getPrix();
                    }
                    textView.setText(prixTotal+" FCFA");
                    updateAnswers(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<CommandeArticleTemporaire>> call, Throwable t) {
                //serverDialog(contextView);
            }
        });
        mSwipeLayout.setRefreshing(false);
    }

    private void serverDialog(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
        alertDialogBuilder.setIcon(R.drawable.ic_action_alert);
        alertDialogBuilder.setTitle("Confirmation");
        alertDialogBuilder.setMessage("Voulez-vous vraiment supprimer cet article de la commande?");
        alertDialogBuilder.setPositiveButton("OUI",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteArticleTemporaire(article.getIdCommandeArticleTemporaire());
            }
        });
        alertDialogBuilder.setNegativeButton("NON",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
