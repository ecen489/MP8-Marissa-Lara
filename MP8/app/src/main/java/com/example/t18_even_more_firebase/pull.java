package com.example.t18_even_more_firebase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.content.Intent;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class pull extends AppCompatActivity {

    FirebaseDatabase fbdb;
    DatabaseReference dbrf;
    EditText IDText;
    ListView simpleList;
    ArrayList<Grade> mylist = new ArrayList<Grade>();
    ArrayAdapter<Grade> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pull);

        fbdb = FirebaseDatabase.getInstance();
        dbrf = fbdb.getReference();
        IDText = findViewById(R.id.userID);
        System.out.println("Hello bud");
        System.out.println(dbrf);
        simpleList = (ListView)findViewById(R.id.listview);
        arrayAdapter = new ArrayAdapter<Grade>(
                this, android.R.layout.simple_list_item_1,mylist
        );
        simpleList.setAdapter(arrayAdapter);
    }

      public void gradeClick(View view) {

            int studID = Integer.parseInt(IDText.getText().toString());

            DatabaseReference gradeKey = dbrf.child("simpsons/grades/");

           Query query = gradeKey.orderByChild("student_id").equalTo(studID);
           query.addListenerForSingleValueEvent(valueEventListener);

        }


    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            if (dataSnapshot.exists()) {
                //Toast.makeText(getApplicationContext(),"listening",Toast.LENGTH_SHORT).show();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Grade grade = snapshot.getValue(Grade.class);
                    mylist.add(grade);


                }
                arrayAdapter.notifyDataSetChanged();
            }

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            //log error

        }
    };

    public void pushClick(View view){
        startActivity(new Intent(pull.this, push.class));
    }

    public void homeClick(View view){
        startActivity(new Intent(pull.this, MainActivity.class));
    }
}
