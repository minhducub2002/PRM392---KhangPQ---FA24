package com.fpt.khangpq.se1726broadcast;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class MainActivity extends AppCompatActivity {

    private Button btnBroadcast;
    private EditText edtData;
    private Receiver receiver;

    private void bindingView() {
        btnBroadcast = findViewById(R.id.btnBroadcast);
        edtData = findViewById(R.id.edtData);
        receiver = new Receiver();
    }

    private void bindingAction() {
        btnBroadcast.setOnClickListener(this::onBtnBroadcastClick);
    }

    private void onBtnBroadcastClick(View view) {
        String data = edtData.getText().toString();
        Intent i = new Intent("com.fpt.khangpq.se1726broadcast.test");
        i.putExtra("data", data);
//        sendBroadcast(i);
        LocalBroadcastManager.getInstance(this)
                .sendBroadcast(i);
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

    @SuppressLint("UnspecifiedRegisterReceiverFlag")
    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter inf = new IntentFilter();
        inf.addAction("com.fpt.khangpq.se1726broadcast.test");
//        inf.addAction("com.fpt.khangpq.se1726broadcast.test");
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, inf);
    }

    @Override
    protected void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
    }
}