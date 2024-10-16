package com.fpt.khangpq.se1726intent;

import android.app.LauncherActivity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private Button btnImplicit;
    private Button btnExplicit;
    private EditText edtData;
    private ActivityResultLauncher launcherActivity;

    private void bindingView() {
        btnImplicit = findViewById(R.id.btnImplicit);
        btnExplicit = findViewById(R.id.btnExplicit);
        edtData = findViewById(R.id.edtData);
        launcherActivity = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                this::onResultLauncherCallback);
    }

    private void onResultLauncherCallback(ActivityResult activityResult) {
        if (activityResult.getResultCode() == Constant.RESULT_CODE_OK) {
            String data2 = activityResult.getData().getStringExtra("data");
            edtData.setText(data2);
        } else {
            Toast.makeText(this, "LOI ROI", Toast.LENGTH_SHORT).show();
        }
    }

    private void bindingAction() {
        btnExplicit.setOnClickListener(this::onBtnExplicitClick);
        btnImplicit.setOnClickListener(this::onBtnImplicitClick);

    }

    private void onBtnImplicitClick(View view) {
        String url = edtData.getText().toString();
        Uri uri = Uri.parse(url);
        Intent i = new Intent(Intent.ACTION_CALL, uri);
        startActivity(i);
    }

    private static final int REQUEST_CODE_FOR_DOING_STH = 2109;

    private void onBtnExplicitClick(View view) {
        Intent i = new Intent(this, MainActivity2.class);
        i.putExtra("data", edtData.getText().toString());
        i.putExtra("age", 19);
//        startActivity(i);
//        startActivityForResult(i, REQUEST_CODE_FOR_DOING_STH);
        launcherActivity.launch(i);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE_FOR_DOING_STH:
                if (resultCode == Constant.RESULT_CODE_OK) {
                    String data2 = data.getStringExtra("data");
                    edtData.setText(data2);
                } else {
                    Toast.makeText(this, "LOI ROI", Toast.LENGTH_SHORT).show();
                }
                break;
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
//        onReceiveIntent();
    }

    private void onReceiveIntent() {
        Intent i = getIntent();
        String data = i.getStringExtra("data");
        edtData.setText(data);

    }
}