package com.example.gogoooma.cooperativeproject;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    Integer check = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar ab = getSupportActionBar();

        if (GlobalVariable.g_nowTeam != null)
            ab.setTitle("\t" + GlobalVariable.g_nowTeam.getTeamName() + " 팀");
        else
            ab.setTitle("관리자 페이지");
        ab.setDisplayUseLogoEnabled(true);
        ab.setDisplayShowHomeEnabled(true);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if (GlobalVariable.g_user.getAdmin()) {
            Menu nav_Menu = navigationView.getMenu();
            nav_Menu.findItem(R.id.adminFirst).setVisible(true);
            nav_Menu.findItem(R.id.adminSecond).setVisible(true);
            nav_Menu.findItem(R.id.home).setVisible(false);
            nav_Menu.findItem(R.id.timeTable).setVisible(false);
            nav_Menu.findItem(R.id.thirdActivity).setVisible(false);
            nav_Menu.findItem(R.id.fourthActivity).setVisible(false);
            nav_Menu.findItem(R.id.pushActivity).setVisible(false);
            FragmentManager manager = getFragmentManager();
            manager.beginTransaction().replace(R.id.content_main, new AdminActivity()).commit();
        } else {
            FragmentManager manager = getFragmentManager();
            manager.beginTransaction().replace(R.id.content_main, new ProjectActivity()).commit();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
           Intent intent = new Intent(getApplicationContext(),TeamActivity.class);
           startActivity(intent);
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
            return true;
        } else if (id == R.id.profile) {
            Intent intent = new Intent(this, ProfileActivity.class);
            intent.putExtra("member", GlobalVariable.g_user);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        FragmentManager manager = getFragmentManager();
        int id = item.getItemId();

        if (id == R.id.home) {
            manager.beginTransaction().replace(R.id.content_main, new ProjectActivity()).commit();
        } else if (id == R.id.timeTable) {
            manager.beginTransaction().replace(R.id.content_main, new TimeIntegrateActivity()).commit();
        } else if (id == R.id.thirdActivity) {
            manager.beginTransaction().replace(R.id.content_main, new ThirdActivity()).commit();
        } else if (id == R.id.fourthActivity) {
            manager.beginTransaction().replace(R.id.content_main, new PlaceActivity()).commit();
        } else if (id == R.id.pushActivity) {
            manager.beginTransaction().replace(R.id.content_main, new PushActivity()).commit();
        } else if (id == R.id.adminFirst) {

        } else if (id == R.id.adminSecond) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
