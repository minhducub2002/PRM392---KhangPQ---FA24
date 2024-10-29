package com.fpt.khangpq.se1726fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

public class MainActivity extends AppCompatActivity {
    private Button btnRed;
    private Button btnBlue;
    private Button btnGreen;
    private Button btnSendData;
    private EditText edtSendData;
    private GreenFragment greenFragment;

    public void setEdtSendDataText(String text) {
        edtSendData.setText(text);
    }

    private void bindingView() {
        btnRed = findViewById(R.id.btnRed);
        btnBlue = findViewById(R.id.btnBlue);
        btnGreen = findViewById(R.id.btnGreen);
        btnSendData = findViewById(R.id.btnSendData);
        edtSendData = findViewById(R.id.edtSendData);
        if (greenFragment == null)
            greenFragment = new GreenFragment();
    }

    private void bindingAction() {
        btnRed.setOnClickListener(this::onBtnRedClick);
        btnBlue.setOnClickListener(this::onBtnBlueClick);
        btnGreen.setOnClickListener(this::onBtnGreenClick);
        btnSendData.setOnClickListener(this::onBtnSendDataClick);
        greenFragment.setOnBtnShowDataClickListener(this::setEdtSendDataText);
    }

    private void onBtnSendDataClick(View view) {
        String data = edtSendData.getText().toString();
        greenFragment.setTvShowData(data);
    }

    private void onBtnGreenClick(View view) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainerView, greenFragment)
                .addToBackStack(null)
                .commit();
    }

    private void onBtnBlueClick(View view) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainerView, new BlueFragment())
                .addToBackStack(null)

                .commit();
    }

    private void onBtnRedClick(View view) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainerView, new RedFragment())
                .addToBackStack(null)

                .commit();
    }

    @Override
    public void onAttachFragment(@NonNull Fragment fragment) {
        super.onAttachFragment(fragment);
        if (fragment instanceof GreenFragment) {
            greenFragment = (GreenFragment) fragment;
        }

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
        bindingAction();
    }
}