package com.ducnm.btvn_sharedpreference;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LoginActivity extends AppCompatActivity {

    private EditText edtAccount;
    private EditText edtPassword;
    private Button btnLogin;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private ActivityResultLauncher launcherActivity;

    private void bindingView() {
        edtAccount = findViewById(R.id.edtAccount);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        launcherActivity = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), this::onResultLauncherCallback);
    }

    private void onResultLauncherCallback(ActivityResult activityResult) {

    }

    private void bindingAction() {
        btnLogin.setOnClickListener(this::onBtnLoginClick);
    }

    private void onBtnLoginClick(View view) {
        String account = edtAccount.getText().toString();
        String password = edtPassword.getText().toString();
        editor.putString("account", account);
        editor.putString("password", password);
        Toast.makeText(this, editor.commit() ? "Success" : "Failed", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(this, HomeActivity.class);
        launcherActivity.launch(i);
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
        bindingView();
        bindingAction();
        onLoad();
    }

    private void onLoad() {
        String account = sharedPreferences.getString("account", "");
        String password = sharedPreferences.getString("password", "");

        if (account.length() > 0 && password.length() > 0) {
            Intent i = new Intent(this, HomeActivity.class);
            launcherActivity.launch(i);
        }
    }
}