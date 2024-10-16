package com.ducnm.btvn_sqliteopenhelper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.ducnm.btvn_sqliteopenhelper.database.ProductDatabase;

import java.util.Objects;

public class UpdateProductActivity extends AppCompatActivity {

    private EditText edtProductName;
    private EditText edtProductDes;
    private EditText edtProductPrice;
    private Button btnUpdateProduct;

    private Product mProduct;

    private void bindingView() {
        edtProductName = findViewById(R.id.edtProductName);
        edtProductDes = findViewById(R.id.edtProductDes);
        edtProductPrice = findViewById(R.id.edtProductPrice);
        btnUpdateProduct = findViewById(R.id.btnUpdate);
    }

    private void bindingAction() {
        btnUpdateProduct.setOnClickListener(this::onBtnUpdateProductClick);
    }

    private void onBtnUpdateProductClick(View view) {
        updateProduct();
    }

    private void updateProduct() {
        String productName = edtProductName.getText().toString();
        String productDescription = edtProductDes.getText().toString();
        if (productName.isEmpty()) {
            Toast.makeText(this, "Product Name should not be empty!", Toast.LENGTH_SHORT).show();
            return;
        }
        String productPriceStr = edtProductPrice.getText().toString();
        int productPrice;
        if (productPriceStr.isEmpty()) {
            productPrice = 0;
        } else {
            productPrice = Integer.parseInt(productPriceStr);
        }

        mProduct.setProductName(productName);
        mProduct.setProductDescription(productDescription);
        mProduct.setProductPrice(productPrice);

        ProductDatabase.getInstance(this).productDAO().updateProduct(mProduct);

        Toast.makeText(this, "Update product successfully!", Toast.LENGTH_SHORT).show();

        Intent intentResult = new Intent();
        setResult(Constants.RESULT_CODE_OK, intentResult);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_update_product);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        bindingView();
        onReceiveIntent();
        bindingAction();
    }

    private void onReceiveIntent() {
        Intent intentReceive = getIntent();
        mProduct = (Product) intentReceive.getSerializableExtra("obj_product");
        if (mProduct != null) {
            edtProductName.setText(mProduct.getProductName());
            edtProductDes.setText(mProduct.getProductDescription());
            edtProductPrice.setText(String.valueOf(mProduct.getProductPrice().intValue()));
        }
    }
}