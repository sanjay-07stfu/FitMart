package com.fitmart.app.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.fitmart.app.activities.LoginActivity;
import com.fitmart.app.activities.ProfileActivity;
import com.fitmart.app.databinding.FragmentProfileBinding;
import com.fitmart.app.utils.SharedPrefsManager;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SharedPrefsManager prefs = SharedPrefsManager.getInstance(requireContext());
        binding.tvUserName.setText(prefs.getUserName().isEmpty() ? "FitMart User" : prefs.getUserName());
        binding.tvUserEmail.setText(prefs.getUserEmail());

        binding.layoutEditProfile.setOnClickListener(v ->
                startActivity(new Intent(requireContext(), ProfileActivity.class)));
        binding.layoutMyOrders.setOnClickListener(v ->
                Toast.makeText(requireContext(), "My Orders coming soon!", Toast.LENGTH_SHORT).show());
        binding.layoutWishlist.setOnClickListener(v ->
                Toast.makeText(requireContext(), "Wishlist coming soon!", Toast.LENGTH_SHORT).show());
        binding.layoutAddressBook.setOnClickListener(v ->
                Toast.makeText(requireContext(), "Address Book coming soon!", Toast.LENGTH_SHORT).show());
        binding.layoutNotifications.setOnClickListener(v ->
                Toast.makeText(requireContext(), "Notifications coming soon!", Toast.LENGTH_SHORT).show());
        binding.layoutHelpSupport.setOnClickListener(v ->
                Toast.makeText(requireContext(), "Help & Support coming soon!", Toast.LENGTH_SHORT).show());
        binding.layoutLogout.setOnClickListener(v -> confirmLogout(prefs));
    }

    private void confirmLogout(SharedPrefsManager prefs) {
        new AlertDialog.Builder(requireContext())
                .setTitle("Logout")
                .setMessage("Are you sure you want to logout?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    prefs.clearAll();
                    startActivity(new Intent(requireContext(), LoginActivity.class));
                    requireActivity().finishAffinity();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
