package com.example.btvn_2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class HomeActivity extends AppCompatActivity {

    private TextView showNameData;
    private Button btnFinishApp;

    private void bindingViews() {
        showNameData = findViewById(R.id.showNameData);
        btnFinishApp = findViewById(R.id.btnFinishApp);
    }

    private void bindingAction() {
        btnFinishApp.setOnClickListener(this::onBtnFinishAppClick);
    }

    private void onBtnFinishAppClick(View view) {
        Intent i = new Intent();
        setResult(Constants.RESULT_CODE_OK, i);
        finish();
        //System.exit(0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        bindingViews();
        bindingAction();
        onReceiveIntent();
    }

    private void onReceiveIntent() {
        String name = getIntent().getStringExtra("name");
        showNameData.setText(name);
    }

    @Override
    protected void onPause() {
        super.onPause();
        clearNameData();
    }

    private void clearNameData() {
        showNameData.setText("");
    }
}