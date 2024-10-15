package com.ducnm.btvn_service;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private Button btnStartService;
    private Button btnStopService;

    private void bindingView() {
        btnStartService = findViewById(R.id.btnStartService);
        btnStopService = findViewById(R.id.btnStopService);
    }
    private void bindingAction() {
        btnStartService.setOnClickListener(this::onBtnStartServiceClicked);
        btnStopService.setOnClickListener(this::onBtnStopServiceClicked);
    }

    private void onBtnStartServiceClicked(View view) {
        Intent intent = new Intent(MainActivity.this, PlayMusicService.class);
        startService(intent);
    }

    private void onBtnStopServiceClicked(View view) {
        Intent intent = new Intent(MainActivity.this, PlayMusicService.class);
        stopService(intent);
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