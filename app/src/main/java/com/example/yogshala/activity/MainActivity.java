package com.example.yogshala.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.yogshala.AddTransactionActivity;
import com.example.yogshala.R;
import com.example.yogshala.model.Client;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class MainActivity extends AppCompatActivity {

    private TextView totalTV, monthTV, weekTV, todayTV;
    private DatabaseReference clientDatabaseReference;
    private ConstraintLayout CL1,CL2,CL3,CL4,CL5,CL6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Hide the action bar if it exists
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        // Hide the status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Button Clicks from here
        CL1 = findViewById(R.id.CL1);
        CL1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddClientActivity.class);
                startActivity(intent);
            }
        });

        CL2 = findViewById(R.id.CL2);
        CL2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddProgramActivity.class);
                startActivity(intent);
            }
        });

        CL3 = findViewById(R.id.CL3);
        CL3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AdminActivity.class);
                startActivity(intent);
            }
        });

        CL4 = findViewById(R.id.CL4);
        CL4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ClientListActivity.class);
                startActivity(intent);
            }
        });

        CL5 = findViewById(R.id.CL5);
        CL5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddTransactionActivity.class);
                startActivity(intent);
            }
        });

        CL6 = findViewById(R.id.CL6);
        CL6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ReportsActivity.class);
                startActivity(intent);
            }
        });


        totalTV = findViewById(R.id.totalTV);
        monthTV = findViewById(R.id.monthTV);
        weekTV = findViewById(R.id.weekTV);
        todayTV = findViewById(R.id.todayTV);

        clientDatabaseReference = FirebaseDatabase.getInstance().getReference("client");

        loadClientCounts();
    }

    private void loadClientCounts() {
        clientDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int totalCount = 0;
                int thisMonthCount = 0;
                int thisWeekCount = 0;
                int todayCount = 0;

                LocalDate today = LocalDate.now();
                LocalDate startOfWeek = today.minusDays(today.getDayOfWeek().getValue() - 1);
                LocalDate startOfMonth = today.withDayOfMonth(1);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

                for (DataSnapshot clientSnapshot : snapshot.getChildren()) {
                    Client client = clientSnapshot.getValue(Client.class);
                    if (client != null) {
                        totalCount++;

                        LocalDate clientDate = LocalDate.parse(client.getDate(), formatter);

                        // Check if the client was added this month
                        if (!clientDate.isBefore(startOfMonth) && !clientDate.isAfter(today)) {
                            thisMonthCount++;
                        }

                        // Check if the client was added this week
                        if (!clientDate.isBefore(startOfWeek) && !clientDate.isAfter(today)) {
                            thisWeekCount++;
                        }

                        // Check if the client was added today
                        if (clientDate.isEqual(today)) {
                            todayCount++;
                        }
                    }
                }

                totalTV.setText("" + totalCount);
                monthTV.setText("" + thisMonthCount);
                weekTV.setText("" + thisWeekCount);
                todayTV.setText("" + todayCount);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, "Failed to load data", Toast.LENGTH_SHORT).show();
            }
        });
    }
}