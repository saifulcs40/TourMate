package com.example.saiful.tourmate.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.saiful.tourmate.MainActivity;
import com.example.saiful.tourmate.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private EditText emailEt, passwordEt;
    private Button loginBtn;
    private TextView forgetPasswordTv, registerTv;
    private ProgressBar progressBar;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        myInitialization();

        myClickListener();
    }

    private void myInitialization() {

        emailEt = (EditText) findViewById(R.id.login_email_id);
        passwordEt = (EditText) findViewById(R.id.login_password_id);
        loginBtn = findViewById(R.id.login_button_id);
        forgetPasswordTv = findViewById(R.id.login_forget_pass_id);
        registerTv = (TextView)findViewById(R.id.register_text_view_id);

        progressBar = findViewById(R.id.login_progress_bar_id);
        firebaseAuth = FirebaseAuth.getInstance();
    }

    private void myClickListener() {

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEt.getText().toString().trim();
                String password = passwordEt.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    Toast.makeText(LoginActivity.this, "Email is required", Toast.LENGTH_SHORT).show();
                    emailEt.requestFocus();
                }
                if(TextUtils.isEmpty(password)){
                    Toast.makeText(LoginActivity.this, "Password can't be empty", Toast.LENGTH_SHORT).show();
                    emailEt.requestFocus();
                }

                progressBar.setVisibility(View.VISIBLE);

                //login user
                firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Toast.makeText(LoginActivity.this, "Success", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);

                        if(!task.isSuccessful()){
                            Toast.makeText(LoginActivity.this, "Invalid email or password", Toast.LENGTH_SHORT).show();
                        } else{
                            startActivity(new Intent(LoginActivity.this,MainActivity.class));
                            finish();
                        }
                    }
                });
            }
        });

        forgetPasswordTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginActivity.this, "Feature will be available soon...", Toast.LENGTH_LONG).show();
            }
        });

        registerTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
            }
        });
    }
}
