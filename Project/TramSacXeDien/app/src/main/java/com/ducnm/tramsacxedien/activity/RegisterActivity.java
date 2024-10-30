package com.ducnm.tramsacxedien.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
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

import com.ducnm.tramsacxedien.R;
import com.ducnm.tramsacxedien.database.AccountDatabase;
import com.ducnm.tramsacxedien.object.Account;

import java.util.List;
import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {

    private Button btnSignUp;
    private EditText edtUsernameSignUp;
    private EditText edtPasswordSignUp;
    private EditText edtRePasswordSignUp;
    private ActivityResultLauncher launcherHomeActivity;

    private Dialog dialog;
    private Button btnDialogConfirm;

    private void bindingView() {
        edtUsernameSignUp = findViewById(R.id.usernameSignUp);
        edtPasswordSignUp = findViewById(R.id.passwordSignUp);
        edtRePasswordSignUp = findViewById(R.id.rePasswordSignUp);
        btnSignUp = findViewById(R.id.btnSignUp);
        btnDialogConfirm = findViewById(R.id.btnDialogConfirm);
        launcherHomeActivity = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), this::onRegisterActivityCallBack);

        dialog = new Dialog(this);
    }

    private void onRegisterActivityCallBack(ActivityResult activityResult) {

    }

    private void bindingAction() {
        btnSignUp.setOnClickListener(this::onBtnSignUpClick);
        btnDialogConfirm.setOnClickListener(this::onBtnDialogConfirmClick);
    }

    private void onBtnDialogConfirmClick(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        launcherHomeActivity.launch(intent);
        dialog.dismiss();
    }

    private void onBtnSignUpClick(View view) {
        String username = edtUsernameSignUp.getText().toString();
        String password = edtPasswordSignUp.getText().toString();
        String rePassword = edtRePasswordSignUp.getText().toString();
        if (username.isEmpty()) {
            Toast.makeText(this, "Tài khoản không được để trống", Toast.LENGTH_SHORT).show();
            return;
        }
        if (password.isEmpty()) {
            Toast.makeText(this, "Mật khẩu không được để trống", Toast.LENGTH_SHORT).show();
            return;
        }
        if (rePassword.isEmpty()) {
            Toast.makeText(this, "Nhập lại mật khẩu không được để trống", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!password.equals(rePassword)) {
            Toast.makeText(this, "Mật khẩu không trùng khớp", Toast.LENGTH_SHORT).show();
            return;
        }
        Account account = new Account(username, password);
        if (isAccountExist(account)) {
            Toast.makeText(this, "Tài khoản đã tồn tại!", Toast.LENGTH_SHORT).show();
            return;
        }
        AccountDatabase.getInstance(this).accountDao().insertAccount(account);
        Toast.makeText(this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
        edtUsernameSignUp.setText("");
        edtPasswordSignUp.setText("");
        edtRePasswordSignUp.setText("");
        hideSoftKeyboard();
        dialog.show();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.register), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        bindingView();
        setUpDialog();
        bindingAction();
    }

    private void setUpDialog() {
        dialog.setContentView(R.layout.dialog_register_success);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_register_sucess_background);
        btnDialogConfirm = dialog.findViewById(R.id.btnDialogConfirm);
    }

    private boolean isAccountExist(Account account) {
        List<Account> accountList = AccountDatabase.getInstance(this).accountDao().checkAccount(account.getUsername());
        return accountList != null && !accountList.isEmpty();
    }

    public void hideSoftKeyboard() {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(Objects.requireNonNull(getCurrentFocus()).getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}