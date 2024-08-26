package com.example.yogshala.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.yogshala.Adapter.FeesDueAdapter;
import com.example.yogshala.R;
import com.example.yogshala.model.Transaction;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class ActivityFeesDue extends AppCompatActivity {

    private ImageView backBtn;

    private ListView listView;
    private FeesDueAdapter adapter;
    private ArrayList<Transaction> transactionList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fees_due);

        // Hide the action bar if it exists
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        // Hide the status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityFeesDue.this, MainActivity.class);
                startActivity(intent);
            }
        });

        listView = findViewById(R.id.listview);
        transactionList = new ArrayList<>();

        // Initialize the adapter with the list of transactions
        adapter = new FeesDueAdapter(this, transactionList);
        listView.setAdapter(adapter);

        // Get today's date
        String todayDate = getTodayDate();

        // Load the due fees data
        loadDueFees(todayDate);
    }

    private String getTodayDate() {
        // Define the date format
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        // Get today's date
        return sdf.format(new Date());
    }

    private void loadDueFees(String todayDate) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Transactions");

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                transactionList.clear();
                Map<String, Transaction> latestTransactions = new HashMap<>();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Transaction transaction = snapshot.getValue(Transaction.class);
                    if (transaction != null) {
                        String clientId = transaction.getClientId();
                        String toDate = transaction.getToDate();

                        // Update if this is the first transaction for the client or if this transaction is later
                        if (!latestTransactions.containsKey(clientId) ||
                                isLaterDate(toDate, latestTransactions.get(clientId).getToDate())) {
                            latestTransactions.put(clientId, transaction);
                        }
                    }
                }

                // Now filter and add transactions where toDate is less than today's date
                for (Transaction transaction : latestTransactions.values()) {
                    if (isEarlierDate(transaction.getToDate(), todayDate)) {
                        transactionList.add(transaction);
                    }
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle possible errors.
            }
        });
    }

    private boolean isLaterDate(String date1, String date2) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        try {
            Date d1 = sdf.parse(date1);
            Date d2 = sdf.parse(date2);

            // Return true if d1 is after d2
            return d1 != null && d2 != null && d1.after(d2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean isEarlierDate(String date1, String date2) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        try {
            Date d1 = sdf.parse(date1);
            Date d2 = sdf.parse(date2);

            // Return true if d1 is before d2
            return d1 != null && d2 != null && d1.before(d2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }


}