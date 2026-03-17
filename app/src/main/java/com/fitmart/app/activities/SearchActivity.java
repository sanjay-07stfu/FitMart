package com.fitmart.app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import com.fitmart.app.adapters.ProductAdapter;
import com.fitmart.app.databinding.ActivitySearchBinding;
import com.fitmart.app.models.Product;
import com.fitmart.app.utils.CartManager;
import com.fitmart.app.utils.Constants;
import com.fitmart.app.utils.DataProvider;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity implements ProductAdapter.ProductClickListener {

    private ActivitySearchBinding binding;
    private ProductAdapter adapter;
    private List<Product> allProducts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        allProducts = DataProvider.getAllProducts();
        setupRecyclerView();

        binding.btnBack.setOnClickListener(v -> finish());
        binding.etSearch.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterProducts(s.toString());
            }
            @Override public void afterTextChanged(Editable s) {}
        });

        binding.etSearch.requestFocus();
    }

    private void setupRecyclerView() {
        adapter = new ProductAdapter(new ArrayList<>(allProducts), this);
        binding.rvSearchResults.setLayoutManager(new GridLayoutManager(this, 2));
        binding.rvSearchResults.setAdapter(adapter);
    }

    private void filterProducts(String query) {
        List<Product> filtered = new ArrayList<>();
        if (query.isEmpty()) {
            filtered.addAll(allProducts);
        } else {
            String lower = query.toLowerCase();
            for (Product p : allProducts) {
                if (p.getName().toLowerCase().contains(lower)
                        || p.getCategory().toLowerCase().contains(lower)
                        || p.getDescription().toLowerCase().contains(lower)) {
                    filtered.add(p);
                }
            }
        }
        adapter.updateList(filtered);
    }

    @Override
    public void onProductClick(Product product) {
        Intent intent = new Intent(this, ProductDetailActivity.class);
        intent.putExtra(Constants.EXTRA_PRODUCT, product);
        startActivity(intent);
    }

    @Override
    public void onAddToCart(Product product) {
        CartManager.getInstance().addProduct(product);
    }
}
