package com.example.john.akelny.Admin;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.john.akelny.Model.Category;
import com.example.john.akelny.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class AddCategory extends Activity {
    EditText CategoryName;
    Button AddCategorytoDB;
    Category category;
    FirebaseDatabase database;
    DatabaseReference myRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);
        CategoryName = (EditText) findViewById(R.id.CategoryName);
        AddCategorytoDB = (Button) findViewById(R.id.AddCategoryToDataBase);
        AddCategorytoDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                category = new Category(CategoryName.getText().toString());
                database = FirebaseDatabase.getInstance();
                myRef = database.getReference("Categories");
                String key = myRef.push().getKey();
                myRef.child(key).setValue(category);


            }
        });
    }
}
