package com.fitmart.app.activities;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.fitmart.app.databinding.ActivityProfileBinding;
import com.fitmart.app.utils.SharedPrefsManager;

public class ProfileActivity extends AppCompatActivity {

    private ActivityProfileBinding binding;
    private SharedPrefsManager prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        prefs = SharedPrefsManager.getInstance(this);
        populateFields();

        binding.btnBack.setOnClickListener(v -> finish());
        binding.btnSave.setOnClickListener(v -> saveProfile());
    }

    private void populateFields() {
        binding.etFullName.setText(prefs.getUserName());
        binding.etEmail.setText(prefs.getUserEmail());
        binding.etPhone.setText(prefs.getUserPhone());
    }

    private void saveProfile() {
        String name  = binding.etFullName.getText().toString().trim();
        String email = binding.etEmail.getText().toString().trim();
        String phone = binding.etPhone.getText().toString().trim();

        if (TextUtils.isEmpty(name)) {
            binding.tilFullName.setError("Name is required");
            return;
        }
        prefs.saveUserData(prefs.getUserUid(), name, email, phone);
        Toast.makeText(this, "Profile updated!", Toast.LENGTH_SHORT).show();
        finish();
    }
}
