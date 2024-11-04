package com.ducnm.tramsacxedien.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;
import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    private TextView tvSignup;
    private Button btnLogin;
    private CheckBox checkBoxSaveAccount;
    private EditText edtUsernameLogin;
    private EditText edtPasswordLogin;
    private TextView tvForgotAccount;
    private ActivityResultLauncher<Intent> launcherHomeActivity;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private ProgressDialog progressDialog;

    private FirebaseAuth mAuth;

    private void bindingView() {
        tvSignup = findViewById(R.id.tvSignup);
        btnLogin = findViewById(R.id.btnLogin);
        checkBoxSaveAccount = findViewById(R.id.checkBoxSaveAccount);
        edtUsernameLogin = findViewById(R.id.usernameLogin);
        edtPasswordLogin = findViewById(R.id.passwordLogin);
        tvForgotAccount = findViewById(R.id.tvForgotAccount);

        mAuth = FirebaseAuth.getInstance();
        sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        progressDialog = new ProgressDialog(this);

        launcherHomeActivity = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), this::onMainActivityCallBack);
    }

    private void onMainActivityCallBack(ActivityResult activityResult) {
        // Xử lý callback khi trở về từ MainActivity nếu cần thiết
    }

    private void bindingAction() {
        tvForgotAccount.setOnClickListener(this::onTxForgotAccountClick);
        tvSignup.setOnClickListener(this::onTvSignupClick);
        btnLogin.setOnClickListener(this::onBtnLoginClick);
    }


    private void onBtnLoginClick(View view) {
        Log.d("DucNM_Log", "Đang thực hiện đăng nhập..."); // Thêm log khi bắt đầu quá trình đăng nhập

        String email = edtUsernameLogin.getText().toString();
        String password = edtPasswordLogin.getText().toString();

        // Kiểm tra thông tin đăng nhập
        if (email.isEmpty()) {
            Toast.makeText(this, "Tài khoản không được để trống", Toast.LENGTH_SHORT).show();
            Log.d("DucNM_Log", "Tài khoản không được để trống.");
            return;
        }
        if (password.isEmpty()) {
            Toast.makeText(this, "Mật khẩu không được để trống", Toast.LENGTH_SHORT).show();
            Log.d("DucNM_Log", "Mật khẩu không được để trống.");
            return;
        }
        if (!isValidEmail(email)) {
            // Email không hợp lệ
            Toast.makeText(this, "Email không hợp lệ", Toast.LENGTH_SHORT).show();
            return;
        }
        progressDialog.show();
        if (isCheckBoxChecked()) {
            editor.putString("saved_email", email);
            editor.putString("saved_password", password);
            editor.putBoolean("save_account", true);
            editor.apply();

        } else {
            editor.remove("saved_email");
            editor.remove("saved_password");
            editor.putBoolean("save_account", false);
            editor.apply();
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            progressDialog.dismiss();
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("DucNM_Debug", "signInWithEmail:success");
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            launcherHomeActivity.launch(intent);

                            FirebaseUser user = mAuth.getCurrentUser();
                        } else {
                            progressDialog.dismiss();
                            // If sign in fails, display a message to the user.
                            Log.w("DucNM_Debug", "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Tài khoản hoặc mật khẩu không đúng!",
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }

    private void onTxForgotAccountClick(View view) {
        Intent intent = new Intent(this, ForgotPasswordActivity.class);
        launcherHomeActivity.launch(intent);
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

        boolean isSaved = sharedPreferences.getBoolean("save_account", false);
        if (isSaved) {
            String savedEmail = sharedPreferences.getString("saved_email", null);
            String savedPassword = sharedPreferences.getString("saved_password", null);
            if (savedEmail != null) {
                edtUsernameLogin.setText(savedEmail);
            }

            if (savedPassword != null) {
                edtPasswordLogin.setText(savedPassword);
            }

            checkBoxSaveAccount.setChecked(true);

            edtUsernameLogin.setText(savedEmail);
            edtPasswordLogin.setText(savedPassword);
            checkBoxSaveAccount.setChecked(true);
        }
        bindingAction();
    }

    public void hideSoftKeyboard() {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
            if (getCurrentFocus() != null) {
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            }
        } catch (Exception e) {
            Log.e("DucNM_Log", "Lỗi khi ẩn bàn phím: ", e);
        }
    }

    private boolean isAccountExist(Account account) {
        List<Account> accountList = AccountDatabase.getInstance(this).accountDao().checkAccount(account.getUsername());
        if (accountList == null || accountList.isEmpty()) {
            Log.d("DucNM_Log", "Tài khoản không tồn tại.");
            return false;
        } else {
            boolean isValid = accountList.get(0).getPassword().equals(account.getPassword());
            Log.d("DucNM_Log", "Tài khoản tồn tại, mật khẩu hợp lệ: " + isValid);
            return isValid;
        }
    }

    public boolean isValidEmail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isCheckBoxChecked() {
        return checkBoxSaveAccount.isChecked();
    }
}
