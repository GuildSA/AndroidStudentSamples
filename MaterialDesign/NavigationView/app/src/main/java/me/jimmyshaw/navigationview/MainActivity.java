package me.jimmyshaw.navigationview;

import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout mDrawerLayout;
    private TextView mTextViewMenuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mTextViewMenuItem = (TextView) findViewById(R.id.textview_menu_item);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            // The if statement is only here to mitigate potential null pointer exceptions.
            navigationView.setNavigationItemSelectedListener(this);
        }

        // ActionBarDrawerToggle(Activity activity, DrawerLayout drawerLayout, Toolbar toolbar,
        // @StringRes int openDrawerContentDescRes,
        // @StringRes int closeDrawerContentDescRes)
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar,
                R.string.drawer_open, R.string.drawer_close);

        mDrawerLayout.setDrawerListener(drawerToggle);
        // If the syncState method is not called then the drawer toggle icon AKA "Hamburger icon"
        // either won't synchronize with the drawer layout or the icon won't appear at all.
        drawerToggle.syncState();
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        // Extract the menu item name and set it as the text to our text view then hide the drawer.
        String itemName = (String) menuItem.getTitle();
        mTextViewMenuItem.setText(itemName);
        hideDrawer();

        switch (menuItem.getItemId()) {
            case R.id.item_android:
                // Item specific code.
                break;
            case R.id.item_ios:
                // Item specific code.
                break;
            default:
                break;
        }

        return true;
    }

    // Open the drawer.
    private void showDrawer() {
        mDrawerLayout.openDrawer(GravityCompat.START);
    }

    // Close the drawer.
    private void hideDrawer() {
        mDrawerLayout.closeDrawer(GravityCompat.START);
    }

    // When the user pushes the back button to close the app, if the drawer is open then hide the
    // drawer. Otherwise, perform the usual action with the back button press which is closing
    // the app.
    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            hideDrawer();
        }
        else {
            super.onBackPressed();
        }
    }
}
