package com.fitmart.app.models;

import java.io.Serializable;

public class Product implements Serializable {
    private int id;
    private String name;
    private String description;
    private double price;
    private double originalPrice;
    private int discountPercent;
    private String imageUrl;
    private String category;
    private float rating;
    private int reviewCount;
    private boolean inStock;
    private boolean isFeatured;
    private boolean isNewArrival;
    private boolean isBestSeller;

    public Product() {}

    public Product(int id, String name, String description, double price,
                   double originalPrice, String imageUrl, String category,
                   float rating, int reviewCount, boolean inStock) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.originalPrice = originalPrice;
        this.imageUrl = imageUrl;
        this.category = category;
        this.rating = rating;
        this.reviewCount = reviewCount;
        this.inStock = inStock;
        if (originalPrice > 0 && price < originalPrice) {
            this.discountPercent = (int) (((originalPrice - price) / originalPrice) * 100);
        }
    }

    // Getters
    public int getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public double getPrice() { return price; }
    public double getOriginalPrice() { return originalPrice; }
    public int getDiscountPercent() { return discountPercent; }
    public String getImageUrl() { return imageUrl; }
    public String getCategory() { return category; }
    public float getRating() { return rating; }
    public int getReviewCount() { return reviewCount; }
    public boolean isInStock() { return inStock; }
    public boolean isFeatured() { return isFeatured; }
    public boolean isNewArrival() { return isNewArrival; }
    public boolean isBestSeller() { return isBestSeller; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setDescription(String description) { this.description = description; }
    public void setPrice(double price) { this.price = price; }
    public void setOriginalPrice(double originalPrice) { this.originalPrice = originalPrice; }
    public void setDiscountPercent(int discountPercent) { this.discountPercent = discountPercent; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public void setCategory(String category) { this.category = category; }
    public void setRating(float rating) { this.rating = rating; }
    public void setReviewCount(int reviewCount) { this.reviewCount = reviewCount; }
    public void setInStock(boolean inStock) { this.inStock = inStock; }
    public void setFeatured(boolean featured) { isFeatured = featured; }
    public void setNewArrival(boolean newArrival) { isNewArrival = newArrival; }
    public void setBestSeller(boolean bestSeller) { isBestSeller = bestSeller; }
}
