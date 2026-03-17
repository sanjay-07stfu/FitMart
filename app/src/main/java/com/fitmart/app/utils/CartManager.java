package com.fitmart.app.utils;

import com.fitmart.app.models.CartItem;
import com.fitmart.app.models.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * Singleton cart manager — holds cart state in memory for the session.
 */
public class CartManager {
    private static CartManager instance;
    private final List<CartItem> cartItems = new ArrayList<>();

    private CartManager() {}

    public static synchronized CartManager getInstance() {
        if (instance == null) {
            instance = new CartManager();
        }
        return instance;
    }

    public void addProduct(Product product) {
        for (CartItem item : cartItems) {
            if (item.getProduct().getId() == product.getId()) {
                item.incrementQuantity();
                return;
            }
        }
        cartItems.add(new CartItem(product, 1));
    }

    public void removeProduct(int productId) {
        cartItems.removeIf(item -> item.getProduct().getId() == productId);
    }

    public void incrementQuantity(int productId) {
        for (CartItem item : cartItems) {
            if (item.getProduct().getId() == productId) {
                item.incrementQuantity();
                return;
            }
        }
    }

    public void decrementQuantity(int productId) {
        for (CartItem item : cartItems) {
            if (item.getProduct().getId() == productId) {
                if (item.getQuantity() <= 1) {
                    removeProduct(productId);
                } else {
                    item.decrementQuantity();
                }
                return;
            }
        }
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public int getCartCount() {
        int count = 0;
        for (CartItem item : cartItems) count += item.getQuantity();
        return count;
    }

    public double getSubtotal() {
        double total = 0;
        for (CartItem item : cartItems) total += item.getTotalPrice();
        return total;
    }

    public void clearCart() {
        cartItems.clear();
    }

    public boolean isEmpty() {
        return cartItems.isEmpty();
    }
}
