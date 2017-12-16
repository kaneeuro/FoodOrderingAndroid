package com.sadicomputing.foodordering.activity;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.LayerDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sadicomputing.foodordering.R;
import com.sadicomputing.foodordering.activity.serveur.ServeurCommandeActivity;
import com.sadicomputing.foodordering.activity.serveur.ServeurMenuActivity;
import com.sadicomputing.foodordering.activity.serveur.ServeurPlatsdujourActivity;
import com.sadicomputing.foodordering.activity.serveur.ServeurViennoiseriesActivity;
import com.sadicomputing.foodordering.entity.Compte;
import com.sadicomputing.foodordering.utils.Utils;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Compte compte;
    Context contextView;
    TextView textViewUser, textViewMatricule;
    ImageView imageViewProfil;
    LinearLayout layoutServeur, layoutCuisinier, layoutComptable;
    CardView cardViewCommandeServeur, cardViewMessageServeur,
             cardViewCommandeCuisinier, cardViewMessageCuisinier,
             cardViewCommandeComptable, cardViewMessageComptable;
    MenuItem menuItem;
    public static int mNotificationsCountCommande;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        contextView = this;
        compte = LoginActivity.compte;

        layoutServeur = (LinearLayout) findViewById(R.id.layoutServeur);
        layoutCuisinier = (LinearLayout) findViewById(R.id.layoutCuisinier);
        layoutComptable = (LinearLayout) findViewById(R.id.layoutComptable);

        cardViewCommandeServeur = (CardView) findViewById(R.id.commandeServeur);
        cardViewMessageServeur = (CardView) findViewById(R.id.messageServeur);
        cardViewCommandeCuisinier = (CardView) findViewById(R.id.commandeCuisinier);
        cardViewMessageCuisinier = (CardView) findViewById(R.id.messageCuisinier);
        cardViewCommandeComptable = (CardView) findViewById(R.id.commandeComptable);
        cardViewMessageComptable = (CardView) findViewById(R.id.messageComptable);

        textViewUser = (TextView) findViewById(R.id.textViewUser);
        textViewMatricule = (TextView) findViewById(R.id.textViewMatricule);
        imageViewProfil = (ImageView) findViewById(R.id.imageViewProfil);

        //textViewUser.setText(compte.getEmploye().getPrenom().toUpperCase()+" "+compte.getEmploye().getNom().toUpperCase());
        //textViewUser.setText(compte.getEmploye().getMatricule().toUpperCase());
        //imageViewProfil.setImageResource(R.drawable.icons8_circled_user_female);
        Toast.makeText(this, compte.getEmploye().getPrenom().toUpperCase()+" "+compte.getEmploye().getNom().toUpperCase()
                                +" "+compte.getRole(), Toast.LENGTH_LONG).show();

        if (compte.getRole().equalsIgnoreCase("SERVEUR")){
            layoutServeur.setVisibility(View.VISIBLE);
        }
        else if (compte.getRole().equalsIgnoreCase("CUISINIER")){
            layoutCuisinier.setVisibility(View.VISIBLE);
        }
        else if (compte.getRole().equalsIgnoreCase("COMPTABLE")){
            layoutComptable.setVisibility(View.VISIBLE);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        cardViewCommandeServeur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ServeurCommandeActivity.class));
            }
        });

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_notification) {
            return true;
        }
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.action_logout) {
            alertDialog(contextView);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_commande) {
            // Handle the camera action
            startActivity(new Intent(getApplicationContext(), ServeurCommandeActivity.class));
        } else if (id == R.id.nav_facture) {

        } else if (id == R.id.nav_message) {

        } else if (id == R.id.nav_platsdujour) {
            startActivity(new Intent(getApplicationContext(), ServeurPlatsdujourActivity.class));
        } else if (id == R.id.nav_viennoiseries) {
            startActivity(new Intent(getApplicationContext(), ServeurViennoiseriesActivity.class));
        } else if (id == R.id.nav_menu) {
            startActivity(new Intent(getApplicationContext(), ServeurMenuActivity.class));
        } else if (id == R.id.nav_settings) {

        } else if (id == R.id.nav_logout) {
            alertDialog(contextView);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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
