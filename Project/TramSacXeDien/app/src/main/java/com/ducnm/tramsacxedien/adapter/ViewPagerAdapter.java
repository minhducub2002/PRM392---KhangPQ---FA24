package com.ducnm.tramsacxedien.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.ducnm.tramsacxedien.fragment.HomeFragment;
import com.ducnm.tramsacxedien.fragment.MychargeFragment;
import com.ducnm.tramsacxedien.fragment.PaymentFragment;
import com.ducnm.tramsacxedien.fragment.SearchFragment;
import com.ducnm.tramsacxedien.fragment.SettingFragment;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 1:
                return new MychargeFragment();
            case 2:
                return new SearchFragment();
            case 3:
                return new PaymentFragment();
            case 4:
                return new SettingFragment();
            case 0:
            default:
                return new HomeFragment();
        }

    }

    @Override
    public int getCount() {
        return 5;
    }
}
