package com.example.yogshala.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yogshala.R;

public class DetailedDataActivity extends AppCompatActivity {

    private TextView tvName, tvArea, tvEnquiryDate, tvProgramDetail, tvAge2, tvStatus2, tvInterest2, tvRefferedBy2 , tvAmountDetail;
    private TextView tvParent2, tvMobile2, tvEmail2, tvAddress2, tvRefferalName;

    private ImageView backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_data);

        // Hide the action bar if it exists
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        // Hide the status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Initialize views
        tvName = findViewById(R.id.tvName);
        tvArea = findViewById(R.id.tvArea);
        tvEnquiryDate = findViewById(R.id.tvEnquiryDate);
        tvProgramDetail = findViewById(R.id.tvProgramDetail);
        tvAge2 = findViewById(R.id.tvAge2);
        tvStatus2 = findViewById(R.id.tvStatus2);
        tvInterest2 = findViewById(R.id.tvInterest2);
        tvRefferedBy2 = findViewById(R.id.tvRefferedBy2);
        tvParent2 = findViewById(R.id.tvParent2);
        tvMobile2 = findViewById(R.id.tvMobile2);
        tvAmountDetail = findViewById(R.id.tvAmountDetail);
        tvEmail2 = findViewById(R.id.tvEmail2);
        tvAddress2 = findViewById(R.id.tvAddress2);
        tvRefferalName = findViewById(R.id.tvRefferalName);

        //Button Clicks from here
        backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailedDataActivity.this, ClientListActivity.class);
                startActivity(intent);
            }
        });

        // Get data from intent
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String area = intent.getStringExtra("area");
        String enquiryDate = intent.getStringExtra("enquiryDate");
        String program = intent.getStringExtra("program");
        String age = intent.getStringExtra("age");
        String status = intent.getStringExtra("status");
        String interest = intent.getStringExtra("interest");
        String referredBy = intent.getStringExtra("referredBy");
        String referralName = intent.getStringExtra("referredName");

        String parentName = intent.getStringExtra("parentName");
        String mobile = intent.getStringExtra("mobile");
        String amount = intent.getStringExtra("amount");
        String email = intent.getStringExtra("email");
        String address = intent.getStringExtra("address");

        // Set data to views
        tvName.setText(name);
        tvArea.setText(area);
        tvEnquiryDate.setText(enquiryDate);
        tvProgramDetail.setText(program);
        tvAge2.setText(age);
        tvStatus2.setText(status);
        tvInterest2.setText(interest);
        tvRefferedBy2.setText(referredBy);
        tvRefferalName.setText(referralName);
        tvParent2.setText(parentName);
        tvMobile2.setText(mobile);
        tvAmountDetail.setText(amount);
        tvEmail2.setText(email);
        tvAddress2.setText(address);
    }
}