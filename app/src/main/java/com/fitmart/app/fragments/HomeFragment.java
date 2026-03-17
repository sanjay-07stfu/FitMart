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
import androidx.recyclerview.widget.LinearLayoutManager;

import com.fitmart.app.activities.MainActivity;
import com.fitmart.app.activities.ProductDetailActivity;
import com.fitmart.app.activities.SearchActivity;
import com.fitmart.app.adapters.BannerAdapter;
import com.fitmart.app.adapters.CategoryAdapter;
import com.fitmart.app.adapters.ProductAdapter;
import com.fitmart.app.databinding.FragmentHomeBinding;
import com.fitmart.app.models.Category;
import com.fitmart.app.models.Product;
import com.fitmart.app.utils.CartManager;
import com.fitmart.app.utils.Constants;
import com.fitmart.app.utils.DataProvider;
import com.fitmart.app.utils.SharedPrefsManager;

import java.util.List;

public class HomeFragment extends Fragment
        implements ProductAdapter.ProductClickListener,
                   CategoryAdapter.CategoryClickListener {

    private FragmentHomeBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String name = SharedPrefsManager.getInstance(requireContext()).getUserName();
        binding.tvGreeting.setText("Hello, " + (name.isEmpty() ? "Fitness Lover" : name) + " 👋");

        setupBanner();
        setupCategories();
        setupFeatured();
        setupNewArrivals();

        binding.searchBar.setOnClickListener(v ->
                startActivity(new Intent(requireContext(), SearchActivity.class)));
    }

    private void setupBanner() {
        List<String> banners = DataProvider.getBannerUrls();
        BannerAdapter bannerAdapter = new BannerAdapter(banners);
        binding.vpBanner.setAdapter(bannerAdapter);

        // Connect TabLayout dots to ViewPager2
        new com.google.android.material.tabs.TabLayoutMediator(
                binding.dotsIndicator, binding.vpBanner,
                (tab, position) -> { /* no text needed for dots */ }
        ).attach();
    }

    private void setupCategories() {
        List<Category> categories = DataProvider.getCategories();
        CategoryAdapter catAdapter = new CategoryAdapter(categories, this);
        binding.rvCategories.setLayoutManager(
                new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.rvCategories.setAdapter(catAdapter);
    }

    private void setupFeatured() {
        List<Product> products = DataProvider.getFeaturedProducts();
        ProductAdapter adapter = new ProductAdapter(products, this);
        binding.rvFeatured.setLayoutManager(
                new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.rvFeatured.setAdapter(adapter);
    }

    private void setupNewArrivals() {
        List<Product> products = DataProvider.getNewArrivals();
        ProductAdapter adapter = new ProductAdapter(products, this);
        binding.rvNewArrivals.setLayoutManager(
                new GridLayoutManager(requireContext(), 2));
        binding.rvNewArrivals.setAdapter(adapter);
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
    public void onCategoryClick(Category category) {
        // Switch to shop fragment filtered by category
        ShopFragment shopFragment = new ShopFragment();
        Bundle args = new Bundle();
        args.putString(Constants.EXTRA_CATEGORY, category.getName());
        shopFragment.setArguments(args);
        requireActivity().getSupportFragmentManager()
                         .beginTransaction()
                         .replace(com.fitmart.app.R.id.fragment_container, shopFragment)
                         .addToBackStack(null)
                         .commit();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
