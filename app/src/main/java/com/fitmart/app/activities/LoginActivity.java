package com.fitmart.app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.fitmart.app.databinding.ActivityLoginBinding;
import com.fitmart.app.utils.SharedPrefsManager;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnLogin.setOnClickListener(v -> attemptLogin());
        binding.tvRegister.setOnClickListener(v ->
                startActivity(new Intent(this, RegisterActivity.class)));
        binding.tvForgotPassword.setOnClickListener(v ->
                Toast.makeText(this, "Password reset email sent!", Toast.LENGTH_SHORT).show());
    }

    private void attemptLogin() {
        String email    = binding.etEmail.getText().toString().trim();
        String password = binding.etPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            binding.tilEmail.setError("Email is required");
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.tilEmail.setError("Enter a valid email");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            binding.tilPassword.setError("Password is required");
            return;
        }
        if (password.length() < 6) {
            binding.tilPassword.setError("Password must be at least 6 characters");
            return;
        }

        binding.tilEmail.setError(null);
        binding.tilPassword.setError(null);

        // Show progress
        binding.btnLogin.setEnabled(false);
        binding.progressBar.setVisibility(View.VISIBLE);

        // Simulate async login (replace with real auth — e.g. Firebase)
        binding.getRoot().postDelayed(() -> {
            binding.progressBar.setVisibility(View.GONE);
            binding.btnLogin.setEnabled(true);

            // Save session
            SharedPrefsManager prefs = SharedPrefsManager.getInstance(this);
            prefs.saveUserData("uid_001", extractNameFromEmail(email), email, "");
            prefs.setLoggedIn(true);

            startActivity(new Intent(this, MainActivity.class));
            finishAffinity();
        }, 1500);
    }

    private String extractNameFromEmail(String email) {
        String local = email.contains("@") ? email.substring(0, email.indexOf('@')) : email;
        return local.substring(0, 1).toUpperCase() + local.substring(1);
    }
}
