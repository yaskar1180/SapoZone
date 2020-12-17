package com.example.sapozone;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.sapozone.viewHolders.ShopHolder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.preference.PreferenceManager;
import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class MenuActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;


    private SharedPreferences sharedPref = null;


    private ListView displayedShops;


    private String[] prenoms = new String[]{
            "Antoine", "Benoit", "Cyril", "David", "Eloise", "Florent",
            "Gerard", "Hugo", "Ingrid", "Jonathan", "Kevin", "Logan",
            "Mathieu", "Noemie", "Olivia", "Philippe", "Quentin", "Romain",
            "Sophie", "Tristan", "Ulric", "Vincent", "Willy", "Xavier",
            "Yann", "Zoé"
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        this.sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        setContentView(R.layout.activity_menu);


        this.displayedShops = findViewById(R.id.displayedShops);


        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(MenuActivity.this,
                android.R.layout.simple_list_item_1, prenoms);
        displayedShops.setAdapter(adapter);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.searchFloatingButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Snackbar.make(view, "On va ouvrir un pop up de recherche avancée", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_movie, R.id.nav_renewal,
                R.id.nav_subscription, R.id.nav_consumable, R.id.nav_ticket)
                .setDrawerLayout(drawer)
                .build();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


    public void disconnect(View view){
        Intent intent = new Intent(this, MainActivity.class);

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.clear();
        editor.apply();
        startActivity(intent);
    }


    View cellule = ...; //nous verrons plus tard comment la générer

    ShopHolder viewHolder = (ShopHolder) cellule.getTag();
    //comme nos vues sont réutilisées, notre cellule possède déjà un ViewHolder
    if(viewHolder == null){
        //si elle n'avait pas encore de ViewHolder
        viewHolder = new ShopHolder();

        //récupérer nos sous vues
        viewHolder.name = (TextView)  cellule.findViewById(R.id.name);
        viewHolder.postalCode   = (TextView)  cellule.findViewById(R.id.postalCode);
        viewHolder.logo = (ImageView) cellule.findViewById(R.id.logo);

        //puis on sauvegarde le mini-controlleur dans la vue
        cellule.setTag(viewHolder);
    }


    //convertView est notre vue recyclée
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //Android nous fournit un convertView null lorsqu'il nous demande de la créer
        //dans le cas contraire, cela veux dire qu'il nous fournit une vue recyclée
        if(convertView == null){
            //Nous récupérons notre row_tweet via un LayoutInflater,
            //qui va charger un layout xml dans un objet View
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_tweet,parent, false);
        }

        TweetViewHolder viewHolder = (TweetViewHolder) convertView.getTag();
        if(viewHolder == null){
            viewHolder = new TweetViewHolder();
            viewHolder.pseudo = (TextView) convertView.findViewById(R.id.pseudo);
            viewHolder.text = (TextView) convertView.findViewById(R.id.text);
            viewHolder.avatar = (ImageView) convertView.findViewById(R.id.avatar);
            convertView.setTag(viewHolder);
        }

        //nous renvoyons notre vue à l'adapter, afin qu'il l'affiche
        //et qu'il puisse la mettre à recycler lorsqu'elle sera sortie de l'écran
        return convertView;
    }


}

