package com.example.b2crentacar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class AddCarActivity extends AppCompatActivity {
    private Uri filePath;
    private final int PICK_IMAGE_REQUEST = 71;
    private ImageView carImage;
    private Button addCar;
    private EditText plateNumber,brand,model,price,year;
    private String PlateNumber,Brand,Model,Price,Year;
    private RadioGroup radioGroupType, radioGroupGear, radioGroupFuelType;
    private RadioButton RadioButtonType, RadioButtonGear, RadioButtonFuelType;
    private String RadioTypeText, RadioGearText, RadioFuelTypeText;
    private Button addCarBtn;
    private DatabaseReference database;
    private ImageButton backtoAdmin;
    FirebaseStorage storage;
    StorageReference storageReference;

    private ArrayList<RentDate> dates=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_car);
        carImage=findViewById(R.id.carImage);
        plateNumber = findViewById(R.id.txtPlateNo);
        brand = findViewById(R.id.txtBrand);
        model = findViewById(R.id.txtModel);
        price = findViewById(R.id.txtPrice);
        year = findViewById(R.id.txtYear);
        radioGroupType = findViewById(R.id.radioGroup3);
        radioGroupGear = findViewById(R.id.radioGroup);
        radioGroupFuelType = findViewById(R.id.radioGroup5);
        addCarBtn = (Button) findViewById(R.id.btnAddCar);
        database = FirebaseDatabase.getInstance().getReference();
        backtoAdmin = findViewById(R.id.backToAdminMenu);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        backtoAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),AdminActivity.class);
                startActivity(intent);
                finish();
            }
        });

        carImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });

        addCarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlateNumber = plateNumber.getText().toString();
                Brand = brand.getText().toString();
                Model = model.getText().toString();
                Price = price.getText().toString();
                Year = year.getText().toString();

                int selectedIdType = radioGroupType.getCheckedRadioButtonId();
                RadioButtonType = findViewById(selectedIdType);
                RadioTypeText = RadioButtonType.getText().toString();

                int selectedIdGear = radioGroupGear.getCheckedRadioButtonId();
                RadioButtonGear = findViewById(selectedIdGear);
                RadioGearText = RadioButtonGear.getText().toString();

                int selectedIdFuelType = radioGroupFuelType.getCheckedRadioButtonId();
                RadioButtonFuelType = findViewById(selectedIdFuelType);
                RadioFuelTypeText = RadioButtonFuelType.getText().toString();

                if(checkInput()){

                    database.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()){
                                if(snapshot.child("Cars").hasChild(PlateNumber)){
                                    Toast.makeText(AddCarActivity.this,"The Car is already added",Toast.LENGTH_SHORT).show();
                                }else{

                                    final ProgressDialog progressDialog = new ProgressDialog(AddCarActivity.this);
                                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                                    progressDialog.setTitle("Preparing the Product...");
                                    progressDialog.setCancelable(false);
                                    progressDialog.show();

                                    uploadImage();

                                    Handler handler = new Handler();
                                    handler.postDelayed(new Runnable() {
                                        public void run() {
                                            database.child("Cars").child(PlateNumber).child("PlateNumber").setValue(PlateNumber);
                                            database.child("Cars").child(PlateNumber).child("Brand").setValue(Brand);
                                            database.child("Cars").child(PlateNumber).child("Model").setValue(Model);
                                            database.child("Cars").child(PlateNumber).child("Price").setValue(Price);
                                            database.child("Cars").child(PlateNumber).child("Year").setValue(Year);
                                            database.child("Cars").child(PlateNumber).child("Type").setValue(RadioTypeText);
                                            database.child("Cars").child(PlateNumber).child("Gear").setValue(RadioGearText);
                                            database.child("Cars").child(PlateNumber).child("Fueltype").setValue(RadioFuelTypeText);

                                            ArrayList<Rent> rents=new ArrayList<>();
                                            rents.add(new Rent("","","","",""));

                                            database.child("Rents").child(PlateNumber).setValue(rents);

                                            progressDialog.dismiss();
                                            Toast.makeText(AddCarActivity.this,"Car is added",Toast.LENGTH_SHORT).show();
                                            Intent intent=new Intent(AddCarActivity.this,AdminActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                    }, 2000);
                                }
                            }
                        }


                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }

            }
        });

    }

    private boolean checkInput(){
        if(!TextUtils.isEmpty(PlateNumber)){
            if(!TextUtils.isEmpty(Brand)){
                if(!TextUtils.isEmpty(Model)){
                    if(!TextUtils.isEmpty(Price)){
                        if(!TextUtils.isEmpty(Year)){
                            return true;
                        }else{
                            year.setError("Please Enter Year");
                        }
                    }else{
                        price.setError("Please Enter Price");
                    }
                }else{
                    model.setError("Please Enter Model");
                }
            }else{
                brand.setError("Please Enter Brand");
            }
        }else{
            plateNumber.setError("Please Enter Plate Number");
        }

        return false;

    }

    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(AddCarActivity.this.getContentResolver(), filePath);
                carImage.setImageBitmap(bitmap);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    private void uploadImage() {

        if (filePath != null) {

            StorageReference ref = storageReference.child(PlateNumber);
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            StorageReference newReference = FirebaseStorage.getInstance().getReference(PlateNumber);
                            newReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String downloadUrl = uri.toString();
                                    database.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            if (snapshot.exists()) {
                                                database.child("Cars").child(PlateNumber).child("Photo").setValue(downloadUrl);
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {
                                        }
                                    });


                                }
                            });

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {


                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                        }
                    });
        }

    }

}