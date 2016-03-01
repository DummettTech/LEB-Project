package com.example.alex.tests;

import android.annotation.TargetApi;

import java.lang.annotation.Target;



import android.annotation.TargetApi;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.le.BluetoothLeScanner;
import android.content.Context;
import android.content.Intent;
import android.graphics.Region;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.ToggleButton;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.jar.Manifest;


@TargetApi(25)

public class MainActivity extends AppCompatActivity {

    private Region region;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Spinner dropbown = (Spinner)findViewById(R.id.spinner);

        String[] list = new String[]{"0", "1", "2","3","4","5"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, list);
        dropbown.setAdapter(adapter);


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

        //setadresses(); //crashes when ran here
    }

    private BluetoothAdapter mBluetooth;

    private SparseArray<BluetoothDevice> mDevices;


    int beconnum;

    String name;

    String[] adresslist = new String[5];
    String[] weblinks = new String[5];

    boolean foundbeacon = false;

public void setadresses ()
    {



        adresslist[0] = "0C:F3:EE:00:4F:6C"; //beacon 1
        adresslist[1] = "E3:C9:42:12:DB:71"; //esitom
        weblinks[1] = "http://www.lincstothepast.com/exhibitions/treasures/lincolnshire-tank/";
        weblinks[2] = "http://www.lincstothepast.com/Museum-of-Lincolnshire-Life--Burton-Road--Lincoln/245911.record?pt=S";


    }

    boolean isopen;
    boolean manualinput = false;

    private BluetoothAdapter.LeScanCallback mLeScanCalllBack = new BluetoothAdapter.LeScanCallback() {
        @Override
        public void onLeScan(final BluetoothDevice device, final int rssi, final byte[] scanRecord) {

            Spinner dropdown = (Spinner)findViewById(R.id.spinner);

            if (!device.getAddress().isEmpty()) {

                if(dropdown.getPopupContext().toString().equals("0")){}
                else {

                    beconnum = Integer.parseInt(dropdown.getPopupContext().toString());
                    foundbeacon = true;
                    manualinput = true;

                    Log.d("LOGGING","beconnum: " + dropdown.getPopupContext().toString());

                    }

                name = device.getAddress();

                if (foundbeacon == true) {

                    while (rssi >= -80 || manualinput == true) {
                        if (isopen = true){
                            openURL(weblinks[beconnum]);
                            isopen = true;
                        }
                    }
                    beconnum = 0;
                    isopen = false;
                    manualinput = false;
                }
                else {

                    Log.d("ADDRE    SSES", "Device Adresss is " + name + " RSSI is: " + rssi);

                    if (name.toString().equals(adresslist[0])) {
                        Log.d("LOGGING", "address is 0");
                        beconnum = 1;
                        foundbeacon = true;
                    }
                    if (name.toString().equals(adresslist[1])) {
                        Log.d("LOGGING", "address is 1");
                        beconnum = 2;
                        foundbeacon = true;
                    }

                }
            }

            int startByte = 2;

            while (startByte <= 5) {
                if (

                        ((int) scanRecord[startByte + 2] & 0xff) == 0x02 &&

                                ((int) scanRecord[startByte + 3] & 0xff) == 0x15) {
                    break;
                }
                startByte++;
            }

            //this section was for recording the device uuid but may not be needed anymore


        }


    };

    BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    boolean hasBluetooth = (mBluetoothAdapter == null);

    UUID id1;

    public void openURL(String inURL) {
        Intent browse = new Intent(Intent.ACTION_VIEW, Uri.parse(inURL));

        startActivity(browse);


    }

    public void buttonOnClick(View v) {

        boolean selectable = false;


        id1.fromString("8a5a8c8f-58ea-4955-bc5e-9cbd4af87286");

        final UUID[] uuids = {UUID.fromString("8a5a8c8f-58ea-4955-bc5e-9cbd4af87286")};

        ToggleButton button = (ToggleButton) v;


        if (button.isChecked()) {
            button.setText("ON");

            mBluetoothAdapter.enable();

            startScan();

        }

        if (!button.isChecked()) {
            mBluetoothAdapter.disable();
            button.setText("OFF");
        }

    }

    public void rescanButton(View view) {

        if (mBluetoothAdapter.isEnabled()) {
            ((Button) view).setText("did it");
            startScan();
        } else {

            ((Button) view).setText("Turning on Bluetooth");
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

            if (mBluetoothAdapter.isEnabled()) {
                ((Button) view).setText("Did it");
                startScan();
            }
        }
    }

    public void startScan() {

        Log.d("Log", "StartScan");

        setadresses(); //crashes here too

        if (!mBluetoothAdapter.isEnabled()) {

        } else {
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


    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.alex.tests/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);

        if (mBluetoothAdapter.isEnabled()
                ) {
            mBluetoothAdapter.disable();
            //can do something here if needed later
        }

    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.alex.tests/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        mBluetoothAdapter.disable();
        client.disconnect();
    }
}
