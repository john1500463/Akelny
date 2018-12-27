package com.example.john.akelny.Admin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.john.akelny.Model.Category;
import com.example.john.akelny.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RemoveCategory extends AppCompatActivity {
    Spinner spinner;
    FirebaseDatabase database;
    DatabaseReference myRef;
    ArrayList<String> CategoryNames;
    Button DeleteCategoryToDatabase;
    String itemselecteed;
    ArrayAdapter<String> arrayAdapter;
    boolean flag=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_category);
        spinner=(Spinner)findViewById(R.id.CategoriesRemoveSpinner);
        DeleteCategoryToDatabase =(Button)findViewById(R.id.DeleteCategoryToDatabase);

        CategoryNames = new ArrayList<String>();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Categories");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    if (flag==true){
                        break;

                    }
                    else {
                        Category x = snapshot.getValue(Category.class);
                        CategoryNames.add(x.CategoryName);
                    }

                }
                flag=true;
                SpinnerRead();



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
            DeleteCategoryToDatabase.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemselecteed = spinner.getSelectedItem().toString();
                    myRef = database.getReference("Categories");
                    myRef.orderByChild("CategoryName").equalTo(itemselecteed).addValueEventListener(new ValueEventListener(){
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for(DataSnapshot datas: dataSnapshot.getChildren()){
                                String keys=datas.getKey().toString();
                                myRef.child(keys).removeValue();
                                for (int i =0;i<CategoryNames.size();i++){
                                    if(CategoryNames.get(i).equals(itemselecteed)){
                                        CategoryNames.remove(i);
                                        SpinnerRead();
                                        break;
                                    }

                                }


                            }

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }

                    });

                }
            });
    }


    @Override
    protected void onResume() {
        super.onResume();
        flag=false;

    }

    public void SpinnerRead(){

        arrayAdapter= new ArrayAdapter<String>(RemoveCategory.this,android.R.layout.simple_spinner_item, CategoryNames);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
    }
}
