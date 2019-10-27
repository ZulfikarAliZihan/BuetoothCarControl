package com.zulfikar.buetoothcarcontrol;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button bluetoothOnBtn,bluetoothOffBtn;
    BluetoothAdapter bluetoothAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bluetoothAdapter=BluetoothAdapter.getDefaultAdapter();
        bluetoothOnBtn=(Button) findViewById(R.id.b_on);
        bluetoothOffBtn=(Button) findViewById(R.id.b_off);

    }

    public void onBluetooth(View view){

        if(bluetoothAdapter==null){
            Toast.makeText(getApplicationContext(),"Your phone does not support bluetooth",Toast.LENGTH_LONG).show();
        }
        else {
            if(!bluetoothAdapter.isEnabled()){
                Intent intent=new Intent(bluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(intent,1);
            }
        }

    }

    public void offBluetooth(View view){
        if(bluetoothAdapter.isEnabled()){
            bluetoothAdapter.disable();
            Toast.makeText(getApplicationContext(),"Bluetooth is turned off",Toast.LENGTH_SHORT).show();
        }
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
