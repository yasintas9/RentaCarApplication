package com.example.b2crentacar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ReservationActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerItemReservedViewAdapter recyclerItemReservedViewAdapter;
    private DatabaseReference database;
    private FirebaseAuth fAuth;

    private ImageButton goHome;
    private ArrayList<Reservation> reservations=new ArrayList<>();
    private ArrayList<Car> cars=new ArrayList<>();
    private ArrayList<Dates> dates=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);
        recyclerView=findViewById(R.id.reservedCarsRecycler);
        goHome=findViewById(R.id.goHome);

        fAuth=FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference();

        final ProgressDialog progressDialog = new ProgressDialog(ReservationActivity.this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setTitle("Preparing the reservations...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        getReservations();
        getCars();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                initRecyclerView();
                progressDialog.dismiss();
            }
        }, 2000);

        goHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReservationActivity.this,SearchAndFilterActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerItemReservedViewAdapter = new RecyclerItemReservedViewAdapter(this,cars,dates);
        recyclerView.setAdapter(recyclerItemReservedViewAdapter);
    }
    public void getReservations(){

        database.child("Reservations").child(fAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Reservation reservation = postSnapshot.getValue(Reservation.class);
                    reservations.add(reservation);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void getCars(){
            database.child("Cars").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    for (DataSnapshot postSnapshot : snapshot.getChildren()) {

                        Car car1 = postSnapshot.getValue(Car.class);
                        for (Reservation r:reservations)
                        {
                            if (r.getPlateNumber().equals(car1.getPlateNumber()))
                            {
                                cars.add(car1);
                                Dates d=new Dates(r.getPickupDate(),r.getDropoffDate());
                                dates.add(d);
                            }
                        }
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

    }


}