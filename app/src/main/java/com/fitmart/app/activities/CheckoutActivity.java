package com.fitmart.app.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.fitmart.app.databinding.ActivityCheckoutBinding;
import com.fitmart.app.utils.CartManager;

public class CheckoutActivity extends AppCompatActivity {

    private ActivityCheckoutBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCheckoutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnBack.setOnClickListener(v -> finish());

        double subtotal = CartManager.getInstance().getSubtotal();
        double delivery = subtotal >= 999 ? 0 : 99;
        double total    = subtotal + delivery;

        binding.tvSubtotal.setText(String.format("₹%.0f", subtotal));
        binding.tvDelivery.setText(delivery == 0 ? "Free" : String.format("₹%.0f", delivery));
        binding.tvTotal.setText(String.format("₹%.0f", total));

        binding.btnPlaceOrder.setOnClickListener(v -> {
            String name    = binding.etName.getText().toString().trim();
            String address = binding.etAddress.getText().toString().trim();
            String city    = binding.etCity.getText().toString().trim();
            String pincode = binding.etPincode.getText().toString().trim();

            if (name.isEmpty() || address.isEmpty() || city.isEmpty() || pincode.isEmpty()) {
                Toast.makeText(this, "Please fill all address fields", Toast.LENGTH_SHORT).show();
                return;
            }

            binding.btnPlaceOrder.setEnabled(false);
            binding.progressBar.setVisibility(View.VISIBLE);

            binding.getRoot().postDelayed(() -> {
                CartManager.getInstance().clearCart();
                binding.progressBar.setVisibility(View.GONE);
                binding.layoutOrderForm.setVisibility(View.GONE);
                binding.layoutOrderSuccess.setVisibility(View.VISIBLE);
            }, 2000);
        });

        binding.btnContinueShopping.setOnClickListener(v -> {
            finishAffinity();
            startActivity(new android.content.Intent(this, MainActivity.class));
        });
    }
}
