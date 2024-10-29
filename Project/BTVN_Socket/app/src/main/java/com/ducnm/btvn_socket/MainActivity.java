package com.ducnm.btvn_socket;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class MainActivity extends AppCompatActivity {

    private Socket socket;
    private MessageAdapter messageAdapter;
    private List<String> messageList = new ArrayList<>();

    private RecyclerView recyclerView;
    private EditText etMessage;
    private Button btnSend;

    private void bindingView() {
        recyclerView = findViewById(R.id.rvMessages);
        etMessage = findViewById(R.id.etMessage);
        btnSend = findViewById(R.id.btnSend);
        messageAdapter = new MessageAdapter(messageList);
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
        initRecyclerView();
        connectSocket();
        listenForMessages();
        sendMessages();
        bindingAction();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        socket.disconnect();
        socket.off("chatMessage");
    }

    private void sendMessages() {
        // Gửi tin nhắn khi nút "Send" được nhấn
        btnSend.setOnClickListener(v -> {
            String message = etMessage.getText().toString();
            if (!message.isEmpty()) {
                Log.d("DucNM_Debug", "Sending message: " + message);
                socket.emit("chatMessage", message); // Gửi tin nhắn tới server
                etMessage.setText(""); // Xóa nội dung sau khi gửi
            }
        });
    }

    private void listenForMessages() {
        // Lắng nghe sự kiện chat message từ server
        socket.on("chatMessage", new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String message = (String) args[0];
                        messageList.add(message);
                        messageAdapter.notifyItemInserted(messageList.size() - 1); // Chỉ cập nhật tin nhắn mới nhất
                        recyclerView.scrollToPosition(messageList.size() - 1);
                    }
                });
            }
        });
    }

    private void connectSocket() {
        // Kết nối tới server socket.io
        try {
            socket = IO.socket("http://192.168.1.125:3000");
            socket.connect();

            // Log kết nối thành công
            socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    runOnUiThread(() -> {
                        System.out.println("Socket connected!");
                    });
                }
            });

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }


    private void initRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(messageAdapter);
    }
}