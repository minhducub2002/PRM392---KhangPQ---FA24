package com.ducnm.tramsacxedien.activity;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;
import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {

    private Button btnSignUp;
    private EditText edtUsernameSignUp;
    private EditText edtPasswordSignUp;
    private EditText edtRePasswordSignUp;
    private ActivityResultLauncher launcherHomeActivity;

    private Dialog dialog;
    private ProgressDialog progressDialog;
    private Button btnDialogConfirm;

    private FirebaseAuth mAuth;

    private void bindingView() {
        edtUsernameSignUp = findViewById(R.id.usernameSignUp);
        edtPasswordSignUp = findViewById(R.id.passwordSignUp);
        edtRePasswordSignUp = findViewById(R.id.rePasswordSignUp);
        btnSignUp = findViewById(R.id.btnSignUp);
        btnDialogConfirm = findViewById(R.id.btnDialogConfirm);
        launcherHomeActivity = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                this::onRegisterActivityCallBack);

        mAuth = FirebaseAuth.getInstance();

        dialog = new Dialog(this);
        progressDialog = new ProgressDialog(this);
    }

    private void onRegisterActivityCallBack(ActivityResult activityResult) {

    }

    private void bindingAction() {
        btnDialogConfirm.setOnClickListener(this::onBtnDialogConfirmClick);
        btnSignUp.setOnClickListener(this::onBtnSignUpClick);
    }

    private void onBtnDialogConfirmClick(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        launcherHomeActivity.launch(intent);
        dialog.dismiss();
    }

    private void onBtnSignUpClick(View view) {
        String email = edtUsernameSignUp.getText().toString();
        String password = edtPasswordSignUp.getText().toString();
        String rePassword = edtRePasswordSignUp.getText().toString();
        if (email.isEmpty()) {
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
        if (!isValidEmail(email)) {
            // Email không hợp lệ
            Toast.makeText(this, "Email không hợp lệ", Toast.LENGTH_SHORT).show();
            return;
        }
        if (password.length() < 6) {
            Toast.makeText(this, "Mật khẩu phải có ít nhất 6 ký tự", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!password.equals(rePassword)) {
            Toast.makeText(this, "Mật khẩu không trùng khớp", Toast.LENGTH_SHORT).show();
            return;
        }
//        Account account = new Account(username, password);
//        if (isAccountExist(account)) {
//            Toast.makeText(this, "Tài khoản đã tồn tại!", Toast.LENGTH_SHORT).show();
//            return;
//        }
//        AccountDatabase.getInstance(this).accountDao().insertAccount(account);
        progressDialog.show();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            progressDialog.dismiss();
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("DucNM_Debug", "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(RegisterActivity.this, "Đăng ký thành công!",
                                    Toast.LENGTH_SHORT).show();
                            edtUsernameSignUp.setText("");
                            edtPasswordSignUp.setText("");
                            edtRePasswordSignUp.setText("");
                            hideSoftKeyboard();
                            dialog.show();
                        } else {
                            // If sign in fails, display a message to the user.
                            progressDialog.dismiss();
                            Exception exception = task.getException();
                            Log.w("DucNM_Debug", "createUserWithEmail:failure", exception);
                            Toast.makeText(RegisterActivity.this, exception.getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
//        Toast.makeText(this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
//        edtUsernameSignUp.setText("");
//        edtPasswordSignUp.setText("");
//        edtRePasswordSignUp.setText("");
//        hideSoftKeyboard();
//        dialog.show();
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

    public boolean isValidEmail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}