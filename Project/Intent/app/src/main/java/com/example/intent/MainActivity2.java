package com.example.intent;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity2 extends AppCompatActivity {

    private TextView showData;
    private Button btnSave;

    private void bindingView(){
        showData = findViewById(R.id.edtData);
        btnSave = findViewById(R.id.btnSave);
    }

    private void bindingAction() {
        btnSave.setOnClickListener(this::onBtnSaveClicked);
    }

    private void onBtnSaveClicked(View view) {
        String data = showData.getText().toString();
        Intent i = new Intent();
        i.putExtra("data", data);
        //startActivity(i);
        setResult(Constant.RESULT_CODE_OK, i);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        bindingView();
        bindingAction();
        onReceiveIntent();
    }

    private void onReceiveIntent(){
        Intent intent = getIntent();
        String data = intent.getStringExtra("data");
        int age = intent.getIntExtra("age", 0);
        showData.setText(data + " " + age);
    }
}