package com.evansroy.todo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
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

public class LogInActivity extends AppCompatActivity {

    TextInputEditText password, email;
    Button btnLogIn;
    TextView tvNotRegistered;
    Toolbar toolbar;
    private FirebaseAuth mAuth;

    private ProgressDialog loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_log_in);

        toolbar = findViewById(R.id.toolBarLogin);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Log In");

        email = findViewById(R.id.emailEtLogIn);
        password =findViewById(R.id.passETLogIn);

        tvNotRegistered = findViewById(R.id.textView);

        mAuth = FirebaseAuth.getInstance();
        btnLogIn = findViewById(R.id.button);
        loader = new ProgressDialog(this);

        tvNotRegistered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LogInActivity.this,SignUpActivity.class));
                finish();
            }
        });

        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailLogIn = email.getText().toString().trim();
                String passLogIn = password.getText().toString().trim();

                if (emailLogIn.isEmpty()){
                    email.setError("Email is Required!!");
                    return;
                }

                if (passLogIn.isEmpty()){
                    password.setError("Password is Required!!");
                    return;
                }
                else {

                    loader.setMessage("LogIn in Progress");
                    loader.setCanceledOnTouchOutside(false);
                    loader.show();

                    mAuth.signInWithEmailAndPassword(emailLogIn,passLogIn).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                startActivity(new Intent(LogInActivity.this,HomeActivity.class));
                                finish();
                                loader.dismiss();
                            }else {
                                String error = task.getException().toString();
                                Toast.makeText(LogInActivity.this, "LogIn Failed!!" + error, Toast.LENGTH_SHORT).show();
                                loader.dismiss();
                            }
                        }
                    });
                }

            }
        });
    }
}