package com.example.john.akelny.Admin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import com.example.john.akelny.Model.Category;
import com.example.john.akelny.Model.Resturant;
import com.example.john.akelny.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AddNewFood extends AppCompatActivity {
    ImageView image;
    FirebaseDatabase database;
    DatabaseReference myRef;
    ArrayList<String> ResturantName;
    ArrayList<Resturant> resturants;

    ArrayList<String> CategoryNames;
    ArrayList<Category> Categories;
    Spinner CategoriesSpinner,ResturantsSpinner;
    ArrayAdapter arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_food);
        ResturantsSpinner=(Spinner)findViewById(R.id.spinnerResturantNameAddFood);
        image = (ImageView)findViewById(R.id.NextFirstPageInFood);
        CategoriesSpinner=(Spinner)findViewById(R.id.spinner1);

        ResturantName = new ArrayList<String>();
        resturants= new ArrayList<Resturant>();
        image.setOnClickListener(new View.OnClickListener() {
                                     @Override
                                     public void onClick(View v) {
                                         Intent intent = new Intent(AddNewFood.this, AddNewFood2.class);
                                         startActivity(intent);
                                     }
                                 });

            database = FirebaseDatabase.getInstance();
            myRef = database.getReference("Restaurants");
             myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                        Resturant x = snapshot.getValue(Resturant.class);
                        ResturantName.add(x.ResuturantName);
                        resturants.add(x);

                    }
                    arrayAdapter = new ArrayAdapter<String>(AddNewFood.this, android.R.layout.simple_spinner_item, ResturantName);
                    arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    ResturantsSpinner.setAdapter(arrayAdapter);
                    }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        CategoryNames= new ArrayList<String>();
        Categories = new ArrayList<Category>();
        myRef = database.getReference("Categories");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    Category x = snapshot.getValue(Category.class);
                    CategoryNames.add(x.CategoryName);
                    Categories.add(x);

                }
                arrayAdapter = new ArrayAdapter<String>(AddNewFood.this, android.R.layout.simple_spinner_item, CategoryNames);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                CategoriesSpinner.setAdapter(arrayAdapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

}
