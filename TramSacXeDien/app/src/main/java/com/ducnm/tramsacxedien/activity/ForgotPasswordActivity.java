package com.ducnm.tramsacxedien.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.ducnm.tramsacxedien.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity {

    private EditText edtEmail;
    private Button btnSendCode;
    private ProgressDialog progressDialog;

    private void bindingView() {
        edtEmail = findViewById(R.id.edtEmail);
        btnSendCode = findViewById(R.id.btnSendCode);
        progressDialog = new ProgressDialog(this);
    }

    private void bindingAction() {
        btnSendCode.setOnClickListener(this::onBtnSendCodeClick);
    }

    private void onBtnSendCodeClick(View view) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        String emailAddress = edtEmail.getText().toString();
        if (!isValidEmail(emailAddress)) {
            Toast.makeText(this, "Email không hợp lệ", Toast.LENGTH_SHORT).show();
            return;
        }
        progressDialog.show();
        auth.sendPasswordResetEmail(emailAddress)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            progressDialog.dismiss();
                            edtEmail.setText("");
                            hideSoftKeyboard();
                            Toast.makeText(ForgotPasswordActivity.this, "Email đã được gửi", Toast.LENGTH_SHORT).show();
                            Log.d("DucNM_Log", "Email sent.");
                        } else {
                            progressDialog.dismiss();
                            edtEmail.setText("");
                            hideSoftKeyboard();
                            Toast.makeText(ForgotPasswordActivity.this, "Không thể gửi email", Toast.LENGTH_SHORT).show();
                            Log.w("DucNM_Log", "Email not sent.", task.getException());
                        }
                    }
                });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_forgot_password);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.forgot), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        bindingView();
        bindingAction();
    }

    public boolean isValidEmail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
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
}