package com.example.john.akelny.User;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
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

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ShowResturantFood extends Activity {

    FirebaseDatabase database;
    DatabaseReference myRef;
    ArrayList<Food> foodArrayList;
    ImageView imageview;
    String ResturantName;
    ListView listView;
    ImageView plusImageView;
    ArrayList<Food> Cart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_resturant_food);
        Cart= new ArrayList<Food>();
        ResturantName = getIntent().getStringExtra("RName");
        listView= (ListView)findViewById(R.id.listViewFoodOrder);
        foodArrayList = new ArrayList<Food>();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Food");


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
                Cart.add(foodArrayList.get(position));
                foodArrayList.remove(position);
                CustomerAdapter customerAdapter = new CustomerAdapter();
                listView.setAdapter(customerAdapter);
            }
        });
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


            TextView FoodName = convertView.findViewById(R.id.FoodNameTextView);

            FoodName.setText(foodArrayList.get(position).FoodName);
            TextView FoodDesc = convertView.findViewById(R.id.FoodDescriptionTextView);
            FoodDesc.setText(foodArrayList.get(position).FoodDescription);


            return convertView;
        }
    }
}
