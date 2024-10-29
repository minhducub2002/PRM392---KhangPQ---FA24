package com.ducnm.btvn_retrofit;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ducnm.btvn_retrofit.dto.Photo;
import com.ducnm.btvn_retrofit.services.ApiServices;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private Button btnLoadData;
    private RecyclerView rcvPhoto;
    private PhotoAdapter photoAdapter;
    private List<Photo> data;

    void bindingView() {
        btnLoadData = findViewById(R.id.btnLoadData);
        rcvPhoto = findViewById(R.id.rcvPhoto);
    }

    void bindingAction() {
        btnLoadData.setOnClickListener(this::onBtnLoadDataClick);
    }

    private void onBtnLoadDataClick(View view) {
        try {
            ApiServices.getPhotoApiEndpoint()
                    .getAll()
                    .enqueue(new Callback<List<Photo>>() {
                        @Override
                        public void onResponse(Call<List<Photo>> call, Response<List<Photo>> response) {
                            data = response.body();
                            photoAdapter.setPhotoList(data);
                            Log.d("DucNM_Debug", data.toString());
                        }

                        @Override
                        public void onFailure(Call<List<Photo>> call, Throwable t) {
                            Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        photoAdapter = new PhotoAdapter();
        initRecyclerView();
    }

    private void initRecyclerView() {
        rcvPhoto.setAdapter(photoAdapter);
        rcvPhoto.setLayoutManager(new LinearLayoutManager(this));
    }

}