package com.ducnm.tramsacxedien.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.ducnm.tramsacxedien.R;
import com.ducnm.tramsacxedien.adapter.ViewPagerAdapter;
import com.ducnm.tramsacxedien.databinding.ActivityMainBinding;
import com.ducnm.tramsacxedien.fragment.HomeFragment;
import com.ducnm.tramsacxedien.fragment.MychargeFragment;
import com.ducnm.tramsacxedien.fragment.PaymentFragment;
import com.ducnm.tramsacxedien.fragment.SearchFragment;
import com.ducnm.tramsacxedien.fragment.SettingFragment;
import com.ducnm.tramsacxedien.object.Account;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private TextView tvUsername;
    private TextView tvPassword;

    private Account mAccount;

    private ViewPager mViewPager;
    private BottomNavigationView mBottomNavigationView;

    private void bindingView() {
//        tvUsername = findViewById(R.id.tvUsername);
//        tvPassword = findViewById(R.id.tvPassword);
        mViewPager = findViewById(R.id.viewPager);
        mBottomNavigationView = findViewById(R.id.bottomNavigationView);
    }

    private void bindingAction() {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        mViewPager.setAdapter(viewPagerAdapter);

        // Load trước 2 page
        mViewPager.setOffscreenPageLimit(2);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        mBottomNavigationView.getMenu().findItem(R.id.homeItem).setChecked(true);
                        break;
                    case 1:
                        mBottomNavigationView.getMenu().findItem(R.id.mychargeItem).setChecked(true);
                        break;
                    case 2:
                        mBottomNavigationView.getMenu().findItem(R.id.searchItem).setChecked(true);
                        break;
                    case 3:
                        mBottomNavigationView.getMenu().findItem(R.id.paymentItem).setChecked(true);
                        break;
                    case 4:
                        mBottomNavigationView.getMenu().findItem(R.id.settingItem).setChecked(true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mBottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.homeItem:
                        mViewPager.setCurrentItem(0);
                        break;
                    case R.id.mychargeItem:
                        mViewPager.setCurrentItem(1);
                        break;
                    case R.id.searchItem:
                        mViewPager.setCurrentItem(2);
                        break;
                    case R.id.paymentItem:
                        mViewPager.setCurrentItem(3);
                        break;
                    case R.id.settingItem:
                        mViewPager.setCurrentItem(4);
                        break;
                }
                return true;
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        bindingView();
        onReceiveIntent();
        sendDataToSettingFragment();
        bindingAction();
    }

    private void sendDataToSettingFragment() {
        getSupportFragmentManager().beginTransaction().replace(R.id.viewPager, SettingFragment.getInstance(mAccount)).commit();
    }

    private void onReceiveIntent() {
        Intent intentReceive = getIntent();
        mAccount = (Account) intentReceive.getSerializableExtra("obj_account");

//        if (mAccount != null) {
//            tvUsername.setText(mAccount.getUsername());
//            tvPassword.setText(mAccount.getPassword());
//        }
    }

}