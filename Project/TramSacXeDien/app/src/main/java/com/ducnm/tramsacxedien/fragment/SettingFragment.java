package com.ducnm.tramsacxedien.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ducnm.tramsacxedien.R;
import com.ducnm.tramsacxedien.activity.MainActivity;
import com.ducnm.tramsacxedien.object.Account;


public class SettingFragment extends Fragment {

    private View mView;
    private MainActivity mActivity;

    private Account mAccount;
    private TextView tvSettingFragment;

    public SettingFragment() {
        // Required empty public constructor
    }

    private void bindingView() {
        tvSettingFragment = mView.findViewById(R.id.tvUsername);
        mAccount = mActivity.getAccount();
    }

    private void bindingAction() {
        tvSettingFragment.setText("Hello " + mAccount.getUsername());
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
//        onReceiveBundle();
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