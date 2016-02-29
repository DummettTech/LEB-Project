package com.example.alex.tests;

import android.annotation.TargetApi;

import java.lang.annotation.Target;



import android.annotation.TargetApi;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.le.BluetoothLeScanner;
import android.content.Context;
import android.content.Intent;
import android.graphics.Region;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ToggleButton;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.jar.Manifest;


@TargetApi(18)

public class MainActivity extends AppCompatActivity {

    private Region region;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (mBluetoothAdapter.isEnabled()
                ) {
                mBluetoothAdapter.disable();
                //can do something here if needed later
        }
    }
    private BluetoothAdapter mBluetooth;

    private SparseArray<BluetoothDevice> mDevices;


    private int beconnum;

    String name;

    private BluetoothAdapter.LeScanCallback mLeScanCalllBack = new BluetoothAdapter.LeScanCallback()
    {
        @Override

        public void onLeScan(final BluetoothDevice device, final int rssi, final byte [] scanRecord)
        {

            //openURL("http://www.google.com");



            if(!device.getAddress().isEmpty()) {

                name = device.getAddress();

                if (name == "0C:F3:EE:00:4F:6C") { beconnum = 1; }

                    //done it
                    if (beconnum == 1){

                    openURL("http://www,dum12373618.wordpress.com");

                }
            }

            int startByte = 2;
            boolean patternFound = false;

            while (startByte <= 5) {
                if (

                        ((int) scanRecord[startByte + 2] & 0xff) == 0x02 && //Identifies an iBeacon

                        ((int) scanRecord[startByte + 3] & 0xff) == 0x15) { //Identifies correct data length

                    patternFound = true;
                    break;
                }
                startByte++;
            }

            name = id1.toString();

            //add to later
        }


    };

    BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    boolean hasBluetooth = (mBluetoothAdapter == null);

    int i = 1;
    boolean istrue = false;
    UUID beacon1;
    UUID[] uuids;

    BluetoothServerSocket tmp;

    public boolean startLeScan (UUID id)
    {

        if (id == beacon1) {

        istrue = true;

        }
        //beacon declirations


        return (istrue);



    }

    UUID id1;

    public void openURL(String inURL)
    {
        Intent browse = new Intent(Intent.ACTION_VIEW, Uri.parse(inURL));

        startActivity(browse);

        

    }

    public void buttonOnClick(View v){

        boolean selectable = false;



        id1.fromString("8a5a8c8f-58ea-4955-bc5e-9cbd4af87286");

        final UUID[] uuids = {UUID.fromString("8a5a8c8f-58ea-4955-bc5e-9cbd4af87286")};

        ToggleButton button = (ToggleButton) v;




            if (button.isChecked()) {
                button.setText("ON");

                mBluetoothAdapter.enable();


            startScan();

        }

        if (!button.isChecked()){


            mBluetoothAdapter.disable();

            button.setText("OFF");


        }

    }

    public void rescanButton (View view)
    {


        if(mBluetoothAdapter.isEnabled()) {
            ((Button) view).setText("did it");


            startScan();
        }
        else {

            ((Button)view).setText("Turning on Bluetooth");

            ToggleButton toggle = (ToggleButton) findViewById(R.id.togglebutton);


            toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    if (buttonView.isChecked()) {
                        isChecked = true;
                    } else {
                        isChecked = false;
                    }

                    if (!isChecked) {
                        buttonView.toggle();
                        mBluetoothAdapter.enable();
                    }
                }
            });


            if(mBluetoothAdapter.isEnabled())
                {
                    ((Button)view).setText("Did it");
                    startScan();
                }
        }
    }

    public void startScan ()
    {


        if (!mBluetoothAdapter.isEnabled())
        {

        }

        else {
            Handler handler = new Handler();


            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mBluetoothAdapter.stopLeScan(mLeScanCalllBack);

                    final UUID[] uuids = {UUID.fromString("8a5a8c8f-58ea-4955-bc5e-9cbd4af87286")};

                    mBluetoothAdapter.startLeScan(mLeScanCalllBack);

                   /* while (mBluetoothAdapter.startLeScan(uuids, mLeScanCalllBack))


                    {

                        //while (blah.toString() == "0C:F3:EE:00:4F:6C"){
                            mBluetoothAdapter.startLeScan(mLeScanCalllBack);
                            //openURL("http://www.dum12373618.wordpress.com");
                    }*/
                }
            }, 5000);//50 seconds timer
        }
    }



}
