package com.ducnm.btvn_service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class PlayMusicService extends Service {
    private MediaPlayer mediaPlayer;

    @Override
    public void onCreate() {
        super.onCreate();
        // Khởi tạo MediaPlayer với file nhạc trong res/raw/sample.mp3
        mediaPlayer = MediaPlayer.create(this, R.raw.sample);
        mediaPlayer.setLooping(true); // Đặt nhạc phát liên tục
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Bắt đầu phát nhạc
        mediaPlayer.start();
        Toast.makeText(this, "Music Service Started", Toast.LENGTH_SHORT).show();
        return START_STICKY;  // Để service tiếp tục chạy khi bị tắt
    }

    @Override
    public void onDestroy() {
        // Dừng và giải phóng MediaPlayer khi dừng service
        mediaPlayer.stop();
        mediaPlayer.release();
        Toast.makeText(this, "Music Service Stopped", Toast.LENGTH_SHORT).show();
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null; // Chúng ta không cần bind trong trường hợp này
    }
}

