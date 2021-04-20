package com.example.b2crentacar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

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
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static ArrayList<Car> cars=new ArrayList<>();
    private RecyclerItemViewAdaptor adapter;
    private RecyclerView recyclerView;
    private DatabaseReference database;
    FirebaseStorage storage;
    StorageReference storageReference;
    static String brand,model,price,year,type,fueltype,gear,platenum,imageurl;
    private ArrayList<String> list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent i = getIntent();
        String platenumber = i.getStringExtra("plateNumber");

        recyclerView=findViewById(R.id.recyclerViewCars);

        database = FirebaseDatabase.getInstance().getReference();

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();




        database.child("Cars").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    Car car1 = postSnapshot.getValue(Car.class);
                    cars.add(car1);



                }


            /*     platenum = snapshot.child("Plate Number").getValue().toString();
                 brand = snapshot.child("Brand").getValue().toString();
                 model =snapshot.child("Model").getValue().toString();
                 price =snapshot.child("Price").getValue().toString();
                 year =snapshot.child("Year").getValue().toString();
                 type = snapshot.child("Type").getValue().toString();
                 fueltype =snapshot.child("Fuel Type").getValue().toString();
                 gear = snapshot.child("Gear").getValue().toString();   */
                //imageurl = snapshot.child("Image Url").getValue().toString();

                //  Car c = new Car(platenum,brand,model,price,year,type,fueltype,gear,"");
                // cars.add(c);
                initRecyclerView();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new RecyclerItemViewAdaptor(this, cars,"18.04.2021","30.04.2021");
        recyclerView.setAdapter(adapter);
    }




}