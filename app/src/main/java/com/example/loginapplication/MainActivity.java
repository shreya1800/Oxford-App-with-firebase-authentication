package com.example.loginapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import javax.xml.validation.Validator;

public class MainActivity extends AppCompatActivity {

    private EditText eName;
    private EditText ePassword;
    private Button eLogin;
    private TextView userRegistration;
    private TextView tvAttempt;
    private int counter = 5;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        eName = (EditText)findViewById(R.id.etName);
        ePassword = (EditText)findViewById(R.id.etPassword);
        eLogin = (Button)findViewById(R.id.btnLogin);
        userRegistration = (TextView)findViewById(R.id.tvRegister);

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

        FirebaseUser user = firebaseAuth.getCurrentUser();

       /* if(user!=null){
            finish();
            startActivity(new Intent(MainActivity.this,Second_activity.class));
        }*/

        eLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate(eName.getText().toString(), ePassword.getText().toString());
            }
        });



        userRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RegistrationActivity.class));

            }
        });
    }
    private void validate(String eName, String ePassword){

        progressDialog.setMessage("Rukho zara, sabar Karo!");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(eName, ePassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    progressDialog.dismiss();
                    Toast.makeText(MainActivity.this,"Login Successful!",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MainActivity.this,Second_activity.class));
                }else{
                    Toast.makeText(MainActivity.this,"Login Failed!",Toast.LENGTH_SHORT).show();
                    counter--;
                    tvAttempt.setText("No. of Attempts remaining:" + counter);
                    progressDialog.dismiss();
                    if (counter==0){
                        eLogin.setEnabled(false);
                    }
                }

            }
        });

    }

}