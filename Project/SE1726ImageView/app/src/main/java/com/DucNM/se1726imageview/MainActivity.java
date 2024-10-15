package com.DucNM.se1726imageview;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;

public class MainActivity extends AppCompatActivity {

    private Button btnLoad;
    private Button btnChange;
    private ImageView imgView;
    private EditText edtUrl;

    private void bindingView(){
        btnLoad = findViewById(R.id.btnLoad);
        btnChange = findViewById(R.id.btnChange);
        imgView = findViewById(R.id.imgView);
        edtUrl = findViewById(R.id.edtUrl);
    }
    private void bindingAction(){
        btnLoad.setOnClickListener(this::onBtnLoadClick);
    }

    private void onBtnLoadClick(View view) {
        String url = edtUrl.getText().toString();
        Glide.with(this)
                .load(url)
                .error(R.drawable.error)
                .placeholder(R.drawable.loading)
                .into(imgView);
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
}