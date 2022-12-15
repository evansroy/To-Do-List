package com.evansroy.todo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity {

    TextView tvDontHaveAccount;
    Toolbar toolbar;
    TextInputEditText fullName,emailSignUp,passwordSignup,confirmPassword;
    Button signUp;
    private ProgressDialog loader;

    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_up);


        toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Registration");

        tvDontHaveAccount = findViewById(R.id.textView);
        fullName =findViewById(R.id.fullNameEt);
        emailSignUp = findViewById(R.id.emailEt);
        passwordSignup = findViewById(R.id.passET);
        confirmPassword = findViewById(R.id.confirmPassEt);

        signUp = findViewById(R.id.buttonSignUp);

        mAuth = FirebaseAuth.getInstance();
        loader = new ProgressDialog(this);


        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String fullNames = fullName.getText().toString().trim();
                String password = passwordSignup.getText().toString().trim();
                String confirmPass = confirmPassword.getText().toString().trim();
                String email = emailSignUp.getText().toString().trim();

                if (TextUtils.isEmpty(fullNames)){
                    fullName.setError("Full Names Are Required!!");
                    return;
                }
                if (TextUtils.isEmpty(email)){
                    emailSignUp.setError("Email is Required!!");
                    return;
                }
                if (TextUtils.isEmpty(password)){
                    passwordSignup.setError("Password is Required!!");
                    return;
                }
                if (TextUtils.isEmpty(confirmPass)){
                    confirmPassword.setError("Confirm password is Required!!");
                    return;
                }

                if (password.equals(confirmPass)){


                    loader.setMessage("Registration in Progress Please ");
                    loader.setCanceledOnTouchOutside(false);
                    loader.show();

                    mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()){
                                startActivity(new Intent(SignUpActivity.this,HomeActivity.class));
                                finish();
                                loader.dismiss();
                            }else{

                                String error = task.getException().toString();
                                Toast.makeText(SignUpActivity.this, "Registration Failed!!" + error, Toast.LENGTH_SHORT).show();
                                loader.dismiss();
                            }

                        }
                    });
                }
//                else {
//
//                    Toast.makeText(SignUpActivity.this, "Passwords Don't Match!!", Toast.LENGTH_SHORT).show();
//                    return;
//
//
//                }



            }
        });

        tvDontHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUpActivity.this, LogInActivity.class));
                finish();
            }
        });
    }
}