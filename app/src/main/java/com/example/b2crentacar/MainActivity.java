package com.example.b2crentacar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static ArrayList<Car> cars=new ArrayList<>();
    private RecyclerItemViewAdaptor adapter;
    private RecyclerView recyclerView;
    private DatabaseReference database;
    FirebaseStorage storage;
    StorageReference storageReference;
    private ArrayList<String> list = new ArrayList<>();
    private Button price,year,model;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView=findViewById(R.id.recyclerViewCars);
        database = FirebaseDatabase.getInstance().getReference();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        price=findViewById(R.id.btnPrice);
        model=findViewById(R.id.btnBrand);
        year=findViewById(R.id.btnYear);

        database.child("Cars").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    Car car1 = postSnapshot.getValue(Car.class);
                    cars.add(car1);
                }
                initRecyclerView();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        price.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Collections.sort(cars, Car.compareToPrice);
                ReinitRecyclerView();
                Toast.makeText(getApplicationContext(),"Succesfull",Toast.LENGTH_SHORT).show();
            }
        });

        model.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Collections.sort(cars, Car.compareToBrand);
                ReinitRecyclerView();
                Toast.makeText(getApplicationContext(),"Succesfull",Toast.LENGTH_SHORT).show();
            }
        });

        year.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Collections.sort(cars, Car.compareToYear);
                ReinitRecyclerView();
                Toast.makeText(getApplicationContext(),"Succesfull",Toast.LENGTH_SHORT).show();
            }
        });



    }
    private void ReinitRecyclerView() {
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
    }

    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new RecyclerItemViewAdaptor(this, cars,"18.04.2021","30.04.2021");
        recyclerView.setAdapter(adapter);
    }

    public void sort()
    {



    }




}