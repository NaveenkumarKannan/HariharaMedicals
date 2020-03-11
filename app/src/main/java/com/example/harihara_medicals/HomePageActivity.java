package com.example.harihara_medicals;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.harihara_medicals.Doctor.My_appointment;
import com.example.harihara_medicals.Medicine.MedicalRecords;
import com.example.harihara_medicals.Medicine.My_order_activity;
import com.example.harihara_medicals.ui.Book_Dr.BookdrFragment;
import com.example.harihara_medicals.ui.Calender.CalenderFragment;
import com.example.harihara_medicals.ui.home.HomeFragment;
import com.example.harihara_medicals.ui.productfragment2.ProductFragment2;
import com.example.harihara_medicals.ui.user.UserFragment;
import com.example.harihara_medicals.utils.Util;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

public class HomePageActivity extends AppCompatActivity {
    FrameLayout frameLayout;
    BottomNavigationView navigationView;
    NavigationView navigation_View;
    public DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);
        loadFragment(new HomeFragment());
        frameLayout = findViewById(R.id.nav_host_fragment);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.navigation_home:
                        setTitle("Home");
                        loadFragment(new HomeFragment());
                        break;
                    case R.id.navigation_calender:
                        setTitle("Calender");
                        loadFragment(new CalenderFragment());
                        break;
                    case R.id.navigation_book_dr:
                        setTitle("Book Dr");
                        loadFragment(new BookdrFragment());
                        break;
                    case R.id.navigation_medicine:
                        setTitle("Medicein");
                        loadFragment(new ProductFragment2());
                        break;
                    case R.id.navigation_user:
                        setTitle("User");
                        loadFragment(new UserFragment());
                        break;

                }
                return true;
            }
        });
       /* ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,
                    new HomeFragment()).commit();
            navigation_View.setCheckedItem(R.id.navigation_home);
        }*/
        drawerLayout = findViewById(R.id.drawer_layout);
        navigation_View = findViewById(R.id.navi_view);
        navigation_View.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.nav_appointment:
                       /*getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,
                               new HomeFragment()).commit();*/
                        startActivity(new Intent(HomePageActivity.this, My_appointment.class));
                        break;
                    case R.id.nav_order:
                       /*getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,
                               new BookdrFragment()).commit();*/
                        startActivity(new Intent(HomePageActivity.this, My_order_activity.class));
                        break;
                    case R.id.nav_remainder:
                        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,
                                new CalenderFragment()).commit();
                        break;
                    case R.id.nav_medical_records:
                       /*getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,
                               new ProductFragment2()).commit();*/
                        startActivity(new Intent(HomePageActivity.this, MedicalRecords.class));
                        break;
                    case R.id.nav_log_out:
                        Toast.makeText(HomePageActivity.this, "done", Toast.LENGTH_LONG).show();
                        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,
                                new UserFragment()).commit();


                        break;

                }

                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });


    }


    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.nav_host_fragment, fragment)
                .commit();
    }

    @Override
    public void onBackPressed() {
        Util.onBack(this);
    }
}
