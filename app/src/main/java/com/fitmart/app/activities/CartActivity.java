package com.fitmart.app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.fitmart.app.adapters.CartAdapter;
import com.fitmart.app.databinding.ActivityCartBinding;
import com.fitmart.app.utils.CartManager;

public class CartActivity extends AppCompatActivity implements CartAdapter.CartActionListener {

    private ActivityCartBinding binding;
    private CartAdapter adapter;
    private final CartManager cart = CartManager.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnBack.setOnClickListener(v -> finish());
        setupRecyclerView();
        updateSummary();

        binding.btnCheckout.setOnClickListener(v -> {
            if (cart.isEmpty()) {
                Toast.makeText(this, "Your cart is empty", Toast.LENGTH_SHORT).show();
            } else {
                startActivity(new Intent(this, CheckoutActivity.class));
            }
        });
    }

    private void setupRecyclerView() {
        adapter = new CartAdapter(cart.getCartItems(), this);
        binding.rvCart.setLayoutManager(new LinearLayoutManager(this));
        binding.rvCart.setAdapter(adapter);
    }

    private void updateSummary() {
        if (cart.isEmpty()) {
            binding.layoutEmptyCart.setVisibility(View.VISIBLE);
            binding.layoutSummary.setVisibility(View.GONE);
            binding.btnCheckout.setVisibility(View.GONE);
        } else {
            binding.layoutEmptyCart.setVisibility(View.GONE);
            binding.layoutSummary.setVisibility(View.VISIBLE);
            binding.btnCheckout.setVisibility(View.VISIBLE);

            double subtotal  = cart.getSubtotal();
            double delivery  = subtotal >= 999 ? 0 : 99;
            double total     = subtotal + delivery;

            binding.tvSubtotal.setText(String.format("₹%.0f", subtotal));
            binding.tvDelivery.setText(delivery == 0 ? "Free" : String.format("₹%.0f", delivery));
            binding.tvTotal.setText(String.format("₹%.0f", total));
            binding.tvItemCount.setText(cart.getCartCount() + " items");
        }
    }

    @Override
    public void onIncrement(int productId) {
        cart.incrementQuantity(productId);
        adapter.notifyDataSetChanged();
        updateSummary();
    }

    @Override
    public void onDecrement(int productId) {
        cart.decrementQuantity(productId);
        adapter.notifyDataSetChanged();
        updateSummary();
    }

    @Override
    public void onRemove(int productId) {
        cart.removeProduct(productId);
        adapter.notifyDataSetChanged();
        updateSummary();
    }
}
