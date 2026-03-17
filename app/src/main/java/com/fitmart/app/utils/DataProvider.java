package com.fitmart.app.utils;

import com.fitmart.app.models.Category;
import com.fitmart.app.models.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * Provides in-memory sample data for the FitMart app.
 * In a production app, replace with real API calls.
 */
public class DataProvider {

    public static List<Product> getFeaturedProducts() {
        List<Product> list = new ArrayList<>();
        list.add(new Product(1, "Whey Protein Gold Standard",
                "100% Whey Protein by Optimum Nutrition. 24g of protein per serving. Supports muscle growth & recovery.",
                2499, 2999, "https://images.unsplash.com/photo-1593095948071-474c5cc2989d?w=400",
                "Supplements", 4.8f, 1240, true));
        list.add(new Product(2, "Adjustable Dumbbell Set",
                "Space-saving adjustable dumbbells — 5 kg to 25 kg. Perfect for home gym workouts.",
                8999, 12000, "https://images.unsplash.com/photo-1571019613454-1cb2f99b2d8b?w=400",
                "Equipment", 4.7f, 856, true));
        list.add(new Product(3, "Resistance Bands Set",
                "Premium latex resistance bands. Set of 5 bands with different resistance levels.",
                799, 1299, "https://images.unsplash.com/photo-1518611012118-696072aa579a?w=400",
                "Accessories", 4.6f, 2103, true));
        list.add(new Product(4, "Pre-Workout Explosive Energy",
                "High-stim pre-workout formula with 200mg caffeine, beta-alanine & citrulline. 30 servings.",
                1499, 1999, "https://images.unsplash.com/photo-1517836357463-d25dfeac3438?w=400",
                "Supplements", 4.5f, 672, true));
        return list;
    }

    public static List<Product> getNewArrivals() {
        List<Product> list = new ArrayList<>();
        list.add(new Product(5, "FitMart Training Gloves",
                "Premium leather gym gloves with wrist support. Anti-slip grip for all exercises.",
                599, 899, "https://images.unsplash.com/photo-1583454155184-870a1f63aebc?w=400",
                "Accessories", 4.4f, 388, true));
        list.add(new Product(6, "Yoga Mat Premium 6mm",
                "Non-slip eco-friendly TPE yoga mat. 183cm x 61cm. Ideal for yoga and floor workouts.",
                1299, 1799, "https://images.unsplash.com/photo-1601925228245-3f924b394562?w=400",
                "Accessories", 4.7f, 921, true));
        list.add(new Product(7, "BCAA Recovery Drink",
                "Branched Chain Amino Acids 2:1:1 ratio. 30 servings. Reduces muscle soreness.",
                999, 1299, "https://images.unsplash.com/photo-1593095948071-474c5cc2989d?w=400",
                "Supplements", 4.3f, 543, true));
        list.add(new Product(8, "Pull-Up Bar Doorframe",
                "Heavy duty doorframe pull-up bar. No screws required. Supports up to 120 kg.",
                1799, 2499, "https://images.unsplash.com/photo-1571019613454-1cb2f99b2d8b?w=400",
                "Equipment", 4.5f, 267, true));
        return list;
    }

    public static List<Product> getBestSellers() {
        List<Product> list = new ArrayList<>();
        list.add(new Product(9, "Mass Gainer XXL 3kg",
                "High-calorie mass gainer with 60g carbs & 30g protein per serving. Chocolate flavour.",
                3299, 3999, "https://images.unsplash.com/photo-1517836357463-d25dfeac3438?w=400",
                "Supplements", 4.6f, 1897, true));
        list.add(new Product(10, "Gym Backpack 30L",
                "Multipurpose gym bag with wet/dry compartment, shoe pocket & water bottle holder.",
                1499, 2199, "https://images.unsplash.com/photo-1553062407-98eeb64c6a62?w=400",
                "Accessories", 4.8f, 1102, true));
        list.add(new Product(11, "Foam Roller Deep Tissue",
                "High-density EPP foam roller for myofascial release. Improves flexibility & recovery.",
                899, 1199, "https://images.unsplash.com/photo-1518611012118-696072aa579a?w=400",
                "Accessories", 4.6f, 778, true));
        list.add(new Product(12, "Smart Water Bottle 750ml",
                "BPA-free tracked hydration bottle with time markers. Motivates 2L water intake daily.",
                749, 999, "https://images.unsplash.com/photo-1571019613454-1cb2f99b2d8b?w=400",
                "Accessories", 4.5f, 2344, true));
        return list;
    }

    public static List<Product> getAllProducts() {
        List<Product> all = new ArrayList<>();
        all.addAll(getFeaturedProducts());
        all.addAll(getNewArrivals());
        all.addAll(getBestSellers());
        return all;
    }

    public static List<Product> getProductsByCategory(String category) {
        List<Product> result = new ArrayList<>();
        for (Product p : getAllProducts()) {
            if (p.getCategory().equalsIgnoreCase(category)) {
                result.add(p);
            }
        }
        return result;
    }

    public static List<Category> getCategories() {
        List<Category> list = new ArrayList<>();
        list.add(new Category(1, "Supplements", 0, "#FF6B35", 48));
        list.add(new Category(2, "Equipment",   0, "#2ECC71", 32));
        list.add(new Category(3, "Apparel",      0, "#3498DB", 56));
        list.add(new Category(4, "Accessories",  0, "#9B59B6", 74));
        list.add(new Category(5, "Nutrition",    0, "#F39C12", 29));
        list.add(new Category(6, "Cardio",       0, "#E74C3C", 21));
        return list;
    }

    public static List<String> getBannerUrls() {
        List<String> urls = new ArrayList<>();
        urls.add("https://images.unsplash.com/photo-1571019613454-1cb2f99b2d8b?w=800");
        urls.add("https://images.unsplash.com/photo-1517836357463-d25dfeac3438?w=800");
        urls.add("https://images.unsplash.com/photo-1518611012118-696072aa579a?w=800");
        return urls;
    }
}
