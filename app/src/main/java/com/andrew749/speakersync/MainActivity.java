package com.andrew749.speakersync;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.telephony.TelephonyManager;
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

    String uuid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        connectButton = (Button) findViewById(R.id.connectButton);
        createButton = (Button) findViewById(R.id.createButton);
        connectButton.setOnClickListener(this);
        createButton.setOnClickListener(this);
        TelephonyManager tManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        uuid = tManager.getDeviceId();
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    public void AcceptThread() {
        BluetoothServerSocket tmp = null;
        try {
            tmp = mBluetoothAdapter.listenUsingInsecureRfcommWithServiceRecord("MYAPP", UUID.fromString(uuid));

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

    public void run() {
        BluetoothSocket socket = null;
        try {
            socket = mmServerSocket.accept();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private void doServer() {

        Intent discoverableIntent = new
                Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
        startActivity(discoverableIntent);
        while (mmServerSocket == null) ;
        Log.d("SS", "Started bluetooth");
        AcceptThread();
        r.run();

    }

    Runnable r = new Runnable() {
        @Override
        public void run() {
            run();
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

        File file = new File("C:\\testing.txt");

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
