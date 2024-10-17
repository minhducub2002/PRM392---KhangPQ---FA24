package com.ducnm.btvn_multithread;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    private Button btnChange;
    private Button btnLoad;
    private EditText edtUrl;
    private ImageView imgView;

    // Tạo ExecutorService với một thread pool để xử lý tác vụ
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    // Sử dụng Handler để chuyển kết quả về main thread
    private final Handler mainHandler = new Handler(Looper.getMainLooper());

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
        imgView.setImageResource(R.drawable.img_3);
        // link: https://file.hstatic.net/200000159621/article/3.9__5__278779a3db4c4ef380cd59d09905c760_grande.jpg
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                // Background task: tải ảnh từ URL
                Bitmap img = urlToBitmap(url);

                // Post kết quả về lại UI thread
                Handler handler = new Handler(getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        // Cập nhật giao diện người dùng
                        if (img == null) {
                            imgView.setImageResource(R.drawable.img_2); // Error image
                        } else {
                            imgView.setImageBitmap(img); // Hiển thị ảnh đã tải
                        }
                    }
                });
            }
        });

//        executorService.execute(() -> {
//            Bitmap img = urlToBitmap(url);
//
//            // Sử dụng Handler để đưa kết quả về main thread
//            mainHandler.post(() -> {
//                if (img == null) {
//                    imgView.setImageResource(R.drawable.img_2);
//                } else {
//                    imgView.setImageBitmap(img);
//                }
//            });
//        });

//        new Thread(() -> {
//            Bitmap img = urlToBitmap(url);
//            runOnUiThread(() -> {
//                if (img == null) {
//                    imgView.setImageResource(R.drawable.img_2);
//                } else {
//                    imgView.setImageBitmap(img);
//                }
//            });
//        }).start();


//        Glide.with(this)
//                .load(url)
//                .error(R.drawable.img_2)
//                .placeholder(R.drawable.img_3)
//                .into(imgView);
    }

    private Bitmap urlToBitmap(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void onBtnChangeClick(View view) {
        imgView.setImageResource(R.drawable.img_1);

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