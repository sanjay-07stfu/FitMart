package com.fitmart.app.activities;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.fitmart.app.R;
import com.fitmart.app.databinding.ActivityProductDetailBinding;
import com.fitmart.app.models.Product;
import com.fitmart.app.utils.CartManager;
import com.fitmart.app.utils.Constants;

public class ProductDetailActivity extends AppCompatActivity {

    private ActivityProductDetailBinding binding;
    private Product product;
    private int quantity = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProductDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        product = (Product) getIntent().getSerializableExtra(Constants.EXTRA_PRODUCT);
        if (product == null) {
            finish();
            return;
        }

        populateProductDetails();
        setupListeners();
    }

    private void populateProductDetails() {
        Glide.with(this)
             .load(product.getImageUrl())
             .placeholder(R.drawable.ic_shop)
             .into(binding.ivProduct);

        binding.tvProductName.setText(product.getName());
        binding.tvPrice.setText(String.format("₹%.0f", product.getPrice()));
        binding.tvOriginalPrice.setText(String.format("₹%.0f", product.getOriginalPrice()));
        binding.tvDiscount.setText(product.getDiscountPercent() + "% OFF");
        binding.tvDescription.setText(product.getDescription());
        binding.tvCategory.setText(product.getCategory());
        binding.ratingBar.setRating(product.getRating());
        binding.tvRatingCount.setText(String.format("(%.1f) • %d Reviews",
                product.getRating(), product.getReviewCount()));
        binding.tvQuantity.setText(String.valueOf(quantity));

        if (product.isInStock()) {
            binding.tvStockStatus.setText("✓ In Stock");
            binding.tvStockStatus.setTextColor(getColor(R.color.green_success));
        } else {
            binding.tvStockStatus.setText("✗ Out of Stock");
            binding.tvStockStatus.setTextColor(getColor(R.color.error));
            binding.btnAddToCart.setEnabled(false);
            binding.btnBuyNow.setEnabled(false);
        }
    }

    private void setupListeners() {
        binding.btnBack.setOnClickListener(v -> finish());

        binding.btnDecrement.setOnClickListener(v -> {
            if (quantity > 1) {
                quantity--;
                binding.tvQuantity.setText(String.valueOf(quantity));
            }
        });

        binding.btnIncrement.setOnClickListener(v -> {
            if (quantity < 10) {
                quantity++;
                binding.tvQuantity.setText(String.valueOf(quantity));
            }
        });

        binding.btnAddToCart.setOnClickListener(v -> {
            for (int i = 0; i < quantity; i++) {
                CartManager.getInstance().addProduct(product);
            }
            Toast.makeText(this, product.getName() + " added to cart!", Toast.LENGTH_SHORT).show();
        });

        binding.btnBuyNow.setOnClickListener(v -> {
            CartManager.getInstance().addProduct(product);
            startActivity(new android.content.Intent(this, CartActivity.class));
        });

        binding.btnWishlist.setOnClickListener(v ->
                Toast.makeText(this, "Added to wishlist!", Toast.LENGTH_SHORT).show());
    }
}
