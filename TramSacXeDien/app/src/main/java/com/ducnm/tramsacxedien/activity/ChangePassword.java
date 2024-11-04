package com.ducnm.tramsacxedien.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.ducnm.tramsacxedien.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ChangePassword extends AppCompatActivity {

    private EditText etOldPassword;
    private EditText etNewPassword;
    private EditText etConfirmPassword;
    private Button btnChangePassword;
    private ImageView ivBack;

    private Dialog dialog;
    private ProgressDialog progressDialog;

    // Liên kết các thành phần giao diện
    private void bindingView() {
        etOldPassword = findViewById(R.id.edtOldPassword);
        etNewPassword = findViewById(R.id.edtNewPassword);
        etConfirmPassword = findViewById(R.id.edtConfirmPassword);
        btnChangePassword = findViewById(R.id.btnChangePassword);
        ivBack = findViewById(R.id.change_pwd_back);
        dialog = new Dialog(this);
        progressDialog = new ProgressDialog(this);
    }

    // Thiết lập các hành động
    private void bindingAction() {
        btnChangePassword.setOnClickListener(this::onBtnChangePasswordClick);
        ivBack.setOnClickListener(this::onIvBackClick);
    }

    private void onIvBackClick(View view) {
        finish();
    }

    // Xử lý sự kiện khi nút thay đổi mật khẩu được nhấn
    private void onBtnChangePasswordClick(View view) {
        setUpDialog();
        dialog.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        // Thiết lập padding để tránh chèn các thành phần hệ thống
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        bindingView();
        bindingAction();
    }

    // Thiết lập dialog xác nhận khi thay đổi mật khẩu
    private void setUpDialog() {
        dialog.setContentView(R.layout.dialog_confirm);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        Button btnCancel = dialog.findViewById(R.id.btnCancel);
        Button btnSave = dialog.findViewById(R.id.btnSave);

        btnCancel.setOnClickListener(v -> dialog.dismiss());

        btnSave.setOnClickListener(v -> {
            savePassword();
        });
    }

    // Phương thức lưu mật khẩu mới
    private void savePassword() {
        dialog.dismiss();
        String oldPassword = etOldPassword.getText().toString().trim();
        String newPassword = etNewPassword.getText().toString().trim();
        String confirmPassword = etConfirmPassword.getText().toString().trim();

        if (validatePassword(oldPassword, newPassword, confirmPassword)) {
            progressDialog.show();
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            assert user != null;
            user.updatePassword(newPassword)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                progressDialog.dismiss();
                                finish();
                                Log.d("DucNM_Debug", "User password updated.");
                            } else {
                                progressDialog.dismiss();
                                Log.w("DucNM_Debug", "User password update failed.", task.getException());
                            }
                        }
                    });
            Toast.makeText(this, "Mật khẩu đã được thay đổi!", Toast.LENGTH_SHORT).show();
        }
    }

    // Kiểm tra tính hợp lệ của mật khẩu mới
    private boolean validatePassword(String oldPassword, String newPassword, String confirmPassword) {
        if (oldPassword.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập mật khẩu cũ", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (newPassword.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập mật khẩu mới", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!newPassword.equals(confirmPassword)) {
            Toast.makeText(this, "Mật khẩu xác nhận không khớp", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (newPassword.length() < 6) {
            Toast.makeText(this, "Mật khẩu phải có ít nhất 6 ký tự", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
