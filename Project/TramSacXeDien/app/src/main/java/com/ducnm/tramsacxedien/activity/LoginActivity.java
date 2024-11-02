package com.ducnm.tramsacxedien.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.ducnm.tramsacxedien.R;
import com.ducnm.tramsacxedien.database.AccountDatabase;
import com.ducnm.tramsacxedien.object.Account;

import java.util.List;
import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    private TextView tvSignup;
    private Button btnLogin;
    ;
    private CheckBox checkBoxSaveAccount;
    private EditText edtUsernameLogin;
    private EditText edtPasswordLogin;
    private TextView tvForgotAccount;
    private ActivityResultLauncher launcherHomeActivity;

    private void bindingView() {
        tvSignup = findViewById(R.id.tvSignup);
        btnLogin = findViewById(R.id.btnLogin);
        checkBoxSaveAccount = findViewById(R.id.checkBoxSaveAccount);
        edtUsernameLogin = findViewById(R.id.usernameLogin);
        edtPasswordLogin = findViewById(R.id.passwordLogin);
        tvForgotAccount = findViewById(R.id.tvForgotAccount);
        launcherHomeActivity = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), this::onMainActivityCallBack);
    }

    private void onMainActivityCallBack(ActivityResult activityResult) {

    }

    private void bindingAction() {
        btnLogin.setOnClickListener(this::onBtnLoginClick);
        tvForgotAccount.setOnClickListener(this::onTxForgotAccountClick);
        tvSignup.setOnClickListener(this::onTvSignupClick);
    }

    private void onBtnLoginClick(View view) {
        String username = edtUsernameLogin.getText().toString();
        String password = edtPasswordLogin.getText().toString();
        if (username.isEmpty()) {
            Toast.makeText(this, "Tài khoản không được để trống", Toast.LENGTH_SHORT).show();
            return;
        }
        if (password.isEmpty()) {
            Toast.makeText(this, "Mật khẩu không được để trống", Toast.LENGTH_SHORT).show();
            return;
        }
        Account account = new Account(username, password);
        if (isAccountExist(account)) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("obj_account", account);
            launcherHomeActivity.launch(intent);
            hideSoftKeyboard();
        } else {
            Toast.makeText(this, "Tài khoản hoặc mật khẩu không đúng!", Toast.LENGTH_SHORT).show();
            edtUsernameLogin.setText("");
            edtPasswordLogin.setText("");
            hideSoftKeyboard();
        }

    }

    private void onTxForgotAccountClick(View view) {

    }

    private void onTvSignupClick(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        launcherHomeActivity.launch(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.login), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        bindingView();
        bindingAction();
    }

    public void hideSoftKeyboard() {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(Objects.requireNonNull(getCurrentFocus()).getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean isAccountExist(@NonNull Account account) {
        List<Account> accountList = AccountDatabase.getInstance(this).accountDao().checkAccount(account.getUsername());
        if (accountList == null && accountList.isEmpty()) {
            return false;
        } else {
            return accountList.get(0).getPassword().equals(account.getPassword());
        }
    }


}