package com.andrew749.speakersync;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;


public class MainActivity extends ActionBarActivity implements View.OnClickListener {
    Button connectButton, createButton;
    BluetoothDevice serverDevice;
    private static final UUID MY_UUID = UUID.fromString("fa87c0d0-afac-11de-8a39-0800200c9a66");
    BluetoothSocket socket = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        connectButton = (Button) findViewById(R.id.connectButton);
        createButton = (Button) findViewById(R.id.createButton);
        connectButton.setOnClickListener(this);
        createButton.setOnClickListener(this);
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    public void AcceptThread() {
        BluetoothServerSocket tmp = null;
        try {
            tmp = mBluetoothAdapter.listenUsingInsecureRfcommWithServiceRecord("MYAPP", MY_UUID);
        } catch (IOException e) {
        }
        mmServerSocket = tmp;
    }

    private void searchForPeers() {
        Log.d("SS", "searching for peers");
//        serverDevice = mBluetoothAdapter.getRemoteDevice("2c:54:cf:cf:6d:84:a9");
        serverDevice = mBluetoothAdapter.getRemoteDevice("bc:f5:ac:83:06:bc");
        try {
            serverDevice.createInsecureRfcommSocketToServiceRecord(UUID.fromString("MYAPP"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d("SS", serverDevice.getName());

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public BluetoothAdapter mBluetoothAdapter;
    private BluetoothServerSocket mmServerSocket;

    public void run1() {
        try {
            Log.d("SS", "listening for connect");
            socket = mmServerSocket.accept();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.d("SS", "connected!!!1");
    }

    private void doServer() {
        Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
        startActivity(discoverableIntent);
        AcceptThread();
        Log.d("SS", "Started bluetooth");
        r.run();


    }

    Runnable r = new Runnable() {
        @Override
        public void run() {
            run1();
        }
    };

    private void connectToServer() {

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.connectButton) {
            //do connect action
            searchForPeers();
        } else if (id == R.id.createButton) {
            //do create action
            doServer();
        }
    }

    private byte[] convertToByteStream() {
        FileInputStream fileInputStream = null;
        Log.d("SS", "writing byte stream");
        File file = new File("");

        byte[] bFile = new byte[(int) file.length()];

        try

        {
            //convert file into array of bytes
            fileInputStream = new FileInputStream(file);
            fileInputStream.read(bFile);
            fileInputStream.close();

        } catch (
                Exception e
                )

        {
            e.printStackTrace();
        }
        return bFile;
    }
}
