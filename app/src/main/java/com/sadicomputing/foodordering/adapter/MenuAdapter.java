package com.sadicomputing.foodordering.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jaychang.srv.SimpleCell;
import com.jaychang.srv.SimpleViewHolder;
import com.sadicomputing.foodordering.R;
import com.sadicomputing.foodordering.activity.LoginActivity;
import com.sadicomputing.foodordering.activity.MainActivity;
import com.sadicomputing.foodordering.activity.serveur.ServeurDetailsArticleActivity;
import com.sadicomputing.foodordering.activity.serveur.ServeurMenuActivity;
import com.sadicomputing.foodordering.entity.Article;
import com.sadicomputing.foodordering.entity.CommandeArticleTemporaire;
import com.sadicomputing.foodordering.service.RetrofitService;
import com.sadicomputing.foodordering.service.RetrofitUtlis;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.sadicomputing.foodordering.activity.MainActivity.mNotificationsCountCommande;

/**
 * Created by modykane on 11/12/2017.
 */

public class MenuAdapter extends SimpleCell<Article, MenuAdapter.ViewHolder> {

    private int mNotificationsCount=0;
    public static Article article;
    private RetrofitService retrofitService;
    private Context mContext;
    private List<Article> mItems;
    private CommandeArticleTemporaire temporaire;

    public MenuAdapter(@NonNull Article item) {
        super(item);
    }

    public MenuAdapter(@NonNull Article item, Context mContext, List<Article> mItems) {
        super(item);
        this.mContext = mContext;
        this.mItems = mItems;
        retrofitService = RetrofitUtlis.getRetrofitService();
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
    protected ViewHolder onCreateViewHolder(ViewGroup parent, View cellView) {
        return new ViewHolder(cellView);
    }

    /*
    - Bind data to widgets in our viewholder.
     */
    @Override
    protected void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i, @NonNull Context context, Object o) {
        viewHolder.textView.setText(getItem().getDesignation());
        viewHolder.textView2.setText(""+getItem().getPrix()+" FCFA");
        viewHolder.imageView.setImageResource(R.drawable.icons8_details_pane);
        viewHolder.imageView2.setImageResource(R.drawable.ic_action_add);
        viewHolder.imageView3.setImageResource(R.drawable.ic_action_details);

        viewHolder.imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                article = getItem(viewHolder.getAdapterPosition());
                //Toast.makeText(mContext,article.getDesignation()+" est ajouté à la commande", Toast.LENGTH_SHORT).show();
                addArticleTemporaire(new CommandeArticleTemporaire(LoginActivity.compte.getEmploye(),article));
                updateNotificationsBadge();
            }
        });

        viewHolder.imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                article = getItem(viewHolder.getAdapterPosition());
                Intent intent = new Intent(mContext, ServeurDetailsArticleActivity.class);
                mContext.startActivity(intent);

            }
        });
    }
    /**
     - Our ViewHolder class.
     - Inner static class.
     * Define your view holder, which must extend SimpleViewHolder.
     * */
    public static class ViewHolder extends SimpleViewHolder {
        TextView textView, textView2;
        ImageView imageView, imageView2, imageView3;

        ViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.designationplatsdujour);
            textView2 = itemView.findViewById(R.id.prixplatsdujour);
            imageView = itemView.findViewById(R.id.imageplatsdujour);
            imageView2 = itemView.findViewById(R.id.addplatsdujour);
            imageView3 = itemView.findViewById(R.id.detailsplatsdujour);

        }
    }

    private Article getItem(int adapterPosition) {
        return mItems.get(adapterPosition);
    }

    public void addArticleTemporaire(CommandeArticleTemporaire articleTemporaire){
        retrofitService.saveArticleTemporaire(articleTemporaire).enqueue(new Callback<CommandeArticleTemporaire>() {
            @Override
            public void onResponse(Call<CommandeArticleTemporaire> call, Response<CommandeArticleTemporaire> response) {
                if (response.isSuccessful())
                    Toast.makeText(mContext, response.body().getArticle().getDesignation()+" est ajouté(e) à la commande", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<CommandeArticleTemporaire> call, Throwable t) {
                Log.e("ERROR",t.getMessage());
                Toast.makeText(mContext,"Impossible d'ajouter l'article à la commande", Toast.LENGTH_SHORT).show();
            }
        });
    }
    /*
      Updates the count of notifications in the ActionBar.
     */
    public void updateNotificationsBadge() {
        mNotificationsCount = mNotificationsCountCommande + 1;
        mNotificationsCountCommande=mNotificationsCount;
        // force the ActionBar to relayout its MenuItems.
        // onCreateOptionsMenu(Menu) will be called again.
        //invalidateOptionsMenu();
    }
}
