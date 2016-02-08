package com.example.alex.tests;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.le.BluetoothLeScanner;
import android.content.Context;
import android.content.Intent;
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

//<uses-permission android:name="android.permission.BLUETOOTH" />
  //       <uses-permission android:name="android.permission.BLUETOOTH_ADMIN" />
//pretty sure this doesnt go here, will have to check back later



public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if (mBluetoothAdapter.isEnabled()
                ) {



            //can do something here if needed later

        }
    }
    private BluetoothAdapter mBluetooth;

    private SparseArray<BluetoothDevice> mDevices;



    BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    boolean hasBluetooth = (mBluetoothAdapter == null);

    //private final integer REQUEST_ENABLE_BT = 1;

    int i = 1;
    boolean istrue = false;
    UUID beacon1;
    UUID[] uuids;

    BluetoothServerSocket tmp;

    public boolean startLeScan (UUID id)
    {


      //  beacon1 = 8a5a8c8f-58ea-4955-bc5e-9cbd4af87286;

        //beacon1.fromString("8a5a8c8f-58ea-4955-bc5e-9cbd4af87286");

        //id.compareTo(beacon1);


        tmp = mBluetoothAdapter.listenUsingRfcommWithServiceRecord("MyAPP",beacon1);



        if (id == beacon1) {

        istrue = true;

        }
        //beacon declirations


        return (istrue);



    }


    public void setentry(View v, Object data, boolean isSelectable)
    {

        ListView listView = (ListView) v;



        listView.addFooterView(listView,data,isSelectable);




    }

    UUID id1;

    public void buttonOnClick(View v, View l){
    //    int i = 0;

        boolean selectable = false;

        //ListView listView = (ListView) findViewById(R.id.blelistview);





        id1.fromString("8a5a8c8f-58ea-4955-bc5e-9cbd4af87286");

        Button button = (Button) v;
        //((Button) v).setText("on");


        while (i == 0) {



          /*  if (hasBluetooth && !mBluetoothAdapter.isEnabled()) {

                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);

            }


*/
            ((Button) v).setText("ON");


            if(startLeScan(id1) == true)
            {

                //true
            };

            mBluetoothAdapter.enable();

            //listView.addFooterView(listView,i, selectable);



            if(hasBluetooth && !mBluetoothAdapter.isEnabled()) {

                ((Button) v).setText("SUPER on");

            }


            i = 1;
        }

        if (i == 1){


            mBluetoothAdapter.disable();

            ((Button) v).setText("OFF");
            i = 0;

        }
    }
}
