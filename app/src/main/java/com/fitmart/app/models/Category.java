package com.fitmart.app.models;

public class Category {
    private int id;
    private String name;
    private int iconResId;
    private String colorHex;
    private int productCount;

    public Category() {}

    public Category(int id, String name, int iconResId, String colorHex, int productCount) {
        this.id = id;
        this.name = name;
        this.iconResId = iconResId;
        this.colorHex = colorHex;
        this.productCount = productCount;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public int getIconResId() { return iconResId; }
    public String getColorHex() { return colorHex; }
    public int getProductCount() { return productCount; }

    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setIconResId(int iconResId) { this.iconResId = iconResId; }
    public void setColorHex(String colorHex) { this.colorHex = colorHex; }
    public void setProductCount(int productCount) { this.productCount = productCount; }
}
