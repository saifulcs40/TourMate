package com.example.saiful.tourmate.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.saiful.tourmate.MainActivity;
import com.example.saiful.tourmate.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    private EditText fullNameEt, emailEt, passwordEt;
    private Button registerBtn;
    private TextView loginTv;
    private ImageView backBtn;
    private ProgressBar progressBar;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        myInitialization();
        myClickListener();
    }

    private void myInitialization() {

        fullNameEt = findViewById(R.id.register_full_name_id);
        emailEt = findViewById(R.id.register_email_id);
        passwordEt = findViewById(R.id.register_password_id);
        registerBtn = findViewById(R.id.register_button_id);
        loginTv = findViewById(R.id.register_login_btn_id);
        backBtn = findViewById(R.id.back_press_btn_id);
        progressBar = findViewById(R.id.progress_bar_id);

        firebaseAuth = FirebaseAuth.getInstance();
    }

    private void myClickListener() {

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullName = fullNameEt.getText().toString().trim();
                String email = emailEt.getText().toString().trim();
                String password = passwordEt.getText().toString().trim();

                if(TextUtils.isEmpty(fullName)){
                    Toast.makeText(RegisterActivity.this, "Enter your full name", Toast.LENGTH_LONG).show();
                }

                if(TextUtils.isEmpty(email)){
                    Toast.makeText(RegisterActivity.this, "Enter email address", Toast.LENGTH_LONG).show();
                }

                if(password.length()<6){
                    Toast.makeText(RegisterActivity.this, "Password too short. Enter a password of minimum 6 digits of length", Toast.LENGTH_LONG).show();
                }

                progressBar.setVisibility(View.VISIBLE);

                //create user
                firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Toast.makeText(RegisterActivity.this, "Success", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);

                        if (!task.isSuccessful()) {
                            Toast.makeText(RegisterActivity.this, "Authentication failed!", Toast.LENGTH_SHORT).show();
                        } else {
                            startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                            finish();
                        }
                    }
                });
            }
        });

        loginTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
            }
        });
    }
}
