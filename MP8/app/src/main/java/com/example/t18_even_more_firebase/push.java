package com.example.t18_even_more_firebase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
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
import java.util.HashMap;

public class push extends AppCompatActivity {

    EditText courseIDText;
    EditText subjectText;
    EditText gradeText;
    FirebaseDatabase fbdb;
    DatabaseReference dbrf;
    HashMap<String, Integer> idLookup = new HashMap<>();
    int StudentID;

    int Courseids;
    String Grades;
    String Subjects;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push);
        fbdb = FirebaseDatabase.getInstance();


        courseIDText = findViewById(R.id.CourseID);
        subjectText = findViewById(R.id.Subject);
        gradeText = findViewById(R.id.Grade);

        idLookup.put("Bart", 123);
        idLookup.put("Milhouse", 456);
        idLookup.put("Ralph", 404);
        idLookup.put("Lisa", 888);

    }



    public void AddData(View view){
        Courseids = Integer.parseInt(courseIDText.getText().toString());
        Subjects = subjectText.getText().toString();
        Grades = gradeText.getText().toString();
        StudentID = 888;
        dbrf = fbdb.getReference("simpsons/grades/");
        String key = dbrf.push().getKey();

        HashMap<String, Object> gradeHash = new HashMap<>();
        gradeHash.put("course_id", Courseids);
        gradeHash.put("course_name", Subjects);
        gradeHash.put("grade", Grades);
        gradeHash.put("student_id", StudentID);

        HashMap<String, Object> newData = new HashMap<>();
        newData.put(key, gradeHash);
        dbrf.updateChildren(newData);



    }

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            if (dataSnapshot.exists()) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Grade grade = snapshot.getValue(Grade.class);

                }
            }

        }


        @Override
        public void onCancelled(DatabaseError databaseError) {
            //log error

        }

    };

    public void radioClick(View view){

    }

    public void PushClick(View view){
        startActivity(new Intent(push.this, pull.class));
    }

    public void HomeClick(View view){ startActivity(new Intent(push.this, MainActivity.class));}
}
