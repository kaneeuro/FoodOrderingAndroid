package com.sadicomputing.foodordering.activity.serveur;

import android.content.Intent;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sadicomputing.foodordering.R;
import com.sadicomputing.foodordering.activity.LoginActivity;
import com.sadicomputing.foodordering.adapter.MenuAdapter;
import com.sadicomputing.foodordering.adapter.PlatsdujourAdapter;
import com.sadicomputing.foodordering.entity.Article;
import com.sadicomputing.foodordering.entity.CommandeArticleTemporaire;
import com.sadicomputing.foodordering.entity.Tables;
import com.sadicomputing.foodordering.service.RetrofitService;
import com.sadicomputing.foodordering.service.RetrofitUtlis;
import com.sadicomputing.foodordering.utils.Utils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.sadicomputing.foodordering.activity.MainActivity.mNotificationsCountCommande;

public class ServeurDetailsArticleActivity extends AppCompatActivity {

    private int mNotificationsCount=0;
    private RetrofitService retrofitService;
    private View mView;
    private Article article = MenuAdapter.article;
    private CommandeArticleTemporaire temporaire;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serveur_details_article);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        retrofitService = RetrofitUtlis.getRetrofitService();

        ImageView imageView = findViewById(R.id.imagedetailsarticle);
        TextView textView = findViewById(R.id.designationdetailsarticle);
        TextView textView1 = findViewById(R.id.prixdetailsarticle);
        TextView textView2 = findViewById(R.id.categoriedetailsarticle);

        imageView.setImageResource(R.drawable.icons8_paella);
        textView.setText(article.getDesignation());
        textView1.setText(article.getPrix()+" FCFA");
        textView2.setText(article.getCategorie().getNom());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mView=view;
                addArticleTemporaire();
                updateNotificationsBadge();
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //addArticleTemporaire();
    }

    public void addArticleTemporaire(){
        temporaire = new CommandeArticleTemporaire(LoginActivity.compte.getEmploye(),article);
        retrofitService.saveArticleTemporaire(temporaire).enqueue(new Callback<CommandeArticleTemporaire>() {
            @Override
            public void onResponse(Call<CommandeArticleTemporaire> call, Response<CommandeArticleTemporaire> response) {
                if (response.isSuccessful())
                    Toast.makeText(getApplicationContext(), response.body().getArticle().getDesignation()+" est ajouté(e) à la commande", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<CommandeArticleTemporaire> call, Throwable t) {
                Log.e("ERROR",t.getMessage());
                Toast.makeText(getApplicationContext(),"Impossible d'ajouter l'article à la commande", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_bis, menu);

        // Get the notifications MenuItem and
        // its LayerDrawable (layer-list)
        MenuItem item = menu.findItem(R.id.action_panier);
        LayerDrawable icon = (LayerDrawable) item.getIcon();

        // Update LayerDrawable's BadgeDrawable
        Utils.setBadgeCount(this, icon, mNotificationsCountCommande);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_panier) {
            startActivity(new Intent(getApplicationContext(), ServeurResumeCommandeActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /*
      Updates the count of notifications in the ActionBar.
     */
    public void updateNotificationsBadge() {
        mNotificationsCount = mNotificationsCountCommande + 1;
        mNotificationsCountCommande=mNotificationsCount;
        // force the ActionBar to relayout its MenuItems.
        // onCreateOptionsMenu(Menu) will be called again.
        invalidateOptionsMenu();
    }

}
