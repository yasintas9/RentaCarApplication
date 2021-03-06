package com.example.b2crentacar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private Button login;
    private EditText mail,password;
    private String Mail,Password;
    private FirebaseAuth fAuth;
    private TextView goSignUp;
    private ImageButton goSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mail=findViewById(R.id.txtMail2);
        password=findViewById(R.id.txtPassword2);
        login=findViewById(R.id.btnLogin);
        fAuth = FirebaseAuth.getInstance();
        goSignUp=findViewById(R.id.textViewtoSignUp);

        goSearch=findViewById(R.id.goSearchPage);

        goSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,SearchAndFilterActivity.class);
                startActivity(intent);
                finish();
            }
        });

        goSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
                //finish();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Mail=mail.getText().toString();
                Password=password.getText().toString();

                if(Mail.equals("admin") && Password.equals("admin")){

                    Intent intent = new Intent(LoginActivity.this,AdminActivity.class);
                    startActivity(intent);
                    //finish();
                }
                else if(TextUtils.isEmpty(Mail) || TextUtils.isEmpty(Password) || Password.length()<6){
                    Toast.makeText(LoginActivity.this, "All the Information Are Required and CHECK the password length", Toast.LENGTH_SHORT).show();
                }
                else{
                    fAuth.signInWithEmailAndPassword(Mail,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Intent intent = new Intent(LoginActivity.this, SearchAndFilterActivity.class);
                                startActivity(intent);
                                finish();
                            }
                            else {
                                Toast.makeText(LoginActivity.this, "Check Username or password", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }


            }
        });

    }
}