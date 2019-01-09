package com.example.john.akelny.User;


import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.john.akelny.Model.Food;
import com.example.john.akelny.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class RestrauntsFoodFragment extends Fragment {

static ArrayList <Food> Carttt;
String ResturantName;
ListView listView;
ArrayList<Food> foodArrayList;
FirebaseDatabase database;
DatabaseReference myRef;
Button mintomax;
Button maxtomin;
static String resDelFees;
ImageView imageview,ImageView;
    public RestrauntsFoodFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View v= inflater.inflate(R.layout.fragment_restraunts_food, container, false);

        maxtomin = (Button) v.findViewById(R.id.maxtomin);
        mintomax = (Button) v.findViewById(R.id.mintomax);
        Carttt= new ArrayList<Food>();
        Bundle arguments = getArguments();
        ResturantName = arguments.getString("RName");
        resDelFees=arguments.getString("DeliveryFees");



        listView= (ListView) v.findViewById(R.id.listViewFoodOrder);
        foodArrayList = new ArrayList<Food>();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Food");

        mintomax.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomerAdapter customerAdapter = new CustomerAdapter();

                Collections.sort(foodArrayList, new Comparator<Food>() {
                    @Override
                    public int compare(Food o1, Food o2) {
                        return Integer.valueOf(o1.Price).compareTo(Integer.valueOf(o2.Price));
                    }
                });
                listView.setAdapter(customerAdapter);
            }
        });

        maxtomin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomerAdapter customerAdapter = new CustomerAdapter();
                Collections.sort(foodArrayList, new Comparator<Food>() {
                    @Override
                    public int compare(Food o1, Food o2) {
                        return Integer.valueOf(o2.Price).compareTo(Integer.valueOf(o1.Price));
                    }
                });
                listView.setAdapter(customerAdapter);
            }
        });

        myRef.orderByChild("ResturantID").equalTo(ResturantName).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot datas: dataSnapshot.getChildren()){
                    Food food = datas.getValue(Food.class);
                    foodArrayList.add(food);

                }
                CustomerAdapter customerAdapter = new CustomerAdapter();
                listView.setAdapter(customerAdapter);

            }



            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Carttt.add(foodArrayList.get(position));
                foodArrayList.remove(position);
                CustomerAdapter customerAdapter = new CustomerAdapter();
                listView.setAdapter(customerAdapter);
            }
        });



        return v;
    }

    private class CustomerAdapter extends BaseAdapter {


        @Override
        public int getCount() {

            return foodArrayList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {

            return 0;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = getLayoutInflater().inflate(R.layout.foodlistview,null);
            ImageView= convertView.findViewById(R.id.imageView4);
            imageview = convertView.findViewById(R.id.FoodImageView);
            InputStream srt = null;
            Bitmap bitmap = null;
            try {
                srt = new URL(foodArrayList.get(position).FoodImage).openStream();
                bitmap = BitmapFactory.decodeStream(srt);
            } catch (IOException e) {
                e.printStackTrace();
            }

            imageview.setImageBitmap(bitmap);
            ImageView.setImageDrawable(getResources().getDrawable(R.drawable.plus));


            TextView FoodName = convertView.findViewById(R.id.FoodNameTextView);

            FoodName.setText(foodArrayList.get(position).FoodName);
            TextView FoodDesc = convertView.findViewById(R.id.FoodDescriptionTextView);
            FoodDesc.setText(foodArrayList.get(position).FoodDescription);


            return convertView;
        }
    }
}
