package com.fpt.khangpq.se1726recyclerview;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<Word> data;
    private RecyclerView rcv;

    private void fakeData() {
        data = new ArrayList<>();
        for (int i = 0; i < 1000; ) {
            i++;
            data.add(new Word("Word " + i, "Definition " + i));
        }
    }


    private void bindingView() {
        rcv = findViewById(R.id.rcv);
    }

    private void bindingAction() {
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
        Log.d("DucNM_Debug", "rcv: " + rcv);
        bindingAction();
        fakeData();
        initRcv();
    }

    private void initRcv() {
        RecyclerView.Adapter adapter = new DictionaryAdapter(data);
        rcv.setAdapter(adapter);
//        rcv.setLayoutManager(new LinearLayoutManager(this));
        rcv.setLayoutManager(new GridLayoutManager(this, 2));
    }
}