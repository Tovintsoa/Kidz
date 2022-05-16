package com.m1p9.kidz;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.m1p9.kidz.databinding.ActivityMainBinding;
import com.m1p9.kidz.manager.SessionManagement;
import com.m1p9.kidz.ui.gallery.GalleryFragment;
import com.m1p9.kidz.ui.home.HomeFragment;
import com.m1p9.kidz.ui.login.LoginActivity;
import com.m1p9.kidz.ui.slideshow.SlideshowFragment;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    private DrawerLayout mDrawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);
        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
       // DrawerLayout mdrawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,R.id.action_logout)
                .setOpenableLayout(mDrawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        setNavigationViewListener();
        changeMailHeader();





    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;
        Class fragmentClass;

        switch (item.getItemId()){
            case R.id.action_logout:{
                logout();
                return true;
            }
            case R.id.nav_home:{
                fragmentClass = HomeFragment.class;

                break;
            }
            case R.id.nav_gallery:{
                fragmentClass = GalleryFragment.class;
                break;
            }
            case R.id.nav_slideshow:{
                fragmentClass = SlideshowFragment.class;
                break;
            }
            default:
                fragmentClass = HomeFragment.class;

        }
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.nav_host_fragment_content_main, fragment);
        fragmentManager.popBackStack();
        fragmentTransaction.commit();
        item.setChecked(true);
        System.out.println("aaa");

        // Set action bar title
        getSupportActionBar().setTitle(item.getTitle());
        // Close the navigation drawer
        mDrawer.closeDrawers();
        return true;
    }
    private void logout() {
        SessionManagement sessionManagement = new SessionManagement(MainActivity.this);
        sessionManagement.removeSession();
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
    }

    private void setNavigationViewListener() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }
    private void changeMailHeader(){
        SessionManagement sessionManagement = new SessionManagement(MainActivity.this);
        System.out.println(sessionManagement.getUserSession());

        NavigationView nav = (NavigationView) findViewById(R.id.nav_view);
        View headerView = nav.getHeaderView(0);
        TextView mail = (TextView) headerView.findViewById(R.id.textView);
        TextView usernameTop = (TextView) headerView.findViewById(R.id.textViewName);
        mail.setText(sessionManagement.getUserSession().getuEmail());
        usernameTop.setText(sessionManagement.getUserSession().getuUsername());

    }
}