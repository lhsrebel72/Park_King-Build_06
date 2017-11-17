package com.example.maupi.parkking;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MeterActivity extends AppCompatActivity {
    TextView priceTextView;
    DatabaseHelper db = new DatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meter);

        Bundle extras = getIntent().getExtras();

        String meterID = "111111";
        if(extras != null){
            meterID = extras.getString("ParkingMeterID");
        }

        Log.d("FROM DB", db.getPrice(meterID));

    }
}
