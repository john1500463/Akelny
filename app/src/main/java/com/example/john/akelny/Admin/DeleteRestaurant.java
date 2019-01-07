package com.example.john.akelny.Admin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.john.akelny.MainActivity;
import com.example.john.akelny.Model.Resturant;
import com.example.john.akelny.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class DeleteRestaurant extends Activity {

    Spinner SpinnerRemoveFood;
    ArrayList<String> resturantsNames;
    FirebaseDatabase database;
    DatabaseReference myRef;
    ArrayAdapter<String> arrayAdapter;
    Button DeleteResturantFromDataBase;
    String itemselecteed;


    StorageReference mStorageRef;
    ArrayList<String> ImagesName;
    int index;
    boolean flag=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_restaurant);
        ImagesName= new ArrayList<String>();
        SpinnerRemoveFood = (Spinner) findViewById(R.id.SpinnerRemoveFood);

        resturantsNames = new ArrayList<String>();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Restaurants");
        DeleteResturantFromDataBase = (Button) findViewById(R.id.RemoveResturantFromDB);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    if (flag==true)
                    {
                        break;
                    }
                    Resturant x = snapshot.getValue(Resturant.class);
                    resturantsNames.add(x.ResuturantName);
                    ImagesName.add(x.Logo);

                }
                flag =true;
                SpinnerReadResturants();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        DeleteResturantFromDataBase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemselecteed = SpinnerRemoveFood.getSelectedItem().toString();
            //To get which image is choosen in the spinner
                for (int i=0;i<resturantsNames.size();i++){
                    if(resturantsNames.get(i).equals(itemselecteed)){
                        index=i;
                        break;
                    }
                }


                mStorageRef = FirebaseStorage.getInstance().getReferenceFromUrl(ImagesName.get(index));
                mStorageRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                  @Override
                  public void onSuccess(Void aVoid) {
//
//                      Intent intent = new Intent(DeleteRestaurant.this, MainActivity.class);
//                      startActivity(intent);

                  }
              });

                     myRef.orderByChild("ResuturantName").equalTo(itemselecteed).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot datas : dataSnapshot.getChildren()) {
                            String keys = datas.getKey().toString();
                            myRef.child(keys).removeValue();
                            for (int i = 0; i < resturantsNames.size(); i++) {
                                if (resturantsNames.get(i).equals(itemselecteed)) {
                                    resturantsNames.remove(i);
                                    SpinnerReadResturants();
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

    public void SpinnerReadResturants() {

        arrayAdapter = new ArrayAdapter<String>(DeleteRestaurant.this, android.R.layout.simple_spinner_item, resturantsNames);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerRemoveFood.setAdapter(arrayAdapter);
    }
}
