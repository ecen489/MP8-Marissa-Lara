package com.example.t18_even_more_firebase;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Intent;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button createButton;
    Button loginButton;
    EditText emailText;
    EditText passwordText;
    EditText IDText;

    FirebaseDatabase fbdb;
    DatabaseReference dbrf;

    FirebaseAuth mAuth;
    FirebaseUser user = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fbdb = FirebaseDatabase.getInstance();
        dbrf = fbdb.getReference();

        createButton=findViewById(R.id.button1);
        loginButton=findViewById(R.id.button2);
        emailText=findViewById(R.id.email);
        passwordText=findViewById(R.id.password);


        mAuth = FirebaseAuth.getInstance();
    }

    public void CreateClick(View view) {

        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();

        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){

                            Toast.makeText(getApplicationContext(),"Created Account",Toast.LENGTH_SHORT).show();
                            user = mAuth.getCurrentUser(); //The newly created user is already signed in
                        }
                        else{
                            Toast.makeText(getApplicationContext(),"Something went wrong",Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    public void LoginClick(View view) {

        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(),"Login Successful!",Toast.LENGTH_SHORT).show();
                            user = mAuth.getCurrentUser(); //The user is signed in
                        } else {
                            Toast.makeText(getApplicationContext(),"Something went wrong",Toast.LENGTH_SHORT).show();
                        }
                    }
                });


    }

    public void LogoutClick(View view) {
        mAuth.signOut();
        user =null;
        Toast.makeText(getApplicationContext(),"Logged Out",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        user = mAuth.getCurrentUser();
    }

    public void pullClick(View view){
        if (user != null){
            startActivity(new Intent(MainActivity.this, pull.class));
        }
        else{
            Toast.makeText(getApplicationContext(),"ERROR: Please log in",Toast.LENGTH_SHORT).show();
        }
    }
}
