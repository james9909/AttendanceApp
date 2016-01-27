package me.james9909.attendanceapp.activity;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import me.james9909.attendanceapp.R;
import me.james9909.attendanceapp.fragment.LoginFragment;
import me.james9909.attendanceapp.fragment.TakeAttendanceFragment;
import me.james9909.attendanceapp.utils.Config;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle drawerToggle;
    private SharedPreferences sharedPreferences;
    private Toolbar toolbar;
    private Config config;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        config = Config.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                if (!isLoggedIn()) {
                    Toast.makeText(getApplicationContext(), "Please log in to perform actions", Toast.LENGTH_SHORT).show();
                    return false;
                }
                selectDrawerItem(item);
                return true;
            }
        });

        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.drawer_open, R.string.drawer_close);

        drawerLayout.setDrawerListener(drawerToggle);

        showFragment(LoginFragment.class);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
        }

        // Handle opening of navigation drawer
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        drawerToggle.onConfigurationChanged(newConfig);
    }

    public boolean isLoggedIn() {
        return !config.getAdminEmail().equals("");
    }

    public void showFragment(Class fragmentClass) {
        Fragment fragment;
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();

        fragment.setArguments(getCurrentFragmentArguments());
        fragmentManager.beginTransaction().replace(R.id.fragments, fragment, "FRAGMENT").commit();
    }

    public void selectDrawerItem(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.take_attendance:
                showFragment(TakeAttendanceFragment.class);
                break;
            case R.id.view_attendance:
                break;
        }

        item.setChecked(true);
        setTitle(item.getTitle());
        drawerLayout.closeDrawers();
    }

    private Bundle getCurrentFragmentArguments(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        try {
            Fragment currentFragment = getSupportFragmentManager().findFragmentByTag("FRAGMENT");
            return currentFragment.getArguments();
        } catch (Exception e) {
            e.printStackTrace();
            return new Bundle();
        }
    }

}
