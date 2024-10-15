package com.example.styleandtheme;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    TextView tvShowData;
    Button btnChange;
    Button btnChange2;

    private void bindingView() {
        tvShowData = findViewById(R.id.tvShowData);
        btnChange = findViewById(R.id.btnChange);
    }

    private void bindingAction() {
        btnChange.setOnClickListener(this::onBtnChangeClick);

//        btnChange.setOnLongClickListener(v -> {
//            return onBtnChangeLongClick(v);
//        });
    }

//    private void onBtnChangeLongClick(View v) {
//        @SuppressLint("ShowToast") Toast toast = Toast.makeText(this, "Hello World", Toast.LENGTH_SHORT);
//    }

    private void onBtnChangeClick(View v) {
        tvShowData.setText("Hello World");
        tvShowData.setTextSize(30);
        Log.d("DucNM", "onBtnChangeClick");
        Toast toast = Toast.makeText(this, "Hello World", Toast.LENGTH_LONG);
        toast.show();
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

//    @Override
//    public boolean onKey(View view, int i, KeyEvent keyEvent) {
//        if (v.getId() == R.id.tvShowData) {
//
//        }
//        return false;
//    }
}