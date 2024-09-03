package com.example.yogshala.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yogshala.R;


public class DetailedTransactionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_transaction);

        // Hide the action bar if it exists
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        // Hide the status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        ImageView backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Closes the current activity and returns to the previous one
            }
        });



        // Retrieve the data from the Intent
        String clientName = getIntent().getStringExtra("clientName");
        String fromDate = getIntent().getStringExtra("fromDate");
        String toDate = getIntent().getStringExtra("toDate");
        String mode = getIntent().getStringExtra("mode");
        String type = getIntent().getStringExtra("type");
        String monthFee = getIntent().getStringExtra("monthFee");
        String receivedAmount = getIntent().getStringExtra("receivedAmount");
        String program = getIntent().getStringExtra("program");
        String remark = getIntent().getStringExtra("remark");



        // Find and populate the views with the retrieved data
        TextView tvClientName = findViewById(R.id.tvName);
        TextView tvFromDate = findViewById(R.id.tvFromDate);
        TextView tvToDate = findViewById(R.id.tvToDate);
        TextView tvMode = findViewById(R.id.tvMode2);
        TextView tvType = findViewById(R.id.tvType);
        TextView tvMonthFee = findViewById(R.id.tvMonthFee2);
        TextView tvReceivedAmount = findViewById(R.id.tvReceivedAmount2);
        TextView tvProgram = findViewById(R.id.tvProgram2);
        TextView tvRemark = findViewById(R.id.tvRemark2);

        tvClientName.setText(clientName);
        tvFromDate.setText(fromDate);
        tvToDate.setText(toDate);
        tvMode.setText(mode);
        tvType.setText(type);
        tvProgram.setText(program);
        tvRemark.setText(remark);
        tvMonthFee.setText(String.valueOf(monthFee));
        tvReceivedAmount.setText(String.valueOf(receivedAmount));
    }
}