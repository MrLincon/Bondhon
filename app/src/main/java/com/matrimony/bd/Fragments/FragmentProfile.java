package com.matrimony.bd.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.matrimony.bd.Activity.UpdateProfileActivity;
import com.matrimony.bd.BottomSheets.BottomSheetMore;
import com.matrimony.bd.BottomSheets.ProfileBottomSheets.BottomSheetF1;
import com.matrimony.bd.BottomSheets.ProfileBottomSheets.BottomSheetF10;
import com.matrimony.bd.BottomSheets.ProfileBottomSheets.BottomSheetF11;
import com.matrimony.bd.BottomSheets.ProfileBottomSheets.BottomSheetF2;
import com.matrimony.bd.BottomSheets.ProfileBottomSheets.BottomSheetF3;
import com.matrimony.bd.BottomSheets.ProfileBottomSheets.BottomSheetF4;
import com.matrimony.bd.BottomSheets.ProfileBottomSheets.BottomSheetF5;
import com.matrimony.bd.BottomSheets.ProfileBottomSheets.BottomSheetF6;
import com.matrimony.bd.BottomSheets.ProfileBottomSheets.BottomSheetF7;
import com.matrimony.bd.BottomSheets.ProfileBottomSheets.BottomSheetF8;
import com.matrimony.bd.BottomSheets.ProfileBottomSheets.BottomSheetF9;
import com.matrimony.bd.Models.ViewPagerAdapter;
import com.matrimony.bd.R;


public class FragmentProfile extends Fragment {

    View view;

    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile, container, false);


        tabLayout = view.findViewById(R.id.tabLayoutID);
        viewPager = view.findViewById(R.id.viewPager);

        tabLayout.setupWithViewPager(viewPager);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.setCurrentItem(0, true);


        return view;
    }
}
