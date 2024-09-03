package com.example.yogshala.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.yogshala.Adapter.FeesDueAdapter;
import com.example.yogshala.R;
import com.example.yogshala.model.Transaction;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;

public class AllTransactionActivity extends AppCompatActivity {

    private ImageView backBtn;
    private ListView listView;
    private FeesDueAdapter adapter;
    private ArrayList<Transaction> transactionsList;
    private ArrayList<Transaction> filteredTransactionsList;
    private DatabaseReference databaseReference;

    private EditText etFromDate, etToDate;
    private Button btnShow;
    private SimpleDateFormat dateFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_transaction);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        listView = findViewById(R.id.listview);
        transactionsList = new ArrayList<>();
        filteredTransactionsList = new ArrayList<>();
        adapter = new FeesDueAdapter(this, filteredTransactionsList);
        listView.setAdapter(adapter);

        backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(v -> {
            Intent intent = new Intent(AllTransactionActivity.this, MainActivity.class);
            startActivity(intent);
        });

        etFromDate = findViewById(R.id.etFromDate);
        etToDate = findViewById(R.id.etToDate);
        btnShow = findViewById(R.id.btnShow);

        dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        etFromDate.setOnClickListener(v -> showDatePickerDialog(etFromDate));
        etToDate.setOnClickListener(v -> showDatePickerDialog(etToDate));

        databaseReference = FirebaseDatabase.getInstance().getReference("Transactions");

        btnShow.setOnClickListener(v -> filterTransactionsByDate());

        // Load all transactions initially
        loadAllTransactions();
    }

    private void showDatePickerDialog(EditText editText) {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, year, month, dayOfMonth) -> {
                    calendar.set(year, month, dayOfMonth);
                    editText.setText(dateFormat.format(calendar.getTime()));
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    private void loadAllTransactions() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                transactionsList.clear(); // Clear the list before adding new data
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Transaction transaction = snapshot.getValue(Transaction.class);
                    if (transaction != null) {
                        transactionsList.add(transaction);
                    }
                }

                // Initially display all transactions
                filteredTransactionsList.clear();
                filteredTransactionsList.addAll(transactionsList);
                Collections.sort(filteredTransactionsList, (t1, t2) -> t2.getToDate().compareTo(t1.getToDate()));
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(AllTransactionActivity.this, "Failed to load data.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void filterTransactionsByDate() {
        String fromDateStr = etFromDate.getText().toString();
        String toDateStr = etToDate.getText().toString();
        Date fromDate = null, toDate = null;

        try {
            fromDate = dateFormat.parse(fromDateStr);
            toDate = dateFormat.parse(toDateStr);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Date finalFromDate = fromDate;
        Date finalToDate = toDate;

        filteredTransactionsList.clear();

        for (Transaction transaction : transactionsList) {
            try {
                Date transactionDate = dateFormat.parse(transaction.getToDate());
                if (finalFromDate != null && finalToDate != null && transactionDate != null) {
                    if (!transactionDate.before(finalFromDate) && !transactionDate.after(finalToDate)) {
                        filteredTransactionsList.add(transaction);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        Collections.sort(filteredTransactionsList, (t1, t2) -> t2.getToDate().compareTo(t1.getToDate()));
        adapter.notifyDataSetChanged();
    }
}
