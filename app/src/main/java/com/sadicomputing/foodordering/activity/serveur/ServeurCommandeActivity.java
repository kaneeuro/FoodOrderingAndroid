package com.sadicomputing.foodordering.activity.serveur;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.sadicomputing.foodordering.R;
import com.sadicomputing.foodordering.activity.LoginActivity;
import com.sadicomputing.foodordering.service.RetrofitService;
import com.sadicomputing.foodordering.service.RetrofitUtlis;
import com.sadicomputing.foodordering.utils.Utils;

import static com.sadicomputing.foodordering.activity.MainActivity.mNotificationsCountCommande;

public class ServeurCommandeActivity extends AppCompatActivity {

    private int mNotificationsCount=0;
    private Context contextView;
    CardView cardViewPlatsdujour, cardViewViennoiseries, cardViewMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serveur_commande);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        contextView = this;
        cardViewPlatsdujour = (CardView) findViewById(R.id.platsdujour);
        cardViewViennoiseries = (CardView) findViewById(R.id.viennoiseries);
        cardViewMenu = (CardView) findViewById(R.id.menu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        cardViewPlatsdujour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ServeurPlatsdujourActivity.class));
            }
        });
        cardViewViennoiseries.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ServeurViennoiseriesActivity.class));
            }
        });
        cardViewMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ServeurMenuActivity.class));
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_panier) {
            ServeurResumeCommandeActivity.prixTotal=0;
            startActivity(new Intent(getApplicationContext(), ServeurResumeCommandeActivity.class));
            return true;
        }
        /*if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.action_logout) {
            alertDialog(contextView);
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    public void alertDialog(Context view){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setIcon(R.drawable.ic_action_alert);
        alertDialogBuilder.setTitle("Confirmation");
        alertDialogBuilder.setMessage("Voulez-vous vraiment quitter cette application ?");
        alertDialogBuilder.setPositiveButton("OUI",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
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
