package com.fpt.khangpq.se1726broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class Receiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String data = intent.getStringExtra("data");
        if (data != null) {
            Toast.makeText(context, intent.getStringExtra("data"), Toast.LENGTH_SHORT).show();
        }
    }
}
