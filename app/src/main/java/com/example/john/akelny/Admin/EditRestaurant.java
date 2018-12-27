package com.example.john.akelny.Admin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.john.akelny.Model.Category;
import com.example.john.akelny.Model.Resturant;
import com.example.john.akelny.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class EditRestaurant extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference myRef;
    StorageReference mStorageRef;
    ArrayList<String> ResturantName;
    ArrayList<Resturant> resturants;
    Spinner spinnerEditResturants;
    EditText EditDeliveryFees;
    EditText EditDeliveryTime;
    EditText EditStartTime;
    EditText EditEndTime;
    Button LoadNewImage;
    Button EditResturanttoDataBase;
    int PositionChosen;
    ArrayAdapter arrayAdapter;
    boolean flag=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_restaurant);
        ResturantName = new ArrayList<String>();
        resturants= new ArrayList<Resturant>();
        EditDeliveryFees=(EditText)findViewById(R.id.DeleviryFeesEdit);
        EditDeliveryTime=(EditText)findViewById(R.id.DeleviryTimeEdit);
        EditStartTime=(EditText)findViewById(R.id.StartTimeEdit);
        EditEndTime=(EditText)findViewById(R.id.EndTimeEdit);
        EditResturanttoDataBase=(Button)findViewById(R.id.EditRestaurantToDataBase);

        spinnerEditResturants=(Spinner)findViewById(R.id.EditResturantSpinner);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Restaurants");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    if (flag==true)
                    {
                        break;
                    }
                    Resturant x = snapshot.getValue(Resturant.class);
                    ResturantName.add(x.ResuturantName);
                    resturants.add(x);

                }
                flag=true;
                SpinnerReadResturants();


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        EditResturanttoDataBase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myRef.orderByChild("ResuturantName").equalTo(resturants.get(PositionChosen).ResuturantName.toString()).addValueEventListener(new ValueEventListener(){
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot datas: dataSnapshot.getChildren()){
                            String keys=datas.getKey().toString();
                            Resturant resturant = new Resturant(resturants.get(PositionChosen).ResuturantName.toString(),Float.valueOf(EditDeliveryTime.getText().toString()),Float.valueOf(EditDeliveryFees.getText().toString()),resturants.get(PositionChosen).Logo.toString(),Float.valueOf(EditStartTime.getText().toString()),Float.valueOf(EditEndTime.getText().toString()));
                            myRef.child(keys).setValue(resturant);
                            Toast.makeText(EditRestaurant.this, "Changed Succssefully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(EditRestaurant.this,AdminMenu.class);
                            startActivity(intent);
                            break;


                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }

                });

            }
        });

        spinnerEditResturants.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    EditDeliveryFees.setText(resturants.get(position).DeliveryFees.toString());
                    EditDeliveryTime.setText(resturants.get(position).DeliveryTime.toString());
                    EditStartTime.setText(resturants.get(position).StartTime.toString());
                    EditEndTime.setText(resturants.get(position).EndTime.toString());
                    PositionChosen=position;



            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    public void SpinnerReadResturants() {

        arrayAdapter = new ArrayAdapter<String>(EditRestaurant.this, android.R.layout.simple_spinner_item, ResturantName);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerEditResturants.setAdapter(arrayAdapter);
    }
}
