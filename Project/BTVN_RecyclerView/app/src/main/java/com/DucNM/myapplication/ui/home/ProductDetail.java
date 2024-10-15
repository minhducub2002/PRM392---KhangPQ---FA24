package com.DucNM.myapplication.ui.home;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.DucNM.myapplication.CartScreen;
import com.DucNM.myapplication.R;
import com.DucNM.myapplication.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationView;

public class ProductDetail extends AppCompatActivity {

    private ImageView imgViewProduct;
    private TextView txtViewProduct;
    private Button btnAddCart;
    private String data;

    private void bindingView() {
        imgViewProduct = findViewById(R.id.imgViewProduct);
        txtViewProduct = findViewById(R.id.txtViewProduct);
        btnAddCart = findViewById(R.id.btnAddCart);
    }

    private void bindingAction() {
        btnAddCart.setOnClickListener(this::onBtnAddCartClick);
    }

    private void onBtnAddCartClick(View view) {
        Intent intent = new Intent(view.getContext(), CartScreen.class);
        intent.putExtra("Product Name", data);
        //PendingIntent pi = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_product_detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        bindingView();
        bindingAction();
        onReceiveIntent();
    }

    private void onReceiveIntent() {
        Intent intent = getIntent();
        data = intent.getStringExtra("categoryName");
        int imageResId = intent.getIntExtra("imageResId", 0);
        imgViewProduct.setImageResource(imageResId);
        txtViewProduct.setText(data);
    }
}