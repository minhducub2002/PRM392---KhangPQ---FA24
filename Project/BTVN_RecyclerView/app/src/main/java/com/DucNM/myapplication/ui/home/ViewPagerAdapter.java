package com.DucNM.myapplication.ui.home;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 1:
                return new DealFragment();
            case 2:
                return new CategoryFragment();
            case 0:
                return new FeatureFragment();
        }
        return new FeatureFragment();
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        switch (position) {
            case 0:
                title = "Featured";
                break;
            case 1:
                title = "Deals";
                break;
            case 2:
                title = "Categories";
                break;
        }
        return title;
    }
}
