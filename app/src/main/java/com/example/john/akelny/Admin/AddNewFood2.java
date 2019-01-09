package com.example.john.akelny.Admin;

import android.app.Activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.john.akelny.Model.PriceSize;
import com.example.john.akelny.Model.Size;
import com.example.john.akelny.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AddNewFood2 extends Activity {
    Spinner SpinnerSize;
    ArrayList<String> SizesNames;
    FirebaseDatabase database;
    DatabaseReference myRef;
    ArrayAdapter arrayAdapter;
    ProgressDialog progressDialog;
    Button Submit;
    EditText Price;
    String FoodID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_food2);
        progressDialog = new ProgressDialog(this);
        Submit = (Button)findViewById(R.id.SubmitPriceSize);
        Price = (EditText)findViewById(R.id.PriceEditText);
        FoodID = getIntent().getStringExtra("FoodID");


        progressDialog.setTitle("Loading Information");
        progressDialog.setMessage("Loading");
        progressDialog.setCancelable(false);
        progressDialog.show();
        SpinnerSize = (Spinner) findViewById(R.id.SizesSpinner);
        SizesNames = new ArrayList<String>();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Size");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    Size x = snapshot.getValue(Size.class);
                    SizesNames.add(x.SizeName);

                }
                arrayAdapter = new ArrayAdapter<String>(AddNewFood2.this, android.R.layout.simple_spinner_item, SizesNames);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                SpinnerSize.setAdapter(arrayAdapter);
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                myRef = database.getReference("PriceSize");
                PriceSize pricesize = new PriceSize(Price.getText().toString(),SpinnerSize.getSelectedItem().toString(),FoodID);
                String key = myRef.push().getKey();
                myRef.child(key).setValue(pricesize);
                SizesNames.remove(SpinnerSize.getSelectedItem().toString());
                arrayAdapter = new ArrayAdapter<String>(AddNewFood2.this, android.R.layout.simple_spinner_item, SizesNames);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                SpinnerSize.setAdapter(arrayAdapter);

            }
        });
    }
}
