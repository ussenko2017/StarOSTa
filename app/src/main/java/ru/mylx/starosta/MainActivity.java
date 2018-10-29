package ru.mylx.starosta;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.app.FragmentTransaction;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    FragmentTransaction ftrans;
    addStudFragment addStudFragment;
    addPredmetFragment addPredmetFragment;
    otdelFragment otdelFragment;
    otdel_listFragment otdel_listFragment;
    predmet_listFragment predmet_listFragment;
    stud_listFragment stud_listFragment;
    addOzhFragment addOzhFragment;
    settingsFragment settingsFragment;
    View view;
    journalGridViewFragment1 journalToStudentFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

/*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        addStudFragment = new addStudFragment();
        addPredmetFragment = new addPredmetFragment();
        otdelFragment = new otdelFragment();
        otdel_listFragment = new otdel_listFragment();
        predmet_listFragment = new predmet_listFragment();
        stud_listFragment = new stud_listFragment();
        addOzhFragment = new addOzhFragment();
        settingsFragment = new settingsFragment();
        journalToStudentFragment = new journalGridViewFragment1();
        reload_menu();
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
        if (id == R.id.action_settings) {
            ftrans = getFragmentManager().beginTransaction();
            ftrans.replace(R.id.container, settingsFragment);
            ftrans.commit();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        ftrans = getFragmentManager().beginTransaction();

        if (id == R.id.setStud) {
            ftrans.replace(R.id.container, addStudFragment);
        } else if (id == R.id.setPredmet) {
            ftrans.replace(R.id.container, addPredmetFragment);
        } else if (id == R.id.setOtdel) {
            ftrans.replace(R.id.container, otdelFragment);
        } else if (id == R.id.setOzh) {
            ftrans.replace(R.id.container, addOzhFragment);
        } else if (id == R.id.getStud) {
            ftrans.replace(R.id.container, stud_listFragment);
        } else if (id == R.id.getPredmet) {
            ftrans.replace(R.id.container, predmet_listFragment);
        } else if (id == R.id.getOtdel) {
            ftrans.replace(R.id.container, otdel_listFragment);
        }else if (id == R.id.journal){
            ftrans.replace(R.id.container, journalToStudentFragment);
        }else if (id == R.id.nav_share){
            DBHelper.createExcelTables(getApplicationContext());
            Toast.makeText(getApplicationContext(),"Отчет сохранен",Toast.LENGTH_LONG).show();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        ftrans.commit();
        return true;
    }
    public void reload_menu() {
        ftrans = getFragmentManager().beginTransaction();
        ftrans.replace(R.id.container, addStudFragment);
        ftrans.commit();
    }

}
