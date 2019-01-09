package com.example.john.akelny.User;


import android.app.Fragment;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.john.akelny.Model.Food;
import com.example.john.akelny.Model.Order;
import com.example.john.akelny.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class CartFragment extends Fragment {

    ListView listView;
    ArrayList <Food> food;
    ImageView plus;
    ImageView minus;
    TextView text,Price;

    FirebaseDatabase database;
    DatabaseReference myRef;
    ArrayList <String> FoodIDs;

    Order order;

    String DelFees;
    Double price=0.0;
    int Position;
    ArrayList<Integer> integers = new ArrayList<Integer>();
    TextView textView;
    Button Checkout;

    public CartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_cart, container, false);
        database = FirebaseDatabase.getInstance();

        listView=v.findViewById(R.id.listViewCart);
        Price = v.findViewById(R.id.editTotalPrice);
        food=(ArrayList<Food>)getArguments().getSerializable("CartItems");

        DelFees = getArguments().getString("DelFees");
        FoodIDs = new ArrayList<String>();

        for (int i=0;i<food.size();i++){
            integers.add(1);
            price=price+Double.valueOf(food.get(i).Price.toString());
            FoodIDs.add(food.get(i).FoodName);

        }


        price=price+Double.valueOf(DelFees);
        Price.setText(String.valueOf(price));
        CustomAdapter customAdapter = new CustomAdapter();
        listView.setAdapter(customAdapter);
        Checkout = (Button) v.findViewById(R.id.button3);
        Checkout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Order order = new Order(FoodIDs,integers,String.valueOf(price));
                myRef = database.getReference("Orders");
                String key = myRef.push().getKey();
                myRef.child(key).setValue(order);
            }
        });
        return v;
    }

    private class CustomAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return food.size();
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
            convertView = getLayoutInflater().inflate(R.layout.cartcustomlistview,null);
            plus = (ImageView)convertView.findViewById(R.id.imageViewplusCart2);
            minus = (ImageView)convertView.findViewById(R.id.imageViewMinusCart);
            plus.setImageDrawable(getResources().getDrawable(R.drawable.plus512));
            minus.setImageDrawable(getResources().getDrawable(R.drawable.minus32));
            plus.setOnClickListener(myButtonClickListenerPlus);
            minus.setOnClickListener(myButtonClickListenerMinus);
            text = (TextView) convertView.findViewById(R.id.FoodNameCustomLV);
            text.setText(food.get(position).FoodName);
            textView = (TextView) convertView.findViewById(R.id.Quantity);
            textView.setText(String.valueOf(integers.get(position)));

            return convertView;
        }
    }

    private View.OnClickListener myButtonClickListenerPlus = new View.OnClickListener() {

        @Override

        public void onClick(View v) {

            View parentRow = (View) v.getParent();

            ListView listView = (ListView) parentRow.getParent();

            Position = listView.getPositionForView(parentRow);

            integers.set(Position,integers.get(Position)+1);

            CustomAdapter customAdapter= new CustomAdapter();
            listView.setAdapter(customAdapter);
            price=0.0;
            for (int i=0;i<food.size();i++){
                int num = integers.get(i);
                for(int j=0;j<num;j++){
                    price=price+Double.valueOf(food.get(i).Price);
                }

            }
            price=price+Double.valueOf(DelFees);
            Price.setText(String.valueOf(String.valueOf(price)));


        }

    };

    private View.OnClickListener myButtonClickListenerMinus = new View.OnClickListener() {

        @Override

        public void onClick(View v) {

            View parentRow = (View) v.getParent();

            ListView listView = (ListView) parentRow.getParent();

            Position = listView.getPositionForView(parentRow);
            if(integers.get(Position)>1){

            integers.set(Position,integers.get(Position)-1);
            price=price-Double.valueOf(food.get(Position).Price);

            CustomAdapter customAdapter= new CustomAdapter();
            listView.setAdapter(customAdapter);

                price=price+Double.valueOf(DelFees);
            }

            Price.setText(String.valueOf(price));

        }

    };
}
