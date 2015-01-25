package com.andrew749.speakersync;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.p2p.WifiP2pManager;
import android.util.Log;

/**
 * Created by andrew on 1/24/15.
 */
public class SongBroadcastReceiver extends BroadcastReceiver {
    private WifiP2pManager mManager;
    private WifiP2pManager.Channel channel;
    WifiP2pManager.PeerListListener listListener;

    public SongBroadcastReceiver(WifiP2pManager manager, WifiP2pManager.Channel channel,
                                 MainActivity activity) {
    }


    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        Log.d("SS", "receivedd broadcast");
        Log.d("SS", intent.getAction());

    }
}
