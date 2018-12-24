package com.example.john.akelny.Admin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.john.akelny.Model.Category;
import com.example.john.akelny.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class EditCategory extends AppCompatActivity {
    Spinner spinner;
    FirebaseDatabase database;
    DatabaseReference myRef;
    ArrayList<String> CategoryNames;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_category);
        spinner=(Spinner)findViewById(R.id.CategoriesEditSpinner);
        CategoryNames = new ArrayList<String>();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Categories");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Category x = snapshot.getValue(Category.class);
                    CategoryNames.add(x.CategoryName);
                }


                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(EditCategory.this,android.R.layout.simple_spinner_item, CategoryNames);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(arrayAdapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
}
