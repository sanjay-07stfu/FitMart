package com.fitmart.app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.fitmart.app.databinding.ActivityRegisterBinding;
import com.fitmart.app.utils.SharedPrefsManager;

public class RegisterActivity extends AppCompatActivity {

    private ActivityRegisterBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnBack.setOnClickListener(v -> finish());
        binding.btnRegister.setOnClickListener(v -> attemptRegister());
        binding.tvLogin.setOnClickListener(v -> finish());
    }

    private void attemptRegister() {
        String fullName  = binding.etFullName.getText().toString().trim();
        String email     = binding.etEmail.getText().toString().trim();
        String phone     = binding.etPhone.getText().toString().trim();
        String password  = binding.etPassword.getText().toString().trim();
        String confirm   = binding.etConfirmPassword.getText().toString().trim();

        if (TextUtils.isEmpty(fullName)) {
            binding.tilFullName.setError("Full name is required");
            return;
        }
        if (TextUtils.isEmpty(email) || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.tilEmail.setError("Enter a valid email");
            return;
        }
        if (TextUtils.isEmpty(phone) || phone.length() < 10) {
            binding.tilPhone.setError("Enter a valid phone number");
            return;
        }
        if (password.length() < 6) {
            binding.tilPassword.setError("Password must be at least 6 characters");
            return;
        }
        if (!password.equals(confirm)) {
            binding.tilConfirmPassword.setError("Passwords do not match");
            return;
        }

        // Clear errors
        binding.tilFullName.setError(null);
        binding.tilEmail.setError(null);
        binding.tilPhone.setError(null);
        binding.tilPassword.setError(null);
        binding.tilConfirmPassword.setError(null);

        binding.btnRegister.setEnabled(false);
        binding.progressBar.setVisibility(View.VISIBLE);

        // Simulate async registration (replace with real backend)
        binding.getRoot().postDelayed(() -> {
            binding.progressBar.setVisibility(View.GONE);
            binding.btnRegister.setEnabled(true);

            SharedPrefsManager prefs = SharedPrefsManager.getInstance(this);
            prefs.saveUserData("uid_" + System.currentTimeMillis(), fullName, email, phone);
            prefs.setLoggedIn(true);

            Toast.makeText(this, "Account created successfully!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, MainActivity.class));
            finishAffinity();
        }, 1500);
    }
}
