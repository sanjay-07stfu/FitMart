package com.fitmart.app.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import com.fitmart.app.activities.MainActivity;
import com.fitmart.app.activities.ProductDetailActivity;
import com.fitmart.app.adapters.ProductAdapter;
import com.fitmart.app.databinding.FragmentShopBinding;
import com.fitmart.app.models.Product;
import com.fitmart.app.utils.CartManager;
import com.fitmart.app.utils.Constants;
import com.fitmart.app.utils.DataProvider;
import com.google.android.material.chip.Chip;

import java.util.List;

public class ShopFragment extends Fragment implements ProductAdapter.ProductClickListener {

    private FragmentShopBinding binding;
    private ProductAdapter adapter;
    private String selectedCategory = "All";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentShopBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Handle category argument from HomeFragment
        if (getArguments() != null) {
            selectedCategory = getArguments().getString(Constants.EXTRA_CATEGORY, "All");
        }

        setupChips();
        setupRecyclerView();
        loadProducts();
    }

    private void setupChips() {
        String[] categories = {"All", "Supplements", "Equipment", "Apparel", "Accessories", "Nutrition", "Cardio"};
        for (String cat : categories) {
            Chip chip = new Chip(requireContext());
            chip.setText(cat);
            chip.setCheckable(true);
            chip.setChecked(cat.equals(selectedCategory));
            chip.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) {
                    selectedCategory = cat;
                    loadProducts();
                }
            });
            binding.chipGroup.addView(chip);
        }
    }

    private void setupRecyclerView() {
        adapter = new ProductAdapter(DataProvider.getAllProducts(), this);
        binding.rvProducts.setLayoutManager(new GridLayoutManager(requireContext(), 2));
        binding.rvProducts.setAdapter(adapter);
    }

    private void loadProducts() {
        List<Product> products;
        if (selectedCategory.equals("All")) {
            products = DataProvider.getAllProducts();
        } else {
            products = DataProvider.getProductsByCategory(selectedCategory);
        }
        adapter.updateList(products);
        binding.tvProductCount.setText(products.size() + " Products");
    }

    @Override
    public void onProductClick(Product product) {
        Intent intent = new Intent(requireContext(), ProductDetailActivity.class);
        intent.putExtra(Constants.EXTRA_PRODUCT, product);
        startActivity(intent);
    }

    @Override
    public void onAddToCart(Product product) {
        CartManager.getInstance().addProduct(product);
        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).updateCartBadge();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
