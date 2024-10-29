package com.ducnm.btvn_broadcast;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.widget.Toast;

public class BroadcastReceiver extends android.content.BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        // Kiểm tra thay đổi kết nối WiFi
        if (ConnectivityManager.CONNECTIVITY_ACTION.equals(action)) {
            if (isNetworkAvailable(context)) {
                Toast.makeText(context, "WiFi Connected", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "WiFi Disconnected", Toast.LENGTH_SHORT).show();
            }
        }

        // Kiểm tra thay đổi trạng thái Bluetooth
        if (BluetoothAdapter.ACTION_STATE_CHANGED.equals(action)) {
            int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, -1);
            switch (state) {
                case BluetoothAdapter.STATE_ON:
                    Toast.makeText(context, "Bluetooth Enabled", Toast.LENGTH_SHORT).show();
                    break;
                case BluetoothAdapter.STATE_OFF:
                    Toast.makeText(context, "Bluetooth Disabled", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }

    // Hàm kiểm tra trạng thái kết nối mạng
    private boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager == null) {
            return false;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Network network = connectivityManager.getActiveNetwork();
            if (network == null) {
                return false;
            }
            NetworkCapabilities capabilities = connectivityManager.getNetworkCapabilities(network);
            return capabilities != null && capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI);
        } else {
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            return networkInfo != null && networkInfo.isConnected();
        }
    }
}
