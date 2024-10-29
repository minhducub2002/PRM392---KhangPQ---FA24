package com.fpt.khangpq.se1726retrofit;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.fpt.khangpq.se1726retrofit.dto.Post;
import com.fpt.khangpq.se1726retrofit.services.ApiServices;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private Button btnLoad;
    private EditText edtData;
    private EditText edtShowData;

    private void bindingView() {
        btnLoad = findViewById(R.id.btnLoad);
        edtData = findViewById(R.id.edtData);
        edtShowData = findViewById(R.id.edtShowData);
    }

    private void bindingAction() {
        btnLoad.setOnClickListener(this::onBtnLoadClick);
    }

    private void onBtnLoadClick(View view) {
        String data = edtData.getText().toString();
        try {
            long id = Long.parseLong(data);
            ApiServices.getiPostApiEndpoint()
                    .getByUserId(id)
                    .enqueue(new Callback<List<Post>>() {
                        @Override
                        public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                            List<Post> posts = response.body();
                            String result = "";
                            for (Post post : posts) {
                                result += post + "\n\n";
                            }
                            edtShowData.setText(result);
                        }

                        @Override
                        public void onFailure(Call<List<Post>> call, Throwable t) {
                            edtShowData.setText("LOI ROI!---" + t.getMessage());
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
    }
}