package io.android.openchirp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import io.android.openchirp.Fragments.BaseFragment;
import io.android.openchirp.Fragments.CreateDeviceFragment;
import io.android.openchirp.Fragments.MyDevicesFragment;
import io.android.openchirp.Fragments.MyGroupsFragment;
import io.android.openchirp.Fragments.MyLocationsFragment;
import io.android.openchirp.Fragments.MyServicesFragment;
import io.android.openchirp.Fragments.MyShortcutsFragment;
import io.android.openchirp.Fragments.NewFragment;
import io.android.openchirp.Fragments.UserDetailsFragment;
import io.android.openchirp.R;

public class NavigationDrawer extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        SharedPreferences preferences = getApplicationContext().getSharedPreferences("openchirp", Context.MODE_PRIVATE);
        String user_name = preferences.getString("user_name", "dummy name");
        String user_email = preferences.getString("user_email", "dummy email");

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setItemIconTintList(null);
        View nav_header = navigationView.getHeaderView(0);

        TextView text_user_name = (TextView) nav_header.findViewById(R.id.user_name);
        text_user_name.setText(user_name);
        TextView text_user_email = (TextView) nav_header.findViewById(R.id.user_email);
        text_user_email.setText(user_email);

        navigationView.setNavigationItemSelectedListener(this);

//        fragmentTransaction = getSupportFragmentManager().beginTransaction();
//        fragmentTransaction.add(R.id.base_frame, image_new BaseFragment()).replace(R.id.base_frame, image_new BaseFragment()).commit();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
//      else {
//            super.onBackPressed();
//        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation_drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

//        if (id == R.id.nav_new)
//        {
//            this.setTitle("LoRaBug Provisioning");
//            fragmentTransaction = getSupportFragmentManager().beginTransaction();
//            fragmentTransaction.add(R.id.base_frame, new NewFragment()).replace(R.id.base_frame, new NewFragment()).commit();
//        }

        if (id == R.id.nav_shortcuts)
        {
            this.setTitle("My Shortcuts");
            fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.base_frame, new BaseFragment()).replace(R.id.base_frame, new MyShortcutsFragment()).commit();
        }
        else if (id == R.id.nav_home)
        {
            this.setTitle("About Us");
            fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.base_frame, new BaseFragment()).replace(R.id.base_frame, new BaseFragment()).commit();
        }
        else if (id == R.id.nav_user_details)
        {
            this.setTitle("User Details");
            fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.base_frame, new UserDetailsFragment()).replace(R.id.base_frame, new UserDetailsFragment()).commit();
        }
        else if (id == R.id.nav_devices)
        {
            this.setTitle("My Devices");
            fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.base_frame, new MyDevicesFragment()).replace(R.id.base_frame, new MyDevicesFragment()).commit();
        }
//        else if (id == R.id.nav_create_device)
//        {
//            this.setTitle("Create Device");
//            fragmentTransaction = getSupportFragmentManager().beginTransaction();
//            fragmentTransaction.add(R.id.base_frame, new CreateDeviceFragment()).replace(R.id.base_frame, new CreateDeviceFragment()).commit();
//        }
        else if (id == R.id.nav_groups)
        {
            this.setTitle("My Groups");
            fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.base_frame, new MyGroupsFragment()).replace(R.id.base_frame, new MyGroupsFragment()).commit();
        }
        else if (id == R.id.nav_locations)
        {
            this.setTitle("My Locations");
            fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.base_frame, new MyLocationsFragment()).replace(R.id.base_frame, new MyLocationsFragment()).commit();
        }
        else if (id == R.id.nav_services)
        {
            this.setTitle("My Services");
            fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.base_frame, new MyServicesFragment()).replace(R.id.base_frame, new MyServicesFragment()).commit();
        }
        else if (id == R.id.nav_sign_out)
        {
            Intent SignInIntent = new Intent(getApplicationContext(), SignInActivity.class);
            SignInIntent.putExtra("signout", "true");
            startActivity(SignInIntent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
