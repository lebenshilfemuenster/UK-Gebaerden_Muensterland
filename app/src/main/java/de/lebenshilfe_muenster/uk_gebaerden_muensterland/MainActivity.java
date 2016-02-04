package de.lebenshilfe_muenster.uk_gebaerden_muensterland;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import de.lebenshilfe_muenster.uk_gebaerden_muensterland.about_signs.AboutSignsFragment;
import de.lebenshilfe_muenster.uk_gebaerden_muensterland.settings.SettingsFragment;
import de.lebenshilfe_muenster.uk_gebaerden_muensterland.sign_browser.SignBrowserFragment;
import de.lebenshilfe_muenster.uk_gebaerden_muensterland.sign_trainer.SignTrainerFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        showFragmentAndSetToolbarTitle(new SignBrowserFragment(), R.string.sign_browser);
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
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_sign_browser) {
            showFragmentAndSetToolbarTitle(new SignBrowserFragment(), R.string.sign_browser);
        } else if (id == R.id.nav_sign_trainer) {
            showFragmentAndSetToolbarTitle(new SignTrainerFragment(), R.string.sign_trainer);
        } else if (id == R.id.nav_sign_info) {
            showFragmentAndSetToolbarTitle(new AboutSignsFragment(), R.string.about_signs);
        } else if (id == R.id.nav_sign_settings) {
            showFragmentAndSetToolbarTitle(new SettingsFragment(), R.string.settings);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void showFragmentAndSetToolbarTitle(Fragment fragment, int actionBarTitleStringId) {
        final FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
        final ActionBar supportActionBar = getSupportActionBar();
        if (null == supportActionBar) {
            throw new IllegalStateException("SupportActionBar is null. Should be set in onCreate() method.");
        } else {
            supportActionBar.setTitle(actionBarTitleStringId);
        }
    }

}
