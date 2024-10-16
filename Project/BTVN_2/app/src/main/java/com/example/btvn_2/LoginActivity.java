package com.example.btvn_2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LoginActivity extends AppCompatActivity {

    private ActivityResultLauncher launcherHomeActivity;
    private EditText edtNameData;
    private Button loginButton;


    private void bindingViews() {
        edtNameData = findViewById(R.id.edtNameData);
        loginButton = findViewById(R.id.loginButton);
        launcherHomeActivity =registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), this::onHomeActivityCallBack);
    }

    private void onHomeActivityCallBack(ActivityResult activityResult) {
        if(activityResult.getResultCode() == Constants.RESULT_CODE_OK){
            finish();
        }
    }

    private void bindingAction() {
        loginButton.setOnClickListener(this::onLoginButtonClick);
    }

    private void onLoginButtonClick(View view) {
        Intent intent = new Intent(this, HomeActivity.class);
        intent.putExtra("name", edtNameData.getText().toString());
        launcherHomeActivity.launch(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        bindingViews();
        bindingAction();
    }

    @Override
    protected void onPause() {
        super.onPause();
        clearNameData();
    }

    private void clearNameData() {
        edtNameData.setText("");
    }
}