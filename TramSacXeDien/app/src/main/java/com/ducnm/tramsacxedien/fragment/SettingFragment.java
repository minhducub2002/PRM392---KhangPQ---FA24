package com.ducnm.tramsacxedien.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.ducnm.tramsacxedien.R;
import com.ducnm.tramsacxedien.activity.ChangePassword;
import com.ducnm.tramsacxedien.activity.GuideActivity;
import com.ducnm.tramsacxedien.activity.HistoryCharge;
import com.ducnm.tramsacxedien.activity.LoginActivity;
import com.ducnm.tramsacxedien.activity.MainActivity;
import com.ducnm.tramsacxedien.activity.NotificationActivity;
import com.ducnm.tramsacxedien.activity.PersonInformation;
import com.ducnm.tramsacxedien.activity.PrivacyPolicy;
import com.ducnm.tramsacxedien.activity.SettingActivity;
import com.ducnm.tramsacxedien.object.Account;
import com.google.firebase.auth.FirebaseAuth;


public class SettingFragment extends Fragment {

    private View mView;
    private MainActivity mActivity;

    private Account mAccount;
    private TextView tvSettingFragment;

    private TextView tvUserInfor;
    private TextView tvHistoryCharge;
    private TextView tvSettingg;
    private TextView tvNotification;
    private TextView tvChangePassword;
    private TextView tvGuide;
    private TextView tvPrivacyPolicy;
    private Button btnDangXuat;

    private FirebaseAuth mAuth;


    public SettingFragment() {
        // Required empty public constructor
    }

    private void bindingView() {
        mAuth = FirebaseAuth.getInstance();
        tvSettingFragment = mView.findViewById(R.id.tvUsername);
        tvUserInfor = mView.findViewById(R.id.tvUserInfor);
        tvHistoryCharge = mView.findViewById(R.id.tvHistoryCharge);
        tvSettingg = mView.findViewById(R.id.tvSettingg);
        tvNotification = mView.findViewById(R.id.tvNotification);
        tvChangePassword = mView.findViewById(R.id.tvChangePassword);
        tvGuide = mView.findViewById(R.id.tvGuide);
        tvPrivacyPolicy = mView.findViewById(R.id.tvPrivacyPolicy);
        btnDangXuat = mView.findViewById(R.id.btnDangXuat);

    }

    private void bindingAction() {

        tvUserInfor.setOnClickListener(this::onTvUserInforClick);
        tvSettingFragment.setText("Xin chào " + mAuth.getCurrentUser().getEmail());
        tvHistoryCharge.setOnClickListener(this::onTvHistoryChargeClick);
        tvSettingg.setOnClickListener(this::onTvSettinggClick);
        tvNotification.setOnClickListener(this::onTvNotificationClick);
        tvChangePassword.setOnClickListener(this::onTvChangePasswordClick);
        tvGuide.setOnClickListener(this::onTvGuideClick);
        tvPrivacyPolicy.setOnClickListener(this::onTvPrivacyPolicyClick);
        btnDangXuat.setOnClickListener(this::onBtnDangXuatClick);
    }

    private void onBtnDangXuatClick(View view) {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(mActivity, LoginActivity.class);
        startActivity(intent);
    }

    private void onTvUserInforClick(View view) {
        Intent intent = new Intent(mActivity, PersonInformation.class);
        startActivity(intent);
    }
    private void onTvHistoryChargeClick(View view) {
        Intent intent = new Intent(mActivity, HistoryCharge.class);
        startActivity(intent);
    }
    private void onTvSettinggClick(View view) {
        Intent intent = new Intent(mActivity, SettingActivity.class);
        startActivity(intent);
    }
    private void onTvNotificationClick(View view) {
        Intent intent = new Intent(mActivity, NotificationActivity.class);
        startActivity(intent);
    }
    private void onTvChangePasswordClick(View view) {
        Intent intent = new Intent(mActivity, ChangePassword.class);
        startActivity(intent);
    }
    private void onTvGuideClick(View view) {
        Intent intent = new Intent(mActivity, GuideActivity.class);
        startActivity(intent);
    }
    private void onTvPrivacyPolicyClick(View view) {
        Intent intent = new Intent(mActivity, PrivacyPolicy.class);
        startActivity(intent);
    }



    public static SettingFragment getInstance(Account account) {
        SettingFragment fragment = new SettingFragment();
        Bundle args = new Bundle();
        args.putSerializable("key_obj_account", account);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("DucNM_Log", "SettingFragment onCreate");
    }

    private void onReceiveBundle() {
        mAccount = (Account) getArguments().get("key_obj_account");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("DucNM_Log", "SettingFragment onCreateView");
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_setting, container, false);
        mActivity = (MainActivity) getActivity();
        bindingView();
        bindingAction();
        return mView;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Log.d("DucNM_Log", "SettingFragment onAttach");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("DucNM_Log", "SettingFragment onResume");
    }

}