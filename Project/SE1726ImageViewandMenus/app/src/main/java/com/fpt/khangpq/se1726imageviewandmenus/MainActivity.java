package com.fpt.khangpq.se1726imageviewandmenus;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;

public class MainActivity extends AppCompatActivity {

    private Button btnChange;
    private Button btnLoad;
    private EditText edtUrl;
    private ImageView imgView;

    private void bindingView() {
        btnChange = findViewById(R.id.btnChange);
        imgView = findViewById(R.id.imgView);
        btnLoad = findViewById(R.id.btnLoad);
        edtUrl = findViewById(R.id.edtUrl);
    }

    private void bindingAction() {
        btnChange.setOnClickListener(this::onBtnChangeClick);
        btnLoad.setOnClickListener(this::onBtnLoadClick);
        registerForContextMenu(imgView);
    }

    private void onBtnLoadClick(View view) {
        String url = edtUrl.getText().toString();
        Glide.with(this)
                .load(url)
                .error(R.drawable.img_2)
                .placeholder(R.drawable.img_3)
                .into(imgView);
    }

    private void onBtnChangeClick(View view) {
        imgView.setImageResource(R.drawable.img_1);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.opt_setting) {
            Toast.makeText(this, "opt_setting_ContextItemSelected", Toast.LENGTH_SHORT).show();
        }
        if (id == R.id.opt_detail) {
            Toast.makeText(this, "opt_detail_ContextItemSelected", Toast.LENGTH_SHORT).show();

        }
        if (id == R.id.opt_signout) {

            Toast.makeText(this, "opt_signout_ContextItemSelected", Toast.LENGTH_SHORT).show();
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.opt_setting) {
            Toast.makeText(this, "opt_setting", Toast.LENGTH_SHORT).show();
        }
        if (id == R.id.opt_detail) {
            Toast.makeText(this, "opt_detail", Toast.LENGTH_SHORT).show();

        }
        if (id == R.id.opt_signout) {

            Toast.makeText(this, "opt_signout", Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
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