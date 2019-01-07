package com.example.john.akelny.Admin;

import android.app.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.john.akelny.Model.Category;
import com.example.john.akelny.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class EditCategory extends Activity {
    Spinner spinner;
    FirebaseDatabase database;
    DatabaseReference myRef;
    ArrayList<String> CategoryNames;
    EditText NewName;
    Button EditButton;
    String itemselecteed;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_category);
        spinner=(Spinner)findViewById(R.id.CategoriesEditSpinner);
        NewName=(EditText)findViewById(R.id.CategoryNewName);
        EditButton = (Button)findViewById(R.id.EditCategoryToDatabase);
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

        EditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemselecteed = spinner.getSelectedItem().toString();
                myRef = database.getReference("Categories");
                myRef.orderByChild("CategoryName").equalTo(itemselecteed).addValueEventListener(new ValueEventListener(){
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot datas: dataSnapshot.getChildren()){
                            String keys=datas.getKey().toString();
                            Category category = new Category(NewName.getText().toString());
                            myRef.child(keys).setValue(category);


                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }

                });

            }
        });

}
}
