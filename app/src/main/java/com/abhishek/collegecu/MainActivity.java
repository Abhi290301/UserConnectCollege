package com.abhishek.collegecu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager2.widget.ViewPager2;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Toast;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private ViewPager2 viewPager2;
    private BottomNavigationView bottomNavigationView;
    private ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.bottomNav);
        viewPager2 = findViewById(R.id.viewPager);
        Window window = getWindow();
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.orange));



        ActionBar actionBar = getSupportActionBar();
        ColorDrawable colorDrawable = new ColorDrawable(ContextCompat.getColor(this, R.color.orange));
        assert actionBar != null;
        actionBar.setBackgroundDrawable(colorDrawable);
        DrawerLayout drawerLayout = findViewById(R.id.drawerLayout);
        NavigationView navigationView = findViewById(R.id.navigationview);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.start, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        navigationView.setNavigationItemSelectedListener(this);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this);
        viewPager2.setAdapter(viewPagerAdapter);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            switch (id) {
                case R.id.home:
                    viewPager2.setCurrentItem(0);
                    break;
                case R.id.notice:
                    viewPager2.setCurrentItem(1);
                    break;
                case R.id.faculty:
                    viewPager2.setCurrentItem(2);
                    break;
                case R.id.gallery:
                    viewPager2.setCurrentItem(3);
                    break;
                case R.id.about:
                    viewPager2.setCurrentItem(4);
                    break;
            }
            return true;
        });
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        bottomNavigationView.getMenu().findItem(R.id.home).setChecked(true);
                        Objects.requireNonNull(getSupportActionBar()).setTitle("Home");
                        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                        break;
                    case 1:
                        bottomNavigationView.getMenu().findItem(R.id.notice).setChecked(true);
                        Objects.requireNonNull(getSupportActionBar()).setTitle("Notice");
                        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                        break;
                    case 2:
                        bottomNavigationView.getMenu().findItem(R.id.faculty).setChecked(true);
                        Objects.requireNonNull(getSupportActionBar()).setTitle("Faculty Information");
                        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                        break;
                    case 3:
                        bottomNavigationView.getMenu().findItem(R.id.gallery).setChecked(true);
                        Objects.requireNonNull(getSupportActionBar()).setTitle("Gallery");
                        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                        break;
                    case 4:
                        bottomNavigationView.getMenu().findItem(R.id.about).setChecked(true);
                        Objects.requireNonNull(getSupportActionBar()).setTitle("Developer info");
                        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

                        break;

                }
                super.onPageSelected(position);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_developer:
                Toast.makeText(this, "Developer", Toast.LENGTH_SHORT).show();
                break;
            case R.id.navigation_video:
                Toast.makeText(this, "Video Lectures", Toast.LENGTH_SHORT).show();
                break;
            case R.id.navigation_rate:
                Toast.makeText(this, "Rate Us", Toast.LENGTH_SHORT).show();
                break;
            case R.id.navigation_share:
                Toast.makeText(this, "Share App", Toast.LENGTH_SHORT).show();
                break;
            case R.id.navigation_website:
                Toast.makeText(this, "Visit Website", Toast.LENGTH_SHORT).show();
                break;
            case R.id.navigation_theme:
                Toast.makeText(this, "Themes", Toast.LENGTH_SHORT).show();
                break;
            case R.id.navigation_ebook:
                Toast.makeText(this, "E-Books", Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }
}