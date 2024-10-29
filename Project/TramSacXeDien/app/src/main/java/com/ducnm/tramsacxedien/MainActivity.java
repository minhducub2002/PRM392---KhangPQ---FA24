package com.ducnm.tramsacxedien;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private TextView tvSignup;
    private Button btnLogin;;
    private CheckBox checkBoxSaveAccount;
    private EditText usernameLogin;
    private EditText passwordLogin;
    private TextView txForgotAccount;
    private ActivityResultLauncher launcherHomeActivity;

    private void bindingView() {
        tvSignup = findViewById(R.id.tvSignup);
        btnLogin = findViewById(R.id.btnLogin);
        checkBoxSaveAccount = findViewById(R.id.checkBoxSaveAccount);
        usernameLogin = findViewById(R.id.usernameLogin);
        passwordLogin = findViewById(R.id.passwordLogin);
        txForgotAccount = findViewById(R.id.txForgotAccount);
        launcherHomeActivity =registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), this::onMainActivityCallBack);
    }

    private void onMainActivityCallBack(ActivityResult activityResult) {

    }

    private void bindingAction() {
        tvSignup.setOnClickListener(this::onTvSignupClick);
    }

    private void onTvSignupClick(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        launcherHomeActivity.launch(intent);
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