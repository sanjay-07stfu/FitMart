package com.fitmart.app.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.fitmart.app.R;
import com.fitmart.app.databinding.ActivityMainBinding;
import com.fitmart.app.fragments.CartFragment;
import com.fitmart.app.fragments.HomeFragment;
import com.fitmart.app.fragments.ProfileFragment;
import com.fitmart.app.fragments.ShopFragment;
import com.fitmart.app.utils.CartManager;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setupBottomNavigation();

        // Load home by default
        if (savedInstanceState == null) {
            loadFragment(new HomeFragment());
        }
    }

    private void setupBottomNavigation() {
        binding.bottomNav.setOnItemSelectedListener(item -> {
            Fragment fragment = null;
            int id = item.getItemId();
            if (id == R.id.nav_home)    fragment = new HomeFragment();
            else if (id == R.id.nav_shop)    fragment = new ShopFragment();
            else if (id == R.id.nav_cart)    fragment = new CartFragment();
            else if (id == R.id.nav_profile) fragment = new ProfileFragment();

            if (fragment != null) {
                loadFragment(fragment);
                return true;
            }
            return false;
        });
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }

    public void updateCartBadge() {
        int count = CartManager.getInstance().getCartCount();
        BadgeDrawable badge = binding.bottomNav.getOrCreateBadge(R.id.nav_cart);
        if (count > 0) {
            badge.setVisible(true);
            badge.setNumber(count);
        } else {
            badge.setVisible(false);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateCartBadge();
    }
}
