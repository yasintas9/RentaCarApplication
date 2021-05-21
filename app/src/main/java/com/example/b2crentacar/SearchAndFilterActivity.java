package com.example.b2crentacar;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mawippel.validator.OverlappingVerificator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

public class SearchAndFilterActivity extends AppCompatActivity {

    Button pickStart, pickEnd, search,goLogin;
    DatePickerDialog picker1, picker2;
    EditText startDate, endDate;
    private static ArrayList<Car> cars = new ArrayList<>();
    private static ArrayList<Car> filteredcars = new ArrayList<>();
    private EditText model, brand;
    private RadioGroup radioGroupType, radioGroupGear, radioGroupFuelType;
    private RadioButton RadioButtonType, RadioButtonGear, RadioButtonFuelType;
    private FirebaseAuth fAuth;
    private TextView textView5;
    private ImageButton gologout,goReservations;

    private DatabaseReference database;
    FirebaseStorage storage;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_and_filter);

        fAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();


        radioGroupType = findViewById(R.id.radioGroupType);
        radioGroupGear = findViewById(R.id.radioGroupGear);
        radioGroupFuelType = findViewById(R.id.radioGroupFuelType);
        model = findViewById(R.id.txtModel2);
        brand = findViewById(R.id.txtBrand2);

        pickEnd = findViewById(R.id.pickEnd);
        pickStart = findViewById(R.id.pickStart);
        startDate = findViewById(R.id.txtstartDate);
        endDate = findViewById(R.id.txtReturnDate);
        search = findViewById(R.id.btnSearch);
        goLogin=findViewById(R.id.btngoLogin);
        textView5=findViewById(R.id.textView5);
        gologout=findViewById(R.id.gologout);
        goReservations=findViewById(R.id.reservationPage);

        cars.clear();

        if (fAuth.getCurrentUser()!=null)
        {
                textView5.setEnabled(false);
                textView5.setVisibility(View.INVISIBLE);

                goLogin.setEnabled(false);
                goLogin.setVisibility(View.INVISIBLE);



        }
        else {
            goReservations.setEnabled(false);
            goReservations.setVisibility(View.INVISIBLE);
            gologout.setEnabled(false);
            gologout.setVisibility(View.INVISIBLE);


        }

        goReservations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),ReservationActivity.class);
                startActivity(intent);
                finish();
            }
        });

        gologout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fAuth.signOut();
                Intent intent=new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        pickEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);

                picker2 = new DatePickerDialog(SearchAndFilterActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                endDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                picker2.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                picker2.show();
            }
        });

        pickStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);

                picker1 = new DatePickerDialog(SearchAndFilterActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                startDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                picker1.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                picker1.show();
            }
        });


        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String RadioTypeText, RadioGearText, RadioFuelTypeText;
                String carModel = model.getText().toString();
                String carBrand = brand.getText().toString();

                int selectedIdType = radioGroupType.getCheckedRadioButtonId();
                RadioButtonType = findViewById(selectedIdType);
                RadioTypeText = RadioButtonType.getText().toString();

                int selectedIdGear = radioGroupGear.getCheckedRadioButtonId();
                RadioButtonGear = findViewById(selectedIdGear);
                RadioGearText = RadioButtonGear.getText().toString();

                int selectedIdFuelType = radioGroupFuelType.getCheckedRadioButtonId();
                RadioButtonFuelType = findViewById(selectedIdFuelType);
                RadioFuelTypeText = RadioButtonFuelType.getText().toString();

                cars.clear();
                filteredcars.clear();

                database.child("Cars").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                            Car car1 = postSnapshot.getValue(Car.class);
                            cars.add(car1);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                final ProgressDialog progressDialog = new ProgressDialog(SearchAndFilterActivity.this);
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.setTitle("Searching the cars...");
                progressDialog.setCancelable(false);
                progressDialog.show();

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        for (Car car1 : cars) {
                            if (car1.getType().equals(RadioTypeText) && car1.getFueltype().equals(RadioFuelTypeText) && car1.getGear().equals(RadioGearText)) {
                                filteredcars.add(car1);
                            }
                        }
                        if (!filteredcars.isEmpty()) {

                            database.child("Rents").addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {

                          /*  for (int i=0;i<filteredcars.size();i++){

                                if (snapshot.hasChildren())
                                {
                                    for (int j=0;j<snapshot.child(filteredcars.get(i).getPlateNumber()).getChildrenCount();j++)
                                    {

                                        String start=snapshot.child(filteredcars.get(i).getPlateNumber()).child(String.valueOf(j)).child("startDate").getValue().toString();
                                        String end=snapshot.child(filteredcars.get(i).getPlateNumber()).child(String.valueOf(j)).child("endDate").getValue().toString();

                                        String date1[]=start.split("/");
                                        String date2[]=end.split("/");
                                        String date3[]=startDate.getText().toString().split("/");
                                        String date4[]=endDate.getText().toString().split("/");

                                        LocalDate comparableInit = LocalDate.of(Integer.parseInt(date1[2]), Integer.parseInt(date1[1]), Integer.parseInt(date1[0]));
                                        LocalDate comparableEnd = LocalDate.of(Integer.parseInt(date2[2]), Integer.parseInt(date2[1]), Integer.parseInt(date2[0]));
                                        LocalDate toCompareInit = LocalDate.of(Integer.parseInt(date3[2]), Integer.parseInt(date3[1]), Integer.parseInt(date3[0]));
                                        LocalDate toCompareEnd = LocalDate.of(Integer.parseInt(date4[2]), Integer.parseInt(date4[1]), Integer.parseInt(date4[0]));

                                        boolean isOverlap = OverlappingVerificator.isOverlap(comparableInit, comparableEnd, toCompareInit, toCompareEnd);


                                        if (isOverlap)
                                        {
                                            filteredcars.remove(j);
                                        }
                                    }
                                }
                            }*/
                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });

                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(),"Succesfull",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(SearchAndFilterActivity.this, MainActivity.class);
                            intent.putExtra("car_list", new Gson().toJson(filteredcars));
                            intent.putExtra("startDate",startDate.getText().toString());
                            intent.putExtra("endDate",endDate.getText().toString());
                            startActivity(intent);


                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "There is no such car that fits your selections", Toast.LENGTH_SHORT).show();
                        }


                    }
                }, 2000);

            }
        });

        goLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    Intent intent=new Intent(getApplicationContext(),LoginActivity.class);
                    startActivity(intent);

            }
        });


    }
}