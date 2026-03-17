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
import com.fitmart.app.models.CartItem;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    public interface CartActionListener {
        void onIncrement(int productId);
        void onDecrement(int productId);
        void onRemove(int productId);
    }

    private final List<CartItem> cartItems;
    private final CartActionListener listener;

    public CartAdapter(List<CartItem> cartItems, CartActionListener listener) {
        this.cartItems = cartItems;
        this.listener  = listener;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                                  .inflate(R.layout.item_cart, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        CartItem item = cartItems.get(position);

        holder.tvName.setText(item.getProduct().getName());
        holder.tvPrice.setText(String.format("₹%.0f", item.getProduct().getPrice()));
        holder.tvItemTotal.setText(String.format("₹%.0f", item.getTotalPrice()));
        holder.tvQuantity.setText(String.valueOf(item.getQuantity()));

        Glide.with(holder.itemView.getContext())
             .load(item.getProduct().getImageUrl())
             .placeholder(R.drawable.ic_shop)
             .centerCrop()
             .into(holder.ivProduct);

        int productId = item.getProduct().getId();
        holder.btnIncrement.setOnClickListener(v -> listener.onIncrement(productId));
        holder.btnDecrement.setOnClickListener(v -> listener.onDecrement(productId));
        holder.btnRemove.setOnClickListener(v -> listener.onRemove(productId));
    }

    @Override
    public int getItemCount() { return cartItems.size(); }

    static class CartViewHolder extends RecyclerView.ViewHolder {
        ImageView ivProduct;
        TextView tvName, tvPrice, tvQuantity, tvItemTotal;
        View btnIncrement, btnDecrement, btnRemove;

        CartViewHolder(@NonNull View itemView) {
            super(itemView);
            ivProduct    = itemView.findViewById(R.id.iv_product);
            tvName       = itemView.findViewById(R.id.tv_product_name);
            tvPrice      = itemView.findViewById(R.id.tv_price);
            tvQuantity   = itemView.findViewById(R.id.tv_quantity);
            tvItemTotal  = itemView.findViewById(R.id.tv_item_total);
            btnIncrement = itemView.findViewById(R.id.btn_increment);
            btnDecrement = itemView.findViewById(R.id.btn_decrement);
            btnRemove    = itemView.findViewById(R.id.btn_remove);
        }
    }
}
