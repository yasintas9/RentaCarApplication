package com.example.b2crentacar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Car> cars=new ArrayList<>();
    private RecyclerItemViewAdaptor adapter;
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView=findViewById(R.id.recyclerViewCars);


        Car car1 =new Car("1231313132","Renault","Clio","250TL","2020","Hatchback","Diesel","Manuel","https://cdn.euroncap.com/media/57972/renault-clio.png?mode=crop&width=359&height=235");
        Car car2 =new Car("1231313133","Renault","Clio","280TL","2020","Hatchback","Diesel","Automatic","https://cdn.euroncap.com/media/57972/renault-clio.png?mode=crop&width=359&height=235");
        Car car3 =new Car("1231313134","Renault","Clio","350TL","2021","Hatchback","Diesel","Automatic","https://cdn.euroncap.com/media/57972/renault-clio.png?mode=crop&width=359&height=235");
        cars.add(car1);

        cars.add(car2);
        cars.add(car3);

        initRecyclerView();

    }

    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new RecyclerItemViewAdaptor(this, cars,"18.04.2021","30.04.2021");
        recyclerView.setAdapter(adapter);
    }




}