package com.fpt.khangpq.se1726fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class GreenFragment extends Fragment {

    private Button btnShowData;
    private EditText edtData;
    private TextView tvShowData;

    private void bindingView() {
        btnShowData = getView().findViewById(R.id.btnShowData);
        edtData = getView().findViewById(R.id.edtData);
        tvShowData = getView().findViewById(R.id.tvShowData);
    }

    private void bindingAction() {
        btnShowData.setOnClickListener(this::onBtnShowDataClick);
    }

    public interface OnBtnShowDataClickListener {
        void onClick(String data);
    }

    private OnBtnShowDataClickListener callback;

    public void setOnBtnShowDataClickListener(OnBtnShowDataClickListener callback) {
        this.callback = callback;
    }

    private void onBtnShowDataClick(View view) {
        String data = edtData.getText().toString();
        tvShowData.setText(data);
//        ((MainActivity) getActivity()).setEdtSendDataText(data);
        if (callback != null) {
            callback.onClick(data);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindingView();
        bindingAction();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_green, container, false);
    }

    public void setTvShowData(String data) {
        tvShowData.setText(data);
    }
}