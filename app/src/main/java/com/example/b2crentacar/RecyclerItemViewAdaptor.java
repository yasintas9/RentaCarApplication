package com.example.b2crentacar;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.net.URL;
import java.util.ArrayList;
import java.util.UUID;

import static android.app.Activity.RESULT_OK;

public class RecyclerItemViewAdaptor extends RecyclerView.Adapter<RecyclerItemViewAdaptor.ViewHolder> {

    private final int PICK_IMAGE_REQUEST = 71;
    private Context context;
    private TextView type,fueltype,gear,brandAndModel,price,year,pickupdate,dropoffdate;
    private String pickupDate,dropoffDate;
    ArrayList<Car> cars=new ArrayList<>();
    private DatabaseReference database;
    private FirebaseAuth fAuth;

    public RecyclerItemViewAdaptor(Context context, ArrayList<Car> cars,String pickupDate,String dropoffDate) {
        this.cars = cars;
        this.context = context;
        this.pickupDate=pickupDate;
        this.dropoffDate=dropoffDate;
        database = FirebaseDatabase.getInstance().getReference();
        fAuth=FirebaseAuth.getInstance();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cars_card_view,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context)
                .asBitmap()
                .load(cars.get(position).getPhoto())
                .into(holder.itemImage);

        type.setText(cars.get(position).getType());
        gear.setText(cars.get(position).getGear());
        fueltype.setText(cars.get(position).getFueltype());
        brandAndModel.setText(cars.get(position).getBrand()+" "+cars.get(position).getModel());
        price.setText(cars.get(position).getPrice());
        year.setText(cars.get(position).getYear());
        pickupdate.setText(pickupDate);
        dropoffdate.setText(dropoffDate);

        holder.select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Confirm");
                builder.setMessage("Are you sure? You are going to reserve the car");
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (fAuth.getCurrentUser()!=null)
                        {
                            database.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if (snapshot.exists()){

                                        final ProgressDialog progressDialog = new ProgressDialog(context);
                                        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                                        progressDialog.setTitle("Preparing the Product...");
                                        progressDialog.setCancelable(false);
                                        progressDialog.show();

                                        Handler handler = new Handler();
                                        handler.postDelayed(new Runnable() {
                                            public void run() {
                                                String random=UUID.randomUUID().toString();
                                                database.child("Reservations").child(fAuth.getCurrentUser().getUid()).child(random).child("userId").setValue(fAuth.getCurrentUser().getUid());
                                                database.child("Reservations").child(fAuth.getCurrentUser().getUid()).child(random).child("PlateNumber").setValue(cars.get(position).getPlateNumber());
                                                database.child("Reservations").child(fAuth.getCurrentUser().getUid()).child(random).child("PickupDate").setValue(pickupDate);
                                                database.child("Reservations").child(fAuth.getCurrentUser().getUid()).child(random).child("DropoffDate").setValue(dropoffDate);


                                                dialog.dismiss();
                                                Toast.makeText(context,"Car is reserved",Toast.LENGTH_SHORT).show();
                                                Intent intent=new Intent(context,ReservationActivity.class);
                                                context.startActivity(intent);
                                                ((Activity)context).finish();

                                            }
                                        }, 1000);
                                    }
                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }
                        else {
                            Toast.makeText(context,"You have to login first",Toast.LENGTH_SHORT).show();
                        }

                    }
                });

                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();

            }
        });

    }

    @Override
    public int getItemCount() {
        return cars.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView itemImage;
        TextView select;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemImage=itemView.findViewById(R.id.itemImage);
            type=itemView.findViewById(R.id.type);
            gear=itemView.findViewById(R.id.gear);
            fueltype=itemView.findViewById(R.id.fuelType);
            brandAndModel=itemView.findViewById(R.id.brandAndModel);
            price=itemView.findViewById(R.id.price);
            year=itemView.findViewById(R.id.year);
            pickupdate=itemView.findViewById(R.id.pickupDate);
            dropoffdate=itemView.findViewById(R.id.dropoffDate);
            select=itemView.findViewById(R.id.selectCar);

        }
    }



}
