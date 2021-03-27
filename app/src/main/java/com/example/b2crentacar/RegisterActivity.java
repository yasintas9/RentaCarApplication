package com.example.b2crentacar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Date;

public class RegisterActivity extends AppCompatActivity {
    private Button signup;
    private EditText id,name,surname,phone,birthDate,mail,password,confirmPassword;
    private String Id,Name,Surname,Phone,BirtDate,Mail,Password,ConfirmPassword;
    private  FirebaseAuth fAuth;
    private  DatabaseReference db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        signup=findViewById(R.id.btnSignup);

        id=findViewById(R.id.txtIdno);
        name=findViewById(R.id.txtName);
        surname=findViewById(R.id.txtSurname);
        phone=findViewById(R.id.txtPhone);
        birthDate=findViewById(R.id.txtBirth);
        mail=findViewById(R.id.txtMail);
        password=findViewById(R.id.txtPassword);
        confirmPassword=findViewById(R.id.txtPasswordConfirm);
        fAuth= FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance().getReference();


    }

     public void signupp(View view){


                 Id=id.getText().toString();
                 Name=name.getText().toString();
                 Surname=surname.getText().toString();
                 Phone=phone.getText().toString();
                 BirtDate=birthDate.getText().toString();
                 Mail=mail.getText().toString();
                 Password=password.getText().toString();
                 ConfirmPassword=confirmPassword.getText().toString();

                 if (!TextUtils.isEmpty(Id) && !TextUtils.isEmpty(Name) && !TextUtils.isEmpty(Surname) && !TextUtils.isEmpty(Phone) && !TextUtils.isEmpty(BirtDate)
                         && !TextUtils.isEmpty(Mail) && !TextUtils.isEmpty(Password) &&!TextUtils.isEmpty(ConfirmPassword)) {
                     if ((Password.length()>=6) && (ConfirmPassword.length()>=6)) {
                         if (Password.equals(ConfirmPassword)) {
                             if (validateEmail(mail)) {


                                 fAuth.createUserWithEmailAndPassword(Mail, Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                     @Override
                                     public void onComplete(@NonNull Task<AuthResult> task) {
                                         if (task.isSuccessful()) {
                                             Toast.makeText(RegisterActivity.this, "User Created", Toast.LENGTH_LONG).show();
                                             db.child("users").child(Id).child("id").setValue(Id);
                                             db.child("users").child(Id).child("name").setValue(Name);
                                             db.child("users").child(Id).child("surname").setValue(Surname);
                                             db.child("users").child(Id).child("phone").setValue(Phone);
                                             db.child("users").child(Id).child("birthday").setValue(BirtDate);
                                             db.child("users").child(Id).child("email").setValue(Mail);
                                             fAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                 @Override
                                                 public void onComplete(@NonNull Task<Void> task) {
                                                     if (task.isSuccessful()) {
                                                         Toast.makeText(RegisterActivity.this, "Verify your email address", Toast.LENGTH_LONG).show();
                                                     }
                                                 }
                                             });
                                             Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                             startActivity(intent);
                                         }
                                     }
                                 });


                             } else {

                             }
                         } else {
                             Toast.makeText(RegisterActivity.this, "Passwords are not EQUAL!!", Toast.LENGTH_LONG).show();
                         }
                     }else{
                         Toast.makeText(RegisterActivity.this, "Check Your Passwords  ", Toast.LENGTH_LONG).show();
                     }
                     }else {
                         Toast.makeText(RegisterActivity.this, "Check Your Information",Toast.LENGTH_LONG).show();
                     }


                 }











    private boolean validateEmail(EditText email){
        String emailinput = email.getText().toString();

        if(!emailinput.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(emailinput).matches()){

            return true;
        }else{
            Toast.makeText(this,"Invalid Email Address",Toast.LENGTH_SHORT).show();
            return false;
        }
    }


    public boolean checkDate(){
        String[] birth= birthDate.getText().toString().split("/");
        int now= Calendar.getInstance().get(Calendar.YEAR);

        int month= Integer.parseInt(birth[0]);
        int day= Integer.parseInt(birth[1]);
        int year= Integer.parseInt(birth[2]);

        if (birth[2].length()==4  &&year<= now-18 && month>0 && month<13 && day>0 && day<=31){
            return true;
        }
        else{
            return false;
        }
    }


    public boolean checkPassword(){
        if (Password.equals(confirmPassword)){
            return true;
        }
        else{
            return false;
        }
    }
    public boolean checkAge(){
        String[] date=BirtDate.split("/");
        id.setText(date[2]);

        if (true){
            return true;
        }
        else{
            return false;
        }
    }

}