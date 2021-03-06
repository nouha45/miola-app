package com.example.firsttest;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.firsttest.adapter.AdapterEtud;
import com.example.firsttest.adapter.AdapterProf;
import com.example.firsttest.adminUI.ListEtud;
import com.example.firsttest.adminUI.ListProfUpd;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class DashboardAdmin extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener{

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    FirebaseAuth mAuth;
    private CardView crudProfs, crudEtuds,emploiCard;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_admin);
        mAuth= FirebaseAuth.getInstance();


        /*-------------hooks-------------*/
        crudProfs=(CardView) findViewById(R.id.crudProfs);
        crudEtuds=(CardView)findViewById(R.id.crudEtudiants);
        emploiCard=(CardView)findViewById(R.id.emploiCard);

        crudProfs.setOnClickListener(this);
        crudEtuds.setOnClickListener(this);
        emploiCard.setOnClickListener(this);

        drawerLayout= findViewById(R.id.drawer_layout);
        navigationView= findViewById(R.id.nav_view);
        toolbar= findViewById(R.id.toolbar);
        /*---------- Tool Bar---------------*/
        setSupportActionBar(toolbar);

        /*------------------Navigation Drawer Menu----------------*/

        //hide or show items

        Menu menu =navigationView.getMenu();
        menu.findItem(R.id.nav_login).setVisible(false);


        navigationView.bringToFront();
        ActionBarDrawerToggle toogle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toogle);
        toogle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        navigationView.setCheckedItem(R.id.nav_home);

    }
    @Override
    public void onBackPressed(){

        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else{super.onBackPressed();}
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId()){
          /*  case R.id.nav_home:
                break; //because we already in Home screen*/

            case R.id.nav_profile:
                Intent intent = new Intent(DashboardAdmin.this,ProfileProf.class);
                startActivity(intent);break;

            case R.id.nav_about:
                Intent intent2 = new Intent(DashboardAdmin.this,About.class);
                startActivity(intent2);
                break;
            case R.id.nav_home:
                // startActivity(new Intent(this,Home.class));
                break;
            case R.id.nav_logout:
                mAuth.signOut();
                signout();
        }
        drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }

    private void signout() {
        Intent mainActivity = new Intent(DashboardAdmin.this,Login.class);
        mainActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainActivity);
        finish();
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.crudProfs:
                startActivity(new Intent(this, ListProfUpd.class));break;

            case R.id.crudEtudiants:
                startActivity(new Intent(this, ListEtud.class));break;
            case R.id.emploiCard:
                startActivity(new Intent(this, EmploiActivity.class));break;

        }

    }
}
