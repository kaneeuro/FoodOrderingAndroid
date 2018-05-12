package com.sadicomputing.foodordering.activity.accueil;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.sadicomputing.foodordering.R;
import com.sadicomputing.foodordering.activity.accueil.LoginActivity;
import com.sadicomputing.foodordering.activity.comptable.ComptableCommandeActivity;
import com.sadicomputing.foodordering.activity.cuisinier.CuisineCommandeActivity;
import com.sadicomputing.foodordering.activity.cuisinier.CuisineCommandesdujourActivity;
import com.sadicomputing.foodordering.activity.serveur.ServeurCommandeActivity;
import com.sadicomputing.foodordering.activity.serveur.ServeurCommandesdujourActivity;
import com.sadicomputing.foodordering.activity.serveur.ServeurMenuActivity;
import com.sadicomputing.foodordering.activity.serveur.ServeurPlatsdujourActivity;
import com.sadicomputing.foodordering.activity.serveur.ServeurViennoiseriesActivity;
import com.sadicomputing.foodordering.entity.Compte;
import com.sadicomputing.foodordering.utils.Constantes;

import static com.sadicomputing.foodordering.activity.accueil.LoginActivity.session;

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
    private EditText editTextNumeroTable;
    public static int numeroTable;
    private int buttonClicked;
    private ImageView imageViewCommande, imageViewMessage;

    private FirebaseAnalytics mFirebaseAnalytics;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
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

        //Try to use AsyncTask for loading User's informations
        //textViewUser.setText(compte.getEmploye().getPrenom().toUpperCase()+" "+compte.getEmploye().getNom().toUpperCase());
        //textViewMatricule.setText(compte.getEmploye().getMatricule().toUpperCase());
        //imageViewProfil.setImageResource(R.drawable.icons8_circled_user_female);

        //Picasso.with(contextView).load("http://10.0.2.2/FoodOrdering/images/uploads/user_female.png").into(imageViewProfil);
        Toast.makeText(this, compte.getEmploye().getPrenom().toUpperCase()+" "+compte.getEmploye().getNom().toUpperCase()
                                +" "+compte.getRole(), Toast.LENGTH_LONG).show();

        if (compte.getRole().equalsIgnoreCase("SERVEUR")){
            imageViewCommande = (ImageView) findViewById(R.id.commandeServeurImgView);
            imageViewMessage = (ImageView) findViewById(R.id.messageServeurImgView);
            Constantes.loadImage(contextView,"restaurant_menu.png",imageViewCommande);
            Constantes.loadImage(contextView,"urgent_message.png",imageViewMessage);
            layoutServeur.setVisibility(View.VISIBLE);
        }
        else if (compte.getRole().equalsIgnoreCase("CUISINIER")){
            imageViewCommande = (ImageView) findViewById(R.id.commandeCuisinierImgView);
            imageViewMessage = (ImageView) findViewById(R.id.messageCuisinierImgView);
            Constantes.loadImage(contextView,"meal.png",imageViewCommande);
            Constantes.loadImage(contextView,"urgent_message.png",imageViewMessage);
            layoutCuisinier.setVisibility(View.VISIBLE);
        }
        else if (compte.getRole().equalsIgnoreCase("COMPTABLE")){
            imageViewCommande = (ImageView) findViewById(R.id.commandeComptableImgView);
            imageViewMessage = (ImageView) findViewById(R.id.messageComptableImgView);
            Constantes.loadImage(contextView,"invoice.png",imageViewCommande);
            Constantes.loadImage(contextView,"urgent_message.png",imageViewMessage);
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
                buttonClicked = R.id.commandeServeur;
                numeroTableDialog();
            }
        });
        cardViewMessageServeur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ServeurCommandesdujourActivity.class));
            }
        });

        cardViewCommandeCuisinier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), CuisineCommandeActivity.class));
            }
        });
        cardViewMessageCuisinier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), CuisineCommandesdujourActivity.class));
            }
        });

        cardViewCommandeComptable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ComptableCommandeActivity.class));
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (!drawer.isDrawerOpen(GravityCompat.START)) {
            alertDialog();
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
            alertDialog();
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
            numeroTableDialog();
        } else if (id == R.id.nav_facture) {

        } else if (id == R.id.nav_message) {

        } else if (id == R.id.nav_platsdujour) {
            buttonClicked = R.id.nav_platsdujour;
            numeroTableDialog();
        } else if (id == R.id.nav_viennoiseries) {
            buttonClicked = R.id.nav_viennoiseries;
            numeroTableDialog();
        } else if (id == R.id.nav_menu) {
            buttonClicked = R.id.nav_menu;
            numeroTableDialog();
        } else if (id == R.id.nav_settings) {

        } else if (id == R.id.nav_logout) {
            alertDialog();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void alertDialog(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setIcon(R.drawable.ic_action_alert);
        alertDialogBuilder.setTitle("Confirmation");
        alertDialogBuilder.setMessage("Voulez-vous vraiment quitter cette application ?");
        alertDialogBuilder.setPositiveButton("OUI",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                session = 0;
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

    public void numeroTableDialog(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        // Get the layout inflater
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialog_numerotable,null);
        alertDialogBuilder.setIcon(R.drawable.ic_action_menu);
        alertDialogBuilder.setTitle(R.string.numerotable);
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        alertDialogBuilder.setView(dialogView);
        editTextNumeroTable = dialogView.findViewById(R.id.numerotable);
        editTextNumeroTable.setError( "Champ obligatoire!" );
        // Add action buttons
        alertDialogBuilder.setPositiveButton("CONTINUER", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                if (!editTextNumeroTable.getText().toString().trim().equals("")){
                    numeroTable = Integer.parseInt(editTextNumeroTable.getText().toString().trim());
                    if (buttonClicked == R.id.commandeServeur)
                        startActivity(new Intent(getApplicationContext(), ServeurCommandeActivity.class));
                    else if (buttonClicked == R.id.nav_platsdujour)
                        startActivity(new Intent(getApplicationContext(), ServeurPlatsdujourActivity.class));
                    else if (buttonClicked == R.id.nav_viennoiseries)
                        startActivity(new Intent(getApplicationContext(), ServeurViennoiseriesActivity.class));
                    else if (buttonClicked == R.id.nav_menu)
                        startActivity(new Intent(getApplicationContext(), ServeurMenuActivity.class));
                }
            }
        });
        alertDialogBuilder.setNegativeButton("ANNULER", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

}
