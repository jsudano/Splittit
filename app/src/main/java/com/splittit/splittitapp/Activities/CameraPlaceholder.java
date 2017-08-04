package com.splittit.splittitapp.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.splittit.splittitapp.R;
import com.splittit.splittitapp.Utils.Payer;
import com.splittit.splittitapp.Utils.PaymentItem;
import com.splittit.splittitapp.Utils.Receipt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CameraPlaceholder extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_placeholder);

        Button btnManual = (Button) findViewById(R.id.btn_fill_manually);
        Button btnTestData = (Button) findViewById(R.id.btn_test_data);

        btnManual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startSplitActivity();
            }
        });

        btnTestData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startSplitActivity(getTestData());
            }
        });
    }


    private void startSplitActivity() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    private void startSplitActivity(Receipt r) {
        Intent i = new Intent(this, MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        i.putExtra("receipt", r);
        startActivityIfNeeded(i, 0);
    }

    private Receipt getTestData() {
        ArrayList<PaymentItem> list = new ArrayList<>();
        list.add(new PaymentItem("Burger", 20));
        list.add(new PaymentItem("Fish", 10));
        list.add(new PaymentItem("Cornetto", 5));
        list.add(new PaymentItem("Water", 100));
        return new Receipt("Basic Bob's", list);
    }
}
