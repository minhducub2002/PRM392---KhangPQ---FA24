package com.ducnm.btvn_sqliteopenhelper;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ducnm.btvn_sqliteopenhelper.database.ProductDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private static final int MY_REQUEST_CODE = 10;
    private ActivityResultLauncher launcherHomeActivity;

    private EditText edtProductName;
    private EditText edtProductDes;
    private EditText edtProductPrice;
    private Button btnAddProduct;
    private RecyclerView rcvProduct;
    private TextView tvDeleteAll;

    private ProductAdapter productAdapter;
    private List<Product> mProductList;

    private void bindingView() {
        edtProductName = findViewById(R.id.edtProductName);
        edtProductDes = findViewById(R.id.edtProductDes);
        edtProductPrice = findViewById(R.id.edtProductPrice);
        rcvProduct = findViewById(R.id.rcvProduct);
        btnAddProduct = findViewById(R.id.btnAddProduct);
        tvDeleteAll = findViewById(R.id.tvDeleteAll);
        launcherHomeActivity =registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), this::onHomeActivityCallBack);

    }

    private void onHomeActivityCallBack(ActivityResult activityResult) {
        if(activityResult.getResultCode() == Constants.RESULT_CODE_OK){
            loadData();
            //finish();
        }
    }

    private void bindingAction() {
        btnAddProduct.setOnClickListener(this::onBtnAddProductClick);
        tvDeleteAll.setOnClickListener(this::onTvDeleteAllClick);
    }

    private void onTvDeleteAllClick(View view) {
        clickDeleteAllProduct();
    }

    private void onBtnAddProductClick(View view) {
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

        Product product = new Product(productName, productDescription, productPrice);
        if (isProductExist(product)) {
            Toast.makeText(this, "Product already exists!", Toast.LENGTH_SHORT).show();
            return;
        }

        ProductDatabase.getInstance(this).productDAO().insertProduct(product);
        Toast.makeText(this, "Add product successfully!", Toast.LENGTH_SHORT).show();

        edtProductName.setText("");
        edtProductDes.setText("");
        edtProductPrice.setText("");
        hideSoftKeyboard();

        loadData();

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
        mProductList = new ArrayList<>();
//        productAdapter = new ProductAdapter(productList);
        productAdapter = new ProductAdapter(new ProductAdapter.IClickItemProduct() {
            @Override
            public void updateProduct(Product product) {
                clickUpdateProduct(product);
            }

            @Override
            public void deleteProduct(Product product) {
                clickDeleteProduct(product);
            }
        });
        initRecyclerView();
        bindingAction();
    }

    private void initRecyclerView() {
        mProductList = ProductDatabase.getInstance(this).productDAO().getAllProduct();
        //RecyclerView.Adapter<ProductAdapter.ProductViewHolder> adapter = productAdapter;
        productAdapter.setProductList(mProductList);
        rcvProduct.setAdapter(productAdapter);
        rcvProduct.setLayoutManager(new GridLayoutManager(this, 1));
        Log.d("DucNM_Debug", "initRecyclerView Done!, productList.size() = " + mProductList.size());
    }

    public void hideSoftKeyboard() {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(Objects.requireNonNull(getCurrentFocus()).getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadData() {
        mProductList = ProductDatabase.getInstance(this).productDAO().getAllProduct();
        productAdapter.setProductList(mProductList);
    }

    private boolean isProductExist(Product product) {
        List<Product> productList = ProductDatabase.getInstance(this).productDAO().checkProduct(product.getProductName());
        return productList != null && !productList.isEmpty();
    }

    private void clickUpdateProduct(Product product) {
        Intent intent = new Intent(this, UpdateProductActivity.class);
//        Bundle bundle = new Bundle();
//        bundle.putSerializable("obj_product", product);
//        intent.putExtras(bundle);
        intent.putExtra("obj_product", product);
        //startActivity(intent);
        launcherHomeActivity.launch(intent);
        //startActivityForResult(intent, MY_REQUEST_CODE);
    }

    private void clickDeleteProduct(Product product) {
        new AlertDialog.Builder(this)
                .setTitle("Confirm delete User")
                .setMessage("Are you sure?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Delete product
                        ProductDatabase.getInstance(MainActivity.this).productDAO().deleteProduct(product);
                        Toast.makeText(MainActivity.this, "Delete product successfully!", Toast.LENGTH_SHORT).show();
                        loadData();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    private void clickDeleteAllProduct() {
        new AlertDialog.Builder(this)
                .setTitle("Confirm delete all Product")
                .setMessage("Are you sure to DELETE ALL?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Delete product
                        ProductDatabase.getInstance(MainActivity.this).productDAO().deleteAllProduct();
                        Toast.makeText(MainActivity.this, "Delete all product successfully!", Toast.LENGTH_SHORT).show();
                        loadData();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            loadData();
        }
    }
}