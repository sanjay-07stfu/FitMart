package com.fitmart.app.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.fitmart.app.R;
import com.fitmart.app.models.Product;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    public interface ProductClickListener {
        void onProductClick(Product product);
        void onAddToCart(Product product);
    }

    private List<Product> products;
    private final ProductClickListener listener;

    public ProductAdapter(List<Product> products, ProductClickListener listener) {
        this.products = products;
        this.listener = listener;
    }

    public void updateList(List<Product> newList) {
        this.products = newList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                                  .inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = products.get(position);

        holder.tvName.setText(product.getName());
        holder.tvPrice.setText(String.format("₹%.0f", product.getPrice()));
        holder.tvRating.setText(String.valueOf(product.getRating()));

        if (product.getDiscountPercent() > 0) {
            holder.tvDiscount.setVisibility(View.VISIBLE);
            holder.tvDiscount.setText(product.getDiscountPercent() + "% OFF");
        } else {
            holder.tvDiscount.setVisibility(View.GONE);
        }

        Glide.with(holder.itemView.getContext())
             .load(product.getImageUrl())
             .placeholder(R.drawable.ic_shop)
             .centerCrop()
             .into(holder.ivProduct);

        holder.itemView.setOnClickListener(v -> listener.onProductClick(product));
        holder.btnAddToCart.setOnClickListener(v -> listener.onAddToCart(product));
    }

    @Override
    public int getItemCount() { return products.size(); }

    static class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView ivProduct;
        TextView tvName, tvPrice, tvRating, tvDiscount;
        View btnAddToCart;

        ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            ivProduct    = itemView.findViewById(R.id.iv_product);
            tvName       = itemView.findViewById(R.id.tv_product_name);
            tvPrice      = itemView.findViewById(R.id.tv_price);
            tvRating     = itemView.findViewById(R.id.tv_rating);
            tvDiscount   = itemView.findViewById(R.id.tv_discount);
            btnAddToCart = itemView.findViewById(R.id.btn_add_to_cart);
        }
    }
}
