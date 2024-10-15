package com.DucNM.myapplication;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CartScreen extends AppCompatActivity {

    private List<Product> listProduct = new ArrayList<>();
    private RecyclerView recyclerView;
    private CartAdapter adapter;

    private void bindingView() {
        recyclerView = findViewById(R.id.recyclerView);
    }

    private void bindingAction() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cart_screen);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        bindingView();
        bindingAction();
        onReceiveIntent();
        initRecyclerView();
    }

    private void onReceiveIntent() {
        Intent intent = getIntent();
        String data = intent.getStringExtra("Product Name");
        listProduct.add(new Product(data, data + " nè hihihi"));
        initRecyclerView();
    }

    private void initRecyclerView() {
        RecyclerView.Adapter adapter = new CartAdapter(listProduct);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
    }
}