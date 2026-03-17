package com.fitmart.app.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.fitmart.app.activities.CartActivity;
import com.fitmart.app.activities.CheckoutActivity;
import com.fitmart.app.adapters.CartAdapter;
import com.fitmart.app.databinding.FragmentCartBinding;
import com.fitmart.app.utils.CartManager;

public class CartFragment extends Fragment implements CartAdapter.CartActionListener {

    private FragmentCartBinding binding;
    private CartAdapter adapter;
    private final CartManager cart = CartManager.getInstance();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentCartBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter = new CartAdapter(cart.getCartItems(), this);
        binding.rvCart.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.rvCart.setAdapter(adapter);

        updateSummary();

        binding.btnCheckout.setOnClickListener(v ->
                startActivity(new Intent(requireContext(), CheckoutActivity.class)));
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

            double subtotal = cart.getSubtotal();
            double delivery = subtotal >= 999 ? 0 : 99;
            binding.tvSubtotal.setText(String.format("₹%.0f", subtotal));
            binding.tvDelivery.setText(delivery == 0 ? "Free" : String.format("₹%.0f", delivery));
            binding.tvTotal.setText(String.format("₹%.0f", subtotal + delivery));
        }
    }

    @Override public void onIncrement(int productId) {
        cart.incrementQuantity(productId); refresh();
    }
    @Override public void onDecrement(int productId) {
        cart.decrementQuantity(productId); refresh();
    }
    @Override public void onRemove(int productId) {
        cart.removeProduct(productId); refresh();
    }

    private void refresh() {
        adapter.notifyDataSetChanged();
        updateSummary();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
