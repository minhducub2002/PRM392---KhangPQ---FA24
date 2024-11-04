package com.ducnm.tramsacxedien.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class PersonInformation extends AppCompatActivity {

    private EditText edtName;
    private EditText edtPhone;
    private EditText edtEmail;
    private EditText edtAddress;
    private EditText edtVehicle;
    private FirebaseUser mUser;

    private Button btnLuu;
    private Dialog dialog;
    private ImageButton btnBack;

    private void bindingView() {
        edtAddress = findViewById(R.id.et_address);
        edtEmail = findViewById(R.id.et_email);
        edtPhone = findViewById(R.id.et_phone);
        edtName = findViewById(R.id.et_name);
        edtVehicle = findViewById(R.id.et_vehicle);
        btnLuu = findViewById(R.id.btn_Luu);
        btnBack = findViewById(R.id.btn_information_back);

        mUser = FirebaseAuth.getInstance().getCurrentUser();
        dialog = new Dialog(this);
        Log.d("DucNM_Log", "Views bound successfully.");
    }

    private void bindingAction() {
        btnLuu.setOnClickListener(this::onBtnLuuClick);
        btnBack.setOnClickListener(this::onBtnBackClick);
    }

    private void onBtnBackClick(View view) {
        finish();
    }

    private void onBtnLuuClick(View view) {
        setUpDialog(); // Move dialog setup to here
        dialog.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_person_information);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.personInformation), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        bindingView();
        setUserInformation();
        bindingAction();
    }

    private void setUserInformation() {
        if (mUser == null) {
            return;
        }
        edtName.setText(mUser.getDisplayName());
        edtEmail.setText(mUser.getEmail());
        edtPhone.setText(mUser.getPhoneNumber());
        edtAddress.setText("Hà Nội");
        edtVehicle.setText("Lamborghini");
    }

    private void setUpDialog() {
        dialog.setContentView(R.layout.dialog_confirm);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        Button btnCancel = dialog.findViewById(R.id.btnCancel);
        Button btnSave = dialog.findViewById(R.id.btnSave);

        btnCancel.setOnClickListener(v -> dialog.dismiss());

        btnSave.setOnClickListener(v -> {
            savePersonalInformation(); // Call method to save information
            dialog.dismiss(); // Close dialog
        });
    }

    private void savePersonalInformation() {
        // Implement save logic here (e.g., validate input, save to database, etc.)
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(edtName.getText().toString())
                .build();

        user.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d("DucNM_Debug", "User profile updated.");
                        }
                    }
                });

        String newEmail = edtEmail.getText().toString().trim();
        String oldEmail = mUser.getEmail();

        if (newEmail.equals(oldEmail)) {
            Toast.makeText(this, "Thông tin đã được lưu!", Toast.LENGTH_SHORT).show();
            return;
        }
        else {
            user.updateEmail(newEmail)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Log.d("DucNM_Debug", "User email address updated.");
                            } else {
                                Log.w("DucNM_Debug", "User email address update failed.", task.getException());
                            }
                        }
                    });
        }
        Toast.makeText(this, "Thông tin đã được lưu!", Toast.LENGTH_SHORT).show();
    }
}
