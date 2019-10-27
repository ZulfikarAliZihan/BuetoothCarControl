package com.zulfikar.buetoothcarcontrol;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    Button bluetoothOnBtn,bluetoothOffBtn;
    BluetoothAdapter bluetoothAdapter;
    ArrayAdapter<String> arrayAdapter;
    ArrayList devicesArrayList;
    ListView deviceList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bluetoothAdapter=BluetoothAdapter.getDefaultAdapter();
        bluetoothOnBtn=(Button) findViewById(R.id.b_on);
        bluetoothOffBtn=(Button) findViewById(R.id.b_off);
        deviceList=(ListView) findViewById(R.id.list_device);
        devicesArrayList=new ArrayList();
        //findDevices();

        arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_expandable_list_item_1, devicesArrayList);
        deviceList.setAdapter(arrayAdapter);

    }

//    private void findDevices() {
//        if(!bluetoothAdapter.isDiscovering()){
//            Intent i=new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
//            startActivityForResult(i,0);
//        }
//
//        Set<BluetoothDevice> bluetoothDevices = bluetoothAdapter.getBondedDevices();
//
//        if (bluetoothDevices.size()>0) {
//            String[] name = new String[bluetoothDevices.size()];
//            for (BluetoothDevice bt : bluetoothDevices) {
//                devicesArrayList.add(bt.getName());
//
//            }
//
//
//        }
//    }

    public void discover(View view){
        bluetoothAdapter.startDiscovery();
    }

    public void onBluetooth(View view){

        if(bluetoothAdapter==null){
            Toast.makeText(getApplicationContext(),"Your phone does not support bluetooth",Toast.LENGTH_LONG).show();
            finish();
        }
        else {
            if(!bluetoothAdapter.isEnabled()){
                Intent intent=new Intent(bluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(intent,1);

            }
            else {
                bluetoothAdapter.disable();
                Toast.makeText(getApplicationContext(),"Bluetooth is turned off",Toast.LENGTH_SHORT).show();
                devicesArrayList.clear();
                arrayAdapter.notifyDataSetChanged();
            }
        }

    }

//    public void offBluetooth(View view){
//        if(bluetoothAdapter.isEnabled()){
//            bluetoothAdapter.disable();
//            Toast.makeText(getApplicationContext(),"Bluetooth is turned off",Toast.LENGTH_SHORT).show();
//        }
//    }


    public BroadcastReceiver broadcastReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action=intent.getAction();
            if(BluetoothDevice.ACTION_FOUND.equals(action)){
                BluetoothDevice device=intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                devicesArrayList.add(device.getName());
                arrayAdapter.notifyDataSetChanged();
            }
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter intentFilter=new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(broadcastReceiver,intentFilter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==1) {
            if (resultCode==RESULT_OK) {
                Toast.makeText(getApplicationContext(), "Bluetooth enabled", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
