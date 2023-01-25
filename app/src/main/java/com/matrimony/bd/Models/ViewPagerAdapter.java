package com.matrimony.bd.Models;


import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.matrimony.bd.Fragments.FragmentFriendList;
import com.matrimony.bd.Fragments.FragmentProfileData;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        final Fragment[] fragment = {null};

        if (position == 0) {
            fragment[0] = new FragmentProfileData();
        } else if (position == 1) {
            fragment[0] = new FragmentFriendList();
        }
        return fragment[0];
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {

        if (position == 0) {
            return "প্রোফাইল";
        }  else if (position == 1) {
            return "ফ্রেন্ড লিস্ট";
        }
        return null;
    }

}
