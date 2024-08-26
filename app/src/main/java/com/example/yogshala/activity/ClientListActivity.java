package com.example.yogshala.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.yogshala.Adapter.ClientAdapter;

import com.example.yogshala.model.Client;

import com.example.yogshala.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
//
//public class ClientListActivity extends AppCompatActivity {
//
//    private ListView listView;
//    private ClientAdapter clientAdapter;
//    private List<Client> clientList;
//    private DatabaseReference databaseReference;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_client_list);
//
//        // Hide the action bar if it exists
//        if (getSupportActionBar() != null) {
//            getSupportActionBar().hide();
//        }
//
//        // Hide the status bar
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
//
//        listView = findViewById(R.id.listview);
//        clientList = new ArrayList<>();
//        clientAdapter = new ClientAdapter(this, clientList);
//        listView.setAdapter(clientAdapter);
//
//        databaseReference = FirebaseDatabase.getInstance().getReference("client");
//
//        fetchClients();
//
//        // Set OnItemClickListener for ListView items
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Client client = clientList.get(position);
//
//                Intent intent = new Intent(ClientListActivity.this, DetailedDataActivity.class);
//                intent.putExtra("name", client.getFirstName() + " " + client.getLastName());
//                intent.putExtra("area", client.getArea());
//                intent.putExtra("enquiryDate", client.getDate());
//                intent.putExtra("program", client.getProgram());
//                intent.putExtra("age", client.getAge());
//                intent.putExtra("status", client.getStatus());
//                intent.putExtra("interest", client.getInterest());
//                intent.putExtra("referredBy", client.getReferral());
//                intent.putExtra("referredName", client.getReferralName());
//                intent.putExtra("parentName", client.getParentFirstName() + " " + client.getParentLastName());
//                intent.putExtra("mobile", client.getPhone());
//                intent.putExtra("amount", client.getAmount());
//                intent.putExtra("email", client.getEmail());
//                intent.putExtra("address", client.getAddress());
//                startActivity(intent);
//            }
//        });
//    }
//
//    private void fetchClients() {
//        databaseReference.orderByChild("date").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                clientList.clear();
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                    Client client = snapshot.getValue(Client.class);
//                    if (client != null && client.getDate() != null) {
//                        clientList.add(client);
//                        Log.d("ClientListActivity", "Client added: " + client.getFirstName());
//                    } else {
//                        Log.d("ClientListActivity", "Client is null or has no date");
//                    }
//                }
//                // Reverse the list to get the latest dates on top
//                Collections.reverse(clientList);
//                clientAdapter.notifyDataSetChanged();
//                Log.d("ClientListActivity", "Total Clients: " + clientList.size());
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                Log.e("ClientListActivity", "Failed to retrieve data", databaseError.toException());
//            }
//        });
//    }
//}



import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ListView;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ClientListActivity extends AppCompatActivity {

    private ImageView backBtn;

    private ListView listView;
    private ClientAdapter clientAdapter;
    private List<Client> clientList;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_list);

        // Hide the action bar if it exists
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        // Hide the status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        listView = findViewById(R.id.listview);
        clientList = new ArrayList<>();
        clientAdapter = new ClientAdapter(this, clientList);
        listView.setAdapter(clientAdapter);

        databaseReference = FirebaseDatabase.getInstance().getReference("client");

        backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClientListActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        fetchClients();

        // Set OnItemClickListener for ListView items
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Client client = clientList.get(position);

                Intent intent = new Intent(ClientListActivity.this, DetailedDataActivity.class);
                intent.putExtra("name", client.getFirstName() + " " + client.getLastName());
                intent.putExtra("area", client.getArea());
                intent.putExtra("enquiryDate", client.getDate());
                intent.putExtra("program", client.getProgram());
                intent.putExtra("age", client.getAge());
                intent.putExtra("status", client.getStatus());
                intent.putExtra("interest", client.getInterest());
                intent.putExtra("referredBy", client.getReferral());
                intent.putExtra("referredName", client.getReferralName());
                intent.putExtra("parentName", client.getParentFirstName() + " " + client.getParentLastName());
                intent.putExtra("mobile", client.getPhone());
                intent.putExtra("amount", client.getAmount());
                intent.putExtra("email", client.getEmail());
                intent.putExtra("address", client.getAddress());
                startActivity(intent);
            }
        });
    }

    private void fetchClients() {
        databaseReference.orderByChild("date").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                clientList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Client client = snapshot.getValue(Client.class);
                    if (client != null && client.getDate() != null) {
                        clientList.add(client);
                    }
                }
                // Reverse the list to get the latest dates on top
                Collections.reverse(clientList);
                clientAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle possible errors.
            }
        });
    }
}

